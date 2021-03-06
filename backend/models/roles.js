const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const schema = new Schema({
  personID: {type: Schema.Types.ObjectId, ref."Person"},
  role: {type: String}
});

module.exports = mongoose.model("Roles", schema);
