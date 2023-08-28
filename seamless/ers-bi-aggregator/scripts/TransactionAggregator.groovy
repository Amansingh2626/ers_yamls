package com.seamless.customer.bi.aggregator.aggregate

import com.seamless.customer.bi.aggregator.model.ReportIndex
import com.seamless.customer.bi.aggregator.util.DateUtil
import com.seamless.customer.bi.aggregator.util.DateFormatter
import groovy.util.logging.Slf4j
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.action.search.SearchScrollRequest
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.core.TimeValue
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.rest.RestStatus
import org.elasticsearch.search.SearchHit
import org.elasticsearch.search.SearchHits
import org.elasticsearch.search.sort.SortOrder
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.transaction.annotation.Transactional
import java.text.SimpleDateFormat

/**
 *
 *
 *
 *
 */
@Slf4j
//@DynamicMixin
public class TransactionAggregator extends AbstractAggregator {
    static final def TABLE = "transaction_aggregator"

    @Autowired
    RestHighLevelClient client

    @Autowired
    protected JdbcTemplate jdbcTemplate

    @Value('${TransactionAggregator.bulkInsertionMode:false}')
    boolean bulkInsertionMode

    @Value('${TransactionAggregator.bulkInsertionModeFromDateString:2020-08-03}')
    String bulkInsertionModeFromDateString

    @Value('${TransactionAggregator.bulkInsertionModeToDateString:2020-08-09}')
    String bulkInsertionModeToDateString



    @Value('${DateUtil.timeOffset:+5h+30m}')
    String timeOffset

    @Value('${TransactionAggregator.scrollSize:1000}')
    int scrollSize

    @Transactional
    @Scheduled(cron = '${TransactionAggregator.cron:*/3 * * * * ?}')
    public void aggregate() {

        log.info("********** TransactionAggregator Aggregator started at " + new Date())
        if (bulkInsertionMode) {

            log.info("bulkInsertionModeFromDateString: " + bulkInsertionModeFromDateString)
            log.info("bulkInsertionModeToDateString: " + bulkInsertionModeToDateString)

            List<String> indices = DateUtil.getIndexList(bulkInsertionModeFromDateString, bulkInsertionModeToDateString)
            //need to change

            for (String index : indices) {
                //fetch data from ES
                try {
                    List<TransactionAggregatorModel> transactionSummaryModels = aggregateDataES(index,
                            bulkInsertionModeFromDateString, bulkInsertionModeToDateString)
                    insertAggregation(transactionSummaryModels)
                    Thread.sleep(50)
                } catch (InterruptedException e) {
                    log.error(e.getMessage())
                }
                catch (Exception e) {
                    log.error(e.getMessage())
                }
            }

        } else {
            List<ReportIndex> indices = DateUtil.getIndex()

            for (ReportIndex index : indices) {

                log.info(index.toString())
                //fetch data from ES
                List<TransactionAggregatorModel> transactionSummaryModels = aggregateDataES(index.getIndexName(), index
                        .getStartDate(), index.getEndDate())
                insertAggregation(transactionSummaryModels)
            }
        }
        log.info("********** TransactionAggregator Aggregator ended at " + new Date())
    }


    private List<TransactionAggregatorModel> aggregateDataES(String index, String fromDate, String toDate) {

        SearchRequest searchRequest = new SearchRequest(index)
        SearchSourceBuilder searchSourceBuilder = fetchInput(fromDate, toDate)
        searchRequest.source(searchSourceBuilder)
        searchRequest.scroll(TimeValue.timeValueMinutes(5))
        SearchResponse searchResponse = generateSearchResponse(searchRequest)
        List<TransactionAggregatorModel> transactionSummaryModels = generateResponse(searchResponse)
        String scrollId =  searchResponse.getScrollId()
        log.info("hits size outside loop for the first time:::"+searchResponse.getHits().size())
        while(searchResponse.getHits().size()!=0) {
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId)
            scrollRequest.scroll(TimeValue.timeValueMinutes(5))
            searchResponse = generateScrollSearchResponse(scrollRequest)
            log.info("_________________hits size inside loop _____________________"+searchResponse.getHits().size())
            transactionSummaryModels.addAll(generateResponse(searchResponse))
            scrollId = searchResponse.getScrollId()
        }
        return transactionSummaryModels
    }

    private SearchResponse generateSearchResponse(SearchRequest searchRequest) {
        SearchResponse searchResponse = null
        log.info("*******Request:::: " + searchRequest.toString())
        try {
            searchResponse = client.search(searchRequest, COMMON_OPTIONS)
        } catch (Exception e) {
            log.error("Error mapping rule " + searchRequest + "\nError message : " + e)
        }
        return searchResponse
    }

    private SearchResponse generateScrollSearchResponse(SearchScrollRequest scrollRequest) {
        SearchResponse searchResponse = null
        try {
            searchResponse = client.scroll(scrollRequest, COMMON_OPTIONS)
        } catch (Exception e) {
            log.error("Error mapping rule " + scrollRequest + "\nError message : " + e)
        }
        return searchResponse
    }
    private SearchSourceBuilder fetchInput(String fromDate, String toDate) {
        String transactionProfiles="SALE";
        String[] includeFields =["senderResellerId","SenderResellerName","ReceiverResellerName","receiverResellerId","RECEIVER_FIELD_domainCode","receiverMSISDN","ersReference","TRANSACTION_PROFILE","TransactionType","Channel","RECEIVER_FIELD_externalCode","ClientReference","transactionAmount","RECEIVER_COMMISSION_AMOUNT","RECEIVER_PAYABLE_AMOUNT","timestamp", "instrumentType", "paymentInstrumentDate", "paymentInstrumentNumber", "extTxnDate","UPLIFT_COMMISSION_AMOUNT","UPLIFT_COMMISSION_CURRENCY"];
        String[] excludeFields = []
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termsQuery("resultCode", 0))
                .filter(QueryBuilders.termsQuery("transactionProfile.keyword", transactionProfiles.split(",")))

        if (!bulkInsertionMode) {
            queryBuilder = queryBuilder.filter(QueryBuilders.rangeQuery("timestamp").gte("now" + timeOffset + "-13h/d").lt("now" + timeOffset + "+1h/d")
                    .includeLower(true).includeUpper(true))
        }
        searchSourceBuilder.query(queryBuilder).size(scrollSize).fetchSource(includeFields, excludeFields).sort("_doc",SortOrder.ASC).docValueField("timestamp", "epoch_millis")
        return searchSourceBuilder
    }

    private List<TransactionAggregatorModel> generateResponse(SearchResponse searchResponse){
        List<TransactionAggregatorModel> transactionAggregatorModelList = new ArrayList<>()

        if (searchResponse == null) {
            log.info("******* Null response received ")
        } else {
            RestStatus status = searchResponse.status()
            log.debug("response status -------------" + status)
            HashMap<String, TransactionAggregatorModel> transactionAggregatorModelMap = new HashMap<>()

            if (status == RestStatus.OK) {
                SearchHits searchHits = searchResponse.getHits()

                for (SearchHit searchHit : searchHits.getHits()) {
                    if(searchHit != null){
                        Map<String, Object> searchHitMap = searchHit.getSourceAsMap()
                        if(searchHitMap != null){
                            String transactionProfile =searchHitMap.get("TRANSACTION_PROFILE")


                            String ersReference =searchHitMap.get("ersReference")
                            String senderResellerId =searchHitMap.get("senderResellerId")
                            String receiverResellerId =searchHitMap.get("receiverResellerId")
                            String RECEIVER_FIELD_DomainCode =searchHitMap.get("RECEIVER_FIELD_domainCode")
                            String receiverMSISDN =searchHitMap.get("receiverMSISDN")
                            String TransactionType =searchHitMap.get("TransactionType")
                            String Channel =searchHitMap.get("Channel")
                            Date transaction_time =DateFormatter.formatDate(searchHitMap.get("timestamp"))
                            Double transactionAmount=Double.valueOf(searchHitMap.get("transactionAmount"))
                            String externalCode =searchHitMap.get("RECEIVER_FIELD_externalCode")
                            String externalTxnNumber =searchHitMap.get("ClientReference")
                            Double receiver_amount=Double.valueOf(searchHitMap.get("RECEIVER_COMMISSION_AMOUNT"))
                            Double uplift_amount=Double.valueOf(searchHitMap.get("UPLIFT_COMMISSION_AMOUNT"))
                            String RECEIVER_COMMISSION_VALUE = ((receiver_amount+uplift_amount) +" "+ searchHitMap.get("UPLIFT_COMMISSION_CURRENCY"))
                            String RECEIVER_PAYABLE_AMOUNT =searchHitMap.get("RECEIVER_PAYABLE_AMOUNT")
                            String instrumentType = searchHitMap.get("instrumentType")
                            String senderResellerName = searchHitMap.get("SenderResellerName")
                            String receiverResellerName = searchHitMap.get("ReceiverResellerName")

                            SimpleDateFormat dayFormatter = new SimpleDateFormat("yyyy-MM-dd");

                            Date paymentInstrumentDate = dayFormatter.parse(searchHitMap.get("paymentInstrumentDate") as String);
                            String paymentInstrumentNumber = searchHitMap.get("paymentInstrumentNumber")
                            Date extTxnDate = null;

                            if(searchHitMap.containsKey("extTxnDate")){
                                extTxnDate = dayFormatter.parse(searchHitMap.get("extTxnDate") as String)
                            }

                            TransactionAggregatorModel transactionAggregatorModel = new TransactionAggregatorModel(
                                    senderResellerId,
                                    receiverResellerId,
                                    RECEIVER_FIELD_DomainCode,
                                    receiverMSISDN,
                                    transactionAmount,
                                    ersReference,
                                    transactionProfile,
                                    TransactionType,
                                    transaction_time,
                                    Channel,
                                    externalCode,
                                    externalTxnNumber,
                                    RECEIVER_COMMISSION_VALUE,
                                    RECEIVER_PAYABLE_AMOUNT,
                                    instrumentType,
                                    paymentInstrumentDate,
                                    paymentInstrumentNumber,
                                    extTxnDate,
                                    senderResellerName,
                                    receiverResellerName
                            )
                            transactionAggregatorModelMap.put(ersReference, transactionAggregatorModel)
                        }

                    }
                }
            }
            transactionAggregatorModelMap.each {
                entry -> transactionAggregatorModelList.add(entry.value)
            }
        }
        return transactionAggregatorModelList
    }

    private def insertAggregation(List transactionAggregatorModelList) {
        log.info("TransactionAggregator Aggregated into ${transactionAggregatorModelList.size()} rows.")
        if (transactionAggregatorModelList.size() != 0) {
            def sql = "INSERT INTO ${TABLE} (sender_reseller_id,receiver_reseller_id,domain_code,receiver_MSISDN,ers_reference,transaction_profile,transaction_amount,transaction_type,external_code,external_txn_number,receiver_commission_value,receiver_payable_value,channel,transaction_date,instrument_type,payment_instrument_date,payment_instrument_number, external_txn_date," +
                    "sender_reseller_name,receiver_reseller_name) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE ers_reference=ers_reference"
            log.debug(sql)

            try{
                def batchUpdate = jdbcTemplate.batchUpdate(sql, [
                        setValues   : { ps, i ->
                            def row = transactionAggregatorModelList[i]
                            def index = 0
                            ps.setString(++index, row.senderResellerId)
                            ps.setString(++index, row.receiverResellerId)
                            ps.setString(++index, row.RECEIVER_FIELD_DomainCode)
                            ps.setString(++index, row.receiverMSISDN)
                            ps.setString(++index, row.ersReference)
                            ps.setString(++index, row.TRANSACTION_PROFILE)
                            ps.setBigDecimal(++index, row.transactionAmount)
                            ps.setString(++index, row.TransactionType)
                            ps.setString(++index, row.externalCode)
                            ps.setString(++index, row.externalTxnNumber)
                            ps.setString(++index, row.RECEIVER_COMMISSION_VALUE)
                            ps.setString(++index, row.RECEIVER_PAYABLE_AMOUNT)
                            ps.setString(++index, row.Channel)
                            ps.setDate(++index,  new java.sql.Date(row.transaction_time.getTime()))
                            ps.setString(++index, row.instrumentType)
                            ps.setDate(++index, new java.sql.Date(row.paymentInstrumentDate.getTime()))
                            ps.setString(++index, row.paymentInstrumentNumber)
                            ps.setDate(++index,  row.extTxnDate!=null?new java.sql.Date(row.extTxnDate.getTime()):null)
                            ps.setString(++index,row.senderResellerName)
                            ps.setString(++index,row.receiverResellerName)
                        },
                        getBatchSize: { transactionAggregatorModelList.size() }
                ] as BatchPreparedStatementSetter)
            }catch(Exception ex){
                log.info("Exception :: " + ex)
            }
        }
    }

}


class TransactionAggregatorModel {

    private String senderResellerId
    private String receiverResellerId
    private String RECEIVER_FIELD_DomainCode
    private String receiverMSISDN
    private Double transactionAmount
    private String ersReference
    private String TRANSACTION_PROFILE
    private String TransactionType
    private Date transaction_time
    private String Channel
    private String externalCode
    private String externalTxnNumber
    private String RECEIVER_COMMISSION_VALUE
    private String RECEIVER_PAYABLE_AMOUNT
    private String instrumentType
    private Date paymentInstrumentDate
    private String paymentInstrumentNumber
    private Date extTxnDate
    private String senderResellerName
    private String receiverResellerName

    TransactionAggregatorModel(
            String senderResellerId,
            String receiverResellerId,
            String RECEIVER_FIELD_DomainCode,
            String receiverMSISDN,
            Double transactionAmount,
            String ersReference,
            String TRANSACTION_PROFILE,
            String transactionType,
            Date transaction_time,
            String Channel,
            String externalCode,
            String externalTxnNumber,
            String RECEIVER_COMMISSION_VALUE,
            String RECEIVER_PAYABLE_AMOUNT,
            String instrumentType,
            Date paymentInstrumentDate,
            String paymentInstrumentNumber,
            Date extTxnDate,
            String senderResellerName,
            String receiverResellerName
    ) {
        this.senderResellerId = senderResellerId
        this.receiverResellerId = receiverResellerId
        this.RECEIVER_FIELD_DomainCode = RECEIVER_FIELD_DomainCode
        this.receiverMSISDN = receiverMSISDN
        this.transactionAmount = transactionAmount
        this.ersReference = ersReference
        this.Channel = Channel
        this.TRANSACTION_PROFILE = TRANSACTION_PROFILE
        TransactionType = transactionType
        this.transaction_time = transaction_time
        this.externalCode = externalCode
        this.externalTxnNumber = externalTxnNumber
        this.RECEIVER_COMMISSION_VALUE = RECEIVER_COMMISSION_VALUE
        this.RECEIVER_PAYABLE_AMOUNT = RECEIVER_PAYABLE_AMOUNT
        this.instrumentType = instrumentType
        this.paymentInstrumentDate = paymentInstrumentDate
        this.paymentInstrumentNumber = paymentInstrumentNumber
        this.extTxnDate = extTxnDate
        this.senderResellerName=senderResellerName
        this.receiverResellerName=receiverResellerName
    }


    @Override
    public String toString() {
        return "TransactionAggregatorModel{" +
                "senderResellerId='" + senderResellerId + '\'' +
                ", receiverResellerId='" + receiverResellerId + '\'' +
                ", RECEIVER_FIELD_DomainCode='" + RECEIVER_FIELD_DomainCode + '\'' +
                ", receiverMSISDN='" + receiverMSISDN + '\'' +
                ", transactionAmount=" + transactionAmount +
                ", ersReference='" + ersReference + '\'' +
                ", TRANSACTION_PROFILE='" + TRANSACTION_PROFILE + '\'' +
                ", TransactionType='" + TransactionType + '\'' +
                ", transaction_time=" + transaction_time +
                ", Channel='" + Channel + '\'' +
                ", externalCode='" + externalCode + '\'' +
                ", externalTxnNumber='" + externalTxnNumber + '\'' +
                ", RECEIVER_COMMISSION_VALUE='" + RECEIVER_COMMISSION_VALUE + '\'' +
                ", RECEIVER_PAYABLE_AMOUNT='" + RECEIVER_PAYABLE_AMOUNT + '\'' +
                ", instrumentType='" + instrumentType + '\'' +
                ", paymentInstrumentDate=" + paymentInstrumentDate +
                ", paymentInstrumentNumber='" + paymentInstrumentNumber + '\'' +
                ", extTxnDate=" + extTxnDate +
                ", senderResellerName='" + senderResellerName + '\'' +
                ", receiverResellerName='" + receiverResellerName + '\'' +
                '}';
    }

    String getSenderResellerId() {
        return senderResellerId
    }

    void setSenderResellerId(String senderResellerId) {
        this.senderResellerId = senderResellerId
    }

    String getReceiverResellerId() {
        return receiverResellerId
    }

    void setReceiverResellerId(String receiverResellerId) {
        this.receiverResellerId = receiverResellerId
    }

    String getRECEIVER_FIELD_DomainCode() {
        return RECEIVER_FIELD_DomainCode
    }

    void setRECEIVER_FIELD_DomainCode(String RECEIVER_FIELD_DomainCode) {
        this.RECEIVER_FIELD_DomainCode = RECEIVER_FIELD_DomainCode
    }

    String getReceiverMSISDN() {
        return receiverMSISDN
    }

    void setReceiverMSISDN(String receiverMSISDN) {
        this.receiverMSISDN = receiverMSISDN
    }

    Double getTransactionAmount() {
        return transactionAmount
    }

    void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount
    }

    String getErsReference() {
        return ersReference
    }

    void setErsReference(String ersReference) {
        this.ersReference = ersReference
    }

    String getTRANSACTION_PROFILE() {
        return TRANSACTION_PROFILE
    }

    void setTRANSACTION_PROFILE(String TRANSACTION_PROFILE) {
        this.TRANSACTION_PROFILE = TRANSACTION_PROFILE
    }

    String getTransactionType() {
        return TransactionType
    }

    void setTransactionType(String transactionType) {
        TransactionType = transactionType
    }

    Date getTransaction_time() {
        return transaction_time
    }

    void setTransaction_time(Date transaction_time) {
        this.transaction_time = transaction_time
    }

    String getChannel() {
        return Channel
    }

    void setChannel(String channel) {
        Channel = channel
    }

    String getExternalCode() {
        return externalCode
    }

    void setExternalCode(String externalCode) {
        this.externalCode = externalCode
    }

    String getExternalTxnNumber() {
        return externalTxnNumber
    }

    void setExternalTxnNumber(String externalTxnNumber) {
        this.externalTxnNumber = externalTxnNumber
    }

    String getRECEIVER_COMMISSION_VALUE() {
        return RECEIVER_COMMISSION_VALUE
    }

    void setRECEIVER_COMMISSION_VALUE(String RECEIVER_COMMISSION_VALUE) {
        this.RECEIVER_COMMISSION_VALUE = RECEIVER_COMMISSION_VALUE
    }

    String getRECEIVER_PAYABLE_AMOUNT() {
        return RECEIVER_PAYABLE_AMOUNT
    }

    void setRECEIVER_PAYABLE_AMOUNT(String RECEIVER_PAYABLE_AMOUNT) {
        this.RECEIVER_PAYABLE_AMOUNT = RECEIVER_PAYABLE_AMOUNT
    }

    String getInstrumentType() {
        return instrumentType
    }

    void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType
    }

    Date getPaymentInstrumentDate() {
        return paymentInstrumentDate
    }

    void setPaymentInstrumentDate(Date paymentInstrumentDate) {
        this.paymentInstrumentDate = paymentInstrumentDate
    }

    String getPaymentInstrumentNumber() {
        return paymentInstrumentNumber
    }

    void setPaymentInstrumentNumber(String paymentInstrumentNumber) {
        this.paymentInstrumentNumber = paymentInstrumentNumber
    }

    Date getExtTxnDate() {
        return extTxnDate
    }

    void setExtTxnDate(Date extTxnDate) {
        this.extTxnDate = extTxnDate
    }

    String getSenderResellerName() {
        return senderResellerName
    }

    void setSenderResellerName(String senderResellerName) {
        this.senderResellerName = senderResellerName
    }

    String getReceiverResellerName() {
        return receiverResellerName
    }

    void setReceiverResellerName(String receiverResellerName) {
        this.receiverResellerName = receiverResellerName
    }
}
