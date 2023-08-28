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
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component("c2CReturnTransferTransformer")
public class C2CReturnTransferTransformer implements ERSIOTransformer
{

	private static final Logger LOGGER = LoggerFactory.getLogger(C2CReturnTransferTransformer.class);
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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("C2CReturnTransferTransformer :: transformInboundRequest");

		try
		{
			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.COMMAND);
			LinkedCaseInsensitiveMap<Object> commandProduct = (LinkedCaseInsensitiveMap<Object>) command.get(Constants.Gp.PRODUCTS);

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION).getFields();
			LinkedCaseInsensitiveMap<String> constants = providerConfigurations.getOperations().get(Constants.OPERATION).getConstants();

			String dmsSystemToken = (String) message.getHeaders().get("system-token");
			dmsSystemToken = dmsSystemToken.replaceAll("\"rootComponent\": ?\"[a-zA-Z-]*\"", "\"rootComponent\": \"dms\"");

			Map<String, Object> transferRequest = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> sender = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> receiver = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> product = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> amount = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> transactionProperties = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> map = Collections.synchronizedMap(new LinkedHashMap<>());

			if (!command.get(Constants.Gp.MSISDN1).toString().isEmpty())
			{
				sender.put(StandardLinkConstants.TXEConstant.TYPE, Constants.Txe.RESELLER_MSISDN);
				sender.put(StandardLinkConstants.TXEConstant.ID, command.get(Constants.Gp.MSISDN1).toString());
			}
			else
			{
				sender.put(StandardLinkConstants.TXEConstant.ID, command.get(Constants.Gp.LOGINID).toString());
				sender.put(StandardLinkConstants.TXEConstant.TYPE, Constants.Txe.RESELLER_ID);
			}
			sender.put(StandardLinkConstants.TXEConstant.ACCOUNT_TYPE_ID, String.valueOf(operationFields.get(Constants.Gp.RESELLER_ACCOUNT_ID)));

			if (!command.get(Constants.Gp.MSISDN2).toString().isEmpty())
			{
				receiver.put(StandardLinkConstants.TXEConstant.ID, command.get(Constants.Gp.MSISDN2).toString());
				receiver.put(StandardLinkConstants.TXEConstant.TYPE, StandardLinkConstants.TXEConstant.RESELLERMSISDN);
			}
			else
			{
				receiver.put(StandardLinkConstants.TXEConstant.ID, command.get(Constants.Gp.LOGINID2).toString());
				receiver.put(StandardLinkConstants.TXEConstant.TYPE, StandardLinkConstants.TXEConstant.RESELLERID);
			}
			receiver.put(StandardLinkConstants.TXEConstant.ACCOUNT_TYPE_ID, String.valueOf(operationFields.get(Constants.Gp.RESELLER_ACCOUNT_ID)));

			amount.put(StandardLinkConstants.TXEConstant.VALUE, commandProduct.get(Constants.Gp.QTY).toString());
			amount.put(StandardLinkConstants.TXEConstant.CURRENCY, constants.get(Constants.Gp.CURRENCY));
			product.put(StandardLinkConstants.TXEConstant.PRODUCT_SKU, operationFields.get(Constants.Txe.PRODUCT_SKU));
			product.put(StandardLinkConstants.TXEConstant.AMOUNT, amount);

			map.put(Constants.Txe.PREFERRED_LANGUAGE, String.valueOf(command.get(Constants.Gp.LANGUAGE1)));
			map.put(Constants.Txe.CLIENT_REFERENCE, command.get(Constants.Gp.EXT_REF_NUM).toString());
			map.put(Constants.Txe.GP_EXT_REQUEST_TYPE, command.get(Constants.Gp.GP_EXT_REQUEST_TYPE).toString());
			map.put(Constants.Txe.GP_EXT_REQUEST_DATE, command.get(Constants.Gp.GP_EXT_REQUEST_DATE).toString());
			map.put(Constants.Txe.EXT_NW_CODE, command.get(Constants.Gp.EXT_NW_CODE).toString());

			if (sender.get(Constants.Txe.TYPE).toString().equalsIgnoreCase(Constants.Txe.RESELLER_MSISDN))
			{
				map.put(Constants.Txe.SENDER_LOGINID, command.get(Constants.Gp.LOGINID).toString());
				map.put(Constants.Txe.SENDER_PASSWORD, command.get(Constants.Gp.PASSWORD).toString());
			}
			else
			{
				map.put(Constants.Txe.SENDER_MSISDN, command.get(Constants.Gp.MSISDN1).toString());
				map.put(Constants.Txe.SENDER_PIN, command.get(Constants.Gp.PIN).toString());
			}

			map.put(Constants.Txe.SENDER_EXT_CODE, command.get(Constants.Gp.EXT_CODE).toString());
			map.put(Constants.Txe.RECEIVER_EXT_CODE, command.get(Constants.Gp.EXT_CODE_2).toString());
			map.put(Constants.Txe.RECEIVER_MSISDN, command.get(Constants.Gp.MSISDN2).toString());
			map.put(Constants.Txe.RECEIVER_LOGINID, command.get(Constants.Gp.LOGINID2).toString());

			map.put(Constants.Gp.GW_LOGINID, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.LOGIN)).get(0)).get(0));
			map.put(Constants.Gp.GW_PASSWORD, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.PASSWORD)).get(0)).get(0));
			map.put(Constants.Gp.GW_CODE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.REQUEST_GATEWAY_CODE)).get(0)).get(0));
			map.put(Constants.Gp.GW_TYPE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.REQUEST_GATEWAY_TYPE)).get(0)).get(0));
			map.put(Constants.Gp.GW_PORT, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.SERVICE_PORT)).get(0)).get(0));
			map.put(Constants.Gp.GW_SOURCE_TYPE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.SOURCE_TYPE)).get(0)).get(0));

			transactionProperties.put(Constants.Txe.MAP, map);

			transferRequest.put(Constants.Txe.SENDER, sender);
			transferRequest.put(Constants.Txe.RECEIVER, receiver);
			transferRequest.put(Constants.Txe.PRODUCT, product);
			transferRequest.put(Constants.Txe.TRANSACTION_PROPERTIES, transactionProperties);

			return MessageBuilder.withPayload(transferRequest).copyHeaders(message.getHeaders()).setHeader(Constants.Headers.SYSTEM_TOKEN, dmsSystemToken).setHeaderIfAbsent(Constants.Headers.VALIDATE_ONLY, false).build();
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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("C2CReturnTransferTransformer :: transformOutboundResponse");

		try
		{
			Map<String, Object> response = new LinkedHashMap<>();
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION).getFields();

			response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));
			response.put(Constants.Gp.GP_RESPONSE_TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));

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
					response.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
					response.put(Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
					response.put(Constants.Gp.GP_TXN_ID, internalResponse.get(Constants.Txe.ERS_REFERENCE));
					response.put(Constants.Gp.EXT_REF_NUM, requestPayloadCommand.get(Constants.Gp.EXT_REF_NUM));
				}
				else
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
					response.put(Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
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
		private static final String PROVIDER = "gp";
		private static final String OPERATION = "c2cReturnTransfer";

		private static final class Headers
		{
			private static final String VALIDATE_ONLY = "validateOnly";
			private static final String SYSTEM_TOKEN = "system-token";
		}

		private static final class Gp
		{
			private static final String MSISDN1 = "MSISDN1";
			private static final String PIN = "PIN";
			private static final String MSISDN2 = "MSISDN2";
			private static final String QTY = "QTY";
			private static final String PRODUCTS = "PRODUCTS";
			private static final String LANGUAGE1 = "LANGUAGE1";
			private static final String EXT_REF_NUM = "EXTREFNUM";
			private static final String CURRENCY = "currency";
			private static final String RESELLER_ACCOUNT_ID = "resellerAccountId";
			private static final String GP_TXN_ID = "TXNID";
			private static final String GP_TXN_STATUS = "TXNSTATUS";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String GP_EXT_REQUEST_TYPE = "TYPE";
			private static final String GP_RESPONSE_TYPE = "TYPE";
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String EXT_NW_CODE = "EXTNWCODE";
			private static final String LOGIN = "LOGIN";
			private static final String LOGINID = "LOGINID";
			private static final String PASSWORD = "PASSWORD";
			private static final String EXT_CODE = "EXTCODE";
			private static final String EXT_CODE_2 = "EXTCODE2";
			private static final String LOGINID2 = "LOGINID2";
			private static final String DATE_FORMAT = "dd/MM/YYYY hh:mm:ss";
			private static final String REQUEST_GATEWAY_CODE = "REQUEST_GATEWAY_CODE";
			private static final String REQUEST_GATEWAY_TYPE = "REQUEST_GATEWAY_TYPE";
			private static final String SERVICE_PORT = "SERVICE_PORT";
			private static final String SOURCE_TYPE = "SOURCE_TYPE";
			private static final String COMMAND = "COMMAND";
			private static final String REQUEST_PAYLOAD = "requestPayload";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String GW_LOGINID = "GATEWAY_LOGINID";
			private static final String GW_PASSWORD = "GATEWAY_PASSWORD";
			private static final String GW_CODE = "REQUEST_GATEWAY_CODE";
			private static final String GW_TYPE = "REQUEST_GATEWAY_TYPE";
			private static final String GW_PORT = "SERVICE_PORT";
			private static final String GW_SOURCE_TYPE = "SOURCE_TYPE";
		}

		private static final class Txe
		{
			private static final String PRODUCT_SKU = "productSKU";
			private static final String PREFERRED_LANGUAGE = "preferredLanguage";
			private static final String CLIENT_REFERENCE = "clientReference";
			private static final String TYPE = "type";
			private static final String SENDER = "sender";
			private static final String RECEIVER = "receiver";
			private static final String PRODUCT = "product";
			private static final String TRANSACTION_PROPERTIES = "transactionProperties";
			private static final String MAP = "map";
			private static final String RESELLER_MSISDN = "RESELLERMSISDN";
			private static final String RESELLER_ID = "RESELLERID";
			private static final String GP_EXT_REQUEST_TYPE = "externalRequestType";
			private static final String GP_EXT_REQUEST_DATE = "externalRequestDate";
			private static final String EXT_NW_CODE = "extNwCode";
			private static final String SENDER_LOGINID = "senderLoginId";
			private static final String SENDER_PASSWORD = "senderPassword";
			private static final String SENDER_MSISDN = "senderMsisdn";
			private static final String SENDER_PIN = "senderPin";
			private static final String SENDER_EXT_CODE = "senderExtCode";
			private static final String RECEIVER_MSISDN = "receiverMsisdn";
			private static final String RECEIVER_EXT_CODE = "receiverExtCode";
			private static final String RECEIVER_LOGINID = "receiverLoginId";
			private static final String ERS_REFERENCE = "ersReference";
			private static final String RESULT_CODE = "resultCode";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String MESSAGE = "message";
			private static final String RESULT_DESCRIPTION = "resultDescription";
		}
	}
}

