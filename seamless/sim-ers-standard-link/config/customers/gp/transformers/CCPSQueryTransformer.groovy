package com.seamless.ers.standardlink.transformers;

import com.seamless.ers.standardlink.config.StandardLinkConfig;
import com.seamless.ers.standardlink.model.common.ERSIOTransformer;
import com.seamless.ers.standardlink.model.common.constants.StandardLinkConstants;
import com.seamless.ers.standardlink.model.configs.ProviderConfigurations;
import com.seamless.ers.standardlink.model.customer.gp.ccpsQuery.request.AccountDetails;
import com.seamless.ers.standardlink.model.customer.gp.ccpsQuery.request.Request;
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

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component("cCPSQueryTransformer")
public class CCPSQueryTransformer implements ERSIOTransformer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(CCPSQueryTransformer.class);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Override
	public Message<?> transformOutboundRestRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("CcpsQueryTransformer :: transformOutboundRestRequest");

		try
		{
			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			Map<String, Object> additionalFields = (Map<String, Object>) requestFields.get(Constants.ADDITIONAL_FIELDS);

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION_ID).getFields();

			Request request = new Request();
			AccountDetails accountDetails = new AccountDetails();

			request.setSourceSystem((String) operationFields.get(Constants.SOURCE_SYSTEM));
			request.setSourceTxnId((String) additionalFields.get(Constants.ERS_REFERENCE));
			request.setBankName((String) operationFields.get(Constants.BANK_NAME));
			request.setPaymentDate((String) additionalFields.get(Constants.PAYMENT_DATE));
			accountDetails.setAmount((int) Double.parseDouble((String) additionalFields.get(Constants.AMOUNT)));
			accountDetails.setMsisdn((String) additionalFields.get(Constants.MSISDN));
			request.setAccountDetails(Collections.singletonList(accountDetails));

			return MessageBuilder.withPayload(request).copyHeaders(message.getHeaders())
					.setHeader(HttpHeaders.CONTENT_TYPE, operationFields.get(StandardLinkConstants.Headers.CONTENT_TYPE))
					.setHeaderIfAbsent(StandardLinkConstants.Fields.CHANNEL, operationFields.get(StandardLinkConstants.Fields.CHANNEL))
					.setHeaderIfAbsent(HttpHeaders.AUTHORIZATION, getBasicToken())
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

	/**
	 * Transforms outgoing response to Server compatible response
	 *
	 * @param incomingResponse
	 * @return
	 * @throws TransformerException
	 */
	@Override
	public Object transformInboundRestResponse(Object incomingResponse) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("CcpsQueryTransformer :: transformInboundRestResponse");
		try
		{
			Map<String, Object> response = new LinkedHashMap<>();
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);

			if (incomingResponse instanceof CompletableFuture)
			{
				incomingResponse = ((CompletableFuture<?>) incomingResponse).get();
			}

			if (incomingResponse instanceof Exception)
			{
				Exception exception = (Exception) incomingResponse;
				response.put(Constants.RESULT_DESCRIPTION, StandardLinkUtilities.getRootCause(exception));
				response.put(Constants.RESULT_CODE, StandardLinkResultCodes.INTERNAL_COMPONENT_ERROR);
				return response;
			}

			Map<String, Object> internalResponse = (LinkedHashMap<String, Object>) incomingResponse;
			if(internalResponse.get(Constants.STATUS).equals("ALREADY_PROCESSED")) {
				response.put(Constants.SERVER_RESPONSE, internalResponse.get(Constants.TXNSTATUS));
				response.put(Constants.RESULT_CODE, StandardLinkResultCodes.SUCCESS);
				response.put(Constants.RESULT_DESCRIPTION, "CCPS Query Topup : '" + internalResponse.get(Constants.TXNSTATUS) + "'.");
			}else {
				response.put(Constants.SERVER_RESPONSE, internalResponse.get(Constants.STATUS));
				response.put(Constants.RESULT_CODE, internalResponse.get(Constants.ERROR_CODE));
				response.put(Constants.RESULT_DESCRIPTION, "CCPS Query Topup : '" + internalResponse.get(Constants.STATUS) + "'.");
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
		private static final String ERROR_CODE = "errorCode";
		private static final String STATUS = "status";
		private static final String TXNSTATUS = "txnStatus";
		private static final String SOURCE_SYSTEM = "sourceSystem";
		private static final String BANK_NAME = "bankName";
		private static final String PAYMENT_DATE = "paymentDate";
		private static final String AMOUNT = "amount";
		private static final String MSISDN = "msisdn";
		private static final String PROVIDER = "gp";
		private static final String OPERATION_ID = "ccpsQuery";
		private static final String ERS_REFERENCE = "ersReference";
		private static final String SERVER_RESPONSE = "serverResponse";
		private static final String RESULT_DESCRIPTION = "resultDescription";
		private static final String RESULT_CODE = "resultCode";
		private static final String ADDITIONAL_FIELDS = "additionalFields";
	}

	private String getBasicToken() {

		ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
		Map<String, Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION_ID).getFields();

		String str = (operationFields.get("username") == null ? "" : operationFields.get("username")) + ":" + (operationFields.get("password") == null ? "" : operationFields.get("password"));
		return "Basic " + Base64Utils.encodeToString(str.getBytes(StandardCharsets.UTF_8));
	}

}
