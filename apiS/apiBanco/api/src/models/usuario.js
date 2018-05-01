var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var usuarioSchema = new Schema({

  Nome: String,
  IPaddres: String,
  Acesso: [{
    dataHora: {
      type: Date,
      default: Date.now
    }
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
