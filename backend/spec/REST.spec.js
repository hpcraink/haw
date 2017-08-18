'use strict';

const request = require('supertest');
const bcrypt = require('bcryptjs');

const MongoClient = require('mongodb').MongoClient;

const app = require('../app/app');
const mongodb_uri = "mongodb://localhost:27017/bwUniCluster"

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

xdescribe("drop unused collections in a mongo database", () => {
  const collections = ['countries', 'addresses', 'universities', 'logins', 'people'];
  collections.forEach( item => {
    beforeAll((done) => {
      // here drop the collection
      MongoClient.connect(mongodb_uri, (err, db) => {
        db.collection(item).drop((err, reply) => {
          if (err) {
            db.close();
            done.fail(err);
          }
          db.close();
          done();
        });
      });
    });
  });

  it("a dummy test, to drop the collections", (done) => {
    expect(true).toBe(true);
    done();
  })
});

describe("REST /user'", () => {
  // create test objects
  const user1 = {
    country: "Germany",
    address: "Schillerstr. 102",
    town: "Esslingen",
    postcode: "73730",
    firstName: "Arnold",
    lastName: "Oberhausen",
    gender: "Male",
    email: "arobit00@hs-esslingen.de",
    uni: 'Hochschule Esslingen',
    password: bcrypt.hashSync("test1234", salt)
  };

  const user2 = {
    country: "Germany",
    address: "Bahnhofstr. 21",
    town: "Stuttgart",
    postcode: "71710",
    firstName: "Jane",
    lastName: "Doe",
    gender: "Female",
    email: "jado01@hft-stuttgart.de",
    uni: "Hochschule für Technik Stuttgart",
    password: bcrypt.hashSync("test12345", salt)
  };

  // delete test objects from database
  //  // Set up the connection to the local db
  //  MongoClient.connect(mongodb_uri, (err, db) => {
  //    // From 'users' collection remove 'user' doc
  //    db.collection('countrys').deleteMany({lastName: "Test User REST"}, (err, res) => {
  //      if (err) throw err;
  //      db.close();
  //      done();
  //    });
  //  });

  // Tests
  xit("'/user' doesn't create an unvalidated user", (done) => {
    request(app).post('/user')
      //.send(unvalid)
      .send({firstName: "Unvalid", lastName: "Test User REST"})
      .expect(500) // will display the error message on testing, should be ignored as test is success
      .end(error => error ? done.fail() : done());
  });

  it("'/user' creates a new user", (done) => {
    request(app).post('/user')
      .send(user1)
      .expect(201)
      .end(error => error ? done.fail(error) : done());
  });

  xit("creates a new doc in 'persons' collection in mongodb", done => {
    MongoClient.connect(mongodb_uri, function(err, db) {
      db.collection('persons').findOne({lastName: 'Oberhausen'}, (err, result) => {
        expect(err).toBeNull();
        expect(result.firstName).toBe("Arnold");
        expect(result.email).toBe('arobit00@hs-esslingen.de');
        db.close();
        done();
      });
    });
  });
});
