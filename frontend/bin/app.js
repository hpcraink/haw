'use strict';

const express = require('express');
const favicon = require('serve-favicon');
//const logger = require('morgan');
const fs = require('fs');

// Make HTTP2 work with Express (this must be before any other middleware)
//const http2 = require('http2');
//express.request.__proto__ = http2.IncomingMessage.prototype;
//express.response.__proto__ = http2.ServerResponse.prototype;
/** Set express application */
const app = express();

// uncomment after placing your favicon in /public
app.use(favicon('./public/favicon.ico'));

// morgan logger
//app.use(logger('dev'));

// set static folder pointing to './public'
app.use(express.static('./public'));

// serve index.html
app.use(function(req, res, next) {
  fs.createReadStream("./public/index.html").pipe(res);
});

module.exports = app;
