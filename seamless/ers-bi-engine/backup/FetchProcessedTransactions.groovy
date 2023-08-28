package com.seamless.customer.bi.engine.scripts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seamless.customer.bi.engine.request.ReportRequest;
import com.seamless.customer.bi.engine.response.ReportResponse;
import com.seamless.customer.bi.engine.response.ResultCode;
import com.seamless.customer.bi.engine.service.IReportScriptBaseService;
import com.seamless.customer.bi.engine.service.IReportScriptService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.elasticsearch.client.core.CountResponse
import com.seamless.common.StringUtils


import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
import java.text.DecimalFormat;


@Component("FetchProcessedTransactions")
public class FetchProcessedTransactions extends IReportScriptBaseService implements IReportScriptService {
    Logger log=Logger.getLogger("FetchProcessedTransactions");
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private final String query = "{\"elasticIndex\":{\"indexName\":\"data_lake_\",\"isDataWeeklyIndexed\":true},\"elasticQuery\":{\"query\":{\"bool\":{\"must\":[{\"bool\":{\"must\":[{\"wildcard\":{\"actionDoneBy.keyword\":{\"value\":\"<:userId:>\"}}},{\"terms\":{\"productSKU.keyword\":\"<-:productSKU:->\",\"boost\":1}},{\"terms\":{\"resultCode.keyword\":[\"0\",\"4\",\"3\"],\"boost\":1}}]}},{\"range\":{\"timestamp\":{\"from\":\"<:fromDate:>\",\"to\":\"<:toDate:>\"}}}]}},\"script_fields\":{\"NetPaybleAmount\":{\"script\":{\"source\":\"doc['transactionAmount'].value - doc['UPLIFT_COMMISSION_AMOUNT'].value\"}}},\"sort\":[{\"@timestamp\":{\"order\":\"desc\"}}],\"_source\":{\"includes\":[\"ersReference\",\"productSKU\",\"receiverMSISDN\",\"receiverResellerId\",\"ReceiverResellerName\",\"resultCode\",\"resultMessage\",\"senderMSISDN\",\"senderResellerId\",\"timestamp\",\"transactionAmount\",\"transactionProfile\",\"actionDoneBy\",\"state\"]},\"size\":\"<:size:>\"}}";


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
        log.info("FetchProcessedTransactions method called for: [" + reportRequest.getReportData().getReportName() + "]");
        // Custom logic to get the required result-data from elastic search
        ReportResponse reportResponse=null;
        Map<String, Map<String,Object>> transactionsMap=fetchProcessedTransactions(reportRequest);
        reportResponse=getResult(transactionsMap,reportRequest);
        return reportResponse;

    }

    private ReportResponse getResult(Map<String,Map<String,Object>> transactionMap,ReportRequest reportRequest)
    {
        log.info("Started forming response for fetch processed transactions");
        ReportResponse reportResponse = new ReportResponse();
        List<Map<String, Object>> responseList = new ArrayList<>();
        for (Map.Entry<String, Map<String, Object>> entry : transactionMap.entrySet())
        {
            Map<String, Object> transaction=entry.getValue();
            responseList.add(transaction);
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
        log.info("Response for fetch processed transactions is "+reportResponse.toString());
        return reportResponse;
    }

    public Map<String, Map<String,Object>> fetchProcessedTransactions(ReportRequest reportRequest)
    {
        //Define scroll size : Default value is 6000
        final int scrollSize = 6000;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        if(reportRequest.getRawRequest().get("toDate")==null||StringUtils.isEmpty(reportRequest.getRawRequest().get("toDate").toString()))
        		{
        			String toDate= simpleDateFormat.format(new Date());
        			reportRequest.getRawRequest().put("toDate", toDate);
        		}
        Set<SearchResponse> searchResponses = executeElasticSearchQuery(reportRequest, objectMapper, restHighLevelClient, query, scrollSize);
        List<SearchHits> searchHits=null;
        if(searchResponses!=null&&!searchResponses.isEmpty()) {
           searchHits = getSearchHits(searchResponses);
        }
        // Custom logic to get the required result-data from elastic search
        Map<String, Map<String,Object>> transactionMap= new HashMap<>();
        if (searchHits!=null&&!searchHits.isEmpty()) {
            //Actual count of total records found
            reportRequest.getRawRequest().put("totalRecordsCount", searchHits.get(0).getTotalHits().value);
            log.info("Fetched " + searchHits.get(0).getHits().length + " number of approved transactions by user id " + reportRequest.getRawRequest().get("userId"));
            for (int i = 0; i < searchHits.get(0).getHits().length; i++) {
                String netPaybleAmount = "";
                Map<String, Object> fieldsMap = searchHits.get(0).getHits()[i].getFields();
                Map<String,Object> responseMap = searchHits.get(0).getAt(i).getSourceAsMap();
                if (fieldsMap.containsKey("NetPaybleAmount") && Objects.nonNull(fieldsMap.get("NetPaybleAmount"))){
                    netPaybleAmount =  ((DocumentField)fieldsMap.get("NetPaybleAmount")).getValue();
                    String netAmt =  new DecimalFormat("0.00").format(Double.valueOf(netPaybleAmount));
                    responseMap.put("NetPaybleAmount",netAmt);
                }
                transactionMap.put((String) searchHits.get(0).getAt(i).getSourceAsMap().get("ersReference"), searchHits.get(0).getAt(i).getSourceAsMap());
            }

        }
        return transactionMap;
    }
}

