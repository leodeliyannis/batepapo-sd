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
import br.com.ufp.sd.types.AtualizaUsuarioRequest;
import br.com.ufp.sd.types.ConsultaUsuarioRequest;
import br.com.ufp.sd.types.ConsultaUsuariosResponse;
import br.com.ufp.sd.types.CriaUsuarioRequest;
import br.com.ufp.sd.types.DeletaUsuarioRequest;
import br.com.ufp.sd.types.LoginRequest;
import br.com.ufp.sd.types.LoginResponse;
import br.com.ufp.sd.types.RegistraAcessoRequest;
import br.com.ufp.sd.types.RegristraChatRequest;
import br.com.ufp.sd.types.RegristraPesquisaRequest;
import br.com.ufp.sd.utils.ResponseType;

@WebService(name = "ApiSoap", 
			serviceName = "ApiSoap", 
			portName = "ApiSoapPort", 
			targetNamespace = "http://br.com.upf.sd/ApiSoap")
public class ApiSoap {
	
	private final String baseUri = "http://loadbalancer-sd";
//	private final String baseUri = "http://127.0.0.1:5000";
	
	private Logger logger = Logger.getLogger(ApiSoap.class);
	
	private Client restClient = ClientBuilder.newClient();
	
	@WebMethod(action = "login", operationName = "login")
	@WebResult(name = "loginResponse")
	public LoginResponse login(
			@WebParam(name = "loginRequest") LoginRequest request){
		
		LoginResponse response = new LoginResponse();
		
		try {
			//NDC.push("[Inicio]");
			logger.info("Inicio");
			logger.info("Request: "+request);
			
			request.validateFields();
			
			ApiUsuarios api = new ApiUsuarios(restClient, baseUri);
			response = api.login(request);
			
			logger.info("cod. retorno: " + response.getCdRetorno());
			logger.info("desc. retorno: " + response.getDsRetorno());
			logger.info("token retorno: " + response.getToken());
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
	
	@WebMethod(action = "getUsuarios", operationName = "getUsuarios")
	@WebResult(name = "getUsuariosResponse")
	public ConsultaUsuariosResponse consultarUsuarios(
			@WebParam(name = "consultaUsuarioRequest") ConsultaUsuarioRequest request) {
		
		ConsultaUsuariosResponse response = new ConsultaUsuariosResponse();
		
		try {
			//NDC.push("[Inicio]");
			logger.info("Inicio");
			
			request.validateFields();
			
			ApiUsuarios api = new ApiUsuarios(restClient, baseUri);
			response = api.consultaUsuarios(request);
			
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
			@WebParam(name = "criaUsuarioRequest") CriaUsuarioRequest request){
		
		ResponseType response = new ResponseType();
		
		try {
			//NDC.push("[Inicio]");
			logger.info("Inicio");
			logger.info("Request: "+request);
			
			request.validateFields();
			
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
			@WebParam(name = "atualizaUsuarioRequest") AtualizaUsuarioRequest request){
		
		ResponseType response = new ResponseType();
		
		try {
			//NDC.push("[Inicio]");
			logger.info("Inicio");
			logger.info("Request: "+request);
			
			request.validateFields();
			
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
			@WebParam(name = "deletaUsuarioRequest") DeletaUsuarioRequest request){
		
		ResponseType response = new ResponseType();
		
		try {
			//NDC.push("[Inicio]");
			logger.info("Inicio");
			logger.info("Request: "+request);
			
			request.validateFields();
			
			ApiUsuarios api = new ApiUsuarios(restClient, baseUri);
			response = api.deletaUsuario(request);
			
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
	
	@WebMethod(action = "regristraAcesso", operationName = "regristraAcesso")
	@WebResult(name = "regristraAcessoResponse")
	public ResponseType regristraAcesso(
			@WebParam(name = "regristraAcessoRequest") RegistraAcessoRequest request){
		
		ResponseType response = new ResponseType();
		
		try {
			//NDC.push("[Inicio]");
			logger.info("Inicio");
			logger.info("Request: "+request);
			
			request.validateFields();
			
			ApiUsuarios api = new ApiUsuarios(restClient, baseUri);
			response = api.regristraAcesso(request);
			
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
	
	@WebMethod(action = "regristraPesquisa", operationName = "regristraPesquisa")
	@WebResult(name = "regristraPesquisaResponse")
	public ResponseType regristraPesquisa(
			@WebParam(name = "regristraPesquisaRequest") RegristraPesquisaRequest request){
		
		ResponseType response = new ResponseType();
		
		try {
			//NDC.push("[Inicio]");
			logger.info("Inicio");
			logger.info("Request: "+request);
			
			request.validateFields();
			
			ApiUsuarios api = new ApiUsuarios(restClient, baseUri);
			response = api.regristraPesquisa(request);
			
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
	
	@WebMethod(action = "regristraChat", operationName = "regristraChat")
	@WebResult(name = "regristraChatResponse")
	public ResponseType regristraChat(
			@WebParam(name = "regristraChatRequest") RegristraChatRequest request){
		
		ResponseType response = new ResponseType();
		
		try {
			//NDC.push("[Inicio]");
			logger.info("Inicio");
			logger.info("Request: "+request);
			
			request.validateFields();
			
			ApiUsuarios api = new ApiUsuarios(restClient, baseUri);
			response = api.regristraChat(request);
			
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
	
//	public static void main(String[] args) {
//		ApiSoap api = new ApiSoap();
//		UsuarioCreate cria = new UsuarioCreate();
//		CriaUsuarioRequest user = new CriaUsuarioRequest();
		
//		cria.setNome("Claudemir");
//		cria.setSenha("teste");
//		cria.setIPaddres("192.168.0.1");
//		user.setUsuario(cria);
//		api.criaUsuario(user);
		
//		LoginRequest login = new LoginRequest();
//		login.setNome("Claudemir");
//		login.setSenha("teste");
//		login.setIPaddres("192.168.0.1");
//		api.login(login);
		
//		ConsultaUsuarioRequest consulta	= new ConsultaUsuarioRequest();
//		Autenticacao aut = new Autenticacao();
//		aut.setToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiI1YWVlNzMzODdhMTg0NDI3M2MzOWFmOWIiLCJleHAiOjE1MjU2NjMxNzM2ODN9.YqDh-Ehp7xdcDHl_Jjv5E148FWYti9aXLhX4Fz-Fifc");
//		consulta.setAutenticacao(aut);
//		api.consultarUsuarios(consulta);
//
//	}
}
