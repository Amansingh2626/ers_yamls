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
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component("cP2PUssdCreditTransferTransformer")
public class CP2PUssdCreditTransferTransformer implements ERSIOTransformer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.DATE_FORMAT);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Override
	public Message<?> transformInboundRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("CP2PUssdCreditTransferTransformer :: transformOutboundRequest");
		try
		{
			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			Map<String, Object> command = (Map<String, Object>) requestFields.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.COMMAND);
			if (command != null)
			{
				requestFields.putAll(command);
			}
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.PROVIDER);
			//		replacePinBase64(requestFields, message.getHeaders().get("contentType").toString(),providerConfigurations);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.OPERATION).getFields();
			LinkedCaseInsensitiveMap<String> constants = providerConfigurations.getOperations().get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.OPERATION).getConstants();

			String msisdn = String.valueOf(requestFields.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.MSISDN1));
			String txeSystemToken = (String) message.getHeaders().get("system-token");
			txeSystemToken = txeSystemToken.replaceAll("\"uid\": ?\"[a-zA-Z0-9-]*\"", "\"uid\":\"" +msisdn +"\"");

			Map<String, Object> cp2pCreditTransfer = new HashMap<>();
			Map<String, Object> sender = new HashMap<>();
			List<Map<String, Object>> additionalProperties = new ArrayList<>();
			Map<String, Object> senderPassword = new HashMap<>();
			Map<String, Object> receiver = new HashMap<>();
			Map<String, Object> product = new HashMap<>();
			Map<String, Object> amount = new HashMap<>();
			Map<String, Object> transactionProperties = new HashMap<>();
			Map<String, Object> map = new HashMap<>();

			sender.put(StandardLinkConstants.TXEConstant.ID, requestFields.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.MSISDN1).toString());
			sender.put(StandardLinkConstants.TXEConstant.TYPE, com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Txe.SUBSCRIBER_MSISDN);
			sender.put(StandardLinkConstants.TXEConstant.ACCOUNT_TYPE_ID, String.valueOf(operationFields.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.SENDER_ACCOUNT_ID)));
			senderPassword.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.NAME, com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Txe.SUBSCRIBERPASSWORD);
			senderPassword.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.VALUE, requestFields.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.PIN).toString());
			additionalProperties.add(senderPassword);
			sender.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.ADDITIONAL_PROPERTIES, additionalProperties);

			receiver.put(StandardLinkConstants.TXEConstant.ID, requestFields.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.MSISDN2).toString());
			receiver.put(StandardLinkConstants.TXEConstant.TYPE, com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Txe.SUBSCRIBER_MSISDN);
			receiver.put(StandardLinkConstants.TXEConstant.ACCOUNT_TYPE_ID, String.valueOf(operationFields.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.RECEIVER_ACCOUNT_ID)));

			amount.put(StandardLinkConstants.TXEConstant.VALUE, requestFields.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.AMOUNT));
			amount.put(StandardLinkConstants.TXEConstant.CURRENCY, constants.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.CURRENCY));
			product.put(StandardLinkConstants.TXEConstant.PRODUCT_SKU, operationFields.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Txe.PRODUCT_SKU));
			product.put(StandardLinkConstants.TXEConstant.AMOUNT, amount);

			//Populating additional transaction fields
			map.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Txe.GP_EXT_REQUEST_TYPE, requestFields.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.GP_EXT_REQUEST_TYPE));
			map.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Txe.PREFERRED_LANGUAGE, requestFields.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.LANGUAGE1));
			map.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Txe.PREFERRED_LANGUAGE2, requestFields.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.LANGUAGE2));
			map.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Txe.SELECTOR, requestFields.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.SELECTOR));

			for (String key : requestFields.keySet())
			{
				if (key.startsWith(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.EXTERNALDATA))
				{
					map.put(key, requestFields.get(key));
				}
			}

			map.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.GW_LOGINID, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.LOGIN)).get(0)).get(0));
			map.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.GW_PASSWORD, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.PASSWORD)).get(0)).get(0));
			map.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.GW_CODE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.REQUEST_GATEWAY_CODE)).get(0)).get(0));
			map.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.GW_TYPE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.REQUEST_GATEWAY_TYPE)).get(0)).get(0));
			map.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.GW_PORT, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.SERVICE_PORT)).get(0)).get(0));
			map.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.GW_SOURCE_TYPE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.SOURCE_TYPE)).get(0)).get(0));

			transactionProperties.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Txe.MAP, map);

			cp2pCreditTransfer.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Txe.SELLER, sender);
			cp2pCreditTransfer.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Txe.TOPUPPRINCIPALID, receiver);
			cp2pCreditTransfer.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Txe.PRODUCT, product);
			cp2pCreditTransfer.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Txe.TRANSACTION_PROPERTIES, transactionProperties);

			return MessageBuilder.withPayload(cp2pCreditTransfer).copyHeaders(message.getHeaders()).setHeader(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.SYSTEM_TOKEN, txeSystemToken).setHeaderIfAbsent("validateOnly", false).setHeader(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.IS_REQUEST_TYPE_TEXT, requestFields.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.IS_REQUEST_TYPE_TEXT)).build();
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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("CP2PUssdCreditTransferTransformer :: transformOutboundResponse");

		try
		{
			LinkedCaseInsensitiveMap<Object> response = new LinkedCaseInsensitiveMap<>();
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.OPERATION).getFields();

			response.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));
			response.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.GP_RESPONSE_TYPE, operationFields.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.RESPONSE_TYPE));
			if (outgoingResponse instanceof CompletableFuture)
			{
				outgoingResponse = ((CompletableFuture<?>) outgoingResponse).get();
			}

			if (outgoingResponse instanceof Exception)
			{
				Exception exception = (Exception) outgoingResponse;
				response.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.GP_MESSAGE, StandardLinkUtilities.getRootCause(exception));
				response.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.GP_TXN_STATUS, StandardLinkResultCodes.INTERNAL_COMPONENT_ERROR);
				return response;
			}

			Map<String, Object> internalResponse = (LinkedHashMap<String, Object>) outgoingResponse;
			Map<String, Object> request = (Map<String, Object>) internalResponse.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.REQUEST_PAYLOAD);
			Map<String, Object> requestPayloadCommand = (Map<String, Object>) request.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.COMMAND);

			if (!internalResponse.isEmpty() && internalResponse.containsKey(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Txe.RESULT_CODE))
			{
				Integer resultCodeValue = (Integer) internalResponse.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Txe.RESULT_CODE);
				String defaultMessage = String.valueOf(internalResponse.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Txe.RESULT_MESSAGE) != null ? internalResponse.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Txe.RESULT_MESSAGE) : internalResponse.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Txe.MESSAGE) != null ? internalResponse.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Txe.MESSAGE) : internalResponse.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Txe.RESULT_DESCRIPTION));
				if (resultCodeValue == StandardLinkResultCodes.SUCCESS)
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.PROVIDER, com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);

					response.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.TYPE, operationFields.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.RESPONSE_TYPE));
					response.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.GP_TXN_ID, (internalResponse.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Txe.ERS_REFERENCE).toString()));
					response.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.GP_MESSAGE, resultCode.getDescription());
					response.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
				}
				else
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.PROVIDER, com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.GP_MESSAGE, resultCode.getDescription());
					response.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
					response.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.TYPE, operationFields.get(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.RESPONSE_TYPE));
				}
			}
			else
			{
				response.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.GP_MESSAGE, "Request has failed on server.");
				response.put(com.seamless.ers.standardlink.transformers.CP2PUssdCreditTransferTransformer.Constants.Gp.GP_TXN_STATUS, StandardLinkResultCodes.FAILED_ON_SERVER);
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
			private static final String IS_REQUEST_TYPE_TEXT = "IS_REQUEST_TYPE_TEXT";
			private static final String NAME = "name";
			private static final String VALUE = "value";
			private static final String PIN = "PIN";
			private static final String ADDITIONAL_PROPERTIES = "additionalProperties";
			private static final String GP_RESPONSE_TYPE = "TYPE";
			private static final String PROVIDER = "gp";
			private static final String GW_LOGINID = "GATEWAY_LOGINID";
			private static final String GW_PASSWORD = "GATEWAY_PASSWORD";
			private static final String GW_CODE = "REQUEST_GATEWAY_CODE";
			private static final String GW_TYPE = "REQUEST_GATEWAY_TYPE";
			private static final String GW_PORT = "SERVICE_PORT";
			private static final String GW_SOURCE_TYPE = "SOURCE_TYPE";
			private static final String MSISDN1 = "MSISDN1";
			private static final String SELECTOR = "SELECTOR";
			private static final String GP_EXT_REQUEST_TYPE = "TYPE";
			private static final String LANGUAGE1 = "LANGUAGE1";
			private static final String LANGUAGE2 = "LANGUAGE2";
			private static final String AMOUNT = "AMOUNT";
			private static final String SENDER_ACCOUNT_ID = "senderAccountId";
			private static final String MSISDN2 = "MSISDN2";
			private static final String RECEIVER_ACCOUNT_ID = "receiverAccountId";
			private static final String CURRENCY = "currency";
			private static final String COMMAND = "COMMAND";
			private static final String OPERATION = "cp2pUSSDCreditTransfer";
			private static final String LOGINID = "LOGINID";
			private static final String GP_TXN_STATUS = "TXNSTATUS";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String GP_TXN_ID = "TXNID";
			private static final String TYPE = "TYPE";
			private static final String REQUEST_PAYLOAD = "requestPayload";
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String DATE_FORMAT = "dd/MM/YYYY hh:mm:ss";
			private static final String LOGINID2 = "LOGINID2";
			private static final String LOGIN = "LOGIN";
			private static final String PASSWORD = "PASSWORD";
			private static final String REQUEST_GATEWAY_CODE = "REQUEST_GATEWAY_CODE";
			private static final String REQUEST_GATEWAY_TYPE = "REQUEST_GATEWAY_TYPE";
			private static final String SERVICE_PORT = "SERVICE_PORT";
			private static final String SOURCE_TYPE = "SOURCE_TYPE";
			private static final String EXTERNALDATA = "EXTERNALDATA";
			private static final String RESPONSE_TYPE = "responseType";
		}

		private static final class Txe
		{
			private static final Object SUBSCRIBERPASSWORD = "SUBSCRIBERPASSWORD";
			private static final String ERS_REFERENCE = "ersReference";
			private static final String TRANSACTION_PROPERTIES = "transactionProperties";
			private static final String PRODUCT = "product";
			private static final String SELLER = "seller";
			private static final String TOPUPPRINCIPALID = "topUpPrincipalId";
			private static final String MAP = "map";
			private static final String GP_EXT_REQUEST_TYPE = "externalRequestType";
			private static final String SELECTOR = "selector";
			private static final String PREFERRED_LANGUAGE2 = "preferredLanguage2";
			private static final String PREFERRED_LANGUAGE = "preferredLanguage";
			private static final String PRODUCT_SKU = "productSKU";
			private static final String SUBSCRIBER_MSISDN = "SUBSCRIBERMSISDN";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String RESULT_CODE = "resultCode";
			private static final Object RESELLERID = "RESELLERID";
			private static final String MESSAGE = "message";
			private static final String RESULT_DESCRIPTION = "resultDescription";
		}
	}

//	private Map<String,Object> replacePinBase64(Map<String, Object> requestMap, String contentType,ProviderConfigurations providerConfigurations){
//		LOGGER.debug("Incoming contentType : "+contentType);
//		List<String> base64AllowedList=providerConfigurations.getAllowBase64();
//		LOGGER.debug("Available ContentType :" +providerConfigurations.getAllowBase64Fields());
//		List<String> allowBase64Fields=providerConfigurations.getAllowBase64Fields();
//		String contentTypeNew=contentType.replace(";charset=UTF-8","");
//		if(base64AllowedList!=null && base64AllowedList.size()>0 && allowBase64Fields !=null && allowBase64Fields.size()>0 && base64AllowedList.contains(contentTypeNew)){
//			for(String fields: allowBase64Fields){
//				LOGGER.debug("Started Decoding PIN base64 for :"+fields);
//				if(Objects.nonNull(requestMap.get(fields))&&!StringUtils.isEmpty(requestMap.get(fields).toString())){
//					String fieldValue = new String(Base64Utils.decodeFromString(requestMap.get(fields).toString()));
//					requestMap.put(fields,fieldValue);
//				}
//				LOGGER.debug("Completed Decoding base64 for :"+fields);
//			}
//		}
//		return requestMap;
//	}
}
