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

import com.seamless.ers.interfaces.ersifextlink.dto.AccountTransactionResponse;
import com.seamless.ers.standardlink.config.StandardLinkConfig;
import com.seamless.ers.standardlink.model.common.ERSIOTransformer;
import com.seamless.ers.standardlink.model.common.ResultCode;
import com.seamless.ers.standardlink.model.common.constants.StandardLinkConstants;
import com.seamless.ers.standardlink.model.configs.ProviderConfigurations;
import com.seamless.ers.standardlink.model.customer.gp.queryTopup.request.QueryTopupRequest;
import com.seamless.ers.standardlink.model.exception.TransformerException;
import com.seamless.ers.standardlink.model.internal.StandardLinkResultCodes;
import com.seamless.ers.standardlink.utilities.StandardLinkUtilities;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmPoint;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class SkittoQueryTopupTransformer implements ERSIOTransformer
{

	private static final Logger LOGGER = LoggerFactory.getLogger(SkittoQueryTopupTransformer.class);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Override
	public Message<?> transformOutboundSoapRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("SkittoQueryTopupTransformer :: transformOutboundSoapRequest");
		try
		{
			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LinkedCaseInsensitiveMap<Object> extraFields = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.EXTRA_FIELDS);

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();
			QueryTopupRequest queryTopupRequest = new QueryTopupRequest();
			LinkedCaseInsensitiveMap<Object> additionalFields = (LinkedCaseInsensitiveMap<Object>) requestFields.get("additionalFields");
			String referenceId = additionalFields.get(Constants.Txe.ERSREFERENCE).toString();
			queryTopupRequest.setTransactionId(referenceId);

LOGGER.info(" query Topup request "+queryTopupRequest);
LOGGER.info(" query Topup request "+referenceId);
			return MessageBuilder.withPayload(queryTopupRequest)
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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("SkittoQueryTopupTransformer :: transformInboundSoapResponse");
		try
		{
			AccountTransactionResponse accountTransactionResponse = new AccountTransactionResponse();
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
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
				return accountTransactionResponse;
			}
			else if (incomingResponse != null)
			{
				Map<String, Object> response = (LinkedHashMap<String, Object>) incomingResponse;
LOGGER.info(" response map "+response);
				accountTransactionResponse.setResultCode(Integer.parseInt(response.get("responseCode").toString()));
				accountTransactionResponse.setResultDescription(response.get("responseDetail").toString());

				return accountTransactionResponse;

			}
			Map<String, Object> externalResponse = (LinkedHashMap<String, Object>) incomingResponse;
			Integer resultCodeValue = (Integer) externalResponse.get(Constants.Txe.RESULT_CODE);
			String defaultMessage = String.valueOf(externalResponse.get(Constants.Txe.RESULT_MESSAGE) != null ? externalResponse.get(Constants.Txe.RESULT_MESSAGE) : externalResponse.get(Constants.Txe.MESSAGE) != null ? externalResponse.get(Constants.Txe.MESSAGE) : externalResponse.get(Constants.Txe.RESULT_DESCRIPTION));
			ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
			accountTransactionResponse.setResultCode(Integer.parseInt(resultCode.getInternalResultCode()));
			accountTransactionResponse.setResultDescription(resultCode.getDescription());
			return accountTransactionResponse;
		}
		catch (Exception e)
		{
			throw new TransformerException(StandardLinkResultCodes.TRANSFORMATION_ERROR, "An error occurred during response transformation : " + ExceptionUtils.getFullStackTrace(e));
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
			private static final String PROVIDER = "gp";
			private static final String OPERATION = "skittoQueryTopup";
			private static final String REQUEST_PAYLOAD = "requestPayload";
			private static final String VALUE = "value";
			private static final String AMOUNT_UNIT_RELATION = "amountUnitRelation";
			private static final String RECEIVER_MSISDN = "RECEIVER_MSISDN";
			private static final String SENDER_ACCOUNT_ID = "SENDER_ACCOUNT_ID";
			private static final String REFERENCE = "reference";
			private static final String EXTRA_FIELDS = "extraFields";
			private static final String AMOUNT = "amount";
			private static final String ACCOUNT = "account";
			private static final String DISTRIBUTOR_ID = "distributorId";
			private static final String TRANSACTION_ID = "transactionId";
			private static final String TRANSACTION_DATE = "transactionDate";
			private static final String MSISDN = "msisdn";
		}

		private static final class Txe
		{
			private static final String RESULT_CODE = "responseCode";
			private static final String RESULT_MESSAGE = "responseDetail";
			private static final String MESSAGE = "message";
			private static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String TRANSACTION_ID = "transactionId";
			private static final String TRANSACTION_DATE = "transactionDate";
			private static final String RESELLER_ID = "resellerId";
			private static final String ERSREFERENCE = "ersreference";
			private static final String MSISDN = "msisdn";
		}
	}
}


