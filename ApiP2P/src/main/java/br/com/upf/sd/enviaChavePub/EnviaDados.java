package br.com.upf.sd.enviaChavePub;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.xml.bind.DatatypeConverter;

public class EnviaDados {	
	
	private DataOutputStream dOut;

	public EnviaDados(String ip, int port, boolean modoDebug, byte[] valores) {
		envia(ip, port, modoDebug, valores);
	}

	//Envia por TCP
	private void envia(String ip, int port, boolean modoDebug, byte[] valores) {
	    Socket socket = null;
	    
	    //port += 1;
	    
	    try {
	    	socket = new Socket(ip, port);
	    	if (modoDebug)
				System.out.println("\nEnviando Dados...");
			
	    	String encoded = DatatypeConverter.printBase64Binary(valores);
	    	dOut = new DataOutputStream(socket.getOutputStream());

		    dOut.writeInt(encoded.getBytes().length); // Escreve comprimento da mensagem
		    dOut.write(encoded.getBytes());           // Envia mensagem
		    
		    if (modoDebug)
		    	System.out.println("Dados Enviado!");	
			
	    } catch(IOException ex ){
	    	System.err.println("Erro no EnviaDados: "+ex);
	    } finally {
        	//Finaliza objetos
        	try {
        		//Finaliza objetos
        		if(!socket.isClosed()) {
        			//socket.shutdownOutput();
    				socket.close();	
        		}
			} catch (IOException e) {
				System.err.println("Erro ao fechar as conexões: "+e);
			}
        	 
        }
	}
}
