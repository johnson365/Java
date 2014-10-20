package edu.ustc.lightdoc.parser;
import java.util.List;

import org.dom4j.Document;

import edu.ustc.lightdoc.entity.*;


public interface IDOMHelper {
	
	/**
	 * method name	   :	loadXML
	 * method describe :	����xml�ļ�
	 * 
	 * @param fileName :  	xml����
	 * @return Document:  	xml�ļ�
	 * 
	 * */
	public Document loadXML(String fileName);
	
	/**
	 * method name	   :	writeToXml
	 * method describe :	дxml�ļ�
	 * 
	 * @param fileName :    д���ļ���ַ
	 * @param Document :  	document
	 * @return boolean :	
	 * 
	 * */
	public boolean writeToXml(String fileName,Document document);
	
	
	/**
	 * method name	   :	getOutline
	 * method describe :	��ȡ���νṹ
	 * 
	 * @return TopicNode:  	xml����������
	 * 						topic/task/step
	 * 
	 * */
	public SoftwareNode getOutline(String xmlUrl) throws Throwable;
	
		
	/**
	 * method name     : 	getTaskByPath
	 * method describe : 	��ȡָ��λ�õ�Task������
	 * 
	 * @param taskTitle��	����task��title
	 * @return Task    : 	���ظ���title��Task����
	 * 
	 * */
	public Module getModuleByPath(String xmlUrl,String taskTitle);
	
	/**
	 * method name     : 	getStepByPath
	 * method describe : 	��ȡָ��λ�õ�Step������
	 * 
	 * @param stepTitle:	����step��title
	 * @param taskTitle:	����task��title
	 * @return Step    : 	���ظ���title��step����
	 * 
	 * */
	public Function getFuncByPath(String xmlUrl,String taskTitle,String stepTitle);
	
	/**
	 * method name	   :	modifyElementByPath
	 * method describe :	�޸ĸ���·���ڵ������
	 * 
	 * @param fileName :	Ҫ�޸ĵ��ļ���
	 * @param taskTitle:	Ҫ�޸ĵ�taskTitle
	 * @param stepTitle:	Ҫ�޸ĵ�stepTitle
	 * @return step	      ��	�޸ĵĽڵ�����
	 * 
	 * */
	public boolean modifyElementByPath(String xpath, String xmlUrl, Step step);
	
	public abstract boolean addElementByPath(String xpath, String xmlUrl, int num, Step step);
	
	/**
	 * method name     : 	getTask
	 * method describe : 	��ȡ����taskTitle��Task��Ӧ������
	 * 
	 * @param taskTitle��	����task��title
	 * @return Task    : 	���ظ���title��Task����
	 * 
	 * */
	public Module getModuleByTitle(String xmlUrl,String taskTitle);
	
	/**
	 * method name	   :	getStep
	 * method describe :	��ȡ����taskTitle�͸���stepTitle��Step��Ӧ������
	 * 
	 * @param taskTitle:	����task��title
	 * @param stepTitle:	����step��title
	 * @return Step	      ��	���ظ���title��Step����
	 * 
	 * */
	//public Step getStep(String taskTitle,String stepTitle);
	
	/**
	 * method name	   :	getCmd
	 * method describe :	��ȡ����taskTitle�͸���stepTitle������cmdTitle��Cmd��Ӧ������
	 * 
	 * @param taskTitle:	����task��title
	 * @param stepTitle:	����step��title
	 * @return Step	      ��	���ظ���title��Step����
	 * 
	 * */
	//public Cmd getCmd(String taskTitle,String stepTitle,String cmdTitle);
	
	/**
	 * method name	   :	getTopic
	 * method describe :	��ȡָ���ĵ�����������
	 * 
	 * @return Topic   ��	����Topic
	 * */
	public Software getSoftwareByUrl(String xmlUrl);

	public String toXpath(String path, List<String> conditions);

	public boolean delElementByPath(String xpath, int num, String url);
	
	public abstract boolean addSSToFunction(String xmlUrl, String xpath, int num, StepSet ss);

	public boolean updateFunc(String url, String xpath, Function func);

	public boolean updateSoft(String url, String title, String desc);
}
