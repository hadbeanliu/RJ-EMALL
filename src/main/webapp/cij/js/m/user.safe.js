(function($, E){
	if ( ! E.user ) {
		E.user = {};
	}
	var safe = E.user.safe = {}; // 电商项目 用户安全中心模块 命名空间
	safe.data="";
	safe.getuserinfo = function(){
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
		safe.getuserinfo();
		if(typeof(safe.data.email)!="undefined"&&safe.data.email!=null&&safe.data.email!="null"&&typeof(safe.data.mobiletel)!="undefined"&&safe.data.mobiletel!=null&&safe.data.mobiletel!="null"){
			$('.safelevel').html("").append("<p class='left title-top-first'>安全级别：</p>"
                                           +"<img src='/emall/tpl/dsww/001/images/pic-9-103.png' alt='' class='left'/>&nbsp;"
                                           +"<p class='left' style='color:#09ab21'>&nbsp;&nbsp;高&nbsp;&nbsp;</p>"
                                           +"<p class='left' style='color: #c42927'><i class='fa fa-star'></i>您的安全设置已全部启用。</p>");
		}else if((typeof(safe.data.email)!="undefined"&&safe.data.email!=null&&safe.data.email!="null") || (typeof(safe.data.mobiletel)!="undefined"&&safe.data.mobiletel!=null&&safe.data.mobiletel!="null")){
			$('.safelevel').html("").append("<p class='left title-top-first'>安全级别：</p>"
                    +"<img src='/emall/tpl/dsww/001/images/pic-9-102.png' alt='' class='left'/>&nbsp;"
                    +"<p class='left' style='color:#09ab21'>&nbsp;&nbsp;较高&nbsp;&nbsp;</p>"
                    +"<p class='left' style='color: #c42927'><i class='fa fa-star'></i>建议您启动全部安全设置，以保障账户及资金安全。</p>");
		}else{
			$('.safelevel').html("").append("<p class='left title-top-first'>安全级别：</p>"
                    +"<img src='/emall/tpl/dsww/001/images/pic-92.png' alt='' class='left'/>&nbsp;"
                    +"<p class='left' style='color:#09ab21'>&nbsp;&nbsp;低&nbsp;&nbsp;</p>"
                    +"<p class='left' style='color: #c42927'><i class='fa fa-star'></i>建议您启动全部安全设置，以保障账户及资金安全。</p>");
		}
		if(typeof(safe.data.email)!="undefined"&&safe.data.email!=null&&safe.data.email!="null"){
			$(".emailimg").html("").append("<img src='/emall/tpl/dsww/001/images/pic-9-3.png' alt='' class='left' style='width: 66px'/><p class='p-font-25 left'>邮箱验证</p><img src='/emall/tpl/dsww/001/images/pic-9-4.png' alt='' class='left'/>")
			$(".emailcheck").html("").append("<a class='font-14-gray mar-top-safe-center a-underline a-sa-center-edit editemail' >修改</a>");
			var reg = /(.{2}).+(.{2}@.+)/g;
			$(".emailvalue").html("您验证的邮箱:"+safe.data.email.replace(reg, "$1****$2"));
		}
		if(typeof(safe.data.mobiletel)!="undefined"&&safe.data.mobiletel!=null&&safe.data.mobiletel!="null"){
			$(".mobiletelimg").html("").append("<img src='/emall/tpl/dsww/001/images/pic-9-1.png' alt='' class='left' style='width: 66px'/><p class='p-font-25 left'>手机验证</p><img src='/emall/tpl/dsww/001/images/pic-9-4.png' alt='' class='left'/>")
			$(".mobiletelcheck").html("").append("<a class='font-14-gray mar-top-safe-center a-underline a-sa-center-edit editmobiletel'>修改</a>");
			var reg = /(.{3}).+(.{3}.+)/g;
			$(".mobiletelvalue").html("您验证的手机:"+safe.data.mobiletel.replace(reg, "$1****$2"));
		}
	});
	
	$(".pwdedit").click(function(){
	 	    if((typeof(safe.data.mobiletel)=="undefined"||safe.data.mobiletel==null||safe.data.mobiletel=="null")&&(typeof(safe.data.email)=="undefined"||safe.data.email==null||safe.data.email=="null")){
	 	    	$(".v_info_fail").find(".v_show_info").text("请先绑定手机或邮箱");
	    		setTimeout(function() {
	    		$(".v_info_fail").removeClass("show");
	    		}, 3000);
	 	    }else{
	 	    	window.location.href = "/emall/user/safe/checkmobiletel.htm";
	 	    }
			
		});
	$(".emailcheck").click(function(){
		window.location.href = "/emall/user/safe/checkpwdtoemail.htm";
	})
	$(".mobiletelapply").click(function(){
		window.location.href = "/emall/user/safe/checkpwdtomobiletel.htm";
	})
	$("body").on("click",".editemail",function(){
	 	   window.location.href = "/emall/user/safe/checkemail.htm";
		   
		});
	$("body").on("click",".editmobiletel",function(){
	 	   window.location.href = "/emall/user/safe/checknewmobiletel.htm";
		});
	
})(jQuery, RJ.E);