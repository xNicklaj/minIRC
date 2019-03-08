package application.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import application.controller.MainController;

public class NetworkManager extends SocketInfo implements Runnable{
	private Socket socket;
	private String line;
	public static MainController controller;
	private Scanner scan;
	private PrintWriter writer;
	private int behaviour; 
	private static boolean run;
	
	public static final int OUTBOUND = 0;
	public static final int INBOUND = 1;
	
	public NetworkManager()
	{
		try {
			socket = new Socket(this.getIp(), this.getPort());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void rebindServer()
	{
		try {
			socket = new Socket(this.getIp(), this.getPort());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMessage()
	{
		while(true)
			try {
				writer = new PrintWriter(socket.getOutputStream(), true);
				writer.println(line);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void setBehaviour(int behaviour)
	{
		if(behaviour != 0 || behaviour != 1)
			return;
		
		this.behaviour = behaviour;
	}

	@Override
	public void run() {
		
	}
}
