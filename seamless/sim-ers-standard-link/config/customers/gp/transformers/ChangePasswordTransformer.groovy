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
public class ChangePasswordTransformer implements ERSIOTransformer
{

	private static final Logger LOGGER = LoggerFactory.getLogger(ChangePasswordTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Override
	public Message<?> transformInboundRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("ChangePasswordTransformer :: transformOutboundRequest");

		try
		{
			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.COMMAND);
			LinkedCaseInsensitiveMap<Object> data = (LinkedCaseInsensitiveMap<Object>) command.get(Constants.Gp.DATA);

			String dmsSystemToken = (String) message.getHeaders().get("system-token");
			dmsSystemToken = dmsSystemToken.replaceAll("\"rootComponent\": ?\"[a-zA-Z-]*\"", "\"rootComponent\": \"dms\"");

			Map<String, Object> transferRequest = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> dealerData = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> resellerData = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> dealerPrincipal = Collections.synchronizedMap(new LinkedHashMap<>());
			List<Map<String, Object>> users = new ArrayList<>();
			Map<String, Object> user = Collections.synchronizedMap(new LinkedHashMap<>());

			dealerPrincipal.put(Constants.Gp.PRINCIPALTYPE, Constants.Gp.PRINCIPALVALUE);

			if (StringUtils.isNotEmpty(data.get(Constants.Gp.LOGINID).toString()))
			{
				resellerData.put(Constants.Gp.RESELLERID, data.get(Constants.Gp.LOGINID));
				user.put(Constants.Gp.USERID, data.get(Constants.Gp.LOGINID));
				dealerPrincipal.put(Constants.Gp.PRINCIPALID, data.get(Constants.Gp.LOGINID));
			}
			transferRequest.put(Constants.Txe.DEALER_PRINCIPAL, dealerPrincipal);
			dealerData.put(Constants.Txe.RESELLER_DATA, resellerData);

			if (Objects.nonNull(data.get(Constants.Gp.NEWPASSWD)) && StringUtils.isNotEmpty(data.get(Constants.Gp.NEWPASSWD).toString()))
			{
				user.put(Constants.Gp.PASSWORD, data.get(Constants.Gp.NEWPASSWD));
			}

			users.add(user);
			dealerData.put(Constants.Txe.USERS, users);
			transferRequest.put(Constants.Txe.DEALER_DATA_USERS, dealerData);

			return MessageBuilder.withPayload(transferRequest).
					copyHeaders(message.getHeaders()).setHeaderIfAbsent(StandardLinkConstants.Headers.AUTHORIZATION, message.getHeaders().get("authorization")).
					setHeader(StandardLinkConstants.Headers.SYSTEM_TOKEN, dmsSystemToken).
					setHeader(StandardLinkConstants.Api.ACCEPT_KEY, StandardLinkConstants.Api.CONTENT_TYPE_JSON).build();
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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("ChangePasswordTransformer :: transformOutboundResponse");
		try
		{
			Map<String, Object> response = new LinkedHashMap<>();
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION).getFields();

			response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));
			response.put(Constants.Gp.TYPE, operationFields.get(Constants.Txe.RESPONSE_TYPE));
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

			if (!internalResponse.isEmpty() && internalResponse.containsKey(Constants.Txe.RESULT_CODE))
			{
				Integer resultCodeValue = (Integer) internalResponse.get(Constants.Txe.RESULT_CODE);
				String defaultMessage = String.valueOf(internalResponse.get(Constants.Txe.RESULT_MESSAGE) != null ? internalResponse.get(Constants.Txe.RESULT_MESSAGE) : internalResponse.get(Constants.Txe.MESSAGE) != null ? internalResponse.get(Constants.Txe.MESSAGE) : internalResponse.get(Constants.Txe.RESULT_DESCRIPTION));
				if (resultCodeValue == StandardLinkResultCodes.SUCCESS)
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
					response.put(Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
				}
				else
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
					response.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
				}
			}
			else
			{
				response.put(Constants.Gp.GP_TXN_STATUS, StandardLinkResultCodes.FAILED_ON_SERVER);
				response.put(Constants.Gp.GP_MESSAGE, "Request has failed on server.");

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
		private static final String OPERATION = "changePassword";

		private static final class Gp
		{
			private static final String PRINCIPALTYPE = "type";
			private static final String DATA = "DATA";
			private static final String LOGINID = "LOGINID";
			private static final String RESELLERID = "resellerId";
			private static final String USERID = "USERID";
			private static final String PRINCIPALID = "id";
			private static final String NEWPASSWD = "NEWPASSWD";
			private static final String PASSWORD = "password";
			private static final String PRINCIPALVALUE = "RESELLERID";
			private static final String DATE_FORMAT = "dd/MM/YYYY hh:mm:ss";
			private static final String COMMAND = "COMMAND";
			private static final String PRODUCTS = "products";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String GP_TXN_STATUS = "TXNSTATUS";
			private static final String REQUEST_PAYLOAD = "requestPayload";
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String TYPE = "TYPE";
		}

		private static final class Txe
		{
			private static final String RESPONSE_TYPE = "responseType";
			private static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String RESULT_CODE = "resultCode";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String MESSAGE = "message";
			private static final String DEALER_PRINCIPAL = "dealerPrincipal";
			private static final String RESELLER_DATA = "resellerData";
			private static final String USERS = "users";
			private static final String DEALER_DATA_USERS = "dealerData";
		}
	}
}
