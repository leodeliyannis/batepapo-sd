const apiSchemaUsuarioMutation = `

  input inCreateUsuario {
    Nome: String!
    IPaddres: String!
  }

  input inUpdateUsuario {
    _id: String!
    Nome: String
    IPaddres: String
  }

  input inDeleteUsuario {
    _id: String!
  }


  type Mutation {
    createUsuario(input: inCreateUsuario): tyResponse
    updateUsuario(input: inUpdateUsuario): tyResponse
    deleteUsuario(input: inDeleteUsuario): tyResponse
  }
`
module.exports = apiSchemaUsuarioMutation
