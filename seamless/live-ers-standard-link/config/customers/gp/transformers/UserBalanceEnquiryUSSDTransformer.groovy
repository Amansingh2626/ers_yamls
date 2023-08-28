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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component("userBalanceEnquiryUSSDTransformer")
public class UserBalanceEnquiryUSSDTransformer implements ERSIOTransformer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(UserBalanceEnquiryUSSDTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Autowired
	private SendSmsNotificationUtility smsNotificationUtility;

	Object language;

	@Override
	public Message<?> transformInboundRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("UserBalanceEnquiryUSSDTransformer :: transformInboundRequest");

		try
		{
			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.COMMAND);
			if (command != null)
			{
				requestFields.putAll(command);
			}

			String dmsSystemToken = (String) message.getHeaders().get("system-token");
			dmsSystemToken = dmsSystemToken.replaceAll("\"rootComponent\": ?\"[a-zA-Z-]*\"", "\"rootComponent\": \"dms\"");
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Map> systemToken = objectMapper.readValue(String.valueOf(message.getHeaders().get(StandardLinkConstants.Headers.SYSTEM_TOKEN)), Map.class);
			if (systemToken != null && systemToken.size() > 0)
			{
				Map<String, String> initiatorInfo = (Map) systemToken.get("context").get("initiator");
				language = initiatorInfo.get("language");
			}
			else
			{
				LOGGER.info("System token not found for UserBalanceEnquiryUSSDTransformer");
			}
			Map<String, Object> channelUserBalanceEnquiryRequest = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> dealerId = Collections.synchronizedMap(new LinkedHashMap<>());

			if (requestFields.get(Constants.Gp.MSISDN2) != null && !String.valueOf(requestFields.get(Constants.Gp.MSISDN2)).isEmpty())
			{
				dealerId.put(Constants.Dms.ID, String.valueOf(requestFields.get(Constants.Gp.MSISDN2)));
				dealerId.put(Constants.Dms.TYPE, Constants.Dms.RESELLERMSISDN);
			}
			else
			{
				dealerId.put(Constants.Dms.ID, String.valueOf(requestFields.get(Constants.Gp.MSISDN1)));
				dealerId.put(Constants.Dms.TYPE, Constants.Dms.RESELLERMSISDN);
			}

			channelUserBalanceEnquiryRequest.put(Constants.Dms.DEALER_ID, dealerId);
			channelUserBalanceEnquiryRequest.put(Constants.Dms.FETCH_ACCOUNT_BALANCE_INFORMATION, "true");
			return MessageBuilder.withPayload(channelUserBalanceEnquiryRequest)
					.setHeader(Constants.Headers.ERROR_CHANNEL, message.getHeaders().get("errorChannel"))
					.setHeader(Constants.Headers.HISTORY, message.getHeaders().get("history"))
					.setHeader(Constants.Headers.PROVIDER_ID, message.getHeaders().get("PROVIDER_ID"))
					.setHeader(Constants.Headers.REPLY_CHANNEL, message.getHeaders().get("replyChannel"))
					.setHeader(Constants.Headers.OPERATION, message.getHeaders().get("OPERATION"))
					.setHeader(Constants.Headers.HOST, message.getHeaders().get("host"))
					.setHeader(Constants.Headers.CACHE_CONTROL, message.getHeaders().get("cache-control"))
					.setHeader(Constants.Headers.ACCEPT_ENCODING, message.getHeaders().get("accept-encoding"))
					.setHeader(Constants.Headers.AUTHORIZATION, message.getHeaders().get("authorization"))
					.setHeader(Constants.Headers.REQUEST_PAYLOAD, message.getHeaders().get(Constants.Headers.REQUEST_PAYLOAD))
					.setHeader(Constants.Headers.SYSTEM_TOKEN, dmsSystemToken)
					.setHeader(Constants.Gp.IS_REQUEST_TYPE_TEXT, requestFields.get(Constants.Gp.IS_REQUEST_TYPE_TEXT)).build();
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
 *
 * @param outgoingResponse
 * @return
 * @throws TransformerException
 */
	@Override
	public Object transformOutboundResponse(Object outgoingResponse) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("UserBalanceEnquiryUSSDTransformer :: transformOutboundResponse");

		try
		{
			Map<String, Object> response = new LinkedHashMap<>();
			Map<String, Object> requestFields;
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION).getFields();
			LinkedCaseInsensitiveMap<Object> englishMessageBasedOnLanguage = (LinkedCaseInsensitiveMap<Object>) providerConfigurations.getOperations().get(Constants.OPERATION).getFields().get("en");
			LinkedCaseInsensitiveMap<Object> notificationMessageBasedOnLanguage = (LinkedCaseInsensitiveMap<Object>) providerConfigurations.getOperations().get(Constants.OPERATION).getFields().get(language);
			response.put(Constants.Gp.GP_EXT_REQUEST_TYPE, operationFields.get(Constants.Dms.RESPONSE_TYPE));
			response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));

			if (outgoingResponse instanceof CompletableFuture)
			{
				outgoingResponse = ((CompletableFuture<?>) outgoingResponse).get();
			}

			if (outgoingResponse instanceof Exception)
			{
				Exception exception = (Exception) outgoingResponse;
				response.put(Constants.Gp.MESSAGE, StandardLinkUtilities.getRootCause(exception));
				response.put(Constants.Gp.TXNSTATUS, StandardLinkResultCodes.INTERNAL_COMPONENT_ERROR);
				response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));
				return response;
			}

			Map<String, Object> internalResponse = (LinkedHashMap<String, Object>) outgoingResponse;
			requestFields = (Map<String, Object>) internalResponse.get(Constants.Gp.REQUEST_PAYLOAD);
			Map<String, Object> requestPayloadCommand = (Map<String, Object>) requestFields.get(Constants.Gp.COMMAND);

			if (requestPayloadCommand != null)
			{
				requestFields.putAll(requestPayloadCommand);
			}

			String defaultMessage = String.valueOf(internalResponse.get(Constants.Dms.RESULT_DESCRIPTION) != null ? internalResponse.get(Constants.Dms.RESULT_DESCRIPTION) : internalResponse.get(Constants.Dms.MESSAGE));
			ResultCode resultCode;
			if (!internalResponse.isEmpty() && internalResponse.containsKey("resultCode"))
			{
				Integer resultCodeValue = (Integer) internalResponse.get("resultCode");
				resultCode = standardLinkConfig.getResultCodeBasedOnLanguage(Constants.PROVIDER, Constants.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage,String.valueOf(language));
				response.put(Constants.Gp.TXNSTATUS, resultCode.getInternalResultCode());
			}
			else
			{
				resultCode = new ResultCode(String.valueOf(StandardLinkResultCodes.FAILED_ON_SERVER), String.valueOf(notificationMessageBasedOnLanguage.get("defaultMessage")));
				response.put(Constants.Gp.MESSAGE, "Request has failed on server.");
				response.put(Constants.Gp.TXNSTATUS, StandardLinkResultCodes.FAILED_ON_SERVER);
			}

			String resellerId = "";
			if (Objects.nonNull(internalResponse.get("resellerInfo")) && StringUtils.isNotEmpty(String.valueOf(internalResponse.get("resellerInfo"))))
			{
				LinkedHashMap resellerInfo = (LinkedHashMap) internalResponse.get("resellerInfo");
				if (Objects.nonNull(resellerInfo.get("resellerData")) && StringUtils.isNotEmpty(String.valueOf(resellerInfo.get("resellerData"))))
				{
					LinkedHashMap resellerData = (LinkedHashMap) resellerInfo.get("resellerData");
					resellerId = resellerData.get("resellerId").toString();
				}
			}
			String resellersBalance = fetchResellersBalance(internalResponse);

			//Send SMS notification via Notification manager
			String requestedMsisdn = requestFields.get("MSISDN1").toString();
			if(Objects.nonNull(requestFields.get("MSISDN2")) && StringUtils.isNotBlank(requestFields.get("MSISDN2").toString())){
				requestedMsisdn = requestFields.get("MSISDN2").toString();
			}
			String message = resellersBalance;
			String flashMessage = resellersBalance;
			if (Objects.nonNull(requestFields.get(Constants.Gp.MSISDN2)) && StringUtils.isNotEmpty(requestFields.get(Constants.Gp.MSISDN2).toString()) && !requestFields.get(Constants.Gp.MSISDN1).toString().equals(requestFields.get(Constants.Gp.MSISDN2).toString()))
			{
				if (notificationMessageBasedOnLanguage.containsKey("childMessage") && null != notificationMessageBasedOnLanguage.get("childMessage") &&!StringUtils.isBlank(resellersBalance) && !StringUtils.isBlank(resellerId))
				{
					flashMessage = englishMessageBasedOnLanguage.get("childMessage").toString();
					message = notificationMessageBasedOnLanguage.get("childMessage").toString();
				}
			}
			else
			{
				if (notificationMessageBasedOnLanguage.containsKey("selfMessage") && null != notificationMessageBasedOnLanguage.get("selfMessage") && !StringUtils.isBlank(resellersBalance) && !StringUtils.isBlank(resellerId))
				{
					flashMessage = englishMessageBasedOnLanguage.get("selfMessage").toString();
					message = notificationMessageBasedOnLanguage.get("selfMessage").toString();
				}
			}

			if (!StringUtils.isBlank(resellersBalance) && !StringUtils.isBlank(resellerId))
			{
				response.put(Constants.Gp.MESSAGE, String.format(flashMessage, resellersBalance, Objects.nonNull(requestFields.get(Constants.Gp.MSISDN2))? requestFields.get(Constants.Gp.MSISDN2) : ""));
				smsNotificationUtility.sendSmsNotification(String.valueOf(requestFields.get(Constants.Gp.MSISDN1)), String.valueOf(requestFields.get(Constants.Gp.MSISDN1)), String.format(message, resellersBalance, Objects.nonNull(requestFields.get(Constants.Gp.MSISDN2))? requestFields.get(Constants.Gp.MSISDN2) : ""));
			}
			else
			{
				response.put(Constants.Gp.MESSAGE, defaultMessage);
				smsNotificationUtility.sendSmsNotification(String.valueOf(requestFields.get(Constants.Gp.MSISDN1)), String.valueOf(requestFields.get(Constants.Gp.MSISDN1)), resultCode.getDescription());
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

	private String fetchResellersBalance(Map<String, Object> responseMap)
	{

		if (Objects.nonNull(responseMap.get(Constants.Gp.RESELLERINFO)) && StringUtils.isNotEmpty(String.valueOf(responseMap.get(Constants.Gp.RESELLERINFO))))
		{
			LinkedHashMap resellerInfo = (LinkedHashMap) responseMap.get(Constants.Gp.RESELLERINFO);
			if (Objects.nonNull(resellerInfo.get(Constants.Gp.RESELLERDATA)) && StringUtils.isNotEmpty(String.valueOf(resellerInfo.get(Constants.Gp.RESELLERDATA))))
			{
				LinkedHashMap resellerData = (LinkedHashMap) resellerInfo.get(Constants.Gp.RESELLERDATA);
				if (Objects.nonNull(resellerData.get(Constants.Gp.ACCOUNTS)))
				{
					List<Map> accountInformation = (List<Map>) resellerData.get(Constants.Gp.ACCOUNTS);
					if (accountInformation != null && accountInformation.size() > 0)
					{
						double totalAvailableBalance = 0l;
						for (Map accountInfoMap : accountInformation)
						{
							LOGGER.info("Balance : " + accountInfoMap.get("balance"));
							if (accountInfoMap != null && accountInfoMap.containsKey(Constants.Gp.BALANCE))
							{
								LinkedHashMap balanceMap = (LinkedHashMap) accountInfoMap.get(Constants.Gp.BALANCE);
								totalAvailableBalance += (Double) balanceMap.get(Constants.Gp.VALUE);
							}
						}
						return new DecimalFormat("0.00").format(totalAvailableBalance);
					}
				}
			}
		}
		return null;
	}
	private String fetchResellersMsisdn(Map<String, Object> responseMap) {
		if (Objects.nonNull((LinkedHashMap) responseMap.get("resellerInfo")) && StringUtils.isNotEmpty(String.valueOf((LinkedHashMap) responseMap.get("resellerInfo")))) {
			LinkedHashMap resellerInfo = (LinkedHashMap) responseMap.get("resellerInfo");
			if (Objects.nonNull((LinkedHashMap) resellerInfo.get("resellerData")) && StringUtils.isNotEmpty(String.valueOf((LinkedHashMap) resellerInfo.get("resellerData")))) {
				LinkedHashMap resellerData = (LinkedHashMap) resellerInfo.get("resellerData");
				if(Objects.nonNull(resellerData.get("resellerMSISDN"))) {
					String resellerMSISDN = String.valueOf(resellerData.get("resellerMSISDN"));
					if (StringUtils.isNotEmpty(resellerMSISDN))
						return resellerMSISDN;
				}
			}
		}
		return "";
	}

	private static final class Constants
	{

		private static final String PROVIDER = "gp";
		private static final String OPERATION = "balEnquiryUSSD";

		private static final class Headers
		{
			private static final String SYSTEM_TOKEN = "system-token";
			private static final String AUTHORIZATION = "authorization";
			private static final String PROVIDER_ID = "PROVIDER_ID";
			private static final String HISTORY = "history";
			private static final String ERROR_CHANNEL = "errorChannel";
			private static final String REPLY_CHANNEL = "replyChannel";
			private static final String OPERATION = "OPERATION";
			private static final String HOST = "host";
			private static final String CACHE_CONTROL = "cache-control";
			private static final String ACCEPT_ENCODING = "accept-encoding";
			private static final String REQUEST_PAYLOAD = "requestPayload";
		}

		private static final class Gp
		{
			private static final String GP_EXT_REQUEST_TYPE = "TYPE";
			private static final String TXNSTATUS = "TXNSTATUS";
			private static final String MESSAGE = "MESSAGE";
			private static final String IS_REQUEST_TYPE_TEXT = "IS_REQUEST_TYPE_TEXT";
			private static final String RESELLERINFO = "resellerInfo";
			private static final Object RESELLERDATA = "resellerData";
			private static final Object ACCOUNTS = "accounts";
			private static final Object VALUE = "value";
			private static final String MSISDN1 = "MSISDN1";
			private static final String MSISDN2 = "MSISDN2";
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String DATE_FORMAT = "dd/MM/YYYY hh:mm:ss";
			private static final String COMMAND = "COMMAND";
			private static final String BALANCE = "balance";
			private static final String REQUEST_PAYLOAD = "requestPayload";
		}

		private static final class Dms
		{
			private static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String MESSAGE = "message";
			private static final String TYPE = "type";
			private static final String ID = "id";
			private static final String DEALER_ID = "dealerID";
			private static final String FETCH_ACCOUNT_BALANCE_INFORMATION = "fetchAccountBalanceInformation";
			private static final String RESELLERMSISDN = "RESELLERMSISDN";
			private static final String RESPONSE_TYPE = "responseType";
		}
	}
}