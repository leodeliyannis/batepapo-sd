package br.com.upf.sd.pesquisa;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import br.com.upf.sd.resources.ApiBanco;
import br.com.upf.sd.types.Pesquisa;
import br.com.upf.sd.types.TopicosUsuariosResponse;

public class RecebeDadosPesquisa {	
	
	private ServerSocket servidor;        
    private Socket cliente;
    private DataInputStream dIn;
    private DataOutputStream dOut;
    private String ipConectado;

	public RecebeDadosPesquisa(){}
	
	public String getIpConectado() {
		return this.ipConectado;
	}
    
    public void getRecebeDados(int porta, boolean modoDebug) {
    	recebe(porta, modoDebug);
    }

    private void recebe(int porta, boolean modoDebug) {   
    	 byte[] message = null;
    	 
    	 porta += 1;
    	
    	 try {      		
	        servidor = new ServerSocket(porta);
	        System.out.println("Aguardando conexao...");

	        
	        if (modoDebug)
	        	System.out.println("\nPorta "+porta+" aberta!");
	        
	        cliente = servidor.accept();
	        this.ipConectado = cliente.getInetAddress().getHostAddress();
        	System.out.println("Nova conexao com " + ipConectado);
        	
        	dIn = new DataInputStream(cliente.getInputStream());

        	int length = dIn.readInt(); //ler comprimento da mensagem recebida
        	if(length>0) {
        	    message = new byte[length];
        	    dIn.readFully(message, 0, message.length); //ler a mensagem
        	}
        	
        	ByteArrayInputStream in = new ByteArrayInputStream(message);
			ObjectInputStream is = new ObjectInputStream(in);
        	
			Pesquisa pesquisa = (Pesquisa) is.readObject();
			
			
			if(pesquisa.getMetodo().equals("pesquisa_topico")) {
				
				ApiBanco api = new ApiBanco();
				TopicosUsuariosResponse topicosUser = api.pesquisaTopicos(pesquisa.getNome(), pesquisa.getToken());
                
				//Monta envio para client
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    	 		ObjectOutputStream os = new ObjectOutputStream(outputStream);
    	 		os.writeObject(topicosUser);
    			    	 		
    	    	//String encoded = DatatypeConverter.printBase64Binary(outputStream.toByteArray());
    	    	dOut = new DataOutputStream(cliente.getOutputStream());

    		    dOut.writeInt(outputStream.toByteArray().length); // Escreve comprimento da mensagem
    		    dOut.write(outputStream.toByteArray());           // Envia mensagem
    		    os.flush();
				
			} else if(pesquisa.getMetodo().equals("pesquisa_usuario")) { 
				System.out.println("Ainda não implementado");
			}
			
           
        } catch (IOException e) {
            System.err.println("Erro no RecebeDados: "+e);
           
        } catch (ClassNotFoundException e) {
        	System.err.println("Erro no RecebeDados: "+e);
        	
		} catch (Exception e) {
			System.err.println("Erro no RecebeDados: "+e);
			
		} finally {        	
        	try {
        		//Finaliza objetos
        		if(!cliente.isClosed()) {
        			//cliente.shutdownOutput();
        			cliente.close();	
        		}
        		if(!servidor.isClosed()) {        			
        			servidor.close();	
        		}
			} catch (IOException e) {
				System.err.println("Erro ao fechar as conexões: "+e);
			}
        	 
        }		
		                  
    }
}
