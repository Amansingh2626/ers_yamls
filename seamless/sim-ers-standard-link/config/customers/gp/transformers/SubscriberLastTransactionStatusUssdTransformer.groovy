package com.seamless.ers.standardlink.transformers;

import com.seamless.common.StringUtils;
import com.seamless.ers.standardlink.config.StandardLinkConfig;
import com.seamless.ers.standardlink.model.common.ERSIOTransformer;
import com.seamless.ers.standardlink.model.common.ResultCode;
import com.seamless.ers.standardlink.model.common.constants.StandardLinkConstants;
import com.seamless.ers.standardlink.model.configs.ProviderConfigurations;
import com.seamless.ers.standardlink.model.exception.TransformerException;
import com.seamless.ers.standardlink.model.internal.StandardLinkResultCodes;
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
public class SubscriberLastTransactionStatusUssdTransformer implements ERSIOTransformer
{

	private static final Logger LOGGER = LoggerFactory.getLogger(SubscriberLastTransactionStatusUssdTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("SubscriberLastTransactionStatusUssdTransformer :: transformInboundRequest");

		try
		{
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();
			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.COMMAND);

			if (command != null)
			{
				requestFields.putAll(command);
			}

			Map<String, Object> statusRequest = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> request = Collections.synchronizedMap(new LinkedHashMap<>());

			request.put(Constants.Bi.SIZE, "1");
			request.put(Constants.Bi.REPORT_NAME, operationFields.getOrDefault(Constants.Bi.REPORT_NAME, "subscriber_last_transaction_status"));
			request.put(Constants.Bi.TARGET_MSISDN, String.valueOf(requestFields.get(Constants.Gp.MSISDN1)));
			statusRequest.put(Constants.Bi.REQUEST, request);
			return MessageBuilder.withPayload(statusRequest).copyHeaders(message.getHeaders()).copyHeaders(message.getHeaders()).setHeaderIfAbsent(StandardLinkConstants.Headers.AUTHORIZATION, StringUtils.isEmpty(String.valueOf(message.getHeaders().get(Constants.Gp.AUTHORIZATION))) ? "" : message.getHeaders().get(Constants.Gp.AUTHORIZATION)).setHeaderIfAbsent(StandardLinkConstants.Headers.SYSTEM_TOKEN, message.getHeaders().get(Constants.Gp.SYSTEM_TOKEN)).setHeader(Constants.Gp.IS_REQUEST_TYPE_TEXT, requestFields.get(Constants.Gp.IS_REQUEST_TYPE_TEXT)).build();
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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("SubscriberLastTransactionStatusUssdTransformer :: transformOutboundResponse");
		Map<String, Object> request;
		try
		{
			LinkedCaseInsensitiveMap<Object> response = new LinkedCaseInsensitiveMap<>();

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();

			response.put(Constants.Gp.TYPE, operationFields.get(Constants.Bi.RESPONSE_TYPE));
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
				response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));
				return response;
			}

			Map<String, Object> internalResponse = (LinkedHashMap<String, Object>) outgoingResponse;
			request = (Map<String, Object>) internalResponse.get(Constants.Gp.REQUEST_PAYLOAD);
			Map<String, Object> requestPayloadCommand = (Map<String, Object>) request.get(Constants.Gp.COMMAND);
			if (requestPayloadCommand != null)
			{
				request.putAll(requestPayloadCommand);
			}

			String defaultMessage = String.valueOf(internalResponse.get(Constants.Bi.RESULT_DESCRIPTION) != null ? internalResponse.get(Constants.Bi.RESULT_DESCRIPTION) : internalResponse.get(Constants.Bi.MESSAGE));

			if (internalResponse.containsKey(Constants.Bi.RESULT_CODE))
			{
				Integer resultCodeValue = (Integer) internalResponse.get("resultCode");
				ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
				response.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());

				if (resultCode != null)
				{
					response.put(Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());

				}
				else
				{
					response.put(Constants.Gp.GP_TXN_STATUS, String.valueOf(resultCodeValue));
				}
			}
			else
			{
				response.put(Constants.Gp.TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));
				response.put(Constants.Gp.GP_TXN_STATUS, String.valueOf(StandardLinkResultCodes.INTERNAL_COMPONENT_ERROR));
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
		private static final class Gp
		{
			private static final String SYSTEM_TOKEN = "system-token";
			private static final String AUTHORIZATION = "authorization";
			private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
			private static final String PIN = "PIN";
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String PROVIDER = "gp";
			private static final String OPERATION = "subscriberLastTransactionStatusUssd";
			private static final String COMMAND = "COMMAND";
			private static final String MSISDN1 = "MSISDN1";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String GP_TXN_STATUS = "TXNSTATUS";
			private static final String TYPE = "TYPE";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String IS_REQUEST_TYPE_TEXT = "IS_REQUEST_TYPE_TEXT";
			private static final String REQUEST_PAYLOAD = "requestPayload";
		}

		private static final class Bi
		{
			public static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String SENDER_PIN = "senderPin";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String MESSAGE = "message";
			private static final String RESULT_CODE = "resultCode";
			private static final String SIZE = "size";
			private static final String REPORT_NAME = "reportName";
			private static final String TARGET_MSISDN = "targetMSISDN";
			private static final String REQUEST = "request";
		}
	}
}
