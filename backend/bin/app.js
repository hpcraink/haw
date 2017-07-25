'use strict';

const express = require('express');
const http2 = require('http2');

const cookieParser = require('cookie-parser');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const fs = require('fs');
const morgan = require('morgan'); // HTTP request logger middleware
const cors = require('cors'); // Cross-origin resource sharing

//const appRoutes = requir('../routes/app');
const messageRoutes = require('../routes/messages');
const userRoutes = require('../routes/user');
const statisticsRoutes = require('../routes/statistics');

// Make HTTP2 work with Express (this must be before any other middleware)
express.request.__proto__ = http2.IncomingMessage.prototype;
express.response.__proto__ = http2.ServerResponse.prototype;
const app = express();
app.use(cors());

// connect to mongodb through mongoose
mongoose.Promise = global.Promise;
const db_uri = 'mongodb://mongo_db:27017/bwUniCluster';
const db_options = {
  promiseLibrary: global.Promise,
  useMongoClient: true
};
mongoose.connect(db_uri, db_options)
  .then(() => {
    const admin = new mongoose.mongo.Admin(mongoose.connection.db);
    admin.buildInfo((err, info) => {
      if (err) {console.error(`Error getting MongoDB info: ${err}`)}
      else {
        console.log(`Connection to MongoDB (version ${info.version}) oppened successfully!`);
      };
    })
  })
  .catch((err) => console.error(`Error connecting to MongoDB: ${err}`));

app.use(morgan('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
//app.use(express.static(path.join(__dirname, 'public')));

app.use('/statistic', statisticsRoutes);
app.use('/message', messageRoutes); // should be before appRoutes to be handled first
app.use('/user', userRoutes); // should be before appRoutes to be handled first
//app.use('/', appRoutes); //send all requests to ./routs/app.js

module.exports = app;
