(function($, E){
	if ( ! E.seller ) {
		E.seller = {};
	}
	var deliver = E.seller.deliver = {}; // 电商项目 订单模块 命名空间
	var $form = $(".v_form");
	var $panel=$('#seller_page').find('.v_edit_store_form');
	var saveVoUrl= E.Cfg.contextPath+"/seller/order/delivery/saveLogistics.json";
	var LogisticsData;
	$.ajax({
		url:E.Cfg.contextPath+"/cij/js/logistics-company.json",
		dataType:"JSON",
		type : "POST",
		success:function(data){
			LogisticsData=data;
			for(var d in data){
				var $opt = $("<option>").text(data[d].expName).val(data[d].simpleName);
				//测试用
				if(data[d].simpleName === "yuantong"){
					$opt.attr("selected","selected");
				}
				$opt.appendTo($('.logistics-select'));
			}
		}
	});
	deliver.getFormdata = function($form){//获得表单数据
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
	
	deliver.changeSellerAddress=function($o,flag){
		$.ajax({
			url:E.Cfg.contextPath+"/user/address/vo_getUserAddress.json",
			type:"POST",
			dataType:"JSON",
			success:function(data){
				var html=[];
				var addid=$('.senddefid').val();
				if(flag==1){
					addid=$('.canceldefid').val();
				}
				for (var i = 0; i < data.length; i++){
					html.push('<label for="addr-default_'+i+'">'
				            +'<input type="radio" name="addressid" id="addr-default_'+i+'" '+(data[i].id==addid?"checked='checked'":"")+' value="'+data[i].id+'">'
				            +'<span class="addr">'+data[i].areas+' '+data[i].address+'</span>'
				            +'<span class="code"> '+data[i].zipcode+' </span>'
				            +'<span class="name"> '+data[i].receiver+' </span>'
				            +'<span class="tel">'+(data[i].mobiletel!=""?data[i].mobiletel:data[i].phone)+'</span></label>');
				}
				$panel.html(html).find(':radio').click(function(){
					var $sp=$(this).parent('label');
					if(flag==0){
						$('.senddefid').val($(this).val());
						$('.sendaddress').text($sp.find('.addr').text()+"，"+$sp.find('.code').text()+"，"+$sp.find('.name').text()+"，"+$sp.find('.tel').text());
					}else{
						$('.canceldefid').val($(this).val());
						$('.canceladdress').text($sp.find('.addr').text()+"，"+$sp.find('.code').text()+"，"+$sp.find('.name').text()+"，"+$sp.find('.tel').text());
					}
					$panel.foundation("reveal", "close");
					});
				$panel.foundation("reveal", "open");
			}
		});
	};
	
	$(function(){
//		$('.area-select').initArea();
		E.selectareas.init($('#seller_page'));
		$('[name="mailno"]').val("710032690831");
		$('.v_oper_edit').click(function(){
			var addressId=$('.addressId').val();
			if(addressId!=""){
				$.ajax({
	    			url:E.Cfg.contextPath+"/seller/order/delivery/address/"+addressId+".json",
	    			dataType:"JSON",
	    			type : "POST",
	    			data : {"addressId" : addressId},
	    			success: function(data) {
	    				$(".receiver").val(data.receiver);
	    				$(".address").val(data.address);
	    				$(".zipcode").val(data.zipcode);
	    				$(".mobiletel").val(data.mobiletel);
	    				$(".phone").val(data.phone);
//	    				E.areas.acccordToCode(data.district);
	    				E.selectareas.acccordToCode($('.v_edit_form'),data.district);
	    				$('.v_edit_form').foundation("reveal", "open");
	    			},
	    			error: function(error) {
	    				alert(error);
	    			}
	    		});
			}else{
				
			}
		});

		$('.v_oper_submit').click(function() {
			var $vh=$('.v_hidden');
			var $edf=$('#edit-order');
			var areas=$edf.find('.city-province')[0].options[$edf.find('.city-province')[0].selectedIndex].text + 
			$edf.find('.city-city')[0].options[$edf.find('.city-city')[0].selectedIndex].text + 
			$edf.find('.city-district')[0].options[$edf.find('.city-district')[0].selectedIndex].text;
			var distcode=$edf.find('.city-district')[0].options[$edf.find('.city-district')[0].selectedIndex].value;
			var detail=$edf.find(".address").val();
			var rn=$edf.find(".receiver").val();
			var zc=$edf.find(".zipcode").val();
			var mt=$edf.find(".mobiletel").val();
			var ph=$edf.find(".phone").val();
			var str=areas+" "+detail+(zc==null?"":("，"+zc))+"，"+rn+(mt==null?(ph==null?"":("，"+ph)):("，"+mt));
			$('.receive_address').text(str);
			$vh.find('.receiver').val(rn);
			$vh.find('.mobiletel').val(mt);
			$vh.find('.phone').val(ph);
			$vh.find('.areas').val(areas);
			$vh.find('.distcode').val(distcode);
			$vh.find('.address').val(detail);
			$vh.find('.zipcode').val(zc);
			$('.v_edit_form').foundation("reveal", "close");
		});
		
		$('.v_oper_save').click(function(){
			if(!RJ.E.util.verificationForm($form)){
				return;
			}
			$.ajax({
				type:"POST",
				url:saveVoUrl,
				data:deliver.getFormdata($form),
				success:function(data){
					if(data===""){
						var $panel=$('#seller_page').find('.v_info_warning');
						$panel.addClass("show");
				    	$panel.find(".v_show_info").text("该订单已发货！将返回订单发货列表！");
			    		setTimeout(function() {
			    			$panel.removeClass("show");
			    		}, 6000);
			    		location.href=Cfg.contextPath+"/seller/order/delivery.htm";
					}else{
						location.href=Cfg.contextPath+"/seller/order/delivery/result/"+data.orderId+".htm";
					}
				}
			});
		});
		
		$('.v_change_cancel').click(function(){
			deliver.changeSellerAddress($('.v_change_cancel'),1);
		});
		
		$('.v_change_send').click(function(){
			deliver.changeSellerAddress($('.v_change_send'),0);
		});
	});
	
})(jQuery,RJ.E);
