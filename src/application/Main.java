package application;
	
import application.filemanager.PathFinder;
import application.filemanager.Settings;
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
		PathFinder pf = new PathFinder();
		pf = null;
		
		Settings settings = new Settings();
		settings.createSettings();
		Font.loadFont(PathFinder.getResourcePath("shared\\fonts\\RobotoMedium.ttf"), 14);
		
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
