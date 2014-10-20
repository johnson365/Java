<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html >
<head>     
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />     
<title>错误页面</title>     
<style type="text/css">
body{
	width:100%;
	height:100%;
	position:absolute;
	background:#4B4B4B;
}
.title{
	display:block;
	font-family:Georgia;
	font-style:oblique;
	font-size:50px;
	position:absolute;
	right:6em;
	top:1em;
	color:#C9C9C9;
	text-shadow:2px 2px 2px #CCCCCC;
}
.main{
	width:800px;
	height:300px;
	background:#D5D5D5;
	border:1px solid #D5D5D5;
	border-radius: 10px;
	position:absolute;
	right:14em;
	top:8em;
	-moz-box-shadow:5px 5px 5px #3E94AC;
	-webkit-box-shadow:5px 5px 5px #3E94AC;
	box-shadow:0 0.5px 0 #3E94AC;
}
.error{
	width:300px;
	height:260px;
	display:block;
	margin:10px;
	padding:80px 10px 10px 10px;
	float:left;
	font-size:80px;
	font-family:Georgia;
	font-style:normal;
	color:#B5263C;
	text-shadow:2px 2px 2px #B1364F;
}
.deteil{
	width:400px;
	height:260px;
	margin:10px;
	float:left;
	display:block;
	border-left:2px solid #3E3E3E;
}
.deteil span{
	display:block;
	width:auto;
	height:auto;
	padding:20px 10px 10px 10px;
	font-family:Georgia;
	font-size:24px;
	font-style:normal;
}
.deteil a{
	display:block;
	width:150px;
	height:20px;
	margin:60px 180px;
	padding:5px;
	color:#2E2E2E;
	text-decoration:none;
	border:1px solid #484848;
	border-radius:5px;
	text-align:center;
}
.deteil a:hover{
	color:#48A4AE;
	border:1px solid #0CC;
	-moz-box-shadow:0 0 3px #48A4AE;
	-webkit-box-shadow:0 0 3px #48A4AE;
	box-shadow:0 0 3px #48A4AE;
}
</style>
</head>     
<body>
	<span class="title">LightDoc</span>
	<div class="main">
    	<span class="error">ERROR</span>
        <div class="deteil"><span>${msg}</span>
        	<a class="button" href="history.go(-1);">back to main Page</a>
        </div>
    </div>
</body>     
</html>