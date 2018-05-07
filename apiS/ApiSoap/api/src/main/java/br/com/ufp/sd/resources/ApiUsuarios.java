package br.com.ufp.sd.resources;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import br.com.ufp.sd.querys.UsuariosJsonUtil;
import br.com.ufp.sd.types.AtualizaUsuarioRequest;
import br.com.ufp.sd.types.ConsultaUsuarioRequest;
import br.com.ufp.sd.types.ConsultaUsuariosResponse;
import br.com.ufp.sd.types.DeletaUsuarioRequest;
import br.com.ufp.sd.types.LoginRequest;
import br.com.ufp.sd.types.LoginResponse;
import br.com.ufp.sd.types.RegistraAcessoRequest;
import br.com.ufp.sd.types.RegristraChatRequest;
import br.com.ufp.sd.types.RegristraPesquisaRequest;
import br.com.ufp.sd.types.UsuarioCreate;
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
	
	public LoginResponse login(LoginRequest request) throws Exception {
		Response apiResponse = null;

		try {
			WebTarget endpoint = client.target(BASE_URL).path("/usuarios");
			logger.info("endpoint: " + endpoint);

			JSONObject usuarioJson = UsuariosJsonUtil.montaJsonDeLogin(request);
			logger.info("json envio: " + usuarioJson.toString());

			apiResponse = endpoint.request().accept(MediaType.APPLICATION_JSON)
					.post(Entity.json(usuarioJson.toString()));

			ResponseUtil.throwApiExceptionIfAny(apiResponse);			

			LoginResponse response = UsuariosJsonUtil.montaDadosRetornoLogin(apiResponse);
			//logger.info("criaPedido - retorno api headers: " + apiResponse.getStringHeaders());
			logger.info("retorno api: " + response);

			return response;
		} finally {
			if (apiResponse != null) {
				apiResponse.close();
			}
		}
	}

	public ConsultaUsuariosResponse consultaUsuarios(ConsultaUsuarioRequest request) throws Exception {
		Response apiResponse = null;

		try {
			WebTarget endpoint = client.target(BASE_URL).path("/usuarios");
			logger.info("endpoint: " + endpoint);

			JSONObject usuarioJson = UsuariosJsonUtil.montaJsonDeConsultaDosUsuarios(request);
			logger.info("json envio: " + usuarioJson.toString());

			apiResponse = endpoint.request().accept(MediaType.APPLICATION_JSON)
					.post(Entity.json(usuarioJson.toString()));

			ResponseUtil.throwApiExceptionIfAny(apiResponse);

			ConsultaUsuariosResponse resposta = UsuariosJsonUtil.montaDadosDosUsuariosRetornados(apiResponse);

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
	
	public ResponseType atualizaUsuario(AtualizaUsuarioRequest request) throws Exception {
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
	
	public ResponseType deletaUsuario(DeletaUsuarioRequest request) throws Exception {
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
	
	public ResponseType regristraAcesso(RegistraAcessoRequest request) throws Exception {
		Response apiResponse = null;

		try {
			WebTarget endpoint = client.target(BASE_URL).path("/usuarios");
			logger.info("endpoint: " + endpoint);

			JSONObject usuarioJson = UsuariosJsonUtil.montaJsonDeRegistraAcesso(request);
			logger.info("json envio: " + usuarioJson.toString());
			
			apiResponse = endpoint.request().accept(MediaType.APPLICATION_JSON)
					.post(Entity.json(usuarioJson.toString()));

			ResponseUtil.throwApiExceptionIfAny(apiResponse);

			ResponseType response = UsuariosJsonUtil.montaDadosRetornoRegistraAcesso(apiResponse);
			//logger.info("deletaUsuario - retorno api headers: " + apiResponse.getStringHeaders());
			logger.info("retorno api: " + response.getDsRetorno());

			return response;
		} finally {
			if (apiResponse != null) {
				apiResponse.close();
			}
		}
	}

	public ResponseType regristraPesquisa(RegristraPesquisaRequest request) throws Exception {
		Response apiResponse = null;

		try {
			WebTarget endpoint = client.target(BASE_URL).path("/usuarios");
			logger.info("endpoint: " + endpoint);

			JSONObject usuarioJson = UsuariosJsonUtil.montaJsonDeRegistraPesquisa(request);
			logger.info("json envio: " + usuarioJson.toString());
			
			apiResponse = endpoint.request().accept(MediaType.APPLICATION_JSON)
					.post(Entity.json(usuarioJson.toString()));

			ResponseUtil.throwApiExceptionIfAny(apiResponse);

			ResponseType response = UsuariosJsonUtil.montaDadosRetornoRegistraPesquisa(apiResponse);
			//logger.info("deletaUsuario - retorno api headers: " + apiResponse.getStringHeaders());
			logger.info("retorno api: " + response.getDsRetorno());

			return response;
		} finally {
			if (apiResponse != null) {
				apiResponse.close();
			}
		}
	}

	public ResponseType regristraChat(RegristraChatRequest request) throws Exception {
		Response apiResponse = null;

		try {
			WebTarget endpoint = client.target(BASE_URL).path("/usuarios");
			logger.info("endpoint: " + endpoint);

			JSONObject usuarioJson = UsuariosJsonUtil.montaJsonDeRegistraChat(request);
			logger.info("json envio: " + usuarioJson.toString());
			
			apiResponse = endpoint.request().accept(MediaType.APPLICATION_JSON)
					.post(Entity.json(usuarioJson.toString()));

			ResponseUtil.throwApiExceptionIfAny(apiResponse);

			ResponseType response = UsuariosJsonUtil.montaDadosRetornoRegistraChat(apiResponse);
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
