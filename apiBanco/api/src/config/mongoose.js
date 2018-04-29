"use strict"

var env = process.env.NODE_ENV || 'development',
    config = require('./configDB')[env],
    mongoose = require('mongoose');

module.exports = function () {
    mongoose.Promise = global.Promise;
    var db = mongoose.connect(config.db, { useMongoClient: true });
    mongoose.connection.on('error', function (err) {
        console.log('Error: Não foi possível conectar-se ao MongoDB. Você se esqueceu de executar `mongod`?'.red);
    }).on('open', function () {
        console.log('Conexão  estabelecida com MongoDB')
    })
    return db;
};
