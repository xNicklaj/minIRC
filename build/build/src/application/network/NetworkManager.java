package application.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import application.controller.Buffer;
import application.controller.ConnectedServerUpdater;
import application.controller.MainController;
import javafx.application.Platform;

public class NetworkManager extends Thread{
	private Socket socket;
	private BufferedReader reader;
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
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException e) {
			controller.setReset(true);
			this.run = false;
		} catch (IOException e) {
			controller.setReset(true);
			this.run = false;
		}
	}
	
	public void sendMessage(String msg)
	{
		try {
			writer = new PrintWriter(socket.getOutputStream(), true);
			writer.println(msg);
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
			String username;
			String message;
			while(run)
			{
				username = reader.readLine();
				message = reader.readLine();
				if(username.equals("admin") && message.substring(0, 3).equals("0xc"))
				{
					switch(message)
					{
					case "0xc01":
						this.disconnect();
						this.controller.getInputField().setEditable(false);
						Platform.runLater(new ConnectedServerUpdater("Non connesso", controller));
						break;
					}
				}
				Platform.runLater(new Buffer(username, message, controller));
			}
			if(reader != null)
				reader.close();
			if(writer != null)
				writer.close();
		} catch (IOException e) {
			System.exit(-1);
		}
	}
}
