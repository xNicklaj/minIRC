package application.network;

public class SocketInfo {
	private static int port;
	private static String ip;
	private static String username;
	private static String namecolor;
	
	public static String getUsername() {
		return username;
	}
	public static void setUsername(String username) {
		SocketInfo.username = username;
	}
	public static String getNamecolor() {
		return namecolor;
	}
	public static void setNamecolor(String namecolor) {
		SocketInfo.namecolor = namecolor;
	}
	public int getPort() {
		return port;
	}
	public static void setPort(int port) {
		SocketInfo.port = port;
	}
	public String getIp() {
		return ip;
	}
	public static void setIp(String ip) {
		SocketInfo.ip = ip;
	}
	
	
}
