const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const schema = new Schema({
  name: {type: String},
  abbreviation: {type: String}
});

module.exports = mongoose.model("Center", schema);
