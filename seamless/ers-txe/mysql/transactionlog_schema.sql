GRANT ALL PRIVILEGES ON *.* TO 'refill'@'%' IDENTIFIED BY 'refill';

-- MariaDB dump 10.19  Distrib 10.4.24-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: transactionlog
-- ------------------------------------------------------
-- Server version	10.4.20-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ersinstall`
--

DROP TABLE IF EXISTS `ersinstall`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ersinstall` (
  `VersionKey` smallint(6) NOT NULL AUTO_INCREMENT,
  `Version` varchar(20) NOT NULL,
  `Status` tinyint(4) NOT NULL DEFAULT 0,
  `Script` varchar(200) NOT NULL,
  `last_modified` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`VersionKey`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `missing_job_references`
--

DROP TABLE IF EXISTS `missing_job_references`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `missing_job_references` (
  `node_id` varchar(20) NOT NULL,
  `job_name` varchar(80) NOT NULL COMMENT 'Scheduled Job Name as defined in the Quartz scheduler',
  `ersReference` varchar(35) NOT NULL DEFAULT '' COMMENT 'Unique transaction id generated by timestampbase + threadId',
  PRIMARY KEY (`node_id`,`job_name`,`ersReference`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `replication_status`
--

DROP TABLE IF EXISTS `replication_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `replication_status` (
  `nodeId` varchar(25) NOT NULL DEFAULT '' COMMENT 'Node if for which this replication status applies to',
  `lastReplicatedReference` varchar(25) NOT NULL DEFAULT '' COMMENT 'Last transaction reference that was exported',
  `lastReplicatedSeqNo` int(11) NOT NULL COMMENT 'Last sequence number exported',
  `lastReplicationTime` datetime DEFAULT NULL COMMENT 'Last time transactions were exported',
  `replicationStatus` varchar(20) DEFAULT '' COMMENT 'Current replication status',
  PRIMARY KEY (`nodeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `txe_transaction_reference_sequence`
--

DROP TABLE IF EXISTS `txe_transaction_reference_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `txe_transaction_reference_sequence` (
  `seqNo` int(11) NOT NULL,
  PRIMARY KEY (`seqNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `txe_transactions_archive_chain_references`
--

DROP TABLE IF EXISTS `txe_transactions_archive_chain_references`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `txe_transactions_archive_chain_references` (
  `chainErsReference` varchar(35) NOT NULL DEFAULT '' COMMENT 'Unique original transaction in a chain id generated by timestampbase + threadId',
  `originalChainErsReference` varchar(35) DEFAULT '' COMMENT 'Unique original transaction reference for original chain. Will only be set for chain binded transactions.',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `chainErsReference` (`chainErsReference`),
  KEY `originalChainErsReference` (`originalChainErsReference`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `txe_transactions_archive_locks`
--

DROP TABLE IF EXISTS `txe_transactions_archive_locks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `txe_transactions_archive_locks` (
  `ersReference` varchar(35) NOT NULL DEFAULT '' COMMENT 'Unique transaction id generated by timestampbase + threadId',
  `lockReference` varchar(35) NOT NULL DEFAULT '' COMMENT 'Transaction id of the transaction that required the lock',
  `nodeId` varchar(25) DEFAULT '' COMMENT 'Node id that locked this transaction',
  `time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT 'Time when this lock was created',
  PRIMARY KEY (`ersReference`),
  KEY `lockReference` (`lockReference`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `txe_transactions_archive_references`
--

DROP TABLE IF EXISTS `txe_transactions_archive_references`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `txe_transactions_archive_references` (
  `ersReference` varchar(35) NOT NULL DEFAULT '' COMMENT 'Unique transaction id generated by timestampbase + threadId',
  `originalErsReference` varchar(35) DEFAULT '' COMMENT 'Unique transaction reference for original transaction. Will only be set for multi-phase transactions.',
  PRIMARY KEY (`ersReference`),
  KEY `ersReference` (`ersReference`),
  KEY `originalErsReference` (`originalErsReference`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `txe_transactions_archive_xml`
--

DROP TABLE IF EXISTS `txe_transactions_archive_xml`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `txe_transactions_archive_xml` (
  `ersReference` varchar(35) NOT NULL DEFAULT '' COMMENT 'Unique transaction id generated by timestampbase + threadId',
  `seqNo` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'sequence number indicating in which order the transactions are written to the database',
  `dataFormat` varchar(10) DEFAULT '' COMMENT 'Format of data xml - raw xml text, zip - zipped xml text',
  `modelVersion` tinyint(3) unsigned DEFAULT 1 COMMENT 'Version of data model',
  `data` blob NOT NULL COMMENT 'blob:ed transaction_data',
  `created` timestamp NOT NULL DEFAULT current_timestamp() COMMENT 'ordering of inserts without using auto_increments',
  `transaction_profile` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`seqNo`),
  UNIQUE KEY `ersReference` (`ersReference`),
  KEY `created` (`created`),
  KEY `created_reference` (`created`,`ersReference`),
  KEY `transaction_profile` (`transaction_profile`)
) ENGINE=InnoDB AUTO_INCREMENT=213 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `txe_transactions_xml`
--

DROP TABLE IF EXISTS `txe_transactions_xml`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `txe_transactions_xml` (
  `ersReference` varchar(35) NOT NULL DEFAULT '' COMMENT 'Unique transaction id generated by timestampbase + threadId',
  `originalErsReference` varchar(35) DEFAULT '' COMMENT 'Unique transaction reference for original transaction. Will only be set for multi-phase transactions.',
  `originalChainErsReference` varchar(35) DEFAULT '' COMMENT 'Unique original transaction reference for original chain. Will only be set for chain binded transactions.',
  `seqNo` int(11) NOT NULL AUTO_INCREMENT COMMENT 'sequence number indicating in which order the transactions are written to the database',
  `dataFormat` varchar(10) DEFAULT '' COMMENT 'Format of data xml - raw xml text, zip - zipped xml text',
  `modelVersion` tinyint(3) unsigned DEFAULT 1 COMMENT 'Version of data model',
  `data` blob NOT NULL COMMENT 'blob:ed transaction_data',
  `transaction_profile` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`seqNo`),
  KEY `transaction_profile` (`transaction_profile`)
) ENGINE=InnoDB AUTO_INCREMENT=213 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-16  6:24:30

