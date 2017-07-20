'use strict';

var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var mongoose = require('mongoose');
const http2 = require('http2');
const fs = require('fs');

var appRoutes = require('./routes/app');
var messageRoutes = require('./routes/messages');
var userRoutes = require('./routes/user');
var statisticsRoutes = require('./routes/statistics');

// Make HTTP2 work with Express (this must be before any other middleware)
express.request.__proto__ = http2.IncomingMessage.prototype;
express.response.__proto__ = http2.ServerResponse.prototype;
var app = express();

// connect to mongodb through mongoose
mongoose.connect('localhost:27017/bwUniCluster');

// view engine setup
//app.set('views', path.join(__dirname, 'views'));
//app.set('view engine', 'hbs');

// uncomment after placing your favicon in /public
app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

// for running backend and angular on different servers:
app.use(function(req, res, next) {
  res.setHeader('Access-Control-Allow-Origin', '*'); // Allows all domains to make http request to this server
  res.setHeader('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept'); // specifeis which headers are allowed on inc requests.
  res.setHeader('Access-Control-Allow-Methods', 'POST, GET, PATCH, DELETE, OPTIONS'); //which http methods are allowed.
  next(); // letting the request to continue.
});

app.use('/statistic', statisticsRoutes);
app.use('/message', messageRoutes); // should be before appRoutes to be handled first
app.use('/user', userRoutes); // should be before appRoutes to be handled first
app.use('/', appRoutes); //send all requests to ./routs/app.js

// catch 404 and forward to error handler (handled by angular)
app.use(function(req, res, next) {
  //res.render('index');
  fs.createReadStream("./public/index.html").pipe(res);
});

module.exports = app;
