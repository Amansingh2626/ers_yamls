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
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component
public class PromoBillPaymentTransformer implements ERSIOTransformer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(PromoBillPaymentTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Override
	public Message<?> transformInboundRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("PromoBillPaymentTransformer :: transformInboundRequest");
		try
		{
            LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.COMMAND);

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();
			LinkedCaseInsensitiveMap<String> constants = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getConstants();

			Map<String, Object> promoBillPaymentRequest = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> sender = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> receiver = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> product = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> amount = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> transactionProperties = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> map = Collections.synchronizedMap(new LinkedHashMap<>());

			if (Objects.nonNull(command.get(Constants.Gp.MSISDN1)) && !command.get(Constants.Gp.MSISDN1).toString().isEmpty())
			{
				sender.put(StandardLinkConstants.TXEConstant.ID, String.valueOf(command.get(Constants.Gp.MSISDN1)));
				sender.put(StandardLinkConstants.TXEConstant.TYPE, Constants.Txe.RESELLERMSISDN);
			}
			else
			{
				sender.put(StandardLinkConstants.TXEConstant.ID, command.get(Constants.Gp.LOGINID));
				sender.put(StandardLinkConstants.TXEConstant.TYPE, Constants.Txe.RESELLERID);
			}
			sender.put(StandardLinkConstants.TXEConstant.ACCOUNT_TYPE_ID, String.valueOf(operationFields.get(Constants.Gp.RESELLER_ACCOUNT_ID)));
			if (!command.get(Constants.Gp.MSISDN2).toString().isEmpty())
			{
				receiver.put(StandardLinkConstants.TXEConstant.ID, String.valueOf(command.get(Constants.Gp.MSISDN2)));
				receiver.put(StandardLinkConstants.TXEConstant.TYPE, Constants.Txe.SUBSCRIBERMSISDN);
			}
			else
			{
				receiver.put(StandardLinkConstants.TXEConstant.ID, command.get(Constants.Gp.LOGINID2));
				receiver.put(StandardLinkConstants.TXEConstant.TYPE, Constants.Txe.RESELLERID);
			}
			receiver.put(StandardLinkConstants.TXEConstant.ACCOUNT_TYPE_ID, operationFields.get(Constants.Gp.RECEIVER_ACCOUNT_ID));

			amount.put(StandardLinkConstants.TXEConstant.VALUE, command.get(Constants.Gp.AMOUNT));
			amount.put(StandardLinkConstants.TXEConstant.CURRENCY, constants.get(Constants.Gp.CURRENCY));
			product.put(StandardLinkConstants.TXEConstant.PRODUCT_SKU, operationFields.get(Constants.Txe.TOPUP));
			product.put(StandardLinkConstants.TXEConstant.AMOUNT, amount);

			//Populating additional transaction fields
			map.put(Constants.Txe.TYPE, command.get(Constants.Gp.GP_EXT_REQUEST_TYPE));
			map.put(Constants.Txe.EXT_TXNDATE, command.get(Constants.Gp.DATE));
			map.put(Constants.Txe.GP_EXT_REQUEST_TYPE, command.get(Constants.Gp.GP_EXT_REQUEST_TYPE));
			map.put(Constants.Txe.GP_EXT_REQUEST_DATE, command.get(Constants.Gp.GP_EXT_REQUEST_DATE));
			map.put(Constants.Txe.EXTNWCODE, command.get(Constants.Gp.EXTNWCODE));

			if (sender.get(Constants.Txe.TYPE).toString().equalsIgnoreCase(Constants.Txe.RESELLERMSISDN))
			{
				map.put(Constants.Txe.SENDER_LOGINID, command.get(Constants.Gp.LOGINID));
				map.put(Constants.Txe.SENDER_PASSWORD, command.get(Constants.Gp.PASSWORD));
			}
			else
			{
				map.put(Constants.Txe.SENDER_MSISDN, command.get(Constants.Gp.MSISDN1));
				map.put(Constants.Txe.SENDER_PIN, command.get(Constants.Gp.PIN));
			}
			map.put(Constants.Txe.SENDER_EXTCODE, command.get(Constants.Gp.EXTCODE));
			map.put(Constants.Txe.CLIENT_REFERENCE, command.get(Constants.Gp.EXTREFNUM));
			map.put(Constants.Txe.PREFERRED_LANGUAGE, String.valueOf(command.get(Constants.Gp.LANGUAGE1)));
			map.put(Constants.Txe.PREFERRED_LANGUAGE2, String.valueOf(command.get(Constants.Gp.LANGUAGE2)));
			map.put(Constants.Txe.SELECTOR, String.valueOf(command.get(Constants.Gp.SELECTOR)));

			for (String key : command.keySet())
			{
				if (key.startsWith("EXTERNALDATA"))
				{
					map.put(key, command.get(key));
				}
			}

			map.put(Constants.Gp.GW_LOGINID, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.LOGIN)).get(0)).get(0));
			map.put(Constants.Gp.GW_PASSWORD, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.PASSWORD)).get(0)).get(0));
			map.put(Constants.Gp.GW_CODE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.REQUEST_GATEWAY_CODE)).get(0)).get(0));
			map.put(Constants.Gp.GW_TYPE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.REQUEST_GATEWAY_TYPE)).get(0)).get(0));
			map.put(Constants.Gp.GW_PORT, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.SERVICE_PORT)).get(0)).get(0));
			map.put(Constants.Gp.GW_SOURCE_TYPE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.SOURCE_TYPE)).get(0)).get(0));
			map.put(Constants.Txe.BONUS, command.get(Constants.Gp.BONUS));

			transactionProperties.put(Constants.Txe.MAP, map);

			promoBillPaymentRequest.put(Constants.Txe.SELLER, sender);
			promoBillPaymentRequest.put(Constants.Txe.TOPUPPRINCIPALID, receiver);
			promoBillPaymentRequest.put(Constants.Txe.PRODUCT, product);
			promoBillPaymentRequest.put(Constants.Txe.TRANSACTION_PROPERTIES, transactionProperties);

			return MessageBuilder.withPayload(promoBillPaymentRequest).copyHeaders(message.getHeaders()).setHeaderIfAbsent(Constants.SYSTEM_TOKEN, message.getHeaders().get("system-token")).setHeaderIfAbsent("validateOnly", false).build();
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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("PromoBillPaymentTransformer :: transformOutboundResponse");

		try
		{
			Map<String, Object> response = new LinkedHashMap<>();
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();

			response.put(Constants.Gp.TYPE, operationFields.get(Constants.Txe.RESPONSE_TYPE));
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

			if (!internalResponse.isEmpty() && internalResponse.containsKey(Constants.Txe.RESULT_CODE))
			{
				Integer resultCodeValue = (Integer) internalResponse.get(Constants.Txe.RESULT_CODE);
				String defaultMessage = String.valueOf(internalResponse.get(Constants.Txe.RESULT_MESSAGE) != null ? internalResponse.get(Constants.Txe.RESULT_MESSAGE) : internalResponse.get(Constants.Txe.MESSAGE) != null ? internalResponse.get(Constants.Txe.MESSAGE) : internalResponse.get(Constants.Txe.RESULT_DESCRIPTION));
				if (resultCodeValue == StandardLinkResultCodes.SUCCESS)
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
					response.put(Constants.Gp.GP_TXN_DATE, (requestPayloadCommand.get(Constants.Gp.DATE).toString()));
					response.put(Constants.Gp.GP_TXN_ID, (internalResponse.get(Constants.Txe.ERS_REFERENCE).toString()));
					response.put(Constants.Gp.AMOUNT, (requestPayloadCommand.get(Constants.Gp.AMOUNT).toString()));
					response.put(Constants.Gp.SERVICE_TYPE, operationFields.get(Constants.Gp.SERVICE_TYPE));
					response.put(Constants.Gp.GP_MESSAGE, (resultCode.getDescription()));
				}
				else
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Gp.GP_TXN_STATUS, (resultCode.getInternalResultCode()));
					response.put(Constants.Gp.GP_MESSAGE, (resultCode.getDescription()));
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
		private static final String SYSTEM_TOKEN = "system-token";

		private static final class Gp
		{
			private static final String BONUS = "BONUS";
			private static final String LOGINID2 = "loginId2";
			private static final String PROVIDER = "gp";
			private static final String MSISDN = "MSISDN";
			private static final String LOGINID = "LOGINID";
			private static final String RESELLER_ACCOUNT_ID = "resellerAccountId";
			private static final String RECEIVER_ACCOUNT_ID = "receiverAccountId";
			private static final String MSISDN2 = "MSISDN2";
			private static final String AMOUNT = "AMOUNT";
			private static final String GP_EXT_REQUEST_TYPE = "TYPE";
			private static final String DATE = "DATE";
			private static final String GP_EXT_REQUEST_DATE = "externalRequestDate";
			private static final String EXTNWCODE = "extNwCode";
			private static final String CURRENCY = "currency";
			private static final String PASSWORD = "PASSWORD";
			private static final String MSISDN1 = "MSISDN1";
			private static final String PIN = "PIN";
			private static final String EXTCODE = "EXTCODE";
			private static final String SELECTOR = "selector";
			private static final String EXTREFNUM = "EXTREFNUM";
			private static final String LANGUAGE1 = "LANGUAGE1";
			private static final String LANGUAGE2 = "LANGUAGE2";
			private static final String GW_LOGINID = "GATEWAY_LOGINID";
			private static final String GW_PASSWORD = "GATEWAY_PASSWORD";
			private static final String GW_CODE = "REQUEST_GATEWAY_CODE";
			private static final String GW_TYPE = "REQUEST_GATEWAY_TYPE";
			private static final String GW_PORT = "SERVICE_PORT";
			private static final String GW_SOURCE_TYPE = "SOURCE_TYPE";
			private static final String DATE_FORMAT = "dd/MM/YYYY hh:mm:ss";
			private static final String COMMAND = "COMMAND";
			private static final String OPERATION = "promoBillPayment";
			private static final String LOGIN = "LOGIN";
			private static final String REQUEST_GATEWAY_CODE = "REQUEST_GATEWAY_CODE";
			private static final String REQUEST_GATEWAY_TYPE = "REQUEST_GATEWAY_TYPE";
			private static final String SERVICE_PORT = "SERVICE_PORT";
			private static final String SOURCE_TYPE = "SOURCE_TYPE";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String GP_TXN_STATUS = "TXNSTATUS";
			private static final String REQUEST_PAYLOAD = "requestPayload";
			private static final String TYPE = "TYPE";
			private static final String GP_TXN_ID = "TXNID";
			private static final String GP_TXN_DATE = "TXNDATE";
			private static final String SERVICE_TYPE = "SERVICETYPE";
		}

		private static final class Txe
		{
			private static final String BONUS = "bonus";
			private static final String SUBSCRIBERMSISDN = "SUBSCRIBERMSISDN";
			private static final String TYPE = "type";
			private static final String RESELLERMSISDN = "RESELLERMSISDN";
			private static final String RESELLERID = "RESELLERID";
			private static final String EXT_TXNDATE = "extTxnDate";
			private static final String GP_EXT_REQUEST_TYPE = "externalRequestType";
			private static final String GP_EXT_REQUEST_DATE = "externalRequestDate";
			private static final String EXTNWCODE = "extNwCode";
			private static final String SENDER_LOGINID = "senderLoginId";
			private static final String SENDER_PASSWORD = "senderPassword";
			private static final String SENDER_MSISDN = "senderMsisdn";
			private static final String SENDER_PIN = "senderPin";
			private static final String SENDER_EXTCODE = "senderExtCode";
			private static final String SELECTOR = "selector";
			private static final String CLIENT_REFERENCE = "clientReference";
			private static final String PREFERRED_LANGUAGE = "preferredLanguage";
			private static final String PREFERRED_LANGUAGE2 = "preferredLanguage2";
			private static final String MAP = "map";
			private static final String SELLER = "seller";
			private static final String TOPUPPRINCIPALID = "topUpPrincipalId";
			private static final String PRODUCT = "product";
			private static final String TRANSACTION_PROPERTIES = "transactionProperties";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String ERS_REFERENCE = "ersReference";
			private static final String TOPUP = "topUp";
			private static final String RESULT_CODE = "resultCode";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String MESSAGE = "message";
			private static final String RESULT_DESCRIPTION = "resultDescription";
		}
	}
}