package com.seamless.ers.standardlink.transformers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seamless.ers.standardlink.config.StandardLinkConfig;
import com.seamless.ers.standardlink.model.common.ERSIOTransformer;
import com.seamless.ers.standardlink.model.common.ResultCode;
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
public class PromoVasEXTGTChannelTransformer implements ERSIOTransformer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(PromoVasEXTGTChannelTransformer.class);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	LinkedCaseInsensitiveMap<Object> requestFields;

	@Override
	public Message<?> transformInboundRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("GpPromoVasEXTGTChannelTransformer :: transformInboundRequest");

		Map<String, Object> promoVasEXTGTChannelRequest = Collections.synchronizedMap(new LinkedHashMap<>());
		Map<String, Object> sender = Collections.synchronizedMap(new LinkedHashMap<>());
		Map<String, Object> receiver = Collections.synchronizedMap(new LinkedHashMap<>());
		Map<String, Object> product = Collections.synchronizedMap(new LinkedHashMap<>());
		Map<String, Object> amount = Collections.synchronizedMap(new LinkedHashMap<>());
		Map<String, Object> transactionProperties = Collections.synchronizedMap(new LinkedHashMap<>());
		Map<String, Object> map = Collections.synchronizedMap(new LinkedHashMap<>());
		try
		{
			requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LOGGER.info("Forming TXE Promo bill request for operation " + Constants.Gp.OPERATIONID);

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			Map<String, Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATIONID).getFields();
			LinkedCaseInsensitiveMap<Object> additionalFields = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.COMMAND);

			if (Objects.nonNull(additionalFields.get(Constants.Gp.MSISDN)) && !additionalFields.get(Constants.Gp.MSISDN).toString().isEmpty())
			{
				sender.put(Constants.Txe.ID, additionalFields.get(Constants.Gp.MSISDN));
				sender.put(Constants.Txe.TYPE, Constants.Txe.RESELLERMSISDN);
			}
			else
			{
				sender.put(Constants.Txe.ID, additionalFields.get(Constants.Gp.LOGINID));
				sender.put(Constants.Txe.TYPE, Constants.Txe.RESELLERID);
			}
			sender.put(Constants.Txe.ACCOUNT_TYPE_ID, String.valueOf(operationFields.get(Constants.Gp.SENDER_ACCOUNT_ID)));

			receiver.put(Constants.Txe.ID, additionalFields.get(Constants.Gp.MSISDN2));
			receiver.put(Constants.Txe.TYPE, Constants.Txe.SUBSCRIBER_MSISDN);
			receiver.put(Constants.Txe.ACCOUNT_TYPE_ID, String.valueOf(operationFields.get(Constants.Gp.RECEIVER_ACCOUNT_ID)));

			amount.put(Constants.Txe.VALUE, additionalFields.get(Constants.Gp.AMOUNT));
			amount.put(Constants.Txe.CURRENCY, providerConfigurations.getOperations().get(Constants.Gp.OPERATIONID).getConstants().get(Constants.Gp.CURRENCY));
			product.put(Constants.Txe.AMOUNT, amount);

			//Populating additional transaction fields
			map.put(Constants.Txe.GP_EXT_REQUEST_TYPE, additionalFields.get(Constants.Gp.GP_EXT_REQUEST_TYPE));
			map.put(Constants.Txe.GP_EXT_REQUEST_DATE, additionalFields.get(Constants.Gp.GP_EXT_REQUEST_DATE));
			map.put(Constants.Txe.EXTNWCODE, additionalFields.get(Constants.Gp.EXTNWCODE));

			map.put(Constants.Txe.SENDER_EXTCODE, additionalFields.get(Constants.Gp.EXTCODE));
			map.put(Constants.Txe.CLIENT_REFERENCE, additionalFields.get(Constants.Gp.EXTREFNUM));
			map.put(Constants.Txe.PREFERRED_LANGUAGE, String.valueOf(additionalFields.get(Constants.Gp.LANGUAGE1)));
			map.put(Constants.Txe.PREFERRED_LANGUAGE2, String.valueOf(additionalFields.get(Constants.Gp.LANGUAGE2)));
			map.put(Constants.Txe.BONUS, additionalFields.get(Constants.Gp.BONUS));
			map.put(Constants.Txe.SELECTOR, operationFields.get(String.valueOf(additionalFields.get(Constants.Gp.SELECTOR))));

			for (String key : additionalFields.keySet())
			{
				if (key.startsWith(Constants.Gp.EXTERNALDATA))
				{
					map.put(key, additionalFields.get(key));
				}
			}

			map.put(Constants.Gp.GW_LOGINID, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.LOGIN)).get(0)).get(0));
			map.put(Constants.Gp.GW_PASSWORD, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.PASSWORD)).get(0)).get(0));
			map.put(Constants.Gp.GW_CODE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.REQUEST_GATEWAY_CODE)).get(0)).get(0));
			map.put(Constants.Gp.GW_TYPE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.REQUEST_GATEWAY_TYPE)).get(0)).get(0));
			map.put(Constants.Gp.GW_PORT, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.SERVICE_PORT)).get(0)).get(0));
			map.put(Constants.Gp.GW_SOURCE_TYPE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.SOURCE_TYPE)).get(0)).get(0));

			transactionProperties.put(Constants.Txe.MAP, map);

			promoVasEXTGTChannelRequest.put(Constants.Txe.SELLER, sender);
			promoVasEXTGTChannelRequest.put(Constants.Txe.TOPUPPRINCIPALID, receiver);
			promoVasEXTGTChannelRequest.put(Constants.Txe.PRODUCT, product);
			promoVasEXTGTChannelRequest.put(Constants.Txe.TRANSACTION_PROPERTIES, transactionProperties);

			return MessageBuilder.withPayload(promoVasEXTGTChannelRequest).copyHeaders(message.getHeaders()).setHeaderIfAbsent(Constants.SYSTEM_TOKEN, message.getHeaders().get("system-token")).setHeaderIfAbsent("validateOnly", false).build();
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
	public Object transformOutboundResponse(Object object) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("GpPromoVasEXTGTChannelTransformer :: transformOutboundResponse");
		ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
		LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATIONID).getFields();
		try
		{
			ObjectMapper objectMapper = new ObjectMapper();
			if (object instanceof CompletableFuture)
			{
				object = ((CompletableFuture<?>) object).get();
			}
			Map<String, Object> responseMap = objectMapper.convertValue(object, Map.class);
			Map<String, Object> promoRechargeResponse = new LinkedHashMap<>();

			promoRechargeResponse.put(Constants.Gp.GP_EXT_REQUEST_TYPE, operationFields.get(Constants.Txe.RESPONSE_TYPE));

			Integer resultCodeValue = (Integer) responseMap.get(Constants.Txe.RESULTCODE);
			String defaultMessage = String.valueOf(responseMap.get(Constants.Txe.RESULT_MESSAGE) != null ? responseMap.get(Constants.Txe.RESULT_MESSAGE) : responseMap.get(Constants.Txe.MESSAGE) != null ? responseMap.get(Constants.Txe.MESSAGE) : responseMap.get(Constants.Txe.RESULT_DESCRIPTION));
			ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATIONID, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);

			if (resultCode != null)
			{
				promoRechargeResponse.put(Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
				promoRechargeResponse.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
			}
			else
			{
				promoRechargeResponse.put(Constants.Gp.GP_TXN_STATUS, responseMap.get(Constants.Txe.RESULTCODE));
				promoRechargeResponse.put(Constants.Gp.GP_MESSAGE, responseMap.get(Constants.Txe.RESULTMESSAGE));
			}

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);
			promoRechargeResponse.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));

			if (Objects.nonNull(((LinkedCaseInsensitiveMap<Object>) (requestFields.get(Constants.Gp.COMMAND))).get(Constants.Gp.EXTREFNUM))
					&& !((LinkedCaseInsensitiveMap<Object>) (requestFields.get(Constants.Gp.COMMAND))).get(Constants.Gp.EXTREFNUM).toString().isEmpty())
			{
				promoRechargeResponse.put(Constants.Gp.EXTREFNUM, ((LinkedCaseInsensitiveMap<Object>) (requestFields.get(Constants.Gp.COMMAND))).get(Constants.Gp.EXTREFNUM));
			}
			promoRechargeResponse.put(Constants.Gp.TXNID, responseMap.get(Constants.Txe.ERS_REFERENCE));

			promoRechargeResponse.put(Constants.Gp.AMOUNT, ((LinkedCaseInsensitiveMap<Object>) (requestFields.get(Constants.Gp.COMMAND))).get(Constants.Gp.AMOUNT));
			promoRechargeResponse.put(Constants.Gp.TXNDATE, ((LinkedCaseInsensitiveMap<Object>) (requestFields.get(Constants.Gp.COMMAND))).get(Constants.Gp.DATE));
			promoRechargeResponse.put(Constants.Gp.SERVICETYPE, standardLinkConfig.getProviderConfigurations()
					.get(Constants.Gp.PROVIDER).getOperations()
					.get(Constants.Gp.OPERATIONID).getFields().get(Constants.Txe.SERVICE_TYPE));

			return promoRechargeResponse;
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
			private static final String LOGIN = "LOGIN";
			private static final String PASSWORD = "PASSWORD";
			private static final String REQUEST_GATEWAY_CODE = "REQUEST_GATEWAY_CODE";
			private static final String REQUEST_GATEWAY_TYPE = "REQUEST_GATEWAY_TYPE";
			private static final String SERVICE_PORT = "SERVICE_PORT";
			private static final String SOURCE_TYPE = "SOURCE_TYPE";
			private static final String PROVIDER = "gp";
			private static final String COMMAND = "COMMAND";
			private static final String OPERATIONID = "promoVas";
			private static final String MSISDN = "MSISDN";
			private static final String MSISDN2 = "MSISDN2";
			private static final String EXTREFNUM = "EXTREFNUM";
			private static final String AMOUNT = "AMOUNT";
			private static final String DATE = "DATE";
			private static final String DATE_FORMAT = "dd/MM/YYYY hh:mm:ss";
			private static final String GW_LOGINID = "GATEWAY_LOGINID";
			private static final String GW_PASSWORD = "GATEWAY_PASSWORD";
			private static final String GW_CODE = "REQUEST_GATEWAY_CODE";
			private static final String GW_TYPE = "REQUEST_GATEWAY_TYPE";
			private static final String GW_PORT = "SERVICE_PORT";
			private static final String GW_SOURCE_TYPE = "SOURCE_TYPE";
			private static final String LOGINID = "LOGINID";
			private static final String SENDER_ACCOUNT_ID = "senderAccountId";
			private static final String RECEIVER_ACCOUNT_ID = "receiverAccountId";
			private static final String GP_EXT_REQUEST_TYPE = "TYPE";
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String LANGUAGE1 = "LANGUAGE1";
			private static final String LANGUAGE2 = "LANGUAGE2";
			private static final String EXTCODE = "EXTCODE";
			private static final String EXTNWCODE = "EXTNWCODE";
			private static final String SELECTOR = "SELECTOR";
			private static final String BONUS = "BONUS";
			private static final String CURRENCY = "currency";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String GP_TXN_STATUS = "TXNSTATUS";
			private static final String TXNID = "TXNID";
			private static final String TXNDATE = "TXNDATE";
			private static final String SERVICETYPE = "SERVICETYPE";
			private static final String EXTERNALDATA = "EXTERNALDATA";
		}

		private static final class Txe
		{
			public static final String RESULT_MESSAGE = "resultMessage";
			public static final String MESSAGE = "message";
			public static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String TYPE = "type";
			private static final String ID = "id";
			private static final String RESULTCODE = "resultCode";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String SUBSCRIBER_MSISDN = "SUBSCRIBERMSISDN";
			private static final String ERS_REFERENCE = "ersReference";
			private static final String SERVICE_TYPE = "serviceType";
			private static final String RESULTMESSAGE = "resultMessage";
			private static final String PRODUCT = "product";
			private static final String TRANSACTION_PROPERTIES = "transactionProperties";
			private static final String TOPUPPRINCIPALID = "topUpPrincipalId";
			private static final String SELLER = "seller";
			private static final String MAP = "map";
			private static final String GP_EXT_REQUEST_TYPE = "externalRequestType";
			private static final String GP_EXT_REQUEST_DATE = "externalRequestDate";
			private static final String EXTNWCODE = "extNwCode";
			private static final String SENDER_EXTCODE = "senderExtCode";
			private static final String ACCOUNT_TYPE_ID = "accountTypeId";
			private static final String VALUE = "value";
			private static final String AMOUNT = "amount";
			private static final String CURRENCY = "currency";
			private static final String RESELLERMSISDN = "RESELLERMSISDN";
			private static final String RESELLERID = "RESELLERID";
			private static final String PREFERRED_LANGUAGE = "preferredLanguage";
			private static final String PREFERRED_LANGUAGE2 = "preferredLanguage2";
			private static final String CLIENT_REFERENCE = "clientReference";
			private static final String SELECTOR = "selector";
			private static final String BONUS = "bonus";
		}
	}
}