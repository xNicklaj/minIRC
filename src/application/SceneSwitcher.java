package application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import application.controller.MainController;
import application.controller.ServerController;
import application.controller.ThemesUpdater;
import application.filemanager.PathFinder;
import application.filemanager.Settings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.stage.Stage;

public class SceneSwitcher {
	private Parent root;
	private Stage primaryStage;
	private Scene mainScene;
	private ThemesUpdater updater;
	private MainController controller;
	
	public SceneSwitcher(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
		//primaryStage.initStyle(StageStyle.UNDECORATED);
		//primaryStage.setFullScreen(true);
		//primaryStage.setResizable(false);
		primaryStage.setTitle("minIRC - Minimal IRC");
		primaryStage.setResizable(false);
	}
	
	public SceneSwitcher() {
		super();
	}
	
	public int switchToMain()
	{
		root = null;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
		Settings settings = new Settings();
		updater = new ThemesUpdater();
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		root.setId("anchor");
		controller = (MainController) loader.getController();
		
		ServerController.controller = controller;
		
		controller.getInputField().setEditable(false);
		controller.getPasswordField().setAccessibleText(" ");
		controller.getServerScrollpane().setVbarPolicy(ScrollBarPolicy.NEVER);
		controller.getServerScrollpane().setHbarPolicy(ScrollBarPolicy.NEVER);
		controller.evaluateStoredServer();
		
		updater.setController(controller);
		
		System.out.println(PathFinder.getResourcePath("themes\\mountain\\scene.css"));
		mainScene = new Scene(root, 1280, 720);
		if(settings.getThemeName().equals("mountain"))
			try {
				mainScene.getStylesheets().add(new URL("file:///" + PathFinder.getResourcePath("themes\\mountain\\scene.css")).toExternalForm());
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		else if(settings.getThemeName().equals("forest"))
			try {
				mainScene.getStylesheets().add(new URL("file:///" + PathFinder.getResourcePath("themes\\forest\\scene.css")).toExternalForm());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//mainScene.getStylesheets().add(getClass().getResource("css/forest_theme.css").toExternalForm());
		primaryStage.setScene(mainScene);
		primaryStage.show();
		return 0;
	}
	
	public MainController getController()
	{
		return this.controller;
	}
	
}
