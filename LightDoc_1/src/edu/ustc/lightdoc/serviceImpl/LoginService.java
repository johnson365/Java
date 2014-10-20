package edu.ustc.lightdoc.serviceImpl;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import com.opensymphony.xwork2.ActionContext;

import edu.ustc.lightdoc.entity.User;
import edu.ustc.lightdoc.parserImpl.UserInfoParser;

public class LoginService {
	
	public boolean isEquals(String username, String password){
		boolean flag = false;
		SAXReader saxReader = new SAXReader();
		Document doc = null;
		if(username == null || password == null){
			return false;
		}
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String basePath = request.getSession().getServletContext().getRealPath("/")+"doc\\";
			doc = saxReader.read(basePath+"UserInfo.xml");
		} catch (DocumentException e2) {
			e2.printStackTrace();
		}
		UserInfoParser userinfo = new UserInfoParser();
		User user = userinfo.getUserInfo(doc);
		String name = user.getUsername();
		String pass = user.getPassword();
		if(name == null || pass == null){
			System.out.println("some information get from cfg XML is NULL!");
			return false;
		}
		if (username.equals(name)&&password.equals(pass)){
			flag = true;
			ActionContext.getContext().getSession().put("user", name);
		}
		return flag;
	}
}
