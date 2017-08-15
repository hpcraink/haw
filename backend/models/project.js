const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const schema = new Schema({
  name: {type: String, required: true},
  leaderPersonID: {type: Schema.Types.ObjectId, ref."Person"},
  startDate: {type: Date},
  endDate: {type: Date}
});

module.exports = mongoose.model("Project", schema);
