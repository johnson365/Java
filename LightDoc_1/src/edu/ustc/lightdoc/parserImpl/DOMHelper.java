package edu.ustc.lightdoc.parserImpl;

import java.io.*;
import java.util.*;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import edu.ustc.lightdoc.entity.*;
import edu.ustc.lightdoc.parser.IDOMHelper;

public class DOMHelper implements IDOMHelper{

	//获取标签映射表
	public HashMap<String, String> getTagMap() {
		try {
			HashMap<String, String> tagMap = new HashMap<String, String>();
			DTagHandler tagHandler = new DTagHandler();
			String xmlUrl = "TagConfig.xml";
			tagMap = tagHandler.getTagMap(xmlUrl);
			if (tagMap != null){
				return tagMap;
			}else{
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	public String toXpath(String path,List<String> conditions){
		try {
			HashMap<String, String> tagMap = new HashMap<String, String>();
			tagMap = getTagMap();
			int index = 0;
			StringBuilder xpath = new StringBuilder();
			String[] pathNode = path.split("/");
			for (String str : pathNode) {
				xpath.append("/");
				if (str.endsWith("?")){
					xpath.append(tagMap.get(str.replace("?","").trim()));
					if (conditions.get(index) != null){
						xpath.append("[" + tagMap.get("title") + "='" + conditions.get(index) + "']");
						index++;
					}
				}else if (str.endsWith("*")){
					xpath.append(tagMap.get(str.replace("*", "").trim()));
					if(index < conditions.size()){
						xpath.append("[" + conditions.get(index++) + "='" + conditions.get(index++) + "']");
					}
				}else if (str.endsWith("!")) {
					xpath.append(tagMap.get(str.replace("!", "").trim()));
					if(index < conditions.size()){
						xpath.append("[" + conditions.get(index++) + "]");
					}
				}else{
					xpath.append(tagMap.get(str.trim()));
				}
			}
			
			return xpath.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String toXpath(String path){
		HashMap<String, String> tagMap = new HashMap<String, String>();
		tagMap = getTagMap();
		StringBuilder xpath = new StringBuilder();
		String[] pathNode = path.split("/");
		for (String str : pathNode) {
			xpath.append("/");
			xpath.append(tagMap.get(str.trim()));
		}
		return xpath.toString();
	}
	
	@Override
	public Document loadXML(String xmlUrl) {
		SAXReader saxReader = new SAXReader();
		try {
			saxReader.setEncoding("UTF-8");
			Document document = saxReader.read(xmlUrl);
			
			if(document != null)
				return document;
			else 
				return null;
		} catch (DocumentException e) {
			e.printStackTrace();
			System.out.println("文件没找到");
			return null;
		}
	}
	@Override
	public SoftwareNode getOutline(String xmlUrl) throws Throwable {
		SoftwareNode softNode = new SoftwareNode();
		try {
			Document doc = this.loadXML(xmlUrl);
			HashMap<String, String> tagMap = getTagMap();
			
			if(doc == null || tagMap == null){
				return null;
			}
			Element root = doc.getRootElement();
			softNode.setNodeName(root.elementTextTrim(tagMap.get("title")));
			Software soft = new Software();
			soft.setAuthor(root.elementTextTrim(tagMap.get("author")));
			soft.setSystemDesc(root.elementTextTrim(tagMap.get("desc")));
			soft.setSystemTitle(root.elementTextTrim(tagMap.get("title")));
			softNode.setSoftware(soft);
			
			String path = "software/modules";
			Element element = (Element) root.selectSingleNode(toXpath(path));
			if(element == null){
				System.out.println("没有获得节点!");
				return null;
			}
			Iterator<?> iterator = element.elementIterator();
			ArrayList<SoftwareNode> taskList = new ArrayList<SoftwareNode>();
			while (iterator.hasNext()) {
				Element moduleElement = (Element)iterator.next();
				SoftwareNode moduleNode = new SoftwareNode();
				moduleNode.setNodeName(moduleElement.elementTextTrim(tagMap.get("title")));
				if (moduleElement.element(tagMap.get("funcs")) != null){
					Iterator<?> listOfFun = moduleElement.element(tagMap.get("funcs")).elementIterator();
					ArrayList<SoftwareNode> funcList = new ArrayList<SoftwareNode>();
					while (listOfFun.hasNext()){
						SoftwareNode funcNode = new SoftwareNode();
						Element elem = (Element)listOfFun.next();
						funcNode.setNodeName(elem.elementTextTrim(tagMap.get("title")));
						funcList.add(funcNode);
					}
					moduleNode.setNodeList(funcList);
					taskList.add(moduleNode);
				}
			}
			softNode.setNodeList(taskList);
			return softNode;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Module getModuleByPath(String xmlUrl, String moduleTitle) {
		try {
			HashMap<String, String> tagMap = getTagMap();
			Document document = this.loadXML(xmlUrl);
			if(document == null || tagMap == null){
				return null;
			}
			String path = "software/modules/module?";
			List<String> conditions = new ArrayList<String>();
			conditions.add(moduleTitle);
			
			Element root = document.getRootElement();
			Element element = (Element)root.selectSingleNode(toXpath(path,conditions));
			Module module = this.getModule(element, tagMap);
			return module;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Function getFuncByPath(String xmlUrl, String moduleTitle, String funcTitle) {
		try {
			HashMap<String, String> tagMap = getTagMap();
			String path = "software/modules/module?/funcs/func?";
			List<String> conditions = new ArrayList<String>();
			conditions.add(moduleTitle);
			conditions.add(funcTitle);
			
			Document document = this.loadXML(xmlUrl);
			if(document == null || tagMap == null){
				return null;
			}
			Element root = document.getRootElement();
			String xPath = this.toXpath(path, conditions);
			Element element = (Element) root.selectSingleNode(xPath);
			Function func = this.getFunc(element, tagMap);
			
			return func;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean modifyElementByPath(String xpath, String xmlUrl, Step step){
		try {
			HashMap<String, String> tagMap = new HashMap<String, String>();
			tagMap = getTagMap();
			
			Document document = this.loadXML(xmlUrl);
			if(document == null || tagMap == null){
				return false;
			}
			Element root = document.getRootElement();
			Element element = (Element) root.selectSingleNode(xpath);
			if(element == null){
				System.out.println(xpath);
				return false;
			}
			element.setText(step.getStepDesc());
			Attribute attrX = element.attribute("x");
			attrX.setValue(step.getOffsetX());
			Attribute attrY = element.attribute("y");
			attrY.setValue(step.getOffsetY());
			writeToXml(xmlUrl, document);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean writeToXml(String fileName,Document document){
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();     
            FileOutputStream fos = new FileOutputStream(fileName);     
            XMLWriter writer = new XMLWriter(fos, format);     
            writer.write(document);
			writer.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public Module getModuleByTitle(String xmlUrl, String moduleTitle) {
		try {
			HashMap<String, String> tagMap = new HashMap<String, String>();
			tagMap = getTagMap();
			Document document = this.loadXML(xmlUrl);
			Element root = document.getRootElement();
			if (root.element(tagMap.get("modules")) != null){
				Iterator<?> imodules = root.element(tagMap.get("modules")).elementIterator();
				while (imodules.hasNext()) {
					Element modulesElem = (Element) imodules.next();
					Iterator<?> it = modulesElem.elements(tagMap.get("module")).iterator();
					while(it.hasNext()){
						Element elem = (Element) it.next();
						Module tmp = this.getModule(elem, tagMap);
						if(tmp.getModuleTitle().equals(moduleTitle)){
							return tmp;
						}
					}
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Software getSoftwareByUrl(String xmlUrl) {
		Software soft = new Software();
		try {
			HashMap<String, String> tagMap = new HashMap<String, String>();
			tagMap = getTagMap();
			Document document = this.loadXML(xmlUrl);
			Element root = document.getRootElement();
			if (root != null){
				String tgAuthor = tagMap.get("author");
				soft.setAuthor(root.elementTextTrim(tgAuthor));
				soft.setSystemTitle(root.elementTextTrim(tagMap.get("title")));
				soft.setSystemDesc(root.elementTextTrim(tagMap.get("desc")));
			}
			//如果是tasks标签，遍历各个task
			if (root.element(tagMap.get("modules")) != null){
				List<Module> modules = new ArrayList<Module>();
				Iterator<?> imodules = root.element(tagMap.get("modules")).elementIterator();
				while (imodules.hasNext()) {
					Element modulesElem = (Element) imodules.next();
					Iterator<?> itOfModule = modulesElem.element(tagMap.get("module")).elementIterator();
					while(itOfModule.hasNext()){
						Element moduleElem = (Element)itOfModule.next();
						Module module = this.getModule(moduleElem, tagMap);
						if(module != null)
							modules.add(module);
					}
				}
				soft.setModules(modules);
			}
			return soft;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Module getModule(Element elem, HashMap<String, String> tagMap){
		Module module = new Module();
		module.setModuleTitle(elem.elementTextTrim(tagMap.get("title")));
		module.setModuleDesc(elem.elementTextTrim(tagMap.get("desc")));
		
		if (elem.element(tagMap.get("funcs")) != null){
			ArrayList<Function> funcList = new ArrayList<Function>();
			Iterator<?> funcs = elem.element(tagMap.get("funcs")).elementIterator();
			while(funcs.hasNext()){
				Element funcElem = (Element) funcs.next();
				Function func = this.getFunc(funcElem, tagMap);
				if(func != null)
					funcList.add(func);
			}
			module.setFuncs(funcList);
		}
		return module;
	}
	
	
	public Function getFunc(Element elem, HashMap<String, String> tagMap){
		Function func = new Function();
		ArrayList<StepSet> stepSets = new ArrayList<StepSet>();
		if(elem == null || tagMap == null){
			System.out.println("函数getFunc中传递的参数为空！");
			return null;
		}
		func.setFuncTitle(elem.elementTextTrim(tagMap.get("title")));
		func.setFuncDesc(elem.elementTextTrim(tagMap.get("desc")));
		if (elem.element(tagMap.get("stepSet")) != null){
			Iterator<?> it = elem.elements(tagMap.get("stepSet")).iterator();
			while(it.hasNext()) {
				Element ssElem = (Element) it.next();
				StepSet stepSet = this.getStepSet(ssElem, tagMap);
				if(stepSet != null)
					stepSets.add(stepSet);
			}
			func.setStepSets(stepSets);
		}
		return func;
	}
	
	public StepSet getStepSet(Element elem, HashMap<String, String> tagMap){
		StepSet stepSet = new StepSet();
		String temp = elem.attributeValue("bg-img");
		if(temp == null){
			System.out.println("函数getStepSet获取属性bg-img失败！");
			return null;
		}
		stepSet.setImgSrc(temp);
		if(elem.element(tagMap.get("step")) != null){
			Iterator<?> itOfStep = elem.elements(tagMap.get("step")).iterator();
			List<Step> steps = new ArrayList<Step>();
			while(itOfStep.hasNext()){
				Element stepElement = (Element) itOfStep.next();
				Step step = this.getStep(stepElement);
				if(step != null)
					steps.add(step);
			}
			stepSet.setSteps(steps);
		}
		return stepSet;
	}
	
	public Step getStep(Element elem){
		Step step = new Step();
		step.setStepDesc(elem.getTextTrim());
		@SuppressWarnings("unchecked")
		List<Attribute> attributes = elem.attributes();
		for(Attribute attr : attributes){
			if(attr.getName() != null){
				if(attr.getName().equals("x")){
					String offsetX = attr.getValue();
					step.setOffsetX(offsetX);
				}else{
					String offsetY = attr.getValue();
					step.setOffsetY(offsetY);
				}
			}
		}
		return step;
	}

	@Override
	public boolean addElementByPath(String xpath, String xmlUrl, int num, Step step) {
		try{
			HashMap<String, String> tagMap = new HashMap<String, String>();
			tagMap = getTagMap();
		
			Document document = this.loadXML(xmlUrl);
			if(document == null || tagMap == null){
				return false;
			}
			Element root = document.getRootElement();
			Element element = (Element) root.selectSingleNode(xpath);
			if(element == null){
				System.out.println(xpath);
				return false;
			}
			@SuppressWarnings("unchecked")
			List<Element> elements = element.elements();
			Element el = DocumentHelper.createElement(tagMap.get("step"));
			el.setText(step.getStepDesc());
			el.addAttribute("x", step.getOffsetX());
			el.addAttribute("y", step.getOffsetY());
			elements.add(num,el);
			element.setContent(elements);
			writeToXml(xmlUrl, document);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delElementByPath(String xpath, int num, String url) {
		try{
			HashMap<String, String> tagMap = new HashMap<String, String>();
			tagMap = getTagMap();
		
			Document document = this.loadXML(url);
			if(document == null || tagMap == null){
				return false;
			}
			Element root = document.getRootElement();
			Element element = (Element) root.selectSingleNode(xpath);
			if(element == null){
				System.out.println(xpath);
				return false;
			}
			@SuppressWarnings("unchecked")
			List<Element> elements = element.elements();
			elements.remove(num);
			if(elements.size() == 0){
				element.getParent().remove(element);
			}else{
				element.setContent(elements);
			}
			writeToXml(url, document);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean addSSToFunction(String xmlUrl, String xpath, int num, StepSet ss) {
		try{
			HashMap<String, String> tagMap = new HashMap<String, String>();
			tagMap = getTagMap();
	
			Document document = this.loadXML(xmlUrl);
			if(document == null || tagMap == null){
				return false;
			}
			Element root = document.getRootElement();
			Element element = (Element) root.selectSingleNode(xpath);
			if(element == null){
				System.out.println(xpath);
					return false;
			}
			@SuppressWarnings("unchecked")
			List<Element> elements = element.elements(tagMap.get("stepSet"));
			Element el = DocumentHelper.createElement(tagMap.get("stepSet"));
			el.addAttribute("bg-img", ss.getImgSrc());
			el.setText("");
			elements.add(num, el);
			writeToXml(xmlUrl, document);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateFunc(String url, String xpath, Function func) {
		try{
			HashMap<String, String> tagMap = new HashMap<String, String>();
			tagMap = getTagMap();
			Document document = this.loadXML(url);
			if(document == null || tagMap == null){
				return false;
			}
			Element root = document.getRootElement();
			Element element = (Element) root.selectSingleNode(xpath);
			if(element == null){
				System.out.println(xpath);
				return false;
			}
			Element elTitle = element.element(tagMap.get("title"));
			elTitle.setText(func.getFuncTitle());
			Element elDesc = element.element(tagMap.get("desc"));
			elDesc.setText(func.getFuncDesc());
			writeToXml(url, document);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateSoft(String url, String title, String desc) {
		try{
			HashMap<String, String> tagMap = new HashMap<String, String>();
			tagMap = getTagMap();
			Document document = this.loadXML(url);
			if(document == null || tagMap == null){
				return false;
			}
			Element root = document.getRootElement();
			Element elTitle = root.element(tagMap.get("title"));
			elTitle.setText(title);
			Element elDesc = root.element(tagMap.get("desc"));
			elDesc.setText(desc);
			writeToXml(url, document);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	

}
