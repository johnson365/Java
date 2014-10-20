/*导航栏点击事件处理函数
 * 点击导航栏的叶子节点，在右侧显示详细信息*/
$(document).ready(function(){
$("#menu ul ul li a").click(function(){
	var value = $(this).html(); 
	$(".sf-sub-indicator").detach();
	var pre = $(this).parent().parent().prev().prev().text();
	var xmlName = $("#xmlName").val();
	$.ajax({
		async: false,
		url:'loadStep.action',
		type:'get',
		data:{val_task: pre, val_step: value, fileName: xmlName},
		dataType:'json',
		contentType:'application/x-www-form-urlencoded; charset=UTF-8',
		success:function(data){
			$("#deteil").empty();
			 var tmp = eval("("+data+")");
			 var d = tmp.stepTmp;
			$("#deteil").append('<h6 class="header-line">功能名称：'+d.stepTitle+'</h6>');
			$("#deteil").append('<h6 class="header-line">功能描述：'+d.stepDesc+'</h6>');
			$("#deteil").append('<h6 class="header-line">具体步骤如下：</h6>');
			$(d.cmds).each(function (i, value){
				$("#deteil").append('<h6 class="header-line">'+value.cmdText+'</h6>');
			});
		}
	});
});

$(".rightNav a").click(function(){
	alert("hello");
});

$("#publish").click(function(){
	var fileName = $("#xmlName").val();
	window.location.href="audit.action?fileName="+fileName;
});

$("#modify").click(function(){
	var fileName = $("#xmlName").val();
	window.location.href="preModify.action?fileName="+fileName;
});
});

	


