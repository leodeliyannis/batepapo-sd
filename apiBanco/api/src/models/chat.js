var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var chatSchema = new Schema({

  usuario1: String,
  usuario2: String,
  dataHora: {
    type: Date,
    default: Date.now
  },
  topico: String

});

var ModelChat = mongoose.model('Chat', chatSchema);
module.exports = ModelChat;
