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
 	 <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link id="main_stylesheet" rel="stylesheet" href="css/style.css" type="text/css" /> 
	<link rel="stylesheet" href="css/jquery.multilevelpushmenu.css">
	<link rel="stylesheet" href="css/modify.css">
    <link rel="stylesheet" href="css/rightMenu.css">
    <link rel="stylesheet" href="css/animation.css">	
	<script type="text/javascript" src="js/jquery.js"></script> 
	<script type="text/javascript" src="js/jquery.easing.1.3.js"></script> 
	<script src="js/jquery.multilevelpushmenu.min.js"></script>
    <script type="text/javascript" src="js/audit.js"></script>
    <script type="text/javascript" src="js/TvSlide.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>
    <script type="text/javascript" src="js/modernizr.custom.js"></script>
</head>
<body>
<div class="bg">
  <div id="input">
	<a id="modify" class="button edit" href="javascript:void(0);" onclick="tvSlide.modify()">修改</a>
	<a id="audit" class="button save" href="javascript:void(0);">确认审核</a>
  </div>
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
			<!-- END .content --> 
		</div> 
	  </div>
	</div>
</div>
<div class="modify_div" id="step_md_div">
	<ul class="navTab">
		<li><a class="tab" href="javascript:void(0)" onclick="stc(0)">修改步骤</a></li>
		<li><a class="tab" href="javascript:void(0)" onclick="stc(1)">新增步骤</a></li>
		<li><a class="tab" href="javascript:void(0)" onclick="stc(2)">新增背景</a></li>
	</ul>
	<div class="md_div" id="md_img">
		<img draggable="true" class="md_point img" id="modify_point" src="images/point.gif"/>
		<textarea rows="3" cols="30" id="md_stepDesc" class="ta_stepDesc"></textarea>
	</div>
	<div class="md_div" id="md_add">
		<img draggable="true" class="md_point img" id="add_point" src="images/point.gif"/>
		<textarea rows="3" cols="30" id="add_stepDesc" class="ta_stepDesc"></textarea>
	</div>
	<div class="md_div" id="md_adds">
		<img alt="background-image" id="bg_add" height="528px" width="919px">
	</div>
	<div id="md_input" align="center">
		<a class="button save" id="btn_mod" onclick="modify(1);">确认修改</a>
		<a class="button add" id="btn_add" onclick="add(1);">确认新增</a>
		<a class="button delete" id="btn_del" onclick="del();">确认删除</a>
		<div id="up_div">
			<input type="file" id="upload">
			<a class="button save" id="up_add" onclick="add(2);">确认新增</a>
		</div>
	</div>
</div>
<div class="modify_div" id="ss_md_div" align="center">
	<span>标题和描述修改页面</span>
	<table>
		<tr><td>标题：</td><td><textarea rows="2" cols="30" id="md_title"></textarea></td></tr>
		<tr><td>描述：</td><td><textarea rows="4" cols="30" id="md_desc"></textarea></td></tr>
		<tr><td colspan="2" align="center"><a class="button save" href="javascript:void(0);" onclick="save()">保存修改</a>
		<a class="button delete" href="javascript:void(0);" onclick="cancel(this);">取消</a></td></tr>
	</table>
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
 	 <input type="hidden" name="fileUrl" id="xmlUrl" value="${xmlUrl}"/>
 	 <input type="hidden" name="fileName" id="xmlName" value="${xmlName}"/>
</div>
 <script type="text/javascript" src="js/rightMenu.js"></script>
</body>
</html>
