package br.com.upf.sd.chat;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.xml.bind.DatatypeConverter;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

import br.com.upf.sd.Default;


public class Receber implements Runnable {
	
	private DataInputStream dIn;
	private byte[] message = null;	
	private SecretKey desKey;	
	private int count = 0;
	private boolean modoDebug;
	private String ipConexao;
	private Text txMensagens;
	private Display display;
    
    public Receber(boolean modoDebug, DataInputStream dIn, SecretKey desKey, String ipConexao, Text txMensagens, Display display) {
        this.dIn = dIn;    
        this.desKey = desKey;  
        this.modoDebug = modoDebug;
        this.ipConexao = ipConexao;
        this.txMensagens = txMensagens;
        this.display = display;
    }
	
	public void run() {
		Default diffieHellman = new Default();
		
		try {
			
//			if(dIn != null) {
//				serverSocket = new ServerSocket(Integer.parseInt(recebe));
//				
//				Socket cliente = serverSocket.accept();  
//				cliente.setSoTimeout(10000000);//10 min
//				
//				DataInputStream dIn = new DataInputStream(cliente.getInputStream());
//				
//				System.out.println("Obtendo conexão segura");
//			}
			
			while (true) {
				
				display.sleep();
				
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
		        txMensagens.append(" -- "+new String(decoded, "UTF-8")+"\n");
			}
			
		} catch (Exception e) {				
			System.out.println(ipConexao+" saiu da conversa!");	
			System.exit(-2);
		} 		
	}		   	 	
}
