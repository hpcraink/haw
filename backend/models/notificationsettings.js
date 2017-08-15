const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const schema = new Schema({
  emailServer: {type: String},
  port: {type: Number},
  sslPort: {type: Number},
  useSsl: {type: Boolean},
  email: {type: String},
  nameFrom: {type: String},
  user: {type: String},
  password: {type: String}
});

module.exports = mongoose.model("Notificationsettings", schema);
