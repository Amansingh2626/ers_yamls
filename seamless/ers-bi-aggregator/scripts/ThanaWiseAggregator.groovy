package com.seamless.customer.bi.aggregator.aggregate

import com.seamless.common.StringUtils
import com.seamless.customer.bi.aggregator.aggregate.AbstractAggregator
import com.seamless.customer.bi.aggregator.model.ReportIndex
import com.seamless.customer.bi.aggregator.util.DateUtil
import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import org.apache.commons.lang.exception.ExceptionUtils
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.action.search.SearchScrollRequest
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.core.TimeValue
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.rest.RestStatus
import org.elasticsearch.search.aggregations.Aggregation
import org.elasticsearch.search.aggregations.AggregationBuilders
import org.elasticsearch.search.aggregations.Aggregations
import org.elasticsearch.search.aggregations.ParsedMultiBucketAggregation
import org.elasticsearch.search.aggregations.bucket.composite.CompositeAggregationBuilder
import org.elasticsearch.search.aggregations.bucket.composite.CompositeValuesSourceBuilder
import org.elasticsearch.search.aggregations.bucket.composite.ParsedComposite
import org.elasticsearch.search.aggregations.bucket.composite.TermsValuesSourceBuilder
import org.elasticsearch.search.aggregations.metrics.ParsedSum
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.ColumnMapRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import java.text.SimpleDateFormat

import java.util.*
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.*;

/**
 *
 *
 *
 *
 */
@Slf4j
//@DynamicMixin
public class ThanaWiseAggregator extends AbstractAggregator {

    static final def TABLE = "geo_manager_mapping"
    static final def TRIGGER_SQL = "select geo_code from " +TABLE;


    @Autowired
    RestHighLevelClient client

    @Autowired
    protected JdbcTemplate jdbcTemplate


    @Value('${ThanaWiseAggregator.scrollSize:10000}')
    int scrollSize

    @Value('#{${ThanaWiseAggregator.mappedData}}')
    private Map<String, String> mappedData;


    @Value('${ThanaWiseAggregator.notificationUrl:http://localhost:8277/register}')
    String notificationUrl

    SimpleDateFormat timeFormate = new SimpleDateFormat("HH:mm:ss")

    Optional<DateMapping> dateMapping;


    @Transactional
    @Scheduled(cron = '${ThanaWiseAggregator.cron:*/3 * * * * ?}')
    public void aggregate() {

        log.info("********** ThanaWiseAggregator Aggregator started at " + new Date())
        String current = timeFormate.format(new Date())
        Set<String> key = mappedData.keySet();
        dateMapping = key.stream()
                .filter(
                        { data ->
                            String[] data1 = data.split("/");
                            if (current.compareTo(data1[0]) >= 0 && current.compareTo(data1[1]) <= 0) {
                                return true;
                            }
                            return false;
                        }
                ).map({ data -> new DateMapping(mappedData.get(data)) }).findFirst();



        List<String> areaCodes = fetchAreaCodeData();
        List<ReportIndex> indices = DateUtil.getIndex()

        for (ReportIndex index : indices) {
            log.info(index.toString())
            //fetch data from ES
            List<ThanaWiseReportModel> thanaWiseAggregatorModels = aggregateDataES(index.getIndexName(), index
                    .getStartDate(), index.getEndDate(), areaCodes)
            //    insertAggregation(thanaWiseAggregatorModels)

            List<AreaDetailsModel> areas = fetchAreaDetails();

            HttpHeaders headers = new HttpHeaders();
            sendNotification(headers, areas, thanaWiseAggregatorModels)

        }

        log.info("********** C2SDailyAggregator Aggregator ended at " + new Date())
    }

    def sendNotification(HttpHeaders headers,
                         List<AreaDetailsModel> areaDetails,
                         List<ThanaWiseReportModel> responseModels)
    {
        log.info("Sending notifications start ***************")

        areaDetails.forEach({ areaRecord ->
            log.info("sending message for region: " + areaRecord.getRegionName())
            ThanaWiseReportModel data = responseModels.stream().filter({ model ->
                model.getArea() ==  areaRecord.getRegionName()}).findFirst().get()
            log.info("##Area Name : " + data!=null? data.getAreaName():null);
            if(data!=null) {
                log.info("Sending sms ***************")
                prepareSmsNotificationRequestBody(data, areaRecord)

                log.info("Sending email ***************")

                Map<String, Object> fields = new ConcurrentHashMap();
                fields.put("notificationType", "EMAIL");
                fields.put("recipientId", areaRecord.getEmail());
                fields.put("senderId", "se.vms@seamless.se");
                fields.put("message", readEmailMessage(data));
                fields.put("referenceEventId", "eventId");


                Map<String, Object> emailProps = new HashMap<>();
                fields.put("EMAILPROPS", emailProps);

                Map req = new HashMap();
                req.put("eventTag","ADHOC_ALERT");
                req.put("fields",fields);

                log.info("Request Data: " + new JsonBuilder(req).toPrettyString());
                HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(req, headers);
                RestTemplate restTemplate = new RestTemplate();

                try {
                    String returnStatus = restTemplate.postForObject(notificationUrl, request,  String.class);
                    log.info("Email sent status" + returnStatus);

                }catch(Exception e){
                    log.error(e);
                }
            }
        })
        log.info("notifications sending ended ***************");
    }


/**
 *
 * @param data
 * @return
 */
    def readEmailMessage(ThanaWiseReportModel data){
        File f = new File('/opt/seamless/conf/bi-aggregator/scripts/thana_message_content.ftl')
        def engine = new groovy.text.GStringTemplateEngine()

        def area = ""
        if(!data.getC2cThanaWise().isEmpty())
        {
            log.info("##Setting area name for c2c thana wise before sending email notification " , data.getAreaName())
            area = data.getAreaName();
        }

        else if(!data.getC2sThanaWise().isEmpty())
        {
            log.info("##Setting area name for c2s thana wise before sending email notification " , data.getAreaName())
            area = data.getAreaName();
        }

        if(StringUtils.isEmpty(area)){
            area = data.getAreaName();
            log.info("####{ readEmailMessage }- Area Name is not available for c2cByThana and c2sByThana. Seems there are no transactions available :AreaName = {}" ,area);
        }
        def binding = [data : data,  area : area]
        def template = engine.createTemplate(f).make(binding)
        return template.toString()
    }


    def prepareSmsNotificationRequestBody(ThanaWiseReportModel data, AreaDetailsModel record){

        Map<String, Object> fields = new ConcurrentHashMap();
        fields.put("notificationType","SMS");
        fields.put("recipientId", record.getMsisdn());
        fields.put("message", readReminderSmsBody(data));
        fields.put("senderid", "9988776655");
        fields.put("referenceEventId","eventId");

        Map req = new HashMap();
        req.put("eventTag","ADHOC_ALERT");
        req.put("fields",fields);

        //log.info("Request Data: " + new JsonBuilder(req).toPrettyString());
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(req, headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            String returnStatus = restTemplate.postForObject(notificationUrl, request,  String.class);
            log.info("SMS sent status" + returnStatus);

        }catch(Exception e){
            log.error(e);
        }

    }

    static def readReminderSmsBody(ThanaWiseReportModel data){


        def f = new File('/opt/seamless/conf/bi-aggregator/scripts/thana_message_content.ftl')
        def engine = new groovy.text.GStringTemplateEngine()

        def area = ""
        if(!data.getC2cThanaWise().isEmpty())
        {
            log.info("##Setting area name for c2c thana wise before sending sms notification " , data.getAreaName())
            area = data.getAreaName();
        }

        else if(!data.getC2sThanaWise().isEmpty())
        {
            log.info("##Setting area name for c2s thana wise before sending sms notification " , data.getAreaName())
            area = data.getAreaName();
        }
        if(StringUtils.isEmpty(area)){
            area = data.getAreaName();
            log.info("####{ readReminderSmsBody }- Area Name is not available for c2cByThana and c2sByThana. Seems there are no transactions available :AreaName = {}" ,area);
        }

        def binding = [data : data, area : area]
        def template = engine.createTemplate(f).make(binding)
        return template.toString()
    }

    private List<ThanaWiseReportModel> aggregateDataES(String index, String fromDate, String toDate, List<String> areaCodes) {


        SearchRequest searchRequest = new SearchRequest(index)
        SearchSourceBuilder searchSourceBuilder = fetchInput(fromDate, toDate, "C2C", areaCodes)
        searchRequest.source(searchSourceBuilder)
        searchRequest.scroll(TimeValue.timeValueMinutes(5))
        List<ThanaWiseModel> c2sThanaWiseAggregatorModels = generateResponse(searchRequest,  "C2C");


        searchSourceBuilder = fetchInput(fromDate, toDate,"C2S", areaCodes);
        searchRequest.source(searchSourceBuilder);
        searchRequest.scroll(TimeValue.timeValueMinutes(5))
        List<ThanaWiseModel> c2cThanaWiseAggregatorModels = generateResponse(searchRequest, "C2S");

        List<AreaDetailsModel> areas = fetchAreaDetails();
        List<ThanaWiseReportModel> responseModels = generateFinalResponse(c2sThanaWiseAggregatorModels, c2cThanaWiseAggregatorModels, areas);
        return responseModels;

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


    private SearchSourceBuilder fetchInput(String fromDate, String toDate, String transactionType, List<String> areaCodes) {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder queryBuilder = null
        CompositeAggregationBuilder compositeBuilder = null
        if(transactionType == "C2C")
        {

        //    receiver_ownerId = sender_ownerId = SENDER_FIELD_ownerName
            List<CompositeValuesSourceBuilder<?>> sources = new ArrayList<>();
            sources.add(new TermsValuesSourceBuilder("RECEIVER_FIELD_thanaCode").field("RECEIVER_FIELD_thanaCode.keyword").missingBucket(true));
            sources.add(new TermsValuesSourceBuilder("receiver_ownerId").field("receiver_ownerId.keyword").missingBucket(true));
            sources.add(new TermsValuesSourceBuilder("RECEIVER_FIELD_areaCode").field("RECEIVER_FIELD_areaCode.keyword").missingBucket(true));
            sources.add(new TermsValuesSourceBuilder("RECEIVER_FIELD_areaName").field("RECEIVER_FIELD_areaName.keyword").missingBucket(true));
            sources.add(new TermsValuesSourceBuilder("SENDER_FIELD_ownerName").field("SENDER_FIELD_ownerName.keyword").missingBucket(true));
            sources.add(new TermsValuesSourceBuilder("RECEIVER_FIELD_thanaName").field("RECEIVER_FIELD_thanaName.keyword").missingBucket(true));

            compositeBuilder = new CompositeAggregationBuilder("ThanaWise",
                    sources).size(10000);
            compositeBuilder.subAggregation(AggregationBuilders.sum("totalAmountValue").field("transactionAmount"))
            queryBuilder = QueryBuilders.boolQuery()
                    .must(QueryBuilders.wildcardQuery("productSKU.keyword","C2C*"))
                    .filter(QueryBuilders.termsQuery("ReceiverResellerType.keyword","RET"))
                    .filter(QueryBuilders.termsQuery("resultCode", 0))
                    .filter(QueryBuilders.termsQuery("RECEIVER_FIELD_areaCode.keyword", areaCodes))


        }
        else
        {
            List<CompositeValuesSourceBuilder<?>> sources = new ArrayList<>();
            sources.add(new TermsValuesSourceBuilder("SENDER_FIELD_areaName").field("SENDER_FIELD_areaName.keyword").missingBucket(true));
            sources.add(new TermsValuesSourceBuilder("SENDER_FIELD_thanaCode").field("SENDER_FIELD_thanaCode.keyword").missingBucket(true));
            sources.add(new TermsValuesSourceBuilder("SENDER_FIELD_areaCode").field("SENDER_FIELD_areaCode.keyword").missingBucket(true));
            sources.add(new TermsValuesSourceBuilder("SENDER_FIELD_ownerName").field("SENDER_FIELD_ownerName.keyword").missingBucket(true));
            sources.add(new TermsValuesSourceBuilder("sender_ownerId").field("sender_ownerId.keyword").missingBucket(true));
            sources.add(new TermsValuesSourceBuilder("SENDER_FIELD_thanaName").field("SENDER_FIELD_thanaName.keyword").missingBucket(true));

            compositeBuilder = new CompositeAggregationBuilder("ThanaWise",
                    sources).size(10000);
            compositeBuilder.subAggregation(AggregationBuilders.sum("totalAmountValue").field("transactionAmount"))

            queryBuilder = QueryBuilders.boolQuery()
                    .must(QueryBuilders.wildcardQuery("transactionProfile.keyword","C2S*"))
                    .filter(QueryBuilders.termsQuery("resultCode", 0))
                    .filter(QueryBuilders.termsQuery("SENDER_FIELD_areaCode.keyword", areaCodes))


        }

        if(dateMapping.isPresent()){
            queryBuilder = queryBuilder.filter(QueryBuilders.rangeQuery("timestamp").gte(dateMapping.get().getFrom()).lt(dateMapping.get().getTo())
                    .includeLower(true).includeUpper(true))
        }
        log.info("Final query for " + queryBuilder.toString());
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.aggregation(compositeBuilder).size(scrollSize);
        return searchSourceBuilder;
    }


    private List<ThanaWiseModel> generateResponse(SearchRequest searchRequest, String type) {
        List<ThanaWiseModel> thanaWiseModels = new ArrayList<>();
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, COMMON_OPTIONS);
        } catch (Exception e) {
            log.error("Error mapping rule " + searchRequest + "\nError message : " + e);
        }
        log.info("*******Request:::: " + searchRequest.toString())
        RestStatus status = searchResponse.status();
        log.info("response status -------------" + status);
        if (status == RestStatus.OK) {
            Aggregations aggregations = searchResponse.getAggregations();

            ParsedComposite parsedComposite = aggregations.asMap().get("ThanaWise");
            for (ParsedMultiBucketAggregation.ParsedBucket bucket : parsedComposite.getBuckets()) {
                LinkedHashMap<String, String> keyValuesMap = bucket.getKey();
                Map<String, Aggregation> aggregationMap = bucket.getAggregations().asMap();
                Aggregation totalAmountAggregration = aggregationMap.get("totalAmountValue");
                ParsedSum p = (ParsedSum) totalAmountAggregration;
                // Date dateTimeDay = DateFormatter.formatDate(keyValuesMap.get("EndTimeDay"));
                ThanaWiseModel thanaWiseModel = null;
                if(type.equals("C2C")) {
                    log.info("##Area Code for C2C is " + keyValuesMap.get("RECEIVER_FIELD_areaCode"));
                    log.info("##Area Name for C2C is " + keyValuesMap.get("RECEIVER_FIELD_areaName"));
                    thanaWiseModel = new ThanaWiseModel(keyValuesMap.get("receiver_ownerId"), keyValuesMap.get("RECEIVER_FIELD_thanaCode"),
                            BigDecimal.valueOf(p.value()).setScale(2,BigDecimal.ROUND_UP).toPlainString(), keyValuesMap.get("RECEIVER_FIELD_areaCode"), keyValuesMap.get("RECEIVER_FIELD_areaName"), keyValuesMap.get("SENDER_FIELD_ownerName"), keyValuesMap.get("RECEIVER_FIELD_thanaName"));
                } else
                {
                    log.info("##Area Code for C2S is " + keyValuesMap.get("SENDER_FIELD_areaCode"));
                    log.info("##Area Name for C2S is " + keyValuesMap.get("SENDER_FIELD_areaName"));
                    thanaWiseModel = new ThanaWiseModel(keyValuesMap.get("sender_ownerId"), keyValuesMap.get("SENDER_FIELD_thanaCode"),
                            BigDecimal.valueOf(p.value()).setScale(2,BigDecimal.ROUND_UP).toPlainString(), keyValuesMap.get("SENDER_FIELD_areaCode"), keyValuesMap.get("SENDER_FIELD_areaName"), keyValuesMap.get("SENDER_FIELD_ownerName"), keyValuesMap.get("SENDER_FIELD_thanaName"));
                }
                log.info("##Thana Wise is " + thanaWiseModel.toString())
                thanaWiseModels.add(thanaWiseModel);
            }
        }
        return thanaWiseModels;
    }


/**
 *
 * @param c2cResponseModels
 * @param c2sResponseModels
 * @param areaCodes
 * @return
 */
    private static List<ThanaWiseReportModel> generateFinalResponse(List<ThanaWiseModel> c2cResponseModels, List<ThanaWiseModel> c2sResponseModels, List<AreaDetailsModel> areaCodes){
        //   List<String> regions = ["a","b","c"]


        List<ThanaWiseReportModel> thanaWiseReportModels = new ArrayList<>()
        log.info("generateFinalResponse c2cResponseModels size = "+c2cResponseModels.size());
        log.info("generateFinalResponse c2sResponseModels size = "+c2sResponseModels.size());
        try{
            areaCodes.forEach({ area ->
                List<ThanaWiseModel> c2cByThana = c2cResponseModels.stream().filter(
                        { c2c -> (c2c.getArea().equalsIgnoreCase(area.getRegionName())) }).collect(Collectors.toList());
                log.info("#c2cByThana size = "+c2cByThana.size());
                log.info("#Area Get RegionName = "+area.getRegionName());
                String areaName = "";
                double totalC2c = 0;
                c2cByThana.stream().forEach({ c2c -> totalC2c = totalC2c + Double.parseDouble(c2c.getAmount()) })
                List<ThanaWiseModel> c2sByThana = c2sResponseModels.stream().filter(
                        { c2s -> (c2s.getArea().equalsIgnoreCase( area.getRegionName())) }).collect(Collectors.toList());

                log.info("#c2sByThana size = "+c2sByThana.size());
                double totalC2s = 0;
                c2sByThana.stream().forEach({ c2s -> totalC2s = totalC2s + Double.parseDouble(c2s.getAmount()) })

                if (!c2cByThana.isEmpty()) {
                    log.info("##########Check1############");
                    Optional<ThanaWiseModel> model = c2cByThana.stream().filter(
                            { name -> name.getAreaName() != null }).findAny();
                    if (model.isPresent()) {
                        log.info("##########Check1.1############");
                        areaName = model.get().getAreaName()
                    }
                    if(StringUtils.isEmpty(areaName)){
                        log.info("#### Area Name is not available for c2cByThana");
                    }
                }
                if (!c2sByThana.isEmpty()) {
                    log.info("##########Check2############");
                    Optional<ThanaWiseModel> model = c2sByThana.stream().filter(
                            { name -> name.getAreaName() != null }).findAny();
                    if (model.isPresent()) {
                        log.info("##########Check2.1############");
                        areaName = model.get().getAreaName()
                    }
                    if(StringUtils.isEmpty(areaName)){
                        log.info("#### Area Name is not available for c2sByThana");
                    }
                }

                if(StringUtils.isEmpty(areaName)){
                    areaName = area.getAreaName()
                    log.info("####{ generateFinalResponse }- Area Name is not available for c2cByThana and c2sByThana. Seems there are no transactions available :AreaName = {}" ,areaName);
                }

                ThanaWiseReportModel thanaWiseReportModel = new ThanaWiseReportModel(
                        area.getRegionName()
                        , BigDecimal.valueOf(totalC2c).setScale(2,BigDecimal.ROUND_UP).toPlainString()
                        , BigDecimal.valueOf(totalC2s).setScale(2,BigDecimal.ROUND_UP).toPlainString()
                        , c2cByThana, c2sByThana,areaName)
                log.info("##Thana Wise Report Model is " + thanaWiseReportModel.toString())
                thanaWiseReportModels.add(thanaWiseReportModel)

            })
            log.info("ThanaWiseReportModel size = "+thanaWiseReportModels.size());
        }catch(Exception e){
            log.error(" error :: == "+ ExceptionUtils.getFullStackTrace(e))
        }
        return thanaWiseReportModels
    }


    private List<AreaDetailsModel> fetchAreaDetails()
    {
        String query = "select a.geo_code as regionName,a.geo_name as areaName, b.`manager_name` managerName, b.`email` as email, b.`msisdn` as msisdn" +
                " from geo_manager_mapping a, geo_manager b " +
                " where a.geo_manager_id=b.id and manager_type=\"AREA\";"

        List<AreaDetailsModel> areaDetailsModelList = new ArrayList<>();

        def result = jdbcTemplate.queryForList(query)
        for (Map<String,Object> row : result)
        {
            AreaDetailsModel areaDetails = new AreaDetailsModel(
                    row.get("regionName") as String,
                    row.get("areaName") as String,
                    row.get("managerName") as String,
                    row.get("email") as String,
                    row.get("msisdn") as String,
            )

            areaDetailsModelList.add(areaDetails)
        }
        return areaDetailsModelList
    }

    private List<String> fetchAreaCodeData(){


        // fetch last trigger status from mysql cutoff_trigger_status
        def triggerData = jdbcTemplate.query(TRIGGER_SQL, new ColumnMapRowMapper());

        log.debug("Received ${triggerData.size()} rows from geo code mappings")
        def areaCode = "";
        def areaCodeList = [];
        for(int i = 0; i < triggerData.size(); i++)
        {
            areaCode = triggerData.get(i).get("geo_code");
            areaCodeList.add(areaCode)
        }

        return areaCodeList;
    }


}

class ThanaWiseModel
{
    private String owner;
    private String thana;
    private String amount;
    private String area;
    private String areaName;
    private String ownerName;
    private String thanaName;

    ThanaWiseModel(String owner, String thana, String amount, String area, String areaName, String ownerName, String thanaName) {
        this.owner = owner
        this.thana = thana
        this.amount = amount
        this.area = area
        this.areaName = areaName
        this.ownerName = ownerName
        this.thanaName = thanaName
    }

    String getOwner() {
        return owner
    }

    void setOwner(String owner) {
        this.owner = owner
    }

    String getThana() {
        return thana
    }

    void setThana(String thana) {
        this.thana = thana
    }

    String getAmount() {
        return amount
    }

    void setAmount(String amount) {
        this.amount = amount
    }

    String getArea() {
        return area
    }

    void setArea(String area) {
        this.area = area
    }

    String getAreaName() {
        return areaName
    }

    void setAreaName(String areaName) {
        this.areaName = areaName
    }

    String getOwnerName() {
        return ownerName
    }

    void setOwnerName(String ownerName) {
        this.ownerName = ownerName
    }

    String getThanaName() {
        return thanaName
    }

    void setThanaName(String thanaName) {
        this.thanaName = thanaName
    }

    @Override
    public String toString() {
        return "ThanaWiseModel{" +
                "owner='" + owner + '\'' +
                ", thana=" + thana +
                ", amount=" + amount +
                ", area=" + area +
                ", areaName=" + areaName +
                ", ownerName='" + ownerName + '\'' +
                ", thanaName='" + thanaName + '\'' +
                '}';
    }
}

class ThanaWiseReportModel {
    private String area;
    private String totalC2cAmount;
    private String totalC2sAmount;
    private List<ThanaWiseModel> c2cThanaWise;
    private List<ThanaWiseModel> c2sThanaWise;
    private String date;
    private String areaName;


    ThanaWiseReportModel(String area, String totalC2cAmount, String totalC2sAmount, List<ThanaWiseModel> c2cThanaWise, List<ThanaWiseModel> c2sThanaWise,String areaName) {
        this.area = area
        this.totalC2cAmount = totalC2cAmount
        this.totalC2sAmount = totalC2sAmount
        this.c2cThanaWise = c2cThanaWise
        this.c2sThanaWise = c2sThanaWise
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        this.date = formatter.format(date);
        this.areaName = areaName;

    }

    String getArea() {
        return area
    }

    void setArea(String area) {
        this.area = area
    }

    String getTotalC2cAmount() {
        return totalC2cAmount
    }

    void setTotalC2cAmount(String totalC2cAmount) {
        this.totalC2cAmount = totalC2cAmount
    }

    String getTotalC2sAmount() {
        return totalC2sAmount
    }

    void setTotalC2sAmount(String totalC2sAmount) {
        this.totalC2sAmount = totalC2sAmount
    }

    List<ThanaWiseModel> getC2cThanaWise() {
        return c2cThanaWise
    }

    void setC2cThanaWise(List<ThanaWiseModel> c2cThanaWise) {
        this.c2cThanaWise = c2cThanaWise
    }

    List<ThanaWiseModel> getC2sThanaWise() {
        return c2sThanaWise
    }

    void setC2sThanaWise(List<ThanaWiseModel> c2sThanaWise) {
        this.c2sThanaWise = c2sThanaWise
    }


    String getDate() {
        return date
    }

    void setDate(String date) {
        this.date = date
    }

    String getAreaName() {
        return areaName
    }

    void setAreaName(String areaName) {
        this.areaName = areaName
    }


    @Override
    public String toString() {
        return "ThanaWiseReportModel{" +
                "region='" + area + '\'' +
                ", totalC2cAmount=" + totalC2cAmount +
                ", totalC2sAmount=" + totalC2sAmount +
                ", c2cThanaWise=" + c2cThanaWise +
                ", c2sThanaWise=" + c2sThanaWise +
                ", date='" + date + '\'' +
                ", areaName='" + areaName + '\'' +
                '}';
    }
}

class AreaDetailsModel {

    private String regionName;
    private String areaName;
    private String managerName;
    private String email;
    private String msisdn;

    AreaDetailsModel(String regionName,String areaName, String managerName, String email, String msisdn) {
        this.regionName = regionName
        this.areaName = areaName
        this.managerName = managerName
        this.email = email
        this.msisdn = msisdn
    }

    String getRegionName() {
        return regionName
    }

    void setRegionName(String regionName) {
        this.regionName = regionName
    }

    String getAreaName() {
        return areaName
    }

    void setAreaName(String areaName) {
        this.areaName = areaName
    }

    String getManagerName() {
        return managerName
    }

    void setManagerName(String managerName) {
        this.managerName = managerName
    }

    String getEmail() {
        return email
    }

    void setEmail(String email) {
        this.email = email
    }

    String getMsisdn() {
        return msisdn
    }

    void setMsisdn(String msisdn) {
        this.msisdn = msisdn
    }
}

class DateMapping {

    private String from = "now/d";

    private String to;

    String getFrom() {
        return from
    }

    void setFrom(String from) {
        this.from = from
    }

    String getTo() {
        return to
    }

    void setTo(String to) {
        this.to = to
    }

    DateMapping() {
    }

    DateMapping(String from, String to) {
        this.from = from
        this.to = to
    }

    DateMapping(String to) {
        SimpleDateFormat dateFormat =new SimpleDateFormat("YYYY-MM-dd")
        this.to = dateFormat.format(new Date())+to
    }

    @Override
    public String toString() {
        return "DateMapping{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}

