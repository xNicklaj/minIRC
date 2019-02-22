package application.controller;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ServerController {

    @FXML
    private Text servername;

    @FXML
    private MaterialDesignIconView close_icon;

    @FXML
    private MaterialDesignIconView connectIcon;
    
    @FXML
    private Text username;

    @FXML
    private Text IP;

    @FXML
    private Text port;
    
    public static MainController controller;
    
    public static boolean isConnected = false;
    
    @FXML
    void connect(MouseEvent event) {
    	//controller.addExternalConnection(servername, username, IP, port);
    	isConnected = true;
    }

    @FXML
    void remove(MouseEvent event) {

    	isConnected = false;
    }

	public MaterialDesignIconView getClose_icon() {
		return close_icon;
	}

	public MaterialDesignIconView getConnectIcon() {
		return connectIcon;
	}

	public Text getServername() {
		return servername;
	}

	public Text getUsername() {
		return username;
	}

	public Text getIP() {
		return IP;
	}

	public Text getPort() {
		return port;
	}

	public static MainController getController() {
		return controller;
	}

	public static boolean isConnected() {
		return isConnected;
	}
 
}
