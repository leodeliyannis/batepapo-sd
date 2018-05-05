const usuarioModel = require('../../models/usuario');

const apiResolversUsuario = {

  getUsuarios: () => {
    let usuario = usuarioModel.find({},{Acesso:0, Configuracao:0}).exec()//function (err, docs) {console.log(docs)}

    if (!usuario) {
      return {cdRetorno: -2, dsRetorno: 'Erro ao buscar os usuários do Banco de Dados'}
    }

    return usuario
  },

  createUsuario: ({input}) => {

    create = {}
    try {
      create["Nome"]=input.Nome;
      create["IPaddres"]=input.IPaddres;
      create["Configuracao"]={usuario: "Teste"};

      let uModel = new usuarioModel(create);
      let newUsuarioInDB = uModel.save();

      return {cdRetorno: 0, dsRetorno: 'Sucesso'}

    } catch (e) {
        return {cdRetorno: -2, dsRetorno: 'Erro ao inserir o usuário do Banco de Dados, erro: '+e}
    }

  },

  updateUsuario: async ({input}) => {

    var update={};

    try {
      if(input.Nome){
        update["Nome"]=input.Nome;
      }if(input.IPaddres){
        update["IPaddres"]=input.IPaddres;
      }

      let newUpdate = await usuarioModel.findOneAndUpdate(
        { _id: input._id },
        { $set:update },
        { upsert: false }
      ).exec()

      if (!newUpdate) {
        return {cdRetorno: -1, dsRetorno: 'Usuário não existe!'}
      }
      console.log("Usuário atualizado: "+newUpdate)

      let atualizacoes = usuarioModel.update(
        { _id : input._id },
        { "$push": { "Configuracao.Atualizacoes":{usuario: input.usuario} }},
        { upsert: false }
      ).exec()

      return {cdRetorno: 0, dsRetorno: 'Sucesso'}

    } catch (e) {
        return {cdRetorno: -2, dsRetorno: 'Erro ao atualizar o usuário do Banco de Dados, erro: '+e}
    }

  },

  deleteUsuario: async ({input}) => {

    try {

      let removeUsuario = await usuarioModel.findOneAndRemove({ _id:input._id }).exec()

      if (!removeUsuario) {
        return {cdRetorno: -1, dsRetorno: 'Usuário não existe!'}
      }
      console.log("Usuário removido: "+removeUsuario)
      return {cdRetorno: 0, dsRetorno: 'Sucesso'}

    } catch (e) {
        return {cdRetorno: -2, dsRetorno: 'Erro ao remover o usuário do Banco de Dados, erro: '+e}
    }

  }

};

module.exports = apiResolversUsuario
