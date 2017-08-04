(function($, E){
	var goodsDetail = E.goodsDetail = {}; // 电商项目 商品详情 命名空间
	
	goodsDetail.add = function($form) { // 添加到购物车
		alert("0")
		location.href ="/myCart.html";
	}
	
	$(function(){ // 绑定购物车操作
		$('.myCart').on('click',function(){
			goodsDetail.add($form);
		})
	});	
})(jQuery, RJ.E);