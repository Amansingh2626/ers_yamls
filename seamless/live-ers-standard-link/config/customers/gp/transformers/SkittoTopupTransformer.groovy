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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seamless.ers.interfaces.ersifcommon.dto.ERSHashtableParameter;
import com.seamless.ers.interfaces.ersifextlink.dto.AccountTransactionResponse;
import com.seamless.ers.standardlink.config.StandardLinkConfig;
import com.seamless.ers.standardlink.model.common.ERSIOTransformer;
import com.seamless.ers.standardlink.model.common.ResultCode;
import com.seamless.ers.standardlink.model.common.constants.StandardLinkConstants;
import com.seamless.ers.standardlink.model.configs.ProviderConfigurations;
import com.seamless.ers.standardlink.model.customer.gp.topup.request.Source;
import com.seamless.ers.standardlink.model.customer.gp.topup.request.Subscriber;
import com.seamless.ers.standardlink.model.customer.gp.topup.request.TopupRequest;
import com.seamless.ers.standardlink.model.customer.gp.topup.response.TopupResponse;
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
public class SkittoTopupTransformer implements ERSIOTransformer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(SkittoTopupTransformer.class);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Override
	public Message<?> transformOutboundSoapRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("SkittoTopupTransformer :: transformOutboundSoapRequest");
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

			LinkedCaseInsensitiveMap<Object> extraFields = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.EXTRA_FIELDS);
			LinkedCaseInsensitiveMap<Object> amountMap = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.AMOUNT);
			LinkedCaseInsensitiveMap<Object> accountMap = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.ACCOUNT);

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();
			LinkedCaseInsensitiveMap<String> constants = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getConstants();

			//BigDecimal amount = new BigDecimal((Double) amountMap.get(Constants.Gp.VALUE));
                        BigDecimal amount = new BigDecimal(((Double) amountMap.get(Constants.Gp.VALUE)) * 100);
			String amountUnitRelation = operationFields.get(Constants.Gp.AMOUNT_UNIT_RELATION).toString();
			String msisdn = extraFields.get(Constants.Gp.RECEIVER_MSISDN).toString();
			//String distributorId = extraFields.get(Constants.Gp.SENDER_ACCOUNT_ID).toString();
			String distributorId = operationFields.get(Constants.Gp.SKITTO_DISTRIBUTOR_ID).toString();
			//String distributorId = "Pretups";
			//String subDistributorId = extraFields.get(Constants.Gp.SENDER_ACCOUNT_ID).toString();
			//String posId = extraFields.get(Constants.Gp.SENDER_ACCOUNT_ID).toString();
			//String referenceId = requestFields.get(Constants.Txe.ERSREFERENCE).toString();

			LOGGER.info("========================Extra Fileds ======================");
			LOGGER.info(extraFields.toString());
			String transactionId = extraFields.get(Constants.Txe.TRANSACTION_ID).toString();
			//String transactionId = "123456";

			TopupRequest topupRequest = new TopupRequest();
			Source source = new Source();
			Subscriber subscriber = new Subscriber();

                        //topupRequest.setAmount(String.valueOf((amount.doubleValue() * 100)));
                        topupRequest.setAmount(String.valueOf(amount.intValue()));
			topupRequest.setAmountUnitRelation(amountUnitRelation);

			String msisdnStripPrefix = (String) operationFields.get("msisdn_strip_prefix");
			if (!msisdnStripPrefix.isEmpty())
			{
				if (msisdn.startsWith(msisdnStripPrefix))
				{
						msisdn = msisdn.substring(msisdnStripPrefix.length());
				}
			}
			String msisdnAddPrefix = (String) operationFields.get("msisdn_add_prefix");
			if (!msisdnAddPrefix.isEmpty())
			{
				msisdn = msisdnAddPrefix + msisdn;
			}
			LOGGER.info("msisdn after formatting " + msisdn);
			subscriber.setMsisdn(msisdn);
			subscriber.setImsi(null);
			subscriber.setId(null);
			topupRequest.setSubscriber(subscriber);

			source.setDistributorId(distributorId);
			source.setSubdistributorId(null);
			source.setPosId(null);
			topupRequest.setSource(source);

			topupRequest.setVoucherId(null);
			topupRequest.setVoucherSeries(null);
			topupRequest.setReferenceId(null);
			topupRequest.setTransactionId(transactionId);

			return MessageBuilder.withPayload(topupRequest)
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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("SkittoTopupTransformer :: transformInboundSoapResponse");
		Map<String, Object> response = new LinkedHashMap<>();
		try
		{
			AccountTransactionResponse accountTransactionResponse = new AccountTransactionResponse();
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();

			if (incomingResponse instanceof CompletableFuture)
			{
				incomingResponse = ((CompletableFuture<?>) incomingResponse).get();
			}

			if (incomingResponse instanceof Exception)
			{
				Exception exception = (Exception) incomingResponse;
				ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(StandardLinkResultCodes.FAILED_ON_SERVER), String.valueOf(StandardLinkResultCodes.FAILED_ON_SERVER), StandardLinkUtilities.getRootCause(exception));
				accountTransactionResponse.setResultDescription(resultCode.getDescription());
				accountTransactionResponse.setResultCode(Integer.parseInt(resultCode.getInternalResultCode()));
				populateChargingSystemIp(operationFields, accountTransactionResponse);
				return accountTransactionResponse;
			}

			if (incomingResponse.toString().contains("Read timed out"))
			{
				Map<String, Object> topupResponse = (LinkedHashMap<String, Object>) incomingResponse;
				Integer resultCodeValue = (Integer) topupResponse.get(Constants.Gp.RESULT_CODE);
				String defaultMessage = String.valueOf(topupResponse.get(Constants.Gp.MESSAGE));
				ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
                                accountTransactionResponse.setResultCode(0);
				accountTransactionResponse.setResultDescription(resultCode.getDescription());
				accountTransactionResponse.setField(Constants.Txe.NATIVE_RESULT_CODE, operationFields.get("resultCode").toString());
				accountTransactionResponse.setField(Constants.Txe.NATIVE_RESULT_DESCRIPTION, operationFields.get("resultDescription").toString());
				accountTransactionResponse.setField(Constants.Txe.LINK_TYPE, (String) operationFields.get(Constants.Gp.LINK_TYPE));
				accountTransactionResponse.setField(Constants.Txe.LINK_NODE_ID, (String) operationFields.get(Constants.Gp.LINK_NODE_ID));
				populateChargingSystemIp(operationFields, accountTransactionResponse);
				return accountTransactionResponse;
			}
			else
			{
				Map<String, Object> topUpResponse = (LinkedHashMap<String, Object>) incomingResponse;
				if (topUpResponse.containsKey("body"))
				{
					Map<String, Object> body = (LinkedHashMap<String, Object>) topUpResponse.get("body");
					topUpResponse = (LinkedHashMap<String, Object>) body.get("topupResponse");
				}

				Map<String, Object> source = (LinkedHashMap<String, Object>) topUpResponse.get("source");
				Map<String, Object> subscriber = (LinkedHashMap<String, Object>) topUpResponse.get("subscriber");
				Map<String, Object> rechargedAccount = (LinkedHashMap<String, Object>) topUpResponse.get("rechargedAccount");
				String resultCodeValue = (String) topUpResponse.get(Constants.Txe.RESPONSE_CODE);
				String defaultMessage = String.valueOf(topUpResponse.get(Constants.Txe.RESULT_MESSAGE) != null ? topUpResponse.get(Constants.Txe.RESULT_MESSAGE) : topUpResponse.get(Constants.Txe.MESSAGE) != null ? topUpResponse.get(Constants.Txe.MESSAGE) : topUpResponse.get(Constants.Txe.RESULT_DESCRIPTION) != null ? topUpResponse.get(Constants.Txe.RESULT_DESCRIPTION) : topUpResponse.get(Constants.Txe.RESPONSE_DESCRIPTION));
				ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
				accountTransactionResponse.setResultCode(Integer.parseInt(resultCode.getInternalResultCode()));
				accountTransactionResponse.setResultDescription(resultCode.getDescription());
				accountTransactionResponse.setField(Constants.Txe.EXTERNAL_RESULT_CODE, topUpResponse.get(Constants.Gp.RESPONSE_CODE).toString());
				accountTransactionResponse.setField(Constants.Txe.EXTERNAL_RESULT_DESCRIPTION, topUpResponse.get(Constants.Gp.RESPONSE_DETAILS).toString());
				accountTransactionResponse.setField(Constants.Txe.TRANSACTION_ID, (String) topUpResponse.get(Constants.Gp.TRANSACTION_ID));
				accountTransactionResponse.setField(Constants.Txe.RESELLER_ID, source.get(Constants.Gp.DISTRIBUTOR_ID).toString());
				accountTransactionResponse.setField(Constants.Txe.MSISDN, subscriber.get(Constants.Gp.MSISDN).toString());
				accountTransactionResponse.setField(Constants.Txe.SKITTO_AMOUNT, rechargedAccount.get(Constants.Gp.AMOUNT).toString());
				accountTransactionResponse.setField(Constants.Txe.SKITTO_CURRENCY_ID, rechargedAccount.get(Constants.Gp.CURRENCY_ID).toString());
				accountTransactionResponse.setField(Constants.Txe.SKITTO_CURRENCY_NAME, rechargedAccount.get(Constants.Gp.CURRENCY_NAME).toString());
				accountTransactionResponse.setField(Constants.Txe.SKITTO_UNIT_ID, rechargedAccount.get(Constants.Gp.UNIT_ID).toString());
				accountTransactionResponse.setField(Constants.Txe.SKITTO_UNIT_NAME, rechargedAccount.get(Constants.Gp.UNIT_NAME).toString());
				accountTransactionResponse.setField(Constants.Txe.SKITTO_UNIT_RELATION, rechargedAccount.get(Constants.Gp.UNIT_RELATION).toString());
				accountTransactionResponse.setField(Constants.Txe.SKITTO_EXPIRY_DATE, String.valueOf(rechargedAccount.get(Constants.Gp.EXPIRY_DATE)));
				accountTransactionResponse.setField(Constants.Txe.SKITTO_NEW_BALANCE, rechargedAccount.get(Constants.Gp.NEW_BALANCE).toString());
				accountTransactionResponse.setField(Constants.Txe.NATIVE_RESULT_CODE, resultCode.getInternalResultCode());
				accountTransactionResponse.setField(Constants.Txe.NATIVE_RESULT_DESCRIPTION, resultCode.getDescription());
				accountTransactionResponse.setField(Constants.Txe.LINK_TYPE, (String) operationFields.get(Constants.Gp.LINK_TYPE));
				accountTransactionResponse.setField(Constants.Txe.LINK_NODE_ID, (String) operationFields.get(Constants.Gp.LINK_NODE_ID));
				populateChargingSystemIp(operationFields, accountTransactionResponse);
				return accountTransactionResponse;
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
			fields.put("info7", url.getHost());
			accountTransactionResponse.setFields(fields);
		}
	}

	private static final class Constants
	{
		private static final class Gp
		{
			private static final String PROVIDER = "gp";
			private static final String OPERATION = "skittoTopup";
			private static final String REQUEST_PAYLOAD = "requestPayload";
			private static final String VALUE = "value";
			private static final String AMOUNT_UNIT_RELATION = "amountUnitRelation";
			private static final String RECEIVER_MSISDN = "RECEIVER_MSISDN";
			private static final String SENDER_ACCOUNT_ID = "SENDER_ACCOUNT_ID";
			private static final String REFERENCE = "reference";
			private static final String EXTRA_FIELDS = "extraFields";
			private static final String AMOUNT = "amount";
			private static final String ACCOUNT = "account";
			private static final String MESSAGE = "message";
			private static final String RESULT_CODE = "resultCode";
			private static final String DISTRIBUTOR_ID = "distributorId";
			private static final String RESPONSE_CODE = "responseCode";
			private static final String RESPONSE_DETAILS = "responseDetail";
			private static final String MSISDN = "msisdn";
			private static final String CURRENCY_ID = "currencyId";
			private static final String CURRENCY_NAME = "currencyName";
			private static final String UNIT_ID = "unitId";
			private static final String UNIT_NAME = "unitName";
			private static final String UNIT_RELATION = "unitRelation";
			private static final String EXPIRY_DATE = "expiryDate";
			private static final String NEW_BALANCE = "newBalance";
			private static final String TRANSACTION_ID = "transactionId";
			private static final String LINK_TYPE = "linkType";
			private static final String LINK_NODE_ID = "linkNodeId";
			private static final String SKITTO_DISTRIBUTOR_ID = "distributorId";
		}

		private static final class Txe
		{
			private static final String EXTERNAL_RESULT_CODE = "externalResultCode";
			private static final String EXTERNAL_RESULT_DESCRIPTION = "externalResultDescription";
			private static final String NATIVE_RESULT_CODE = "NativeResultCode";
			private static final String NATIVE_RESULT_DESCRIPTION = "NativeResultDescription";
			private static final String LINK_TYPE = "LinkType";
			private static final String LINK_NODE_ID = "LinkNodeId";
			private static final String RESULT_CODE = "resultCode";
			private static final String RESPONSE_CODE = "responseCode";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String MESSAGE = "message";
			private static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String RESPONSE_DESCRIPTION = "responseDescription";
			private static final String TRANSACTION_ID = "transactionId";
			private static final String RESELLER_ID = "resellerId";
			private static final String ERSREFERENCE = "ersReference";
			private static final String MSISDN = "skitto_amount";
			private static final String SKITTO_AMOUNT = "skitto_amount";
			private static final String SKITTO_CURRENCY_ID = "skitto_currencyId";
			private static final String SKITTO_CURRENCY_NAME = "skitto_currencyName";
			private static final String SKITTO_UNIT_ID = "skitto_unitId";
			private static final String SKITTO_UNIT_NAME = "skitto_unitName";
			private static final String SKITTO_UNIT_RELATION = "skitto_unitRelation";
			private static final String SKITTO_EXPIRY_DATE = "skitto_expiryDate";
			private static final String SKITTO_NEW_BALANCE = "skitto_newBalance";
		}
	}
}

