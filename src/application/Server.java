package application;

import javafx.util.Callback;

import java.io.IOException;

import application.controller.ServerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class Server extends Pane{
	private Node view;
	private ServerController controller;
	
	public Server()
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Server.fxml"));
		loader.setControllerFactory(new Callback<Class<?>, Object>()
		{
			@Override
			public Object call(Class<?> arg0) {
				return controller = new ServerController();
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
	
	public void setServerName(String servername)
	{
		controller.getServername().setText(servername);
	}
	
	public void setUsername(String username)
	{
		controller.getUsername().setText(username);
	}
	
	public String getUsername()
	{
		return controller.getUsername().getText();
	}
	
	public String getServername()
	{
		return controller.getServername().getText();
	}
	
	public void setIP(String IP)
	{
		controller.getIP().setText(IP);
	}
	
	public String getIP()
	{
		return controller.getIP().getText();
	}
	
	public void setPort(String port)
	{
		controller.getPort().setText(port);
	}
	
	public String getPort()
	{
		return controller.getPort().getText();
	}
}
