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
		controller.setSwitcher(this);
		controller.evaluateStoredServer();
		
		updater.setController(controller);
		updater.start();
		
		mainScene = new Scene(root, 1280, 720);
		try {
			this.setStyle(new URL("file:///" + PathFinder.getResourcePath("themes\\" + settings.getThemeName() + "\\scene.css")));
		} catch (MalformedURLException e1) {
			try {
				this.setStyle(new URL("file:///" + PathFinder.getResourcePath("themes\\ForestLight\\scene.css")));
			} catch (MalformedURLException e) {
				System.out.println(e.getClass().getName());
				e.printStackTrace();
			}
		}
		//mainScene.getStylesheets().add(getClass().getResource("css/forest_theme.css").toExternalForm());
		primaryStage.setScene(mainScene);
		primaryStage.show();
		return 0;
	}

	public void setStyle(URL urlToStyle)
	{
		this.mainScene.getStylesheets().clear();
		this.mainScene.getStylesheets().add(urlToStyle.toExternalForm());
	}
	
	public MainController getController()
	{
		return this.controller;
	}
	
}
