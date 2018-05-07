const usuarioModel = require('../../models/usuario');
const estatisticaModel = require('../../models/estatistica');
const jwt = require('jwt-simple');
const moment = require('moment');
const segredo = 'sdmiuuebf375reuwd4SDjsdJAHs'

const apiResolversUsuario = {

  login: async ({input}) => {

    let usuario = await usuarioModel.findOne({Nome: input.Nome},{Acessos:0, Configuracao:0}).exec()//function (err, docs) {console.log(docs)}

    if(!usuario)
      return {cdRetorno: -1, dsRetorno: 'Usuario ou senha invalidos!'}

    var expires = moment().add(1,'days').valueOf();
    if(usuario.Senha == input.Senha) {
        var token = jwt.encode({
          iss: usuario._id,
          exp: expires
        }, segredo);

        let estLogin = estatisticaModel.findByIdAndUpdate(
          { _id: 'Login' },
          {$set: {nome: 'Login', descricao: 'Qtd login validos'}, $inc: { seq: 1}},
          { upsert: true }
        ).exec()

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

      let estDelete = estatisticaModel.findOneAndRemove({ _id: removeUsuario.Nome }).exec()

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

    let atualizacoes = usuarioModel.update(
      { _id : input._id },
      { "$push": { "Acessos":{dataHora: Date.now()} }}
    ).exec()

    return {cdRetorno: 0, dsRetorno: 'Sucesso'}
  },

  registraPesquisa: async ({input}) => {
    let validToken = await verificaToken(input.token)

    if(!validToken)
      return {cdRetorno: -1, dsRetorno: 'Token não informado, invalido ou expirado!'}

    let atualizacoes = usuarioModel.update(
      { _id : input._id },
      { "$push": { "Topicos":{nome: input.topico} }}
    ).exec()

    let estPesquisa = estatisticaModel.findByIdAndUpdate(
      { _id: input.topico },
      {$set: {nome: 'TopicoPesquisa', descricao: 'Qtd pesquisas no topico'}, $inc: { seq: 1}},
      { upsert: true }
    ).exec()

    return {cdRetorno: 0, dsRetorno: 'Sucesso'}
  },

  registraChat: async ({input}) => {
    let validToken = await verificaToken(input.token)

    if(!validToken)
      return {cdRetorno: -1, dsRetorno: 'Token não informado, invalido ou expirado!'}

    let atualizacoes = await usuarioModel.findByIdAndUpdate(
      { _id : input._id },
      { "$push": { "Chats":{usuario2: input.usuario, topico: input.topico} }}
    ).exec()

    let estChat = estatisticaModel.findByIdAndUpdate(
      { _id: 'Chat'},
      {$set: {nome: 'Chat', descricao: 'Qtd chats iniciados'}, $inc: { seq: 1}},
      { upsert: true }
    ).exec()

    let estUser = estatisticaModel.findByIdAndUpdate(
      { _id: atualizacoes.Nome},
      {$set: {nome: 'UserChat', descricao: 'Qtd chats iniciados pelo usuario'}, $inc: { seq: 1}},
      { upsert: true }
    ).exec()

    return {cdRetorno: 0, dsRetorno: 'Sucesso'}
  },

  estatisticaQtdUserAcesso: async ({input}) => {

    let validToken = await verificaToken(input.token)

    if(!validToken)
      return {cdRetorno: -1, dsRetorno: 'Token não informado, invalido ou expirado!'}

    let estat = await estatisticaModel.findOne({_id: 'Login'},{}).exec()

    if (!estat)
      return {cdRetorno: -2, dsRetorno: 'Erro ao buscar os usuários do Banco de Dados'}

    return {cdRetorno: 0, dsRetorno: 'Sucesso', valor: estat.seq}
  },

  estatisticaTopicosPesquisa: async ({input}) => {

    let validToken = await verificaToken(input.token)

    if(!validToken)
      return {cdRetorno: -1, dsRetorno: 'Token não informado, invalido ou expirado!'}

    let estat = await estatisticaModel.find({nome: 'TopicoPesquisa'},{}).exec()

    if (!estat)
      return {cdRetorno: -2, dsRetorno: 'Erro ao buscar os usuários do Banco de Dados'}

    return {cdRetorno: 0, dsRetorno: 'Sucesso', topicos: estat}
  },

  estatisticaQtdChatsIniciado: async ({input}) => {

    let validToken = await verificaToken(input.token)

    if(!validToken)
      return {cdRetorno: -1, dsRetorno: 'Token não informado, invalido ou expirado!'}

    let estat = await estatisticaModel.findOne({_id: 'Chat'},{}).exec()

    if (!estat)
      return {cdRetorno: -2, dsRetorno: 'Erro ao buscar os usuários do Banco de Dados'}

    return {cdRetorno: 0, dsRetorno: 'Sucesso', valor: estat.seq}
  },

  estatisticaUsuarioChatsIniciado: async ({input}) => {

    let validToken = await verificaToken(input.token)

    if(!validToken)
      return {cdRetorno: -1, dsRetorno: 'Token não informado, invalido ou expirado!'}

    let estat = await estatisticaModel.findOne({_id: input.Nome},{}).exec()

    if (!estat)
      return {cdRetorno: -2, dsRetorno: 'Erro ao buscar os usuários do Banco de Dados'}

    return {cdRetorno: 0, dsRetorno: 'Sucesso', valor: estat.seq}
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
