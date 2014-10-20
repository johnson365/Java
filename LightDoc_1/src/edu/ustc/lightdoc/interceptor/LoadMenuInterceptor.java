package edu.ustc.lightdoc.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoadMenuInterceptor extends AbstractInterceptor{

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		String xmlName = request.getParameter("xmlName");

		//System.out.println(xmlName+"address");
		if(xmlName!=null)
		{
			return invocation.invoke();
		}
		else
		{
			return "search";
		}
	}

}
