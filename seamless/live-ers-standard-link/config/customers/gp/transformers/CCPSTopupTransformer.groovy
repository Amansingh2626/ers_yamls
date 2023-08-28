/*
 *   Created By   : Bharath SaiSwetha
 *   Email        : saiswetha.bharath@seamless.se
 *   Created Date : 12/02/2022
 *   Time         : 01:10 PM
 *
 *   Copyright(c) 2021. Seamless Distribution Systems AB - All Rights Reserved
 *   Unauthorized copying of this file, via any medium is strictly prohibited. It is proprietary and confidential.
 *
 */

package com.seamless.ers.standardlink.transformers;

import com.seamless.ers.interfaces.ersifcommon.dto.ERSHashtableParameter;
import com.seamless.ers.interfaces.ersifextlink.dto.AccountTransactionResponse;
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
import org.springframework.http.HttpHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.seamless.ers.standardlink.model.internal.StandardLinkResultCodes.SUCCESS;

@Component("cCPSTopupTransformer")
public class CCPSTopupTransformer implements ERSIOTransformer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.class);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Override
	public Message<?> transformOutboundRestRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("CCPSTopupTransformer :: transformOutboundSoapRequest");
		try
		{
			LinkedCaseInsensitiveMap<Object> requestFields;
			if (message.getPayload() instanceof LinkedHashMap)
			{
				requestFields = StandardLinkUtilities.convertToCaseInsensitiveMap((Map<String, Object>) message.getPayload());
			}
			else
			{
				requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			}

			LinkedCaseInsensitiveMap<Object> extraFields = (LinkedCaseInsensitiveMap<Object>) requestFields.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.EXTRA_FIELDS);
			LinkedCaseInsensitiveMap<Object> amountMap = (LinkedCaseInsensitiveMap<Object>) requestFields.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.AMOUNT);
			LinkedCaseInsensitiveMap<Object> accountMap = (LinkedCaseInsensitiveMap<Object>) requestFields.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.ACCOUNT);

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.OPERATION).getFields();

			Map<String, Object> topupRequest = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> accountDetail = Collections.synchronizedMap(new LinkedHashMap<>());

			BigDecimal amountVal = new BigDecimal((Double) amountMap.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.VALUE));
			String amount =String.valueOf(amountVal.doubleValue() * 100);
			String msisdn = extraFields.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.RECEIVER_MSISDN).toString();
			List accountDetails = Collections.synchronizedList(new ArrayList<>());

			accountDetail.put(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.AMOUNT, amount);
			accountDetail.put(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.MSISDN, msisdn.substring(3));

			String bankName = operationFields.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.BANK_NAME).toString();
			SimpleDateFormat formatter = new SimpleDateFormat(operationFields.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.CCPS_TOPUP_DATE_FORMAT).toString());
			String paymentDate = formatter.format(new Date());
			String paymentType = operationFields.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.PAYMENT_TYPE).toString();
			String remarks = operationFields.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.REMARKS).toString();
			String sourceSystem = operationFields.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.SOURCE_SYSTEM).toString();
			String sourceTxnId = requestFields.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.ERSREFERENCE).toString();
			accountDetails.add(accountDetail);
			topupRequest.put(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.ACCOUNT_DETAILS, accountDetails);
			topupRequest.put(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.BANK_NAME, bankName);
			topupRequest.put(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.PAYMENT_DATE, paymentDate);
			topupRequest.put(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.PAYMENT_TYPE, paymentType);
			topupRequest.put(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.SOURCE_SYSTEM, sourceSystem);
			topupRequest.put(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.REMARKS, remarks);
			topupRequest.put(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.SOURCE_TXN_ID, sourceTxnId);

			return MessageBuilder.withPayload(topupRequest)
					.copyHeaders(message.getHeaders())
					.setHeader(HttpHeaders.CONTENT_TYPE, operationFields.get(StandardLinkConstants.Headers.CONTENT_TYPE))
					.setHeaderIfAbsent(StandardLinkConstants.Headers.VALIDATION, StandardLinkConstants.Headers.PASSED)
					.setHeaderIfAbsent(StandardLinkConstants.Fields.CHANNEL, operationFields.get(StandardLinkConstants.Fields.CHANNEL))
					.setHeaderIfAbsent(HttpHeaders.AUTHORIZATION, getBasicToken()).build();
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
	public Object transformInboundRestResponse(Object incomingResponse) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("CCPSTopupTransformer :: transformInboundSoapResponse");
		try
		{

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.OPERATION).getFields();

			if (incomingResponse instanceof CompletableFuture)
			{
				incomingResponse = ((CompletableFuture<?>) incomingResponse).get();
			}

			if (incomingResponse instanceof Exception)
			{
				Exception exception = (Exception) incomingResponse;
				ResultCode resultCode = standardLinkConfig.getResultCodeFor(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.PROVIDER, com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.OPERATION, String.valueOf(StandardLinkResultCodes.FAILED_ON_SERVER), String.valueOf(StandardLinkResultCodes.FAILED_ON_SERVER), String.valueOf(StandardLinkUtilities.getRootCause(exception)));
				accountTransactionResponse.setResultDescription(resultCode.getDescription());
				accountTransactionResponse.setResultCode(Integer.parseInt(resultCode.getInternalResultCode()));
			}
			Map<String, Object> topupResponse=null;
			if(incomingResponse != null){
				topupResponse=(LinkedHashMap<String, Object>) incomingResponse;
			}
			Map<String, Object> externalResponse = (LinkedHashMap<String, Object>) incomingResponse;
			if (incomingResponse != null) {
				LOGGER.debug("Contents of incomingResponse: " + incomingResponse.toString() + incomingResponse.getClass());
				if (topupResponse.containsKey("resultCode") && Integer.parseInt(topupResponse.get("resultCode").toString()) == StandardLinkResultCodes.INACCESSIBLE_SERVER_URL) {
			        topupResponse = (LinkedHashMap<String, Object>) incomingResponse;
					Integer resultCodeValue = (Integer) topupResponse.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.RESULT_CODE);
					String defaultMessage = String.valueOf(topupResponse.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.MESSAGE));
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.PROVIDER, com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					accountTransactionResponse.setResultCode(Integer.parseInt(resultCode.getInternalResultCode()));
					accountTransactionResponse.setResultDescription(resultCode.getDescription());
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.NATIVE_RESULT_CODE, operationFields.get("resultCode").toString());
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.NATIVE_RESULT_DESCRIPTION, operationFields.get("resultDescription").toString());
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.LINK_TYPE, (String) operationFields.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.LINK_TYPE));
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.LINK_NODE_ID, (String) operationFields.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.LINK_NODE_ID));
					populateChargingSystemIp(operationFields);
					return accountTransactionResponse;
				} else if (!externalResponse.isEmpty() && externalResponse.containsKey(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.STATUS) && String.valueOf(externalResponse.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.STATUS)).equals(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.SUCCESS)) {
					Integer resultCodeValue = SUCCESS;
					String defaultMessage = "SUCCESS";
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.PROVIDER, com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					accountTransactionResponse.setResultCode(Integer.parseInt(resultCode.getInternalResultCode()));
					accountTransactionResponse.setResultDescription(resultCode.getDescription());
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.STATUS, String.valueOf(externalResponse.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.STATUS)));
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.ID, String.valueOf(externalResponse.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.ID)));
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.NATIVE_RESULT_CODE, resultCode.getInternalResultCode());
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.NATIVE_RESULT_DESCRIPTION, resultCode.getDescription());
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.LINK_TYPE, (String) operationFields.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.LINK_TYPE));
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.LINK_NODE_ID, (String) operationFields.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.LINK_NODE_ID));
					populateChargingSystemIp(operationFields);
					return accountTransactionResponse;
				} else if (!externalResponse.isEmpty() && externalResponse.containsKey(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.ERROR_CODE) && String.valueOf(externalResponse.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.ERROR_CODE)) == String.valueOf(StandardLinkResultCodes.SUCCESS)) {
					Integer resultCodeValue = (Integer) externalResponse.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.ERROR_CODE);
					String defaultMessage = String.valueOf(externalResponse.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.ERROR_DESCRIPTION) != null ? externalResponse.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.ERROR_DESCRIPTION) : externalResponse.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.ERROR_DESCRIPTION) != null ? externalResponse.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.ERROR_DESCRIPTION) : externalResponse.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.ERROR_DESCRIPTION));
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.PROVIDER, com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					accountTransactionResponse.setResultCode(Integer.parseInt(resultCode.getInternalResultCode()));
					accountTransactionResponse.setResultDescription(resultCode.getDescription());
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.NATIVE_RESULT_CODE, resultCode.getInternalResultCode());
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.NATIVE_RESULT_DESCRIPTION, resultCode.getDescription());
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.LINK_TYPE, (String) operationFields.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.LINK_TYPE));
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.LINK_NODE_ID, (String) operationFields.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.LINK_NODE_ID));
					populateChargingSystemIp(operationFields);
					return accountTransactionResponse;
				} else {
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.PROVIDER, com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.OPERATION, String.valueOf(StandardLinkResultCodes.INACCESSIBLE_SERVER_URL), String.valueOf(StandardLinkResultCodes.INACCESSIBLE_SERVER_URL), String.valueOf(StandardLinkResultCodes.INACCESSIBLE_SERVER_URL));
					accountTransactionResponse.setResultDescription(resultCode.getDescription());
					accountTransactionResponse.setResultCode(Integer.parseInt(resultCode.getInternalResultCode()));
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.NATIVE_RESULT_CODE, resultCode.getInternalResultCode());
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.NATIVE_RESULT_DESCRIPTION, resultCode.getDescription());
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.LINK_TYPE, (String) operationFields.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.LINK_TYPE));
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Txe.LINK_NODE_ID, (String) operationFields.get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.Gp.LINK_NODE_ID));
					populateChargingSystemIp(operationFields);
					return accountTransactionResponse;
				}
			}
			populateChargingSystemIp(operationFields);
			return accountTransactionResponse;
		}
		catch (Exception e)
		{
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
	private void populateChargingSystemIp(LinkedCaseInsensitiveMap<Object> operationFields) throws MalformedURLException {
		Object fetchChargingSystemIp = operationFields.get("fetchChargingSystemIp");
		Object server_url = operationFields.get("server_url");
		LOGGER.debug("Fetch charging system Ip : " + fetchChargingSystemIp);
		LOGGER.debug("Charging system url for CCPS : " + server_url);
		if (Objects.nonNull(fetchChargingSystemIp) && Boolean.valueOf(String.valueOf(fetchChargingSystemIp))){
			ERSHashtableParameter fields = accountTransactionResponse.getFields();
			URL url = new URL(server_url.toString());
			LOGGER.debug("Charging system Ip is : " + url.getHost());
			fields.put("info7" , url.getHost());
			accountTransactionResponse.setFields(fields);
		}
	}

	AccountTransactionResponse accountTransactionResponse = new AccountTransactionResponse();

	private static final class Constants
	{
		private static final String PROVIDER = "gp";
		private static final String OPERATION = "ccpsTopup";

		private static final class Gp
		{
			public static final String RESULT_CODE = "resultCode";
			public static final String MESSAGE = "message";
			public static final String LINK_TYPE = "linkType";
			public static final String LINK_NODE_ID = "linkNodeId";
			private static final String REFERENCE = "reference";
			private static final String REQUEST_PAYLOAD = "requestPayload";
			private static final String ACCOUNT_DETAILS = "accountDetails";
			private static final String AMOUNT = "amount";
			private static final String MSISDN = "msisdn";
			private static final String BANK_NAME = "bankName";
			private static final String PAYMENT_DATE = "paymentDate";
			private static final String PAYMENT_TYPE = "paymentType";
			private static final String EXTRA_FIELDS = "extraFields";
			private static final String REMARKS = "remarks";
			private static final String SOURCE_SYSTEM = "sourceSystem";
			private static final String SOURCE_TXN_ID = "sourceTxnId";
			private static final String ERROR_CODE = "errorCode";
			private static final String STATUS = "status";
			private static final String ERROR_DESC = "errorDesc";
			private static final String VALUE = "value";
			private static final String RECEIVER_MSISDN = "RECEIVER_MSISDN";
			private static final String ACCOUNT = "account";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String SUCCESS = "SUCCESS";
			private static final String CCPS_TOPUP_DATE_FORMAT = "ccpsTopupDateFormat";

		}

		private static final class Txe
		{
			public static final String NATIVE_RESULT_CODE = "NativeResultCode";
			public static final String NATIVE_RESULT_DESCRIPTION = "NativeResultDescription";
			public static final String LINK_TYPE = "LinkType";
			public static final String LINK_NODE_ID = "LinkNodeId";
			private static final String TYPE = "type";
			private static final String ERROR_CODE = "errorCode";
			private static final String ERROR_DESCRIPTION = "errorDesc";
			private static final String ID = "id";
			private static final String ERSREFERENCE = "ersreference";
			private static final String STATUS = "status";
			private static final String EXTERNAL_RESULT_CODE = "externalResultCode";
			private static final String EXTERNAL_RESULT_DESCRIPTION = "externalResultDescription";
		}
	}
	private String getBasicToken() {

		ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.PROVIDER);
		Map<String, Object> operationFields = providerConfigurations.getOperations().get(com.seamless.ers.standardlink.transformers.CCPSTopupTransformer.Constants.OPERATION).getFields();

		String str = (operationFields.get("username") == null ? "" : operationFields.get("username")) + ":" + (operationFields.get("password") == null ? "" : operationFields.get("password"));
		return "Basic " + Base64Utils.encodeToString(str.getBytes(StandardCharsets.UTF_8));
	}
}

