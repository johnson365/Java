package edu.ustc.lightdoc.parser;
import java.util.List;

import org.dom4j.Document;

import edu.ustc.lightdoc.entity.*;


public interface IDOMHelper {
	
	/**
	 * method name	   :	loadXML
	 * method describe :	加载xml文件
	 * 
	 * @param fileName :  	xml名称
	 * @return Document:  	xml文件
	 * 
	 * */
	public Document loadXML(String fileName);
	
	/**
	 * method name	   :	writeToXml
	 * method describe :	写xml文件
	 * 
	 * @param fileName :    写入文件地址
	 * @param Document :  	document
	 * @return boolean :	
	 * 
	 * */
	public boolean writeToXml(String fileName,Document document);
	
	
	/**
	 * method name	   :	getOutline
	 * method describe :	获取树形结构
	 * 
	 * @return TopicNode:  	xml解析的数据
	 * 						topic/task/step
	 * 
	 * */
	public SoftwareNode getOutline(String xmlUrl) throws Throwable;
	
		
	/**
	 * method name     : 	getTaskByPath
	 * method describe : 	获取指定位置的Task的内容
	 * 
	 * @param taskTitle：	给定task的title
	 * @return Task    : 	返回给定title的Task对象
	 * 
	 * */
	public Module getModuleByPath(String xmlUrl,String taskTitle);
	
	/**
	 * method name     : 	getStepByPath
	 * method describe : 	获取指定位置的Step的内容
	 * 
	 * @param stepTitle:	给定step的title
	 * @param taskTitle:	给定task的title
	 * @return Step    : 	返回给定title的step对象
	 * 
	 * */
	public Function getFuncByPath(String xmlUrl,String taskTitle,String stepTitle);
	
	/**
	 * method name	   :	modifyElementByPath
	 * method describe :	修改给定路径节点的内容
	 * 
	 * @param fileName :	要修改的文件名
	 * @param taskTitle:	要修改的taskTitle
	 * @param stepTitle:	要修改的stepTitle
	 * @return step	      ：	修改的节点内容
	 * 
	 * */
	public boolean modifyElementByPath(String xpath, String xmlUrl, Step step);
	
	public abstract boolean addElementByPath(String xpath, String xmlUrl, int num, Step step);
	
	/**
	 * method name     : 	getTask
	 * method describe : 	获取给定taskTitle的Task对应的内容
	 * 
	 * @param taskTitle：	给定task的title
	 * @return Task    : 	返回给定title的Task对象
	 * 
	 * */
	public Module getModuleByTitle(String xmlUrl,String taskTitle);
	
	/**
	 * method name	   :	getStep
	 * method describe :	获取给定taskTitle和给定stepTitle的Step对应的内容
	 * 
	 * @param taskTitle:	给定task的title
	 * @param stepTitle:	给定step的title
	 * @return Step	      ：	返回给定title的Step对象
	 * 
	 * */
	//public Step getStep(String taskTitle,String stepTitle);
	
	/**
	 * method name	   :	getCmd
	 * method describe :	获取给定taskTitle和给定stepTitle、给定cmdTitle的Cmd对应的内容
	 * 
	 * @param taskTitle:	给定task的title
	 * @param stepTitle:	给定step的title
	 * @return Step	      ：	返回给定title的Step对象
	 * 
	 * */
	//public Cmd getCmd(String taskTitle,String stepTitle,String cmdTitle);
	
	/**
	 * method name	   :	getTopic
	 * method describe :	获取指定文档的所有内容
	 * 
	 * @return Topic   ：	返回Topic
	 * */
	public Software getSoftwareByUrl(String xmlUrl);

	public String toXpath(String path, List<String> conditions);

	public boolean delElementByPath(String xpath, int num, String url);
	
	public abstract boolean addSSToFunction(String xmlUrl, String xpath, int num, StepSet ss);

	public boolean updateFunc(String url, String xpath, Function func);

	public boolean updateSoft(String url, String title, String desc);
}
