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
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component("o2CDirectTransferTransformer")
public class O2CDirectTransferTransformer implements ERSIOTransformer
{

	private static final Logger LOGGER = LoggerFactory.getLogger(O2CWithdrawTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Override
	public Message<?> transformInboundRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("O2CDirectTransferTransformer :: transformInboundRequest");

		try
		{
			LOGGER.debug("######## transformInboundRequest Thread ####### :- " + Thread.currentThread().getName());
			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.COMMAND);
			LinkedCaseInsensitiveMap<Object> commandProduct = (LinkedCaseInsensitiveMap<Object>) command.get(Constants.Gp.PRODUCTS);
			LinkedCaseInsensitiveMap<Object> commandPaymentDetails = (LinkedCaseInsensitiveMap<Object>) command.get(Constants.Gp.PAYMENT_DETAILS);

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			Map<String, Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();
			LinkedCaseInsensitiveMap<String> constants = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getConstants();

			Map<String, Object> o2cDirectTransferRequest = Collections.synchronizedMap(new LinkedHashMap<>());

			Map<String, Object> senderRequestMap = Collections.synchronizedMap(new LinkedHashMap<>());
			senderRequestMap.put(Constants.Txe.TYPE, Constants.Txe.RESELLERID);
			senderRequestMap.put(Constants.Txe.ID, String.valueOf(operationFields.get(Constants.Gp.RESELLER_SENDER_ID)));
			senderRequestMap.put(Constants.Txe.ACCOUNT_TYPE_ID, String.valueOf(operationFields.get(Constants.Gp.RESELLER_ACCOUNT_ID)));

			Map<String, Object> receiverRequestMap = Collections.synchronizedMap(new LinkedHashMap<>());

			Object receiverMsisdn = null, receiverId = null;

			if (message.getHeaders().containsKey(Constants.Txe.RECEIVER_MSISDN) || message.getHeaders().containsKey(Constants.Txe.RECEIVER_ID))
			{
				try
				{
					receiverMsisdn = message.getHeaders().get(Constants.Txe.RECEIVER_MSISDN);
					receiverId = message.getHeaders().get(Constants.Txe.RECEIVER_ID);
					LOGGER.debug("Fetched receiverId :" + receiverId + " and receiverMsisdn :" + receiverMsisdn + " from header successfully");

				}
				catch (Exception exception)
				{
					LOGGER.error("Failed to fetch receiverId and receiverMsisdn from header ", message.getHeaders(),
							ExceptionUtils.getFullStackTrace(exception));
				}
			}
			receiverRequestMap.put(Constants.Txe.TYPE, Constants.Txe.RESELLERMSISDN);
			if (Objects.nonNull(receiverMsisdn))
			{
				receiverRequestMap.put(Constants.Txe.ID, receiverMsisdn);
			}
			else
			{
				receiverRequestMap.put(Constants.Txe.ID, String.valueOf(command.get(Constants.Gp.MSISDN)));
			}
			receiverRequestMap.put(Constants.Txe.ACCOUNT_TYPE_ID, String.valueOf(operationFields.get(Constants.Gp.RESELLER_ACCOUNT_ID)));

			Map<String, Object> amountRequestMap = Collections.synchronizedMap(new LinkedHashMap<>());
                        amountRequestMap.put(Constants.Txe.VALUE, new BigDecimal(String.valueOf(commandProduct.get(Constants.Gp.QTY))));
			amountRequestMap.put(Constants.Txe.CURRENCY, constants.get(Constants.Gp.CURRENCY));

			Map<String, Object> productRequestMap = Collections.synchronizedMap(new LinkedHashMap<>());
			productRequestMap.put(Constants.Txe.PRODUCT_SKU, operationFields.get(String.valueOf(commandProduct.get(Constants.Gp.PRODUCTCODE))));
			productRequestMap.put(Constants.Txe.AMOUNT, amountRequestMap);
			Map<String, Object> requestMap = new LinkedHashMap<>();

			if (command.get(Constants.Gp.TRF_CATEGORY) != null
					&& !StringUtils.isEmpty(command.get(Constants.Gp.TRF_CATEGORY).toString())
					&& command.get(Constants.Gp.TRF_CATEGORY).toString().equals(Constants.Gp.SALE))
			{
				requestMap.put(Constants.Gp.APPLICABLECONTRACT, 1);
receiverRequestMap.put(Constants.Txe.ACCOUNT_TYPE_ID, String.valueOf(operationFields.get(Constants.Gp.RESELLER_ACCOUNT_ID+"_"+Constants.Gp.SALE)));
				productRequestMap.put(Constants.Txe.PRODUCT_SKU, operationFields.get(commandProduct.get(Constants.Gp.PRODUCTCODE) + "_SALE"));
				requestMap.put(Constants.Txe.TRF_CATEGORY, command.get(Constants.Gp.TRF_CATEGORY));
			}
			else if (command.get(Constants.Gp.TRF_CATEGORY) != null
					&& !StringUtils.isEmpty(command.get(Constants.Gp.TRF_CATEGORY).toString())
					&& command.get(Constants.Gp.TRF_CATEGORY).toString().equals(Constants.Gp.FOC))
			{
				productRequestMap.put(Constants.Txe.PRODUCT_SKU, operationFields.get(commandProduct.get(Constants.Gp.PRODUCTCODE) + "_FOC"));
receiverRequestMap.put(Constants.Txe.ACCOUNT_TYPE_ID, String.valueOf(operationFields.get(Constants.Gp.RESELLER_ACCOUNT_ID+"_"+Constants.Gp.FOC)));
				requestMap.put(Constants.Txe.TRF_CATEGORY, command.get(Constants.Gp.TRF_CATEGORY));
			}
			else
			{
				LOGGER.info("As a proper trf category is not given so unable to define product SKU. ");
			}

			if (productRequestMap.get(Constants.Txe.PRODUCT_SKU) == null || StringUtils.isEmpty(productRequestMap.get(Constants.Txe.PRODUCT_SKU).toString()))
			{
				productRequestMap.put(Constants.Txe.PRODUCT_SKU, operationFields.get(Constants.DEFAULT_PRODUCT_SKU));
			}
			Map<String, Object> transactionProperties = new LinkedHashMap<>();

			requestMap.put(Constants.Txe.TYPE, String.valueOf(command.get(Constants.Gp.GP_EXT_REQUEST_TYPE)));
			requestMap.put(Constants.Txe.EXTNWCODE, String.valueOf(command.get(Constants.Gp.EXTNWCODE)));
			requestMap.put(Constants.Txe.USERORIGIN_ID, String.valueOf(command.get(Constants.Gp.USERORIGIN_ID)));
			requestMap.put(Constants.Txe.RECEIVER_PIN, String.valueOf(command.get(Constants.Gp.PIN)));
			requestMap.put(Constants.Txe.RECEIVER_EXTCODE, String.valueOf(command.get(Constants.Gp.EXTCODE)));
			requestMap.put(Constants.Txe.CLIENT_REFERENCE, String.valueOf(command.get(Constants.Gp.EXT_TXNNUMBER)));
			requestMap.put(Constants.Txe.EXT_TXNDATE, String.valueOf(command.get(Constants.Gp.EXT_TXNDATE)));
			requestMap.put(Constants.Txe.PRODUCT_CODE, String.valueOf(commandProduct.get(Constants.Gp.PRODUCTCODE)));
			requestMap.put(Constants.Txe.REF_NUMBER, String.valueOf(command.get(Constants.Gp.REF_NUMBER)));
			requestMap.put(Constants.Txe.REMARKS, String.valueOf(command.get(Constants.Gp.REMARKS)));

			if (Objects.nonNull(command.get(Constants.Gp.QTY)) && !commandPaymentDetails.get(Constants.Gp.QTY).toString().isEmpty())
				requestMap.put(Constants.Txe.QTY, String.valueOf(commandProduct.get(Constants.Gp.QTY)));

			requestMap.put(Constants.Gp.GW_LOGINID, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.LOGIN)).get(0)).get(0));
			requestMap.put(Constants.Gp.GW_PASSWORD, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.PASSWORD)).get(0)).get(0));
			requestMap.put(Constants.Gp.GW_CODE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.REQUEST_GATEWAY_CODE)).get(0)).get(0));
			requestMap.put(Constants.Gp.GW_TYPE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.REQUEST_GATEWAY_TYPE)).get(0)).get(0));
			requestMap.put(Constants.Gp.GW_PORT, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.SERVICE_PORT)).get(0)).get(0));
			requestMap.put(Constants.Gp.GW_SOURCE_TYPE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.SOURCE_TYPE)).get(0)).get(0));

			//requestMap.put(Constants.Txe.TRF_CATEGORY, String.valueOf(command.get(Constants.Gp.TRF_CATEGORY)));

			if (commandPaymentDetails.containsKey(Constants.Gp.PAYMENT_TYPE))
				requestMap.put(Constants.Txe.INSTRUMENT_TYPE, String.valueOf(commandPaymentDetails.get(Constants.Gp.PAYMENT_TYPE)));

			if (commandPaymentDetails.containsKey(Constants.Gp.PAYMENT_INST_NUMBER))
				requestMap.put(Constants.Txe.PAYMENT_INSTRUMENT_NUMBER, commandPaymentDetails.get(Constants.Gp.PAYMENT_INST_NUMBER) == null ? "" :
						commandPaymentDetails.get(Constants.Gp.PAYMENT_INST_NUMBER));

			if (commandPaymentDetails.containsKey(Constants.Gp.PAYMENT_DATE))
				requestMap.put(Constants.Txe.PAYMENT_INSTRUMENT_DATE, commandPaymentDetails.get(Constants.Gp.PAYMENT_DATE));

			if (Objects.nonNull(command.get(Constants.Gp.NETPAYABLE_AMOUNT)) && !commandPaymentDetails.get(Constants.Gp.NETPAYABLE_AMOUNT).toString().isEmpty())
				requestMap.put(Constants.Gp.NETPAYABLE_AMOUNT, commandProduct.get(Constants.Gp.NETPAYABLE_AMOUNT));

			transactionProperties.put(Constants.Txe.MAP, requestMap);

			o2cDirectTransferRequest.put(Constants.Txe.SENDER, senderRequestMap);
			o2cDirectTransferRequest.put(Constants.Txe.RECEIVER, receiverRequestMap);
			o2cDirectTransferRequest.put(Constants.Txe.PRODUCT, productRequestMap);
			o2cDirectTransferRequest.put(Constants.Txe.TRANSACTION_PROPERTIES, transactionProperties);

			return MessageBuilder.withPayload(o2cDirectTransferRequest).copyHeaders(message.getHeaders()).setHeaderIfAbsent(StandardLinkConstants.Headers.SYSTEM_TOKEN, message.getHeaders().get(Constants.Gp.SYSTEM_TOKEN)).setHeaderIfAbsent(Constants.Txe.VALIDATE_ONLY, false).build();
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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("O2CDirectTransferTransformer :: transformOutboundResponse");

		try
		{
			LOGGER.debug("######## transformOutboundResponse Thread ####### :- " + Thread.currentThread().getName());

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
					response.put(Constants.Gp.EXT_TXNNUMBER, requestPayloadCommand.get(Constants.Gp.EXT_TXNNUMBER));
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
		private static final String DEFAULT_PRODUCT_SKU = "defaultProductSKU";

		private static final class Gp
		{
			private static final String APPLICABLECONTRACT = "applicableContract";
			private static final String PROVIDER = "gp";
			private static final String OPERATION = "o2cDirectTransfer";
			private static final String MSISDN = "MSISDN";
			private static final String PIN = "PIN";
			private static final String PRODUCTCODE = "PRODUCTCODE";
			private static final String QTY = "QTY";
			private static final String PRODUCTS = "PRODUCTS";
			private static final String EXTREFNUM = "EXTREFNUM";
			private static final String CURRENCY = "currency";
			private static final String RESELLER_ACCOUNT_ID = "resellerAccountId";
			private static final String RESELLER_SENDER_ID = "resellerSenderId";
			private static final String GP_TXN_ID = "TXNID";
			private static final String GP_TXN_STATUS = "TXNSTATUS";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String PAYMENT_DETAILS = "PAYMENTDETAILS";
			private static final String NETPAYABLE_AMOUNT = "NETPAYABLEAMOUNT";
			private static final String USERORIGIN_ID = "USERORIGINID";
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
			private static final String TRF_CATEGORY = "TRFCATEGORY";
			private static final String PAYMENT_TYPE = "PAYMENTTYPE";
			private static final String PAYMENT_INST_NUMBER = "PAYMENTINSTNUMBER";
			private static final String REF_NUMBER = "REFNUMBER";
			private static final String PAYMENT_DATE = "PAYMENTDATE";
			private static final String LOGIN = "LOGIN";
			private static final String REQUEST_GATEWAY_CODE = "REQUEST_GATEWAY_CODE";
			private static final String REQUEST_GATEWAY_TYPE = "REQUEST_GATEWAY_TYPE";
			private static final String SERVICE_PORT = "SERVICE_PORT";
			private static final String SOURCE_TYPE = "SOURCE_TYPE";
			private static final String COMMAND = "COMMAND";
			private static final String REQUEST_PAYLOAD = "requestPayload";
			private static final String SYSTEM_TOKEN = "system-token";
			private static final String FOC = "FOC";
			private static final String SALE = "SALE";
			private static final String TYPE = "TYPE";
			private static final String RESPONSE_TYPE = "responseType";
		}

		private static final class Txe
		{
			private static final String PAYMENT_INSTRUMENT_DATE = "paymentInstrumentDate";
			private static final String PAYMENT_INSTRUMENT_NUMBER = "paymentInstrumentNumber";
			private static final String INSTRUMENT_TYPE = "instrumentType";
			private static final String RECEIVER_MSISDN = "receivermsisdn";
			private static final String RECEIVER_ID = "receiverid";
			private static final String TRF_CATEGORY = "trfCategory";
			private static final String PRODUCT_SKU = "productSKU";
			private static final String CURRENCY = "currency";
			private static final String CLIENT_REFERENCE = "clientReference";
			private static final String TYPE = "type";
			private static final String ID = "id";
			private static final String ACCOUNT_TYPE_ID = "accountTypeId";
			private static final String SENDER = "sender";
			private static final String RECEIVER = "receiver";
			private static final String PRODUCT = "product";
			private static final String TRANSACTION_PROPERTIES = "transactionProperties";
			private static final String VALUE = "value";
			private static final String AMOUNT = "amount";
			private static final String MAP = "map";
			private static final String RESELLERMSISDN = "RESELLERMSISDN";
			private static final String RESELLERID = "RESELLERID";
			private static final String EXTNWCODE = "extNwCode";
			private static final String RECEIVER_PIN = "receiverPin";
			private static final String RECEIVER_EXTCODE = "receiverExtCode";
			private static final String ERS_REFERENCE = "ersReference";
			private static final String USERORIGIN_ID = "USERORIGINID";
			private static final String PRODUCT_CODE = "productCode";
			private static final String EXT_TXNDATE = "extTxnDate";
			private static final String REMARKS = "remarks";
			private static final String QTY = "qty";
			private static final String REF_NUMBER = "refNumber";
			private static final String RESULT_CODE = "resultCode";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String MESSAGE = "message";
			private static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String VALIDATE_ONLY = "validateOnly";
		}
	}
}
