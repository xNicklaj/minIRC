package application.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import sun.net.util.IPAddressUtil;

import application.network.outboundListener;
import application.network.SocketInfo;
import application.network.inboundListener;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

public class MainController {

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

	private static outboundListener outbound = new outboundListener(); 

	private static inboundListener inbound = new inboundListener();

	private static SceneSwitcher switcher;

	private static Thread outboundThread;

	private static Thread inboundThread;

	private static boolean connectionAddingException = false;

	public static void setSwitcher(SceneSwitcher switcher)
	{
		MainController.switcher = switcher;
	}

	public boolean validateIPAddress(String ipAddress) { 
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

	private void addConnection()
	{
		if(!connectionNameField.getText().trim().isEmpty() && !usernameField.getText().trim().isEmpty() && !IPField.getText().trim().isEmpty() && !portField.getText().trim().isEmpty())
		{
			SocketInfo.setConnectionName(connectionNameField.getText());
			SocketInfo.setUsername(usernameField.getText());
			if(!validateIPAddress(IPField.getText()))
			{
				IPField.setStyle("-jfx-unfocus-color: rgba(244, 67, 54, 1);");
				connectionAddingException = true;	
			}
			SocketInfo.setIp(IPField.getText());
			try {
				SocketInfo.setPort(Integer.parseInt(portField.getText()));
			}catch(NumberFormatException e)
			{
				portField.setStyle("-jfx-unfocus-color: rgba(244, 67, 54, 1);");
				connectionAddingException = true;
			}
			if(connectionAddingException)
				return;
			connectionNameField.clear();
			usernameField.clear();
			IPField.clear();
			portField.clear();
			outboundThread = new Thread(outbound);
			inboundThread = new Thread(inbound);
			outboundThread.start();
			inboundThread.start();
			thisConnection.setText(SocketInfo.getConnectionName() + ", connesso come " + SocketInfo.getUsername());
			connectionAddingException = false;
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
	}

	@FXML
	void addConnection(ActionEvent event) {
		if(!connectionNameField.getText().trim().isEmpty() && !usernameField.getText().trim().isEmpty() && !IPField.getText().trim().isEmpty() && !portField.getText().trim().isEmpty())
		{
			SocketInfo.setConnectionName(connectionNameField.getText());
			SocketInfo.setUsername(usernameField.getText());
			if(!validateIPAddress(IPField.getText()))
			{
				IPField.setStyle("-jfx-unfocus-color: rgba(244, 67, 54, 1);");
				connectionAddingException = true;	
			}
			SocketInfo.setIp(IPField.getText());
			try {
				SocketInfo.setPort(Integer.parseInt(portField.getText()));
			}catch(NumberFormatException e)
			{
				portField.setStyle("-jfx-unfocus-color: rgba(244, 67, 54, 1);");
				connectionAddingException = true;	
			}
			if(connectionAddingException)
				return;
			connectionNameField.clear();
			usernameField.clear();
			IPField.clear();
			portField.clear();
			outboundThread = new Thread(outbound);
			inboundThread = new Thread(inbound);
			outboundThread.start();
			inboundThread.start();
			thisConnection.setText(SocketInfo.getConnectionName() + ", connesso come " + SocketInfo.getUsername());
			connectionAddingException = false;
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

	}

	@FXML
	void checkIfEmpty(KeyEvent event) {
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
			addConnection();
	}

	@FXML
	void sendFromReturn(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER)
		{
			outbound.sendMessage(inputField.getText());
			inputField.clear();
		}
	}

	@FXML
	void sendMessage(ActionEvent event) {
		outbound.sendMessage(inputField.getText());
		inputField.clear();
	}

}
