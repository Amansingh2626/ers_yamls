
import com.seamless.common.StringUtils
import com.seamless.customer.bi.aggregator.aggregate.AbstractAggregator
import com.seamless.customer.bi.aggregator.util.GenerateHash
import groovy.util.logging.Slf4j
import org.apache.commons.lang.exception.ExceptionUtils
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.transaction.annotation.Transactional
import javax.sql.DataSource
import groovy.time.TimeCategory
import groovy.time.TimeDuration

import groovy.sql.Sql;
@Slf4j
public class ResellerDataAggregator extends AbstractAggregator {
    static final def RESELLER_BALANCE_TABLE = "reseller_balance_aggregator"

    static final def RESELLER_REFILL_INFO_SQL = "SELECT cr.tag as resellerId,cr.name as resellerName," +
            "            cr.status as status,cr.reseller_path as resellerPath,  " +
            "            extp.`parameter_value` as 'domainCode', extp1.`parameter_value` as 'domainName'," +
            "            extp3.`parameter_value` as 'geographicalDomain',extp4.`parameter_value` as 'ownerMsisdn',extp5.`parameter_value` as 'ownerName',extp2.`parameter_value` as 'externalCode'," +
            "            cs1.`name` as 'parentName',dev1.`address` as 'parentMSISDN',dev.`address` as msisdn, " +
            "            rtp.`id` as resellerType, rtp.`name` as resellerTypeName from `Refill`.`commission_receivers` cr " +
            "            left join `Refill`.`reseller_extra_params` extp on cr.`receiver_key`=extp.`receiver_key`  and extp.`parameter_key`='domainCode' " +
            "            left join `Refill`.`reseller_extra_params` extp1 on cr.`receiver_key`=extp1.`receiver_key` and  extp1.`parameter_key`='domainName'" +
            "            left join `Refill`.`reseller_extra_params` extp2 on cr.`receiver_key`=extp2.`receiver_key` and  extp2.`parameter_key`='externalCode'" +
            "            left join `Refill`.`reseller_extra_params` extp3 on cr.`receiver_key`=extp3.`receiver_key` and  extp3.`parameter_key`='regionName'" +
            "            left join `Refill`.`reseller_extra_params` extp4 on cr.`receiver_key`=extp4.`receiver_key` and  extp4.`parameter_key`='ownerMsisdn'" +
            "            left join `Refill`.`reseller_extra_params` extp5 on cr.`receiver_key`=extp5.`receiver_key` and  extp5.`parameter_key`='ownerName'" +
            "            left join `Refill`.`extdev_devices` dev  on cr.`receiver_key`=dev.`device_key` " +
            "            left join `Refill`.`reseller_types` rtp on cr.`type_key`=rtp.`type_key` " +
            "            left join `Refill`.`reseller_hierarchy` rh on cr.`receiver_key` = rh.`child_key`" +
            "            left join `Refill`.`commission_receivers` cs1 on  cs1.`receiver_key` = rh.`parent_key`" +
            "            left join `Refill`.`extdev_devices` dev1  on cs1.`receiver_key`=dev1.`device_key` order by resellerId ";

    static final def RESELLER_CHILD_BALANCE_INFO_SQL = "INSERT INTO  reseller_child_balance_aggregator (parent_id,child_balance) \n" +
            "select temp.parent_id ,sum(temp.balance) from(\n" +
            "SELECT DISTINCT p.reseller_id as parent_id ,c.reseller_id as child_id, c.balance as balance\n" +
            "FROM reseller_balance_aggregator p\n" +
            "INNER JOIN reseller_balance_aggregator c\n" +
            "ON c.reseller_path LIKE CONCAT('%',CONCAT('/', p.reseller_id,'/') , '%'))\n" +
            "as temp group by temp.parent_id\n" +
            "ON DUPLICATE KEY UPDATE child_balance = VALUES(child_balance)"


    @Value('${ResellerDataAggregator.limit:10}')
    int limit

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier(value="accountsDataSource")
    private DataSource accounts;

    @Autowired
    @Qualifier(value="refill")
    protected JdbcTemplate refillJdbcTemplate;

    @Autowired
    @Qualifier(value="accounts")
    protected JdbcTemplate accountJdbcTemplate;

    @Value('${ResellerDataAggregator.updateSkipAccounts.balance.list:BONUS,CASH,COMMISSION,TAX_BOOKKEEPING,COMMISSION_BOOKKEEPING}')
    private String bookKeepingAccountsList;

    @Value('${account_ledger.index:account_lake_}')
    private String accountLedgerIndex;



    @Autowired
    RestHighLevelClient client

    @Transactional
    @Scheduled(cron = '${ResellerDataAggregator.cron:*/20 * * * * ?}')
    @Override
    void aggregate() {
        try {
            log.info("ResellerDataAggregator started***************************************************************************" + new Date());
            //Fetch reseller info through query with limits
            //extractResellerDataAndInsert();
            insertChildBalanceAggregation()
            log.info("ResellerDataAggregator Completed ***************************************************************************" + new Date());
        }catch(Exception e){
            log.error(" Error in ResellerDataAggregator "+ ExceptionUtils.getFullStackTrace(e));
            log.error("Error message "+e.getMessage())
        }
    }

    def extractResellerDataAndInsert() {
        log.info("Extracting reseller data ");
        log.debug("Query ::"+RESELLER_REFILL_INFO_SQL);
        def resellerRefillResult = refillJdbcTemplate.queryForList(RESELLER_REFILL_INFO_SQL);
        List<ResellerBalanceModel> resellerBalanceModeCombinelList = new ArrayList<>();
        if(resellerRefillResult) {
            Map<String, ResellerBalanceModel> resellerRefillMap = new HashMap<>();
            resellerRefillResult.forEach({ row ->
                def ownerName = "";
/*                if (!StringUtils.isBlank(row.resellerPath)) {
                    String[] resellerPathArray = row.resellerPath.split("/");
                    if (resellerPathArray.size() > 1) {
                        ownerName = resellerPathArray[1]
                    }
                }*/
                resellerBalanceModeCombinelList.add(new ResellerBalanceModel(null, row.resellerId, null, null, null, null,
                        row.domainCode, row.domainName, row.externalCode, row.msisdn, row.resellerType, row.resellerName,
                        row.status, row.geographicalDomain, row.parentName, row.parentMSISDN, row.resellerPath, row.ownerName, row.ownerMsisdn,
                        row.resellerTypeName
                ))
            })
            insertAggregation(resellerBalanceModeCombinelList);
        }
    }

    private def insertAggregation(List resellerModelList) {

        log.info("reseller Data having ${resellerModelList.size()} rows.")
        if (resellerModelList.size() != 0) {
            def sql = "UPDATE ${RESELLER_BALANCE_TABLE} set domain_code=?, domain_name=?, external_code=?, msisdn=?, reseller_type=?, reseller_name=?, status=?," +
                    " geographical_domain=?, parent_name=?, parent_msisdn=?, reseller_path=?, owner_name=?, owner_msisdn=?, reseller_type_name=? where reseller_id=?";
            log.debug(sql)
            def batchUpdate = jdbcTemplate.batchUpdate(sql, [
                    setValues   : { ps, i ->
                        def row = resellerModelList[i]
                        ps.setString(1, row.domainCode)
                        ps.setString(2, row.domainName)
                        ps.setString(3, row.externalCode)
                        ps.setString(4, row.msisdn)
                        ps.setString(5, row.resellerType)
                        ps.setString(6, row.resellerName)
                        ps.setString(7, row.status)
                        ps.setString(8, row.geographicalDomain)
                        ps.setString(9, row.parentName)
                        ps.setString(10, row.parentMSISDN)
                        ps.setString(11, row.resellerPath)
                        ps.setString(12, row.ownerName)
                        ps.setString(13, row.ownerMsisdn)
                        ps.setString(14, row.resellerTypeName)
                        ps.setString(15,row.resellerId)
                    },
                    getBatchSize: { resellerModelList.size() }
            ] as BatchPreparedStatementSetter)
        }
    }



    private def insertChildBalanceAggregation() {
/*
        log.info("inserting children balance")
        log.debug(RESELLER_CHILD_BALANCE_INFO_SQL)
        jdbcTemplate.execute(RESELLER_CHILD_BALANCE_INFO_SQL)*/
        Date start = new Date()

        // Creating a connection to the database
        def sql = Sql.newInstance(jdbcTemplate.getDataSource())

        int resellerCount = 0;
        sql.eachRow('select reseller_id from reseller_balance_aggregator where reseller_type !="RET"' ){ row ->
            println("rows :: "+ row['reseller_id'])
            log.info("rows :: "+ row['reseller_id'])
            def result = sql.rows('select reseller_id, sum(balance) as child_balance from reseller_balance_aggregator r where MATCH(r.reseller_path) AGAINST(?) and reseller_id !=?', "/"+row['reseller_id']+"/",row['reseller_id'])
            def resId =  row['reseller_id']
            def chBal = result.get(0).child_balance
            if (resId?.trim())
            {
                sql.executeInsert """
                INSERT INTO reseller_child_balance_aggregator (parent_id, child_balance)
                VALUES (${resId}, ${chBal})
                ON DUPLICATE KEY UPDATE child_balance = VALUES(child_balance)
                """
                resellerCount++
            }

        }
        println("resellerCount: " + resellerCount)
        sql.close()
        Date stop = new Date()

        TimeDuration td = TimeCategory.minus( stop, start )
        println td
    }


}

class ResellerBalanceModel {
    private String id;
    private String resellerId;
    private String accountId;
    private String accountTypeId;
    private Double balance;
    private Date updatedDate;
    private String domainCode;
    private String domainName;
    private String externalCode;
    private String msisdn;
    private String resellerType;
    private String resellerName;
    private String status;
    private String geographicalDomain;
    private String parentName;
    private String parentMSISDN;
    private String resellerPath;
    private String ownerName;
    private String ownerMsisdn;
    private String resellerTypeName;

    ResellerBalanceModel(String id, String resellerId, String accountId, String accountTypeId,
                         Double balance, Date updatedDate, String domainCode, String domainName,
                         String externalCode, String msisdn, String resellerType, String resellerName,
                         Integer status, String geographicalDomain, String parentName, String parentMSISDN,
                         String resellerPath,String ownerName,String ownerMsisdn,String resellerTypeName) {
        this.id = id
        this.resellerId = resellerId
        this.accountId = accountId
        this.accountTypeId = accountTypeId
        this.balance = balance
        this.updatedDate = updatedDate
        this.domainCode = domainCode
        this.domainName = domainName
        this.externalCode = externalCode
        this.msisdn = msisdn
        this.resellerType = resellerType
        this.resellerName = resellerName
        this.status = status
        this.geographicalDomain = geographicalDomain
        this.parentName = parentName
        this.parentMSISDN = parentMSISDN
        this.resellerPath = resellerPath
        this.ownerName = ownerName
        this.ownerMsisdn=ownerMsisdn
        this.resellerTypeName = resellerTypeName;
    }


    String getResellerId() {
        return resellerId
    }

    void setResellerId(String resellerId) {
        this.resellerId = resellerId
    }

    String getAccountId() {
        return accountId
    }

    void setAccountId(String accountId) {
        this.accountId = accountId
    }

    String getAccountTypeId() {
        return accountTypeId
    }

    void setAccountTypeId(String accountTypeId) {
        this.accountTypeId = accountTypeId
    }

    Double getBalance() {
        return balance
    }

    void setBalance(Double balance) {
        this.balance = balance
    }

    Date getUpdatedDate() {
        return updatedDate
    }

    void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate
    }

    String getDomainCode() {
        return domainCode
    }

    void setDomainCode(String domainCode) {
        this.domainCode = domainCode
    }

    String getDomainName() {
        return domainName
    }

    void setDomainName(String domainName) {
        this.domainName = domainName
    }

    String getExternalCode() {
        return externalCode
    }

    void setExternalCode(String externalCode) {
        this.externalCode = externalCode
    }

    String getMsisdn() {
        return msisdn
    }

    void setMsisdn(String msisdn) {
        this.msisdn = msisdn
    }

    String getResellerType() {
        return resellerType
    }

    void setResellerType(String resellerType) {
        this.resellerType = resellerType
    }

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    String getResellerName() {
        return resellerName
    }

    void setResellerName(String resellerName) {
        this.resellerName = resellerName
    }

    String getStatus() {
        return status
    }

    void setStatus(String status) {
        this.status = status
    }

    String getGeographicalDomain() {
        return geographicalDomain
    }

    void setGeographicalDomain(String geographicalDomain) {
        this.geographicalDomain = geographicalDomain
    }

    String getParentName() {
        return parentName
    }

    void setParentName(String parentName) {
        this.parentName = parentName
    }

    String getParentMSISDN() {
        return parentMSISDN
    }

    void setParentMSISDN(String parentMSISDN) {
        this.parentMSISDN = parentMSISDN
    }

    String getResellerPath() {
        return resellerPath
    }

    void setResellerPath(String resellerPath) {
        this.resellerPath = resellerPath
    }

    String getOwnerName() {
        return ownerName
    }

    void setOwnerName(String ownerName) {
        this.ownerName = ownerName
    }

    String getOwnerMsisdn() {
        return ownerMsisdn
    }

    void setOwnerMsisdn(String ownerMsisdn) {
        this.ownerMsisdn = ownerMsisdn
    }

    String getResellerTypeName() {
        return resellerTypeName
    }

    void setResellerTypeName(String resellerTypeName) {
        this.resellerTypeName = resellerTypeName
    }
}

class ResellerChildBalanceModel {
    private String id;
    private String parentId;
    private Double childBalance;


    ResellerChildBalanceModel(String id, String parentId, Double childBalance) {
        this.id = id
        this.parentId = parentId
        this.childBalance = childBalance
    }

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    String getParentId() {
        return parentId
    }

    void setParentId(String parentId) {
        this.parentId = parentId
    }

    Double getChildBalance() {
        return childBalance
    }

    void setChildBalance(Double childBalance) {
        this.childBalance = childBalance
    }

}

