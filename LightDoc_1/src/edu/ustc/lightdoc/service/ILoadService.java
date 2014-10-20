package edu.ustc.lightdoc.service;

import java.util.List;

import edu.ustc.lightdoc.entity.MenuNode;
import edu.ustc.lightdoc.entity.Function;
import edu.ustc.lightdoc.entity.SoftwareNode;

public interface ILoadService {

	public abstract List<MenuNode> getMenu(String xmlName);
	
	public abstract Function getFuncByPath(String xmlName, String module, String func);
	
	public abstract boolean isFileExisted(String xmlName);
	
	public abstract SoftwareNode loadMenu(String xmlName);
	
	public abstract String getUrlByFileName(String fileName);
	
	public abstract boolean getCheckValueByFileName(String fileName);
	
	public abstract boolean modifyXml(String fileName, String value);

}