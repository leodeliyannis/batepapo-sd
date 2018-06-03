package br.com.upf.sd.querys;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.ws.rs.core.Response;

import org.json.JSONObject;

import br.com.upf.sd.types.LoginRequest;
import br.com.upf.sd.types.LoginResponse;
import br.com.upf.sd.types.Topico;
import br.com.upf.sd.types.TopicosUsuariosResponse;
import br.com.upf.sd.types.Usuario;
import br.com.upf.sd.types.UsuariosTopicosResponse;
import br.com.upf.sd.utils.ResponseType;
import br.com.upf.sd.utils.Security;

public class UsuariosJsonUtil {
	
//	private static Logger logger = Logger.getLogger(UsuariosJsonUtil.class);

	private UsuariosJsonUtil() {}
	
	/** Login
	 * 
	 * return cdRetorno dsRetorno token
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException 
	 */
	public static JSONObject montaJsonDeLogin(LoginRequest request) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		JSONObject jsonObj = new JSONObject();
		
		String senha = Security.encrypt(request.getSenha());
		
		jsonObj.put("query", "{login(input: {Nome: \""+request.getNome()+"\", Senha: \""+senha+"\", IPaddres: \""+ request.getIPaddres()+"\"}) {cdRetorno dsRetorno token}}");
		
		return jsonObj;
	}
	
	public static LoginResponse montaDadosRetornoLogin(Response apiResponse) {
		JSONObject jsonRetorno = new JSONObject(apiResponse.readEntity(String.class));
		
//		logger.info("login - json response: " + jsonRetorno);
		
		return montaRetorno(jsonRetorno, "login");
	}
	
	/** montaJsonDeRegistraTopico
	 * 
	 * return cdRetorno dsRetorno
	 */
	public static JSONObject montaJsonDeRegistraTopico(String nome, String topico, String token) {
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("query", "mutation{registraTopico(input: {nome: \""+ nome +"\", token: \""+ token +"\", topico: \""+ topico +"\"}) {cdRetorno dsRetorno}}");
			
		return jsonObj;
	}
	
	public static ResponseType montaDadosRetornoRegistraTopico(Response apiResponse) {
		JSONObject jsonRetorno = new JSONObject(apiResponse.readEntity(String.class));
		
//		logger.info("registraTopico - json response: " + jsonRetorno);
		
		return montaRetorno(jsonRetorno, "registraTopico");
	}
	
	/** montaJsonPesquisaTopicos
	 * 
	 * return cdRetorno dsRetorno Usuarios{Nome IPaddres}
	 */
	public static JSONObject montaJsonPesquisaTopicos(String topico, String token) {
		JSONObject jsonObj = new JSONObject();		
		
		jsonObj.put("query", "{getTopicosUsuarios(input: {topico: \""+ topico +"\", token: \""+ token +"\"}) {cdRetorno dsRetorno Usuarios{Nome IPaddres}}}");
			
		return jsonObj;
	}
	
	public static TopicosUsuariosResponse montaDadosRetornoPesquisaTopicos(Response apiResponse) {		
		JSONObject jsonRetorno = new JSONObject(apiResponse.readEntity(String.class));
		TopicosUsuariosResponse resp = new TopicosUsuariosResponse();		
		
		resp.setCdRetorno(jsonRetorno.getJSONObject("data").getJSONObject("getTopicosUsuarios").getInt("cdRetorno"));
		resp.setDsRetorno(jsonRetorno.getJSONObject("data").getJSONObject("getTopicosUsuarios").getString("dsRetorno"));

		if(resp.getCdRetorno() == 0) {
			for (int i = 0; i < jsonRetorno.getJSONObject("data").getJSONObject("getTopicosUsuarios").getJSONArray("Usuarios").length(); ++i) {
				JSONObject jsonUsuario = jsonRetorno.getJSONObject("data").getJSONObject("getTopicosUsuarios").getJSONArray("Usuarios").getJSONObject(i);
				Usuario usuario = montaUsuario(jsonUsuario);
				resp.getUsuarios().add(usuario);				
			}
		}		
		
		return resp;
	}
	
	private static Usuario montaUsuario(JSONObject jsonRetorno) {
		Usuario user = new Usuario();

		user.setNome(jsonRetorno.getString("Nome"));
		user.setIpAddres(jsonRetorno.getString("IPaddres"));
		user.setStatus("Online");
		
		return user;
	}
	
	/** montaJsonPesquisaUsuarios
	 * 
	 * return cdRetorno dsRetorno Usuarios{Nome IPaddres}
	 */
	public static JSONObject montaJsonPesquisaUsuarios(String nome, String token) {
		JSONObject jsonObj = new JSONObject();		
		
		jsonObj.put("query", "{getUsuariosTopicos(input: {nome: \""+ nome +"\", token: \""+ token +"\"}) {cdRetorno dsRetorno Usuarios{Nome IPaddres Topicos{nome}}}}");
			
		return jsonObj;
	}
	
	public static UsuariosTopicosResponse montaDadosRetornoPesquisaUsuarios(Response apiResponse) {		
		JSONObject jsonRetorno = new JSONObject(apiResponse.readEntity(String.class));
		UsuariosTopicosResponse resp = new UsuariosTopicosResponse();		
		
		resp.setCdRetorno(jsonRetorno.getJSONObject("data").getJSONObject("getUsuariosTopicos").getInt("cdRetorno"));
		resp.setDsRetorno(jsonRetorno.getJSONObject("data").getJSONObject("getUsuariosTopicos").getString("dsRetorno"));

		if(resp.getCdRetorno() == 0) {
			for (int i = 0; i < jsonRetorno.getJSONObject("data").getJSONObject("getUsuariosTopicos").getJSONArray("Usuarios").length(); ++i) {
				JSONObject jsonUsuario = jsonRetorno.getJSONObject("data").getJSONObject("getUsuariosTopicos").getJSONArray("Usuarios").getJSONObject(i);
				for (int j = 0; j < jsonUsuario.getJSONArray("Topicos").length(); ++j) {
					JSONObject jsonTopicos = jsonUsuario.getJSONArray("Topicos").getJSONObject(j);
					Topico topico = new Topico();

					topico.setTopico(jsonTopicos.getString("nome"));
					topico.setIpAddres(jsonUsuario.getString("IPaddres"));
					topico.setStatus("Online");
					
					resp.getTopicos().add(topico);
				}
			}
		}		
		
		return resp;
	}
	
	/** Metodo para todos os reponses de mutation 
	 * 
	 * return cdRetorno dsRetorno token
	 * **/
	private static LoginResponse montaRetorno(JSONObject jsonRetorno, String metodo) {
		LoginResponse resp = new LoginResponse();
		
		resp.setCdRetorno(jsonRetorno.getJSONObject("data").getJSONObject(metodo).getInt("cdRetorno"));
		resp.setDsRetorno(jsonRetorno.getJSONObject("data").getJSONObject(metodo).getString("dsRetorno"));
		
		if(metodo.equals("login")) {
			if(resp.getCdRetorno() == 0) {
				resp.setToken(jsonRetorno.getJSONObject("data").getJSONObject(metodo).getString("token"));
			}
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
