{
<#compress>
    "inventories": [
    <#list request.requestObjectsList as inventList>
        {
        "data" : {
        <#list inventList.data?keys as key>
            "${key}" : "${inventList.data[key]}"<#if key_has_next>,</#if>
        </#list>
        },
        <#list inventList.fields?keys as key>
            "${key}" : "${inventList.fields[key]}"<#if key_has_next>,</#if>
        </#list>
        }<#if inventList?has_next>,</#if>
    </#list>
    ],
    "batchId": "${request.batchId}",
    "importType": "${request.importType}"
</#compress>
}