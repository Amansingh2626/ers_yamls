package com.seamless.ers.standardlink.transformers;

import com.seamless.ers.interfaces.ersifcommon.dto.ERSHashtableParameter;
import com.seamless.ers.interfaces.ersifextlink.dto.AccountTransactionResponse;
import com.seamless.ers.standardlink.config.StandardLinkConfig;
import com.seamless.ers.standardlink.model.common.ERSIOTransformer;
import com.seamless.ers.standardlink.model.common.ResultCode;
import com.seamless.ers.standardlink.model.configs.ProviderConfigurations;
import com.seamless.ers.standardlink.model.exception.TransformerException;
import com.seamless.ers.standardlink.model.internal.StandardLinkResultCodes;
import com.seamless.ers.standardlink.utilities.StandardLinkUtilities;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.seamless.ers.standardlink.model.internal.StandardLinkResultCodes.SUCCESS;

@Component
public class EsbTopupTransformer implements ERSIOTransformer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(EsbTopupTransformer.class);

	@Autowired
	private StandardLinkConfig standardLinkConfig;

	@Override
	public Message<?> transformOutboundRestRequest(Message<?> message) throws TransformerException
	{
		EtmPoint point = EtmManager.getEtmMonitor().createPoint("EsbTopupTransformer :: transformOutboundSoapRequest");
		try
		{
			LinkedCaseInsensitiveMap<Object> requestFields;

			if (message.getPayload() instanceof LinkedHashMap)
			{
				requestFields = StandardLinkUtilities.convertToCaseInsensitiveMap((Map<String, Object>) message.getPayload());
			}
			else
			{
				requestFields = (LinkedCaseInsensitiveMap<Object>) message.getPayload();
			}

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();
			LinkedCaseInsensitiveMap<Object> extraFields = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.EXTRA_FIELDS);
			LinkedCaseInsensitiveMap<Object> amountMap = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.AMOUNT);
			LinkedCaseInsensitiveMap<Object> accountMap = (LinkedCaseInsensitiveMap<Object>) requestFields.get(Constants.Gp.ACCOUNT);

			Map<String, Object> topupRequest = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> requestBody = Collections.synchronizedMap(new LinkedHashMap<>());
			String createdDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date());
			requestBody.put("createdDate", createdDate);
			requestBody.put("referenceId", requestFields.get("ersReference").toString());
			Map<String, Object> orderItem = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> itemPrice = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> product = Collections.synchronizedMap(new LinkedHashMap<>());
			ArrayList<Map<String, Object>> characteristic = new ArrayList<>();
			Map<String, Object> actionCharacteristic = Collections.synchronizedMap(new LinkedHashMap<>());
			actionCharacteristic.put("name", "action");
			actionCharacteristic.put("value", operationFields.get("actionName"));
			characteristic.add(actionCharacteristic);
			product.put("characteristic", characteristic);
			itemPrice.put("product", product);
			Map<String, Object> price = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, Object> taxIncludedAmount = Collections.synchronizedMap(new LinkedHashMap<>());
			taxIncludedAmount.put("value", String.valueOf((int) Double.parseDouble(String.valueOf(amountMap.get("value")))));
			price.put("taxIncludedAmount", taxIncludedAmount);
			Map<String, Object> baseAmount = Collections.synchronizedMap(new LinkedHashMap<>());
			baseAmount.put("value", String.valueOf((int) Double.parseDouble(String.valueOf(amountMap.get("value")))));
			//price.put("baseAmount", taxIncludedAmount);
			itemPrice.put("price", price);
			orderItem.put("itemPrice", itemPrice);
			Map<String, Object> billingAccount = Collections.synchronizedMap(new LinkedHashMap<>());
			billingAccount.put("id", accountMap.get("accountId"));
			orderItem.put("billingAccount", billingAccount);
			requestBody.put("orderItem", orderItem);
			Map<String, Object> channel = Collections.synchronizedMap(new LinkedHashMap<>());
			if (operationFields.get("roleBackResellerType") != null && operationFields.containsKey("roleBackResellerType") && !StringUtils.isEmpty(operationFields.get("roleBackResellerType")))
			{
				List<String> roleBackResellerType = Arrays.asList(String.valueOf(operationFields.get("roleBackResellerType")).split(","));
				LOGGER.info("roleBackResellerType " + roleBackResellerType);
				if (roleBackResellerType != null && roleBackResellerType.contains(extraFields.get("reseller_type")))
				{
					channel.put("rollback", "YES");
				}
			}
			else
			{
				channel.put("rollback", "NO");
			}
			channel.put("role", operationFields.get("role"));
			if (operationFields.containsKey("id") && operationFields.get("id") != null)
			{
				channel.put("id", operationFields.get("id"));
			}
			channel.put("name", operationFields.get("name"));
			channel.put("channelID", extraFields.get("reseller_type").toString());
			requestBody.put("channel", channel);
			Map<String, Object> relatedParty = Collections.synchronizedMap(new LinkedHashMap<>());
			relatedParty.put("id", extraFields.get("SENDER_MSISDN").toString());
			requestBody.put("relatedParty", relatedParty);
			topupRequest.put("requestBody", requestBody);
			return MessageBuilder.withPayload(topupRequest)
					.copyHeaders(message.getHeaders())
					.setHeader(Constants.Gp.SOURCE_SYSTEM_ID, operationFields.get("sourceSystemId"))
					.setHeader(Constants.Gp.CHANNEL_MEDIA, operationFields.get("channelMedia"))
					.setHeaderIfAbsent(Constants.Gp.BUSSINESS_UNIT, operationFields.get("businessUnit"))
					.setHeaderIfAbsent(HttpHeaders.AUTHORIZATION, getBasicToken())
					.setHeaderIfAbsent(HttpHeaders.ACCEPT, "application/json")
					.build();
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
	public Object transformInboundRestResponse(Object incomingResponse) throws TransformerException
	{

		EtmPoint point = EtmManager.getEtmMonitor().createPoint("EsbTopupTransformer :: transformInboundSoapResponse");
		AccountTransactionResponse accountTransactionResponse = new AccountTransactionResponse();
		try
		{

			ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
			LinkedCaseInsensitiveMap<Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();

			if (incomingResponse instanceof CompletableFuture)
			{
				incomingResponse = ((CompletableFuture<?>) incomingResponse).get();
			}

			if (incomingResponse instanceof Exception)
			{
				Exception exception = (Exception) incomingResponse;
				ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(StandardLinkResultCodes.FAILED_ON_SERVER), String.valueOf(StandardLinkResultCodes.FAILED_ON_SERVER), String.valueOf(StandardLinkUtilities.getRootCause(exception)));
				accountTransactionResponse.setResultDescription(resultCode.getDescription());
				accountTransactionResponse.setResultCode(Integer.parseInt(resultCode.getInternalResultCode()));
			}

			Map<String, Object> externalResponse = (LinkedHashMap<String, Object>) incomingResponse;
			if(externalResponse.containsKey("responseBody"))
			{
				externalResponse = (Map<String, Object>) externalResponse.get("responseBody");
			}

			if (!externalResponse.isEmpty() && externalResponse.containsKey(Constants.Gp.REMARKS) && String.valueOf(externalResponse.get(Constants.Gp.REMARKS)).equals(Constants.Gp.SUCCESS))
			{
				String resultCodeValue = (String) externalResponse.get(Constants.Gp.ERROR_CODE);
				String defaultMessage = String.valueOf(externalResponse.get(Constants.Gp.REMARKS));
				ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(SUCCESS), String.valueOf(SUCCESS), defaultMessage);
				accountTransactionResponse.setResultCode(Integer.parseInt(resultCode.getInternalResultCode()));
				accountTransactionResponse.setResultDescription(resultCode.getDescription());
				accountTransactionResponse.setField(Constants.Txe.REMARKS, String.valueOf(externalResponse.get(Constants.Gp.REMARKS)));
				accountTransactionResponse.setField(Constants.Txe.REFERENCE_ID, String.valueOf(externalResponse.get("referenceId")));
				accountTransactionResponse.setField(Constants.Txe.NATIVE_RESULT_CODE, resultCodeValue);
				accountTransactionResponse.setField(Constants.Txe.NATIVE_RESULT_DESCRIPTION, defaultMessage);
				accountTransactionResponse.setField(Constants.Txe.LINK_TYPE, (String) operationFields.get(Constants.Gp.LINK_TYPE));
				accountTransactionResponse.setField(Constants.Txe.LINK_NODE_ID, (String) operationFields.get(Constants.Gp.LINK_NODE_ID));
			}
			else if (!externalResponse.isEmpty() && externalResponse.containsKey("errorCode") && externalResponse.containsKey("errorDescription"))
			{
				String errorCode = String.valueOf(externalResponse.get("errorCode"));
				String errorDescription = String.valueOf(externalResponse.get("errorDescription"));
				ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(externalResponse.get("errorCode")), String.valueOf(externalResponse.get("errorCode")), errorDescription);
				if (resultCode == null)
				{
					accountTransactionResponse.setResultDescription(errorDescription);
					accountTransactionResponse.setResultCode(Integer.parseInt(String.valueOf(externalResponse.get("errorCode"))));

				}
				else
				{
					accountTransactionResponse.setResultDescription(errorDescription);
					accountTransactionResponse.setResultCode(Integer.parseInt(resultCode.getInternalResultCode()));
				}
				accountTransactionResponse.setField(Constants.Txe.NATIVE_RESULT_CODE, errorCode);
				accountTransactionResponse.setField(Constants.Txe.NATIVE_RESULT_DESCRIPTION,errorDescription);
				accountTransactionResponse.setField(Constants.Txe.LINK_TYPE, (String) operationFields.get(Constants.Gp.LINK_TYPE));
				accountTransactionResponse.setField(Constants.Txe.LINK_NODE_ID, (String) operationFields.get(Constants.Gp.LINK_NODE_ID));
				populateChargingSystemIp(operationFields);
				return accountTransactionResponse;
			}
			else
			{
				String defaultMessage = externalResponse.get(Constants.Gp.ERROR_DESC) != null ? String.valueOf(externalResponse.get(Constants.Gp.ERROR_DESC)) : String.valueOf(externalResponse.get(Constants.Gp.MESSAGE));
				String resultCodeValue = externalResponse.get(Constants.Gp.ERROR_CODE) != null ? String.valueOf(externalResponse.get(Constants.Gp.ERROR_CODE)) : String.valueOf(externalResponse.get(Constants.Gp.RESULT_CODE));
				ResultCode resultCode = standardLinkConfig.getResultCodeFor(Constants.Gp.PROVIDER, Constants.Gp.OPERATION, String.valueOf(StandardLinkResultCodes.INACCESSIBLE_SERVER_URL), resultCodeValue, defaultMessage);
				accountTransactionResponse.setResultDescription(resultCode.getDescription());
				accountTransactionResponse.setResultCode(Integer.parseInt(resultCode.getInternalResultCode()));
				accountTransactionResponse.setField(Constants.Txe.NATIVE_RESULT_CODE, resultCodeValue);
				accountTransactionResponse.setField(Constants.Txe.NATIVE_RESULT_DESCRIPTION, defaultMessage);
				accountTransactionResponse.setField(Constants.Txe.LINK_TYPE, (String) operationFields.get(Constants.Gp.LINK_TYPE));
				accountTransactionResponse.setField(Constants.Txe.LINK_NODE_ID, (String) operationFields.get(Constants.Gp.LINK_NODE_ID));
				populateChargingSystemIp(operationFields);
				return accountTransactionResponse;
			}
			populateChargingSystemIp(operationFields);
			return accountTransactionResponse;
		}
		catch (Exception e)
		{
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

	private void populateChargingSystemIp(LinkedCaseInsensitiveMap<Object> operationFields) throws MalformedURLException {
		Object fetchChargingSystemIp = operationFields.get("fetchChargingSystemIp");
		Object serverUrl = operationFields.get(Constants.Gp.SERVER_URL);
		LOGGER.debug("Fetch chanrging system Ip : " + fetchChargingSystemIp);
		LOGGER.debug("Charging system url for ESB : " + serverUrl);
		if (Objects.nonNull(fetchChargingSystemIp) && Boolean.valueOf(String.valueOf(fetchChargingSystemIp))){
			ERSHashtableParameter fields = accountTransactionResponse.getFields();
			URL url = new URL(serverUrl.toString());
			LOGGER.debug("Charging system Ip is : " + url.getHost());
			fields.put("info7" , url.getHost());
			accountTransactionResponse.setFields(fields);
		}
	}
	AccountTransactionResponse accountTransactionResponse = new AccountTransactionResponse();

	private static final class Constants
	{
		private static final class Gp
		{
			private static final String EXTRA_FIELDS = "extraFields";
			private static final String REMARKS = "remarks";
			private static final String SOURCE_SYSTEM_ID = "sourceSystemId";
			private static final String CHANNEL_MEDIA = "channelMedia";
			private static final String BUSSINESS_UNIT = "businessUnit";
			private static final String PROVIDER = "gp";
			private static final String OPERATION = "esbTopup";
			private static final String SUCCESS = "SUCCESS";
			private static final String AMOUNT = "amount";
			private static final String ACCOUNT = "account";
			private static final String ERROR_CODE = "errorCode";
			private static final String ERROR_DESC = "errorDesc";
			private static final String MESSAGE = "message";
			private static final String RESULT_CODE = "resultCode";
			private static final String LINK_TYPE = "linkType";
			private static final String LINK_NODE_ID = "linkNodeId";
			private static final String SERVER_URL = "server_url";

		}

		private static final class Txe
		{
			private static final String REMARKS = "remarks";
			private static final String REFERENCE_ID = "referenceId";
			private static final String NATIVE_RESULT_CODE = "NativeResultCode";
			private static final String NATIVE_RESULT_DESCRIPTION = "NativeResultDescription";
			private static final String LINK_TYPE = "LinkType";
			private static final String LINK_NODE_ID = "LinkNodeId";
		}
	}

	private String getBasicToken()
	{

		ProviderConfigurations providerConfigurations = standardLinkConfig.getProviders().get(Constants.Gp.PROVIDER);
		Map<String, Object> operationFields = providerConfigurations.getOperations().get(Constants.Gp.OPERATION).getFields();

		String str = (operationFields.get("username") == null ? "" : operationFields.get("username")) + ":" + (operationFields.get("password") == null ? "" : operationFields.get("password"));
		return "Basic " + Base64Utils.encodeToString(str.getBytes(StandardCharsets.UTF_8));

	}
}







