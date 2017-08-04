(function($, E){
	if ( ! E.buyer ) {
		E.buyer = {};
	}
	var addMyAttention = E.addMyAttention = {}; // 电商项目 我的浏览模块 命名空间	
	addMyAttention.addGoods = function(){		
		var data = {};
		var goodsId = "2015071016000004";//苹果手机
		var storeId = "";
		if(goodsId && goodsId != "") {
			data["goods.goodsId"] = goodsId;
			data['attentionType'] = '1';
		}
//		console.info(data);return;
		$.ajax({
            type: "post",
            dataType: 'json',
            data: data,
            url:  E.Cfg.contextPath+"/buyer/myattention/v_save.json",
            success: function(data) {  
            },
            error:function(error){            	
            }
        });	
	}
	
	addMyAttention.addStore = function(){		
		var data = {};
		var storeId = "0ic1ukkh306y222p";
		if(storeId && storeId != "") {
			data["store.storeId"] = storeId;
			data['attentionType'] = '2';
		}
//		console.info(data);return;
		$.ajax({
            type: "post",
            data: data,
            url:  E.Cfg.contextPath+"/buyer/myattention/v_save.json",
        });	
	}

	$(function(){
		//点击关注按钮添加关注(商品)
		$(this).find('.save_attention_goods').bind('click',function(){
			addMyAttention.addGoods();
		});
		
		//点击关注按钮添加关注(店铺)
		$(this).find('.save_attention_store').bind('click',function(){
			addMyAttention.addStore();
		});
	});

})(jQuery, RJ.E);