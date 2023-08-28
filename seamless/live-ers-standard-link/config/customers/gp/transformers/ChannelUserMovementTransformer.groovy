package com.seamless.ers.standardlink.transformers;

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
import org.springframework.http.HttpHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Component
public class ChannelUserMovementTransformer implements ERSIOTransformer
{

	private static final Logger LOGGER = LoggerFactory.getLogger(ChannelUserMovementTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Override
	public Message<?> transformInboundRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("ChannelUserMovementTransformer :: transformOutboundRequest");

		try
		{
			Object payload = message.getPayload();
			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) payload;
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.COMMAND);

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION).getFields();

			String dmsSystemToken = (String) message.getHeaders().get("system-token");
			dmsSystemToken = dmsSystemToken.replaceAll("\"rootComponent\": ?\"[a-zA-Z-]*\"", "\"rootComponent\": \"dms\"");

			String queryString = "newResellerParentId=";

			if (command.containsKey(Constants.Gp.FROM_USER_ORIGIN_ID))
			{
				queryString = queryString + String.valueOf(command.get(Constants.Gp.TO_PARENT_ORIGIN_ID));
			}

			if (command.containsKey(Constants.Gp.TO_PARENT_ORIGIN_ID))
			{
				queryString = queryString + "&resellerId=" + String.valueOf(command.get(Constants.Gp.FROM_USER_ORIGIN_ID));
			}

			return MessageBuilder.withPayload(queryString)
					.copyHeaders(message.getHeaders())
					.setHeader("errorChannel", message.getHeaders().get("errorChannel"))
					.setHeader("history", message.getHeaders().get("history"))
					.setHeader("PROVIDER_ID", message.getHeaders().get("PROVIDER_ID"))
					.setHeader("replyChannel", message.getHeaders().get("replyChannel"))
					.setHeader("OPERATION", message.getHeaders().get("OPERATION"))
					.setHeader("host", message.getHeaders().get("host"))
					.setHeader("cache-control", message.getHeaders().get("cache-control"))
					.setHeader("accept-encoding", message.getHeaders().get("accept-encoding"))
					.setHeader(HttpHeaders.CONTENT_TYPE, operationFields.get(StandardLinkConstants.Headers.CONTENT_TYPE))
					.setHeaderIfAbsent(Constants.AUTHORIZATION, message.getHeaders().get(Constants.AUTHORIZATION))
					.setHeader(Constants.SYSTEM_TOKEN, dmsSystemToken).build();
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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("ChannelUserMovementTransformer :: transformOutboundResponse");

		try
		{
			Map<String, Object> response = new LinkedHashMap<>();
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION).getFields();

			response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));
			response.put(Constants.Gp.GP_EXT_REQUEST_TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));

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
			Map<String, Object> request = (Map<String, Object>) internalResponse.get(Constants.Gp.REQUEST_PAYLOAD);
			Map<String, Object> requestPayloadCommand = (Map<String, Object>) request.get(Constants.Gp.COMMAND);

			if (!internalResponse.isEmpty() && internalResponse.containsKey(Constants.Txe.RESULT_CODE))
			{
				Integer resultCodeValue = (Integer) internalResponse.get(Constants.Txe.RESULT_CODE);
				String defaultMessage = String.valueOf(internalResponse.get(Constants.Txe.RESULT_MESSAGE) != null ? internalResponse.get(Constants.Txe.RESULT_MESSAGE) : internalResponse.get(Constants.Txe.MESSAGE) != null ? internalResponse.get(Constants.Txe.MESSAGE) : internalResponse.get(Constants.Txe.RESULT_DESCRIPTION));
				if (resultCodeValue == StandardLinkResultCodes.SUCCESS)
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					if (Objects.nonNull(requestPayloadCommand.get(Constants.Gp.EXTREFNUM)) &&
							!org.apache.commons.lang3.StringUtils.isEmpty(requestPayloadCommand.get(Constants.Gp.EXTREFNUM).toString()))
					{
						response.put(Constants.Gp.EXTREFNUM, (requestPayloadCommand.get(Constants.Gp.EXTREFNUM).toString()));
					}
					response.put(Constants.Gp.MESSAGE, resultCode.getDescription());
					response.put(Constants.Gp.TXNSTATUS, resultCode.getInternalResultCode());
				}
				else if(defaultMessage==Constants.Txe.DESCRIPTION)
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Gp.MESSAGE, "TO_USER_GEOGRAPHICAL_CODE should not be empty or Incorrect");
					response.put(Constants.Gp.TXNSTATUS, resultCode.getInternalResultCode());
				}
				else
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Gp.MESSAGE, resultCode.getDescription());
					response.put(Constants.Gp.TXNSTATUS, resultCode.getInternalResultCode());
				}
			}
			else
			{
				response.put(Constants.Gp.MESSAGE, "Request has failed on server.");
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
		private static final String PROVIDER = "gp";
		private static final String OPERATION = "channelUserMovement";
		private static final String SYSTEM_TOKEN = "system-token";
		private static final String AUTHORIZATION = "authorization";

		private static final class Gp
		{
			private static final String EXTREFNUM = "EXTREFNUM";
			private static final String DATE_FORMAT = "dd/MM/YYYY hh:mm:ss";
			private static final String TXNSTATUS = "TXNSTATUS";
			private static final String MESSAGE = "MESSAGE";
			private static final String REQUEST_PAYLOAD = "requestPayload";
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String COMMAND = "COMMAND";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String GP_EXT_REQUEST_TYPE = "TYPE";
			private static final String FROM_USER_ORIGIN_ID = "FROM_USER_ORIGIN_ID";
			private static final String TO_PARENT_ORIGIN_ID = "TO_PARENT_ORIGIN_ID";
		}

		private static final class Txe
		{
			private static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String RESULT_CODE = "resultCode";
			private static final String MESSAGE = "message";
			private static final String DESCRIPTION = "400 : [no body]";
		}
	}
}
