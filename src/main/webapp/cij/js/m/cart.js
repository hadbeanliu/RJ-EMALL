(function($, E){
	var cart = E.cart = {}; // 电商项目 购物车模块 命名空间
	
	cart.add = function($form) { // 添加到购物车
		
	}
	
	$(function(){ // 绑定购物车操作
		$('.E_AddCartLink').click(function(){ // 加入购物车连接按钮
			cart.add($('.E_goodsInfo'));
		});
	});
	
})(jQuery, RJ.E);