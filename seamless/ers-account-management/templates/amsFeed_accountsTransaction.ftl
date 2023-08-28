"ams.ersReference": "${response.getErsReference()!""}",
"ams.resultcode": ${response.response.resultCode!400},
"ams.resultMessage": "${response.response.resultDescription!""}"<#if response.response.getAccountTransactionResponses()??>,</#if>
<#compress>
    <#if response.response.getAccountTransactionResponses()??>
        <#assign acctTransactions=response.response.getAccountTransactionResponses()/>
        "ams.ersReference": "${response.getErsReference()!""}",
        <#list transaction as acctTransactions>
            "ams.balanceBefore":"${transaction.getBalanceBefore()}",
            "ams.balanceAfter":"${transaction.getBalanceAfter()}",
            "ams.expiryBefore":"${transaction.getExpiryBefore()}",
            "ams.expiryAfter":"${transaction.getExpiryAfter()}",
            "ams.accountClassId":"${transaction.getAccountClassId()}",
            "ams.accountClassExpiry":"${transaction.getAccountClassExpiry()}",
            <#assign accountData=transaction.getAccountData()>
            "ams.account":"${accountData.getAccountId()}",
            "ams.accountStatus":"${accountData.getStatus()}",
            "ams.account":"${account.getAccount().getAccountId()}",
            "ams.accountTypeId":"${account.getAccount().getAccountTypeId()}"
        </#list>
    </#if>
</#compress>