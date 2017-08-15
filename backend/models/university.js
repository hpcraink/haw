const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const schema = new Schema({
  name: {type: String},
  abbreviation: {type: String},
  isHAW: {type: Boolean},
  addressID: {type: Schema.Types.ObjectId, ref."Address"}
});

module.exports = mongoose.model("University", schema);
