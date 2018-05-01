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
import br.com.ufp.sd.types.Usuario;
import br.com.ufp.sd.utils.ResponseType;

@WebService(name = "ApiSoap", 
			serviceName = "ApiSoap", 
			portName = "ApiSoapPort", 
			targetNamespace = "http://br.com.upf.sd/ApiSoap")
public class Api {
	
	private final String baseUri = "http://127.0.0.1:4000";
	
	private Logger logger = Logger.getLogger(Api.class);
	
	private Client restClient = ClientBuilder.newClient();
	
	@WebMethod(action = "getUsuarios", operationName = "getUsuarios")
	@WebResult(name = "getUsuariosResponse")
	public ConsultaUsuariosResponse consultarUsuarios() {
		
		ConsultaUsuariosResponse response = new ConsultaUsuariosResponse();
		
		try {
			NDC.push("[Inicio - consultarUsuarios]");
			logger.info("Inicio - consultarUsuarios");
			
			ApiUsuarios api = new ApiUsuarios(restClient, baseUri);
			response = api.consultaUsuarios();
			
			logger.info("consultarUsuarios - cod. retorno: " + response.getCdRetorno());
			logger.info("consultarUsuarios - desc. retorno: " + response.getDsRetorno());
			logger.info("fim - consultarUsuarios - response: " + response);
			
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
			@WebParam(name = "criaUsuarioRequest") Usuario request){
		
		ResponseType response = new ResponseType();
		
		try {
			NDC.push("[Inicio - criaUsuario]");
			logger.info("Inicio - criaUsuario");
			
			ApiUsuarios api = new ApiUsuarios(restClient, baseUri);
			response = api.criaUsuario(request);
			
			logger.info("criaUsuario - cod. retorno: " + response.getCdRetorno());
			logger.info("criaUsuario - desc. retorno: " + response.getDsRetorno());
			logger.info("fim - criaUsuario - response: " + response);
			
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
			@WebParam(name = "atualizaUsuarioRequest") Usuario request){
		
		ResponseType response = new ResponseType();
		
		try {
			NDC.push("[Inicio - atualizaUsuario]");
			logger.info("Inicio - atualizaUsuario");
			
			ApiUsuarios api = new ApiUsuarios(restClient, baseUri);
			response = api.atualizaUsuario(request);
			
			logger.info("atualizaUsuario - cod. retorno: " + response.getCdRetorno());
			logger.info("atualizaUsuario - desc. retorno: " + response.getDsRetorno());
			logger.info("fim - atualizaUsuario - response: " + response);
			
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
			NDC.push("[Inicio - deletaUsuario]");
			logger.info("Inicio - deletaUsuario");
			
			ApiUsuarios api = new ApiUsuarios(restClient, baseUri);
			response = api.deletaUsuario(id);
			
			logger.info("deletaUsuario - cod. retorno: " + response.getCdRetorno());
			logger.info("deletaUsuario - desc. retorno: " + response.getDsRetorno());
			logger.info("fim - deletaUsuario - response: " + response);
			
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage(), e);
			response.setCdRetorno(ResponseType.STATUS_COD_EXPTION);
			response.setDsRetorno("Ocorreu um erro desconhecido ao executar operação. " + e.getMessage());
		} finally {
			NDC.pop();
		}
		
		return response;	
	}
}
