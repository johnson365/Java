window.onload=function(){
	  document.getElementById("username").focus();
};

document.onkeydown = function(e){
	if(!e) e = window.event;
	if((e.keyCode || e.which) == 13){
		var obtnLogin=document.getElementById("submit_btn");
		obtnLogin.focus();
	}
};

$(function(){
	$('#submit_btn').click(function(){
		show_loading();
		//var url = $('#login_form').attr("action");
		if($('.username').val() == ''){
			show_err_msg('用户名为空！');	
			$('.username').focus();
			return false;
		}else if($('.password').val() == ''){
			show_err_msg('密码为空！');
			$('.password').focus();
			return false;
		}else{
			show_msg('登陆中...');	
			 var form = document.getElementById("login_form");
			 form.method ="post";
			 form.submit();
		}
	});
});

$(function(){
	$('#reset_btn').click(function(){
		if($('.username').val() != '')
		{
			$('.username').val("");
		}
		if($('.password').val() != '')
		{
			$('.password').val("");
		}
		$('.username').focus();
	});
});


