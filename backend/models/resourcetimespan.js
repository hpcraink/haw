const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const schema = new Schema({
  resourceID: {type: Schema.Types.ObjectId, ref."Resource"},
  name: {type: String},
  timespan: {type: Number},
  startDate: {type: Date},
  endDate: {type: Date}
});

module.exports = mongoose.model("Resourcetimespan", schema);
