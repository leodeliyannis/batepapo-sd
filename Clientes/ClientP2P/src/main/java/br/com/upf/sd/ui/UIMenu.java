package br.com.upf.sd.ui;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

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

import br.com.upf.sd.main.GeradorChaveCryptChat;
import br.com.upf.sd.pesquisa.EnviaDadosPesquisa;
import br.com.upf.sd.types.Argumentos;
import br.com.upf.sd.types.Pesquisa;
import br.com.upf.sd.types.Topico;
import br.com.upf.sd.types.TopicosUsuariosResponse;
import br.com.upf.sd.types.Usuario;
import br.com.upf.sd.types.UsuariosTopicosResponse;

public class UIMenu {

	protected Shell shell;
	private Table tbPesquisa;
	private Text txPessoa;
	private TableColumn tblclmnPessoa;
	private TableColumn tblclmnTopico;
	private TableColumn tblclmnConversar;
	private Table tbMeusTopicos;
	private TableColumn tblclmnMeusTopicos;	

	private static String token;
	private static String ipServidor;
	private static String meuUser;
	private static List<String> topicos = new ArrayList<String>();
	private static TopicosUsuariosResponse topicosUsuario;
	private static UsuariosTopicosResponse usuarioTopicos;
	
	private static Socket socket;
	private static Escuta escuta;
	
	private static String conecta;
	private static String recebe;
	public static Boolean conectado;
	
	private TableItem tableItem;
	private TableItem tableItem_1;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args, List<String> topicosArgs) {//, List<String> topicosArgs
		try {
			
			token = args[0];
			ipServidor = args[1];
			meuUser = args[2];
			
			conecta = args[3];
			recebe = args[4];
			
			topicos = topicosArgs;
			
			escuta = new Escuta();
			escuta.start();

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
		
		txPessoa = new Text(shell, SWT.BORDER);
		txPessoa.setBounds(170, 438, 243, 28);
		
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
		btnPesquisaTopicos.setBounds(419, 408, 117, 25);
		btnPesquisaTopicos.setText("Pesquisa Topico");
		
		btnPesquisaTopicos.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Pesquisa pesquisa = new Pesquisa();
				
				pesquisa.setMetodo("pesquisa_topico");
				pesquisa.setNome(combo.getText());
				pesquisa.setToken(token);
				
				EnviaDadosPesquisa edp = new EnviaDadosPesquisa();
				topicosUsuario = edp.getEnviaDadosPesquisaTopicos(ipServidor, 10353, true, pesquisa);
				
				tbPesquisa.removeAll();
								
				topicosUsuario.getUsuarios().removeIf(p -> p.getNome().equals(meuUser));
				for(Usuario i: topicosUsuario.getUsuarios()) {
					tableItem_1 = new TableItem(tbPesquisa, SWT.NONE);					
					tableItem_1.setText(new String[] { ""+i.getNome(), ""+pesquisa.getNome(), i.getStatus()});
				}
				
			}
		});		

		Button btnPesquisaPessoa = new Button(shell, SWT.NONE);
		btnPesquisaPessoa.setBounds(419, 439, 117, 25);
		btnPesquisaPessoa.setText("Pesquisa Pessoa");
		
		btnPesquisaPessoa.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Pesquisa pesquisa = new Pesquisa();
				
				pesquisa.setMetodo("pesquisa_usuario");
				pesquisa.setNome(txPessoa.getText());
				pesquisa.setToken(token);
				
				EnviaDadosPesquisa edp = new EnviaDadosPesquisa();
				usuarioTopicos = edp.getEnviaDadosPesquisaUsuario(ipServidor, 10353, true, pesquisa);
				
				tbPesquisa.removeAll();
				
				for(Topico i: usuarioTopicos.getTopicos()) {
					tableItem_1 = new TableItem(tbPesquisa, SWT.NONE);					
					tableItem_1.setText(new String[] { ""+pesquisa.getNome(), ""+i.getTopico(), i.getStatus()});
				}
				
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
		        escuta.stopSocket();
		        escuta.stop();
		        if(nome.length() > 0) {
	        		for(Usuario user: topicosUsuario.getUsuarios()) {
	        			if(nome.contains(user.getNome())) {
	        				UIChat.main(new String[] {"c", nome, user.getIpAddres(), conecta, recebe}, null, socket);
	        				break;
	        			}
	        		}
	        	}
			}
	    });

	}
	static class Escuta extends Thread {
		public Escuta() {}
		
		private GeradorChaveCryptChat cryptKey;
		private SecretKey key;
		
		@Override
		public void run() {

			try {
				Argumentos argumentos = new Argumentos();
				
				argumentos.setModo("r");
				argumentos.setDebug(true);
				argumentos.setDh(1024);
				argumentos.setAes(128);
				
				cryptKey = new GeradorChaveCryptChat(argumentos, conecta, recebe);
				key = cryptKey.run();
				
				System.out.println("Key: "+key.getEncoded());
	  			
				if(key != null) {
					UIChat.main(new String[] {"r", null, null, conecta, recebe}, key, socket);
				}
				
	  			
			} catch (Exception e) {
				System.err.println(e);
			}
			
		}
		
		public void stopSocket() {
			cryptKey.stopSocket();
		}
	}
	
	
}
