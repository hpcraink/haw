//var express = require('express');
//var router = express.Router();

const Router = require('router');
const router = new Router();
const fs = require('fs');

/* GET home page. */
router.get('/', function(req, res, next) {
    //res.render('index');
    fs.createReadStream("./views/index.html").pipe(res);
});

module.exports = router;
