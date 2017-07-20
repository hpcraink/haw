//var express = require('express');
//var router = express.Router();

const Statistics = require('../models/statistics');

const Router = require('router');
const router = new Router();

router.get('/', function(req, res, next) {
  Statistics.find()
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

module.exports = router;
