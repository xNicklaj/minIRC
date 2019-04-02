package application.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import com.jfoenix.controls.JFXListView;

import application.filemanager.PathFinder;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ThemesUpdater extends Thread{
	private MainController controller;
	private boolean run;
	private boolean behaviour;
	private ThemeUpdaterRunnable runnable;
	
	public ThemesUpdater()
	{
		this.setName("themes-updater");
		this.run = true;
	}

	public void setController(MainController controller)
	{
		this.controller = controller;
		this.runnable = new ThemeUpdaterRunnable(controller);
	}
	
	public void validateBehaviour()
	{
		if(!(new File(PathFinder.getJarResourcePath("themes")) == null))
			this.behaviour = true;
	}

	public void run()
	{
		while(run)
		{
			Platform.runLater(runnable);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}
	
	public void startUpdater()
	{
	}

	public void toggleRun()
	{
		this.run = !run;
	}
}
