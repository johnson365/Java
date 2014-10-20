package edu.ustc.lightdoc.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import edu.ustc.lightdoc.entity.MenuNode;
import edu.ustc.lightdoc.entity.Function;
import edu.ustc.lightdoc.entity.SoftwareNode;
import edu.ustc.lightdoc.parser.ICfgDOMHelper;
import edu.ustc.lightdoc.parser.IDOMHelper;
import edu.ustc.lightdoc.service.ILoadService;

public class LoadService implements ILoadService {
	private IDOMHelper domHelper;
	private ICfgDOMHelper cfgDomHelper;

	public IDOMHelper getDomHelper() {
		return domHelper;
	}

	public void setDomHelper(IDOMHelper domHelper) {
		this.domHelper = domHelper;
	}
	
	/* (non-Javadoc)
	 * @see edu.ustc.lightdoc.service.ILoadService#getMenu(java.lang.String)
	 */
	@Override
	public List<MenuNode> getMenu(String xmlName){
		int pid = 0;
		int id = 1;
		String url = "./getDeteil.action";
		try {
			List<MenuNode> list = new ArrayList<MenuNode>();
			SoftwareNode software = domHelper.getOutline(null);
			MenuNode mn = new MenuNode();
			mn.setId(id);
			mn.setPid(pid);
			pid = id;
			id++;
			mn.setName(software.getNodeName());
			mn.setUrl(url);
			list.add(mn);
			if(software.getNodeList().size() > 0){
				for(int i=0; i<software.getNodeList().size(); i++){
					SoftwareNode task = software.getNodeList().get(i);
					MenuNode mnTask = new MenuNode();
					mnTask.setId(id);
					mnTask.setPid(pid);
					pid = id;
					id++;
					mnTask.setName(task.getNodeName());
					mnTask.setUrl(url);
					list.add(mnTask);
					if (task.getNodeList().size() > 0){
						for(int j=0; j<task.getNodeList().size(); j++){
							SoftwareNode step = task.getNodeList().get(j);
							MenuNode mnStep = new MenuNode();
							mnStep.setId(id);
							mnStep.setPid(pid);
							pid = id;
							id++;
							mnStep.setName(step.getNodeName());
							mnStep.setUrl(url);
							list.add(mnStep);
						}
					}
				}
			}
			return list;
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public SoftwareNode loadMenu(String xmlName){
		try {
			return this.domHelper.getOutline(xmlName);
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean isFileExisted(String xmlName){
		Document doc =  this.domHelper.loadXML(xmlName);
		if (doc != null){
			return true;
		}else{
			return false;
		}
	}

	public Function getFuncByPath(String fileUrl, String module, String func){
		Function tmp = null;
		try{
			//String xmlUrl = this.getUrlByFileName(fileName);
			tmp = this.domHelper.getFuncByPath(fileUrl, module, func);
			return tmp;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public String getUrlByFileName(String fileName){
		return this.cfgDomHelper.getUrlByFileName(fileName);
	}
	
	public ICfgDOMHelper getCfgDomHelper() {
		return cfgDomHelper;
	}

	public void setCfgDomHelper(ICfgDOMHelper cfgDomHelper) {
		this.cfgDomHelper = cfgDomHelper;
	}

	public boolean getCheckValueByFileName(String fileName){
		String result = this.cfgDomHelper.getCheckValueByFileName(fileName);
		if (result.equals("true"))
			return true;
		else
			return false;
	}
	
	public boolean modifyXml(String fileName, String value){
		boolean result = this.cfgDomHelper.modifyXmlFile(fileName, value);
		return result;
	}
}
