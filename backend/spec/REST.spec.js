'use strict';

const request = require('supertest');
const bcrypt = require('bcryptjs');

const MongoClient = require('mongodb').MongoClient;

const app = require('../app/app');
const mongodb_uri = "mongodb://localhost:27017/bwUniCluster"

describe("GET '/'", () => {
  it("responds with a greeting", (done) => {
    request(app).get('/')
      .expect(200)
      .expect("Background server is up!")
      .end(error => error ? done.fail(error) : done()); // return error if done() fails, else done
  })
});

describe("REST /user'", () => {
  // create a test user Object
  const User = require('../models/user');
  const user = new User({
    firstName: "Test REST",
    lastName: "Test User REST",
    password: bcrypt.hashSync('testPass', 10),
    email: 'test@test.rest',
  });

  // delete test objects from database
  afterAll((done) => {
    // Set up the connection to the local db
    MongoClient.connect(mongodb_uri, (err, db) => {
      // From 'users' collection remove 'user' doc
      db.collection('users').deleteMany({lastName: "Test User REST"}, (err, res) => {
        if (err) throw err;
        db.close();
        done();
      });
    });
  });

  // Tests
  it("'/user' doesn't create an unvalidated user", (done) => {
    request(app).post('/user')
      //.send(unvalid)
      .send({firstName: "Unvalid", lastName: "Test User REST"})
      .expect(500) // will display the error message on testing, should be ignored as test is success
      .end(error => error ? done.fail() : done());
  });

  it("'/user' creates a new user", (done) => {
    request(app).post('/user')
      .send(user)
      .expect(201)
      .end(error => error ? done.fail(error) : done());
  });
});
