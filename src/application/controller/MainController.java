package application.controller;

import java.awt.Event;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import application.network.outboundListener;
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

	public static void setSwitcher(SceneSwitcher switcher)
	{
		MainController.switcher = switcher;
	}

	@FXML
	void addConnection(ActionEvent event) {
		connectionNameField.clear();
		usernameField.clear();
		IPField.clear();
		portField.clear();
		
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
