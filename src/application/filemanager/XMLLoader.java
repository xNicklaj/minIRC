package application.filemanager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import application.Main;

public class XMLLoader{
	private Document document;
	private String filename;
	
	public void FXMLStructure()
	{
		
	}
	
	public void createXML(XMLLoadable object)
	{
		object.XMLStructure();
		if(new File(PathFinder.getProjectPath() + filename).exists())
			return;
		
		this.updateXML();
	}
	
	private void updateXML()
	{
		XMLOutputter outputFile = new XMLOutputter();
		outputFile.setFormat(Format.getPrettyFormat());
		try {
			outputFile.output(document, new FileWriter(PathFinder.getProjectPath() + "settings.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setLayout()
	{
		FXMLStructure();
	}
	
	public Document getDocument()
	{
		return this.document;
	}
	
	public void addNode(String[] parentNodes, String childNode, String value)
	{
		Element childElement = new Element(childNode);
		childElement.setText(value);
		
		Element parent = document.getRootElement();
		
		int i = 0;
		if(parentNodes[0].equals(this.document.getRootElement().getName()))
			i++;
		for( ; i < parentNodes.length; i++)
			parent = parent.getChild(parentNodes[i]);
		
		parent.addContent(childElement);
		this.updateXML();
	}
	
	private boolean isStringChild(Element child, String childName)
	{
		if(child.getName().equals(childName))
			return true;
		
		return false;
	}
	
	public XMLLoader(String filename)
	{
		this.document = new Document();
		this.filename = filename;
	}
}
