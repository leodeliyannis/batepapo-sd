package br.com.upf.sd.ws;


import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.apache.log4j.NDC;

import br.com.upf.sd.resources.ApiUsuarios;
import br.com.upf.sd.types.LoginRequest;
import br.com.upf.sd.types.LoginResponse;
import br.com.upf.sd.types.QuantidadeChatsRealizadosRequest;
import br.com.upf.sd.types.QuantidadeChatsUsuarioRequest;
import br.com.upf.sd.types.QuantidadeResponse;
import br.com.upf.sd.types.QuantidadeUsuarioLoginRequest;
import br.com.upf.sd.types.TopicosMaisAcessadosRequest;
import br.com.upf.sd.types.TopicosMaisAcessadosResponse;
import br.com.upf.sd.utils.ResponseType;

@Path("/Api")
public class ApiRest {
	
	private final String baseUri = "http://loadbalancer-sd";
//	private final String baseUri = "http://127.0.0.1:4000";
	
	private Logger logger = Logger.getLogger(ApiRest.class);
	
	private Client restClient = ClientBuilder.newClient();
	
	@POST
	@Path("/login")	
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response login(LoginRequest request){
		
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
			
			Response.accepted(response).build();
		} finally {
			NDC.pop();
		}
		
		return Response.accepted(response).build();
	}
	
	@GET
	@Path("/quantidadesUsuariosLoginsValidos")	
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response quantidadesUsuariosLoginsValidos(QuantidadeUsuarioLoginRequest request){
		
		QuantidadeResponse response = new QuantidadeResponse();
		
		try {
			//NDC.push("[Inicio]");
			logger.info("Inicio");
			logger.info("Request: "+request);
			
			request.validateFields();

			ApiUsuarios api = new ApiUsuarios(restClient, baseUri);
			response = api.quantidadesUsuariosLoginsValidos(request);
			
			logger.info("cod. retorno: " + response.getCdRetorno());
			logger.info("desc. retorno: " + response.getDsRetorno());
			logger.info("response: " + response);
			logger.info("fim");
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage(), e);
			response.setCdRetorno(ResponseType.STATUS_COD_EXPTION);
			response.setDsRetorno("Ocorreu um erro desconhecido ao executar operação. " + e.getMessage());
			
			Response.accepted(response).build();
		} finally {
			NDC.pop();
		}
		
		return Response.accepted(response).build();
	}
	
	@GET
	@Path("/quantidadesTopicosMaisAcessados")	
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response quantidadesTopicosMaisAcessados(TopicosMaisAcessadosRequest request){
		
		TopicosMaisAcessadosResponse response = new TopicosMaisAcessadosResponse();
		
		try {
			//NDC.push("[Inicio]");
			logger.info("Inicio");
			logger.info("Request: "+request);
			
			request.validateFields();

			ApiUsuarios api = new ApiUsuarios(restClient, baseUri);
			response = api.quantidadesTopicosMaisAcessados(request);
			
			logger.info("cod. retorno: " + response.getCdRetorno());
			logger.info("desc. retorno: " + response.getDsRetorno());
			logger.info("response: " + response);
			logger.info("fim");
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage(), e);
			response.setCdRetorno(ResponseType.STATUS_COD_EXPTION);
			response.setDsRetorno("Ocorreu um erro desconhecido ao executar operação. " + e.getMessage());
			
			Response.accepted(response).build();
		} finally {
			NDC.pop();
		}
		
		return Response.accepted(response).build();
	}
	
	@GET
	@Path("/quantidadesChatsRealizados")	
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response quantidadesChatsRealizados(QuantidadeChatsRealizadosRequest request){
		
		QuantidadeResponse response = new QuantidadeResponse();
		
		try {
			//NDC.push("[Inicio]");
			logger.info("Inicio");
			logger.info("Request: "+request);
			
			request.validateFields();

			ApiUsuarios api = new ApiUsuarios(restClient, baseUri);
			response = api.quantidadesChatsRealizados(request);
			
			logger.info("cod. retorno: " + response.getCdRetorno());
			logger.info("desc. retorno: " + response.getDsRetorno());
			logger.info("response: " + response);
			logger.info("fim");
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage(), e);
			response.setCdRetorno(ResponseType.STATUS_COD_EXPTION);
			response.setDsRetorno("Ocorreu um erro desconhecido ao executar operação. " + e.getMessage());
			
			Response.accepted(response).build();
		} finally {
			NDC.pop();
		}
		
		return Response.accepted(response).build();
	}
	
	@GET
	@Path("/quantidadesChatsRealizadosUsuarios")	
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response quantidadesChatsRealizadosUsuarios(QuantidadeChatsUsuarioRequest request){
		
		QuantidadeResponse response = new QuantidadeResponse();
		
		try {
			//NDC.push("[Inicio]");
			logger.info("Inicio");
			logger.info("Request: "+request);
			
			request.validateFields();

			ApiUsuarios api = new ApiUsuarios(restClient, baseUri);
			response = api.quantidadesChatsRealizadosUsuarios(request);
			
			logger.info("cod. retorno: " + response.getCdRetorno());
			logger.info("desc. retorno: " + response.getDsRetorno());
			logger.info("response: " + response);
			logger.info("fim");
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage(), e);
			response.setCdRetorno(ResponseType.STATUS_COD_EXPTION);
			response.setDsRetorno("Ocorreu um erro desconhecido ao executar operação. " + e.getMessage());
			
			Response.accepted(response).build();
		} finally {
			NDC.pop();
		}
		
		return Response.accepted(response).build();
	}
	
	@GET
	@Path("/teste")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getMsg() {
 
		return Response.status(200).build();
 
	}
}
