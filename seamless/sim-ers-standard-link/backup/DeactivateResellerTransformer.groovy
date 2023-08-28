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
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class DeactivateResellerTransformer implements ERSIOTransformer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(DeactivateResellerTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Override
	public Message<?> transformInboundRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("DeactivateResellerTransformer :: transformOutboundRequest");

		try
		{
			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.COMMAND);

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();

			String dmsSystemToken = (String) message.getHeaders().get("system-token");
			dmsSystemToken = dmsSystemToken.replaceAll("\"rootComponent\": ?\"[a-zA-Z-]*\"", "\"rootComponent\": \"dms\"");

			LinkedCaseInsensitiveMap data = (LinkedCaseInsensitiveMap) command.get(Constants.Gp.DATA);
			Map<String, Object> deleteRequest = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> principalId = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> customParameters = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> targetResellers = Collections.synchronizedMap(new LinkedHashMap<>());

			if (!command.get(Constants.Gp.LOGINID).toString().isEmpty())
			{
				principalId.put(StandardLinkConstants.TXEConstant.ID, command.get(Constants.Gp.LOGINID));
				principalId.put(StandardLinkConstants.TXEConstant.TYPE, operationFields.get(Constants.Txe.TARGET_PRINCIPAL_TYPE));
			}
			deleteRequest.put(Constants.Txe.PRINCIPAL_ID, principalId);
			if (command.get(Constants.Gp.GP_EXT_REQUEST_TYPE).toString().equals(Constants.Txe.USERDELREQ))
			{
				deleteRequest.put(Constants.Txe.OPERATION_TYPE, Constants.Txe.DEACTIVATE);
			}
			customParameters.put(Constants.Txe.GLOBAL_REASON, operationFields.get(Constants.Txe.GLOBAL_REASON));
			deleteRequest.put(Constants.Txe.CUSTOM_PARAMETERS, customParameters);
			deleteRequest.put(Constants.Txe.TARGET_PRINCIPAL_TYPE, operationFields.get(Constants.Txe.TARGET_PRINCIPAL_TYPE));
			if (command.get(Constants.Gp.GP_EXT_REQUEST_TYPE).toString().equals(Constants.Txe.USERDELREQ))
			{
				targetResellers.put(data.get(Constants.Gp.LOGINID).toString(), operationFields.get(Constants.Txe.GLOBAL_REASON));
			}
			else
			{
LOGGER.info("Data is " + data + "UserOriginId is " + data.get(Constants.Gp.USERORIGINID).toString());
				targetResellers.put(data.get(Constants.Gp.USERORIGINID).toString(), operationFields.get(Constants.Txe.GLOBAL_REASON));
			}
			deleteRequest.put(Constants.Txe.TARGET_RESELLERS, targetResellers);
			return MessageBuilder.withPayload(deleteRequest).copyHeaders(message.getHeaders())
					.setHeader(StandardLinkConstants.Headers.SYSTEM_TOKEN, dmsSystemToken)
					.setHeader("errorChannel",message.getHeaders().get("errorChannel"))
					.setHeader("IS_INTERNAL_CALL",message.getHeaders().get("IS_INTERNA_CALL"))
					.setHeader("history",message.getHeaders().get("history"))
					.setHeader("PROVIDER_ID",message.getHeaders().get("PROVIDER_ID"))
					.setHeader("replyChannel",message.getHeaders().get("replyChannel"))
					.setHeader("OPERATION",message.getHeaders().get("OPERATION"))
					.setHeader("host",message.getHeaders().get("host"))
					.setHeader("connection",message.getHeaders().get("connection"))
					.setHeader("cache-control",message.getHeaders().get("cache-control"))
					.setHeader("accept-encoding",message.getHeaders().get("accept-encoding"))
					.setHeader(StandardLinkConstants.Headers.AUTHORIZATION, message.getHeaders().get("authorization"))
					.setHeaderIfAbsent("validateOnly", false).build();
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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("DeactivateResellerTransformer :: transformOutboundResponse");

		try
		{
			Map<String, Object> response = new LinkedHashMap<>();
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
				response.put(Constants.Gp.GP_TXN_STATUS, StandardLinkResultCodes.INTERNAL_COMPONENT_ERROR);
				return response;
			}

			Map<String, Object> internalResponse = (LinkedHashMap<String, Object>) outgoingResponse;
			Map<String, Object> request = (Map<String, Object>) internalResponse.get(Constants.Gp.REQUEST_PAYLOAD);
			Map<String, Object> requestPayloadCommand = (Map<String, Object>) request.get(Constants.Gp.COMMAND);
			Map<String, Object> data = (Map<String, Object>) (requestPayloadCommand.get(Constants.Gp.DATA));

			if (!internalResponse.isEmpty() && internalResponse.containsKey(Constants.Txe.RESULT_CODE))
			{
				Integer resultCodeValue = (Integer) internalResponse.get(Constants.Txe.RESULT_CODE);
				String defaultMessage = String.valueOf(internalResponse.get(Constants.Txe.RESULT_MESSAGE) != null ? internalResponse.get(Constants.Txe.RESULT_MESSAGE) : internalResponse.get(Constants.Txe.MESSAGE) != null ? internalResponse.get(Constants.Txe.MESSAGE) : internalResponse.get(Constants.Txe.RESULT_DESCRIPTION));
				if (resultCodeValue == StandardLinkResultCodes.SUCCESS)
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Gp.TYPE, operationFields.get(Constants.Txe.RESPONSE_TYPE));
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);
					response.put(Constants.Gp.DATE, (simpleDateFormat.format(new Date())));
					response.put(Constants.Gp.USER_ID, (String.valueOf(data.get(Constants.Gp.LOGINID))));
					response.put(Constants.Gp.MSISDN, (String.valueOf(data.get(Constants.Gp.USER_MSISDN))));
					response.put(Constants.Gp.EXTERNAL_CODE, (String.valueOf(data.get(Constants.Gp.EXTERNAL_CODE))));
					response.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
					response.put(Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
				}
				else
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
					response.put(Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
					response.put(Constants.Gp.TYPE, operationFields.get(Constants.Txe.RESPONSE_TYPE));
				}
			}
			else
			{
				response.put(Constants.Gp.GP_MESSAGE, "Request has failed on server.");
				response.put(Constants.Gp.GP_TXN_STATUS, StandardLinkResultCodes.FAILED_ON_SERVER);
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
			private static final String USERORIGINID = "USERORIGINID";
			private static final String PROVIDER = "gp";
			private static final String DATA = "DATA";
			private static final String LOGINID = "LOGINID";
			private static final String GP_EXT_REQUEST_TYPE = "TYPE";
			private static final String USERLOGINID = "USERLOGINID";
			private static final String DATE_FORMAT = "dd/MM/YYYY hh:mm:ss";
			private static final String USER_MSISDN = "USERMSISDN";
			private static final String EXTERNAL_CODE = "EXTERNALCODE";
			private static final String COMMAND = "COMMAND";
			private static final String OPERATION = "deactivateReseller";
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String GP_TXN_STATUS = "TXNSTATUS";
			private static final String DATE = "DATE";
			private static final String USER_ID = "USERID";
			private static final String MSISDN = "MSISDN";
			private static final String REQUEST_PAYLOAD = "requestPayload";
			private static final String TYPE = "TYPE";
		}

		private static final class Txe
		{
			private static final String PRINCIPAL_ID = "principalId";
			private static final String USERDELREQ = "USERDELREQ";
			private static final String OPERATION_TYPE = "operationType";
			private static final String DEACTIVATE = "DEACTIVATE";
			private static final String GLOBAL_REASON = "globalReason";
			private static final String TARGET_RESELLERS = "targetResellers";
			private static final String CUSTOM_PARAMETERS = "customParameters";
			private static final String TARGET_PRINCIPAL_TYPE = "targetPrincipalType";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String RESPONSE = "response";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String RESULT_CODE = "resultCode";
			private static final String MESSAGE = "message";
		}
	}
}
