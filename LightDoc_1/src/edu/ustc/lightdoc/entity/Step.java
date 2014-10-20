package edu.ustc.lightdoc.entity;

public class Step implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String stepDesc;//步骤描述
	private String offsetX;//步骤对应的X轴位移
	private String offsetY;//步骤对应的Y轴位移
	
	/*********************constructor************************/

	public Step() {
	}
	

	public Step(String stepDesc, String offsetX, String offsetY) {
		this.stepDesc = stepDesc;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	/*********************setter and getter******************/
	public String getStepDesc() {
		return stepDesc;
	}
	public void setStepDesc(String stepDesc) {
		this.stepDesc = stepDesc;
	}
	public void setOffsetX(String offsetX) {
		this.offsetX = offsetX;
	}

	public void setOffsetY(String offsetY) {
		this.offsetY = offsetY;
	}

	public String getOffsetX() {
		return offsetX;
	}

	public String getOffsetY() {
		return offsetY;
	}

	
	
}
