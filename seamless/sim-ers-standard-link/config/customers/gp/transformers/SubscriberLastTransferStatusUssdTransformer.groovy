package com.seamless.ers.standardlink.transformers

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component
public class SubscriberLastTransferStatusUssdTransformer implements ERSIOTransformer
{

	private static final Logger LOGGER = LoggerFactory.getLogger(SubscriberLastTransferStatusUssdTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);
	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Autowired
	SendSmsNotificationUtility smsNotificationUtility;

	Object language;

	/**
	 * Transforms outgoing request to server known request
	 *
	 * @param message
	 * @return
	 * @throws TransformerException
	 */
	@Override
	public Message<?> transformInboundRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("SubscriberLastTransferStatusUssdTransformer :: transformInboundRequest");
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
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();
			LinkedCaseInsensitiveMap<Object> requestMap = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LOGGER.info("Forming BI request to fetch last transaction status of subscriber for operation " + Constants.Gp.OPERATION);
			LinkedCaseInsensitiveMap<Object> additionalFields = (LinkedCaseInsensitiveMap<Object>) requestMap.get(Constants.Gp.COMMAND);
			if (additionalFields != null)
			{
				requestMap.putAll(additionalFields);
			}

			Map<String, Object> statusRequest = new HashMap<>();
			Map<String, Object> request = new HashMap<>();
			request.put(Constants.Bi.SIZE, "1");
			request.put(Constants.Bi.REPORT_NAME, operationFields.getOrDefault(Constants.Bi.REPORT_NAME, "subscriber_last_transaction_status"));
			request.put(Constants.Bi.TARGET_MSISDN, requestMap.get(Constants.Gp.MSISDN1));
			statusRequest.put(Constants.Bi.REQUEST, request);
			return MessageBuilder.withPayload(statusRequest).copyHeaders(message.getHeaders()).setHeaderIfAbsent(StandardLinkConstants.Headers.AUTHORIZATION, message.getHeaders().get("authorization")).setHeaderIfAbsent(StandardLinkConstants.Headers.SYSTEM_TOKEN, message.getHeaders().get("system-token")).setHeader(Constants.Gp.IS_REQUEST_TYPE_TEXT, requestMap.get(Constants.Gp.IS_REQUEST_TYPE_TEXT)).build();
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

	/**
	 * Transforms outgoing response to Server compatible response
	 *
	 * @param outgoingResponse
	 * @return
	 * @throws TransformerException
	 */
	@Override
	public Object transformOutboundResponse(Object outgoingResponse) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("SubscriberLastTransferStatusUssdTransformer :: transformOutboundResponse");
		Object smsNotificationReceiverMsisdn = null, ersReference = null, timestamp = null, transactionType = null, resultMessage, product = null, transactionAmount = null;
		StringBuilder messageBuilder = null;
		Map<String, Object> request = new LinkedHashMap<>();
		try
		{
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();

			LOGGER.info("Forming GP response for operation " + Constants.Gp.OPERATION);
			LinkedCaseInsensitiveMap<Object> response = new LinkedCaseInsensitiveMap<>();

			response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));
			response.put(Constants.Gp.GP_EXT_REQUEST_TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));

			if (outgoingResponse instanceof CompletableFuture)
			{
				outgoingResponse = ((CompletableFuture<?>) outgoingResponse).get();
			}

			if (outgoingResponse instanceof Exception)
			{
				Exception exception = (Exception) outgoingResponse;
				response.put(Constants.Gp.GP_MESSAGE, StandardLinkUtilities.getRootCause(exception));
				response.put(Constants.Gp.TXNSTATUS, StandardLinkResultCodes.INTERNAL_COMPONENT_ERROR);
				return response;
			}

			Map<String,String> map = Constants.getData(String.valueOf(language));

			if (outgoingResponse != null)
			{
				Map<String, Object> responseMap = (LinkedHashMap<String, Object>) outgoingResponse;
				request = (Map<String, Object>) responseMap.get(Constants.Gp.REQUEST_PAYLOAD);
				Map<String, Object> requestPayloadCommand = (Map<String, Object>) request.get(Constants.Gp.COMMAND);
				if (requestPayloadCommand != null)
				{
					request.putAll(requestPayloadCommand);
				}
				messageBuilder = new StringBuilder(map.get(Constants.DEFAULT_MESSAGE) + request.get(Constants.Gp.MSISDN1));
				String defaultMessage = String.valueOf(responseMap.get(Constants.Bi.RESULT_DESCRIPTION) != null ? responseMap.get(Constants.Bi.RESULT_DESCRIPTION) : responseMap.get(Constants.Bi.MESSAGE));
				response.put(Constants.Gp.GP_EXT_REQUEST_TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));
				response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));
				if (responseMap.containsKey(Constants.Bi.RESULTCODE))
				{
					Integer resultCodeValue = (Integer) responseMap.get("resultCode");
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);

					if (resultCode != null)
					{
						response.put(Constants.Gp.TXNSTATUS, resultCode.getInternalResultCode());

					}
					else
					{
						response.put(Constants.Gp.TXNSTATUS, responseMap.get(Constants.Bi.RESULTCODE).toString());
					}
					response.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
				}
				List list = (List) responseMap.get("list");
				if (Objects.nonNull(list) && list.size() > 0)
				{
					LinkedHashMap recordMap = (LinkedHashMap) list.get(0);
					ersReference = recordMap.get("ersReference");
					timestamp = recordMap.get("timestamp");
					transactionType = recordMap.get("TransactionType");
					resultMessage = recordMap.get("resultMessage");
					product = "FlexiLoad";
					transactionAmount = recordMap.get("transactionAmount");
					messageBuilder = new StringBuilder(map.get(Constants.LAST_TRANSFER_TXN_ID));
					messageBuilder.append(ersReference).append(String.format(map.get(Constants.MESSAGE),timestamp,transactionType,product,transactionAmount));
//					messageBuilder.append(ersReference).append(" transfer date time " + timestamp).append(" transfer type " + transactionType).append(" product value " + product).append(" net payable amount " + transactionAmount);
				}
			}
			else
			{
				LOGGER.info("The subscriber last transfer status response from BI is null for operation " + Constants.Gp.OPERATION);
				response.put(Constants.Gp.GP_EXT_REQUEST_TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));
				response.put(Constants.Gp.TXNSTATUS, String.valueOf(StandardLinkResultCodes.INTERNAL_COMPONENT_ERROR));
			}

			//Send SMS notification via Notification manager
			smsNotificationUtility.sendSmsNotification(null, String.valueOf(request.get(Constants.Gp.MSISDN1)), messageBuilder.toString());
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
		private static final String DEFAULT_MESSAGE = "DEFAULT_MESSAGE";
		private static final String LAST_TRANSFER_TXN_ID = "LAST_TRANSFER_TXN_ID";
		private static final String MESSAGE = "MESSAGE";

		private static Map<String, String> getData(String language) {
			Map<String, String> responseMap = new HashMap<>();
			if (language.equalsIgnoreCase("en")) {
				responseMap.put(DEFAULT_MESSAGE, En.DEFAULT_MESSAGE);
				responseMap.put(LAST_TRANSFER_TXN_ID, En.LAST_TRANSFER_TXN_ID);
				responseMap.put(MESSAGE, En.MESSAGE);
			} else {
				responseMap.put(DEFAULT_MESSAGE, Bn.DEFAULT_MESSAGE);
				responseMap.put(LAST_TRANSFER_TXN_ID, Bn.LAST_TRANSFER_TXN_ID);
				responseMap.put(MESSAGE, Bn.MESSAGE);
			}
			return responseMap;
		}

		private static final class En {
			private static final String DEFAULT_MESSAGE = "No record found ";
			private static final String LAST_TRANSFER_TXN_ID = "Last transfer status of transaction ID ";
			private static final String MESSAGE = "transfer date time %s transfer type %s product value %s net payable amount %s";
		}

		private static final class Bn
		{
			private static final String DEFAULT_MESSAGE = "পাওয়া কোন রেকর্ড ";
			private static final String LAST_TRANSFER_TXN_ID = "লেনদেন আইডির শেষ স্থানান্তরের অবস্থা ";
			private static final String MESSAGE = "স্থানান্তরের তারিখ সময় %s স্থানান্তর প্রকার %s পণ্যের মূল্য %s নেট প্রদেয় পরিমাণ %s";
		}
		private static final class Gp
		{
			private static final String GP_EXT_REQUEST_TYPE = "TYPE";
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
			private static final String PROVIDER = "gp";
			private static final String OPERATION = "subscriberLastTransferStatusUssd";
			private static final String COMMAND = "COMMAND";
			private static final String MSISDN1 = "MSISDN1";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String TXNSTATUS = "TXNSTATUS";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String IS_REQUEST_TYPE_TEXT = "IS_REQUEST_TYPE_TEXT";
			private static final String REQUEST_PAYLOAD = "requestPayload";

		}

		private static final class Bi
		{
			private static final String MESSAGE = "message";
			private static final String RESULTCODE = "resultCode";
			private static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String SIZE = "size";
			private static final String REPORT_NAME = "reportName";
			private static final String TARGET_MSISDN = "targetMSISDN";
			private static final String REQUEST = "request";
		}
	}
}

