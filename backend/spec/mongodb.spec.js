'use strict';

const MongoClient = require('mongodb').MongoClient;
const Server = require('mongodb').Server;
const bcrypt = require('bcryptjs');
const fs = require('fs');

const db_uri = "mongodb://localhost:27017/bwUniCluster"


// select a test_uni object from bw_unis.json file
//const unis = require('../json/bw_unis.json');
//const test_uni = unis.filter((obj) => {
//  return obj.abbr === 'es';
//});
//console.log(test_uni);

// select a test country object from countries.json file
//const countries = require('../json/countries.json');
//const test_country = countries.filter((obj) => {
//  return obj.iso2 === "DE";
//});
//console.log(test_country);

const salt = bcrypt.genSaltSync(10); // generates salt for bcrypt hash password, 2^10 circles of iterration

// TESTS
xdescribe("MongoDB tests if", function() {

  // a test user Object
  const user = {
    country: "Germany",
    address: "Schillerstr. 102",
    town: "Esslingen",
    postcode: "73730",
    firstName: "Arnold",
    lastName: "Oberhausen",
    gender: "Herr",
    email: "arobit00@hs-esslingen.de",
    uni: 'Hocschule Esslingen',
    password: bcrypt.hashSync("test1234", salt)
  };

  beforeAll((done) => {
    // insert test user to the database
    MongoClient.connect(db_uri, function(err, db) {
      db.collection('users').insertOne(user, (err, res) => {
        if (err) throw err;
        db.close();
        done();
      })
    });
  });

  afterAll((done) => {
    // remove test user from the database
    MongoClient.connect(db_uri, function(err, db) {
      db.collection('users').deleteOne(user, (err, res) => {
        if (err) throw err;
        db.close();
        done();
      })
    });
  });

  it("mongo server is running and a 'bwUniCluster database exists'", function(done) {
    MongoClient.connect(db_uri, function(err, db) {
      expect(err).toBeNull();
      expect(db).not.toBeNull();
      expect(db).not.toBeUndefined();
      db.close();
      done();
    });
  });

  it("user is successfully inserted", (done) => {
    MongoClient.connect(db_uri, function(err, db) {
      db.collection('users').findOne({lastName: 'Oberhausen'}, (err, result) => {
        expect(err).toBeNull();
        expect(result.firstName).toBe("Arnold");
        expect(result.email).toBe('arobit00@hs-esslingen.de');
        db.close();
        done();
      })
    });
  });
});
