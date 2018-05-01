package br.com.ufp.sd.utils;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ResponseUtil {

	private ResponseUtil() {

	}

	public static void throwApiExceptionIfAny(Response apiResponse) throws Exception {
		String erro = null;

		if (apiResponse.getStatus() == Status.UNAUTHORIZED.getStatusCode()) {
			erro = "Erro de autenticação/permissão ou limite de acessos excedido no período. Favor tentar novamente em alguns instantes: HTTP "
					+ apiResponse.getStatus() + " - ERRO: " + escape(apiResponse.readEntity(String.class));
		}

		if (apiResponse.getStatus() == Status.BAD_REQUEST.getStatusCode()
				|| apiResponse.getStatus() == Status.FORBIDDEN.getStatusCode()
				|| apiResponse.getStatus() == Status.NOT_FOUND.getStatusCode()) {
			erro = "Erro na invocação da API Soap: HTTP " + apiResponse.getStatus() + " - ERRO: "
					+ escape(apiResponse.readEntity(String.class));
		}

		if (apiResponse.getStatus() >= 500) {
			erro = "Erro na invocação da API Soap: HTTP " + apiResponse.getStatus() + " - ERRO: "
					+ escape(apiResponse.readEntity(String.class));
		}

		if (erro != null) {
			throw new Exception(erro);
		}
	}

	private static String escape(String content) {
		return content.replace("\n", " ");
	}

}
