{
<#compress>
"batchId": "${request.batchId}",
"productVariants": [
<#list request.requestObjectsList as productList>
    {
        "recordId": "${productList?counter}",
        "productCode": "${productList.productCode}",
        "productSKU": "${productList.productSKU}"
        ,"supplierReference": "${(productList.supplierReference?trim)!''}"
        ,"eanCode": "${(productList.eanCode?trim)!''}"
        ,"status": <#if productList.status?? && productList.status="0">"blocked"<#elseif productList.status="1">"available"<#elseif productList.status="2">"decommissioned"<#else>""</#if>
        ,"unitPrice": {
                  "currency": "${(productList.currency?trim)!''}",
                  "price": "${(productList.price?trim)!'0'}",
                  "variablePrice": "${(productList.variablePrice?trim)!'false'}"
         }
         ,"upsellOption": <#if productList.upsellOption?? >"${productList.upsellOption?trim?lower_case}"<#else>"no"</#if>
         ,"upSellProducts": "${(productList.upSellProducts?trim)!''}"
         ,"associateRule": <#if productList.associateRule?? && productList.associateRule?lower_case="y">true<#else>false</#if>
         ,"ruleIds": "${(productList.ruleIds?trim)!''}"
         ,"availableFrom":"${(productList.availableFrom?trim)!(.now?string["yyyy-MM-dd"])}"
         ,"availableUntil":"${(productList.availableUntil?trim)!'2099-12-31'}"
         ,"unitOfMeasure": {
            <#list productList.unitOfMeasure?keys as key>
               <#if (productList.unitOfMeasure[key]?? && productList.unitOfMeasure[key] != "")>
                "${key}" : "${productList.unitOfMeasure[key]}"<#if key_has_next>,</#if>
               </#if>
            </#list>
          }
         ,"weight": {
            <#list productList.weight?keys as key>
               <#if (productList.weight[key]?? && productList.weight[key] != "")>
                "${key}" : "${productList.weight[key]}"<#if key_has_next>,</#if>
               </#if>
            </#list>
         }
         ,"volume": {
            <#list productList.volume?keys as key>
               <#if (productList.volume[key]?? && productList.volume[key] != "")>
                "${key}" : "${productList.volume[key]}"<#if key_has_next>,</#if>
               </#if>
            </#list>
         }
         ,"warrantyPeriod": {
            <#list productList.warrantyPeriod?keys as key>
               <#if (productList.warrantyPeriod[key]?? && productList.warrantyPeriod[key] != "")>
                "${key}" : "${productList.warrantyPeriod[key]}"<#if key_has_next>,</#if>
               </#if>
            </#list>
         }
         ,"extraParameters": {
            <#list productList.extraParameters?keys as key>
               <#if (productList.extraParameters[key]?? && productList.extraParameters[key] != "")>
                "${key}" : "${productList.extraParameters[key]}"<#if key_has_next>,</#if>
               </#if>
            </#list>
         }
    }<#if productList_has_next>,</#if>
</#list>
]
</#compress>
}
