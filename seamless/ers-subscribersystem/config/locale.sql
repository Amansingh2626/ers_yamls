# Dump of table accounts
# ------------------------------------------------------------

DROP TABLE IF EXISTS `accounts`;

CREATE TABLE `accounts` (
  `subscriberKey` int(11) NOT NULL DEFAULT 0,
  `accountId` varchar(32) NOT NULL DEFAULT '',
  `accountTypeId` varchar(32) NOT NULL DEFAULT '',
  `creationDate` datetime DEFAULT NULL,
  `activationDate` datetime DEFAULT NULL,
  `flags` int(10) unsigned DEFAULT NULL,
  `accountDescription` varchar(80) DEFAULT NULL,
  `extraFields` text DEFAULT NULL,
  `lastActivity` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`subscriberKey`,`accountTypeId`,`accountId`),
  KEY `subscriberKey` (`subscriberKey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;

INSERT INTO `accounts` (`subscriberKey`, `accountId`, `accountTypeId`, `creationDate`, `activationDate`, `flags`, `accountDescription`, `extraFields`, `lastActivity`)
VALUES
	(1,'8801711234567','AIRTIME',NULL,NULL,NULL,NULL,NULL,'2021-11-15 08:08:02'),
	(1,'AIRTIME_test','AIRTIME','2021-12-07 11:25:06','2020-12-07 11:25:06',NULL,'def','','2021-12-07 11:25:06');

/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table services
# ------------------------------------------------------------

DROP TABLE IF EXISTS `services`;

CREATE TABLE `services` (
  `subscriberKey` int(11) NOT NULL DEFAULT 0,
  `serviceId` varchar(32) NOT NULL DEFAULT '',
  `password` varchar(128) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 0,
  `passwordChangeRequired` tinyint(4) NOT NULL DEFAULT 0,
  `lastPasswordChange` datetime DEFAULT NULL,
  `firstActivationDate` datetime DEFAULT NULL,
  PRIMARY KEY (`subscriberKey`,`serviceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `services` WRITE;
/*!40000 ALTER TABLE `services` DISABLE KEYS */;

INSERT INTO `services` (`subscriberKey`, `serviceId`, `password`, `status`, `passwordChangeRequired`, `lastPasswordChange`, `firstActivationDate`)
VALUES
	(1,'ALL',NULL,2,0,NULL,'2021-11-15 08:08:02');

/*!40000 ALTER TABLE `services` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table subscribers
# ------------------------------------------------------------

DROP TABLE IF EXISTS `subscribers`;

CREATE TABLE `subscribers` (
  `subscriberKey` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL DEFAULT 0,
  `subscriberId` varchar(40) DEFAULT NULL,
  `language` varchar(2) DEFAULT NULL,
  `countryCode` varchar(2) DEFAULT NULL,
  `timezone` varchar(40) DEFAULT NULL,
  `firstName` varchar(32) DEFAULT NULL,
  `lastName` varchar(32) DEFAULT NULL,
  `middleName` varchar(32) DEFAULT NULL,
  `idType` varchar(32) DEFAULT NULL,
  `idNumber` varchar(32) DEFAULT NULL,
  `anonymousId` varchar(25) DEFAULT NULL,
  `dateOfBirth` datetime DEFAULT NULL,
  `street` varchar(120) DEFAULT NULL,
  `zip` varchar(10) DEFAULT NULL,
  `city` varchar(32) DEFAULT NULL,
  `country` varchar(32) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `kycLevel` int(11) DEFAULT NULL,
  `password` varchar(256) DEFAULT NULL,
  `passwordFormat` tinyint(1) NOT NULL DEFAULT 0,
  `lastPasswordChange` datetime DEFAULT NULL,
  `subscriberMSISDN` varchar(40) DEFAULT NULL,
  `anonymousIdExpiry` datetime DEFAULT NULL,
  `fields` text DEFAULT NULL,
  PRIMARY KEY (`subscriberKey`),
  UNIQUE KEY `subscriberId` (`subscriberId`),
  UNIQUE KEY `anonymousId` (`anonymousId`),
  KEY `subscriberMSISDN` (`subscriberMSISDN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `subscribers` WRITE;
/*!40000 ALTER TABLE `subscribers` DISABLE KEYS */;

INSERT INTO `subscribers` (`subscriberKey`, `version`, `subscriberId`, `language`, `countryCode`, `timezone`, `firstName`, `lastName`, `middleName`, `idType`, `idNumber`, `anonymousId`, `dateOfBirth`, `street`, `zip`, `city`, `country`, `phone`, `kycLevel`, `password`, `passwordFormat`, `lastPasswordChange`, `subscriberMSISDN`, `anonymousIdExpiry`, `fields`)
VALUES
	(1,0,'8801711234567',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'8801712121212',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,'2021-11-15 08:08:02','8801711234567','2021-11-16 18:50:50',NULL),
	(2,0,'AIRTIME_test',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2021-11-15 08:08:02','8801711234566',NULL,NULL);

/*!40000 ALTER TABLE `subscribers` ENABLE KEYS */;
UNLOCK TABLES;
