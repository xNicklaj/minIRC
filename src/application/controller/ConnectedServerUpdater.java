package application.controller;

public class ConnectedServerUpdater implements Runnable{
	private String connectedServerName;
	private MainController controller;
	
	public ConnectedServerUpdater(String connectedServerName, MainController controller)
	{
		this.connectedServerName = connectedServerName;
		this.controller = controller;
	}
	
	public void run()
	{
		controller.setConnected(this.connectedServerName);
	}
}
