package edu.ustc.lightdoc.action;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import edu.ustc.lightdoc.entity.User;
import edu.ustc.lightdoc.serviceImpl.LoginService;

public class LoginAction extends ActionSupport implements ModelDriven<User>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5500315713180615127L;
	User user = new User();
	private Logger logger = Logger.getLogger(LoginAction.class);
	public String execute(){
		String username = user.getUsername();
		String password = user.getPassword();
		LoginService ls = new LoginService();
		System.out.println(username+".....");
		if(ls.isEquals(username, password))
		{
			logger.info(username + "success login in..");
			return "success";
		}
		else {
			logger.error("login fail...");
			return "login";
		}
	}
	@Override
	public User getModel() {
		return user;
	}

}
