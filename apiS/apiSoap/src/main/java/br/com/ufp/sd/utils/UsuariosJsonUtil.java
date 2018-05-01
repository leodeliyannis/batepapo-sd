package br.com.ufp.sd.utils;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.ufp.sd.types.Usuario;

public class UsuariosJsonUtil {

	private UsuariosJsonUtil() {

	}
	
	/** Consulta **/
	public static JSONObject montaJsonDeConsultaDosUsuarios() {
		/** {getUsuarios {_id Nome IPaddres Configuracao{usuario dt_criacao Atualizacoes{usuario dt_atualizacao}}} **/
		JSONObject consulta = new JSONObject();
		
		consulta.put("getUsuarios", "_id Nome IPaddres");
		
		return consulta;
	
	}
	
	public static List<Usuario> montaDadosDosUsuariosRetornados(Response apiResponse) {
		JSONArray jsonRetorno = new JSONArray(apiResponse.readEntity(String.class));

		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		for (int i = 0; i < jsonRetorno.length(); i++) {
			JSONObject jsonUsuario = jsonRetorno.getJSONObject(i);
			Usuario usuario = montaUsuario(jsonUsuario);
			usuarios.add(usuario);
		}

		return usuarios;
	}
	
	private static Usuario montaUsuario(JSONObject jsonRetorno) {
		Usuario user = new Usuario();
		
		user.setId(jsonNvl(jsonRetorno, "_id"));
		user.setNome(jsonNvl(jsonRetorno, "Nome"));
		user.setIPaddres(jsonNvl(jsonRetorno, "IPaddres"));
		
		return user;
	
	}
	
	/** Criação **/
	public static JSONObject montaJsonDeCriacaoDoUsuario(Usuario request) {
		/** mutation{createUsuario (input: {Nome IPaddres}) {cdRetorno dsRetorno}} **/
		JSONObject mutation = new JSONObject();
		JSONObject usuario = new JSONObject();
		JSONObject param = new JSONObject();
		
		param.put("Nome", request.getNome());
		param.put("IPaddres", request.getNome());
		
		usuario.put("createUsuario", "(input: "+param+") {cdRetorno dsRetorno}" );
		mutation.put("mutation", usuario);
		
		return mutation;
	
	}
	
	public static ResponseType montaDadosRetornoUsuarioCriado(Response apiResponse) {
		JSONObject jsonRetorno = new JSONObject(apiResponse.readEntity(String.class));
		
		return montaRetorno(jsonRetorno);
	}
	
	/** Atualização **/
	public static JSONObject montaJsonDeAtualizacaoDoUsuario(Usuario request) {
		/** mutation{updateUsuario (input: {_id Nome IPaddres}) {cdRetorno dsRetorno}} **/
		JSONObject mutation = new JSONObject();
		JSONObject usuario = new JSONObject();
		JSONObject param = new JSONObject();
		
		param.put("_id", request.getId());
		param.put("Nome", request.getNome());
		param.put("IPaddres", request.getNome());
		
		usuario.put("updateUsuario", "(input: "+param+") {cdRetorno dsRetorno}" );
		mutation.put("mutation", usuario);
		
		return mutation;
	
	}
	
	public static ResponseType montaDadosRetornoUsuarioAtualizado(Response apiResponse) {
		JSONObject jsonRetorno = new JSONObject(apiResponse.readEntity(String.class));
		
		return montaRetorno(jsonRetorno);
	}
	
	/** Delete **/
	public static JSONObject montaJsonDeDeleteDoUsuario(String id) {
		/** mutation{deleteUsuario (input: {_id}) {cdRetorno dsRetorno}} **/
		JSONObject mutation = new JSONObject();
		JSONObject usuario = new JSONObject();
		JSONObject param = new JSONObject();
		
		param.put("_id", id);
		
		usuario.put("deleteUsuario", "(input: "+param+") {cdRetorno dsRetorno}" );
		mutation.put("mutation", usuario);
		
		return mutation;
	
	}
	
	public static ResponseType montaDadosRetornoUsuarioDeletado(Response apiResponse) {
		JSONObject jsonRetorno = new JSONObject(apiResponse.readEntity(String.class));
		
		return montaRetorno(jsonRetorno);
	}
	
	/** Metodo para todos os reponses de mutation **/
	private static ResponseType montaRetorno(JSONObject jsonRetorno) {
		ResponseType resp = new ResponseType();
		
		resp.setCdRetorno(Integer.parseInt(jsonNvl(jsonRetorno, "cdRetorno")));
		resp.setDsRetorno(jsonNvl(jsonRetorno, "dsRetorno"));
		
		return resp;
	
	}
	
	private static String jsonNvl(JSONObject obj, String attr) {
		if (obj.has(attr)) {
			try {
				return obj.getString(attr);
			} catch (JSONException e) {
				return "";
			}
		} else {
			return "";
		}
	}


}
