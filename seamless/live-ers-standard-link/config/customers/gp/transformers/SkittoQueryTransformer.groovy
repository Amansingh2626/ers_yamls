package com.seamless.ers.standardlink.transformers;

import com.seamless.ers.standardlink.config.StandardLinkConfig;
import com.seamless.ers.standardlink.model.common.ERSIOTransformer;
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
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class SkittoQueryTransformer implements ERSIOTransformer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(SkittoQueryTransformer.class);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Override
	public Message<?> transformOutboundRestRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("SkittoQueryTransformer :: transformOutboundRestRequest");

		try
		{
			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			Map<String, Object> additionalFields = (Map<String, Object>) requestFields.get(Constants.ADDITIONAL_FIELDS);

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION_ID).getFields();

			String ersReference = (String) additionalFields.get(Constants.ERS_REFERENCE);
			String serverType = (String) additionalFields.get(Constants.SERVER_TYPE);

			Map<String, Object> queryRequest = Collections.synchronizedMap(new LinkedHashMap<>());

			queryRequest.put(Constants.ERS_REFERENCE, ersReference);
			queryRequest.put(Constants.SERVER_TYPE, serverType);

			return MessageBuilder.withPayload(queryRequest).copyHeaders(message.getHeaders()).setHeader(HttpHeaders.CONTENT_TYPE, operationFields.get(StandardLinkConstants.Headers.CONTENT_TYPE)).setHeaderIfAbsent(StandardLinkConstants.Headers.VALIDATION, StandardLinkConstants.Headers.PASSED).build();
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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("SkittoQueryTransformer :: transformInboundRestResponse");
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
			response.put(Constants.SERVER_RESPONSE,internalResponse.get(Constants.SERVER_RESPONSE));
			response.put(Constants.RESULT_CODE,StandardLinkResultCodes.SUCCESS);
			response.put(Constants.RESULT_DESCRIPTION,"SKITTO Query Topup : '" + internalResponse.get(Constants.RESULT_MESSAGE) + "'.");

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
		private static final String OPERATION_ID = "skittoQuery";
		private static final String ERS_REFERENCE = "ersReference";
		private static final String SERVER_TYPE = "serverType";
		private static final String SERVER_RESPONSE = "serverResponse";
		private static final String RESULT_DESCRIPTION = "resultDescription";
		private static final String RESULT_MESSAGE = "resultMessage";
		private static final String RESULT_CODE = "resultCode";
		private static final String ADDITIONAL_FIELDS = "additionalFields";
	}

}