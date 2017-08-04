(function($, E){
	var include = E.userInfo = {}; // 电商项目  命名空间
	var a = E.Cfg.contextPath;
	include.getUserInfo = function(){
	                	$.ajax({
	            			url:E.Cfg.contextPath+"/user/vo_getuser.json",
	            			dataType:"JSON",
//	            			type : "POST",
	            			success: function(data) {
	            				//$("#user").val(data.loginName);
	            				if(typeof(data.nickName)!="undefined" && data.nickName!="" && data.nickName!=null){
	            					$("#userName").text(data.nickName);
	            				}else{
	            					$("#userName").text(data.loginName);
	            				}
	            				
	            			},
	            			error: function(error) {
	            				$(".v_info_fail").addClass("show");
	            				$(".v_info_fail").find(".v_show_info").text("服务器异常");
	            	    		setTimeout(function() {
	            	    		$(".v_info_fail").removeClass("show");
	            	    		}, 3000);
	            			}
	            		})
	                }
	
	include.checkIsLogin = function(userExistCall){
		var ifrid = 'lg' + new Date().getTime();
		$('body').append('<iframe id="'+ ifrid +'" src="'+E.Cfg.contextPath+'/user/vo_getuser.json" style="display:none"></iframe>')
		.find('#'+ifrid).on('load', function(){
			var url = encodeURIComponent(window.location.href);
			
			// 测试环境下调整登陆跳转，正式环境会调整
			url = encodeURIComponent( E.Cfg.contextPath + "/red.jsp?redUrl=" + window.location.href );
			
			$.ajax({
				url:E.Cfg.contextPath+"/user/novalidate/check_islogin.json",
				dataType:"JSON",
				success: function(data) {
					if(!data){
//						location.href="http://192.168.21.199:8888/login.sp?act=index&redirect="+url;
						include.openLogin(userExistCall);
						
					} else if (typeof userExistCall === 'function') { /* 存在用户时调用 */
						userExistCall();
					}
				},
				error: function(error) {
					$(".v_info_fail").addClass("show");
					$(".v_info_fail").find(".v_show_info").text("服务器异常");
		    		setTimeout(function() {
		    		$(".v_info_fail").removeClass("show");
		    		}, 3000);
				}
			});
			
			$('#'+ifrid).remove();
		})
	}
	
	include.openLogin = function(userExistCall) {
		var cb = 'loginSuccess';
		var url = 'http://192.168.14.202:9080/sso-server/sso_ws/getLogin'+
							'?host='+ encodeURIComponent(E.Cfg.contextPath)+
//							'?host='+ encodeURIComponent('http://192.168.4.200:8888/emall')+
							'&path='+ encodeURIComponent('/red.jsp?redUrl=' + encodeURIComponent('http://192.168.14.202:9080/cms/red.jsp?cb='+cb) );
		var htm = '<div id="loginPanel" class="reveal-modal openModal" data-reveal="" aria-labelledby="modalTitle" aria-hidden="true" role="dialog" style="width:430px; height:450px;">\
	        <form data-abide="" novalidate="novalidate">\
	            <h3>用户登陆</h3>\
	            <iframe style="width: 100%; height: 392px;" src="'+ url +'" marginwidth="0" marginheight="0" frameborder="0" allowtransparency=""></iframe>\
	        </form>\
	        <a class="close-reveal-modal" aria-label="Close">×</a>\
	      </div>';
		
		var $login = $('#loginPanel');
		if ($login.size() === 0) {
			$login = $(htm);
			$('body').append($login);
		}
		
		window[cb] = function(){
			$login.foundation("reveal", "close");
			//var $img = $('<img width=0 height=0 src="http://192.168.4.200:8888/emall/user/info.json"/>');
			//$img.bind('onload', function(){
				userExistCall();
			//	$img.remove();
			//});
			//$('body').append($img);
			
		}
		
		$login.foundation("reveal", "open");
	}
	
//	include.loginOut = function(){
//		var url=window.location.href;
//    	$.ajax({
//			url:E.Cfg.contextPath+"/emall/user/loginout.json",
//			type : "GET",
//			data : {"url" : url},
//			success: function(data) {
//				alert(data);
//				window.location.href=data;
//			},
//			error: function(error) {
//				alert("用户名为空");
//			}
//		})
//    }
	
	$(function(){
		if ($("#userName").size() > 0) { // 存在用户占位符则进行用户信息获取
			include.getUserInfo();
		}
		  
//			$('.loginOut').click(function(e){
//				include.loginOut();			
//				return false;
//			});
	});   
	 
	
})(jQuery, RJ.E);


