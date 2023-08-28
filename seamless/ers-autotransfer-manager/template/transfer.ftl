{
<#compress>
  "product": {
    "productSKU":  "${configuration.getProductSKU()}",
    "amount": {
      "currency": "${configuration.getDefaultCurrency()}",
      "value": "<#if resellerInformation.getThresholdUpperLimit()?? && resellerInformation.getResellerBalance()??>   <#if (resellerInformation.getThresholdUpperLimit() - resellerInformation.getResellerBalance()) <= 0> ${resellerInformation.getTransferAmount()?string.computer}  <#else>      ${(resellerInformation.getThresholdUpperLimit() - resellerInformation.getResellerBalance())?string.computer} </#if> <#else>  ${resellerInformation.getTransferAmount()?string.computer} </#if>"
    }
  },
  "receiver": {
    "accountTypeId": "${resellerInformation.getAccountTypeId()}",
    "id": "${resellerInformation.getResellerId()}",
    "type": "RESELLERID"
  },
  "sender": {
    "accountTypeId": "${resellerInformation.getAccountTypeId()}",
    "id": "${resellerInformation.getParentResellerId()}",
    "type": "RESELLERID"
  },
  "transactionProperties": {
  }
  </#compress>
}
