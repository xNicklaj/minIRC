package application.filemanager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import application.Main;

public class PathFinder {
	private static Path projectPath = Paths.get(new File("").getAbsolutePath());
	private static PrintWriter printer;
	
	public PathFinder()
	{
		try {
			printer = new PrintWriter(new FileWriter(new File("log.txt")));
		} catch (IOException e) {

		}
	}
	
	public static String getProjectPath()
	{
		return projectPath + "\\";
	}
	
	public static String getJarResourcePath(String resource)
	{
		return "" + Main.class.getResource("/resources/" + resource);
	}
	
	public static String getResourcePath(String resource)
	{
		return getProjectPath()+ "resources\\" + resource;
	}
	
	public static String getThemePath(String theme)
	{
		return getProjectPath() + "themes\\" + theme + "\\";
	}
	
	public synchronized static void pushToLogs(String text)
	{
		if(printer == null)
			try {
				printer = new PrintWriter(new FileWriter(new File("log.txt")));
			} catch (IOException e) {
				System.out.println(e.getClass().getName());
				e.printStackTrace();
			}
		
		printer.println("[" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()) + "] " + text);
		printer.flush();
	}
	
}
