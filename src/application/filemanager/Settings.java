package application.filemanager;

import org.jdom2.Element;

public class Settings implements XMLLoadable{
	private XMLLoader settings;
	
	public Settings()
	{
		settings = new XMLLoader("settings.xml");
	}
		
	public XMLLoader getSettings()
	{
		return this.settings;
	}
	
	public void createSettings()
	{
		this.settings.createXML(this);
	}
	
	public void addServer(String servername, String username, String serverIP, String serverport)
	{
		String[] parentNodes = new String[3];
		parentNodes[0] = "content";
		parentNodes[1] = "serverlist";
		parentNodes[2] = "server";
		this.settings.addNode(parentNodes, "servername" , servername);
	}
	
	public void XMLStructure()
	{
		Element content = new Element("content");
		settings.getDocument().setRootElement(content);
		
		Element serverlist = new Element("serverlist");
		Element settings = new Element("settings");
		
		settings.addContent(new Element("theme"));
		settings.getChild("theme").setText("forest");
		
		this.settings.getDocument().getRootElement().addContent(serverlist);
		this.settings.getDocument().getRootElement().addContent(settings);
	}
}
