package br.com.ufp.sd.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.apache.log4j.Logger;
import org.apache.log4j.NDC;

import br.com.ufp.sd.resources.ApiUsuarios;
import br.com.ufp.sd.types.ConsultaUsuariosResponse;
import br.com.ufp.sd.types.UsuarioCreate;
import br.com.ufp.sd.types.UsuarioUpdate;
import br.com.ufp.sd.utils.ResponseType;

@WebService(name = "ApiSoap", 
			serviceName = "ApiSoap", 
			portName = "ApiSoapPort", 
			targetNamespace = "http://br.com.upf.sd/ApiSoap")
public class ApiSoap {
	
	private final String baseUri = "http://loadbalancer-sd";
	
	private Logger logger = Logger.getLogger(ApiSoap.class);
	
	private Client restClient = ClientBuilder.newClient();
	
	@WebMethod(action = "getUsuarios", operationName = "getUsuarios")
	@WebResult(name = "getUsuariosResponse")
	public ConsultaUsuariosResponse consultarUsuarios() {
		
		ConsultaUsuariosResponse response = new ConsultaUsuariosResponse();
		
		try {
			//NDC.push("[Inicio]");
			logger.info("Inicio");
			
			ApiUsuarios api = new ApiUsuarios(restClient, baseUri);
			response = api.consultaUsuarios();
			
			logger.info("cod. retorno: " + response.getCdRetorno());
			logger.info("desc. retorno: " + response.getDsRetorno());
			logger.info("fim");
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage(), e);
			response.setCdRetorno(ResponseType.STATUS_COD_EXPTION);
			response.setDsRetorno("Ocorreu um erro desconhecido ao executar operação. " + e.getMessage());
		} finally {
			NDC.pop();
		}
		
		return response;	
	}
	
	@WebMethod(action = "criaUsuario", operationName = "criaUsuario")
	@WebResult(name = "criaUsuarioResponse")
	public ResponseType criaUsuario(
			@WebParam(name = "criaUsuarioRequest") UsuarioCreate request){
		
		ResponseType response = new ResponseType();
		
		try {
			//NDC.push("[Inicio]");
			logger.info("Inicio");
			
			ApiUsuarios api = new ApiUsuarios(restClient, baseUri);
			response = api.criaUsuario(request);
			
			logger.info("cod. retorno: " + response.getCdRetorno());
			logger.info("desc. retorno: " + response.getDsRetorno());
			logger.info("response: " + response);
			logger.info("fim");
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage(), e);
			response.setCdRetorno(ResponseType.STATUS_COD_EXPTION);
			response.setDsRetorno("Ocorreu um erro desconhecido ao executar operação. " + e.getMessage());
		} finally {
			NDC.pop();
		}
		
		return response;	
	}
	
	@WebMethod(action = "atualizaUsuario", operationName = "atualizaUsuario")
	@WebResult(name = "atualizaUsuarioResponse")
	public ResponseType atualizaUsuario(
			@WebParam(name = "atualizaUsuarioRequest") UsuarioUpdate request){
		
		ResponseType response = new ResponseType();
		
		try {
			//NDC.push("[Inicio]");
			logger.info("Inicio");
			
			ApiUsuarios api = new ApiUsuarios(restClient, baseUri);
			response = api.atualizaUsuario(request);
			
			logger.info("cod. retorno: " + response.getCdRetorno());
			logger.info("desc. retorno: " + response.getDsRetorno());
			logger.info("response: " + response);
			logger.info("fim");
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage(), e);
			response.setCdRetorno(ResponseType.STATUS_COD_EXPTION);
			response.setDsRetorno("Ocorreu um erro desconhecido ao executar operação. " + e.getMessage());
		} finally {
			NDC.pop();
		}
		
		return response;	
	}
	
	@WebMethod(action = "deletaUsuario", operationName = "deletaUsuario")
	@WebResult(name = "deletaUsuarioResponse")
	public ResponseType deletaUsuario(
			@WebParam(name = "deletaUsuarioRequest") String id){
		
		ResponseType response = new ResponseType();
		
		try {
			//NDC.push("[Inicio]");
			logger.info("Inicio");
			
			ApiUsuarios api = new ApiUsuarios(restClient, baseUri);
			response = api.deletaUsuario(id);
			
			logger.info("cod. retorno: " + response.getCdRetorno());
			logger.info("desc. retorno: " + response.getDsRetorno());
			logger.info("response: " + response);
			logger.info("fim");
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage(), e);
			response.setCdRetorno(ResponseType.STATUS_COD_EXPTION);
			response.setDsRetorno("Ocorreu um erro desconhecido ao executar operação. " + e.getMessage());
		} finally {
			NDC.pop();
		}
		
		return response;	
	}
	
	/*public static void main(String[] args) {
		Api api = new Api();
//		Usuario user = new Usuario();
		
//		user.setId("5ae8b68b325fd9271859f576");
//		user.setNome("Claudemir");
//		user.setIPaddres("0.0.0.0");
		
		api.consultarUsuarios();
//		api.criaUsuario(user);
//		api.atualizaUsuario(user);
//		api.deletaUsuario("5ae8b68b325fd9271859f576");

	}*/
}
