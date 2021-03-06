package br.com.upf.sd.main;

import br.com.upf.sd.pesquisa.RecebeDadosPesquisaMobile;
import br.com.upf.sd.types.Argumentos;

public class OrquestradorPesquisa extends Thread {

private static final int COD_ERRO_TECNICO = -2;
	
	private boolean modoDebug = false;
	private Argumentos argumentos;

	public OrquestradorPesquisa(Argumentos argumentos) {
		this.argumentos = argumentos;
	}
	
	public void run() { 

		final int porta = argumentos.getPorta();	
		modoDebug = argumentos.getDebug();			
		
		try{
			while(true) {
				
				RecebeDadosPesquisaMobile rdp = new RecebeDadosPesquisaMobile();	
				rdp.getRecebeDados(porta, modoDebug);
				
			}
		} catch (Exception e) {
			System.err
			.println("Ocorreu um erro durante a execucao do Orquestrador: "
					+ e.getMessage());
			System.exit(COD_ERRO_TECNICO);
		}
	}
}
