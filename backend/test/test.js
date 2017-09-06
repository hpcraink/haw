'use strict';

const request = require('supertest');
const bcrypt = require('bcryptjs');

const MongoClient = require('mongodb').MongoClient;
const mongodb_uri = "mongodb://localhost:27017/bwUniCluster"

const app = require('../app/app');

/*
// import mongoose models
const Person = require('../models/person');
const Login = require('../models/login');
const Address = require('../models/address');
const Country = require('../models/country');
const University = require('../models/university');
*/

//generate salt for bcrypt hash password, doing 2^10 cycles of iterration
const salt = bcrypt.genSaltSync(10);

// TESTS
describe("GET '/'", () => {
  it("responds with a greeting", (done) => {
    request(app).get('/')
      .expect(200)
      .expect("Background server is up!")
      .end(error => error ? done.fail(error) : done()); // return error if done() fails, else done
  })
});

describe("REST /user'", () => {
  // create test objects
  const user1 = {
    country: "Germany",
    address: "TEST ADDRESS. 102",
    town: "Esslingen",
    postcode: "73730",
    firstName: "TEST USER",
    lastName: "ONE",
    gender: "Male",
    email: "test_user1@hs-esslingen.de",
    uni: 'Hochschule Esslingen',
    password: bcrypt.hashSync("test1234", salt)
  };

  const user2 = {
    country: "Germany",
    address: "TEST ADDRESS. 21",
    town: "Stuttgart",
    postcode: "71710",
    firstName: "TEST USER",
    lastName: "TWO",
    gender: "Female",
    email: "test_user2@hft-stuttgart.de",
    uni: "Hochschule für Technik Stuttgart",
    password: bcrypt.hashSync("test12345", salt)
  };

  after((done) => { // doesn't properly work, as with jasmine. is executed before it async
    // delete test objects from database
    MongoClient.connect(mongodb_uri, (err, db) => {
      // remove login and user credentials from db
      db.collection('people').findOne({eMailaddress: user1.email}, (err, found_person) => {
        if (err) throw err;
        console.log("FP ==>", found_person);
        // remove login doc
        db.collection('logins').deleteOne({personID: found_person._id}, (error, result) => {
          if (error) throw error;
          console.log("FP Log ==>", found_person);
          // remove user from people collection
          db.collection('people').deleteOne({_id: found_person._id}, (error, result) => {
            if (error) throw error;
          });
        });

        // remove address doc from db
        db.collection('addresses').deleteOne({streetAndNr: user1.address}, (error, result) => {
          if (error) throw error;
        });
        done();
      });
    });
  });

  // Tests
  it("creates a new user", (done) => {
    request(app).post('/user')
      .send(user1)
      .expect(201)
      .end((error, res) => error ? done(error) : done());
  });

});
