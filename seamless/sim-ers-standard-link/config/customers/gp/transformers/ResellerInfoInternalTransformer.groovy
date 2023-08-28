package com.seamless.ers.standardlink.transformers;

import com.seamless.common.StringUtils;
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
public class ResellerInfoInternalTransformer implements ERSIOTransformer
{

	private static final Logger LOGGER = LoggerFactory.getLogger(ResellerInfoInternalTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);

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

		try {
			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LOGGER.info("Forming DMS view user request for operation " + Constants.OPERATION_ID);

			if (requestFields != null) {
				Map<String, Object> viewUserRequest = Collections.synchronizedMap(new LinkedHashMap<>());
				Map<String, Object> dealerInfo = Collections.synchronizedMap(new LinkedHashMap<>());
				Boolean isInternalCall = (Boolean) message.getHeaders().getOrDefault(Constants.Gp.IS_INTERNAL_CALL, false);

				if (isInternalCall) {
					dealerInfo.put(Constants.Dms.TYPE, Constants.Dms.RESELLERID);
					dealerInfo.put(Constants.Dms.ID, requestFields.get(Constants.Dms.FETRESELLERINFORMATION));
				}

				viewUserRequest.put(Constants.Dms.DEALER_ID, dealerInfo);
				viewUserRequest.put(Constants.Dms.FETCH_ACCOUNT_BAL_INFO, "true");

				return MessageBuilder.withPayload(viewUserRequest).copyHeaders(message.getHeaders()).setHeaderIfAbsent(StandardLinkConstants.Headers.AUTHORIZATION, message.getHeaders().get("authorization")).setHeaderIfAbsent(StandardLinkConstants.Headers.SYSTEM_TOKEN, message.getHeaders().get("system-token")).build();
			} else {
				LOGGER.info("The request sent is invalid for operation " + Constants.OPERATION_ID);
				return null;
			}
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
	 * @param additionalFields
	 * @return
	 */
	private boolean isCallForC2CWithdrawInterceptor(Map<String, Object> additionalFields) {
		return ((additionalFields.get(Constants.Gp.GP_EXT_REQUEST_TYPE).toString().equalsIgnoreCase("EXC2CWDREQ") && additionalFields.containsKey(Constants.Gp.MSISDN2) && additionalFields.containsKey(Constants.Gp.LOGINID2)) ||
				(additionalFields.get(Constants.Gp.GP_EXT_REQUEST_TYPE).toString().equalsIgnoreCase("WDTHREQ") && additionalFields.containsKey(Constants.Gp.MSISDN1) && additionalFields.containsKey(Constants.Gp.MSISDN2)));

	}

	private boolean isCallForFetchParentResellerInformation(Map<String, Object> additionalFields) {
		return Objects.nonNull(((LinkedHashMap) additionalFields.get(Constants.Gp.DATA))) &&
				Objects.nonNull(((LinkedHashMap) additionalFields.get(Constants.Gp.DATA)).get(Constants.Gp.PARENTORIGINID)) &&
				Objects.nonNull(((LinkedHashMap) additionalFields.get(Constants.Gp.DATA)).get(Constants.Gp.PARENTMSISDN)) &&
				Objects.nonNull(((LinkedHashMap) additionalFields.get(Constants.Gp.DATA)).get(Constants.Gp.PARENTMSISDN).toString()) &&
				StringUtils.isNotEmpty(((LinkedHashMap) additionalFields.get(Constants.Gp.DATA)).get(Constants.Gp.PARENTORIGINID).toString());

	}

	private boolean isCallForOwnerResellerInformation(Map<String, Object> additionalFields) {
		return Objects.nonNull(((LinkedHashMap) additionalFields.get(Constants.Gp.DATA))) &&
				Objects.nonNull(((LinkedHashMap) additionalFields.get(Constants.Gp.DATA)).get(Constants.Gp.PARENTORIGINID)) &&
				Objects.nonNull(((LinkedHashMap) additionalFields.get(Constants.Gp.DATA)).get(Constants.Gp.PARENTMSISDN)) &&
				Objects.nonNull(((LinkedHashMap) additionalFields.get(Constants.Gp.DATA)).get(Constants.Gp.PARENTMSISDN).toString()) &&
				StringUtils.isNotEmpty(((LinkedHashMap) additionalFields.get(Constants.Gp.DATA)).get(Constants.Gp.PARENTORIGINID).toString());

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

		LOGGER.info("Forming GP response for operation " + Constants.OPERATION_ID);
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("ResellerInfoInternalTransformer :: transformOutboundResponse");


		try
		{
			LinkedCaseInsensitiveMap<Object> response = new LinkedCaseInsensitiveMap<>();
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION_ID).getFields();

			if (outgoingResponse instanceof CompletableFuture)
			{
				outgoingResponse = ((CompletableFuture<?>) outgoingResponse).get();
			}

			if (outgoingResponse instanceof Exception)
			{
				Exception exception = (Exception) outgoingResponse;
				response.put(Constants.Dms.RESULT_DESCRIPTION, StandardLinkUtilities.getRootCause(exception));
				response.put(Constants.Dms.RESULT_CODE, StandardLinkResultCodes.INTERNAL_COMPONENT_ERROR);
				return response;
			}

			Map<String, Object> internalResponse = (LinkedHashMap<String, Object>) outgoingResponse;
			Map<String, Object> request = (Map<String, Object>) internalResponse.get(Constants.Gp.REQUEST_PAYLOAD);
			Map<String, Object> requestPayloadCommand = (Map<String, Object>) request.get(Constants.Gp.COMMAND);
			Map<String, Object> requestData = (Map<String, Object>) requestPayloadCommand.get(Constants.Gp.DATA);
			Map<String, Object> msisdns = (Map<String, Object>) requestData.get(Constants.Gp.MSISDNS);

			if (!internalResponse.isEmpty() && internalResponse.containsKey(Constants.Dms.RESULT_CODE))
			{
				Integer resultCodeValue = (Integer) internalResponse.get(Constants.Dms.RESULT_CODE);
				String defaultMessage = String.valueOf(internalResponse.get(Constants.Dms.RESULT_MESSAGE) != null ? internalResponse.get(Constants.Dms.RESULT_MESSAGE) : internalResponse.get(Constants.Dms.MESSAGE) != null ? internalResponse.get(Constants.Dms.MESSAGE) : internalResponse.get(Constants.Dms.RESULT_DESCRIPTION));

				if (resultCodeValue == StandardLinkResultCodes.SUCCESS)
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION_ID, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);

					response.put(Constants.Dms.RESULT_DESCRIPTION, resultCode.getDescription());
					response.put(Constants.Dms.RESULT_CODE, resultCode.getInternalResultCode());

					response.put(Constants.Gp.TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));
					response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));

					if (Objects.nonNull(requestPayloadCommand.get(Constants.Gp.EXTREFNUM)) &&
							!org.apache.commons.lang3.StringUtils.isEmpty(requestPayloadCommand.get(Constants.Gp.EXTREFNUM).toString())) {
						response.put("extRefNum", requestPayloadCommand.get(Constants.Gp.EXTREFNUM).toString());

					}
					Map<String, Object> roles = new LinkedHashMap<>();
					Map<String, Object> record = new LinkedHashMap<>();
					Map<String, Object> data = new LinkedHashMap<>();

					Map<String, Object> accountMap = null;
					LinkedHashMap<String, Object> resellerData = null;

					if (Objects.nonNull(internalResponse.get(Constants.Dms.RESELLER_INFO)) && !StringUtils.isEmpty(String.valueOf(internalResponse.get(Constants.Dms.RESELLER_INFO))))
					{
						LinkedHashMap<String, Object> resellerInfo = (LinkedHashMap<String, Object>) internalResponse.get(Constants.Dms.RESELLER_INFO);

						if (Objects.nonNull(resellerInfo.get(Constants.Dms.RESELLER_DATA)) && !StringUtils.isEmpty(String.valueOf(resellerInfo.get(Constants.Dms.RESELLER_DATA))))
						{
							resellerData = (LinkedHashMap<String, Object>) resellerInfo.get(Constants.Dms.RESELLER_DATA);
							List<Map<String, Object>> accountMapList = (List<Map<String, Object>>) resellerData.get(Constants.Dms.ACCOUNTS);
							if (Objects.nonNull(accountMapList) && accountMapList.size() > 0)
							{
								accountMap = accountMapList.get(0);
							}

							response.put(Constants.Dms.RESELLER_TYPE_ID, resellerData.get(Constants.Dms.RESELLER_TYPE_ID));
							response.put(Constants.Dms.RESELLER_PATH, resellerData.get(Constants.Dms.RESELLER_PATH));
							response.put(Constants.Dms.RESELLER_ID, resellerData.get(Constants.Dms.RESELLER_ID));
							response.put(Constants.Dms.RESELLER_MSISDN, resellerData.get(Constants.Dms.RESELLER_MSISDN));

							if (Objects.nonNull(resellerInfo.get(Constants.Dms.ADDITIONAL_FIELDS)) && !StringUtils.isEmpty(String.valueOf(resellerInfo.get(Constants.Dms.ADDITIONAL_FIELDS))))
							{

								ArrayList additionalField = (ArrayList) resellerInfo.get(Constants.Dms.ADDITIONAL_FIELDS);

								for (Object item : additionalField)
								{
									LinkedHashMap<String, Object> linkedHashMap = (LinkedHashMap<String, Object>) item;

									if (linkedHashMap.containsValue(Constants.Dms.PARENT_MSISDN))
									{
										response.put(Constants.Dms.PARENT_MSISDN, linkedHashMap.get(Constants.Dms.VALUE));
									}
									else if (linkedHashMap.containsValue(Constants.Dms.PARENT_RESELLER_TYPE))
									{
										response.put(Constants.Dms.PARENT_CATEGORY, linkedHashMap.get(Constants.Dms.VALUE));
									}
									else if (linkedHashMap.containsValue(Constants.Dms.OWNER_NAME))
									{
										response.put(Constants.Dms.OWNER_NAME, linkedHashMap.get(Constants.Dms.VALUE));
									}
									else if (linkedHashMap.containsValue(Constants.Dms.OWNER_MSISDN))
									{
										response.put(Constants.Dms.OWNER_MSISDN, linkedHashMap.get(Constants.Dms.VALUE));
									}
									else if (linkedHashMap.containsValue(Constants.Dms.OWNER_CATEGORY))
									{
										response.put(Constants.Dms.OWNER_CATEGORY, linkedHashMap.get(Constants.Dms.VALUE));
									}

								}
							}
							data.put("roles", roles);
							response.put("data", data);
							Map<String, Object> balanceMap = accountMap != null ? (Map<String, Object>) accountMap.get(Constants.Dms.VALUE) : null;
							response.put(Constants.Dms.RESELLER_BALANCE, balanceMap != null ? balanceMap.getOrDefault(Constants.Dms.VALUE, 0) : 0);
							response.put(Constants.Dms.PARENT_RESELLER_ID, resellerData.get(Constants.Dms.PARENT_RESELLER_ID));
							response.put(Constants.Dms.PARENT_RESELLER_NAME, resellerData.get(Constants.Dms.PARENT_RESELLER_NAME));

						}
					}
				}
				else
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION_ID, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Dms.RESULT_DESCRIPTION, resultCode.getDescription());
					response.put(Constants.Dms.RESULT_CODE, resultCode.getInternalResultCode());
				}
			}
			else
			{
				response.put(Constants.Dms.RESULT_DESCRIPTION, "Request has failed on server.");
				response.put(Constants.Dms.RESULT_CODE, StandardLinkResultCodes.FAILED_ON_SERVER);
			}

			response.put(Constants.Gp.DATA, requestData);

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
		private static final String OPERATION_ID = "viewChannelUser";

		private static final class Gp
		{
			public static final String GP_EXT_REQUEST_TYPE = "TYPE";
			public static final String MSISDN2 = "MSISDN2";
			public static final String MSISDN1 = "MSISDN1";
			public static final String LOGINID2 = "LOGINID2";
			public static final Object PARENTORIGINID = "PARENTORIGINID";
			public static final Object PARENTMSISDN = "PARENTMSISDN";
			public static final String TYPE = "TYPE";
			public static final String RESPONSE_TYPE = "type";
			public static final String GP_EXT_REQUEST_DATE = "DATE";
			public static final String EXTREFNUM = "EXTREFNUM";
			private static final String MSISDNS = "MSISDNS";
			private static final String DATA = "DATA";
			private static final String DATE_FORMAT = "dd/MM/YYYY hh:mm:ss";
			private static final String COMMAND = "COMMAND";
			private static final String IS_INTERNAL_CALL = "IS_INTERNAL_CALL";
			private static final String REQUEST_PAYLOAD = "requestPayload";
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
			private static final String RESELLER_PATH = "resellerPath";
			private static final String PARENT_CATEGORY = "parentCategory";
			private static final String OWNER_NAME = "ownerName";
			private static final String OWNER_MSISDN = "ownerMsisdn";
			private static final String OWNER_CATEGORY = "ownerCategory";
			private static final String PARENT_RESELLER_TYPE = "parentResellerType";
			private static final String RESELLER_BALANCE = "resellerBalance";
			private static final String PARENT_RESELLER_ID = "parentResellerId";
		}
	}
}