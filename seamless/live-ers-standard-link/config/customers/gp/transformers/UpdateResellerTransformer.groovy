package com.seamless.ers.standardlink.transformers;

import com.fasterxml.jackson.databind.ObjectMapper;
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

@Component("updateResellerTransformer")
public class UpdateResellerTransformer implements ERSIOTransformer
{

	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateResellerTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Override
	public Message<?> transformInboundRequest(Message<?> message) throws TransformerException
	{

		LOGGER.info("Forming DMS view user request for operation " + Constants.OPERATION_ID);
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("UpdateResellerTransformer :: transformInboundRequest");
		try
		{

			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.COMMAND);

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION_ID).getFields();

			LinkedCaseInsensitiveMap<Object> data = (LinkedCaseInsensitiveMap<Object>) command.get(Constants.Gp.DATA);
			LinkedCaseInsensitiveMap<Object> msisdns = (LinkedCaseInsensitiveMap<Object>) data.get(Constants.Gp.MSISDNS);

			String dmsSystemToken = (String) message.getHeaders().get("system-token");
			dmsSystemToken = dmsSystemToken.replaceAll("\"rootComponent\": ?\"[a-zA-Z-]*\"", "\"rootComponent\": \"dms\"");

			Map<String, Object> dealerData = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> resellerData = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> usersValue = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> dealerPrincipal = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> updateRequest = Collections.synchronizedMap(new LinkedHashMap<>());
			List<Map<String, Object>> usersArray = new ArrayList();

			updateRequest.put(Constants.Dms.PARENT_RESELLER_ID, data.get(Constants.Gp.PARENT_ORIGIN_ID));
			updateRequest.put(Constants.Dms.RESELLER_TYPE, data.get(Constants.Gp.USER_CAT_CODE));
//			updateRequest.put(Constants.Dms.NAME, data.get(Constants.Gp.USER_NAME));
			resellerData.put(Constants.Dms.RESELLER_NAME, data.get(Constants.Gp.USER_NAME));
			updateRequest.put(Constants.Dms.RESELLER_MSISDN, msisdns.get(Constants.Gp.MSISDN1));

			List<LinkedCaseInsensitiveMap<Object>> additionalFieldsArray = new ArrayList();
			LinkedCaseInsensitiveMap<Object> additionalFieldsValue = new LinkedCaseInsensitiveMap<Object>();
			LinkedCaseInsensitiveMap<Object> addressMap = new LinkedCaseInsensitiveMap<Object>();

			String resellerId = "";
			if (Objects.nonNull(data.get(Constants.Gp.WEB_LOGIN_ID)) && StringUtils.isNotEmpty(data.get(Constants.Gp.WEB_LOGIN_ID).toString()))
			{
				resellerId = String.valueOf(data.get(Constants.Gp.WEB_LOGIN_ID));
				if (Objects.nonNull(data.get(Constants.Gp.WEB_PASSWORD)) && StringUtils.isNotEmpty(data.get(Constants.Gp.WEB_PASSWORD).toString()))
				{
					usersValue.put(Constants.Dms.PASSWORD, data.get(Constants.Gp.WEB_PASSWORD));
					usersArray.add(usersValue);
				}
			}
			else
			{
				resellerId = String.valueOf(msisdns.get(Constants.Gp.MSISDN1));
				usersValue.put(Constants.Dms.PASSWORD, operationFields.get(Constants.Gp.DEFAULT_PIN));
				usersArray.add(usersValue);
			}

//			We do not need to send our name parameter in additional fields while update
//			additionalFieldsValue = new LinkedCaseInsensitiveMap<Object>();
//			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.NAME);
//			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.USER_NAME));
//			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new LinkedCaseInsensitiveMap<Object>();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.SHORT_NAME);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.SHORT_NAME));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new LinkedCaseInsensitiveMap<Object>();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.USER_NAME_PREFIX);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.USER_NAME_PREFIX));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new LinkedCaseInsensitiveMap<Object>();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.SUBSCRIBER_CODE);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.SUBSCRIBER_CODE));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new LinkedCaseInsensitiveMap<Object>();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.EXTERNAL_CODE);
			additionalFieldsValue.put(Constants.Dms.VALUE, fetchExternalCode(data));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new LinkedCaseInsensitiveMap<Object>();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.CONTACT_PERSON);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.CONTACT_PERSON));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new LinkedCaseInsensitiveMap<Object>();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.CONTACT_NUMBER);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.CONTACT_NUMBER));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new LinkedCaseInsensitiveMap<Object>();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.SSN);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.SSN));
			additionalFieldsArray.add(additionalFieldsValue);

			List<String> addressList = new ArrayList<>();
			if (Objects.nonNull(data.get(Constants.Gp.ADDRESS1)))
			{
				addressList.add(String.valueOf(data.get(Constants.Gp.ADDRESS1)));
			}
			if (Objects.nonNull(data.get(Constants.Dms.ADDRESS_2)))
			{
				addressList.add(String.valueOf(data.get(Constants.Dms.ADDRESS_2)));
			}
			if (!addressList.isEmpty())
			{
				addressMap.put(Constants.Dms.STREET, String.join(",", addressList));
			}

			addressMap.put(Constants.Dms.CITY, data.get(Constants.Gp.CITY));
			addressMap.put(Constants.Dms.COUNTRY, data.get(Constants.Gp.COUNTRY));
			addressMap.put(Constants.Dms.EMAIL, data.get(Constants.Gp.EMAIL_ID));

			additionalFieldsValue = new LinkedCaseInsensitiveMap<Object>();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.ADDRESS_1);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.ADDRESS1));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new LinkedCaseInsensitiveMap<Object>();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.ADDRESS_2);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.ADDRESS2));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new LinkedCaseInsensitiveMap<Object>();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.CITY);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.CITY));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new LinkedCaseInsensitiveMap<Object>();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.STATE);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.STATE));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new LinkedCaseInsensitiveMap<Object>();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.COUNTRY);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.COUNTRY));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new LinkedCaseInsensitiveMap<Object>();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.APPOINTMENT_DATE);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.APPOINTMENT_DATE));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new LinkedCaseInsensitiveMap<Object>();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.OUTLET_TYPE);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.OUTLET_TYPE));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new LinkedCaseInsensitiveMap<Object>();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.SUB_OUTLET_TYPE);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.SUB_OUTLET_TYPE));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new LinkedCaseInsensitiveMap<Object>();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.LBA);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.LBA));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new LinkedCaseInsensitiveMap<Object>();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.MSISDN_2);
			additionalFieldsValue.put(Constants.Dms.VALUE, msisdns.get(Constants.Gp.MSISDN2));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new LinkedCaseInsensitiveMap<Object>();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.MSISDN_3);
			additionalFieldsValue.put(Constants.Dms.VALUE, msisdns.get(Constants.Gp.MSISDN3));
			additionalFieldsArray.add(additionalFieldsValue);

			if (data.get(Constants.Gp.NETWORK_CODE) != null && !StringUtils.isEmpty(data.get(Constants.Gp.NETWORK_CODE).toString()))
			{
				additionalFieldsValue = new LinkedCaseInsensitiveMap<Object>();
				additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.NETWORK_CODE);
				additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.NETWORK_CODE));
				additionalFieldsArray.add(additionalFieldsValue);
			}

			if (data.get(Constants.Gp.GEOGRAPHY_CODE) != null && !StringUtils.isEmpty(data.get(Constants.Gp.GEOGRAPHY_CODE).toString()))
			{
				additionalFieldsValue = new LinkedCaseInsensitiveMap<Object>();
				additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.GEOGRAPHY_CODE);
				additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.GEOGRAPHY_CODE));
				additionalFieldsArray.add(additionalFieldsValue);
			}

			for (Object key : data.keySet())
			{
				String str = key.toString();
				if (str.startsWith(Constants.Gp.INFO))
				{
					additionalFieldsValue = new LinkedCaseInsensitiveMap<Object>();
					additionalFieldsValue.put(Constants.Dms.NAME, key.toString().toLowerCase());
					additionalFieldsValue.put(Constants.Dms.VALUE, data.get(key));
					additionalFieldsArray.add(additionalFieldsValue);
				}
			}

			resellerData.put(Constants.Dms.ADDRESS, addressMap);
			resellerData.put(Constants.Dms.STATUS, operationFields.get(Constants.Gp.resellerStatus));
			resellerData.put(Constants.Dms.RESELLER_ID, resellerId);
			dealerData.put(Constants.Dms.RESELLER_DATA, resellerData);

			dealerData.put(Constants.Dms.USERS, usersArray);
			dealerData.put(Constants.Dms.ADDITIONAL_FIELDS, additionalFieldsArray);

			dealerPrincipal.put(StandardLinkConstants.TXEConstant.TYPE, "USERID");
			dealerPrincipal.put(StandardLinkConstants.TXEConstant.ID, resellerId);

			updateRequest.put(Constants.Dms.DEALER_DATA, dealerData);
			updateRequest.put(Constants.Dms.DEALER_PRINCIPAL, dealerPrincipal);
			ObjectMapper objectMapper=new ObjectMapper();
			LOGGER.info("==============");
			LOGGER.info(objectMapper.writeValueAsString(updateRequest));
			return MessageBuilder.withPayload(updateRequest)
					.setHeader(Constants.Headers.ERROR_CHANNEL, message.getHeaders().get("errorChannel"))
					.setHeader(Constants.Headers.HISTORY, message.getHeaders().get("history"))
					.setHeader(Constants.Headers.PROVIDER_ID, message.getHeaders().get("PROVIDER_ID"))
					.setHeader(Constants.Headers.REPLY_CHANNEL, message.getHeaders().get("replyChannel"))
					.setHeader(Constants.Headers.OPERATION, message.getHeaders().get("OPERATION"))
					.setHeader(Constants.Headers.HOST, message.getHeaders().get("host"))
					.setHeader(Constants.Headers.CACHE_CONTROL, message.getHeaders().get("cache-control"))
					.setHeader(Constants.Headers.ACCEPT_ENCODING, message.getHeaders().get("accept-encoding"))
//					.setHeader("Content-Type","application/json")
					.setHeader(StandardLinkConstants.Headers.AUTHORIZATION, StringUtils.isEmpty(String.valueOf(message.getHeaders()
							.get("authorization"))) ? "" : message.getHeaders().get("authorization"))
					.setHeader("authorization","authorization")
					.setHeader(Constants.Headers.SYSTEM_TOKEN, dmsSystemToken)
					.setHeader(Constants.Headers.REQUEST_PAYLOAD, message.getHeaders().get(Constants.Headers.REQUEST_PAYLOAD))
					.setHeader(Constants.Gp.IS_REQUEST_TYPE_TEXT, requestFields.get(Constants.Gp.IS_REQUEST_TYPE_TEXT)).build();
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

	private Object fetchExternalCode(LinkedCaseInsensitiveMap<Object> data)
	{
		Object externalCode = data.get(Constants.Gp.EXTERNAL_CODE);

		if (externalCode instanceof ArrayList)
		{
			ArrayList<Object> externalCodes = (ArrayList<Object>) externalCode;
			externalCode = externalCodes.get(0);
		}
		Object newExternalCode = data.get(Constants.Gp.NEW_EXTERNAL_CODE);
		if (Objects.nonNull(newExternalCode) && StringUtils.isNotEmpty(newExternalCode.toString()))
		{
			return newExternalCode;
		}
		else
		{
			return externalCode;
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

		EtmPoint point = EtmManager.getEtmMonitor().createPoint("UpdateResellerTransformer :: transformOutboundResponse");

		try
		{
			Map<String, Object> response = new LinkedHashMap<>();
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION_ID).getFields();
			response.put(Constants.Dms.TYPE, operationFields.get(Constants.Dms.RESPONSE_TYPE));
			response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));

			if (outgoingResponse instanceof CompletableFuture)
			{
				outgoingResponse = ((CompletableFuture<?>) outgoingResponse).get();
			}

			if (outgoingResponse instanceof Exception)
			{
				Exception exception = (Exception) outgoingResponse;
				response.put(Constants.Gp.GP_MESSAGE, StandardLinkUtilities.getRootCause(exception));
				response.put(Constants.Gp.GP_TXN_STATUS, StandardLinkResultCodes.INTERNAL_COMPONENT_ERROR);
				return response;
			}

			Map<String, Object> internalResponse = (LinkedHashMap<String, Object>) outgoingResponse;
			Map<String, Object> request = (Map<String, Object>) internalResponse.get(Constants.Gp.REQUEST_PAYLOAD);
			LinkedCaseInsensitiveMap<Object> requestPayloadCommand = (LinkedCaseInsensitiveMap<Object>) request.get(Constants.Gp.COMMAND);
			LinkedCaseInsensitiveMap<Object> data = (LinkedCaseInsensitiveMap<Object>) requestPayloadCommand.get(Constants.Gp.DATA);
			LinkedCaseInsensitiveMap<Object> msisdns = (LinkedCaseInsensitiveMap<Object>) data.get(Constants.Gp.MSISDNS);
			Object externalCode = fetchExternalCode(data);

			if (Objects.nonNull(data.get(Constants.Gp.WEB_LOGIN_ID))
					&& StringUtils.isNotEmpty(data.get(Constants.Gp.WEB_LOGIN_ID).toString()))
			{
				response.put(Constants.Gp.USERID, data.get(Constants.Gp.WEB_LOGIN_ID).toString());
			}
			else if (Objects.nonNull(externalCode)
					&& StringUtils.isNotEmpty(externalCode.toString()))
			{
				response.put(Constants.Gp.USERID, externalCode.toString());
			}
			else
			{
				response.put(Constants.Gp.USERID, msisdns.get(Constants.Gp.MSISDN1).toString());
			}

			if (!internalResponse.isEmpty() && internalResponse.containsKey(Constants.Dms.RESULT_CODE))
			{
				Integer resultCodeValue = (Integer) internalResponse.get(Constants.Dms.RESULT_CODE);
				String defaultMessage = String.valueOf(internalResponse.get(Constants.Dms.RESULT_MESSAGE) != null ? internalResponse.get(Constants.Dms.RESULT_MESSAGE) : internalResponse.get(Constants.Dms.MESSAGE) != null ? internalResponse.get(Constants.Dms.MESSAGE) : internalResponse.get(Constants.Dms.RESULT_DESCRIPTION));
				if (resultCodeValue == StandardLinkResultCodes.SUCCESS)
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION_ID, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
					response.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
					response.put(Constants.Gp.EXTERNAL_CODE, Objects.nonNull(externalCode) ? String.valueOf(externalCode) : "");
				}
				else
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION_ID, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
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

	private static final class Constants
	{
		public static final String PROVIDER = "gp";
		public static final String OPERATION_ID = "updateReseller";

		private static final class Headers
		{
			private static final String SYSTEM_TOKEN = "system-token";
			private static final String AUTHORIZATION = "authorization";
			private static final String PROVIDER_ID = "PROVIDER_ID";
			private static final String HISTORY = "history";
			private static final String ERROR_CHANNEL = "errorChannel";
			private static final String REPLY_CHANNEL = "replyChannel";
			private static final String OPERATION = "OPERATION";
			private static final String HOST = "host";
			private static final String CACHE_CONTROL = "cache-control";
			private static final String ACCEPT_ENCODING = "accept-encoding";
			public static final String REQUEST_PAYLOAD = "requestPayload";
		}

		private static final class Gp
		{

			private static final String DATA = "DATA";
			private static final String DATE_FORMAT = "dd/MM/YYYY hh:mm:ss";
			private static final String MSISDNS = "MSISDNS";
			private static final String PARENT_ORIGIN_ID = "PARENTORIGINID";
			private static final String COMMAND = "COMMAND";
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String GP_TXN_STATUS = "TXNSTATUS";
			private static final String REQUEST_PAYLOAD = "requestPayload";
			private static final String MSISDN1 = "MSISDN1";
			private static final String WEB_LOGIN_ID = "WEBLOGINID";
			private static final String WEB_PASSWORD = "WEBPASSWORD";
			private static final String USER_CAT_CODE = "USERCATCODE";
			private static final String USER_NAME = "USERNAME";
			private static final String DEFAULT_PIN = "defaultPin";
			private static final String SHORT_NAME = "SHORTNAME";
			private static final String USER_NAME_PREFIX = "USERNAMEPREFIX";
			private static final String SUBSCRIBER_CODE = "SUBSCRIBERCODE";
			private static final String EXTERNAL_CODE = "EXTERNALCODE";
			private static final String NEW_EXTERNAL_CODE = "NEWEXTERNALCODE";
			private static final String CONTACT_PERSON = "CONTACTPERSON";
			private static final String CONTACT_NUMBER = "CONTACTNUMBER";
			private static final String SSN = "SSN";
			private static final String ADDRESS1 = "ADDRESS1";
			private static final String ADDRESS2 = "ADDRESS2";
			private static final String CITY = "CITY";
			private static final String COUNTRY = "COUNTRY";
			private static final String EMAIL_ID = "EMAILID";
			private static final String STATE = "STATE";
			private static final String APPOINTMENT_DATE = "APPOINTMENTDATE";
			private static final String OUTLET_TYPE = "OUTLETTYPE";
			private static final String SUB_OUTLET_TYPE = "SUBOUTLETTYPE";
			private static final String LBA = "LBA";
			private static final String MSISDN2 = "MSISDN2";
			private static final String MSISDN3 = "MSISDN3";
			private static final String resellerStatus = "resellerStatus";
			private static final String authorization = "authorization";
			private static final String INFO = "INFO";
			private static final String NETWORK_CODE = "NETWORKCODE";
			private static final String GEOGRAPHY_CODE = "GEOGRAPHYCODE";
			private static final String IS_REQUEST_TYPE_TEXT = "IS_REQUEST_TYPE_TEXT";
			private static final String USERID="USERID";
		}

		private static final class Dms
		{
			private static final String USERNAME = "userId";
			private static final String TYPE = "TYPE";
			private static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String PARENT_RESELLER_ID = "parentResellerId";
			private static final String RESELLER_TYPE = "resellerType";
			private static final String RESELLER_ID = "resellerId";
			private static final String NAME = "name";
			private static final String RESELLER_NAME = "resellerName";
			private static final String VALUE = "value";
			private static final String RESELLER_MSISDN = "resellerMSISDN";
			private static final String RESULT_CODE = "resultCode";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String MESSAGE = "message";
			private static final String PASSWORD = "password";
			private static final String SHORT_NAME = "shortName";
			private static final String USER_NAME_PREFIX = "userNamePrefix";
			private static final String SUBSCRIBER_CODE = "subscriberCode";
			private static final String EXTERNAL_CODE = "externalCode";
			private static final String CONTACT_PERSON = "contactPerson";
			private static final String CONTACT_NUMBER = "contactNumber";
			private static final String SSN = "ssn";
			private static final String ADDRESS_1 = "ADDRESS1";
			private static final String ADDRESS_2 = "ADDRESS2";
			private static final String CITY = "city";
			private static final String STATE = "state";
			private static final String STREET = "street";
			private static final String COUNTRY = "country";
			private static final String EMAIL = "email";
			private static final String APPOINTMENT_DATE = "appointmentDate";
			private static final String OUTLET_TYPE = "outletType";
			private static final String SUB_OUTLET_TYPE = "subOutletType";
			private static final String LBA = "lba";
			private static final String MSISDN_2 = "msisdn2";
			private static final String MSISDN_3 = "msisdn3";
			private static final String ADDRESS = "address";
			private static final String STATUS = "status";
			private static final String RESELLER_DATA = "resellerData";
			private static final String DEALER_DATA = "dealerData";
			private static final String DEALER_PRINCIPAL = "dealerPrincipal";
			private static final String USERS = "users";
			private static final String ADDITIONAL_FIELDS = "additionalFields";
			private static final String USER_ID = "userId";
			private static final String NETWORK_CODE = "networkCode";
			private static final String GEOGRAPHY_CODE = "geographyCode";
		}
	}
}
