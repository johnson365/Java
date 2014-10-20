package edu.ustc.lightdoc.entity;

public class Audit {
	private String fileName;
	private String url;
	private String status;
	
	public Audit() {
	}
	public Audit(String fileName, String url, String status) {
		super();
		this.fileName = fileName;
		this.url = url;
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	

}
