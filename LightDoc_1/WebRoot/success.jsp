<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<link href="css/jquery-ui-1.8.16.custom.css" rel="stylesheet" type="text/css"/>
<link href="css/main.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script> 
<script type="text/javascript" src="js/script.js"></script>
<script type="text/javascript" src="layer/layer.js"></script>

<title>Animated jQuery progressbar | Script tutorials</title>
</head>
<body>
    <div class="example">
        <h3>
        <a href="loadMenu.action?xmlName=${xmlName}"> ${message}</a>
        </h3>
		<input type="hidden" name="topic" id="xmlName" value="${xmlName}"/>
        <div id="progress1">
            <div class="pbar"></div>
            <div class="elapsed"></div>
        </div>
    </div>
</body>
</html>