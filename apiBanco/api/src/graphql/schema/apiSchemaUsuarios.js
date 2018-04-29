const { buildSchema } = require('graphql');
const apiSchemaUsuarioQuery = require('./apiSchemaUsuarioQuery')
const apiSchemaUsuarioMutation = require('./apiSchemaUsuarioMutation')

const apiSchemaUsuarios = buildSchema(apiSchemaUsuarioQuery + apiSchemaUsuarioMutation)
module.exports = apiSchemaUsuarios
