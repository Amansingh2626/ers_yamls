package com.seamless.customer.bi.engine.scripts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seamless.customer.bi.engine.request.ReportRequest;
import com.seamless.customer.bi.engine.response.ReportResponse;
import com.seamless.customer.bi.engine.response.ResultCode;
import com.seamless.customer.bi.engine.service.IReportScriptBaseService;
import com.seamless.customer.bi.engine.service.IReportScriptService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.ParsedMultiBucketAggregation;
import org.elasticsearch.search.aggregations.bucket.composite.ParsedComposite;
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.log4j.Logger;
import org.elasticsearch.client.core.CountResponse



@Component("C2STransferSummary")
public class C2STransferSummary extends IReportScriptBaseService implements IReportScriptService {
	org.apache.log4j.Logger log= Logger.getLogger("C2STransferSummary");
	@Autowired
	private RestHighLevelClient restHighLevelClient;

	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	private final String query = "{\"elasticIndex\":{\"indexName\":\"data_lake_\",\"isDataWeeklyIndexed\":true},\"elasticQuery\":{\"query\":{\"bool\":{\"must\":[{\"match\":{\"resultCode\":\"0\"}},{\"term\":{\"TransactionType.keyword\":{\"value\":\"TOPUP\"}}},{\"bool\":{\"should\":[{\"wildcard\":{\"SENDER_FIELD_regionName\":{\"value\":\"<:zone:>\"}}},{\"terms\":{\"SENDER_FIELD_regionName.keyword\":\"<-:zone:->\",\"boost\":1}},{\"wildcard\":{\"RECEIVER_FIELD_regionName\":{\"value\":\"<:zone:>\"}}},{\"terms\":{\"RECEIVER_FIELD_regionName.keyword\":\"<-:zone:->\",\"boost\":1}}]}},{\"bool\":{\"should\":[{\"wildcard\":{\"SENDER_FIELD_domainCode\":{\"value\":\"<:Domain:>\"}}},{\"terms\":{\"SENDER_FIELD_domainCode.keyword\":\"<-:Domain:->\",\"boost\":1}}]}},{\"bool\":{\"should\":[{\"wildcard\":{\"SenderResellerType\":{\"value\":\"<:domainType:>\"}}},{\"terms\":{\"SenderResellerType.keyword\":\"<-:domainType:->\",\"boost\":1}}]}},{\"bool\":{\"should\":[{\"wildcard\":{\"transactionProfile\":{\"value\":\"<:transactionType:>\"}}},{\"terms\":{\"transactionProfile.keyword\":\"<-:transactionType:->\",\"boost\":1}}]}},{\"range\":{\"timestamp\":{\"from\":\"<:fromDate:>\",\"to\":\"<:toDate:>\"}}}]}},\"_source\":false,\"stored_fields\":\"_none_\",\"aggregations\":{\"C2STransferSummary\":{\"composite\":{\"size\":1000,\"sources\":[{\"SenderResellerName\":{\"terms\":{\"field\":\"SenderResellerName.keyword\"}}},{\"senderMSISDN\":{\"terms\":{\"field\":\"senderMSISDN.keyword\"}}},{\"SENDER_FIELD_externalCode\":{\"terms\":{\"field\":\"SENDER_FIELD_externalCode.keyword\"}}},{\"SenderResellerType\":{\"terms\":{\"field\":\"senderResellerTypeName.keyword\"}}},{\"sender_parentId\":{\"terms\":{\"field\":\"senderResellerParentName.keyword\"}}},{\"SENDER_FIELD_parentMsisdn\":{\"terms\":{\"field\":\"SENDER_FIELD_parentMsisdn.keyword\"}}},{\"sender_ownerId\":{\"terms\":{\"field\":\"SENDER_FIELD_ownerName.keyword\"}}},{\"SENDER_FIELD_ownerMsisdn\":{\"terms\":{\"field\":\"SENDER_FIELD_ownerMsisdn.keyword\"}}},{\"thanaCode\":{\"terms\":{\"field\":\"SENDER_FIELD_thanaName.keyword\"}}},{\"productCategory\":{\"terms\":{\"field\":\"serviceType.keyword\"}}}]},\"aggregations\":{\"txnAmt\":{\"sum\":{\"field\":\"transactionAmount\"}}}}}}}";

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
		log.info("C2STransferSummary method called for: [" + reportRequest.getReportData().getReportName() + "]");
		// Custom logic to get the required result-data from elastic search
		ReportResponse reportResponse=null;
		Map<String, Map<String,Object>> transactionsMap= null;
		try {
			transactionsMap = FetchC2STransferSummary(reportRequest);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		reportResponse=getResult(transactionsMap,reportRequest);
		return reportResponse;

	}

	private ReportResponse getResult(Map<String,Map<String,Object>> transactionMap,ReportRequest reportRequest)
	{
		log.info("Started forming response for C2S Transfer Summary");
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
		log.debug("Response for C2S Transfer Summary is "+reportResponse.toString());
		return reportResponse;
	}

	public Map<String, Map<String,Object>> FetchC2STransferSummary(ReportRequest reportRequest) throws NoSuchFieldException {


		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
		String toDate = simpleDateFormat.format(new Date());
		reportRequest.getRawRequest().put("toDate", toDate);
		final int scrollSize = 6000;

		// Custom logic to get the required result-data from elastic search
		Map<String, Map<String, Object>> transactionMap = new LinkedHashMap<>();
		Set<SearchResponse> searchResponses = executeElasticSearchQuery(reportRequest, objectMapper, restHighLevelClient, query, scrollSize);
		List<Aggregations> aggregations = getAggregations(searchResponses);
		ParsedComposite parsedComposite= (ParsedComposite) aggregations.get(0).asMap().get("C2STransferSummary");
		for (ParsedMultiBucketAggregation.ParsedBucket bucket : parsedComposite.getBuckets())
		{
			LinkedHashMap<String,String> bucketKeyValueMap= (LinkedHashMap<String, String>) bucket.getKey();
			Map<String, Aggregation> aggregationMap = bucket.getAggregations().asMap();
			Aggregation totalTransactionAmountAggregation = aggregationMap.get("txnAmt");
			ParsedSum transactionAmountSum = (ParsedSum) totalTransactionAmountAggregation;

			String key=bucket.getKeyAsString();
			Map<String,Object> bucketData=new LinkedHashMap<>();
			bucketData.put("Reseller name",bucketKeyValueMap.get("SenderResellerName"));
			bucketData.put("MSISDN",bucketKeyValueMap.get("senderMSISDN") != "NA" ? bucketKeyValueMap.get("senderMSISDN").substring(3) : "NA");
			bucketData.put("DMS code",bucketKeyValueMap.get("SENDER_FIELD_externalCode"));
			bucketData.put("reseller type",bucketKeyValueMap.get("SenderResellerType"));
			bucketData.put("Parent name",bucketKeyValueMap.get("sender_parentId"));
			bucketData.put("Parent MSISDN",bucketKeyValueMap.get("SENDER_FIELD_parentMsisdn") != "NA" ? bucketKeyValueMap.get("SENDER_FIELD_parentMsisdn").substring(3) : "NA");
			bucketData.put("Owner name",bucketKeyValueMap.get("sender_ownerId"));
			bucketData.put("Owner MSISDN",bucketKeyValueMap.get("SENDER_FIELD_ownerMsisdn") != "NA" ? bucketKeyValueMap.get("SENDER_FIELD_ownerMsisdn").substring(3) : "NA");
			bucketData.put("Geographical domain",bucketKeyValueMap.get("thanaCode"));
			bucketData.put("Sub Service",bucketKeyValueMap.get("productCategory"));
			bucketData.put("Txn count",bucket.getDocCount());
                        bucketData.put("Tx sum amount",new BigDecimal(transactionAmountSum.value()).setScale(2, BigDecimal.ROUND_UP));
			bucketData.put("Process fees","0");
                        bucketData.put("Receiver credit amt",new BigDecimal(transactionAmountSum.value()).setScale(2, BigDecimal.ROUND_UP));
			transactionMap.put(key,bucketData);
		}
		return transactionMap;

	}
}
