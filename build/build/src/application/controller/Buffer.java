package application.controller;

public class Buffer implements Runnable{
	private String username;
	private String message;
	private MainController controller;
	
	public Buffer(String username, String message, MainController controller)
	{
		this.username = username;
		this.message = message;
		this.controller = controller;
	}
	
	public void run()
	{
		controller.addMessage(username, message);
	}
}
