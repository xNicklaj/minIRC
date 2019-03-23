package application.controller;

import application.filemanager.Settings;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

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
    
    private String password;
    
    public static MainController controller;
    
    public static boolean isConnected = false;
    
    @FXML
    public void toggleConnection(MouseEvent event) {
    	if(!isConnected)
    	{
    		controller.loginFromRecord(username.getText(), this.password, IP.getText(), port.getText());
    		this.connectIcon.setGlyphName("LAN_DISCONNECT");
    		isConnected = true;
    	}
    	else
    	{
    		this.connectIcon.setGlyphName("LAN_CONNECT");
    		//controller.updateConnectionBar(false);
    		isConnected = false;
    	}
    }
    
    public void setPassword(String password)
    {
    	this.password = password;
    }
    
    public String getServerHash()
    {
    	return "" + servername.getText().hashCode() + username.getText().hashCode() + IP.getText().hashCode() + port.getText().hashCode();
    }

    @FXML
    public void remove(MouseEvent event) {
    	Settings settings = new Settings();
    	for(int i = 0; i <= settings.getServerList().size(); i++)
    	{
    		if(settings.getServerList().get(i).getAttributeValue("id").equals(this.getServerHash()))
    		{
    			settings.removeServer(i);
    			controller.evaluateStoredServer();
    			return;
    		}
    	}
    	
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
