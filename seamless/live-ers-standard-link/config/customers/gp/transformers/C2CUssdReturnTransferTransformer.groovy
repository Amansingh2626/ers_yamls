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
public class C2CUssdReturnTransferTransformer implements ERSIOTransformer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(C2CUssdReturnTransferTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);
	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Override
	public Message<?> transformInboundRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("C2CUssdReturnTransferTransformer :: transformOutboundRequest");

		try
		{
			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.COMMAND);

			if (command != null)
			{
				requestFields.putAll(command);
			}

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION).getFields();
			LinkedCaseInsensitiveMap<String> constants = providerConfigurations.getOperations().get(Constants.OPERATION).getConstants();

			Map<String, Object> returnTransferRequest = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> sender = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> receiver = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> product = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> amount = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> transactionProperties = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> map = Collections.synchronizedMap(new LinkedHashMap<>());

			if (Objects.nonNull(requestFields.get(Constants.Gp.MSISDN1)) && StringUtils.isNotEmpty(requestFields.get(Constants.Gp.MSISDN1).toString()))
			{
				sender.put(Constants.Txe.TYPE, Constants.Txe.RESELLERMSISDN);
				sender.put(Constants.Txe.ID, requestFields.get(Constants.Gp.MSISDN1));
			}
			else
			{
				sender.put(Constants.Txe.TYPE, Constants.Txe.RESELLERID);
				sender.put(Constants.Txe.ID, requestFields.get(Constants.Gp.LOGINID).toString());
			}
			sender.put(Constants.Txe.ACCOUNT_TYPE_ID, String.valueOf(operationFields.get(Constants.Gp.RESELLER_ACCOUNT_ID)));

			if (Objects.nonNull(requestFields.get(Constants.Gp.MSISDN2)) && StringUtils.isNotEmpty(requestFields.get(Constants.Gp.MSISDN2).toString()))
			{
				receiver.put(Constants.Txe.TYPE, Constants.Txe.RESELLERMSISDN);
				receiver.put(Constants.Txe.ID, requestFields.get(Constants.Gp.MSISDN2));
			}
			else
			{
				receiver.put(Constants.Txe.TYPE, Constants.Txe.RESELLERID);
				receiver.put(Constants.Txe.ID, requestFields.get(Constants.Gp.LOGINID2).toString());
			}
			receiver.put(Constants.Txe.ACCOUNT_TYPE_ID, String.valueOf(operationFields.get(Constants.Gp.RESELLER_ACCOUNT_ID)));

			amount.put(Constants.Txe.VALUE, requestFields.get(Constants.Gp.TOPUPVALUE));
			amount.put(Constants.Txe.CURRENCY, constants.get(Constants.Gp.CURRENCY));
			product.put(Constants.Txe.PRODUCT_SKU, operationFields.getOrDefault(Constants.Txe.PRODUCT_SKU, operationFields.get(Constants.Txe.DEFAULT_PRODUCT_SKU)));
			product.put(Constants.Txe.AMOUNT, amount);

			map.put(Constants.Txe.PREFERRED_LANGUAGE, String.valueOf(requestFields.getOrDefault(Constants.Gp.LANGUAGE1, "en")));
			map.put(Constants.Txe.GP_EXT_REQUEST_TYPE, requestFields.get(Constants.Gp.GP_EXT_REQUEST_TYPE));
			map.put(Constants.Txe.SENDER_MSISDN, requestFields.get(Constants.Gp.MSISDN1));
			map.put(Constants.Txe.SENDER_PIN, requestFields.get(Constants.Gp.PIN));
			map.put(Constants.Gp.GW_LOGINID, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.LOGIN)).get(0)).get(0));
			map.put(Constants.Gp.GW_PASSWORD, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.PASSWORD)).get(0)).get(0));
			map.put(Constants.Gp.GW_CODE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.REQUEST_GATEWAY_CODE)).get(0)).get(0));
			map.put(Constants.Gp.GW_TYPE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.REQUEST_GATEWAY_TYPE)).get(0)).get(0));
			map.put(Constants.Gp.GW_PORT, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.SERVICE_PORT)).get(0)).get(0));
			map.put(Constants.Gp.GW_SOURCE_TYPE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.SOURCE_TYPE)).get(0)).get(0));
			map.put(Constants.Gp.IS_REQUEST_TYPE_TEXT, requestFields.getOrDefault(Constants.Gp.IS_REQUEST_TYPE_TEXT,"false"));

			transactionProperties.put(Constants.Txe.MAP, map);

			returnTransferRequest.put(Constants.Txe.SENDER, sender);
			returnTransferRequest.put(Constants.Txe.RECEIVER, receiver);
			returnTransferRequest.put(Constants.Txe.PRODUCT, product);
			returnTransferRequest.put(Constants.Txe.TRANSACTION_PROPERTIES, transactionProperties);

			return MessageBuilder.withPayload(returnTransferRequest).copyHeaders(message.getHeaders()).setHeaderIfAbsent(StandardLinkConstants.Headers.SYSTEM_TOKEN, message.getHeaders().get(Constants.SYSTEM_TOKEN)).setHeaderIfAbsent(Constants.VALIDATE_ONLY, false).setHeader(Constants.Gp.IS_REQUEST_TYPE_TEXT, requestFields.get(Constants.Gp.IS_REQUEST_TYPE_TEXT)).build();
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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("C2CUssdReturnTransferTransformer :: transformOutboundResponse");
		try
		{
			LinkedCaseInsensitiveMap<Object> response = new LinkedCaseInsensitiveMap<>();
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION).getFields();

			response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));
			response.put(Constants.Gp.TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));

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
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
					response.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
					if (isRequestTypeText(internalResponse))
					{
						response.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
					}
				}
				else
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
					if (isRequestTypeText(internalResponse))
					{
						response.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
					}
				}
			}
			else
			{
				if (isRequestTypeText(internalResponse))
				{
					response.put(Constants.Gp.GP_MESSAGE, "Request has failed on server.");
				}
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
		private static final String OPERATION = "c2cUSSDReturnTransfer";
		private static final String SYSTEM_TOKEN = "system-token";
		private static final String AUTHORIZATION = "authorization";
		private static final String VALIDATE_ONLY = "validateOnly";

		private static final class Gp
		{
			private static final String MSISDN1 = "MSISDN1";
			private static final String RESELLER_ACCOUNT_ID = "resellerAccountId";
			private static final String MSISDN2 = "MSISDN2";
			private static final String TOPUPVALUE = "TOPUPVALUE";
			private static final String CURRENCY = "currency";
			private static final String LANGUAGE1 = "LANGUAGE1";
			private static final String GP_EXT_REQUEST_TYPE = "TYPE";
			private static final String PIN = "PIN";
			private static final String DATE_FORMAT = "dd/MM/YYYY hh:mm:ss";
			private static final String LOGINID = "LOGINID";
			private static final String LOGINID2 = "LOGINID2";
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String LOGIN = "LOGIN";
			private static final String PASSWORD = "PASSWORD";
			private static final String REQUEST_GATEWAY_CODE = "REQUEST_GATEWAY_CODE";
			private static final String REQUEST_GATEWAY_TYPE = "REQUEST_GATEWAY_TYPE";
			private static final String SERVICE_PORT = "SERVICE_PORT";
			private static final String SOURCE_TYPE = "SOURCE_TYPE";
			private static final String IS_REQUEST_TYPE_TEXT = "IS_REQUEST_TYPE_TEXT";
			private static final String PRODUCTS = "products";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String GP_TXN_STATUS = "TXNSTATUS";
			private static final String REQUEST_PAYLOAD = "requestPayload";
			private static final String TYPE = "TYPE";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String COMMAND = "COMMAND";
			private static final String GW_LOGINID = "GATEWAY_LOGINID";
			private static final String GW_PASSWORD = "GATEWAY_PASSWORD";
			private static final String GW_CODE = "REQUEST_GATEWAY_CODE";
			private static final String GW_TYPE = "REQUEST_GATEWAY_TYPE";
			private static final String GW_PORT = "SERVICE_PORT";
			private static final String GW_SOURCE_TYPE = "SOURCE_TYPE";
		}

		private static final class Txe
		{
			private static final String TYPE = "type";
			private static final String RESELLERMSISDN = "RESELLERMSISDN";
			private static final String ID = "id";
			private static final String ACCOUNT_TYPE_ID = "accountTypeId";
			private static final String VALUE = "value";
			private static final String CURRENCY = "currency";
			private static final String PRODUCT_SKU = "productSKU";
			private static final String DEFAULT_PRODUCT_SKU = "defaultProductSKU";
			private static final String AMOUNT = "amount";
			private static final String DEFAULT = "default";
			private static final String PREFERRED_LANGUAGE = "preferredLanguage";
			private static final String GP_EXT_REQUEST_TYPE = "externalRequestType";
			private static final String SENDER_MSISDN = "senderMsisdn";
			private static final String SENDER_PIN = "senderPin";
			private static final String MAP = "map";
			private static final String SENDER = "sender";
			private static final String RECEIVER = "receiver";
			private static final String PRODUCT = "product";
			private static final String TRANSACTION_PROPERTIES = "transactionProperties";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String RESULTCODE = "resultCode";
			private static final String RESULTMESSAGE = "resultMessage";
			private static final String RESPONSE = "response";
			private static final Object RESELLERID = "RESELLERID";
			private static final String RESULT_CODE = "resultCode";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String MESSAGE = "message";
			private static final String RESULT_DESCRIPTION = "resultDescription";
		}
	}

	private boolean isRequestTypeText(Map<String, Object> responseMap)
	{
		if (Objects.nonNull(responseMap) && responseMap.containsKey(Constants.Txe.TRANSACTION_PROPERTIES))
		{
			LinkedHashMap transactionProperties = (LinkedHashMap) responseMap.get(Constants.Txe.TRANSACTION_PROPERTIES);
			LinkedHashMap map = (LinkedHashMap) transactionProperties.get("map");
			if (Objects.nonNull(map) && map.containsKey(Constants.Gp.IS_REQUEST_TYPE_TEXT))
			{
				return Boolean.valueOf(map.get(Constants.Gp.IS_REQUEST_TYPE_TEXT).toString());
			}
		}
		return false;
	}
}