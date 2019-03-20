package application.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import application.network.NetworkManager;
import application.Message;
import application.Server;
import application.filemanager.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainController {

	public static Object connectionMutex = new Object();

	public static boolean resetAll;

	@FXML
	private JFXTextField usernameField;

	@FXML
	private JFXTextField passwordField;

	@FXML
	private JFXTextField IPField;

	@FXML
	private JFXButton registerButton;

	@FXML
	private JFXButton loginButton;

	@FXML
	private Text thisConnection;

	@FXML
	private JFXTextField inputField;

	@FXML
	private JFXButton sendButton;

	@FXML
	private ScrollPane chatPane;

	@FXML
	private VBox chatPaneContent;

	@FXML
	private ScrollPane serverScrollpane;

	@FXML
	private VBox serverList;
	
	@FXML
    void login(ActionEvent event) {
		if(event.getSource() == loginButton)
			this.loginFromInput();
    }

    @FXML
    void register(ActionEvent event) {
    	if(event.getSource() == registerButton)
    		this.registerFromInput();
    }
	
	int port;

	private Server currentServer;

	public ScrollPane getServerScrollpane() {
		return serverScrollpane;
	}

	private static NetworkManager manager;

	private static boolean connectionAddingException = false;

	private boolean validateIPAddress(String ipAddress) { 
		String[] tokens = ipAddress.split("\\.");
		try
		{
			if(tokens[tokens.length - 1].contains(":"))
			{
				this.port = Integer.parseInt(tokens[tokens.length - 1].substring(tokens[tokens.length - 1].indexOf(":") + 1));
				tokens[tokens.length - 1] = tokens[tokens.length - 1].substring(0, tokens[tokens.length - 1].lastIndexOf(":"));
			}
			else
				port = 2332;
		}catch(NumberFormatException e)
		{
			IPField.setStyle("-jfx-unfocus-color: rgba(244, 67, 54, 1);");
			connectionAddingException = true;
			return false;
		}
		
		
		if (tokens.length != 4) { 
			return false;
		} 
		for (String str : tokens) {
			int i = Integer.parseInt(str);
			if ((i < 0) || (i > 255))
				return false; 
		} 
		return true;	
	}

	public void setReset(boolean reset)
	{
		this.resetAll = reset;
	}
	
	public void loginFromInput()
	{
		resetAll = false;
		if(!usernameField.getText().trim().isEmpty() && !IPField.getText().trim().isEmpty() && !passwordField.getText().trim().isEmpty())
		{
			if(manager != null)
			{
				manager.disconnect();
			}
			manager = new NetworkManager("inbound-thread@" + IPField.getText());
			
			manager.setUsername(usernameField.getText());
			
			if(!validateIPAddress(IPField.getText()))
			{
				IPField.setStyle("-jfx-unfocus-color: rgba(244, 67, 54, 1);");
				connectionAddingException = true;	
			}
			else
			{
				if(IPField.getText().contains(":"))
				{
					manager.setIp(IPField.getText().substring(0, IPField.getText().indexOf(":")));
					manager.setPort(this.port);
				}
				else
				{
					manager.setIp(IPField.getText());
					manager.setPort(2332);
				}
			}
			
			if(connectionAddingException)
				return;
			manager.setController(this);
			manager.rebindServer();
			try {
				manager.setConnectionName(new BufferedReader(new InputStreamReader(manager.getSocket().getInputStream())).readLine());
			} catch (IOException e1) {
				System.out.println(e1.getClass().getName());
				e1.printStackTrace();
			}
			
			try {
				PrintWriter writer = new PrintWriter(manager.getSocket().getOutputStream());
				writer.println("login");
				writer.println(manager.getUsername());
				writer.println(manager.getPassword());
			} catch (IOException e1) {
				System.out.println(e1.getClass().getName());
				e1.printStackTrace();
			}
			
			try {
				new BufferedReader(new InputStreamReader(manager.getSocket().getInputStream())).readLine();
			} catch (IOException e1) {
				System.out.println(e1.getClass().getName());
				e1.printStackTrace();
			}
			
			manager.start();
			
			Settings settings = new Settings();
			settings.addServer(manager.getUsername(), usernameField.getText(), passwordField.getText(), IPField.getText(), Integer.toString(this.port));
	
			usernameField.clear();
			passwordField.clear();
			IPField.clear();
	
			thisConnection.setText(manager.getConnectionName() + ", connesso come " + manager.getUsername());
			connectionAddingException = false;
			inputField.setEditable(true);
		}
		else
		{
			connectionAddingException = true;
			if(usernameField.getText().trim().isEmpty())
				usernameField.setStyle("-jfx-unfocus-color: rgba(244, 67, 54, 1);");
			if(passwordField.getText().trim().isEmpty())
				passwordField.setStyle("-jfx-unfocus-color: rgba(244, 67, 54, 1);");
			if(IPField.getText().trim().isEmpty())
				IPField.setStyle("-jfx-unfocus-color: rgba(244, 67, 54, 1);");
		}
		this.evaluateStoredServer();
	}

	public void loginFromRecord(String username, String password, String IP, String port)
	{
		if(manager != null)
		{
			manager.disconnect();
		}
		manager = new NetworkManager("inbound-thread@" + IP);
		
		manager.setUsername(username);
		manager.setIp(IP);
		manager.setPort(Integer.parseInt(port));
		
		if(connectionAddingException)
			return;
		manager.setController(this);
		manager.rebindServer();
		try {
			manager.setConnectionName(new BufferedReader(new InputStreamReader(manager.getSocket().getInputStream())).readLine());
		} catch (IOException e) {
			System.out.println(e.getClass().getName());
			e.printStackTrace();
		}
		
		try {
			PrintWriter writer = new PrintWriter(manager.getSocket().getOutputStream());
			writer.println("login");
			writer.println(manager.getUsername());
			writer.println(manager.getPassword());
		} catch (IOException e) {
			System.out.println(e.getClass().getName());
			e.printStackTrace();
		}
		
		try {
			new BufferedReader(new InputStreamReader(manager.getSocket().getInputStream())).readLine();
		} catch (IOException e) {
			System.out.println(e.getClass().getName());
			e.printStackTrace();
		}
		
		manager.start();
		
		Settings settings = new Settings();
		settings.addServer(manager.getUsername(), usernameField.getText(), passwordField.getText(), IPField.getText(), Integer.toString(this.port));
	
		usernameField.clear();
		passwordField.clear();
		IPField.clear();
	}

	public void registerFromInput()
	{
		resetAll = false;
		if(!usernameField.getText().trim().isEmpty() && !IPField.getText().trim().isEmpty() && !passwordField.getText().trim().isEmpty())
		{
			if(manager != null)
			{
				manager.disconnect();
			}
			manager = new NetworkManager("inbound-thread@" + IPField.getText());
			
			manager.setUsername(usernameField.getText());
			
			if(!validateIPAddress(IPField.getText()))
			{
				IPField.setStyle("-jfx-unfocus-color: rgba(244, 67, 54, 1);");
				connectionAddingException = true;	
			}
			else
			{
				if(IPField.getText().contains(":"))
				{
					manager.setIp(IPField.getText().substring(0, IPField.getText().indexOf(":")));
					manager.setPort(this.port);
				}
				else
				{
					manager.setIp(IPField.getText());
					manager.setPort(2332);
				}
			}
			
			if(connectionAddingException)
				return;
			manager.setController(this);
			manager.rebindServer();
			try {
				manager.setConnectionName(new BufferedReader(new InputStreamReader(manager.getSocket().getInputStream())).readLine());
			} catch (IOException e1) {
				System.out.println(e1.getClass().getName());
				e1.printStackTrace();
			}
			
			try {
				PrintWriter writer = new PrintWriter(manager.getSocket().getOutputStream(), true);
				writer.println("register");
				writer.println(manager.getUsername());
				writer.println(manager.getPassword());
				writer.flush();
			} catch (IOException e) {
				System.out.println(e.getClass().getName());
				e.printStackTrace();
			}
			
			try {
				new BufferedReader(new InputStreamReader(manager.getSocket().getInputStream())).readLine();
			} catch (IOException e1) {
				System.out.println(e1.getClass().getName());
				e1.printStackTrace();
			}
			
			manager.start();
			
			Settings settings = new Settings();
			settings.addServer(manager.getUsername(), usernameField.getText(), passwordField.getText(), IPField.getText(), Integer.toString(this.port));
	
			usernameField.clear();
			passwordField.clear();
			IPField.clear();
	
			thisConnection.setText(manager.getConnectionName() + ", connesso come " + manager.getUsername());
			connectionAddingException = false;
			inputField.setEditable(true);
		}
		else
		{
			connectionAddingException = true;
			if(usernameField.getText().trim().isEmpty())
				usernameField.setStyle("-jfx-unfocus-color: rgba(244, 67, 54, 1);");
			if(passwordField.getText().trim().isEmpty())
				passwordField.setStyle("-jfx-unfocus-color: rgba(244, 67, 54, 1);");
			if(IPField.getText().trim().isEmpty())
				IPField.setStyle("-jfx-unfocus-color: rgba(244, 67, 54, 1);");
		}
		this.evaluateStoredServer();
	}

	@FXML
	private void addConnection(ActionEvent event) {
		//this.addInternalConnection();
	}

	@FXML
	private void checkIfEmpty(KeyEvent event) {
		if(connectionAddingException)
		{
			if(event.getSource() == usernameField) {
				if(!usernameField.getText().trim().isEmpty())
					usernameField.setStyle("-jfx-unfocus-color: #000000;");
			}
			else if(event.getSource() == IPField) {
				if(!IPField.getText().trim().isEmpty())
					IPField.setStyle("-jfx-unfocus-color: #000000;");
			}
			else if(event.getSource() == passwordField) {
				if(!passwordField.getText().trim().isEmpty())
					passwordField.setStyle("-jfx-unfocus-color: #000000;");
			}
		}
		if(event.getCode() == KeyCode.ENTER);
			//addInternalConnection();
	}

	@FXML
	private void sendFromReturn(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER && !inputField.getText().trim().isEmpty())
		{
			this.sendMessage();
		}
	}

	@FXML
	private void sendMessage(ActionEvent event) {
		if(inputField.getText().trim().isEmpty())
			return;

		this.sendMessage();
	}

	private void sendMessage()
	{
		Message message = new Message();
		message.setUsername(manager.getUsername());
		message.setMessage(inputField.getText());
		manager.sendMessage(inputField.getText());
		chatPaneContent.getChildren().add(message);
		//chatPane.setContent(message);
		inputField.clear();
	}

	public Object getConnectionMutex()
	{
		return this.connectionMutex;
	}


	public void addMessage(String username, String text)
	{
		Message message = new Message();
		message.setUsername(username);
		message.setMessage(text);
		chatPaneContent.getChildren().add(message);
	}

	public void evaluateStoredServer()
	{
		Settings settings = new Settings();
		Server server = null;
		while(serverList.getChildren().size() != 0)
			serverList.getChildren().remove(0);
		for(int i = 0; i < settings.getServerList().size(); i++)
		{
			if(server != null)
				server = null;
			server = new Server();
			server.setUsername(settings.getServerList().get(i).getChildText("username"));
			server.setServerName(settings.getServerList().get(i).getChildText("servername"));
			server.setPassword(settings.getServerList().get(i).getChildText("password"));
			server.setIP(settings.getServerList().get(i).getChildText("serverIP"));
			server.setPort(settings.getServerList().get(i).getChildText("serverport"));
			serverList.getChildren().add(server);
		}

	}

	public JFXTextField getInputField()
	{
		return inputField;
	}
	
	public JFXTextField getPasswordField()
	{
		return passwordField;
	}

}
