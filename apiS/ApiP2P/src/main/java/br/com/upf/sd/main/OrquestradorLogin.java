package br.com.upf.sd.main;

import br.com.upf.sd.login.RecebeDadosLogin;
import br.com.upf.sd.types.Argumentos;

public class OrquestradorLogin extends Thread {
	
	private static final int COD_ERRO_TECNICO = -2;
	
	private boolean modoDebug = false;
	private int aesTamanho = 128;
	private int dhTamanho = 1024;
	private Argumentos argumentos;

	public OrquestradorLogin(Argumentos argumentos) {
		this.argumentos = argumentos;
	}
	
	public void run() {
		
		final int porta = argumentos.getPorta();	
		modoDebug = argumentos.getDebug();
		dhTamanho = argumentos.getDh() == null ? dhTamanho : argumentos.getDh();
		aesTamanho = argumentos.getAes() == null ? aesTamanho : argumentos.getAes();			
		
		try{
			
	        RecebeDadosLogin edl = new RecebeDadosLogin(modoDebug, porta);
	        new Thread(edl).start();

		} catch (Exception e) {
			System.err
					.println("Ocorreu um erro durante a execucao do Orquestrador: "
							+ e.getMessage());
			System.exit(COD_ERRO_TECNICO);
		}

	}		   
}
