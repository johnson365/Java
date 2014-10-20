<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'check.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/audit.css">
	<style type="text/css">
		table {border-style:dotted;border-width: 2px;}
		table tr:nth-child(even){background: #fff38f;}
		table tr:nth-child(odd){background: #ffffee;}
		table td,th {border-style: solid;border-width: 1px;height: 30px;text-align: center;}
		table td span{display:none;}
		.input {width: 200px;height: 35px;background-color: #fff38f;}
		.submit {width:80px;height: 35px;}
		.btn {width:80px;height: 35px;}
	</style>
	
<script src="js/jquery.js"></script>
<script>
$(document).ready(function() {
    $("#content").find("[id^='tab']").hide(); // Hide all content
    $("#tabs li:first").attr("id","current"); // Activate the first tab
    $("#content #tab1").fadeIn(); // Show first tab's content
    
    $('#tabs a').click(function(e) {
        e.preventDefault();
        if ($(this).closest("li").attr("id") == "current"){ //detection for current tab
         return;       
        }
        else{             
          $("#content").find("[id^='tab']").hide(); // Hide all content
          $("#tabs li").attr("id",""); //Reset id's
          $(this).parent().attr("id","current"); // Activate this
          $('#' + $(this).attr('name')).fadeIn(); // Show content for the current tab
        }
    });
    
     $("#ta1 a").click(function(){
    	var fileName = $(this).parent().prev().prev().html();
    	var url = $(this).siblings("span").html();
    	(this).href="preAudit.action?xmlUrl="+url+"&xmlName="+fileName;
    }); 
});
function check()
{
	var flag;
	var input1 = document.getElementById("textfield1");
	var input2 = document.getElementById("textfield2");
	if(input1.value.trim()=="" || input2.value.trim()=="")
	{
	 	flag = false;
	 	alert("文件名||路径不能为空");
	}
	else
	{
		flag = true;
	}
	return flag;
	
	
}

function getFullPath(obj) {
    if (obj) {
        //Internet Explorer 
        if (window.navigator.userAgent.indexOf("MSIE") >= 1) {
            obj.select();
            return document.selection.createRange().text;
        } 
        //Firefox
        else if (window.navigator.userAgent.indexOf("Firefox") >= 1) { 
            if (obj.files) {
                return obj.files.item(0).getAsDataURL();
            }
            return obj.value;
        } 
        alert(obj.value);
        return obj.value;
    } 
}


</script>

  </head>
  
  <body>
  <ul id="tabs">
    <li><a href="#" name="tab1" id="t1">查看文件</a></li>
    <li><a href="#" name="tab2" id="t2">添加文件</a></li>
</ul>

<div id="content"> 
    <div id="tab1">
    <table id="ta1">
    	<tr>
    		<th width="600">文件名</th>
    		<th width="200">审核状态</th>	
    		<th width="200">审核</th>
    	</tr>
      <s:iterator value="xmlInfo" id="xmlInfo">  
    	 <tr> 
    	 	 <td><s:property value="#xmlInfo.fileName"/></td>
    	 	 <td><s:property value="#xmlInfo.status"/></td> 
    	 	 <s:if test="#xmlInfo.status == 'false'">
    	 	 	<td><a href="javascript:void(0);">审核</a><span><s:property value="#xmlInfo.url"/></span></td>
    	 	 </s:if>
    	 	 <s:else>
    	 	 	<td><a href="javascript:void(0);">修改</a><span><s:property value="#xmlInfo.url"/></span></td>
    	 	 </s:else>
    	 </tr>
     </s:iterator> 
     </table>
    </div>
    <div id="tab2">
    	<form action="addXml" method="post" onSubmit="return check();">
    		 文件名称：<input id='textfield1' name="audit.fileName" type="text" class="input"><p>
    		 路径信息：<input id='textfield2' name="audit.url" type="text" class="input">&nbsp;&nbsp;
    		 <input name="file1" type="file" style="display:none" class="file1" onchange="document.getElementById('textfield2').value=this.value"  />
    		 <input class="btn" type="button" value="浏览..." onclick=file1.click() />
    		<input class="submit" type="submit" value="添加" >
    	</form>
    </div>
</div>
  
  </body>
</html>
