var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var usuarioSchema = new Schema({

  Nome: { type : String , unique : true },
  Senha: String,
  IPaddres: String,
  Acessos: [{
    dataHora: {
      type: Date,
      default: Date.now
    }
  }],
  Topicos: [{
    nome: String,
    dataHora: {
      type: Date,
      default: Date.now
    }
  }],
  Chats: [{
    usuario2: String,
    dataHora: {
      type: Date,
      default: Date.now
    },
    topico: String
  }],
  Configuracao: {
    usuario: String,
    dt_criacao:{
      type: Date,
      default: Date.now
    },
    Atualizacoes:[{
      usuario: String,
      dt_atualizacao:{
        type: Date,
        default: Date.now
      }
    }]
  }

});

var ModelUsuario = mongoose.model('Usuario', usuarioSchema);
module.exports = ModelUsuario;
