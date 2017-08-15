const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const schema = new Schema({
  resourceID: {type: Schema.Types.ObjectId, ref."Resource"},
  filename: {type: String},
  fileDate: {type: Date},
  fileHash: {type: String}
});

module.exports = mongoose.model("Parsedfile", schema);
