package application;
	
import application.controller.MainController;
import application.filemanager.PathFinder;
import application.filemanager.Settings;
import application.network.SocketInfo;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Main extends Application{
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent e) {
	              System.exit(0);
	          }
	    });
		
		Settings settings = new Settings();
		settings.createSettings();
		Font.loadFont(PathFinder.getResourcePath("shared\\fonts\\RobotoMedium.ttf"), 14);
		
		SocketInfo.setUsername("Nicklaj");
		SocketInfo.setNamecolor("#ff0000");
		SocketInfo.setPort(3316);
		SocketInfo.setIp("127.0.0.1");
		
		try {
			SceneSwitcher switcher = new SceneSwitcher(primaryStage);
			switcher.switchToMain();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
