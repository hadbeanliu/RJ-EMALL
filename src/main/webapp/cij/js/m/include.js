(function($, E){
	var include = E.include = {}; // 电商项目  命名空间
	var a = E.Cfg.contextPath;
	include.InitUser = function(){
	                	$.ajax({
	            			url:E.Cfg.contextPath+"/user/vo_getuser.json",
	            			dataType:"JSON",
	            			type : "POST",
	            			success: function(data) {
	            				//$("#user").val(data.loginName);
	            				$("#userName").html(data.loginName);
	            			},
	            			error: function(error) {
	            				alert("用户名为空");
	            			}
	            		})
	                }
	
	include.loginOut = function(){
		var url=window.location.href;
    	$.ajax({
			url:E.Cfg.contextPath+"/emall/user/loginout.json",
			type : "GET",
			data : {"url" : url},
			success: function(data) {
				alert(data);
				window.location.href=data;
			},
			error: function(error) {
				alert("用户名为空");
			}
		})
    }
	
	$(function(){
		  include.InitUser();
		  
			$('.loginOut').click(function(e){
				include.loginOut();			
				return false;
			});
	});   
	 
	
})(jQuery, RJ.E);


