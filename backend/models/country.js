const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const schema = new Schema({
  iso2: {type: String},
  iso3: {type: String},
  namede: {type: String},
  nameen: {type: String}
});

module.exports = mongoose.model("Country", schema);
