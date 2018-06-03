package br.com.upf.sd.login;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.xml.bind.DatatypeConverter;

import br.com.upf.sd.Default;
import br.com.upf.sd.types.Argumentos;
import br.com.upf.sd.types.Token;
import br.com.upf.sd.types.UsuarioInput;

public class EnviaDadosLogin implements Runnable {	
	
	private DatagramSocket clientSocket;
	private InetAddress IPAddress;	
	
	private UsuarioInput user;
	private Token token;
	private int porta;
	private boolean modoDebug;
	private SecretKey desKey;
	
	public EnviaDadosLogin(Argumentos argumentos, SecretKey desKey) throws SocketException, UnknownHostException {
		clientSocket = new DatagramSocket();
		IPAddress = InetAddress.getByName("localhost");
		this.user = argumentos.getUsuarioInput();
		this.token = argumentos.getToken();
		this.porta = argumentos.getPorta();
		this.modoDebug = argumentos.getDebug();
		this.desKey = desKey;
	}
	
	public String getToken() {
		return this.token.gettoken();
	}
	
	private void shutdown() {
		clientSocket.close();
	}
	
	public void run() {
		Default diffieHellman = new Default();
		
		synchronized(this) {
			
			byte[] dados = new byte[3072];
			byte[] mensagemRecebida;
		
			try {
				
				clientSocket.setSoTimeout(5000);
				
				byte[] encoded  = encoded(user, diffieHellman);            	
            	
            	//Envia dados login criptografados
            	DatagramPacket out = new DatagramPacket(encoded, encoded.length, IPAddress, porta);
				clientSocket.send(out);
				
				//Recebe token criptografados
				DatagramPacket receivedPacket = new DatagramPacket(dados, dados.length);
				clientSocket.receive(receivedPacket);
				
				mensagemRecebida = new byte[receivedPacket.getLength()];
		        System.arraycopy(receivedPacket.getData(), receivedPacket.getOffset(), mensagemRecebida, 0, receivedPacket.getLength());
				
				int length = receivedPacket.getLength();
				
				byte[] decoded = decoded(length, mensagemRecebida, diffieHellman);

				ByteArrayInputStream in = new ByteArrayInputStream(decoded);
				ObjectInputStream is = new ObjectInputStream(in);
				
				token = (Token) is.readObject();
				
				System.out.println("Token -> " + token.gettoken());

				notify();
				
			} catch (Exception e) {
				System.err.println("Exception Thrown: " + e);
			} finally {
				this.shutdown();
			}
		}
		
	}
	
	private byte[] encoded(UsuarioInput user, Default diffieHellman) throws NoSuchAlgorithmException, 
	                                                     NoSuchPaddingException, 
	                                                     InvalidKeyException, 
	                                                     InvalidAlgorithmParameterException, 
	                                                     IOException, IllegalBlockSizeException, BadPaddingException {
		byte[] cleartext;
		byte[] ciphertext;   
		byte[] ciphertextandnonce;
		
		IvParameterSpec nonce = new IvParameterSpec(iv());
		int ivLength = iv().length;

        Cipher aliceCipher = Cipher.getInstance("AES/CTR/PKCS5Padding");                                                                                                                                         
        aliceCipher.init(Cipher.ENCRYPT_MODE, this.desKey, nonce);                                         
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(outputStream);
		os.writeObject(user);
		
    	cleartext = outputStream.toByteArray();                	
    	String encoded = DatatypeConverter.printBase64Binary(cleartext);                   	
    	ciphertext = aliceCipher.doFinal(encoded.getBytes());
    	                
    	diffieHellman.printHex(modoDebug, "\nNonce codificado: ", nonce.getIV());                                                                        	                
    	diffieHellman.printHex(modoDebug, "\nMensagem SEM nonce:", ciphertext);                  	
    	
    	ciphertextandnonce = new byte[ciphertext.length + ivLength];
    	System.arraycopy(ciphertext, 0, ciphertextandnonce, 0, ciphertext.length);
    	System.arraycopy(iv(), 0, ciphertextandnonce, ciphertext.length, ivLength);
    	                             	                	                	
    	diffieHellman.printHex(modoDebug, "\nMensagem COM nonce: ", ciphertextandnonce);
    	System.out.println("--------------------------------------");
		
    	return ciphertextandnonce;
	}
	
	private byte[] decoded(int length, byte[] mensagem, Default diffieHellman) throws NoSuchAlgorithmException, 
																					    NoSuchPaddingException, 
																					    InvalidKeyException, 
																					    InvalidAlgorithmParameterException, 
																					    IllegalBlockSizeException, 
																					    BadPaddingException, UnsupportedEncodingException {
		
		IvParameterSpec nonce = new IvParameterSpec(iv());
		
		diffieHellman.printHex(modoDebug, "\nNonce codificado: ", nonce.getIV());			
		
		byte[] ciphertextandnonce = Arrays.copyOfRange(mensagem, 0, length-16);
		
		diffieHellman.printHex(modoDebug, "\nMensagem SEM nonce: ", ciphertextandnonce);	
		diffieHellman.printHex(modoDebug, "\nMensagem COM nonce: ", mensagem);
		
		Cipher aliceCipher = Cipher.getInstance("AES/CTR/PKCS5Padding");												                                
		aliceCipher.init(Cipher.DECRYPT_MODE, this.desKey, nonce);
		
		byte[] recovered = aliceCipher.doFinal(ciphertextandnonce);
		String value = new String(recovered, "UTF-8");  
		
		return DatatypeConverter.parseBase64Binary(value);
	}
	
	private byte[] iv() {
		//vetor iv fixo para modo de bloco CTR fixo
	    byte[] nonceBytes = new byte[] { 
	    	  (byte) 0x69, (byte) 0xdd, (byte) 0xa8, (byte) 0x45, 
	    	  (byte) 0x5c, (byte) 0x7d, (byte) 0xd4, (byte) 0x25, 
	      	  (byte) 0x4b, (byte) 0xf3, (byte) 0x53, (byte) 0xb7, 
	      	  (byte) 0x73, (byte) 0x30, (byte) 0x4e, (byte) 0xec }; //16 byts 
	    
	    return nonceBytes;
	}
}
