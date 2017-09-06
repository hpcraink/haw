const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const schema = new Schema({
  centerID: {type: Schema.Types.ObjectId, ref."Center"},
  resourceID: {type: Schema.Types.ObjectId, ref."Resource"}
});

module.exports = mongoose.model("Centerresources", schema);
