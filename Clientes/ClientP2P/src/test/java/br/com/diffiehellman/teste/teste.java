package br.com.diffiehellman.teste;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.xml.bind.DatatypeConverter;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import br.com.upf.sd.Default;
import br.com.upf.sd.chat.Enviar;
import br.com.upf.sd.main.GeradorChaveCryptChat;
import br.com.upf.sd.types.Argumentos;

public class teste extends JFrame {
 private JTextField txMensagensEnvia;
 private JTextArea txMensagensRecebe;
 
 private Socket socket;
 private ServerSocket serverSocket; 
 private SecretKey Key;
 
 private static String modo;
 private static String nome;
 private static String host;
 private static String conecta;
 private static String recebe;
 
 private int count = 0;

 public teste(String title) {
  super(title);
  getContentPane().setLayout(null);
  
  txMensagensEnvia = new JTextField();
  txMensagensEnvia.setBounds(10, 349, 530, 51);
  getContentPane().add(txMensagensEnvia);
  txMensagensEnvia.setColumns(10);
  
  JButton btEnvia = new JButton("Send");  
  btEnvia.setBounds(550, 349, 59, 51);
  getContentPane().add(btEnvia);
  
  JLabel lblMensagens = new JLabel("Mensagens");
  lblMensagens.setBounds(278, 28, 59, 14);
  getContentPane().add(lblMensagens);
  
  txMensagensRecebe = new JTextArea();
  txMensagensRecebe.setEditable(false);
  txMensagensRecebe.setBounds(10, 46, 599, 292);
  getContentPane().add(txMensagensRecebe);
  
  if(modo.equals("r")) {
	  start();
  }else if(modo.equals("c")) {
	  System.out.println("Conecta iniciado");
  	try {
  		
  		Argumentos argumentos = new Argumentos();
			
			argumentos.setModo("c");
			argumentos.setIp(host);
			argumentos.setDebug(true);
			argumentos.setDh(1024);
			argumentos.setAes(128);
  		
	  		GeradorChaveCryptChat cryptKey = new GeradorChaveCryptChat(argumentos, conecta, recebe);
	  		Key = cryptKey.run();

  		
  			System.out.println("Obtendo conexão segura");
			socket = new Socket(host, Integer.parseInt(conecta));
			socket.setSoTimeout(10000000);//10 min
			System.out.println("Conexão segura estabelecida");
			
			Default diffieHellman = new Default();
			
			Enviar enviar = new Enviar(socket, count, true, Key, diffieHellman);
			
			btEnvia.addActionListener(new ActionListener() {
			  	public void actionPerformed(ActionEvent arg0) {
			  		enviar.run(txMensagensEnvia.getText());
					txMensagensRecebe.append(nome+" - "+txMensagensEnvia.getText()+"\n");
					txMensagensEnvia.setText("");
			  	}
			  });
			
//  		receber = new Receber(argumentos.getDebug(), null, Key, null, txMensagensRecebe, display);
//  		receber.run();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
      		System.err.println("Finalizando socket");
      		//Finaliza objetos
      		socket.shutdownInput();
				socket.shutdownOutput();
				socket.close();
			} catch (IOException ex) {
				System.err.println("Erro ao finalizar os objetos em enviar: "+ex);
			}       
		} 
  }
  
  
  setVisible(true);
  
 
 }
 
 	private void start() {

	  

	  Thread worker = new Thread() {
	  
		 byte[] message = null;	

			 int count = 0;
			 boolean modoDebug;
			 String ipConexao;
	   public void run() {
		   
		   
	    
		     try {
		    	 
		    	 	Argumentos argumentos = new Argumentos();
					
					argumentos.setModo("r");
					argumentos.setIp(host);
					argumentos.setDebug(true);
					argumentos.setDh(1024);
					argumentos.setAes(128);
					
					GeradorChaveCryptChat cryptKey = new GeradorChaveCryptChat(argumentos, conecta, recebe);
					Key =  cryptKey.run();

					
		    	 
				serverSocket = new ServerSocket(Integer.parseInt(recebe));
				Socket cliente = serverSocket.accept();  
				 cliente.setSoTimeout(10000000);//10 min
				
				 DataInputStream dIn = new DataInputStream(cliente.getInputStream());
					
				 System.out.println("Obtendo conexão segura");
				 
				 while (true) {
					
					
				
			
					 count++;
						
					int length = dIn.readInt();
					message = new byte[length];
					
					dIn.readFully(message, 0, message.length); // read the message				
					
					byte[] iv = Arrays.copyOfRange(message, length-16, length);	
					
					iv[15] = (byte) ((byte) (count & 0xff) ^ Key.getEncoded()[10]);				
					IvParameterSpec nonce = new IvParameterSpec(iv);
									
							
					
					byte[] ciphertextandnonce = Arrays.copyOfRange(message, 0, length-16);
									
					
								
					Cipher aliceCipher = Cipher.getInstance("AES/CTR/PKCS5Padding");												                                
			        aliceCipher.init(Cipher.DECRYPT_MODE, Key, nonce);
									 	    		 	    
			 	    byte[] recovered = aliceCipher.doFinal(ciphertextandnonce);
			 	    String value = new String(recovered, "UTF-8");  
			 	    byte[] decoded = DatatypeConverter.parseBase64Binary(value);
			 	    
			        System.out.println("\nMensagem recebida:\n"+new String(decoded, "UTF-8"));
//					 Thread.sleep(1000);
					 
					 SwingUtilities.invokeLater(new Runnable() {
					      public void run() {
					    	  try {
								System.out.println("Recebi1: "+new String(decoded, "UTF-8"));
								txMensagensRecebe.append(new String(decoded, "UTF-8")+"\n");								
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					    	  
					      }
					     });
					 
//					 throw new Exception("Err");
				 }
				 
//				 Thread.sleep(1000);
				 
				 
			} catch (NumberFormatException | IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
				// TODO Auto-generated catch block
				System.err.println(e);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     			     
			
			 
//		     System.out.println("Recebi: "+mensagem);
//		    SwingUtilities.invokeLater(new Runnable() {
//			     public void run() {
//			    	 System.out.println("Recebi2: "+new String(decoded, "UTF-8"));
//			    	 txMensagensRecebe.append(new String(decoded, "UTF-8"));
//			     }
//		    });
	    
	   }
	  };
	  
	  worker.start();
	  
	  
	 }
 
 public static void main(String[] args) {
	nome = "teste";
	host = "127.0.0.1";
	
	modo = "r";
	conecta = "4441";
	recebe = "4442";
	
	modo = "c";
	conecta = "4442";
	recebe = "4441";
	SwingUtilities.invokeLater(new Runnable() {
   
		@Override
		public void run() {
		    new teste("SwingWorker Demo");
		}
	  });
 }
}