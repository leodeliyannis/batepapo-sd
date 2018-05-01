package br.com.upf.sd.utils;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.upf.sd.types.Usuario;

public class UsuariosJsonUtil {

	private UsuariosJsonUtil() {

	}
	
	/** Consulta **/
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
		JSONObject usuario = new JSONObject();
		
		usuario.put("Nome", request.getNome());
		usuario.put("IPaddres", request.getNome());
		
		return usuario;
	
	}
	
	public static ResponseType montaDadosRetornoUsuarioCriado(Response apiResponse) {
		JSONObject jsonRetorno = new JSONObject(apiResponse.readEntity(String.class));
		
		return montaRetorno(jsonRetorno);
	}
	
	/** Atualização **/
	public static JSONObject montaJsonDeAtualizacaoDoUsuario(Usuario request) {
		JSONObject usuario = new JSONObject();
		
		usuario.put("Nome", request.getNome());
		usuario.put("IPaddres", request.getNome());
		
		return usuario;
	
	}
	
	public static ResponseType montaDadosRetornoUsuarioAtualizado(Response apiResponse) {
		JSONObject jsonRetorno = new JSONObject(apiResponse.readEntity(String.class));
		
		return montaRetorno(jsonRetorno);
	}
	
	/** Delete **/
	public static JSONObject montaJsonDeDeleteDoUsuario(String id) {
		JSONObject usuario = new JSONObject();
		
		usuario.put("_id", id);
		
		return usuario;
	
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
