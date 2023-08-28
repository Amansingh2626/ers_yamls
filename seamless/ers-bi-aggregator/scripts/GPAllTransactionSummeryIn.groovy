package com.seamless.customer.bi.aggregator.aggregate

import com.seamless.customer.bi.aggregator.model.ReportIndex
import com.seamless.customer.bi.aggregator.util.DateUtil
import com.seamless.customer.bi.aggregator.util.GenerateHash
import groovy.util.logging.Slf4j
import org.apache.commons.lang.exception.ExceptionUtils
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.rest.RestStatus
import org.elasticsearch.search.aggregations.*
import org.elasticsearch.search.aggregations.bucket.composite.CompositeAggregationBuilder
import org.elasticsearch.search.aggregations.bucket.composite.CompositeValuesSourceBuilder
import org.elasticsearch.search.aggregations.bucket.composite.ParsedComposite
import org.elasticsearch.search.aggregations.bucket.composite.TermsValuesSourceBuilder
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms
import org.elasticsearch.search.aggregations.metrics.ParsedSum
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.transaction.annotation.Transactional
import java.util.Calendar
import java.util.Date

import java.text.SimpleDateFormat

/**
 *
 *
 *
 *
 */
@Slf4j
//@DynamicMixin
public class GPAllTransactionSummeryIn extends AbstractAggregator {
    static final def TABLE = "gp_all_transaction_summary"

    public static final String SENDER_RESELLER = "senderResellerId"
    public static final String RECEIVER_RESELLER = "receiverResellerId"

    static final def QUERY = "select t2.accountId as 'accountId', t2.balanceAfter as 'balance'  from transactions t2 inner join ( select max(t1.transactionKey) as transactionKey , t1.accountId from transactions t1  where t1.createDate between DATE_SUB(CONCAT(CURRENT_DATE(),' 00:00:00') , INTERVAL 1 DAY) AND DATE_SUB(CONCAT(CURRENT_DATE(),' 23:59:59') , INTERVAL 1 DAY)  group by t1.accountId) temp on  temp.transactionKey = t2.transactionKey and temp.accountId=t2.accountId";

    public static final String CLOSING_BALANCE_BULK_MODE_QUERY = "select t.accountId as 'accountId',  t.balanceAfter as 'balanceAfter' from transactions t where t.createDate between ? AND ?  and accountTypeId='RESELLER' and accountId=? order by createDate desc limit 1"

    public static final String BALANCE_FROM_AGG = "select balance from reseller_balance_aggregator where account_id=? and account_type_id='Reseller'"

    @Autowired
    RestHighLevelClient client
    //private static final string OPERATORNAME = "operator"
    @Autowired
    protected JdbcTemplate jdbcTemplate

     @Autowired
     @Qualifier(value="accounts")
     protected JdbcTemplate accountJdbcTemplate;

    @Value('${GPAllTransactionSummeryIn.bulkInsertionMode:false}')
    boolean bulkInsertionMode

    @Value('${GPAllTransactionSummeryIn.bulkInsertionModeFromDateString:2020-08-03}')
    String bulkInsertionModeFromDateString

    @Value('${GPAllTransactionSummeryIn.bulkInsertionModeToDateString:2020-08-09}')
    String bulkInsertionModeToDateString

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss")

    SimpleDateFormat dayFormatter = new SimpleDateFormat("yyyy-MM-dd")

    @Transactional
    @Scheduled(cron = '${GPAllTransactionSummeryIn.cron:*/3 * * * * ?}')
     void aggregate() {

        log.info("GPAllTransactionSummeryIn Aggregator started********************************************************************************" + new Date())
        Map<String, GPAllTransactionSummeryInModel> allTransactionSummeryInModelMap = new HashMap<>()
        if (bulkInsertionMode) {
            log.info("bulkInsertionModeFromDateString: " + bulkInsertionModeFromDateString)
            log.info("bulkInsertionModeToDateString: " + bulkInsertionModeToDateString)
            List<String> indices = DateUtil.getIndexList(bulkInsertionModeFromDateString, bulkInsertionModeToDateString)
            try {
                aggregateDataES(indices, bulkInsertionModeFromDateString, bulkInsertionModeToDateString, allTransactionSummeryInModelMap)
            } catch (InterruptedException e) {
                log.error(e.getMessage())
            }
            catch (Exception e) {
                log.error(e.getMessage())
            }
        } else {
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal  = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            String endDate=s.format(new Date(cal.getTimeInMillis()))
            cal.add(Calendar.DATE, -1)
            String startDate=s.format(new Date(cal.getTimeInMillis()))
            List<String> indices = DateUtil.getIndexList(startDate, endDate)
            aggregateDataES(indices, startDate, endDate, allTransactionSummeryInModelMap)
        }
        log.info("GPAllTransactionSummeryIn Aggregator ended************************************************************************************")
    }


    private void aggregateDataES(List<String> index, String fromDate, String toDate, Map<String, GPAllTransactionSummeryInModel> allTransactionSummeryInModelMap) {
        SearchRequest searchRequest = new SearchRequest(index.toArray(new String[0]))
        Map<String,Object> afterKey = null
        SearchSourceBuilder searchSourceBuilder = buildESAggByTermQuery(fromDate, toDate, afterKey, SENDER_RESELLER)
        searchRequest.source(searchSourceBuilder)
        afterKey = generateResponse(searchRequest, allTransactionSummeryInModelMap, SENDER_RESELLER)
        insertAggregation(new ArrayList(allTransactionSummeryInModelMap.values()))
        if (afterKey != null) {
            log.info(SENDER_RESELLER +" agg term ############## after key" + afterKey)
            while (afterKey != null) {
                searchSourceBuilder = buildESAggByTermQuery(fromDate, toDate, afterKey, SENDER_RESELLER)
                searchRequest.source(searchSourceBuilder)
                allTransactionSummeryInModelMap = new HashMap<>()
                afterKey = generateResponse(searchRequest, allTransactionSummeryInModelMap, SENDER_RESELLER)
                insertAggregation(new ArrayList(allTransactionSummeryInModelMap.values()))
                log.info(SENDER_RESELLER +" agg term ############## after key" + afterKey)
            }
        }
        afterKey = null;
        searchSourceBuilder = buildESAggByTermQuery(fromDate, toDate, afterKey, RECEIVER_RESELLER)
        searchRequest.source(searchSourceBuilder)
        allTransactionSummeryInModelMap = new HashMap<>()
        afterKey = generateResponse(searchRequest, allTransactionSummeryInModelMap, RECEIVER_RESELLER)
        insertAggregation(new ArrayList(allTransactionSummeryInModelMap.values()))
        if (afterKey != null) {
            log.info(RECEIVER_RESELLER+"  ##############" + afterKey)
            while (afterKey != null) {
                searchSourceBuilder = buildESAggByTermQuery(fromDate, toDate, afterKey, RECEIVER_RESELLER)
                searchRequest.source(searchSourceBuilder)
                allTransactionSummeryInModelMap = new HashMap<>()
                afterKey = generateResponse(searchRequest, allTransactionSummeryInModelMap, RECEIVER_RESELLER)
                insertAggregation(new ArrayList(allTransactionSummeryInModelMap.values()))
                log.info(RECEIVER_RESELLER+" agg term ##############" + afterKey)
            }
        }

    }

    private SearchSourceBuilder buildESAggByTermQuery(String fromDate, String toDate, Map<String,Object> afterKey, String aggTerm) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()

        AggregationBuilder aggregationBuilders = AggregationBuilders.dateHistogram("timestamp").field("timestamp").
                fixedInterval(DateHistogramInterval.days(1)).
                subAggregation(AggregationBuilders.terms("profile").
                        field("transactionProfile.keyword").size(100).
                                subAggregation(AggregationBuilders.sum("amount").field("transactionAmount")))

        List<CompositeValuesSourceBuilder<?>> sources = new ArrayList<>();
        sources.add(new TermsValuesSourceBuilder(aggTerm).field(aggTerm+".keyword"));
        CompositeAggregationBuilder compositeBuilder = new CompositeAggregationBuilder(aggTerm,
                sources).size(10000).subAggregation(aggregationBuilders);
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termsQuery("seamlessResultStatus.keyword", "0"))
                .filter(QueryBuilders.rangeQuery("timestamp").gte(fromDate).lt(toDate))
        searchSourceBuilder.query(queryBuilder);
        if(null != afterKey){
            compositeBuilder.aggregateAfter(afterKey)
        }
        searchSourceBuilder.aggregation(compositeBuilder).size(0)

        return searchSourceBuilder
    }

    private Map<String,Object> generateResponse(SearchRequest searchRequest, Map<String, GPAllTransactionSummeryInModel> allTransactionSummeryInModelMap,
                                                 String aggTerm) {
        SearchResponse searchResponse = null
        try {
            searchResponse = client.search(searchRequest, COMMON_OPTIONS)
        } catch (Exception e) {
            log.error("Error mapping rule " + searchRequest + "\nError message : " + e)
        }
        if(null != searchResponse) {
            log.info("*******Request:::: " + searchRequest)
            RestStatus status = searchResponse.status()
            log.info("response status -------------" + status)

            if (status == RestStatus.OK && null != searchResponse.getAggregations()) {
                Aggregations aggregations = searchResponse.getAggregations()
                ParsedComposite parsedComposite = aggregations.asMap().get(aggTerm);
                Map<String, Object> resellerClosingBalanceMap = new HashMap<>();
                if(!bulkInsertionMode) {
                    def resellerClosingBalance = accountJdbcTemplate.queryForList(QUERY);
                    ListIterator resellerClosingBalanceIterattor = resellerClosingBalance.listIterator();
                    while (resellerClosingBalanceIterattor.hasNext()) {
                        Map<String, Double> closingBalanceRecord = (Map<String, Object>) resellerClosingBalanceIterattor.next();
                        resellerClosingBalanceMap.put(closingBalanceRecord.get("accountId"), closingBalanceRecord.get("balance"));
                    }
                }
                def closingBalanceAgg = new Object[3];
                def aggBalanceAgg = new Object[1];
                for (ParsedMultiBucketAggregation.ParsedBucket bucket : parsedComposite.getBuckets()) {
                    //  LinkedHashMap<String, String> keyValuesMap = bucket.getKey()
                    String resellerId = ((Map) bucket.getKey()).get(aggTerm)
                    Map<String, Aggregation> aggregationMap = bucket.getAggregations().asMap()
                    ParsedDateHistogram parsedDateHistogram = aggregationMap.get("timestamp")
                    for (ParsedMultiBucketAggregation.ParsedBucket dateBucket : parsedDateHistogram.getBuckets()) {
                        String date = dateBucket.getKeyAsString()
                        if (dateBucket.getKeyAsString() != null) {
                            ParsedStringTerms parsedStringTerms_profile = dateBucket.getAggregations().asMap().get("profile")
                            for (ParsedMultiBucketAggregation.ParsedBucket bucket_profile : parsedStringTerms_profile.getBuckets()) {
                                String profile = bucket_profile.getKeyAsString()
                                if (profile != null) {
                                    Date parse = dateFormat.parse(date)
                                    Double closingBalance
                                    if(!bulkInsertionMode) {
                                        closingBalance = resellerClosingBalanceMap.get(resellerId);
                                    }else {
                                        closingBalanceAgg[0] = dayFormatter.format(parse) + " 00:00:00"
                                        closingBalanceAgg[1] = dayFormatter.format(parse) + " 23:59:59"
                                        closingBalanceAgg[2] = resellerId
                                        Map queryForMap;
                                        try {
                                            queryForMap = accountJdbcTemplate.queryForMap(CLOSING_BALANCE_BULK_MODE_QUERY, closingBalanceAgg);
                                        }catch (IncorrectResultSizeDataAccessException e) {
                                            log.warn(" no data found. balance will be fetched from bi aggregated table for reseller "+resellerId+ " date "+parse.toString() )
                                        }
                                        if(null!=queryForMap && queryForMap.containsKey("balanceAfter") && queryForMap.get("balanceAfter") !=null){
                                            closingBalance = queryForMap.get("balanceAfter")
                                        }else {
                                            aggBalanceAgg[0]=resellerId;
                                            Map balanceQueryForMap;
                                            try {
                                                balanceQueryForMap = jdbcTemplate.queryForMap(BALANCE_FROM_AGG,aggBalanceAgg)
                                            }catch (IncorrectResultSizeDataAccessException e) {
                                                log.warn(" no data found from aggerated table for reseller "+ resellerId +" date "+parse.toString())
                                            }
                                            if(null!=balanceQueryForMap && balanceQueryForMap.containsKey("balance")
                                                    && balanceQueryForMap.get("balance") !=null){
                                                closingBalance = balanceQueryForMap.get("balance")
                                            }else {
                                                closingBalance = 0d;
                                            }
                                        }
                                    }
                                    ParsedSum parsedSum = bucket_profile.getAggregations().getAsMap().get("amount")
                                    Double amount = parsedSum.getValue().doubleValue()
                                    Long count = bucket_profile.getDocCount().longValue()
                                    log.debug(resellerId + ":: " + date + " :: " + profile + " :: " + amount + " :: " + count + " ::: " + parse.getDateString()+":::"+closingBalance)
                                    if (allTransactionSummeryInModelMap.containsKey(resellerId + "_" + parse.getDateString())) {
                                        GPAllTransactionSummeryInModel allTransactionSummeryInModel = allTransactionSummeryInModelMap.get(resellerId + "_" + parse.getDateString())
                                        allTransactionSummeryInModel.setResellerId(resellerId)
                                        allTransactionSummeryInModel.setEndTimeDay(parse)
                                        populateTransactionCountAndAmount(allTransactionSummeryInModel,
                                                resellerId, parse, profile, amount, count, aggTerm,closingBalance)
                                        allTransactionSummeryInModelMap.put(resellerId + "_" + parse.getDateString(), allTransactionSummeryInModel)
                                    } else {
                                        GPAllTransactionSummeryInModel allTransactionSummeryInModel = new GPAllTransactionSummeryInModel()
                                        populateTransactionCountAndAmount(allTransactionSummeryInModel,
                                                resellerId, parse, profile, amount, count, aggTerm,closingBalance)
                                        allTransactionSummeryInModelMap.put(resellerId + "_" + parse.getDateString(), allTransactionSummeryInModel)
                                    }
                                }
                            }

                        }

                    }
                }
                return parsedComposite.afterKey();
            }
        }
        return null;
    }

    static def populateTransactionCountAndAmount(GPAllTransactionSummeryInModel allTransactionSummeryInModel,
                                                 String resellerId, Date parse, String profile, double amount, long count, String aggTerm,Double closingBalance) {
        allTransactionSummeryInModel.setNetBalance(closingBalance!=null ?  closingBalance : 0l)
        allTransactionSummeryInModel.setResellerId(resellerId)
        allTransactionSummeryInModel.setEndTimeDay(parse)
        allTransactionSummeryInModel.setId(GenerateHash.createHashString(resellerId, parse.getDateString()))
        if (profile.equalsIgnoreCase("O2C_WITHDRAW") && aggTerm.equals(SENDER_RESELLER)) {
            allTransactionSummeryInModel.setO2cOutRtWdAmount(allTransactionSummeryInModel.getO2cOutRtWdAmount() + amount)
            allTransactionSummeryInModel.setO2cOutRtWdCount(allTransactionSummeryInModel.getO2cOutRtWdCount() + count)
        } else if (profile.equalsIgnoreCase("TRANSFER")) {
            if(aggTerm.equals(SENDER_RESELLER)) {
                allTransactionSummeryInModel.setC2cOutTrfAmount(allTransactionSummeryInModel.getC2cOutTrfAmount() + amount)
                allTransactionSummeryInModel.setC2cOutTrfCount(allTransactionSummeryInModel.getC2cOutTrfCount() + count)
            }else {
                allTransactionSummeryInModel.setC2cInTrfAmount(allTransactionSummeryInModel.getC2cInTrfAmount() + amount)
                allTransactionSummeryInModel.setC2cInTrfCount(allTransactionSummeryInModel.getC2cInTrfCount() + count)
            }
        } else if (profile.equalsIgnoreCase("C2C_WITHDRAW") || profile.equalsIgnoreCase("RETURN")) {
            if (aggTerm.equals(SENDER_RESELLER)) {
                double rtWdOutAmount = allTransactionSummeryInModel.getC2cOutRtWdAmount() + amount
                long rtWdOutCount = allTransactionSummeryInModel.getC2cOutRtWdCount() + count
                allTransactionSummeryInModel.setC2cOutRtWdAmount(rtWdOutAmount)
                allTransactionSummeryInModel.setC2cOutRtWdCount(rtWdOutCount)
                if(profile.equalsIgnoreCase("C2C_WITHDRAW")){
                    allTransactionSummeryInModel.setC2cOutWdAmount(rtWdOutAmount)
                    allTransactionSummeryInModel.setC2cOutWdCount(rtWdOutCount)
                }else if(profile.equalsIgnoreCase("RETURN")){
                    allTransactionSummeryInModel.setC2cOutRtAmount(rtWdOutAmount)
                    allTransactionSummeryInModel.setC2cOutRtCount(rtWdOutCount)
                }
            } else {
                double rtWdInAmount = allTransactionSummeryInModel.getC2cInRtWdAmount() + amount
                long rtWdInCount = allTransactionSummeryInModel.getC2cInRtWdCount() + count
                allTransactionSummeryInModel.setC2cInRtWdAmount(rtWdInAmount)
                allTransactionSummeryInModel.setC2cInRtWdCount(rtWdInCount)
                if(profile.equalsIgnoreCase("C2C_WITHDRAW")){
                    allTransactionSummeryInModel.setC2cInWdAmount(rtWdInAmount)
                    allTransactionSummeryInModel.setC2cInWdCount(rtWdInCount)
                }else if(profile.equalsIgnoreCase("RETURN")){
                    allTransactionSummeryInModel.setC2cInRtAmount(rtWdInAmount)
                    allTransactionSummeryInModel.setC2cInRtCount(rtWdInCount)
                }
            }
        } else if (profile.startsWith("C2S") && aggTerm.equals(SENDER_RESELLER)) {
            allTransactionSummeryInModel.setC2sOutTrfAmount(allTransactionSummeryInModel.getC2sOutTrfAmount() + amount)
            allTransactionSummeryInModel.setC2sOutTrfCount(allTransactionSummeryInModel.getC2sOutTrfCount() + count)
        }
        else if (aggTerm.equals(RECEIVER_RESELLER) && (profile.equalsIgnoreCase("SALE") || profile.equalsIgnoreCase("FOC"))) {
            allTransactionSummeryInModel.setO2cInTrfAmount(allTransactionSummeryInModel.getO2cInTrfAmount() + amount)
            allTransactionSummeryInModel.setO2cInTrfCount(allTransactionSummeryInModel.getO2cInTrfCount() + count)
        } else {
            log.warn(" Transaction profile is not mapped " + profile + " possible data loss principal type "+aggTerm)
        }
    }

    private def insertAggregation(List totalTransactions) {
        if (totalTransactions.size() != 0) {
            def sql = "INSERT INTO ${TABLE} (id, aggregation_date, reseller_id, O2C_IN_Trf_count, O2C_IN_Trf_amount, C2C_IN_Trf_count, " +
                    "C2C_IN_Trf_amount, C2C_IN_Rt_Wd_count, C2C_IN_Rt_Wd_amount, O2C_OUT_Rt_Wd_count, O2C_OUT_Rt_Wd_amount, " +
                    "C2C_OUT_Trf_count, C2C_OUT_Trf_amount, C2C_OUT_Rt_Wd_count, C2C_OUT_Rt_Wd_amount, C2S_OUT_count, C2S_OUT_amount,net_balance,C2C_IN_Wd_count, " +
                    " C2C_IN_Wd_amount, C2C_IN_Rt_count, C2C_IN_Rt_amount, C2C_OUT_Rt_count, C2C_OUT_Rt_amount, C2C_OUT_Wd_count, C2C_OUT_Wd_amount)" +
                    " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) " +
                    "ON DUPLICATE KEY UPDATE O2C_IN_Trf_count = O2C_IN_Trf_count+ VALUES(O2C_IN_Trf_count), " +
                    "O2C_IN_Trf_amount = O2C_IN_Trf_amount+ VALUES(O2C_IN_Trf_amount), " +
                    "C2C_IN_Trf_count = C2C_IN_Trf_count+ VALUES(C2C_IN_Trf_count), " +
                    "C2C_IN_Trf_amount = C2C_IN_Trf_amount+ VALUES(C2C_IN_Trf_amount), " +
                    "C2C_IN_Rt_Wd_count =C2C_IN_Rt_Wd_count+ VALUES(C2C_IN_Rt_Wd_count), " +
                    "C2C_IN_Rt_Wd_amount = C2C_IN_Rt_Wd_amount+ VALUES(C2C_IN_Rt_Wd_amount), " +
                    "O2C_OUT_Rt_Wd_count = O2C_OUT_Rt_Wd_count+VALUES(O2C_OUT_Rt_Wd_count), " +
                    "O2C_OUT_Rt_Wd_amount = O2C_OUT_Rt_Wd_amount+VALUES(O2C_OUT_Rt_Wd_amount), " +
                    "C2C_OUT_Trf_count = C2C_OUT_Trf_count+VALUES(C2C_OUT_Trf_count), " +
                    "C2C_OUT_Trf_amount = C2C_OUT_Trf_amount+VALUES(C2C_OUT_Trf_amount), " +
                    "C2C_OUT_Rt_Wd_count =C2C_OUT_Rt_Wd_count+ VALUES(C2C_OUT_Rt_Wd_count), " +
                    "C2C_OUT_Rt_Wd_amount = C2C_OUT_Rt_Wd_amount+VALUES(C2C_OUT_Rt_Wd_amount), " +
                    "C2S_OUT_count = C2S_OUT_count+VALUES(C2S_OUT_count), " +
                    "C2S_OUT_amount = C2S_OUT_amount+VALUES(C2S_OUT_amount) ," +
                    "net_balance = VALUES(net_balance), "+
                    "C2C_IN_Rt_count =C2C_IN_Rt_count+ VALUES(C2C_IN_Rt_count), " +
                    "C2C_IN_Rt_amount = C2C_IN_Rt_amount+ VALUES(C2C_IN_Rt_amount), " +
                    "C2C_IN_Wd_count =C2C_IN_Wd_count+ VALUES(C2C_IN_Wd_count), " +
                    "C2C_IN_Wd_amount = C2C_IN_Wd_amount+ VALUES(C2C_IN_Wd_amount), " +
                    "C2C_OUT_Wd_count =C2C_OUT_Wd_count+ VALUES(C2C_OUT_Wd_count), " +
                    "C2C_OUT_Wd_amount = C2C_OUT_Wd_amount+VALUES(C2C_OUT_Wd_amount), " +
                    "C2C_OUT_Rt_count =C2C_OUT_Rt_count+ VALUES(C2C_OUT_Rt_count), " +
                    "C2C_OUT_Rt_amount = C2C_OUT_Rt_amount+VALUES(C2C_OUT_Rt_amount) "

            try {
                def batchUpdate = jdbcTemplate.batchUpdate(sql, [
                        setValues   : { ps, i ->
                            def row = totalTransactions[i]
                            def index = 0
                            ps.setString(++index, row.id)
                            ps.setDate(++index, new java.sql.Date(row.endTimeDay.getTime()))
                            ps.setString(++index, row.resellerId)
                            ps.setLong(++index, row.o2cInTrfCount)
                            ps.setDouble(++index, row.o2cInTrfAmount)
                            ps.setLong(++index, row.c2cInTrfCount)
                            ps.setDouble(++index, row.c2cInTrfAmount)
                            ps.setLong(++index, row.c2cInRtWdCount)
                            ps.setDouble(++index, row.c2cInRtWdAmount)
                            ps.setLong(++index, row.o2cOutRtWdCount)
                            ps.setDouble(++index, row.o2cOutRtWdAmount)
                            ps.setLong(++index, row.c2cOutTrfCount)
                            ps.setDouble(++index, row.c2cOutTrfAmount)
                            ps.setLong(++index, row.c2cOutRtWdCount)
                            ps.setDouble(++index, row.c2cOutRtWdAmount)
                            ps.setLong(++index, row.c2sOutTrfCount)
                            ps.setDouble(++index, row.c2sOutTrfAmount)
                            ps.setDouble(++index, row.netBalance)
                            ps.setLong(++index, row.c2cInWdCount)
                            ps.setDouble(++index, row.c2cInWdAmount)
                            ps.setLong(++index, row.c2cInRtCount)
                            ps.setDouble(++index, row.c2cInRtAmount)
                            ps.setLong(++index, row.c2cOutRtCount)
                            ps.setDouble(++index, row.c2cOutRtAmount)
                            ps.setLong(++index, row.c2cOutWdCount)
                            ps.setDouble(++index, row.c2cOutWdAmount)
                        },
                        getBatchSize: { totalTransactions.size() }
                ] as BatchPreparedStatementSetter)
            } catch (Exception e) {
                log.info(ExceptionUtils.getFullStackTrace(e))
            }
        }
    }
}
//

class GPAllTransactionSummeryInModel {
    private String id
    private Date endTimeDay
    private String resellerId
    private String resellerMSISDN
    private String resellerType
    private String region
    private String transactionProfile
    private long o2cInTrfCount
    private double o2cInTrfAmount
    private long c2cInTrfCount
    private double c2cInTrfAmount
    private long c2cInRtWdCount
    private double c2cInRtWdAmount

    private long c2cInRtCount
    private double c2cInRtAmount
    private long c2cInWdCount
    private double c2cInWdAmount

    private long c2cOutRtCount
    private double c2cOutRtAmount
    private long c2cOutWdCount
    private double c2cOutWdAmount

    private long o2cOutRtWdCount
    private double o2cOutRtWdAmount
    private long c2cOutTrfCount
    private double c2cOutTrfAmount
    private long c2cOutRtWdCount
    private double c2cOutRtWdAmount
    private long c2sOutTrfCount
    private double c2sOutTrfAmount
    private double netBalance


    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    Date getEndTimeDay() {
        return endTimeDay
    }

    void setEndTimeDay(Date endTimeDay) {
        this.endTimeDay = endTimeDay
    }

    String getResellerId() {
        return resellerId
    }

    void setResellerId(String resellerId) {
        this.resellerId = resellerId
    }

    String getResellerMSISDN() {
        return resellerMSISDN
    }

    void setResellerMSISDN(String resellerMSISDN) {
        this.resellerMSISDN = resellerMSISDN
    }

    String getResellerType() {
        return resellerType
    }

    void setResellerType(String resellerType) {
        this.resellerType = resellerType
    }

    String getRegion() {
        return region
    }

    void setRegion(String region) {
        this.region = region
    }

    String getTransactionProfile() {
        return transactionProfile
    }

    void setTransactionProfile(String transactionProfile) {
        this.transactionProfile = transactionProfile
    }

    long getO2cInTrfCount() {
        return o2cInTrfCount
    }

    void setO2cInTrfCount(long o2cInTrfCount) {
        this.o2cInTrfCount = o2cInTrfCount
    }

    double getO2cInTrfAmount() {
        return o2cInTrfAmount
    }

    void setO2cInTrfAmount(double o2cInTrfAmount) {
        this.o2cInTrfAmount = o2cInTrfAmount
    }

    long getC2cInTrfCount() {
        return c2cInTrfCount
    }

    void setC2cInTrfCount(long c2cInTrfCount) {
        this.c2cInTrfCount = c2cInTrfCount
    }

    double getC2cInTrfAmount() {
        return c2cInTrfAmount
    }

    void setC2cInTrfAmount(double c2cInTrfAmount) {
        this.c2cInTrfAmount = c2cInTrfAmount
    }

    long getC2cInRtWdCount() {
        return c2cInRtWdCount
    }

    void setC2cInRtWdCount(long c2cInRtWdCount) {
        this.c2cInRtWdCount = c2cInRtWdCount
    }

    double getC2cInRtWdAmount() {
        return c2cInRtWdAmount
    }

    void setC2cInRtWdAmount(double c2cInRtWdAmount) {
        this.c2cInRtWdAmount = c2cInRtWdAmount
    }

    long getO2cOutRtWdCount() {
        return o2cOutRtWdCount
    }

    void setO2cOutRtWdCount(long o2cOutRtWdCount) {
        this.o2cOutRtWdCount = o2cOutRtWdCount
    }

    double getO2cOutRtWdAmount() {
        return o2cOutRtWdAmount
    }

    void setO2cOutRtWdAmount(double o2cOutRtWdAmount) {
        this.o2cOutRtWdAmount = o2cOutRtWdAmount
    }

    long getC2cOutTrfCount() {
        return c2cOutTrfCount
    }

    void setC2cOutTrfCount(long c2cOutTrfCount) {
        this.c2cOutTrfCount = c2cOutTrfCount
    }

    double getC2cOutTrfAmount() {
        return c2cOutTrfAmount
    }

    void setC2cOutTrfAmount(double c2cOutTrfAmount) {
        this.c2cOutTrfAmount = c2cOutTrfAmount
    }

    long getC2cOutRtWdCount() {
        return c2cOutRtWdCount
    }

    void setC2cOutRtWdCount(long c2cOutRtWdCount) {
        this.c2cOutRtWdCount = c2cOutRtWdCount
    }

    double getC2cOutRtWdAmount() {
        return c2cOutRtWdAmount
    }

    void setC2cOutRtWdAmount(double c2cOutRtWdAmount) {
        this.c2cOutRtWdAmount = c2cOutRtWdAmount
    }

    long getC2sOutTrfCount() {
        return c2sOutTrfCount
    }

    void setC2sOutTrfCount(long c2sOutTrfCount) {
        this.c2sOutTrfCount = c2sOutTrfCount
    }

    double getC2sOutTrfAmount() {
        return c2sOutTrfAmount
    }

    void setC2sOutTrfAmount(double c2sOutTrfAmount) {
        this.c2sOutTrfAmount = c2sOutTrfAmount
    }

    double getNetBalance() {
        return netBalance
    }

    void setNetBalance(double netBalance) {
        this.netBalance = netBalance
    }

    long getC2cInRtCount() {
        return c2cInRtCount
    }

    void setC2cInRtCount(long c2cInRtCount) {
        this.c2cInRtCount = c2cInRtCount
    }

    double getC2cInRtAmount() {
        return c2cInRtAmount
    }

    void setC2cInRtAmount(double c2cInRtAmount) {
        this.c2cInRtAmount = c2cInRtAmount
    }

    long getC2cInWdCount() {
        return c2cInWdCount
    }

    void setC2cInWdCount(long c2cInWdCount) {
        this.c2cInWdCount = c2cInWdCount
    }

    double getC2cInWdAmount() {
        return c2cInWdAmount
    }

    void setC2cInWdAmount(double c2cInWdAmount) {
        this.c2cInWdAmount = c2cInWdAmount
    }

    long getC2cOutRtCount() {
        return c2cOutRtCount
    }

    void setC2cOutRtCount(long c2cOutRtCount) {
        this.c2cOutRtCount = c2cOutRtCount
    }

    double getC2cOutRtAmount() {
        return c2cOutRtAmount
    }

    void setC2cOutRtAmount(double c2cOutRtAmount) {
        this.c2cOutRtAmount = c2cOutRtAmount
    }

    long getC2cOutWdCount() {
        return c2cOutWdCount
    }

    void setC2cOutWdCount(long c2cOutWdCount) {
        this.c2cOutWdCount = c2cOutWdCount
    }

    double getC2cOutWdAmount() {
        return c2cOutWdAmount
    }

    void setC2cOutWdAmount(double c2cOutWdAmount) {
        this.c2cOutWdAmount = c2cOutWdAmount
    }


    @Override
    public String toString() {
        return "GPAllTransactionSummeryInModel{" +
                "id='" + id + '\'' +
                ", endTimeDay=" + endTimeDay +
                ", resellerId='" + resellerId + '\'' +
                ", resellerMSISDN='" + resellerMSISDN + '\'' +
                ", resellerType='" + resellerType + '\'' +
                ", region='" + region + '\'' +
                ", transactionProfile='" + transactionProfile + '\'' +
                ", o2cInTrfCount=" + o2cInTrfCount +
                ", o2cInTrfAmount=" + o2cInTrfAmount +
                ", c2cInTrfCount=" + c2cInTrfCount +
                ", c2cInTrfAmount=" + c2cInTrfAmount +
                ", c2cInRtWdCount=" + c2cInRtWdCount +
                ", c2cInRtWdAmount=" + c2cInRtWdAmount +
                ", c2cInRtCount=" + c2cInRtCount +
                ", c2cInRtAmount=" + c2cInRtAmount +
                ", c2cInWdCount=" + c2cInWdCount +
                ", c2cInWdAmount=" + c2cInWdAmount +
                ", c2cOutRtCount=" + c2cOutRtCount +
                ", c2cOutRtAmount=" + c2cOutRtAmount +
                ", c2cOutWdCount=" + c2cOutWdCount +
                ", c2cOutWdAmount=" + c2cOutWdAmount +
                ", o2cOutRtWdCount=" + o2cOutRtWdCount +
                ", o2cOutRtWdAmount=" + o2cOutRtWdAmount +
                ", c2cOutTrfCount=" + c2cOutTrfCount +
                ", c2cOutTrfAmount=" + c2cOutTrfAmount +
                ", c2cOutRtWdCount=" + c2cOutRtWdCount +
                ", c2cOutRtWdAmount=" + c2cOutRtWdAmount +
                ", c2sOutTrfCount=" + c2sOutTrfCount +
                ", c2sOutTrfAmount=" + c2sOutTrfAmount +
                ", netBalance=" + netBalance +
                '}';
    }
}


