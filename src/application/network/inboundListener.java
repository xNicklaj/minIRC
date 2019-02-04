package application.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class inboundListener extends SocketInfo implements Runnable{
	private ServerSocket server;
	private Socket inboundSocket;
	private String line;
	
	public void startServer() throws IOException{
		server = new ServerSocket(this.getPort());
		inboundSocket = server.accept();
		
		Scanner in = new Scanner(inboundSocket.getInputStream());
		
		while(true) {
			try {
				line = in.nextLine();
			} catch (NoSuchElementException e) {
				inboundSocket.close();
				inboundSocket = server.accept();
				in.close();
				in = new Scanner(inboundSocket.getInputStream());
			}
		}
	}
	
	public void run()
	{
		try {
			this.startServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
