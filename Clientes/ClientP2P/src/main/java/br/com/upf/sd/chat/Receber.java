package br.com.upf.sd.chat;

import java.awt.TextArea;
import java.io.DataInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.swing.SwingUtilities;
import javax.xml.bind.DatatypeConverter;

import br.com.upf.sd.Default;


public class Receber {
	
	private DataInputStream dIn;
	private byte[] message = null;	
	private SecretKey desKey;	
	private int count = 0;
	private boolean modoDebug;
	private String nome;
	private String ipConexao;
	private TextArea txMensagens;
    
    public Receber(boolean modoDebug, DataInputStream dIn, SecretKey desKey, String nome, String ipConexao, TextArea txMensagens) {
        this.dIn = dIn;    
        this.desKey = desKey;  
        this.modoDebug = modoDebug;
        this.nome = nome;
        this.ipConexao = ipConexao;
        this.txMensagens = txMensagens;
    }
	
	public void run() {
		Default diffieHellman = new Default();
		
		try {
			
			System.out.println("Conectado a: "+ipConexao);
			
			while (true) {
				
				count++;
				
				int length = dIn.readInt();
				message = new byte[length];
				
				dIn.readFully(message, 0, message.length); // read the message				
				
				byte[] iv = Arrays.copyOfRange(message, length-16, length);	
				diffieHellman.printHex(modoDebug, "\nNonce recebido: ", iv);
				iv[15] = (byte) ((byte) (count & 0xff) ^ this.desKey.getEncoded()[10]);				
				IvParameterSpec nonce = new IvParameterSpec(iv);
								
				diffieHellman.printHex(modoDebug, "\nNonce codificado: ", nonce.getIV());			
				
				byte[] ciphertextandnonce = Arrays.copyOfRange(message, 0, length-16);
								
				diffieHellman.printHex(modoDebug, "\nMensagem SEM nonce: ", ciphertextandnonce);	
				diffieHellman.printHex(modoDebug, "\nMensagem COM nonce: ", message);
							
				Cipher aliceCipher = Cipher.getInstance("AES/CTR/PKCS5Padding");												                                
		        aliceCipher.init(Cipher.DECRYPT_MODE, this.desKey, nonce);
								 	    		 	    
		 	    byte[] recovered = aliceCipher.doFinal(ciphertextandnonce);
		 	    String value = new String(recovered, "UTF-8");  
		 	    byte[] decoded = DatatypeConverter.parseBase64Binary(value);
		 	    
		        System.out.println("\nMensagem recebida:\n"+new String(decoded, "UTF-8"));
		        System.out.println("--------------------------------------");
		        
		        SwingUtilities.invokeLater(new Runnable() {
			        public void run() {
				    	try {				
				    		txMensagens.append(nome+" --- "+new String(decoded, "UTF-8")+"\n");								
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
				    }
		        });

			}
			
		} catch (Exception e) {				
			System.out.println(ipConexao+" saiu da conversa!");	
//			System.exit(0);
		}
		
	}		   	 	
}
