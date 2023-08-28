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

@Component("o2CWithdrawTransformer")
public class O2CWithdrawTransformer implements ERSIOTransformer
{

	private static final Logger LOGGER = LoggerFactory.getLogger(O2CWithdrawTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Override
	public Object transformInboundRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("O2CWithdrawTransformer :: transformInboundRequest");

		try
		{
			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.COMMAND);
			LinkedCaseInsensitiveMap<Object> commandProduct = (LinkedCaseInsensitiveMap<Object>) command.get(Constants.Gp.PRODUCTS);

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			Map<String, Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();
			LinkedCaseInsensitiveMap<String> constants = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getConstants();

			Map<String, Object> o2cWithdrawRequest = Collections.synchronizedMap(new LinkedHashMap<>());

			Map<String, Object> receiver = Collections.synchronizedMap(new LinkedHashMap<>());
			receiver.put(Constants.Txe.TYPE, Constants.Txe.RESELLERID);
			receiver.put(Constants.Txe.ID, String.valueOf(operationFields.get(Constants.Gp.RESELLER_RECEIVER_ID)));
			receiver.put(Constants.Txe.ACCOUNT_TYPE_ID, String.valueOf(operationFields.get(Constants.Gp.RESELLER_ACCOUNT_ID)));

			Map<String, Object> sender = new LinkedHashMap<>();
			sender.put(Constants.Txe.TYPE, Constants.Txe.RESELLERMSISDN);
			sender.put(Constants.Txe.ACCOUNT_TYPE_ID, String.valueOf(operationFields.get(Constants.Gp.RESELLER_ACCOUNT_ID)));
                        Object senderMsisdn = null;

			if (message.getHeaders().containsKey(O2CWithdrawTransformer.Constants.Txe.RECEIVER_MSISDN) ||
					message.getHeaders().containsKey(O2CWithdrawTransformer.Constants.Txe.RECEIVER_ID))
			{
				senderMsisdn = message.getHeaders().get(O2CWithdrawTransformer.Constants.Txe.RECEIVER_MSISDN);
				sender.put(Constants.Txe.ID, senderMsisdn);
				LOGGER.debug("Fetched receiverMsisdn :" + senderMsisdn + " from header successfully");
			}else {
				sender.put(Constants.Txe.ID, String.valueOf(command.get(Constants.Gp.MSISDN)));
			}

			Map<String, Object> amount = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> product = Collections.synchronizedMap(new LinkedHashMap<>());
			if (commandProduct != null)
			{
				amount.put(StandardLinkConstants.TXEConstant.VALUE, commandProduct.get(Constants.Gp.QTY).toString());
				amount.put(StandardLinkConstants.TXEConstant.CURRENCY, constants.get(Constants.Gp.CURRENCY));

				product.put(Constants.Txe.PRODUCT_SKU, operationFields.get(Constants.Gp.PRODUCTSKU));
				product.put(Constants.Txe.AMOUNT, amount);
			}
			else
			{
				LOGGER.info("The product info is null for operation " + Constants.Gp.OPERATION);
			}

			Map<String, Object> transactionProperties = Collections.synchronizedMap(new LinkedHashMap<>());

			Map<String, Object> requestMap = Collections.synchronizedMap(new LinkedHashMap<>());
			requestMap.put(Constants.Txe.SENDER_EXTCODE, String.valueOf(command.get(Constants.Gp.EXTCODE)));
			requestMap.put(Constants.Txe.SENDER_PIN, String.valueOf(command.get(Constants.Gp.PIN)));
			requestMap.put(Constants.Txe.CLIENT_REFERENCE, String.valueOf(command.get(Constants.Gp.EXT_TXNNUMBER)));
			requestMap.put(Constants.Txe.PULL_BACK, operationFields.get(Constants.Txe.PULL_BACK));
			requestMap.put(Constants.Txe.TYPE, String.valueOf(command.get(Constants.Gp.GP_EXT_REQUEST_TYPE)));
			requestMap.put(Constants.Txe.EXTNWCODE, String.valueOf(command.get(Constants.Gp.EXTNWCODE)));
			requestMap.put(Constants.Txe.PRODUCT_CODE, String.valueOf(commandProduct.get(Constants.Gp.PRODUCTCODE)));
			requestMap.put(Constants.Txe.EXT_TXNDATE, String.valueOf(command.get(Constants.Gp.EXT_TXNDATE)));
			requestMap.put(Constants.Txe.REMARKS, String.valueOf(command.get(Constants.Gp.REMARKS)));
			requestMap.put(Constants.Gp.USERORIGINID, String.valueOf(command.get(com.seamless.ers.standardlink.transformers.O2CWithdrawTransformer.Constants.Gp.USERORIGINID)));

			requestMap.put(Constants.Gp.GW_LOGINID, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.LOGIN)).get(0)).get(0));
			requestMap.put(Constants.Gp.GW_PASSWORD, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.PASSWORD)).get(0)).get(0));
			requestMap.put(Constants.Gp.GW_CODE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.REQUEST_GATEWAY_CODE)).get(0)).get(0));
			requestMap.put(Constants.Gp.GW_TYPE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.REQUEST_GATEWAY_TYPE)).get(0)).get(0));
			requestMap.put(Constants.Gp.GW_PORT, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.SERVICE_PORT)).get(0)).get(0));
			requestMap.put(Constants.Gp.GW_SOURCE_TYPE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.SOURCE_TYPE)).get(0)).get(0));
			requestMap.put(Constants.Gp.APPLICABLECONTRACT, 1);
			transactionProperties.put(Constants.Txe.MAP, requestMap);

			o2cWithdrawRequest.put(Constants.Txe.SENDER, sender);
			o2cWithdrawRequest.put(Constants.Txe.RECEIVER, receiver);
			o2cWithdrawRequest.put(Constants.Txe.PRODUCT, product);
			o2cWithdrawRequest.put(Constants.Txe.TRANSACTION_PROPERTIES, transactionProperties);

			return MessageBuilder.withPayload(o2cWithdrawRequest).copyHeaders(message.getHeaders()).setHeaderIfAbsent(StandardLinkConstants.Headers.SYSTEM_TOKEN, message.getHeaders().get(Constants.Gp.SYSTEM_TOKEN)).setHeaderIfAbsent(Constants.Txe.VALIDATE_ONLY, false).build();
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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("O2CWithdrawTransformer :: transformOutboundResponse");

		try
		{
			Map<String, Object> response = new LinkedHashMap<>();
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();
			response.put(Constants.Gp.TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));
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
					response.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
					response.put(Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
					response.put(Constants.Gp.GP_TXN_ID, internalResponse.get(Constants.Txe.ERS_REFERENCE));
					response.put(Constants.Gp.EXTREFNUM, requestPayloadCommand.get(Constants.Gp.EXTREFNUM));
				}
				else
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
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
		private static final class Gp
		{
			private static final String PRODUCTSKU = "ProductSKU";
			private static final String USERORIGINID = "USERORIGINID";
			private static final String APPLICABLECONTRACT = "applicableContract";
			private static final String PROVIDER = "gp";
			private static final String OPERATION = "o2cWithdraw";
			private static final String MSISDN = "MSISDN";
			private static final String PIN = "PIN";
			private static final String PRODUCTCODE = "PRODUCTCODE";
			private static final String QTY = "QTY";
			private static final String PRODUCTS = "PRODUCTS";
			private static final String EXTREFNUM = "EXTREFNUM";
			private static final String CURRENCY = "currency";
			private static final String RESELLER_ACCOUNT_ID = "resellerAccountId";
			private static final String RESELLER_RECEIVER_ID = "resellerReceiverId";
			private static final String GP_TXN_ID = "TXNID";
			private static final String GP_TXN_STATUS = "TXNSTATUS";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String EXT_TXNNUMBER = "EXTTXNNUMBER";
			private static final String GP_EXT_REQUEST_TYPE = "TYPE";
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String EXTNWCODE = "EXTNWCODE";
			private static final String PASSWORD = "PASSWORD";
			private static final String EXTCODE = "EXTCODE";
			private static final String REMARKS = "REMARKS";
			private static final String EXT_TXNDATE = "EXTTXNDATE";
			private static final String DATE_FORMAT = "dd/MM/YYYY hh:mm:ss";
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
			private static final String COMMAND = "COMMAND";
			private static final String REQUEST_PAYLOAD = "requestPayload";
			private static final String SYSTEM_TOKEN = "system-token";
			private static final String TYPE = "TYPE";
			private static final String RESPONSE_TYPE = "responseType";
		}

		private static final class Txe
		{
			private static final String PULL_BACK = "PULL_BACK";
			private static final String SENDER_EXTCODE = "senderExtCode";
			private static final String SENDER_PIN = "senderPin";
			private static final String PRODUCT_SKU = "productSKU";
			private static final String CLIENT_REFERENCE = "clientReference";
			private static final String TYPE = "type";
			private static final String ID = "id";
			private static final String ACCOUNT_TYPE_ID = "accountTypeId";
			private static final String SENDER = "sender";
			private static final String RECEIVER = "receiver";
			private static final String PRODUCT = "product";
			private static final String TRANSACTION_PROPERTIES = "transactionProperties";
			private static final String AMOUNT = "amount";
			private static final String MAP = "map";
			private static final String RESELLERMSISDN = "RESELLERMSISDN";
			private static final String RESELLERID = "RESELLERID";
			private static final String EXTNWCODE = "extNwCode";
			private static final String ERS_REFERENCE = "ersReference";
			private static final String PRODUCT_CODE = "productCode";
			private static final String EXT_TXNDATE = "extTxnDate";
			private static final String REMARKS = "remarks";
			private static final String RESULT_CODE = "resultCode";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String MESSAGE = "message";
			private static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String VALIDATE_ONLY = "validateOnly";
                        private static final String RECEIVER_MSISDN = "receivermsisdn";
                        private static final String RECEIVER_ID = "receiverid";
		}
	}
}

