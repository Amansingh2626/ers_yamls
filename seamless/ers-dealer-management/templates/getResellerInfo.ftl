"dms": {
    "resultCode": ${response.resultCode?string.computer!400},
    "resultDescription": "${response.resultDescription!""}"
    <#if response.resellerInfo??>,
    "resellerInfo": {
        "reseller": {
            "resellerId": "${(response.resellerInfo.resellerData.resellerId)!""}",
            "resellerName": "${(response.resellerInfo.resellerData.resellerName)!""}",
            "distributorId": "${(response.resellerInfo.resellerData.distributorId)!""}",
            "contractId": "${(response.resellerInfo.resellerData.contractId)!""}",
            "resellerMSISDN": "${(response.resellerInfo.resellerData.resellerMSISDN)!""}",
            "parentResellerId": "${(response.resellerInfo.resellerData.parentResellerId)!""}",
            "parentResellerName": "${(response.resellerInfo.resellerData.parentResellerName)!""}",
            "resellerPath": "${(response.resellerInfo.resellerData.resellerPath)!""}",
            "resellerTypeId": "${(response.resellerInfo.resellerData.resellerTypeId)!""}",
            "resellerTypeName": "${(response.resellerInfo.resellerData.resellerTypeName)!""}",
            "status": "${(response.resellerInfo.resellerData.status)!""}"
        },
    "users":[<#list response.resellerInfo.users as user>
       {
           "id":"${(user.userId)!'N/A'}",
           "roleName":"${(user.roleName)!'N/A'}",
           "roleId":"${(user.roleId)!'N/A'}"
       }
       <#sep>,
       </#list>]
    }
    </#if>
}
