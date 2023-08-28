"ersReference": "${response.ersReference!""}",
"resultcode": ${response.resultStatus.getCode()!0},
"resultMessage": "${response.resultStatus.name()!""}",
"originalErsReference": "${response.originalErsReference!""}",
"channel": "${response.channel!""}",
"transactionProfile": "${response.transactionProfile!""}",
"transactionType": "${response.transactionType!""}",
"endTime": "${response.endTime?string('dd.MM.yyyy HH:mm:ss')}",
"transactionAmount": "${response.transactionAmount!""}",
"receiverMSISDN": "${response.receiverMSISDN!""}",
"receiverResellerId": "${response.receiverResellerId!""}",
"receiverRegionId": "${response.receiverRegionId!""}",
"receiverBalanceValueBefore": "${response.receiverBalanceValueBefore!""}",
"receiverBalanceValueAfter": "${response.receiverBalanceValueAfter!""}",
"receiverCommission": "${response.receiverCommission!""}",
"receiverAccountType": "${response.receiverAccountType!""}",
"receiverBonusAmount": "${response.receiverBonusAmount!""}",
"receiverResellerName": "${response.receiverResellerName!""}",
"receiverResellerType": "${response.receiverResellerType!""}",
"senderMSISDN": "${response.senderMSISDN!""}",
"senderResellerId": "${response.senderResellerId!""}",
"senderResellerType": "${response.senderResellerType!""}",
"senderResellerPath": "${response.senderResellerPath!""}",
"senderResellerJuridicalName": "${response.senderResellerJuridicalName!""}",
"senderRegionId": "${response.senderRegionId!""}",
"senderBalanceValueBefore": "${response.senderBalanceValueBefore!""}",
"senderBalanceValueAfter": "${response.senderBalanceValueAfter!""}",
"senderCommission": "${response.senderCommission!""}",
"senderAccountType": "${response.senderAccountType!""}",
"senderResellerName": "${response.senderResellerName!""}",
"clientReference": "${response.clientReference!""}",
"externalSystemReference": "${response.externalSystemReference!""}",
"supplierReference": "${response.supplierReference!""}",
"productName": "${response.productName!""}",
"productSKU": "${response.productSKU!""}",
"groupId": "${response.groupId!""}",
"merchantId": "${response.merchantId!""}",
"bulkBatchId": "${response.bulkBatchId!""}",
"timeTaken": "${response.timeTaken!""}"