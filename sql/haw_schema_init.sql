-- This SQL script initializes the schema and tables for the accounting of
-- computer resources of Universities of Applied Science (HAW) and the
-- Universities in general.
-- Author: Rainer Keller
--
-- TODO:
--   - Messenger:
-- 	   .. Bisher CONTENT, USER
--     SOLLTE NEU: Verknüpgfungstabelle haben mit:
--       PersonID, content, visbility, date
--
-- We need to parse every once in a while
--     mdiag -f -o acct | less -S
--
-- Aggregation over time:
--
-- The following tables need to be pre-filled:
--   1. country
--   2. currency
--   3. unit
--   4. notificationsettings
--

-- ----------------------------------------------------------------
-- Create the schema and the tables
CREATE SCHEMA IF NOT EXISTS haw;
USE haw;

-- ----------------------------------------------------------------
-- Prepare to have all names (tables, this file) and their content in UTF8
SET NAMES 'UTF8';
-- ----------------------------------------------------------------
-- First drop all the tables
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS center;
DROP TABLE IF EXISTS centerresources;
DROP TABLE IF EXISTS country;
DROP TABLE IF EXISTS currency;
DROP TABLE IF EXISTS login;
DROP TABLE IF EXISTS moabjob;
DROP TABLE IF EXISTS notificationsettings;
DROP TABLE IF EXISTS parsedfile;
DROP TABLE IF EXISTS person;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS projectparticipants;
DROP TABLE IF EXISTS resource;
DROP TABLE IF EXISTS resourceaccounted;
DROP TABLE IF EXISTS resourcetimespan;
DROP TABLE IF EXISTS resourceuniversityshare;
DROP TABLE IF EXISTS resourceusers;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS unit;
DROP TABLE IF EXISTS university;

-- ----------------------------------------------------------------
-- Create all tables;
CREATE TABLE address (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'The unique ID of the person',
    streetAndNr VARCHAR(60) COMMENT 'Street name and number',
    town VARCHAR(60) COMMENT 'City',
    postcode VARCHAR(12) COMMENT 'Postal code (ZIP)',
	countryID INT COMMENT 'The unique ID of the country',
    version INT NOT NULL DEFAULT 1 COMMENT 'The version of this row, necessary for optimistic logging',
    PRIMARY KEY (id)
) CHARACTER SET utf8 COLLATE utf8_bin COMMENT='entity Address';

CREATE TABLE center (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'The unique ID of the center',
    name VARCHAR(64) DEFAULT NULL COMMENT 'The name of the compute center or an Owner',
    abbreviation VARCHAR(8) DEFAULT NULL COMMENT 'The abbreviation of the compute center or the owner',
    version INT NOT NULL DEFAULT 1 COMMENT 'The version of this row, necessary for optimistic logging',
    PRIMARY KEY (id)
) CHARACTER SET utf8 COLLATE utf8_bin COMMENT='entity Center';

CREATE TABLE centerresources (
    centerID INT NOT NULL COMMENT 'The unique ID of the center',
    resourceID INT NOT NULL COMMENT 'The unique ID of the resource',
    PRIMARY KEY (centerID, resourceID)
) CHARACTER SET utf8 COLLATE utf8_bin COMMENT='collection of resources per center';

CREATE TABLE country (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'The unique ID of the country (defined by the UN)',
    iso2 CHAR(2) NOT NULL UNIQUE COMMENT 'The unique 2-letter code as defined by ISO 3166-1',
    iso3 CHAR(3) NOT NULL UNIQUE COMMENT 'The unique 3-letter code as defined by ISO 3166-1',
    namede VARCHAR(64) DEFAULT NULL COMMENT 'The official name in german',
    nameen VARCHAR(64) DEFAULT NULL COMMENT 'The official name in english',
    version INT NOT NULL DEFAULT 1 COMMENT 'The version of this row, necessary for optimistic logging',
    PRIMARY KEY (id)
) CHARACTER SET utf8 COLLATE utf8_bin COMMENT='entity Country';

CREATE TABLE currency (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'The unique ID of the currency',
    namede VARCHAR(64) DEFAULT NULL COMMENT 'The official name in german',
    nameen VARCHAR(64) DEFAULT NULL COMMENT 'The official name in english',
    abbreviation VARCHAR(8) DEFAULT NULL COMMENT 'The official name in english',
    symbol CHAR DEFAULT '' COMMENT 'The symbol used',
    version INT NOT NULL DEFAULT 1 COMMENT 'The version of this row, necessary for optimistic logging',
    PRIMARY KEY (id)
) CHARACTER SET utf8 COLLATE utf8_bin COMMENT='entity Currency';

CREATE TABLE login (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'The unique ID of the login',
    personID INT NOT NULL COMMENT 'The unique ID of the person',
    password VARCHAR(1024) NOT NULL COMMENT 'Secret password hashed with salt',
    salt VARCHAR(1024) NOT NULL COMMENT 'Random hash used for password checking',
    requestTime TIMESTAMP COMMENT 'Time when password has been sent to the person',
    version INT NOT NULL DEFAULT 1 COMMENT 'The version of this row, necessary for optimistic logging',
    UNIQUE(personID),
    PRIMARY KEY (id)
) CHARACTER SET utf8 COLLATE utf8_bin COMMENT='entity Login';

CREATE TABLE moabjob (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'The unique ID of this job',
    parsedFileID INT NOT NULL COMMENT 'The unique ID of the parsed file, this job was read from',
    moabAname VARCHAR(120) COMMENT 'The AName',
    moabAccount VARCHAR(60) COMMENT 'The Account',
    moabAverageUtilizedProcs DOUBLE DEFAULT 1.0 COMMENT 'Avg. Utilization of Procs',
    moabBackfill BOOLEAN DEFAULT 0 COMMENT 'Whether Backfill is used 0 = no, 1 = yes',
    moabBypassCount INT COMMENT 'The BypassCount',
    -- moabClass SET('singlenode', 'multinode', 'XXX') DEFAULT 'singlenode' COMMENT 'The class of this job',
    moabClass VARCHAR(60) COMMENT 'Name of class/queue required by job and number of class initiators required per task',
    moabDistrm VARCHAR(60) COMMENT 'The DstRM',
    moabDistrmjid INT COMMENT 'The unique JobId (as JobId, SystemJID, DstRMJID)',
    moabEffQueueTime DOUBLE COMMENT 'The EffQueueTime',
    moabEndTime BIGINT COMMENT 'The EndTime',
    moabExecutable VARCHAR(1024) COMMENT 'The executable',
    moabGlobalqueue BOOLEAN DEFAULT 1 COMMENT 'Whether a global Queue? 0 = no, 1 = yes',
    moabGroup VARCHAR(4) COMMENT 'The group',
	moabIWD VARCHAR(1024) COMMENT 'The IWD',
    moabJobId INT COMMENT 'The unique JobId (as JobId, SystemJID, DstRMJID)',
    moabNodeCount INT COMMENT 'The node count',
    moabNodeMatchPolicy VARCHAR(60) DEFAULT 'EXACTNODE' COMMENT 'The Node match Policy (EXACTNODE)',
    moabPE DOUBLE COMMENT 'Number of processor-equivalents requested by job',
	moabPartition VARCHAR(120) COMMENT 'Colon-delimited List of partitions the job has access to(?)',
    moabProcPerTask INT COMMENT 'Processes per Task',
    moabReqWallTime INT COMMENT 'Amount of seconds of Wall-clock time',
    moabStartCount INT COMMENT 'The number of times job has been started by Moab',
    moabStartPriority INT COMMENT 'The start priority of job',
    moabStartTime BIGINT COMMENT 'The time this job was started by the resource management system',
    moabSubmitDir VARCHAR(1024) COMMENT 'The directoy this job was submitted from',
    moabSubmitTime BIGINT COMMENT 'The time this job was submitted to the resource management system',
    moabSystemId VARCHAR(60) COMMENT 'The system ID',
    moabSystemJID INT COMMENT 'The unique JobId (as JobId, SystemJID, DstRMJID)',
    moabTaskCount INT COMMENT 'The number of task',
    moabTemplateSets VARCHAR(60) DEFAULT 'DEFAULT' COMMENT 'The TemplateSets',
    moabTimeQueuedTotal INT COMMENT 'Time (in seconds) the Job was queued in total',
    moabTotalCost INT COMMENT 'The number of UsedWallTime * the taskCount',
    moabTotalRequestedMemory INT COMMENT 'The total number of requested memory (in MB)',
    moabTotalRequestedNodes INT COMMENT 'The total number of requested nodes',
    moabTotalRequestedProcs INT COMMENT 'The total number of requested processes',
    moabTotalRequestedTasks INT COMMENT 'The total number of requested tasks',
    moabUMask VARCHAR(8) COMMENT 'The umask of the submitted jobs',
    moabUnicore BOOLEAN DEFAULT 0 COMMENT 'Whether this is a Unicore job? 0 = no, 1 = yes',
    moabUsedWallTime INT COMMENT 'The wall clock tiem used by this job',
    moabUser VARCHAR(60) COMMENT 'The user submitting this job',
    UNIQUE(moabJobId),  -- XXX This should be verified
    PRIMARY KEY (id)
) CHARACTER SET utf8 COLLATE utf8_bin COMMENT='entity moabjob';

CREATE TABLE notificationsettings (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'The unique ID of the notification settings',
    emailServer VARCHAR(60) NOT NULL COMMENT 'Hostserver of the email account',
    port INT NOT NULL COMMENT 'Port of SMTP server which the notifications will be sent to',
    sslPort INT NOT NULL DEFAULT 465 COMMENT 'Port of SMTP server for using SSL/TLS',
    useSsl BOOLEAN NOT NULL DEFAULT 1 COMMENT 'Use SSL/TLS to connect to SMTP server; 0 = no, 1 = yes',
    email VARCHAR(60) NOT NULL COMMENT 'EmailAddress from which the notifications will be sent',
    nameFrom VARCHAR(60) COMMENT 'Name from whom the notifications will be sent',
    user VARCHAR(60) NOT NULL COMMENT 'Username for the email account stored in email attribute',
    password VARCHAR(60) NOT NULL COMMENT 'Password for the email account stored in email attribute',
    version INT NOT NULL DEFAULT 1 COMMENT 'The version of this row, necessary for optimistic logging',
    PRIMARY KEY (id)
) CHARACTER SET utf8 COLLATE utf8_bin COMMENT='entity NotificationSettings';

CREATE TABLE parsedfile (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'The unique ID of the person',
    resourceID INT NOT NULL COMMENT 'The unique ID of the resource, this parsed file is stored from',
	fileName VARCHAR(60) COMMENT 'The files name being parsed',
    fileDate DATE COMMENT 'The files date having been parsed',
    fileHash VARCHAR(120) COMMENT 'Hash value stored for this file (application dependent)',
    version INT NOT NULL DEFAULT 1 COMMENT 'The version of this row, necessary for optimistic logging',
    PRIMARY KEY (id)
) CHARACTER SET utf8 COLLATE utf8_bin COMMENT='entity ParsedFiles';

CREATE TABLE person (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'The unique ID of the person',
    universityID INT NOT NULL COMMENT 'The unique ID of the University, this person is affiliated with',
	firstName VARCHAR(60) COMMENT 'First name of this Person',
    lastName VARCHAR(60) COMMENT 'Last name of this Person',
	gender SET('UNSPECIFIED', 'MALE', 'FEMALE') DEFAULT 'UNSPECIFIED' COMMENT 'The gender of this person',
    eMailaddress VARCHAR(120) COMMENT 'E-Mail Address',
    additionalInformation VARCHAR(512) COMMENT 'Additional information, available to user and admin, only',
    adminInformation VARCHAR(512) COMMENT 'Additional admin information, e.g. private to admins, only',
    deleted BOOLEAN NOT NULL DEFAULT 0 COMMENT '1 = entry is deleted; 0 or 1',
    version INT NOT NULL DEFAULT 1 COMMENT 'The version of this row, necessary for optimistic logging',
    PRIMARY KEY (id)
) CHARACTER SET utf8 COLLATE utf8_bin COMMENT='entity Person';

CREATE TABLE project (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'The unique ID of the project',
	name VARCHAR(60) COMMENT 'Name of the project',
    leaderPersonID INT NOT NULL COMMENT 'The unique ID of the person leading this project',
    startDate DATE NOT NULL COMMENT 'The start date of the project',
    endDate DATE NOT NULL COMMENT 'The end date of the project',
    version INT NOT NULL DEFAULT 1 COMMENT 'The version of this row, necessary for optimistic logging',
    PRIMARY KEY (id)
) CHARACTER SET utf8 COLLATE utf8_bin COMMENT='entity Project';

CREATE TABLE projectparticipants (
    projectID INT NOT NULL COMMENT 'The unique ID of the project',
    personID INT NOT NULL COMMENT 'The unique ID of the person',
    PRIMARY KEY (projectID, personID)
) CHARACTER SET utf8 COLLATE utf8_bin COMMENT='collection of participants per project';

CREATE TABLE resource (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'The unique ID of the resource',
    name VARCHAR(60) COMMENT 'The name of the resource (e.g. bwUnicluster, GPU, CPU, core, memory)',
    accountingName VARCHAR(60) COMMENT 'The name of the resource as accounted by Moab/Torque',
    version INT NOT NULL DEFAULT 1 COMMENT 'The version of this row, necessary for optimistic logging',
    PRIMARY KEY (id)
) CHARACTER SET utf8 COLLATE utf8_bin COMMENT='entity Resource';

CREATE TABLE resourceaccounted (
    resourceID INT NOT NULL COMMENT 'The unique ID of the resource',
    containedResourceID INT NOT NULL COMMENT 'The unique ID of the contained resource(s) making up another resource',
	numberOf INT DEFAULT 1 COMMENT 'The number of entities this is made up of',
	unitID INT DEFAULT 0 COMMENT 'The unit of resource',
    costPerUnit DOUBLE DEFAULT 0.0 COMMENT 'The cost per Unit in base currency',
    currencyID INT COMMENT 'The unique ID of the currency, this cost is based on',
    PRIMARY KEY (resourceID, containedResourceID)
) CHARACTER SET utf8 COLLATE utf8_bin COMMENT='collection of accounted resources per resource';

CREATE TABLE resourcetimespan (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'The unique ID of the timespan',
	resourceID INT NOT NULL COMMENT 'The unique ID of the resource',
    name VARCHAR(60) COMMENT 'The name of the timespan (e.g. Erstinstallation Phase1 or Ausbau2017)',
    -- Time-spans may NOT overlap, they are actually cumbersome, but the only way, since time-periods are not available for Primary Keys!
    timespan INT NOT NULL DEFAULT 1 COMMENT 'The unique time-span (e.g. 1.1.2017 - 31.12.2018 is timespan1, 1.1.2019-31.12.2020 is timespan2)',
    startDate DATE NOT NULL COMMENT 'Beginning of time-span for this share',
    endDate DATE NOT NULL COMMENT 'End of time-span for this share',
    version INT NOT NULL DEFAULT 1 COMMENT 'The version of this row, necessary for optimistic logging',
    PRIMARY KEY (id),
    -- We only allow timespan per id)
    UNIQUE (resourceID, timespan)
) CHARACTER SET utf8 COLLATE utf8_bin COMMENT='entity ResourceTimeSpan';

CREATE TABLE resourceuniversityshare (
	resourcetimespanID INT NOT NULL COMMENT 'The unique ID of the resources timespan',
    universityID INT NOT NULL COMMENT 'The unique ID of the University (having a share on this resource)',
	universityShare DOUBLE NOT NULL COMMENT 'The share of this university for this resource for this timespan, e.g. 10% -- all shares should add up to 100%',
    PRIMARY KEY (resourcetimespanID, universityID)
) CHARACTER SET utf8 COLLATE utf8_bin COMMENT='collection of share of University per resource for a specific timespan';

CREATE TABLE resourceusers (
    resourceID INT NOT NULL COMMENT 'The unique ID of the resource',
    personID INT NOT NULL COMMENT 'The unique ID of the person (which is a users of the resource)',
    username VARCHAR(60) NOT NULL COMMENT 'The username of this person on this resource to match with accounting data',
    PRIMARY KEY (resourceID, personID)
) CHARACTER SET utf8 COLLATE utf8_bin COMMENT='collection of users per resource';

CREATE TABLE roles (
    personID INT NOT NULL COMMENT 'The unique ID of the person',
    role SET('USER', 'PROJECTLEADER', 'UNIRESPONSIBLE', 'ADMIN') NOT NULL COMMENT 'The name of the role for this person',
    PRIMARY KEY (personID, role)
) CHARACTER SET utf8 COLLATE utf8_bin COMMENT='collection of (multiple) roles per person';

CREATE TABLE unit(
	id INT NOT NULL AUTO_INCREMENT COMMENT 'The unique ID of this unit',
	name VARCHAR(60) COMMENT 'Name of this unit (e.g. Stueck)',
    abbreviation VARCHAR(8) COMMENT 'The abbreviation of this unit (e.g. Stk)',
    version INT NOT NULL DEFAULT 1 COMMENT 'The version of this row, necessary for optimistic logging',
    PRIMARY KEY (id)
) CHARACTER SET utf8 COLLATE utf8_bin COMMENT='entity Unit';

CREATE TABLE university (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'The unique ID of the University',
    name VARCHAR(60) COMMENT 'The name of this University',
    abbreviation VARCHAR(8) COMMENT 'The abbreviation for the name of this University (like ST=Stuttgart, UL=Ulm, etc.)',
    isHAW BOOL NOT NULL DEFAULT 1 COMMENT 'Whether this University is a Hochschule fuer angewandte Wissenschaften (HAW); 0 = no, 1 = yes',
    addressID INT NOT NULL COMMENT 'The unique ID of the Address of this University',
    version INT NOT NULL DEFAULT 1 COMMENT 'The version of this row, necessary for optimistic logging',
    PRIMARY KEY (id)
) CHARACTER SET utf8 COLLATE utf8_bin COMMENT='entity University';


-- ----------------------------------------------------------------
-- Alter the tables to add the reference constraints between tables
ALTER TABLE address
	ADD CONSTRAINT countryID_address
		FOREIGN KEY (countryID)
		REFERENCES country(id)
		ON DELETE SET NULL
		ON UPDATE RESTRICT;

ALTER TABLE centerresources
	ADD CONSTRAINT centerID_centerresources
		FOREIGN KEY (centerID)
		REFERENCES center(id)
		ON DELETE CASCADE
		ON UPDATE NO ACTION,
	ADD CONSTRAINT resourceID_centerresources
		FOREIGN KEY (resourceID)
		REFERENCES resource(id)
		ON DELETE CASCADE
		ON UPDATE NO ACTION;

ALTER TABLE login
	ADD CONSTRAINT personID_login
		FOREIGN KEY (personID)
		REFERENCES person(id)
		ON DELETE CASCADE
		ON UPDATE NO ACTION;

ALTER TABLE moabjob
	ADD CONSTRAINT parsedFileID_moabjob
		FOREIGN KEY (parsedFileID)
		REFERENCES parsedFile(id)
		ON DELETE CASCADE
		ON UPDATE NO ACTION;

ALTER TABLE parsedfile
	ADD CONSTRAINT resourceID_parsedfile
		FOREIGN KEY (resourceID)
		REFERENCES resource(id)
		ON DELETE CASCADE
		ON UPDATE NO ACTION;

ALTER TABLE person
	ADD CONSTRAINT universityID_person
		FOREIGN KEY (universityID)
		REFERENCES university(id)
		ON DELETE CASCADE
		ON UPDATE NO ACTION;

ALTER TABLE project
	ADD CONSTRAINT personID_project
		FOREIGN KEY (leaderPersonID)
		REFERENCES person(id)
		ON DELETE CASCADE
		ON UPDATE NO ACTION;

ALTER TABLE projectparticipants
	ADD CONSTRAINT personID_projectparticipants
		FOREIGN KEY (personID)
		REFERENCES person(id)
		ON DELETE CASCADE
		ON UPDATE NO ACTION,
	ADD CONSTRAINT projectID_projectparticipants
		FOREIGN KEY (projectID)
		REFERENCES project(id)
		ON DELETE CASCADE
		ON UPDATE NO ACTION;

ALTER TABLE resourceaccounted
	ADD CONSTRAINT resourceID_resourceaccounted
		FOREIGN KEY (resourceID)
		REFERENCES resource(id)
		ON DELETE CASCADE
		ON UPDATE NO ACTION,
	ADD CONSTRAINT containedresourceID_resourceaccounted
		FOREIGN KEY (containedResourceID)
		REFERENCES resource(id)
		ON DELETE CASCADE
		ON UPDATE NO ACTION,
	ADD CONSTRAINT unitID_resourceaccounted
		FOREIGN KEY (unitID)
		REFERENCES unit(id)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION,
	ADD CONSTRAINT currencyID_resourceaccounted
		FOREIGN KEY (currencyID)
		REFERENCES currency(id)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION;

ALTER TABLE resourcetimespan
	ADD CONSTRAINT resourceID_resourcetimespan
		FOREIGN KEY (resourceID)
		REFERENCES resource(id)
		ON DELETE CASCADE
		ON UPDATE NO ACTION;

ALTER TABLE resourceuniversityshare
	ADD CONSTRAINT resourcetimespanID_resourceuniversityshare
		FOREIGN KEY (resourcetimespanID)
		REFERENCES resourcetimespan(id)
		ON DELETE CASCADE
		ON UPDATE NO ACTION,
	ADD CONSTRAINT universityID_resourceuniversity
		FOREIGN KEY (universityID)
		REFERENCES university(id)
		ON DELETE CASCADE
		ON UPDATE NO ACTION;

ALTER TABLE resourceusers
	ADD CONSTRAINT resourceID_resourceusers
		FOREIGN KEY (resourceID)
		REFERENCES resource(id)
		ON DELETE CASCADE
		ON UPDATE NO ACTION,
	ADD CONSTRAINT personID_resourceusers
		FOREIGN KEY (personID)
		REFERENCES person(id)
		ON DELETE CASCADE
		ON UPDATE NO ACTION;

ALTER TABLE roles
	ADD CONSTRAINT personID_roles
		FOREIGN KEY (personID)
		REFERENCES person(id)
		ON DELETE CASCADE
		ON UPDATE NO ACTION;

ALTER TABLE university
	ADD CONSTRAINT addressID_university
		FOREIGN KEY (addressID)
		REFERENCES address(id)
		ON DELETE CASCADE
		ON UPDATE NO ACTION;

-- -----------------------------------------------------------------------------;
COMMIT;
-- -----------------------------------------------------------------------------;

-- -----------------------------------------------------------------------------;
-- END
-- -----------------------------------------------------------------------------;
