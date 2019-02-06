package application.filemanager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XMLLoader{
	private Document document;
	private String filename;
	
	public void createXML()
	{
		if(new File(PathFinder.getProjectPath() + filename).exists())
			return;
		
		XMLOutputter outputFile = new XMLOutputter();
		outputFile.setFormat(Format.getPrettyFormat());
		try {
			outputFile.output(document, new FileWriter(PathFinder.getProjectPath() + "settings.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Document getDocument()
	{
		return this.document;
	}
	
	public XMLLoader(String filename)
	{
		this.document = new Document();
		this.filename = filename;
	}
	
	
	public void createSettings()
	{
		if(new File(PathFinder.getProjectPath() + "settings.xml").exists())
			return;
		
		Element content = new Element("content");
		Document document = new Document(content);
		
		Element serverlist = new Element("serverlist");
		Element settings = new Element("settings");
		
		settings.addContent(new Element("theme", "forest"));
		
		document.getRootElement().addContent(serverlist);
		document.getRootElement().addContent(settings);
		
		XMLOutputter outputFile = new XMLOutputter();
		outputFile.setFormat(Format.getPrettyFormat());
		try {
			outputFile.output(document, new FileWriter(PathFinder.getProjectPath() + "settings.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
