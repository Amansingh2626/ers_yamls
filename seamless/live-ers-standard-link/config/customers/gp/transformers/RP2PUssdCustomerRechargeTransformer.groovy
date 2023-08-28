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

@Component("rP2PUssdCustomerRechargeTransformer")
public class RP2PUssdCustomerRechargeTransformer implements ERSIOTransformer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(RP2PUssdCustomerRechargeTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Override
	public Message<?> transformInboundRequest(Message<?> message) throws TransformerException
	{

		EtmPoint point = EtmManager.getEtmMonitor().createPoint("RP2PUssdCustomerRechargeTransformer :: transformInboundRequest");

		try
		{
			String operationId = (String) message.getHeaders().getOrDefault("OPERATION",Constants.Gp.OPERATION);
			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.COMMAND);
			if (command != null)
			{
				requestFields.putAll(command);
			}

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(operationId).getFields();
			LinkedCaseInsensitiveMap<String> constants = providerConfigurations.getOperations().get(operationId).getConstants();

			Map<String, Object> rp2pRechargeRequest = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> sender = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> receiver = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> product = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> amount = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> transactionProperties = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> map = Collections.synchronizedMap(new LinkedHashMap<>());

			if (!requestFields.get(Constants.Gp.MSISDN1).toString().isEmpty())
			{
				sender.put(StandardLinkConstants.TXEConstant.ID, String.valueOf(requestFields.get(Constants.Gp.MSISDN1)));
				sender.put(StandardLinkConstants.TXEConstant.TYPE, Constants.Txe.RESELLERMSISDN);
			}
			else
			{
				sender.put(StandardLinkConstants.TXEConstant.ID, requestFields.get(Constants.Gp.LOGINID).toString());
				sender.put(StandardLinkConstants.TXEConstant.TYPE, Constants.Txe.RESELLERID);
			}
			sender.put(StandardLinkConstants.TXEConstant.ACCOUNT_TYPE_ID, String.valueOf(operationFields.get(Constants.Gp.SENDER_ACCOUNT_ID)));

			if (!requestFields.get(Constants.Gp.MSISDN2).toString().isEmpty())
			{
				receiver.put(StandardLinkConstants.TXEConstant.ID, String.valueOf(requestFields.get(Constants.Gp.MSISDN2)));
				receiver.put(StandardLinkConstants.TXEConstant.TYPE, Constants.Txe.SUBSCRIBER_MSISDN);
			}
			else
			{
				receiver.put(StandardLinkConstants.TXEConstant.ID, requestFields.get(Constants.Gp.LOGINID2).toString());
				receiver.put(StandardLinkConstants.TXEConstant.TYPE, StandardLinkConstants.TXEConstant.RESELLERID);
			}
			receiver.put(StandardLinkConstants.TXEConstant.ACCOUNT_TYPE_ID, String.valueOf(operationFields.get(Constants.Gp.RECEIVER_ACCOUNT_ID)));

			amount.put(StandardLinkConstants.TXEConstant.VALUE, requestFields.get(Constants.Gp.AMOUNT));
			amount.put(StandardLinkConstants.TXEConstant.CURRENCY, constants.get(Constants.Gp.CURRENCY));
			product.put(StandardLinkConstants.TXEConstant.AMOUNT, amount);

			if (isRequestTypeText(requestFields))
			{
				if (Objects.nonNull(requestFields.get(Constants.Gp.BONUS)) && StringUtils.isNotEmpty(requestFields.get(Constants.Gp.BONUS).toString()))
				{
					map.put(Constants.Txe.BONUS, requestFields.get(Constants.Gp.BONUS));
				}
				map.put(Constants.Txe.SELECTOR, requestFields.get(Constants.Gp.SELECTOR));
			}
			else
			{
				map.put(Constants.Txe.SELECTOR, operationFields.get(String.valueOf(requestFields.get(Constants.Gp.SELECTOR))));
				map.put(Constants.Txe.PREFERRED_LANGUAGE, requestFields.getOrDefault(Constants.Gp.LANGUAGE1, "en"));
				map.put(Constants.Txe.PREFERRED_LANGUAGE2, requestFields.get(Constants.Gp.LANGUAGE2));
			}

			map.put(Constants.Gp.GP_EXT_REQUEST_TYPE, message.getHeaders().get(Constants.Gp.TYPE));
			map.put(Constants.Gp.GW_LOGINID, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.LOGIN)).get(0)).get(0));
			map.put(Constants.Gp.GW_PASSWORD, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.PASSWORD)).get(0)).get(0));
			map.put(Constants.Gp.GW_CODE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.REQUEST_GATEWAY_CODE)).get(0)).get(0));
			map.put(Constants.Gp.GW_TYPE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.REQUEST_GATEWAY_TYPE)).get(0)).get(0));
			map.put(Constants.Gp.GW_PORT, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.SERVICE_PORT)).get(0)).get(0));
			map.put(Constants.Gp.GW_SOURCE_TYPE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.SOURCE_TYPE)).get(0)).get(0));
			map.put("IS_REQUEST_TYPE_TEXT", requestFields.getOrDefault("IS_REQUEST_TYPE_TEXT", "false"));

			transactionProperties.put(Constants.Txe.MAP, map);

			rp2pRechargeRequest.put(Constants.Txe.SELLER, sender);
			rp2pRechargeRequest.put(Constants.Txe.TOPUPPRINCIPALID, receiver);
			rp2pRechargeRequest.put(Constants.Txe.PRODUCT, product);
			rp2pRechargeRequest.put(Constants.Txe.TRANSACTION_PROPERTIES, transactionProperties);

			return MessageBuilder.withPayload(rp2pRechargeRequest).copyHeaders(message.getHeaders()).setHeaderIfAbsent(Constants.SYSTEM_TOKEN, message.getHeaders().get("system-token")).setHeaderIfAbsent("validateOnly", false).setHeader("IS_REQUEST_TYPE_TEXT", requestFields.get("IS_REQUEST_TYPE_TEXT")).build();
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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("RP2PUssdCustomerRechargeTransformer :: transformOutboundResponse");

		try
		{
			if (outgoingResponse instanceof CompletableFuture)
			{
				outgoingResponse = ((CompletableFuture<?>) outgoingResponse).get();
			}
			Map<String, Object> internalResponse = (LinkedHashMap<String, Object>) outgoingResponse;
			Map<String, Object> request = (Map<String, Object>) internalResponse.get(Constants.Gp.REQUEST_PAYLOAD);
			Map<String, Object> requestPayloadCommand = (Map<String, Object>) request.get(Constants.Gp.COMMAND);

			String operationId = (String) internalResponse.getOrDefault("OPERATION",Constants.Gp.OPERATION);
			LinkedCaseInsensitiveMap<Object> response = new LinkedCaseInsensitiveMap<>();
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(operationId).getFields();

			response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));

			if (outgoingResponse instanceof Exception)
			{
				Exception exception = (Exception) outgoingResponse;
				response.put(Constants.Gp.GP_MESSAGE, StandardLinkUtilities.getRootCause(exception));
				response.put(Constants.Gp.GP_TXN_STATUS, StandardLinkResultCodes.INTERNAL_COMPONENT_ERROR);
				return response;
			}

			if (!internalResponse.isEmpty() && internalResponse.containsKey(Constants.Txe.RESULT_CODE))
			{
				Integer resultCodeValue = (Integer) internalResponse.get(Constants.Txe.RESULT_CODE);
				String defaultMessage = String.valueOf(internalResponse.get(Constants.Txe.RESULT_MESSAGE) != null ? internalResponse.get(Constants.Txe.RESULT_MESSAGE) : internalResponse.get(Constants.Txe.MESSAGE) != null ? internalResponse.get(Constants.Txe.MESSAGE) : internalResponse.get(Constants.Txe.RESULT_DESCRIPTION));
				response.put(Constants.Gp.TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));

				if (resultCodeValue == StandardLinkResultCodes.SUCCESS)
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, operationId, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					if (Objects.nonNull(internalResponse.get(Constants.Txe.ERS_REFERENCE)) && StringUtils.isNotEmpty(internalResponse.get(Constants.Txe.ERS_REFERENCE).toString())) {
						response.put(Constants.Gp.GP_TXN_ID, internalResponse.get(Constants.Txe.ERS_REFERENCE));
					}
					response.put(Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
					response.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
				}
				else
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, operationId, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
					response.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
				}
			}
			else
			{
				response.put(Constants.Gp.GP_TXN_STATUS, StandardLinkResultCodes.FAILED_ON_SERVER);
			}

			if (isResponseTypeText(internalResponse))
			{
				String defaultMessage = String.valueOf(internalResponse.get(Constants.Txe.RESULT_MESSAGE) != null ? internalResponse.get(Constants.Txe.RESULT_MESSAGE) : internalResponse.get(Constants.Txe.MESSAGE) != null ? internalResponse.get(Constants.Txe.MESSAGE) : internalResponse.get(Constants.Txe.RESULT_DESCRIPTION));
				response.put(Constants.Gp.GP_MESSAGE, defaultMessage);
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
			private static final String REQUEST_PAYLOAD = "requestPayload";
			private static final String PROVIDER = "gp";
			private static final String MSISDN1 = "MSISDN1";
			private static final String SENDER_ACCOUNT_ID = "senderAccountId";
			private static final String MSISDN2 = "MSISDN2";
			private static final String AMOUNT = "AMOUNT";
			private static final String RECEIVER_ACCOUNT_ID = "receiverAccountId";
			private static final String BONUS = "BONUS";
			private static final String CURRENCY = "currency";
			private static final String SELECTOR = "SELECTOR";
			private static final String LANGUAGE1 = "LANGUAGE1";
			private static final String LANGUAGE2 = "LANGUAGE2";
			private static final String TYPE = "TYPE";
			private static final String GP_EXT_REQUEST_TYPE = "externalRequestType";
			private static final String DATE_FORMAT = "dd/MM/YYYY hh:mm:ss";
			private static final String COMMAND = "COMMAND";
			private static final String OPERATION = "rp2pUSSDCustomerRecharge";
			private static final String LOGINID = "LOGINID";
			private static final String LOGINID2 = "LOGINID2";
			private static final String LOGIN = "LOGIN";
			private static final String PASSWORD = "PASSWORD";
			private static final String REQUEST_GATEWAY_CODE = "REQUEST_GATEWAY_CODE";
			private static final String REQUEST_GATEWAY_TYPE = "REQUEST_GATEWAY_TYPE";
			private static final String SERVICE_PORT = "SERVICE_PORT";
			private static final String SOURCE_TYPE = "SOURCE_TYPE";
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String GP_TXN_STATUS = "TXNSTATUS";
			private static final String GP_TXN_ID = "TXNID";
			private static final String GW_LOGINID = "GATEWAY_LOGINID";
			private static final String GW_PASSWORD = "GATEWAY_PASSWORD";
			private static final String GW_CODE = "REQUEST_GATEWAY_CODE";
			private static final String GW_TYPE = "REQUEST_GATEWAY_TYPE";
			private static final String GW_PORT = "SERVICE_PORT";
			private static final String GW_SOURCE_TYPE = "SOURCE_TYPE";
			private static final String RESPONSE_TYPE = "responseType";
		}

		private static final class Txe
		{
			private static final String RESELLERMSISDN = "RESELLERMSISDN";
			private static final String BONUS = "bonus";
			private static final String SELECTOR = "selector";
			private static final String PREFERRED_LANGUAGE = "preferredLanguage";
			private static final String PREFERRED_LANGUAGE2 = "preferredLanguage2";
			private static final String MAP = "map";
			private static final String SELLER = "seller";
			private static final String TOPUPPRINCIPALID = "topUpPrincipalId";
			private static final String PRODUCT = "product";
			private static final String TRANSACTION_PROPERTIES = "transactionProperties";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String ERS_REFERENCE = "ersReference";
			private static final String RESELLERID = "RESELLERID";
			private static final String RESULT_CODE = "resultCode";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String MESSAGE = "message";
			private static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String SUBSCRIBER_MSISDN = "SUBSCRIBERMSISDN";
		}
	}

	private boolean isRequestTypeText(Map<String, Object> additionalFields)
	{
		if (Objects.nonNull(additionalFields) && additionalFields.containsKey("IS_REQUEST_TYPE_TEXT"))
		{
			return Boolean.valueOf(additionalFields.get("IS_REQUEST_TYPE_TEXT").toString());
		}
		return false;
	}

	private boolean isResponseTypeText(Map<String, Object> responseMap)
	{
		if (Objects.nonNull(responseMap) && responseMap.containsKey("transactionProperties"))
		{
			LinkedHashMap transactionProperties = (LinkedHashMap) responseMap.get("transactionProperties");
			LinkedHashMap map = (LinkedHashMap) transactionProperties.get("map");
			if (Objects.nonNull(map) && map.containsKey("IS_REQUEST_TYPE_TEXT"))
				return Boolean.valueOf(map.get("IS_REQUEST_TYPE_TEXT").toString());
		}
		return false;
	}
}