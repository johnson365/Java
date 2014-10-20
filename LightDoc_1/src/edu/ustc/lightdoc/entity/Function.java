package edu.ustc.lightdoc.entity;
import java.util.List;

/**
 * Step 任务步骤类
 */
public class Function implements java.io.Serializable {	
	private static final long serialVersionUID = 1L;
	private String funcTitle;	//功能名称
	private String funcDesc;	//功能描述
	private List<StepSet> stepSets;		//功能步骤集
	
	/****************constructor*************************/
	public Function() {
	}
	
	public Function(String funcTitle, String funcDesc, List<StepSet> stepSets) {
		this.funcTitle = funcTitle;
		this.funcDesc = funcDesc;
		this.stepSets = stepSets;
	}

	/****************setter and getter******************/
	
	public List<StepSet> getStepSets() {
		return stepSets;
	}
	public String getFuncTitle() {
		return funcTitle;
	}

	public void setFuncTitle(String funcTitle) {
		this.funcTitle = funcTitle;
	}

	public String getFuncDesc() {
		return funcDesc;
	}

	public void setFuncDesc(String funcDesc) {
		this.funcDesc = funcDesc;
	}

	public void setStepSets(List<StepSet> stepSets) {
		this.stepSets = stepSets;
	}
	
}
