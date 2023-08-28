"dms":
[<#list response.resellerDataTransactions as response>
{
[ADDED]    "resultCode": ${response.resultCode?string.computer!400},
[ADDED]    "resultDescription": "${response.resultDescription!""}",
[ADDED]    "batchId": "${response.batchId!""}"
    <#if response.resellerData??>,
        "resellerId": "${response.resellerData.resellerId!""}",
        "resellerName": "${response.resellerData.resellerName!""}",
        "resellerMSISDN": "${response.resellerData.resellerMSISDN!""}",
        "resellerType": "${response.resellerData.resellerTypeId!""}",
        "resellerParentId": "${response.resellerData.parentResellerId!""}",
        "resellerPath": "${response.resellerData.resellerPath!""}"
    </#if>
    <#sep>,
</#list>]
