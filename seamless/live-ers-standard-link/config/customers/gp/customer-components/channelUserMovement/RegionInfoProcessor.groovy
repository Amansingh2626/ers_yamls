/*
 * Created Date : 15/08/2021
 * Time         : 6:14 PM
 *
 * @author 		: Bilal Mirza <bilal.mirza@seamless.se>
 * Purpose      : To Process the internal call of transferOrder which will return transferOrderNumber to be used in line calls
 *
 * Copyright(c) 2021. Seamless Distribution Systems AB - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited. It is proprietary and confidential.
 */
package com.seamless.ers.standardlink.interceptors.channelUserMovement;
import com.seamless.ers.standardlink.processors.InterceptedRequestProcessor;
import com.seamless.ers.standardlink.model.exception.ClientException;
import com.seamless.ers.standardlink.model.exception.ServerException;
import com.seamless.ers.standardlink.model.internal.StandardLinkResultCodes;
import com.seamless.ers.standardlink.model.internal.StandardResponseModel;
import com.seamless.ers.standardlink.utilities.StandardLinkUtilities;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.channel.RendezvousChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.transformer.MessageTransformationException;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.LinkedHashMap;

@Component("regionInfoProcessor")
public class RegionInfoProcessor implements InterceptedRequestProcessor
{
	private static final Logger LOGGER = LoggerFactory.getLogger(RegionInfoProcessor.class);
	private static final String SERVER_ERROR_CODE = "serverErrorCode";
	private static final String RESULT_CODE = "resultCode";
	private static final String RESULT_DESCRIPTION = "resultDescription";

	@Autowired
	@Qualifier("fetchRegionInfoInternalRequestChannel")
	private MessageChannel fetchResellerRegionChannel;

	@Override
	public LinkedCaseInsensitiveMap<Object> process(Message<?> message)
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("RegionInfoProcessor :: getRegionInfo");
		LinkedCaseInsensitiveMap<Object> requestFields = StandardLinkUtilities.convertToCaseInsensitiveMap((LinkedHashMap<String, Object>) message.getPayload());
		LinkedCaseInsensitiveMap<Object> responseFields = new LinkedCaseInsensitiveMap<>();

		try
		{
			RendezvousChannel replyChannel = new RendezvousChannel();
			Message<?> updatedMessage = MessageBuilder.fromMessage(message)
					.setReplyChannel(replyChannel)
					.setErrorChannel(replyChannel)
					.build();

			boolean send = fetchResellerRegionChannel.send(updatedMessage);

			if (send)
			{
				LOGGER.info("getResellerRegionInfo message successfully sent to entry channel");
			}
			else
			{
				LOGGER.error("getResellerRegionInfo message could not be sent to entry channel");
				responseFields.put(RESULT_CODE, StandardLinkResultCodes.CONFIGURATION_ERROR);
				responseFields.put(RESULT_DESCRIPTION, StandardLinkResultCodes.getDefaultMessage(StandardLinkResultCodes.CONFIGURATION_ERROR));
				return responseFields;
			}
			Message<?> responseMessage = replyChannel.receive();

			if (responseMessage.getPayload() instanceof LinkedCaseInsensitiveMap)
			{
				LinkedCaseInsensitiveMap<Object> responseMessageMap = (LinkedCaseInsensitiveMap<Object>) responseMessage.getPayload();
				if (responseMessageMap.containsKey(RESULT_CODE) && Integer.valueOf(String.valueOf(responseMessageMap.get(RESULT_CODE))) == (StandardLinkResultCodes.SUCCESS)||(StandardLinkResultCodes.INTERNAL_SUCCESS))
				{
					LOGGER.info("Received response of type [" + LinkedCaseInsensitiveMap.class.getName() + "] from SI flow");
					responseFields = (LinkedCaseInsensitiveMap<Object>) responseMessage.getPayload();
					return responseFields;
				}
			}
			else
			{
				LOGGER.error("An error occurred while processing getResellerRegionInfo request");
				MessagingException messagingException = ((MessagingException) responseMessage.getPayload());
				if (messagingException.getCause() instanceof ClientException)
				{
					ClientException clientException = (ClientException) messagingException.getCause();
					responseFields.put(RESULT_DESCRIPTION, clientException.getResultMessage());
					responseFields.put(RESULT_CODE, clientException.getResultCode());
				}
				else if (messagingException.getCause() instanceof ServerException)
				{
					ServerException serverException = (ServerException) messagingException.getCause();
					responseFields.put(SERVER_ERROR_CODE, serverException.getHttpStatus());
					responseFields.put(RESULT_DESCRIPTION, serverException.getStatusMessage());
					responseFields.put(RESULT_CODE, StandardLinkResultCodes.FAILED_ON_SERVER);
				}
				else if (messagingException instanceof MessageTransformationException || messagingException.getCause() instanceof MessageTransformationException)
				{
					MessageTransformationException transformerException = (MessageTransformationException) messagingException;
					responseFields.put(RESULT_DESCRIPTION, transformerException.getMessage());
					responseFields.put(RESULT_CODE, StandardLinkResultCodes.TRANSFORMATION_ERROR);
				}
				else
				{
					String errorMessage = ((MessagingException) responseMessage.getPayload()).getCause().getMessage();
					responseFields.put(RESULT_CODE, StandardLinkResultCodes.FAILED_ON_SERVER);
					responseFields.put(RESULT_DESCRIPTION, errorMessage);
					return responseFields;
				}
			}
		}
		catch (Exception e)
		{
			throw new ClientException(StandardLinkResultCodes.CONFIGURATION_ERROR, "An error occurred during reseller info message processing");
		}
		finally
		{
			if (point != null)
			{
				point.collect();
			}
		}
		return requestFields;
	}

}


