<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE HTML>
<html>
<head>
 	
	<link id="main_stylesheet" rel="stylesheet" href="css/style.css" type="text/css" /> 
	<link rel="stylesheet" href="css/jquery.multilevelpushmenu.css">
    <link rel="stylesheet" href="css/basichtml.css">	
    <link rel="stylesheet" href="css/rightMenu.css">	
    <link rel="stylesheet" href="css/animation.css">
	<script type="text/javascript" src="js/jquery.js"></script> 
	<script type="text/javascript" src="js/jquery.easing.1.3.js"></script> 
	<script type="text/javascript" src="js/jquery.scrollTo-min.js"></script> 
	<script src="js/jquery.multilevelpushmenu.min.js"></script>
    <script type="text/javascript" src="js/basichtml.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>
     <script type="text/javascript" src="js/TvSlide.js"></script>
     <script type="text/javascript" src="js/modernizr.custom.js"></script>
</head>
<body>
<div class="bg">
  <div class="content-wrapper"> 
	<div class="content"> 
		  <div class="homepage-wrapper"> 
				<div class="homepage-slider"> 
					<a href="javascript:void(0);" class="btn-previous" onclick="tvSlide.scrollLefty();">&nbsp;</a> 
					<a href="javascript:void(0);" class="btn-next" onclick="tvSlide.scrollRight();">&nbsp;</a>
					<div id="aktuals_field" style="overflow:hidden; position:relative;width:919px;height:528px"> 
						<div class="homepage-slider-item pt-page-current" id="firstPage">
							<span class="title"><s:property value="#request.node.software.systemTitle"/></span>
							<span class="desc"><s:property value="#request.node.software.systemDesc"/></span>
						</div>
					</div> 
					<div id="progress"> </div> 
				</div> 
			</div> 
			<!-- END .content --> 
		</div> 
	  </div>
	</div>
<!-- 右侧导航 -->
<div class="rightNav" id="menu">
 	<s:set name="node" value="#request.node"></s:set>
 	<ul>
 	<s:iterator id="node1" value="#node.nodeList" status="id">
 		<li><a href="javascript:void(0);" style="right: -110px;"><em><s:property value="#id.index"/></em><b><s:property value="#node1.nodeName"/></b></a>
 			<ul>
 				<s:iterator id="node2" value="#node1.nodeList">
                   <li>
                      <a href="javascript:void(0);" ><s:property value="#node2.nodeName"/></a>
                   </li>
                </s:iterator>
 			</ul>
 		</li>
 	</s:iterator>
 	</ul>
 	 <input type="hidden" name="software" id="xmlUrl" value="${xmlUrl}"/>
 <!-- 	 <input type="hidden" name="module" id="module"/>
 	 <input type="hidden" name="func" id="func"/> -->
</div>
 <script type="text/javascript" src="js/rightMenu.js"></script>
</body>
</html>
