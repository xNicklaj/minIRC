package application.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import application.controller.MainController;


public class InboundListener extends SocketInfo implements Runnable{
	private ServerSocket server;
	private Socket inboundSocket;
	private String line;
	public static MainController controller;
	
	public void startServer() throws IOException{
		server = new ServerSocket(this.getPort());
		inboundSocket = server.accept();
		
		Scanner in = new Scanner(inboundSocket.getInputStream());
		
		while(true) {
			try {
				line = in.nextLine();
				controller.addMessage(line.substring(0, line.indexOf("/")), line.substring(line.indexOf("/"), line.lastIndexOf("/")));
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
