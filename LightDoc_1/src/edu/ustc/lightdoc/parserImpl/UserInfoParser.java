package edu.ustc.lightdoc.parserImpl;

import org.dom4j.*;
import edu.ustc.lightdoc.entity.User;

public class UserInfoParser {
	Document doc = null;
	
	public User getUserInfo(Document doc){
		
		User user = new User();
		Element root = doc.getRootElement();
		Element usernameE = root.element("username");
		Element passwordE = root.element("password");
		
		String username = usernameE.getText();
		String password = passwordE.getText();
		user.setUsername(username);
		user.setPassword(password);
		return user;
	}
}
