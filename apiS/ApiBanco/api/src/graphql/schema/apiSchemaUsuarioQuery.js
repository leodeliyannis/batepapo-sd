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

  type tyResponseEstatistica {
  	cdRetorno: Int!
  	dsRetorno: String!
    valor: Int
  }

  type tyResponseTopicosPesquisa {
    cdRetorno: Int!
  	dsRetorno: String!
    topicos: [tyTopicos]
  }

  type tyTopicos {
  	topico: String
    descricao: String
    tipo: String
    quantidade: Int
  }

  type Query {
    getUsuarios(input: inToken): tyUsuario
    login      (input: inLogin): tyResponseLogin
    getTopicosUsuarios (input: inTopico): tyUsuario
    getUsuariosTopicos (input: inUsuario): tyUsuario

    estatisticaQtdUserAcesso        (input: inToken): tyResponseEstatistica
    estatisticaTopicosPesquisa      (input: inToken): tyResponseTopicosPesquisa
    estatisticaQtdChatsIniciado     (input: inToken): tyResponseEstatistica
    estatisticaUsuarioChatsIniciado (input: inUsuarioChatsIniciado): tyResponseEstatistica
  }
`
module.exports = apiSchemaUsuarioQuery
