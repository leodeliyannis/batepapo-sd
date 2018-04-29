const apiSchemaUsuarioQuery = `

  type tyUsuarios {
    _id: String!
    Nome: String!
    IPaddres: String!
    Configuracao: tyConfig!
  }

  type tyConfig {
    usuario: String!
    dt_criacao: String!
    Atualizacoes: [tyConfigAtualizacoes]
  }

  type tyConfigAtualizacoes {
    usuario: String
    dt_atualizacao: String
  }

  type tyResponse {
  	cdRetorno: Int!
  	dsRetorno: String!
  }

  type Query {
    getUsuarios: [tyUsuarios]
  }
`
module.exports = apiSchemaUsuarioQuery
