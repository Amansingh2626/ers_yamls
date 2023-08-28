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
import com.seamless.ers.standardlink.model.customer.gp.validateTopup.request.InitTopupRequest;
import com.seamless.ers.standardlink.model.customer.gp.validateTopup.request.Source;
import com.seamless.ers.standardlink.model.customer.gp.validateTopup.request.Subscriber;
import com.seamless.ers.standardlink.model.customer.gp.validateTopup.response.InitTopupResponse;
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
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Component
public class SkittoValidateTopupTransformer implements ERSIOTransformer
{

	private static final Logger LOGGER = LoggerFactory.getLogger(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.class);

	@Autowired
	private StandardLinkConfig standardLinkConfig;
	private static final String SKITTO_DISTRIBUTOR_ID = "distributorId";

	@Override
	public Message<?> transformOutboundSoapRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("SkittoValidateTopupTransformer :: transformOutboundSoapRequest");
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
			LinkedCaseInsensitiveMap<Object> extraFields = (LinkedCaseInsensitiveMap<Object>) requestFields.get(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.EXTRA_FIELDS);
			LinkedCaseInsensitiveMap<Object> amountMap = (LinkedCaseInsensitiveMap<Object>) requestFields.get(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.AMOUNT);
			LinkedCaseInsensitiveMap<Object> senderAccountMap = (LinkedCaseInsensitiveMap<Object>) requestFields.get(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.SENDER_ACCOUNT);
			LinkedCaseInsensitiveMap<Object> receiverAccountMap = (LinkedCaseInsensitiveMap<Object>) requestFields.get(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.RECEIVER_ACCOUNT);

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.OPERATION).getFields();
			LinkedCaseInsensitiveMap<String> constants = providerConfigurations.getOperations().get(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.OPERATION).getConstants();

			BigDecimal amount = new BigDecimal((Double) amountMap.get(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.VALUE));
			String amountUnitRelation = operationFields.get(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.AMOUNT_UNIT_RELATION).toString();
			String msisdn = receiverAccountMap.get(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.ACCOUNT_ID).toString();
			String distributorId = operationFields.get(SKITTO_DISTRIBUTOR_ID).toString();

			InitTopupRequest initTopupRequest = new InitTopupRequest();
			Source source = new Source();
			Subscriber subscriber = new Subscriber();

			initTopupRequest.setAmount(String.valueOf(amount.intValue() * 100));
			initTopupRequest.setAmountUnitRelation(amountUnitRelation);

			subscriber.setMsisdn(msisdn);
			subscriber.setImsi(null);
			subscriber.setId(null);
			initTopupRequest.setSubscriber(subscriber);

			source.setDistributorId(distributorId);
			source.setSubdistributorId(null);
			source.setPosId(null);
			initTopupRequest.setSource(source);

			initTopupRequest.setVoucherId(null);
			initTopupRequest.setVoucherSeries(null);
			initTopupRequest.setReferenceId(null);

			return MessageBuilder.withPayload(initTopupRequest)
					.copyHeaders(message.getHeaders())
					.setHeader(HttpHeaders.CONTENT_TYPE, operationFields.get(StandardLinkConstants.Headers.CONTENT_TYPE))
					.setHeaderIfAbsent(StandardLinkConstants.Headers.VALIDATION, StandardLinkConstants.Headers.PASSED)
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
	public Object transformInboundSoapResponse(Object incomingResponse) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("SkittoValidateTopupTransformer :: transformInboundSoapResponse");
		Map<String, Object> response = new LinkedHashMap<>();
		try
		{
			AccountTransactionResponse accountTransactionResponse = new AccountTransactionResponse();
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.OPERATION).getFields();

			if (incomingResponse instanceof CompletableFuture)
			{
				incomingResponse = ((CompletableFuture<?>) incomingResponse).get();
			}
			if (incomingResponse instanceof Exception)
			{
				Exception exception = (Exception) incomingResponse;
				ResultCode resultCode = standardLinkConfig.getResultCodeFor(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.PROVIDER, com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.OPERATION, String.valueOf(StandardLinkResultCodes.FAILED_ON_SERVER), String.valueOf(StandardLinkResultCodes.FAILED_ON_SERVER), StandardLinkUtilities.getRootCause(exception));
				accountTransactionResponse.setResultDescription(resultCode.getDescription());
				accountTransactionResponse.setResultCode(Integer.parseInt(resultCode.getInternalResultCode()));
				populateChargingSystemIp(operationFields, accountTransactionResponse);
				return accountTransactionResponse;
			}
			else
			{
				Map<String, Object> externalResponse = (LinkedHashMap<String, Object>) incomingResponse;
				if (externalResponse.containsKey("body"))
				{
					Map<String, Object> body = (LinkedHashMap<String, Object>) externalResponse.get("body");
					externalResponse = (LinkedHashMap<String, Object>) body.get("initTopupResponse");
				}
				Map<String, Object> source = (LinkedHashMap<String, Object>) externalResponse.get("source");
				Map<String, Object> subscriber = (LinkedHashMap<String, Object>) externalResponse.get("subscriber");
				Integer resultCodeValue = (Integer) externalResponse.get("responseCode");

				if (resultCodeValue == StandardLinkResultCodes.SUCCESS)
				{
					String defaultMessage = externalResponse.get("responseDetail").toString();
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.PROVIDER, com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					accountTransactionResponse.setResultCode(Integer.parseInt(resultCode.getInternalResultCode()));
					accountTransactionResponse.setResultDescription(resultCode.getDescription());
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Txe.TRANSACTION_ID, externalResponse.get(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.TRANSACTION_ID).toString());
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Txe.TRANSACTION_DATE, externalResponse.get(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.TRANSACTION_DATE).toString());
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Txe.RESELLER_ID, source.get(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.DISTRIBUTOR_ID).toString());
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Txe.MSISDN, subscriber.get(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.MSISDN).toString());
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Txe.IMSI, subscriber.get(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.IMSI).toString());
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Txe.ID, subscriber.get(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.ID).toString());
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Txe.ERSREFERENCE, subscriber.get(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.MSISDN).toString());
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Txe.NATIVE_RESULT_CODE, resultCode.getInternalResultCode());
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Txe.NATIVE_RESULT_DESCRIPTION, resultCode.getDescription());
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Txe.LINK_TYPE, (String) operationFields.get(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.LINK_TYPE));
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Txe.LINK_NODE_ID, (String) operationFields.get(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.LINK_NODE_ID));
					populateChargingSystemIp(operationFields, accountTransactionResponse);
					return accountTransactionResponse;
				}
				else
				{
					resultCodeValue = (Integer) externalResponse.get(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Txe.RESULT_CODE);
					String defaultMessage = String.valueOf(externalResponse.get(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Txe.RESPONSE_DETAIL));
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.PROVIDER, com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					accountTransactionResponse.setResultCode(Integer.parseInt(resultCode.getInternalResultCode()));
					accountTransactionResponse.setResultDescription(resultCode.getDescription());
					accountTransactionResponse.setField(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Txe.TRANSACTION_DATE, externalResponse.get(com.seamless.ers.standardlink.transformers.SkittoValidateTopupTransformer.Constants.Gp.TRANSACTION_DATE).toString());
					populateChargingSystemIp(operationFields, accountTransactionResponse);
					return accountTransactionResponse;
				}
			}
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

	private void populateChargingSystemIp(LinkedCaseInsensitiveMap<Object> operationFields, AccountTransactionResponse accountTransactionResponse) throws MalformedURLException
	{
		Object fetchChargingSystemIp = operationFields.get("fetchChargingSystemIp");
		Object serverUrl = operationFields.get("server_url");
		LOGGER.debug("Fetch charging system Ip : " + fetchChargingSystemIp);
		LOGGER.debug("Charging system url for SKITTO : " + serverUrl);
		if (Objects.nonNull(fetchChargingSystemIp) && Boolean.valueOf(String.valueOf(fetchChargingSystemIp)))
		{
			ERSHashtableParameter fields = accountTransactionResponse.getFields();
			URL url = new URL(serverUrl.toString());
			LOGGER.debug("Charging system Ip is : " + url.getHost());
			fields.put("info5", url.getHost());
			accountTransactionResponse.setFields(fields);
		}
	}

	private static final class Constants
	{
		private static final class Gp
		{
			private static final String PROVIDER = "gp";
			private static final String OPERATION = "skittoValidateTopup";
			private static final String REQUEST_PAYLOAD = "requestPayload";
			private static final String VALUE = "value";
			private static final String AMOUNT_UNIT_RELATION = "amountUnitRelation";
			private static final String ACCOUNT_ID = "accountId";
			private static final String ERS_REFERENCE = "ersReference";
			private static final String EXTRA_FIELDS = "extraFields";
			private static final String AMOUNT = "amount";
			private static final String SENDER_ACCOUNT = "senderAccount";
			private static final String RECEIVER_ACCOUNT = "receiverAccount";
			private static final String IMSI = "imsi";
			private static final String ID = "id";
			private static final String TRANSACTION_DATE = "transactionDate";
			private static final String TRANSACTION_ID = "transactionId";
			private static final String DISTRIBUTOR_ID = "distributorId";
			private static final String MSISDN = "msisdn";
			private static final String LINK_TYPE = "linkType";
			private static final String LINK_NODE_ID = "linkNodeId";
		}

		private static final class Txe
		{
			private static final String RESULT_CODE = "resultCode";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String MESSAGE = "message";
			private static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String TRANSACTION_ID = "transactionId";
			private static final String RESELLER_ID = "resellerId";
			private static final String ERSREFERENCE = "ersreference";
			private static final String MSISDN = "msisdn";
			private static final String IMSI = "imsi";
			private static final String ID = "id";
			private static final String TRANSACTION_DATE = "transactionDate";
			private static final String NATIVE_RESULT_CODE = "NativeResultCode";
			private static final String NATIVE_RESULT_DESCRIPTION = "NativeResultDescription";
			private static final String LINK_TYPE = "LinkType";
			private static final String LINK_NODE_ID = "LinkNodeId";
			private static final String RESPONSE_DETAIL = "responseDetail";
		}
	}
}


