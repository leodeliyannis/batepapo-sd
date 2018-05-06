const usuarioModel = require('../../models/usuario');
const jwt = require('jwt-simple');
const moment = require('moment');
const segredo = 'sdmiuuebf375reuwd4SDjsdJAHs'

const apiResolversUsuario = {

  login: async ({input}) => {

    let usuario = await usuarioModel.findOne({Nome: input.Nome},{Acessos:0, Configuracao:0}).exec()//function (err, docs) {console.log(docs)}

    var expires = moment().add(1,'days').valueOf();
    if(usuario.Senha == input.Senha) {
        var token = jwt.encode({
          iss: usuario._id,
          exp: expires
        }, segredo);

        return {cdRetorno: 0, dsRetorno: 'Sucesso', token: token}
    } else {
        return {cdRetorno: -1, dsRetorno: 'Usuario ou senha invalidos!'}
    }

  },

  getUsuarios: async ({input}) => {

    let validToken = await verificaToken(input.token)

    if(!validToken)
      return {cdRetorno: -1, dsRetorno: 'Token não informado, invalido ou expirado!'}

    let usuarios = await usuarioModel.find({},{Acessos:0, Configuracao:0}).exec()//function (err, docs) {console.log(docs)}

    if (!usuarios)
      return {cdRetorno: -2, dsRetorno: 'Erro ao buscar os usuários do Banco de Dados'}

    return {cdRetorno: 0, dsRetorno: 'Sucesso', Usuarios: usuarios}
  },

  createUsuario: ({input}) => {

    create = {}
    try {
      create["Nome"]=input.Nome;
      create["Senha"]=input.Senha;
      create["IPaddres"]=input.IPaddres;
      create["Configuracao"]={usuario: input.Nome};

      let uModel = new usuarioModel(create);
      let newUsuarioInDB = uModel.save();

      return {cdRetorno: 0, dsRetorno: 'Sucesso'}

    } catch (e) {
        return {cdRetorno: -2, dsRetorno: 'Erro ao inserir o usuário do Banco de Dados, erro: '+e}
    }

  },

  updateUsuario: async ({input}) => {

    let validToken = await verificaToken(input.token)

    if(!validToken)
      return {cdRetorno: -1, dsRetorno: 'Token não informado, invalido ou expirado!'}

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

      if (!newUpdate)
        return {cdRetorno: -1, dsRetorno: 'Usuário não existe!'}

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

    let validToken = await verificaToken(input.token)

    if(!validToken)
      return {cdRetorno: -1, dsRetorno: 'Token não informado, invalido ou expirado!'}

    try {

      let removeUsuario = await usuarioModel.findOneAndRemove({ _id:input._id }).exec()

      if (!removeUsuario)
        return {cdRetorno: -1, dsRetorno: 'Usuário não existe!'}

      console.log("Usuário removido: "+removeUsuario)
      return {cdRetorno: 0, dsRetorno: 'Sucesso'}

    } catch (e) {
        return {cdRetorno: -2, dsRetorno: 'Erro ao remover o usuário do Banco de Dados, erro: '+e}
    }

  },

  registraAcesso: async ({input}) => {
    let validToken = await verificaToken(input.token)

    if(!validToken)
      return {cdRetorno: -1, dsRetorno: 'Token não informado, invalido ou expirado!'}

  },

  registraPesquisa: async ({input}) => {
    let validToken = await verificaToken(input.token)

    if(!validToken)
      return {cdRetorno: -1, dsRetorno: 'Token não informado, invalido ou expirado!'}

  },

  registraChat: async ({input}) => {
    let validToken = await verificaToken(input.token)

    if(!validToken)
      return {cdRetorno: -1, dsRetorno: 'Token não informado, invalido ou expirado!'}
      
  }

};

async function verificaToken(token) {
  try {
    var decoded = jwt.decode(token, segredo);
    console.log('decodando ' + decoded.iss);

    if (decoded.exp <= Date.now()) {
      console.log('Data expirada: '+decoded.exp);
      return false
    }

    let usuario = await usuarioModel.findOne({ _id: decoded.iss }).exec()

    if(!usuario) {
      console.log("Usuário não encontrado com o id: "+decoded.iss);
      return false
    } else {
      return true
    }

  } catch (e) {
    console.log('Exception: '+e);
    return false
  }
}

module.exports = apiResolversUsuario
