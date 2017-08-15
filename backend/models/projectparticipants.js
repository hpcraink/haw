const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const schema = new Schema({
  ProjectID: {type: Schema.Types.ObjectId, ref."Project"},
  PersonID: {type: Schema.Types.ObjectId, ref."Person"}
});

module.exports = mongoose.model("Projectparticipants", schema);
