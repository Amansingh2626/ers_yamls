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

@Component("updateResellerInternalTransformer")
public class UpdateResellerInternalTransformer implements ERSIOTransformer
{

	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateResellerInternalTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Override
	public Message<?> transformInboundRequest(Message<?> message) throws TransformerException
	{

		LOGGER.info("Forming DMS Update Reseller Internal request for operation " + Constants.OPERATION_ID);
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("UpdateResellerInternalTransformer :: transformOutboundRequest");
		try
		{
			String dmsSystemToken = (String) message.getHeaders().get("system-token");
			dmsSystemToken = dmsSystemToken.replaceAll("\"rootComponent\": ?\"[a-zA-Z-]*\"", "\"rootComponent\": \"dms\"");

			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.COMMAND);
			List<Map<String, Object>> regionInfo = (List<Map<String, Object>>) requestFields.get(Constants.Dms.REGION_INFO);

			Map<String, Object> dealerData = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> resellerData = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> dealerPrincipal = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> updateRequest = Collections.synchronizedMap(new LinkedHashMap<>());
			List<Map<String, Object>> usersArray = new ArrayList<>();

			Map<String, Object> parentValueMap = new LinkedHashMap<>();
			parentValueMap.put(Constants.Dms.NAME, Constants.Dms.PARENT_MSISDN);
			parentValueMap.put(Constants.Dms.VALUE, command.get(Constants.Dms.TO_PARENT_MSISDN));
			regionInfo.add(parentValueMap);

			parentValueMap = new LinkedHashMap<>();
			parentValueMap.put(Constants.Dms.NAME, Constants.Dms.PARENT_EXTERNAL_CODE);
			parentValueMap.put(Constants.Dms.VALUE, command.get(Constants.Dms.TO_PARENT_EXTCODE));
			regionInfo.add(parentValueMap);

			dealerData.put(Constants.Dms.RESELLER_DATA, resellerData);
			dealerData.put(Constants.Dms.USERS, usersArray);
			dealerData.put(Constants.Dms.ADDITIONAL_FIELDS, regionInfo);

			dealerPrincipal.put(Constants.Dms.TYPE, Constants.Dms.RESELLER_ID);
			dealerPrincipal.put(Constants.Dms.ID, command.get(Constants.Dms.FROM_USER_ORIGIN_ID));

			updateRequest.put(Constants.Dms.DEALER_DATA, dealerData);
			updateRequest.put(Constants.Dms.DEALER_PRINCIPAL, dealerPrincipal);
			return MessageBuilder.withPayload(updateRequest).copyHeaders(message.getHeaders()).setHeader(StandardLinkConstants.Headers.AUTHORIZATION, StringUtils.isEmpty(String.valueOf(message.getHeaders().get(Constants.Gp.authorization))) ? "" : message.getHeaders().get(Constants.Gp.authorization)).setHeader(StandardLinkConstants.Headers.SYSTEM_TOKEN, dmsSystemToken).build();
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

		EtmPoint point = EtmManager.getEtmMonitor().createPoint("UpdateResellerInternalTransformer :: transformOutboundResponse");

		try
		{
			Map<String, Object> response = new LinkedCaseInsensitiveMap<>();
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION_ID).getFields();

			response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));
			response.put(Constants.Dms.TYPE, operationFields.get(Constants.Dms.RESPONSE_TYPE));

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

			if (!internalResponse.isEmpty() && internalResponse.containsKey(Constants.Dms.RESULT_CODE))
			{
				Integer resultCodeValue = (Integer) internalResponse.get(Constants.Dms.RESULT_CODE);
				String defaultMessage = String.valueOf(internalResponse.get(Constants.Dms.RESULT_MESSAGE) != null ? internalResponse.get(Constants.Dms.RESULT_MESSAGE) : internalResponse.get(Constants.Dms.MESSAGE) != null ? internalResponse.get(Constants.Dms.MESSAGE) : internalResponse.get(Constants.Dms.RESULT_DESCRIPTION));
				if (resultCodeValue == StandardLinkResultCodes.SUCCESS)
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION_ID, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Dms.RESULT_DESCRIPTION, resultCode.getDescription());
					response.put(Constants.Dms.RESULT_CODE, resultCode.getInternalResultCode());
					String login = (String) requestPayloadCommand.get("FROM_USER_ORIGIN_ID");
					if (Objects.nonNull(login)
							&& StringUtils.isNotEmpty(login))
					{
						response.put(Constants.Gp.USERID, login);
					}
				}
				else
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION_ID, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Dms.RESULT_DESCRIPTION, resultCode.getDescription());
					response.put(Constants.Dms.RESULT_CODE, resultCode.getInternalResultCode());
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
		private static final String OPERATION_ID = "UpdateResellerInternal";
		private static final String SYSTEM_TOKEN = "system-token";

		private static final class Gp
		{
			private static final String USERID = "userId";
			private static final String DATE_FORMAT = "dd/MM/YYYY hh:mm:ss";
			private static final String COMMAND = "command";
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String GP_TXN_STATUS = "TXNSTATUS";
			private static final String REQUEST_PAYLOAD = "requestPayload";
			private static final String authorization = "authorization";
		}

		private static final class Dms
		{
			private static final String NAME = "name";
			private static final String VALUE = "value";
			private static final String TYPE = "type";
			private static final String ID = "id";
			private static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String RESELLER_ID = "resellerId";
			private static final String RESULT_CODE = "resultCode";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String MESSAGE = "message";
			private static final String RESELLER_DATA = "resellerData";
			private static final String DEALER_DATA = "dealerData";
			private static final String DEALER_PRINCIPAL = "dealerPrincipal";
			private static final String USERS = "users";
			private static final String ADDITIONAL_FIELDS = "additionalFields";
			private static final String REGION_INFO = "regionInfo";
			private static final String FROM_USER_ORIGIN_ID = "FROM_USER_ORIGIN_ID";
			private static final String PARENT_MSISDN = "parentMsisdn";
			private static final String PARENT_EXTERNAL_CODE = "parentExternalCode";
			private static final String TO_PARENT_MSISDN = "TO_PARENT_MSISDN";
			private static final String TO_PARENT_EXTCODE = "TO_PARENT_EXTCODE";
		}
	}
}
