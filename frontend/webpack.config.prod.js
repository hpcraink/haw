var path = require('path');

var webpack = require('webpack');
var webpackMerge = require('webpack-merge');
var commonConfig = require('./webpack.config.js');

const CompressionPlugin = require('compression-webpack-plugin');
const ngtools = require('@ngtools/webpack');

module.exports = webpackMerge.smart(commonConfig, {
  entry: {
    'app': './src/main.aot.ts'
  },

  module: {
    rules: [
      {
        test: /\.ts$/,
        loader: '@ngtools/webpack'
      }
    ]
  },

  plugins: [
    new ngtools.AotPlugin({
      tsConfigPath: './tsconfig.aot.json'
    }),
    new webpack.optimize.UglifyJsPlugin({
      compress: {
        warnings: false
      },
      output: {
        comments: false
      },
      sourceMap:false
    }),
    new CompressionPlugin({
      asset: "[path].gz[query]",
      algorithm: "gzip",
      test: /\.js$|\.html$|\.css/,
      threshold: 10240,
      minRatio: 0.8
    })
  ]
});
