package com.seamless.icms.aggregator.aggregate

import com.seamless.icms.aggregator.model.ReportIndex
import com.seamless.icms.aggregator.util.DateFormatter
import com.seamless.icms.aggregator.util.DateUtil
import groovy.util.logging.Log4j
import org.elasticsearch.action.search.ClearScrollRequest
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.action.search.SearchScrollRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.core.TimeValue
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.rest.RestStatus
import org.elasticsearch.search.SearchHit
import org.elasticsearch.search.SearchHits
import org.elasticsearch.search.aggregations.Aggregation
import org.elasticsearch.search.aggregations.AggregationBuilders
import org.elasticsearch.search.aggregations.Aggregations
import org.elasticsearch.search.aggregations.ParsedMultiBucketAggregation
import org.elasticsearch.search.aggregations.bucket.composite.*
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval
import org.elasticsearch.search.aggregations.metrics.ParsedAvg
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.elasticsearch.search.sort.SortOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.transaction.annotation.Transactional

import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.IsoFields
import java.time.temporal.TemporalAdjusters

/**
 *
 *
 *
 *
 */
@Log4j
//@DynamicMixin
public class ResellerIdTypeWiseDay extends AbstractAggregator {
    static final def TABLE = "reseller_id_type_wise_day"
    @Autowired
    RestHighLevelClient client;

    @Autowired
    protected JdbcTemplate jdbcTemplate;


    @Value('${ResellerIdTypeWiseDay.bulkInsertionMode:false}')
    boolean bulkInsertionMode;

    @Value('${ResellerIdTypeWiseDay.scrollSize:3000}')
    int scrollSize;

    @Value('${ResellerIdTypeWiseDay.productSkus:O2c}')
    String profileId;

    @Value('${ResellerIdTypeWiseDay.bulkInsertionModeFromDateString:2020-08}')
    String bulkInsertionModeFromDateString;

    @Value('${ResellerIdTypeWiseDay.bulkInsertionModeToDateString:2020-08}')
    String bulkInsertionModeToDateString;

    @Transactional
    @Scheduled(cron = '${ResellerIdTypeWiseDay.cron:*/3 * * * * ?}')
    public void aggregate() {

        log.info(" ****************** ResellerIdTypeWiseDay started ******************");
        def profileIdList = profileId.split(",")

        if (bulkInsertionMode) {
            log.debug("Bulk insertion mode: true")
            log.info("bulkInsertionModeFromDateString: " + bulkInsertionModeFromDateString);
            log.info("bulkInsertionModeToDateString: " + bulkInsertionModeToDateString);


            List<String> indexList = DateUtil.getIndexList(bulkInsertionModeFromDateString, bulkInsertionModeToDateString);

            log.info("Index list based on FromDate and ToDate: "+ indexList);
            for(int i=0; i< indexList.size(); i++){
                try {
                    aggregateDataES(indexList[i],bulkInsertionModeFromDateString,bulkInsertionModeToDateString,profileIdList)
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                } catch (Exception e){
                    log.error("Exception occurred! could not aggregate! "+e.getMessage())}
            }
        } else {
            log.debug("Bulk insertion mode: false")
            List<ReportIndex> indices = DateUtil.getIndex();

            for(ReportIndex index : indices){
                try {
                    log.info("Index: "+ index.getIndexName())
                    aggregateDataES(index.getIndexName(), index.getStartDate(), index.getEndDate(),profileIdList)
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                } catch (Exception e){
                    log.error("Exception occurred! could not aggregate! "+e.getMessage())}
            }
        }

        log.info(" **************** ResellerIdTypeWiseDay ended ***************");
    }


    private void aggregateDataES(String index,  String fromDate, String toDate, String[] profileIdList) {
        SearchRequest searchRequest = new SearchRequest(index);

        SearchSourceBuilder searchSourceBuilder = fetchInput(fromDate, toDate, profileIdList);
        searchRequest.source(searchSourceBuilder);
        searchRequest.scroll(TimeValue.timeValueMinutes(5));
        SearchResponse searchResponse = null;
        try {
            log.info("Calling Elasticsearch.. index: "+ index);
            log.info("::::::First Request:::: " + searchRequest.toString());
            searchResponse = client.search(searchRequest, COMMON_OPTIONS);
        } catch (Exception e) {
            log.error("Exception occurred while calling Elasticsearch! " + e.getMessage());
        }
        String scrollId= generateResponse(searchResponse);
        log.info("_____hits size outside loop first time____"+searchResponse.getHits().size())

        while(searchResponse.getHits().size()!=0) {
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(TimeValue.timeValueSeconds(30));
            log.info("::::::Scroll Request:::: " + scrollRequest.toString());
            try {
                searchResponse = client.scroll(scrollRequest, COMMON_OPTIONS);
            } catch (Exception e) {
                log.error("Exception occurred while calling Elasticsearch! " + e.getMessage());
            }
            log.info("_____hits size inside loop____"+searchResponse.getHits().size())
            scrollId = generateResponse(searchResponse);
        }
        if(scrollId!=null){
            ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
            clearScrollRequest.addScrollId(scrollId);
            client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        }
    }

    private SearchSourceBuilder fetchInput(String fromDate, String toDate, String[] profileIdList) {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if(bulkInsertionMode) {
            QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                    .filter(QueryBuilders.termsQuery("productSKU.keyword", profileIdList))
                    .filter(QueryBuilders.termsQuery("resultCode","0"))
            searchSourceBuilder.size(scrollSize).query(queryBuilder);
        }
        else {
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                    .filter(QueryBuilders.termsQuery("productSKU.keyword", profileIdList))
                    .filter(QueryBuilders.termsQuery("resultCode","0"))
                    .filter(QueryBuilders.rangeQuery("timestamp").gte(fromDate).lt(toDate))
            searchSourceBuilder.size(scrollSize).query(queryBuilder);
        }

        return searchSourceBuilder;
    }

    private  String generateResponse(SearchResponse searchResponse) {
        List<ResellerIdTypeWiseDayModel> resellerIdTypeWiseDayModelList = new ArrayList<>();


        if (searchResponse.status() != null && searchResponse.status() == RestStatus.OK) {

            SearchHits searchHits = searchResponse.getHits();

            for (SearchHit searchHit : searchHits.getHits()) {
                Map<String, String> searchHitMap = searchHit.getSourceAsMap();
                String id = searchHit.getId();
                Date dateTimeDay = DateFormatter.formatDate(searchHitMap.get("timestamp"));

                SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd")

                String day= simpleDateFormat.format(dateTimeDay);


                ResellerIdTypeWiseDayModel resellerIdTypeWiseDayModel = new ResellerIdTypeWiseDayModel(
                        id,
                        searchHitMap.getOrDefault("receiverResellerId","N/A"),
                        searchHitMap.getOrDefault("ReceiverResellerType","N/A"),
                        dateTimeDay,
                        searchHitMap.getOrDefault("transactionAmount", "N/A") as double,
                        searchHitMap.getOrDefault("TRANSACTION_PROFILE","N/A"),
                        searchHitMap.getOrDefault("ReceiverAccountType", "N/A"),
                        searchHitMap.getOrDefault("receiverMSISDN","N/A"),
                        searchHitMap.getOrDefault("Channel","N/A"),
                        searchHitMap.getOrDefault("resultStatus","N/A"),
                        searchHitMap.getOrDefault("currency","N/A"),
                        day
                )

                resellerIdTypeWiseDayModelList.add(resellerIdTypeWiseDayModel);

            }
            insertAggregation(resellerIdTypeWiseDayModelList);
            return searchResponse.getScrollId();

        } else {
            log.error("Error occurred. Response from Elasticsearch not 'OK'. Could not aggregate")
            return null;
        }
    }


    private def insertAggregation(List resellerIdTypeWiseDayModelList) {
        if (resellerIdTypeWiseDayModelList.size() != 0) {
            log.info("Inserting ${resellerIdTypeWiseDayModelList.size()} rows into table "+ TABLE);
            def sql = "INSERT INTO ${TABLE}  (`id`, `reseller_id`,`reseller_type`,`date_timestamp`, `amount`, `transaction_type`, `wallet_type`," +
                    "`reseller_msisdn`,`channel`, `result_status`,`currency`,`date`) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE id=VALUES(id)";
            def batchUpdate = jdbcTemplate.batchUpdate(sql, [
                    setValues   : { ps, i ->
                        def row = resellerIdTypeWiseDayModelList[i]
                        def index = 0
                        ps.setString(++index, row.id)
                        ps.setString(++index, row.resellerId)
                        ps.setString(++index, row.resellerType)
                        ps.setTimestamp(++index, new Timestamp(row.dateTimestamp.getTime()))
                        ps.setDouble(++index,row.amount)
                        ps.setString(++index, row.transactionType)
                        ps.setString(++index, row.walletType)
                        ps.setString(++index, row.resellerMsisdn)
                        ps.setString(++index, row.channel)
                        ps.setString(++index, row.resultStatus)
                        ps.setString(++index, row.currency)
                        ps.setString(++index, row.date)

                    },
                    getBatchSize: { resellerIdTypeWiseDayModelList.size() }
            ] as BatchPreparedStatementSetter)
        }
        else {
            log.info("List size 0. Skipping inserting into table")
        }
    }

}
class ResellerIdTypeWiseDayModel {
    private String id;
    private String resellerId;
    private String resellerType;
    private Date dateTimestamp;
    private double amount;
    private String transactionType;
    private String walletType;
    private String resellerMsisdn;
    private String channel;
    private String resultStatus;
    private String currency;
    private String date;

    ResellerIdTypeWiseDayModel(String id, String resellerId, String resellerType, Date dateTimestamp, double amount, String transactionType, String walletType, String resellerMsisdn, String channel, String resultStatus, String currency, String date) {
        this.id = id
        this.resellerId = resellerId
        this.resellerType = resellerType
        this.dateTimestamp = dateTimestamp
        this.amount = amount
        this.transactionType = transactionType
        this.walletType = walletType
        this.resellerMsisdn = resellerMsisdn
        this.channel = channel
        this.resultStatus = resultStatus
        this.currency = currency
        this.date = date
    }

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    String getResellerId() {
        return resellerId
    }

    void setResellerId(String resellerId) {
        this.resellerId = resellerId
    }

    String getResellerType() {
        return resellerType
    }

    void setResellerType(String resellerType) {
        this.resellerType = resellerType
    }

    Date getDateTimestamp() {
        return dateTimestamp
    }

    void setDateTimestamp(Date dateTimestamp) {
        this.dateTimestamp = dateTimestamp
    }

    double getAmount() {
        return amount
    }

    void setAmount(double amount) {
        this.amount = amount
    }

    String getTransactionType() {
        return transactionType
    }

    void setTransactionType(String transactionType) {
        this.transactionType = transactionType
    }

    String getWalletType() {
        return walletType
    }

    void setWalletType(String walletType) {
        this.walletType = walletType
    }

    String getResellerMsisdn() {
        return resellerMsisdn
    }

    void setResellerMsisdn(String resellerMsisdn) {
        this.resellerMsisdn = resellerMsisdn
    }

    String getChannel() {
        return channel
    }

    void setChannel(String channel) {
        this.channel = channel
    }

    String getResultStatus() {
        return resultStatus
    }

    void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus
    }

    String getCurrency() {
        return currency
    }

    void setCurrency(String currency) {
        this.currency = currency
    }

    String getDate() {
        return date
    }

    void setDate(String date) {
        this.date = date
    }
}
