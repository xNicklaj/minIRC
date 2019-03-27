package application.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import com.jfoenix.controls.JFXListView;

import application.filemanager.PathFinder;

public class ThemesUpdater {
	private MainController controller;
	
	public void setController(MainController controller)
	{
		this.controller = controller;
	}
	
	public void updateInternalList()
	{
		JFXListView<?> list = controller.getThemesList();
		ArrayList<String> themes = new ArrayList<String>(Arrays.asList(new File(PathFinder.getResourcePath("\\themes")).list()));
		for(int i = 0; i < themes.size(); i++)
		{
			
		}
	}
	
	
}
