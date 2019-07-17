package application.controller;

import java.io.File;
import application.filemanager.PathFinder;
import javafx.application.Platform;

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
