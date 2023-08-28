package com.seamless.ers.standardlink.transformers;

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
	private static final Logger LOGGER = LoggerFactory.getLogger(FetchLastXTransactionsTransformer.class);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	/**
	 * @param message
	 * @return
	 * @throws TransformerException
	 */
	@Override
	public Message<?> transformInboundRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("FetchLastXTransactionsTransformer :: transformOutboundRequest");

		try
		{
			LOGGER.info("FetchLastXTransactionsTransformer calling service ============");
			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.COMMAND);

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();

			Map<String, Object> request = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> fieldsMap = Collections.synchronizedMap(new LinkedHashMap<>());

		//	if (command.containsKey(Constants.Gp.NUMBER_OF_LAST_X_TXN) && command.get(Constants.Gp.NUMBER_OF_LAST_X_TXN) != null && !StringUtils.isEmpty(command.get(Constants.Gp.NUMBER_OF_LAST_X_TXN).toString()))
		//	{
		//		fieldsMap.put(Constants.Gp.SIZE, String.valueOf(command.get(Constants.Gp.NUMBER_OF_LAST_X_TXN)));
		//	}
		//	else
		//	{
				fieldsMap.put(Constants.Gp.SIZE, String.valueOf(operationFields.get(Constants.Gp.NUMBER_OF_LAST_X_TXN)));
		//	}

			String days = null;
			if (command.containsKey(Constants.Gp.NUMBER_OF_DAYS) && command.get(Constants.Gp.NUMBER_OF_DAYS) != null && !StringUtils.isEmpty(command.get(Constants.Gp.NUMBER_OF_DAYS).toString()))
			{
				days = command.get(Constants.Gp.NUMBER_OF_DAYS).toString();
			}
			else
			{
				days = operationFields.get(Constants.Gp.NUMBER_OF_DAYS).toString();
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, -Integer.parseInt(days));
			fieldsMap.put(Constants.Gp.FROM_DATE, simpleDateFormat.format(calendar.getTime()));
			fieldsMap.put(Constants.Gp.TO_DATE, simpleDateFormat.format(new Date()));

			Object receiverMsisdn = null, receiverId = null;
			LOGGER.debug("Request Headers :" + message.getHeaders().toString());
			if (message.getHeaders().containsKey(StandardLinkConstants.TXEConstant.RECEIVER_MSISDN) || message.getHeaders().containsKey(Constants.Txe.RECEIVER_ID))
			{
				try
				{
					receiverMsisdn = message.getHeaders().get(StandardLinkConstants.TXEConstant.RECEIVER_MSISDN);
					receiverId = message.getHeaders().get(Constants.Txe.RECEIVER_ID);
					LOGGER.debug("Fetched receiverId :" + receiverId + " and receiverMsisdn :" + receiverMsisdn + " from header successfully");

				}
				catch (Exception exception)
				{
					LOGGER.error("Failed to fetch receiverId and receiverMsisdn from header ", message.getHeaders(),
							ExceptionUtils.getFullStackTrace(exception));
				}
			}

			if (command.containsKey(Constants.Gp.MSISDN) && command.get(Constants.Gp.MSISDN) != null && !StringUtils.isEmpty(command.get(Constants.Gp.MSISDN).toString()))
			{
				String msisdn = String.valueOf(command.get(Constants.Gp.MSISDN));
				fieldsMap.put(Constants.Gp.RESELLER_MSISDN,msisdn);
				fieldsMap.put(Constants.Gp.REPORT_NAME, "last_X_transactions_report_resellerMsisdn");
				LOGGER.info("Trying to fetch the last transaction for MSISDN: " + command.get(Constants.Gp.MSISDN));
			}
			else if (command.containsKey(Constants.Gp.LOGINID) && command.get(Constants.Gp.LOGINID) != null && !StringUtils.isEmpty(command.get(Constants.Gp.LOGINID).toString()))
			{
				fieldsMap.put(Constants.Gp.RESELLER_ID, command.get(Constants.Gp.LOGINID).toString());
				fieldsMap.put(Constants.Gp.REPORT_NAME, "last_X_transactions_report_resellerId");
				LOGGER.info("Trying to fetch the last transaction for resellerId: " + command.get(Constants.Gp.LOGINID));
			}
			else if (Objects.nonNull(receiverMsisdn))
			{
				fieldsMap.put(Constants.Gp.RESELLER_MSISDN, receiverMsisdn);
				fieldsMap.put(Constants.Gp.REPORT_NAME, "last_X_transactions_report_resellerMsisdn");
				LOGGER.info("Trying to fetch the last transaction for MSISDN fetched from system token: " + command.get(Constants.Gp.MSISDN));
			}
			else if (Objects.nonNull(receiverId))
			{
				fieldsMap.put(Constants.Gp.RESELLER_ID, command.get(Constants.Gp.LOGINID));
				fieldsMap.put(Constants.Gp.REPORT_NAME, "last_X_transactions_report_resellerId");
				LOGGER.info("Trying to fetch the last transaction for resellerId fetched from system token: " + command.get(Constants.Gp.LOGINID));
			}

			if (command.containsKey(Constants.Gp.RECEIVER_MSISDN) && command.get(Constants.Gp.RECEIVER_MSISDN) != null && !StringUtils.isEmpty(command.get(Constants.Gp.RECEIVER_MSISDN).toString()))
			{
				fieldsMap.put(Constants.Gp.RECEIVERMSISDN, command.get(Constants.Gp.RECEIVER_MSISDN));
			}
			else
			{
				fieldsMap.put(Constants.Gp.RECEIVERMSISDN, ".*");
			}
			if (command.containsKey(Constants.Gp.SERVICE_TYPE) && command.get(Constants.Gp.SERVICE_TYPE) != null && !StringUtils.isEmpty(command.get(Constants.Gp.SERVICE_TYPE).toString()))
			{
				String serviceType = String.valueOf(command.get(Constants.Gp.SERVICE_TYPE)).toUpperCase();
				if (serviceType.equalsIgnoreCase("O2C") || serviceType.equalsIgnoreCase("C2C")){
					fieldsMap.put(Constants.Gp.SERVICETYPE, "TRANSFER");
				}else if (serviceType.equalsIgnoreCase("C2S")){
					fieldsMap.put(Constants.Gp.SERVICETYPE, "TOPUP");
				}
			}
			else
			{
				fieldsMap.put(Constants.Gp.SERVICETYPE, ".*");
			}
			request.put(Constants.Gp.REQUEST, fieldsMap);
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
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();

			String dateFormat = String.valueOf(operationFields.get(Constants.Gp.GP_DATE_FORMAT));
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

			response.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));

			if (outgoingResponse instanceof CompletableFuture)
			{
				outgoingResponse = ((CompletableFuture<?>) outgoingResponse).get();
			}
			if (outgoingResponse instanceof Exception)
			{
				Exception exception = (Exception) outgoingResponse;
				response.put(Constants.Gp.GP_MESSAGE, StandardLinkUtilities.getRootCause(exception));
				response.put(Constants.Gp.GP_TXN_STATUS, StandardLinkResultCodes.FAILED);
				response.put(Constants.Gp.GP_REQSTATUS, StandardLinkResultCodes.FAILED);
				return response;
			}
			Map<String, Object> internalResponse = (LinkedHashMap<String, Object>) outgoingResponse;
			LOGGER.info("Printing response ===== 1");
			LOGGER.info(internalResponse.toString());
			Map<String, Object> request = (Map<String, Object>) internalResponse.get(Constants.Gp.REQUEST_PAYLOAD);
			LOGGER.info("Printing response ===== 2");
			LOGGER.info(request.toString());
			Map<String, Object> requestPayloadCommand = (Map<String, Object>) request.get(Constants.Gp.COMMAND);

			LOGGER.info("Response from BI =================");


			if (!internalResponse.isEmpty() && internalResponse.containsKey(Constants.Txe.RESULT_CODE))
			{
				LOGGER.info("Response from BI =================1 ");
				Integer resultCodeValue = (Integer) internalResponse.get(Constants.Txe.RESULT_CODE);
				String defaultMessage = String.valueOf(internalResponse.get(Constants.Txe.RESULT_MESSAGE) != null ? internalResponse.get(Constants.Txe.RESULT_MESSAGE) : internalResponse.get(Constants.Txe.MESSAGE) != null ? internalResponse.get(Constants.Txe.MESSAGE) : internalResponse.get(Constants.Txe.RESULT_DESCRIPTION));
				if (resultCodeValue == StandardLinkResultCodes.SUCCESS)
				{
					LOGGER.info("Response from BI =================2 ");
					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Gp.GP_RESPONSE_TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));

					response.put(Constants.Gp.GP_MESSAGE, "");

					LOGGER.info("Response from BI =================3 ");

					if (requestPayloadCommand.containsKey(Constants.Gp.EXTREFNUM) && !StringUtils.isEmpty(requestPayloadCommand.get(Constants.Gp.EXTREFNUM).toString()))
					{
						response.put(Constants.Gp.EXTREFNUM, (requestPayloadCommand.get(Constants.Gp.EXTREFNUM).toString()));
					}
					else
					{
						response.put(Constants.Gp.EXTREFNUM, (""));
					}

					LOGGER.info("Response from BI =================4 ");

					List<TransactionDetail> transactionsDetailsData=new ArrayList<>();
					StringBuilder responseMessageBuilder = new StringBuilder("Transfer Details");
					if (internalResponse.get(Constants.Gp.LIST) != null)
					{

						LOGGER.info("Response from BI =================5 ");

						ArrayList<Map<String, Object>> transactions = ((ArrayList<Map<String, Object>>) internalResponse.get(Constants.Gp.LIST));
						LOGGER.info("Response from BI =================6 ");
						LOGGER.info(transactions.toString());
						LOGGER.info("Response from BI =================7 ");

						Map<String, Object> transactionDetail = (LinkedHashMap<String, Object>) outgoingResponse;

						LOGGER.info(" Transaction response ====1 ");
						LOGGER.info(transactionDetail.toString());
						LOGGER.info(" Transaction response ====2");

						for (int i = 0; i < transactions.size(); i++)
						{
							//responseMessageBuilder = responseMessageBuilder.append(i + 1).append("-");
				                        responseMessageBuilder = responseMessageBuilder.append(" ").append(i + 1).append("-").append(" ");
                                                        TransactionDetail transactionDetailObject1=new TransactionDetail();
							LOGGER.info(" Transaction response ====3");
							LOGGER.info(transactions.get(i).toString());
							LOGGER.info(" Transaction response ====4");
							if (transactions.get(i).containsKey(Constants.Gp.ERSREFERENCE) && !StringUtils.isEmpty(transactions.get(i).get(Constants.Gp.ERSREFERENCE).toString()))
							{
								transactionDetailObject1.setTXNID((transactions.get(i).get(Constants.Gp.ERSREFERENCE).toString()));
								responseMessageBuilder.append("TxnID:").append(transactions.get(i).get(Constants.Gp.ERSREFERENCE).toString()).append(",");
							}
							else
							{
								transactionDetailObject1.setTXNID("");
								responseMessageBuilder.append("TxnID:").append(",");
							}

							if (transactions.get(i).containsKey(Constants.Gp.RECEIVERMSISDN) && !StringUtils.isEmpty(transactions.get(i).get(Constants.Gp.RECEIVERMSISDN).toString()))
							{
								//transactionDetailObject.put(Constants.Gp.RECEIVER_MSISDN, (transactions.get(i).get(Constants.Gp.RECEIVERMSISDN).toString()));
								transactionDetailObject1.setRECEIVERMSISDN(transactions.get(i).get(Constants.Gp.RECEIVERMSISDN).toString());
								responseMessageBuilder.append("MSISDN:").append(transactions.get(i).get(Constants.Gp.RECEIVERMSISDN).toString()).append(",");
							}else{
								transactionDetailObject1.setRECEIVERMSISDN("");
								responseMessageBuilder.append("MSISDN:").append(",");
							}

							if (transactions.get(i).containsKey(Constants.Gp.TIMESTAMP) && !StringUtils.isEmpty(transactions.get(i).get(Constants.Gp.TIMESTAMP).toString()))
							{
								String transactionDate = transactions.get(i).get(Constants.Gp.TIMESTAMP).toString();
								String bidateformat = operationFields.get(Constants.Gp.BI_DATE_FORMAT).toString();
								SimpleDateFormat simplDateFormat = new SimpleDateFormat(bidateformat);
								Date txnDate = simplDateFormat.parse(transactionDate);

								transactionDetailObject1.setTXNDATETIME(simpleDateFormat.format(txnDate));

								simplDateFormat = new SimpleDateFormat(Constants.Gp.MESSAGE_DATE_FORMAT);
								responseMessageBuilder.append("DATE:").append(simplDateFormat.format(txnDate)).append(",");

							}
							else
							{
								transactionDetailObject1.setTXNDATETIME("");
								responseMessageBuilder.append("DATE:").append(",");
							}
							if (transactions.get(i).containsKey(Constants.Gp.RESULTCODE) && Objects.nonNull(transactions.get(i).get(Constants.Gp.RESULTCODE)) && !StringUtils.isEmpty(transactions.get(i).get(Constants.Gp.RESULTCODE).toString()))
							{
								String code = "";
								String transactionResultCode = String.valueOf(transactions.get(i).get(Constants.Gp.RESULTCODE));
								resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(transactionResultCode), String.valueOf(transactionResultCode), defaultMessage);

								if (resultCode.getInternalResultCode().equalsIgnoreCase(String.valueOf(StandardLinkResultCodes.INTERNAL_SUCCESS))) {

									boolean isNativeResultCodePresent = transactions.get(i).containsKey(Constants.Gp.NATIVE_RESULT_CODE) && Objects.nonNull(transactions.get(i).get(Constants.Gp.NATIVE_RESULT_CODE)) && !StringUtils.isEmpty(transactions.get(i).get(Constants.Gp.NATIVE_RESULT_CODE).toString());
									if (isNativeResultCodePresent && !String.valueOf(transactions.get(i).get(Constants.Gp.NATIVE_RESULT_CODE)).equalsIgnoreCase("0")){
										code = String.valueOf(StandardLinkResultCodes.AMBIGUOUS);
										responseMessageBuilder.append("Status:").append(code).append(":Ambiguous,");
									}else {
										code = String.valueOf(resultCode.getInternalResultCode());
										responseMessageBuilder.append("Status:").append(code).append(":Successful,");
									}
								}
								else {
									code = String.valueOf(StandardLinkResultCodes.FAILED);
									responseMessageBuilder.append("Status:").append(code).append(":Failed,");
								}
								transactionDetailObject1.setTXNSTATUS(code);
							}
							else
							{
								transactionDetailObject1.setTXNSTATUS("");
								responseMessageBuilder.append("Status:").append(",");

							}
							//should verify.
							if (transactions.get(i).containsKey(StandardLinkConstants.TXEConstant.PRODUCT_SKU) && !StringUtils.isEmpty(transactions.get(0).get(StandardLinkConstants.TXEConstant.PRODUCT_SKU).toString()))
							{
								LOGGER.info("==== Going to fetch product ====");
								String productSku = transactions.get(0).get(StandardLinkConstants.TXEConstant.PRODUCT_SKU).toString().toUpperCase();
								LOGGER.info("=== Product is ===" + productSku);
								LOGGER.info("=== Going to fetch transaction type ===");
								String txnType = fetchTxnType(productSku);
								LOGGER.info("=== Transaction type is ===" + txnType);
								transactionDetailObject1.setTRFTYPE(txnType);
								responseMessageBuilder.append("Type:").append(txnType).append(",");
							}
							else
							{
								transactionDetailObject1.setTRFTYPE("");
								responseMessageBuilder.append("Type:").append(",");
							}

							if (transactions.get(i).containsKey(Constants.Gp.TRANSACTIONAMOUNT) && !StringUtils.isEmpty(transactions.get(i).get(Constants.Gp.TRANSACTIONAMOUNT).toString()))
							{
								transactionDetailObject1.setTXNAMOUNT((transactions.get(i).get(Constants.Gp.TRANSACTIONAMOUNT).toString()));
								responseMessageBuilder.append("Amount:").append(transactions.get(i).get(Constants.Gp.TRANSACTIONAMOUNT).toString()).append(" BDT").append(",");
							}
							else
							{
								transactionDetailObject1.setTXNAMOUNT("");
								responseMessageBuilder.append("Amount:").append(",");
							}

							transactionsDetailsData.add(transactionDetailObject1);

						}
						responseMessageBuilder = new StringBuilder( responseMessageBuilder.substring(0, responseMessageBuilder.length() - 1)).append(".");
						response.put(Constants.Gp.GP_MESSAGE,responseMessageBuilder.toString());
					}
					TransactionDetailsV2 transactionDetailsV2=new TransactionDetailsV2();
					transactionDetailsV2.setTXNDETAIL(transactionsDetailsData);
					LOGGER.info("Response from BI =================8 ");
					response.put(Constants.Gp.LISTTRANSACTIONDETAILS, transactionDetailsV2);
					response.put(Constants.Gp.GP_REQSTATUS, StandardLinkResultCodes.INTERNAL_SUCCESS);
					requestPayloadCommand.put(Constants.Txe.RESPONSE, response);
				}
				else
				{
					LOGGER.info("Response from BI =================9 ");

					ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage);
					response.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
					response.put(Constants.Gp.GP_TXN_STATUS, StandardLinkResultCodes.FAILED);
					response.put(Constants.Gp.GP_REQSTATUS, StandardLinkResultCodes.INTERNAL_SUCCESS);
					response.put(Constants.Gp.GP_RESPONSE_TYPE, operationFields.get(Constants.Gp.RESPONSE_TYPE));
				}
			}
			else
			{
				LOGGER.info("Response from BI =================10 ");

				response.put(Constants.Gp.GP_MESSAGE, "Request has failed on server.");
				response.put(Constants.Gp.GP_TXN_STATUS, StandardLinkResultCodes.FAILED);
				response.put(Constants.Gp.GP_REQSTATUS, StandardLinkResultCodes.FAILED);
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

	private String fetchTxnType(String productSku)
	{
		if (productSku.contains("O2C") || productSku.indexOf("O2C") > 0)
			return "O2C";
		else if (productSku.contains("C2C") || productSku.indexOf("O2C") > 0)
			return "C2C";
		return "C2S";
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
			private static final String GP_DATE_FORMAT = "gpdateformat";
			private static final String BI_DATE_FORMAT = "bidateformat";
			private static final String NATIVE_RESULT_CODE = "NativeResultCode";
			private static final String MESSAGE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.s";
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

		public List<TransactionDetail> getTXNDETAIL() {
			return TXNDETAIL;
		}

		public void setTXNDETAIL(List<TransactionDetail> TXNDETAIL) {
			this.TXNDETAIL = TXNDETAIL;
		}

		@JsonProperty("TXNDETAIL")
		List<TransactionDetail> TXNDETAIL;



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
