var webpack = require('webpack');
var path = require('path');

module.exports = {
  entry: {
    'app': './src/main.ts'
  },

  output: {
    path: path.resolve(__dirname + '/../public/js/app'),
    publicPath: "/js/app/",
    filename: '[name].bundle.js',
    chunkFilename: '[id].chunk.js'
  },

  resolve: {
    extensions: ['.js', '.ts']
  },

  module:{
    rules: [
      {
        test: /\.html$/,
        use: [{ loader: 'html-loader'}]
      },
      {
        test: /\.css$/,
        use: [{ loader: 'raw-loader'}]
      }
    ],
    exprContextCritical: false
  }
};
