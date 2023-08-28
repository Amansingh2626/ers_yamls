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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component("userBalanceEnquiryTransformer")
public class UserBalanceEnquiryTransformer implements ERSIOTransformer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(UserBalanceEnquiryTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Override
	public Message<?> transformInboundRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("UserBalanceEnquiryTransformer :: transformInboundRequest");

		try
		{
			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.COMMAND);
			LinkedCaseInsensitiveMap<Object> data = (LinkedCaseInsensitiveMap<Object>) command.get(Constants.Gp.DATA);

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION).getFields();

			String dmsSystemToken = (String) message.getHeaders().get("system-token");
			dmsSystemToken = dmsSystemToken.replaceAll("\"rootComponent\": ?\"[a-zA-Z-]*\"", "\"rootComponent\": \"dms\"");

			Map<String, Object> channelUserBalanceEnquiryRequest = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> dealerId = Collections.synchronizedMap(new LinkedHashMap<>());

			if (data.get(Constants.Gp.MSISDN) != null && !String.valueOf(data.get(Constants.Gp.MSISDN)).isEmpty())
			{
				dealerId.put(Constants.Txe.ID, String.valueOf(data.get(Constants.Gp.MSISDN)));
				dealerId.put(Constants.Txe.TYPE, operationFields.getOrDefault(Constants.DEALER_ID_TYPE, Constants.Txe.RESELLERMSISDN));
			}
			else
			{
				dealerId.put(Constants.Txe.ID, String.valueOf(data.get(Constants.Gp.USER_LOGIN_ID)));
				dealerId.put(Constants.Txe.TYPE, operationFields.getOrDefault(Constants.DEALER_ID_TYPE, Constants.Txe.RESELLERID));
			}

			channelUserBalanceEnquiryRequest.put(Constants.Txe.DEALER_ID, dealerId);
			channelUserBalanceEnquiryRequest.put(Constants.Txe.FETCH_ACCOUNT_BALANCE_INFORMATION, "true");
			return MessageBuilder.withPayload(channelUserBalanceEnquiryRequest).copyHeaders(message.getHeaders()).setHeaderIfAbsent(StandardLinkConstants.Headers.AUTHORIZATION, message.getHeaders().get(Constants.AUTHORIZATION)).setHeader(StandardLinkConstants.Headers.SYSTEM_TOKEN, dmsSystemToken).build();
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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("UserBalanceEnquiryTransformer :: transformOutboundResponse");

		try
		{
			Map<String, Object> response = new LinkedHashMap<>();

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION).getFields();
			LinkedCaseInsensitiveMap<String> constants = providerConfigurations.getOperations().get(Constants.OPERATION).getConstants();
			response.put(Constants.Gp.GP_EXT_REQUEST_TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));
			response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));

			if (outgoingResponse instanceof CompletableFuture)
			{
				outgoingResponse = ((CompletableFuture<?>) outgoingResponse).get();
			}

			if (outgoingResponse instanceof Exception)
			{
				Exception exception = (Exception) outgoingResponse;
				response.put(Constants.Gp.GP_MESSAGE, StandardLinkUtilities.getRootCause(exception));
				response.put(Constants.Gp.TXNSTATUS, StandardLinkResultCodes.INTERNAL_COMPONENT_ERROR);
				response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));
				return response;
			}

			Map<String, Object> internalResponse = (LinkedHashMap<String, Object>) outgoingResponse;
			Map<String, Object> request = (Map<String, Object>) internalResponse.get(Constants.Txe.REQUEST_PAYLOAD);
			Map<String, Object> requestPayloadCommand = (Map<String, Object>) request.get(Constants.Gp.COMMAND);
			Map<String, Object> requestPayloadCommandData = (Map<String, Object>) requestPayloadCommand.get(Constants.Gp.DATA);

			if (!internalResponse.isEmpty() && internalResponse.containsKey(Constants.Txe.RESULT_CODE))
			{
				Integer resultCodeValue = (Integer) internalResponse.get(Constants.Txe.RESULT_CODE);
				String defaultMessage = String.valueOf(internalResponse.get(Constants.Txe.RESULT_MESSAGE) != null ? internalResponse.get(Constants.Txe.RESULT_MESSAGE) : internalResponse.get(Constants.Txe.MESSAGE));

				if (resultCodeValue == StandardLinkResultCodes.SUCCESS)
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					Map<String, Object> resellerInfo = (Map<String, Object>) internalResponse.get(Constants.Txe.RESELLER_INFO);
					Map<String, Object> resellerData = (Map<String, Object>) resellerInfo.get(Constants.Txe.RESELLER_DATA);
					List<Map<String, Object>> accounts = (List<Map<String, Object>>) resellerData.get(Constants.Txe.ACCOUNTS);
					Map<String, Object> account = accounts.get(0);
					Map<String, Object> balance = (Map<String, Object>) account.get(Constants.Txe.BALANCE);

					Map<String, Object> record = new LinkedHashMap<>();
					Map<String, Object> data = new LinkedHashMap<>();
					response.put(Constants.Gp.TXNSTATUS, resultCode.getInternalResultCode());
					response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));
					response.put(Constants.Gp.EXTREFNUM, requestPayloadCommand.get(Constants.Gp.EXTREFNUM));

					record.put(Constants.Gp.BALANCE, fetchResellersBalance(internalResponse));
					record.put(Constants.Gp.PRODUCTCODE, constants.get(Constants.PRODUCTCODE));
					record.put(Constants.Gp.PRODUCTSHORTNAME, constants.get(Constants.PRODUCTSHORTCODE));

					data.put(Constants.Gp.MSISDN, requestPayloadCommandData.get(Constants.Gp.MSISDN));
					data.put(Constants.Gp.USERLOGINID, requestPayloadCommandData.get(Constants.Gp.USERLOGINID));
					data.put(Constants.Gp.RECORD, record);
					response.put(Constants.Gp.DATA, data);
				}
				else
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
					response.put(Constants.Gp.TXNSTATUS, resultCode.getInternalResultCode());
				}
			}
			else
			{
				response.put(Constants.Gp.GP_MESSAGE, "Request has failed on server.");
				response.put(Constants.Gp.TXNSTATUS, StandardLinkResultCodes.FAILED_ON_SERVER);
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

	private String fetchResellersBalance(Map<String, Object> responseMap)
	{
		LinkedHashMap<String, Object> resellerInfo = (LinkedHashMap<String, Object>) responseMap.get(Constants.Txe.RESELLER_INFO);
		LinkedHashMap<String, Object> resellerData = (LinkedHashMap<String, Object>) resellerInfo.get(Constants.Txe.RESELLER_DATA);
		List accounts = (List) resellerData.get(Constants.Txe.ACCOUNTS);
		double totalAvailableBalance = 0l;
		for (Object account : accounts)
		{
			LinkedHashMap<String, Object> accountInfoMap = (LinkedHashMap<String, Object>) account;
			LinkedHashMap<String, Object> balanceMap = (LinkedHashMap<String, Object>) accountInfoMap.get(Constants.Txe.BALANCE);
			totalAvailableBalance += (Double) balanceMap.get(Constants.Txe.VALUE);
		}
		return new DecimalFormat("0.00").format(totalAvailableBalance);
	}

	private static final class Constants
	{
		private static final String PRODUCTSHORTCODE = "productShortName";
		private static final String PROVIDER = "gp";
		private static final String OPERATION = "USERBALANCEENQUIRY";
		private static final String AUTHORIZATION = "authorization";
		private static final String DEALER_ID_TYPE = "dealerTypeId";
		private static final String PRODUCTCODE = "PRODUCTCODE";

		private static final class Gp
		{
			private static final String USER_LOGIN_ID = "USERLOGINID";
			private static final String GP_EXT_REQUEST_TYPE = "TYPE";
			private static final String MSISDN = "MSISDN";
			private static final String USERLOGINID = "USERLOGINID";
			private static final String PRODUCTCODE = "PRODUCTCODE";
			private static final String PRODUCTSHORTNAME = "PRODUCTSHORTNAME";
			private static final String EXTREFNUM = "EXTREFNUM";
			private static final String TXNSTATUS = "TXNSTATUS";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String DATE_FORMAT = "dd/MM/YYYY hh:mm:ss";
			private static final String COMMAND = "COMMAND";
			private static final String DATA = "DATA";
			private static final String BALANCE = "BALANCE";
			private static final String RECORD = "RECORD";
			private static final String RESPONSE_TYPE = "responseType";
		}

		private static final class Txe
		{
			private static final String TYPE = "type";
			private static final String ID = "id";
			private static final String DEALER_ID = "dealerID";
			private static final String BALANCE = "balance";
			private static final String VALUE = "value";
			private static final String FETCH_ACCOUNT_BALANCE_INFORMATION = "fetchAccountBalanceInformation";
			private static final String RESELLERMSISDN = "RESELLERMSISDN";
			private static final String RESELLERID = "RESELLERID";
			private static final String RESULT_CODE = "resultCode";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String REQUEST_PAYLOAD = "requestPayload";
			private static final String RESELLER_DATA = "resellerData";
			private static final String RESELLER_INFO = "resellerInfo";
			private static final String MESSAGE = "message";
			private static final String ACCOUNTS = "accounts";
		}
	}
}

