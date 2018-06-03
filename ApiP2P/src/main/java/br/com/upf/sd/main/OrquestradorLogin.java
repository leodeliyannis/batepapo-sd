package br.com.upf.sd.main;

import java.security.KeyPair;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.DHParameterSpec;

import br.com.upf.sd.Default;
import br.com.upf.sd.enviaChavePub.EnviaDados;
import br.com.upf.sd.enviaChavePub.RecebeDados;
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
		
		final String modo = argumentos.getModo();
		final int porta = argumentos.getPorta();	
		modoDebug = argumentos.getDebug();
		dhTamanho = argumentos.getDh() == null ? dhTamanho : argumentos.getDh();
		aesTamanho = argumentos.getAes() == null ? aesTamanho : argumentos.getAes();			
		
		Default diffieHellman = new Default();
		try{
			while(true) {
			
				//Obtem IP da maquina	
				diffieHellman.meuIP();
				
				RecebeDados recebeDados = new RecebeDados();
				//recebe chave em formato codificado de Alice
				byte[] chavePubKeyEncRecebida = recebeDados.getRecebeDados(porta, modo, modoDebug);
				diffieHellman.printHex(modoDebug, "\nChave publica recebida:", chavePubKeyEncRecebida);
				
				//Bob instancia uma chave pública DH do material de chave codificada.
				//Bob recebe os parametros DH associados a chave pulica de Alice
				DHParameterSpec dhSkipParamSpec = diffieHellman.geradorParametrosDH(dhTamanho, modo, chavePubKeyEncRecebida);			
				//Bob usa os mesmos parametros para criar seu par de chaves
				KeyPair kPair = diffieHellman.criaParChave(dhSkipParamSpec);
				//Bob cria e inicializa o objeto Key de acordo DH
				KeyAgreement KeyAgree = diffieHellman.criaInicializaObjAcordo(kPair);
				
				// Bob codifica sua chave pública
		        byte[] bobPubKeyEnc = kPair.getPublic().getEncoded();
		        //diffieHellman.printHex(modoDebug, "\nChave publica criada:", bobPubKeyEnc);
		        
		        //envia para Alice
		        String ipRecebido = recebeDados.getIpConectado();		        
		        new EnviaDados(ipRecebido, porta, modoDebug, bobPubKeyEnc); 
		        
		        //Bob usa a chave pública de Alice para a primeira fase (e única) de sua versão do protocolo DH.
		        KeyAgree.doPhase(diffieHellman.getPubKey(), true);
		        
		        //Gera segredo compartilhado
		        byte[] segredoCompartilhado = KeyAgree.generateSecret();
		        //diffieHellman.printHex(modoDebug, "\nBob secret:", segredoCompartilhado);
		        
		        //Gera chave simetrica AES
		        SecretKey bobDesKey = diffieHellman.criaChaveSecreta(segredoCompartilhado, aesTamanho);                   	             	        	       
		       // diffieHellman.printHex(modoDebug, "\nChave Simetrica AES:", bobDesKey.getEncoded()); 
	
	        	//Conexão com outra ponta(Alice) para o login
		        RecebeDadosLogin edl = new RecebeDadosLogin(modoDebug, bobDesKey, ipRecebido, porta);
		        System.out.println("opa");
		        new Thread(edl).start();
				
			}

		} catch (Exception e) {
			System.err
					.println("Ocorreu um erro durante a execucao do Orquestrador: "
							+ e.getMessage());
			System.exit(COD_ERRO_TECNICO);
		}

	}		   
}
