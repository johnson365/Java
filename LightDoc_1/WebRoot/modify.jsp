<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE HTML">
<html>
 
    <head>
        <meta charset="utf-8">
        <title>审核修改页面</title>
        <script type="text/javascript" src="js/jquery.js"></script> 
        <script type="text/javascript" src="js/modify.js"></script>
        <script type="text/javascript" src="layer/layer.js"></script>
        <link rel="stylesheet" href="css/modify.css">	
    </head>
<body>
 	<form action="" name="form_update" id="form_update" method="post">
		<div class="top">
		<div class="left_div">
		<input type="hidden" name="fileName" value="${fileName }"/>
		<input type="hidden" name="moduleTitle" value="${moduleTitle }"/> 
		<table id="tb_update">
			<tr>
				<td width="80">步骤名称：</td>
				<td width="307"><input type="text" name="func.funcTitle" value="${func.funcTitle }"/></td>
			</tr>
			<tr>
				<td>步骤描述：</td>
				<td><textarea name="func.funcDesc">${func.funcDesc }</textarea></td>
			</tr>
		</table>
		</div>
		<div class="right_div">
			<img id="img_preview" src=""/>
		</div>
		</div>
		<div class="step_con">
		<s:set name="func" value="#request.func"></s:set>
		<s:iterator value="#func.stepSets" id="stepSet" status="ss">
		<table >
			<tr>
				<td colspan="5" align="left">背景图片：&nbsp;
				<input type="text" name="func.stepSets.imgSrc" value='<s:property value="#stepSet.imgSrc"/>'/>
				&nbsp;<a class="preview" href="javascript:void(0);" onclick="view()">预览</a>
				</td>
			</tr>
			<tr><th height="35"></th>
				<th>offsetX</th>
				<th>offsetY</th>
				<th>操作描述</th>
				<th>增删操作</th>
			</tr>
			<s:iterator value="#stepSet.steps" id="step" status="stepId">
			<tr><td><s:property value="#stepId.index"/></td>
				<td><input class="input_step coordinate" type="text" name="func.stepSets[<s:property value="#ss.index"/>].steps[<s:property value="#stepId.index"/>].offsetX" value='<s:property value="#step.offsetX"/>'/></td>
				<td><input class="input_step coordinate" type="text" name="func.stepSets[<s:property value="#ss.index"/>].steps[<s:property value="#stepId.index"/>].offsetY" value='<s:property value="#step.offsetY"/>'/></td>
			  	<td><textarea class="input_step" name="func.stepSets[<s:property value="#ss.index"/>].steps[<s:property value="#stepId.index"/>].stepDesc"><s:property value="#step.stepDesc"/></textarea></td>
				<td><a class="add" href="javascript:void(0);">增加</a>&nbsp;&nbsp;
					<a class="sub" href="javascript:void(0);">删除</a></td>
			</tr>
			</s:iterator>
		</table>
		</s:iterator>
		</div>
	</form>
	<div class="div_btn" align="center">
		<input type="button" id="submit" value="提交"/>&nbsp;&nbsp;
		<input type="button" id="close" value="取消" onclick="cancel();"/>
	</div>
</body>
</html>

