package application;
	
import application.controller.MainController;
import application.filemanager.Settings;
import application.network.SocketInfo;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Main extends Application{
	
	@Override
	public void start(Stage primaryStage) {
		Settings settings = new Settings();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent e) {
	              System.exit(0);
	          }
	    });
		settings.createSettings();
		SocketInfo.setUsername("Nicklaj");
		SocketInfo.setNamecolor("#ff0000");
		SocketInfo.setPort(3316);
		SocketInfo.setIp("127.0.0.1");
		Settings test = new Settings();
		String[] path = new String[3];
		path[0] = "content";
		path[1] = "serverlist";
		
		//System.out.println(test.getSettings().getNodesList(path).get(0).getText());
		try {
			SceneSwitcher switcher = new SceneSwitcher(primaryStage);
			MainController.setSwitcher(switcher);
			switcher.switchToMain();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
