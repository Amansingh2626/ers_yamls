package com.seamless.customer.bi.engine.scripts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seamless.customer.bi.engine.request.ReportRequest;
import com.seamless.customer.bi.engine.response.ReportResponse;
import com.seamless.customer.bi.engine.response.ResultCode;
import com.seamless.customer.bi.engine.service.IReportScriptBaseService;
import com.seamless.customer.bi.engine.service.IReportScriptService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.document.DocumentField
import org.elasticsearch.rest.RestStatus
import org.elasticsearch.search.SearchHit
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.ParsedMultiBucketAggregation;
import org.elasticsearch.search.aggregations.bucket.composite.ParsedComposite;
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.log4j.Logger;
import org.elasticsearch.client.core.CountResponse

import java.util.stream.Collectors


@Component("O2CTransferDetailsHierarchy")
public class O2CTransferDetailsHierarchy extends IReportScriptBaseService implements IReportScriptService {
    org.apache.log4j.Logger log = Logger.getLogger("O2CTransferDetailsHierarchy");
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static final SimpleDateFormat convertedFormate = new SimpleDateFormat("dd-MM-yyyy");


    private final String FOC_SALES_O2C_QUERY = "{\"elasticIndex\":{\"indexName\":\"data_lake_\",\"isDataWeeklyIndexed\":true},\"elasticQuery\":{\"query\":{\"bool\":{\"must\":[{\"terms\":{\"transactionProfile.keyword\":[\"SALE\",\"FOC\",\"O2C_WITHDRAW\"],\"boost\":1}},{\"term\":{\"seamlessResultStatus\":{\"value\":0}}},{\"term\":{\"batchId.keyword\":{\"value\":\"NA\"}}},{\"bool\":{\"should\":[{\"wildcard\":{\"RECEIVER_FIELD_regionName\":{\"value\":\"<:zone:>\"}}},{\"terms\":{\"RECEIVER_FIELD_regionName.keyword\":\"<-:zone:->\",\"boost\":1}},{\"wildcard\":{\"SENDER_FIELD_regionName\":{\"value\":\"<:zone:>\"}}},{\"terms\":{\"SENDER_FIELD_regionName.keyword\":\"<-:zone:->\",\"boost\":1}}]}},{\"bool\":{\"should\":[{\"wildcard\":{\"ReceiverResellerName\":{\"value\":\"<:resellerName:>\"}}},{\"terms\":{\"ReceiverResellerName.keyword\":\"<-:resellerName:->\",\"boost\":1}},{\"wildcard\":{\"SenderResellerName\":{\"value\":\"<:resellerName:>\"}}},{\"terms\":{\"SenderResellerName.keyword\":\"<-:resellerName:->\",\"boost\":1}}]}},{\"bool\":{\"should\":[{\"wildcard\":{\"RECEIVER_FIELD_domainCode\":{\"value\":\"<:Domain:>\"}}},{\"terms\":{\"RECEIVER_FIELD_domainCode.keyword\":\"<-:Domain:->\",\"boost\":1}},{\"wildcard\":{\"SENDER_FIELD_domainCode\":{\"value\":\"<:Domain:>\"}}},{\"terms\":{\"SENDER_FIELD_domainCode.keyword\":\"<-:Domain:->\",\"boost\":1}}]}},{\"bool\":{\"should\":[{\"wildcard\":{\"ReceiverResellerType\":{\"value\":\"<:domainType:>\"}}},{\"terms\":{\"ReceiverResellerType.keyword\":\"<-:domainType:->\",\"boost\":1}},{\"wildcard\":{\"SenderResellerType\":{\"value\":\"<:domainType:>\"}}},{\"terms\":{\"SenderResellerType.keyword\":\"<-:domainType:->\",\"boost\":1}}]}},{\"bool\":{\"should\":[{\"wildcard\":{\"SenderResellerPath.keyword\":\"<:resellerPath:>/*\"}},{\"wildcard\":{\"RECEIVER_RESELLER_PATH.keyword\":\"<:resellerPath:>/*\"}}]}},{\"range\":{\"timestamp\":{\"from\":\"<:fromDate:>\",\"to\":\"<:toDate:>\"}}}]}},\"script_fields\":{\"MSISDN\":{\"script\":{\"source\":\"params._source.containsKey('receiverMSISDN') && doc['receiverMSISDN.keyword'].value!='NA' ? doc['receiverMSISDN.keyword'].value.substring(3):params['default']\",\"params\":{\"default\":\"\"}}},\"Date Time\":{\"script\":{\"source\":\"doc['timestamp'].value.format(DateTimeFormatter.ofPattern('dd-MM-yyyy HH:mm:ss'))\"}},\"paymentInstrumentDate\":{\"script\":{\"source\":\"params._source.containsKey('paymentInstrumentDate') && doc['paymentInstrumentDate'].value!='' ? doc['paymentInstrumentDate'].value.format(DateTimeFormatter.ofPattern('dd-MM-yyyy')) : '' \"}}},\"_source\":{\"includes\":[\"SenderResellerName\",\"ReceiverResellerName\",\"RECEIVER_FIELD_domainCode\",\"MSISDN\",\"ersReference\",\"transactionProfile\",\"TransactionType\",\"Date Time\",\"Channel\",\"instrumentType\",\"paymentInstrumentDate\",\"paymentInstrumentNumber\",\"ClientReference\",\"extTxnDate\",\"transactionAmount\",\"RECEIVER_COMMISSION_VALUE\",\"transactionAmount\",\"RECEIVER_PAYABLE_AMOUNT\",\"RECEIVER_COMMISSION_AMOUNT\",\"UPLIFT_COMMISSION_AMOUNT\",\"UPLIFT_COMMISSION_CURRENCY\"],\"excludes\":[]}}}";

    @Autowired
    ObjectMapper objectMapper;

    @Override
    long getRowCount(ReportRequest reportRequest) {
        CountResponse countResponse = executeElasticsearchQueryForCount(reportRequest, objectMapper, restHighLevelClient, FOC_SALES_O2C_QUERY);
        log.debug("countResponse::::::::"+countResponse)
        if (Objects.nonNull(countResponse) && countResponse.getCount() != 0)
            return countResponse.getCount();
        else
            return 0L;
    }

    @Override
    public ReportResponse getAllRecords(ReportRequest reportRequest) {
        log.info("O2CtransferDetails method called for: [" + reportRequest.getReportData().getReportName() + "]");
        // Custom logic to get the required result-data from elastic search
        ReportResponse reportResponse = null;
        Map<String, List<Map<String, Object>>> transactionsMap = null;
        try {
            transactionsMap = FetchO2CTransferDetails(reportRequest);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        reportResponse = getResult(transactionsMap, reportRequest);
        return reportResponse;

    }

    private ReportResponse getResult(Map<String, List<Map<String, Object>>> transactionMap, ReportRequest reportRequest) {
        ReportResponse reportResponse = new ReportResponse();
        long recordCount = transactionMap.size();
        List<Map<String, Object>> responseList = transactionMap.values().stream().flatMap({ list -> list.stream() })
                .collect(Collectors.toList())
        if (reportRequest.getRawRequest().containsKey("totalRecordsCount") && reportRequest.getRawRequest().get("totalRecordsCount") != null) {
            reportResponse.setTotalRecordCount(Long.parseLong(reportRequest.getRawRequest().get("totalRecordsCount").toString()));
        } else {
            reportResponse.setTotalRecordCount(0);
        }
        reportResponse.setList(responseList);
        reportResponse.setResultCode(ResultCode.SUCCESS.getResultCode());
        reportResponse.setResultDescription(ResultCode.SUCCESS.name());
        log.debug("Response for O2C Transfer Details is " + reportResponse.toString());
        return reportResponse;
    }

    public Map<String, List<Map<String, Object>>> FetchO2CTransferDetails(ReportRequest reportRequest) throws NoSuchFieldException {

        final int scrollSize = 6000;
        String flag = "SALE,FOC"
        // Custom logic to get the required result-data from elastic search
        Set<SearchResponse> searchResponses = executeElasticSearchQuery(reportRequest, objectMapper, restHighLevelClient, FOC_SALES_O2C_QUERY, scrollSize);

        Map<String, List<Map<String, Object>>> map = new HashMap<>();
        int index = 0;
        for (SearchResponse searchResponse:searchResponses){
            SearchHits searchHits = searchResponses.getAt(index).getHits()
            for (SearchHit searchHit : searchHits.getHits()) {
                if (searchHit != null) {
                    Map<String, Object> searchHitMap = searchHit.getSourceAsMap()
                    Map<String, DocumentField> searchFiledMap = searchHit.getFields()
                    if (searchHitMap != null) {
                        //GET ALL THE DETAILS IN VARIABLE
                        String transactionProfile = searchHitMap.get("transactionProfile")
                        String ersReference = searchHitMap.get("ersReference")
                        String senderResellerId = searchHitMap.get("SenderResellerName")
                        String receiverResellerId = searchHitMap.get("ReceiverResellerName")
                        String receiverFieldDomainCode = searchHitMap.get("RECEIVER_FIELD_domainCode")
                        String receiverMSISDN = searchFiledMap.get("MSISDN").value
                        String transactionType = searchHitMap.get("TransactionType")
                        String channel = searchHitMap.get("Channel")
                        String transaction_time = searchFiledMap.get("Date Time").value
                        Double transactionAmount = Double.valueOf(searchHitMap.get("transactionAmount"))
                        String externalTxnNumber = searchHitMap.get("ClientReference")
                        Double receiver_amount = Double.valueOf(searchHitMap.get("RECEIVER_COMMISSION_AMOUNT"))
                        Double uplift_amount = Double.valueOf(searchHitMap.get("UPLIFT_COMMISSION_AMOUNT"))
                        Double receiverCommissionAmount = (receiver_amount + uplift_amount)
                        Double receiverPayableAmount = Double.valueOf(searchHitMap.get("RECEIVER_PAYABLE_AMOUNT"))
                        String instrumentType = searchHitMap.get("instrumentType")
                        String paymentInstrumentDate = "";
                        if (searchFiledMap.containsKey("paymentInstrumentDate")) {
                            paymentInstrumentDate = searchFiledMap.get("paymentInstrumentDate").value;
                        }
                        String paymentInstrumentNumber = "";
                        if (searchHitMap.containsKey("paymentInstrumentNumber")) {
                            paymentInstrumentNumber = searchHitMap.get("paymentInstrumentNumber");
                        }
                        String extTxnDate = "";
                        if (searchHitMap.containsKey("extTxnDate")) {
                            try {
                                extTxnDate = convertedFormate.format(simpleDateFormat.parse(searchHitMap.get("extTxnDate")));
                            }
                            catch (Exception pe)
                            {
                                log.error("Exception while parse date--------------------");
                                log.error(ExceptionUtils.getStackTrace(pe));
                                extTxnDate = searchHitMap.get("extTxnDate");
                            }
                        }

                        //PROCESS TO MAKE A RESPONSE
                        def transactionAmountT = 0d;
                        def receiverCommissionValueT = 0d;
                        def receiverPayableAmountT = 0d;
                        def transactionAmountW = 0d;
                        def receiverCommissionValueW = 0d;
                        def receiverPayableAmountW = 0d;
                        def reseller = "";

                        if (flag.contains(transactionProfile)) {
                            reseller = receiverResellerId
                        } else {
                            reseller = senderResellerId
                        }


                        if (map.get(reseller) != null) {
                            List<Map<String, Object>> list = map.get(reseller);
                            list.add(0, toMap(senderResellerId, receiverResellerId, receiverFieldDomainCode, receiverMSISDN, ersReference,
                                    transactionProfile, transactionType, transaction_time, channel, instrumentType,
                                    paymentInstrumentDate, paymentInstrumentNumber, externalTxnNumber, extTxnDate,
                                    transactionAmount, receiverCommissionAmount, receiverPayableAmount))
                            int i = list.size();

                            Map<String, Object> getTransferMap = null;
                            if (flag.contains(transactionProfile)) {
                                getTransferMap = list.get(i - 2);
                                transactionAmountT = getTransferMap.get("Requested quantity") + transactionAmount;
                                receiverCommissionValueT = getTransferMap.get("Comm.") + receiverCommissionAmount;
                                receiverPayableAmountT = getTransferMap.get("Net payable amount") + receiverPayableAmount;
                                list.set(i - 2, toMap(
                                        "", reseller, "TOTAL TRANSFER", "", "", "", "", "", "", "", "", "", "", "",
                                        transactionAmountT, receiverCommissionValueT, receiverPayableAmountT
                                ))
                            } else {
                                getTransferMap = list.get(i - 1);
                                transactionAmountW = getTransferMap.get("Requested quantity") + transactionAmount;
                                receiverCommissionValueW = getTransferMap.get("Comm.") + receiverCommissionAmount;
                                receiverPayableAmountW = getTransferMap.get("Net payable amount") + receiverPayableAmount;
                                list.set(i - 1, toMap(
                                        "", reseller, "TOTAL WITHDRAW", "", "", "", "", "", "", "", "", "", "", "",
                                        transactionAmountW, receiverCommissionValueW, receiverPayableAmountW
                                ))
                            }
                            map.put(reseller, list)
                        } else {
                            List<Map<String, Object>> list = new LinkedList<>();
                            list.add(toMap(senderResellerId, receiverResellerId, receiverFieldDomainCode, receiverMSISDN, ersReference,
                                    transactionProfile, transactionType, transaction_time, channel, instrumentType,
                                    paymentInstrumentDate, paymentInstrumentNumber, externalTxnNumber, extTxnDate,
                                    transactionAmount, receiverCommissionAmount, receiverPayableAmount))

                            if (flag.contains(transactionProfile)) {
                                transactionAmountT = transactionAmount;
                                receiverCommissionValueT = receiverCommissionAmount;
                                receiverPayableAmountT = receiverPayableAmount;
                            } else {
                                transactionAmountW = transactionAmount;
                                receiverCommissionValueW = receiverCommissionAmount;
                                receiverPayableAmountW = receiverPayableAmount;
                            }
                            list.add(toMap(
                                    "", reseller, "TOTAL TRANSFER", "", "", "", "", "", "", "", "", "", "", "",
                                    transactionAmountT, receiverCommissionValueT, receiverPayableAmountT
                            ))
                            list.add(toMap(
                                    "", reseller, "TOTAL WITHDRAW", "", "", "", "", "", "", "", "", "", "", "",
                                    transactionAmountW, receiverCommissionValueW, receiverPayableAmountW
                            ))
                            map.put(reseller, list)
                        }
                    }
                }
            }
            index++;
        }
        return map;

    }

    public Map<String, Object> toMap(String senderResellerId, String receiverResellerId, String RECEIVER_FIELD_DomainCode, String receiverMSISDN, String ersReference,
                                     String TRANSACTION_PROFILE, String transactionType, String transaction_time, String channel, String instrumentType, String paymentInstrumentDate,
                                     String paymentInstrumentNumber, String externalTxnNumber, String extTxnDate, Double transactionAmount, Double RECEIVER_COMMISSION_VALUE, Double RECEIVER_PAYABLE_AMOUNT) {
        Map<String, Object> responseMap = new LinkedHashMap<>();
        responseMap.put("Sender Reseller", senderResellerId);
        responseMap.put("Receiver reseller", receiverResellerId);
        responseMap.put("Domain", RECEIVER_FIELD_DomainCode);
        responseMap.put("MSISDN", receiverMSISDN);
        responseMap.put("ERS reference", ersReference);
        responseMap.put("Trf. Category", TRANSACTION_PROFILE);
        responseMap.put("Trf. sub-type", transactionType);
        responseMap.put("Date Time", transaction_time);
        responseMap.put("Channel", channel);
        responseMap.put("Instrument Type", instrumentType);
        responseMap.put("Pay instrument date", paymentInstrumentDate);
        responseMap.put("Pay instrument no.", paymentInstrumentNumber);
        responseMap.put("External Trf. No.", externalTxnNumber);
        responseMap.put("External Trf. Date", extTxnDate);
        responseMap.put("Requested quantity", new BigDecimal(transactionAmount).setScale(2, BigDecimal.ROUND_UP));
        responseMap.put("Comm.", new BigDecimal(RECEIVER_COMMISSION_VALUE).setScale(2, BigDecimal.ROUND_UP));
        responseMap.put("Receiver quantity", new BigDecimal(transactionAmount).setScale(2, BigDecimal.ROUND_UP));
        responseMap.put("Net payable amount", new BigDecimal(RECEIVER_PAYABLE_AMOUNT).setScale(2, BigDecimal.ROUND_UP));
        return responseMap
    }

}

