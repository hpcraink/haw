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
    return res.status(500).json({
      title: 'An error occured',
      error: error
    });
    throw error; // do I need this?
  };
  // if all saved without errors return 201
  res.status(201).json({
    message: "info saved to mongodb!",
  });
};


router.post('/', function(req, res, next) {
  // - create new objects to insert in dbs
  const self = this;
  let insert_objs = []; // the array containig all the objects
  let country, univeristy, address; // objects used to create links

  // -- check if the chosen country already exists in db
  Country.findOne({nameen: req.body.country}, (err, found) => {
    if (err) {
      return res.status(500).json({
        title: 'Error finding country',
        error: err
      });
      next();
    };
    // if not found save the corespondig object to insert_objs array
    if (!found) {
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
    else {
      // set country object to the found one
      country = found;
    }
  });

  // -- do the same with uni as was done with countries above
  University.findOne({name: req.body.uni}, (err, found) => {
    if (err) {
      return res.status(500).json({
        title: "Error finding the univeristy",
        error: err
      });
      next();
    }
    if (!found) {
      const unis = require('../json/unis.json');
      const chosen_uni = unis.filter(obj => {
        return obj.name === req.body.uni;
      });
      university = new University(chosen_uni[0]);
      insert_objs.push(university);
    }
    else {
      university = found;
    }
  });

  // -- check if address object already exists in the database and insert a new one if not
  Address.findOne({streetAndNr: req.body.address, postcode: req.body.postcode}, (err, found) => {
    if (err) {
      return res.status(500).json({
        title: "Error finding address",
        error: err
      });
      next();
    };
    if (!found) {
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
      address = found;
    }
  });

  Person.findOne({eMailaddress: req.body.email}, (err, found) => {
    if (err) {
      return res.status(500).json({
        title: "Error finding person",
        error: err
      })
      next();
    };
    if (!found) {
      // -- create and save person object to mongodb
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

      const login = new Login({
        password: bcrypt.hashSync(req.body.password, salt),
        personID: person._id,
        requestTime: '' // TODO: set the time user was created
      });
      insert_objs.push(login);
    }
    else {
      res.status(409).json({
        title: "User already exists",
        error: {message: 'User already exists'}
      });
      next();
    };

    // insert docs to mongodb collections, after the insert_objs is created
    save_array(res, insert_objs);
    next();
  });

});

router.post('/signin', function(req, res, next) {
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

module.exports = router;
