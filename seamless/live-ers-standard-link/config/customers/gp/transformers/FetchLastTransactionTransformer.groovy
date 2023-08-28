package com.seamless.ers.standardlink.transformers;

import com.seamless.ers.standardlink.config.StandardLinkConfig;
import com.seamless.ers.standardlink.model.common.ERSIOTransformer;
import com.seamless.ers.standardlink.model.common.ResultCode;
import com.seamless.ers.standardlink.model.configs.ProviderConfigurations;
import com.seamless.ers.standardlink.model.exception.TransformerException;
import com.seamless.ers.standardlink.model.internal.StandardLinkResultCodes;
import com.seamless.ers.standardlink.utilities.StandardLinkUtilities;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmPoint;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;
import java.math.BigDecimal;
import java.math.RoundingMode;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component
public class FetchLastTransactionTransformer implements ERSIOTransformer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(FetchLastTransactionTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Override
	public Message<?> transformInboundRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("FetchLastTransactionTransformer :: transformInboundRequest");

		try
		{

			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.COMMAND);

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();

			Map<String, Object> request = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> fieldsMap = Collections.synchronizedMap(new LinkedHashMap<>());
			fieldsMap.put(Constants.Gp.TRANSACTIONCOUNT, 1);

			if (command.containsKey(Constants.Gp.MSISDN) && command.get(Constants.Gp.MSISDN) != null && !StringUtils.isEmpty(command.get(Constants.Gp.MSISDN).toString()))
			{
				fieldsMap.put(Constants.Gp.RESELLERMSISDN, command.get(Constants.Gp.MSISDN));
				fieldsMap.put(Constants.Gp.REPORTNAME, "last_transaction_report_resellerMsisdn");
				LOGGER.info("Trying to fetch the last transaction for MSISDN: " + command.get(Constants.Gp.MSISDN));
			}
			else if (command.containsKey(Constants.Gp.LOGINID) && command.get(Constants.Gp.LOGINID) != null && !StringUtils.isEmpty(command.get(Constants.Gp.LOGINID).toString()))
			{
				fieldsMap.put(Constants.Gp.RESELLERID, command.get(Constants.Gp.LOGINID));
				fieldsMap.put(Constants.Gp.REPORTNAME, "last_transaction_report_resellerId");
				LOGGER.info("Trying to fetch the last transaction for resellerId: " + command.get(Constants.Gp.LOGINID));
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, -Integer.parseInt(operationFields.get(Constants.Gp.NUMBER_OF_DAYS).toString()));
			fieldsMap.put(Constants.Gp.FROMDATE, simpleDateFormat.format(calendar.getTime()));
			fieldsMap.put(Constants.Gp.TODATE, simpleDateFormat.format(new Date()));
			LOGGER.info("Trying to fetch the last transaction for resellerId: " + command.get(Constants.Gp.LOGINID));
			request.put(Constants.Gp.REQUEST, fieldsMap);

			return MessageBuilder.withPayload(request).copyHeaders(message.getHeaders()).build();
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
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("FetchLastTransactionTransformer :: transformOutboundResponse");

		try
		{
			Map<String, Object> response = new LinkedCaseInsensitiveMap<>();
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();

			if (outgoingResponse instanceof CompletableFuture)
			{
				outgoingResponse = ((CompletableFuture<?>) outgoingResponse).get();
			}
			if (outgoingResponse instanceof Exception)
			{
				Exception exception = (Exception) outgoingResponse;
				response.put(Constants.Gp.MESSAGE, StandardLinkUtilities.getRootCause(exception));
				response.put(Constants.Gp.REQSTATUS, StandardLinkResultCodes.INTERNAL_COMPONENT_ERROR);
				return response;
			}
			Map<String, Object> internalResponse = (LinkedHashMap<String, Object>) outgoingResponse;
			Map<String, Object> request = (Map<String, Object>) internalResponse.get(Constants.Gp.REQUEST_PAYLOAD);
			Map<String, Object> requestPayloadCommand = (Map<String, Object>) request.get(Constants.Gp.COMMAND);
			if (!internalResponse.isEmpty() && internalResponse.containsKey(Constants.Txe.RESULTCODE))
			{
				Integer resultCodeValue = (Integer) internalResponse.get(Constants.Txe.RESULTCODE);
				String defaultMessage = String.valueOf(internalResponse.get(Constants.Txe.RESULT_MESSAGE) != null ? internalResponse.get(Constants.Txe.RESULT_MESSAGE) : internalResponse.get(Constants.Txe.MESSAGE) != null ? internalResponse.get(Constants.Txe.MESSAGE) : internalResponse.get(Constants.Txe.RESULT_DESCRIPTION));
				if (resultCodeValue == StandardLinkResultCodes.SUCCESS)
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Gp.GP_EXT_REQUEST_TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));
					response.put(Constants.Gp.GP_EXT_REQUEST_DATE, (simpleDateFormat.format(new Date())));
					response.put(Constants.Gp.MESSAGE, resultCode.getDescription());
					response.put(Constants.Gp.REQSTATUS, resultCode.getInternalResultCode());
					if (internalResponse.get(Constants.Gp.LIST) != null)
					{
						ArrayList<Map<String, Object>> transactions = (ArrayList<Map<String, Object>>) internalResponse.get(Constants.Gp.LIST);
						if (transactions.size() > 0)
						{
							if (transactions.get(0).containsKey(Constants.Gp.TIMESTAMP) && !StringUtils.isEmpty(transactions.get(0).get(Constants.Gp.TIMESTAMP).toString()))
							{
								response.put(Constants.Gp.TXNDATETIME, (transactions.get(0).get(Constants.Gp.TIMESTAMP).toString()));
							}
							else
							{
								response.put(Constants.Gp.TXNDATETIME, (""));
							}
							Map<String, Object> productRecord = new LinkedCaseInsensitiveMap<>();
							productRecord.put(Constants.Gp.PRODUCTCODE, (transactions.get(0).get(Constants.Gp.PRODUCTSKU).toString()));
							if (transactions.get(0).containsKey(Constants.Gp.TRANSACTIONAMOUNT) && !StringUtils.isEmpty(transactions.get(0).get(Constants.Gp.TRANSACTIONAMOUNT).toString()))
							{
								productRecord.put(Constants.Gp.BALANCE, (transactions.get(0).get(Constants.Gp.TRANSACTIONAMOUNT).toString()));
								String amount = String.valueOf(transactions.get(0).get(Constants.Gp.TRANSACTIONAMOUNT));
								amount = StringUtils.isNotEmpty(amount) ? amount.trim().replaceAll(",",""):"" ;
								BigDecimal roundOfValue = new BigDecimal(amount).setScale(2, RoundingMode.DOWN);
								productRecord.put(Constants.Gp.BALANCE, roundOfValue.toString());
							}
							else
							{
								productRecord.put(Constants.Gp.BALANCE, (""));
							}

							if (transactions.get(0).containsKey(Constants.Gp.PRODUCTNAME) && !StringUtils.isEmpty(transactions.get(0).get(Constants.Gp.PRODUCTNAME).toString()))
							{
								productRecord.put(Constants.Gp.PRODUCTSHORTNAME, (transactions.get(0).get(Constants.Gp.PRODUCTNAME).toString()));
							}
							response.put(Constants.Gp.RECORD, (productRecord));
							if (transactions.get(0).containsKey(Constants.Txe.RESULTCODE) && !StringUtils.isEmpty(transactions.get(0).get(Constants.Txe.RESULTCODE).toString()))
							{
								resultCodeValue = (Integer) internalResponse.get(Constants.Txe.RESULTCODE);
								resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
								response.put(Constants.Gp.TXNSTATUS, resultCode.getInternalResultCode());
							}
							else
							{
								response.put(Constants.Gp.TXNSTATUS, (""));
							}
							if (transactions.get(0).containsKey(Constants.Gp.ERSREFERENCE) && !StringUtils.isEmpty(transactions.get(0).get(Constants.Gp.ERSREFERENCE).toString()))
							{
								response.put(Constants.Gp.TXNID, (transactions.get(0).get(Constants.Gp.ERSREFERENCE).toString()));
							}
							else
							{
								response.put(Constants.Gp.TXNID, (""));
							}
							// should verify.
							if (transactions.get(0).containsKey(Constants.Gp.TRANSACTIONTYPE) && !StringUtils.isEmpty(transactions.get(0).get(Constants.Gp.TRANSACTIONTYPE).toString()))
							{
								response.put(Constants.Gp.TRFTYPE, (transactions.get(0).get(Constants.Gp.TRANSACTIONTYPE).toString()));
							}
							else
							{
								response.put(Constants.Gp.TRFTYPE, (""));
							}
						}
					}

					if (requestPayloadCommand.containsKey(Constants.Gp.EXTREFNUM) && !StringUtils.isEmpty(requestPayloadCommand.get(Constants.Gp.EXTREFNUM).toString()))
					{
						response.put(Constants.Gp.EXTREFNUM, (requestPayloadCommand.get(Constants.Gp.EXTREFNUM).toString()));
					}
					else
					{
						response.put(Constants.Gp.EXTREFNUM, (""));
					}
//					requestPayloadCommand.put(Constants.Txe.RESPONSE, response);
				}
				else
				{
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Gp.MESSAGE, resultCode.getDescription());
					response.put(Constants.Gp.TXNSTATUS, resultCode.getInternalResultCode());
					response.put(Constants.Gp.REQSTATUS, resultCode.getInternalResultCode());
					response.put(Constants.Gp.GP_EXT_REQUEST_TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));
				}
			}
			else
			{
				response.put(Constants.Gp.MESSAGE, "Request has failed on server.");
				response.put(Constants.Gp.TXNSTATUS, StandardLinkResultCodes.FAILED_ON_SERVER);
				response.put(Constants.Gp.REQSTATUS, StandardLinkResultCodes.FAILED_ON_SERVER);
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
			private static final String GP_EXT_REQUEST_TYPE = "TYPE";
			private static final String REQSTATUS = "REQSTATUS";
			private static final String NUMBER_OF_DAYS = "NUMBER_OF_DAYS";
			private static final String RESELLERID = "resellerId";
			private static final String RESELLERMSISDN = "resellerMsisdn";
			private static final String MSISDN = "MSISDN";
			private static final String PROVIDER = "gp";
			private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
			private static final String EXTREFNUM = "EXTREFNUM";
			private static final String COMMAND = "COMMAND";
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String OPERATION = "fetchLastTransaction";
			private static final String MESSAGE = "MESSAGE";
			private static final String TXNSTATUS = "TXNSTATUS";
			private static final String REQUEST_PAYLOAD = "requestPayload";
			private static final String TRANSACTIONCOUNT = "size";
			private static final String FROMDATE = "fromDate";
			private static final String TODATE = "toDate";
			private static final String LOGINID = "LOGINID";
			private static final String REPORTNAME = "reportName";
			private static final String REQUEST = "request";
			private static final String DATE = "date";
			private static final String LIST = "list";
			private static final String TIMESTAMP = "timestamp";
			private static final String TXNDATETIME = "TXNDATETIME";
			private static final String PRODUCTCODE = "PRODUCTCODE";
			private static final String PRODUCTSKU = "productSKU";
			private static final String TRANSACTIONAMOUNT = "transactionAmount";
			private static final String BALANCE = "BALANCE";
			private static final String PRODUCTNAME = "ProductName";
			private static final String PRODUCTSHORTNAME = "PRODUCTSHORTNAME";
			private static final String RECORD = "RECORD";
			private static final String ERSREFERENCE = "ersReference";
			private static final String TXNID = "TXNID";
			private static final String TRANSACTIONTYPE = "TransactionType";
			private static final String TRFTYPE = "TRFTYPE";
			private static final String TYPE = "TYPE";
			private static final String RESPONSE_TYPE = "responseType";
		}

		private static final class Txe
		{
			private static final String RESULTCODE = "resultCode";
			private static final String RESPONSE = "response";
			private static final String RESULT_MESSAGE = "resultMessage";
			private static final String MESSAGE = "message";
			private static final String RESULT_DESCRIPTION = "resultDescription";
		}
	}
}
