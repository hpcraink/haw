-- Setup the first tables with mandatory static data
-- Author: Rainer Keller

-- -----------------------------------------------------------------------;
-- SETUP THE REQUIRED TABLES
-- 1. The country information
-- -----------------------------------------------------------------------;
INSERT INTO country (id, iso2, iso3, nameen, namede) VALUES
    (4, 'AF', 'AFG', 'Afghanistan', 'Afghanistan'),
    (8, 'AL', 'ALB', 'Albania', 'Albanien'),
    (10, 'AQ', 'ATA', 'Antarctica', 'Antarktis'),
    (12, 'DZ', 'DZA', 'Algeria', 'Algerien'),
    (16, 'AS', 'ASM', 'American Samoa', 'Amerikanisch-Samoa'),
    (20, 'AD', 'AND', 'Andorra', 'Andorra'),
    (24, 'AO', 'AGO', 'Angola', 'Angola'),
    (28, 'AG', 'ATG', 'Antigua and Barbuda', 'Antigua und Barbuda'),
    (31, 'AZ', 'AZE', 'Azerbaijan', 'Aserbaidschan'),
    (32, 'AR', 'ARG', 'Argentina', 'Argentinien'),
    (36, 'AU', 'AUS', 'Australia', 'Australien'),
    (40, 'AT', 'AUT', 'Austria', 'Österreich'),
    (44, 'BS', 'BHS', 'Bahamas', 'Bahamas'),
    (48, 'BH', 'BHR', 'Bahrain', 'Bahrain'),
    (50, 'BD', 'BGD', 'Bangladesh', 'Bangladesh'),
    (51, 'AM', 'ARM', 'Armenia', 'Armenien'),
    (52, 'BB', 'BRB', 'Barbados', 'Barbados'),
    (56, 'BE', 'BEL', 'Belgium', 'Belgien'),
    (60, 'BM', 'BMU', 'Bermuda', 'Bermudas'),
    (64, 'BT', 'BTN', 'Bhutan', 'Bhutan'),
    (68, 'BO', 'BOL', 'Bolivia (Plurinational State of)', 'Bolivien'),
    (70, 'BA', 'BIH', 'Bosnia and Herzegovina', 'Bosnien-Herzegowina'),
    (72, 'BW', 'BWA', 'Botswana', 'Botswana'),
    (74, 'BV', 'BVT', 'Bouvet Island', 'Bouvetinsel'),
    (76, 'BR', 'BRA', 'Brazil', 'Brasilien'),
    (84, 'BZ', 'BLZ', 'Belize', 'Belize'),
    (86, 'IO', 'IOT', 'British Indian Ocean Territory', 'Brit. Territorium im Indischen Ozean'),
    (90, 'SB', 'SLB', 'Solomon Islands', 'Solomonen'),
    (92, 'VG', 'VGB', 'Virgin Islands (British)', 'Jungferninseln (Brit.)'),
    (96, 'BN', 'BRN', 'Brunei Darussalam', 'Brunei'),
    (100, 'BG', 'BGR', 'Bulgaria', 'Bulgarien'),
    (104, 'MM', 'MMR', 'Myanmar', 'Myanmar'),
    (108, 'BI', 'BDI', 'Burundi', 'Burundi'),
    (112, 'BY', 'BLR', 'Belarus', 'Weißrussland'),
    (116, 'KH', 'KHM', 'Cambodia', 'Kambodscha'),
    (120, 'CM', 'CMR', 'Cameroon', 'Kamerun'),
    (124, 'CA', 'CAN', 'Canada', 'Kanada'),
    (132, 'CV', 'CPV', 'Cabo Verde', 'Kap Verde'),
    (136, 'KY', 'CYM', 'Cayman Islands', 'Kaiman Inseln'),
    (140, 'CF', 'CAF', 'Central African Republic', 'Zentralafrikanische Republik'),
    (144, 'LK', 'LKA', 'Sri Lanka', 'Sri Lanka'),
    (148, 'TD', 'TCD', 'Chad', 'Tschad'),
    (152, 'CL', 'CHL', 'Chile', 'Chile'),
    (156, 'CN', 'CHN', 'China', 'China'),
    (158, 'TW', 'TWN', 'Taiwan (Province of China)', 'Taiwan'),
    (162, 'CX', 'CXR', 'Christmas Island', 'Weihnachtsinsel'),
    (166, 'CC', 'CCK', 'Cocos (Keeling) Islands', 'Kokosinseln'),
    (170, 'CO', 'COL', 'Colombia', 'Kolumbien'),
    (174, 'KM', 'COM', 'Comoros', 'Komoren'),
    (175, 'YT', 'MYT', 'Mayotte', 'Mayotte'),
    (178, 'CG', 'COG', 'Congo', 'Kongo'),
    (180, 'CD', 'COD', 'Congo (the Democratic Republic of the)', 'Kongo, Demokr. Republik'),
    (184, 'CK', 'COK', 'Cook Islands', 'Cookinseln'),
    (188, 'CR', 'CRI', 'Costa Rica', 'Costa Rica'),
    (191, 'HR', 'HRV', 'Croatia', 'Kroatien'),
    (192, 'CU', 'CUB', 'Cuba', 'Kuba'),
    (196, 'CY', 'CYP', 'Cyprus', 'Zypern'),
    (203, 'CZ', 'CZE', 'Czech Republic', 'Tschechische Republik'),
    (204, 'BJ', 'BEN', 'Benin', 'Benin'),
    (208, 'DK', 'DNK', 'Denmark', 'Dänemark'),
    (212, 'DM', 'DMA', 'Dominica', 'Dominika'),
    (214, 'DO', 'DOM', 'Dominican Republic', 'Dominikanische Republik'),
    (218, 'EC', 'ECU', 'Ecuador', 'Ecuador'),
    (222, 'SV', 'SLV', 'El Salvador', 'El Salvador'),
    (226, 'GQ', 'GNQ', 'Equatorial Guinea', 'Äquatorial Guinea'),
    (231, 'ET', 'ETH', 'Ethiopia', 'Äthiopien'),
    (232, 'ER', 'ERI', 'Eritrea', 'Eritrea'),
    (233, 'EE', 'EST', 'Estonia', 'Estland'),
    (234, 'FO', 'FRO', 'Faroe Islands', 'Färöer Inseln'),
    (238, 'FK', 'FLK', 'Falkland Islands [Malvinas]', 'Falkland Inseln'),
    (239, 'GS', 'SGS', 'South Georgia and the South Sandwich Islands', 'Südgeorgien und südl. Sandwichinseln'),
    (242, 'FJ', 'FJI', 'Fiji', 'Fidschi'),
    (246, 'FI', 'FIN', 'Finland', 'Finnland'),
    (248, 'AX', 'ALA', 'Åland Islands', 'Åland'),
    (250, 'FR', 'FRA', 'France', 'Frankreich'),
    (254, 'GF', 'GUF', 'French Guiana', 'Franz. Guyana'),
    (258, 'PF', 'PYF', 'French Polynesia', 'Franz. Polynesien'),
    (260, 'TF', 'ATF', 'French Southern Territories', 'Franz. Süd-Territorium'),
    (262, 'DJ', 'DJI', 'Djibouti', 'Djibuti'),
    (266, 'GA', 'GAB', 'Gabon', 'Gabun'),
    (268, 'GE', 'GEO', 'Georgia', 'Georgien'),
    (270, 'GM', 'GMB', 'Gambia', 'Gambia'),
    (275, 'PS', 'PSE', 'Palestine', 'Palästina'),
    (276, 'DE', 'DEU', 'Germany', 'Deutschland'),
    (288, 'GH', 'GHA', 'Ghana', 'Ghana'),
    (292, 'GI', 'GIB', 'Gibraltar', 'Gibraltar'),
    (296, 'KI', 'KIR', 'Kiribati', 'Kiribati'),
    (300, 'GR', 'GRC', 'Greece', 'Griechenland'),
    (304, 'GL', 'GRL', 'Greenland', 'Grönland'),
    (308, 'GD', 'GRD', 'Grenada', 'Grenada'),
    (312, 'GP', 'GLP', 'Guadeloupe', 'Guadeloupe'),
    (316, 'GU', 'GUM', 'Guam', 'Guam'),
    (320, 'GT', 'GTM', 'Guatemala', 'Guatemala'),
    (324, 'GN', 'GIN', 'Guinea', 'Guinea'),
    (328, 'GY', 'GUY', 'Guyana', 'Guyana'),
    (332, 'HT', 'HTI', 'Haiti', 'Haiti'),
    (334, 'HM', 'HMD', 'Heard Island and McDonald Islands', 'Heard und McDonaldinseln'),
    (336, 'VA', 'VAT', 'Holy See', 'Staat Vatikanstadt'),
    (340, 'HN', 'HND', 'Honduras', 'Honduras'),
    (344, 'HK', 'HKG', 'Hong Kong', 'Hong Kong'),
    (348, 'HU', 'HUN', 'Hungary', 'Ungarn'),
    (352, 'IS', 'ISL', 'Iceland', 'Island'),
    (356, 'IN', 'IND', 'India', 'Indien'),
    (360, 'ID', 'IDN', 'Indonesia', 'Indonesien'),
    (364, 'IR', 'IRN', 'Iran (Islamic Republic of)', 'Iran'),
    (368, 'IQ', 'IRQ', 'Iraq', 'Irak'),
    (372, 'IE', 'IRL', 'Ireland', 'Irland'),
    (376, 'IL', 'ISR', 'Israel', 'Israel'),
    (380, 'IT', 'ITA', 'Italy', 'Italien'),
    (384, 'CI', 'CIV', 'Côte d\'Ivoire', 'Elfenbeinküste'),
    (388, 'JM', 'JAM', 'Jamaica', 'Jamaika'),
    (392, 'JP', 'JPN', 'Japan', 'Japan'),
    (398, 'KZ', 'KAZ', 'Kazakhstan', 'Kasachstan'),
    (400, 'JO', 'JOR', 'Jordan', 'Jordanien'),
    (404, 'KE', 'KEN', 'Kenya', 'Kenia'),
    (408, 'KP', 'PRK', 'Korea (the Democratic People\'s Republic of)', 'Demokr. Volksrepublik Korea'),
    (410, 'KR', 'KOR', 'Korea (the Republic of)', 'Südkorea'),
    (414, 'KW', 'KWT', 'Kuwait', 'Kuwait'),
    (417, 'KG', 'KGZ', 'Kyrgyzstan', 'Kirgisistan'),
    (418, 'LA', 'LAO', 'Lao People\'s Democratic Republic', 'Laos'),
    (422, 'LB', 'LBN', 'Lebanon', 'Libanon'),
    (426, 'LS', 'LSO', 'Lesotho', 'Lesotho'),
    (428, 'LV', 'LVA', 'Latvia', 'Lettland'),
    (430, 'LR', 'LBR', 'Liberia', 'Liberia'),
    (434, 'LY', 'LBY', 'Libya', 'Libyen'),
    (438, 'LI', 'LIE', 'Liechtenstein', 'Liechtenstein'),
    (440, 'LT', 'LTU', 'Lithuania', 'Litauen'),
    (442, 'LU', 'LUX', 'Luxembourg', 'Luxemburg'),
    (446, 'MO', 'MAC', 'Macao', 'Macau'),
    (450, 'MG', 'MDG', 'Madagascar', 'Madagaskar'),
    (454, 'MW', 'MWI', 'Malawi', 'Malawi'),
    (458, 'MY', 'MYS', 'Malaysia', 'Malaysia'),
    (462, 'MV', 'MDV', 'Maldives', 'Malediven'),
    (466, 'ML', 'MLI', 'Mali', 'Mali'),
    (470, 'MT', 'MLT', 'Malta', 'Malta'),
    (474, 'MQ', 'MTQ', 'Martinique', 'Martinique'),
    (478, 'MR', 'MRT', 'Mauritania', 'Mauretanien'),
    (480, 'MU', 'MUS', 'Mauritius', 'Mauritius'),
    (484, 'MX', 'MEX', 'Mexico', 'Mexiko'),
    (492, 'MC', 'MCO', 'Monaco', 'Monaco'),
    (496, 'MN', 'MNG', 'Mongolia', 'Mongolei'),
    (498, 'MD', 'MDA', 'Moldova (the Republic of)', 'Moldavien'),
    (499, 'ME', 'MNE', 'Montenegro', 'Montenegro'),
    (500, 'MS', 'MSR', 'Montserrat', 'Montserrat'),
    (504, 'MA', 'MAR', 'Morocco', 'Marokko'),
    (508, 'MZ', 'MOZ', 'Mozambique', 'Mosambik'),
    (512, 'OM', 'OMN', 'Oman', 'Oman'),
    (516, 'NA', 'NAM', 'Namibia', 'Namibia'),
    (520, 'NR', 'NRU', 'Nauru', 'Nauru'),
    (524, 'NP', 'NPL', 'Nepal', 'Nepal'),
    (528, 'NL', 'NLD', 'Netherlands', 'Niederlande'),
    (531, 'CW', 'CUW', 'Curaçao', 'Curaçao'),
    (533, 'AW', 'ABW', 'Aruba', 'Aruba'),
    (534, 'SX', 'SXM', 'Sint Maarten (Dutch part)', 'Sint Maarten (holl. Teil)'),
    (535, 'BQ', 'BES', 'Bonaire', 'Bonaire, Sint Eustatius, Saba'),
    (540, 'NC', 'NCL', 'New Caledonia', 'Neukaledonien'),
    (548, 'VU', 'VUT', 'Vanuatu', 'Vanuatu'),
    (554, 'NZ', 'NZL', 'New Zealand', 'Neuseeland'),
    (558, 'NI', 'NIC', 'Nicaragua', 'Nicaragua'),
    (562, 'NE', 'NER', 'Niger', 'Niger'),
    (566, 'NG', 'NGA', 'Nigeria', 'Nigeria'),
    (570, 'NU', 'NIU', 'Niue', 'Niue'),
    (574, 'NF', 'NFK', 'Norfolk Island', 'Norfolk Inseln'),
    (578, 'NO', 'NOR', 'Norway', 'Norwegen'),
    (580, 'MP', 'MNP', 'Northern Mariana Islands', 'Marianen'),
    (581, 'UM', 'UMI', 'United States Minor Outlying Islands', 'U.S. Minor Outlying Islands'),
    (583, 'FM', 'FSM', 'Micronesia (Federated States of)', 'Mikronesien'),
    (584, 'MH', 'MHL', 'Marshall Islands', 'Marshall Inseln'),
    (585, 'PW', 'PLW', 'Palau', 'Palau'),
    (586, 'PK', 'PAK', 'Pakistan', 'Pakistan'),
    (591, 'PA', 'PAN', 'Panama', 'Panama'),
    (598, 'PG', 'PNG', 'Papua New Guinea', 'Papua Neuguinea'),
    (600, 'PY', 'PRY', 'Paraguay', 'Paraguay'),
    (604, 'PE', 'PER', 'Peru', 'Peru'),
    (608, 'PH', 'PHL', 'Philippines', 'Philippinen'),
    (612, 'PN', 'PCN', 'Pitcairn', 'Pitcairn'),
    (616, 'PL', 'POL', 'Poland', 'Polen'),
    (620, 'PT', 'PRT', 'Portugal', 'Portugal'),
    (624, 'GW', 'GNB', 'Guinea-Bissau', 'Guinea Bissau'),
    (626, 'TL', 'TLS', 'Timor-Leste', 'Osttimor'),
    (630, 'PR', 'PRI', 'Puerto Rico', 'Puerto Rico'),
    (634, 'QA', 'QAT', 'Qatar', 'Qatar'),
    (638, 'RE', 'REU', 'Réunion', 'Reunion'),
    (642, 'RO', 'ROU', 'Romania', 'Rumänien'),
    (643, 'RU', 'RUS', 'Russian Federation', 'Rußland'),
    (646, 'RW', 'RWA', 'Rwanda', 'Ruanda'),
    (652, 'BL', 'BLM', 'Saint Barthélemy', 'Saint-Barthélemy'),
    (654, 'SH', 'SHN', 'Saint Helena', 'St. Helena'),
    (659, 'KN', 'KNA', 'Saint Kitts and Nevis', 'St. Kitts Nevis Anguilla'),
    (660, 'AI', 'AIA', 'Anguilla', 'Anguilla'),
    (662, 'LC', 'LCA', 'Saint Lucia', 'Saint Lucia'),
    (663, 'MF', 'MAF', 'Saint Martin (French part)', 'Saint Martin (franz. Teil)'),
    (666, 'PM', 'SPM', 'Saint Pierre and Miquelon', 'St. Pierre und Miquelon'),
    (670, 'VC', 'VCT', 'Saint Vincent and the Grenadines', 'St. Vincent'),
    (674, 'SM', 'SMR', 'San Marino', 'San Marino'),
    (678, 'ST', 'STP', 'Sao Tome and Principe', 'São Tomé und Príncipe'),
    (682, 'SA', 'SAU', 'Saudi Arabia', 'Saudi-Arabien'),
    (686, 'SN', 'SEN', 'Senegal', 'Senegal'),
    (688, 'RS', 'SRB', 'Serbia', 'Serbien'),
    (690, 'SC', 'SYC', 'Seychelles', 'Seychellen'),
    (694, 'SL', 'SLE', 'Sierra Leone', 'Sierra Leone'),
    (702, 'SG', 'SGP', 'Singapore', 'Singapur'),
    (703, 'SK', 'SVK', 'Slovakia', 'Slowakei'),
    (704, 'VN', 'VNM', 'Viet Nam', 'Vietnam'),
    (705, 'SI', 'SVN', 'Slovenia', 'Slowenien'),
    (706, 'SO', 'SOM', 'Somalia', 'Somalia'),
    (710, 'ZA', 'ZAF', 'South Africa', 'Südafrika'),
    (716, 'ZW', 'ZWE', 'Zimbabwe', 'Zimbabwe'),
    (724, 'ES', 'ESP', 'Spain', 'Spanien'),
    (728, 'SS', 'SSD', 'South Sudan', 'Südsudan'),
    (729, 'SD', 'SDN', 'Sudan', 'Sudan'),
    (732, 'EH', 'ESH', 'Western Sahara*', 'Westsahara'),
    (740, 'SR', 'SUR', 'Suriname', 'Surinam'),
    (744, 'SJ', 'SJM', 'Svalbard and Jan Mayen', 'Svalbard und Jan Mayen'),
    (748, 'SZ', 'SWZ', 'Swaziland', 'Swasiland'),
    (752, 'SE', 'SWE', 'Sweden', 'Schweden'),
    (756, 'CH', 'CHE', 'Switzerland', 'Schweiz'),
    (760, 'SY', 'SYR', 'Syrian Arab Republic', 'Syrien'),
    (762, 'TJ', 'TJK', 'Tajikistan', 'Tadschikistan'),
    (764, 'TH', 'THA', 'Thailand', 'Thailand'),
    (768, 'TG', 'TGO', 'Togo', 'Togo'),
    (772, 'TK', 'TKL', 'Tokelau', 'Tokelau'),
    (776, 'TO', 'TON', 'Tonga', 'Tonga'),
    (780, 'TT', 'TTO', 'Trinidad and Tobago', 'Trinidad Tobago'),
    (784, 'AE', 'ARE', 'United Arab Emirates', 'Vereinigte Arabische Emirate'),
    (788, 'TN', 'TUN', 'Tunisia', 'Tunesien'),
    (792, 'TR', 'TUR', 'Turkey', 'Türkei'),
    (795, 'TM', 'TKM', 'Turkmenistan', 'Turkmenistan'),
    (796, 'TC', 'TCA', 'Turks and Caicos Islands', 'Turks und Kaikos Inseln'),
    (798, 'TV', 'TUV', 'Tuvalu', 'Tuvalu'),
    (800, 'UG', 'UGA', 'Uganda', 'Uganda'),
    (804, 'UA', 'UKR', 'Ukraine', 'Ukraine'),
    (807, 'MK', 'MKD', 'Macedonia (the former Yugoslav Republic of)', 'Mazedonien'),
    (818, 'EG', 'EGY', 'Egypt', 'Ägypten'),
    (826, 'GB', 'GBR', 'United Kingdom of Great Britain and Northern Ireland', 'Großbritannien (UK)'),
    (831, 'GG', 'GGY', 'Guernsey', 'Guernsey (Kanalinsel)'),
    (832, 'JE', 'JEY', 'Jersey', 'Jersey (Kanalinsel)'),
    (833, 'IM', 'IMN', 'Isle of Man', 'Isle of Man'),
    (834, 'TZ', 'TZA', 'Tanzania', 'Tansania'),
    (840, 'US', 'USA', 'United States of America', 'Vereinigte Staaten von Amerika'),
    (850, 'VI', 'VIR', 'Virgin Islands (U.S.)', 'Jungferninseln (USA)'),
    (854, 'BF', 'BFA', 'Burkina Faso', 'Burkina Faso'),
    (858, 'UY', 'URY', 'Uruguay', 'Uruguay'),
    (860, 'UZ', 'UZB', 'Uzbekistan', 'Usbekistan'),
    (862, 'VE', 'VEN', 'Venezuela (Bolivarian Republic of)', 'Venezuela'),
    (876, 'WF', 'WLF', 'Wallis and Futuna', 'Wallis und Futuna'),
    (882, 'WS', 'WSM', 'Samoa', 'Samoa'),
    (887, 'YE', 'YEM', 'Yemen', 'Jemen'),
    (894, 'ZM', 'ZMB', 'Zambia', 'Sambia');

-- -----------------------------------------------------------------------;
-- 2. Currency information
-- -----------------------------------------------------------------------;
INSERT INTO currency (nameen, namede, abbreviation, symbol) VALUES
    ('Euro', 'Euro', 'EUR', '€'),
    ('Dollar', 'US Dollar', 'USD', '$');

-- -----------------------------------------------------------------------;
-- 3. The units
-- -----------------------------------------------------------------------;
INSERT INTO unit (name, abbreviation) VALUES
    ('Stück', 'Stk'),    -- ID:1
    ('Gigabyte', 'GB'),
    ('Terabyte', 'TB');

SET @UnitStkID = 1;

-- -----------------------------------------------------------------------;
-- 4. The notification settings
-- -----------------------------------------------------------------------;
INSERT INTO notificationsettings(emailServer, port, sslPort, useSsl, email, nameFrom, user, password) VALUES
   ('stmp.gmx,de', 25, 465, false, 'admin@grid.hs-esslingen.de',
    'HAW-Accounting Admin', 'admin@grid.hs-esslingen.de', 'geheim');

COMMIT;
