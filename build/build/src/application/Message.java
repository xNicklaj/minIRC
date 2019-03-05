package application;

import javafx.util.Callback;

import java.io.IOException;

import application.controller.MessageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Message extends Pane{
	private Node view;
	private MessageController controller;
	
	public Message()
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Message.fxml"));
		loader.setControllerFactory(new Callback<Class<?>, Object>()
		{
			@Override
			public Object call(Class<?> arg0) {
				return controller = new MessageController();
			}
			
		});
		/*loader.setController(new MessageController());
		controller = loader.getController();*/
		try {
			view = (Node) loader.load();
		}catch(IOException e) {
			e.printStackTrace();
		}
		getChildren().add(view);
	}
	
	public void setMessage(Text message)
	{
		controller.getMessageField().getChildren().setAll(message);
	}
	
	public void setMessage(String message)
	{
		controller.getMessageField().getChildren().setAll(new Text(message));
	}
	
	public void setUsername(String username)
	{
		controller.getUsernameField().setText(username);
	}
	
	public void setUsername(Text username)
	{
		controller.getUsernameField().setText(username.getText());
	}
	
	public String getUsername()
	{
		return controller.getUsernameField().getText();
	}
	
	public String getMessage()
	{
		return controller.getUsernameField().getText();
	}
}
