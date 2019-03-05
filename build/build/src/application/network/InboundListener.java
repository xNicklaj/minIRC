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
	private static boolean run;

	public void startServer() throws IOException{
		if(server != null)
		{
			run = false;
			server.close();
			inboundSocket.close();
			server = null;
			inboundSocket = null;
			server = new ServerSocket(this.getPort());
		}
		else
		{
			server = new ServerSocket(this.getPort());
		}
		inboundSocket = server.accept();

		Scanner in = new Scanner(inboundSocket.getInputStream());
		run = true;
		while(run) {
			try {
				line = in.nextLine();
				controller.addMessage(line.substring(0, line.indexOf("/")), line.substring(line.indexOf("/"), line.lastIndexOf("/")));
			} catch (NoSuchElementException e) {
				
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
