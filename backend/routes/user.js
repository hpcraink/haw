var bcrypt = require('bcryptjs'); // Lib to help you hash passwords
var jwt = require('jsonwebtoken'); // an implementation of JSON Web Tokens

const User = require('../models.org/user');

const Router = require('router');
const router = new Router();

const salt = bcrypt.genSaltSync(10); // generates salt for bcrypt hash password, 2^10 circles of iterration
router.post('/', function(req, res, next) {
  var user = new User({
    firstName: req.body.firstName,
    lastName: req.body.lastName,
    password: bcrypt.hashSync(req.body.password, salt),
    email: req.body.email
  });
  user.save(function(err, result) {
    if (err) {
      return res.status(500).json({
        title: 'An error occured',
        error: err
      });
    }
    res.status(201).json({
      message: "User created!",
      obj: result
    });
  })
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
