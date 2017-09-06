const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const schema = new Schema({
  resourceID: {type: Schema.Types.ObjectId, ref."Resource"},
  personID: {type: Schema.Types.ObjectId, ref."Person"},
  username: {type: String, required: true}
});

module.exports = mongoose.model("Resourceusers", schema);
