package br.com.upf.sd.main;

import java.security.KeyPair;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.DHParameterSpec;

import br.com.upf.sd.Default;
import br.com.upf.sd.enviaChavePub.chat.EnviaDados;
import br.com.upf.sd.enviaChavePub.chat.RecebeDados;
import br.com.upf.sd.types.Argumentos;

public class GeradorChaveCryptChat {
	
	private static final int COD_ERRO_TECNICO = -2;
	
	private boolean modoDebug = false;
	private int aesTamanho = 128;
	private int dhTamanho = 1024;
	private Argumentos argumentos;
	
	private String conecta;
	private String recebe;
	
	private RecebeDados recebeDados;

	public GeradorChaveCryptChat(Argumentos argumentos, String conecta, String recebe) {
		this.argumentos = argumentos;
		this.conecta = conecta;
		this.recebe = recebe;
	}
	
	public void stopSocket() {
		recebeDados.stopSocket();
	}
	
	/*
	 * Este programa executa o protocolo de acordo de chave Diffie-Hellman entre 2 partes: 
	 * Alice e Bob.
	 * Por padrão, são usados parâmetros pré-configurados 
	 * (módulo e base de geradores primo de 1024 bits usado por SKIP).
	 * Também inicia um chat usando criptografia AES utilizando a chave gerada por Diffie-Hellman
	 * Por padrão, são usados parâmetros pré-configurados~
	 * (Criptografia AES 128 bits)
	 */
	public SecretKey run() {
		
		final String modo = argumentos.getModo();
		final String ip = argumentos.getIp();
//		final int porta = argumentos.getPorta();	
		modoDebug = argumentos.getDebug();
		dhTamanho = argumentos.getDh() == null ? dhTamanho : argumentos.getDh();
		aesTamanho = argumentos.getAes() == null ? aesTamanho : argumentos.getAes();
		
		Default diffieHellman = new Default();
		try{		
			if(modo.equals("r")) {
				//Obtem IP da maquina		
				diffieHellman.meuIP();
				
				recebeDados = new RecebeDados();
				//recebe chave em formato codificado de Alice
				byte[] chavePubKeyEncRecebida = recebeDados.getRecebeDados(Integer.parseInt(recebe), modo, modoDebug);
				diffieHellman.printHex(modoDebug, "\nChave publica recebida:", chavePubKeyEncRecebida);
				
				if(chavePubKeyEncRecebida == null) {
					return null;
				}
					
				//Bob instancia uma chave pública DH do material de chave codificada.
				//Bob recebe os parametros DH associados a chave pulica de Alice
				DHParameterSpec dhSkipParamSpec = diffieHellman.geradorParametrosDH(dhTamanho, modo, chavePubKeyEncRecebida);				
				//Bob usa os mesmos parametros para criar seu par de chaves
				KeyPair kPair = diffieHellman.criaParChave(dhSkipParamSpec);				
				//Bob cria e inicializa o objeto Key de acordo DH
				KeyAgreement KeyAgree = diffieHellman.criaInicializaObjAcordo(kPair);
				
				// Bob codifica sua chave pública
		        byte[] bobPubKeyEnc = kPair.getPublic().getEncoded();
		        diffieHellman.printHex(modoDebug, "\nChave publica criada:", bobPubKeyEnc);
		        
		        //envia para Alice
		        String ipConectado = recebeDados.getIpConectado();
		        new EnviaDados(ipConectado, Integer.parseInt(conecta), modoDebug, bobPubKeyEnc, ""); 
		        
		        //Bob usa a chave pública de Alice para a primeira fase (e única) de sua versão do protocolo DH.
		        KeyAgree.doPhase(diffieHellman.getPubKey(), true);
		        
		        //Gera segredo compartilhado
		        byte[] segredoCompartilhado = KeyAgree.generateSecret();
		        diffieHellman.printHex(modoDebug, "\nBob secret:", segredoCompartilhado);
		        
		        //Gera chave simetrica AES
		        SecretKey bobDesKey = diffieHellman.criaChaveSecreta(segredoCompartilhado, aesTamanho);                   	             	        	       
		        diffieHellman.printHex(modoDebug, "\nChave Simetrica AES:", bobDesKey.getEncoded()); 
		        
		        return bobDesKey;
		        
		        //notify();

			} else if(modo.equals("c")) {
				//Cria parametros DH
				DHParameterSpec dhSkipParamSpec = diffieHellman.geradorParametrosDH(dhTamanho, modo, null);
				//Alice cria par de chaves usando os parametros gerados a cima
				KeyPair kPair = diffieHellman.criaParChave(dhSkipParamSpec);
				//Alice cria e inicialiaza o objeto de acordo
				KeyAgreement KeyAgree = diffieHellman.criaInicializaObjAcordo(kPair);
							
		        // Alice codifica sua chave pública	
		        byte[] pubKeyEnc = kPair.getPublic().getEncoded();	        	    
		        diffieHellman.printHex(modoDebug, "\nChave publica criada:", pubKeyEnc);	        
		        
		        //envia para Bob
		        new EnviaDados(ip, Integer.parseInt(conecta), modoDebug, pubKeyEnc, "");
		        
		        //Alice recebe chave publica de Bob
		        byte[] chavePubKeyEncRecebida = new RecebeDados().getRecebeDados(Integer.parseInt(recebe), modo, modoDebug);	        	       
		        diffieHellman.printHex(modoDebug, "\nChave publica recebida:", chavePubKeyEncRecebida);
		        
		        if(chavePubKeyEncRecebida == null) {
					return null;
				}
		        		        
		        //Alice usa a chave pública de Bob para a primeira fase (e única) de sua versão do protocolo DH.
				//Antes que ela possa fazê-lo, ela tem que instanciar uma chave pública DH de material de chave codificada de Bob.
		        //Gera segredo compartilhado
		        byte[] segredoCompartilhado = diffieHellman.segredoCompartilhado(chavePubKeyEncRecebida, KeyAgree);	        	         
		        diffieHellman.printHex(modoDebug, "\nAlice secret:", segredoCompartilhado);
		        
		        //Gera chave simetrica AES
		        SecretKey aliceDesKey = diffieHellman.criaChaveSecreta(segredoCompartilhado, aesTamanho);
		        diffieHellman.printHex(modoDebug, "\nChave Simetrica AES:", aliceDesKey.getEncoded()); 
		        
		        return aliceDesKey;	
		        
		        //notify();
			
			} 
		} catch (Exception e) {
			System.err
					.println("Ocorreu um erro durante a execucao do Orquestrador Gerador Chaves: "
							+ e);
			System.exit(COD_ERRO_TECNICO);
		}
		return null;
	}	   
}
