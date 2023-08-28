SELECT cr.tag as resellerId,pop.id,cr.name as resellerName,
                        cr.status as status,cr.reseller_path as resellerPath,  
                        extp.parameter_value as 'domainCode', extp1.parameter_value as 'domainName',
                        extp3.parameter_value as 'geographicalDomain',extp4.parameter_value as 'ownerMsisdn',extp5.parameter_value as 'ownerName',extp2.parameter_value as 'externalCode',
                        cs1.name as 'parentName',dev1.address as 'parentMSISDN',dev.address as msisdn, 
                        rtp.id as resellerType, rtp.name as resellerTypeName from commission_receivers cr 
                        left join reseller_extra_params extp on cr.receiver_key=extp.receiver_key  and extp.parameter_key='domainCode' 
                        left join reseller_extra_params extp1 on cr.receiver_key=extp1.receiver_key and  extp1.parameter_key='domainName'
                        left join reseller_extra_params extp2 on cr.receiver_key=extp2.receiver_key and  extp2.parameter_key='externalCode'
                        left join reseller_extra_params extp3 on cr.receiver_key=extp3.receiver_key and  extp3.parameter_key='regionName'
                        left join reseller_extra_params extp4 on cr.receiver_key=extp4.receiver_key and  extp4.parameter_key='ownerMsisdn'
                        left join reseller_extra_params extp5 on cr.receiver_key=extp5.receiver_key and  extp5.parameter_key='ownerName'
                        left join extdev_devices dev  on cr.receiver_key=dev.device_key 
                        left join reseller_types rtp on cr.type_key=rtp.type_key 
                        left join reseller_hierarchy rh on cr.receiver_key = rh.child_key
                        left join commission_receivers cs1 on  cs1.receiver_key = rh.parent_key
                        left join extdev_devices dev1  on cs1.receiver_key=dev1.device_key 
                        right join pay_prereg_accounts as ppa on cr.tag=ppa.account_nr
                        left join pay_options as pop on ppa.payment_account_type_key=pop.account_type_key ;
