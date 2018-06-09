package br.com.upf.sd.querys;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.ws.rs.core.Response;

import org.json.JSONObject;

import br.com.upf.sd.types.LoginRequest;
import br.com.upf.sd.types.LoginResponse;
import br.com.upf.sd.types.QuantidadeChatsRealizadosRequest;
import br.com.upf.sd.types.QuantidadeChatsUsuarioRequest;
import br.com.upf.sd.types.QuantidadeResponse;
import br.com.upf.sd.types.QuantidadeUsuarioLoginRequest;
import br.com.upf.sd.types.TopicosMaisAcessadosRequest;
import br.com.upf.sd.types.TopicosMaisAcessadosResponse;
import br.com.upf.sd.utils.Security;

public class UsuariosJsonUtil {

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
		
		jsonObj.put("query", "{login(input: {Nome: \""+request.getNome()+"\", Senha: \""+senha+"\", IPaddres: \""+ request.getIpAddres()+"\"}) {cdRetorno dsRetorno token}}");
		
		return jsonObj;
	}
	
	public static LoginResponse montaDadosRetornoLogin(Response apiResponse) {
		JSONObject jsonRetorno = new JSONObject(apiResponse.readEntity(String.class));
		
		return montaRetornoLogin(jsonRetorno, "login");
	}
	
	/** EstatisticaQtdUserAcesso
	 * 
	 * @param request
	 * @return cdRetorno dsRetorno valor
	 */
	public static JSONObject montaJsonRequestQuantidadesUsuariosLoginsValidos(QuantidadeUsuarioLoginRequest request) {
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("query", "{estatisticaQuantidadesUsuariosLoginsValidos(input: {token: \""+request.getAutenticacao().getToken()+"\", inicio: \""+request.getDatas().getDataHoraInicio()+"\", fim: \""+request.getDatas().getDataHoraFim()+"\"}) {cdRetorno dsRetorno valor}}");
		
		return jsonObj;
	}
	
	public static QuantidadeResponse montaDadosRetornoQuantidadesUsuariosLoginsValidos(Response apiResponse) {
		JSONObject jsonRetorno = new JSONObject(apiResponse.readEntity(String.class));
		
		return montaRetorno(jsonRetorno, "estatisticaQuantidadesUsuariosLoginsValidos");
	}
	
	/** EstatisticaQuantidadesTopicosMaisAcessados
	 * 
	 * @param request
	 * @return
	 */
	public static JSONObject montaJsonRequestQuantidadesTopicosMaisAcessados(TopicosMaisAcessadosRequest request) {
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("query", "{estatisticaQuantidadesTopicosMaisAcessados(input: {token: \""+request.getAutenticacao().getToken()+"\", inicio: \""+request.getDatas().getDataHoraInicio()+"\", fim: \""+request.getDatas().getDataHoraFim()+"\"}) {cdRetorno dsRetorno valor}}");
		
		return jsonObj;
	}
	
	public static TopicosMaisAcessadosResponse montaDadosRetornoQuantidadesTopicosMaisAcessados(Response apiResponse) {
		JSONObject jsonRetorno = new JSONObject(apiResponse.readEntity(String.class));
		TopicosMaisAcessadosResponse resp = new TopicosMaisAcessadosResponse();
		
		resp.setCdRetorno(jsonRetorno.getJSONObject("data").getJSONObject("estatisticaQuantidadesTopicosMaisAcessados").getInt("cdRetorno"));
		resp.setDsRetorno(jsonRetorno.getJSONObject("data").getJSONObject("estatisticaQuantidadesTopicosMaisAcessados").getString("dsRetorno"));
		
		/**
		 * TO DO - Montar dados de retorno
		 */
		
		return resp;
	}
	
	/** EstatisticaQtdChatsIniciado
	 * 
	 * @param request
	 * @return cdRetorno dsRetorno valor
	 */
	public static JSONObject montaJsonRequestQuantidadesChatsRealizados(QuantidadeChatsRealizadosRequest request) {
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("query", "{estatisticaQuantidadesChatsRealizados(input: {token: \""+request.getAutenticacao().getToken()+"\", inicio: \""+request.getDatas().getDataHoraInicio()+"\", fim: \""+request.getDatas().getDataHoraFim()+"\"}) {cdRetorno dsRetorno valor}}");
		
		return jsonObj;
	}
	
	public static QuantidadeResponse montaDadosRetornoQuantidadesChatsRealizados(Response apiResponse) {
		JSONObject jsonRetorno = new JSONObject(apiResponse.readEntity(String.class));
		
		return montaRetorno(jsonRetorno, "estatisticaQuantidadesChatsRealizados");
	}
	
	/** EstatisticaUsuarioChatsIniciado
	 * 
	 * @param request
	 * @return cdRetorno dsRetorno valor
	 */
	public static JSONObject montaJsonRequestQuantidadesChatsRealizadosUsuarios(QuantidadeChatsUsuarioRequest request) {
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("query", "{estatisticaQuantidadesChatsRealizadosUsuarios(input: {token: \""+request.getAutenticacao().getToken()+"\", usuario: \""+request.getAutenticacao().getToken()+"\", inicio: \""+request.getDatas().getDataHoraInicio()+"\", fim: \""+request.getDatas().getDataHoraFim()+"\"}) {cdRetorno dsRetorno valor}}");
		
		return jsonObj;
	}
	
	public static QuantidadeResponse montaDadosRetornoQuantidadesChatsRealizadosUsuarios(Response apiResponse) {
		JSONObject jsonRetorno = new JSONObject(apiResponse.readEntity(String.class));
		
		return montaRetorno(jsonRetorno, "estatisticaQuantidadesChatsRealizadosUsuarios");
	}
	
	
	/** Metodo para todos os reponses de mutation 
	 * 
	 * return cdRetorno dsRetorno
	 * **/
	private static QuantidadeResponse montaRetorno(JSONObject jsonRetorno, String metodo) {
		QuantidadeResponse resp = new QuantidadeResponse();
		
		resp.setCdRetorno(jsonRetorno.getJSONObject("data").getJSONObject(metodo).getInt("cdRetorno"));
		resp.setDsRetorno(jsonRetorno.getJSONObject("data").getJSONObject(metodo).getString("dsRetorno"));
		resp.setQuantidade(Integer.parseInt(jsonRetorno.getJSONObject("data").getJSONObject(metodo).getString("valor")));
		
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
}
