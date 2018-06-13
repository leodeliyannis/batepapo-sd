package br.com.upf.sd.resources;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import br.com.upf.sd.querys.UsuariosJsonUtil;
import br.com.upf.sd.types.LoginRequest;
import br.com.upf.sd.types.LoginResponse;
import br.com.upf.sd.types.TopicosUsuariosResponse;
import br.com.upf.sd.types.UsuarioInput;
import br.com.upf.sd.types.UsuariosTopicosResponse;
import br.com.upf.sd.utils.ResponseType;
import br.com.upf.sd.utils.ResponseUtil;

public class ApiBanco {
	
	private Client client = ClientBuilder.newClient();
	private String host = "http://192.168.1.103:80";
	
	public LoginResponse login(LoginRequest request) throws Exception {
		Response apiResponse = null;

		try {
			WebTarget endpoint = client.target(host).path("/usuarios");

			JSONObject usuarioJson = UsuariosJsonUtil.montaJsonDeLogin(request);
			
			apiResponse = endpoint.request().accept(MediaType.APPLICATION_JSON)
					.post(Entity.json(usuarioJson.toString()));
			
			ResponseUtil.throwApiExceptionIfAny(apiResponse);

			LoginResponse response = UsuariosJsonUtil.montaDadosRetornoLogin(apiResponse);		

			return response;
		} finally {
			if (apiResponse != null) {
				apiResponse.close();
			}
		}
	}
	
	public ResponseType registraTopico(UsuarioInput request, String token) throws Exception {
		Response apiResponse = null;
		ResponseType response = null;

		try {
			WebTarget endpoint = client.target(host).path("/usuarios");
			
			for(String i: request.getTopicos()) {

				JSONObject usuarioJson = UsuariosJsonUtil.montaJsonDeRegistraTopico(request.getUsuario(), i, token);
	
				apiResponse = endpoint.request().accept(MediaType.APPLICATION_JSON)
						.post(Entity.json(usuarioJson.toString()));
	
				ResponseUtil.throwApiExceptionIfAny(apiResponse);
	
				response = UsuariosJsonUtil.montaDadosRetornoRegistraTopico(apiResponse);
			}		

			return response;
		} finally {
			if (apiResponse != null) {
				apiResponse.close();
			}
		}
	}
	
	public TopicosUsuariosResponse pesquisaTopicos(String topico, String token) throws Exception {
		Response apiResponse = null;
		TopicosUsuariosResponse response = null;

		try {
			WebTarget endpoint = client.target(host).path("/usuarios");
		
			JSONObject usuarioJson = UsuariosJsonUtil.montaJsonPesquisaTopicos(topico, token);

			apiResponse = endpoint.request().accept(MediaType.APPLICATION_JSON)
					.post(Entity.json(usuarioJson.toString()));

			ResponseUtil.throwApiExceptionIfAny(apiResponse);

			response = UsuariosJsonUtil.montaDadosRetornoPesquisaTopicos(apiResponse);		

			return response;
		} finally {
			if (apiResponse != null) {
				apiResponse.close();
			}
		}
	}
	
	public UsuariosTopicosResponse pesquisaUsuarios(String nome, String token) throws Exception {
		Response apiResponse = null;
		UsuariosTopicosResponse response = null;

		try {
			WebTarget endpoint = client.target(host).path("/usuarios");
		
			JSONObject usuarioJson = UsuariosJsonUtil.montaJsonPesquisaUsuarios(nome, token);

			apiResponse = endpoint.request().accept(MediaType.APPLICATION_JSON)
					.post(Entity.json(usuarioJson.toString()));

			ResponseUtil.throwApiExceptionIfAny(apiResponse);

			response = UsuariosJsonUtil.montaDadosRetornoPesquisaUsuarios(apiResponse);

			return response;
		} finally {
			if (apiResponse != null) {
				apiResponse.close();
			}
		}
	}

}
