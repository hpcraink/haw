const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const schema = new Schema({
  streetAndNr: {type: String},
  town: {type: String},
  postcode: {type: String},
  countryID: {type: Schema.Types.ObjectId, ref:"Country"}
});

module.exports = mongoose.model("Address", schema);
