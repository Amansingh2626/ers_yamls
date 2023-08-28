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
package com.seamless.ers.standardlink.interceptors.deactivateReseller;

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

import java.util.LinkedHashMap;

@Component("c2cWithdrawInterceptor")
public class C2CWithdrawInterceptor implements ChannelInterceptor
{
	private static final Logger LOGGER = LoggerFactory.getLogger(C2CWithdrawInterceptor.class);
	private static final String RESELLER_BALANCE = "resellerBalance";
	private static final String SERVER_ERROR_CODE = "serverErrorCode";
	private static final String PARENT_RESELLER_ID = "parentResellerId";
	private static final String PROVIDER = "gp";
	private static final String VIEW_CHANNEL_USER = "ViewChannelUser";
	private static final String C2C_WITHDRAW = "C2CWithdraw";
	private static final String C2C_USSD_WITHDRAW = "C2CUssdWithdraw";
	private static final String IS_INTERNAL_CALL = "IS_INTERNAL_CALL";
	private static final String RESULT_CODE = "resultCode";
	private static final String COMMAND = "COMMAND";
	private static final String GP_EXT_REQUEST_TYPE = "TYPE";
	private static final String EXC2CWDREQ = "EXC2CWDREQ";
	private static final String RESULT_DESCRIPTION = "resultDescription";

	@Autowired
	@Qualifier("resellerInfoProcessor")
	private InterceptedRequestProcessor resellerInfoProcessor;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel)
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("C2CWithdrawInterceptor :: preSend");
		try
		{
			LOGGER.info("Fetching reseller info ...");
			LinkedCaseInsensitiveMap<Object> requestFields = StandardLinkUtilities.convertToCaseInsensitiveMap((LinkedHashMap<String, Object>) message.getPayload());
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(COMMAND);
			Message<?> resellerInfoMessage = MessageBuilder.withPayload(requestFields).copyHeaders(message.getHeaders()).setHeader(StandardLinkConstants.Fields.PROVIDER_ID, PROVIDER).setHeader(StandardLinkConstants.Fields.OPERATION, VIEW_CHANNEL_USER).setHeader(IS_INTERNAL_CALL, true).build();

			LinkedCaseInsensitiveMap<Object> resellerInfoResponse = resellerInfoProcessor.process(resellerInfoMessage);
			int resellerErrorCode = resellerInfoResponse.containsKey(RESULT_CODE) ? Integer.parseInt(resellerInfoResponse.get(RESULT_CODE).toString()) : StandardLinkResultCodes.FAILED_ON_SERVER;

			if (resellerErrorCode == StandardLinkResultCodes.SUCCESS && resellerInfoResponse.containsKey(RESELLER_BALANCE) && resellerInfoResponse.containsKey(PARENT_RESELLER_ID))
			{
				String parentResellerId = String.valueOf(resellerInfoResponse.get(PARENT_RESELLER_ID));

				if (StringUtils.isNotEmpty(parentResellerId))
				{
					Message<?> updateWithdrawMessage = null;
					if (resellerInfoResponse.containsKey(GP_EXT_REQUEST_TYPE) && String.valueOf(resellerInfoResponse.get(GP_EXT_REQUEST_TYPE)).equalsIgnoreCase(EXC2CWDREQ))
					{
						updateWithdrawMessage = MessageBuilder.withPayload(message).copyHeaders(message.getHeaders()).setHeader(StandardLinkConstants.Fields.OPERATION, C2C_WITHDRAW).build();
					}
					else
					{
						requestFields.put(PARENT_RESELLER_ID, parentResellerId);
						updateWithdrawMessage = MessageBuilder.withPayload(requestFields).copyHeaders(message.getHeaders()).setHeader(StandardLinkConstants.Fields.OPERATION, C2C_USSD_WITHDRAW).build();
					}
					return updateWithdrawMessage;
				}
			}
			else if (!resellerInfoResponse.isEmpty())
			{
				resellerErrorCode = Integer.parseInt(resellerInfoResponse.get(SERVER_ERROR_CODE).toString());
				throw new ServerException(resellerErrorCode, String.valueOf(resellerInfoResponse.get(RESULT_DESCRIPTION)));
			}
			throw new ClientException((Integer) resellerInfoResponse.get(RESULT_CODE), String.valueOf(resellerInfoResponse.get(RESULT_DESCRIPTION)));
		}
		finally
		{
			if (point != null)
			{
				point.collect();
			}
		}
	}

}
