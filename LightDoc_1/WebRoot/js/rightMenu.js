var btb=$(".rightNav");
var tempS;

$(".rightNav").hover(function(){
		var thisObj = $(this);
		tempS = setTimeout(function(){
		thisObj.find("a").each(function(i){
			var tA=$(this);
			setTimeout(function(){ tA.animate({right:"0"},300);},50*i);
		});
	},200);

},function(){
	$(".rightNav ul").find("li").each(function(){
		$(this).children("ul").slideUp(50);
	});
	if(tempS){ clearTimeout(tempS); }
	$(this).find("a").each(function(i){
		var tA=$(this);
		setTimeout(function(){ tA.animate({right:"-110"},300,function(){
		});},50*i);
	});

});

var isIE6 = !!window.ActiveXObject&&!window.XMLHttpRequest;
if( isIE6 ){ $(window).scroll(function(){ btb.css("top", $(document).scrollTop()+100);}); }
$(".rightNav li").click(function(){
	 $(this).children("ul").slideDown(500);
	 $(this).siblings("li").children("ul").slideUp("fast");
	 var val_tmp = $(this).find("a").find("b").html();
	 //$("#module").val(val_tmp);
	 $("#aktuals_field").removeData("module").data("module", val_tmp);
});

$(".rightNav li li a").click(function(){
	var val_module = $("#aktuals_field").data("module");
	var val_func = $(this).html();
	var fileUrl = $("#xmlUrl").val();
	if(val_module == null || val_module == undefined){
		alert("获取模块名称失败！");
		return false;
	}
	if(val_func == null || val_func == undefined){
		alert("获取功能名称失败！");
		return false;
	}
	if(fileUrl == null || fileUrl == undefined){
		alert("获取软件说明书名称失败！");
		return false;
	}
	$("#aktuals_field").removeData("func").data("func", val_func);
	$.ajax({
		async: false,
		url:'loadStep.action',
		type:'post',
		data:{val_module: val_module, val_func: val_func, fileUrl: fileUrl},
		dataType:'json',
		contentType:'application/x-www-form-urlencoded; charset=UTF-8',
		success:function(data){
			tvSlide.init(data);
		}
	});
	
});
