package br.com.upf.sd.chat;

import java.io.DataOutputStream;
import java.net.Socket;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.xml.bind.DatatypeConverter;

import br.com.upf.sd.Default;

public class Enviar {
	
    private SecretKey desKey;    
    private boolean modoDebug;
    private int count;
    private Socket socket;
    private Default diffieHellman;
     
    public Enviar(Socket socket, int count, boolean modoDebug, SecretKey desKey, Default diffieHellman){    
        this.desKey = desKey;      
        this.modoDebug = modoDebug;
        this.socket = socket;
        this.count = count;
        this.diffieHellman = diffieHellman;
    }
           
	public void run(String mensagem) {	
         
        try {
        	
        	String ipConexao = socket.getInetAddress().getHostAddress();
            System.out.println("\n\tConectado com "+ipConexao);
            System.out.println("Conectado! Digite seuas mensagens: \n");                                                
            
//            DataInputStream dIn = new DataInputStream(socket.getInputStream());
            DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());                                                             
    
            byte[] cleartext;
            byte[] ciphertext;   
            byte[] ciphertextandnonce;
            
            // thread para receber mensagens
//            Receber r = new Receber(true, dIn, desKey, ipConexao, null, null);
//            new Thread(r).start();  

            SecureRandom random = new SecureRandom();
            byte[] nonceBytes = new byte[16];
            random.nextBytes(nonceBytes);
            byte[] nonceBytesToCrypt = new byte[16];
            System.arraycopy(nonceBytes, 0, nonceBytesToCrypt, 0, 15);
                               
            diffieHellman.printHex(modoDebug, "\nNonce original e enviado: ", nonceBytes);
            
            count++;
            //XOR entre count e 10° elemento da chave privada
            nonceBytesToCrypt[15] = (byte) ((byte) (count & 0xff) ^ desKey.getEncoded()[10]);
            IvParameterSpec nonce = new IvParameterSpec(nonceBytesToCrypt);                                  
                                  
            Cipher aliceCipher = Cipher.getInstance("AES/CTR/PKCS5Padding");                                                                                                                                         
            aliceCipher.init(Cipher.ENCRYPT_MODE, desKey, nonce);                                         
            
        	cleartext = mensagem.getBytes("UTF-8");                	
        	String encoded = DatatypeConverter.printBase64Binary(cleartext);                   	
        	ciphertext = aliceCipher.doFinal(encoded.getBytes());
        	                
        	diffieHellman.printHex(modoDebug, "\nNonce codificado: ", nonce.getIV());                                                                        	                
        	diffieHellman.printHex(modoDebug, "\nMensagem SEM nonce:", ciphertext);                  	
        	
        	ciphertextandnonce = new byte[ciphertext.length + nonceBytes.length];
        	System.arraycopy(ciphertext, 0, ciphertextandnonce, 0, ciphertext.length);
        	System.arraycopy(nonceBytes, 0, ciphertextandnonce, ciphertext.length, nonceBytes.length);
        	                             	                	                	
        	diffieHellman.printHex(modoDebug, "\nMensagem COM nonce: ", ciphertextandnonce);                	
        	System.out.println("--------------------------------------");
        	
        	dOut.writeInt(ciphertextandnonce.length); // Escreve comprimento da mensagem            	
 		    dOut.write(ciphertextandnonce);	     	  // Envia mensagem
            
        } catch (Exception ex) {
            System.err.println("Erro em Enviar: "+ex);
        }  
    }                       
    
}
