//var express = require('express');
//var router = express.Router();

var jwt = require('jsonwebtoken');

var User = require('../models/user');
var Message = require('../models/message');

const Router = require('router');
const router = new Router();

router.get('/', function(req, res, next) {
  Message.find()
    .populate('user', 'firstName') // moongose method which says to populate user first name from user + user._id as you don't have the complete user object here, just message.
    .exec(function(err, messages) {
      if (err) {
        return res.status(500).json({
          title: 'An error occured',
          error: err
        })
      }
      res.status(200).json({
        message: "Success",
        obj: messages
      })
    });
});

// check if you are authenticated to use routs bellow
router.use('/', function(req, res, next) {
  jwt.verify(req.query.token, 'bwHPC-C5_secret', function(err, decoded) {
    if (err) {
      return res.status(401).json({
        title: "Not Authenticated",
        error: err
      });
    }
    next(); // request may 'continue his journey', traveling to routs bellow
  })
});

// Add a new message
router.post('/', function(req, res, next) {
  var decoded = jwt.decode(req.query.token); // decrypt the token use verify if token wasn't verified beforehead
  User.findById(decoded.user._id, function(err, user) {
    if (err) {
      return res.status(500).json({
        title: 'An error occured',
        error: err
      });
    }
    var message = new Message({
      content: req.body.content,
      user: user
    });
    message.save(function(err, result) {
      if (err) {
        return res.status(500).json({
          title: 'An error occured',
          error: err
        });
      }
      user.messages.push(result); // push message to 'messages' array in models/user.js --> basically to mongodb
      user.save();
      res.status(201).json({
        message: 'Saved message',
        obj: result
      });
    });
  });
});

// edit router use http PATCH
router.patch('/:id', function(req, res, next) {
  var decoded = jwt.decode(req.query.token); // decrypt the token use verify if token wasn't verified beforehead
  Message.findById(req.params.id, function(err, message) {
    if (err) {
      return res.status(500).json({
        title: 'An error occured',
        error: err
      });
    }
    if (!message) {
      return res.status(500).json({
        title: 'No message found!',
        error: {message: 'Message not found'}
      });
    }
    if (message.user != decoded.user._id) {
      // different user trying to edit the message
      return res.status(401).json({
        title: "Not Authenticated",
        error: { message: "Users do no match"}
      });
    }
    message.content = req.body.content;
    message.save(function(err, result) {
      if (err) {
        return res.status(500).json({
          title: 'An error occured',
          error: err
        });
      }
      res.status(200).json({
        message: 'Updated message',
        obj: result
      })
    })
  })
});

// Delete the message router  (HTTP: DELETE)
router.delete('/:id', function(req, res, next) {
  var decoded = jwt.decode(req.query.token); // decrypt the token use verify if token wasn't verified beforehead
  Message.findById(req.params.id, function(err, message) {
    if (err) {
      return res.status(500).json({
        title: 'An error occured',
        error: err
      });
    }
    if (!message) {
      return res.status(500).json({
        title: 'No message found!',
        error: {message: 'Message not found'}
      });
    }
    if (message.user != decoded.user._id) {
      // different user trying to delete the message
      return res.status(401).json({
        title: "Not Authenticated",
        error: { message: "Users do no match"}
      });
    }
    message.remove(function(err, result) {
      if (err) {
        return res.status(500).json({
          title: 'An error occured',
          error: err
        });
      }
      res.status(200).json({
        message: 'Deleted message',
        obj: result
      })
    })
  })
});

module.exports = router;
