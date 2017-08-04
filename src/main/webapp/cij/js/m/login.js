(function($, E){
	var login = E.login = {}; // 电商项目 用户登录 命名空间
	
	$("#login").dataTablePage({
		url : E.Cfg.contextPath+"/user/login", // 请求地址
		checkboxMode : false // 提醒实现位置
	});
	
	login.my_login = function(){            	
		var $form = $(".form");
		$.ajax({
			url:E.Cfg.contextPath+"/user/login/vo_login.json",
			dataType:"JSON",
			type : "POST",
			data : login.getUserData($form),
			success: function(data) {
				//alert(JSON.stringify(data));
				if(JSON.stringify(data)=="true"){
					location.href="/emall/user.htm";
				}else{
					alert('用户名或密码错误');
				}
				
			},
			error: function(error) {
				alert(error);
			}
		})
	} 

	login.my_logins = function(){            	
		var $form = $(".form");
		$.ajax({
			url:E.Cfg.contextPath+"/user/login/vo_login.json",
			dataType:"JSON",
			type : "POST",
			data : login.getUserData($form),
			success: function(data) {
				//alert(JSON.stringify(data));
				if(JSON.stringify(data)=="true"){
					location.href="/emall/authentication.htm";
				}else{
					alert('用户名或密码错误');
				}
				
			},
			error: function(error) {
				alert(error);
			}
		})
	}
	
	login.getUserData = function(){            	
		var datas = {};
		$('.form').find("[name]").each(function(){
    		var val = $(this).val();
    		if (this.type === 'checkbox') {
    			val = [];
    			form.find("[name="+ this.name +"]:checked").each(function(){
    				val.push(this.value);
    			});
    		}
    		datas[this.name] = val;
    	});
    	
    	if ( ! datas.id ) {
    		delete datas['id'];
    	}
    	
    	return datas;
	}
	
	$(function(){ // 绑定操作
		RJ.E.social.getMedia2('emall','00000005');
		
		$('.user_ajax_submit').click(function(e){
			login.my_login();			
			return false;
		});
		
		$('.authentication_ajax_submit').click(function(e){
			login.my_logins();			
		});
		
	});
})(jQuery, RJ.E);