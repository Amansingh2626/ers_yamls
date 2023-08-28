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

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component
public class LastTransferStatusUssdTransformer implements ERSIOTransformer
{

	private static final Logger LOGGER = LoggerFactory.getLogger(LastTransferStatusUssdTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Autowired
	SendSmsNotificationUtility smsNotificationUtility;
	private Object language;

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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("LastTransferStatusUSSDTransformer :: transformOutboundRequest");

		try
		{
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();
			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.COMMAND);

			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Map> systemToken = objectMapper.readValue(String.valueOf(message.getHeaders().get(StandardLinkConstants.Headers.SYSTEM_TOKEN)), Map.class);
			if (systemToken != null && systemToken.size() > 0)
			{
				Map<String, String> initiatorInfo = (Map) systemToken.get("context").get("initiator");
				language = initiatorInfo.get("language");
			}
			else
			{
				LOGGER.info("System token not found for LastTransferStatusUssdTransformer.");
			}

			if (command != null)
			{
				requestFields.putAll(command);
			}
			Map<String, Object> statusRequest = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> request = Collections.synchronizedMap(new LinkedHashMap<>());

			if (command != null)
			{
				request.put(Constants.Bi.SIZE, "1");
				request.put(Constants.Bi.REPORT_NAME, operationFields.getOrDefault(Constants.Bi.REPORT_NAME, "last_tranasction_status"));
				request.put(Constants.Bi.TARGETMSISDN, requestFields.get(Constants.Gp.MSISDN1));
				statusRequest.put(Constants.Bi.REQUEST, request);
			}
			return MessageBuilder.withPayload(statusRequest).copyHeaders(message.getHeaders()).setHeaderIfAbsent(StandardLinkConstants.Headers.AUTHORIZATION, message.getHeaders().get("authorization")).setHeaderIfAbsent(StandardLinkConstants.Headers.SYSTEM_TOKEN, message.getHeaders().get("system-token")).setHeaderIfAbsent("validateOnly", false).build();

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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("LastTransferStatusUssdTransformer :: transformOutboundResponse");
		try
		{
			Map<String, Object> response = new LinkedHashMap<>();
			Object smsNotificationReceiverMsisdn = null, ersReference = null, timestamp = null, transactionType = null, resultMessage, product = null, transactionAmount = null;
//			StringBuilder messageBuilder = new StringBuilder("Something went wrong while checking status !!!");
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();

			response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));

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

			Map<String, Object> internalResponse = (LinkedHashMap<String, Object>) outgoingResponse;

			if (!internalResponse.isEmpty() && internalResponse.containsKey(Constants.Bi.RESULT_CODE))
			{
				Integer resultCodeValue = (Integer) internalResponse.get(Constants.Bi.RESULT_CODE);
				String defaultMessage = String.valueOf(internalResponse.get(Constants.Bi.RESULT_MESSAGE) != null ? internalResponse.get(Constants.Bi.RESULT_MESSAGE) : internalResponse.get(Constants.Bi.MESSAGE) != null ? internalResponse.get(Constants.Bi.MESSAGE) : internalResponse.get(Constants.Bi.RESULT_DESCRIPTION));
				if (resultCodeValue == StandardLinkResultCodes.SUCCESS)
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Gp.GP_EXT_REQUEST_TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));
					response.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
					response.put(Constants.Gp.TXNSTATUS, resultCode.getInternalResultCode());

					Map<String,String> staticMessageMap = LastTransferStatusUssdTransformer.Constants.getData(String.valueOf(language));

					List list = (List) internalResponse.get("list");
					if (Objects.nonNull(list) && list.size() > 0)
					{
						LinkedHashMap recordMap = (LinkedHashMap) list.get(0);
						smsNotificationReceiverMsisdn = recordMap.get(Constants.Gp.RECEIVER_MSISDN);
						ersReference = recordMap.get(Constants.Gp.ERS_REFERENCE);
						timestamp = recordMap.get(Constants.Gp.TIME_STAMP);
						transactionType = recordMap.get(Constants.Gp.TRANSACTION_TYPE);
						resultMessage = recordMap.get(Constants.Gp.RESULT_MESSAGE);
						product = Constants.Gp.FLEXI_LOAD;
						transactionAmount = recordMap.get(Constants.Gp.TRANSACTION_AMOUNT);
					}
					StringBuilder messageBuilder = new StringBuilder(staticMessageMap.get(Constants.DEFAULT_MESSAGE));
					messageBuilder.append(StringUtils.SPACE).append(ersReference).append(StringUtils.SPACE).append(staticMessageMap.get(Constants.TRANSFER_DATE_TIME)).append(StringUtils.SPACE).append(timestamp).append(StringUtils.SPACE).append(staticMessageMap.get(Constants.TRANSFER_TYPE)).append(StringUtils.SPACE).append(transactionType)
							.append(StringUtils.SPACE).append(staticMessageMap.get(Constants.PRODUCT_VALUE)).append(StringUtils.SPACE).append(product).append(StringUtils.SPACE).append(staticMessageMap.get(Constants.NET_PAYABLE_AMT)).append(StringUtils.SPACE).append(transactionAmount);
					smsNotificationUtility.sendSmsNotification(null, String.valueOf(smsNotificationReceiverMsisdn), messageBuilder.toString());
				}
				else
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeBasedOnLanguage(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage, String.valueOf(language));
					response.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
					response.put(Constants.Gp.TXNSTATUS, resultCode.getInternalResultCode());
					response.put(Constants.Gp.GP_EXT_REQUEST_TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));
				}
			}
			else
			{
				response.put(Constants.Gp.GP_MESSAGE, "Request has failed on server.");
				response.put(Constants.Gp.TXNSTATUS, StandardLinkResultCodes.FAILED_ON_SERVER);
			}
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
		private static final String TRANSFER_DATE_TIME = "TRANSFER_DATE_TIME";
		private static final String TRANSFER_TYPE = "TRANSFER_TYPE";
		private static final String PRODUCT_VALUE = "PRODUCT_VALUE";
		private static final String NET_PAYABLE_AMT = "NET_PAYABLE_AMT";
		private static final class Gp
		{
			private static final String TRANSACTION_AMOUNT = "transactionAmount";
			private static final String FLEXI_LOAD = "FlexiLoad";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String TRANSACTION_TYPE = "TransactionType";
			private static final String TIME_STAMP = "timestamp";
			private static final String ERS_REFERENCE = "ersReference";
			private static final String RECEIVER_MSISDN = "receiverMSISDN";
			private static final String PROVIDER = "gp";
			private static final String OPERATION = "lastTransferStatusUSSD";
			private static final String MSISDN1 = "MSISDN1";
			private static final String TXNSTATUS = "TXNSTATUS";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String GP_EXT_REQUEST_TYPE = "TYPE";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
			private static final String COMMAND = "COMMAND";
		}

		private static final class Bi
		{
			private static final String SIZE = "size";
			private static final String REPORT_NAME = "reportName";
			private static final String TARGETMSISDN = "targetMsisdn";
			private static final String REQUEST = "request";
			private static final String RESULT_CODE = "resultCode";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String MESSAGE = "message";
			private static final String RESULT_DESCRIPTION = "resultDescription";
		}

		private static Map<String, String> getData(String language) {
			Map<String, String> responseMap = new HashMap<>();
			if (language.equalsIgnoreCase("en")) {
				responseMap.put(DEFAULT_MESSAGE, LastTransferStatusUssdTransformer.Constants.En.DEFAULT_MESSAGE);
				responseMap.put(TRANSFER_DATE_TIME, LastTransferStatusUssdTransformer.Constants.En.TRANSFER_DATE_TIME);
				responseMap.put(TRANSFER_TYPE, LastTransferStatusUssdTransformer.Constants.En.TRANSFER_TYPE);
				responseMap.put(PRODUCT_VALUE, LastTransferStatusUssdTransformer.Constants.En.PRODUCT_VALUE);
				responseMap.put(NET_PAYABLE_AMT, LastTransferStatusUssdTransformer.Constants.En.NET_PAYABLE_AMT);
			} else {
				responseMap.put(DEFAULT_MESSAGE, LastTransferStatusUssdTransformer.Constants.Bn.DEFAULT_MESSAGE);
				responseMap.put(TRANSFER_DATE_TIME, LastTransferStatusUssdTransformer.Constants.Bn.TRANSFER_DATE_TIME);
				responseMap.put(TRANSFER_TYPE, LastTransferStatusUssdTransformer.Constants.Bn.TRANSFER_TYPE);
				responseMap.put(PRODUCT_VALUE, LastTransferStatusUssdTransformer.Constants.Bn.PRODUCT_VALUE);
				responseMap.put(NET_PAYABLE_AMT, LastTransferStatusUssdTransformer.Constants.Bn.NET_PAYABLE_AMT);
			}
			return responseMap;
		}

		private static final class En
		{
			private static final String DEFAULT_MESSAGE = "Last transfer status of transaction ID ";
			private static final String TRANSFER_DATE_TIME = "transfer date time";
			private static final String TRANSFER_TYPE = "transfer type";
			private static final String PRODUCT_VALUE = "product value";
			private static final String NET_PAYABLE_AMT = "net payable amount";
		}

		private static final class Bn
		{
			private static final String DEFAULT_MESSAGE = "লেনদেন আইডির শেষ স্থানান্তরের অবস্থা";
			private static final String TRANSFER_DATE_TIME = "স্থানান্তর তারিখ সময়";
			private static final String TRANSFER_TYPE = "স্থানান্তর প্রকার";
			private static final String PRODUCT_VALUE = "পণ্যের মান";
			private static final String NET_PAYABLE_AMT = "নেট প্রদেয় পরিমাণ";
		}
	}
}

