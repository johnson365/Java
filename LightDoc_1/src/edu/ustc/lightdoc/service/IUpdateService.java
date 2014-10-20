package edu.ustc.lightdoc.service;

import java.io.File;
import java.util.List;

import edu.ustc.lightdoc.entity.Audit;
import edu.ustc.lightdoc.entity.Function;
import edu.ustc.lightdoc.entity.Step;

public interface IUpdateService {

	public abstract boolean modifyXml(String fileName, String value);

	public abstract boolean modifyElementByPath(String fileName,
			String taskTitle, String stepTitle,String currPg, String num, Step step);

	public abstract boolean addNodeToXml(Audit audit);
	
	public abstract List<Audit> xmlFileList();
	
	public abstract boolean addElementByPath(String fileName,
			String taskTitle, String stepTitle,String currPg, String num, Step step);

	public abstract boolean delElementByPath(String fileName,
			String moduleTitle, String funcTitle,String currPg, String num);
	
	public abstract boolean uploadFile(String path, String fileName, File file);
	
	public abstract boolean addSSToFunction(String fileName, String module, String func, int currPg, String bgPath);

	public abstract boolean updateFunc(String fileName, String moduleTitle,
			String funcTitle, Function func);

	public abstract boolean updateSoft(String fileName, String title,
			String desc);
}