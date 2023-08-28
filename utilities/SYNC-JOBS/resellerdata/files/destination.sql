load data local infile '/data/output.csv' REPLACE into table reseller_accounts fields terminated by ',' enclosed by '"' lines terminated by '\n'(owner, accountId, accountTypeId,balance,updated_date);
