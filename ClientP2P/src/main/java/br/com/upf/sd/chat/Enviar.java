package br.com.upf.sd.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.xml.bind.DatatypeConverter;

import br.com.upf.sd.Default;

public class Enviar {
	
    private Socket socket;
     
    public Enviar(Socket socket){    
        this.socket = socket;
       
    }
           
	public void run(String mensagem) {	
         
        try {
        	
        	String ipConexao = socket.getInetAddress().getHostAddress();
            System.out.println("\n\tConectado com "+ipConexao);
            System.out.println("Conectado! Digite seuas mensagens: \n");                                                
            
//            DataInputStream dIn = new DataInputStream(socket.getInputStream());
            DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());                                                             
    
            
        	//dOut.writeInt(ciphertextandnonce.length); // Escreve comprimento da mensagem            	
 		  //  dOut.write(ciphertextandnonce);	     	  // Envia mensagem
            
        } catch (Exception ex) {
            System.err.println("Erro em Enviar: "+ex);
        }  
    }                       
    
}
