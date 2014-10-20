package edu.ustc.lightdoc.parserImpl;


import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import edu.ustc.lightdoc.entity.*;
import edu.ustc.lightdoc.parser.ICfgDOMHelper;


public class CfgDOMHelper implements ICfgDOMHelper{
	Manual manual = new Manual();
	Beta beat;
	List<Property> properties;
	List<Beta> beats;
	private Logger logger = Logger.getLogger(CfgDOMHelper.class);
	private String cfgUrl;
	public Document loadXML() {
		SAXReader saxReader = new SAXReader();
		try {
			if(cfgUrl == null){
				HttpServletRequest request = ServletActionContext.getRequest();
				String basePath = request.getSession().getServletContext().getRealPath("/")+"doc\\";
				this.cfgUrl = basePath+"lightdoc.cfg.xml";
			}
			Document document = saxReader.read(this.cfgUrl);
			logger.info("load xml success...");
			return document;
		} catch (DocumentException e) {
			System.out.println("配置文件没找到");
			logger.error("no matched confiuration file...");
			return null;
		}
	}
	public Manual getManual(){
		
		try {
			Document document = this.loadXML();
			Element root = document.getRootElement();
			Iterator<?> it1 = root.elementIterator();
			beats = new ArrayList<Beta>();
			while(it1.hasNext())
			{
				Element beatElement = (Element) it1.next();
				properties = new ArrayList<Property>();
				Iterator<?> it2 = beatElement.elementIterator("property");
				beat = new Beta();
				while(it2.hasNext())
				{
					
					Element propertyElement = (Element) it2.next();
					if(propertyElement.getName().equals("property"))
					{
						Property property = new Property();
						property.setName(propertyElement.attribute("name").getValue());
						property.setValue(propertyElement.getTextTrim());
						properties.add(property);
						
					}
					
				}
				  beats.add(beat);
				  beat.setProperties(properties);
			}
			
			manual.setBetas(beats);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get Manual error...");
		}
		return manual;
	}
	
	public boolean matchFile(String fileName){
		Manual m = (new CfgDOMHelper()).getManual();
		for(Beta beta:m.getBetas())
		{
			for(Property property:beta.getProperties())
			{
				if(property.getValue().equals(fileName))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean modifyXmlFile(String fileName,String checkValue) {
		try {
		/*	HttpServletRequest request = ServletActionContext.getRequest();
			String filePath = request.getSession().getServletContext().getRealPath("/")+"doc\\"+"lightdoc.cfg.xml";*/
			Document document = this.loadXML();
			Element root = document.getRootElement();
			Iterator<?> it1 = root.elementIterator();
			while (it1.hasNext()) {
				Element beatElement = (Element) it1.next();
				Iterator<?> it = beatElement.elementIterator();
				boolean flag = false;
				while (it.hasNext()) {
					Element propertyElement = (Element) it.next();
					if (propertyElement.getTextTrim().equals(fileName)) {
						flag = true;
					}
					if (flag){
						if (propertyElement.attribute("name").getValue()
								.equals("isCheck")) {
							propertyElement.setText(checkValue);
						}
					}
				}
				
			}
			writeToXml(this.cfgUrl, document);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("modify xmlFile error...");
			return false;
		}	
		
	}
	
	public boolean writeToXml(String fileName,Document document){
		try {
			
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("gb2312");
			XMLWriter writer = new XMLWriter(new OutputStreamWriter(
					new FileOutputStream(fileName)),format);
			writer.setEscapeText(false);
			writer.write(document);
			writer.flush();
			writer.close();
			logger.info("write to xml success...");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("write to xml error...");
		}
		return false;
	}

	@Override
	public String getUrlByFileName(String fileName) {
		String urlPath;
		try {
			Document document = this.loadXML();
			Element root = document.getRootElement();
			Iterator<?> it1 = root.elementIterator();
			while (it1.hasNext()) {
				Element beatElement = (Element) it1.next();
				Iterator<?> it = beatElement.elementIterator();
				boolean flag = false;
				while (it.hasNext()) {
					Element propertyElement = (Element) it.next();
					if (propertyElement.getTextTrim().equals(fileName)) {
						flag = true;
					}
					if(flag)
					{
						if (propertyElement.attribute("name").getValue()
								.equals("url")) {
							urlPath = propertyElement.getTextTrim();
							return urlPath;
						}
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("can not getURL by filename");
		}
		return null;			
	}

	@Override
	public String getCheckValueByFileName(String fileName) {
		String checkValue;
		try {
			Document document = this.loadXML();
			Element root = document.getRootElement();
			Iterator<?> it1 = root.elementIterator();
			while (it1.hasNext()) {
				Element beatElement = (Element) it1.next();
				Iterator<?> it = beatElement.elementIterator();
				boolean flag = false;
				while (it.hasNext()) {
					Element propertyElement = (Element) it.next();
					if (propertyElement.getTextTrim().equals(fileName)) {
						flag = true;
					}
					if(flag)
					{
						if (propertyElement.attribute("name").getValue()
								.equals("isCheck")) {
							checkValue = propertyElement.getTextTrim();
							logger.info("value check pass..");
							return checkValue;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("value check fail.");
		}
		return null;
	}
	
	public boolean addNode(String fileName,String url){
		try{
			/*HttpServletRequest request = ServletActionContext.getRequest();
			String filePath = request.getSession().getServletContext().getRealPath("/")+"doc\\"+"lightdoc.cfg.xml";*/
			Document document = this.loadXML();
			Element root = document.getRootElement();
			Element beta = root.addElement("beta");
			Element property1 = beta.addElement("property");
			property1.addAttribute("name", "fileName");
			property1.addText(fileName);
			Element property2 = beta.addElement("property");
			property2.addAttribute("name", "url");
			property2.addText(url);
			Element property3 = beta.addElement("property");
			property3.addAttribute("name", "isCheck");
			property3.addText("false");
			writeToXml(cfgUrl, document);
			logger.info("add node success..");
			return true;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("add node fail.");
			return false;
		}
	}
	/*
	 * get all Xmlfile`s information
	 * 
	 * */
	public List<Audit> getAllXMLFile(){
		Document doc = this.loadXML();
		ArrayList<Audit> listOfAudit = new ArrayList<Audit>();
		try{
			Element root = doc.getRootElement();
			Iterator<?> it = root.elementIterator();
			while(it.hasNext()){
				Element beatElement = (Element) it.next();
				Iterator<?> it2 = beatElement.elementIterator();
				Audit audit = new Audit();
				while(it2.hasNext()){
					Element propertyElement = (Element) it2.next();
					String value = propertyElement.attribute("name").getValue();
					if(value.equals("isCheck")){
						audit.setStatus(propertyElement.getTextTrim());
					}else if(value.equals("fileName")){
						audit.setFileName(propertyElement.getTextTrim());
					}else{
						audit.setUrl(propertyElement.getTextTrim());
					}
				}
				listOfAudit.add(audit);
			}
			logger.info("get all xml file success.");
			return listOfAudit;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("get all xml file fail");
			return null;
		}
		
	}
	
}
