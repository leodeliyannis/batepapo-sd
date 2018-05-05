package br.com.ufp.sd.resources;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import br.com.ufp.sd.querys.UsuariosJsonUtil;
import br.com.ufp.sd.types.ConsultaUsuariosResponse;
import br.com.ufp.sd.types.UsuarioCreate;
import br.com.ufp.sd.types.UsuarioDelete;
import br.com.ufp.sd.types.UsuarioUpdate;
import br.com.ufp.sd.utils.ResponseType;
import br.com.ufp.sd.utils.ResponseUtil;

public class ApiUsuarios {
	
	private Logger logger = Logger.getLogger(ApiUsuarios.class);

	private final Client client;
	private final String BASE_URL;

	public ApiUsuarios(Client client, String BASE_URL) {
		this.client = client;
		this.BASE_URL = BASE_URL;
	}

	public ConsultaUsuariosResponse consultaUsuarios() throws Exception {
		Response apiResponse = null;
		
		ConsultaUsuariosResponse resposta = new ConsultaUsuariosResponse();
		resposta.setCdRetorno(ResponseType.STATUS_COD_OK);
		resposta.setDsRetorno(ResponseType.STATUS_MSG_OK);

		try {
			WebTarget endpoint = client.target(BASE_URL).path("/usuarios");
			logger.info("endpoint: " + endpoint);

			JSONObject usuarioJson = UsuariosJsonUtil.montaJsonDeConsultaDosUsuarios();
			logger.info("json envio: " + usuarioJson.toString());

			apiResponse = endpoint.request().accept(MediaType.APPLICATION_JSON)
					.post(Entity.json(usuarioJson.toString()));

			ResponseUtil.throwApiExceptionIfAny(apiResponse);

			List<UsuarioUpdate> usuarios = UsuariosJsonUtil.montaDadosDosUsuariosRetornados(apiResponse);
			resposta.getUsuarios().addAll(usuarios);
			
			//logger.info("consultaTodosOsUsuarios - retorno api headers: " + apiResponse.getStringHeaders());
			for(UsuarioUpdate user: usuarios) {
				logger.info("retorno - id: " + user.getId() + " usuario: " +user.getNome());
			}

			return resposta;
		} finally {
			if (apiResponse != null) {
				apiResponse.close();
			}
		}
	}
	
	public ResponseType criaUsuario(UsuarioCreate request) throws Exception {
		Response apiResponse = null;

		try {
			WebTarget endpoint = client.target(BASE_URL).path("/usuarios");
			logger.info("endpoint: " + endpoint);

			JSONObject usuarioJson = UsuariosJsonUtil.montaJsonDeCriacaoDoUsuario(request);
			logger.info("json envio: " + usuarioJson.toString());

			apiResponse = endpoint.request().accept(MediaType.APPLICATION_JSON)
					.post(Entity.json(usuarioJson.toString()));

			ResponseUtil.throwApiExceptionIfAny(apiResponse);			

			ResponseType response = UsuariosJsonUtil.montaDadosRetornoUsuarioCriado(apiResponse);
			//logger.info("criaPedido - retorno api headers: " + apiResponse.getStringHeaders());
			logger.info("retorno api: " + response);

			return response;
		} finally {
			if (apiResponse != null) {
				apiResponse.close();
			}
		}
	}
	
	public ResponseType atualizaUsuario(UsuarioUpdate request) throws Exception {
		Response apiResponse = null;

		try {
			WebTarget endpoint = client.target(BASE_URL).path("/usuarios");
			logger.info("endpoint: " + endpoint);

			JSONObject usuarioJson = UsuariosJsonUtil.montaJsonDeAtualizacaoDoUsuario(request);
			logger.info("json envio: " + usuarioJson.toString());

			apiResponse = endpoint.request().accept(MediaType.APPLICATION_JSON)
					.post(Entity.json(usuarioJson.toString()));

			ResponseUtil.throwApiExceptionIfAny(apiResponse);

			ResponseType response = UsuariosJsonUtil.montaDadosRetornoUsuarioAtualizado(apiResponse);
			//logger.info("atualizaUsuario - retorno api headers: " + apiResponse.getStringHeaders());
			logger.info("retorno api: " + response);

			return response;
		} finally {
			if (apiResponse != null) {
				apiResponse.close();
			}
		}
	}
	
	public ResponseType deletaUsuario(UsuarioDelete request) throws Exception {
		Response apiResponse = null;

		try {
			WebTarget endpoint = client.target(BASE_URL).path("/usuarios");
			logger.info("endpoint: " + endpoint);

			JSONObject usuarioJson = UsuariosJsonUtil.montaJsonDeDeleteDoUsuario(request);
			logger.info("json envio: " + usuarioJson.toString());
			
			apiResponse = endpoint.request().accept(MediaType.APPLICATION_JSON)
					.post(Entity.json(usuarioJson.toString()));

			ResponseUtil.throwApiExceptionIfAny(apiResponse);

			ResponseType response = UsuariosJsonUtil.montaDadosRetornoUsuarioDeletado(apiResponse);
			//logger.info("deletaUsuario - retorno api headers: " + apiResponse.getStringHeaders());
			logger.info("retorno api: " + response.getDsRetorno());

			return response;
		} finally {
			if (apiResponse != null) {
				apiResponse.close();
			}
		}
	}

}