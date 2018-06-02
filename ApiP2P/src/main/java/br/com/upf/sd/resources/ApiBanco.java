package br.com.upf.sd.resources;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import br.com.ufp.sd.querys.UsuariosJsonUtil;
import br.com.upf.sd.types.LoginRequest;
import br.com.upf.sd.types.LoginResponse;
import br.com.upf.sd.types.TopicosUsuariosResponse;
import br.com.upf.sd.types.UsuarioInput;
import br.com.upf.sd.utils.ResponseType;
import br.com.upf.sd.utils.ResponseUtil;

public class ApiBanco {
	
//	private Logger logger = Logger.getLogger(ApiBanco.class);
	private Client client = ClientBuilder.newClient();
	
	public LoginResponse login(LoginRequest request) throws Exception {
		Response apiResponse = null;

		try {
			WebTarget endpoint = client.target("http://127.0.0.1:80").path("/usuarios");
//			logger.info("endpoint: " + endpoint);

			JSONObject usuarioJson = UsuariosJsonUtil.montaJsonDeLogin(request);
//			logger.info("json envio: " + usuarioJson.toString());

			apiResponse = endpoint.request().accept(MediaType.APPLICATION_JSON)
					.post(Entity.json(usuarioJson.toString()));

			ResponseUtil.throwApiExceptionIfAny(apiResponse);

			LoginResponse response = UsuariosJsonUtil.montaDadosRetornoLogin(apiResponse);
			//logger.info("criaPedido - retorno api headers: " + apiResponse.getStringHeaders());
//			logger.info("retorno api: " + response);			

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
			WebTarget endpoint = client.target("http://127.0.0.1:80").path("/usuarios");
//			logger.info("endpoint: " + endpoint);
			
			for(String i: request.getTopicos()) {

				JSONObject usuarioJson = UsuariosJsonUtil.montaJsonDeRegistraTopico(request.getUsuario(), i, token);
//			    logger.info("json envio: " + usuarioJson.toString());
	
				apiResponse = endpoint.request().accept(MediaType.APPLICATION_JSON)
						.post(Entity.json(usuarioJson.toString()));
	
				ResponseUtil.throwApiExceptionIfAny(apiResponse);
	
				response = UsuariosJsonUtil.montaDadosRetornoRegistraTopico(apiResponse);
			}
//			logger.info("registraTopico - retorno api headers: " + apiResponse.getStringHeaders());
//			logger.info("retorno api: " + response);			

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
			WebTarget endpoint = client.target("http://127.0.0.1:80").path("/usuarios");
//			logger.info("endpoint: " + endpoint);
		
			JSONObject usuarioJson = UsuariosJsonUtil.montaJsonPesquisaTopicos(topico, token);
//			    logger.info("json envio: " + usuarioJson.toString());

			apiResponse = endpoint.request().accept(MediaType.APPLICATION_JSON)
					.post(Entity.json(usuarioJson.toString()));

			ResponseUtil.throwApiExceptionIfAny(apiResponse);

			response = UsuariosJsonUtil.montaDadosRetornoPesquisaTopicos(apiResponse);
			
//			logger.info("criaPedido - retorno api headers: " + apiResponse.getStringHeaders());
//			logger.info("retorno api: " + response);			

			return response;
		} finally {
			if (apiResponse != null) {
				apiResponse.close();
			}
		}
	}

}
