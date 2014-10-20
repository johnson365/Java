package edu.ustc.lightdoc.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import edu.ustc.lightdoc.entity.Audit;
import edu.ustc.lightdoc.entity.SoftwareNode;
import edu.ustc.lightdoc.service.ILoadService;
import edu.ustc.lightdoc.service.IUpdateService;

public class AuditAction extends ActionSupport implements ModelDriven<Audit>{
	
	private static final long serialVersionUID = 3275666382398793484L;
	
	private Audit audit;
	private List<Audit> xmlInfo;
	private IUpdateService updateService;
	private String message;
	private ILoadService loadService;
	private String fileUrl;
	private static Logger logger = Logger.getLogger(AuditAction.class);
	public Audit getAudit() {
		return audit;
	}

	public void setAudit(Audit audit) {
		this.audit = audit;
	}

	public String execute(){
		xmlInfo = this.updateService.xmlFileList();
		if(xmlInfo != null){
			logger.info("update xml file success...");
			return "success";
		}else{
			logger.error("update xml file fail....");
			return "false";
		}
	}

	public String addXml(){
		boolean flag = this.updateService.addNodeToXml(audit);
		if(flag){
			logger.info("add xml file success...");
			return "success";
		}else{
			logger.error("add xml file fail....");
			return "error";
		}
	}
	
	public String preAudit(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String xmlUrl = request.getParameter("xmlUrl");
		String xmlName = request.getParameter("xmlName");
		if(xmlUrl == null){
			message="文件路径为空！";
			request.setAttribute("message", message);
			logger.error("no xml matched....");
			return "error";
		}
		if(this.loadService.isFileExisted(xmlUrl)){
			SoftwareNode tNode = this.loadService.loadMenu(xmlUrl);
			request.setAttribute("xmlUrl", xmlUrl);
			if(xmlName != null)
				request.setAttribute("xmlName", xmlName);
			request.setAttribute("node", tNode);
			logger.info("xml matched! go on....");
			return "success";
		}else {
			message="获取文件列表失败！";
			request.setAttribute("message", message);
			logger.info("no xml matched...");
			return "error";
		}
	}
	
	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileName(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String audit(){
		HttpServletRequest  request = ServletActionContext.getRequest();
		String xmlName = request.getParameter("xmlName");
		boolean result = this.updateService.modifyXml(xmlName, "true");
		if(result){
			request.setAttribute("xmlName", xmlName);
			request.setAttribute("message", "修改成功。。。正在跳转。。。");
			logger.info("audit success...");
			return "success";
		}
		else 
			return "error";
	}
	
	
	public IUpdateService getUpdateService() {
		return updateService;
	}

	public void setUpdateService(IUpdateService updateService) {
		this.updateService = updateService;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ILoadService getLoadService() {
		return loadService;
	}

	public void setLoadService(ILoadService loadService) {
		this.loadService = loadService;
	}

	public List<Audit> getXmlInfo() {
		return xmlInfo;
	}

	public void setXmlInfo(List<Audit> xmlInfo) {
		this.xmlInfo = xmlInfo;
	}


	@Override
	public Audit getModel() {
		return audit;
	}
	
}
