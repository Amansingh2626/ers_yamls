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
public class ViewChannelUserInternalTransformer implements ERSIOTransformer
{

	private static final Logger LOGGER = LoggerFactory.getLogger(ViewChannelUserInternalTransformer.class);
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

		EtmPoint point = EtmManager.getEtmMonitor().createPoint("ViewChannelUserInternalTransformer :: transformInboundRequest");

		try
		{
			LOGGER.info("Forming DMS view user request for operation " + Constants.OPERATION_ID);

			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.COMMAND);
			LinkedCaseInsensitiveMap<Object> commandData = (LinkedCaseInsensitiveMap<Object>) command.get(Constants.Gp.DATA);
			Map<String, Object> viewUserRequest = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> dealerInfo = Collections.synchronizedMap(new LinkedHashMap<>());
			Boolean isInternalCall = (Boolean) message.getHeaders().getOrDefault("IS_INTERNAL_CALL", false);

			String dmsSystemToken = (String) message.getHeaders().get("system-token");
			dmsSystemToken = dmsSystemToken.replaceAll("\"rootComponent\": ?\"[a-zA-Z-]*\"", "\"rootComponent\": \"dms\"");

			if (isInternalCall)
			{
				//This condition is given to validate the calls coming from EXTGW for De-register/De-activate/Delete a reseller
				if (isCallForDeregisterInterceptor(command))
				{
					dealerInfo.put(Constants.Dms.TYPE, Constants.Dms.RESELLERID);
					dealerInfo.put(Constants.Dms.ID, (commandData.get(Constants.Gp.USERORIGIN_ID)));
				}//This condition is for calls coming from EXTGW for C2C Withdraw call.
				else if (isCallForC2CWithdrawInterceptor(command))
				{
					if (!command.get(Constants.Gp.MSISDN2).toString().isEmpty())
					{
						dealerInfo.put(Constants.Dms.TYPE, Constants.Dms.RESELLERMSISDN);
						dealerInfo.put(Constants.Dms.ID, command.get(Constants.Gp.MSISDN2).toString());
					}
					else
					{
						dealerInfo.put(Constants.Dms.TYPE, Constants.Dms.RESELLERID);
						dealerInfo.put(Constants.Dms.ID, command.get(Constants.Gp.LOGINID2).toString());
					}
				}
				else if (isCallForChannelUserMovement(command))
				{
					dealerInfo.put(Constants.Dms.TYPE, Constants.Dms.RESELLERID);
					dealerInfo.put(Constants.Dms.ID, command.get(Constants.Gp.FROM_USER_ORIGIN_ID).toString());
				}
			}
			else
			{
				if (Objects.nonNull(commandData.get(Constants.Gp.MSISDN)) && StringUtils.isNotEmpty(commandData.get(Constants.Gp.MSISDN).toString()))
				{
					dealerInfo.put(Constants.Dms.TYPE, Constants.Dms.RESELLERMSISDN);
					dealerInfo.put(Constants.Dms.ID,commandData.get(Constants.Gp.MSISDN).toString());
				}
				else
				{
					dealerInfo.put(Constants.Dms.TYPE, Constants.Dms.RESELLERID);
					dealerInfo.put(Constants.Dms.ID, commandData.get(Constants.Gp.USERORIGIN_ID));
				}
			}

			viewUserRequest.put(Constants.Dms.DEALER_ID, dealerInfo);
			viewUserRequest.put(Constants.Dms.FETCH_ACCOUNT_BAL_INFO, "true");

			return MessageBuilder.withPayload(viewUserRequest).copyHeaders(message.getHeaders())
					.setHeader("errorChannel", message.getHeaders().get("errorChannel"))
					.setHeader("IS_INTERNAL_CALL", message.getHeaders().get("IS_INTERNAL_CALL"))
					.setHeader("history", message.getHeaders().get("history"))
					.setHeader("PROVIDER_ID", message.getHeaders().get("PROVIDER_ID"))
					.setHeader("replyChannel", message.getHeaders().get("replyChannel"))
					.setHeader("OPERATION", message.getHeaders().get("OPERATION"))
					.setHeader("host", message.getHeaders().get("host"))
					.setHeader("connection", message.getHeaders().get("connection"))
					.setHeader("cache-control", message.getHeaders().get("cache-control"))
					.setHeader("accept-encoding", message.getHeaders().get("accept-encoding"))
					.setHeader(Constants.REQUEST_PAYLOAD, message.getHeaders().get(Constants.REQUEST_PAYLOAD))
					.setHeader(StandardLinkConstants.Headers.AUTHORIZATION, "authorization")
					.setHeader(StandardLinkConstants.Headers.SYSTEM_TOKEN, dmsSystemToken).build();
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

		EtmPoint point = EtmManager.getEtmMonitor().createPoint("ViewChannelUserInternalTransformer :: transformOutboundResponse");

		try
		{
			LOGGER.info("Forming GP response for operation " + Constants.OPERATION_ID);
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION_ID).getFields();
			Map<String, Object> viewChannelUserInternalResponse = new LinkedCaseInsensitiveMap<>();

			if (outgoingResponse instanceof CompletableFuture)
			{
				outgoingResponse = ((CompletableFuture<?>) outgoingResponse).get();
			}

			if (outgoingResponse instanceof Exception)
			{
				Exception exception = (Exception) outgoingResponse;
				viewChannelUserInternalResponse.put(Constants.Gp.GP_MESSAGE, StandardLinkUtilities.getRootCause(exception));
				viewChannelUserInternalResponse.put(Constants.Gp.GP_TXN_STATUS, StandardLinkResultCodes.INTERNAL_COMPONENT_ERROR);
				return viewChannelUserInternalResponse;
			}

			Map<String, Object> responseMap = (LinkedHashMap<String, Object>) outgoingResponse;
			Map<String, Object> requestFields = (Map<String, Object>) responseMap.get(Constants.REQUEST_PAYLOAD);
			Map<String, Object> requestPayloadCommand = (Map<String, Object>) requestFields.get(Constants.Gp.COMMAND);

			if (requestPayloadCommand != null)
			{
				requestFields.putAll(requestPayloadCommand);
			}

			String defaultMessage = String.valueOf(responseMap.get(Constants.Dms.RESULT_DESCRIPTION) != null ? responseMap.get(Constants.Dms.RESULT_DESCRIPTION) : responseMap.get(Constants.Dms.MESSAGE));
			ResultCode resultCode;
			viewChannelUserInternalResponse.put(Constants.Gp.TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));
			viewChannelUserInternalResponse.put(Constants.Gp.DATE, simpleDateFormat.format(new Date()));

			if (!responseMap.isEmpty() && responseMap.containsKey("resultCode"))
			{
				Integer resultCodeValue = (Integer) responseMap.get("resultCode");
				resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION_ID, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
				viewChannelUserInternalResponse.put(Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
			}
			else
			{
				resultCode = new ResultCode(String.valueOf(StandardLinkResultCodes.FAILED_ON_SERVER), "Request has failed on server.");
				viewChannelUserInternalResponse.put(Constants.Gp.GP_MESSAGE, "Request has failed on server.");
				viewChannelUserInternalResponse.put(Constants.Gp.GP_TXN_STATUS, StandardLinkResultCodes.FAILED_ON_SERVER);
				viewChannelUserInternalResponse.put(Constants.Gp.ERRORKEY, responseMap.get(Constants.Gp.RESULTCODE).toString());
			}

			if (resultCode.getInternalResultCode().equalsIgnoreCase(String.valueOf(StandardLinkResultCodes.INTERNAL_SUCCESS)) || resultCode.getInternalResultCode().equalsIgnoreCase(String.valueOf(StandardLinkResultCodes.SUCCESS)))
			{
				viewChannelUserInternalResponse.put(Constants.Gp.EXTREFNUM, requestFields.get(Constants.Gp.EXTREFNUM).toString());
				Map<String, Object> data = Collections.synchronizedMap(new LinkedHashMap<>());
				Map<String, Object> roles = Collections.synchronizedMap(new LinkedHashMap<>());
				Map<String, Object> record = Collections.synchronizedMap(new LinkedHashMap<>());
				Map<String, Object> accountMap = null;
				LinkedHashMap<String, Object> resellerData = null;

				if (Objects.nonNull(responseMap.get(Constants.Dms.RESELLER_INFO)) && !StringUtils.isEmpty(String.valueOf(responseMap.get(Constants.Dms.RESELLER_INFO))))
				{
					LinkedHashMap<String, Object> resellerInfo = (LinkedHashMap<String, Object>) responseMap.get(Constants.Dms.RESELLER_INFO);

					if (Objects.nonNull(resellerInfo.get(Constants.Dms.RESELLER_DATA)) && !StringUtils.isEmpty(String.valueOf(resellerInfo.get(Constants.Dms.RESELLER_DATA))))
					{
						resellerData = (LinkedHashMap<String, Object>) resellerInfo.get(Constants.Dms.RESELLER_DATA);
						List<Map<String, Object>> accountMapList = (List<Map<String, Object>>) resellerData.get("accounts");
						if (Objects.nonNull(accountMapList))
						{
							accountMap = accountMapList.get(0);
						}
						if (Objects.nonNull(resellerData.get(Constants.Dms.STATUS)) && !StringUtils.isEmpty((String) resellerData.get(Constants.Dms.STATUS)))
						{

							data.put(Constants.Gp.STATUS, resellerData.get(Constants.Dms.STATUS));
							if (resellerData.get(Constants.Dms.STATUS).equals(Constants.Dms.FROZEN))
							{
								data.put(Constants.Gp.INSUSPEND, Constants.Dms.Y);
							}
							else
							{
								data.put(Constants.Gp.INSUSPEND, Constants.Dms.N);
							}
							if (resellerData.get(Constants.Dms.STATUS).equals(Constants.Dms.BLOCKED))
							{
								data.put(Constants.Gp.OUTSUSPEND, Constants.Dms.Y);
							}
							else
							{
								data.put(Constants.Gp.OUTSUSPEND, Constants.Dms.N);
							}
						}

						if (Objects.nonNull(resellerData.get(Constants.Dms.RESELLER_NAME)) && !StringUtils.isEmpty((String) resellerData.get(Constants.Dms.RESELLER_NAME)))
						{
							data.put(Constants.Gp.USRNAME, resellerData.get(Constants.Dms.RESELLER_NAME));
						}
						if (Objects.nonNull(resellerData.get(Constants.Dms.RESELLERMSISDN)) && !StringUtils.isEmpty((String) resellerData.get(Constants.Dms.RESELLERMSISDN)))
						{
							data.put(Constants.Gp.MSISDN, resellerData.get(Constants.Dms.RESELLERMSISDN));
						}
						if (Objects.nonNull(resellerData.get(Constants.Dms.RESELLERID)) && !StringUtils.isEmpty((String) resellerData.get(Constants.Dms.RESELLERID)))
						{
							data.put(Constants.Gp.LOGINID, resellerData.get(Constants.Dms.RESELLERID));
						}
						if (Objects.nonNull(resellerData.get(Constants.Dms.PARENT_RESELLER_NAME)) && !StringUtils.isEmpty((String) resellerData.get(Constants.Dms.PARENT_RESELLER_NAME)))
						{
							data.put(Constants.Gp.PARENTNAME, String.valueOf(resellerData.get(Constants.Dms.PARENT_RESELLER_NAME)));
						}

						if (Objects.nonNull(resellerInfo.get(Constants.Dms.USERS)) && !StringUtils.isEmpty(String.valueOf(resellerInfo.get(Constants.Dms.USERS))))
						{
							List<String> usersData = (List<String>) resellerInfo.get(Constants.Dms.USERS);
							for (Object user : usersData)
							{
								LinkedHashMap<String, Object> userMap = (LinkedHashMap<String, Object>) user;
								if (Objects.nonNull(userMap.get(Constants.Dms.USER_ID)) && !StringUtils.isEmpty((String) userMap.get(Constants.Dms.USER_ID)) && Objects.nonNull(userMap.get(Constants.Dms.ROLE_NAME)) && !StringUtils.isEmpty((String) userMap.get(Constants.Dms.ROLE_NAME)))
								{
									String userName = (String) userMap.get(Constants.Dms.USER_ID);
									if (Objects.nonNull(resellerData.get(Constants.Dms.RESELLERID)) && userName.equals(resellerData.get(Constants.Dms.RESELLERID)))
									{
										record.put(Constants.Gp.TYPE, String.valueOf(userMap.get(Constants.Dms.ROLE_NAME)));
									}
								}
							}
						}
					}
					if (Objects.nonNull(resellerInfo.get(Constants.Dms.ADDITIONAL_FIELDS)) && !StringUtils.isEmpty(String.valueOf((ArrayList) resellerInfo.get(Constants.Dms.ADDITIONAL_FIELDS))))
					{

						ArrayList additionalField = (ArrayList) resellerInfo.get(Constants.Dms.ADDITIONAL_FIELDS);

						List<String> addressList = new LinkedList<>();
						for (Object item : additionalField)
						{
							LinkedHashMap<String, String> linkedHashMap = (LinkedHashMap<String, String>) item;

							if (linkedHashMap.containsValue(Constants.Dms.EXTERNAL_CODE))
							{
								data.put(Constants.Gp.EXTCODE, linkedHashMap.get(Constants.Dms.VALUE));
							}
							else if (linkedHashMap.containsValue(Constants.Dms.SHORT_NAME))
							{
								data.put(Constants.Gp.SHTNAME, linkedHashMap.get(Constants.Dms.VALUE));
							}
							else if (linkedHashMap.containsValue(Constants.Dms.SUBSCRIBER_CODE))
							{
								data.put(Constants.Gp.SUBCODE, linkedHashMap.get(Constants.Dms.VALUE));
							}
							else if (linkedHashMap.containsValue(Constants.Dms.ADDRESS1))
							{
								addressList.add(linkedHashMap.get(Constants.Dms.VALUE));
							}
							else if (linkedHashMap.containsValue(Constants.Dms.ADDRESS2))
							{
								addressList.add(linkedHashMap.get(Constants.Dms.VALUE));
							}
							else if (linkedHashMap.containsValue(Constants.Dms.CITY))
							{
								addressList.add(linkedHashMap.get(Constants.Dms.VALUE));
							}
							else if (linkedHashMap.containsValue(Constants.Dms.STATE))
							{
								addressList.add(linkedHashMap.get(Constants.Dms.VALUE));
							}
							else if (linkedHashMap.containsValue(Constants.Dms.COUNTRY))
							{
								addressList.add(linkedHashMap.get(Constants.Dms.VALUE));
							}
							else if (linkedHashMap.containsValue(Constants.Dms.PARENTMSISDN))
							{
								data.put(Constants.Gp.PARENTMSISDN, linkedHashMap.get(Constants.Dms.VALUE));
							}

						}
						String address = String.join(",", addressList);
						data.put(Constants.Gp.ADDRESS, address);
					}
				}
				roles.put(Constants.Gp.RECORD, record);
				data.put(Constants.Gp.ROLES, roles);
				viewChannelUserInternalResponse.put(Constants.Gp.DATA, data);
				Map<String, Object> balanceMap = accountMap != null ? (Map<String, Object>) accountMap.get(Constants.Dms.BALANCE) : null;
				if (balanceMap != null)
				{
					viewChannelUserInternalResponse.put(Constants.Dms.RESELLER_BALANCE, balanceMap.getOrDefault(Constants.Dms.VALUE, 0));
				}
				else
				{
					viewChannelUserInternalResponse.put(Constants.Dms.RESELLER_BALANCE, 0);
				}
				if (resellerData != null)
				{
					data.put(Constants.Dms.PARENT_RESELLER_ID, resellerData.get(Constants.Dms.PARENT_RESELLER_ID));
					data.put(Constants.Dms.PARENT_RESELLER_NAME, resellerData.get(Constants.Dms.PARENT_RESELLER_NAME));
					data.put(Constants.Gp.CATEGORYCODE, resellerData.get(Constants.Dms.RESELLER_TYPE_ID));
				}
			}
			else
			{
				viewChannelUserInternalResponse.put(Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
				viewChannelUserInternalResponse.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
			}

			return viewChannelUserInternalResponse;
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
		private static final String OPERATION_ID = "viewChannelUserInternal";
		private static final String REQUEST_PAYLOAD = "requestPayload";

		private static final class Gp
		{
			private static final String RESULTCODE = "resultCode";
			private static final String MSISDN = "MSISDN";
			private static final String MSISDN1 = "MSISDN1";
			private static final String MSISDN2 = "MSISDN2";
			private static final String GP_TXN_STATUS = "TXNSTATUS";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String DATA = "DATA";
			private static final String EXTREFNUM = "EXTREFNUM";
			private static final String EXC2CWDREQ = "EXC2CWDREQ";
			private static final String WDTHREQ = "WDTHREQ";
			private static final String DATE_FORMAT = "dd/MM/YYYY hh:mm:ss";
			private static final String COMMAND = "COMMAND";
			private static final String USERORIGIN_ID = "USERORIGINID";
			private static final String LOGINID2 = "LOGINID2";
			private static final String GP_EXT_REQUEST_TYPE = "TYPE";
			private static final String DATE = "DATE";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String TYPE = "TYPE";
			private static final String ERRORKEY = "ERRORKEY";
			private static final String STATUS = "STATUS";
			private static final String INSUSPEND = "INSUSPEND";
			private static final String OUTSUSPEND = "OUTSUSPEND";
			private static final String USRNAME = "USRNAME";
			private static final String LOGINID = "LOGINID";
			private static final String EXTCODE = "EXTCODE";
			private static final String SHTNAME = "SHTNAME";
			private static final String PARENTNAME = "PARENTNAME";
			private static final String SUBCODE = "SUBCODE";
			private static final String PARENTMSISDN = "PARENTMSISDN";
			private static final String RECORD = "RECORD";
			private static final String ROLES = "ROLES";
			private static final String ADDRESS = "ADDRESS";
			private static final String USER_MOVEMENT_REQ = "USERMOVEMENTREQ";
			private static final String FROM_USER_ORIGIN_ID = "FROM_USER_ORIGIN_ID";
			private static final String CATEGORYCODE = "CATEGORYCODE";

		}

		private static final class Dms
		{
			private static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String MESSAGE = "message";
			private static final String TYPE = "type";
			private static final String ID = "id";
			private static final String DEALER_ID = "dealerID";
			private static final String RESELLERMSISDN = "resellerMSISDN";
			private static final String RESELLERID = "resellerId";
			private static final String FETCH_ACCOUNT_BAL_INFO = "fetchAccountBalanceInformation";
			private static final String RESELLER_INFO = "resellerInfo";
			private static final String RESELLER_DATA = "resellerData";
			private static final String STATUS = "status";
			private static final String RESELLER_NAME = "resellerName";
			private static final String ROLE_NAME = "roleName";
			private static final String USERS = "users";
			private static final String USER_ID = "userId";
			private static final String PARENT_RESELLER_NAME = "parentResellerName";
			private static final String VALUE = "value";
			private static final String ADDITIONAL_FIELDS = "additionalFields";
			private static final String BLOCKED = "Blocked";
			private static final String FROZEN = "Frozen";
			private static final String PARENTMSISDN = "parentMsisdn";
			private static final String COUNTRY = "country";
			private static final String STATE = "state";
			private static final String CITY = "city";
			private static final String ADDRESS2 = "address2";
			private static final String ADDRESS1 = "address1";
			private static final String SUBSCRIBER_CODE = "subscriberCode";
			private static final String SHORT_NAME = "shortName";
			private static final String EXTERNAL_CODE = "externalCode";
			private static final String Y = "Y";
			private static final String N = "N";
			private static final String PARENT_RESELLER_ID = "PARENTRESELLERID";
			private static final String RESELLER_TYPE_ID = "resellerTypeId";
			private static final String RESELLER_BALANCE = "resellerBalance";
			private static final String BALANCE = "balance";
		}
	}

	private boolean isCallForC2CWithdrawInterceptor(Map<String, Object> additionalFields)
	{
		return ((additionalFields.get(Constants.Gp.TYPE).toString().equalsIgnoreCase(Constants.Gp.EXC2CWDREQ) && additionalFields.containsKey(Constants.Gp.MSISDN2) && additionalFields.containsKey(Constants.Gp.LOGINID2)) ||
				(additionalFields.get(Constants.Gp.TYPE).toString().equalsIgnoreCase(Constants.Gp.WDTHREQ) && additionalFields.containsKey(Constants.Gp.MSISDN1) && additionalFields.containsKey(Constants.Gp.MSISDN2)));

	}

	private boolean isCallForDeregisterInterceptor(Map<String, Object> additionalFields)
	{
		return Objects.nonNull(additionalFields.get(Constants.Gp.DATA)) &&
				Objects.nonNull(((LinkedCaseInsensitiveMap) additionalFields.get(Constants.Gp.DATA)).get(Constants.Gp.USERORIGIN_ID)) &&
				StringUtils.isNotEmpty(((LinkedCaseInsensitiveMap) additionalFields.get(Constants.Gp.DATA)).get(Constants.Gp.USERORIGIN_ID).toString());

	}

	private boolean isCallForChannelUserMovement(Map<String, Object> additionalFields)
	{
		if (Objects.nonNull(additionalFields.get(Constants.Gp.GP_EXT_REQUEST_TYPE))){
			return (additionalFields.get(Constants.Gp.GP_EXT_REQUEST_TYPE).toString().equalsIgnoreCase(Constants.Gp.USER_MOVEMENT_REQ) && additionalFields.containsKey(Constants.Gp.LOGINID));
		}
		return false;

	}
}


