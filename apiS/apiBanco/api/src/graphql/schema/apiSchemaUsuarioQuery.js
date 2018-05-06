const apiSchemaUsuarioQuery = `

  type tyUsuario {
    cdRetorno: Int!
  	dsRetorno: String!
    Usuarios: [tyUsuarios]
  }

  type tyUsuarios {
    _id: String!
    Nome: String!
    IPaddres: String!
    Acesso: tyAcesso
    Configuracao: tyConfig
  }

  type tyLogin {
    _id: String!
    Nome: String!
    IPaddres: String!
    Configuracao: tyConfig!
  }

  type tyConfig {
    usuario: String
    dt_criacao: String
    Atualizacoes: [tyConfigAtualizacoes]
  }

  type tyConfigAtualizacoes {
    usuario: String
    dt_atualizacao: String
  }

  type tyAcesso {
    dataHora: String
  }

  type tyResponse {
  	cdRetorno: Int!
  	dsRetorno: String!
  }

  type tyResponseLogin {
  	cdRetorno: Int!
  	dsRetorno: String!
    token: String
  }

  type Query {
    getUsuarios(input: inToken): tyUsuario
    login(input: inLogin): tyResponseLogin
  }
`
module.exports = apiSchemaUsuarioQuery
