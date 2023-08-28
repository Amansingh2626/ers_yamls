package com.seamless.ers.standardlink.transformers;

import com.seamless.ers.standardlink.config.StandardLinkConfig;
import com.seamless.ers.standardlink.model.common.ERSIOTransformer;
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
public class PostpaidBillPaymentUSSDTransformer implements ERSIOTransformer
{

	private static final Logger LOGGER = LoggerFactory.getLogger(PostpaidBillPaymentUSSDTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("GpUSSDPostpaidBillPaymentTransformer :: transformInboundRequest");

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

			Map<String, Object> paymentRequest = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> product = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> amount = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> topUpPrincipalId = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> seller = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> map = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> transactionProperties = Collections.synchronizedMap(new LinkedHashMap<>());

			amount.put(Constants.Txe.VALUE, String.valueOf(requestFields.get(Constants.Gp.AMOUNT)));
			amount.put(Constants.Txe.CURRENCY, constants.get(Constants.Gp.CURRENCY));
			product.put(Constants.Txe.AMOUNT, amount);
			paymentRequest.put(Constants.Txe.PRODUCT, product);

			topUpPrincipalId.put(Constants.Txe.ACCOUNT_TYPE_ID, operationFields.get(Constants.Gp.SUBSCRIBER_TYPE));
			topUpPrincipalId.put(Constants.Txe.ID, String.valueOf(requestFields.get(Constants.Gp.MSISDN2)));
			topUpPrincipalId.put(Constants.Txe.TYPE, Constants.Txe.SUBSCRIBER_MSISDN);

			paymentRequest.put(Constants.Txe.TOPUPPRINCIPALID, topUpPrincipalId);

			seller.put(Constants.Txe.ACCOUNT_TYPE_ID, operationFields.get(Constants.Gp.RESELLER_TYPE));
			seller.put(Constants.Txe.ID, String.valueOf(requestFields.get(Constants.Gp.MSISDN1)));
			seller.put(Constants.Txe.TYPE, Constants.Txe.RESELLERMSISDN);
			paymentRequest.put(Constants.Txe.SELLER, seller);

			map.put(Constants.Gp.GP_EXT_REQUEST_TYPE, message.getHeaders().get(Constants.Gp.TYPE));
			map.put(Constants.Txe.PREFERRED_LANGUAGE, requestFields.get(Constants.Gp.LANGUAGE1));
			map.put(Constants.Txe.PREFERRED_LANGUAGE2, requestFields.get(Constants.Gp.LANGUAGE2));
			map.put(Constants.Txe.SELECTOR, String.valueOf(requestFields.get(Constants.Gp.SELECTOR)));
			map.put(Constants.Txe.SENDER_PIN, String.valueOf(requestFields.get(Constants.Gp.PIN)));

			map.put(Constants.Gp.GW_LOGINID, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.LOGIN)).get(0)).get(0));
			map.put(Constants.Gp.GW_PASSWORD, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.PASSWORD)).get(0)).get(0));
			map.put(Constants.Gp.GW_CODE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.REQUEST_GATEWAY_CODE)).get(0)).get(0));
			map.put(Constants.Gp.GW_TYPE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.REQUEST_GATEWAY_TYPE)).get(0)).get(0));
			map.put(Constants.Gp.GW_PORT, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.SERVICE_PORT)).get(0)).get(0));
			map.put(Constants.Gp.GW_SOURCE_TYPE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.SOURCE_TYPE)).get(0)).get(0));
			map.put(Constants.Gp.IS_REQUEST_TYPE_TEXT, requestFields.getOrDefault(Constants.Gp.IS_REQUEST_TYPE_TEXT, "false"));

			transactionProperties.put(Constants.Txe.MAP, map);
			paymentRequest.put(Constants.Txe.TRANSACTION_PROPERTIES, transactionProperties);

			return MessageBuilder.withPayload(paymentRequest).copyHeaders(message.getHeaders()).setHeaderIfAbsent(StandardLinkConstants.Headers.AUTHORIZATION, message.getHeaders().get("authorization")).setHeaderIfAbsent(StandardLinkConstants.Headers.SYSTEM_TOKEN, message.getHeaders().get("system-token")).setHeaderIfAbsent("validateOnly", false).setHeader(Constants.Gp.IS_REQUEST_TYPE_TEXT, requestFields.get(Constants.Gp.IS_REQUEST_TYPE_TEXT)).build();
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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("GpUSSDPostpaidBillPaymentTransformer :: transformOutboundResponse");
		try
		{
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION).getFields();
			LOGGER.info("Forming GP response for operation " + Constants.OPERATION);
			Map<String, Object> postpaidBillPaymentUSSDResponse = new LinkedHashMap<>();
			if (outgoingResponse instanceof CompletableFuture)
			{
				outgoingResponse = ((CompletableFuture<?>) outgoingResponse).get();
			}
			if (outgoingResponse instanceof Exception)
			{
				Exception exception = (Exception) outgoingResponse;
				postpaidBillPaymentUSSDResponse.put(Constants.Gp.GP_MESSAGE, StandardLinkUtilities.getRootCause(exception));
				postpaidBillPaymentUSSDResponse.put(Constants.Gp.GP_TXN_STATUS, StandardLinkResultCodes.INTERNAL_COMPONENT_ERROR);
				return postpaidBillPaymentUSSDResponse;
			}

			Map<String, Object> responseMap = (LinkedHashMap<String, Object>) outgoingResponse;

			postpaidBillPaymentUSSDResponse.put(Constants.Gp.TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));
			postpaidBillPaymentUSSDResponse.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));
			postpaidBillPaymentUSSDResponse.put(Constants.Gp.GP_MESSAGE, responseMap.get(Constants.Gp.RESULT_MESSAGE));
			if (responseMap.containsKey(Constants.Txe.ERS_REFERENCE))
			{
				postpaidBillPaymentUSSDResponse.put(Constants.Gp.TXNID, responseMap.get(Constants.Txe.ERS_REFERENCE).toString());
			}
			if (responseMap.containsKey(Constants.Txe.RESULTCODE))
			{
				postpaidBillPaymentUSSDResponse.put(Constants.Gp.TXN_STATUS, responseMap.get(Constants.Txe.RESULTCODE).toString());
			}

			return postpaidBillPaymentUSSDResponse;
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
		private static final String OPERATION = "postpaidUSSDBillPayment";

		private static final class Gp
		{
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String DATE_FORMAT = "dd/MM/YYYY hh:mm:ss";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String PASSWORD = "PASSWORD";
			private static final String MSISDN1 = "MSISDN1";
			private static final String PIN = "PIN";
			private static final String MSISDN2 = "MSISDN2";
			private static final String AMOUNT = "AMOUNT";
			private static final String CURRENCY = "currency";
			private static final String LANGUAGE1 = "LANGUAGE1";
			private static final String LANGUAGE2 = "LANGUAGE2";
			private static final String RESELLER_TYPE = "resellerType";
			private static final String SELECTOR = "SELECTOR";
			private static final String GP_EXT_REQUEST_TYPE = "externalRequestType";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String GP_TXN_STATUS = "TXNSTATUS";
			private static final String SUBSCRIBER_TYPE = "subscriberType";
			private static final String COMMAND = "COMMAND";
			private static final String TYPE = "TYPE";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String TXNID = "TXNID";
			private static final String TXN_STATUS = "TXNSTATUS";
			private static final String GW_LOGINID = "GATEWAY_LOGINID";
			private static final String GW_PASSWORD = "GATEWAY_PASSWORD";
			private static final String GW_CODE = "REQUEST_GATEWAY_CODE";
			private static final String GW_TYPE = "REQUEST_GATEWAY_TYPE";
			private static final String GW_PORT = "SERVICE_PORT";
			private static final String GW_SOURCE_TYPE = "SOURCE_TYPE";
			private static final String LOGIN = "LOGIN";
			private static final String REQUEST_GATEWAY_CODE = "REQUEST_GATEWAY_CODE";
			private static final String REQUEST_GATEWAY_TYPE = "REQUEST_GATEWAY_TYPE";
			private static final String SERVICE_PORT = "SERVICE_PORT";
			private static final String SOURCE_TYPE = "SOURCE_TYPE";
			private static final String IS_REQUEST_TYPE_TEXT = "IS_REQUEST_TYPE_TEXT";
		}

		private static final class Txe
		{
			private static final String CURRENCY = "currency";
			private static final String PREFERRED_LANGUAGE = "preferredLanguage";
			private static final String PREFERRED_LANGUAGE2 = "preferredLanguage2";
			private static final String TYPE = "type";
			private static final String ID = "id";
			private static final String TOPUPPRINCIPALID = "topUpPrincipalId";
			private static final String ACCOUNT_TYPE_ID = "accountTypeId";
			private static final String PRODUCT = "product";
			private static final String TRANSACTION_PROPERTIES = "transactionProperties";
			private static final String VALUE = "value";
			private static final String AMOUNT = "amount";
			private static final String MAP = "map";
			private static final String RESELLERMSISDN = "RESELLERMSISDN";
			private static final String SUBSCRIBER_MSISDN = "SUBSCRIBERMSISDN";
			private static final String GP_EXT_REQUEST_TYPE = "externalRequestType";
			private static final String SENDER_PIN = "senderPin";
			private static final String ERS_REFERENCE = "ersReference";
			private static final String RESULTCODE = "resultCode";
			private static final String RESPONSE = "response";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String SELECTOR = "selector";
			private static final String SELLER = "seller";
		}
	}
}
