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
public class SuspendUserTransformer implements ERSIOTransformer
{

	private static final Logger LOGGER = LoggerFactory.getLogger(SuspendUserTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Override
	public Message<?> transformInboundRequest(Message<?> message) throws TransformerException
	{

		LOGGER.info("Forming DMS view user request for operation " + Constants.OPERATION_ID);
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("SuspendUserTransformer :: transformInboundRequest");
		try
		{

			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.COMMAND);

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION_ID).getFields();

			LinkedCaseInsensitiveMap<Object> data = (LinkedCaseInsensitiveMap<Object>) command.get(Constants.Gp.DATA);

			String dmsSystemToken = (String) message.getHeaders().get("system-token");
			dmsSystemToken = dmsSystemToken.replaceAll("\"rootComponent\": ?\"[a-zA-Z-]*\"", "\"rootComponent\": \"dms\"");

			Map<String, Object> principalId = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> targetResellers = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> suspendRequest = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> customParams = Collections.synchronizedMap(new LinkedHashMap<>());

			String system_token = (String) message.getHeaders().get(Constants.Dms.SYSTEM_TOKEN);
			int start = system_token.indexOf("\"" + Constants.Dms.UID + "\"") + ("\"" + Constants.Dms.UID + "\"").length() + 1;
			int uid_start = system_token.indexOf("\"", start);
			int uid_end = system_token.indexOf("\"", uid_start + 1);

			principalId.put(Constants.Dms.ID, system_token.substring(uid_start + 1, uid_end));
			principalId.put(Constants.Dms.TYPE, operationFields.get(Constants.Dms.TARGET_PRINCIPAL_TYPE));
			suspendRequest.put(Constants.Dms.PRINCIPAL_ID, principalId);

			suspendRequest.put(Constants.Dms.PARENT_RESELLER_ID, data.get(Constants.Gp.PARENT_ORIGIN_ID));
			suspendRequest.put(Constants.Dms.RESELLER_TYPE, data.get(Constants.Gp.USER_CAT_CODE));
			suspendRequest.put(Constants.Dms.NAME, data.get(Constants.Gp.USER_NAME));
			suspendRequest.put(Constants.Dms.RESELLER_MSISDN, data.get(Constants.Gp.MSISDN1));

			if (command.get(Constants.Gp.EXT_REQUEST_TYPE).toString().equals(Constants.Dms.USERDELREQ))
			{
				suspendRequest.put(Constants.Dms.OPERATION_TYPE, Constants.Dms.DEACTIVATE);
			}
			else if (data.containsKey(Constants.Gp.ACTION) && data.get(Constants.Gp.ACTION).toString().trim().equalsIgnoreCase(Constants.Gp.S))
			{
				suspendRequest.put(Constants.Dms.OPERATION_TYPE, operationFields.get(Constants.Gp.S));
			}
			else if (data.containsKey(Constants.Gp.ACTION) && data.get(Constants.Gp.ACTION).toString().trim().equalsIgnoreCase(Constants.Gp.R))
			{
				suspendRequest.put(Constants.Dms.OPERATION_TYPE, operationFields.get(Constants.Gp.R));
			}

			customParams.put(Constants.Dms.GLOBAL_REASON, operationFields.get(Constants.Dms.GLOBAL_REASON));
			suspendRequest.put(Constants.Dms.CUSTOM_PARAMETERS, customParams);
			suspendRequest.put(Constants.Dms.TARGET_PRINCIPAL_TYPE, operationFields.get(Constants.Dms.TARGET_PRINCIPAL_TYPE));

			if (command.get(Constants.Gp.EXT_REQUEST_TYPE).toString().equals(Constants.Dms.USERDELREQ))
			{
				targetResellers.put(data.get(Constants.Gp.LOGINID).toString(), operationFields.get(Constants.Dms.GLOBAL_REASON));
			}
			else
			{
				if (Objects.nonNull(data.get(Constants.Gp.USER_MSISDN))
						&& StringUtils.isNotBlank(data.get(Constants.Gp.USER_MSISDN).toString()))
				{
                                        String msisdn = data.get(Constants.Gp.USER_MSISDN).toString();
					if(msisdn.length() == 10){
						msisdn="880"+msisdn;
					}else if(msisdn.length() == 11){
						msisdn="88"+msisdn;
                                        }

                                        targetResellers.put(msisdn, operationFields.get(Constants.Dms.GLOBAL_REASON));
					suspendRequest.put(Constants.Dms.TARGET_PRINCIPAL_TYPE, operationFields.get("targetPrincipalMSISDNType"));

				}
				else if (Objects.nonNull(data.get(Constants.Gp.USER_ORIGIN_ID)) &&
						StringUtils.isNotBlank(data.get(Constants.Gp.USER_ORIGIN_ID).toString()))
				{

					targetResellers.put(data.get(Constants.Gp.USER_ORIGIN_ID).toString(), operationFields.get(Constants.Dms.GLOBAL_REASON));
					suspendRequest.put(Constants.Dms.TARGET_PRINCIPAL_TYPE, operationFields.get("targetPrincipalType"));

				}
				else if (Objects.nonNull(data.get(Constants.Gp.USER_LOGIN_ID)) &&
						StringUtils.isNotBlank(data.get(Constants.Gp.USER_LOGIN_ID).toString()))
				{

					targetResellers.put(data.get(Constants.Gp.USER_LOGIN_ID).toString(), operationFields.get(Constants.Dms.GLOBAL_REASON));
					suspendRequest.put(Constants.Dms.TARGET_PRINCIPAL_TYPE, operationFields.get("targetPrincipalType"));
				}
			}
			suspendRequest.put(Constants.Dms.TARGET_RESELLERS, targetResellers);
			return MessageBuilder.withPayload(suspendRequest)
					.setHeader("errorChannel", message.getHeaders().get("errorChannel"))
					.setHeader("history", message.getHeaders().get("history"))
					.setHeader("PROVIDER_ID", message.getHeaders().get("PROVIDER_ID"))
					.setHeader("replyChannel", message.getHeaders().get("replyChannel"))
					.setHeader("OPERATION", message.getHeaders().get("OPERATION"))
					.setHeader("host", message.getHeaders().get("host"))
					.setHeader("cache-control", message.getHeaders().get("cache-control"))
					.setHeader("accept-encoding", message.getHeaders().get("accept-encoding"))
					.setHeader(StandardLinkConstants.Headers.SYSTEM_TOKEN, dmsSystemToken)
					.setHeader("authorization", message.getHeaders().get("authorization"))
					.setHeader(Constants.REQUEST_PAYLOAD, message.getHeaders().get(Constants.REQUEST_PAYLOAD))
					.setHeaderIfAbsent(Constants.Dms.VALIDATE_ONLY, false).build();
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

		EtmPoint point = EtmManager.getEtmMonitor().createPoint("SuspendUserTransformer :: transformOutboundResponse");

		try
		{
			Map<String, Object> response = new LinkedHashMap<>();
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION_ID).getFields();

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
			LinkedCaseInsensitiveMap<Object> request = (LinkedCaseInsensitiveMap<Object>) internalResponse.get(Constants.Gp.REQUEST_PAYLOAD);
			Map<String, Object> requestPayloadCommand = (LinkedCaseInsensitiveMap<Object>) request.get(Constants.Gp.COMMAND);

			if (requestPayloadCommand != null)
			{
				request.putAll(requestPayloadCommand);
			}

			LinkedCaseInsensitiveMap<Object> data = (LinkedCaseInsensitiveMap<Object>) requestPayloadCommand.get(Constants.Gp.DATA);

			if (!internalResponse.isEmpty() && internalResponse.containsKey(Constants.Dms.RESULT_CODE))
			{
				Integer resultCodeValue = (Integer) internalResponse.get(Constants.Dms.RESULT_CODE);
				String defaultMessage = String.valueOf(internalResponse.get(Constants.Dms.RESULT_MESSAGE) != null ? internalResponse.get(Constants.Dms.RESULT_MESSAGE) : internalResponse.get(Constants.Dms.MESSAGE) != null ? internalResponse.get(Constants.Dms.MESSAGE) : internalResponse.get(Constants.Dms.RESULT_DESCRIPTION));
				if (resultCodeValue == StandardLinkResultCodes.SUCCESS)
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION_ID, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
                                if (resultCode != null) {
                                response.put(Constants.Gp.MESSAGE, resultCode.getDescription());
                                response.put(Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
}
					if (requestPayloadCommand.get(Constants.Gp.EXT_REQUEST_TYPE).toString().equals(Constants.Dms.USERDELREQ))
					{
						response.put(Constants.Dms.TYPE, operationFields.get(Constants.Dms.RESPONSE_TYPE));
					}
					else
					{
						response.put("type", operationFields.get(Constants.Dms.RESPONSE_TYPE));
						response.put("action", data.get(Constants.Gp.ACTION).toString());
					}
					LinkedHashMap msisdns = (LinkedHashMap) data.get(Constants.Gp.MSISDNS);

					if (response.containsKey(Constants.Dms.RESELLER_DETAILS))
					{
						Object[] userId = ((Map<String, Object>) (response.get(Constants.Dms.RESELLER_DETAILS))).keySet().toArray();
						response.put(Constants.Dms.USER_ID, userId[0]);
					}
					else
					{
						response.put(Constants.Dms.USER_ID, data.get(Constants.Gp.MSISDN1).toString());
					}

					response.put(Constants.Gp.EXTERNAL_CODE, data.get(Constants.Gp.EXTERNAL_CODE).toString());
					response.put(Constants.Dms.MSISDN, data.get(Constants.Gp.MSISDN1).toString());
				}
				else
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION_ID, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					if (resultCode != null)
					{
						response.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
						response.put(Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
					}
					else
					{
						response.put(Constants.Gp.TXN_STATUS, (internalResponse.get(Constants.Dms.RESULTCODE).toString()));
						if (!internalResponse.get(Constants.Dms.RESULT_DESCRIPTION).toString().equals("SUCCESS"))
						{
							response.put(Constants.Gp.MESSAGE, internalResponse.get(Constants.Dms.RESULT_DESCRIPTION).toString());
						}
						else if ((requestPayloadCommand).get(Constants.Gp.GP_EXT_REQUEST_TYPE).toString().equals(Constants.Dms.USERDELREQ))
						{
							response.put(Constants.Gp.MESSAGE, operationFields.get(Constants.Dms.DELETED_MESSAGE));
						}
						else if (data.get(Constants.Gp.ACTION).toString().trim().equalsIgnoreCase("s"))
						{
							response.put(Constants.Gp.MESSAGE, operationFields.get(Constants.Dms.BLOCKED_MESSAGE));
						}
						else if (data.get(Constants.Gp.ACTION).toString().trim().equalsIgnoreCase("r"))
						{
							response.put(Constants.Gp.MESSAGE, operationFields.get(Constants.Dms.UNBLOCKED_MESSAGE));
						}
					}
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
		private static final String PROVIDER = "gp";
		private static final String OPERATION_ID = "suspendUser";
		private static final String REQUEST_PAYLOAD = "requestPayload";

		private static final class Gp
		{
			public static final String USER_MSISDN = "USERMSISDN";
			public static final String USER_ORIGIN_ID = "USERORIGINID";
			public static final String GP_EXT_REQUEST_TYPE = "TYPE";
			public static final String TXN_STATUS = "txnStats";
			public static final String MESSAGE = "message";
			private static final String DATA = "DATA";
			private static final String DATE_FORMAT = "dd/MM/YYYY hh:mm:ss";
			private static final String MSISDNS = "msisdns";
			private static final String PARENT_ORIGIN_ID = "PARENTORIGINID";
			private static final String COMMAND = "COMMAND";
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String GP_TXN_STATUS = "TXNSTATUS";
			private static final String REQUEST_PAYLOAD = "requestPayload";
			private static final String MSISDN1 = "USERMSISDN";
			private static final String WEB_LOGIN_ID = "WEBLOGINID";
			private static final String USER_CAT_CODE = "USERCATCODE";
			private static final String USER_NAME = "USERNAME";
			private static final String EXTERNAL_CODE = "EXTERNALCODE";
			private static final String EXT_REQUEST_TYPE = "TYPE";
			private static final String ACTION = "ACTION";
			private static final String S = "S";
			private static final String R = "R";
			private static final String LOGINID = "LOGINID";
			private static final String USER_LOGIN_ID = "USERLOGINID";
		}

		private static final class Dms
		{
			public static final String RESULTCODE = "resultCode";
			public static final String DELETED_MESSAGE = "deletedMessage";
			public static final String BLOCKED_MESSAGE = "blockedMessage";
			public static final String UNBLOCKED_MESSAGE = "unblockedMessage";
			public static final String RESELLER_DETAILS = "resellerDetails";
			private static final String TYPE = "type";
			private static final String ID = "id";
			private static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String PARENT_RESELLER_ID = "parentResellerId";
			private static final String RESELLER_TYPE = "resellerType";
			private static final String NAME = "name";
			private static final String RESELLER_MSISDN = "resellerMSISDN";
			private static final String RESULT_CODE = "resultCode";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String MESSAGE = "message";
			private static final String MSISDN = "msisdn";
			private static final String USER_ID = "userId";
			private static final String SYSTEM_TOKEN = "system-token";
			private static final String VALIDATE_ONLY = "validateOnly";
			private static final String UID = "uid";
			private static final String PRINCIPAL_ID = "principalId";
			private static final String OPERATION_TYPE = "operationType";
			private static final String GLOBAL_REASON = "globalReason";
			private static final String CUSTOM_PARAMETERS = "customParameters";
			private static final String TARGET_PRINCIPAL_TYPE = "targetPrincipalType";
			private static final String TARGET_RESELLERS = "targetResellers";
			private static final String DEACTIVATE = "DEACTIVATE";
			private static final String USERDELREQ = "USERDELREQ";
		}
	}
}
