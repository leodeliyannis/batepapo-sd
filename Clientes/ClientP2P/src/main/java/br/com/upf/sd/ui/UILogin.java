package br.com.upf.sd.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import br.com.upf.sd.login.EnviaDadosLogin;
import br.com.upf.sd.main.OrquestradorLogin;
import br.com.upf.sd.types.Argumentos;
import br.com.upf.sd.types.Token;
import br.com.upf.sd.types.UsuarioInput;

public class UILogin {
	
	// http://www.coderpanda.com/java-socket-programming-transferring-java-object-through-socket-using-udp/

	protected Shell shell;
	private Text txUser;
	private Text txSenha;
	private Text txIp;
	private Table table;
	private Text txPortaRecebe;
	private Text txPortaConecta;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UILogin window = new UILogin();
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
		shell.setSize(470, 500);
		shell.setText("Login");
		
		Label lblUsurio = new Label(shell, SWT.NONE);
		lblUsurio.setBounds(103, 47, 55, 21);
		lblUsurio.setText("Usuário: ");
		
		txUser = new Text(shell, SWT.BORDER);
		txUser.setBounds(164, 44, 155, 30);
		
		Label lblSenha = new Label(shell, SWT.NONE);
		lblSenha.setBounds(111, 83, 47, 21);
		lblSenha.setText("Senha: ");
		
		txSenha = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		txSenha.setBounds(164, 80, 155, 26);
		
		final Label lblStatusLogin = new Label(shell, SWT.NONE);
		lblStatusLogin.setAlignment(SWT.CENTER);
		lblStatusLogin.setBounds(10, 422, 433, 21);
		lblStatusLogin.setText("");
		
		//progressBar = new ProgressBar(shell, SWT.INDETERMINATE);
		
		final Button btnLogin = new Button(shell, SWT.NONE);		
		btnLogin.setBounds(174, 390, 90, 30);
		btnLogin.setText("Login");
		
		Label lblIpServidor = new Label(shell, SWT.NONE);
		lblIpServidor.setBounds(93, 115, 65, 18);
		lblIpServidor.setText("Ip Server: ");
		
		txIp = new Text(shell, SWT.BORDER);
		txIp.setText("127.0.0.1");
		txIp.setBounds(164, 112, 155, 26);
		
		Label lblAssuntos = new Label(shell, SWT.NONE);
		lblAssuntos.setBounds(190, 204, 65, 21);
		lblAssuntos.setText("Assuntos:");
		
		table = new Table(shell, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION);
		table.setBounds(103, 231, 244, 153);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		final TableItem tableItem_0 = new TableItem(table, SWT.NONE);
		tableItem_0.setText("Eleições 2018");
		
		final TableItem tableItem_1 = new TableItem(table, SWT.NONE);
		tableItem_1.setText("Segurança pública");
		
		final TableItem tableItem_2 = new TableItem(table, SWT.NONE);
		tableItem_2.setText("Preço da gasolina");
		
		final TableItem tableItem_7 = new TableItem(table, SWT.NONE);
		tableItem_7.setText("Sistema nacional de saúde ");
		
		final TableItem tableItem_6 = new TableItem(table, SWT.NONE);
		tableItem_6.setText("Aposentadoria");
		
		final TableItem tableItem_3 = new TableItem(table, SWT.NONE);
		tableItem_3.setText("Traição");
		
		final TableItem tableItem_4 = new TableItem(table, SWT.NONE);
		tableItem_4.setText("Multas de trânsito");
		
		final TableItem tableItem_5 = new TableItem(table, SWT.NONE);
		tableItem_5.setText("Educação");		
		
		txPortaRecebe = new Text(shell, SWT.BORDER);
		txPortaRecebe.setText("444");
		txPortaRecebe.setBounds(264, 144, 55, 26);
		
		txPortaConecta = new Text(shell, SWT.BORDER);
		txPortaConecta.setText("444");
		txPortaConecta.setBounds(164, 144, 55, 26);
		
		Label lblC = new Label(shell, SWT.NONE);
		lblC.setBounds(138, 147, 20, 20);
		lblC.setText("C:");
		
		Label lblR = new Label(shell, SWT.NONE);
		lblR.setBounds(235, 147, 20, 20);
		lblR.setText("R:");
		
		btnLogin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnLogin.addMouseListener(new MouseAdapter() {	

			@Override
			public void mouseUp(MouseEvent e) {

				try {
					
					lblStatusLogin.setText("Iniciando login seguro com o servidor");
					
					List<String> topicos = new ArrayList<String>();										
					
					if(tableItem_0.getChecked()) {
						topicos.add(tableItem_0.getText());
					}if(tableItem_1.getChecked()) {
						topicos.add(tableItem_1.getText());
					}if(tableItem_2.getChecked()) {
						topicos.add(tableItem_2.getText());
					}if(tableItem_3.getChecked()) {
						topicos.add(tableItem_3.getText());
					}if(tableItem_4.getChecked()) {
						topicos.add(tableItem_4.getText());
					}if(tableItem_5.getChecked()) {
						topicos.add(tableItem_5.getText());
					}if(tableItem_6.getChecked()) {
						topicos.add(tableItem_6.getText());
					}if(tableItem_7.getChecked()) {
						topicos.add(tableItem_7.getText());
					}
					
					UsuarioInput user = new UsuarioInput(txUser.getText(), txSenha.getText(), topicos);
					Token token = new Token();
					
					OrquestradorLogin orq = new OrquestradorLogin();
					Argumentos argumentos = new Argumentos();
					
					argumentos.setModo("c");
					argumentos.setIp(txIp.getText());
					argumentos.setPorta(10453);
					argumentos.setDebug(true);
					argumentos.setDh(1024);
					argumentos.setAes(128);
					argumentos.setUsuarioInput(user);
					argumentos.setToken(token);
					
					EnviaDadosLogin edl = orq.run(argumentos);

					lblStatusLogin.setText("Aguardando login seguro com o servidor");

					synchronized(edl) {
												
						edl.wait(1000);
						lblStatusLogin.setText("Token recebido");
						
						if(!br.com.upf.sd.utils.Utils.nvl(edl.getToken()).equals("")) {
							
							String [] args = new String[5];
						    args[0] = edl.getToken().toString();
						    args[1] = txIp.getText();
						    args[2] = txUser.getText();
						    
						    args[3] = txPortaConecta.getText();
						    args[4] = txPortaRecebe.getText();
						    
							shell.dispose();
							UIMenu.main(args, topicos);

						} else if(edl.getToken() == null) { 
							lblStatusLogin.setText("Erro ao conectar ao servidor, Timed out!");
						} else {
							lblStatusLogin.setText("Usuário ou senha invalidos");
						}
						
					}
				
				} catch (Exception e1) {
					btnLogin.setEnabled(true);
					System.err.println(e1);
					lblStatusLogin.setText("Erro ao conectar ao servidor");
				}
			}
		});
       

	}
}
