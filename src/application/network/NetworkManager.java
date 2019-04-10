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
import application.controller.ServerController;
import application.filemanager.PathFinder;
import application.filemanager.Settings;
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
			if(new Settings().getLogginglevel() >= 1)
				PathFinder.pushToLogs("Connected to " + this.getIp());
			writer = new PrintWriter(socket.getOutputStream(), true);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException e) {
			controller.setReset(true);
			this.run = false;
			if(new Settings().getLogginglevel() >= 1)
				PathFinder.pushToLogs("Error attempting to connect to " + this.getIp());
		} catch (IOException e) {
			controller.setReset(true);
			this.run = false;
			if(new Settings().getLogginglevel() >= 1)
				PathFinder.pushToLogs("Error attempting to connect to " + this.getIp());
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
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.controller.getInputField().setText("");
		this.controller.getInputField().setEditable(false);
		Platform.runLater(new ConnectedServerUpdater("Non connesso", controller));
		new ConnectedServerUpdater("non connesso", ServerController.controller).run();
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
				if(!run) continue;
				message = reader.readLine();
				if(!run) continue;
				if(username.equals("admin") && message.substring(0, 3).equals("0xc"))
				{
					switch(message)
					{
					case "0xc0001":
						if(new Settings().getLogginglevel() >= 1)
							PathFinder.pushToLogs("Connection forcibly closed by remote host");
						this.disconnect();
						break;
					}
				}
				else
				{
					Platform.runLater(new Buffer(username, message, controller));
					if(new Settings().getLogginglevel() >= 2)
						PathFinder.pushToLogs(username + ": " + message);
				}
			}
			socket.close();
			socket = null;
		} catch (IOException e) {
			if(new Settings().getLogginglevel() >= 1)
				PathFinder.pushToLogs("Disconnected from " + this.getIp());
			this.controller.getInputField().setEditable(false);
			Platform.runLater(new ConnectedServerUpdater("Non connesso", controller));
			new ConnectedServerUpdater("non connesso", ServerController.controller).run();
		}
	}
}
