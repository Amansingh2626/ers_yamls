package com.seamless.ers.standardlink.transformers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
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
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class FetchLastXTransactionsTransformer implements ERSIOTransformer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.DATE_FORMAT);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Override
	public Message<?> transformInboundRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("FetchLastXTransactionsTransformer :: transformOutboundRequest");

		try
		{
			LOGGER.info("FetchLastXTransactionsTransformer calling service ============");
			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.COMMAND);

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.OPERATION).getFields();

			Map<String, Object> request = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> fieldsMap = Collections.synchronizedMap(new LinkedHashMap<>());

			if (command.containsKey(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.NUMBER_OF_LAST_X_TXN) && command.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.NUMBER_OF_LAST_X_TXN) != null && !StringUtils.isEmpty(command.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.NUMBER_OF_LAST_X_TXN).toString()))
			{
				fieldsMap.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.SIZE, String.valueOf(command.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.NUMBER_OF_LAST_X_TXN)));
			}
			else
			{
				fieldsMap.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.SIZE, String.valueOf(operationFields.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.NUMBER_OF_LAST_X_TXN)));
			}
			String days = null;
			if (command.containsKey(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.NUMBER_OF_DAYS) && command.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.NUMBER_OF_DAYS) != null && !StringUtils.isEmpty(command.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.NUMBER_OF_DAYS).toString()))
			{
				days = command.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.NUMBER_OF_DAYS).toString();
			}
			else
			{
				days = operationFields.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.NUMBER_OF_DAYS).toString();
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, -Integer.parseInt(days));
			fieldsMap.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.FROM_DATE, simpleDateFormat.format(calendar.getTime()));
			fieldsMap.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.TO_DATE, simpleDateFormat.format(new Date()));

			Object receiverMsisdn = null, receiverId = null;
			LOGGER.debug("Request Headers :" + message.getHeaders().toString());
			if (message.getHeaders().containsKey(StandardLinkConstants.TXEConstant.RECEIVER_MSISDN) || message.getHeaders().containsKey(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Txe.RECEIVER_ID))
			{
				try
				{
					receiverMsisdn = message.getHeaders().get(StandardLinkConstants.TXEConstant.RECEIVER_MSISDN);
					receiverId = message.getHeaders().get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Txe.RECEIVER_ID);
					LOGGER.debug("Fetched receiverId :" + receiverId + " and receiverMsisdn :" + receiverMsisdn + " from header successfully");

				}
				catch (Exception exception)
				{
					LOGGER.error("Failed to fetch receiverId and receiverMsisdn from header ", message.getHeaders(),
							ExceptionUtils.getFullStackTrace(exception));
				}
			}

			if (command.containsKey(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.MSISDN) && command.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.MSISDN) != null && !StringUtils.isEmpty(command.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.MSISDN).toString()))
			{
				fieldsMap.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.RESELLER_MSISDN, command.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.MSISDN));
				fieldsMap.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.REPORT_NAME, "last_X_transactions_report_resellerMsisdn");
				LOGGER.info("Trying to fetch the last transaction for MSISDN: " + command.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.MSISDN));
			}
			else if (command.containsKey(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.LOGINID) && command.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.LOGINID) != null && !StringUtils.isEmpty(command.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.LOGINID).toString()))
			{
				fieldsMap.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.RESELLER_ID, command.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.LOGINID));
				fieldsMap.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.REPORT_NAME, "last_transaction_report_resellerId");
				LOGGER.info("Trying to fetch the last transaction for resellerId: " + command.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.LOGINID));
			}
			else if (Objects.nonNull(receiverMsisdn))
			{
				fieldsMap.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.RESELLER_MSISDN, receiverMsisdn);
				fieldsMap.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.REPORT_NAME, "last_X_transactions_report_resellerMsisdn");
				LOGGER.info("Trying to fetch the last transaction for MSISDN fetched from system token: " + command.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.MSISDN));
			}
			else if (Objects.nonNull(receiverId))
			{
				fieldsMap.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.RESELLER_ID, command.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.LOGINID));
				fieldsMap.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.REPORT_NAME, "last_transaction_report_resellerId");
				LOGGER.info("Trying to fetch the last transaction for resellerId fetched from system token: " + command.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.LOGINID));
			}

			if (command.containsKey(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.RECEIVER_MSISDN) && command.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.RECEIVER_MSISDN) != null && !StringUtils.isEmpty(command.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.RECEIVER_MSISDN).toString()))
			{
				fieldsMap.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.RECEIVERMSISDN, command.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.RECEIVER_MSISDN));
			}
			else
			{
				fieldsMap.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.RECEIVERMSISDN, ".*");
			}
			if (command.containsKey(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.SERVICE_TYPE) && command.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.SERVICE_TYPE) != null && !StringUtils.isEmpty(command.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.SERVICE_TYPE).toString()))
			{
				fieldsMap.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.SERVICETYPE, command.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.SERVICE_TYPE));
			}
			else
			{
				fieldsMap.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.SERVICETYPE, ".*");
			}
			request.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.REQUEST, fieldsMap);

			return MessageBuilder.withPayload(request).copyHeaders(message.getHeaders()).setHeaderIfAbsent(StandardLinkConstants.Headers.SYSTEM_TOKEN, message.getHeaders().get("system-token")).setHeaderIfAbsent(StandardLinkConstants.Headers.AUTHORIZATION, message.getHeaders().get("authorization")).setHeaderIfAbsent("validateOnly", false).build();
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

	@Override
	public Object transformOutboundResponse(Object outgoingResponse) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("FetchLastXTransactionsTransformer :: transformOutboundResponse");

		try
		{
			Map<String, Object> response = new LinkedHashMap<>();
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.OPERATION).getFields();

			response.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));
			if (outgoingResponse instanceof CompletableFuture)
			{
				outgoingResponse = ((CompletableFuture<?>) outgoingResponse).get();
			}
			if (outgoingResponse instanceof Exception)
			{
				Exception exception = (Exception) outgoingResponse;
				response.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.GP_MESSAGE, StandardLinkUtilities.getRootCause(exception));
				response.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.GP_TXN_STATUS, StandardLinkResultCodes.INTERNAL_COMPONENT_ERROR);
				response.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.GP_REQSTATUS, StandardLinkResultCodes.INTERNAL_COMPONENT_ERROR);
				return response;
			}
			Map<String, Object> internalResponse = (LinkedHashMap<String, Object>) outgoingResponse;
			LOGGER.info("Printing response ===== 1");
			LOGGER.info(internalResponse.toString());
			Map<String, Object> request = (Map<String, Object>) internalResponse.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.REQUEST_PAYLOAD);
			LOGGER.info("Printing response ===== 2");
			LOGGER.info(request.toString());
			Map<String, Object> requestPayloadCommand = (Map<String, Object>) request.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.COMMAND);

			LOGGER.info("Response from BI =================");


			if (!internalResponse.isEmpty() && internalResponse.containsKey(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Txe.RESULT_CODE))
			{
				LOGGER.info("Response from BI =================1 ");
				Integer resultCodeValue = (Integer) internalResponse.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Txe.RESULT_CODE);
				String defaultMessage = String.valueOf(internalResponse.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Txe.RESULT_MESSAGE) != null ? internalResponse.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Txe.RESULT_MESSAGE) : internalResponse.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Txe.MESSAGE) != null ? internalResponse.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Txe.MESSAGE) : internalResponse.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Txe.RESULT_DESCRIPTION));
				if (resultCodeValue == StandardLinkResultCodes.SUCCESS)
				{
					LOGGER.info("Response from BI =================2 ");
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.PROVIDER, com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.GP_RESPONSE_TYPE, operationFields.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.RESPONSE_TYPE));

					if (resultCode != null)
					{
						response.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.GP_REQSTATUS, (resultCode.getInternalResultCode()));
						response.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.GP_MESSAGE, (resultCode.getDescription()));
						response.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.GP_TXN_STATUS, (resultCode.getInternalResultCode()));

					}
					else
					{
						response.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.GP_REQSTATUS, (response.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Txe.RESULTCODE).toString()));
						response.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.GP_MESSAGE, (response.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Txe.RESULT_DESCRIPTION).toString()));
						response.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.GP_TXN_STATUS,  (response.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Txe.RESULTCODE).toString()));

					}
					LOGGER.info("Response from BI =================3 ");

					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.DATE_FORMAT);
					if (requestPayloadCommand.containsKey(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.EXTREFNUM) && !StringUtils.isEmpty(requestPayloadCommand.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.EXTREFNUM).toString()))
					{
						response.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.EXTREFNUM, (requestPayloadCommand.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.EXTREFNUM).toString()));
					}
					else
					{
						response.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.EXTREFNUM, (""));
					}

					LOGGER.info("Response from BI =================4 ");

					//List<Map<String, Object>> transactionDetails = new ArrayList<>();
					//Map<String, Object> transactionsDetailsData =null;
					List<com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.TransactionDetail> transactionsDetailsData=new ArrayList<>();
					if (internalResponse.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.LIST) != null)
					{

						LOGGER.info("Response from BI =================5 ");

						ArrayList<Map<String, Object>> transactions = ((ArrayList<Map<String, Object>>) internalResponse.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.LIST));
						LOGGER.info("Response from BI =================6 ");
						LOGGER.info(transactions.toString());
						LOGGER.info("Response from BI =================7 ");

						Map<String, Object> transactionDetail = (LinkedHashMap<String, Object>) outgoingResponse;

						LOGGER.info(" Transaction response ====1 ");
						LOGGER.info(transactionDetail.toString());
						LOGGER.info(" Transaction response ====2");

						for (int i = 0; i < transactions.size(); i++)
						{
							//transactionsDetailsData =new TransactionDetailsV2();
							//Map<String, Object>	transactionDetailObject=new HashMap<>();
							com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.TransactionDetail transactionDetailObject1=new com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.TransactionDetail();
							LOGGER.info(" Transaction response ====3");
							LOGGER.info(transactions.get(i).toString());
							LOGGER.info(" Transaction response ====4");
							if (transactions.get(i).containsKey(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.ERSREFERENCE) && !StringUtils.isEmpty(transactions.get(i).get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.ERSREFERENCE).toString()))
							{
								//transactionDetailObject.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.TXNID, (transactions.get(i).get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.ERSREFERENCE).toString()));
								transactionDetailObject1.setTXNID((transactions.get(i).get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.ERSREFERENCE).toString()));
							}
							else
							{
								//transactionDetailObject.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.TXNID, (""));
								transactionDetailObject1.setTXNID("");
							}
							if (transactions.get(i).containsKey(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.TIMESTAMP) && !StringUtils.isEmpty(transactions.get(i).get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.TIMESTAMP).toString()))
							{
								//transactionDetailObject.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.TXNDATETIME, (transactions.get(i).get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.TIMESTAMP).toString()));
								transactionDetailObject1.setTXNDATETIME((transactions.get(i).get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.TIMESTAMP).toString()));
							}
							else
							{
								//transactionDetailObject.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.TXNDATETIME, (""));
								transactionDetailObject1.setTXNDATETIME("");
							}
							if (transactions.get(i).containsKey(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.RESULTCODE) && !StringUtils.isEmpty(transactions.get(i).get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.RESULTCODE).toString()))
							{
								//transactionDetailObject.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.GP_TXN_STATUS, (transactions.get(i).get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.RESULTCODE).toString()));
								transactionDetailObject1.setTXNSTATUS(transactions.get(i).get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.RESULTCODE).toString());
							}
							else
							{
								//transactionDetailObject.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.GP_TXN_STATUS, (""));
								transactionDetailObject1.setTXNSTATUS("");
							}
							if (transactions.get(i).containsKey(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.TRANSACTIONAMOUNT) && !StringUtils.isEmpty(transactions.get(i).get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.TRANSACTIONAMOUNT).toString()))
							{
								//transactionDetailObject.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.TXNAMOUNT, (transactions.get(i).get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.TRANSACTIONAMOUNT).toString()));
								transactionDetailObject1.setTXNAMOUNT((transactions.get(i).get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.TRANSACTIONAMOUNT).toString()));
							}
							else
							{
								//transactionDetailObject.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.TXNAMOUNT, (""));
								transactionDetailObject1.setTXNAMOUNT("");
							}
							//should verify.
							if (transactions.get(i).containsKey(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.TRANSACTIONTYPE) && !StringUtils.isEmpty(transactions.get(0).get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.TRANSACTIONTYPE).toString()))
							{
								//transactionDetailObject.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.TRFTYPE, (transactions.get(0).get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.TRANSACTIONTYPE).toString()));
								transactionDetailObject1.setTRFTYPE(transactions.get(0).get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.TRANSACTIONTYPE).toString());
							}
							else
							{
								//transactionDetailObject.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.TRFTYPE, (""));
								transactionDetailObject1.setTRFTYPE("");
							}
							if (transactions.get(i).containsKey(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.RECEIVERMSISDN) && !StringUtils.isEmpty(transactions.get(i).get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.RECEIVERMSISDN).toString()))
							{
								//transactionDetailObject.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.RECEIVER_MSISDN, (transactions.get(i).get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.RECEIVERMSISDN).toString()));
								transactionDetailObject1.setRECEIVERMSISDN(transactions.get(i).get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.RECEIVERMSISDN).toString());
							}

							transactionsDetailsData.add(transactionDetailObject1);
							//transactionDetails.add(transactionsDetailsData);

						}
					}
					com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.TransactionDetailsV2 transactionDetailsV2=new com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.TransactionDetailsV2();
					transactionDetailsV2.setTXNDETAIL(transactionsDetailsData);
					LOGGER.info("Response from BI =================8 ");
					response.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.LISTTRANSACTIONDETAILS, transactionDetailsV2);
					requestPayloadCommand.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Txe.RESPONSE, response);
				}
				else
				{
					LOGGER.info("Response from BI =================9 ");

					ResultCode resultCode = standardLinkConfig.getResultCodeFor(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.PROVIDER, com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.GP_MESSAGE, resultCode.getDescription());
					response.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
					response.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.GP_REQSTATUS, resultCode.getInternalResultCode());
					response.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.GP_RESPONSE_TYPE, operationFields.get(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.RESPONSE_TYPE));
				}
			}
			else
			{
				LOGGER.info("Response from BI =================10 ");

				response.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.GP_MESSAGE, "Request has failed on server.");
				response.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.GP_TXN_STATUS, StandardLinkResultCodes.FAILED_ON_SERVER);
				response.put(com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.Constants.Gp.GP_REQSTATUS, StandardLinkResultCodes.FAILED_ON_SERVER);
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
		private static final class Gp
		{
			private static final String GP_REQSTATUS = "REQSTATUS";
			private static final String PROVIDER = "gp";
			private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
			private static final String EXTREFNUM = "EXTREFNUM";
			private static final String COMMAND = "COMMAND";
			private static final String OPERATION = "fetchLastXTransactions";
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String GP_TXN_STATUS = "TXNSTATUS";
			private static final String REQUEST_PAYLOAD = "requestPayload";
			private static final String NUMBER_OF_LAST_X_TXN = "NUMBER_OF_LAST_X_TXN";
			private static final String SIZE = "size";
			private static final String NUMBER_OF_DAYS = "NUMBER_OF_DAYS";
			private static final String FROM_DATE = "fromDate";
			private static final String TO_DATE = "toDate";
			private static final String MSISDN = "MSISDN";
			private static final String RESELLER_MSISDN = "resellerMsisdn";
			private static final String REPORT_NAME = "reportName";
			private static final String LOGINID = "LOGINID";
			private static final String RESELLER_ID = "resellerId";
			private static final String RECEIVER_MSISDN = "RECEIVER_MSISDN";
			private static final String RECEIVERMSISDN = "receiverMSISDN";
			private static final String SERVICE_TYPE = "SERVICE_TYPE";
			private static final String SERVICETYPE = "serviceType";
			private static final String REQUEST = "request";
			private static final String GP_RESPONSE_TYPE = "TYPE";
			private static final String REQ_STATUS = "reqStatus";
			private static final String MESSAGE = "message";
			private static final String EXT_REF_NUM = "extRefNum";
			private static final String LIST = "list";
			private static final String ERSREFERENCE = "ersReference";
			private static final String TXNID = "TXNID";
			private static final String TIMESTAMP = "timestamp";
			private static final String TXNDATETIME = "TXNDATETIME";
			private static final String RESULTCODE = "resultCode";
			private static final String TXNSTATUS = "txnStatus";
			private static final String TRANSACTIONAMOUNT = "transactionAmount";
			private static final String TXNAMOUNT = "TXNAMOUNT";
			private static final String TRANSACTIONTYPE = "TransactionType";
			private static final String TRFTYPE = "TRFTYPE";
			private static final String LISTTRANSACTIONDETAILS = "TXNDETAILS";
			private static final String LISTTRANSACTIONDETAIL = "TXNDETAIL";

			private static final String RESPONSE_TYPE = "responseType";
		}

		private static final class Txe
		{
			private static final String RECEIVER_ID = "receiverId";
			private static final String RESULTCODE = "resultCode";
			private static final String RESPONSE = "response";
			private static final String RESULT_CODE = "resultCode";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String MESSAGE = "message";
			private static final String RESULT_DESCRIPTION = "resultDescription";
		}
	}

	public class TransactionDetailsV2 {

		public List<com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.TransactionDetail> getTXNDETAIL() {
			return TXNDETAIL;
		}

		public void setTXNDETAIL(List<com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.TransactionDetail> TXNDETAIL) {
			this.TXNDETAIL = TXNDETAIL;
		}

		@JsonProperty("TXNDETAIL")
		List<com.seamless.ers.standardlink.transformers.FetchLastXTransactionsTransformer.TransactionDetail> TXNDETAIL;



	}

	public class TransactionDetail {
		public String getTXNID() {
			return TXNID;
		}

		public void setTXNID(String TXNID) {
			this.TXNID = TXNID;
		}

		public String getTXNDATETIME() {
			return TXNDATETIME;
		}

		public void setTXNDATETIME(String TXNDATETIME) {
			this.TXNDATETIME = TXNDATETIME;
		}

		public String getTRFTYPE() {
			return TRFTYPE;
		}

		public void setTRFTYPE(String TRFTYPE) {
			this.TRFTYPE = TRFTYPE;
		}

		public String getTXNSTATUS() {
			return TXNSTATUS;
		}

		public void setTXNSTATUS(String TXNSTATUS) {
			this.TXNSTATUS = TXNSTATUS;
		}

		public String getTXNAMOUNT() {
			return TXNAMOUNT;
		}

		public void setTXNAMOUNT(String TXNAMOUNT) {
			this.TXNAMOUNT = TXNAMOUNT;
		}

		public String getRECEIVERMSISDN() {
			return RECEIVERMSISDN;
		}

		public void setRECEIVERMSISDN(String RECEIVERMSISDN) {
			this.RECEIVERMSISDN = RECEIVERMSISDN;
		}

		@JsonProperty("TXNID")
		String TXNID;
		@JsonProperty("TXNDATETIME")
		String TXNDATETIME;
		@JsonProperty("TRFTYPE")
		String TRFTYPE;
		@JsonProperty("TXNSTATUS")
		String TXNSTATUS;
		@JsonProperty("TXNAMOUNT")
		String TXNAMOUNT;
		@JsonProperty("RECEIVERMSISDN")
		String RECEIVERMSISDN;

		public TransactionDetail() {
		}


	}
}
