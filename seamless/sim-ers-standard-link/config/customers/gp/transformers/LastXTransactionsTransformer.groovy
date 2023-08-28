package com.seamless.ers.standardlink.transformers;

import com.seamless.ers.standardlink.model.common.constants.StandardLinkConstants;
import com.seamless.ers.standardlink.config.StandardLinkConfig;
import com.seamless.ers.standardlink.model.common.ERSIOTransformer;
import com.seamless.ers.standardlink.model.common.ResultCode;
import com.seamless.ers.standardlink.model.configs.ProviderConfigurations;
import com.seamless.ers.standardlink.model.exception.TransformerException;
import com.seamless.ers.standardlink.model.internal.StandardLinkResultCodes;
import com.seamless.ers.standardlink.utilities.SendSmsNotificationUtility;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component
public class LastXTransactionsTransformer implements ERSIOTransformer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(LastXTransactionsTransformer.class);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Gp.DATE_FORMAT);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Autowired
	SendSmsNotificationUtility smsNotificationUtility;
	private Object language;


	@Override
	public Message<?> transformInboundRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("LastXTransactionsTransformer :: transformOutboundRequest");

		try
		{
			LinkedCaseInsensitiveMap<Object> requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			LinkedCaseInsensitiveMap<Object> command = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.COMMAND);
			if (command != null)
			{
				requestFields.putAll(command);
			}
			LOGGER.info("Forming request to BI to fetch the last transaction details for operation" + Constants.Gp.OPERATION);

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			Map<String, Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();

			Map<String, Object> request = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> fieldsMap = Collections.synchronizedMap(new LinkedHashMap<>());

			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Map> systemToken = objectMapper.readValue(String.valueOf(message.getHeaders().get(StandardLinkConstants.Headers.SYSTEM_TOKEN)), Map.class);
			if (systemToken != null && systemToken.size() > 0)
			{
				Map<String, String> initiatorInfo = (Map) systemToken.get("context").get("initiator");
				language = initiatorInfo.get("language");
			}
			else
			{
				LOGGER.info("System token not found for LastTransferStatusUssdTransformer.");
			}

			String days = null;
			if (requestFields.containsKey(Constants.Gp.NUMBER_OF_DAYS) && requestFields.get(Constants.Gp.NUMBER_OF_DAYS) != null && !StringUtils.isEmpty(requestFields.get(Constants.Gp.NUMBER_OF_DAYS).toString()))
			{
				if (Integer.valueOf(String.valueOf(requestFields.get(Constants.Gp.NUMBER_OF_DAYS))) >= 0)
				{
					days = requestFields.get(Constants.Gp.NUMBER_OF_DAYS).toString();
					requestFromDateToDate(fieldsMap, days);
				}
			}
			else
			{
				days = String.valueOf(operationFields.get(Constants.Gp.DEFAULTDAYS));
				requestFromDateToDate(fieldsMap, days);
			}

			if (requestFields.containsKey(Constants.Gp.NUMBER_OF_LAST_X_TXN) && requestFields.get(Constants.Gp.NUMBER_OF_LAST_X_TXN) != null && !StringUtils.isEmpty(requestFields.get(Constants.Gp.NUMBER_OF_LAST_X_TXN).toString()))
			{
				if (Integer.valueOf(String.valueOf(requestFields.get(Constants.Gp.NUMBER_OF_LAST_X_TXN))) >= 0)
				{
					fieldsMap.put(Constants.Gp.SIZE, String.valueOf(requestFields.get(Constants.Gp.NUMBER_OF_LAST_X_TXN)));
				}
			}
			else
			{
				fieldsMap.put(Constants.Gp.SIZE, String.valueOf(operationFields.get(Constants.Gp.DEFAULTSIZE)));
			}

			if (requestFields.containsKey(Constants.Gp.RECEIVER_MSISDN) && requestFields.get(Constants.Gp.RECEIVER_MSISDN) != null && !StringUtils.isEmpty(requestFields.get(Constants.Gp.RECEIVER_MSISDN).toString()))
			{
				fieldsMap.put(Constants.Gp.TARGETMSISDN, requestFields.get(Constants.Gp.RECEIVER_MSISDN));
			}
			else if (requestFields.containsKey(Constants.Gp.MSISDN2) && requestFields.get(Constants.Gp.MSISDN2) != null && !StringUtils.isEmpty(requestFields.get(Constants.Gp.MSISDN2).toString()))
			{
				fieldsMap.put(Constants.Gp.TARGETMSISDN, requestFields.get(Constants.Gp.MSISDN2));
			}
			else
			{
				fieldsMap.put(Constants.Gp.TARGETMSISDN, String.valueOf(operationFields.get(Constants.Gp.DEFAULTSERVICETYPE)));
			}

			fieldsMap.put(Constants.Gp.REPORTNAME, String.valueOf(operationFields.get(Constants.Gp.REPORTNAME)));

			if (requestFields.containsKey(Constants.Gp.SERVICE_TYPE) && requestFields.get(Constants.Gp.SERVICE_TYPE) != null && !StringUtils.isEmpty(requestFields.get(Constants.Gp.SERVICE_TYPE).toString()))
			{
				if (requestFields.get(Constants.Gp.SERVICE_TYPE).equals("C2S"))
				{
					fieldsMap.put(Constants.Gp.TRANSACTIONTYPE, String.valueOf(operationFields.get(Constants.Gp.C2SSERVICETYPE)));
				}
				else if (requestFields.get(Constants.Gp.SERVICE_TYPE).equals("C2C"))
				{
					fieldsMap.put(Constants.Gp.TRANSACTIONTYPE, String.valueOf(operationFields.get(Constants.Gp.C2CSERVIVETYPE)));
				}
				else if (requestFields.get(Constants.Gp.SERVICE_TYPE).equals("O2C"))
				{
					fieldsMap.put(Constants.Gp.TRANSACTIONTYPE, String.valueOf(operationFields.get(Constants.Gp.O2CSERVICETYPE)));
				}
			}
			else
			{
				fieldsMap.put(Constants.Gp.TRANSACTIONTYPE, String.valueOf(operationFields.get(Constants.Gp.DEFAULTSERVICETYPE)));
			}
			request.put(Constants.Gp.REQUEST, fieldsMap);
			Message<Map<String, Object>> transferRequestMessage = MessageBuilder.withPayload(request).copyHeaders(message.getHeaders()).setHeader(Constants.Gp.IS_REQUEST_TYPE_TEXT, requestFields.get(Constants.Gp.IS_REQUEST_TYPE_TEXT)).build();
			return transferRequestMessage;
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

	private void requestFromDateToDate(Map<String, Object> fieldsMap, String days)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -Integer.parseInt(days));
		fieldsMap.put(Constants.Gp.FROMDATE, simpleDateFormat.format(calendar.getTime()));
		fieldsMap.put(Constants.Gp.TODATE, simpleDateFormat.format(new Date()));
	}

	@Override
	public Object transformOutboundResponse(Object outgoingResponse) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("LastXTransactionsTransformer :: transformOutboundResponse");

		try
		{
			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			Map<String, Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();
			String c2sServiceType = String.valueOf(operationFields.get(Constants.Gp.C2SSERVICETYPE));
			String c2cServiceType = String.valueOf(operationFields.get(Constants.Gp.C2CSERVIVETYPE));
			String o2cServiceType = String.valueOf(operationFields.get(Constants.Gp.O2CSERVICETYPE));

			Set<String> c2sServiceTypeSet = new HashSet<>(Arrays.asList(c2sServiceType.split(" ")));
			Set<String> c2cServiceTypeSet = new HashSet<>(Arrays.asList(c2cServiceType.split(" ")));
			Set<String> o2cServiceTypeSet = new HashSet<>(Arrays.asList(o2cServiceType.split(" ")));

			LOGGER.info("Forming GP response for operation " + Constants.Gp.OPERATION);

			LOGGER.info("Successfully received response for operation " + Constants.Gp.OPERATION);

			Map<String, Object> lastXTransactionsResponse = new LinkedHashMap<>();

			if (outgoingResponse instanceof CompletableFuture)
			{
				outgoingResponse = ((CompletableFuture<?>) outgoingResponse).get();
			}

			if (outgoingResponse instanceof Exception)
			{
				Exception exception = (Exception) outgoingResponse;
				lastXTransactionsResponse.put(Constants.Gp.GP_MESSAGE, StandardLinkUtilities.getRootCause(exception));
				lastXTransactionsResponse.put(Constants.Gp.GP_TXN_STATUS, StandardLinkResultCodes.INTERNAL_COMPONENT_ERROR);
				lastXTransactionsResponse.put(Constants.Gp.GP_REQSTATUS, StandardLinkResultCodes.INTERNAL_COMPONENT_ERROR);
				return lastXTransactionsResponse;
			}

			Map<String, Object> responseMap = (Map<String, Object>) outgoingResponse;
			lastXTransactionsResponse.put(Constants.Gp.GP_EXT_REQUEST_DATE, simpleDateFormat.format(new Date()));
			String defaultMessage = String.valueOf(responseMap.get(Constants.Bi.MESSAGE) != null ? responseMap.get(Constants.Bi.MESSAGE) : responseMap.get(Constants.Bi.RESULT_DESCRIPTION));

			if (responseMap.containsKey(Constants.Bi.RESULT_CODE) && !responseMap.get(Constants.Bi.RESULT_CODE).toString().equals(String.valueOf(StandardLinkResultCodes.SUCCESS)))
			{
				Integer resultCodeValue = (Integer) responseMap.get(Constants.Bi.RESULT_CODE);
				ResultCode resultCode = standardLinkConfig.getResultCodeBasedOnLanguage(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(resultCodeValue), String.valueOf(resultCodeValue), defaultMessage, String.valueOf(language));
				lastXTransactionsResponse.put(Constants.Gp.GP_MESSAGE, resultCode.getDescription());
				lastXTransactionsResponse.put(Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
				lastXTransactionsResponse.put(Constants.Gp.GP_REQSTATUS, resultCode.getInternalResultCode());
				return lastXTransactionsResponse;
			}

			Map<String, Object> request = (Map<String, Object>) responseMap.get(Constants.Gp.REQUEST_PAYLOAD);
			if (request.containsKey(Constants.Gp.COMMAND))
			{
				Map<String, Object> requestPayloadCommand = (Map<String, Object>) request.get(Constants.Gp.COMMAND);
				request.putAll(requestPayloadCommand);
			}
			String smsReceiverMsisdn = String.valueOf(request.get("MSISDN1"));
			Object requestType = request.get(Constants.Gp.EXT_REQUEST_TYPE);
			if (Objects.nonNull(requestType))
			{
				if (String.valueOf(requestType).equalsIgnoreCase("EXLSTXTRFREQ"))
				{
					lastXTransactionsResponse.put(Constants.Gp.EXT_REQUEST_TYPE, operationFields.get(Constants.Bi.RESPONSE_TYPE_USSD_XML));
				}
				else
				{
					lastXTransactionsResponse.put(Constants.Gp.EXT_REQUEST_TYPE, operationFields.get(Constants.Bi.RESPONSE_TYPE_USSD_TEXT));
				}
			}

			ResultCode resultCode = getResultCode(responseMap.get(Constants.Bi.RESULT_CODE).toString(), responseMap.get(Constants.Gp.RESULTDESCRIPTION).toString());

			if (resultCode != null)
			{
				lastXTransactionsResponse.put(Constants.Gp.GP_TXN_STATUS, resultCode.getInternalResultCode());
				lastXTransactionsResponse.put(Constants.Gp.GP_REQSTATUS, resultCode.getInternalResultCode());
			}
			else
			{
				lastXTransactionsResponse.put(Constants.Gp.GP_TXN_STATUS, responseMap.get(Constants.Bi.RESULT_CODE).toString());
				lastXTransactionsResponse.put(Constants.Gp.GP_REQSTATUS, responseMap.get(Constants.Bi.RESULT_CODE).toString());
			}


			StringBuilder smsResponseMessageBuilder = getResponseMessage(operationFields, lastXTransactionsResponse, responseMap, defaultMessage, String.valueOf(language));

			LOGGER.info("Successfully formed GP response for operation " + Constants.Gp.OPERATION);
			StringBuilder flashResponseMessageBuilder = getResponseMessage(operationFields, lastXTransactionsResponse, responseMap, defaultMessage, "en");
			lastXTransactionsResponse.put(Constants.Gp.GP_MESSAGE, flashResponseMessageBuilder.toString());
			//Send SMS notification via Notification manager
			smsNotificationUtility.sendSmsNotification("", smsReceiverMsisdn, smsResponseMessageBuilder.toString());
			return lastXTransactionsResponse;
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

	private StringBuilder getResponseMessage(Map<String, Object> operationFields, Map<String, Object> lastXTransactionsResponse, Map<String, Object> responseMap, String defaultMessage,  String languageCode) throws ParseException {
		Map<String,String> staticMessageMap = LastXTransactionsTransformer.Constants.getData(String.valueOf(languageCode));
		ResultCode resultCode;
		StringBuilder responseMessageBuilder = new StringBuilder(staticMessageMap.get(Constants.DEFAULT_MESSAGE)).append(StringUtils.SPACE);
		if (responseMap.get(Constants.Gp.LIST) != null && ((List) responseMap.get(Constants.Gp.LIST)).size() > 0)
		{
			ArrayList<Map<String, Object>> transactions = (ArrayList<Map<String, Object>>) responseMap.get(Constants.Gp.LIST);
			for (int i = 0; i < transactions.size(); i++)
			{
				responseMessageBuilder = responseMessageBuilder.append(i + 1).append("-");
				//TXNID
				if (transactions.get(i).containsKey(Constants.Gp.ERSREFERENCE) && !StringUtils.isEmpty(transactions.get(i).get(Constants.Gp.ERSREFERENCE).toString()))
				{
					responseMessageBuilder.append(staticMessageMap.get(Constants.TXN_ID)).append(transactions.get(i).get(Constants.Gp.ERSREFERENCE).toString()).append(",");
				}
				else
				{
					responseMessageBuilder.append(staticMessageMap.get(Constants.TXN_ID)).append(",");
				}
				//MSISDN
				if (transactions.get(i).containsKey(Constants.Gp.RECEIVERMSISDN) && !StringUtils.isEmpty(transactions.get(i).get(Constants.Gp.RECEIVERMSISDN).toString()))
				{
					responseMessageBuilder.append(staticMessageMap.get(Constants.MSISDN)).append(transactions.get(i).get(Constants.Gp.RECEIVERMSISDN).toString()).append(",");
				}
				else
				{
					responseMessageBuilder.append(staticMessageMap.get(Constants.MSISDN)).append(",");
				}

				//DATE
				if (transactions.get(i).containsKey(Constants.Gp.TIMESTAMP) && !StringUtils.isEmpty(transactions.get(i).get(Constants.Gp.TIMESTAMP).toString()))
				{
					String timestamp = transactions.get(i).get("timestamp").toString();
					String fromFormat = String.valueOf(operationFields.get("bidateformat"));
					String toFormat = String.valueOf(operationFields.get("gpdateformat"));
					SimpleDateFormat fromDateFormat = new SimpleDateFormat(fromFormat);
					SimpleDateFormat toDateFormat = new SimpleDateFormat(toFormat);

					Date date = fromDateFormat.parse(timestamp);
					String formattedTimeStamp = toDateFormat.format(date);
					responseMessageBuilder.append(staticMessageMap.get(Constants.DATE)).append(formattedTimeStamp).append(",");
				}
				else
				{
					responseMessageBuilder.append(staticMessageMap.get(Constants.DATE)).append(",");
				}

				//Commenting it as asked from Gp these fields are not required.
				//Txn Status
				// Adding back Status as GP requested to be present in response. Jira Ticket GPB-1101
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
				}
//
//					//TYPE
//					if (transactions.get(i).containsKey(Constants.Gp.TRANSACTIONTYPE) && !StringUtils.isEmpty(transactions.get(0).get(Constants.Gp.TRANSACTIONTYPE).toString()))
//					{
//						String transactionType = transactions.get(0).get(Constants.Gp.TRANSACTIONTYPE).toString();
//						responseMessageBuilder.append("Type:").append(fetchTxnType(c2sServiceTypeSet, c2cServiceTypeSet, o2cServiceTypeSet, transactionType)).append(",");
//					}
//					else
//					{
//						responseMessageBuilder.append("Type:").append(",");
//					}

				//AMOUNT
				if (transactions.get(i).containsKey(Constants.Gp.TRANSACTIONAMOUNT) && !StringUtils.isEmpty(transactions.get(i).get(Constants.Gp.TRANSACTIONAMOUNT).toString()))
				{
					responseMessageBuilder.append(staticMessageMap.get(Constants.AMOUNT)).append(transactions.get(i).get(Constants.Gp.TRANSACTIONAMOUNT).toString()).append(" BDT").append(",");
				}
				else
				{
					responseMessageBuilder.append(staticMessageMap.get(Constants.AMOUNT)).append(",");
				}

			}

			responseMessageBuilder = new StringBuilder( responseMessageBuilder.substring(0, responseMessageBuilder.length() - 1));
//                    responseMessageBuilder.setCharAt(responseMessageBuilder.lastIndexOf(","), '.');
		}
		return responseMessageBuilder;
	}

	private ResultCode getResultCode(String resultCode, String resultDescription)
	{
		ResultCode responseCode = standardLinkConfig.getResultCodeBasedOnLanguage(
				Constants.Gp.PROVIDER,
				Constants.Gp.OPERATION,
				resultCode,
				resultCode,
				resultDescription,
				String.valueOf(language));
		return responseCode;
	}

	private String fetchTxnType(Set<String> c2sServiceTypeSet, Set<String> c2cServiceTypeSet, Set<String> o2cServiceTypeSet, String transactionType)
	{
		if (c2sServiceTypeSet.contains(transactionType))
			return "C2S";
		else if (c2cServiceTypeSet.contains(transactionType))
			return "C2C";
		else if (o2cServiceTypeSet.contains(transactionType))
			return "O2C";
		return transactionType; //Need to change later as all the types needs to be added in properties.
	}

	private static final class Constants
	{
		private static final String DEFAULT_MESSAGE = "DEFAULT_MESSAGE";
		private static final String TXN_ID = "TXN_ID";
		private static final String DATE = "DATE";
		private static final String MSISDN = "MSISDN";
		private static final String AMOUNT = "AMOUNT";
		private static final class Gp
		{
			private static final String NATIVE_RESULT_CODE = "NativeResultCode";
			private static final String RESULTCODE = "resultCode";
			private static final String GP_REQSTATUS = "REQSTATUS";
			private static final String GP_EXT_REQUEST_DATE = "DATE";
			private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
			private static final String NUMBER_OF_DAYS = "NUMBER_OF_DAYS";
			private static final String DEFAULTDAYS = "defaultDays";
			private static final String NUMBER_OF_LAST_X_TXN = "NUMBER_OF_LAST_X_TXN";
			private static final String SIZE = "size";
			private static final String DEFAULTSIZE = "defaultSize";
			private static final String RECEIVER_MSISDN = "RECEIVER_MSISDN";
			private static final String MSISDN2 = "MSISDN2";
			private static final String TARGETMSISDN = "targetMsisdn";
			private static final String REPORTNAME = "reportName";
			private static final String DEFAULTSERVICETYPE = "defaultServiceType";
			private static final String SERVICE_TYPE = "SERVICE_TYPE";
			private static final String TRANSACTIONTYPE = "transactionType";
			private static final String C2SSERVICETYPE = "C2SServiceType";
			private static final String C2CSERVIVETYPE = "C2CServiceType";
			private static final String O2CSERVICETYPE = "O2CServiceType";
			private static final String REQUEST = "request";
			private static final String FROMDATE = "fromDate";
			private static final String TODATE = "toDate";
			private static final String RESULTDESCRIPTION = "resultDescription";
			private static final String LIST = "list";
			private static final String ERSREFERENCE = "ersReference";
			private static final String RECEIVERMSISDN = "receiverMSISDN";
			private static final String TIMESTAMP = "timestamp";
			private static final String TRANSACTIONAMOUNT = "transactionAmount";
			private static final String PROVIDER = "gp";
			private static final String OPERATION = "lastXTransactions";
			private static final String COMMAND = "COMMAND";
			private static final String GP_TXN_STATUS = "TXNSTATUS";
			private static final String EXT_REQUEST_TYPE = "TYPE";
			private static final String GP_MESSAGE = "MESSAGE";
			private static final String REQUEST_PAYLOAD = "requestPayload";
			private static final String IS_REQUEST_TYPE_TEXT = "IS_REQUEST_TYPE_TEXT";
		}

		private static final class Bi
		{
			private static final String RESULT_CODE = "resultCode";
			private static final String RESULT_DESCRIPTION = "resultDescription";
			private static final String MESSAGE = "message";
			private static final String RESPONSE_TYPE_USSD_XML = "responseTypeUssdXml";
			private static final String RESPONSE_TYPE_USSD_TEXT = "responseTypeUssdText";
		}

		/**
		 * @param language
		 * @return
		 */
		private static Map<String, String> getData(String language) {
			Map<String, String> responseMap = new HashMap<>();
			if (language.equalsIgnoreCase("en")) {
				responseMap.put(DEFAULT_MESSAGE, LastXTransactionsTransformer.Constants.En.DEFAULT_MESSAGE);
				responseMap.put(TXN_ID, En.TXN_ID);
				responseMap.put(DATE, En.DATE);
				responseMap.put(MSISDN, En.MSISDN);
				responseMap.put(AMOUNT, En.AMOUNT);
			} else {
				responseMap.put(DEFAULT_MESSAGE, LastXTransactionsTransformer.Constants.Bn.DEFAULT_MESSAGE);
				responseMap.put(TXN_ID, Bn.TXN_ID);
				responseMap.put(DATE, Bn.DATE);
				responseMap.put(MSISDN, Bn.MSISDN);
				responseMap.put(AMOUNT, Bn.AMOUNT);
			}
			return responseMap;
		}

		private static final class En
		{
			private static final String DEFAULT_MESSAGE = "Transfer Details";
			private static final String TXN_ID = "TxnID:";
			private static final String MSISDN = "MSISDN:";
			private static final String DATE = "DATE:";
			private static final String AMOUNT = "Amount:";
		}

		private static final class Bn
		{
			private static final String DEFAULT_MESSAGE = "স্থানান্তর বিবরণ";
			private static final String TXN_ID = "TxnID:";
			private static final String MSISDN = "MSISDN:";
			private static final String DATE = "তারিখ:";
			private static final String AMOUNT = "পরিমাণ:";
		}
	}
}
