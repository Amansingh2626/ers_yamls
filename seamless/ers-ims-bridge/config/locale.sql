SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE `user_base`;
SET FOREIGN_KEY_CHECKS = 1;
INSERT INTO `user_base` (`id`, `repo_name`, `forget_password_url`, `change_password_url`,`expire_password_url`, `login_url`)
VALUES
	(1,'general','http://ers-dealer-management-system-service:8033/dms/auth/forgetPassword','http://ers-dealer-management-system-service:8033/dms/auth/changePassword','http://ers-dealer-management-system-service:8033/dms/auth/expirePassword','http://ers-coreproxy-service:8912/principalService'),
     (2,'ldaprepo','','','','http://localhost:8824/ldap/authenticateUser');

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE `type_repo_map`;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `type_repo_map` (`id`, `reseller_type`, `repo_id`)
VALUES
	(4, 'CORP_PARTNER', 1),
	(8, 'DISTRIBUTOR', 1),
	(2, 'EMPFLEX', 1),
	(10, 'GPC', 1),
	(6, 'GPCF', 1),
	(9, 'GPCF_POS', 1),
	(30, 'MFS_PARTNER', 1),
	(32, 'MYGP_USER', 1),
	(1, 'OPERATOR', 1),
	(3, 'POS', 1),
	(7, 'SE', 1),
	(5, 'Zonal_Manager', 1);
