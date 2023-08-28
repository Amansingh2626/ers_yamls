package com.seamless.customer.bi.aggregator.aggregate

import com.seamless.customer.bi.aggregator.model.ReportIndex
import com.seamless.customer.bi.aggregator.util.DateUtil
import com.seamless.customer.bi.aggregator.util.DateFormatter
import groovy.util.logging.Log4j
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.action.search.SearchScrollRequest
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.unit.TimeValue
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.rest.RestStatus
import org.elasticsearch.search.SearchHit
import org.elasticsearch.search.SearchHits
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.transaction.annotation.Transactional

/**
 *
 *
 *
 *
 */
@Log4j
//@DynamicMixin
public class TransactionMaintainAggregator extends AbstractAggregator {
    static final def TABLE = "transaction_reconciliations"

    @Autowired
    RestHighLevelClient client;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Value('${TransactionMaintainAggregator.bulkInsertionMode:false}')
    boolean bulkInsertionMode;

    @Value('${TransactionMaintainAggregator.bulkInsertionModeFromDateString:2020-08-03}')
    String bulkInsertionModeFromDateString;

    @Value('${TransactionMaintainAggregator.bulkInsertionModeToDateString:2020-08-09}')
    String bulkInsertionModeToDateString;

    @Value('${TransactionMaintainAggregator.eventName:RAISE_ORDER}')
    String eventName

    @Value('${DateUtil.timeOffset:+5h+30m}')
    String timeOffset;

    @Value('${TransactionMaintainAggregator.scrollSize:1000}')
    int scrollSize;

    @Transactional
    @Scheduled(cron = '${TransactionMaintainAggregator.cron:*/3 * * * * ?}')
    public void aggregate() {

        log.info("********** TransactionMaintainAggregator Aggregator started at " + new Date());
        if (bulkInsertionMode) {

            log.info("bulkInsertionModeFromDateString: " + bulkInsertionModeFromDateString);
            log.info("bulkInsertionModeToDateString: " + bulkInsertionModeToDateString);

            List<String> indices = DateUtil.getIndexList(bulkInsertionModeFromDateString, bulkInsertionModeToDateString);
            //need to change

            for (String index : indices) {
                //fetch data from ES
                try {
                    List<TransactionMaintainAggregatorModel> transactionSummaryModels = aggregateDataES(index,
                            bulkInsertionModeFromDateString, bulkInsertionModeToDateString)
                    insertAggregation(transactionSummaryModels);
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
                catch (Exception e) {
                    log.error(e.getMessage())
                }
            }

        } else {
            List<ReportIndex> indices = DateUtil.getIndex();

            for (ReportIndex index : indices) {

                log.info(index.toString())
                //fetch data from ES
                List<TransactionMaintainAggregatorModel> transactionSummaryModels = aggregateDataES(index.getIndexName(), index
                        .getStartDate(), index.getEndDate());
                insertAggregation(transactionSummaryModels);
            }
        }
        log.info("********** TransactionMaintainAggregator Aggregator ended at " + new Date());
    }


    private List<TransactionMaintainAggregatorModel> aggregateDataES(String index, String fromDate, String toDate) {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = fetchInput(fromDate, toDate);
        searchRequest.source(searchSourceBuilder);
        searchRequest.scroll(TimeValue.timeValueMinutes(5));
        SearchResponse searchResponse = generateSearchResponse(searchRequest);
        List<TransactionMaintainAggregatorModel> transactionSummaryModels = generateResponse(searchResponse);
        String scrollId =  searchResponse.getScrollId();
        log.info("hits size outside loop for the first time:::"+searchResponse.getHits().size())
        while(searchResponse.getHits().size()!=0) {
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(TimeValue.timeValueMinutes(5));
            searchResponse = generateScrollSearchResponse(scrollRequest);
            log.info("_________________hits size inside loop _____________________"+searchResponse.getHits().size())
            transactionSummaryModels.addAll(generateResponse(searchResponse));
            scrollId = searchResponse.getScrollId();
        }
        return transactionSummaryModels;
    }

    private SearchResponse generateSearchResponse(SearchRequest searchRequest) {
        SearchResponse searchResponse = null;
        log.info("*******Request:::: " + searchRequest.toString());
        try {
            searchResponse = client.search(searchRequest, COMMON_OPTIONS);
        } catch (Exception e) {
            log.error("Error mapping rule " + searchRequest + "\nError message : " + e);
        }
        return searchResponse;
    }

    private SearchResponse generateScrollSearchResponse(SearchScrollRequest scrollRequest) {
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.scroll(scrollRequest, COMMON_OPTIONS);
        } catch (Exception e) {
            log.error("Error mapping rule " + scrollRequest + "\nError message : " + e);
        }
        return searchResponse;
    }
    private SearchSourceBuilder fetchInput(String fromDate, String toDate) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termsQuery("NativeResultCode.keyword", "8011"))
                .filter(QueryBuilders.termsQuery("NativeResultDescription.keyword", "LINK_TIMEOUT"))
                .filter(QueryBuilders.termsQuery("LinkType.keyword", "SLC"))
                .filter(QueryBuilders.termsQuery("resultCode", 0));
        if (!bulkInsertionMode) {
            queryBuilder = queryBuilder.filter(QueryBuilders.rangeQuery("timestamp").gte("now" + timeOffset + "-3h/d").lt("now" + timeOffset + "+1h/d")
                    .includeLower(true).includeUpper(true))
        }
        searchSourceBuilder.query(queryBuilder).size(scrollSize);
        return searchSourceBuilder;
    }

    private List<TransactionMaintainAggregatorModel> generateResponse(SearchResponse searchResponse){
        List<TransactionMaintainAggregatorModel> transactionMaintainAggregatorModelList = new ArrayList<>();

        if (searchResponse == null) {
            log.info("******* Null response received ")
        } else {
            RestStatus status = searchResponse.status();
            log.debug("response status -------------" + status);
            HashMap<String, TransactionMaintainAggregatorModel> transactionMaintainAggregatorModelMap = new HashMap<>();

            if (status == RestStatus.OK) {
                SearchHits searchHits = searchResponse.getHits();

                for (SearchHit searchHit : searchHits.getHits()) {
                    Map<String, Object> searchHitMap = searchHit.getSourceAsMap();
                     String system =searchHitMap.get("LinkNodeId")
                    if(system=='CCPS' || system=='SKITTO'){

                    String reference_number =searchHitMap.get("ersReference")
                    String sender_id =searchHitMap.get("senderResellerId")
                    String sender_msisdn =searchHitMap.get("senderMSISDN")
                    String receiver_msisdn =searchHitMap.get("RECEIVER_MSISDN")
                    Double transaction_amount =Double.valueOf(searchHitMap.get("transactionAmount"))
                    String currency =searchHitMap.get("currency")
                    String reconciliation_status ="RECON_NEEDED"
                    Date transaction_time =DateFormatter.formatDate(searchHitMap.get("StartTime"))
                    Date create_date =DateFormatter.formatDate(searchHitMap.get("StartTime"))
                    String data =""
                    String reconciliation_description =""
                    TransactionMaintainAggregatorModel transactionMaintainAggregatorModel = new TransactionMaintainAggregatorModel(reference_number,sender_id,sender_msisdn,receiver_msisdn,transaction_amount,currency,system,reconciliation_status,transaction_time,create_date,data,reconciliation_description)
                    transactionMaintainAggregatorModelMap.put(reference_number, transactionMaintainAggregatorModel);
                 }
                }
            }
            transactionMaintainAggregatorModelMap.each {
                entry -> transactionMaintainAggregatorModelList.add(entry.value)
            }
        }
        return transactionMaintainAggregatorModelList;
    }

    private def insertAggregation(List transactionMaintainAggregatorModelList) {

        log.info("TransactionMaintainAggregator Aggregated into ${transactionMaintainAggregatorModelList.size()} rows.")
        if (transactionMaintainAggregatorModelList.size() != 0) {
            def sql = "INSERT INTO ${TABLE} (reference_number,sender_id,sender_msisdn,receiver_msisdn,transaction_amount,currency,system,reconciliation_status,transaction_time,create_date,data,reconcilliation_description" +
                    ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE reference_number=reference_number";
            log.debug(sql)
            def batchUpdate = jdbcTemplate.batchUpdate(sql, [
                    setValues   : { ps, i ->
                        def row = transactionMaintainAggregatorModelList[i]
                        def index = 0
                        ps.setString(++index, row.reference_number)
                        ps.setString(++index, row.sender_id)
                        ps.setString(++index, row.sender_msisdn)
                        ps.setString(++index, row.receiver_msisdn)
                        ps.setBigDecimal(++index, row.transaction_amount)
                        ps.setString(++index, row.currency)
                        ps.setString(++index, row.system)
                        ps.setString(++index, row.reconciliation_status)
                        ps.setDate(++index, new java.sql.Date(row.transaction_time.getTime()))
                        ps.setDate(++index, new java.sql.Date(row.create_date.getTime()))
                        ps.setString(++index, row.data)
                        ps.setString(++index, row.reconciliation_description)
                     },
                    getBatchSize: { transactionMaintainAggregatorModelList.size() }
            ] as BatchPreparedStatementSetter)
        }

    }

}


class TransactionMaintainAggregatorModel {

    private String reference_number;
    private String sender_id;
    private String sender_msisdn;
    private String receiver_msisdn;
    private Double transaction_amount;
    private String currency;
    private String system;
    private String reconciliation_status;
    private Date transaction_time;
    private Date create_date;
    private String data;
    private String reconciliation_description;

    TransactionMaintainAggregatorModel( String reference_number, String sender_id, String sender_msisdn, String receiver_msisdn, Double transaction_amount, String currency, String system, String reconciliation_status, Date transaction_time, Date create_date, String data,String reconciliation_description) {

        this.reference_number = reference_number
        this.sender_id = sender_id
        this.sender_msisdn = sender_msisdn
        this.receiver_msisdn = receiver_msisdn
        this.transaction_amount = transaction_amount
        this.currency = currency
        this.system = system
        this.reconciliation_status = reconciliation_status
        this.reconciliation_description = reconciliation_description
        this.transaction_time = transaction_time
        this.create_date = create_date
        this.data = data
    }


}
