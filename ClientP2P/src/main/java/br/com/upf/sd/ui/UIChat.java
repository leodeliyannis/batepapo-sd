package br.com.upf.sd.ui;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.Pipe.SinkChannel;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.xml.bind.DatatypeConverter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import br.com.upf.sd.Default;
import br.com.upf.sd.chat.Enviar;
import br.com.upf.sd.chat.Receber;
import br.com.upf.sd.main.GeradorChaveCryptChat;
import br.com.upf.sd.types.Argumentos;

public class UIChat {

	protected Shell shlChat;
	private Text txMensagensEnvia;
	private static Text txMensagensRecebe;
	private Button btnEnvia;
	private static Receber receber;
	
	private Socket socket;
	private static Socket cliente;
	private static ServerSocket serverSocket; 
    private static SecretKey Key;
    private static Display display;
    
    private static String modo;
    private static String nome;
    private static String host;
    private static String conecta;
	private static String recebe;
    
    private int count = 0;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args, ServerSocket s) {
		try {
			Escreve esc = new Escreve();
			modo = args[0];
			nome = args[1];
			host = args[2];
			
			conecta = args[3];
			recebe = args[4];
			cliente = null;
			if(s != null) {
				serverSocket = s;	
				UIMenu.conectado = true;
				cliente = serverSocket.accept();
			}else {
				serverSocket = new ServerSocket(Integer.parseInt(recebe));			
			}
			
			UIChat window = new UIChat();
			window.open();
			esc.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public synchronized void open() {
		display = Display.getDefault();
		createContents();
		shlChat.open();
		shlChat.layout();		
		
	    	try {
	    			    		
				socket = new Socket(host, Integer.parseInt(conecta));
				socket.setSoTimeout(10000000);//10 min
				System.out.println("Conexão segura estabelecida");
				
				OutputStreamWriter wr = new OutputStreamWriter(socket.getOutputStream());
				
				btnEnvia.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						try {
							
							wr.write(txMensagensEnvia.getText());
							txMensagensRecebe.append(nome+" - "+txMensagensEnvia.getText()+"\n");
							txMensagensEnvia.setText("");
							wr.flush();
						}catch(Exception e) {
							
						}
					}
				});
				
				
				
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
		
		while (!shlChat.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected synchronized void createContents() {
		
		shlChat = new Shell();
		shlChat.setSize(605, 400);
		shlChat.setText("Chat");
		
		txMensagensEnvia = new Text(shlChat, SWT.BORDER);
		txMensagensEnvia.setBounds(10, 314, 509, 38);
		
		btnEnvia = new Button(shlChat, SWT.NONE);		
		btnEnvia.setBounds(524, 314, 53, 38);
		btnEnvia.setText("Enviar");
		
		txMensagensRecebe = new Text(shlChat, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		txMensagensRecebe.setEditable(false);
		txMensagensRecebe.setBounds(10, 35, 567, 262);
		
		Label lblMensagens = new Label(shlChat, SWT.NONE);
		lblMensagens.setBounds(264, 10, 65, 15);
		lblMensagens.setText("Mensagens");
		
	}
	
	static class Escreve extends Thread{
		public Escreve() {}
		
		@Override
		public void run() {
			System.out.println("Recebe iniciado");
			
			try {			
				if(cliente == null) {
					cliente = serverSocket.accept();
				}
				
				cliente.setSoTimeout(10000000);//10 min
	    		DataInputStream dIn = new DataInputStream(cliente.getInputStream());
				
	    		System.out.println("Obtendo conexão segura");
	    	
	    		BufferedReader buffer;
	    		String str;
	    		while (true) {
					
					display.sleep();
					
					buffer = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
					str = buffer.readLine();
					
			        System.out.println("--------------------------------------");
			        mostra(str);
				}
	    		
			
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		
		public synchronized void mostra(String str) {
			txMensagensRecebe.append(" -- "+str+"\n");
		}
		
	}
}
