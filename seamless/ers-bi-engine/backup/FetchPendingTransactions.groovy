package com.seamless.customer.bi.engine.scripts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seamless.common.StringUtils;
import com.seamless.customer.bi.engine.request.ReportRequest;
import com.seamless.customer.bi.engine.response.ReportResponse;
import com.seamless.customer.bi.engine.response.ResultCode;
import com.seamless.customer.bi.engine.service.IReportScriptBaseService;
import com.seamless.customer.bi.engine.service.IReportScriptService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.document.DocumentField
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
import org.elasticsearch.client.core.CountResponse
import java.text.DecimalFormat;



@Component("FetchPendingTransactions")
public class FetchPendingTransactions extends IReportScriptBaseService implements IReportScriptService {
    Logger log=Logger.getLogger("FetchPendingTransactions");
    @Autowired
    private RestHighLevelClient restHighLevelClient;

//    private final String query = "{\"elasticIndex\":{\"indexName\":\"data_lake_\",\"isDataWeeklyIndexed\":true},\"elasticQuery\":{\"query\":{\"bool\":{\"must\":[{\"bool\":{\"must\":[{\"wildcard\":{\"NextLevelApprovals.keyword\":{\"value\":\"<:role:>\"}}},{\"terms\":{\"productSKU.keyword\":\"<-:productSKU:->\",\"boost\":1}},{\"wildcard\":{\"awaitingCustomerApproval.keyword\":{\"value\":\"true\"}}},{\"match\":{\"SenderResellerPath.keyword\":\"operator\"}}]}},{\"range\":{\"timestamp\":{\"from\":\"<:fromDate:>\",\"to\":\"<:toDate:>\"}}}]}},\"sort\":[{\"@timestamp\":{\"order\":\"desc\"}}],\"_source\":{\"includes\":[\"ersReference\",\"productSKU\",\"receiverMSISDN\",\"receiverResellerId\",\"resultCode\",\"resultMessage\",\"senderMSISDN\",\"senderResellerId\",\"timestamp\",\"transactionAmount\",\"transactionProfile\",\"actionDoneBy\",\"state\"]},\"size\":\"<:size:>\"}}";

private final String query = "{\"elasticIndex\":{\"indexName\":\"data_lake_\",\"isDataWeeklyIndexed\":true},\"elasticQuery\":{\"query\":{\"bool\":{\"must\":[{\"bool\":{\"must\":[{\"wildcard\":{\"NextLevelApprovals.keyword\":{\"value\":\"<:role:>\"}}},{\"terms\":{\"productSKU.keyword\":\"<-:productSKU:->\",\"boost\":1}},{\"wildcard\":{\"awaitingCustomerApproval.keyword\":{\"value\":\"true\"}}},{\"match\":{\"SenderResellerPath.keyword\":\"operator\"}}]}},{\"range\":{\"timestamp\":{\"from\":\"<:fromDate:>\",\"to\":\"<:toDate:>\"}}}]}},\"script_fields\":{\"NetPaybleAmount\":{\"script\":{\"source\":\"doc['transactionAmount'].value - doc['UPLIFT_COMMISSION_AMOUNT'].value\"}}},\"sort\":[{\"@timestamp\":{\"order\":\"desc\"}}],\"_source\":{\"includes\":[\"ersReference\",\"productSKU\",\"receiverMSISDN\",\"receiverResellerId\",\"ReceiverResellerName\",\"resultCode\",\"resultMessage\",\"senderMSISDN\",\"senderResellerId\",\"timestamp\",\"transactionAmount\",\"transactionProfile\",\"actionDoneBy\",\"state\"]},\"size\":\"<:size:>\"}}";

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @Autowired
    ObjectMapper objectMapper;

    @Override
    long getRowCount(ReportRequest reportRequest)
    {
        CountResponse countResponse = executeElasticsearchQueryForCount(reportRequest, objectMapper, restHighLevelClient, query);

        if (Objects.nonNull(countResponse) && countResponse.getCount() !=0)
            return countResponse.getCount();
        else
            return 0L;
    }

    @Override
    public ReportResponse getAllRecords(ReportRequest reportRequest) {
        log.info("FetchPendingTransactions method called for: [" + reportRequest.getReportData().getReportName() + "]");
        // Custom logic to get the required result-data from elastic search
        ReportResponse reportResponse=null;
        Map<String, Map<String,Object>> transactionsMap=fetchPendingTransactionsByUserRole(reportRequest);
        reportResponse=getResult(transactionsMap,reportRequest);
        return reportResponse;

    }

    private ReportResponse getResult(Map<String,Map<String,Object>> transactionMap,ReportRequest reportRequest)
    {
        log.info("Started forming response for fetch pending transactions");
        ReportResponse reportResponse = new ReportResponse();
        List<Map<String, Object>> responseList = new ArrayList<>();
        long recordCount =transactionMap.size();
        long count=0;
        for (Map.Entry<String, Map<String, Object>> entry : transactionMap.entrySet())
        {
            count++;
            Map<String, Object> transaction=entry.getValue();
            responseList.add(transaction);
            if(count==recordCount)
                break;
        }
        if(reportRequest.getRawRequest().containsKey("totalRecordsCount")&&reportRequest.getRawRequest().get("totalRecordsCount")!=null) {
            reportResponse.setTotalRecordCount(Long.parseLong(reportRequest.getRawRequest().get("totalRecordsCount").toString()));
        }
        else {
            reportResponse.setTotalRecordCount(0);
        }
        reportResponse.setList(responseList);
        reportResponse.setResultCode(ResultCode.SUCCESS.getResultCode());
        reportResponse.setResultDescription(ResultCode.SUCCESS.name());
        log.info("Response for fetch pending transactions is "+reportResponse.toString());
        return reportResponse;
    }
    public Map<String, Map<String,Object>> fetchPendingTransactionsByUserRole(ReportRequest reportRequest)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        if(reportRequest.getRawRequest().get("toDate")==null||StringUtils.isEmpty(reportRequest.getRawRequest().get("toDate").toString()))
        	{
        		String toDate= simpleDateFormat.format(new Date());
        		reportRequest.getRawRequest().put("toDate", toDate);
        	}
        //Define scroll size : Default value is 6000
        final int scrollSize = 6000;

        Set<SearchResponse> searchResponses = executeElasticSearchQuery(reportRequest, objectMapper, restHighLevelClient, query, scrollSize);
        List<SearchHits> searchHits=null;
        if(searchResponses!=null&&!searchResponses.isEmpty()) {
            searchHits = getSearchHits(searchResponses);
        }
        // Custom logic to get the required result-data from elastic search
        Map<String, Map<String,Object>> transactionMap= new LinkedHashMap<>();
        if (searchHits!=null&& !searchHits.isEmpty()) {
            reportRequest.getRawRequest().put("totalRecordsCount", searchHits.get(0).getTotalHits().value);
            log.info("Fetched " + searchHits.get(0).getHits().length + " number of transaction for the role id " + reportRequest.getRawRequest().get("role"));
            for (int i = 0; i < searchHits.get(0).getHits().length; i++) {
                String netPaybleAmount = "";
                Map<String, Object> fieldsMap = searchHits.get(0).getHits()[i].getFields();
                Map<String,Object> responseMap = searchHits.get(0).getAt(i).getSourceAsMap();
                if (fieldsMap.containsKey("NetPaybleAmount") && Objects.nonNull(fieldsMap.get("NetPaybleAmount"))){
                   netPaybleAmount =  ((DocumentField)fieldsMap.get("NetPaybleAmount")).getValue();
                   String netAmt =  new DecimalFormat("0.00").format(Double.valueOf(netPaybleAmount));
                   responseMap.put("NetPaybleAmount",netAmt);
                }
                transactionMap.put((String) searchHits.get(0).getAt(i).getSourceAsMap().get("ersReference"), responseMap);
            }
        }
        return transactionMap;
    }
}
