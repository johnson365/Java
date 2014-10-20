package edu.ustc.lightdoc.entity;
import java.util.List;

/**
 * Topic ������
 */
public class Software {
	private String author;		//���˵����������
	private String systemTitle;	//��������
	private String systemDesc;	//��������
	private List<Module> modules;	//���������б�
	
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
