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
package com.seamless.ers.standardlink.interceptors.channelUserMovement;

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
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.Arrays;
import java.util.List;

@Component("channelUserMovementInterceptor")
public class ChannelUserMovementInterceptor implements ChannelInterceptor
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ChannelUserMovementInterceptor.class);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Autowired
	@Qualifier("resellerInfoProcessor")
	private InterceptedRequestProcessor resellerInfoProcessor;

	@Autowired
	@Qualifier("regionInfoProcessor")
	private InterceptedRequestProcessor regionInfoProcessor;

	@Autowired
	@Qualifier("updateResellerProcessor")
	private InterceptedRequestProcessor updateResellerProcessor;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel)
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("ResellerDeactivateInterceptor :: preSend");
		try
		{
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.PROVIDER);
			LinkedCaseInsensitiveMap<Object> channelUserMovementFields = providerConfigurations.getOperations().get(Constants.CHANNEL_USER_MOVEMENT).getFields();
			LinkedCaseInsensitiveMap<Object> requestFields = StandardLinkUtilities.convertToCaseInsensitiveMap((LinkedCaseInsensitiveMap<Object>) message.getPayload());

			Message<?> resellerInfoMessage = MessageBuilder.withPayload(requestFields).copyHeaders(message.getHeaders()).setHeader(StandardLinkConstants.Fields.PROVIDER_ID, Constants.PROVIDER).setHeader(StandardLinkConstants.Fields.OPERATION, Constants.VIEW_CHANNEL_USER).setHeader(Constants.IS_INTERNAL_CALL, true).build();
			LinkedCaseInsensitiveMap<Object> resellerInfoResponse = resellerInfoProcessor.process(resellerInfoMessage);

			LOGGER.debug("Contents of resellerInfoMessage: " + resellerInfoMessage.toString());
			LOGGER.debug("Contents of resellerInfoResponse: " + resellerInfoResponse.toString());

			int resellerErrorCode = resellerInfoResponse.containsKey(Constants.RESULT_CODE) ? Integer.parseInt(resellerInfoResponse.get(Constants.RESULT_CODE).toString()) : resellerInfoResponse.containsKey(Constants.TXNSTATUS) ? Integer.parseInt(resellerInfoResponse.get(Constants.TXNSTATUS).toString()):StandardLinkResultCodes.FAILED_ON_SERVER;

			if (resellerErrorCode == StandardLinkResultCodes.SUCCESS || resellerErrorCode == StandardLinkResultCodes.INTERNAL_SUCCESS)
			{
				Map<String, Object> data = resellerInfoResponse.get(Constants.DATA);
				String reseller_Type = data.get(Constants.CATEGORYCODE)

				List<String> allowedChangeParentResellerTypes = Arrays.asList(channelUserMovementFields.getOrDefault(Constants.ALLOWED_CHANGE_PARENT_RESELLER_TYPE, "").toString().split(","));

				if (allowedChangeParentResellerTypes.contains(reseller_Type))
				{
					Message<?> regionInfoMessage = MessageBuilder.withPayload(requestFields).copyHeaders(message.getHeaders()).setHeader(StandardLinkConstants.Fields.PROVIDER_ID, Constants.PROVIDER).setHeader(StandardLinkConstants.Fields.OPERATION, Constants.FETCH_REGION_INFO_INTERNAL).setHeader(Constants.IS_INTERNAL_CALL, true).build();
					LinkedCaseInsensitiveMap<Object> regionInfoResponse = regionInfoProcessor.process(regionInfoMessage);
					int regionInfoErrorCode = regionInfoResponse.containsKey(Constants.RESULT_CODE) ? Integer.parseInt(regionInfoResponse.get(Constants.RESULT_CODE).toString()) : StandardLinkResultCodes.FAILED_ON_SERVER;

					if(regionInfoErrorCode == (StandardLinkResultCodes.SUCCESS) || regionInfoErrorCode == (StandardLinkResultCodes.INTERNAL_SUCCESS))
					{
						ArrayList regionInfo = regionInfoResponse.get(Constants.REGION_INFO);
						requestFields.put(Constants.REGION_INFO, regionInfo);
						Message<?> updateMessage = MessageBuilder.withPayload(requestFields).copyHeaders(message.getHeaders()).setHeader(StandardLinkConstants.Fields.PROVIDER_ID, Constants.PROVIDER).setHeader(StandardLinkConstants.Fields.OPERATION, Constants.UPDATE_RESELLER).setHeader(Constants.IS_INTERNAL_CALL, true).build();
						LinkedCaseInsensitiveMap<Object> updateResellerResponse = updateResellerProcessor.process(updateMessage);
						int updateResellerErrorCode = updateResellerResponse.containsKey(Constants.RESULT_CODE) ? Integer.parseInt(updateResellerResponse.get(Constants.RESULT_CODE).toString()) : StandardLinkResultCodes.FAILED_ON_SERVER;
						if ((updateResellerErrorCode == StandardLinkResultCodes.SUCCESS)||(updateResellerErrorCode == StandardLinkResultCodes.INTERNAL_SUCCESS))
						{
							Message<?> channelUserMovementMessage = MessageBuilder.withPayload(requestFields).copyHeaders(message.getHeaders()).setHeader(StandardLinkConstants.Fields.OPERATION, Constants.CHANNEL_USER_MOVEMENT).build();
							return channelUserMovementMessage;
						}
						else
						{
							updateResellerErrorCode = Integer.parseInt(resellerInfoResponse.get(Constants.SERVER_ERROR_CODE).toString());
							throw new ServerException(updateResellerErrorCode, String.valueOf(updateResellerResponse.get(Constants.RESULT_DESCRIPTION)));
						}
					}
					else if(regionInfoResponse.get("ERRORKEY")==412){
						return regionInfoMessage;
					}
					else
					{
						regionInfoErrorCode = Integer.parseInt(regionInfoResponse.get(Constants.SERVER_ERROR_CODE).toString());
						throw new ServerException(regionInfoErrorCode, String.valueOf(regionInfoResponse.get(Constants.RESULT_DESCRIPTION)));
					}
				}
				else
				{
					throw new ServerException(200, reseller_Type + " is not allowed.");
				}
			}
			else if (!resellerInfoResponse.isEmpty())
			{
				resellerErrorCode = Integer.parseInt(resellerInfoResponse.get(Constants.SERVER_ERROR_CODE).toString());
				throw new ServerException(resellerErrorCode, String.valueOf(resellerInfoResponse.get(Constants.RESULT_DESCRIPTION)));
			}
			else
			{
				throw new ClientException(resellerErrorCode, String.valueOf(resellerInfoResponse.get(Constants.RESULT_DESCRIPTION)));
			}
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
		private static final String PROVIDER = "GP";
		private static final String VIEW_CHANNEL_USER = "ViewChannelUserInternal";
		private static final String IS_INTERNAL_CALL = "IS_INTERNAL_CALL";
		private static final String RESULT_CODE = "resultCode";
		private static final String TXNSTATUS = "TXNSTATUS";
		private static final String RESULT_DESCRIPTION = "resultDescription";
		private static final String RESELLER_TYPE = "resellerTypeId";
		private static final String ALLOWED_CHANGE_PARENT_RESELLER_TYPE = "allowedChangeParentResellerType";
		private static final String CHANNEL_USER_MOVEMENT = "ChannelUserMovement";
		private static final String FETCH_REGION_INFO_INTERNAL = "FetchRegionInfoInternal";
		private static final String UPDATE_RESELLER = "UpdateResellerInternal";
		private static final String DATA = "DATA";
		private static final String CATEGORYCODE = "CATEGORYCODE";
		private static final String REGION_INFO = "regionInfo";
	}
}

