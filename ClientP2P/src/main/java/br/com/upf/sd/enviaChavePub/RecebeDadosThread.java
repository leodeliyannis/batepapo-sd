package br.com.upf.sd.enviaChavePub;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.bind.DatatypeConverter;

public class RecebeDadosThread extends Thread { 
	
    private ServerSocket servidor;        
    private Socket cliente;
    private DataInputStream dIn;
    private String ipConectado;
    
    private int porta;
    private String modo;
    private boolean modoDebug;
    
    private byte[] key;
	
	public RecebeDadosThread(int porta, String modo, boolean modoDebug) {
    	this.porta = porta;
    	this.modo = modo;
    	this.modoDebug = modoDebug;
    }
	
	public String getIpConectado() {
		return this.ipConectado;
	}
    
    public byte[] getKey() {
    	return key;
    }

    public void run() {   
    	 byte[] message = null;
    	 
    	 //porta += 1;
    	
    	 try {
    		 
    		synchronized(this){
    		 
	    		if (modo.equals("r")) {
	 	        	System.out.println("\nAguardando conexao...");
	 	        } else{
	         		System.out.println("Conectando...");
	 	        }
	    		 
		        servidor = new ServerSocket(porta);
		        servidor.setSoTimeout(86400000);
		        	        
		        if (modoDebug)
		        	System.out.println("\nPorta "+porta+" aberta!");   	        	       
		        
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
	        	key = DatatypeConverter.parseBase64Binary(value);
	            
	            notify();
    		}
        } catch (IOException e) {
            System.err.println("Erro no RecebeDadosThread: "+e);
            
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
