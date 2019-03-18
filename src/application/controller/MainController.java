package application.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import application.network.NetworkManager;
import application.Message;
import application.SceneSwitcher;
import application.Server;
import application.filemanager.Settings;
import application.network.NetworkManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class MainController {

	public static Object connectionMutex = new Object();
	
	public static boolean resetAll;
	
	@FXML
	private JFXTextField connectionNameField;

	@FXML
	private JFXTextField usernameField;

	@FXML
	private JFXTextField IPField;

	@FXML
	private JFXTextField portField;

	@FXML
	private JFXButton addConnectionButton;

	@FXML
	private Text thisConnection;

	@FXML
	private JFXTextField inputField;

	@FXML
	private JFXButton sendButton;

	@FXML
	private ScrollPane chatPane;

	@FXML
	private ListView<?> connectionsList;
	
	@FXML
	private TextFlow message;

	@FXML
    private VBox chatPaneContent;
	
	@FXML
	private VBox serverList;
	
	@FXML
	private ScrollPane serverScrollpane;
	
	private Server currentServer;
	
	public ScrollPane getServerScrollpane() {
		return serverScrollpane;
	}

	private static NetworkManager manager;

	private Thread inboundThread;

	private static boolean connectionAddingException = false;

	private boolean validateIPAddress(String ipAddress) { 
		String[] tokens = ipAddress.split("\\.");
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

	private void addInternalConnection()
	{
		resetAll = false;
		if(!connectionNameField.getText().trim().isEmpty() && !usernameField.getText().trim().isEmpty() && !IPField.getText().trim().isEmpty() && !portField.getText().trim().isEmpty())
		{
			NetworkManager.setConnectionName(connectionNameField.getText());
			NetworkManager.setUsername(usernameField.getText());
			if(!validateIPAddress(IPField.getText()))
			{
				IPField.setStyle("-jfx-unfocus-color: rgba(244, 67, 54, 1);");
				connectionAddingException = true;	
			}
			NetworkManager.setIp(IPField.getText());
			try {
				NetworkManager.setPort(Integer.parseInt(portField.getText()));
			}catch(NumberFormatException e)
			{
				portField.setStyle("-jfx-unfocus-color: rgba(244, 67, 54, 1);");
				connectionAddingException = true;
			}
			if(connectionAddingException)
				return;
			
			Settings settings = new Settings();
			settings.addServer(connectionNameField.getText(), usernameField.getText(), IPField.getText(), portField.getText());
			
			connectionNameField.clear();
			usernameField.clear();
			IPField.clear();
			portField.clear();
			
			if(inboundThread != null)
			{
				manager.disconnect();
			}
			manager = new NetworkManager("inbound-thread");
			manager.start();
			
			try {
				synchronized(connectionMutex) {
					MainController.connectionMutex.wait();
				}
				if(resetAll)
					return;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			thisConnection.setText(NetworkManager.getConnectionName() + ", connesso come " + NetworkManager.getUsername());
			connectionAddingException = false;
			inputField.setEditable(true);
		}
		else
		{
			connectionAddingException = true;
			if(connectionNameField.getText().trim().isEmpty())
				connectionNameField.setStyle("-jfx-unfocus-color: rgba(244, 67, 54, 1);");
			if(usernameField.getText().trim().isEmpty())
				usernameField.setStyle("-jfx-unfocus-color: rgba(244, 67, 54, 1);");
			if(IPField.getText().trim().isEmpty())
				IPField.setStyle("-jfx-unfocus-color: rgba(244, 67, 54, 1);");
			if(portField.getText().trim().isEmpty())
				portField.setStyle("-jfx-unfocus-color: rgba(244, 67, 54, 1);");
		}
		this.evaluateStoredServer();
	}
	
	public void updateConnectionBar(boolean isConnected)
	{
		if(isConnected)
			thisConnection.setText(NetworkManager.getConnectionName() + ", connesso come " + NetworkManager.getUsername());
		else
			this.thisConnection.setText("non connesso");
	}

	@FXML
	private void addConnection(ActionEvent event) {
		this.addInternalConnection();
	}

	@FXML
	private void checkIfEmpty(KeyEvent event) {
		if(connectionAddingException)
		{
			if(event.getSource() == connectionNameField) {
				if(!connectionNameField.getText().trim().isEmpty())
					connectionNameField.setStyle("-jfx-unfocus-color: #000000;");
			}
			else if(event.getSource() == usernameField) {
				if(!usernameField.getText().trim().isEmpty())
					usernameField.setStyle("-jfx-unfocus-color: #000000;");
			}
			else if(event.getSource() == IPField) {
				if(!IPField.getText().trim().isEmpty())
					IPField.setStyle("-jfx-unfocus-color: #000000;");
			}
			else if(event.getSource() == portField) {
				if(!portField.getText().trim().isEmpty())
					portField.setStyle("-jfx-unfocus-color: #000000;");
			}
		}
		if(event.getCode() == KeyCode.ENTER)
			addInternalConnection();
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
		message.setUsername(NetworkManager.getUsername());
		message.setMessage(inputField.getText());
		manager.sendMessage(inputField.getText());
		chatPaneContent.getChildren().add(message);
		//chatPane.setContent(message);
		inputField.clear();
	}

	public void addExternalConnection(String servername, String username, String IP, String port)
	{
		NetworkManager.setConnectionName(servername);
		NetworkManager.setUsername(username);
		if(!validateIPAddress(IP))
		{
			connectionAddingException = true;	
		}
		NetworkManager.setIp(IP);
		try {
			NetworkManager.setPort(Integer.parseInt(port));
		}catch(NumberFormatException e)
		{
			connectionAddingException = true;
		}
		if(connectionAddingException)
			return;
		
		Settings settings = new Settings();
		settings.addServer(servername, username, IP, port);
		
		if(inboundThread != null)
		{
			manager.disconnect();
		}
		manager = new NetworkManager("inbound-thread");
		manager.setController(this);
		manager.start();
		
		
		try {
			synchronized(connectionMutex) {
				MainController.connectionMutex.wait();
			}
			if(resetAll)
				return;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		thisConnection.setText(NetworkManager.getConnectionName() + ", connesso come " + NetworkManager.getUsername());
		connectionAddingException = false;
		inputField.setEditable(true);
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
			server.setIP(settings.getServerList().get(i).getChildText("serverIP"));
			server.setPort(settings.getServerList().get(i).getChildText("serverport"));
			serverList.getChildren().add(server);
		}
		
	}
	
	public JFXTextField getInputField()
	{
		return inputField;
	}

}
