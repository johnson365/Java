package edu.ustc.lightdoc.parserImpl;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.xml.sax.helpers.DefaultHandler;


public class DTagHandler extends DefaultHandler{
	//用于存放标签的映射
	private HashMap<String, String> tagMap = new HashMap<String, String>();
	
	public HashMap<String, String> getTagMap(String xmlUrl) {
		try {
			tagMap = new HashMap<String, String>();
			SAXReader saxReader = new SAXReader();
			HttpServletRequest request = ServletActionContext.getRequest();
			String basePath = request.getSession().getServletContext().getRealPath("/")+"doc/";
			Document document = saxReader.read(basePath+xmlUrl);
			Element root = document.getRootElement();
			Iterator<?> iterator = root.elementIterator();
			while (iterator.hasNext()) {
				Element element = (Element) iterator.next();
				String key = element.attributeValue("name");
				String value = element.getTextTrim();
				tagMap.put(key.toString(),value.toString());
			}
			return tagMap;
		}catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
	}
}
