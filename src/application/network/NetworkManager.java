package application.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import application.controller.MainController;

public class NetworkManager extends Thread{
	private Socket socket;
	private BufferedReader scan;
	private PrintWriter writer;

	private Object mutex;
	private boolean run;

	public MainController controller;
	
	private int port;
	private String connectionName;
	private String username;
	private String password;
	public String getPassword() {
		return password;
	}

	public String getConnectionName() {
		return connectionName;
	}

	public Socket getSocket() {
		return socket;
	}

	public  String getUsername() {
		return username;
	}

	public int getPort() {
		return port;
	}

	public Object getMutex()
	{
		return this.mutex;
	}

	public String getIp() {
		return ip;
	}

	public void setController(MainController controller)
	{
		this.controller = controller;
	}

	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	private String ip;

	public NetworkManager(String name)
	{
		this.setName(name);
	}
	
	public void rebindServer()
	{
		try {
			socket = new Socket(this.getIp(), this.getPort());
			writer = new PrintWriter(socket.getOutputStream(), true);
			writer.println(this.username);
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
		if(socket == null)
			System.err.println("You need to first bind the socket");
		try {
			String msg;
			if(run)
				scan = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(run)
			{
				msg = scan.readLine();
				if(run)
					controller.addMessage("a" /*TODO: insert username*/, msg);
			}
			if(scan != null)
				scan.close();
		} catch (IOException e) {
			System.out.println(e.getClass().getName());
			e.printStackTrace();
		}
	}
}
