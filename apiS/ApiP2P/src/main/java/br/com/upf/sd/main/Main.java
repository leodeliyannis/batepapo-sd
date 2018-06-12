package br.com.upf.sd.main;

import br.com.upf.sd.types.Argumentos;

public class Main {

	public static void main(String[] args) throws Exception{
		
		/** Inicia thread Login Sem Criptografia**/
		Argumentos argumentosLogin = new Argumentos();
		
		argumentosLogin.setModo("r");
		argumentosLogin.setPorta(10553);
		argumentosLogin.setDebug(true);
		argumentosLogin.setDh(1024);
		argumentosLogin.setAes(128);
		
		OrquestradorLogin orqLogin = new OrquestradorLogin(argumentosLogin);
		orqLogin.start();
		/** ********************** **/
		
		/** Inicia thread Login Com Criptografia**/
		Argumentos argumentosLoginCrypt = new Argumentos();
		
		argumentosLoginCrypt.setModo("r");
		argumentosLoginCrypt.setPorta(10453);
		argumentosLoginCrypt.setDebug(true);
		argumentosLoginCrypt.setDh(1024);
		argumentosLoginCrypt.setAes(128);
		
		OrquestradorLoginCrypt orqLoginCrypt = new OrquestradorLoginCrypt(argumentosLoginCrypt);
		orqLoginCrypt.start();
		/** ********************** **/
		
		/** Inicia thread Pesquisas**/
		Argumentos argumentosPesquisa = new Argumentos();
		
		argumentosPesquisa.setModo("r");
		argumentosPesquisa.setPorta(10253);
		argumentosPesquisa.setDebug(true);
		
		OrquestradorPesquisa orqPesquisa = new OrquestradorPesquisa(argumentosPesquisa);
		orqPesquisa.start();
		/** ********************** **/
		
		Argumentos argumentosPesquisaCrypt = new Argumentos();
		
		argumentosPesquisa.setModo("r");
		argumentosPesquisa.setPorta(10353);
		argumentosPesquisa.setDebug(true);
		
		OrquestradorPesquisaCrypt orqPesquisaCrypt = new OrquestradorPesquisaCrypt(argumentosPesquisaCrypt);
		orqPesquisa.start();
		/** ********************** **/

	}

}
