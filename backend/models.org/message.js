var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var User = require('./user');

var schema = new Schema({
  content: {type: String, required: true},
  user: {type: Schema.Types.ObjectId, ref: 'User'}
});

schema.post('remove', function(message) { //mongoose way to create a function when 'remove' method is called we do it for deleting messages from 'user' db (see routes/messages.js)
  User.findById(message.user, function(err, user){
    user.messages.pull(message);
    user.save();
  });
});

module.exports = mongoose.model('Message', schema);
