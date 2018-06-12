package br.com.upf.sd.enviaChavePub.chat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.xml.bind.DatatypeConverter;

public class EnviaDados {	
	
	private DataOutputStream dOut;

	public EnviaDados(String ip, int port, boolean modoDebug, byte[] valores, String etapa) {
		envia(ip, port, modoDebug, valores, etapa);
	}

	//Envia por TCP
	private void envia(String ip, int porta, boolean modoDebug, byte[] valores, String etapa) {
	    Socket socket = null;
	    
	    porta += 5;
	    
	    System.out.println("\nTentando conexao em "+ip+" na porta "+porta);	    
	    
	    try {
	    	socket = new Socket(ip, porta);
	    	if (modoDebug)
				System.out.println("Enviando Dados...");
			
	    	String encoded = DatatypeConverter.printBase64Binary(valores);
	    	dOut = new DataOutputStream(socket.getOutputStream());

		    dOut.writeInt(encoded.getBytes().length); // Escreve comprimento da mensagem
		    dOut.write(encoded.getBytes());           // Envia mensagem
		    
		    if (modoDebug)
		    	System.out.println("Dados Enviado!");	

	    } catch(IOException ex ){
	    	System.err.println("Erro no EnviaDados chat: "+ex);
	    } finally {
        	//Finaliza objetos
        	try {
        		//Finaliza objetos
        		if(socket != null) {
        			socket.shutdownOutput();
    				socket.close();	
        		}
			} catch (IOException e) {
				System.err.println("Erro ao fechar as conexões envia chat: "+e);
			}
        	 
        }
	}
}
