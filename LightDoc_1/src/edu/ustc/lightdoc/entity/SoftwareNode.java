package edu.ustc.lightdoc.entity;
import java.util.List;

public class SoftwareNode {
	private String nodeName;
	private Software software;
	public Software getSoftware() {
		return software;
	}
	public void setSoftware(Software software) {
		this.software = software;
	}
	private List<SoftwareNode> nodeList;
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public List<SoftwareNode> getNodeList() {
		return nodeList;
	}
	public void setNodeList(List<SoftwareNode> nodeList) {
		this.nodeList = nodeList;
	}
	
}
