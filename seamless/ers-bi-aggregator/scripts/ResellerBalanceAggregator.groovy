import com.seamless.common.StringUtils
import com.seamless.customer.bi.aggregator.aggregate.AbstractAggregator
import com.seamless.customer.bi.aggregator.util.DateUtil
import com.seamless.customer.bi.aggregator.util.GenerateHash
import groovy.util.logging.Slf4j
import org.apache.commons.lang.exception.ExceptionUtils
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.SearchHit
import org.elasticsearch.search.aggregations.Aggregation
import org.elasticsearch.search.aggregations.AggregationBuilder
import org.elasticsearch.search.aggregations.AggregationBuilders
import org.elasticsearch.search.aggregations.bucket.composite.CompositeAggregationBuilder
import org.elasticsearch.search.aggregations.bucket.composite.CompositeValuesSourceBuilder
import org.elasticsearch.search.aggregations.bucket.composite.ParsedComposite
import org.elasticsearch.search.aggregations.bucket.composite.TermsValuesSourceBuilder
import org.elasticsearch.search.aggregations.metrics.ParsedSum
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.elasticsearch.search.fetch.subphase.FetchSourceContext
import org.elasticsearch.search.sort.SortBuilders
import org.elasticsearch.search.sort.SortMode
import org.elasticsearch.search.sort.SortOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.stream.Collectors


@Slf4j
public class ResellerBalanceAggregator extends AbstractAggregator {

    static final def RESELLER_BALANCE_TABLE = "reseller_balance_aggregator"


    static final def RESELLER_ACCOUNT_INFO_SQL = "SELECT a.owner,a.accountId,a.accountTypeId,a.balance,  " +
            "            IFNULL( temp.`createDate`, a.`createDate`) as 'updated_date' FROM accountmanagement.accounts a " +
            "            left join (select accountId,max(createDate)  as createDate from accountmanagement.`transactions` " +
            "            group by accountId) temp on a.`accountId` =temp.`accountId` where  a.owner not in ('operator','system') union all " +
            "            SELECT a.owner,a.accountId,a.accountTypeId,a.balance,  " +
            "            IFNULL( temp.`createDate`, a.`createDate`) as 'updated_date' FROM accountmanagement.accounts a " +
            "            left join (select accountId,max(createDate)  as createDate from accountmanagement.`transactions`" +
            "            group by accountId) temp on a.`accountId` =temp.`accountId` where a.owner in ('operator') and a.`accountTypeId`='Reseller'"


    static final def BOOKKEEPING_ACCOUNT_INFO_SQL= "SELECT a.accountId, owner as resellerId,'operator' as resellerName," +
            "            0 as status,'operator' as resellerPath,a.accountTypeId,a.balance,  " +
            "            IFNULL(temp.`createDate`, a.`createDate`) as 'updated_date'," +
            "            null as 'domainCode', null as 'domainName'," +
            "            null as 'geographicalDomain',null as 'ownerMsisdn',null as 'externalCode'," +
            "            null as 'parentName',null as 'parentMSISDN','8801700000000' as msisdn, " +
            "            'OPERATOR' as resellerType , a.owner FROM accountmanagement.accounts a left join (select accountId,max(createDate)  as createDate " +
            "               from accountmanagement.`transactions` group by accountId) temp on a.`accountId` =temp.`accountId` " +
            "            where (a.accountTypeId='BOOKKEEPING' or a.accountTypeId='RESELLER') and a.owner in ('operator','system') and a.accountId not in ('operator')";

    static final def RESELLER_CHILD_BALANCE_TABLE = "reseller_child_balance_aggregator"

    static final String UPDATE_SKIP_ACCOUNTS_SQL = "update reseller_balance_aggregator set balance=balance+? where account_id=? and account_type_id=? "

    @Value('${ResellerBalanceAggregator.limit:10}')
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

    //@Value('${ResellerBalanceAggregator.updateSkipAccounts.balance.list:BONUS,CASH,COMMISSION,TAX_BOOKKEEPING,COMMISSION_BOOKKEEPING}')
    private List<String> bookKeepingAccountsList = new ArrayList<>();

    @Value('${account_ledger.index:account_lake_}')
    private String accountLedgerIndex;



    @Autowired
    RestHighLevelClient client

    @Transactional
    @Scheduled(cron = '${ResellerBalanceAggregator.cron:*/20 * * * * ?}')
    @Override
    void aggregate() {
        try {
            log.info("ResellerBalanceAggregator started***************************************************************************" + new Date());
            //Fetch reseller info through query with limits
            insertResellerAccountInfo();
            updateBookkeepingAccountBalance();
            log.info("ResellerBalanceAggregator Completed ***************************************************************************" + new Date());
        }catch(Exception e){
            log.error(" Error in ResellerBalanceAggregator "+ ExceptionUtils.getFullStackTrace(e));
            log.error("Error message "+e.getMessage())
        }
    }


//keep
    def insertResellerAccountInfo() {
        log.info("Extracting insertResellerAccountInfo ");

        def queryResult = accountJdbcTemplate.queryForList(BOOKKEEPING_ACCOUNT_INFO_SQL);
        List<ResellerBalanceModel> operatorBookKeepingBalanceModelList = new ArrayList<>();
        if (queryResult) {
            queryResult.eachWithIndex { row, index ->
                String id = GenerateHash.createHashString( row.accountId, row.accountTypeId);
                def ownerName = row.resellerPath;
                ResellerBalanceModel resellerBalanceModel = new ResellerBalanceModel(id, row.resellerId, row.accountId,
                        row.accountTypeId, row.balance, row.updated_date, row.domainCode, row.domainName,
                        row.externalCode, row.msisdn, row.resellerType,row.resellerName,
                        row.status, row.geographicalDomain, row.parentName, row.parentMSISDN, row.resellerPath,ownerName,row.ownerMsisdn,"GP Operator");

                operatorBookKeepingBalanceModelList.add(resellerBalanceModel);
            }
            bookKeepingAccountsList = operatorBookKeepingBalanceModelList.stream().map({ acc -> acc.getAccountId() }).
                    collect(Collectors.toList());
            insertAggregationBookKeeping(operatorBookKeepingBalanceModelList);
        }

        log.info("Extracted reseller data");
    }
//keep
    private def insertAccountAggregation(List resellerModelList) {

        log.info("reseller balnce having"+ resellerModelList)
        log.info("reseller balnce having ${resellerModelList.size()} rows.")
        if (resellerModelList.size() != 0) {

            def sql = "INSERT INTO ${RESELLER_BALANCE_TABLE} (reseller_id,account_id,account_type_id,balance,updated_date) " +
                    " VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE balance = VALUES(balance)," +
                    " updated_date = VALUES(updated_date)";
            log.debug(sql)
            def batchUpdate = jdbcTemplate.batchUpdate(sql, [
                    setValues   : { ps, i ->
                        def row = resellerModelList[i]
                        ps.setString(1, row.resellerId)
                        ps.setString(2, row.accountId)
                        ps.setString(3, row.accountTypeId)
                        ps.setDouble(4, row.balance)
                        ps.setTimestamp(5, new java.sql.Timestamp(row.updatedDate.getTime()))

                    },
                    getBatchSize: { resellerModelList.size() }
            ] as BatchPreparedStatementSetter)
        }
    }

    private def insertAggregationBookKeeping(List resellerModelList) {

        log.info("reseller balnce having ${resellerModelList.size()} rows.")
        if (resellerModelList.size() != 0) {
            def sql = "INSERT INTO ${RESELLER_BALANCE_TABLE} (reseller_id,account_id,account_type_id,balance,updated_date," +
                    " domain_code,domain_name,external_code,msisdn,reseller_type,reseller_name,status" +
                    ",geographical_domain,parent_name,parent_msisdn,reseller_path,owner_name,owner_msisdn,reseller_type_name) " +
                    " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE " +
                    " domain_code = VALUES(domain_code) , domain_name = VALUES(domain_name)," +
                    " external_code = VALUES(external_code), msisdn = VALUES(msisdn) , reseller_type = VALUES(reseller_type),"+
                    " reseller_name = VALUES(reseller_name), status = VALUES(status) , geographical_domain = VALUES(geographical_domain),"+
                    " parent_name = VALUES(parent_name), parent_msisdn = VALUES(parent_msisdn) , reseller_path = VALUES(reseller_path),"+
                    " owner_name = VALUES(owner_name),owner_msisdn = VALUES(owner_msisdn),reseller_type_name=VALUES(reseller_type_name)";
            log.debug(sql)
            def batchUpdate = jdbcTemplate.batchUpdate(sql, [
                    setValues   : { ps, i ->
                        def row = resellerModelList[i]
                        ps.setString(1, row.resellerId)
                        ps.setString(2, row.accountId)
                        ps.setString(3, row.accountTypeId)
                        ps.setDouble(4, row.balance)
                        ps.setTimestamp(5, new java.sql.Timestamp(row.updatedDate.getTime()))
                        ps.setString(6, row.domainCode)
                        ps.setString(7, row.domainName)
                        ps.setString(8, row.externalCode)
                        ps.setString(9, row.msisdn)
                        ps.setString(10, row.resellerType)
                        ps.setString(11, row.resellerName)
                        ps.setString(12, row.status)
                        ps.setString(13, row.geographicalDomain)
                        ps.setString(14, row.parentName)
                        ps.setString(15, row.parentMSISDN)
                        ps.setString(16, row.resellerPath)
                        ps.setString(17, row.ownerName)
                        ps.setString(18, row.ownerMsisdn)
                        ps.setString(19, row.resellerTypeName)
                    },
                    getBatchSize: { resellerModelList.size() }
            ] as BatchPreparedStatementSetter)
        }
    }

    public NamedParameterJdbcTemplate getAccountNamedParameterJdbcTemplate()
    {
        return new NamedParameterJdbcTemplate(accounts);
    }

    def updateBookkeepingAccountBalance() {
        log.info(" Started updating bookkeeping account balance.")
        List<String> accountIds = bookKeepingAccountsList;
        log.info(" skip accounts are " + accountIds)
        String EXPORTED_TIME_SQL = "SELECT exported_time from account_ledger_sync"
        String lastExportedTimeEpoch;

        try {
            lastExportedTimeEpoch = jdbcTemplate.queryForObject(EXPORTED_TIME_SQL, String.class);
        } catch (IncorrectResultSizeDataAccessException e) {
            log.error(" no data found " + ExceptionUtils.getFullStackTrace(e))
            lastExportedTimeEpoch = null;
        }
        log.info("accountLedgerIndex:::::::::;"+accountLedgerIndex)
        long exportedEpochTime = 0;
        long exportedToTimeEpoch = Instant.now().toEpochMilli() - 300000;
        SearchRequest searchRequest;
        SearchRequest timeSearchRequest;
        if (null != lastExportedTimeEpoch && StringUtils.isNotBlank(lastExportedTimeEpoch)) {
            exportedEpochTime = Long.parseLong(lastExportedTimeEpoch);
            Date fromDate = new Date(Long.parseLong(lastExportedTimeEpoch))
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d")
            List<String> indices = getIndexList(sdf.format(fromDate), sdf.format(new Date(exportedToTimeEpoch)))
            searchRequest = new SearchRequest(indices.toArray(new String[0]));
            timeSearchRequest = new SearchRequest(indices.toArray(new String[0]));
        } else {
            searchRequest = new SearchRequest(accountLedgerIndex + "*")
            timeSearchRequest = new SearchRequest(accountLedgerIndex + "*")
        }
        log.info(" searchRequest " + searchRequest)
        log.info(" timeSearchRequest " + timeSearchRequest)
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder must = BoolQueryBuilder.newInstance()
        must.must().add(QueryBuilders.newInstance().termsQuery("account", accountIds))
        must.must().add(QueryBuilders.rangeQuery("lastUpdatedTimestamp").lt(exportedToTimeEpoch).gte(exportedEpochTime))
        searchSourceBuilder.query(must)

        searchSourceBuilder.size(0);
        //composite aggregation on accountId and accountTypeId
        List<CompositeValuesSourceBuilder<?>> sources = new ArrayList<>();
        sources.add(new TermsValuesSourceBuilder("account").field("account"))
        sources.add(new TermsValuesSourceBuilder("accountType").field("accountTypeId"))

        CompositeAggregationBuilder compositeBuilder = new CompositeAggregationBuilder("account_balance",
                sources)
        compositeBuilder.subAggregation(AggregationBuilders.sum("balance").field("transactionAmount"))
        compositeBuilder.size(10000)
        searchSourceBuilder.aggregation(compositeBuilder);
        //searchSourceBuilder.aggregation(AggregationBuilders.sum("balance").field("transactionAmount"))
        searchRequest.source(searchSourceBuilder);
        log.info(" account ledger query " + searchSourceBuilder)
        SearchResponse searchResponse = client.search(searchRequest, COMMON_OPTIONS);

        if(null!=searchResponse.getAggregations()){
            Map<String, Aggregation> map = searchResponse.getAggregations().getAsMap()
            ParsedComposite parsedComposite = map.get("account_balance")
            List<ParsedComposite.ParsedBucket> buckets = parsedComposite.getBuckets()
            String UPDATE_SKIP_ACCOUNTS_BATCH_SQL = "INSERT INTO ${RESELLER_BALANCE_TABLE} (account_id,balance,account_type_id) values (?,?,?) " +
                    "ON DUPLICATE KEY UPDATE balance = balance + VALUES(balance)"
            List<Map<String, String>> accountsUpdateList = new ArrayList<>();
            buckets.forEach({ bucket ->
                String accountId = bucket.getKey().get("account");
                String accountType = bucket.getKey().get("accountType")
                ParsedSum balanceAggregator = bucket.getAggregations().get("balance")
                double balance = balanceAggregator.getValue()
                String id = GenerateHash.createHashString( accountId, accountType);
                Map accountsMap = new HashMap();
                accountsMap.put("accountId", accountId)
                accountsMap.put("balance", balance);
                accountsMap.put("accountTypeId", accountType);
                accountsUpdateList.add(accountsMap)
            })
            jdbcTemplate.batchUpdate(UPDATE_SKIP_ACCOUNTS_BATCH_SQL, [
                    setValues   : {
                        ps, i ->
                            def row = accountsUpdateList[i]
                            ps.setString(1, row.accountId)
                            ps.setDouble(2, row.balance)
                            ps.setString(3, row.accountTypeId)
                    },
                    getBatchSize: { accountsUpdateList.size() }
            ] as BatchPreparedStatementSetter)
            log.info(" Skip account update till time " + exportedToTimeEpoch)
            log.info(" time ===>> " + new Date(exportedToTimeEpoch))
            String UPDATE_EXPORTED_TIME_SQL = "INSERT INTO account_ledger_sync (id,exported_time) values(?,?) " +
                    "ON DUPLICATE KEY UPDATE exported_time = VALUES(exported_time)"
            int update = jdbcTemplate.update(UPDATE_EXPORTED_TIME_SQL, 1, exportedToTimeEpoch)
            if (update > 0) {
                log.info(" update completed success fully")
            } else {
                log.info(" fail to store pointer")
            }

            log.info(" Completed updating skip account balance.")

            SearchSourceBuilder timeSearchSourceBuilder = new SearchSourceBuilder();
            timeSearchSourceBuilder.size(1)
            timeSearchSourceBuilder.sort("lastUpdatedTimestamp", SortOrder.DESC);
            String[] include = ["account", "timestamp", "accountTypeId","lastUpdatedTimestamp"];
            timeSearchSourceBuilder.fetchSource(include, null)
            SearchResponse timeSearchResponse;
            String UPDATE_BOOKKEEPING_ACCOUNT_SQL = "update  ${RESELLER_BALANCE_TABLE} set updated_date=? where account_id = ? and account_type_id=?"
            accountIds.forEach({ account ->
                timeSearchSourceBuilder.query(QueryBuilders.termQuery("account", account))
                timeSearchRequest.source(timeSearchSourceBuilder)
                log.info(" timeSearchRequest"+timeSearchRequest)
                timeSearchResponse = client.search(timeSearchRequest, COMMON_OPTIONS);
                SearchHit[] hits = timeSearchResponse.getHits().getHits();
                if (null != hits && hits.size() > 0) {
                    Map<String, Object> sourceMap = hits[0].getSourceAsMap()
                    if (sourceMap.containsKey("lastUpdatedTimestamp") && sourceMap.get("lastUpdatedTimestamp") != null) {
                        def update1 = jdbcTemplate.update(UPDATE_BOOKKEEPING_ACCOUNT_SQL, new Timestamp(Long.parseLong(sourceMap.get("lastUpdatedTimestamp").toString()))
                                , sourceMap.get("account"), sourceMap.get("accountTypeId"))
                    }
                }
            })
        }
        else{
            log.info(" No data present in searchResponse.getAggregations().getAsMap() ")
        }
        log.info(" Skip account time updated ")

    }


    public  List<String> getIndexList(String  fromDateStr, String  toDateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        LocalDate fromDate = LocalDate.parse(fromDateStr, formatter);
        LocalDate toDate = LocalDate.parse(toDateStr, formatter);
        List<String> numberWeeks = new ArrayList<>();
        while (fromDate.isBefore(toDate)) {
            numberWeeks.add(formatWeek(fromDate));
            fromDate = fromDate.plusWeeks(1);
        }
        // Now fromDate is on or after toDate, but are they in the same week?
        if (fromDate.get(WeekFields.ISO.weekOfWeekBasedYear())
                == toDate.get(WeekFields.ISO.weekOfWeekBasedYear())) {
            numberWeeks.add(formatWeek(fromDate));
        }
        return numberWeeks;
    }

    public String formatWeek(LocalDate currentDate) {
        return String.format(accountLedgerIndex+"%dw%02d*",

                currentDate.get(WeekFields.ISO.weekBasedYear()),
                currentDate.get(WeekFields.ISO.weekOfWeekBasedYear()));
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

    ResellerBalanceModel() {

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
/***
 CREATE TABLE IF NOT EXISTS  `bi`.`reseller_balance_aggregator` (
 `id` varchar(200) NOT NULL,
 `reseller_id` varchar(200) NOT NULL,
 `account_id` varchar(200) NOT NULL,
 `account_type_id` varchar(20) NOT NULL,
 `balance` decimal(65,5) DEFAULT NULL,
 `updated_date` timestamp NULL DEFAULT NULL,
 `domain_code` varchar(30) DEFAULT NULL,
 `domain_name` varchar(30) DEFAULT NULL,
 `external_code` varchar(50) DEFAULT NULL,
 `msisdn` varchar(200) DEFAULT NULL,
 `reseller_type` varchar(200) DEFAULT NULL,
 `reseller_name` varchar(200) DEFAULT NULL,
 `status` tinyint(4) DEFAULT NULL,
 `parent_name` varchar(200) DEFAULT NULL,
 `reseller_path` varchar(255) DEFAULT NULL,
 `parent_msisdn` varchar(200) DEFAULT NULL,
 `geographical_domain` varchar(30) DEFAULT NULL,
 `owner_name` varchar(200) DEFAULT NULL,
 `owner_msisdn` varchar(200) DEFAULT NULL,
 PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 **/

/**
 CREATE TABLE IF NOT EXISTS `reseller_child_balance_aggregator` (
 `id` varchar(200) NOT NULL,
 `parent_id` varchar(200) NOT NULL,
 `child_balance` decimal(65,5) DEFAULT NULL,
 PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 **/

