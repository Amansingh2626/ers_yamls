package com.seamless.ers.standardlink.transformers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seamless.common.StringUtils;
import com.seamless.ers.standardlink.config.StandardLinkConfig;
import com.seamless.ers.standardlink.model.common.ERSIOTransformer;
import com.seamless.ers.standardlink.model.common.ResultCode;
import com.seamless.ers.standardlink.model.common.constants.StandardLinkConstants;
import com.seamless.ers.standardlink.model.configs.ProviderConfigurations;
import com.seamless.ers.standardlink.model.customer.gp.addReseller.Fields;
import com.seamless.ers.standardlink.model.customer.gp.addReseller.Parameters;
import com.seamless.ers.standardlink.model.customer.gp.addReseller.UsersValue;
import com.seamless.ers.standardlink.model.exception.TransformerException;
import com.seamless.ers.standardlink.model.internal.StandardLinkResultCodes;
import com.seamless.ers.standardlink.utilities.DmsUtility;
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
public class AddResellerTransformer implements ERSIOTransformer
{

	private static final Logger LOGGER = LoggerFactory.getLogger(AddResellerTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Autowired
	private DmsUtility dmsUtility;

	@Override
	public Message<?> transformInboundRequest(Message<?> message) throws TransformerException
	{

		EtmPoint point = EtmManager.getEtmMonitor().createPoint("AddResellerTransformer :: transformOutboundRequest");

		try
		{
			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			Map<String, Object> command = (Map<String, Object>) requestFields.get(Constants.Gp.COMMAND);
			List regionInfoList = (List) requestFields.get(Constants.Dms.REGION_INFO);

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION_ID).getFields();

			LinkedCaseInsensitiveMap<Object> data = (LinkedCaseInsensitiveMap<Object>) command.get(Constants.Gp.DATA);
			LinkedCaseInsensitiveMap<Object> msisdns = (LinkedCaseInsensitiveMap<Object>) data.get(Constants.Gp.MSISDNS);

			String dmsSystemToken = (String) message.getHeaders().get("system-token");
			dmsSystemToken = dmsSystemToken.replaceAll("\"rootComponent\": ?\"[a-zA-Z-]*\"", "\"rootComponent\": \"dms\"");

			Map<String, Object> createRequest = new LinkedHashMap<>();
			List usersArray = new ArrayList();

			UsersValue usersValue = new UsersValue();
			Fields fields = new Fields();
			Parameters parameters = new Parameters();

			createRequest.put(Constants.Dms.PARENT_RESELLER_ID, data.get(Constants.Gp.PARENT_ORIGIN_ID));
			createRequest.put(Constants.Dms.RESELLER_TYPE, data.get(Constants.Gp.USER_CAT_CODE));
			createRequest.put(Constants.Dms.NAME, data.get(Constants.Gp.USER_NAME));
			createRequest.put(Constants.Dms.RESELLER_MSISDN, msisdns.get(Constants.Gp.MSISDN1));

			List additionalFieldsArray = new ArrayList();
			Map additionalFieldsValue = new HashMap();
			HashMap addressMap = new HashMap();

			if (Objects.nonNull(data.get(Constants.Gp.WEB_LOGIN_ID)) && StringUtils.isNotEmpty(data.get(Constants.Gp.WEB_LOGIN_ID).toString()))
			{
				//createRequest.put(Constants.Dms.RESELLER_ID, data.get(Constants.Gp.WEB_LOGIN_ID));
				createRequest.put(Constants.Dms.RESELLER_ID, null);
				if (Objects.nonNull(data.get(Constants.Gp.WEB_PASSWORD)) && StringUtils.isNotEmpty(data.get(Constants.Gp.WEB_PASSWORD).toString()))
				{
					usersValue.setPassword(String.valueOf(data.get(Constants.Gp.WEB_PASSWORD)));
					usersValue.setUserId((String) data.get(Constants.Gp.WEB_LOGIN_ID));
					usersValue.setRoleId((String) operationFields.get(Constants.Dms.ROLEID));
					parameters.setEmail((String) data.get(Constants.Gp.EMAIL_ID));
					parameters.setMSISDN(String.valueOf(msisdns.get(Constants.Gp.MSISDN1)));
					fields.setParameters(parameters);
					usersValue.setFields(fields);
					usersArray.add(usersValue);

					additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.USER_ORIGIN_ID);
					additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.WEB_LOGIN_ID));
					additionalFieldsArray.add(additionalFieldsValue);
				}

			}
			else
			{
				//createRequest.put(Constants.Dms.RESELLER_ID, msisdns.get(Constants.Gp.MSISDN1));
				createRequest.put(Constants.Dms.RESELLER_ID, null);
				usersValue.setPassword((String) operationFields.get(Constants.Gp.DEFAULT_PIN));
				usersArray.add(usersValue);
				additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.USER_ORIGIN_ID);
				additionalFieldsValue.put(Constants.Dms.VALUE, msisdns.get(Constants.Gp.MSISDN1));
				additionalFieldsArray.add(additionalFieldsValue);

			}

			if (usersArray.size() <= 0)
			{
				usersArray.add(new HashMap());
			}

//			We do not need to send the name field again in additional fields
//			additionalFieldsValue = new HashMap();
//			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.NAME);
//			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.USER_NAME));
//			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.NETWORK_CODE);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.NETWORK_CODE));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.GEOGRAPHY_CODE);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.GEOGRAPHY_CODE));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.PARENT_MSISDN);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.PARENT_MSISDN));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.PARENT_EXTERNAL_CODE);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.PARENT_EXTERNAL_CODE));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.SHORT_NAME);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.SHORT_NAME));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.USER_NAME_PREFIX);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.USER_NAME_PREFIX));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.SUBSCRIBER_CODE);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.SUBSCRIBER_CODE));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.EXTERNAL_CODE);
			Object externalCode = data.get(Constants.Gp.EXTERNAL_CODE);
			if (externalCode instanceof ArrayList)
			{
				ArrayList<String> externalCodes = (ArrayList<String>) externalCode;
				additionalFieldsValue.put(Constants.Dms.VALUE, String.valueOf(externalCodes.get(0)));
			}
			else
			{
				additionalFieldsValue.put(Constants.Dms.VALUE, externalCode);
			}
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.CONTACT_PERSON);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.CONTACT_PERSON));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.CONTACT_NUMBER);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.CONTACT_NUMBER));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.SSN);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.SSN));
			additionalFieldsArray.add(additionalFieldsValue);

			if (requestFields.containsKey(Constants.Dms.REGION_INFO))
			{
				additionalFieldsArray.addAll((ArrayList) requestFields.get(Constants.Dms.REGION_INFO));
			}

			List<String> addressList = new ArrayList<>();
			if (Objects.nonNull(data.get(Constants.Gp.ADDRESS1)))
			{
				addressList.add(String.valueOf(data.get(Constants.Gp.ADDRESS1)));
			}
			if (Objects.nonNull(data.get(Constants.Gp.ADDRESS2)))
			{
				addressList.add(String.valueOf(data.get(Constants.Gp.ADDRESS2)));
			}

			if (!addressList.isEmpty() && addressList.size() > 0)
			{
				addressMap.put(Constants.Dms.STREET, String.join(",", addressList));
			}

			addressMap.put(Constants.Dms.CITY, data.get(Constants.Gp.CITY));
			addressMap.put(Constants.Dms.COUNTRY, data.get(Constants.Gp.COUNTRY));
			addressMap.put(Constants.Dms.EMAIL, data.get(Constants.Gp.EMAIL_ID));

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.ADDRESS_1);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.ADDRESS1));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.ADDRESS_2);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.ADDRESS2));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.CITY);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.CITY));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.STATE);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.STATE));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.COUNTRY);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.COUNTRY));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.APPOINTMENT_DATE);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.APPOINTMENT_DATE));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.OUTLET_TYPE);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.OUTLET_TYPE));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.SUB_OUTLET_TYPE);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.SUB_OUTLET_TYPE));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.LBA);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Gp.LBA));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.PARENT_RESELLER_TYPE);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Dms.PARENT_CATEGORY_NAME));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.OWNER_CATEGORY);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Dms.OWNER_CATEGORY));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.OWNER_NAME);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Dms.OWNER_NAME));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.OWNER_MSISDN);
			additionalFieldsValue.put(Constants.Dms.VALUE, data.get(Constants.Dms.OWNER_MSISDN));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.MSISDN_2);
			additionalFieldsValue.put(Constants.Dms.VALUE, msisdns.get(Constants.Gp.MSISDN2));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.MSISDN_3);
			additionalFieldsValue.put(Constants.Dms.VALUE, msisdns.get(Constants.Gp.MSISDN3));
			additionalFieldsArray.add(additionalFieldsValue);

			Map<String, Object> domainInfoMap = dmsUtility.fetchDomainDetailsForResellerType(message.getHeaders(), createRequest.get("resellerType"));
			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.DOMAIN_CODE);
			additionalFieldsValue.put(Constants.Dms.VALUE, domainInfoMap.get(Constants.Gp.DOMAIN_CODE));
			additionalFieldsArray.add(additionalFieldsValue);

			additionalFieldsValue = new HashMap();
			additionalFieldsValue.put(Constants.Dms.NAME, Constants.Dms.DOMAIN_NAME);
			additionalFieldsValue.put(Constants.Dms.VALUE, domainInfoMap.get(Constants.Gp.DOMAIN_NAME));
			additionalFieldsArray.add(additionalFieldsValue);

			for (Object key : data.keySet())
			{
				String str = key.toString();
				if (str.startsWith(Constants.Gp.INFO))
				{
					additionalFieldsValue = new HashMap();
					additionalFieldsValue.put(Constants.Dms.NAME, key.toString().toLowerCase());
					additionalFieldsValue.put(Constants.Dms.VALUE, data.get(key));
					additionalFieldsArray.add(additionalFieldsValue);
				}
			}

			for (Object regionList : regionInfoList) {
				additionalFieldsValue = new HashMap();
				additionalFieldsValue.putAll((LinkedHashMap)regionList);
				additionalFieldsArray.add(additionalFieldsValue);
			}

			ObjectMapper objectMapper = new ObjectMapper();
			String additionalFieldsRequest = objectMapper.writeValueAsString(additionalFieldsArray);
			createRequest.put(Constants.Dms.ADDITIONAL_FIELDS, additionalFieldsRequest);
			createRequest.put(Constants.Dms.ADDRESS, objectMapper.writeValueAsString(addressMap));
			createRequest.put(Constants.Dms.STATUS, operationFields.get(Constants.Gp.resellerStatus));
			createRequest.put(Constants.Dms.USERS, objectMapper.writeValueAsString(usersArray));

			return MessageBuilder.withPayload(createRequest).copyHeaders(message.getHeaders())
					.setHeader("errorChannel", message.getHeaders().get("errorChannel"))
					.setHeader("history", message.getHeaders().get("history"))
					.setHeader("PROVIDER_ID", message.getHeaders().get("PROVIDER_ID"))
					.setHeader("replyChannel", message.getHeaders().get("replyChannel"))
					.setHeader("OPERATION", message.getHeaders().get("OPERATION"))
					.setHeader("host", message.getHeaders().get("host"))
					.setHeader("cache-control", message.getHeaders().get("cache-control"))
					.setHeader("accept-encoding", message.getHeaders().get("accept-encoding"))
					.setHeader(StandardLinkConstants.Headers.AUTHORIZATION, StringUtils.isEmpty(String.valueOf(message.getHeaders().get("authorization"))) ? "" : message.getHeaders().get("authorization"))
					.setHeader(StandardLinkConstants.Headers.SYSTEM_TOKEN, dmsSystemToken)
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
	 * @param outgoingResponse
	 * @return
	 * @throws TransformerException
	 */
	@Override
	public Object transformOutboundResponse(Object outgoingResponse) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("AddResellerTransformer :: transformOutboundResponse");
		try
		{
			Map<String, Object> response = new LinkedHashMap<>();
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION_ID).getFields();

			response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));
			response.put(Constants.Gp.GP_EXT_REQUEST_TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));

			if (outgoingResponse instanceof CompletableFuture)
			{
				outgoingResponse = ((CompletableFuture<?>) outgoingResponse).get();
			}

			if (outgoingResponse instanceof Exception)
			{
				Exception exception = (Exception) outgoingResponse;
				response.put(Constants.Gp.MESSAGE, StandardLinkUtilities.getRootCause(exception));
				response.put(Constants.Gp.TXNSTATUS, StandardLinkResultCodes.INTERNAL_COMPONENT_ERROR);
				return response;
			}

			Map<String, Object> internalResponse = (LinkedHashMap<String, Object>) outgoingResponse;
			Map<String, Object> request = (Map<String, Object>) internalResponse.get(Constants.Gp.REQUEST_PAYLOAD);
			Map<String, Object> resultDetailsMap = (Map<String, Object>) internalResponse.get(Constants.Dms.RESULT_DETAILS);
			Map<String, Object> requestPayloadCommand = (Map<String, Object>) request.get(Constants.Gp.COMMAND);

			if (!internalResponse.isEmpty() && internalResponse.containsKey(Constants.Dms.RESULT_CODE))
			{
				Integer resultCodeValue = (Integer) internalResponse.get(Constants.Dms.RESULT_CODE);
				String defaultMessage = String.valueOf(internalResponse.get(Constants.Dms.RESULT_MESSAGE) != null ? internalResponse.get(Constants.Dms.RESULT_MESSAGE) : internalResponse.get(Constants.Dms.MESSAGE) != null ? internalResponse.get(Constants.Dms.MESSAGE) : internalResponse.get(Constants.Dms.RESULT_DESCRIPTION));

				if (resultCodeValue == StandardLinkResultCodes.SUCCESS)
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION_ID, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);

					LinkedCaseInsensitiveMap data = (LinkedCaseInsensitiveMap) requestPayloadCommand.get(Constants.Gp.DATA);
					LinkedCaseInsensitiveMap msisdns = (LinkedCaseInsensitiveMap) data.get(Constants.Gp.MSISDNS);

					if (Objects.nonNull(data.get(Constants.Gp.WEB_LOGIN_ID)) && StringUtils.isNotEmpty(data.get(Constants.Gp.WEB_LOGIN_ID).toString()))
					{
						response.put(Constants.Gp.USER_ID, resultDetailsMap.get("reseller_id"));
					}
					response.put(Constants.Gp.MESSAGE, resultCode.getDescription());
					response.put(Constants.Gp.TXNSTATUS, resultCode.getInternalResultCode());
					response.put(Constants.Gp.EXTERNAL_CODE, data.get(Constants.Gp.EXTERNAL_CODE).toString());
					response.put(Constants.Gp.MSISDN, msisdns.get(Constants.Gp.MSISDN1).toString());
				}
				else
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION_ID, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Gp.MESSAGE, resultCode.getDescription());
					response.put(Constants.Gp.TXNSTATUS, resultCode.getInternalResultCode());
				}
			}
			else
			{
				response.put(Constants.Gp.MESSAGE, "Request has failed on server.");
				response.put(Constants.Gp.TXNSTATUS, StandardLinkResultCodes.FAILED_ON_SERVER);
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
		private static final String PROVIDER = "gp";
		private static final String OPERATION_ID = "addReseller";

		private static final class Gp
		{
			private static final String DOMAIN_NAME = "domainName";
			private static final String DOMAIN_CODE = "domainCode";
			private static final String DATA = "DATA";
			private static final String DATE_FORMAT = "dd/MM/YYYY hh:mm:ss";
			private static final String MSISDNS = "MSISDNS";
			private static final String MSISDN = "MSISDN";
			private static final String PARENT_ORIGIN_ID = "PARENTORIGINID";
			private static final String COMMAND = "COMMAND";
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String MESSAGE = "MESSAGE";
			private static final String TXNSTATUS = "TXNSTATUS";
			private static final String REQUEST_PAYLOAD = "requestPayload";
			private static final String MSISDN1 = "MSISDN1";
			private static final String WEB_LOGIN_ID = "WEBLOGINID";
			private static final String WEB_PASSWORD = "WEBPASSWORD";
			private static final String USER_CAT_CODE = "USERCATCODE";
			private static final String USER_NAME = "USERNAME";
			private static final String DEFAULT_PIN = "defaultPin";
			private static final String NETWORK_CODE = "NETWORKCODE";
			private static final String GEOGRAPHY_CODE = "GEOGRAPHYCODE";
			private static final String PARENT_MSISDN = "PARENTMSISDN";
			private static final String PARENT_EXTERNAL_CODE = "PARENTEXTERNALCODE";
			private static final String SHORT_NAME = "SHORTNAME";
			private static final String USER_NAME_PREFIX = "USERNAMEPREFIX";
			private static final String SUBSCRIBER_CODE = "SUBSCRIBERCODE";
			private static final String EXTERNAL_CODE = "EXTERNALCODE";
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
			private static final String INFO = "INFO";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String GP_EXT_REQUEST_TYPE = "TYPE";
			private static final String USER_ID = "USERID";
		}

		private static final class Dms
		{
			private static final String ROLEID = "roleId";
			private static final Object DOMAIN_NAME = "DomainName";
			private static final Object DOMAIN_CODE = "DomainCode";
			private static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String PARENT_RESELLER_ID = "parentResellerId";
			private static final String RESELLER_TYPE = "resellerType";
			private static final String RESELLER_ID = "resellerId";
			private static final String NAME = "name";
			private static final String VALUE = "value";
			private static final String RESELLER_MSISDN = "resellerMSISDN";
			private static final String RESULT_CODE = "resultCode";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String MESSAGE = "message";
			private static final String PASSWORD = "password";
			private static final String USER_ORIGIN_ID = "userOriginId";
			private static final String NETWORK_CODE = "networkCode";
			private static final String GEOGRAPHY_CODE = "geographyCode";
			private static final String PARENT_MSISDN = "parentMsisdn";
			private static final String PARENT_EXTERNAL_CODE = "parentExternalCode";
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
			private static final String STREET = "street";
			private static final String USERS = "users";
			private static final String ADDITIONAL_FIELDS = "additionalFields";
			private static final String PARENT_RESELLER_TYPE = "parentResellerType";
			private static final String PARENT_CATEGORY_NAME = "parentCategoryName";
			private static final String OWNER_CATEGORY = "ownerCategory";
			private static final String OWNER_MSISDN = "ownerMsisdn";
			private static final String OWNER_NAME = "ownerName";
			private static final String REGION_INFO = "regionInfo";
			private static final String RESULT_DETAILS = "resultDetails";

		}
	}
}
