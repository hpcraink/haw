-- This SQL script inserts data to test the schema and build a
-- preliminary data-set, until the web-interface may CRUD itselve.

USE haw;

set @germanyID = (select id from country where namede = 'Deutschland');

-- -----------------------------------------------------------------------;
-- Initialize Addresses
-- -----------------------------------------------------------------------;
-- 1. Addresses for HAWs, Universities and Compute Centers;
-- PLEASE NOTE: DO NOT CHANGE ORDER WITHOUT CHANGING ORDER in table university.
INSERT INTO address(streetAndNr, town, postcode, countryID) VALUE
    ('Beethovenstr. 1', 'Aalen', '73430', @germanyID), -- HS Aalen (AA); ID:1
    ('Jakobstr. 6', 'Albstadt-Ebingen', '73430', @germanyID), -- HS Albstadt-Sigmaringen (AS)
    ('Karlstr. 11', 'Biberach', '88400', @germanyID), -- HS Biberach
    ('Kanalstr. 33', 'Esslingen', '73728', @germanyID),     -- HS ES (ES)
    -- HS ev. Freiburg
    -- HS kath. Freiburg
    ('Robert-Gerwig-Platz 1', 'Furtwangen', '78120', @germanyID), -- HS Furtwangen
    ('Max-Planck-Str. 39', 'Heilbronn', '74081', @germanyID), -- HS Heilbronn
    ('Moltkestr. 30', 'Karlsruhe',  '76133', @germanyID), -- HS Karlsruhe (KA)
    -- HS öffentliche VW Kehl
    ('Alfred-Wachtel-Str. 8', 'Konstanz', '78462', @germanyID), -- HTWG Konstanz
    -- HS ev. Ludwigsburg
    -- HS öffentlich VW Ludwigsburg
    ('Paul-Wittsack-Str. 10', 'Mannheim', '68163', @germanyID), -- HS Mannheim
    ('Neckarsteige 6-10', 'Nürtingen', '72622', @germanyID), -- HS Nürtingen-Geislingen
    ('Badstr. 24', 'Offenburg', '77652', @germanyID), -- HS Offenburg
    ('Tiefenbronner Str. 65', 'Pforzheim', '75175', @germanyID), -- HS Pforzheim
    ('Doggenriedstr.', 'Weingarten', '88250', @germanyID), -- HS Ravensburg-Weingarten
    ('Alteburgstr. 150', 'Reutlingen', '72762', @germanyID), -- HS Reutlingen
    ('Schadenweilerhof', 'Rottenburg a.N.', '72108', @germanyID), -- HS Rottenburg
    -- HS f. Gestaltung Schwäbisch Gmünd
    ('Nobelstr. 10', 'Stuttgart', '70569', @germanyID), -- HS der Medien Stuttgart (HDM)
    ('Schellingstr. 24', 'Stuttgart', '70174', @germanyID), -- HS für Technik Stuttgart (HS)
    ('Prittwitzstr. 10', 'Ulm', '89075', @germanyID); -- HS Ulm

-- 2. Addresses for Universities
INSERT INTO address(streetAndNr, town, postcode, countryID) VALUE    
    ('Friedrichstr. 39 ', 'Freiburg', '79098', @germanyID), -- Uni Freiburg; ID:19
    ('Grabengasse 1', 'Heidelberg', '69117', @germanyID), -- Uni Heidelberg
    ('Schloss Hohenheim 1', 'Stuttgart-Hohenheim', '70599', @germanyID), -- Uni Hohenheim
    ('Universitätsstr. 10', 'Konstanz', '78464', @germanyID), -- Uni Konstanz
    ('Kaiserstr. 12', 'Karlsruhe', '76131', @germanyID), -- KIT
    ('Schloss', 'Mannheim', '68131', @germanyID), -- Uni Mannheim
    ('Keplerstr. 7', 'Stuttgart', '70174', @germanyID), -- Uni Stuttgart
    ('Geschwister-Scholl-Platz', 'Tübingen', '72074', @germanyID), -- Uni Tübingen
    ('Helmholtzstr. 16', 'Ulm', '89081', @germanyID); -- Uni Ulm
    
    
-- 3. Addresses for Compute Centers; for now only KIT-SCC; all others are part of their respective University
INSERT INTO address(streetAndNr, town, postcode, countryID) VALUE
    ('KIT-Campus Nord, Hermann-von-Helmholtz-Platz 1', 'Eggenstein-Leopoldshafen', 76344, @germanyID); -- KIT SCC; ID:28

-- -----------------------------------------------------------------------;
-- Initialize Universities
-- -----------------------------------------------------------------------;
-- aa Aalen
-- as Albstadt-Sigmaringen
-- es Esslingen
-- fr Freiburg
-- hd Heidelberg
-- hf HS Furtwangen?
-- hk HS Karlsruhe
--    HS Konstanz?
-- ho Uni Hohenheim
--    HS Offenburg?
-- hr Hochschule Reutlingen? HS Ravensburg-Weingarten?
-- hs HfT Stuttgart
-- ht Hochschule der Medien????
-- hu HS Ulm
-- kn Uni Konstanz? HTWG Konstanz
-- ma Uni Mannheim
-- ro HS Rottenburg?
-- st Uni Stuttgart
-- tu Uni Tübingen
-- ul Uni Ulm
INSERT INTO university (name, abbreviation, isHAW, addressID) VALUE
    ('HS Aalen', 'aa', true, 1),                     -- ID:1
    ('HS Albstadt-Sigmatingen', 'as', true, 2),
    ('HS Biberach', 'hb', true, 3),                  -- XXX Is abbreviation correct?
    ('HS Esslingen', 'es', true, 4),
    ('HS Furtwangen', 'hf', true, 5),                -- XXX Is abbreviation correct?
    ('HS Heilbronn', 'hh', true, 6),                 -- XXX Is abbreviation correct?
    ('HS Karlsruhe', 'hk', true, 7),
    ('HTWG Konstanz', 'XXX', true, 8),               -- abbreviation unkonw
    ('HS Mannheim', 'hm', true, 9),                  -- XXX Is abbreviation correct?
    ('HS Nürtingen-Geislingen', 'hn', true, 10),     -- XXX Is abbreviation correct?
    ('HS Offenburg', 'ho', true, 11),                -- XXX abbreviation is certainly wrong
    ('HS Pforzheim', 'hp', true, 12),                -- XXX Is abbreviation correct?
    ('HS Ravensburg-Weingarten', 'hr', true, 13),    -- XXX Is abbreviation correct?
    ('HS Reutlingen', 'hr', true, 14),               -- XXX Is abbreviation correct?
    ('HS Rottenburg', 'ro', true, 15),
    ('HS der Medien Stuttgart', 'hdm', true, 16),    -- XXX Is abbreviation correct?
    ('HfT Stuttgart', 'hs', true, 17),
    ('HS Ulm', 'hu', true, 18);
SET @HawAalenID = 1;
SET @HawAlbstadtID = 2;
SET @HawBiberachID = 3;
SET @HawEsslingenID = 4;
SET @HawFurtwangenID = 5;
SET @HawHeilbronnID = 6;
SET @HawKarlsruheID = 7;
SET @HawKonstanzID = 8;
SET @HawMannheimID = 9;
SET @HawNuertingenID = 10;
SET @HawOffenburgID = 11;
SET @HawPforzheimID = 12;
SET @HawRavensburgID = 13;
SET @HawReutlingenID = 14;
SET @HawRottenburgID = 15;
SET @HawHdmStuttgartID = 16;
SET @HawHftStuttgartID = 17;
SET @HawUlmID = 18;

INSERT INTO university (name, abbreviation, isHAW, addressID) VALUE
    ('Uni Freiburg', 'fr', false, 19),               -- ID:19
    ('Uni Heidelberg', 'hd', false, 20),
    ('Uni Hohenheim', 'ho', false, 21),              -- XXX Is abbreviation correct?
    ('KIT', 'ka', false, 22),                        -- XXX Is abbreviation correct?
    ('Uni Konstanz', 'kn', false, 23),
    ('Uni Mannheim', 'ma', false, 24),
    ('Uni Stuttgart', 'st', false, 25),
    ('Uni Tübingen', 'tu', false, 26),
    ('Uni Ulm', 'ul', false, 27);
SET @UniFreiburgID = 19;
SET @UniHeidelbergID = 20;
SET @UniHohenheimID = 21;
SET @UniKarlsruheID = 22;
SET @UniKonstanzID = 23;
SET @UniMannheimID = 24;
SET @UniStuttgartID = 25;
SET @UniTuebingenID = 26;
SET @UniUlmID = 27;
    
-- -----------------------------------------------------------------------;
-- Initialize Centers
-- -----------------------------------------------------------------------;
INSERT INTO center(name, abbreviation) VALUE
    ('RZ der Uni Freiburg', 'RZ FR'), -- ID:1
    ('RZ der Uni Mannheim/Heidelberg', 'RZ MA/HD'), -- ID:2
    ('Zentrum für Datenverarbeitung Uni Tübingen', 'ZDV TÜ'), -- ID:3
    ('Kommunikations- und Informationszentrum der Uni Ulm', 'KIZ UL'), -- ID:4
    ('Steinbuch Centre for Computing des KIT', 'SCC-KIT'), -- ID:5
    ('Höchstleistungsrechenzentrum Stuttgart', 'HLRS'); -- ID:6

-- -----------------------------------------------------------------------;
-- Initialize Resources
-- -----------------------------------------------------------------------;    
INSERT INTO resource(name, accountingName) VALUE
    ('bwUniCluster', 'uc1'), -- ID: 1
    ('Justus', 'justus'); -- ID: 2
    
INSERT INTO resource(name, accountingName) VALUE
    ('bwUniCluster thin nodes', 'uc1'), -- ID:3 -- XXX is the accounted Name correct?
    ('bwUniCluster Broadwell nodes', 'uc1');    -- XXX is the accounted Name correct?
-- -----------------------------------------------------------------------;
-- Initialize the mapping of resources to centers
-- -----------------------------------------------------------------------;
INSERT INTO centerresources(centerID, resourceID) VALUE
    (4, 1), -- bwUnicluster is hosted at KIT
    (3, 2); -- Justus is hosted at Uni Ulm
    
-- -----------------------------------------------------------------------;
-- Initialize the mapping of timespan to resource
-- -----------------------------------------------------------------------;
INSERT INTO resourcetimespan(resourceID, name, timespan, startDate, endDate) VALUE
    (1, 'Erstinstallation bwUnicluster', 1, '2014-01-27', '2017-05-1'), -- bwUnicluster installation DATE denoted as 'YYYY-MM-DD'; ID:1
    (1, '1. Erweiterung bwUnicluster', 2, '2017-05-02', '2022-05-01'); -- bwUnicluster extension -- valid for 5 years; ID:2
    
-- -----------------------------------------------------------------------;
-- Initialize the accounted resources
-- -----------------------------------------------------------------------;    
INSERT INTO resourceaccounted(resourceID, containedResourceID, numberOf, costPerUnit, unitID, currencyID) VALUE
    (1, 3, 512, @UnitStkID, 0.0, NULL), -- bwUniCluster has 512 thin nodes
    (1, 4, 352, @UnitStkID, 0.0, NULL); -- bwUniCluster has 352 Broadwell nodes

-- -----------------------------------------------------------------------;
-- Initialize the mapping of university share to resourcetimespan
-- -----------------------------------------------------------------------;

-- Data from /hptc_cluster/moab_includes/acct.ini.vor_weiterung
INSERT INTO resourceuniversityshare(resourcetimespanID, universityID, universityShare) VALUE
-- Shares of Universities/HAWs on bwUnicluster (First installation):
    (1, @UniFreiburgID,     9.542 / 100),  -- Share of Uni Freiburg
    (1, @UniHeidelbergID,   9.542 / 100),  -- Share of Uni Heidelberg
    (1, @UniHohenheimID,    4.762 / 100),  -- Share of Uni Hohenheim
    (1, @UniKarlsruheID,   38.333 / 100),  -- Share of KIT
    (1, @UniKonstanzID,     4.762 / 100),  -- Share of Uni Konstanz
    (1, @UniMannheimID,     7.143 / 100),  -- Share of Uni Mannheim
    (1, @UniStuttgartID,    9.524 / 100),  -- Share of Uni Stuttgart -- it's NOT 9.542, but 9.524
    (1, @UniTuebingenID,    2.143 / 100),  -- Share of Uni Tübingen
    (1, @UniUlmID,          9.542 / 100),  -- Share of Uni Ulm
    (1, @HawEsslingenID,    4.762 / 2 / 100),  -- Share of HS Esslingen
    (1, @HawHftStuttgartID, 4.762 / 2 / 100);  -- Share of HfT Stuttgart

-- Data from /hptc_cluster/moab_includes/acct.ini
set @hawShare = 0.06525; -- HAW-Share is 6.525% of the system after upgrade
INSERT INTO resourceuniversityshare(resourcetimespanID, universityID, universityShare) VALUE
-- Shares of Universities on "1. Erweiterung bwUnicluster":
    (2, @HawAalenID,        (25/155) * @hawShare),  -- Share of HS Aalen: 16% of HAW-share
    (2, @HawAlbstadtID,     (25/155) * @hawShare),  -- Share of HS Albstadt-Sigmaringen: 16%
    (2, @HawEsslingenID,    (25/155) * @hawShare),  -- Share of HS Esslingen: 16%
    (2, @HawFurtwangenID,   (15/155) * @hawShare),  -- Share of HS Furtwangen: 10%
    (2, @HawKarlsruheID,    (20/155) * @hawShare),  -- Share of HS Karlsruhe: 13%
    (2, @HawReutlingenID,   (5/155) * @hawShare),   -- Share of HS Reutlingen: 3%
    (2, @HawRottenburgID,   (5/155) * @hawShare),   -- Share of HS Rottenburg: 3%
    (2, @HawHftStuttgartID, (25/155) * @hawShare),  -- Share of HfT Stuttgart: 16%
    (2, @HawUlmID,          (10/155) * @hawShare),  -- Share of HS Ulm: 6%
    (2, @UniFreiburgID,      6.525 / 100),  -- Share of Uni Freiburg
    (2, @UniHeidelbergID,    6.525 / 100),  -- Share of Uni Heidelberg
    (2, @UniHohenheimID,     7.830 / 100),  -- Share of Uni Hohenheim
    (2, @UniKarlsruheID,    43.393 / 100),  -- Share of KIT
    (2, @UniKonstanzID,      9.788 / 100),  -- Share of Uni Konstanz
    (2, @UniMannheimID,      4.894 / 100),  -- Share of Uni Mannheim
    (2, @UniStuttgartID,     6.525 / 100),  -- Share of Uni Stuttgart
    (2, @UniTuebingenID,     1.468 / 100),  -- Share of Uni Tübingen
    (2, @UniUlmID,           6.525 / 100);  -- Share of Uni Ulm
    
-- -----------------------------------------------------------------------;
-- Initialize Users
-- -----------------------------------------------------------------------;
INSERT INTO person(universityID, firstName, lastName, gender, eMail, adminInformation, deleted) VALUE
    (@HawAlbstadtID, 'Andreas', 'Knoblauch', 'MALE', 'xxx@hs-albsig.de', 'HAW Responsible for HS Albstadt-Sigmaringen', false), -- ID:1
    (@HawEsslingenID, 'Alexandru', 'Saramet', 'MALE', 'xxx@hs-esslingen.de', 'Admin', false),
    (@HawEsslingenID, 'Adrian', 'Reber', 'MALE', 'xxx@hs-esslingen.de', 'Admin', true), -- only deleted user...
    (@HawHftStuttgartID, 'Rainer', 'Keller', 'MALE', 'xxx@hft-stuttgart.de', 'Admin', false),
    (@HawHftStuttgartID, 'Rafael', 'Doros', 'MALE', 'xxx@hft-stuttgart.de', 'Here: user only', false), -- ID:5
    (@HawHftStuttgartID, 'Ursula', 'Voss', 'FEMALE', 'xxx@hft-stuttgart.de', '', false),
    (@HawHftStuttgartID, 'Maximilian', 'von der Grün', 'MALE', 'xxx@hft-stuttgart.de', '', false); -- ID:7

-- -----------------------------------------------------------------------;
-- Initialize Roles (multiple per users)
-- -----------------------------------------------------------------------;
INSERT INTO roles(personID, role) VALUE
    (1, 'UNIRESPONSIBLE'),
    (2, 'ADMIN'),
    (2, 'UNIRESPONSIBLE'),
    (2, 'USER'),
    (4, 'ADMIN'),
    (4, 'UNIRESPONSIBLE'),
    (4, 'USER'),
    (5, 'USER'),
    (6, 'PROJECTLEADER'),
    (6, 'USER'),
    (7, 'USER');

-- -----------------------------------------------------------------------;
-- Initialize Login
-- -----------------------------------------------------------------------;
-- Logins for all users with password set to 'changeit'
INSERT INTO login(personID, password, salt) VALUE
    (1, '4e993db078e89d12266b95963d9384a24697df90bbff027676ec6da8a8b53380', '265ea337-4583-4b81-b28e-c8a954d8bfbd'),
    (2, '4e993db078e89d12266b95963d9384a24697df90bbff027676ec6da8a8b53380', '265ea337-4583-4b81-b28e-c8a954d8bfbd'),
    (3, '4e993db078e89d12266b95963d9384a24697df90bbff027676ec6da8a8b53380', '265ea337-4583-4b81-b28e-c8a954d8bfbd'),
    (4, '4e993db078e89d12266b95963d9384a24697df90bbff027676ec6da8a8b53380', '265ea337-4583-4b81-b28e-c8a954d8bfbd'),
    (5, '4e993db078e89d12266b95963d9384a24697df90bbff027676ec6da8a8b53380', '265ea337-4583-4b81-b28e-c8a954d8bfbd'),
    (6, '4e993db078e89d12266b95963d9384a24697df90bbff027676ec6da8a8b53380', '265ea337-4583-4b81-b28e-c8a954d8bfbd');

-- -----------------------------------------------------------------------;
-- Initialize Projects and Projectparticipants
-- -----------------------------------------------------------------------;
INSERT INTO project (name, leaderPersonID, startDate, endDate) VALUE
    ('ANSYS CFX project', 6, '2017-01-01', '2022-12-31');

INSERT INTO projectparticipants (projectID, personID) VALUE
    (1, 6),  (1, 7); -- Participants are Ursula and Max

-- -----------------------------------------------------------------------;
-- Initialize the resource users
-- -----------------------------------------------------------------------;
INSERT INTO resourceusers VALUES
    (1, 5, 'hs_rkeller'),    -- on bwUniCluster, User Rainer Kelelr
    (1, 2, 'es_asaramet');  -- on bwUniCluster, User Alexandru Saramet

COMMIT;
