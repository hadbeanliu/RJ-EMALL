(function($, E){
	if ( ! E.user ) {
		E.user = {};
	}
	if ( ! E.user.safe ) {
		E.user.safe = {};
	}
	var editpwd = E.user.editpwd = {}; // 电商项目 用户安全中心模块 命名空间
	var reg = /(.{3}).+(.{3}.+)/g;
	var reg1 = /(.{2}).+(.{2}@.+)/g;
	editpwd.editpassword = function(){
		$.ajax({
			url:E.Cfg.contextPath+"/user/vo_getuser.json",
			dataType:"JSON",
			type : "POST",
			async:false,
			success: function(data) {
				safe.data=data;
			},
			error: function(error) {
				alert(error);
			}
		})
	}
	
	$(function(){
		if(E.user.safe.data.mobiletel!=null){
			$(".validmobiletel").html(E.user.safe.data.mobiletel.replace(reg, "$1****$2"));
		}
		if(E.user.safe.data.email!=null){
			$(".validemail").html(E.user.safe.data.email.replace(reg1, "$1****$2"));
		}
		
		$('.confirmmobiletel').click(function(){
			var code= $('[name=code]').val();
			if(code==""){
				$(".v_info_fail").addClass("show");
				$(".v_info_fail").find(".v_show_info").text("请输入验证码");
	    		setTimeout(function() {
	    		$(".v_info_fail").removeClass("show");
	    		}, 3000);
			}else{
				$.ajax({
					url:E.Cfg.contextPath+"/user/check/checkcode.json",
					type : "POST",
					data : {"code" : code},
					success: function(data) {
						if(data=="right"){
							window.location.href = "editpassword.htm";
						}else if(data=="error overtime"){
							$(".v_info_fail").addClass("show");
							$(".v_info_fail").find(".v_show_info").text("验证码已过期，请重新获取");
				    		setTimeout(function() {
				    		$(".v_info_fail").removeClass("show");
				    		}, 3000);
						}else{
							$(".v_info_fail").addClass("show");
							$(".v_info_fail").find(".v_show_info").text("验证码错误");
				    		setTimeout(function() {
				    		$(".v_info_fail").removeClass("show");
						    }, 3000);
						}
					},
					error: function(error) {
						$(".v_info_fail").addClass("show");
						$(".v_info_fail").find(".v_show_info").text("服务器异常，请稍后再试");
			    		setTimeout(function() {
			    		$(".v_info_fail").removeClass("show");
					    }, 3000);
				    }	
				})
			}
		})
		
		$('.newconfirmmobiletel').click(function(){
			var code= $('[name=code]').val();
			if(code==""){
				$(".v_info_fail").addClass("show");
				$(".v_info_fail").find(".v_show_info").text("请输入验证码");
	    		setTimeout(function() {
	    		$(".v_info_fail").removeClass("show");
	    		}, 3000);
			}else{
				$.ajax({
					url:E.Cfg.contextPath+"/user/check/checkcode.json",
					type : "POST",
					data : {"code" : code},
					success: function(data) {
						if(data=="right"){
							window.location.href = "editmobiletel.htm";
						}else if(data=="error overtime"){
							$(".v_info_fail").addClass("show");
							$(".v_info_fail").find(".v_show_info").text("验证码已过期，请重新获取");
				    		setTimeout(function() {
				    		$(".v_info_fail").removeClass("show");
				    		}, 3000);
						}else{
							$(".v_info_fail").addClass("show");
							$(".v_info_fail").find(".v_show_info").text("验证码错误");
				    		setTimeout(function() {
				    		$(".v_info_fail").removeClass("show");
						    }, 3000);
						}
					},
					error: function(error) {
						$(".v_info_fail").addClass("show");
						$(".v_info_fail").find(".v_show_info").text("服务器异常，请稍后再试");
			    		setTimeout(function() {
			    		$(".v_info_fail").removeClass("show");
					    }, 3000);
				    }	
				})
			}
		})
		
		$('.editconfirmnewmobiletel').click(function(){
			var code= $('[name=code]').val();
			var phone= $('[name=mobiletel]').val();
			if(code==""){
				$(".v_info_fail").addClass("show");
				$(".v_info_fail").find(".v_show_info").text("请输入验证码");
	    		setTimeout(function() {
	    		$(".v_info_fail").removeClass("show");
	    		}, 3000);
			}else if(phone==""){
				$(".v_info_fail").addClass("show");
				$(".v_info_fail").find(".v_show_info").text("");
	    		setTimeout(function() {
	    		$(".v_info_fail").removeClass("show");
	    		}, 3000);
			}else{
				$.ajax({
					url:E.Cfg.contextPath+"/user/check/checkcode.json",
					type : "POST",
					data : {"code" : code},
					success: function(data) {
						if(data=="right"){
							$.ajax({
								url:E.Cfg.contextPath+"/user/safe/checkmobiletelcomplete.json",
								type : "POST",
								data : {"mobiletel" : phone},
								success: function(data) {
									window.location.href = "complete.htm";
								},
								error: function(error) {
									$(".v_info_fail").addClass("show");
										$(".v_info_fail").find(".v_show_info").text("服务器异常发送失败，请稍后再试");
							    		setTimeout(function() {
							    		$(".v_info_fail").removeClass("show");
							    		}, 3000);
								}
							})
						}else if(data=="error overtime"){
							$(".v_info_fail").addClass("show");
							$(".v_info_fail").find(".v_show_info").text("验证码已过期，请重新获取");
				    		setTimeout(function() {
				    		$(".v_info_fail").removeClass("show");
				    		}, 3000);
						}else{
							$(".v_info_fail").addClass("show");
							$(".v_info_fail").find(".v_show_info").text("验证码错误");
				    		setTimeout(function() {
				    		$(".v_info_fail").removeClass("show");
						    }, 3000);
						}
					},
					error: function(error) {
						$(".v_info_fail").addClass("show");
						$(".v_info_fail").find(".v_show_info").text("服务器异常，请稍后再试");
			    		setTimeout(function() {
			    		$(".v_info_fail").removeClass("show");
					    }, 3000);
				    }	
				})
			}
		})
		
		$('#btn-check-emailcheck').click(function() {
			$.ajax({
				url:E.Cfg.contextPath+"/user/safe/sendcheckemail.json",
				type : "POST",
				data : {"email" : E.user.safe.data.email},
				success: function(data) {
					window.location.href = "sendemailcomplete.htm";
				},
				error: function(error) {
					$(".v_info_fail").addClass("show");
						$(".v_info_fail").find(".v_show_info").text("服务器异常发送失败，请稍后再试");
			    		setTimeout(function() {
			    		$(".v_info_fail").removeClass("show");
			    		}, 3000);
				}
			})
		})
		
		$('.checkpwd').click(function(){
			var pwd= $('[name=password]').val();
			if(pwd==""){
				$(".v_info_fail").addClass("show");
				$(".v_info_fail").find(".v_show_info").text("请输入密码");
	    		setTimeout(function() {
	    		$(".v_info_fail").removeClass("show");
	    		}, 3000);
			}else{
				$.ajax({
					url:E.Cfg.contextPath+"/user/checkuserpwd.json",
					type : "POST",
					data : {"pwd" : pwd},
					success: function(data) {
						if(data==true){
							window.location.href = "editnewemail.htm";
						}else{
							$(".v_info_fail").addClass("show");
							$(".v_info_fail").find(".v_show_info").text("密码错误请重新输入");
				    		setTimeout(function() {
				    		$(".v_info_fail").removeClass("show");
				    		}, 3000);
						}
						
					},
					error: function(error) {
						$(".v_info_fail").addClass("show");
							$(".v_info_fail").find(".v_show_info").text("服务器异常发送失败，请稍后再试");
				    		setTimeout(function() {
				    		$(".v_info_fail").removeClass("show");
				    		}, 3000);
					}
				})
			}
		})
		
		$('.checkpwdtomobiletel').click(function(){
			var pwd= $('[name=password]').val();
			if(pwd==""){
				$(".v_info_fail").addClass("show");
				$(".v_info_fail").find(".v_show_info").text("请输入密码");
	    		setTimeout(function() {
	    		$(".v_info_fail").removeClass("show");
	    		}, 3000);
			}else{
				$.ajax({
					url:E.Cfg.contextPath+"/user/checkuserpwd.json",
					type : "POST",
					data : {"pwd" : pwd},
					success: function(data) {
						if(data==true){
							window.location.href = "editmobiletel.htm";
						}else{
							$(".v_info_fail").addClass("show");
							$(".v_info_fail").find(".v_show_info").text("密码错误请重新输入");
				    		setTimeout(function() {
				    		$(".v_info_fail").removeClass("show");
				    		}, 3000);
						}
						
					},
					error: function(error) {
						$(".v_info_fail").addClass("show");
							$(".v_info_fail").find(".v_show_info").text("服务器异常发送失败，请稍后再试");
				    		setTimeout(function() {
				    		$(".v_info_fail").removeClass("show");
				    		}, 3000);
					}
				})
			}
		})
		
	  $('.sendnewemail').click(function() {
		   var newemail= $('[name=newemail]').val();
		   var re = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
		   if(newemail==""){
			   $(".v_info_fail").addClass("show");
				$(".v_info_fail").find(".v_show_info").text("邮箱不能为空");
	    		setTimeout(function() {
	    		$(".v_info_fail").removeClass("show");
	    		}, 3000);
		   }else if(!re.test(newemail)){
			   $(".v_info_fail").addClass("show");
				$(".v_info_fail").find(".v_show_info").text("邮箱格式不正确");
	    		setTimeout(function() {
	    		$(".v_info_fail").removeClass("show");
	    		}, 3000);
		   }else{
			   $.ajax({
					url:E.Cfg.contextPath+"/user/safe/sendnewemail.json",
					type : "POST",
					data : {"email" : newemail},
					success: function(data) {
						window.location.href = "sendemailcomplete.htm";
					},
					error: function(error) {
						$(".v_info_fail").addClass("show");
							$(".v_info_fail").find(".v_show_info").text("服务器异常发送失败，请稍后再试");
				    		setTimeout(function() {
				    		$(".v_info_fail").removeClass("show");
				    		}, 3000);
					}
				})
		   }
		})
		
		 $('#btn-check-pcheck').click(function () {
             $.ajax({
 				url:E.Cfg.contextPath+"/user/check/sendcode.json",
 				type : "POST",
 				data : {"phone" : E.user.safe.data.mobiletel},
 				success: function(data) {
 					if(data=="00"){
 						$('.check-des-down').show();
 			             var count = 60;
 			             var countdown = setInterval(CountDown, 1000);
 			             function CountDown() {
 			                 $("#btn-check-pcheck").attr("disabled", true);
 			                 $("#btn-check-pcheck").val(count + " 秒后,重新获取验证码");
 			                 $("#btn-check-pcheck").css({'background':'#f0f3f5','color':'#c9c9ca','border':'1px solid #c9c9ca','cursor':'not-allowed'});
 			                 if (count == 0) {
 			                     $("#btn-check-pcheck").val("重新获取验证码").removeAttr("disabled").removeAttr("style");
 			                     clearInterval(countdown);
 			                     $('.check-des-down').hide();
 			                 }
 			                 count--;
 			             }
 						$(".v_info_success").addClass("show");
 						$(".v_info_success").find(".v_show_info").text("发送验证码成功");
 			    		setTimeout(function() {
 			    		$(".v_info_success").removeClass("show");
 			    		}, 3000);
 					}else{
 						$(".v_info_fail").addClass("show");
 						$(".v_info_fail").find(".v_show_info").text("发送验证码失败");
 			    		setTimeout(function() {
 			    		$(".v_info_fail").removeClass("show");
 			    		}, 3000);
 					}
 				},
 				error: function(error) {
 					alert(error);
 				}
 			})
         })
		
          $('#btn-check-pcheck-new').click(function () {
        	 var phone= $('[name=mobiletel]').val();
        	 if(phone==""){
        		 $(".v_info_fail").addClass("show");
 				$(".v_info_fail").find(".v_show_info").text("请输入手机号");
 	    		setTimeout(function() {
 	    		$(".v_info_fail").removeClass("show");
 	    		}, 3000);
        	 }else{
                 $.ajax({
      				url:E.Cfg.contextPath+"/user/check/sendcode.json",
      				type : "POST",
      				data : {"phone" : phone},
      				success: function(data) {
      					if(data=="00"){
      						$('.check-des-down').show();
      			             var count = 60;
      			             var countdown = setInterval(CountDown, 1000);
      			             function CountDown() {
      			                 $("#btn-check-pcheck-new").attr("disabled", true);
      			                 $("#btn-check-pcheck-new").val(count + " 秒后,重新获取验证码");
      			                 $("#btn-check-pcheck-new").css({'background':'#f0f3f5','color':'#c9c9ca','border':'1px solid #c9c9ca','cursor':'not-allowed'});
      			                 if (count == 0) {
      			                     $("#btn-check-pcheck-new").val("重新获取验证码").removeAttr("disabled").removeAttr("style");
      			                     clearInterval(countdown);
      			                     $('.check-des-down').hide();
      			                 }
      			                 count--;
      			             }
      						$(".v_info_success").addClass("show");
      						$(".v_info_success").find(".v_show_info").text("发送验证码成功");
      			    		setTimeout(function() {
      			    		$(".v_info_success").removeClass("show");
      			    		}, 3000);
      					}else{
      						$(".v_info_fail").addClass("show");
      						$(".v_info_fail").find(".v_show_info").text("发送验证码失败");
      			    		setTimeout(function() {
      			    		$(".v_info_fail").removeClass("show");
      			    		}, 3000);
      					}
      				},
      				error: function(error) {
      					alert(error);
      				}
      			})
        	 }

         })
         
         $('.completesave').click(function () {
        	 var oldPwd= $('[name=password]').val();
             var newPwd=$('[name=newpassword]').val();
             var snewPwd=$('[name=snewpassword]').val();
             if(oldPwd==""){
            	 $(".v_info_fail").addClass("show");
					$(".v_info_fail").find(".v_show_info").text("请输入旧密码");
		    		setTimeout(function() {
		    		$(".v_info_fail").removeClass("show");
		    		}, 3000);
             }else if(newPwd==""){
            	    $(".v_info_fail").addClass("show");
					$(".v_info_fail").find(".v_show_info").text("请输入新密码");
		    		setTimeout(function() {
		    		$(".v_info_fail").removeClass("show");
		    		}, 3000);
             }else if(newPwd.length>20||newPwd.length<6){
         	    $(".v_info_fail").addClass("show");
				$(".v_info_fail").find(".v_show_info").text("新密码长度6-20位");
	    		setTimeout(function() {
	    		$(".v_info_fail").removeClass("show");
	    		}, 3000);
            	 
             }else if(snewPwd!=newPwd){
            	 $(".v_info_fail").addClass("show");
					$(".v_info_fail").find(".v_show_info").text("新密码输入不一致");
		    		setTimeout(function() {
		    		$(".v_info_fail").removeClass("show");
		    		}, 3000);
             }else{
            	 $.ajax({
      				url:E.Cfg.contextPath+"/user/vo_editpwd.json",
      				type : "POST",
      				data : {"oldPwd" : oldPwd,"newPwd" : newPwd},
      				success: function(data) {
      					if(data=="密码修改成功"){
      						window.location.href = "complete.htm";
      					}else if(data=="原密码错误"){
      						$(".v_info_fail").addClass("show");
      						$(".v_info_fail").find(".v_show_info").text("旧密码错误");
      			    		setTimeout(function() {
      			    		$(".v_info_fail").removeClass("show");
      			    		}, 3000);
      					}else if(data=="请输入新密码"){
      						alert("新密码为空");
      					}
      				},
      				error: function(error) {
      					alert(error);
      				}
      			})
             }
         })
	});
	
})(jQuery, RJ.E);