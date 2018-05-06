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

  input InRegistraAcesso {
    _id: String!
    token: String!
  }

  input InRegistraPesquisa {
    _id: String!
    topico: String!
    token: String!
  }

  input InRegistraChat {
    _id: String!
    topico: String!
    usuario: String!
    token: String!
  }

  type Mutation {
    createUsuario(input: inCreateUsuario): tyResponse
    updateUsuario(input: inUpdateUsuario): tyResponse
    deleteUsuario(input: inDeleteUsuario): tyResponse

    registraAcesso  (input: inDeleteUsuario): tyResponse
    registraPesquisa(input: inDeleteUsuario): tyResponse
    registraChat    (input: inDeleteUsuario): tyResponse
  }
`
module.exports = apiSchemaUsuarioMutation
