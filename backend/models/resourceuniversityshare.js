const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const schema = new Schema({
  resourcetimespanID: {type: Schema.Types.ObjectId, ref."Resourcetimespan"},
  universityID: {type: Schema.Types.ObjectId, ref."University"},
  universityShare: {type: Boolean}
});

module.exports = mongoose.model("Resourceuniversityshare", schema);
