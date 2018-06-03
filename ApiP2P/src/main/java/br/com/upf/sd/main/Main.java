package br.com.upf.sd.main;

import br.com.upf.sd.types.Argumentos;

public class Main {

	public static void main(String[] args) throws Exception{
		
		/** Inicia thread Login**/
		Argumentos argumentosLogin = new Argumentos();
		
		argumentosLogin.setModo("r");
		argumentosLogin.setPorta(10453);
		argumentosLogin.setDebug(true);
		argumentosLogin.setDh(1024);
		argumentosLogin.setAes(128);
		
		OrquestradorLogin orqLogin = new OrquestradorLogin(argumentosLogin);
		orqLogin.start();
		/** ********************** **/
		
		/** Inicia thread Pesquisas**/
		Argumentos argumentosPesquisa = new Argumentos();
		
		argumentosPesquisa.setModo("r");
		argumentosPesquisa.setPorta(10353);
		argumentosPesquisa.setDebug(true);
		
		OrquestradorPesquisa orqPesquisa = new OrquestradorPesquisa(argumentosPesquisa);
		orqPesquisa.start();
		/** ********************** **/

	}

}
