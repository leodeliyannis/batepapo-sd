var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var estatisticaSchema = new Schema({

    _id: {type: String, required: true},
    nome: {type: String, required: true},
    seq: { type: Number, default: 0 },
    descricao: {type: String, required: true}

});

var ModelEstatistica = mongoose.model('estatisticas', estatisticaSchema);
module.exports = ModelEstatistica;
