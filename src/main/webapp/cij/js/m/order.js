(function($, E){
	var order = E.order = {}; // 电商项目 订单模块 命名空间
	
	// 测试数据
	var goods = {
			goodsId: "AR2015051907000026", // 商品ID
			goodsPrice: "50.00", // 商品价格
			goodsTitle: "壳牌（shell） 全新防伪 超凡喜力车用轮滑油", // 商品标题
			goodsImage: E.Cfg.contextPath+ "/Ebusiness/images/merchandise04.gif",
			skuId: '11112222', // 购买型号
			purchasePrice: "24.70", // 购买价格
			purchaseQuantity: 6, // 购买数量
			discount: 0, // 折扣金额
			discountTitle: '' // 折扣描述
	};
	
	order.confirmOrder = function($e) { // 确认订单，填写地址、运送方式
		
	}
	order.submitAppOrder = function($e) { // 进行确认订单，填写地址、运送方式
		
		E.userInfo.checkIsLogin(function(){
			var orderInfos = [];
			var addressId = $('input[id=addressId]').val();
			$('.e_order').each(function(){
				$elem = $(this);
				var preOrderId = $elem.find('input[name=preOrderId]').val();
				var carryId = $elem.find('[name=carryId]').val();
				var remark = $elem.find('input[name=remark]').val();
				if (preOrderId) {
					orderInfos.push({
						preOrderId: preOrderId,
						addressId: addressId,
						carryId: carryId,
						remark: remark ? remark : ''
					});
				}
			})
			console.log(JSON.stringify( RJ.E.Cfg.contextPath ));
			$.ajax({
			    type: "post",
			    url: RJ.E.Cfg.contextPath+"/buyer/order/auction.json",
			    data:"data=" + encodeURIComponent(JSON.stringify( orderInfos )),
			    dataType: "json",   
			    success: function(res){
			    	
			    	var order=res.order;
			    	if(order){
			    		console.log(JSON.stringify(order.id));
			    		rj.openNativeWindow('_www/paytest.html?id='+order.id+'');
			    	}else{
			    		alert('地址信息未填写');
			    	}
			    	
			             },error :function(XMLHttpRequest, textStatus, errorThrow){
			            	 console.log(XMLHttpRequest.status);
			            	 console.log(XMLHttpRequest.readyState);
			            	 console.log(textStatus);
			             }
			});
		});
		
	}
	order.fastConfirmOrder = function($e) { // 进行确认订单，填写地址、运送方式
		
		var buyInfo = getBuyInfo();
		if (buyInfo) {
			E.userInfo.checkIsLogin(function(){
				var form = E.util.createForm('/buyer/order/fast-confirm.htm', { skuId: buyInfo.skuId, purchaseQuantity: buyInfo.num, goodsId: goodsId });
				form.submit();
			});
		}
		
	}
	
	order.submitOrder = function($e) { // 提交订单，进行下一步付款操作
//		var order = {
//			addressId: '00000001',
//			info: [{
//				storeId: '00000002',
//				goods: [goods],
//				memo: '' // 给卖家留言
//			}]
//		};
		
		var orderInfos = [];
		var addressId = $('input[id=addressId]').val();
		if (addressId) {
			$('.e_order').each(function(){
				$elem = $(this);
				var preOrderId = $elem.find('input[name=preOrderId]').val();
				var carryId = $elem.find('[name=carryId]').val();
				var remark = $elem.find('input[name=remark]').val();
				if (preOrderId) {
					orderInfos.push({
						preOrderId: preOrderId,
						addressId: addressId,
						carryId: carryId,
						remark: remark ? remark : ''
					});
				}
			})
			var form = E.util.createForm('/buyer/order/auction.htm', orderInfos, true);
			form.submit();
		} else {
			alert('地址信息未填写');
		}
		
	}
	
	order.pay = function($e) { // 付款操作，选择付款方式进行付款
		
	}
	
	order.confirmGoods = function($e) { // 确认收获，
		
	}
	
	order.comment = function($e) { // 进行评论，调用外部
		
	}
	
	$(function(){ // 绑定订单操作
		$('.E_FastBuyLink').click(function(e){ // 快速下单连接按钮
			
			
			
				
			
			order.fastConfirmOrder( $('.E_goodsInfo') );
			e.stopPropagation();
			
			return false;
		});
		$('.E_SubmitOrder').click(function(){ // 提交订单
			
			if((typeof(plus) != "undefined")){
				
				order.submitAppOrder( $('.E_buyInfo') );
			}else{
				order.submitOrder( $('.E_buyInfo') );
			}
			return false;
		});
	});
	
})(jQuery, RJ.E);