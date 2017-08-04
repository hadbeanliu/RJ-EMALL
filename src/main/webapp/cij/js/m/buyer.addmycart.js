(function($, E){
	if ( ! E.buyer ) {
		E.buyer = {};
	}
	var addmycart = E.addmycart = {}; // 电商项目 购物车模块 命名空间	

	addmycart.addCart=function(){
		var buyInfo = getBuyInfo();
		if (buyInfo) {
			E.userInfo.checkIsLogin(function(){
					var form = E.util.createForm('/buyer/mycart/save.htm', { skuId: buyInfo.skuId, num: buyInfo.num, goodsId: goodsId });
					form.submit();
			});
		}
	}

	$(function(){ // 绑定订单操作
     $('.cart').click(function(e){ // 快速下单连接按钮
			addmycart.addCart();
			return false;
		});
	});

})(jQuery, RJ.E);