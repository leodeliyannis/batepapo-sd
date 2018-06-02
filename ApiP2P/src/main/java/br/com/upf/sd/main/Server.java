package br.com.upf.sd.main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import br.com.upf.sd.types.Token;
import br.com.upf.sd.types.UsuarioInput;

public class Server implements Runnable{
	
	private DatagramSocket serverSocket;
	
	private byte[] in;
	private byte[] out;
	
	public Server() throws SocketException{
		serverSocket = new DatagramSocket(10453);
	}

	public void run() {
		while(true){
			try {
				in = new byte[4048];
				out = new byte[1024];
				
				DatagramPacket receivedPacket = new DatagramPacket(in, in.length);
				serverSocket.receive(receivedPacket);
				

				byte[] data = receivedPacket.getData();
				ByteArrayInputStream in = new ByteArrayInputStream(data);
				ObjectInputStream is = new ObjectInputStream(in);
				
				UsuarioInput user = (UsuarioInput) is.readObject();
				System.out.println("Usuário: "+user.getUsuario());
				System.out.println("Senha: "+user.getSenha());
				
				InetAddress IPAddress = receivedPacket.getAddress();
				int port = receivedPacket.getPort();
				
				Token token = new Token("TESTE");
				
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				ObjectOutputStream os = new ObjectOutputStream(outputStream);
				os.writeObject(token);
				out = outputStream.toByteArray();
				
				DatagramPacket sendPacket = new DatagramPacket(out, out.length, IPAddress, port);
				serverSocket.send(sendPacket);
				
			} catch (Exception e) {
				System.err.println("Exception thrown: " + e.getLocalizedMessage());
			}
			
		}
	}
	
	
	
}
