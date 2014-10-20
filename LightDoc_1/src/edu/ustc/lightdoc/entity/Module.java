package edu.ustc.lightdoc.entity;
import java.util.List;

/**
 * Task 任务类
 */
public class Module {
	private String moduleTitle;	//任务名称
	private String moduleDesc;	//任务描述
	private List<Function> funcs;	//任务步骤列表
	
	/***************************constructor*****************************/
	public Module(String moduleTitle, String moduleDesc, List<Function> funcs) {
		super();
		this.moduleTitle = moduleTitle;
		this.moduleDesc = moduleDesc;
		this.funcs = funcs;
	}
	public Module() {
	}
	
	/***********************setter and getter*************************/
	public String getModuleTitle() {
		return moduleTitle;
	}
	public void setModuleTitle(String moduleTitle) {
		this.moduleTitle = moduleTitle;
	}
	public String getModuleDesc() {
		return moduleDesc;
	}
	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}
	public List<Function> getFuncs() {
		return funcs;
	}
	public void setFuncs(List<Function> funcs) {
		this.funcs = funcs;
	}
	
	
}
