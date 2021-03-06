package application.filemanager;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Attribute;
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
	
	public void removeServer(int i)
	{
		Element parent = this.getSettings().getNodePointer(new String[] {"content", "serverlist"});
		
		parent.removeContent(parent.getChildren().get(i));
		this.settings.updateXML();
	}
	
	public void createSettings()
	{
		this.settings.createXML(this);
	}
	
	public void addServer(String servername, String username, String password, String serverIP, String serverport)
	{
		String[] parentNodes = new String[2];
		String[] content = new String[4];
		String[] childName = new String[4];
		
		content[0] = servername;
		content[1] = username;
		content[2] = serverIP;
		content[3] = serverport;
		
		childName[0] = "servername";
		childName[1] = "username";
		childName[2] = "serverIP";
		childName[3] = "serverport";
		
		parentNodes[0] = "content";
		parentNodes[1] = "serverlist";
		
		Element server = new Element("server");
		Attribute ID = new Attribute("id", "" + servername.hashCode() + username.hashCode() + serverIP.hashCode() + serverport.hashCode());
		server.setAttribute(ID);
		
		Element serverName = new Element("servername");
		serverName.setText(servername);
		
		Element userName = new Element("username");
		userName.setText(username);
		
		Element passWord = new Element("password");
		passWord.setText(password);
		
		Element serverip = new Element("serverIP");
		serverip.setText(serverIP);
		
		Element serverPort = new Element("serverport");
		serverPort.setText(serverport);
		
		server.addContent(serverName);
		server.addContent(userName);
		server.addContent(passWord);
		server.addContent(serverip);
		server.addContent(serverPort);
		
		
		//this.settings.addNode(parentNodes, childName, content);
		if(isIDAvailable("" + servername.hashCode() + username.hashCode() + serverIP.hashCode() + serverport.hashCode()))
			this.settings.addNode(parentNodes, server);
		
	}
	
	private boolean isIDAvailable(String ID)
	{
		for(int i = 0; i < settings.getNodesNumber(new String[]{"content", "serverlist"}); i++)
		{
			if(settings.getNodesList(new String[]{"content", "serverlist"}).get(i).getAttribute("id").getValue().equals(ID))
				return false;
		}
		
		return true;
		
	}
	
	public String getThemeName()
	{
		for(int i = 0; i <  this.settings.getNodesNumber(new String[]{"content", "settings"}); i++)
			if(this.settings.getNodesList(new String[]{"content", "settings"}).get(i).getName().equals("theme"))
				return this.settings.getNodesList(new String[]{"content", "settings"}).get(i).getText();
		
		return null;
	}

	public void setThemeName(String name)
	{
		for(int i = 0; i <  this.settings.getNodesNumber(new String[]{"content", "settings"}); i++)
			if(this.settings.getNodesList(new String[]{"content", "settings"}).get(i).getName().equals("theme"))
				this.settings.getNodesList(new String[]{"content", "settings"}).get(i).setText(name);

		this.settings.updateXML();
	}
	
	public List<Element> getServerList()
	{
		List<Element> list = new ArrayList<Element>();
		for(int i = 0; i < settings.getNodesNumber(new String[] {"content", "serverlist"}); i++)
		{
			list.add(settings.getNodesList(new String[] {"content", "serverlist"}).get(i));
		}
		
		return list;
	}
	
	public int getLogginglevel()
	{
		for(int i = 0; i <  this.settings.getNodesNumber(new String[]{"content", "settings"}); i++)
			if(this.settings.getNodesList(new String[]{"content", "settings"}).get(i).getName().equals("logginglevel"))
				return Integer.parseInt(this.settings.getNodesList(new String[]{"content", "settings"}).get(i).getText());
		
		return 2;
	}
	
	public void XMLStructure()
	{
		Element content = new Element("content");
		settings.getDocument().setRootElement(content);
		
		Element serverlist = new Element("serverlist");
		Element settings = new Element("settings");
		
		settings.addContent(new Element("theme"));
		settings.getChild("theme").setText("ForestLight");
		
		settings.addContent(new Element("logginglevel"));
		settings.getChild("logginglevel").setText("1");
		
		this.settings.getDocument().getRootElement().addContent(serverlist);
		this.settings.getDocument().getRootElement().addContent(settings);
	}
}
