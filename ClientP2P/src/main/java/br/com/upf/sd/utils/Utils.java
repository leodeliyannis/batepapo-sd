package br.com.upf.sd.utils;

public class Utils {
	
	/**
	 * Se no campo estiver null(objeto ou literal) retorna o valor especificado em retorno 
	 * @param valor Valor a ser verificado 
	 * @param retorno Valor a ser retornado
	 * @return String
	 */
    public static String nvl(String valor, String retorno) {
        return (valor != null && valor.length() > 0 && !valor.equalsIgnoreCase("null")) ? valor : retorno;
    }

    /**
	 * Se no campo estiver null(objeto ou literal) retorna ""
	 * @param valor Valor a ser verificado 
	 * @return String
	 */
    public static String nvl(final String valor) {
        return nvl(valor, "");
    }

}
