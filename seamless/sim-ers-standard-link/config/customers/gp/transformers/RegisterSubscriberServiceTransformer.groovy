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

import com.seamless.ers.standardlink.config.StandardLinkConfig;
import com.seamless.ers.standardlink.model.common.ERSIOTransformer;
import com.seamless.ers.standardlink.model.common.ResultCode;
import com.seamless.ers.standardlink.model.common.constants.StandardLinkConstants;
import com.seamless.ers.standardlink.model.configs.ProviderConfigurations;
import com.seamless.ers.standardlink.model.customer.gp.registerSubscriber.request.ActivateService;
import com.seamless.ers.standardlink.model.customer.gp.registerSubscriber.request.Context;
import com.seamless.ers.standardlink.model.exception.TransformerException;
import com.seamless.ers.standardlink.model.internal.StandardLinkResultCodes;
import com.seamless.ers.standardlink.utilities.SendSmsNotificationUtility;
import com.seamless.ers.standardlink.utilities.StandardLinkUtilities;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmPoint;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;

import javax.ws.rs.client.AsyncInvoker;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Component
public class RegisterSubscriberServiceTransformer implements ERSIOTransformer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterSubscriberServiceTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Autowired
	private SendSmsNotificationUtility smsNotificationUtility;

	Object smsReceiverMsisdn = null;

	@Override
	public Message<?> transformOutboundSoapRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("RegisterSubscriberServiceTransformer :: transformOutboundSoapRequest");

		try
		{
			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			Map<String, Object> command = (Map<String, Object>) requestFields.get(Constants.Gp.COMMAND);
			if (command != null)
			{
				requestFields.putAll(command);
			}
			LOGGER.info("Forming TXE Promo bill request for operation " + requestFields.get(Constants.Gp.PROVIDER));

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			Map<String, Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();
			ActivateService activateService = new ActivateService();
			Context context = new Context();
			Context.PrincipalId principalId = new Context.PrincipalId();

			if (Objects.nonNull(requestFields.get(Constants.Gp.MSISDN1)) && !requestFields.get(Constants.Gp.MSISDN1).toString().isEmpty())
			{
				principalId.setId(requestFields.get(Constants.Gp.MSISDN1));
				principalId.setType(Constants.Txe.SUBSCRIBER_MSISDN);
			}

			if (Objects.nonNull(requestFields.get(Constants.Gp.PIN)) && !StringUtils.isEmpty(String.valueOf(requestFields.get(Constants.Gp.PIN))))
			{
				context.setPassword(requestFields.get(Constants.Gp.PIN).toString());
			}
			else
			{
				context.setPassword(operationFields.get(Constants.Gp.DEFAULT_PIN));
			}

			context.setPrincipalId(principalId);

			if (message.getHeaders().containsKey(StandardLinkConstants.Headers.SYSTEM_TOKEN))
			{

				LOGGER.info("Found system-token in headers ");
				String systemToken = (String) message.getHeaders().get(StandardLinkConstants.Headers.SYSTEM_TOKEN);

				try
				{
					int start = systemToken.indexOf("\"" + Constants.Txe.ERS_REFERENCE + "\"") + ("\"" + Constants.Txe.ERS_REFERENCE + "\"").length() + 1;
					int uidStart = systemToken.indexOf("\"", start);
					int uidEnd = systemToken.indexOf("\"", uidStart + 1);

					String ersReferenceNumber = systemToken.substring(uidStart + 1, uidEnd);
					context.setReferredChainErsReference(ersReferenceNumber);

				}
				catch (Exception exception)
				{
					LOGGER.error("Failed to extract system-token {} exception stack  trace {} ", systemToken,
							ExceptionUtils.getFullStackTrace(exception));
				}
			}

			activateService.setServiceId(operationFields.get(Constants.Txe.SERVICE_TYPE));
			activateService.setContext(context);

			return MessageBuilder.withPayload(activateService)
					.copyHeaders(message.getHeaders())
					.setHeader(Constants.Gp.IS_REQUEST_TYPE_TEXT, requestFields.get(Constants.Gp.IS_REQUEST_TYPE_TEXT))
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
	public Object transformInboundSoapResponse(Object incomingResponse) throws TransformerException
	{

		EtmPoint point = EtmManager.getEtmMonitor().createPoint("RegisterSubscriberServiceTransformer :: transformInboundSoapResponse");

		Map<String, Object> response = new LinkedHashMap<>();
		Map<String, Object> request;
		ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
		LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();

		response.put(Constants.Gp.TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));
		response.put(Constants.Gp.DATE, simpleDateFormat.format(new Date()));

		try
		{
			if (incomingResponse instanceof CompletableFuture)
			{
				incomingResponse = ((CompletableFuture<?>) incomingResponse).get();
			}
			if (incomingResponse instanceof Exception)
			{
				Exception exception = (Exception) incomingResponse;
				response.put(Constants.Gp.GP_MESSAGE, StandardLinkUtilities.getRootCause(exception));
				response.put(Constants.Gp.TXNSTATUS, StandardLinkResultCodes.INTERNAL_COMPONENT_ERROR);
				return response;
			}
			if (!incomingResponse.equals(null))
			{
				Map<String, Object> activateServiceResponse = (Map<String, Object>) incomingResponse;
				Map<String, Object> returnMap = (Map<String, Object>) activateServiceResponse.get("return");
				request = (Map<String, Object>) activateServiceResponse.get(Constants.Gp.REQUEST_PAYLOAD);
				Map<String, Object> requestPayloadCommand = (Map<String, Object>) request.get(Constants.Gp.COMMAND);
				Object smsNotificationMessage = returnMap.get("resultMessage");
				if (requestPayloadCommand != null)
				{
					request.putAll(requestPayloadCommand);
				}

				if (request != null)
				{
					Integer resultCodeValue = (Integer) returnMap.get(Constants.Txe.RESULT_CODE);
					String defaultMessage = String.valueOf(returnMap.get(Constants.Txe.RESULT_MESSAGE) != null ? returnMap.get(Constants.Txe.RESULT_MESSAGE).toString() : returnMap.get(Constants.Txe.MESSAGE) != null ? returnMap.get(Constants.Txe.MESSAGE) : returnMap.get(Constants.Txe.RESULT_DESCRIPTION));
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
					response.put(Constants.Gp.TXNSTATUS, resultCode.getInternalResultCode());
					smsNotificationMessage = resultCode.getDescription() + " " + smsReceiverMsisdn;
				}
				else
				{
					response.put(Constants.Gp.GP_MESSAGE, "Request has failed on server.");
					response.put(Constants.Gp.TXNSTATUS, StandardLinkResultCodes.FAILED_ON_SERVER);
					smsNotificationMessage = "With " + smsReceiverMsisdn + " " + smsNotificationMessage;
				}
				//smsNotificationUtility.sendSmsNotification(null, String.valueOf(smsReceiverMsisdn), String.valueOf(smsNotificationMessage));
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
			private static final String DATE = "DATE";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String DATE_FORMAT = "dd/MM/YYYY hh:mm:ss";
			private static final String PROVIDER = "gp";
			private static final String OPERATION = "registerSubscriberService";
			private static final String DEFAULT_PIN = "defaultPIN";
			private static final String COMMAND = "COMMAND";
			private static final String MSISDN1 = "MSISDN1";
			private static final String TXNSTATUS = "TXNSTATUS";
			private static final String TYPE = "TYPE";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String PIN = "PIN";
			private static final String IS_REQUEST_TYPE_TEXT = "IS_REQUEST_TYPE_TEXT";
			private static final String REQUEST_PAYLOAD = "requestPayload";
		}

		private static final class Txe
		{
			private static final Object RESULT_DESCRIPTION = "result";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String MESSAGE = "message";
			private static final String SUBSCRIBER_MSISDN = "SUBSCRIBERMSISDN";
			private static final String SERVICE_TYPE = "serviceType";
			private static final String ERS_REFERENCE = "ersReference";
			private static final String RESULT_CODE = "resultCode";
		}
	}
}