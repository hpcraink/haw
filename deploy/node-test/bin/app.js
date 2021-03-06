'use strict';

var express = require('express');
var favicon = require('serve-favicon');
var logger = require('morgan');
const http2 = require('http2');
const fs = require('fs');

// Make HTTP2 work with Express (this must be before any other middleware)
express.request.__proto__ = http2.IncomingMessage.prototype;
express.response.__proto__ = http2.ServerResponse.prototype;
var app = express();

// uncomment after placing your favicon in /public
app.use(favicon('./public/favicon.ico'));
app.use(logger('dev'));
app.use(express.static('./public'));

// catch 404 and forward to error handler (handled by angular)
app.use(function(req, res, next) {
  fs.createReadStream("./public/index.html").pipe(res);
});

module.exports = app;
