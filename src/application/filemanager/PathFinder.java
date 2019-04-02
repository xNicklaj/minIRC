package application.filemanager;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import application.Main;

public class PathFinder {
	private static Path projectPath = Paths.get(new File("").getAbsolutePath());
	
	public static String getProjectPath()
	{
		return projectPath + "\\";
	}
	
	public static String getJarResourcePath(String resource)
	{
		System.out.println("" + Main.class.getResource("/resources/" + resource));
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
	
}
