'use strict';

const MongoClient = require('mongodb').MongoClient;
const Server = require('mongodb').Server;
const bcrypt = require('bcryptjs');

const db_uri = "mongodb://localhost:27017/bwUniCluster"

describe("MongoDB tests if", function() {

  // a test user Object
  const user = {
    firstName: "Test",
    lastName: "Test User",
    email: 'test@test.info',
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

  it("user is succesifully inserted", (done) => {
    MongoClient.connect(db_uri, function(err, db) {
      db.collection('users').findOne({lastName: 'Test User'}, (err, result) => {
        expect(err).toBeNull();
        expect(result.firstName).toBe("Test");
        expect(result.email).toBe('test@test.info');
        db.close();
        done();
      })
    });
  });
});
