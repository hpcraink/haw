'use strict';

const request = require('supertest');
const app = require('../bin/app');

//const app = "https://localhost:3000";

describe("REST responces for '/' path", () => {
  it("get('/') returns a predifined message", (done) => {
    request(app).get('/')
      .expect(200)
      .expect("Background server is up!")
      .end(error => error ? done.fail(error) : done()); // return error if done() fails, else done
  })
});

describe("REST for '/user'", () => {
  it()
})
