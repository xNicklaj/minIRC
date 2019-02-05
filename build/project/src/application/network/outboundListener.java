package application.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class outboundListener extends SocketInfo implements Runnable{
	private Socket socket;
	private PrintWriter out;
	private String msg;
	private Object mutex = new Object();

	@SuppressWarnings("static-access")
	public void send() {
		try {
			out.print(this.getUsername() + " /" + this.msg + "/");
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
		try {
			socket = new Socket(this.getIp(), this.getPort());
			out = new PrintWriter(socket.getOutputStream());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
