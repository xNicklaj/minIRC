package application.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import application.controller.MainController;

public class NetworkManager extends Thread{
	private Socket socket;
	private BufferedReader scan;
	private PrintWriter writer;

	private Object mutex;
	private boolean run;

	public MainController controller;
	
	private static int port;
	private static String connectionName;
	private static String username;
	private static String namecolor;
	private static String ip;

	public NetworkManager(String name)
	{
		this.setName(name);
	}
	
	public void setController(MainController controller)
	{
		this.controller = controller;
	}

	public void rebindServer()
	{
		try {
			socket = new Socket(this.getIp(), this.getPort());
			writer = new PrintWriter(socket.getOutputStream(), true);
			writer.println(NetworkManager.username);
		} catch (UnknownHostException e) {
			controller.setReset(true);
			this.run = false;
		} catch (IOException e) {
			controller.setReset(true);
			this.run = false;
		} finally
		{
			synchronized(controller.getConnectionMutex())
			{
				controller.getConnectionMutex().notifyAll();	
			}
		}
	}
	
	public Object getMutex()
	{
		return this.mutex;
	}

	public void sendMessage(String msg)
	{
		try {
			writer = new PrintWriter(socket.getOutputStream(), true);
			writer.println(msg);
			//writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void disconnect()
	{
		this.run = false;
	}

	@Override
	public void run() {
		run = true;
		this.rebindServer();
		try {
			String msg;
			if(run)
				scan = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(run)
			{
				msg = scan.readLine();
				if(run)
					controller.addMessage(msg.substring(0, msg.indexOf("/")), msg.substring(msg.indexOf("/"), msg.lastIndexOf("/")));
			}
			if(scan != null)
				scan.close();
		} catch (IOException e) {
			System.out.println(e.getClass().getName());
			e.printStackTrace();
		}
	}
	
	public static String getConnectionName() {
		return connectionName;
	}
	public static void setConnectionName(String connectionName) {
		NetworkManager.connectionName = connectionName;
	}
	
	public static String getUsername() {
		return username;
	}
	public static void setUsername(String username) {
		NetworkManager.username = username;
	}
	
	public static String getNamecolor() {
		return namecolor;
	}
	public static void setNamecolor(String namecolor) {
		NetworkManager.namecolor = namecolor;
	}
	
	public int getPort() {
		return port;
	}
	public static void setPort(int port) {
		NetworkManager.port = port;
	}
	
	public String getIp() {
		return ip;
	}
	public static void setIp(String ip) {
		NetworkManager.ip = ip;
	}
}
