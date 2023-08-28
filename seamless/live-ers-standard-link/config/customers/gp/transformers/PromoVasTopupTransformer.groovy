package com.seamless.ers.standardlink.transformers;

import com.seamless.ers.interfaces.ersifextlink.dto.AccountTransactionResponse;
import com.seamless.ers.standardlink.model.common.ERSIOTransformer;
import com.seamless.ers.standardlink.model.exception.TransformerException;
import com.seamless.ers.standardlink.model.internal.StandardLinkResultCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PromoVasTopupTransformer implements ERSIOTransformer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(PromoVasTopupTransformer.class);
	AccountTransactionResponse accountTransactionResponse = new AccountTransactionResponse();

	@Override
	public Object transformInboundRestResponse(Object incomingResponse) throws TransformerException
	{
		accountTransactionResponse.setResultCode(StandardLinkResultCodes.SUCCESS);
		accountTransactionResponse.setResultDescription("Success");
		return accountTransactionResponse;
	}
}