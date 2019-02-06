package application;
	
import org.jdom2.Document;
import org.jdom2.Element;

import application.controller.MainController;
import application.filemanager.XMLLoadable;
import application.filemanager.XMLLoader;
import application.network.SocketInfo;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application implements XMLLoadable{
	private static XMLLoader settings;
	
	@Override
	public void start(Stage primaryStage) {
		settings = new XMLLoader("settings.xml");
		settings.createXML(this);
		SocketInfo.setUsername("Nicklaj");
		SocketInfo.setNamecolor("#ff0000");
		SocketInfo.setPort(3316);
		SocketInfo.setIp("127.0.0.1");
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
	
	public void XMLStructure()
	{
		Element content = new Element("content");
		settings.getDocument().setRootElement(content);
		
		Element serverlist = new Element("serverlist");
		Element settings = new Element("settings");
		
		settings.addContent(new Element("theme", "forest"));
		
		Main.settings.getDocument().getRootElement().addContent(serverlist);
		Main.settings.getDocument().getRootElement().addContent(settings);
	}
}
