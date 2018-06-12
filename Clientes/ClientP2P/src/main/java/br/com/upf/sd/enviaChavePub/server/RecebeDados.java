package br.com.upf.sd.enviaChavePub.server;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.bind.DatatypeConverter;

public class RecebeDados { 
	
    private ServerSocket servidor;        
    private Socket cliente;
    private DataInputStream dIn;
    private String ipConectado;

	public RecebeDados(){}
	
	public void stopSocket() {
		try {
			if(servidor != null) {
				servidor.close();
			}if(cliente != null) {
				cliente.close();
			}
			
		} catch (Exception e) {
			System.out.println("Erro ao fechar os sockets server");
		}
		
	}
	
	public String getIpConectado() {
		return this.ipConectado;
	}
    
    public byte[] getRecebeDados(int porta, String modo, boolean modoDebug) {
    	return recebe(porta, modo, modoDebug);
    }

	private byte[] recebe(int porta, String modo, boolean modoDebug) {   
    	 byte[] message = null;
    	 
//    	 porta += 1;
    	
    	 try {
    		 
        	System.out.println("Aguardando conexao...");
 	        
	        servidor = new ServerSocket(porta);
	        servidor.setSoTimeout(86400000);
	        	        
	        if (modoDebug)
	        	System.out.println("Porta "+porta+" aberta!");   	        	       
	        
	        cliente = servidor.accept();	   
	        this.ipConectado = cliente.getInetAddress().getHostAddress();
        	System.out.println("Nova conexao com " + ipConectado);
        	
        	dIn = new DataInputStream(cliente.getInputStream());        	        	

        	int length = dIn.readInt(); //ler comprimento da mensagem recebida
        	if(length > 0) {
        	    message = new byte[length];
        	    dIn.readFully(message, 0, message.length); //ler a mensagem
        	} 
        	
        	String value = new String(message);                                                          	 	   	 	   
            return DatatypeConverter.parseBase64Binary(value);
        } catch (Exception e) {
            System.err.println("Erro no RecebeDados server: "+e);
            return null;
            
        } finally {        	
        	stopSocket();
        	 
        }
		                  
    }       
}
