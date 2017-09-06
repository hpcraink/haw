#!/usr/bin/env node

'use strict';

const fs = require('fs');

// import *.json file, returns an array of json objects
const back = require('./back/countries.json');

// create the new countries Array
let countries = [];

// iterrate through old json file and add info to new one
for (let key in back) {
  // create objects to be written in the array
  let obj = {
    iso2: back[key]['cca2'],
    iso3: back[key]['cca3'],
    namede: back[key]['translations']['deu']['common'],
    nameen: back[key]['name']['common']
  };
  // write JSON objects to array
  //countries.push(JSON.stringify(obj));
  countries.push(obj);
};

// write the array as a JSON string to front/countries.json
// you'll need to parse when read: let contents = JSON.parse(countries)
fs.writeFile('./front/countries.json', JSON.stringify(countries), (err) => {
  if (err) throw err;
  console.log("New ./front/countries.json created!");
});
