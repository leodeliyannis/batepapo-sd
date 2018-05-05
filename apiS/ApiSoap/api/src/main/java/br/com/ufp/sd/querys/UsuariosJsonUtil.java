package br.com.ufp.sd.querys;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import br.com.ufp.sd.types.UsuarioCreate;
import br.com.ufp.sd.types.UsuarioDelete;
import br.com.ufp.sd.types.UsuarioUpdate;
import br.com.ufp.sd.utils.ResponseType;

public class UsuariosJsonUtil {
	
	private static Logger logger = Logger.getLogger(UsuariosJsonUtil.class);

	private UsuariosJsonUtil() {

	}
	
	/** Consulta **/
	public static JSONObject montaJsonDeConsultaDosUsuarios() {
		/** {getUsuarios {_id Nome IPaddres Configuracao{usuario dt_criacao Atualizacoes{usuario dt_atualizacao}}} **/
		JSONObject jsonObj = new JSONObject(); 
		
		jsonObj.put("query", "{getUsuarios {_id Nome IPaddres}}");
		
		return jsonObj;
	
	}
	
	public static List<UsuarioUpdate> montaDadosDosUsuariosRetornados(Response apiResponse) {
		JSONObject jsonRetorno = new JSONObject(apiResponse.readEntity(String.class));
		
		logger.info("getUsuarios - json response: " + jsonRetorno);		

		List<UsuarioUpdate> usuarios = new ArrayList<UsuarioUpdate>();
		
		for (int i = 0; i < jsonRetorno.getJSONObject("data").getJSONArray("getUsuarios").length(); ++i) {
			JSONObject jsonUsuario = jsonRetorno.getJSONObject("data").getJSONArray("getUsuarios").getJSONObject(i);
			UsuarioUpdate usuario = montaUsuario(jsonUsuario);
			usuarios.add(usuario);
		}

		return usuarios;
	}
	
	private static UsuarioUpdate montaUsuario(JSONObject jsonRetorno) {
		UsuarioUpdate user = new UsuarioUpdate();

		user.setId(jsonRetorno.getString("_id"));
		user.setNome(jsonRetorno.getString("Nome"));
		user.setIPaddres(jsonRetorno.getString("IPaddres"));
		
		return user;
	
	}
	
	/** Criação **/
	public static JSONObject montaJsonDeCriacaoDoUsuario(UsuarioCreate request) {
		/** mutation{createUsuario (input: {Nome IPaddres}) {cdRetorno dsRetorno}} **/
		JSONObject param = new JSONObject();
		
		param.put("query", "mutation{" +
			       "createUsuario(input: {Nome: \""+request.getNome()+"\", IPaddres: \""+ request.getIPaddres()+"\" }){cdRetorno dsRetorno}" +
		       "}");
		
		return param;
	
	}
	
	public static ResponseType montaDadosRetornoUsuarioCriado(Response apiResponse) {
		JSONObject jsonRetorno = new JSONObject(apiResponse.readEntity(String.class));
		
		logger.info("criaUsuario - json response: " + jsonRetorno);
		
		return montaRetorno(jsonRetorno, "createUsuario");
	}
	
	/** Atualização **/
	public static JSONObject montaJsonDeAtualizacaoDoUsuario(UsuarioUpdate request) {
		/** mutation{updateUsuario (input: {_id Nome IPaddres}) {cdRetorno dsRetorno}} **/
		JSONObject param = new JSONObject();
		String dados = "";
		
		if(request.getNome() != null && !request.getNome().isEmpty()) {
			dados+= ", Nome: \""+request.getNome()+"\"";
		}if(request.getIPaddres() != null && !request.getIPaddres().isEmpty()) {
			dados+= ", IPaddres: \""+request.getIPaddres()+"\"";
		}
		
		param.put("query", "mutation{" +
			       "updateUsuario(input: {_id: \""+request.getId()+"\" "+dados+"}){cdRetorno dsRetorno}" +
		       "}");
		
		return param;
	
	}
	
	public static ResponseType montaDadosRetornoUsuarioAtualizado(Response apiResponse) {
		JSONObject jsonRetorno = new JSONObject(apiResponse.readEntity(String.class));
		
		logger.info("atualizaUsuario - json response: " + jsonRetorno);
		
		return montaRetorno(jsonRetorno, "updateUsuario");
	}
	
	/** Delete **/
	public static JSONObject montaJsonDeDeleteDoUsuario(UsuarioDelete request) {
		/** mutation{deleteUsuario (input: {_id}) {cdRetorno dsRetorno}} **/
		JSONObject param = new JSONObject();

		param.put("query", "mutation{" +
					"deleteUsuario (input: {_id: \""+request.getId()+"\" }) {cdRetorno dsRetorno}" +
				  "}");
		
		return param;
	
	}
	
	public static ResponseType montaDadosRetornoUsuarioDeletado(Response apiResponse) {
		JSONObject jsonRetorno = new JSONObject(apiResponse.readEntity(String.class));

		logger.info("deletaUsuario - json response: " + jsonRetorno);		
		
		return montaRetorno(jsonRetorno, "deleteUsuario");
	}
	
	/** Metodo para todos os reponses de mutation **/
	private static ResponseType montaRetorno(JSONObject jsonRetorno, String metodo) {		
		ResponseType resp = new ResponseType();
		
		resp.setCdRetorno(jsonRetorno.getJSONObject("data").getJSONObject(metodo).getInt("cdRetorno"));
		resp.setDsRetorno(jsonRetorno.getJSONObject("data").getJSONObject(metodo).getString("dsRetorno"));
		
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
