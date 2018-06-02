package br.com.upf.sd.ui;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import br.com.upf.sd.enviaChavePub.RecebeDadosThread;
import br.com.upf.sd.pesquisa.EnviaDadosPesquisa;
import br.com.upf.sd.types.Pesquisa;
import br.com.upf.sd.types.TopicosUsuariosResponse;
import br.com.upf.sd.types.Usuario;

public class UIMenu {

	protected Shell shell;
	private Table tbPesquisa;
	private Text text_1;
	private TableColumn tblclmnPessoa;
	private TableColumn tblclmnTopico;
	private TableColumn tblclmnConversar;
	private Table tbMeusTopicos;
	private TableColumn tblclmnMeusTopicos;	

	private static String token;
	private static String ipServidor;
	private static String meuUser;
	private static ArrayList<String> topicos = new ArrayList<String>();
	private static TopicosUsuariosResponse topicosUsuario;
	private static RecebeDadosThread recebeKey = null;
	private static byte[] chavePubKeyEncRecebida;
	
	private static String conecta;
	private static String recebe;
	public static Boolean conectado;
	
	private TableItem tableItem;
	private TableItem tableItem_1;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			token = args[0];
			ipServidor = args[1];
			meuUser = args[2];
			
			conecta = args[3];
			recebe = args[4];
			Escuta e = new Escuta();
			e.start();
			
			//Carrega meus topicos
			try {
				FileInputStream fis = new FileInputStream("topicos");
	            ObjectInputStream ois = new ObjectInputStream(fis);
	            topicos = (ArrayList<String>) ois.readObject();
	            ois.close();
	            fis.close();
	         } catch(IOException ioe) {
	             ioe.printStackTrace();
	             return;
	         } catch(ClassNotFoundException c) {
	             System.err.println("Class not found");
	             c.printStackTrace();
	             return;
	         }

			UIMenu window = new UIMenu();
			window.open();		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		
		//Aguarda chat
//		try {
//			recebeKey = new RecebeDadosThread(Integer.parseInt(recebe), "r", true);
//			//recebe chave em formato codificado de Alice
//			recebeKey.start();
//			
//			System.out.println("Inicia synchronized");
//			synchronized(recebeKey) {
//				
//				System.out.println("Aguardando chave");
//				//recebeKey.wait();
//				chavePubKeyEncRecebida = recebeKey.getKey();
//				
//				//serializar lista de topicos
//			    ObjectOutputStream oos = null;
//			    try {
//			         FileOutputStream fos= new FileOutputStream("key");
//			         oos= new ObjectOutputStream(fos);
//			         oos.writeObject(chavePubKeyEncRecebida);
//			         oos.close();
//			         fos.close();
//
//		        } catch(IOException ioe) {
//		             ioe.printStackTrace();
//		        }
//			    System.out.println("Iniciando chat");
//			    //UIChat.main(new String[] {"r", null, recebeKey.getIpConectado(), conecta, recebe});
//			}
//		
//		} catch(Exception e) {
//			recebeKey.stop();
//			System.err.println(e);
//			//System.exit(0);
//		}
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();		
		shell.setSize(1024, 600);
		shell.setText("Menu");
		
		//Ao fechar janela main Stop na thread de chat
		shell.addListener(SWT.Close, new Listener() {	   
			public void handleEvent(org.eclipse.swt.widgets.Event arg0) {
				recebeKey.stop();
		        System.exit(0);				
			}
	    });
				
		tbPesquisa = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		tbPesquisa.setBounds(170, 105, 485, 294);
		tbPesquisa.setHeaderVisible(true);
		tbPesquisa.setLinesVisible(true);
		
		tblclmnPessoa = new TableColumn(tbPesquisa, SWT.NONE);
		tblclmnPessoa.setWidth(125);
		tblclmnPessoa.setText("Pessoa");
		
		tblclmnTopico = new TableColumn(tbPesquisa, SWT.NONE);
		tblclmnTopico.setWidth(255);
		tblclmnTopico.setText("Topico");
		
		tblclmnConversar = new TableColumn(tbPesquisa, SWT.NONE);
		tblclmnConversar.setWidth(100);
		tblclmnConversar.setText("Status");
		
		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(170, 438, 243, 21);
		
		tbMeusTopicos = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		tbMeusTopicos.setBounds(723, 105, 192, 294);
		tbMeusTopicos.setHeaderVisible(true);
		tbMeusTopicos.setLinesVisible(true);
		
		tblclmnMeusTopicos = new TableColumn(tbMeusTopicos, SWT.NONE);
		tblclmnMeusTopicos.setWidth(188);
		tblclmnMeusTopicos.setText("Meus Topicos");
		
		tableItem = new TableItem(tbMeusTopicos, SWT.NONE);
		TableItem item = null;
		for(String i: topicos) {
	      item = new TableItem(tbMeusTopicos, SWT.NONE);
	      item.setText(i);
	    }

		tableItem.setText(""+tbMeusTopicos.indexOf(item));
		tbMeusTopicos.remove(0);		
		
		final Combo combo = new Combo(shell, SWT.NONE);
		combo.setBounds(170, 407, 243, 23);
		for(String i: topicos) {
			combo.add(i);
		}

		Button btnPesquisaTopicos = new Button(shell, SWT.NONE);
		btnPesquisaTopicos.setBounds(419, 405, 117, 25);
		btnPesquisaTopicos.setText("Pesquisa Topico");
		
		btnPesquisaTopicos.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Pesquisa pesquisa = new Pesquisa();
				
				pesquisa.setMetodo("pesquisa_topico");
				pesquisa.setNome(combo.getText());
				pesquisa.setToken(token);
				
				EnviaDadosPesquisa edp = new EnviaDadosPesquisa();
				topicosUsuario = edp.getEnviaDadosPesquisa(ipServidor, 10353, true, pesquisa);
				
				tbPesquisa.removeAll();
								
				topicosUsuario.getUsuarios().removeIf(p -> p.getNome().equals(meuUser));
				for(Usuario i: topicosUsuario.getUsuarios()) {
					tableItem_1 = new TableItem(tbPesquisa, SWT.NONE);					
					tableItem_1.setText(new String[] { ""+i.getNome(), ""+pesquisa.getNome(), i.getStatus()});
				}
				
			}
		});		

		Button btnPesquisaPessoa = new Button(shell, SWT.NONE);
		btnPesquisaPessoa.setBounds(419, 436, 117, 25);
		btnPesquisaPessoa.setText("Pesquisa Pessoa");
		
		
		btnPesquisaPessoa.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
			}			
		});		
		
		tbPesquisa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
		        String nome = "";
		        TableItem[] selection = tbPesquisa.getSelection();
		        for (int i = 0; i < selection.length; i++) {
		        	nome += selection[i].getText() + " ";		        	
		        }
		        if(nome.length() > 0) {
	        		for(Usuario user: topicosUsuario.getUsuarios()) {
	        			if(nome.contains(user.getNome())) {
	        				UIChat.main(new String[] {"c", nome, user.getIpAddres(), conecta, recebe}, null);
	        				break;
	        			}
	        		}
	        	}
			}
	    });

	}
	static class Escuta extends Thread{
		public Escuta() {}
		
		@Override
		public void run() {
			
			ServerSocket serverSocket;
			
			try {
				serverSocket = new ServerSocket(Integer.parseInt(recebe));				
				conectado = false;//TODO implementar validação de conexão do usuário
	    		while(true) {
					if(!conectado)
						UIChat.main(new String[] {"r",null, null, conecta, recebe }, serverSocket);    		
	    		}
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	
}
