#!/usr/bin/env node
"use strict";
const fs = require('fs');

// unis Array of Objects
const bw_unis = [
  {
    name: 'Albert-Ludwigs-Universität Freiburg',
    abbr: '',
    isHAW: false
  },
  {
    name: 'Christian-Albrechts-Universität zu Kiel',
    abbr: '',
    isHAW: false
  },
  {
    name: 'DHBW Lörrach',
    abbr: '',
    isHAW: false
  },
  {
    name: 'DHBW Stuttgart',
    abbr: '',
    isHAW: false
  },
  {
    name: 'DHBW Heidenheim',
    abbr: '',
    isHAW: false,
  },
  {
    name: 'DHBW Karlsruhe',
    abbr: '',
    isHAW: false
  },
  {
    name: 'DHBW Mannheim',
    abbr: '',
    isHAW: false
  },
  {
    name: 'DHBW Villingen-Schwenningen',
    abbr: '',
    isHAW: false
  },
  {
    name: 'FIZ Karlsruhe',
    abbr: '',
    isHAW: false
  },
  {
    name: 'Hochschule Aalen - Technik und Wirtschaft',
    abbr: 'aa',
    isHAW: true
  },
  {
    name: 'Hochschule Albstadt-Sigmaringen',
    abbr: 'as',
    isHAW: true
  },
  {
    name: 'Hochschule Biberach',
    abbr: '',
    isHAW: false
  },
  {
    name: 'Hochschule der Medien Stuttgart',
    abbr: '',
    isHAW: false
  },
  {
    name: 'Hochschule Esslingen',
    abbr: 'es',
    isHAW: true
  },
  {
    name: 'Hochschule für Forstwirtschaft Rottenburg (HFR)',
    abbr: 'ro',
    isHAW: true
  },
  {
    name: 'Hochschule für öffentliche Verwaltung und Finanzen Ludwigsburg',
    abbr: '',
    isHAW: false
  },
  {
    name: 'Hochschule für Technik Stuttgart',
    abbr: 'hs',
    isHAW: true
  },
  {
    name: 'Hochschule Furtwangen University',
    abbr: 'hf',
    isHAW: true
  },
  {
    name: 'HfWU Nürtingen-Geislingen',
    abbr: '',
    isHAW: false
  },
  {
    name: 'Hochschule Heilbronn',
    abbr: '',
    isHAW: false
  },
  {
    name: 'Hochschule Karlsruhe - Technik und Wirtschaft',
    abbr: 'hk',
    isHAW: true
  },
  {
    name: 'Hochschule Kehl',
    abbr: '',
    isHAW: false
  },
  {
    name: 'HTWG Konstanz',
    abbr: 'ht',
    isHAW: true
  },
  {
    name: 'Hochschule Mannheim',
    abbr: '',
    isHAW: false
  },
  {
    name: 'Hochschule Offenburg',
    abbr: '',
    isHAW: false
  },
  {
    name: 'Hochschule Pforzheim IdP',
    abbr: '',
    isHAW: false
  },
  {
    name: 'Hochschule Ravensburg-Weingarten',
    abbr: '',
    isHAW: false
  },
  {
    name: 'Reutlingen University / Hochschule Reutlingen',
    abbr: 'hr',
    isHAW: true
  },
  {
    name: 'Hochschule Ulm',
    abbr: 'hu',
    isHAW: true
  },
  {
    name: 'Karlsruher Institut für Technologie (KIT)',
    abbr: '',
    isHAW: false
  },
  {
    name: 'PH Freiburg',
    abbr: '',
    isHAW: false
  },
  {
    name: 'Pädagogische Hochschule Heidelberg',
    abbr: '',
    isHAW: false
  },
  {
    name: 'PH Karlsruhe',
    abbr: '',
    isHAW: false
  },
  {
    name: 'PH Schwäbisch Gmünd',
    abbr: '',
    isHAW: false
  },
  {
    name: 'PH Weingarten',
    abbr: '',
    isHAW: false
  },
  {
    name: 'PH Ludwigsburg',
    abbr: '',
    isHAW: false
  },
  {
    name: 'Universität Heidelberg',
    abbr: '',
    isHAW: false
  },
  {
    name: 'Universität Hohenheim',
    abbr: '',
    isHAW: false
  },
  {
    name: 'Universität Konstanz',
    abbr: '',
    isHAW: false
  },
  {
    name: 'Universität Mannheim',
    abbr: '',
    isHAW: false
  },
  {
    name: 'Universität Rostock',
    abbr: '',
    isHAW: false
  },
  {
    name: 'Universität Stuttgart',
    abbr: '',
    isHAW: false
  },
  {
    name: 'Universität Tübingen',
    abbr: '',
    isHAW: false
  },
  {
    name: 'Universität Ulm',
    abbr: '',
    isHAW: false
  }
];

// write the array as a JSON string to front/bw_unis.json
// you'll need to parse when read: let contents = JSON.parse(content)
fs.writeFile('./front/bw_unis.json', JSON.stringify(bw_unis), (err) => {
  if (err) throw err;
  console.log('front/bw_unis.json created!');
});
