SELECT a.owner,a.accountId,a.accountTypeId,a.balance, IFNULL( temp.`createDate`, a.`createDate`) as 'updated_date' FROM accountmanagement.accounts a left join (select accountId,max(createDate)  as createDate from accountmanagement.`transactions` group by accountId) temp on a.`accountId` =temp.`accountId` WHERE a.accountTypeId != 'BOOKKEEPING';
