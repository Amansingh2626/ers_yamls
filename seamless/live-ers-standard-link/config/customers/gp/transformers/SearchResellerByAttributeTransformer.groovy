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
public class SearchResellerByAttributeTransformer implements ERSIOTransformer
{

	private static final Logger LOGGER = LoggerFactory.getLogger(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Gp.DATE_FORMAT);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	/**
	 * Transforms outgoing request to server known request
	 *
	 * @param message
	 * @return
	 * @throws TransformerException
	 */
	@Override
	public Message<?> transformInboundRequest(Message<?> message) throws TransformerException
	{

		EtmPoint point = EtmManager.getEtmMonitor().createPoint("ResellerInfoInternalTransformer :: transformOutboundRequest");

		try
		{
			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Gp.COMMAND);
			LinkedCaseInsensitiveMap<Object> data = (LinkedCaseInsensitiveMap<Object>) command.get(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Gp.DATA);
			if (command != null)
			{
				requestFields.putAll(command);
			}
			LOGGER.info("Forming DMS view user request for operation " + com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.OPERATION_ID);
			Map<String, Object> searchRequest = Collections.synchronizedMap(new LinkedHashMap<>());

			List additionalFieldsArray = new ArrayList();
			Map additionalFieldsValue = new HashMap();

			additionalFieldsValue.put(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.NAME, com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.EXTERNAL_CODE);

			if (Boolean.parseBoolean(message.getHeaders().getOrDefault(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.IS_UPDATE_RESELLER_CALL, false).toString()))
			{
				Object externalCode = data.get(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Gp.EXTERNAL_CODE);

				if (externalCode instanceof ArrayList)
				{
					LOGGER.info("`EXTERNALCODE` is instance of ArrayList");
					ArrayList<Object> externalCodes = (ArrayList<Object>) externalCode;
					externalCode = externalCodes.get(0);
				}else{
					LOGGER.info("`EXTERNALCODE` is instance of String");
				}
				additionalFieldsValue.put(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.VALUE, externalCode);
			}
			else
			{
				additionalFieldsValue.put(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.VALUE, String.valueOf(data.get(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Gp.PARENT_EXTERNAL_CODE)));
			}
			additionalFieldsArray.add(additionalFieldsValue);
			searchRequest.put("additionalFields", additionalFieldsArray);
			LOGGER.info("Search by Reseller Request========");
			LOGGER.info(searchRequest.toString());
			return MessageBuilder.withPayload(searchRequest).copyHeaders(message.getHeaders()).setHeaderIfAbsent(StandardLinkConstants.Headers.AUTHORIZATION, message.getHeaders().get("authorization")).setHeaderIfAbsent(StandardLinkConstants.Headers.AUTHORIZATION, "not found").setHeaderIfAbsent(StandardLinkConstants.Headers.SYSTEM_TOKEN, message.getHeaders().get("system-token")).build();
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
	 * @param outgoingResponse
	 * @return
	 * @throws TransformerException
	 */
	@Override
	public Object transformOutboundResponse(Object outgoingResponse) throws TransformerException
	{

		EtmPoint point = EtmManager.getEtmMonitor().createPoint("GpSearchResellerByAttributeRequestTransformer :: transformOutboundResponse");
		try
		{
			LOGGER.info("Forming GP response for operation " + com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.OPERATION_ID);
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.OPERATION_ID).getFields();
			LinkedCaseInsensitiveMap<Object> viewChannelUserResponse = new LinkedCaseInsensitiveMap<>();

			if (outgoingResponse instanceof CompletableFuture)
			{
				outgoingResponse = ((CompletableFuture<?>) outgoingResponse).get();
			}

			if (outgoingResponse instanceof Exception)
			{
				Exception exception = (Exception) outgoingResponse;
				viewChannelUserResponse.put(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Gp.GP_MESSAGE, StandardLinkUtilities.getRootCause(exception));
				viewChannelUserResponse.put(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Gp.GP_TXN_STATUS, StandardLinkResultCodes.INTERNAL_COMPONENT_ERROR);
				return viewChannelUserResponse;
			}

			Map<String, Object> responseMap = (LinkedHashMap<String, Object>) outgoingResponse;
			LOGGER.info("OutgoinResponse is " + responseMap.toString())
			Map<String, Object> requestFields = (Map<String, Object>) responseMap.get(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.REQUEST_PAYLOAD);
			Map<String, Object> requestPayloadCommand = (Map<String, Object>) requestFields.get(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Gp.COMMAND);

			if (requestPayloadCommand != null)
			{
				requestFields.putAll(requestPayloadCommand);
			}

			String defaultMessage = String.valueOf(responseMap.get(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.RESULT_DESCRIPTION) != null ? responseMap.get(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.RESULT_DESCRIPTION) : responseMap.get(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.MESSAGE));
			ResultCode resultCode;
			viewChannelUserResponse.put(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Gp.TYPE, operationFields.get(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Gp.RESPONSE_TYPE));
			viewChannelUserResponse.put(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Gp.DATE, simpleDateFormat.format(new Date()));

			if (!responseMap.isEmpty() && responseMap.containsKey(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.RESULT_CODE))
			{
				Integer resultCodeValue = (Integer) responseMap.get(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.RESULT_CODE);
				resultCode = standardLinkConfig.getResultCodeFor(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.PROVIDER, com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.OPERATION_ID, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
				viewChannelUserResponse.put(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
			}
			else
			{
				resultCode = new ResultCode(String.valueOf(StandardLinkResultCodes.FAILED_ON_SERVER), "Request has failed on server.");
				viewChannelUserResponse.put(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Gp.GP_MESSAGE, "Request has failed on server.");
				viewChannelUserResponse.put(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Gp.GP_TXN_STATUS, StandardLinkResultCodes.FAILED_ON_SERVER);
				viewChannelUserResponse.put(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Gp.ERRORKEY, responseMap.get(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Gp.RESULTCODE).toString());
			}

			if (responseMap.containsKey("resellers"))
			{
				ArrayList resellerList = (ArrayList) responseMap.get("resellers");

				for (int i = 0; i < resellerList.size(); i++)
				{

					LinkedHashMap<String, Object> resellers = (LinkedHashMap<String, Object>) resellerList.get(i);

					if (resellers.containsKey("resellerData"))
					{

						LinkedHashMap<String, Object> resellerData = (LinkedHashMap<String, Object>) resellers.get(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.RESELLER_DATA);
						viewChannelUserResponse.put(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.RESELLER_PATH, resellerData.get(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.RESELLER_PATH));
						viewChannelUserResponse.put(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.RESELLER_TYPE_ID, resellerData.get(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.RESELLER_TYPE_ID));
						viewChannelUserResponse.put(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.RESELLER_ID, resellerData.get(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.RESELLER_ID));
						viewChannelUserResponse.put(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.RESELLER_MSISDN, resellerData.get(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.RESELLER_MSISDN));
						viewChannelUserResponse.put(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.RESELLER_TYPE_NAME, resellerData.get(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.RESELLER_TYPE_NAME));
						viewChannelUserResponse.put(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.PARENT_RESELLER_ID, resellerData.get(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.PARENT_RESELLER_ID));
						viewChannelUserResponse.put("resultCode",resultCode.getInternalResultCode());
						if (resellers.containsKey("additionalFields"))
						{
							ArrayList additionalFieldsList = (ArrayList) resellers.get("additionalFields");

							for (Object item : additionalFieldsList)
							{
								LinkedHashMap<String, String> linkedHashMap = (LinkedHashMap<String, String>) item;

								if (linkedHashMap.containsValue(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.OWNER_MSISDN))
								{
									viewChannelUserResponse.put(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.OWNER_MSISDN, linkedHashMap.get(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.VALUE));
								}
								else if (linkedHashMap.containsValue(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.OWNER_CATEGORY))
								{
									viewChannelUserResponse.put(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.OWNER_CATEGORY, linkedHashMap.get(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.VALUE));
								}
								else if (linkedHashMap.containsValue(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.OWNER_NAME))
								{
									viewChannelUserResponse.put(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.OWNER_NAME, linkedHashMap.get(com.seamless.ers.standardlink.transformers.SearchResellerByAttributeTransformer.Constants.Dms.VALUE));
								}
							}
						}
					}

				}
			}
			return viewChannelUserResponse;
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
		private static final String OPERATION_ID = "SearchResellerByAttribute";
		public static final String REQUEST_PAYLOAD = "requestPayload";

		private static final class Gp
		{
			private static final String RESULTCODE = "resultCode";
			private static final String TYPE = "TYPE";
			private static final String RESPONSE_TYPE = "type";
			private static final String DATA = "DATA";
			private static final String DATE_FORMAT = "dd/MM/YYYY hh:mm:ss";
			private static final String COMMAND = "COMMAND";
			private static final String GP_TXN_STATUS = "TXNSTATUS";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String DATE = "DATE";
			private static final String ERRORKEY = "ERRORKEY";
			private static final String EXTERNAL_CODE = "EXTERNALCODE";
			private static final String PARENT_EXTERNAL_CODE = "PARENTEXTERNALCODE";
		}

		private static final class Dms
		{
			private static final String TYPE = "type";
			private static final String ID = "id";
			private static final String RESULT_CODE = "resultCode";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String MESSAGE = "message";
			private static final String DEALER_ID = "dealerID";
			private static final String RESELLERID = "RESELLERID";
			private static final String FETCH_ACCOUNT_BAL_INFO = "fetchAccountBalanceInformation";
			private static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String VALUE = "value";
			private static final String NAME = "name";
			private static final String FETRESELLERINFORMATION = "FETRESELLERINFORMATION";
			private static final String PARENT_MSISDN = "parentMsisdn";
			private static final String ADDITIONAL_FIELDS = "additionalFields";
			private static final String RESELLER_INFO = "resellerInfo";
			private static final String RESELLER_DATA = "resellerData";
			private static final String RESELLER_MSISDN = "resellerMSISDN";
			private static final String RESELLER_ID = "resellerId";
			private static final String PARENT_RESELLER_NAME = "parentResellerName";
			private static final String ACCOUNTS = "accounts";
			private static final String RESELLER_TYPE_ID = "resellerTypeId";
			private static final String RESELLER_TYPE_NAME = "resellerTypeName";
			private static final String RESELLER_PATH = "resellerPath";
			private static final String PARENT_CATEGORY = "parentCategory";
			private static final String OWNER_NAME = "ownerName";
			private static final String OWNER_MSISDN = "ownerMsisdn";
			private static final String OWNER_CATEGORY = "ownerCategory";
			private static final String PARENT_RESELLER_TYPE = "parentResellerType";
			private static final String RESELLER_BALANCE = "resellerBalance";
			private static final String PARENT_RESELLER_ID = "parentResellerId";
			private static final String EXTERNAL_CODE = "externalCode";
			private static final String IS_UPDATE_RESELLER_CALL = "IS_UPDATE_RESELLER_CALL";
		}
	}
}
