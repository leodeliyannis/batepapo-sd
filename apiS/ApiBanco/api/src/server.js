"use strict"

const express = require('express')
const graphqlHTTP = require('express-graphql')
const graphql = require('graphql');
const bodyParser = require('body-parser') //analisar o corpo para que você possa acessar os parâmetros em pedidos em req.body
const helmet = require('helmet') //proteções contra ataques
const logger = require('morgan')
const RateLimit = require('express-rate-limit');
const mongoose = require('./config/mongoose') //conexão ao banco MongoDB
const db = mongoose()

const apiSchemaUsuarios = require("./graphql/schema/apiSchemaUsuarios")
const apiResolversUsuarios = require("./graphql/resolvers/apiResolversUsuarios")

const app = express()

app.use(helmet())
app.enable('trust proxy');

var limiter = new RateLimit({
  windowMs: 15*60*1000, // 15 minutes
  max: 200, // limit each IP to 200 requests per windowMs
  delayMs: 0, // disable delaying - full speed until the max limit is reached
  message: "Muitas requisições deste IP, tente em 15 min"
});

app.use(limiter);

app.use(logger('dev'));
app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(bodyParser.json());

app.use(function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header('Access-Control-Allow-Methods', 'POST, GET, HEAD');
  res.header('Access-Control-Allow-Headers', 'content-type, x-access-token');
  next();
});

app.use('/usuarios', graphqlHTTP({
    schema: apiSchemaUsuarios,
    rootValue: apiResolversUsuarios,
    graphiql: true,
}))

app.get('/test', function(req, res){
  res.sendStatus(200);
});

app.get('/favicon.ico', (req, res) => res.sendStatus(204));

//Garantir que as filas de banco estejam limpas antes de fechar
app.on('SIGINT', function() {
   db.stop(function(err) {
     app.exit(err ? 1 : 0);
   });
});

const PORT = 4000;
app.listen(PORT);
console.log(`Running on port ${PORT}`);
