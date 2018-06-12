package br.com.upf.sd.main;

import java.security.KeyPair;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.DHParameterSpec;

import br.com.upf.sd.Default;
import br.com.upf.sd.enviaChavePub.server.EnviaDados;
import br.com.upf.sd.enviaChavePub.server.RecebeDados;
import br.com.upf.sd.exception.InteracaoException;
import br.com.upf.sd.login.EnviaDadosLogin;
import br.com.upf.sd.types.Argumentos;

public class OrquestradorLogin {
	
	private static final int COD_ERRO_TECNICO = -2;
	
	private boolean modoDebug = false;
	private int aesTamanho = 128;
	private int dhTamanho = 1024;

	public OrquestradorLogin() {}
	
	
	public EnviaDadosLogin run(Argumentos argumentos) throws Exception {
		
		final String modo = argumentos.getModo();
		final String ip = argumentos.getIp();
		final int porta = argumentos.getPorta();	
		modoDebug = argumentos.getDebug();
		dhTamanho = argumentos.getDh() == null ? dhTamanho : argumentos.getDh();
		aesTamanho = argumentos.getAes() == null ? aesTamanho : argumentos.getAes();			
		
		Default diffieHellman = new Default();
		try{		
			
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
	        new EnviaDados(ip, porta, modoDebug, pubKeyEnc, "LOGIN");
	        
	        //Alice recebe chave publica de Bob
	        byte[] chavePubKeyEncRecebida = new RecebeDados().getRecebeDados(porta, modo, modoDebug);	        	       
	        diffieHellman.printHex(modoDebug, "\nChave publica recebida:", chavePubKeyEncRecebida);
	        		        
	        //Alice usa a chave pública de Bob para a primeira fase (e única) de sua versão do protocolo DH.
			//Antes que ela possa fazê-lo, ela tem que instanciar uma chave pública DH de material de chave codificada de Bob.
	        //Gera segredo compartilhado
	        byte[] segredoCompartilhado = diffieHellman.segredoCompartilhado(chavePubKeyEncRecebida, KeyAgree);	        	         
	        diffieHellman.printHex(modoDebug, "\nAlice secret:", segredoCompartilhado);
	        
	        //Gera chave simetrica AES
	        SecretKey aliceDesKey = diffieHellman.criaChaveSecreta(segredoCompartilhado, aesTamanho);
	        diffieHellman.printHex(modoDebug, "\nChave Simetrica AES:", aliceDesKey.getEncoded());

        	//Conexão com outra ponta(Bob) para o login
	        EnviaDadosLogin edl = new EnviaDadosLogin(argumentos, aliceDesKey);
			new Thread(edl).start();
			
			return edl;
		} catch (InteracaoException e) {
			System.err
					.println("Ocorreu um erro durante a execucao do Orquestrador: "
							+ e.getMessage());
			System.exit(COD_ERRO_TECNICO);
		}
		return null;
	}		   
}
