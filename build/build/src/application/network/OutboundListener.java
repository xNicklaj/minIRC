package application.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import application.controller.MainController;

public class OutboundListener extends SocketInfo implements Runnable{
	private Socket socket;
	private PrintWriter out;
	private String msg;
	private Object mutex = new Object();

	@SuppressWarnings("static-access")
	public void send() {
		try {
			out.print(this.getUsername() + "/" + this.msg + "/");
			out.flush();
			out.close();
			socket.close();
		} catch (IOException e) {}

	}

	public void sendMessage(String msg) {
		this.msg = msg;
		synchronized(mutex)
		{
			mutex.notifyAll();
		}
	}

	private void prepareServer()
	{
		synchronized(MainController.connectionMutex)
		{
			try {
				socket = new Socket(this.getIp(), this.getPort());
				out = new PrintWriter(socket.getOutputStream());
			} catch (UnknownHostException e) {
				MainController.resetAll = true;
			} catch (IOException e) {
				MainController.resetAll = true;
			}
			finally {
				MainController.connectionMutex.notifyAll();				
			}
		}
	}

	public void run()
	{
		this.prepareServer();
		synchronized(mutex)
		{
			while(true)
				try {
					mutex.wait();
					send();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
