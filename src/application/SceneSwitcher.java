package application;

import java.io.IOException;

import application.controller.MainController;
import application.network.InboundListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneSwitcher {
	private Parent root;
	private Stage primaryStage;
	private Scene mainScene;
	private MainController controller;
	
	public SceneSwitcher(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
		//primaryStage.initStyle(StageStyle.UNDECORATED);
		//primaryStage.setFullScreen(true);
		primaryStage.setResizable(false);
		primaryStage.setTitle("minIRC - Minimal IRC");
	}
	
	public SceneSwitcher() {
		super();
	}
	
	public int switchToMain()
	{
		root = null;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		root.setId("anchor");
		controller = (MainController) loader.getController();
		InboundListener.controller = controller;
		controller.getInputField().setEditable(false);
		mainScene = new Scene(root, 1280, 720);
		mainScene.getStylesheets().add(getClass().getResource("css/mountain_theme.css").toExternalForm());
		primaryStage.setScene(mainScene);
		primaryStage.show();
		return 0;
	}
	
	public MainController getController()
	{
		return this.controller;
	}
	
}
