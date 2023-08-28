/*
 * @author      : Bilal Mirza <bilal.mirza@seamless.se>
 * Created Date : 20/06/2022
 * Time         : 1:34 PM
 *
 * Purpose      : To check if `WEBLOGINID` exits in request or not. If it doesn't exist, resellerInfo call will be sent to
 *                DMS for fetching resellerId using additionalAttributes.
 *
 * Copyright(c) 2021. Seamless Distribution Systems AB - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited. It is proprietary and confidential.
 *
 */
package com.seamless.ers.standardlink.interceptors.updatereseller;

import com.seamless.common.StringUtils;
import com.seamless.ers.standardlink.processors.InterceptedRequestProcessor;
import com.seamless.ers.standardlink.model.common.constants.StandardLinkConstants;
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

@Component("updateResellerInterceptor")
public class UpdateResellerInterceptor implements ChannelInterceptor
{
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateResellerInterceptor.class);

	@Autowired
	@Qualifier("fetchResellerInfoProcessor")
	private InterceptedRequestProcessor fetchResellerInfoProcessor;

	/**
	 * @param message
	 * @param channel
	 * @return
	 */
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel)
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("FetchResellerInfoInterceptor :: preSend");
		LinkedCaseInsensitiveMap<Object> requestFields = StandardLinkUtilities.convertToCaseInsensitiveMap((LinkedCaseInsensitiveMap<Object>) message.getPayload());
		LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.COMMAND);
		LinkedCaseInsensitiveMap<Object> data = (LinkedCaseInsensitiveMap<Object>) command.get(Constants.DATA);
		try
		{
			LOGGER.info("Checking WEBLOGINID ...");

			String resellerId;
			Message<?> resellerInfoMessage;

			if (data.get(Constants.WEB_LOGIN_ID) == null || String.valueOf(data.get(Constants.WEB_LOGIN_ID)).isEmpty())
			{
				LOGGER.info("WEBLOGINID is missing in the request.");

				if (data.containsKey(Constants.EXTERNAL_CODE) && StringUtils.isNotBlank(String.valueOf(data.get(Constants.EXTERNAL_CODE))))
				{
					LOGGER.info("Fetching ResellerInfo using `EXTERNALCODE` : " + data.get(Constants.EXTERNAL_CODE));
					resellerInfoMessage = MessageBuilder.withPayload(requestFields).copyHeaders(message.getHeaders()).setHeader(StandardLinkConstants.Fields.PROVIDER_ID, Constants.PROVIDER).setHeader(StandardLinkConstants.Fields.OPERATION, Constants.OPERATION_ID).setHeader(Constants.IS_INTERNAL_CALL, true).setHeader(Constants.IS_UPDATE_RESELLER_CALL, true).build();
					LinkedCaseInsensitiveMap<Object> resellerInfoResponse = fetchResellerInfoProcessor.process(resellerInfoMessage);
					int resellerErrorCode = resellerInfoResponse.containsKey(Constants.RESULT_CODE) ? Integer.parseInt(resellerInfoResponse.get(Constants.RESULT_CODE).toString()) : StandardLinkResultCodes.FAILED_ON_SERVER;

					if ((resellerErrorCode == StandardLinkResultCodes.SUCCESS || resellerErrorCode == StandardLinkResultCodes.INTERNAL_SUCCESS) && resellerInfoResponse.containsKey(Constants.RESELLER_ID))
					{
						resellerId = (String) resellerInfoResponse.get(Constants.RESELLER_ID);
						LOGGER.info("Setting resellerId '" + resellerId + "' as WEBLOGINID in the request.");
						data.put(Constants.WEB_LOGIN_ID, resellerId);
						Message<LinkedCaseInsensitiveMap<Object>> updateResellerMessage = MessageBuilder.withPayload(requestFields).copyHeaders(message.getHeaders()).setHeader(StandardLinkConstants.Fields.PROVIDER_ID, Constants.PROVIDER).setHeader(StandardLinkConstants.Fields.OPERATION, Constants.UPDATE_RESELLER).setHeader(Constants.IS_INTERCEPTION_SUCCESSFUL, "TRUE").build();
						return updateResellerMessage;
					}
					else if (!resellerInfoResponse.isEmpty()) {
						if (resellerInfoResponse.containsKey(Constants.SERVER_ERROR_CODE)) {
							resellerErrorCode = Integer.parseInt(resellerInfoResponse.get(Constants.SERVER_ERROR_CODE).toString());
							return MessageBuilder.withPayload(new ServerException(resellerErrorCode, String.valueOf(resellerInfoResponse.get(Constants.RESULT_DESCRIPTION)))).copyHeaders(message.getHeaders()).setHeader(Constants.IS_INTERCEPTION_SUCCESSFUL, "FALSE").build();
						}
					}
					else
					{
						return MessageBuilder.withPayload(new ClientException(resellerErrorCode, "No reseller found with ExternalCode `" + data.get(Constants.EXTERNAL_CODE) + "`.")).copyHeaders(message.getHeaders()).setHeader(Constants.IS_INTERCEPTION_SUCCESSFUL, "FALSE").build();
					}
				}
			}
			return MessageBuilder.fromMessage(message).copyHeaders(message.getHeaders()).setHeader(Constants.IS_INTERCEPTION_SUCCESSFUL, "TRUE").build();
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
		private static final String PROVIDER = "gp";
		private static final String IS_INTERNAL_CALL = "IS_INTERNAL_CALL";
		private static final String RESULT_CODE = "resultCode";
		private static final String COMMAND = "COMMAND";
		private static final String DATA = "DATA";
		private static final String RESULT_DESCRIPTION = "resultDescription";
		private static final String RESELLER_ID = "resellerId";
		private static final String WEB_LOGIN_ID = "WEBLOGINID";
		private static final String EXTERNAL_CODE = "EXTERNALCODE";
		private static final String OPERATION_ID = "SearchResellerByAttribute";
		private static final String IS_UPDATE_RESELLER_CALL = "IS_UPDATE_RESELLER_CALL";
		private static final String UPDATE_RESELLER = "UpdateReseller";
		private static final String IS_INTERCEPTION_SUCCESSFUL = "IS_INTERCEPTION_SUCCESSFUL";
	}
}

