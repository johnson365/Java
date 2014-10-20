package edu.ustc.lightdoc.entity;
import java.util.List;

/**
 * Topic 主题类
 */
public class Software {
	private String author;		//软件说明书所有者
	private String systemTitle;	//主题名称
	private String systemDesc;	//主题描述
	private List<Module> modules;	//主题任务列表
	
	/**************************constructor********************/
	public Software(String author, String systemTitle, String systemDesc,
			List<Module> modules) {
		super();
		this.author = author;
		this.systemTitle = systemTitle;
		this.systemDesc = systemDesc;
		this.modules = modules;
	}

	public Software() {
	}

	
	/***********************setter and getter******************/
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSystemTitle() {
		return systemTitle;
	}

	public void setSystemTitle(String systemTitle) {
		this.systemTitle = systemTitle;
	}

	public String getSystemDesc() {
		return systemDesc;
	}

	public void setSystemDesc(String systemDesc) {
		this.systemDesc = systemDesc;
	}

	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}
	
	
}
