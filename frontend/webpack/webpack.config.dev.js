const webpackMerge = require('webpack-merge');
const commonConfig = require('./webpack.config.js');

module.exports = webpackMerge.smart(commonConfig, {
  devtool: 'cheap-module-eval-source-map',

  module:{
    rules: [
      {
        test: /\.ts$/,
        use: [
          {loader: 'awesome-typescript-loader', options: { transpileOnly: true }},
          'angular2-template-loader',
          'angular-router-loader'
        ]
      }
    ]
  }
});
