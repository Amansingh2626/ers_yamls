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
public class WithdrawBalanceTransformer implements ERSIOTransformer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(WithdrawBalanceTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Override
	public Message<?> transformInboundRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("WithdrawBalanceTransformer :: transformInboundRequest");
		try
		{
            LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.COMMAND);
			String parentResellerId = String.valueOf(requestFields.get(Constants.Txe.PARENT_RESELLER_ID));
			LinkedCaseInsensitiveMap<Object> commandData = (LinkedCaseInsensitiveMap<Object>) command.get(Constants.Gp.DATA);

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION).getFields();
			LinkedCaseInsensitiveMap<String> constants = providerConfigurations.getOperations().get(Constants.OPERATION).getConstants();

			Map<String, Object> sender = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> receiver = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> product = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> amount = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> transactionProperties = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> map = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> withdrawBalanceRequest = Collections.synchronizedMap(new LinkedHashMap<>());

			sender.put(StandardLinkConstants.TXEConstant.ID, parentResellerId);
			sender.put(StandardLinkConstants.TXEConstant.TYPE, Constants.Txe.RESELLER_ID);
			sender.put(StandardLinkConstants.TXEConstant.ACCOUNT_TYPE_ID, String.valueOf(operationFields.get(Constants.Gp.RESELLER_ACCOUNT_ID)));

			if (!commandData.get(Constants.Gp.USER_MSISDN).toString().isEmpty())
			{
				receiver.put(StandardLinkConstants.TXEConstant.ID, commandData.get(Constants.Gp.USER_MSISDN).toString());
				receiver.put(StandardLinkConstants.TXEConstant.TYPE, StandardLinkConstants.TXEConstant.RESELLERMSISDN);
			}
			else
			{
				receiver.put(StandardLinkConstants.TXEConstant.ID, commandData.get(Constants.Gp.USER_ORIGIN_ID).toString());
				receiver.put(StandardLinkConstants.TXEConstant.TYPE, StandardLinkConstants.TXEConstant.RESELLERID);
			}

			receiver.put(StandardLinkConstants.TXEConstant.ACCOUNT_TYPE_ID, String.valueOf(operationFields.get(Constants.Gp.RESELLER_ACCOUNT_ID)));

			amount.put(StandardLinkConstants.TXEConstant.VALUE, requestFields.get(Constants.Gp.WITHDRAWN_AMOUNT).toString());
			amount.put(StandardLinkConstants.TXEConstant.CURRENCY, constants.get(Constants.Gp.CURRENCY));

			product.put(StandardLinkConstants.TXEConstant.PRODUCT_SKU, constants.get(Constants.Txe.PRODUCT_SKU));
			product.put(StandardLinkConstants.TXEConstant.AMOUNT, amount);

			map.put(Constants.Txe.SENDER_PASSWORD, String.valueOf(command.get(Constants.Gp.LANGUAGE1)));
			map.put(Constants.Txe.SENDER_PIN, String.valueOf(command.get(Constants.Gp.LANGUAGE1)));
			map.put(Constants.Txe.CLIENT_REFERENCE, String.valueOf(command.get(Constants.Gp.LANGUAGE1)));
			map.put(Constants.Txe.EXT_TXN_DATE, String.valueOf(command.get(Constants.Gp.LANGUAGE1)));
			map.put(Constants.Gp.GP_EXT_REQUEST_TYPE, String.valueOf(command.get(Constants.Gp.LANGUAGE1)));
			map.put(Constants.Txe.PULL_BACK, String.valueOf(command.get(Constants.Gp.LANGUAGE1)));
			map.put(Constants.Gp.EXT_NW_CODE, "");

			map.put(Constants.Txe.PREFERRED_LANGUAGE, String.valueOf(command.get(Constants.Gp.LANGUAGE1)));
			map.put(Constants.Txe.CLIENT_REFERENCE, command.get(Constants.Gp.EXT_REF_NUM).toString());
			map.put(Constants.Gp.GW_LOGINID, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.LOGIN)).get(0)).get(0));
			map.put(Constants.Gp.GW_PASSWORD, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.PASSWORD)).get(0)).get(0));
			map.put(Constants.Gp.GW_CODE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.REQUEST_GATEWAY_CODE)).get(0)).get(0));
			map.put(Constants.Gp.GW_TYPE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.REQUEST_GATEWAY_TYPE)).get(0)).get(0));
			map.put(Constants.Gp.GW_PORT, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.SERVICE_PORT)).get(0)).get(0));
			map.put(Constants.Gp.GW_SOURCE_TYPE, ((LinkedList<String>) ((ArrayList<Object>) message.getHeaders().get(Constants.Gp.SOURCE_TYPE)).get(0)).get(0));

			map.put(Constants.Txe.APPLICABLE_CONTRACT, 1);

			withdrawBalanceRequest.put(Constants.Txe.SENDER, sender);
			withdrawBalanceRequest.put(Constants.Txe.RECEIVER, receiver);
			withdrawBalanceRequest.put(Constants.Txe.PRODUCT, product);
			withdrawBalanceRequest.put(Constants.Txe.TRANSACTION_PROPERTIES, transactionProperties);

			return MessageBuilder.withPayload(withdrawBalanceRequest).copyHeaders(message.getHeaders()).setHeaderIfAbsent(StandardLinkConstants.Headers.SYSTEM_TOKEN, message.getHeaders().get(StandardLinkConstants.Headers.SYSTEM_TOKEN)).setHeaderIfAbsent("validateOnly", false).build();
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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("WithdrawBalanceTransformer :: transformOutboundResponse");

		try
		{
			Map<String, Object> response = new LinkedHashMap<>();
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION).getFields();

			response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));
			response.put(Constants.Gp.TYPE, operationFields.get(Constants.Txe.RESPONSE_TYPE));
			if (outgoingResponse instanceof CompletableFuture)
			{
				outgoingResponse = ((CompletableFuture<?>) outgoingResponse).get();
			}

			if (outgoingResponse instanceof Exception)
			{
				Exception exception = (Exception) outgoingResponse;
				response.put(Constants.Txe.RESULT_DESCRIPTION, StandardLinkUtilities.getRootCause(exception));
				response.put(Constants.Txe.RESULT_CODE, StandardLinkResultCodes.INTERNAL_COMPONENT_ERROR);
				return response;
			}

			Map<String, Object> internalResponse = (LinkedHashMap<String, Object>) outgoingResponse;

			if (!internalResponse.isEmpty() && internalResponse.containsKey(Constants.Txe.RESULT_CODE))
			{
				Integer resultCodeValue = (Integer) internalResponse.get(Constants.Txe.RESULT_CODE);
				String defaultMessage = String.valueOf(internalResponse.get(Constants.Txe.RESULT_MESSAGE) != null ? internalResponse.get(Constants.Txe.RESULT_MESSAGE) : internalResponse.get(Constants.Txe.MESSAGE) != null ? internalResponse.get(Constants.Txe.MESSAGE) : internalResponse.get(Constants.Txe.RESULT_DESCRIPTION));
				if (resultCodeValue == StandardLinkResultCodes.SUCCESS)
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Txe.RESULT_DESCRIPTION, resultCode.getDescription());
					response.put(Constants.Txe.RESULT_CODE, resultCode.getInternalResultCode());
				}
				else
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Txe.RESULT_DESCRIPTION, resultCode.getDescription());
					response.put(Constants.Txe.RESULT_CODE, resultCode.getInternalResultCode());
				}
			}
			else
			{
				response.put(Constants.Txe.RESULT_DESCRIPTION, "Request has failed on server.");
				response.put(Constants.Txe.RESULT_CODE, StandardLinkResultCodes.FAILED_ON_SERVER);
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
		private static final String OPERATION = "c2cWithdraw";

		private static final class Gp
		{
			private static final String LANGUAGE1 = "LANGUAGE1";
			private static final String EXT_REF_NUM = "EXTREFNUM";
			private static final String CURRENCY = "currency";
			private static final String RESELLER_ACCOUNT_ID = "resellerAccountId";
			private static final String GP_EXT_REQUEST_TYPE = "TYPE";
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String PASSWORD = "PASSWORD";
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
			private static final String USER_MSISDN = "USERMSISDN";
			private static final String USER_ORIGIN_ID = "USERORIGINID";
			private static final String WITHDRAWN_AMOUNT = "withdrawnAmount";
			private static final String EXT_NW_CODE = "EXTNWCODE";
			private static final String DATA = "DATA";
			private static final String TYPE = "TYPE";
		}

		private static final class Txe
		{
			private static final String RESPONSE_TYPE = "responseType";
			private static final String PRODUCT_SKU = "productSKU";
			private static final String PREFERRED_LANGUAGE = "preferredLanguage";
			private static final String CLIENT_REFERENCE = "clientReference";
			private static final String SENDER = "sender";
			private static final String RECEIVER = "receiver";
			private static final String PRODUCT = "product";
			private static final String TRANSACTION_PROPERTIES = "transactionProperties";
			private static final String RESELLER_ID = "RESELLERID";
			private static final String RESULT_CODE = "resultCode";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String MESSAGE = "message";
			private static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String PARENT_RESELLER_ID = "parentResellerId";
			private static final String APPLICABLE_CONTRACT = "applicableContract";
			private static final String SENDER_PASSWORD = "senderPassword";
			private static final String SENDER_PIN = "senderPin";
			private static final String EXT_TXN_DATE = "extTxnDate";
			private static final String PULL_BACK = "PULL_BACK";
		}
	}
}
