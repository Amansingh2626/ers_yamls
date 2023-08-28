"dms": {
    "resultCode": ${response.resultCode?string.computer!400},
    "resultDescription": "${response.resultDescription!""}",
    "batchId": "${response.batchId!""}"
    <#if response.resellerData??>,
    "resellerId": "${response.resellerData.resellerId!""}",
    "resellerName": "${response.resellerData.resellerName!""}",
    "resellerParentId": "${response.resellerData.parentResellerId!""}",
    "resellerPath": "${response.resellerData.resellerPath!""}",
    "resellerStatus": "${response.resellerData.status!""}"
    </#if>
}
