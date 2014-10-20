package edu.ustc.lightdoc.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import edu.ustc.lightdoc.entity.Function;
import edu.ustc.lightdoc.entity.Step;
import edu.ustc.lightdoc.service.ILoadService;
import edu.ustc.lightdoc.service.IUpdateService;

public class UpdateAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4020587674043651688L;
	
	private IUpdateService updateService;
	private ILoadService loadService;
	private String fileUrl;
	private String moduleTitle;
	private String funcTitle;
	private String msg;
	private Logger logger = Logger.getLogger(UpdateAction.class);
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
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

	public ILoadService getLoadService() {
		return loadService;
	}

	public void setLoadService(ILoadService loadService) {
		this.loadService = loadService;
	}

	public IUpdateService getUpdateService() {
		return updateService;
	}

	public void setUpdateService(IUpdateService updateService) {
		this.updateService = updateService;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String updateStep(){
		HttpServletRequest  request = ServletActionContext.getRequest();
		fileUrl = request.getParameter("fileUrl");
		moduleTitle = request.getParameter("module");
		funcTitle = request.getParameter("func");
		String offsetX = request.getParameter("offsetX");
		String offsetY = request.getParameter("offsetY");
		String stepDesc = request.getParameter("stepDesc");
		String currPg = request.getParameter("currPg");
		String num = request.getParameter("num");
		if(fileUrl == null){
			msg = "文件名为空！";
			logger.error("file name is null....");
			return ERROR;
		}
		if(moduleTitle==null){
			msg="moduleTitle为空！";
			logger.error("module name is null....");
			return ERROR;
		}
		if(funcTitle==null){
			msg="funcTitle为空！";
			logger.error("Function name is null....");
			return ERROR;
		}
		if(offsetX==null){
			msg="offsetX为空！";
			logger.error("offsetX is null....");
			return ERROR;
		}
		if(offsetY==null){
			msg="offsetY为空！";
			logger.error("offsetY is null....");
			return ERROR;
		}
		if(stepDesc==null){
			msg="stepDesc为空！";
			logger.error("stepDesc is null....");
			return ERROR;
		}
		if(currPg==null){
			msg="bg_img为空！";
			logger.error("bg_img is null....");
			return ERROR;
		}
		Step step = new Step();
		step.setStepDesc(stepDesc);
		step.setOffsetX(offsetX);
		step.setOffsetY(offsetY);
		boolean tmp = this.updateService.modifyElementByPath(fileUrl, moduleTitle, funcTitle, currPg, num, step);
		if(tmp){
			request.setAttribute("fileName", fileUrl);
			request.setAttribute("message","修改成功，正在跳转。。。立刻跳转?");
			logger.info("updateFunc success....");
			return "success";
			
		}else{
			logger.info("updateFunc fail ....");
			return "error";
		}
	}
	
	public String addStepToFunction(){
		HttpServletRequest  request = ServletActionContext.getRequest();
		fileUrl = request.getParameter("fileUrl");
		moduleTitle = request.getParameter("module");
		funcTitle = request.getParameter("func");
		String offsetX = request.getParameter("offsetX");
		String offsetY = request.getParameter("offsetY");
		String stepDesc = request.getParameter("stepDesc");
		String currPg = request.getParameter("currPg");
		String num = request.getParameter("num");
		if(fileUrl == null){
			msg = "文件名为空！";
			logger.error("file name is null....");
			return ERROR;
		}
		if(moduleTitle==null){
			msg="moduleTitle为空！";
			logger.error("module name is null....");
			return ERROR;
		}
		if(funcTitle==null){
			msg="funcTitle为空！";
			logger.error("Function name is null....");
			return ERROR;
		}
		if(offsetX==null){
			msg="offsetX为空！";
			logger.error("offsetX is null....");
			return ERROR;
		}
		if(offsetY==null){
			msg="offsetY为空！";
			logger.error("offsetY is null....");
			return ERROR;
		}
		if(stepDesc==null){
			msg="stepDesc为空！";
			logger.error("stepDesc is null....");
			return ERROR;
		}
		Step step = new Step();
		step.setStepDesc(stepDesc);
		step.setOffsetX(offsetX);
		step.setOffsetY(offsetY);
		boolean tmp = this.updateService.addElementByPath(fileUrl, moduleTitle, funcTitle, currPg, num, step);
		if(tmp){
			request.setAttribute("fileName", fileUrl);
			request.setAttribute("message","修改成功，正在跳转。。。立刻跳转?");
			logger.info("updateFunc success....");
			return "success";
			
		}else{
			logger.info("updateFunc fail ....");
			return "error";
		}
	}
	
	public String delStep(){
		HttpServletRequest  request = ServletActionContext.getRequest();
		fileUrl = request.getParameter("file");
		moduleTitle = request.getParameter("module");
		funcTitle = request.getParameter("func");
		String num = request.getParameter("num");
		String currPg = request.getParameter("currPg");
		if(fileUrl == null){
			msg = "文件名为空！";
			logger.error("file name is null....");
			return ERROR;
		}
		if(moduleTitle==null){
			msg="moduleTitle为空！";
			logger.error("module name is null....");
			return ERROR;
		}
		if(funcTitle==null){
			msg="funcTitle为空！";
			logger.error("Function name is null....");
			return ERROR;
		}
		if(currPg==null){
			msg="currPg为空！";
			logger.error("bg_img is null....");
			return ERROR;
		}
		boolean tmp = this.updateService.delElementByPath(fileUrl, moduleTitle, funcTitle, currPg, num);
		if(tmp){
			request.setAttribute("fileName", fileUrl);
			request.setAttribute("message","删除成功，正在跳转。。。立刻跳转?");
			logger.info("updateFunc success....");
			return "success";
			
		}else{
			logger.info("updateFunc fail ....");
			return "error";
		}
	}
	public String updateFunc(){
		HttpServletRequest  request = ServletActionContext.getRequest();
		String title = request.getParameter("title");
		String desc = request.getParameter("desc");
		fileUrl = request.getParameter("fileUrl");
		moduleTitle = request.getParameter("module");
		funcTitle = request.getParameter("func");
		if(fileUrl == null){
			msg = "文件名为空！";
			logger.error("file name is null....");
			return ERROR;
		}
		if(moduleTitle==null){
			msg="moduleTitle为空！";
			logger.error("module name is null....");
			return ERROR;
		}
		if(funcTitle==null){
			msg="funcTitle为空！";
			logger.error("Function name is null....");
			return ERROR;
		}
		if(title==null){
			msg="title为空！";
			logger.error("title is null....");
			return ERROR;
		}
		if(desc==null){
			msg="desc为空！";
			logger.error("desc is null....");
			return ERROR;
		}
		Function func = new Function();
		func.setFuncTitle(title);
		func.setFuncDesc(desc);
		boolean tmp = this.updateService.updateFunc(fileUrl, moduleTitle, funcTitle, func);
		if(tmp){
			//request.setAttribute("fileName", fileUrl);
			//request.setAttribute("message","修改成功，正在跳转。。。立刻跳转?");
			logger.info("updateFunc success....");
			return "success";
			
		}else{
			logger.info("updateFunc fail ....");
			return "error";
		}
	}
	
	public String updateSoft(){
		HttpServletRequest  request = ServletActionContext.getRequest();
		String title = request.getParameter("title");
		String desc = request.getParameter("desc");
		fileUrl = request.getParameter("fileUrl");
		if(title==null){
			msg="title为空！";
			logger.error("title is null....");
			return ERROR;
		}
		if(desc==null){
			msg="desc为空！";
			logger.error("desc is null....");
			return ERROR;
		}
		if(fileUrl == null){
			msg = "文件名为空！";
			logger.error("file name is null....");
			return ERROR;
		}
		boolean tmp = this.updateService.updateSoft(fileUrl, title, desc);
		if(tmp){
			request.setAttribute("fileName", fileUrl);
			request.setAttribute("message", fileUrl+"修改成功，正在跳转。。。立刻跳转?");
			logger.info("updateFunc success....");
			return "success";
			
		}else{
			logger.info("updateFunc fail ....");
			return "error";
		}
	}
	
}
