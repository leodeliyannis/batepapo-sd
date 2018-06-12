package br.com.upf.sd.main;

import br.com.upf.sd.pesquisa.RecebeDadosPesquisa;
import br.com.upf.sd.types.Argumentos;

public class OrquestradorPesquisaCrypt extends Thread {

private static final int COD_ERRO_TECNICO = -2;
	
	private boolean modoDebug = false;
	private Argumentos argumentos;

	public OrquestradorPesquisaCrypt(Argumentos argumentos) {
		this.argumentos = argumentos;
	}
	
	public void run() { 

		final int porta = argumentos.getPorta();	
		modoDebug = argumentos.getDebug();			
		
		try{
			while(true) {
				
				RecebeDadosPesquisa rdp = new RecebeDadosPesquisa();	
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
