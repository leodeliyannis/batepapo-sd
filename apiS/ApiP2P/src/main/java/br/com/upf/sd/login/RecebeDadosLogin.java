package br.com.upf.sd.login;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.*;

import com.ctc.wstx.io.UTF8Reader;

import br.com.upf.sd.resources.ApiBanco;
import br.com.upf.sd.types.LoginRequest;
import br.com.upf.sd.types.LoginResponse;
import br.com.upf.sd.types.Token;
import br.com.upf.sd.types.UsuarioInput;
import br.com.upf.sd.utils.ResponseType;

public class RecebeDadosLogin implements Runnable {	
	
	private DatagramSocket serverSocket;
	private boolean modoDebug;
	private int porta;
	
	public RecebeDadosLogin(boolean modoDebug, int porta) throws SocketException {   
        this.modoDebug = modoDebug;
		this.porta = porta;
	}
	
	private void shutdown() {
		serverSocket.close();
	}

	public void run() {
		
		try {			
			serverSocket = new DatagramSocket(porta);
//			System.out.println("Aguardando conexao...");

	        if (modoDebug)
	        	System.out.println("\nPorta "+porta+" aberta para recer login sem criptografia!");
			
			byte[] dados = new byte[2048];//input
			byte[] mensagemRecebida;
			byte[] mensagemEnviada = new byte[2048]; //output
			
			DatagramPacket receivedPacket = new DatagramPacket(dados, dados.length);
			serverSocket.receive(receivedPacket);
			
			String mensagem = new String(receivedPacket.getData());
			
	        
	        JSONObject json = new JSONObject(mensagem);
	        JSONArray topicosJson = json.getJSONArray("topicos");
	        List<String> topicos = new ArrayList<String>();
	        for(int i = 0; i < topicosJson.length(); i++){
	        	topicos.add(topicosJson.optString(i));
	        	
	        }	        
	        
	        UsuarioInput user = new UsuarioInput(json.getString("usuario"),json.getString("usuario"), topicos);
						
			
			InetAddress IPAddress = receivedPacket.getAddress();
			int port = receivedPacket.getPort();
						
			LoginRequest request = new LoginRequest();
			request.setNome(user.getUsuario());
			request.setSenha(user.getSenha());
			request.setIPaddres(IPAddress.toString().replace("/", ""));
			
			LoginResponse responseLogin = new LoginResponse();
			ResponseType responseRegistraTopico = new ResponseType();
			
			ApiBanco apiBanco = new ApiBanco();
			responseLogin = apiBanco.login(request);
			System.out.println("IP conectado: "+IPAddress.toString().replace("/", ""));
			System.out.println("Porta conectada: "+port);
			
			Token token;
			System.out.println("Response token: "+responseLogin.getDsRetorno());
			if(responseLogin.getCdRetorno() == 0) {
				token = new Token(responseLogin.getToken());
				//responseRegistraTopico = apiBanco.registraTopico(user, token.gettoken());
				
				if(responseRegistraTopico.getCdRetorno() != 0 ) {
					throw new Exception("Erro ao inserir um dos topicos no banco!");
				}
			} else {
				token = new Token("");
			}
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(outputStream);
			os.writeObject(token);
			
			mensagemEnviada = outputStream.toByteArray();
			
			DatagramPacket sendPacket = new DatagramPacket(mensagemEnviada, mensagemEnviada.length, IPAddress, port);
			serverSocket.send(sendPacket);
			
			System.out.println("Token Enviado!");
			
			System.out.println("--------------------------------------");
			
		} catch (Exception e) {
			System.err.println("Exception thrown: " + e);
		} finally {
			this.shutdown();
		}
					
	}
	
}
