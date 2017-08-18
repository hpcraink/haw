const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const schema = new Schema({
  personID: {type: Schema.Types.ObjectId, ref:"Person"},
  password: {type: String, required: true},
  requestTime: {type: String}
});

module.exports = mongoose.model("Login", schema);
