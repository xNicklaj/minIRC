package application;

import java.io.IOException;

import application.filemanager.PathFinder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SceneSwitcher {
	private Parent root;
	private Parent newConnectionParent = null;
	private Stage primaryStage;
	private Stage newConnectionStage;
	private Scene mainScene;
	private Scene newConnectionScene;
	
	public SceneSwitcher(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
		//primaryStage.initStyle(StageStyle.UNDECORATED);
		//primaryStage.setFullScreen(true);
		primaryStage.setResizable(false);
		primaryStage.setTitle("minIRC - Minimal IRC");
	}
	
	public SceneSwitcher() {
		
	}
	
	public int switchToMain()
	{
		root = null;
		System.out.println(PathFinder.getResourcePath(""));
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		root.setId("anchor");
		mainScene = new Scene(root, 1280, 720);
		mainScene.getStylesheets().add(getClass().getResource("css/mountain_theme.css").toExternalForm());
		primaryStage.setScene(mainScene);
		primaryStage.show();
		return 0;
	}
	
	public void createNewConnectionPanel()
	{
		newConnectionStage = new Stage();
		newConnectionParent = null;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AddConnection.fxml"));
		try {
			newConnectionParent = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		newConnectionParent.setId("anchor");
		newConnectionScene = new Scene(newConnectionParent, 384, 216);
		newConnectionStage.setResizable(false);
		newConnectionStage.setTitle("Aggiungi connessione");
		newConnectionStage.show();
		
	}
}
