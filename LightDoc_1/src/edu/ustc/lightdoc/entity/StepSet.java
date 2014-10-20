package edu.ustc.lightdoc.entity;

import java.util.List;

/**
 * StepSet ≤Ω÷ËºØ¿‡
 */
public class StepSet implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String imgSrc;
	private List<Step> steps;
	
	/***********************constructor*****************/
	public StepSet() {
	}
	public StepSet(String imgSrc, List<Step> steps) {
		this.imgSrc = imgSrc;
		this.steps = steps;
	}
	
	/*********************setter and getter**************/
	
	public List<Step> getSteps() {
		return steps;
	}
	public String getImgSrc() {
		return imgSrc;
	}
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}
	
	
}
