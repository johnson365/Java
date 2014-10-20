package edu.ustc.lightdoc.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthorityInterceptor extends AbstractInterceptor{

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		ActionContext ctx = invocation.getInvocationContext();
		Map session = ctx.getSession();
		String userName = (String) session.get("user");
		System.out.println(userName+".....");
		if(userName != null)
		{
			return invocation.invoke();
		}
		else
		{
			return "login";
		}
	}
	
}
