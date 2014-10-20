package edu.ustc.lightdoc.serviceImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.ustc.lightdoc.parser.ICfgDOMHelper;
import edu.ustc.lightdoc.parserImpl.CfgDOMHelper;
import edu.ustc.lightdoc.service.IFindAllXMLFile;

public class FindAllXmlFile implements IFindAllXMLFile{
	ArrayList<String> fileList = new ArrayList<String>();
	File file = new File("E:/xml");
	String[] filename = file.list();
	
	
	public List<String> xmlFileList() {
		if (!file.isDirectory()) {
		    file.delete();
		   } 
		else if(file.isDirectory()){
			for(int i=0; i<filename.length; i++){
				if(filename[i].endsWith(".xml")){
					fileList.add(filename[i]);
				}
			}
		}
		return fileList;
	}
	
	//得到返回状态
	public Map<String, String> GetStatus(){
		Map<String, String> xmlStatus = new HashMap<String, String>();
		ICfgDOMHelper icfd = new CfgDOMHelper();
		int xmlSize = this.xmlFileList().size();
		for(int i=0; i<xmlSize; i++){
			String xmlAllName = (String)fileList.get(i);
			String xmlName = (String) xmlAllName.substring(0, xmlAllName.length()-4);
			try{
				String status = icfd.getCheckValueByFileName(xmlName);
				xmlStatus.put((String) fileList.get(i), status);
			}catch(Exception e){
				System.out.println("不存在此文件");
			}
		}
		return xmlStatus;
	}
	
}
	
