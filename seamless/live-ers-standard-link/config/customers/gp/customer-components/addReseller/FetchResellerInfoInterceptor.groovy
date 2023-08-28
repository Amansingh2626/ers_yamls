/*
 * Created Date : 15/08/2021
 * Time         : 2:57 PM
 *
 * @author 		: Bilal Mirza <bilal.mirza@seamless.se>
 * Purpose      : Fetches authentication token either from server or cache and adds it to request
 * <p>
 * Copyright(c) 2021. Seamless Distribution Systems AB - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited. It is proprietary and confidential.
 */
package com.seamless.ers.standardlink.interceptors.addReseller;

import com.seamless.common.StringUtils;
import com.seamless.ers.standardlink.config.StandardLinkConfig;
import com.seamless.ers.standardlink.processors.InterceptedRequestProcessor;
import com.seamless.ers.standardlink.model.common.constants.StandardLinkConstants;
import com.seamless.ers.standardlink.model.configs.ProviderConfigurations;
import com.seamless.ers.standardlink.model.exception.ClientException;
import com.seamless.ers.standardlink.model.exception.ServerException;
import com.seamless.ers.standardlink.model.internal.StandardLinkResultCodes;
import com.seamless.ers.standardlink.utilities.StandardLinkUtilities;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component("fetchResellerInfoInterceptor")
public class FetchResellerInfoInterceptor implements ChannelInterceptor
{
	private static final Logger LOGGER = LoggerFactory.getLogger(FetchResellerInfoInterceptor.class);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Autowired
	@Qualifier("fetchResellerInfoProcessor")
	private InterceptedRequestProcessor fetchResellerInfoProcessor;

	@Autowired
	@Qualifier("regionInfoProcessor")
	private InterceptedRequestProcessor regionInfoProcessor;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel)
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("FetchResellerInterceptor :: preSend");
		try
		{
			LOGGER.info("Fetching reseller info ...");
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.ADD_RESELLER).getFields();

			LinkedCaseInsensitiveMap<Object> requestFields = StandardLinkUtilities.convertToCaseInsensitiveMap((LinkedCaseInsensitiveMap<Object>) message.getPayload());
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.COMMAND);
			LinkedCaseInsensitiveMap<Object> data = (LinkedCaseInsensitiveMap<Object>) command.get(Constants.DATA);
			LinkedCaseInsensitiveMap<Object> msisdns = (LinkedCaseInsensitiveMap<Object>) data.get(Constants.MSISDNS);

			String resellerType = String.valueOf(data.get(Constants.USER_CAT_CODE));
			String resellerTypes = String.valueOf(operationFields.getOrDefault(Constants.CATEGORY_TYPES, ""));
			List<String> resellerList = Arrays.asList(resellerTypes.split(","));

			if (resellerList.contains(resellerType))
			{
				LOGGER.info("Reseller type present in reseller list " + resellerList);
				data.replace(Constants.PARENT_ORIGIN_ID, operationFields.get(Constants.DEFAULT_PARENT_ORIGIN_ID));
				data.replace(Constants.PARENT_MSISDN, operationFields.get(Constants.DEFAULT_PARENT_ORIGIN_MSISDN));
				command.put(Constants.PARENT_CATEGORY_NAME, operationFields.get(Constants.DEFAULT_PARENT_RESELLER_TYPE));
				command.put(Constants.OWNER_CATEGORY_NAME, operationFields.get(Constants.DEFAULT_PARENT_RESELLER_TYPE));
				command.put(Constants.OWNER_NAME, operationFields.get(Constants.DEFAULT_PARENT_ORIGIN_ID));
				command.put(Constants.OWNER_MSISDN, operationFields.get(Constants.DEFAULT_PARENT_ORIGIN_MSISDN));
			}
			else
			{
				requestFields.put(Constants.FETCH_RESELLER_INFORMATION, data.get(Constants.PARENT_ORIGIN_ID));
				Message<?> resellerInfoMessage = MessageBuilder.withPayload(requestFields).copyHeaders(message.getHeaders()).setHeader(StandardLinkConstants.Fields.PROVIDER_ID, Constants.PROVIDER).setHeader(StandardLinkConstants.Fields.OPERATION, Constants.OPERATION_ID).setHeader(Constants.IS_INTERNAL_CALL, true).build();
				LinkedCaseInsensitiveMap<Object> resellerInfoResponse = fetchResellerInfoProcessor.process(resellerInfoMessage);

				String reqParentCategoryName, reqParentResellerPath, ownerName, ownerMsisdn, ownerCategory, parentResellerId, domainName, domainCode;
				int resellerErrorCode = resellerInfoResponse.containsKey(Constants.RESULT_CODE) ? Integer.parseInt(resellerInfoResponse.get(Constants.RESULT_CODE).toString()) : StandardLinkResultCodes.FAILED_ON_SERVER;

				if (resellerErrorCode == StandardLinkResultCodes.INTERNAL_SUCCESS && resellerInfoResponse.containsKey(Constants.PARENT_RESELLER_ID))
				{
					reqParentCategoryName = Objects.nonNull(resellerInfoResponse.get(Constants.RESELLER_TYPE_ID)) ? String.valueOf(resellerInfoResponse.get(Constants.RESELLER_TYPE_ID)) : null;
					parentResellerId = String.valueOf(resellerInfoResponse.get(Constants.PARENT_RESELLER_ID));
					reqParentResellerPath = String.valueOf(resellerInfoResponse.get(Constants.RESELLER_PATH));
					String[] splitArray = reqParentResellerPath.split("/");

					if (StringUtils.isNotEmpty(reqParentCategoryName) && splitArray.length > 2)
					{
						//When adding POS user and SE as parent in USERADDREQ request
						requestFields.put(Constants.PARENT_CATEGORY_NAME, reqParentCategoryName);
						requestFields.put(Constants.FETCH_RESELLER_INFORMATION, parentResellerId);

						resellerInfoResponse = fetchResellerInfoProcessor.process(resellerInfoMessage);
						ownerCategory = String.valueOf(resellerInfoResponse.get(Constants.RESELLER_TYPE_ID));
						ownerName = String.valueOf(resellerInfoResponse.get(Constants.RESELLER_ID));
						ownerMsisdn = String.valueOf(resellerInfoResponse.get(Constants.RESELLER_MSISDN));
					}
					else if (reqParentCategoryName.equalsIgnoreCase(String.valueOf(data.get(Constants.USER_CAT_CODE))))
					{
						ownerCategory = "";
						ownerName = "";
						ownerMsisdn = "";
					}
					else if (StringUtils.isEmpty(parentResellerId))
					{
						//When adding DIST user and OPERATOR as parent in USERADDREQ request
						requestFields.put(Constants.PARENT_CATEGORY_NAME, reqParentCategoryName);
						ownerCategory = String.valueOf(data.get(Constants.USER_CAT_CODE));
						ownerName = String.valueOf(data.get(Constants.WEB_LOGIN_ID));
						ownerMsisdn = String.valueOf(msisdns.get(Constants.MSISDN1));
					}
					else
					{
						//When adding SE user and DIST as parent in USERADDREQ request
						requestFields.put(Constants.PARENT_CATEGORY_NAME, reqParentCategoryName);
						ownerCategory = reqParentCategoryName;
						ownerName = String.valueOf(resellerInfoResponse.get(Constants.RESELLER_ID));
						ownerMsisdn = String.valueOf(resellerInfoResponse.get(Constants.RESELLER_MSISDN));
					}

					requestFields.put(Constants.OWNER_CATEGORY_NAME, ownerCategory);
					requestFields.put(Constants.OWNER_NAME, ownerName);
					requestFields.put(Constants.OWNER_MSISDN, ownerMsisdn);

				}
				else if (!resellerInfoResponse.isEmpty())
				{
					resellerErrorCode = Integer.parseInt(resellerInfoResponse.get(Constants.SERVER_ERROR_CODE).toString());
					throw new ServerException(resellerErrorCode, String.valueOf(resellerInfoResponse.get(Constants.RESULT_DESCRIPTION)));
				}
				else
				{
					throw new ClientException(resellerErrorCode, "Invalid response received from server.");
				}
			}
			/************************ Start Get Region Info ***********************************/
LOGGER.info("===============regionInfoRequest========= Started");

			Message<?> regionInfoRequestMessage = MessageBuilder.withPayload(requestFields).copyHeaders(message.getHeaders()).setHeader(StandardLinkConstants.Fields.PROVIDER_ID, "GP").setHeader(StandardLinkConstants.Fields.OPERATION, "FetchRegionInfoInternal").setHeader("IS_INTERNAL_CALL", true).build();
LOGGER.info("===============regionInfoRequest========= About to send");
			LinkedCaseInsensitiveMap<Object> regionInfoResponse = regionInfoProcessor.process(regionInfoRequestMessage);
LOGGER.info("===============regionInfoResponse========= completed");
			if (regionInfoResponse.containsKey(Constants.RESULT_CODE) && ((List) regionInfoResponse.get(Constants.REGION_INFO)).size() > 0) {
				requestFields.put(Constants.REGION_INFO, regionInfoResponse.get(Constants.REGION_INFO));

			}
			/************************ End Get Region Info ***********************************/

			return MessageBuilder.withPayload(requestFields).copyHeaders(message.getHeaders()).setHeader(StandardLinkConstants.Fields.PROVIDER_ID, "GP").setHeader(StandardLinkConstants.Fields.OPERATION, "AddReseller").setHeader("IS_INTERNAL_CALL", true).build();

		}
		catch (Exception e)
		{
LOGGER.error("Error while processing FetchResellerInfoInterceptor "+e.getMessage(),e);
			throw new ServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred in server during FetchResellerInfoInterceptor execution.");
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
		private static final String SERVER_ERROR_CODE = "serverErrorCode";
		private static final String PARENT_RESELLER_ID = "parentResellerId";
		private static final String PROVIDER = "gp";
		private static final String OPERATION_ID = "SearchResellerByAttribute";
		private static final String IS_INTERNAL_CALL = "IS_INTERNAL_CALL";
		private static final String RESULT_CODE = "resultCode";
		private static final String COMMAND = "COMMAND";
		private static final String DATA = "DATA";
		private static final String RESULT_DESCRIPTION = "resultDescription";
		private static final String FETCH_RESELLER_INFORMATION = "FETRESELLERINFORMATION";
		private static final String PARENT_ORIGIN_ID = "PARENTORIGINID";
		private static final String PARENT_CATEGORY_NAME = "parentCategoryName";
		private static final String PARENT_MSISDN = "PARENTMSISDN";
		private static final String OWNER_CATEGORY_NAME = "ownerCategory";
		private static final String OWNER_NAME = "ownerName";
		private static final String OWNER_MSISDN = "ownerMsisdn";
		private static final String MSISDNS = "msisdns";
		private static final String ADD_RESELLER = "AddReseller";
		private static final String RESELLER_TYPE_ID = "resellerTypeId";
		private static final String RESELLER_ID = "resellerId";
		private static final String RESELLER_MSISDN = "resellerMSISDN";
		private static final String RESELLER_PATH = "resellerPath";
		private static final String USER_CAT_CODE = "USERCATCODE";
		private static final String WEB_LOGIN_ID = "WEBLOGINID";
		private static final String MSISDN1 = "MSISDN1";
		private static final String CATEGORY_TYPES = "categoryTypes";
		private static final String DEFAULT_PARENT_ORIGIN_ID = "defaultParentOriginId";
		private static final String DEFAULT_PARENT_ORIGIN_MSISDN = "defaultParentOriginMsisdn";
		private static final String DEFAULT_PARENT_RESELLER_TYPE = "defaultParentResellerType";
		private static final String REGION_INFO = "regionInfo";
	}
}

