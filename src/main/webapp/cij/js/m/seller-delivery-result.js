(function($, E){
	if ( ! E.seller ) {
		E.seller = {};
	}
	var delresult = E.seller.delresult = {}; // 电商项目 订单模块 命名空间
	var $form = $(".v_form");
	var saveVoUrl= E.Cfg.contextPath+"/seller/order/delivery/updatelog.json";
	var LogisticsData;
	delresult.getFormdata = function($form){//获得表单数据
		var datas = {};
    	$form.find("[name]").each(function(){
    		var val = $(this).val();
    		if (this.type === 'checkbox' || this.type === 'radio') {
    			val = [];
    			$form.find("[name="+ this.name +"]:checked").each(function(){
    				val.push(this.value);
    			});
    			if (val.length === 1) {
    				val = val[0];
    			}
    		}
    		datas[this.name] = val;
    	});

    	return datas;
	};
	
	$(function(){
		var code=$('.logcode').text();
		$.ajax({
			url:E.Cfg.contextPath+"/cij/js/logistics-company.json",
			dataType:"JSON",
			type : "POST",
			success:function(data){
				LogisticsData=data;
				for(var ld in LogisticsData){
					if(code==LogisticsData[ld].simpleName){
						var lgn=LogisticsData[ld].expName;
						$('.logcode').text(lgn);
						break;
					}
				}
			}
		});
		$('.v_oper_edit').click(function(){
			var logisticId=$('.select_id').val();
			var $form=$('.v_edit_form');
			$form.find('.logisticId').val(logisticId);
			$form.foundation("reveal", "open");
		});
		$('.v_oper_submit').click(function(){
			var $form=$('.v_edit_form');
			if(!RJ.E.util.verificationForm($form)){
				return;
			}
			$.ajax({
				type:"POST",
				url:saveVoUrl,
				data:delresult.getFormdata($form),
				success:function(data){
					$('.mailno').text(data.mailno);
					var $panel=$('#seller_page').find('.v_info_warning');
					$panel.addClass("show");
			    	$panel.find(".v_show_info").text("保存成功！");
		    		setTimeout(function() {
		    			$panel.removeClass("show");
		    		}, 3000);
					$form.foundation("reveal", "close");
				}
			});
		});
	});
	
})(jQuery,RJ.E);
