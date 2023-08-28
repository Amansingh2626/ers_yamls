/*
 *   Created By   : Bharath SaiSwetha
 *   Email        : saiswetha.bharath@seamless.se
 *   Created Date : 12/02/2022
 *   Time         : 01:10 PM
 *
 *   Copyright(c) 2021. Seamless Distribution Systems AB - All Rights Reserved
 *   Unauthorized copying of this file, via any medium is strictly prohibited. It is proprietary and confidential.
 *
 */

package com.seamless.ers.standardlink.transformers;

import com.seamless.ers.interfaces.ersifextlink.dto.AccountTransactionResponse;
import com.seamless.ers.standardlink.model.common.ERSIOTransformer;
import com.seamless.ers.standardlink.model.exception.TransformerException;
import com.seamless.ers.standardlink.model.internal.StandardLinkResultCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("cCPSValidateTopupTransformer")
public class CCPSValidateTopupTransformer implements ERSIOTransformer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(CCPSValidateTopupTransformer.class);
	AccountTransactionResponse accountTransactionResponse = new AccountTransactionResponse();

	@Override
	public Object transformInboundRestResponse(Object incomingResponse) throws TransformerException
	{
		accountTransactionResponse.setResultCode(StandardLinkResultCodes.SUCCESS);
		accountTransactionResponse.setResultDescription("Success");
		return accountTransactionResponse;
	}
}
