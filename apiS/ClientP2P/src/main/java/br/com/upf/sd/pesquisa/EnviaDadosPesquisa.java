package br.com.upf.sd.pesquisa;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import br.com.upf.sd.types.Pesquisa;
import br.com.upf.sd.types.TopicosUsuariosResponse;

public class EnviaDadosPesquisa {
	
	private DataInputStream dIn;
	private DataOutputStream dOut;

	public EnviaDadosPesquisa() {
		
	}
	
	public TopicosUsuariosResponse getEnviaDadosPesquisa(String ip, int port, boolean modoDebug, Pesquisa valores) {
		return envia(ip, port, modoDebug, valores);
	}

	//Envia por TCP
	private TopicosUsuariosResponse envia(String ip, int port, boolean modoDebug, Pesquisa valores) {
	    Socket socket = null;
	    byte[] message = null;
	    
	    port += 1;
	    
	    try {
	    	socket = new Socket(ip, port);
	    	if (modoDebug)
				System.out.println("\nEnviando Dados...");
	    	
	    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	 		ObjectOutputStream os = new ObjectOutputStream(outputStream);
	 		os.writeObject(valores);
			
	    	//String encoded = DatatypeConverter.printBase64Binary(outputStream.toByteArray());
	    	dOut = new DataOutputStream(socket.getOutputStream());

		    dOut.writeInt(outputStream.toByteArray().length); // Escreve comprimento da mensagem
		    dOut.write(outputStream.toByteArray());           // Envia mensagem
		    
		    if (modoDebug)
		    	System.out.println("Dados Enviado!");
            
		    //Recebe do servidor
            dIn = new DataInputStream(socket.getInputStream());

        	int length = dIn.readInt(); //ler comprimento da mensagem recebida
        	if(length>0) {
        	    message = new byte[length];
        	    dIn.readFully(message, 0, message.length); //ler a mensagem
        	}
        	
        	ByteArrayInputStream in = new ByteArrayInputStream(message);
			ObjectInputStream is = new ObjectInputStream(in);
			
			TopicosUsuariosResponse topicosUsuario = (TopicosUsuariosResponse) is.readObject();
			return topicosUsuario;
			
	    } catch(Exception ex ){
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
		return null;
	}
}
