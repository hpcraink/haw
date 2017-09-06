const mongoose = require('mongoose');
const Schema = mongoose.Schema;
//const mongooseUniqueValidator = require('mongoose-unique-validator');

const schema = new Schema({
  universityID: {type: Schema.Types.ObjectId, ref:"University"},
  addressID: {type: Schema.Types.ObjectId, ref:"Address"},
  firstName: {type: String, required: true},
  lastName:{type: String, required: true},
  gender:{type: String, required: true},
  eMailaddress:{type: String, required: true},
  //eMailaddress:{type: String, required: true, unique: true},
  //additionalInformation:{type: String},
  //adminInformation: {type: String},
  deleted: {type: Boolean}
});

// a plugin wich adds pre-save validation for unique fields within a mongoose schema
//schema.plugin(mongooseUniqueValidator);

module.exports = mongoose.model('Person', schema);
