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
public class FetchRegionInfoInternalTransformer implements ERSIOTransformer
{

	private static final Logger LOGGER = LoggerFactory.getLogger(FetchRegionInfoInternalTransformer.class);
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

		EtmPoint point = EtmManager.getEtmMonitor().createPoint("FetchRegionInfoInternalTransformer :: transformOutboundRequest");
		Map<String, Object> viewRegionRequest = new HashMap<>();
		try
		{
			LOGGER.info("Forming Region Info request for operation " + Constants.OPERATION_ID);
            LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.COMMAND);
			LinkedCaseInsensitiveMap<Object> commandData = (LinkedCaseInsensitiveMap<Object>) command.get(Constants.Gp.DATA);
			Boolean isInternalCall = (Boolean) message.getHeaders().getOrDefault("IS_INTERNAL_CALL", false);

			if (isInternalCall){
				if ((command.containsKey(FetchRegionInfoInternalTransformer.Constants.Gp.GP_EXT_REQUEST_TYPE) && command.get(FetchRegionInfoInternalTransformer.Constants.Gp.GP_EXT_REQUEST_TYPE).toString().equalsIgnoreCase(FetchRegionInfoInternalTransformer.Constants.Gp.USER_MOVEMENT_REQ)) &&
						((StringUtils.isNotEmpty((String) command.get(FetchRegionInfoInternalTransformer.Constants.Dms.TO_USER_GEOGRAPHICAL_CODE))) && (StringUtils.isNotBlank((String) command.get(FetchRegionInfoInternalTransformer.Constants.Dms.TO_USER_GEOGRAPHICAL_CODE)))))
				{
					viewRegionRequest.put(Constants.Dms.GEOGRAPHY_CODE, command.get(Constants.Dms.TO_USER_GEOGRAPHICAL_CODE));
				}
				else if (!command.isEmpty() && Objects.nonNull(commandData) && commandData.containsKey(Constants.Dms.GEOGRAPHY_CODE) &&  StringUtils.isNotEmpty(String.valueOf(commandData.get(Constants.Dms.GEOGRAPHY_CODE))))
				{
					viewRegionRequest.put(Constants.Dms.GEOGRAPHY_CODE, commandData.get(Constants.Dms.GEOGRAPHY_CODE));
				}
			}else{
				viewRegionRequest.put(Constants.Dms.GEOGRAPHY_CODE, command.get(Constants.Dms.GEOGRAPHY_CODE));
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
		return MessageBuilder.withPayload(viewRegionRequest).copyHeaders(message.getHeaders()).setHeader(StandardLinkConstants.Headers.AUTHORIZATION, Constants.AUTHORIZATION).setHeaderIfAbsent(StandardLinkConstants.Headers.SYSTEM_TOKEN, message.getHeaders().get(Constants.SYSTEM_TOKEN)).build();
	}

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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("FetchRegionInfoInternalTransformer :: transformOutboundResponse");
		try
		{
			Map<String, Object> response = new LinkedCaseInsensitiveMap<>();
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.OPERATION_ID).getFields();

			response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));
			response.put(Constants.Gp.TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));

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
			Map<String, Object> requestPayloadCommand = (Map<String, Object>) request.get(Constants.Gp.COMMAND);
			Map<String, Object> requestPayloadData = (Map<String, Object>) requestPayloadCommand.get(Constants.Gp.DATA);
			Map<String, Object> regionField = new HashMap<>();
			Object regionDepth = null;

			if (requestPayloadCommand.containsKey(Constants.Gp.GP_EXT_REQUEST_TYPE) && requestPayloadCommand.get(Constants.Gp.GP_EXT_REQUEST_TYPE).toString().equalsIgnoreCase(Constants.Gp.USER_MOVEMENT_REQ))
			{
				regionDepth = operationFields.get(requestPayloadCommand.get(Constants.Dms.TO_USER_CATEGORY_CODE).toString().toUpperCase());
			}
			else
			{
				regionDepth = operationFields.get(requestPayloadData.get(Constants.Dms.USER_CAT_CODE));
			}

			if (!internalResponse.isEmpty() && Objects.nonNull(regionDepth) && Objects.nonNull(internalResponse.get(Constants.Dms.DATA)) && !StringUtils.isEmpty(String.valueOf(internalResponse.get(Constants.Dms.DATA))) && internalResponse.containsKey(Constants.Dms.RESULT_CODE))
			{
				Integer resultCodeValue = (Integer) internalResponse.get(Constants.Dms.RESULT_CODE);
				String defaultMessage = String.valueOf(internalResponse.get(Constants.Dms.RESULT_MESSAGE) != null ? internalResponse.get(Constants.Dms.RESULT_MESSAGE) : internalResponse.get(Constants.Dms.MESSAGE) != null ? internalResponse.get(Constants.Dms.MESSAGE) : internalResponse.get(Constants.Dms.RESULT_DESCRIPTION));

				if (resultCodeValue == StandardLinkResultCodes.SUCCESS)
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION_ID, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					ArrayList<Map<String, String>> regionInfoList = (ArrayList) internalResponse.get(Constants.Dms.DATA);
					regionField.put(Constants.Dms.REGION_RESPONSE, regionInfoList);
					String[] listOfRegion = String.valueOf(regionDepth).split(",");
					Map<String, Object> regionAdditionalFields = new HashMap<>();
					List<Map<String, Object>> additionalFieldsArray = new ArrayList<>();

					for (String region : listOfRegion)
					{
						for (Map<String, String> regionMap : regionInfoList)
						{
							if (regionMap.get(Constants.Dms.REGION_TYPE).equalsIgnoreCase(region))
							{
								regionAdditionalFields = new LinkedHashMap<>();
								regionAdditionalFields.put(Constants.Dms.NAME, region.toLowerCase(Locale.ROOT) + Constants.Dms.CODE);
								regionAdditionalFields.put(Constants.Dms.VALUE, regionMap.get(Constants.Dms.REGION_NAME));
								additionalFieldsArray.add(regionAdditionalFields);

								regionAdditionalFields = new LinkedHashMap<>();
								regionAdditionalFields.put(Constants.Dms.NAME, region.toLowerCase(Locale.ROOT) + Constants.Dms.PATH);
								regionAdditionalFields.put(Constants.Dms.VALUE, regionMap.get(Constants.Dms.PATH_LOWER_CASE));
								additionalFieldsArray.add(regionAdditionalFields);

								regionAdditionalFields = new LinkedHashMap<>();
								regionAdditionalFields.put(Constants.Dms.NAME, region.toLowerCase(Locale.ROOT) + Constants.Dms.NAME_POSTFIX);
								regionAdditionalFields.put(Constants.Dms.VALUE, regionMap.get(Constants.Dms.DATA));
								additionalFieldsArray.add(regionAdditionalFields);
							}
						}
					}
					response.put(Constants.Dms.REGION_INFO, additionalFieldsArray);
					response.put(Constants.Gp.RESULT_DESCRIPTION, resultCode.getDescription());
					response.put(Constants.Gp.RESULTCODE, resultCode.getInternalResultCode());
				}
				else
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.PROVIDER, Constants.OPERATION_ID, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
					response.put(Constants.Gp.ERROR_KEY, resultCode.getInternalResultCode());
				}
			}
			else
			{
				response.put(Constants.Gp.GP_MESSAGE, internalResponse.get("resultMessage"));
				response.put(Constants.Gp.ERROR_KEY, internalResponse.get("resultCode"));
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
		private static final String OPERATION_ID = "fetchRegionInfoInternal";
		private static final String SYSTEM_TOKEN = "system-token";
		private static final String AUTHORIZATION = "authorization";

		private static final class Gp
		{
			private static final String PARENTORIGINID = "PARENTORIGINID";
			private static final String PARENTMSISDN = "PARENTMSISDN";
			private static final String LOGINID2 = "LOGINID2";
			private static final String MSISDN1 = "MSISDN1";
			private static final String MSISDN2 = "MSISDN2";
			private static final String RESULTCODE = "resultCode";
			private static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String DATA = "DATA";
			private static final String DATE_FORMAT = "dd/MM/YYYY hh:mm:ss";
			private static final String COMMAND = "COMMAND";
			private static final String GP_EXT_REQUEST_TYPE = "TYPE";
			private static final String GP_EXT_REQUEST_DATE = "externalRequestDate";
			private static final String RESPONSE_TYPE = "responseType";
			private static final String TYPE = "TYPE";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String GP_TXN_STATUS = "TXNSTATUS";
			private static final String ERROR_KEY = "ERRORKEY";
			private static final String REQUEST_PAYLOAD = "requestPayload";
			private static final String USER_MOVEMENT_REQ = "USERMOVEMENTREQ";
		}

		private static final class Dms
		{
			private static final String TO_USER_CATEGORY_CODE = "TO_USER_CATEGORY_CODE";
			private static final String GEOGRAPHY_CODE = "GEOGRAPHYCODE";
			private static final String TO_USER_GEOGRAPHICAL_CODE = "TO_USER_GEOGRAPHICAL_CODE";
			private static final String RESULT_CODE = "resultCode";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String MESSAGE = "message";
			private static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String VALUE = "value";
			private static final String NAME = "name";
			private static final String POS = "POS";
			private static final String USER_CAT_CODE = "USERCATCODE";
			private static final String REGION_RESPONSE = "regionResponse";
			private static final String REGION_NAME = "regionName";
			private static final String REGION_TYPE = "regionType";
			private static final String REGION_INFO = "regionInfo";
			private static final String CODE = "Code";
			private static final String PATH = "Path";
			private static final String PATH_LOWER_CASE = "path";
			private static final String NAME_POSTFIX = "Name";
			private static final String DATA = "data";
		}
	}
}


