/*
 *   Created By   : Bharath SaiSwetha
 *   Email        : saiswetha.bharath@seamless.se
 *   Created Date : 12/02/2022
 *   Time         : 01:10 PM
 *
 *   Copyright(c) 2021. Seamless Distribution Systems AB - All Rights Reserved
 *   Unauthorized copying of this file, via any medium is strictly prohibited. It is proprietary and confidential.
 *
 */

package com.seamless.ers.standardlink.transformers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seamless.ers.standardlink.config.StandardLinkConfig;
import com.seamless.ers.standardlink.model.common.ERSIOTransformer;
import com.seamless.ers.standardlink.model.common.ResultCode;
import com.seamless.ers.standardlink.model.common.constants.StandardLinkConstants;
import com.seamless.ers.standardlink.model.configs.ProviderConfigurations;
import com.seamless.ers.standardlink.model.customer.gp.setpin.request.ChangeServicePassword;
import com.seamless.ers.standardlink.model.customer.gp.setpin.request.Context;
import com.seamless.ers.standardlink.model.customer.gp.setpin.request.ObjectFactory;
import com.seamless.ers.standardlink.model.exception.ClientException;
import com.seamless.ers.standardlink.model.exception.TransformerException;
import com.seamless.ers.standardlink.model.exception.ValidationException;
import com.seamless.ers.standardlink.model.internal.StandardLinkResultCodes;
import com.seamless.ers.standardlink.utilities.SendSmsNotificationUtility;
import com.seamless.ers.standardlink.utilities.StandardLinkUtilities;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Component
public class SetPinUssdTransformer implements ERSIOTransformer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(SetPinUssdTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Autowired
	private SendSmsNotificationUtility smsNotificationUtility;

	Object smsReceiverMsisdn = null;
	String newPin = "";
	private Object language;

	@Override
	public Message<?> transformOutboundSoapRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("SetPinUssdTransformer :: transformOutboundSoapRequest");
		try
		{
			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			Map<String, Object> command = (Map<String, Object>) requestFields.get(Constants.Gp.COMMAND);
			if (command != null)
			{
				requestFields.putAll(command);
			}
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<String> constants = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getConstants();

			String msisdn1 = requestFields.get(Constants.Txe.MSISDN).toString();
			smsReceiverMsisdn = Objects.nonNull(msisdn1) ? msisdn1 : "";
			String currentPin = requestFields.get(Constants.Txe.PIN).toString();
			newPin = requestFields.get(Constants.Txe.NEWPIN).toString();
			String confirmPin = requestFields.get(Constants.Txe.CONFIRMPIN).toString();


			ObjectFactory setPinReqObjMapper = new ObjectFactory();
			Context.PrincipalId contextPrincipalId = setPinReqObjMapper.createContextPrincipalId();
			contextPrincipalId.setId(msisdn1);
			contextPrincipalId.setType(constants.get(Constants.SUBSCRIBER_PRINCIPAL));
			Context context = setPinReqObjMapper.createContext();
			context.setPassword(currentPin);

			if (message.getHeaders().containsKey(Constants.SYSTEM_TOKEN))
			{
				String systemToken = (String) message.getHeaders().get(Constants.SYSTEM_TOKEN);
				ObjectMapper objectMapper = new ObjectMapper();
				Map<String, Map> systemTokenMap = objectMapper.readValue(String.valueOf(message.getHeaders().get(StandardLinkConstants.Headers.SYSTEM_TOKEN)), Map.class);

				try
				{
					JSONObject systemTokenJsonObj = new JSONObject(systemToken);
					Object ersReferenceNumber = systemTokenJsonObj.get(Constants.Txe.ERSREFERENCE);
					context.setReferredChainErsReference(ersReferenceNumber);

					if (systemTokenMap != null && systemTokenMap.size() > 0){
						Map<String, String> initiatorInfo = (Map) systemTokenMap.get(SetPinUssdTransformer.Constants.Txe.CONTEXT).get(SetPinUssdTransformer.Constants.Txe.INITIATOR);
						language = initiatorInfo.get(SetPinUssdTransformer.Constants.Txe.LANGUAGE);
					}

				}
				catch (Exception exception)
				{
					throw new ValidationException(StandardLinkResultCodes.TRANSFORMATION_ERROR, "Invalid system token found during request transformation : " + exception.getMessage());
				}
			}

			if (!newPin.equals(confirmPin))
			{
				return MessageBuilder.withPayload(new ChangeServicePassword())
						.copyHeaders(message.getHeaders())
						.setHeaderIfAbsent(StandardLinkConstants.Headers.VALIDATION, StandardLinkConstants.Headers.PASSED)
						.setHeader(Constants.Gp.IS_REQUEST_TYPE_TEXT, requestFields.getOrDefault(Constants.Gp.IS_REQUEST_TYPE_TEXT, "false"))
						.build();
			}

			context.setPrincipalId(contextPrincipalId);
			ChangeServicePassword changeServicePassword = setPinReqObjMapper.createChangeServicePassword();
			changeServicePassword.setContext(context);
			changeServicePassword.setNewPassword(newPin);
			changeServicePassword.setServiceId(Constants.SERVICE_NAME);

			return MessageBuilder.withPayload(changeServicePassword)
					.copyHeaders(message.getHeaders())
					.setHeaderIfAbsent(StandardLinkConstants.Headers.VALIDATION, StandardLinkConstants.Headers.PASSED)
					.setHeader(Constants.Gp.IS_REQUEST_TYPE_TEXT, requestFields.getOrDefault(Constants.Gp.IS_REQUEST_TYPE_TEXT, "false"))
					.build();
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
	public Object transformInboundSoapResponse(Object outgoingResponse) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("SetPinUssdTransformer :: transformInboundSoapResponse");
		LinkedCaseInsensitiveMap<Object> response = new LinkedCaseInsensitiveMap<>();
		Map<String, Object> request = new LinkedHashMap<>();

		try
		{
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();

			LinkedCaseInsensitiveMap<Object> defaultFailMessage = (LinkedCaseInsensitiveMap<Object>) providerConfigurations.getOperations().get(SetPinUssdTransformer.Constants.Gp.OPERATION).getFields().get(language);
			Object smsNotificationMessage = defaultFailMessage.get("defaultMessage");

			response.put(Constants.Gp.GP_EXT_REQUEST_TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));
			response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));

			if (outgoingResponse instanceof CompletableFuture)
			{
				outgoingResponse = ((CompletableFuture<?>) outgoingResponse).get();
			}

			if (outgoingResponse instanceof ClientException)
			{
				ValidationException exception = (ValidationException) outgoingResponse;
				Integer resultCodeValue = Integer.valueOf(String.valueOf(exception.getResultCode()));
				String defaultMessage = exception.getMessage();
				ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);

				if (null == resultCode)
				{
					response.put(Constants.Gp.TXNSTATUS, Constants.Txe.FAILED);
				}
				else
				{
					response.put(Constants.Gp.TXNSTATUS, resultCode.getDescription());
				}
			}
			else if (outgoingResponse instanceof Exception)
			{
				Exception exception = (Exception) outgoingResponse;
				response.put(Constants.Gp.GP_MESSAGE, StandardLinkUtilities.getRootCause(exception));
				response.put(Constants.Gp.TXNSTATUS, StandardLinkResultCodes.DIFFERENT_PIN_ERROR);
				return response;
			}

			Map<String, Object> changeServicePasswordResponse = (Map<String, Object>) outgoingResponse;
			Map<String, Object> returnMap = (Map<String, Object>) changeServicePasswordResponse.get("return");
			if (returnMap!=null)
			{
				request = (Map<String, Object>) changeServicePasswordResponse.get(Constants.Gp.REQUEST_PAYLOAD);
				Map<String, Object> requestPayloadCommand = (Map<String, Object>) request.get(Constants.Gp.COMMAND);

				if (requestPayloadCommand != null)
				{
					request.putAll(requestPayloadCommand);
				}

				Integer resultCodeValue = Integer.valueOf(returnMap.get(Constants.Txe.RESULT_CODE).toString());
				String defaultMessage = String.valueOf(returnMap.get(Constants.Txe.RESULT_MESSAGE) != null ? returnMap.get(Constants.Txe.RESULT_MESSAGE).toString() : returnMap.get(Constants.Txe.MESSAGE) != null ? returnMap.get(Constants.Txe.MESSAGE) : returnMap.get(Constants.Txe.RESULT_DESCRIPTION));

				response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));
				ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
				response.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
				response.put(Constants.Gp.TXNSTATUS, resultCode.getInternalResultCode());
				ResultCode smsNotificationResultCode = standardLinkConfig.getResultCodeBasedOnLanguage(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage, String.valueOf(language));

				smsNotificationMessage = smsNotificationResultCode.getDescription();

				if (String.valueOf(resultCodeValue).equalsIgnoreCase(String.valueOf(StandardLinkResultCodes.SUCCESS)))
				{
					smsNotificationMessage = smsNotificationMessage + " " + newPin;
				}
			}
			else
			{
				response.put(Constants.Gp.GP_MESSAGE, changeServicePasswordResponse.get(Constants.Txe.MESSAGE));
				response.put(Constants.Gp.TXNSTATUS, StandardLinkResultCodes.FAILED_ON_SERVER);
			}
//			smsNotificationUtility.sendSmsNotification("", String.valueOf(smsReceiverMsisdn), String.valueOf(smsNotificationMessage));
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
		private static final String SERVICE_NAME = "serviceType";
		private static final String SUBSCRIBER_PRINCIPAL = "subscriberPrincipal";
		private static final String SYSTEM_TOKEN = "system-token";

		private static final class Gp
		{
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String DATE_FORMAT = "dd/MM/YYYY hh:mm:ss";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String PROVIDER = "gp";
			private static final String OPERATION = "setPinUssd";
			private static final String COMMAND = "COMMAND";
			private static final String TXNSTATUS = "TXNSTATUS";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String GP_EXT_REQUEST_TYPE = "TYPE";
			private static final String IS_REQUEST_TYPE_TEXT = "IS_REQUEST_TYPE_TEXT";
			private static final String REQUEST_PAYLOAD = "requestPayload";
		}

		private static final class Txe
		{
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String RESULT_CODE = "resultCode";
			private static final String MESSAGE = "message";
			private static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String ERSREFERENCE = "ersReference";
			private static final String MSISDN = "MSISDN1";
			private static final String PIN = "PIN";
			private static final String NEWPIN = "NEWPIN";
			private static final String FAILED = "Failed";
			private static final String CONFIRMPIN = "CONFIRMPIN";
			private static final String CONTEXT = "context";
			private static final String INITIATOR = "initiator";
			private static final String LANGUAGE = "language";
		}
	}
}

