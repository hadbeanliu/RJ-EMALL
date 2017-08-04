/**
 * 实现点击关注图标时，关注和取消关注功能
 */
(function($, E){
	var clickEvent = E.clickEvent = {}; // 电商项目 购物车模块 命名空间	
	
	var url = E.Cfg.contextPath;
	var $clickHeart = $('.trash');

	var delUrl = url + '/buyer/myattention/del_attention.json';//自定义删除
	var saveUrl = url + '/buyer/myattention/v_save.json';

	clickEvent.saveAttention = function(self){//保存关注
		_self = $(self);
		var data = {};
		var tag1 = _self.parents('li').find('[name]')[0];
		if(tag1 && tag1.name == "goods.goodsId") {
			data[_self.parents('li').find('[name]')[0].name] = tag1.value;
			data['attentionType'] = '1';
		}
		if(tag1 && tag1.name == "store.storeId") {
			data[_self.parents('li').find('[name]')[0].name] = tag1.value;
			data['attentionType'] = '2';
		}
//		console.info(data);return;
		$.ajax({
    		type : "POST",
    		url : saveUrl,
    		data : data,//vo
    		dataType : "json",
    		success : function(res) {
    			//保存成功后,加入<input>表单
    			var vo = res;
    			_self.parents('li').find('input[name=attentionId]').val(vo.id);
//    			$(_self).parents('.img-container').find('.edit-pop-bg a:nth-child(2)').addClass('chose');
    			$(_self).parents('li').addClass('datatable_row');
    			$(_self).parents('li').find('.edit-pop-bg').removeClass('hide');
    		},
    		error : function(e) {
    			console.info("error 保存数据时出错！");
    		}
    	});
	}
	
	clickEvent.delAttention = function(_target){//单个删除关注
		/**
		 * 1.在商品信息页面的关注-》直接取编号
		 * 2.在关注界面-》初始化时追加一个隐藏类型的表单，存id值
		 */
		var data = {};//关注编号
		data['id'] = $(_target).parents('li').find('input[name=attentionId]').val();
//		$(_target).parents('li').removeClass('datatable_row');
//		$(_target).parents('li').find('.edit-pop-bg').addClass('hide');
//		console.info($(_target).parents('li').find('input[name=attentionId]').val());return;
		$.ajax({
    		type : "POST",
    		url : delUrl,
    		data : data,//vo
    		dataType : "json",
    		success : function(res) {
    		},
    		error : function(e) {
    			console.info("error 删除数据时出错！");
    		}
    	});
	}
	
	$(function(){
	});
})(jQuery, RJ.E);