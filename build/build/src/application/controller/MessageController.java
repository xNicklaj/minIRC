package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class MessageController implements Initializable{

	@FXML
    private Text usernameField;

    @FXML
    private TextFlow messageField;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public Text getUsernameField()
	{
		return usernameField;
	}
	
	public TextFlow getMessageField()
	{
		return messageField;
	}

}
