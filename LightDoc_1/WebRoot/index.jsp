<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
<%@taglib prefix="s" uri="/struts-tags" %>  
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>  
<%@ taglib uri="/struts-tags" prefix="s" %> 
<!DOCTYPE HTML>  
<html>  
  <head>  
    <base href="<%=basePath%>">  
      
    <title>LD</title>  
    <meta http-equiv="pragma" content="no-cache">  
    <meta http-equiv="cache-control" content="no-cache">  
    <meta http-equiv="expires" content="0">      
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">  
    <meta http-equiv="description" content="This is my page">  
    <link rel="stylesheet" type="text/css" href="style/css/search.css" />
	<script src="js/jquery.js"></script>
	<script type="text/javascript">
	window.onload=function(){
		  document.getElementById("search").focus();
	};
	</script>
  </head>  
    
  <body>
	  <p><font size="6">&nbsp;</font></p>
	  <img alt="lightDoc" src="images/prelogo.png"><br/>
	  <font color="#408080"><s:property value="#request.message"/></font>
	  <form class="form-wrapper" id="seach_form" action="loadMenu.action" method="get">
		<input type="text" name="xmlName" id="search" placeholder="输入xml文件名" required>
		<input type="submit" value="搜索" id="submit">
	</form>
</body>
</html>