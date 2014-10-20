$(document).ready(function(){
	 var $div = $(".img");
        /* 绑定鼠标左键按住事件 */
	 $div.bind("mousedown",function(event){
		 event.preventDefault();
		 /* 获取需要拖动节点的坐标 */
		 var offset_x = $(this)[0].offsetLeft;//x坐标
		 var offset_y = $(this)[0].offsetTop;//y坐标
		 /* 获取当前鼠标的坐标 */
		 var mouse_x = event.pageX;
		 var mouse_y = event.pageY;             
	 
		 /* 绑定拖动事件 */
		 /* 由于拖动时，可能鼠标会移出元素，所以应该使用全局（document）元素 */
	 	$(document).bind("mousemove",function(ev){
	 		ev.preventDefault();
	 		/* 计算鼠标移动了的位置 */
	 		var _x = ev.pageX - mouse_x;
	 		var _y = ev.pageY - mouse_y;
	 
	 		/* 设置移动后的元素坐标 */
	 		var now_x = (offset_x + _x ) ;
	 		var now_y = (offset_y + _y ) ;                   
	 		/* 改变目标元素的位置 */
	 		$div.css({top:now_y+"px",left:now_x +"px"});	
	 	 });
	 	
	 	$div.bind("mouseup",function(event){
	 		event.preventDefault();
	 		$(this).unbind("mousemove");
	 	});
	 });
	    /* 当鼠标左键松开，接触事件绑定 */
	$(document).bind("mouseup",function(event){
		event.preventDefault();
		$(this).unbind("mousemove");
	});
	
	//审核事件处理函数
	$("#audit").click(function(){
		var fileName = $("#xmlName").val();
		window.location.href="audit.action?xmlName="+fileName;
	});
	
	$("#upload").change(function(){
		var objUrl = getObjectURL(this.files[0]) ;
		console.log("objUrl = "+objUrl) ;
		if (objUrl) {
			$("#bg_add").attr("src", objUrl) ;
		}
	}) ;
});

var xhr;
function stc(num){
	$(".md_show").removeClass("md_show");
	$(".md_div").eq(num).addClass("md_show");
	if(num == 0){
		$("#btn_add, #up_div").hide();
		$("#btn_mod, #btn_del").show();
		
	}else if(num == 1){
		$("#btn_add").show();
		$("#btn_mod, #btn_del, #up_div").hide();
	}else{
		$("#up_div").show();
		$("#btn_mod, #btn_add, #btn_del").hide();
	}
	$(".selected").removeClass("selected");
	$(".navTab li:nth-child("+(num+1)+") a").addClass("selected");
}

function modify(num)
{
	var offsetX = $("img#modify_point").css("left");
	var offsetY = $("img#modify_point").css("top");
	var stepDesc = $("#md_stepDesc").val();
	var e = $("#aktuals_field");
	var func =e.data("func");
	var module = e.data("module");
	var fileUrl = $("#xmlUrl").val();
	var fileName = $("#xmlName").val();
	var num = e.data("num");
	var currPg = e.data("currPage");
	if(!offsetX){alert("坐标错误！请重新设置坐标！");return false;}
	if(!offsetY){alert("坐标错误！请重新设置坐标！");return false;}
	if(!stepDesc){alert("说明错误！请检查说明！");return false;}
	if(!func){alert("获取功能名称发生错误！");return false;}
	if(!module){alert("获取模块名称发生错误！");return false;}
	if(!fileUrl){alert("获取文件名称发生错误！");return false;}
	$.ajax({
		async: false,
		url:'updateStep.action',
		type:'post',
		data:{offsetX: offsetX, offsetY: offsetY, stepDesc: stepDesc, func:func, module:module, fileUrl:fileUrl, currPg:currPg, num:num},
		dataType:'json',
		contentType:'application/x-www-form-urlencoded; charset=UTF-8',
		success:function(data){
			window.location.href="preAudit.action?xmlUrl="+fileUrl+"&xmlName="+fileName;
		}
	});
}

function cancel(){
	var index = $.layer.index;
	alert(index);
}

function add(num){
	var e = $("#aktuals_field");
	var func =e.data("func");
	var module = e.data("module");
	var fileUrl = $("#xmlUrl").val();
	var fileName = $("#xmlName").val();
	if(!func){alert("获取功能名称发生错误！");return false;}
	if(!module){alert("获取模块名称发生错误！");return false;}
	if(!fileUrl){alert("获取文件名称发生错误！");return false;}
	
	if(num == 1){
		var el =  $("img#add_point");
		var offsetX =el.css("left");
		var offsetY = el.css("top");
		var stepDesc = $("#add_stepDesc").val();
		var currPg = e.data("currPage"); 
		var num = e.data("num");
		if(!offsetX){alert("坐标错误！请重新设置坐标！");return false;}
		if(!offsetY){alert("坐标错误！请重新设置坐标！");return false;}
		if(!stepDesc){alert("说明错误！请检查说明！");return false;}
		$.ajax({
			async: false,
			url:'addStepToFunction.action',
			type:'post',
			data:{offsetX: offsetX, offsetY: offsetY, stepDesc: stepDesc, func:func, module:module, fileUrl:fileUrl, currPg:currPg, num:num},
			dataType:'json',
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			success:function(data){
				alert("修改成功！");
				window.location.href="preAudit.action?xmlUrl="+fileUrl+"&xmlName="+fileName;
			}
		});
	}else{
		
		var currPg = e.data("currPage");
		if(!currPg){alert("当前页面数值错误！请检查当前页面数值！");return false;}
		var fd = new FormData();
        fd.append("fileToUpload", document.getElementById('upload').files[0]);
        fd.append("uploadFileName", document.getElementById('upload').value);
        fd.append("module", module);
        fd.append('func', func);
        fd.append('currPg', currPg-1);
        fd.append('fileUrl', fileUrl);
        createXMLHttp();
        xhr.open("POST", "addSSToFunction.action");
        xhr.onreadystatechange = handleResponse;  
        xhr.send(fd);
		
	}
}
function createXMLHttp(){  
    if(window.ActiveXObject){  
        xhr = new ActiveXObject("Microsoft.XMLHTTP");  
    }  
    else{  
        xhr = new XMLHttpRequest() ;  
    }  
}  
function handleResponse(){
	// 判断对象状态  
	if (xhr.readyState == 4) {  
		var $e = $("#aktuals_field");
		var bgUrl = xhr.responseText;
		//alert("SUCCESS"+xhr.responseText);
		if(xhr.status == 200){
			$(".md_show").removeClass("md_show");
			$("#md_add").css({'background-image':'url('+bgUrl+')'}).addClass("md_show");
			$e.data("num", 0);
			stc(1);
		}
		
	}  
}
function del(num){
	if(!confirm("确定删除？注意：此操作不可恢复，请谨慎操作！"))return false;
	var el = $("#aktuals_field");
	var func =el.data("func");
	var module = el.data("module");
	var file = $("#xmlUrl").val();
	var fileName = $("#xmlName").val();
	var currPg = el.data("currPage"); 
	var num = el.data("num");
	if(!func){alert("获取功能名称发生错误！");return false;}
	if(!module){alert("获取模块名称发生错误！");return false;}
	if(!file){alert("获取文件名称发生错误！");return false;}
	$.ajax({
		async: false,
		url:'delStep.action',
		type:'post',
		data:{func:func, module:module, file:file, currPg:currPg, num:num},
		dataType:'json',
		contentType:'application/x-www-form-urlencoded; charset=UTF-8',
		success:function(data){
			alert("删除成功！");
			window.location.href="preAudit.action?xmlUrl="+file+"&xmlName="+fileName;
		}
	});
}

function getObjectURL(file) {
	var url = null ; 
	if (window.createObjectURL!=undefined) { // basic
		url = window.createObjectURL(file) ;
	} else if (window.URL!=undefined) { // mozilla(firefox)
		url = window.URL.createObjectURL(file) ;
	} else if (window.webkitURL!=undefined) { // webkit or chrome
		url = window.webkitURL.createObjectURL(file) ;
	}
	return url ;
}

function save(){
	var title = $("#md_title").val();
	var desc = $("#md_desc").val();
	var el = $("#aktuals_field");
	var func =el.data("func");
	var fileUrl = $("#xmlUrl").val();
	var fileName = $("#xmlName").val();
	if(!fileUrl){alert("获取文件名称发生错误！");return false;}
	if(!func){
		$.ajax({
			async: false,
			url:'updateSoft.action',
			type:'post',
			data:{fileUrl:fileUrl, title:title, desc:desc},
			dataType:'json',
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			success:function(data){
				alert("修改成功！");
				window.location.href="preAudit.action?xmlUrl="+fileUrl+"&xmlName="+fileName;
			}
		});
	}else{
		var module = el.data("module");
		if(!module){alert("获取模块名称发生错误！");return false;}
		$.ajax({
			async: false,
			url:'updateFunc.action',
			type:'post',
			data:{func:func, module:module, fileUrl:fileUrl, title:title, desc:desc},
			dataType:'json',
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			success:function(data){
				alert("修改成功！");
				window.location.href="preAudit.action?xmlUrl="+fileUrl+"&xmlName="+fileName;
			}
		});
	}
}

function cancel(e){
	
}
