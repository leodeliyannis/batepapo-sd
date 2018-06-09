package br.com.upf.sd.resources;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import br.com.upf.sd.querys.UsuariosJsonUtil;
import br.com.upf.sd.types.LoginRequest;
import br.com.upf.sd.types.LoginResponse;
import br.com.upf.sd.types.QuantidadeChatsRealizadosRequest;
import br.com.upf.sd.types.QuantidadeChatsUsuarioRequest;
import br.com.upf.sd.types.QuantidadeResponse;
import br.com.upf.sd.types.QuantidadeUsuarioLoginRequest;
import br.com.upf.sd.types.TopicosMaisAcessadosRequest;
import br.com.upf.sd.types.TopicosMaisAcessadosResponse;
import br.com.upf.sd.utils.ResponseUtil;

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
	
	public QuantidadeResponse quantidadesUsuariosLoginsValidos(QuantidadeUsuarioLoginRequest request) throws Exception {
		Response apiResponse = null;

		try {
			WebTarget endpoint = client.target(BASE_URL).path("/usuarios");
			logger.info("endpoint: " + endpoint);

			JSONObject usuarioJson = UsuariosJsonUtil.montaJsonRequestQuantidadesUsuariosLoginsValidos(request);
			logger.info("json envio: " + usuarioJson.toString());

			apiResponse = endpoint.request().accept(MediaType.APPLICATION_JSON)
					.post(Entity.json(usuarioJson.toString()));

			ResponseUtil.throwApiExceptionIfAny(apiResponse);			

			QuantidadeResponse response = UsuariosJsonUtil.montaDadosRetornoQuantidadesUsuariosLoginsValidos(apiResponse);
			//logger.info("criaPedido - retorno api headers: " + apiResponse.getStringHeaders());
			logger.info("retorno api: " + response);

			return response;
		} finally {
			if (apiResponse != null) {
				apiResponse.close();
			}
		}
	}
	
	public TopicosMaisAcessadosResponse quantidadesTopicosMaisAcessados(TopicosMaisAcessadosRequest request) throws Exception {
		Response apiResponse = null;

		try {
			WebTarget endpoint = client.target(BASE_URL).path("/usuarios");
			logger.info("endpoint: " + endpoint);

			JSONObject usuarioJson = UsuariosJsonUtil.montaJsonRequestQuantidadesTopicosMaisAcessados(request);
			logger.info("json envio: " + usuarioJson.toString());

			apiResponse = endpoint.request().accept(MediaType.APPLICATION_JSON)
					.post(Entity.json(usuarioJson.toString()));

			ResponseUtil.throwApiExceptionIfAny(apiResponse);			

			TopicosMaisAcessadosResponse response = UsuariosJsonUtil.montaDadosRetornoQuantidadesTopicosMaisAcessados(apiResponse);
			//logger.info("criaPedido - retorno api headers: " + apiResponse.getStringHeaders());
			logger.info("retorno api: " + response);

			return response;
		} finally {
			if (apiResponse != null) {
				apiResponse.close();
			}
		}
	}
	
	public QuantidadeResponse quantidadesChatsRealizados(QuantidadeChatsRealizadosRequest request) throws Exception {
		Response apiResponse = null;

		try {
			WebTarget endpoint = client.target(BASE_URL).path("/usuarios");
			logger.info("endpoint: " + endpoint);

			JSONObject usuarioJson = UsuariosJsonUtil.montaJsonRequestQuantidadesChatsRealizados(request);
			logger.info("json envio: " + usuarioJson.toString());

			apiResponse = endpoint.request().accept(MediaType.APPLICATION_JSON)
					.post(Entity.json(usuarioJson.toString()));

			ResponseUtil.throwApiExceptionIfAny(apiResponse);			

			QuantidadeResponse response = UsuariosJsonUtil.montaDadosRetornoQuantidadesChatsRealizados(apiResponse);
			//logger.info("criaPedido - retorno api headers: " + apiResponse.getStringHeaders());
			logger.info("retorno api: " + response);

			return response;
		} finally {
			if (apiResponse != null) {
				apiResponse.close();
			}
		}
	}
	
	public QuantidadeResponse quantidadesChatsRealizadosUsuarios(QuantidadeChatsUsuarioRequest request) throws Exception {
		Response apiResponse = null;

		try {
			WebTarget endpoint = client.target(BASE_URL).path("/usuarios");
			logger.info("endpoint: " + endpoint);

			JSONObject usuarioJson = UsuariosJsonUtil.montaJsonRequestQuantidadesChatsRealizadosUsuarios(request);
			logger.info("json envio: " + usuarioJson.toString());

			apiResponse = endpoint.request().accept(MediaType.APPLICATION_JSON)
					.post(Entity.json(usuarioJson.toString()));

			ResponseUtil.throwApiExceptionIfAny(apiResponse);			

			QuantidadeResponse response = UsuariosJsonUtil.montaDadosRetornoQuantidadesChatsRealizadosUsuarios(apiResponse);
			//logger.info("criaPedido - retorno api headers: " + apiResponse.getStringHeaders());
			logger.info("retorno api: " + response);

			return response;
		} finally {
			if (apiResponse != null) {
				apiResponse.close();
			}
		}
	}

}
