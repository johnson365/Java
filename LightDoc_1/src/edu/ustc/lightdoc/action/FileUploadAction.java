package edu.ustc.lightdoc.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;

import edu.ustc.lightdoc.service.IUpdateService;

public class FileUploadAction extends ActionSupport {
	
	private static final long serialVersionUID = 6824886566930002754L;
	private String fileUrl;
	private String module;
	private String func;
	private int currPg;
	private File fileToUpload;
	private String uploadFileName;
	private String msg;
	private IUpdateService updateService;
	public IUpdateService getUpdateService() {
		return updateService;
	}
	public void setUpdateService(IUpdateService updateService) {
		this.updateService = updateService;
	}

	private Logger logger = Logger.getLogger(FileUploadAction.class);
	
	public File getFileToUpload() {
		return fileToUpload;
	}
	public void setFileToUpload(File fileToUpload) {
		this.fileToUpload = fileToUpload;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getFunc() {
		return func;
	}
	public void setFunc(String func) {
		this.func = func;
	}
	public int getCurrPg() {
		return currPg;
	}
	public void setCurrPg(int currPg) {
		this.currPg = currPg;
	}
	
	public String addSSToFunction(){
		if(fileUrl == null){
			msg = "文件名为空！";
			logger.error("file name is null....");
			return ERROR;
		}
		if(module==null){
			msg="moduleTitle为空！";
			logger.error("module name is null....");
			return ERROR;
		}
		if(func==null){
			msg="funcTitle为空！";
			logger.error("Function name is null....");
			return ERROR;
		}
		if(currPg < 0){
			msg="currPg错误！";
			logger.error("currPage is null....");
			return ERROR;
		}
		String path = ServletActionContext.getServletContext().getRealPath("");
		boolean result = this.updateService.uploadFile(path, getUploadFileName(), getFileToUpload());
		if(!result){
			msg="文件上传发生错误！";
			return ERROR;
		}
		
		String bgPath = "../../slide/"+this.getUploadFileName();
		result = this.updateService.addSSToFunction(fileUrl, module, func, currPg, bgPath);
		if(!result){
			msg="写入文件发生错误！";
			return ERROR;
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache"); 
			PrintWriter out = response.getWriter();
			out.print(bgPath);
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return ERROR;
		}
	}
}
