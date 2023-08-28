package com.seamless.customer.bi.aggregator.aggregate

import com.seamless.common.StringUtils
import com.seamless.customer.bi.aggregator.aggregate.AbstractAggregator
import com.seamless.customer.bi.aggregator.model.ReportIndex
import com.seamless.customer.bi.aggregator.util.DateUtil
import groovy.text.GStringTemplateEngine
import groovy.util.logging.Slf4j
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
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
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.client.RestTemplate
import java.text.SimpleDateFormat
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.util.MultiValueMap
import java.util.concurrent.ConcurrentHashMap
import org.springframework.scheduling.annotation.Scheduled
import org.apache.commons.lang.exception.ExceptionUtils

import java.util.stream.Collectors

@Slf4j
public class RegionWiseSmsReport extends AbstractAggregator
{

    @Value('${RegionWiseSmsReport.notificationUrl:http://localhost:8277}')
    String notificationManagerUrl;

    @Autowired
    RestHighLevelClient client

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    SimpleDateFormat timeFormate = new SimpleDateFormat("HH:mm:ss")

    static Optional<DateMapping> dateMapping;

    @Value('#{${RegionWiseSmsReport.mappedData}}')
    private Map<String, String> mappedData;

    @Override
    @Scheduled(cron = '${RegionWiseSmsReport.cron:*/3 * * * * ?}')
    void aggregate() {
        log.info("region wise sms report started***************************************************************************" + new Date());
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

        List<ReportIndex> indices = DateUtil.getIndex();
        for (ReportIndex index : indices) {
            log.info(index.toString())
            aggregateDataES(index.getIndexName(), index.getStartDate(), index.getEndDate());
        }
    }

    private void aggregateDataES(String index, String fromDate, String toDate) {
        HttpHeaders headers = new HttpHeaders()
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = fetchInput("C2C")
        searchRequest.source(searchSourceBuilder)
        List<AreaWiseModel> c2cResponseModels = generateResponse(searchRequest)
        searchSourceBuilder = fetchInput("C2S")
        searchRequest.source(searchSourceBuilder)
        List<AreaWiseModel> c2sResponseModels = generateResponse(searchRequest)
        List<RegionDetailsModel> regionDetails = fetchRegionDetails()
        List<RegionWiseReportModel> responseModels = generateFinalResponse(c2cResponseModels,c2sResponseModels,regionDetails)
        sendNotification(headers, regionDetails, responseModels)
    }

    private static SearchSourceBuilder fetchInput(String transactionType) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder queryBuilder
        CompositeAggregationBuilder compositeBuilder = null
        if(transactionType == "C2C")
        {
            List<CompositeValuesSourceBuilder<?>> sources = new ArrayList<>();
            sources.add(new TermsValuesSourceBuilder("areaCode").field("RECEIVER_FIELD_areaCode.keyword").missingBucket(true));
            sources.add(new TermsValuesSourceBuilder("regionCode").field("RECEIVER_FIELD_regionCode.keyword").missingBucket(true));
            sources.add(new TermsValuesSourceBuilder("areaName").field("RECEIVER_FIELD_areaName.keyword").missingBucket(true));
            sources.add(new TermsValuesSourceBuilder("regionName").field("RECEIVER_FIELD_regionName.keyword").missingBucket(true));


            compositeBuilder = new CompositeAggregationBuilder("AreaWise",
                    sources).size(10000);

            compositeBuilder.subAggregation(AggregationBuilders.sum("totalAmountValue").field("transactionAmount"))

            queryBuilder = QueryBuilders.boolQuery()
                    .must(QueryBuilders.wildcardQuery("productSKU.keyword","C2C*"))
                    .filter(QueryBuilders.termsQuery("ReceiverResellerType.keyword","RET"))
                    .filter(QueryBuilders.termsQuery("resultCode", 0))
        }
        else
        {
            List<CompositeValuesSourceBuilder<?>> sources = new ArrayList<>();
            sources.add(new TermsValuesSourceBuilder("areaCode").field("SENDER_FIELD_areaCode.keyword").missingBucket(true));
            sources.add(new TermsValuesSourceBuilder("regionCode").field("SENDER_FIELD_regionCode.keyword").missingBucket(true));
            sources.add(new TermsValuesSourceBuilder("areaName").field("SENDER_FIELD_areaName.keyword").missingBucket(true));
            sources.add(new TermsValuesSourceBuilder("regionName").field("SENDER_FIELD_regionName.keyword").missingBucket(true));


            compositeBuilder = new CompositeAggregationBuilder("AreaWise",
                    sources).size(10000);

            compositeBuilder.subAggregation(AggregationBuilders.sum("totalAmountValue").field("transactionAmount"))

            queryBuilder = QueryBuilders.boolQuery()
                    .must(QueryBuilders.wildcardQuery("transactionProfile.keyword","C2S*"))
                    .filter(QueryBuilders.termsQuery("resultCode", 0))
        }
        if(dateMapping.isPresent()){
            queryBuilder =  queryBuilder.filter(QueryBuilders.rangeQuery("timestamp").gte(dateMapping.get().getFrom()).lt(dateMapping.get().getTo())
                    .includeLower(true).includeUpper(true))
        }
        log.info("Final query for " + transactionType , queryBuilder.toString());
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.aggregation(compositeBuilder).size(10000);
        return searchSourceBuilder;
    }

    private List<AreaWiseModel> generateResponse(SearchRequest searchRequest) {

        List<AreaWiseModel> areaWiseModels = new ArrayList<>();
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
            ParsedComposite parsedComposite = aggregations.asMap().get("AreaWise");


            for (ParsedMultiBucketAggregation.ParsedBucket bucket : parsedComposite.getBuckets()) {
                LinkedHashMap<String, String> keyValuesMap = bucket.getKey();
                log.info("==============================================================================================================================");

                log.info("keyValuesMap =" +keyValuesMap.toString());
                log.info("==============================================================================================================================");

                Map<String, Aggregation> aggregationMap = bucket.getAggregations().asMap();
                log.info("aggregationMap = "+aggregationMap);
                log.info("==============================================================================================================================");

                Aggregation totalAmountAggregration = aggregationMap.get("totalAmountValue");
                ParsedSum p = (ParsedSum) totalAmountAggregration;
                // Date dateTimeDay = DateFormatter.formatDate(keyValuesMap.get("EndTimeDay"));
                log.info("==============================================================================================================================");

                log.info("keyValuesMap.get(\"regionCode\") ="+keyValuesMap.get("regionCode"));
                log.info("keyValuesMap.get(\"regionName\") ="+keyValuesMap.get("regionName"));
                log.info("keyValuesMap.get(\"areaCode\") ="+keyValuesMap.get("areaCode"));
                log.info("==============================================================================================================================");

                log.info("p.value()"+p.value())
                AreaWiseModel areaWiseModel = new AreaWiseModel(keyValuesMap.get("regionCode"),keyValuesMap.get("areaCode"),BigDecimal.valueOf(p.value()).setScale(2,BigDecimal.ROUND_UP).toPlainString(),keyValuesMap.get("areaName"),keyValuesMap.get("regionName"))
                log.info("AreaWiseModel Details ="+areaWiseModel.toString());
                areaWiseModels.add(areaWiseModel)
            }
        }
        log.info("==============================================================================================================================");
        log.info("==============================================================================================================================");

        return areaWiseModels;
    }

    private List<RegionDetailsModel> fetchRegionDetails()
    {
        String query = "select a.geo_code as regionCode,a.geo_name as regionName, b.`manager_name` as managerName, b.`email` as email, b.`msisdn` as msisdn from geo_manager_mapping a, geo_manager b where a.geo_manager_id=b.id and manager_type=\"REGION\";"

        List<RegionDetailsModel> regionDetailsModelList = new ArrayList<>()

        def result = jdbcTemplate.queryForList(query)
        for (Map<String,Object> row : result)
        {
            RegionDetailsModel regionDetails = new RegionDetailsModel(
                    row.get("regionCode") as String,
                    row.get("regionName") as String,
                    row.get("managerName") as String,
                    row.get("email") as String,
                    row.get("msisdn") as String,
            )

            regionDetailsModelList.add(regionDetails)
        }
        return regionDetailsModelList
    }

    private static List<RegionWiseReportModel> generateFinalResponse(List<AreaWiseModel> c2cResponseModels,
                                                                     List<AreaWiseModel> c2sResponseModels,
                                                                     List<RegionDetailsModel> regionDetails){
        List<String> regions = new ArrayList<>()
        List<RegionWiseReportModel> regionWiseSmsReportModels = new ArrayList<>()

        log.info("generateFinalResponse c2cResponseModels size = "+c2cResponseModels.size());
        log.info("generateFinalResponse c2sResponseModels size = "+c2sResponseModels.size());
        try {
            log.info("c2cResponseModels Details Started ========");
            c2cResponseModels.forEach({ c2c ->
                log.info(c2c.toString());
            });
            log.info("c2cResponseModels Details Completed ========");

            log.info("c2sResponseModels Details Started ========");
            c2sResponseModels.forEach({ c2s ->
                log.info(c2s.toString());
            });
            log.info("c2sResponseModels Details Completed ========");

            regionDetails.forEach({ region ->
                List<AreaWiseModel> c2cByRegion = c2cResponseModels.stream().filter(
                        { c2c -> (region.getRegionCode().equalsIgnoreCase(c2c.getRegion())) }).map({ c2c ->
                    if (StringUtils.isEmpty(c2c.getRegionName())) {
                        c2c.setRegionName(region.getRegionName());
                    };
                    return c2c;
                }).collect(Collectors.toList())

                double totalC2c = 0;
                c2cByRegion.stream().forEach({ c2c -> totalC2c = totalC2c + Double.parseDouble(c2c.getAmount()) })

                List<AreaWiseModel> c2sByRegion = c2sResponseModels.stream().filter(
                        { c2s -> (region.getRegionCode().equalsIgnoreCase(c2s.getRegion())) })
                        .map({ c2s ->
                            if (StringUtils.isEmpty(c2s.getRegionName())) {
                                c2s.setRegionName(region.getRegionName());
                            };
                            return c2s;
                        }).collect(Collectors.toList())

                double totalC2s = 0;
                c2sByRegion.stream().forEach({ c2s -> totalC2s = totalC2s + Double.parseDouble(c2s.getAmount()) })

                String regionName = null
                String regionCode = null
                // (!c2cByRegion.isEmpty()) ? {regionName = c2cByRegion.get(0).getRegionName()} : {regionName = c2sByRegion.get(0).getRegionName()}
                // regionName = !c2cByRegion.isEmpty() ? c2cByRegion.get(0).getRegionName() : c2sByRegion.get(0).getRegionName()

                if (!c2cByRegion.isEmpty()) {
                    Optional<AreaWiseModel> model = c2cByRegion.stream().filter(
                            { name -> name.getRegionName() != null }).findAny();
                    if (model.isPresent()) {
                        regionName = model.get().getRegionName()
                        regionCode = model.get().getRegion()
                    }
                    if(StringUtils.isEmpty(regionName)){
                        log.info("#### Region Name is not available for c2cByRegion");
                    }
                }
                if (!c2sByRegion.isEmpty()) {
                    Optional<AreaWiseModel> model = c2sByRegion.stream().filter(
                            { name -> name.getRegionName() != null }).findAny();
                    if (model.isPresent()) {
                        regionName = model.get().getRegionName()
                        regionCode = model.get().getRegion()
                    }
                    if(StringUtils.isEmpty(regionName)){
                        log.info("#### Region Name is not available for c2sByRegion");
                    }
                }
                log.info("#### Region Name from Model :"+regionName);
                log.info("#### Region Code from Model :"+regionCode);

                if(StringUtils.isEmpty(regionName)){
                    log.info("#### Region Name is not available for c2sByRegion and c2cByRegion. Seems there are no transactions available. hence applying region name from database");
                    regionName=region.getRegionName();
                }

                log.info("#### Region Code from DB :"+region.getRegionCode());
                log.info("#### Region Name from DB :"+region.getRegionName());

                RegionWiseReportModel regionWiseSmsReportModel = new RegionWiseReportModel(
                        region.getRegionCode(), BigDecimal.valueOf(totalC2c).setScale(2,BigDecimal.ROUND_UP).toPlainString(),
                        BigDecimal.valueOf(totalC2s).setScale(2,BigDecimal.ROUND_UP).toPlainString(), c2cByRegion, c2sByRegion, regionName)

                regionWiseSmsReportModels.add(regionWiseSmsReportModel)
            })

            log.info("regionWiseSmsReportModels: " + regionWiseSmsReportModels)
        }
        catch (Exception e)
        {
            log.error(" error :: == "+ ExceptionUtils.getFullStackTrace(e))

        }
        return regionWiseSmsReportModels
    }

    private void sendNotification(HttpHeaders headers,
                                  List<RegionDetailsModel> regionDetails,
                                  List<RegionWiseReportModel> responseModels)
    {
        log.info("region wise sms sending begins ***************")

        RestTemplate restTemplate = new RestTemplate()

        regionDetails.forEach({ regionRecord ->
            log.info("sending message for region code: " + regionRecord.getRegionCode());
            RegionWiseReportModel data = responseModels.stream().filter({ model ->
                model.getRegion() ==  regionRecord.getRegionCode()}).findFirst().get()
            log.info("Region Name: " + (data!=null? data.getRegionName():null));
           // log.info("TotalC2sAmount: " + data!=null? data.getTotalC2sAmount():0);
            //log.info("TotalC2cAmount: " + data!=null? data.getTotalC2cAmount():0);
            if(data!=null) {
                HttpEntity<MultiValueMap<String, String>> request = prepareSmsNotificationRequestBody(data, regionRecord)
                try {
                    String returnStatus = restTemplate.postForObject(notificationManagerUrl, request,String.class);
                    log.info("SMS sent status" + returnStatus);
                }catch(Exception e){
                    log.error("Error while sending notification "+e.getMessage(),e);
                }

                log.info("Sending email ***************")

                Map<String, Object> fields = new ConcurrentHashMap();
                fields.put("notificationType", "EMAIL");
                fields.put("recipientId", regionRecord.getEmail());
                fields.put("senderId", "se.vms@seamless.se");
                fields.put("message", readEmailMessage(data));
                fields.put("referenceEventId", "eventId");
                Map<String, Object> emailProps = new HashMap<>();
                fields.put("EMAILPROPS", emailProps);
                Map req = new HashMap();
                req.put("eventTag","ADHOC_ALERT");
                req.put("fields",fields);
                def requestEmail = new HttpEntity<>(req, headers);
                try {
                    String returnStatus = restTemplate.postForObject(notificationManagerUrl, requestEmail,  String.class)
                    log.info("Email sent status" + returnStatus);
                }catch(Exception e){
                    log.error(e);
                }
            }else{
                log.info("###### There are not transaction available for C2S or C2C. Hence notification will not send");
            }
        })
        log.info("region wise notification sending ended ***************")
    }

    static def prepareSmsNotificationRequestBody(RegionWiseReportModel data, RegionDetailsModel record){

        Map<String, Object> fields = new ConcurrentHashMap();
        fields.put("notificationType","SMS");
        fields.put("recipientId", record.getMsisdn());
        fields.put("message", readReminderSmsBody(data));
        fields.put("senderid","9988776655");
        fields.put("referenceEventId","eventId");

        Map req = new HashMap();
        req.put("eventTag","ADHOC_ALERT");
        req.put("fields",fields);

        //log.info("Request Data: " + new JsonBuilder(req).toPrettyString());
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(req, headers);
        return request;
    }

    static def readReminderSmsBody(RegionWiseReportModel data){
        def f = new File('/opt/seamless/conf/bi-aggregator/scripts/region_wise_sms_report.ftl')
        def engine = new GStringTemplateEngine()
        def binding = [data : data ]
        def template = engine.createTemplate(f).make(binding)
        return template.toString()
    }

    static def readEmailMessage(RegionWiseReportModel regionModel){
        File f = new File('/opt/seamless/conf/bi-aggregator/scripts/region_wise_sms_report.ftl')
        def engine = new GStringTemplateEngine()
        def binding = [data : regionModel ]
        def template = engine.createTemplate(f).make(binding)
        return template.toString()
    }
}

class AreaWiseModel
{
    private  String region;
    private String area;
    private String amount;
    private String areaName;
    private String regionName;

    AreaWiseModel(String region, String area, String amount, String areaName, String regionName) {
        this.region = region
        this.area = area
        this.amount = amount
        this.regionName = regionName
        this.areaName = areaName
    }

    String getRegion() {
        return region
    }

    void setRegion(String region) {
        this.region = region
    }

    String getArea() {
        return area
    }

    void setArea(String area) {
        this.area = area
    }

    String getAmount() {
        return amount
    }

    void setAmount(String amount) {
        this.amount = amount
    }

    String getAreaName() {
        return areaName
    }

    void setAreaName(String areaName) {
        this.areaName = areaName
    }

    String getRegionName() {
        return regionName
    }

    void setRegionName(String regionName) {
        this.regionName = regionName
    }

    @Override
    public String toString() {
        return "AreaWiseModel{" +
                "region='" + region + '\'' +
                ", area='" + area + '\'' +
                ", amount=" + amount +
                ", areaName='" + areaName + '\'' +
                ", regionName='" + regionName + '\'' +
                '}';
    }
}

class RegionWiseReportModel
{
    private String region;
    private String totalC2cAmount;
    private String totalC2sAmount;
    private List<AreaWiseModel> c2cAreaWise;
    private List<AreaWiseModel> c2sAreaWise;
    private String date
    private String regionName;

    RegionWiseReportModel(String region, String totalC2cAmount, String totalC2sAmount, List<AreaWiseModel> c2cAreaWise,
                          List<AreaWiseModel> c2sAreaWise,String regionName) {
        this.region = region
        this.totalC2cAmount = totalC2cAmount
        this.totalC2sAmount = totalC2sAmount
        this.c2cAreaWise = c2cAreaWise
        this.c2sAreaWise = c2sAreaWise
        this.regionName = regionName
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        this.date = formatter.format(date);
    }

    String getRegion() {
        return region
    }

    void setRegion(String regionName) {
        this.region = regionName
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

    List<AreaWiseModel> getC2cAreaWise() {
        return c2cAreaWise
    }

    void setC2cAreaWise(List<AreaWiseModel> c2cAreaWise) {
        this.c2cAreaWise = c2cAreaWise
    }

    List<AreaWiseModel> getC2sAreaWise() {
        return c2sAreaWise
    }

    void setC2sAreaWise(List<AreaWiseModel> c2sAreaWise) {
        this.c2sAreaWise = c2sAreaWise
    }

    String getRegionName() {
        return regionName
    }

    void setRegionName(String regionName) {
        this.regionName = regionName
    }


    @Override
    public String toString() {
        return "RegionWiseReportModel{" +
                "region='" + region + '\'' +
                ", totalC2cAmount=" + totalC2cAmount +
                ", totalC2sAmount=" + totalC2sAmount +
                ", c2cAreaWise=" + c2cAreaWise +
                ", c2sAreaWise=" + c2sAreaWise +
                ", date='" + date + '\'' +
                ", regionName='" + regionName + '\'' +
                '}';
    }
}

class RegionDetailsModel
{

    String regionName
    String regionCode;
    String managerName
    String email
    String msisdn

    RegionDetailsModel(String regionCode,String regionName, String managerName, String email, String msisdn) {
        this.regionCode = regionCode
        this.regionName=regionName;
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

    String getRegionCode() {
        return regionCode
    }

    void setRegionCode(String regionCode) {
        this.regionCode = regionCode
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
