load data local infile '/data/refill.csv'  REPLACE into table refill_data_dump  fields terminated by ',' enclosed by '"' lines terminated by '\n' ( resellerId ,accounttypeid, resellerName ,status, resellerPath , domainCode , domainName , geographicalDomain , ownerMsisdn , ownerName , externalCode , parentName , parentMSISDN , msisdn , resellerType , resellerTypeName );
