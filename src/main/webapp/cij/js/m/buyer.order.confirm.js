(function($, E){
	
	$(function() {
		var $confirm=$('.v_oper_confirm');
		$confirm.click(function(){
			var orderId=$('#orderId').val();
			$.ajax({
				url : E.Cfg.contextPath + "/buyer/order/detail/confirm/"+orderId+".json",
				type : "POST",
				data : {"orderId":orderId},
				success:function(data){
					var $form=$('#frame_main');
					var $panel=$form.find('.v_info_warning');
					$panel.addClass("show");
			    	$panel.find(".v_show_info").text("确认收获成功！");
		    		setTimeout(function() {
		    			$panel.removeClass("show");
		    		}, 3000);
					$form.foundation("reveal", "close");
					location.href=Cfg.contextPath+"/buyer/order/detail.htm?orderId="+orderId;
				},
				error:function(e){
					alert("错误："+e);
				}
			});
		});
	});
})(jQuery,RJ.E);