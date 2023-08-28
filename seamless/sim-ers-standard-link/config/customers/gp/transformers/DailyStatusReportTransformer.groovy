package com.seamless.ers.standardlink.transformers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seamless.ers.standardlink.config.StandardLinkConfig;
import com.seamless.ers.standardlink.model.common.ERSIOTransformer;
import com.seamless.ers.standardlink.model.common.ResultCode;
import com.seamless.ers.standardlink.model.common.constants.StandardLinkConstants;
import com.seamless.ers.standardlink.model.configs.ProviderConfigurations;
import com.seamless.ers.standardlink.model.exception.TransformerException;
import com.seamless.ers.standardlink.model.internal.StandardLinkResultCodes;
import com.seamless.ers.standardlink.utilities.SendSmsNotificationUtility;
import com.seamless.ers.standardlink.utilities.StandardLinkUtilities;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmPoint;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component
public class DailyStatusReportTransformer implements ERSIOTransformer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(DailyStatusReportTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Autowired
	private SendSmsNotificationUtility smsNotificationUtility;

	private Object smsReceiverMsisdn,language;

	@Override
	public Message<?> transformInboundRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("DailyStatusReportTransformer :: transformOutboundRequest");
		LOGGER.info("Forming request to BI to fetch the last transaction details for operation" + Constants.OPERATION);
		try
		{
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Map> systemToken = objectMapper.readValue(String.valueOf(message.getHeaders().get(StandardLinkConstants.Headers.SYSTEM_TOKEN)), Map.class);
			if (systemToken != null && systemToken.size() > 0)
			{
				Map<String, String> initiatorInfo = (Map) systemToken.get("context").get("initiator");
				language = initiatorInfo.get("language");
			}
			else
			{
				LOGGER.info("System token not found for DailyStatusReportTransformer");
			}

			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.COMMAND);
			if (command != null)
			{
				requestFields.putAll(command);
			}

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION).getFields();

			Map<String, Object> fields = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> dailyStatusRequest = Collections.synchronizedMap(new LinkedHashMap<>());

			if (!requestFields.get(Constants.Gp.MSISDN).toString().isEmpty())
			{
				smsReceiverMsisdn = requestFields.get(Constants.Gp.MSISDN);
				fields.put(Constants.Bi.TARGET_MSISDN, smsReceiverMsisdn);
				LOGGER.info("Trying to fetch the daily status report for MSISDN : " + requestFields.get(Constants.Gp.MSISDN));
			}
			else
			{
				fields.put(Constants.Bi.TARGET_MSISDN, requestFields.get(Constants.Gp.LOGINID));
				LOGGER.info("Trying to fetch the daily status report for LOGINID : " + requestFields.get(Constants.Gp.LOGINID));
			}
			fields.put(Constants.Bi.REPORT_NAME, String.valueOf(operationFields.getOrDefault(Constants.Bi.REPORT_NAME, Constants.Bi.SEARCH_DAILY_TRANSACTION)));
			LocalDateTime localDateTime = LocalDateTime.now();
			fields.put(Constants.Bi.FROM_DATE, localDateTime.with(LocalTime.MIDNIGHT).toString() + ":00.000Z");
			fields.put(Constants.Bi.CURRENT_DATE, localDateTime.with(LocalTime.MIDNIGHT).toString() + ":00.000Z");
			fields.put(Constants.Bi.TO_DATE, localDateTime.toString() + "Z");
			fields.put(Constants.Bi.TRANSACTION_COUNT, operationFields.getOrDefault(Constants.Bi.DEFAULT_SIZE, "2"));
			fields.put(Constants.Bi.PAGE, Integer.parseInt(String.valueOf(operationFields.get(Constants.Bi.PAGE))));
			dailyStatusRequest.put(Constants.Bi.REQUEST, fields);
			return MessageBuilder.withPayload(dailyStatusRequest).copyHeaders(message.getHeaders()).setHeaderIfAbsent(StandardLinkConstants.Headers.SYSTEM_TOKEN, message.getHeaders().get("system-token")).setHeaderIfAbsent(StandardLinkConstants.Headers.AUTHORIZATION, message.getHeaders().get("authorization")).setHeader(Constants.Gp.IS_REQUEST_TYPE_TEXT, requestFields.get(Constants.Gp.IS_REQUEST_TYPE_TEXT)).build();
		}
		catch (Exception e)
		{
			LOGGER.error("An error occurred during transformation", e.getMessage());
			throw new TransformerException(StandardLinkResultCodes.TRANSFORMATION_ERROR, "An error occurred during request transformation : " + e.getMessage());
		}
		finally
		{
			if (point != null)
			{
				point.collect();
			}
		}
	}

	@Override
	public Object transformOutboundResponse(Object outgoingResponse) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("DailyStatusReportTransformer :: transformOutboundResponse");

		try
		{
			Map<String, Object> response = new LinkedHashMap<>();
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION).getFields();

			response.put(Constants.Gp.GP_EXT_REQUEST_TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));
			response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));

			if (outgoingResponse instanceof CompletableFuture)
			{
				outgoingResponse = ((CompletableFuture<?>) outgoingResponse).get();
			}

			if (outgoingResponse instanceof Exception)
			{
				Exception exception = (Exception) outgoingResponse;
				response.put(Constants.Gp.GP_MESSAGE, StandardLinkUtilities.getRootCause(exception));
				response.put(Constants.Gp.GP_TXN_STATUS, StandardLinkResultCodes.INTERNAL_COMPONENT_ERROR);
				return response;
			}

			StringBuilder smsMessageBuilder;
			Map<String, Object> internalResponse = (LinkedHashMap<String, Object>) outgoingResponse;
			Map<String, Object> request = (Map<String, Object>) internalResponse.get(Constants.Gp.REQUEST_PAYLOAD);
			Map<String, Object> requestPayloadCommand = (Map<String, Object>) request.get(Constants.Gp.COMMAND);
			if (requestPayloadCommand != null)
			{
				request.putAll(requestPayloadCommand);
			}

			if (!internalResponse.isEmpty() && internalResponse.containsKey(Constants.Bi.RESULT_CODE))
			{
				Integer resultCodeValue = (Integer) internalResponse.get(Constants.Bi.RESULT_CODE);
				String defaultMessage = String.valueOf(internalResponse.get(Constants.Bi.RESULT_MESSAGE) != null ? internalResponse.get(Constants.Bi.RESULT_MESSAGE) : internalResponse.get(Constants.Bi.MESSAGE) != null ? internalResponse.get(Constants.Bi.MESSAGE) : internalResponse.get(Constants.Bi.RESULT_DESCRIPTION));
				if (resultCodeValue == StandardLinkResultCodes.SUCCESS)
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);

					response.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
					response.put(Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
				}
				else
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
					response.put(Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
				}
			}
			else
			{
				response.put(Constants.Gp.GP_MESSAGE, "Request has failed on server.");
				response.put(Constants.Gp.GP_TXN_STATUS, StandardLinkResultCodes.FAILED_ON_SERVER);
			}
			Map<String,String> map = Constants.getData(String.valueOf(language));

			List list = (List) internalResponse.get("list");
			if (Objects.nonNull(list) && list.size() > 0)
			{
				smsMessageBuilder = new StringBuilder(map.get(Constants.DEFAULT_MESSAGE));
				for (Object item : list)
				{
					LinkedHashMap listItem = (LinkedHashMap) item;
					if (listItem.containsValue(Constants.CREDIT_TRANSFERRED))
					{
						smsMessageBuilder.append(map.get(Constants.CREDIT_TRANSFERRED));
					}
					else if (listItem.containsValue(Constants.CREDIT_RECEIVED))
					{
						smsMessageBuilder.append(map.get(Constants.CREDIT_RECEIVED));
					}
					else if (listItem.containsValue(Constants.CREDIT_TRANSFERRED_CHILD))
					{
						smsMessageBuilder.append(map.get(Constants.CREDIT_TRANSFERRED_CHILD));
					}
					else if (listItem.containsValue(Constants.TOPUP_SELF_OR_CHILD))
					{
						smsMessageBuilder.append(map.get(Constants.TOPUP_SELF_OR_CHILD));
					}

					if(listItem.containsKey("TotalTransactionsDone")
							&& listItem.get("TotalTransactionsDone")!=null
							&& !StringUtils.isEmpty(listItem.get("TotalTransactionsDone").toString().trim())) {
						BigDecimal originalTotalTransactionDone = BigDecimal.valueOf(Double.valueOf(listItem.get("TotalTransactionsDone").toString().trim()));
						BigDecimal originalTotalTransactionDoneVal = originalTotalTransactionDone.setScale(0, BigDecimal.ROUND_HALF_UP);
						smsMessageBuilder.append(originalTotalTransactionDoneVal);
					}else{
						smsMessageBuilder.append("0.00");
					}
					smsMessageBuilder.append(":");
					if(listItem.containsKey("TotalTransferredAmount")
							&& listItem.get("TotalTransferredAmount")!=null
							&& !StringUtils.isEmpty(listItem.get("TotalTransferredAmount").toString().trim())) {
						BigDecimal originalTotalTransferredAmount = BigDecimal.valueOf(Double.valueOf(listItem.get("TotalTransferredAmount").toString().trim()));
						BigDecimal originalTotalTransferredAmountVal = originalTotalTransferredAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
						smsMessageBuilder.append(originalTotalTransferredAmountVal);
					}else{
						smsMessageBuilder.append("0.00");
					}
					smsMessageBuilder.append(" BDT,");
				}
				if(!StringUtils.isEmpty(smsMessageBuilder)){
					smsMessageBuilder.deleteCharAt(smsMessageBuilder.length() - 1);
				}
			}
			else
			{
				smsMessageBuilder = new StringBuilder(String.valueOf(operationFields.get("defaultMessage")));
			}
			smsNotificationUtility.sendSmsNotification("", String.valueOf(request.get(Constants.Gp.MSISDN)), String.valueOf(smsMessageBuilder));
			return response;
		}
		catch (Exception e)
		{
			LOGGER.error("An error occurred during transformation", e.getMessage());
			throw new TransformerException(StandardLinkResultCodes.TRANSFORMATION_ERROR, "An error occurred during response transformation : " + e.getMessage());
		}
		finally
		{
			if (point != null)
			{
				point.collect();
			}
		}
	}

	private static final class Constants
	{
		private static final String PROVIDER = "gp";
		private static final String OPERATION = "dailyStatus";
		private static final String DEFAULT_MESSAGE = "DEFAULT_MESSAGE";
		private static final String CREDIT_TRANSFERRED = "CREDIT_TRANSFERRED";
		private static final String CREDIT_RECEIVED = "CREDIT_RECEIVED";
		private static final String CREDIT_TRANSFERRED_CHILD = "CREDIT_TRANSFERRED_CHILD";
		private static final String TOPUP_SELF_OR_CHILD = "TOPUP_SELF_OR_CHILD";

		private static final class Gp
		{
			public static final String IS_REQUEST_TYPE_TEXT = "IS_REQUEST_TYPE_TEXT";
			private static final String TYPE = "TYPE";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String MSISDN = "MSISDN1";
			private static final String EXTREFNUM = "EXTREFNUM";
			private static final String GP_TXN_ID = "TXNID";
			private static final String GP_TXN_STATUS = "TXNSTATUS";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String GP_EXT_REQUEST_TYPE = "TYPE";
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String LOGINID = "LOGINID";
			private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
			private static final String COMMAND = "COMMAND";
			private static final String REQUEST_PAYLOAD = "requestPayload";
		}

		private static Map<String, String> getData(String language) {
			Map<String, String> responseMap = new HashMap<>();
			if (language.equalsIgnoreCase("en")) {
				responseMap.put(DEFAULT_MESSAGE, En.DEFAULT_MESSAGE);
				responseMap.put(CREDIT_RECEIVED, En.CREDIT_RECEIVED);
				responseMap.put(CREDIT_TRANSFERRED_CHILD, En.CREDIT_TRANSFERRED_CHILD);
				responseMap.put(CREDIT_TRANSFERRED, En.CREDIT_TRANSFERRED);
				responseMap.put(TOPUP_SELF_OR_CHILD, En.TOPUP_SELF_OR_CHILD);
			} else {
				responseMap.put(DEFAULT_MESSAGE, Bn.DEFAULT_MESSAGE);
				responseMap.put(CREDIT_RECEIVED, Bn.CREDIT_RECEIVED);
				responseMap.put(CREDIT_TRANSFERRED_CHILD, Bn.CREDIT_TRANSFERRED_CHILD);
				responseMap.put(CREDIT_TRANSFERRED, Bn.CREDIT_TRANSFERRED);
				responseMap.put(TOPUP_SELF_OR_CHILD, Bn.TOPUP_SELF_OR_CHILD);
			}
			return responseMap;
		}

		private static final class En
		{
			private static final String DEFAULT_MESSAGE = "Daily transfer product:";
			private static final String CREDIT_TRANSFERRED = "Total stock transferred:";
			private static final String CREDIT_RECEIVED = "Total stock received:";
			private static final String CREDIT_TRANSFERRED_CHILD = "Child transfer Out:";
			private static final String TOPUP_SELF_OR_CHILD = "Customer recharge:";
		}

		private static final class Bn
		{
			private static final String DEFAULT_MESSAGE = "দৈনিক স্থানান্তর পণ্য:";
			private static final String CREDIT_TRANSFERRED = "মোট স্টক স্থানান্তর:";
			private static final String CREDIT_RECEIVED = "প্রাপ্ত মোট স্টক:";
			private static final String CREDIT_TRANSFERRED_CHILD = "শিশু স্থানান্তর আউট:";
			private static final String TOPUP_SELF_OR_CHILD = "গ্রাহক রিচার্জ:";
		}

		private static final class Bi
		{
			private static final String DEFAULT_SIZE = "defaultSize";
			private static final String PAGE = "page";
			private static final String ERS_REFERENCE = "ersReference";
			private static final String RESULT_CODE = "resultCode";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String MESSAGE = "message";
			private static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String TARGET_MSISDN = "targetMsisdn";
			private static final String REPORT_NAME = "reportName";
			private static final String SEARCH_DAILY_TRANSACTION = "search_daily_transaction";
			private static final String NUMBER_OF_DAYS = "numberOfDays";
			private static final String FROM_DATE = "fromDate";
			private static final String TO_DATE = "toDate";
			private static final String TRANSACTION_COUNT = "size";
			private static final String REQUEST = "request";
			private static final String CURRENT_DATE="currentDate";
		}
	}
}

