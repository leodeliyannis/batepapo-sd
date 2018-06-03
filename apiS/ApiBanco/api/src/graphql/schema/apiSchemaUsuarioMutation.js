const apiSchemaUsuarioMutation = `

  input inToken {
    token: String!
  }

  input inLogin {
    Nome: String!
    Senha: String!
    IPaddres: String!
  }

  input inCreateUsuario {
    Nome: String!
    Senha: String!
    IPaddres: String!
  }

  input inUpdateUsuario {
    _id: String!
    token: String!
    Nome: String
    IPaddres: String
  }

  input inDeleteUsuario {
    _id: String!
    token: String!
  }

  input inRegistraAcesso {
    _id: String!
    token: String!
  }

  input inRegistraPesquisa {
    _id: String!
    topico: String!
    token: String!
  }

  input inRegistraChat {
    _id: String!
    topico: String!
    usuario: String!
    token: String!
  }

  input inUsuarioChatsIniciado {
    nome: String!
    token: String!
  }

  input inTopico {
    nome: String
    topico: String!
    token: String!
  }

  input inUsuario {
    nome: String!
    token: String!
  }

  type Mutation {
    createUsuario(input: inCreateUsuario): tyResponse
    updateUsuario(input: inUpdateUsuario): tyResponse
    deleteUsuario(input: inDeleteUsuario): tyResponse

    registraTopico  (input: inTopico):           tyResponse
    registraAcesso  (input: inRegistraAcesso):   tyResponse
    registraPesquisa(input: inRegistraPesquisa): tyResponse
    registraChat    (input: inRegistraChat):     tyResponse
  }
`
module.exports = apiSchemaUsuarioMutation
