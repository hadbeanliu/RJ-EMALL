(function($, E){
	var reg = E.reg = {}; // 电商项目 用户登录 命名空间
	
	reg.isexit = true;
	window['registet']=function(){
		reg.my_submit();
	}
	reg.my_submit = function(){
		if(reg.isexit==false){
			return false;
		}else{
			var $form = $(".form");
			$.ajax({
				url:E.Cfg.contextPath+"/user/novalidate/vo_save.json",
				dataType:"JSON",
				type : "POST",
				data : reg.getUserData($form),
				success: function(data) {
					console.log(JSON.stringify(data));
					location.href="/emall";
				},
				error: function(error) {
					alert(error);
				}
			})
		}
	} 
	
	reg.get_code = function(){        //获取手机短信验证码    	
		var $form = $(".form");
		var mobiletel = $form.find("input[name='mobiletel']").val();
		if(mobiletel==""){
			//
		}else{
			$.ajax({
				//url:"http://192.168.4.69/message?act=sendMessage",
				url:E.Cfg.contextPath+"/user/novalidate/check/sendcode.json",
				type : "POST",
				data : {"phone" : mobiletel},
				success: function(data) {
					if(data=="00"){
					}
				},
				error: function(error) {
					alert(error);
				}
			})
		}
	}
	
	reg.checkCode = function(page){            	//校验手机短信验证码
		var $form = $(".form");
		var code = $form.find("input[name='code']").val();
		if(code==""){
			$(page).parent().addClass("error");
			$(page).parent().find('.error').html("验证码不能为空");
		}else{
			$.ajax({ 
				//url:"http://192.168.4.69/message?act=cheakCode",
				url:E.Cfg.contextPath+"/user/novalidate/check/checkcode.json",
				type : "POST",
				data : {"code" : code},
				success: function(data) {
					if(data=="right"){
					}else if(data=="error overtime"){
						$(page).parent().addClass("error");
						$(page).parent().find('.error').html("验证码已过期");
					}else{
						$(page).parent().addClass("error");
						$(page).parent().find('.error').html("验证码校验失败");
					}
				},
				error: function(error) {
					alert(error);
				}
			})
		}
	}
	
	reg.checkLoginName = function(page){            	
		var $form = $(".form");
		reg.isexit=true;
		var loginName = $form.find("input[name='loginName']").val();
		if(loginName==""){
			$(page).parent().addClass("error");
			$(page).parent().find('.error').html("用户名不能为空");
		}else if(loginName.length<6||loginName.length>20){
			$(page).parent().addClass("error");
			$(page).parent().find('.error').html("用户名长度6-20位之间");
		}else{
			$.ajax({
				url:E.Cfg.contextPath+"/user/novalidate/vo_checkName.json",
				dataType:"JSON",
				type : "POST",
				data : {"loginName":loginName},
				success: function(data) {
					//alert(JSON.stringify(data));
					if(JSON.stringify(data)=="false"){
					}else{
						$(page).parent().addClass("error");
						$(page).parent().find('.error').html("用户名已存在");
						reg.isexit=false;
						
					}
					
				},
				error: function(error) {
					alert(error);
				}
			})
		}
	}
	
	reg.checkPhone = function(page){
		var mobiletel=$(".phone-bg").val();
		$(page).parent().find('.error').html("手机号码格式不对");
		if(mobiletel==""){
			$(page).parent().addClass("error");
			$(page).parent().find('.error').html("请输入手机号");
		}else{
			$.ajax({
				url:E.Cfg.contextPath+"/user/novalidate/checkphone.json",
				dataType:"JSON",
				type : "POST",
				data : {"mobiletel":mobiletel},
				success: function(data) {
					//alert(JSON.stringify(data));
					if(data==false){
						
					}else{
						//$(page).parent().addClass("error");
						//$(page).parent().find('.error').html("手机已存在");
					}
					
				},
				error: function(error) {
					alert(error);
				}
			})
		}
	}
	
	reg.social_reg = function(){            	
		var $form = $(".form");
		socialUserId = $("#socialUserId").val();
		socialId = $("#psmrID").val();
		var data=reg.getUserData($form);
		data['socialId']=socialId;
		data['socialUserId']=socialUserId;
		$.ajax({
			url:E.Cfg.contextPath+"/user/novalidate/vo_socailsave.json",
			dataType:"JSON",
			type : "POST",
			data : data,
			success: function(data) {
				console.log(JSON.stringify(data));
				location.href="/emall/user.htm";
			},
			error: function(error) {
				alert(error);
			}
		})
	}
	
	reg.getUserData = function(){            	
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
		$('body').on("blur",".checkName",function(){
			reg.checkLoginName(this);
		});
		$('body').on("blur",".phone-bg",function(){
			reg.checkPhone(this);
		});
		$('.getcode').click(function(e){
			reg.get_code();			
			return false;
		});
		$('body').on("blur",".checkcode",function(){
			reg.checkCode(this);
		});
		//$('.reg_ajax_submit').click(function(e){
//			reg.my_submit();			
//			return false;
		//});
		$('.social_ajax_submit').click(function(e){
			reg.social_reg();			
			return false;
		});
		
		 $('#btn-get-password').click(function () {
			 var mobiletel=$(".phone-bg").val();
			 if(mobiletel==""){
				 
			 }else{
				 var count = 60;
	             var countdown = setInterval(CountDown, 1000);
	             function CountDown() {
	               $("#btn-get-password").attr("disabled", true);
	               $("#btn-get-password").val('重新获取（'+count + "）");
	               $("#btn-get-password").css({'background':'#f0f3f5','color':'#c9c9ca','border':'1px solid #c9c9ca','cursor':'not-allowed'});
	               if (count == 0) {
	                 $("#btn-get-password").val("重新获取验证码").removeAttr("disabled").removeAttr("style");
	                 clearInterval(countdown);
	               }
	               count--;
	             }
			 }
           })
		
		$form = $(".form");
		url = "/emall/user/novalidate/user_validate.json";
		RJ.E.util.bindFormVerificationByUrl($form,url);
		
//		$(document).foundation({//非公共部分，自定义验证方法
//			abide : {
//				validators : {
//					validCode : function(el, required, parent) {
//						//el.value 表单值
//						//需要得到的手机短信上的验证码
//						var m_code = "";
//						if(el.value == m_code){
//							return true;
//						}
//						//$(el).parent("div").find('small').html('验证码错误');
//						return false;
//					},
//				}
//			}
//		});
	});

})(jQuery, RJ.E);