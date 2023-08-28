/*
 *   Created By   : Bhupad Rathi
 *   Email        : bhupad.rathi@seamless.se
 *   Created Date : 19/02/2022
 *   Time         : 01:10 PM
 *
 *   Copyright(c) 2021. Seamless Distribution Systems AB - All Rights Reserved
 *   Unauthorized copying of this file, via any medium is strictly prohibited. It is proprietary and confidential.
 *
 */

package com.seamless.ers.standardlink.transformers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seamless.common.StringUtils;
import com.seamless.ers.standardlink.config.StandardLinkConfig;
import com.seamless.ers.standardlink.model.common.ERSIOTransformer;
import com.seamless.ers.standardlink.model.common.ResultCode;
import com.seamless.ers.standardlink.model.common.constants.StandardLinkConstants;
import com.seamless.ers.standardlink.model.configs.ProviderConfigurations;
import com.seamless.ers.standardlink.model.customer.gp.deregisterSubscriberService.request.Envelope;
import com.seamless.ers.standardlink.model.exception.TransformerException;
import com.seamless.ers.standardlink.model.internal.StandardLinkResultCodes;
import com.seamless.ers.standardlink.utilities.SendSmsNotificationUtility;
import com.seamless.ers.standardlink.utilities.StandardLinkUtilities;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmPoint;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.seamless.ers.standardlink.model.common.constants.StandardLinkConstants.Fields.OPERATION_ID;

@Slf4j
@Component
public class DeregisterSubscriberServiceTransformer implements ERSIOTransformer
{

	@Autowired
	private SendSmsNotificationUtility smsNotificationUtility;

	@Autowired
	private StandardLinkConfig standardLinkConfig;
	private Object language;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);

	@Override
	public Message<?> transformOutboundSoapRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("DeregisterSubscriberServiceTransformer :: transformOutboundSoapRequest");
		try
		{
			LinkedCaseInsensitiveMap<Object> requestMap = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LOGGER.info(" Transforming request for operation {} ", requestMap.get(OPERATION_ID));

			Map<String, Object> requestBodyFields = (Map<String, Object>) requestMap.get(Constants.Gp.COMMAND);
			if (requestBodyFields != null)
			{
				requestMap.putAll(requestBodyFields);
			}

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<String> constants = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getConstants();

			Envelope.Body.UpdateSubscriberServiceStatus updateSubscriberServiceStatus = new Envelope.Body.UpdateSubscriberServiceStatus();
			String msisdn1 = requestMap.get(Constants.Txe.MSISDN).toString();
			Envelope.Body.UpdateSubscriberServiceStatus.SubscriberId subscriberId = new Envelope.Body.UpdateSubscriberServiceStatus.SubscriberId();
			Envelope.Body.UpdateSubscriberServiceStatus.Context context = new Envelope.Body.UpdateSubscriberServiceStatus.Context();
			if (message.getHeaders().containsKey(StandardLinkConstants.Headers.SYSTEM_TOKEN))
			{
				LOGGER.error("Found system-token in headers.");
				String systemToken = (String) message.getHeaders().get(StandardLinkConstants.Headers.SYSTEM_TOKEN);
				ObjectMapper objectMapper = new ObjectMapper();
				Map<String, Map> systemTokenMap = objectMapper.readValue(String.valueOf(message.getHeaders().get(StandardLinkConstants.Headers.SYSTEM_TOKEN)), Map.class);

				try
				{
					int start = systemToken.indexOf("\"" + Constants.Txe.ERS_REFERENCE + "\"") + ("\"" + Constants.Txe.ERS_REFERENCE + "\"").length() + 1;
					int uidStart = systemToken.indexOf("\"", start);
					int uidEnd = systemToken.indexOf("\"", uidStart + 1);
					String ersReferenceNumber = systemToken.substring(uidStart + 1, uidEnd);
					context.setReferredChainErsReference(ersReferenceNumber);


					if (systemTokenMap != null && systemTokenMap.size() > 0){
						Map<String, String> initiatorInfo = (Map) systemTokenMap.get(Constants.Txe.CONTEXT).get(Constants.Txe.INITIATOR);
						language = initiatorInfo.get(Constants.Txe.LANGUAGE);
					}
				}
				catch (Exception exception)
				{
					LOGGER.error("Failed to extract system-token {} exception stack  trace {} ", systemToken, ExceptionUtils.getFullStackTrace(exception));
				}
			}
			subscriberId.setValue(msisdn1);
			updateSubscriberServiceStatus.setSubscriberId(subscriberId);
			if (requestMap.containsKey(Constants.Txe.PIN) && StringUtils.isNotBlank(requestMap.get(Constants.Txe.PIN).toString()))
			{
				String pin = requestMap.get(Constants.Txe.PIN).toString();
				Envelope.Body.UpdateSubscriberServiceStatus.Password password = new Envelope.Body.UpdateSubscriberServiceStatus.Password();
				password.setValue(pin);
				updateSubscriberServiceStatus.setPassword(password);
				context.setPassword(pin);
			}
			Envelope.Body.UpdateSubscriberServiceStatus.ServiceId serviceId = new Envelope.Body.UpdateSubscriberServiceStatus.ServiceId();
			serviceId.setValue(constants.get(Constants.SERVICE_NAME));
			updateSubscriberServiceStatus.setServiceId(serviceId);
			Envelope.Body.UpdateSubscriberServiceStatus.Status status = new Envelope.Body.UpdateSubscriberServiceStatus.Status();
			status.setValue(constants.get(Constants.DEACTIVATE_STATUS));
			updateSubscriberServiceStatus.setStatus(status);
			Envelope.Body.UpdateSubscriberServiceStatus.Context.PrincipalId principalId = new Envelope.Body.UpdateSubscriberServiceStatus.Context.PrincipalId();
			Envelope.Body.UpdateSubscriberServiceStatus.Context.PrincipalId.Id id = new Envelope.Body.UpdateSubscriberServiceStatus.Context.PrincipalId.Id();
			id.setValue(msisdn1);
			Envelope.Body.UpdateSubscriberServiceStatus.Context.PrincipalId.Type type = new Envelope.Body.UpdateSubscriberServiceStatus.Context.PrincipalId.Type();
			type.setValue(constants.get(Constants.SUBSCRIBER_PRINCIPAL));
			principalId.setId(id);
			principalId.setType(type);
			context.setPrincipalId(principalId);
			updateSubscriberServiceStatus.setContext(context);
			return MessageBuilder.withPayload(updateSubscriberServiceStatus).copyHeaders(message.getHeaders()).setHeaderIfAbsent(StandardLinkConstants.Headers.VALIDATION, StandardLinkConstants.Headers.PASSED).setHeader(Constants.Gp.IS_REQUEST_TYPE_TEXT, requestMap.get(Constants.Gp.IS_REQUEST_TYPE_TEXT)).build();
		}
		catch (Exception e)
		{
			LOGGER.error("An error occurred during transformation {} ", ExceptionUtils.getFullStackTrace(e));
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
	public Object transformInboundSoapResponse(Object incomingResponse) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("DeregisterSubscriberServiceTransformer :: transformInboundSoapResponse");
		Map<String, Object> deregisterSubscriberServiceResponse = new LinkedHashMap<>();
		try
		{
			Map<String, Object> request;
			LOGGER.debug(" transforming incoming response body {} for operation {} ", incomingResponse, Constants.Gp.OPERATION);
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();
			LinkedCaseInsensitiveMap<Object> defaultFailMessage = (LinkedCaseInsensitiveMap<Object>) providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields().get(language);

			Object smsNotificationMessage = defaultFailMessage.get("defaultMessage");

			deregisterSubscriberServiceResponse.put(Constants.Gp.GP_EXT_REQUEST_TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));

			if (incomingResponse instanceof CompletableFuture)
			{
				incomingResponse = ((CompletableFuture<?>) incomingResponse).get();
			}
			deregisterSubscriberServiceResponse.put(Constants.Gp.GP_EXT_REQUEST_TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));
			deregisterSubscriberServiceResponse.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));

			if (incomingResponse instanceof Exception)
			{
				Exception exception = (Exception) incomingResponse;
				ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(StandardLinkResultCodes.FAILED_ON_SERVER), String.valueOf(StandardLinkResultCodes.FAILED_ON_SERVER), StandardLinkUtilities.getRootCause(exception));
				LOGGER.error(" Transaction failed provider {} operation {} incoming  response {} ", Constants.Gp.PROVIDER, Constants.Gp.OPERATION, incomingResponse);
				deregisterSubscriberServiceResponse.put(Constants.Gp.TXNSTATUS, resultCode.getDescription());
			}
			Map<String, Object> internalResponse = (Map<String, Object>) incomingResponse;

			if (!internalResponse.isEmpty() && internalResponse.containsKey(Constants.Txe.RETURN))
			{
				LinkedHashMap<String,Object> responseReturn = (LinkedHashMap<String, Object>) internalResponse.get(Constants.Txe.RETURN);
				Integer resultCodeValue = (Integer) responseReturn.get(Constants.Txe.RESULT_CODE);
				String defaultMessage = String.valueOf(responseReturn.get(Constants.Txe.RESULT_MESSAGE) != null ? responseReturn.get(Constants.Txe.RESULT_MESSAGE) : responseReturn.get(Constants.Txe.MESSAGE));

				ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
				ResultCode smsNotificationResultCode = standardLinkConfig.getResultCodeBasedOnLanguage(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage,String.valueOf(language));
				if (resultCodeValue == StandardLinkResultCodes.SUCCESS)
				{
					deregisterSubscriberServiceResponse.put(Constants.Gp.MESSAGE, resultCode.getDescription());
					deregisterSubscriberServiceResponse.put(Constants.Gp.TXNSTATUS, resultCode.getInternalResultCode());
					request = (Map<String, Object>) internalResponse.get(Constants.Gp.REQUEST_PAYLOAD);
					Map<String, Object> requestPayloadCommand = (Map<String, Object>) request.get(Constants.Gp.COMMAND);
					BigInteger msisdn1;
					if (requestPayloadCommand != null)
					{
						request.putAll(requestPayloadCommand);
						msisdn1 = new BigInteger(requestPayloadCommand.get(Constants.Txe.MSISDN).toString());
					}else{
						msisdn1 = new BigInteger(request.get(Constants.Txe.MSISDN).toString());
					}
					String smsReceiverMsisdn = Objects.nonNull(msisdn1)? msisdn1.toString() : "";
					smsNotificationMessage = smsNotificationResultCode.getDescription() + " " + smsReceiverMsisdn;
					smsNotificationUtility.sendSmsNotification("", String.valueOf(msisdn1), String.valueOf(smsNotificationMessage));
					return deregisterSubscriberServiceResponse;
				}
				else
				{
					deregisterSubscriberServiceResponse.put(Constants.Gp.MESSAGE, resultCode.getDescription());
					deregisterSubscriberServiceResponse.put(Constants.Gp.TXNSTATUS, resultCode.getInternalResultCode());
				}
			}
			else
			{
				deregisterSubscriberServiceResponse.put(Constants.Gp.MESSAGE, "Request has failed on server.");
				deregisterSubscriberServiceResponse.put(Constants.Gp.TXNSTATUS, StandardLinkResultCodes.FAILED_ON_SERVER);
			}

		}
		catch (Exception e)
		{
			LOGGER.error("An error occurred during response transformation : {}", ExceptionUtils.getFullStackTrace(e));
			throw new TransformerException(StandardLinkResultCodes.TRANSFORMATION_ERROR, "An error occurred during response transformation : " + e.getMessage());
		}
		finally
		{
			if (point != null)
			{
				point.collect();
			}
		}
		return deregisterSubscriberServiceResponse;
	}

	private static final class Constants
	{
		private static final String SERVICE_NAME = "serviceType";
		private static final String DEACTIVATE_STATUS = "deactivateStatus";
		private static final String SUBSCRIBER_PRINCIPAL = "subscriberPrincipal";

		private static final class Gp
		{
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String DATE_FORMAT = "dd/MM/YYYY hh:mm:ss";
			private static final String MESSAGE = "MESSAGE";
			private static final String TXNSTATUS = "TXNTATUS";
			private static final String PROVIDER = "gp";
			private static final String OPERATION = "deregisterSubscriberService";
			private static final String COMMAND = "COMMAND";
			private static final String GP_EXT_REQUEST_TYPE = "TYPE";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String IS_REQUEST_TYPE_TEXT = "IS_REQUEST_TYPE_TEXT";
			private static final String REQUEST_PAYLOAD = "requestPayload";
		}

		private static final class Txe
		{
			private static final String MSISDN = "MSISDN1";
			private static final String PIN = "PIN";
			private static final String FAILED = "Failed";
			private static final String ERS_REFERENCE = "ersReference";
			private static final String RESULT_CODE = "resultCode";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String MESSAGE = "message";
			private static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String RETURN = "return";
			private static final String CONTEXT = "context";
			private static final String INITIATOR = "initiator";
			private static final String LANGUAGE = "language";
		}
	}
}
