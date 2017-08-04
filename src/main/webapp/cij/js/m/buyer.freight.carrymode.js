(function($, E){
	var carry = E.carry = {};
	var $address=$('#addressId');
//	var carrysData;
//	if(carrysData==null){
//		$.ajax({
//			type:"POST",
//			url: Cfg.contextPath + "/seller/freight/carrys.json",
//			async:false,
//			dataType : "json",
//			success:function(data){
//				carrysData=data;
//			},
//			error:function(e){
//				alert("出错："+e);
//			}
//		});
//	}
	function appendOptTo($o, k, v, d) {
		var typestr=(v.serviceType=="POST"?"平邮":(v.serviceType=="EMS"?"EMS":"快递"));
		var feestr=v.fee=="0"?"免邮":(v.fee+"元");
		var str=typestr+"　"+feestr;
		var $opt = $("<option>").text(str).val(v.id);
		if (k == d) {
			$opt.attr("selected", "true");
		}
		$opt.appendTo($o);
	}
	/*
	 * 根据地区区号填充配送方式
	 * 结算页调用的函数
	 */
	carry.getcarrys=function(){
		var addressId=$address.val();
		var $table = $('.detail-list');
		$table.find('.shop-item').each(function(){
			var $o = $(this).find('.post-way');
			var storeId=$(this).find('[name="storeId"]').val();
			$o.html('');
			var addressandStore={
				addressId : addressId,
				storeId : storeId
			};
			$.ajax({
				url: Cfg.contextPath + "/seller/freight/carrys.json",
				type:"POST",
				dataType : "json",
				data : addressandStore,
				success:function(data){
					$.each(data.carryModeList, function(k, v) {
						appendOptTo($o,k,v,0);
					});
					E.carry.countFee();
				},
				error:function(e){
					alert("出错："+e);
				}
			});
			
//			var flag=0;
//			var code=areacode.substring(0,2)+"0000";
//			$.each(carrysData, function(k, v) {
//				if(v.isDefault=="1"&&v.areascodes!=null){
//					var codes=v.areascodes.split('_');
//					for(var c in codes){
//						if(c == code){
//							flag=1;
//						}
//					}
//				}
//			});
//			$.each(carrysData, function(k, v) {
//				if(flag==1&&v.isDefault=="1"){
//					appendOptTo($o,k,v,0);
//				}else{
//					if(v.isDefault=="0"){
//						appendOptTo($o,k,v,0);
//					}
//				}
//			});
		});
	};
	
	/*
	 * 计算运费
	 */
	carry.countFee=function(){
		var fee=0,tp=0;
		var $table = $('.detail-list');
		$table.find('.shop-item').each(function(){
			var $transp=$('.trans-price').find('num');
			var $totalp=$('.total-price').find('num');
//			var carryId=$(this).find(".post-way")[0].options[$(this).find(".post-way")[0].selectedIndex].value;
			var carryId=$(this).find(".post-way").val();
			if ( ! carryId ) {
				return;
			}
			
			var preOrderId=$(this).find('[name="preOrderId"]').val();
//			preOrderId="0icl8ctwy06y222z"; 
			var carryFee = {
					carryId: carryId,
					preOrderId : preOrderId
				};
				$.ajax({
					url: Cfg.contextPath + "/seller/freight/carryfee.json",
					type:"POST",
					dataType : "json",
					data : carryFee,
					success:function(v){
						var cal=parseInt(v.carryFee.fee);
						fee=fee+cal;
						fee=(fee /100.0).toFixed(2);
						var tcal=parseFloat(v.carryFee.price)*100+cal;
						tp=tp+tcal;
						tp=(tp /100.0).toFixed(2);
						$transp.text(fee);
						$totalp.text(tp);
					},
					error:function(e){
						alert("出错："+e);
					}
				});
		});
	};
//	carry.accAddress=function(){
//		var addressId=$address.val();
//		$.ajax({
//			url: Cfg.contextPath + "/user/address/vo_getAddress.json",
//			type:"POST",
//			dataType : "json",
//			data : {"addressId":addressId},
//			success:function(data){
//				E.carry.getcarrys(data.district);
//				E.carry.countFee();
//			},
//			error:function(e){
//				alert("出错："+e);
//			}
//		});
//	};
	$(function(){
//		$('.more-filter').find(':radio').click(function(){
//			$address.attr("value",$(this).val()).trigger("changeaddress", $(this).val());
//		});
//		E.carry.getcarrys();
		
		//绑定地址改变事件
		$address.bind("changeaddress", function () {
			E.carry.getcarrys();
		});
		
		//配送方式下拉改变
		var $table = $('.detail-list');
		$table.find('.shop-item').each(function(){
			$(".post-way").change(function(){
				E.carry.countFee();//买家改变运送方式后计算运费
			});
		});
		
	});
})(jQuery, RJ.E);