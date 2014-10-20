package edu.ustc.lightdoc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import edu.ustc.lightdoc.entity.Function;
import edu.ustc.lightdoc.entity.SoftwareNode;
import edu.ustc.lightdoc.service.ILoadService;

public class LoadAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ILoadService loadService;
	private String result;
	private String moduleTitle;
	private String funcTitle;
	private String fileName;
	private static Logger logger = Logger.getLogger(LoadAction.class);
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getModuleTitle() {
		return moduleTitle;
	}

	public void setModuleTitle(String moduleTitle) {
		this.moduleTitle = moduleTitle;
	}

	public String getFuncTitle() {
		return funcTitle;
	}

	public void setFuncTitle(String funcTitle) {
		this.funcTitle = funcTitle;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public ILoadService getLoadService() {
		return loadService;
	}

	public void setLoadService(ILoadService loadService) {
		this.loadService = loadService;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String loadMenu() throws Throwable{

			HttpServletRequest  request = ServletActionContext.getRequest();
			String message = null;
			String xmlName = request.getParameter("xmlName");
			String xmlUrl = this.loadService.getUrlByFileName(xmlName);
			boolean flag = this.loadService.getCheckValueByFileName(xmlUrl);
			
			if(xmlUrl == null || !flag){
				message="文件不存在";
				request.setAttribute("message", message);
				logger.error("no xml matched...");
				return "search";
			}
			if(this.loadService.isFileExisted(xmlUrl)){
				SoftwareNode tNode = this.loadService.loadMenu(xmlUrl);
				if(tNode == null){
					message = "获取导航出错！";
					logger.error("navigator error...");
					return ERROR;
				}
				request.setAttribute("xmlUrl", xmlUrl);
				request.setAttribute("node", tNode);
				return "success";
			}else {
				message="文件不存在";
				request.setAttribute("message", message);
				logger.error("no xml file match...");
				return "search";
			}
	}
	
	public String loadStep() {
		HttpServletRequest  request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String funcTmp = null;
		String moduleTmp = null;
		try {
			String fileUrl = request.getParameter("fileUrl");
			moduleTmp = request.getParameter("val_module");
			funcTmp = request.getParameter("val_func");
			Function tmp = this.loadService.getFuncByPath(fileUrl, moduleTmp, funcTmp);
			if (tmp == null){
				logger.error("loadStep method error...");
				return ERROR;
			}
			JSONObject json = JSONObject.fromObject(tmp);
			result = json.toString();
			response.setContentType("text/json;charset=UTF-8");     
			response.setHeader("Cache-Control", "no-cache"); 
			response.getWriter().print(result);
			return null;
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error("loadStep method error...");
			return "error";
		}
	}

}
