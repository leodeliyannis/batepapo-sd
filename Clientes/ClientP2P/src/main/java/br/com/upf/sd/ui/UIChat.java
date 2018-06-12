package br.com.upf.sd.ui;

import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.crypto.SecretKey;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import br.com.upf.sd.Default;
import br.com.upf.sd.chat.Enviar;
import br.com.upf.sd.chat.Receber;
import br.com.upf.sd.main.GeradorChaveCryptChat;
import br.com.upf.sd.types.Argumentos;

public class UIChat {
	
	private static JLabel lbPessoa;
	private static TextArea txMensagensReceber;
	private static JTextField txMensagensEnviar;
	
	private static Socket socket;
	private static ServerSocket serverSocket; 
	private static SecretKey Key;
	
	private static Enviar envia;
	
	private static String modo;
	private static String nome;
	private static String host;
	private static String conecta;
	private static String recebe;
	
	private static int count = 0;

	public static void main(String[] args, SecretKey KeyDes, Socket sock) {
		
		modo = args[0];
		nome = args[1];
		host = args[2];
		conecta = args[3];
		recebe = args[4];
		
		Key = KeyDes;
		socket = sock;
	   
	    SwingUtilities.invokeLater(new Runnable() {
	      public void run() {
	        displayJFrame();
	      }
	    });
    }

	static void displayJFrame() {		

		JFrame jframe = new JFrame("Chat");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jframe.setPreferredSize(new Dimension(520, 400));
		jframe.pack();
		jframe.setLocationRelativeTo(null);
		jframe.getContentPane().setLayout(null);
		
		lbPessoa = new JLabel("");
		lbPessoa.setHorizontalAlignment(SwingConstants.CENTER);
		lbPessoa.setBounds(10, 16, 484, 14);
		jframe.getContentPane().add(lbPessoa);
		
		txMensagensReceber = new TextArea();
		txMensagensReceber.setEditable(false);
		txMensagensReceber.setBounds(10, 36, 484, 266);
		jframe.getContentPane().add(txMensagensReceber);
		
		txMensagensEnviar = new JTextField();
		txMensagensEnviar.setBounds(10, 308, 393, 43);
		jframe.getContentPane().add(txMensagensEnviar);
		txMensagensEnviar.setColumns(10);
		
		jframe.setVisible(true);
		
		if(modo.equals("c")) {
			System.out.println("Inicio do conecta");
			Argumentos argumentos = new Argumentos();
			
			argumentos.setModo("c");
			argumentos.setIp(host);
			argumentos.setDebug(true);
			argumentos.setDh(1024);
			argumentos.setAes(128);
  		
	  		GeradorChaveCryptChat cryptKey = new GeradorChaveCryptChat(argumentos, conecta, recebe);
	  		Key = cryptKey.run();
	  		
			envia = conecta("c");
			
			System.out.println("Conectado!!");
			System.out.println("Inicio do escuta");
			escuta();
			
			System.out.println("Escutando!!");
			
		} else if(modo.equals("r")) {
			System.out.println("Inicio do escuta");
			
			escuta();
			
			System.out.println("Escutando!!");
			
			while(socket == null) {
			
				envia = conecta("c");
			}
			
			System.out.println("Conectado!!");
		}
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				envia.run(txMensagensEnviar.getText());
				txMensagensReceber.append("Eu - "+txMensagensEnviar.getText()+"\n");
				txMensagensEnviar.setText("");
			}
		});
		btnEnviar.setBounds(413, 308, 81, 43);
		jframe.getContentPane().add(btnEnviar);
		jframe.setVisible(true);
	}
	
	private static Enviar conecta(String tipo) {
  		
//  		System.out.println("Conecta recebe key");
  		
  		if(tipo.equals("c")) {
			try {
				socket = new Socket(host, Integer.parseInt(conecta));
				socket.setSoTimeout(10000000);
			} catch (Exception e) {
				System.out.println(e);
			}
  		}
		
//		System.out.println("Conexão segura estabelecida");
		
		Default diffieHellman = new Default();
		
		return new Enviar(socket, count, true, Key, diffieHellman);
  		
  		
	}
	
	private static void escuta() {

		Thread worker = new Thread() {
			
		    public void run() {

				try {
					
					System.out.println("Iniciando receber do chat");

					if(modo.equals("r")) {
						serverSocket = new ServerSocket(Integer.parseInt(recebe));
						
						socket = serverSocket.accept();  
						socket.setSoTimeout(10000000);
					}
					
//					System.out.println(socket.getInetAddress().getHostAddress());
					
					DataInputStream dIn = new DataInputStream(socket.getInputStream());
					
					Receber receber = new Receber(true, dIn, Key, nome, socket.getInetAddress().getHostAddress(), txMensagensReceber);
					receber.run();
				
				} catch (Exception e) {
					System.err.println(e);
				}

		    
		    }
		};
		  
		worker.start();

	}	
	
}