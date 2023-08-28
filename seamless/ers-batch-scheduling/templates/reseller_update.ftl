{
<#compress>
    "batchId": "${request.batchId}",
    "importType": "${request.importType}",
    "resellers": [
    <#list request.requestObjectsList as resellerList>
    {
        "recordId": ${resellerList?counter},
        "dealerData" : {
            "extraParams": {
            "parameters": {
            <#list resellerList.extraParams?keys as key>
                <#if (resellerList.extraParams[key]?? && resellerList.extraParams[key] != "")>
                    "${key}" : "${resellerList.extraParams[key]}"<#if key_has_next>,</#if>
                </#if>
            </#list>
            }
            }
        },
        "dealerPrincipal" : {
            "id": "${resellerList.fields['resellerId']}",
            "type": "RESELLERID"
        }
    }<#if resellerList_has_next>,</#if>
    </#list>
    ]
</#compress>
}