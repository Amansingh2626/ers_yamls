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
public class LastTransferStatusUssdTextTransformer implements ERSIOTransformer
{

	private static final Logger LOGGER = LoggerFactory.getLogger(LastTransferStatusUssdTextTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);
	private Object language;

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Autowired
	private SendSmsNotificationUtility smsNotificationUtility;

/**
 *
 * @param message
 * @return
 * @throws TransformerException
 */
	@Override
	public Message<?> transformInboundRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("LastTransferStatusUssdTextTransformer :: transformOutboundRequest");

		try
		{
			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.COMMAND);
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Map> systemTokenMap = objectMapper.readValue(String.valueOf(message.getHeaders().get(StandardLinkConstants.Headers.SYSTEM_TOKEN)), Map.class);

			if (command != null)
			{
				requestFields.putAll(command);
			}
			Map<String, Object> request = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> fieldsMap = Collections.synchronizedMap(new LinkedHashMap<>());

			if (requestFields.containsKey(Constants.Gp.NOOFTXN) && requestFields.get(Constants.Gp.NOOFTXN) != null && !StringUtils.isEmpty(requestFields.get(Constants.Gp.NOOFTXN).toString()))
			{
				fieldsMap.put(Constants.Gp.SIZE, requestFields.get(Constants.Gp.NOOFTXN));
			}
			else
			{
				fieldsMap.put(Constants.Gp.SIZE, String.valueOf(operationFields.get(Constants.Gp.DEFAULT_SIZE)));
			}

			if (systemTokenMap != null && systemTokenMap.size() > 0){
				Map<String, String> initiatorInfo = (Map) systemTokenMap.get(LastTransferStatusUssdTextTransformer.Constants.Txe.CONTEXT).get(LastTransferStatusUssdTextTransformer.Constants.Txe.INITIATOR);
				language = initiatorInfo.get(LastTransferStatusUssdTextTransformer.Constants.Txe.LANGUAGE);
			}

			String days = null;
			if (requestFields.containsKey(Constants.Gp.NOOFDAYS) && requestFields.get(Constants.Gp.NOOFDAYS) != null && !StringUtils.isEmpty(requestFields.get(Constants.Gp.NOOFDAYS).toString()))
			{
				days = requestFields.get(Constants.Gp.NOOFDAYS).toString();
			}
			else
			{
				days = String.valueOf(operationFields.get(Constants.Gp.DEFAULT_DAYS));
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, -Integer.parseInt(days));
			fieldsMap.put(Constants.Gp.FROMDATE, simpleDateFormat.format(calendar.getTime()));
			fieldsMap.put(Constants.Gp.TODATE, simpleDateFormat.format(new Date()));

			if (requestFields.containsKey(Constants.Gp.MSISDN1) && requestFields.get(Constants.Gp.MSISDN1) != null && !StringUtils.isEmpty(requestFields.get(Constants.Gp.MSISDN1).toString()))
			{
				fieldsMap.put(Constants.Gp.TARGETMSISDN, requestFields.get(Constants.Gp.MSISDN1));
			}
			else if (requestFields.containsKey(Constants.Gp.RECEIVER_MSISDN) && requestFields.get(Constants.Gp.RECEIVER_MSISDN) != null && !StringUtils.isEmpty(requestFields.get(Constants.Gp.RECEIVER_MSISDN).toString()))
			{
				fieldsMap.put(Constants.Gp.TARGETMSISDN, requestFields.get(Constants.Gp.RECEIVER_MSISDN));
			}
			else
			{
				fieldsMap.put(Constants.Gp.TARGETMSISDN, String.valueOf(operationFields.get(Constants.Gp.DEAULT_MSISDN)));
			}
			fieldsMap.put(Constants.Gp.REPORTNAME, String.valueOf(operationFields.get(Constants.Gp.REPORTNAME)));
			if (requestFields.containsKey(Constants.Gp.SERVICE_TYPE) && requestFields.get(Constants.Gp.SERVICE_TYPE) != null && !StringUtils.isEmpty(requestFields.get(Constants.Gp.SERVICE_TYPE).toString()))
			{
				if (requestFields.get(Constants.Gp.SERVICE_TYPE).equals("C2S"))
				{
					fieldsMap.put(Constants.Gp.TRANSACTIONTYPE, String.valueOf(operationFields.get(Constants.Gp.C2SSERVICETYPE)));
				}
				else if (requestFields.get(Constants.Gp.SERVICE_TYPE).equals("C2C"))
				{
					fieldsMap.put(Constants.Gp.TRANSACTIONTYPE, String.valueOf(operationFields.get(Constants.Gp.C2CSERVICETYPE)));
				}
				else if (requestFields.get(Constants.Gp.SERVICE_TYPE).equals("O2C"))
				{
					fieldsMap.put(Constants.Gp.TRANSACTIONTYPE, String.valueOf(operationFields.get(Constants.Gp.O2CSERVICETYPE)));
				}
				else
				{
					fieldsMap.put(Constants.Gp.TRANSACTIONTYPE, String.valueOf(operationFields.get(Constants.Gp.DEFAULTSERVICETYPE)));
				}
			}
			else
			{
				fieldsMap.put(Constants.Gp.TRANSACTIONTYPE, String.valueOf(operationFields.get(Constants.Gp.DEFAULTSERVICETYPE)));
			}
			request.put(Constants.Gp.REQUEST, fieldsMap);
			return MessageBuilder.withPayload(request).copyHeaders(message.getHeaders()).setHeaderIfAbsent(StandardLinkConstants.Headers.SYSTEM_TOKEN, message.getHeaders().get("system-token")).setHeaderIfAbsent(StandardLinkConstants.Headers.AUTHORIZATION, message.getHeaders().get("authorization")).setHeaderIfAbsent("validateOnly", false).setHeader(Constants.Gp.IS_REQUEST_TYPE_TEXT, requestFields.get(Constants.Gp.IS_REQUEST_TYPE_TEXT)).build();
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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("LastTransferStatusUssdTextTransformer :: transformOutboundResponse");
		try
		{
			Map<String, Object> response = new LinkedHashMap<>();
			Map<String, Object> request = new LinkedHashMap<>();
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();

			response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));
			response.put(Constants.Gp.GP_EXT_REQUEST_TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));

			LinkedCaseInsensitiveMap<Object> defaultFailMessage = (LinkedCaseInsensitiveMap<Object>) providerConfigurations.getOperations().get(LastTransferStatusUssdTextTransformer.Constants.Gp.OPERATION).getFields().get(language);

			Object smsNotificationMessage = defaultFailMessage.get("defaultMessage");

			if (outgoingResponse instanceof CompletableFuture)
			{
				outgoingResponse = ((CompletableFuture<?>) outgoingResponse).get();
			}

			if (outgoingResponse instanceof Exception)
			{
				Exception exception = (Exception) outgoingResponse;
				response.put(Constants.Gp.MESSAGE, StandardLinkUtilities.getRootCause(exception));
				response.put(Constants.Gp.TXNSTATUS, StandardLinkResultCodes.INTERNAL_COMPONENT_ERROR);
				return response;
			}

			Map<String, Object> internalResponse = (LinkedHashMap<String, Object>) outgoingResponse;
			request = (Map<String, Object>) internalResponse.get(Constants.Gp.REQUEST_PAYLOAD);
			Map<String, Object> requestPayloadCommand = (Map<String, Object>) request.get(Constants.Gp.COMMAND);
			if (requestPayloadCommand != null)
			{
				request.putAll(requestPayloadCommand);
			}

			if (!internalResponse.isEmpty() && internalResponse.containsKey(Constants.Txe.RESULT_CODE))
			{
				smsNotificationMessage = String.valueOf(internalResponse.get(Constants.Txe.RESULT_DESCRIPTION));
				Integer resultCodeValue = (Integer) internalResponse.get(Constants.Txe.RESULT_CODE);
				String defaultMessage = String.valueOf(internalResponse.get(Constants.Txe.RESULT_MESSAGE) != null ? internalResponse.get(Constants.Txe.RESULT_MESSAGE) : internalResponse.get(Constants.Txe.MESSAGE) != null ? internalResponse.get(Constants.Txe.MESSAGE) : internalResponse.get(Constants.Txe.RESULT_DESCRIPTION));
				if (resultCodeValue == StandardLinkResultCodes.SUCCESS)
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeBasedOnLanguage(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage, String.valueOf(language));
					response.put(Constants.Gp.MESSAGE, resultCode.getDescription());
					response.put(Constants.Gp.TXNSTATUS, resultCode.getInternalResultCode());
					response.put(Constants.Gp.GP_EXT_REQUEST_TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));
				}
				else
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeBasedOnLanguage(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage, String.valueOf(language));
					response.put(Constants.Gp.MESSAGE, resultCode.getDescription());
					response.put(Constants.Gp.TXNSTATUS, resultCode.getInternalResultCode());
				}
			}
			else
			{
				response.put(Constants.Gp.MESSAGE, "Request has failed on server.");
				response.put(Constants.Gp.TXNSTATUS, StandardLinkResultCodes.FAILED_ON_SERVER);
			}
			smsNotificationUtility.sendSmsNotification(null, String.valueOf(request.get(Constants.Gp.MSISDN1)), String.valueOf(smsNotificationMessage));
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
		private static final class Gp
		{
			private static final String IS_REQUEST_TYPE_TEXT = "IS_REQUEST_TYPE_TEXT";
			private static final String NOOFTXN = "NOOFTXN";
			private static final String SIZE = "size";
			private static final String DEFAULT_SIZE = "defaultSize";
			private static final String NOOFDAYS = "NOOFDAYS";
			private static final String DEFAULT_DAYS = "defaultDays";
			private static final String FROMDATE = "fromDate";
			private static final String TODATE = "toDate";
			private static final String TARGETMSISDN = "targetMsisdn";
			private static final String RECEIVER_MSISDN = "RECEIVER_MSISDN";
			private static final String REPORTNAME = "reportName";
			private static final String SERVICE_TYPE = "SERVICE_TYPE";
			private static final String TRANSACTIONTYPE = "transactionType";
			private static final String DEAULT_MSISDN = "defaultMSISDN";
			private static final String C2SSERVICETYPE = "C2SServiceType";
			private static final String C2CSERVICETYPE = "C2CServiceType";
			private static final String O2CSERVICETYPE = "O2CServiceType";
			private static final String DEFAULTSERVICETYPE = "defaultServiceType";
			private static final String REQUEST = "request";
			private static final String PROVIDER = "gp";
			private static final String OPERATION = "LastTransferStatus";
			private static final String MSISDN1 = "MSISDN1";
			private static final String TXNSTATUS = "TXNSTATUS";
			private static final String MESSAGE = "MESSAGE";
			private static final String GP_EXT_REQUEST_TYPE = "TYPE";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
			private static final String REQUEST_PAYLOAD = "requestPayload";
			private static final String COMMAND = "COMMAND";
		}

		private static final class Txe
		{

			private static final String RESULT_CODE = "resultCode";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String MESSAGE = "message";
			private static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String CONTEXT = "context";
			private static final String INITIATOR = "initiator";
			private static final String LANGUAGE = "language";
		}
	}
}


