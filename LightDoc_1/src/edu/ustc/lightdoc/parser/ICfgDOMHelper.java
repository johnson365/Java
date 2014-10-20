package edu.ustc.lightdoc.parser;

import java.util.List;

import org.dom4j.Document;

import edu.ustc.lightdoc.entity.Audit;
import edu.ustc.lightdoc.entity.Manual;

public interface ICfgDOMHelper {

	public abstract Manual getManual();
	
	public abstract Document loadXML();

	public abstract boolean matchFile(String fileName);

	public abstract boolean modifyXmlFile( String fileName,String checkValue);

	public abstract boolean writeToXml(String fileName, Document document);

	public abstract String getUrlByFileName(String fileName);

	public abstract String getCheckValueByFileName(String fileName);
	
	public abstract List<Audit> getAllXMLFile();
	
	public abstract boolean addNode(String fileName,String url);
	//public abstract String getCheckValueByFileNameForAudit(String fileName);

}