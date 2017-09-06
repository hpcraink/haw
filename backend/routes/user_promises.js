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

// predifined function to save array of mongoose objects to mongodb in a post request
let save_array = function(res, array_of_collections) {
  // set an error primitive
  let error;

  array_of_collections.forEach( obj => {
    // save data to collections in mongodb
    obj.save(function(err, result) {
      if (err) {
        // save the ocured error
        error = err;
      };
    });
  });

  // if an error occured during mongodb save opperations, throw 500 and error message
  if (error) {
    /*return res.status(500).json({
      title: 'An error occured',
      error: error
    });*/
    console.error(error);
  };
  // if all saved without errors return 201
  /*res.status(201).json({
    message: "info saved to mongodb!",
  }); */
};

// POST requests
// '/user' POST
router.post('/', function(req, res, done) {
  // Use Promises to chain async functions returned by mongoose
  // need to use Promise.exec() to get a fully-flaged promise in mongoose

  // - create new objects to insert in dbs
  let insert_objs = []; // the array containig all the objects
  let country, university, address; // objects used to create links

  // -- people findOne query:
  const queryPerson = Person.findOne({eMailaddress: req.body.email}).exec(done);

  // countries findOne query
  const queryCountry = Country.findOne({nameen: req.body.country}).exec();

  // univeristies findOne query
  const queryUni = University.findOne({name: req.body.uni}).exec();

  // addresses findOne query
  const queryAddress = Address.findOne({streetAndNr: req.body.address, postcode: req.body.postcode}).exec();

  queryPerson.then((found_person) => {
    // Check if person already exist in the database
    if (found_person) {
      // if person exist in database exit with the res status 409
      // throwing an error to be catched later
      throw new Error("User already exists!")
    }
    console.log("exec 1");
    //throw new Error("User already exists!")
    //done();
  })
    .then(() => {
      // execute after queryPerson.findOne:
      // see if country already exists in the database
      queryCountry.then(found_country => {
        console.log("exec 2");
        // if country not found in database
        if (!found_country) {
          console.log("exec 2 if");
          // -- find the chosen country object in the json file
          const countries = require('../json/countries.json');
          const chosen_country = countries.filter((obj) => {
            // return the object wich has {nameen: req.body.country}
            return obj.nameen === req.body.country;
          });
          // -- create a country object from the returned chosen object
          country = new Country(chosen_country[0]);
          insert_objs.push(country);
        }
        // if there's already such country in database set the country object to the found one
        else {
          // set country object to the found one
          console.log("exec 2 else");
          country = found_country;
          //done();
        }
      })
        .then(() => {
          // execute after queryCountry.findOne:
          // Look for university object in the database
          console.log("exec 3");
          queryUni.then(found_uni => {
            if (!found_uni) {
              // create a new object if not found in db
              const unis = require('../json/unis.json');
              const chosen_uni = unis.filter(obj => {
                return obj.name === req.body.uni;
              });
              university = new University(chosen_uni[0]);
              insert_objs.push(university);
            }
            else {
              university = found_uni;
            }
          })
            .then(() => {
              console.log("exec 4");
              // exec after queryUni.findOne
              //  check if address object already exists in the database and insert a new one if not
              queryAddress.then(found_address => {
                if (!found_address) {
                  // -- create and save address
                  address = new Address({
                    streetAndNr: req.body.address,
                    town: req.body.town,
                    postcode: req.body.postcode,
                    countryID: country._id
                  });
                  insert_objs.push(address);
                }
                else {
                  address = found_address;
                }
              });
            })
              .then(() => {
                console.log("exec last");
                // if person doesn't exist in database insert new docs there
                // save new person data to database
                // -- create a new person object to be inseted to mongodb
                const person = new Person({
                  universityID: university._id,
                  addressID: address._id,
                  firstName: req.body.firstName,
                  lastName: req.body.lastName,
                  gender: req.body.gender,
                  eMailaddress: req.body.email,
                  deleted: false
                });
                insert_objs.push(person);

                // --- save data to logins collection
                const login = new Login({
                  password: bcrypt.hashSync(req.body.password, salt),
                  personID: person._id,
                  requestTime: '' // TODO: set the time user was created
                });
                insert_objs.push(login);

                // insert docs to mongodb collections, after the insert_objs is created
                console.log("LAST OBJS --->", insert_objs);
                save_array(res, insert_objs);
              })
              .catch(error => {
                console.log(error);
              });
        });
    })
    .catch((error) => {
      // catch the error from queryPerson and return status 409 (Conflict)
      res.status(409).json({
        title: "User already exists",
        error: {message: 'User already exists'}
      });
      console.log(error);
    });
    /*
    .catch(err => {
      return res.status(500).json({
        title: 'Error inserting data to database',
        error: err
        //done && done();
      });
    })
  })
  */
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
