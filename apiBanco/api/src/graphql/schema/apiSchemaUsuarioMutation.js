const apiSchemaUsuarioMutation = `

  input inCreateUsuario {
    Nome: String!
    IPaddres: String!
  }

  input inUpdateUsuario {
    _id: Stirng!
    Nome: String
    IPaddres: String
  }

  input inDeleteUsuario {
    _id: Stirng!
  }


  type Mutation {
    createUsuario(input: inCreateUsuario): tyResponse
    updateUsuario(input: inUpdateUsuario): tyResponse
    deleteUsuario(input: inDeleteUsuario): tyResponse
  }
`
module.exports = apiSchemaUsuarioMutation
