package com.seamless.customer.bi.aggregator.aggregate

import com.seamless.customer.bi.aggregator.model.ReportIndex
import com.seamless.customer.bi.aggregator.util.DateUtil
import groovy.util.logging.Log4j
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.action.search.SearchScrollRequest
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.core.TimeValue
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.rest.RestStatus
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.transaction.annotation.Transactional
import org.elasticsearch.search.aggregations.AggregationBuilders



/**
 *
 *
 *
 *
 */
@Log4j
//@DynamicMixin
 public class C2SDailyAggregator extends AbstractAggregator {
    static final def TABLE = "C2S_Daily_Cumulative_balance"

    @Autowired
    RestHighLevelClient client

    @Autowired
    protected JdbcTemplate jdbcTemplate

    @Value('${C2SDailyAggregator.bulkInsertionMode:false}')
    boolean bulkInsertionMode

    @Value('${C2SDailyAggregator.bulkInsertionModeFromDateString:2020-08-03}')
    String bulkInsertionModeFromDateString

    @Value('${C2SDailyAggregator.bulkInsertionModeToDateString:2020-08-09}')
    String bulkInsertionModeToDateString



    @Value('${DateUtil.timeOffset:+5h+30m}')
    String timeOffset

    @Value('${C2SDailyAggregator.scrollSize:1000}')
    int scrollSize

    @Value('${C2SDailyAggregator.aggregateDays:7}')
        int aggregateDays

    @Transactional
    @Scheduled(cron = '${C2SDailyAggregator.cron:*/3 * * * * ?}')
    public void aggregate() {

        log.info("********** C2SDailyAggregator Aggregator started at " + new Date())
        if (bulkInsertionMode) {

            log.info("bulkInsertionModeFromDateString: " + bulkInsertionModeFromDateString)
            log.info("bulkInsertionModeToDateString: " + bulkInsertionModeToDateString)

            List<String> indices = DateUtil.getIndexList(bulkInsertionModeFromDateString, bulkInsertionModeToDateString)
            //need to change

            for (String index : indices) {
                //fetch data from ES
                try {
                    List<C2SDailyAggregatorModel> transactionSummaryModels = aggregateDataES(index,
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
                List<C2SDailyAggregatorModel> transactionSummaryModels = aggregateDataES(index.getIndexName(), index
                        .getStartDate(), index.getEndDate())
                insertAggregation(transactionSummaryModels)
            }
        }
        log.info("********** C2SDailyAggregator Aggregator ended at " + new Date())
    }


    private List<C2SDailyAggregatorModel> aggregateDataES(String index, String fromDate, String toDate) {
        SearchRequest searchRequest = new SearchRequest(index)
        SearchSourceBuilder searchSourceBuilder = fetchInput(fromDate, toDate)
        searchRequest.source(searchSourceBuilder)
        searchRequest.scroll(TimeValue.timeValueMinutes(5))
        SearchResponse searchResponse = generateSearchResponse(searchRequest)
        List<C2SDailyAggregatorModel> transactionSummaryModels = generateResponse(searchResponse)
        String scrollId =  searchResponse.getScrollId()
        log.info("hits size outside loop for the first time:::"+searchResponse.getHits().size())
        while(searchResponse.getHits().size()!=0) {
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId)
            scrollRequest.scroll(TimeValue.timeValueMinutes(5))
            searchResponse = generateScrollSearchResponse(scrollRequest)
            log.info("_________________hits size inside loop _____________________"+searchResponse.getHits().size())
            if(searchResponse.getHits().size()!=0){
            transactionSummaryModels.addAll(generateResponse(searchResponse))
            scrollId = searchResponse.getScrollId()
            }
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
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termsQuery("resultCode", 0))
                .filter(QueryBuilders.wildcardQuery("TRANSACTION_PROFILE", "C2S*"))

        if (!bulkInsertionMode) {
            queryBuilder = queryBuilder.filter(QueryBuilders.rangeQuery("timestamp").gte("now" + timeOffset + "-"+aggregateDays+"d/d").lt("now/d")
                    .includeLower(true).includeUpper(true))
        }
        searchSourceBuilder.query(queryBuilder).size(scrollSize).aggregation(AggregationBuilders.sum("totalAmount").field("transactionAmount"))
        return searchSourceBuilder
    }

    private List<C2SDailyAggregatorModel> generateResponse(SearchResponse searchResponse){
        List<C2SDailyAggregatorModel> c2SDailyAggregatorModelList = new ArrayList<>()

        if (searchResponse == null) {
            log.info("******* Null response received ")
        } else {
            RestStatus status = searchResponse.status()
            log.debug("response status -------------" + status)
             if (status == RestStatus.OK) {
                C2SDailyAggregatorModel c2SDailyAggregatorModel= new C2SDailyAggregatorModel((Double.valueOf(searchResponse.getAggregations().get("totalAmount").value())/aggregateDays));
                c2SDailyAggregatorModelList.add(c2SDailyAggregatorModel);
            }
        }
        return c2SDailyAggregatorModelList
    }

    private def insertAggregation(List c2SDailyAggregatorModelList) {

        log.info("C2SDailyAggregator Aggregated into ${c2SDailyAggregatorModelList.size()} rows.")
        if (c2SDailyAggregatorModelList.size() != 0) {
            def sql = "INSERT INTO ${TABLE} (aggregate_amount,aggregate_date" +
                    ") VALUES (?,?) ON DUPLICATE KEY UPDATE aggregate_date=VALUES(aggregate_date) , aggregate_amount=VALUES(aggregate_amount)"
            log.debug(sql)
            def batchUpdate = jdbcTemplate.batchUpdate(sql, [
                    setValues   : { ps, i ->
                        def row = c2SDailyAggregatorModelList[i]
                        def index = 0

                        ps.setBigDecimal(++index, row.transactionAmount)
                        ps.setDate(++index,  new java.sql.Date(row.transaction_time.getTime()))
                     },
                    getBatchSize: { c2SDailyAggregatorModelList.size() }
            ] as BatchPreparedStatementSetter)
        }

    }

}


class C2SDailyAggregatorModel {
    private Double transactionAmount
    private Date transaction_time

    C2SDailyAggregatorModel(Double transactionAmount) {
        this.transactionAmount = transactionAmount
        this.transaction_time = new Date();
    }
}
