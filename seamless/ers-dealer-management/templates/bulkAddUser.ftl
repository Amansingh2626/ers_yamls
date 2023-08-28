"dms":
[<#list response.resellerUserAddTransactions as responseObj>
    {
    "resultCode": ${responseObj.resultCode?string.computer!400},
    "resultDescription": "${responseObj.resultDescription!""}",
    "batchId": "${responseObj.batchId!""}"
       <#if responseObj.users??>,
          "resellerInfo": {
          "resellerId": "${responseObj.resellerId!""}",
          "resellerIdType": "${responseObj.resellerIdType!""}",
          "users":[<#list responseObj.users as user>
                        {
                        "id":"${(user.userId)!'N/A'}",
                        "roleId":"${(user.roleId)!'N/A'}",
                        "phone":"${(user.phone)!'N/A'}",
                        "name":"${(user.name)!'N/A'}",
                        "email":"${(user.email)!'N/A'}"
                        }
                        <#sep>,
                   </#list>]
           }
       </#if>
}
<#sep>,
</#list>]
