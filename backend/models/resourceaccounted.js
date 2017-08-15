const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const schema = new Schema({
  resourceID: {type: Schema.Types.ObjectId, ref:"Resource"},
  containedResourceID: {type: Number},
  unitID: {type: Schema.Types.ObjectId, ref:"Unit"},
  numberOf: {type: Number},
  costPerUnit: {type: Number},
  currencyID: {type: Schema.Types.ObjectId, ref:"Currency"},
});

module.exports = mongoose.model("Resourceaccounted", schema);
