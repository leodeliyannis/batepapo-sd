package br.com.ufp.sd.querys;

import java.security.NoSuchAlgorithmException;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.JSONObject;

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
import br.com.ufp.sd.types.UsuarioUpdate;
import br.com.ufp.sd.utils.ResponseType;
import br.com.ufp.sd.utils.Security;

public class UsuariosJsonUtil {
	
	private static Logger logger = Logger.getLogger(UsuariosJsonUtil.class);

	private UsuariosJsonUtil() {}
	
	/** Login
	 * 
	 * return cdRetorno dsRetorno token
	 * @throws NoSuchAlgorithmException
	 */
	public static JSONObject montaJsonDeLogin(LoginRequest request) throws NoSuchAlgorithmException {
		JSONObject jsonObj = new JSONObject();
		
		String senha = Security.encrypt(request.getSenha());
		
		jsonObj.put("query", "{login(input: {Nome: \""+request.getNome()+"\", Senha: \""+senha+"\", IPaddres: \""+ request.getIPaddres()+"\"}) {cdRetorno dsRetorno token}}");
		
		return jsonObj;
	}
	
	public static LoginResponse montaDadosRetornoLogin(Response apiResponse) {
		JSONObject jsonRetorno = new JSONObject(apiResponse.readEntity(String.class));
		
		logger.info("login - json response: " + jsonRetorno);
		
		return montaRetornoLogin(jsonRetorno, "login");
	}
	
	/** Consulta  **/
	public static JSONObject montaJsonDeConsultaDosUsuarios(ConsultaUsuarioRequest request) throws NoSuchAlgorithmException {
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("query", "{getUsuarios(input: {token: \""+request.getAutenticacao().getToken()+"\"}) {cdRetorno dsRetorno Usuarios{_id Nome IPaddres}}}");
		
		return jsonObj;
	}
	
	public static ConsultaUsuariosResponse montaDadosDosUsuariosRetornados(Response apiResponse) {
		JSONObject jsonRetorno = new JSONObject(apiResponse.readEntity(String.class));
		ConsultaUsuariosResponse response = new ConsultaUsuariosResponse();
		
		logger.info("getUsuarios - json response: " + jsonRetorno);
		
		response.setCdRetorno(jsonRetorno.getJSONObject("data").getJSONObject("getUsuarios").getInt("cdRetorno"));
		response.setDsRetorno(jsonRetorno.getJSONObject("data").getJSONObject("getUsuarios").getString("dsRetorno"));
		
		if(response.getCdRetorno() == 0) {
			for (int i = 0; i < jsonRetorno.getJSONObject("data").getJSONObject("getUsuarios").getJSONArray("Usuarios").length(); ++i) {
				JSONObject jsonUsuario = jsonRetorno.getJSONObject("data").getJSONObject("getUsuarios").getJSONArray("Usuarios").getJSONObject(i);
				UsuarioUpdate usuario = montaUsuario(jsonUsuario);
				response.getUsuarios().add(usuario);
			}
		}

		return response;
	}
	
	private static UsuarioUpdate montaUsuario(JSONObject jsonRetorno) {
		UsuarioUpdate user = new UsuarioUpdate();

		user.setId(jsonRetorno.getString("_id"));
		user.setNome(jsonRetorno.getString("Nome"));
		user.setIPaddres(jsonRetorno.getString("IPaddres"));
		
		return user;
	}
	
	/** Criação 
	 * 
	 * return cdRetorno dsRetorno
	 * @throws NoSuchAlgorithmException **/
	public static JSONObject montaJsonDeCriacaoDoUsuario(CriaUsuarioRequest request) throws NoSuchAlgorithmException {
		JSONObject param = new JSONObject();
		
		String senha = Security.encrypt(request.getUsuario().getSenha());
		
		param.put("query", "mutation{" +
			       "createUsuario(input: {Nome: \""+request.getUsuario().getNome()+"\", Senha: \""+senha+"\", IPaddres: \""+ request.getUsuario().getIPaddres()+"\" }){cdRetorno dsRetorno}" +
		       "}");
		
		return param;
	}
	
	public static ResponseType montaDadosRetornoUsuarioCriado(Response apiResponse) {
		JSONObject jsonRetorno = new JSONObject(apiResponse.readEntity(String.class));
		
		logger.info("criaUsuario - json response: " + jsonRetorno);
		
		return montaRetorno(jsonRetorno, "createUsuario");
	}
	
	/** Atualização 
	 * 
	 * return cdRetorno dsRetorno
	 * **/
	public static JSONObject montaJsonDeAtualizacaoDoUsuario(AtualizaUsuarioRequest request) throws NoSuchAlgorithmException {
		JSONObject param = new JSONObject();
		String dados = "";
		
		if(request.getUsuarioUpdate().getNome() != null && !request.getUsuarioUpdate().getNome().isEmpty()) {
			dados+= ", Nome: \""+request.getUsuarioUpdate().getNome()+"\"";
		}if(request.getUsuarioUpdate().getIPaddres() != null && !request.getUsuarioUpdate().getIPaddres().isEmpty()) {
			dados+= ", IPaddres: \""+request.getUsuarioUpdate().getIPaddres()+"\"";
		}
		
		param.put("query", "mutation{" +
			       "updateUsuario(input: {_id: \""+request.getUsuarioUpdate().getId()+"\" "+dados+", token: \""+request.getAutenticacao().getToken()+"\"}){cdRetorno dsRetorno}" +
		       "}");
		
		return param;
	}
	
	public static ResponseType montaDadosRetornoUsuarioAtualizado(Response apiResponse) {
		JSONObject jsonRetorno = new JSONObject(apiResponse.readEntity(String.class));
		
		logger.info("atualizaUsuario - json response: " + jsonRetorno);
		
		return montaRetorno(jsonRetorno, "updateUsuario");
	}
	
	/** Delete  
	 * 
	 * return cdRetorno dsRetorno
	 * **/
	public static JSONObject montaJsonDeDeleteDoUsuario(DeletaUsuarioRequest request) throws NoSuchAlgorithmException {
		JSONObject param = new JSONObject();

		param.put("query", "mutation{" +
					"deleteUsuario (input: {_id: \""+request.getUsuario().getId()+"\", token: \""+request.getAutenticacao().getToken()+"\"}) {cdRetorno dsRetorno}" +
				  "}");
		
		return param;
	}
	
	public static ResponseType montaDadosRetornoUsuarioDeletado(Response apiResponse) {
		JSONObject jsonRetorno = new JSONObject(apiResponse.readEntity(String.class));

		logger.info("deletaUsuario - json response: " + jsonRetorno);		
		
		return montaRetorno(jsonRetorno, "deleteUsuario");
	}
	
	/** Registra Acesso  
	 * 
	 * return cdRetorno dsRetorno
	 * **/
	public static JSONObject montaJsonDeRegistraAcesso(RegistraAcessoRequest request) throws NoSuchAlgorithmException {
		JSONObject param = new JSONObject();

		param.put("query", "mutation{" +
					"registraAcesso (input: {_id: \""+request.getId()+"\", token: \""+request.getAutenticacao().getToken()+"\"}) {cdRetorno dsRetorno}" +
				  "}");
		
		return param;
	}
	
	public static ResponseType montaDadosRetornoRegistraAcesso(Response apiResponse) {
		JSONObject jsonRetorno = new JSONObject(apiResponse.readEntity(String.class));

		logger.info("registraAcesso - json response: " + jsonRetorno);		
		
		return montaRetorno(jsonRetorno, "deleteUsuario");
	}
	
	/** Registra Pesquisa  
	 * 
	 * return cdRetorno dsRetorno
	 * **/
	public static JSONObject montaJsonDeRegistraPesquisa(RegristraPesquisaRequest request) {
		JSONObject param = new JSONObject();

		param.put("query", "mutation{" +
					"registraPesquisa (input: {_id: \""+request.getId()+"\", topico: \""+request.getTopico()+"\", token: \""+request.getAutenticacao().getToken()+"\"}) {cdRetorno dsRetorno}" +
				  "}");
		
		return param;
	}
	
	public static ResponseType montaDadosRetornoRegistraPesquisa(Response apiResponse) {
		JSONObject jsonRetorno = new JSONObject(apiResponse.readEntity(String.class));

		logger.info("registraPesquisa - json response: " + jsonRetorno);		
		
		return montaRetorno(jsonRetorno, "deleteUsuario");
	}
	
	/** Registra Chat  
	 * 
	 * return cdRetorno dsRetorno
	 * **/
	public static JSONObject montaJsonDeRegistraChat(RegristraChatRequest request) {
		JSONObject param = new JSONObject();

		param.put("query", "mutation{" +
					"registraChat (input: {_id: \""+request.getId()+"\", topico: \""+request.getTopico()+"\", usuario: \""+request.getUsuario()+"\", token: \""+request.getAutenticacao().getToken()+"\"}) {cdRetorno dsRetorno}" +
				  "}");
		
		return param;
	}
	
	public static ResponseType montaDadosRetornoRegistraChat(Response apiResponse) {
		JSONObject jsonRetorno = new JSONObject(apiResponse.readEntity(String.class));

		logger.info("registraChat - json response: " + jsonRetorno);		
		
		return montaRetorno(jsonRetorno, "deleteUsuario");
	}
	
	/** Metodo para todos os reponses de mutation 
	 * 
	 * return cdRetorno dsRetorno
	 * **/
	private static ResponseType montaRetorno(JSONObject jsonRetorno, String metodo) {
		ResponseType resp = new ResponseType();
		
		resp.setCdRetorno(jsonRetorno.getJSONObject("data").getJSONObject(metodo).getInt("cdRetorno"));
		resp.setDsRetorno(jsonRetorno.getJSONObject("data").getJSONObject(metodo).getString("dsRetorno"));
		
		return resp;
	
	}
	
	private static LoginResponse montaRetornoLogin(JSONObject jsonRetorno, String metodo) {
		LoginResponse resp = new LoginResponse();
		
		resp.setCdRetorno(jsonRetorno.getJSONObject("data").getJSONObject(metodo).getInt("cdRetorno"));
		resp.setDsRetorno(jsonRetorno.getJSONObject("data").getJSONObject(metodo).getString("dsRetorno"));
		
		if(resp.getCdRetorno() == 0) {
			resp.setToken(jsonRetorno.getJSONObject("data").getJSONObject(metodo).getString("token"));
		}
		
		return resp;
	}
	
	/*private static String jsonNvl(JSONObject obj, String attr) {
		if (obj.has(attr)) {
			try {
				return obj.getString(attr);
			} catch (JSONException e) {
				return "";
			}
		} else {
			return "";
		}
	}*/
}
