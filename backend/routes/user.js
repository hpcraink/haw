'use strict';

var bcrypt = require('bcryptjs'); // Lib to help you hash passwords
var jwt = require('jsonwebtoken'); // an implementation of JSON Web Tokens

// import mongoose models
const Person = require('../models/person');
const Login = require('../models/login');
const Address = require('../models/address');
const Country = require('../models/country');
const University = require('../models/university');

const Router = require('router');
const router = new Router();

const salt = bcrypt.genSaltSync(10); // generates salt for bcrypt hash password, 2^10 circles of iterration

// POST requests
// '/user' POST
router.post('/', function(req, res, done) {

  // objects to use later
  let country, university, address;

  // array of new mongoose objects to insert in db
  let insert_objs = [];

  // insert into db error handler
  function error_handler(err) {
    return res.status(500).json({
      title: "An error occured!",
      error: err
    });
  };

  // load the user
  Person.findOne({eMailaddress: req.body.email}, (err, userFromDb) => {
    if (err) return done(err);
    if (userFromDb) {
      // if user already exists return 409
      res.status(409).json({
        title: "User already exists",
        error: {message: "User already exists"}
      });
      return;
      done();
    }

    // load the country
    Country.findOne({nameen: req.body.country}, (err, countryFromDb) => {
      if (err) return done(err);
      if (countryFromDb) {
        country = countryFromDb;
      } else {
        // take the data from json file and create a new object to insert in db
        const countries = require('../json/countries.json');
        const new_country = countries.filter((obj) => {
          return obj.nameen === req.body.country;
        });
        // create a new country mongoose doc
        country = new Country(new_country[0]);
        // save doc to the database
        country.save((err, result) => {
          if (err) error_handler(err);
        });
      };

      // load the university
      University.findOne({name: req.body.uni}, (err, uniFromDb) => {
        if (err) return done(err);
        if (uniFromDb) {
          university = uniFromDb;
        } else {
          const unis = require('../json/unis.json');
          const new_uni = unis.filter((obj) => {
            return obj.name === req.body.uni;
          });
          // create a new university mongoose doc
          university = new University(new_uni[0]);
          // save doc to the database
          university.save((err, result) => {
            if (err) error_handler(err);
          });
        };

        // load the address
        Address.findOne({streetAndNr: req.body.address, postcode: req.body.postcode}, (err, addressFromDb) => {
          if (err) return done(err);
          if (addressFromDb) {
            address = addressFromDb;
          } else {
            // create a new Address object
            address = new Address({
              streetAndNr: req.body.address,
              town: req.body.town,
              postcode: req.body.postcode,
              countryID: country._id
            });
            // save doc to the database
            address.save((err, result) => {
              if (err) error_handler(err);
            });
          };

          // create a new person mongoose object to insert in db
          const person = new Person({
            universityID: university._id,
            addressID: address._id,
            firstName: req.body.firstName,
            lastName: req.body.lastName,
            gender: req.body.gender,
            eMailaddress: req.body.email,
            deleted: false
          });
          // save doc to the database
          person.save((err, result) => {
            if (err) error_handler(err);
          });

          // create a login object to insert into db
          const login = new Login({
            password: bcrypt.hashSync(req.body.password, salt),
            personID: person._id,
            requestTime: '', // TODO: set the time user was created
          });
          // save doc to the database
          login.save((err, result) => {
            if (err) error_handler(err);
          });

          // send a responce status of 201 if all the data is sent to db
          res.status(201).json({message: "new user saved"});
        })
      })
    });
  });
});

/*
router.post('/signin', function(req, res, done) {
  User.findOne({email: req.body.email}, function(err, user) {
    if (err) {
      return res.status(500).json({
        title: 'An error occured',
        error: err
      });
    }
    if (!user) {
      return res.status(401).json({
        title: "Login failed",
        error: {message: "Invalid login credentials"}
      });
    }
    if (!bcrypt.compareSync(req.body.password, user.password)) {
      return res.status(401).json({
        title: "Login failed",
        error: {message: "Invalid login credentials"}
      });
    }
    var token = jwt.sign({user: user}, "bwHPC-C5_secret", {expiresIn: 7200}); // generates a token for comunicating with the server (google jwt)
    res.status(200).json({
      message: "Successfully logged in",
      token: token,
      userId: user._id
    });
  });
});
*/
module.exports = router;
