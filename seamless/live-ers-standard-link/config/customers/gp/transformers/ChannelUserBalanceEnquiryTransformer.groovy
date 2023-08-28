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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component
public class ChannelUserBalanceEnquiryTransformer implements ERSIOTransformer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ChannelUserBalanceEnquiryTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Override
	@Async("channelUserBalanceEnquiryRequestTaskExecutor")
	public Message<?> transformInboundRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("ChannelUserBalanceEnquiryTransformer :: transformOutboundRequest");
		try
		{
            LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.COMMAND);

			Map<String, Object> channelUserBalanceEnquiryRequest = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> dealerId = Collections.synchronizedMap(new LinkedHashMap<>());
			if (!command.get(Constants.Gp.MSISDN).toString().isEmpty())
			{
				dealerId.put(Constants.Txe.ID, command.get(Constants.Gp.MSISDN).toString());
				dealerId.put(Constants.Txe.TYPE, Constants.Txe.RESELLERMSISDN);
			}
			else
			{
				dealerId.put(Constants.Txe.ID, command.get(Constants.Gp.LOGINID).toString());
				dealerId.put(Constants.Txe.TYPE,"RESELLERUSERNAME");
			}

			channelUserBalanceEnquiryRequest.put(Constants.Txe.DEALER_ID, dealerId);
			channelUserBalanceEnquiryRequest.put(Constants.Txe.FETCH_ACCOUNT_BALANCE_INFORMATION, "true");
			return MessageBuilder.withPayload(channelUserBalanceEnquiryRequest).copyHeaders(message.getHeaders())
					.setHeader("errorChannel",message.getHeaders().get("errorChannel"))
					.setHeader("history",message.getHeaders().get("history"))
					.setHeader("PROVIDER_ID",message.getHeaders().get("PROVIDER_ID"))
					.setHeader("replyChannel",message.getHeaders().get("replyChannel"))
					.setHeader("authorization",message.getHeaders().get("authorization"))
					.setHeader("OPERATION",message.getHeaders().get("OPERATION"))
					.setHeader("host",message.getHeaders().get("host"))
					.setHeader("connection",message.getHeaders().get("connection"))
					.setHeader("accept-encoding",message.getHeaders().get("accept-encoding"))
					.setHeader("cache-control",message.getHeaders().get("cache-control"))
					.setHeaderIfAbsent(StandardLinkConstants.Headers.SYSTEM_TOKEN, message.getHeaders().get(Constants.SYSTEM_TOKEN))
					.build();
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
	@Async("channelUserBalanceEnquiryResTaskExecutor")
	public Object transformOutboundResponse(Object outgoingResponse) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("ChannelUserBalanceEnquiryTransformer :: transformOutboundResponse");

		try
		{
			Map<String, Object> response = new LinkedHashMap<>();

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION).getFields();
			LinkedCaseInsensitiveMap<String> constants = providerConfigurations.getOperations().get(Constants.OPERATION).getConstants();

			if (outgoingResponse instanceof CompletableFuture)
			{
				outgoingResponse = ((CompletableFuture<?>) outgoingResponse).get();
			}

			if (outgoingResponse instanceof Exception)
			{
				Exception exception = (Exception) outgoingResponse;
				response.put(Constants.Gp.GP_MESSAGE, StandardLinkUtilities.getRootCause(exception));
				response.put(Constants.Gp.GP_TXN_STATUS, StandardLinkResultCodes.INTERNAL_COMPONENT_ERROR);
				response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));
				return response;
			}

			Map<String, Object> internalResponse = (LinkedHashMap<String, Object>) outgoingResponse;
			Map<String, Object> request = (Map<String, Object>) internalResponse.get(Constants.Gp.REQUEST_PAYLOAD);
			Map<String, Object> requestPayloadCommand = (Map<String, Object>) request.get(Constants.Gp.COMMAND);
			response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));
			//response.put(Constants.Gp.GP_EXT_REQUEST_TYPE, requestPayloadCommand.get(Constants.Gp.GP_EXT_REQUEST_TYPE));
                        response.put(Constants.Gp.GP_EXT_REQUEST_TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));
			if (!internalResponse.isEmpty() && internalResponse.containsKey(StandardLinkConstants.Fields.RESULT_CODE))
			{
				Integer resultCodeValue = (Integer) internalResponse.get(StandardLinkConstants.Fields.RESULT_CODE);
				String defaultMessage = String.valueOf(internalResponse.get(StandardLinkConstants.Fields.RESULT_MESSAGE) != null ? internalResponse.get(StandardLinkConstants.Fields.RESULT_MESSAGE) : internalResponse.get(StandardLinkConstants.Fields.MESSAGE) != null ? internalResponse.get(StandardLinkConstants.Fields.MESSAGE) : internalResponse.get(StandardLinkConstants.Fields.RESULT_DESCRIPTION));

				if (resultCodeValue == StandardLinkResultCodes.SUCCESS)
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					internalResponse.put(Constants.Gp.TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);
					internalResponse.put(Constants.Gp.DATE, simpleDateFormat.format(new Date()));
					Map<String, Object> resellerInfo = (Map<String, Object>) internalResponse.get(Constants.Txe.RESELLER_INFO);
					Map<String, Object> resellerData = (Map<String, Object>) resellerInfo.get(Constants.Txe.RESELLER_DATA);
					List<Map<String, Object>> accounts = (List<Map<String, Object>>) resellerData.get(Constants.Txe.ACCOUNTS);
					Map<String, Object> account = accounts.get(0);
					Map<String, Object> balance = (Map<String, Object>) account.get(Constants.Txe.BALANCE);

					Map<String, Object> record = new LinkedHashMap<>();

					response.put(Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
					response.put(Constants.Gp.EXT_REF_NUM, requestPayloadCommand.get(Constants.Gp.EXT_REF_NUM));
					record.put(Constants.Gp.BALANCE, fetchResellersBalance(internalResponse));
					record.put(Constants.Gp.PRODUCT_CODE, constants.get(Constants.PRODUCT_CODE));
					record.put(Constants.Gp.PRODUCT_SHORT_NAME, constants.get(Constants.PRODUCT_SHORT_NAME));
					response.put(Constants.Gp.RECORD, record);
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
		private static final String PRODUCT_CODE = "productCode";
		private static final String PRODUCT_SHORT_NAME = "productShortName";
		private static final String PROVIDER = "gp";
		private static final String OPERATION = "CHANNELUSERBALANCEENQUIRY";
		private static final String SYSTEM_TOKEN = "system-token";

		private static final class Gp
		{
			private static final String DATE = "Date";
			private static final String TYPE = "TYPE";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String MSISDN = "MSISDN";
			private static final String PRODUCT_CODE = "PRODUCTCODE";
			private static final String PRODUCT_SHORT_NAME = "PRODUCTSHORTNAME";
			private static final String EXT_REF_NUM = "EXTREFNUM";
			private static final String GP_TXN_STATUS = "TXNSTATUS";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String GP_EXT_REQUEST_TYPE = "TYPE";
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String DATE_FORMAT = "dd/MM/YYYY hh:mm:ss";
			private static final String COMMAND = "COMMAND";
			private static final String BALANCE = "BALANCE";
			private static final String RECORD = "RECORD";
			private static final String REQUEST_PAYLOAD = "requestPayload";
			private static final String LOGINID = "LOGINID";
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
			private static final String RESELLER_INFO = "resellerInfo";
			private static final String RESELLER_DATA = "resellerData";
			private static final String ACCOUNTS = "accounts";
			private static final String RESELLERID = "RESELLERID";
		}
	}

}
