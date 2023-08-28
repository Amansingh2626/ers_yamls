package com.seamless.ers.standardlink.transformers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seamless.common.StringUtils;
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
public class ChangePasswordUSSDTransformer implements ERSIOTransformer
{

	private static final Logger LOGGER = LoggerFactory.getLogger(ChangePasswordUSSDTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);
	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Autowired
	private SendSmsNotificationUtility smsNotificationUtility;
	Object msisdn,newPin, confirmPin,language;

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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("ChangePasswordUSSDTransformer :: transformOutboundRequest");
		LOGGER.info("Forming DMS request for Change password " + Constants.OPERATION);

		try
		{
			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			Map<String, Object> command = (Map<String, Object>) requestFields.get(Constants.Gp.COMMAND);
			if (command != null)
			{
				requestFields.putAll(command);
			}

			String dmsSystemToken = (String) message.getHeaders().get("system-token");
			dmsSystemToken = dmsSystemToken.replaceAll("\"rootComponent\": ?\"[a-zA-Z-]*\"", "\"rootComponent\": \"dms\"");

			Map<String, Object> changePasswordRequest = new HashMap<>();
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Map> systemToken = objectMapper.readValue(String.valueOf(message.getHeaders().get(StandardLinkConstants.Headers.SYSTEM_TOKEN)), Map.class);

			String resellerId = "";

			if (systemToken != null && systemToken.size() > 0)
			{
				Map<String, String> initiatorInfo = (Map) systemToken.get(Constants.Dms.CONTEXT).get(Constants.Dms.INITIATOR);
				language = initiatorInfo.get(Constants.Dms.LANGUAGE);
				if (initiatorInfo != null && initiatorInfo.size() > 0 && StringUtils.isNotEmpty(initiatorInfo.get(Constants.Dms.RESELLER_PATH)))
				{
					String[] resellerPath = initiatorInfo.get(Constants.Dms.RESELLER_PATH).split("/");
					resellerId = resellerPath[resellerPath.length - 1];
				}
				else
				{
					LOGGER.info("Either initiatorInfo or resellerPath not found");
				}
			}
			else
			{
				LOGGER.info("System token not found for change PIN of reseller");
			}
			LOGGER.info("resellerId " + resellerId + " for change PIN of reseller");

			msisdn = (requestFields).get(Constants.Gp.MSISDN1);
			if(msisdn.toString().startsWith("0")) {
				msisdn = "88"+msisdn;
			}
			else if(msisdn.toString().startsWith("1")){
				msisdn = "880"+msisdn;
			}

			LOGGER.info("msisdn after formatting " + msisdn);

			if(!requestFields.isEmpty()) {
				newPin = (requestFields).get(Constants.Gp.NEWPIN);
				confirmPin = (requestFields).get(Constants.Gp.CONFIRMPIN);
			}

			if(!Objects.isNull(newPin)) {
				changePasswordRequest.put(Constants.Dms.NEW_PASSWORD, newPin);
			}
			if(!Objects.isNull(confirmPin)) {
				changePasswordRequest.put(Constants.Dms.CONFIRM_PASSWORD, confirmPin);
			}
			changePasswordRequest.put(Constants.Dms.OLD_PASSWORD, (requestFields).get(Constants.Gp.PIN));
			changePasswordRequest.put(Constants.Dms.RESELLER_ID, resellerId);
			changePasswordRequest.put(Constants.Dms.USER_ID, msisdn);

			return MessageBuilder.withPayload(changePasswordRequest).
					copyHeaders(message.getHeaders()).setHeaderIfAbsent(StandardLinkConstants.Headers.AUTHORIZATION, message.getHeaders().get(Constants.AUTHORIZATION)).
					setHeader(StandardLinkConstants.Headers.SYSTEM_TOKEN, dmsSystemToken).
					setHeader(Constants.Dms.LANGUAGE, language).
					setHeader(Constants.Gp.IS_REQUEST_TYPE_TEXT, requestFields.get(Constants.Gp.IS_REQUEST_TYPE_TEXT)).
					build();
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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("ChangePasswordUSSDTransformer :: transformOutboundResponse");
		try
		{
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION).getFields();
			LOGGER.info("Forming GP response for operation " + Constants.OPERATION);

			if (outgoingResponse instanceof CompletableFuture)
			{
				outgoingResponse = ((CompletableFuture<?>) outgoingResponse).get();
			}

			Object notificationMessage = "Something went wrong during pin change of reseller !!!";
			Object smsNotificationMessage = "Something went wrong during pin change of reseller !!!";
			Map<String, Object> responseMap = (Map<String, Object>) outgoingResponse;
			Map<String, Object> request = new LinkedHashMap<>();
			Map<String, Object> changePasswordEXTGT = new LinkedHashMap<>();

			if (outgoingResponse instanceof Exception)
			{
				Exception exception = (Exception) outgoingResponse;
				changePasswordEXTGT.put(Constants.Gp.GP_MESSAGE, StandardLinkUtilities.getRootCause(exception));
				changePasswordEXTGT.put(Constants.Gp.TXNSTATUS, StandardLinkResultCodes.INTERNAL_COMPONENT_ERROR);
				return changePasswordEXTGT;
			}

			request = (Map<String, Object>) responseMap.get(Constants.Gp.REQUEST_PAYLOAD);
			Map<String, Object> requestPayloadCommand = (Map<String, Object>) request.get(Constants.Gp.COMMAND);
			if (requestPayloadCommand != null)
			{
				request.putAll(requestPayloadCommand);
			}

			changePasswordEXTGT.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));
			changePasswordEXTGT.put(Constants.Gp.GP_EXT_REQUEST_TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));
			ResultCode languageBasedResultCode = standardLinkConfig.getResultCodeBasedOnLanguage(
					Constants.PROVIDER,
					Constants.OPERATION,
					responseMap.get(Constants.Dms.RESULT_CODE).toString(),
					responseMap.get(Constants.Dms.RESULT_CODE).toString(),
					responseMap.get(Constants.Dms.RESULT_DESCRIPTION).toString(),String.valueOf(language));

			if(languageBasedResultCode != null)
			{
				smsNotificationMessage = languageBasedResultCode.getDescription() + (responseMap.containsKey(Constants.Dms.RESULT_CODE) && responseMap.get(Constants.Dms.RESULT_CODE).toString().equalsIgnoreCase(String.valueOf(StandardLinkResultCodes.SUCCESS)) ? " " + request.get(Constants.Gp.NEWPIN) : "");
			}else{
				smsNotificationMessage = String.valueOf(responseMap.get(Constants.Dms.RESULT_DESCRIPTION));
			}
			ResultCode resultCode = standardLinkConfig.getResultCodeFor(
					Constants.PROVIDER,
					Constants.OPERATION,
					responseMap.get(Constants.Dms.RESULT_CODE).toString(),
					responseMap.get(Constants.Dms.RESULT_CODE).toString(),
					responseMap.get(Constants.Dms.RESULT_DESCRIPTION).toString());
			if(resultCode != null && responseMap.containsKey(Constants.Dms.RESULT_CODE) && responseMap.get(Constants.Dms.RESULT_CODE).toString().equalsIgnoreCase(String.valueOf(StandardLinkResultCodes.SUCCESS)))
			{
				changePasswordEXTGT.put(Constants.Gp.TXNSTATUS, resultCode.getInternalResultCode());
				notificationMessage = resultCode.getDescription();// + "" + request.get(Constants.Gp.NEWPIN);
			}
			else{
				changePasswordEXTGT.put(Constants.Gp.TXNSTATUS, String.valueOf(responseMap.get(Constants.Dms.RESULTCODE)));
				notificationMessage = String.valueOf(responseMap.get(Constants.Dms.RESULT_DESCRIPTION));
			}
			changePasswordEXTGT.put(Constants.Gp.GP_MESSAGE, notificationMessage);
//			smsNotificationMessage = responseMap.get(Constants.Dms.RESULT_DESCRIPTION) + " " +  request.get(Constants.Gp.NEWPIN);
//			smsNotificationUtility.sendSmsNotification(null, String.valueOf(request.get(Constants.Gp.MSISDN1)), String.valueOf(smsNotificationMessage));
			return changePasswordEXTGT;

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
		private static final String OPERATION = "changePasswordUSSD";
		private static final String AUTHORIZATION = "authorization";

		private static final class Gp
		{
			private static final String IS_REQUEST_TYPE_TEXT = "IS_REQUEST_TYPE_TEXT";
			private static final String DATE_FORMAT = "dd/MM/YYYY hh:mm:ss";
			private static final String COMMAND = "COMMAND";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String TXNSTATUS = "TXNSTATUS";
			private static final String REQUEST_PAYLOAD = "requestPayload";
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String GP_EXT_REQUEST_TYPE = "TYPE";
			private static final String MSISDN1 = "MSISDN1";
			private static final String NEWPIN = "NEWPIN";
			private static final String CONFIRMPIN = "CONFIRMPIN";
			private static final String PIN = "PIN";
		}

		private static final class Dms
		{
			private static final String RESULTCODE = "resultCode";
			private static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String RESULT_CODE = "resultCode";
			private static final String NEW_PASSWORD = "newPassword";
			private static final String OLD_PASSWORD = "oldPassword";
			private static final String CONFIRM_PASSWORD = "confirmPassword";
			private static final String RESELLER_ID = "resellerId";
			private static final String USER_ID = "userId";
			private static final String RESELLER_PATH = "resellerPath";
			private static final String CONTEXT = "context";
			private static final String INITIATOR = "initiator";
			private static final String LANGUAGE = "language";
		}
	}

}
