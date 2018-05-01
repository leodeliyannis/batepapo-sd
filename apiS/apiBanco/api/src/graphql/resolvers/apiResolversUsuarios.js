const usuarioModel = require('../../models/usuario');

const apiResolversUsuario = {

  getUsuarios: () => {
    let usuario = usuarioModel.find({},{Acesso:0, Configuracao:0}).exec()//function (err, docs) {console.log(docs[0])}

    if (!usuario) {
      throw new Error('Erro ao recuperar os usuários do Banco de Dados')
    }

    return usuario
  },

  createUsuario: ({input}) => {

    create = {}

    create["Nome"]=input.Nome;
    create["IPaddres"]=input.IPaddres;
    create["Configuracao"]={usuario: "Teste"};

    let uModel = new usuarioModel(create);
    let newUsuarioInDB = uModel.save();
    if (!newUsuarioInDB) {
      return {cdRetorno: -2, dsRetorno: 'Erro ao inserir usuário no Banco de Dados'}
    }

    return {cdRetorno: 0, dsRetorno: 'Sucesso'}

  },

  updateUsuario: ({input}) => {

    var update={};

    if(input.Nome){
      update["Nome"]=input.Nome;
    }if(input.IPaddres){
      update["IPaddres"]=input.IPaddres;
    }

    let newUpdate = usuarioModel.update(
      {_id: input._id },
      {$set:update}
    ).exec()

    let atualizacoes = usuarioModel.update(
      { _id : input._id },
      { "$push": { "Configuracao.Atualizacoes":{usuario: input.usuario} }}
    ).exec()

    if (!newUpdate) {
      return {cdRetorno: -2, dsRetorno: 'Erro ao realizar o update do usuário no Banco de Dados'}
    }

    return {cdRetorno: 0, dsRetorno: 'Sucesso'}
  },

  deleteUsuario: ({input}) => {

    let removeUsuario = usuarioModel.find({ _id:input._id }).remove().exec()

    if (!removeUsuario) {
      return {cdRetorno: -2, dsRetorno: 'Erro ao remover o usuário do Banco de Dados'}
    }

    return {cdRetorno: 0, dsRetorno: 'Sucesso'}
  }

};

module.exports = apiResolversUsuario
