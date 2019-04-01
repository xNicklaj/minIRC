package application.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import com.jfoenix.controls.JFXListView;

import application.filemanager.PathFinder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ThemesUpdater {
	private MainController controller;
	
	public void setController(MainController controller)
	{
		this.controller = controller;
	}
	
	public void updateInternalList()
	{
		JFXListView<String> list = controller.getThemesList();
		ArrayList<String> themes = new ArrayList<String>(Arrays.asList(new File(PathFinder.getResourcePath("\\themes")).list()));
		ObservableList<String> observable = FXCollections.observableArrayList(themes);
		list.setItems(observable);
	}
	
	
}
