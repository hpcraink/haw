'use strict';

const express = require('express');
const path = require('path');
const cookieParser = require('cookie-parser');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const http2 = require('http2');
const fs = require('fs');
const morgan = require('morgan'); // HTTP request logger middleware
const cors = require('cors'); // Cross-origin resource sharing

//const appRoutes = require('../routes/app');
const messageRoutes = require('../routes/messages');
const userRoutes = require('../routes/user');
const statisticsRoutes = require('../routes/statistics');

// Make HTTP2 work with Express (this must be before any other middleware)
express.request.__proto__ = http2.IncomingMessage.prototype;
express.response.__proto__ = http2.ServerResponse.prototype;
const app = express();
app.use(cors());

// connect to mongodb through mongoose
mongoose.connect('mongodb://mongo_db:27017/bwUniCluster');

app.use(morgan('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
//app.use(express.static(path.join(__dirname, 'public')));

// for running backend and angular on different servers: TODO - resolve this`
/*
app.use(function(req, res, next) {
  res.setHeader('Access-Control-Allow-Origin', '*'); // Allows all domains to make http request to this server
  res.setHeader('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept'); // specifeis which headers are allowed on inc requests.
  res.setHeader('Access-Control-Allow-Methods', 'POST, GET, PATCH, DELETE, OPTIONS'); //which http methods are allowed.
  next(); // letting the request to continue.
});
*/

app.use('/statistic', statisticsRoutes);
app.use('/message', messageRoutes); // should be before appRoutes to be handled first
app.use('/user', userRoutes); // should be before appRoutes to be handled first
//app.use('/', appRoutes); //send all requests to ./routs/app.js

/*app.use(function(req, res, next) {
  fs.createReadStream("./views/index.html").pipe(res);
});*/

module.exports = app;
