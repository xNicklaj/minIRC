package application.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import com.jfoenix.controls.JFXListView;

import application.filemanager.PathFinder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ThemesUpdater extends Thread{
	private MainController controller;
	private boolean run;
	private boolean behaviour = false;

	public void setController(MainController controller)
	{
		this.controller = controller;
	}
	
	public void validateBehaviour()
	{
		if(!(new File(PathFinder.getJarResourcePath("themes")) == null))
			this.behaviour = true;
	}

	public void updateInternalList()
	{
		this.validateBehaviour();
		JFXListView<String> list = controller.getThemesList();
		ArrayList<String> temp = null;
		if(behaviour)
		{ 
			temp = new ArrayList<String>(Arrays.asList(new File(PathFinder.getResourcePath("themes")).list()));
			for(int i = 0; i < temp.size(); i++)
			{
				if(!new File(PathFinder.getResourcePath("themes") + "\\" +  temp.get(i) + "\\scene.css").exists())
				{
					temp.remove(i);
					i--;
				}
			}
		}
		else
		{
			System.out.println(Arrays.asList((new File(PathFinder.getJarResourcePath("themes")).listFiles())));
			temp = new ArrayList<String>(Arrays.asList(new File(PathFinder.getJarResourcePath("themes")).list()));
			for(int i = 0; i < temp.size(); i++)
			{
				if(!new File(PathFinder.getJarResourcePath("themes") + "/" +  temp.get(i) + "/scene.css").exists())
				{
					temp.remove(i);
					i--;
				}
			}
		}

		ArrayList<String> themes = temp;
		ObservableList<String> observable = FXCollections.observableArrayList(themes);
		list.setItems(observable);
	}

	public void run()
	{
		while(run)
		{
			this.updateInternalList();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e.getClass().getName());
				e.printStackTrace();
			}
		}
	}

	public void toggleRun()
	{
		this.run = !run;
	}


}
