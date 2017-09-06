-- MySQL dump 10.13  Distrib 5.7.18, for macos10.12 (x86_64)
--
-- Host: localhost    Database: haw
-- ------------------------------------------------------
-- Server version	5.7.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `CENTER`
--

DROP TABLE IF EXISTS `CENTER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CENTER` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ABBREVIATION` varchar(8) DEFAULT NULL,
  `NAME` varchar(64) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CENTER`
--

LOCK TABLES `CENTER` WRITE;
/*!40000 ALTER TABLE `CENTER` DISABLE KEYS */;
/*!40000 ALTER TABLE `CENTER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CURRENCY`
--

DROP TABLE IF EXISTS `CURRENCY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CURRENCY` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ABBREVIATION` varchar(8) DEFAULT NULL,
  `NAMEDE` varchar(64) DEFAULT NULL,
  `NAMEEN` varchar(64) DEFAULT NULL,
  `SYMBOL` varchar(1) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CURRENCY`
--

LOCK TABLES `CURRENCY` WRITE;
/*!40000 ALTER TABLE `CURRENCY` DISABLE KEYS */;
/*!40000 ALTER TABLE `CURRENCY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LOGIN`
--

DROP TABLE IF EXISTS `LOGIN`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LOGIN` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PASSWORD` varchar(1024) DEFAULT NULL,
  `REQUESTTIME` datetime NOT NULL,
  `SALT` varchar(1024) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `personID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_LOGIN_personID` (`personID`),
  CONSTRAINT `FK_LOGIN_personID` FOREIGN KEY (`personID`) REFERENCES `person` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LOGIN`
--

LOCK TABLES `LOGIN` WRITE;
/*!40000 ALTER TABLE `LOGIN` DISABLE KEYS */;
/*!40000 ALTER TABLE `LOGIN` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MOABJOB`
--

DROP TABLE IF EXISTS `MOABJOB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MOABJOB` (
  `ID` int(11) NOT NULL,
  `MOABACCOUNT` varchar(255) DEFAULT NULL,
  `MOABANAME` varchar(255) DEFAULT NULL,
  `MOABAVERAGEUTILIZEDPROCS` double DEFAULT NULL,
  `MOABBACKFILL` tinyint(4) DEFAULT NULL,
  `MOABBYPASSCOUNT` int(11) DEFAULT NULL,
  `MOABCLASS` varchar(255) DEFAULT NULL,
  `MOABDISTRM` varchar(255) DEFAULT NULL,
  `MOABDISTRMJID` int(11) DEFAULT NULL,
  `MOABEFFQUEUETIME` double DEFAULT NULL,
  `MOABENDTIME` bigint(20) DEFAULT NULL,
  `MOABEXECUTABLE` varchar(255) DEFAULT NULL,
  `MOABGLOBALQUEUE` tinyint(4) DEFAULT NULL,
  `MOABGROUP` varchar(255) DEFAULT NULL,
  `MOABIWD` varchar(255) DEFAULT NULL,
  `MOABJOBID` int(11) DEFAULT NULL,
  `MOABNODECOUNT` int(11) DEFAULT NULL,
  `MOABNODEMATCHPOLICY` varchar(255) DEFAULT NULL,
  `MOABPE` double DEFAULT NULL,
  `MOABPARTITION` varchar(255) DEFAULT NULL,
  `MOABPROCPERTASK` int(11) DEFAULT NULL,
  `MOABREQWALLTIME` int(11) DEFAULT NULL,
  `MOABSTARTCOUNT` int(11) DEFAULT NULL,
  `MOABSTARTPRIORITY` int(11) DEFAULT NULL,
  `MOABSTARTTIME` bigint(20) DEFAULT NULL,
  `MOABSUBMITDIR` varchar(255) DEFAULT NULL,
  `MOABSUBMITTIME` bigint(20) DEFAULT NULL,
  `MOABSYSTEMID` varchar(255) DEFAULT NULL,
  `MOABSYSTEMJID` int(11) DEFAULT NULL,
  `MOABTASKCOUNT` int(11) DEFAULT NULL,
  `MOABTEMPLATESETS` varchar(255) DEFAULT NULL,
  `MOABTIMEQUEUEDTOTAL` int(11) DEFAULT NULL,
  `MOABTOTALCOST` int(11) DEFAULT NULL,
  `MOABTOTALREQUESTEDMEMORY` int(11) DEFAULT NULL,
  `MOABTOTALREQUESTEDNODES` int(11) DEFAULT NULL,
  `MOABTOTALREQUESTEDPROCS` int(11) DEFAULT NULL,
  `MOABTOTALREQUESTEDTASKS` int(11) DEFAULT NULL,
  `MOABUMASK` varchar(255) DEFAULT NULL,
  `MOABUNICORE` tinyint(4) DEFAULT NULL,
  `MOABUSEDWALLTIME` int(11) DEFAULT NULL,
  `MOABUSER` varchar(255) DEFAULT NULL,
  `PARSEDFILEID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MOABJOB`
--

LOCK TABLES `MOABJOB` WRITE;
/*!40000 ALTER TABLE `MOABJOB` DISABLE KEYS */;
/*!40000 ALTER TABLE `MOABJOB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PARSEDFILE`
--

DROP TABLE IF EXISTS `PARSEDFILE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PARSEDFILE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FILEDATE` date DEFAULT NULL,
  `FILEHASH` varchar(120) DEFAULT NULL,
  `FILENAME` varchar(60) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `resourceID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PARSEDFILE_resourceID` (`resourceID`),
  CONSTRAINT `FK_PARSEDFILE_resourceID` FOREIGN KEY (`resourceID`) REFERENCES `RESOURCE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PARSEDFILE`
--

LOCK TABLES `PARSEDFILE` WRITE;
/*!40000 ALTER TABLE `PARSEDFILE` DISABLE KEYS */;
/*!40000 ALTER TABLE `PARSEDFILE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PROJECT`
--

DROP TABLE IF EXISTS `PROJECT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PROJECT` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ENDDATE` date DEFAULT NULL,
  `NAME` varchar(60) DEFAULT NULL,
  `STARTDATE` date DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `leaderPersonID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PROJECT_leaderPersonID` (`leaderPersonID`),
  CONSTRAINT `FK_PROJECT_leaderPersonID` FOREIGN KEY (`leaderPersonID`) REFERENCES `person` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PROJECT`
--

LOCK TABLES `PROJECT` WRITE;
/*!40000 ALTER TABLE `PROJECT` DISABLE KEYS */;
/*!40000 ALTER TABLE `PROJECT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESOURCE`
--

DROP TABLE IF EXISTS `RESOURCE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESOURCE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACCOUNTINGNAME` varchar(8) DEFAULT NULL,
  `NAME` varchar(64) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESOURCE`
--

LOCK TABLES `RESOURCE` WRITE;
/*!40000 ALTER TABLE `RESOURCE` DISABLE KEYS */;
/*!40000 ALTER TABLE `RESOURCE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESOURCEACCOUNTED`
--

DROP TABLE IF EXISTS `RESOURCEACCOUNTED`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESOURCEACCOUNTED` (
  `COSTPERUNIT` double DEFAULT NULL,
  `NUMBEROF` int(11) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `CONTAINEDRESOURCEID` int(11) NOT NULL,
  `RESOURCEID` int(11) NOT NULL,
  `currencyID` int(11) DEFAULT NULL,
  `unitID` int(11) DEFAULT NULL,
  PRIMARY KEY (`CONTAINEDRESOURCEID`,`RESOURCEID`),
  KEY `FK_RESOURCEACCOUNTED_currencyID` (`currencyID`),
  KEY `FK_RESOURCEACCOUNTED_unitID` (`unitID`),
  KEY `FK_RESOURCEACCOUNTED_RESOURCEID` (`RESOURCEID`),
  CONSTRAINT `FK_RESOURCEACCOUNTED_CONTAINEDRESOURCEID` FOREIGN KEY (`CONTAINEDRESOURCEID`) REFERENCES `RESOURCE` (`ID`),
  CONSTRAINT `FK_RESOURCEACCOUNTED_RESOURCEID` FOREIGN KEY (`RESOURCEID`) REFERENCES `RESOURCE` (`ID`),
  CONSTRAINT `FK_RESOURCEACCOUNTED_currencyID` FOREIGN KEY (`currencyID`) REFERENCES `CURRENCY` (`ID`),
  CONSTRAINT `FK_RESOURCEACCOUNTED_unitID` FOREIGN KEY (`unitID`) REFERENCES `UNIT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESOURCEACCOUNTED`
--

LOCK TABLES `RESOURCEACCOUNTED` WRITE;
/*!40000 ALTER TABLE `RESOURCEACCOUNTED` DISABLE KEYS */;
/*!40000 ALTER TABLE `RESOURCEACCOUNTED` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESOURCETIMESPAN`
--

DROP TABLE IF EXISTS `RESOURCETIMESPAN`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESOURCETIMESPAN` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ENDDATE` date DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `STARTDATE` date DEFAULT NULL,
  `TIMESPAN` int(11) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `resourceID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_RESOURCETIMESPAN_resourceID` (`resourceID`),
  CONSTRAINT `FK_RESOURCETIMESPAN_resourceID` FOREIGN KEY (`resourceID`) REFERENCES `RESOURCE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESOURCETIMESPAN`
--

LOCK TABLES `RESOURCETIMESPAN` WRITE;
/*!40000 ALTER TABLE `RESOURCETIMESPAN` DISABLE KEYS */;
/*!40000 ALTER TABLE `RESOURCETIMESPAN` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESOURCEUNIVERSITYSHARE`
--

DROP TABLE IF EXISTS `RESOURCEUNIVERSITYSHARE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESOURCEUNIVERSITYSHARE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `UNIVERSITYSHARE` double DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `resourcetimespanID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_RESOURCEUNIVERSITYSHARE_resourcetimespanID` (`resourcetimespanID`),
  CONSTRAINT `FK_RESOURCEUNIVERSITYSHARE_resourcetimespanID` FOREIGN KEY (`resourcetimespanID`) REFERENCES `RESOURCETIMESPAN` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESOURCEUNIVERSITYSHARE`
--

LOCK TABLES `RESOURCEUNIVERSITYSHARE` WRITE;
/*!40000 ALTER TABLE `RESOURCEUNIVERSITYSHARE` DISABLE KEYS */;
/*!40000 ALTER TABLE `RESOURCEUNIVERSITYSHARE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UNIT`
--

DROP TABLE IF EXISTS `UNIT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UNIT` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ABBREVIATION` varchar(8) DEFAULT NULL,
  `NAME` varchar(60) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UNIT`
--

LOCK TABLES `UNIT` WRITE;
/*!40000 ALTER TABLE `UNIT` DISABLE KEYS */;
/*!40000 ALTER TABLE `UNIT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `POSTCODE` varchar(10) DEFAULT NULL,
  `STREETANDNR` varchar(60) DEFAULT NULL,
  `TOWN` varchar(60) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `countryID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_address_countryID` (`countryID`),
  CONSTRAINT `FK_address_countryID` FOREIGN KEY (`countryID`) REFERENCES `country` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `centerresources`
--

DROP TABLE IF EXISTS `centerresources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `centerresources` (
  `centerID` int(11) NOT NULL,
  `resourceID` int(11) NOT NULL,
  PRIMARY KEY (`centerID`,`resourceID`),
  KEY `FK_centerresources_resourceID` (`resourceID`),
  CONSTRAINT `FK_centerresources_centerID` FOREIGN KEY (`centerID`) REFERENCES `CENTER` (`ID`),
  CONSTRAINT `FK_centerresources_resourceID` FOREIGN KEY (`resourceID`) REFERENCES `RESOURCE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `centerresources`
--

LOCK TABLES `centerresources` WRITE;
/*!40000 ALTER TABLE `centerresources` DISABLE KEYS */;
/*!40000 ALTER TABLE `centerresources` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ISO2` varchar(2) DEFAULT NULL,
  `ISO3` varchar(3) DEFAULT NULL,
  `NAMEDE` varchar(64) DEFAULT NULL,
  `NAMEEN` varchar(64) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UNQ_country_0` (`ISO2`),
  UNIQUE KEY `UNQ_country_1` (`ISO3`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notificationsettings`
--

DROP TABLE IF EXISTS `notificationsettings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notificationsettings` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `EMAIL` varchar(60) DEFAULT NULL,
  `EMAILSERVER` varchar(60) DEFAULT NULL,
  `NAMEFROM` varchar(60) DEFAULT NULL,
  `PASSWORD` varchar(60) DEFAULT NULL,
  `PORT` int(11) DEFAULT NULL,
  `SSLPORT` int(11) DEFAULT NULL,
  `USESSL` tinyint(1) DEFAULT '0',
  `USER` varchar(60) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notificationsettings`
--

LOCK TABLES `notificationsettings` WRITE;
/*!40000 ALTER TABLE `notificationsettings` DISABLE KEYS */;
/*!40000 ALTER TABLE `notificationsettings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ADDITIONALINFORMATION` varchar(512) DEFAULT NULL,
  `ADMININFORMATION` varchar(512) DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `EMAIL` varchar(120) NOT NULL,
  `FIRSTNAME` varchar(60) DEFAULT NULL,
  `GENDER` varchar(15) DEFAULT NULL,
  `LASTNAME` varchar(60) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `UNIVERSITY_ID` int(11) DEFAULT NULL,
  `LOGIN_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `EMAIL` (`EMAIL`),
  KEY `FK_person_LOGIN_ID` (`LOGIN_ID`),
  KEY `FK_person_UNIVERSITY_ID` (`UNIVERSITY_ID`),
  CONSTRAINT `FK_person_LOGIN_ID` FOREIGN KEY (`LOGIN_ID`) REFERENCES `LOGIN` (`personID`),
  CONSTRAINT `FK_person_UNIVERSITY_ID` FOREIGN KEY (`UNIVERSITY_ID`) REFERENCES `university` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projectparticipants`
--

DROP TABLE IF EXISTS `projectparticipants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `projectparticipants` (
  `projectID` int(11) NOT NULL,
  `personID` int(11) NOT NULL,
  PRIMARY KEY (`projectID`,`personID`),
  KEY `FK_projectparticipants_personID` (`personID`),
  CONSTRAINT `FK_projectparticipants_personID` FOREIGN KEY (`personID`) REFERENCES `person` (`ID`),
  CONSTRAINT `FK_projectparticipants_projectID` FOREIGN KEY (`projectID`) REFERENCES `PROJECT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projectparticipants`
--

LOCK TABLES `projectparticipants` WRITE;
/*!40000 ALTER TABLE `projectparticipants` DISABLE KEYS */;
/*!40000 ALTER TABLE `projectparticipants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resourceusers`
--

DROP TABLE IF EXISTS `resourceusers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resourceusers` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `personID` int(11) DEFAULT NULL,
  `resourceID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_resourceusers_resourceID` (`resourceID`),
  KEY `FK_resourceusers_personID` (`personID`),
  CONSTRAINT `FK_resourceusers_personID` FOREIGN KEY (`personID`) REFERENCES `person` (`ID`),
  CONSTRAINT `FK_resourceusers_resourceID` FOREIGN KEY (`resourceID`) REFERENCES `RESOURCE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resourceusers`
--

LOCK TABLES `resourceusers` WRITE;
/*!40000 ALTER TABLE `resourceusers` DISABLE KEYS */;
/*!40000 ALTER TABLE `resourceusers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `personID` int(11) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  KEY `FK_roles_personID` (`personID`),
  CONSTRAINT `FK_roles_personID` FOREIGN KEY (`personID`) REFERENCES `person` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `university`
--

DROP TABLE IF EXISTS `university`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `university` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ABBREVIATION` varchar(8) DEFAULT NULL,
  `ISHAW` tinyint(1) DEFAULT '0',
  `NAME` varchar(60) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `addressID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ABBREVIATION` (`ABBREVIATION`),
  KEY `FK_university_addressID` (`addressID`),
  CONSTRAINT `FK_university_addressID` FOREIGN KEY (`addressID`) REFERENCES `address` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `university`
--

LOCK TABLES `university` WRITE;
/*!40000 ALTER TABLE `university` DISABLE KEYS */;
/*!40000 ALTER TABLE `university` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `universitymembers`
--

DROP TABLE IF EXISTS `universitymembers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `universitymembers` (
  `universityID` int(11) NOT NULL,
  `personID` int(11) NOT NULL,
  PRIMARY KEY (`universityID`,`personID`),
  KEY `FK_universitymembers_personID` (`personID`),
  CONSTRAINT `FK_universitymembers_personID` FOREIGN KEY (`personID`) REFERENCES `person` (`ID`),
  CONSTRAINT `FK_universitymembers_universityID` FOREIGN KEY (`universityID`) REFERENCES `university` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `universitymembers`
--

LOCK TABLES `universitymembers` WRITE;
/*!40000 ALTER TABLE `universitymembers` DISABLE KEYS */;
/*!40000 ALTER TABLE `universitymembers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-12 22:47:12
