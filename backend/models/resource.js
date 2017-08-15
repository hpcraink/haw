const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const schema = new Schema({
  name: {type: String},
  accountingName: {type: String}
});

module.exports = mongoose.model("Resource", schema);
