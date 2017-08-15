const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const schema = new Schema({
  namede: {type: String},
  nameen: {type: String},
  abbreviation: {type: String},
  symbol: {type: String}
});

module.exports = mongoose.model("Currency", schema);
