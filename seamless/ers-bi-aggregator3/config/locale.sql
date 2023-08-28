ALTER TABLE bi.report_metadata ADD COLUMN IF NOT EXISTS `is_editable` boolean NOT NULL DEFAULT true;
ALTER TABLE bi.report_metadata ADD COLUMN IF NOT EXISTS `is_mandatory` boolean NOT NULL DEFAULT false;

TRUNCATE `report_list`;
INSERT INTO `report_list` (`id`, `name`, `grouping`, `query`, `data_source`)
VALUES
	(1, 'last_transaction_report_resellerMsisdn', 'sales', '{\"elasticIndex\":{\"indexName\":\"data_lake_\",\"isDataWeeklyIndexed\":true},\"elasticQuery\":{\"size\":\"<:size:>\",\"sort\":[{\"timestamp\":{\"order\":\"desc\"}}],\"query\":{\"bool\":{\"must\":[{\"bool\":{\"should\":[{\"terms\":{\"SenderMSISDN.keyword\":\"<-:resellerMsisdn:->\",\"boost\":1}}]}},{\"range\":{\"timestamp\":{\"from\":\"<:fromDate:>\",\"to\":\"<:toDate:>\"}}}]}},\"_source\":{\"includes\":[\"ErsReference\",\"resultCode\",\"timestamp\",\"SenderResellerId\",\"SenderMSISDN\",\"SenderAccountType\",\"SenderBalanceValueAfter\",\"ReceiverMSISDN\",\"ReceiverResellerId\",\"ReceiverAccountType\",\"TransactionAmount\",\"TransactionType\",\"ProductSKU\",\"ProductName\",\"TransactionProfile\"],\"excludes\":[]}}}', 'elastic'),
	(2, 'last_X_transactions_report_resellerMsisdn', 'sales', '{\"elasticIndex\":{\"indexName\":\"data_lake_\",\"isDataWeeklyIndexed\":true},\"elasticQuery\":{\"size\":\"<:size:>\",\"sort\":[{\"timestamp\":{\"order\":\"desc\"}}],\"query\":{\"bool\":{\"must\":[{\"bool\":{\"must\":[{\"terms\":{\"SenderMSISDN.keyword\":\"<-:resellerMsisdn:->\",\"boost\":1}},{\"regexp\":{\"ReceiverMSISDN.keyword\":\"<:receiverMsisdn:>\"}},{\"regexp\":{\"ProductSKU.keyword\":\"<:serviceType:>\"}}]}},{\"range\":{\"timestamp\":{\"from\":\"<:fromDate:>\",\"to\":\"<:toDate:>\"}}}]}},\"_source\":{\"includes\":[\"ErsReference\",\"resultCode\",\"timestamp\",\"SenderResellerId\",\"SenderMSISDN\",\"SenderAccountType\",\"SenderBalanceValueAfter\",\"ReceiverMSISDN\",\"ReceiverResellerId\",\"ReceiverAccountType\",\"TransactionAmount\",\"TransactionType\",\"ProductSKU\",\"ProductName\",\"TransactionProfile\"],\"excludes\":[]}}}', 'elastic'),
	(3, 'last_transaction_report_resellerId', 'sales', '{\"elasticIndex\":{\"indexName\":\"data_lake_\",\"isDataWeeklyIndexed\":true},\"elasticQuery\":{\"size\":\"<:size:>\",\"sort\":[{\"timestamp\":{\"order\":\"desc\"}}],\"query\":{\"bool\":{\"must\":[{\"bool\":{\"should\":[{\"terms\":{\"SenderResellerId.keyword\":\"<-:resellerId:->\",\"boost\":1}}]}},{\"range\":{\"timestamp\":{\"from\":\"<:fromDate:>\",\"to\":\"<:toDate:>\"}}}]}},\"_source\":{\"includes\":[\"ErsReference\",\"resultCode\",\"timestamp\",\"SenderResellerId\",\"SenderMSISDN\",\"SenderAccountType\",\"SenderBalanceValueAfter\",\"ReceiverMSISDN\",\"ReceiverResellerId\",\"ReceiverAccountType\",\"TransactionAmount\",\"TransactionType\",\"ProductSKU\",\"ProductName\",\"TransactionProfile\"],\"excludes\":[]}}}', 'elastic'),
	(4, 'last_X_transactions_report_resellerId', 'sales', '{\"elasticIndex\":{\"indexName\":\"data_lake_\",\"isDataWeeklyIndexed\":true},\"elasticQuery\":{\"size\":\"<:size:>\",\"sort\":[{\"timestamp\":{\"order\":\"desc\"}}],\"query\":{\"bool\":{\"must\":[{\"bool\":{\"must\":[{\"terms\":{\"SenderResellerId.keyword\":\"<-:resellerId:->\",\"boost\":1}},{\"regexp\":{\"ReceiverMSISDN.keyword\":\"<:receiverMsisdn:>\"}},{\"regexp\":{\"ProductSKU.keyword\":\"<:serviceType:>\"}}]}},{\"range\":{\"timestamp\":{\"from\":\"<:fromDate:>\",\"to\":\"<:toDate:>\"}}}]}},\"_source\":{\"includes\":[\"ErsReference\",\"resultCode\",\"timestamp\",\"SenderResellerId\",\"SenderMSISDN\",\"SenderAccountType\",\"SenderBalanceValueAfter\",\"ReceiverMSISDN\",\"ReceiverResellerId\",\"ReceiverAccountType\",\"TransactionAmount\",\"TransactionType\",\"ProductSKU\",\"ProductName\",\"TransactionProfile\"],\"excludes\":[]}}}', 'elastic'),
    (5, 'search_daily_transaction', 'sales', '{\"elasticIndex\":{\"indexName\":\"data_lake_\",\"isDataWeeklyIndexed\":true},\"elasticQuery\":{\"size\":\"<:size:>\",\"query\":{\"bool\":{\"must\":[{\"bool\":{\"should\":[{\"wildcard\":{\"senderMSISDN\":{\"value\":\"<:targetMsisdn:>\"}}}]}},{\"range\":{\"timestamp\":{\"from\":\"<:fromDate:>\",\"to\":\"<:toDate:>\"}}}]}},\"_source\":{\"includes\":[\"resultCode\",\"TransactionAmount\",\"ReceiverResellerName\",\"ReceiverMSISDN\",\"senderMSISDN\",\"TransactionAmount\",\"timestamp\"],\"excludes\":[]}}}', 'elastic'),
    (6, 'last_X_transaction_by_size_type_day_targetMsisdn', 'sales', '{\"elasticIndex\":{\"indexName\":\"data_lake_\",\"isDataWeeklyIndexed\":true},\"elasticQuery\":{\"size\":\"<:size:>\",\"sort\":[{\"@timestamp\":{\"order\":\"desc\"}}],\"query\":{\"bool\":{\"must\":[{\"bool\":{\"should\":[{\"bool\":{\"must\":[{\"query_string\":{\"query\":\"<:transactionType:>\",\"fields\":[\"externalRequestType\",\"type\"]}},{\"wildcard\":{\"receiverMSISDN\":{\"value\":\"<:targetMsisdn:>\"}}}]}},{\"bool\":{\"must\":[{\"query_string\":{\"query\":\"<:transactionType:>\",\"fields\":[\"externalRequestType\",\"type\"]}},{\"wildcard\":{\"senderMSISDN\":{\"value\":\"<:targetMsisdn:>\"}}}]}}]}},{\"range\":{\"timestamp\":{\"from\":\"<:fromDate:>\",\"to\":\"<:toDate:>\"}}}]}},\"_source\":{\"includes\":[\"timestamp\",\"receiverMSISDN\",\"transactionAmount\",\"resultCode\",\"SenderResellerName\",\"Channel\",\"senderMSISDN\",\"externalRequestType\",\"type\",\"ersReference\"]}}}', 'elastic'),
    (8, 'last_tranasction_status', 'sales', '{\"elasticIndex\":{\"indexName\":\"data_lake_\",\"isDataWeeklyIndexed\":true},\"elasticQuery\":{\"query\":{\"bool\":{\"must\":[{\"bool\":{\"should\":[{\"wildcard\":{\"senderMSISDN\":{\"value\":\"<:targetMsisdn:>\"}}}]}}]}},\"sort\":[{\"@timestamp\":{\"order\":\"desc\"}}],\"_source\":{\"includes\":[\"timestamp\",\"ReceiverMSISDN\",\"TransactionAmount\",\"resultCode\",\"SenderResellerName\",\"Channel\",\"senderMSISDN\",\"RECEIVER_COMMISSION\",\"ResultStatus\",\"externalRequestType\",\"type\",\"ersReference\"]},\"size\":\"<:size:>\"}}', 'elastic'),
    (9, 'c2s_transfer_status', 'sales', '{\"elasticIndex\":{\"indexName\":\"data_lake_\",\"isDataWeeklyIndexed\":true},\"elasticQuery\":{\"size\":\"<:size:>\",\"sort\":[{\"timestamp\":{\"order\":\"desc\"}}],\"query\":{\"bool\":{\"must\":[{\"regexp\":{\"ErsReference.keyword\":\"<:transactionId:>\"}},{\"query_string\":{\"fields\":[\"externalRequestType\"],\"query\":\"<:requestType:>\"}},{\"bool\":{\"should\":[{\"regexp\":{\"SenderMSISDN.keyword\":\"<:senderMsisdn:>\"}},{\"regexp\":{\"SenderResellerId.keyword\":\"<:senderResellerId:>\"}}]}}]}},\"_source\":{\"includes\":[\"timestamp\",\"resultCode\",\"ErsReference\",\"ResultStatus\",\"externalRequestType\",\"ClientReference\",\"TransactionAmount\"],\"excludes\":[]}}}', 'elastic'),
    (10, 'subscriber_last_tranasction_status', 'sales', '{\"elasticIndex\":{\"indexName\":\"data_lake_\",\"isDataWeeklyIndexed\":true},\"elasticQuery\":{\"query\":{\"bool\":{\"must\":[{\"bool\":{\"should\":[{\"wildcard\":{\"ReceiverMSISDN\":{\"value\":\"<:targetMSISDN:>\"}}}]}}]}},\"sort\":[{\"@timestamp\":{\"order\":\"desc\"}}],\"_source\":{\"includes\":[\"timestamp\",\"ReceiverMSISDN\",\"TransactionAmount\",\"resultCode\",\"SenderResellerName\",\"Channel\",\"SenderMSISDN\",\"RECEIVER_COMMISSION\",\"ResultStatus\",\"externalRequestType\",\"type\",\"ErsReference\"]},\"size\":\"<:size:>\"}}', 'elastic'),
    (12, 'My Monetary Transactions', 'sales', '{\n  \"elasticIndex\": {\n    \"indexName\": \"data_lake_\",\n    \"isDataWeeklyIndexed\": true\n  },\n  \"elasticQuery\": {\n  \"size\": \"<:size:>\",\n  \"query\": {\n    \"bool\": {\n      \"must\": [\n        {\n          \"bool\": {\n            \"should\": [\n              {\n                \"wildcard\": {\n                  \"ErsReference\": {\n                    \"value\": \"<:ErsReference:>\"\n                  }\n                }\n              },\n              {\n                \"term\": {\n                  \"ErsReference.keyword\": \"<:ErsReference:>\"\n                }\n              }\n            ]\n          }\n        },\n        {\n          \"bool\": {\n            \"should\": [\n              {\n                \"wildcard\": {\n                  \"SenderResellerId\": {\n                    \"value\": \"<:SenderResellerId:>\"\n                  }\n                }\n              },\n              {\n                \"term\": {\n                  \"SenderResellerId.keyword\": \"<:SenderResellerId:>\"\n                }\n              }\n            ]\n          }\n        },\n        {\n          \"bool\": {\n            \"should\": [\n              {\n                \"wildcard\": {\n                  \"ReceiverResellerId\": {\n                    \"value\": \"<:ReceiverResellerId:>\"\n                  }\n                }\n              },\n              {\n                \"term\": {\n                  \"ReceiverResellerId.keyword\": \"<:ReceiverResellerId:>\"\n                }\n              }\n            ]\n          }\n        },\n        {\n          \"bool\": {\n            \"should\": [\n              {\n                \"wildcard\": {\n                  \"SenderMSISDN\": {\n                    \"value\": \"<:SenderMSISDN:>\"\n                  }\n                }\n              },\n              {\n                \"term\": {\n                  \"SenderMSISDN.keyword\": \"<:SenderMSISDN:>\"\n                }\n              }\n            ]\n          }\n        },\n        {\n          \"bool\": {\n            \"should\": [\n              {\n                \"wildcard\": {\n                  \"ReceiverMSISDN\": {\n                    \"value\": \"<:ReceiverMSISDN:>\"\n                  }\n                }\n              },\n              {\n                \"term\": {\n                  \"ReceiverMSISDN.keyword\": \"<:ReceiverMSISDN:>\"\n                }\n              }\n            ]\n          }\n        },\n        {\n          \"bool\": {\n            \"should\": [\n              {\n                \"wildcard\": {\n                  \"TransactionProfile\": {\n                    \"value\": \"<:TransactionProfile:>\"\n                  }\n                }\n              },\n              {\n                \"term\": {\n                  \"TransactionProfile.keyword\": \"<:TransactionProfile:>\"\n                }\n              }\n            ]\n          }\n        },\n        {\n          \"bool\": {\n            \"should\": [\n              {\n                \"wildcard\": {\n                  \"ResultStatus\": {\n                    \"value\": \"<:ResultStatus:>\"\n                  }\n                }\n              },\n              {\n                \"term\": {\n                  \"ResultStatus.keyword\": \"<:ResultStatus:>\"\n                }\n              }\n            ]\n          }\n        },\n        {\n          \"bool\": {\n            \"should\": [\n              {\n                \"wildcard\": {\n                  \"ProductSKU\": {\n                    \"value\": \"<:ProductSKU:>\"\n                  }\n                }\n              },\n              {\n                \"term\": {\n                  \"ProductSKU.keyword\": \"<:ProductSKU:>\"\n                }\n              }\n            ]\n          }\n        },\n        {\n          \"bool\": {\n            \"must_not\": [\n              {\n                \"wildcard\": {\n                  \"ErsReference\": {\n                    \"value\": \"NM*\"\n                  }\n                }\n              }\n            ]\n          }\n        },\n        {\n          \"range\": {\n            \"timestamp\": {\n              \"from\": \"<:fromDate:>\",\n              \"to\": \"<:toDate:>\"\n            }\n          }\n        }\n      ]\n    }\n  },\n  \"sort\": [\n    {\n      \"timestamp\": \"<:sort:>\"\n    }\n  ],\n  \"_source\": {\n    \"includes\": [\n      \"ErsReference\",\n      \"timestamp\",\n      \"SenderResellerId\",\n      \"SenderMSISDN\",\n      \"ReceiverResellerId\",\n      \"ReceiverMSISDN\",\n      \"TransactionProfile\",\n      \"ProductSKU\",\n      \"TransactionAmount\",\n      \"resultCode\",\n      \"ResultStatus\"\n    ],\n    \"excludes\": []\n  }\n}\n}', 'elastic'),
    (13, 'My Child Monetary Transactions', 'sales', '{\"elasticIndex\":{\"indexName\":\"data_lake_\",\"isDataWeeklyIndexed\":true},\"elasticQuery\":{\"size\":\"<:size:>\",\"query\":{\"bool\":{\"must\":[{\"bool\":{\"must\":[{\"term\":{\"SenderResellerPath\":\"<:userId:>\"}}],\"must_not\":[{\"term\":{\"SenderResellerId\":\"<:userId:>\"}}]}},{\"range\":{\"timestamp\":{\"from\":\"<:fromDate:>\",\"to\":\"<:toDate:>\"}}}]}},\"sort\":[{\"timestamp\":\"<:sort:>\"}],\"_source\":{\"includes\":[\"ErsReference\",\"resultCode\",\"timestamp\",\"SenderResellerId\",\"SenderMSISDN\",\"SenderAccountType\",\"SenderBalanceValueAfter\",\"ReceiverMSISDN\",\"ReceiverResellerId\",\"ReceiverAccountType\",\"TransactionAmount\",\"TransactionType\",\"ProductSKU\",\"ProductName\",\"TransactionProfile\",\"SenderResellerPath\"],\"excludes\":[]}}}', 'elastic'),
    (14, 'All Transactions Report', 'sales', '{\"elasticIndex\":{\"indexName\":\"data_lake_\",\"isDataWeeklyIndexed\":true},\"elasticQuery\":{\"size\":\"<:size:>\",\"query\":{\"bool\":{\"must\":[{\"bool\":{\"should\":[{\"wildcard\":{\"ersReference\":{\"value\":\"<:ErsReference:>\"}}},{\"term\":{\"ersReference.keyword\":\"<:ErsReference:>\"}}]}},{\"bool\":{\"should\":[{\"wildcard\":{\"senderResellerId\":{\"value\":\"<:SenderResellerId:>\"}}},{\"term\":{\"senderResellerId.keyword\":\"<:SenderResellerId:>\"}}]}},{\"bool\":{\"should\":[{\"wildcard\":{\"receiverResellerId\":{\"value\":\"<:ReceiverResellerId:>\"}}},{\"term\":{\"receiverResellerId.keyword\":\"<:ReceiverResellerId:>\"}},{\"bool\":{\"must_not\":[{\"exists\":{\"field\":\"receiverResellerId\"}}]}}]}},{\"bool\":{\"should\":[{\"wildcard\":{\"senderMSISDN\":{\"value\":\"<:SenderMSISDN:>\"}}},{\"term\":{\"senderMSISDN.keyword\":\"<:SenderMSISDN:>\"}}]}},{\"bool\":{\"should\":[{\"wildcard\":{\"receiverMSISDN\":{\"value\":\"<:ReceiverMSISDN:>\"}}},{\"term\":{\"receiverMSISDN.keyword\":\"<:ReceiverMSISDN:>\"}}]}},{\"bool\":{\"should\":[{\"wildcard\":{\"resultMessage\":{\"value\":\"<:ResultStatus:>\"}}},{\"term\":{\"resultMessage.keyword\":\"<:ResultStatus:>\"}}]}},{\"bool\":{\"should\":[{\"wildcard\":{\"transactionProfile\":{\"value\":\"<:TransactionProfile:>\"}}},{\"term\":{\"transactionProfile.keyword\":\"<:TransactionProfile:>\"}}]}},{\"bool\":{\"should\":[{\"wildcard\":{\"productSKU\":{\"value\":\"<:ProductSKU:>\"}}},{\"term\":{\"productSKU.keyword\":\"<:ProductSKU:>\"}}]}},{\"range\":{\"timestamp\":{\"from\":\"<:fromDate:>\",\"to\":\"<:toDate:>\"}}},{\"range\":{\"transactionAmount\":{\"gte\":\"<:fromAmount:>\",\"lte\":\"<:toAmount:>\"}}}]}},\"sort\":[{\"timestamp\":{\"order\":\"<:sort:>\"}}],\"_source\":{\"includes\":[\"ersReference\",\"timestamp\",\"senderResellerId\",\"senderMSISDN\",\"receiverResellerId\",\"receiverMSISDN\",\"transactionProfile\",\"productSKU\",\"transactionAmount\",\"resultCode\",\"resultMessage\"],\"excludes\":[]}}}', 'elastic'),
    (15, 'fetch_pending_transactions', 'sales', 'FetchPendingTransactions', 'groovy'),
    (16, 'Hierarchy Transaction Search', 'sales', '{\"elasticIndex\":{\"indexName\":\"data_lake_\",\"isDataWeeklyIndexed\":true},\"elasticQuery\":{\"size\":\"<:size:>\",\"query\":{\"bool\":{\"must\":[{\"bool\":{\"should\":[{\"wildcard\":{\"ersReference\":{\"value\":\"<:ErsReference:>\"}}},{\"term\":{\"ersReference.keyword\":\"<:ErsReference:>\"}}]}},{\"bool\":{\"should\":[{\"wildcard\":{\"senderResellerId\":{\"value\":\"<:SenderResellerId:>\"}}},{\"term\":{\"senderResellerId.keyword\":\"<:SenderResellerId:>\"}}]}},{\"bool\":{\"should\":[{\"wildcard\":{\"receiverResellerId\":{\"value\":\"<:ReceiverResellerId:>\"}}},{\"term\":{\"receiverResellerId.keyword\":\"<:ReceiverResellerId:>\"}},{\"bool\":{\"must_not\":[{\"exists\":{\"field\":\"receiverResellerId\"}}]}}]}},{\"bool\":{\"should\":[{\"wildcard\":{\"senderMSISDN\":{\"value\":\"<:SenderMSISDN:>\"}}},{\"term\":{\"senderMSISDN.keyword\":\"<:SenderMSISDN:>\"}}]}},{\"bool\":{\"should\":[{\"wildcard\":{\"receiverMSISDN\":{\"value\":\"<:ReceiverMSISDN:>\"}}},{\"term\":{\"receiverMSISDN.keyword\":\"<:ReceiverMSISDN:>\"}}]}},{\"bool\":{\"should\":[{\"wildcard\":{\"resultMessage\":{\"value\":\"<:ResultStatus:>\"}}},{\"term\":{\"resultMessage.keyword\":\"<:ResultStatus:>\"}}]}},{\"bool\":{\"should\":[{\"wildcard\":{\"transactionProfile\":{\"value\":\"<:TransactionProfile:>\"}}},{\"term\":{\"transactionProfile.keyword\":\"<:TransactionProfile:>\"}}]}},{\"bool\":{\"should\":[{\"wildcard\":{\"productSKU\":{\"value\":\"<:ProductSKU:>\"}}},{\"term\":{\"productSKU.keyword\":\"<:ProductSKU:>\"}}]}},{\"range\":{\"timestamp\":{\"from\":\"<:fromDate:>\",\"to\":\"<:toDate:>\"}}},{\"range\":{\"transactionAmount\":{\"gte\":\"<:fromAmount:>\",\"lte\":\"<:toAmount:>\"}}},{\"bool\":{\"should\":[{\"match\":{\"SenderResellerPath.keyword\":\"<:resellerPath:>\"}},{\"bool\":{\"should\":[{\"bool\":{\"must_not\":[{\"exists\":{\"field\":\"RECEIVER_RESELLER_PATH\"}}]}},{\"bool\":{\"must\":[{\"match\":{\"RECEIVER_RESELLER_PATH\":\"<:resellerPath:>\"}}]}}]}}]}},{\"bool\":{\"should\":[{\"bool\":{\"must\":[{\"term\":{\"receiverResellerId.keyword\":\"<:userId:>\"}}]}},{\"bool\":{\"must_not\":[{\"term\":{\"productSKU.keyword\":\"O2C_foc_transfer\"}}]}}]}}]}},\"sort\":[{\"timestamp\":{\"order\":\"<:sort:>\"}}],\"_source\":{\"includes\":[\"ersReference\",\"timestamp\",\"senderResellerId\",\"senderMSISDN\",\"receiverResellerId\",\"receiverMSISDN\",\"transactionProfile\",\"productSKU\",\"transactionAmount\",\"resultCode\",\"resultMessage\"],\"excludes\":[]}}}', 'elastic'),
    (17, 'fetch_processed_transactions', 'sales', 'FetchProcessedTransactions', 'groovy'),
    (18, 'audit_log_report','audit','{"elasticIndex":{"indexName":"audit_","isDataWeeklyIndexed":true},"elasticQuery":{"query":{"bool":{"must":[{"bool":{"should":[{"wildcard":{"ersReference":{"value":"<:transactionNumber:>"}}},{"terms":{"ersReference.keyword":"<-:transactionNumber:->","boost":1}},{"wildcard":{"transactionNumber":{"value":"<:transactionNumber:>"}}},{"terms":{"transactionNumber.keyword":"<-:transactionNumber:->","boost":1}}]}},{"bool":{"should":[{"wildcard":{"eventName":{"value":"<:eventName:>"}}},{"terms":{"eventName.keyword":"<-:eventName:->","boost":1}}]}},{"bool":{"should":[{"wildcard":{"user.userId":{"value":"<:userId:>"}}},{"terms":{"user.userId.keyword":"<-:userId:->","boost":1}}]}},{"bool":{"should":[{"wildcard":{"user.resellerMSISDN":{"value":"<:resellerMsisdn:>"}}},{"terms":{"user.resellerMSISDN.keyword":"<-:resellerMsisdn:->","boost":1}}]}},{"range":{"timestamp":{"from":"<:fromDate:>","to":"<:toDate:>"}}}]}},"script_fields":{"timestamp":{"script":"doc[\'timestamp\'].value.toString(\'yyyy-MM-dd HH:mm:ss\')"}},"_source":{"includes":["transactionNumber","ersReference","timestamp","eventName","user.userId","user.resellerMSISDN","channel","resultCode","resultMessage"],"excludes":[]}}}','');


TRUNCATE `report_metadata`;
INSERT INTO `report_metadata` (`id`, `report_id`, `name`, `type`, `default_value`, `values`, `reg_ex`, `extra_field_1`, `extra_field_2`,`is_editable`,`is_mandatory`)
VALUES
	(1, '1', 'resellerMsisdn', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
	(2, '1', 'size', 'text', '5', NULL, NULL, NULL, NULL, 1, 0),
	(3, '1', 'fromDate', 'date', NULL, NULL, NULL, NULL, NULL, 1, 0),
	(4, '1', 'toDate', 'date', NULL, NULL, NULL, NULL, NULL, 1, 0),
	(5, '2', 'resellerMsisdn', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
	(6, '2', 'size', 'text', '5', NULL, NULL, NULL, NULL, 1, 0),
	(7, '2', 'receiverMsisdn', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
	(8, '2', 'serviceType', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
	(9, '2', 'fromDate', 'date', NULL, NULL, NULL, NULL, NULL, 1, 0),
	(10, '2', 'toDate', 'date', NULL, NULL, NULL, NULL, NULL, 1, 0),
	(11, '3', 'resellerId', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
	(12, '3', 'size', 'text', '5', NULL, NULL, NULL, NULL, 1, 0),
	(13, '3', 'fromDate', 'date', NULL, NULL, NULL, NULL, NULL, 1, 0),
	(14, '3', 'toDate', 'date', NULL, NULL, NULL, NULL, NULL, 1, 0),
	(15, '4', 'resellerId', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
	(16, '4', 'receiverMsisdn', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
	(17, '4', 'size', 'text', '5', NULL, NULL, NULL, NULL, 1, 0),
	(18, '4', 'serviceType', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
	(19, '4', 'fromDate', 'date', NULL, NULL, NULL, NULL, NULL, 1, 0),
	(20, '4', 'toDate', 'date', NULL, NULL, NULL, NULL, NULL, 1, 0),
    (21, '5', 'fromDate', 'date', NULL, NULL, NULL, NULL, NULL, 1, 0),
    (22, '5', 'toDate', 'date', NULL, NULL, NULL, NULL, NULL, 1, 0),
    (23, '5', 'size', 'text', '5', NULL, NULL, NULL, NULL, 1, 0),
    (24, '5', 'targetMsisdn', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
    (25, '6', 'transactionType', 'text', NULL, NULL, NULL, NULL, NULL, 1, 0),
    (26, '6', 'fromDate', 'date', NULL, NULL, NULL, NULL, NULL, 1, 0),
    (27, '6', 'toDate', 'date', NULL, NULL, NULL, NULL, NULL, 1, 0),
    (28, '6', 'size', 'text', '5', NULL, NULL, NULL, NULL, 1, 0),
    (29, '6', 'targetMsisdn', 'text', NULL, NULL, NULL, NULL, NULL, 1, 0),
    (34, '8', 'targetMsisdn', 'text', NULL, NULL, NULL, NULL, NULL, 1, 0),
    (35, '8', 'size', 'text', '1', NULL, NULL, NULL, NULL, 1, 0),
    (36, '9', 'size', 'text', '1', NULL, NULL, NULL, NULL, 1, 0),
    (37, '9', 'transactionId', 'text', '', NULL, NULL, NULL, NULL, 1, 0),
    (38, '9', 'senderMsisdn', 'text', '', NULL, NULL, NULL, NULL, 1, 0),
    (39, '9', 'senderResellerId', 'text', '', NULL, NULL, NULL, NULL, 1, 0),
    (40, '9', 'requestType', 'text', '', NULL, NULL, NULL, NULL, 1, 0),
    (41, '10', 'size', 'text', '1', NULL, NULL, NULL, NULL, 1, 0),
    (42, '10', 'targetMSISDN', 'text', NULL, NULL, NULL, NULL, NULL, 1, 0),
    (45, '12', 'fromDate', 'date', NULL, NULL, NULL, NULL, NULL, 1, 0),
    (46, '12', 'toDate', 'date', NULL, NULL, NULL, NULL, NULL, 1, 0),
    (47, '12', 'userId', 'text', NULL, NULL, NULL, NULL, NULL, 1, 0),
    (48, '12', 'sort', 'select', 'desc', 'asc,desc', NULL, NULL, NULL, 1, 0),
    (49, '12', 'size', 'select', '5', '5,10,50,100,500,1000,5000', NULL, NULL, NULL, 1, 0),
    (50, '13', 'fromDate', 'date', NULL, NULL, NULL, NULL, NULL, 1, 0),
    (51, '13', 'toDate', 'date', NULL, NULL, NULL, NULL, NULL, 1, 0),
    (52, '13', 'userId', 'text', NULL, NULL, NULL, NULL, NULL, 1, 0),
    (53, '13', 'sort', 'select', 'desc', 'asc,desc', NULL, NULL, NULL, 1, 0),
    (54, '13', 'size', 'select', '5', '5,10,50,100,500,1000,5000', NULL, NULL, NULL, 1, 0),
    (55, '14', 'ErsReference', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
    (56, '14', 'SenderResellerId', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
    (57, '14', 'ReceiverResellerId', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
    (58, '14', 'SenderMSISDN', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
    (59, '14', 'ReceiverMSISDN', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
    (60, '14', 'TransactionProfile', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
    (61, '14', 'ResultStatus', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
    (62, '14', 'ProductSKU', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
    (63, '14', 'fromDate', 'date', NULL, NULL, NULL, NULL, NULL, 1, 0),
    (64, '14', 'toDate', 'date', NULL, NULL, NULL, NULL, NULL, 1, 0),
    (65, '14', 'sort', 'select', 'desc', 'asc,desc', NULL, NULL, NULL, 1, 0),
    (66, '14', 'size', 'select', '50', '5,10,50,100,500,1000,5000', NULL, NULL, NULL, 1, 0),
    (67, '12', 'ErsReference', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
    (68, '12', 'SenderResellerId', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
    (69, '12', 'ReceiverResellerId', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
    (70, '12', 'SenderMSISDN', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
    (71, '12', 'ReceiverMSISDN', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
    (72, '12', 'TransactionProfile', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
    (73, '12', 'ResultStatus', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
    (74, '12', 'ProductSKU', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
    (75, '15', 'role', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
	(76, '15', 'fromDate', 'date', NULL, NULL, NULL, NULL, NULL, 1, 0),
    (79, '15', 'productSKU', 'text', '', NULL, NULL, NULL, NULL, 1, 0),
    (80, '16', 'ErsReference', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
	(81, '16', 'SenderResellerId', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
	(82, '16', 'ReceiverResellerId', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
	(83, '16', 'SenderMSISDN', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
	(84, '16', 'ReceiverMSISDN', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
	(85, '16', 'TransactionProfile', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
	(86, '16', 'ResultStatus', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
	(87, '16', 'ProductSKU', 'text', 'ALL', NULL, NULL, NULL, NULL, 1, 0),
	(88, '16', 'fromDate', 'date', NULL, NULL, NULL, NULL, NULL, 1, 0),
	(89, '16', 'toDate', 'date', NULL, NULL, NULL, NULL, NULL, 1, 0),
	(90, '16', 'sort', 'select', 'desc', 'asc,desc', NULL, NULL, NULL, 1, 0),
	(91, '16', 'size', 'select', '50', '5,10,50,100,500,1000,5000', NULL, NULL, NULL, 1, 0),
    (92, '17', 'fromDate', 'date', NULL, NULL, NULL, NULL, NULL, 1, 0),
    (93, '17', 'userId', 'text', NULL, NULL, NULL, NULL, NULL, 1, 0),
    (94, '17', 'productSKU', 'text', '', NULL, NULL, NULL, NULL, 1, 0),
    (95, '18', 'fromDate', 'date',NULL , NULL,NULL ,NULL ,NULL , 1, 0),
    (96, '18', 'toDate', 'date',NULL ,NULL , NULL,NULL ,NULL , 1, 0),
    (97, '18', 'transactionNumber', 'text', 'ALL', NULL,NULL ,NULL ,NULL , 1, 0),
    (98, '18', 'eventName', 'select', 'ALL', 'ALL','Login,Logout,Reseller Creation,Reseller Update,Reseller Blocked,Reseller unblocked,Reseller/subscriber decommissioned,Reseller/subscriber activated,Reseller User created,Reseller User updated,User Blocked,User UnBlocked,Reseller DeCommissioned,Reseller Parent Change,Reseller Region Change (Part of Reseller Update),Reseller Password Updated,Reseller Password Reset,Reseller User Password Updated,Reseller User Password Reset,Reseller Role Added,Reseller Role Updated,Reseller Role Deleted,Reseller Contract Added,Reseller Contract Updated,Reseller Contract Rule and Margin Addition,Reseller Contract Rule and Margin Update,Password Policy Added,Password Policy Changed/Updated,Product Addition,Product Update,Product Remove/ Delete,Import submitted,O2C Approval,C2C Approval,Batch Operation (O2C, C2C,FOC, C2S) Submitted,Batch Operation (O2C, C2C, C2S) Approval,Gateway Create,Gateway Update,Gateway Delete,Gateway Status Change,C2S Rule Creation,C2S Rule Update,Transaction Limits Define (O2C,C2C,C2S and P2P),Transaction Limits Update (O2C,C2C,C2S and P2P),Automatic Transfer Provision from UI,Automatic Transfer Provision from UI requires an approval Process,P2P Service Subscriber Barred (Via File Upload),P2P Service Subscriber UnBarred,Notiifcation Create,Notification Update,Notification Status Change,Notification Delete', NULL,NULL ,NULL , 1, 0),
    (99,'18', 'userId', 'text', 'ALL', NULL,NULL ,NULL ,NULL , 1, 0),
    (100,'18', 'resellerMsisdn', 'text', 'ALL',NULL ,NULL , NULL,NULL , 1, 0);







TRUNCATE `report_access_control`;
INSERT INTO `report_access_control` (`id`,`type_role`,`name`,`report_list_ids`,`dashboard_url_ids`,`status`,`settings`)
VALUES (1,'Super user',NULL,'1,2,3,4,11','','active',1)
 , (2,'Reseller admin',NULL,'1,2,3,4,11','','active',1)
 , (3,'OPERATOR',NULL,'1,2,3,4,5,6,8,9,12,13,14,15,16,18','','active',1);

TRUNCATE `report_category_mapping`;
INSERT INTO `report_category_mapping` (`id`,`category_name`,`report_list_ids`)
VALUES (1,'Report','12,13'),
       (2,'Audit','18');

DROP TABLE IF EXISTS `safaricom_dealer_information`;

TRUNCATE `report_channel_access_control`;
INSERT INTO `report_channel_access_control` (`id`, `channel`, `report_list_ids`, `status`)
VALUES
	(1, 'web', '1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,18', 'active');

 CREATE TABLE IF NOT EXISTS  `bi`.`reseller_balance_aggregator` (
  `id` varchar(200) NOT NULL,
  `reseller_id` varchar(200) NOT NULL,
  `account_id` varchar(20) NOT NULL,
  `balance` decimal(65,5) DEFAULT NULL,
  `updated_date` timestamp NULL DEFAULT NULL,
  `domain_code` varchar(30) DEFAULT NULL,
  `domain_name` varchar(30) DEFAULT NULL,
  `external_code` varchar(50) DEFAULT NULL,
  `msisdn` varchar(200) DEFAULT NULL,
  `reseller_type` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `bi`.`std_parent_reseller_aggregation` (
  `aggregationTimestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `parentResellerMSISDN` varchar(50) NOT NULL,
  `resellerMSISDN` varchar(50) NOT NULL,
  `parentEmail` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`resellerMSISDN`),
  KEY `resellerMSISDN` (`resellerMSISDN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
