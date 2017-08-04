(function($, E){
	var myattention = E.myattention = {}; // 电商项目 购物车模块 命名空间	
	
	var url = E.Cfg.contextPath;
	
	var dataUrl = url + '/buyer/myattention';
	var dataIndexUrl = dataUrl + '/v_page.json';
	var dataStoreUrl = url + '/buyer/myattention/storeattention';
	var dataActivityUrl = url + '/buyer/myattention/activityattention';
	var delUrls = url + '/buyer/myattention/v_dels.json';//批量删除

	myattention.initData = function(){//初始化页面，商品关注数据
		var $myattentionGoods = $("#data_wdgz_con_1");
		if ($myattentionGoods.length > 0 && $myattentionGoods.dataTablePage) {
				$myattentionGoods.dataTablePage({
				url: dataUrl, // 请求地址
				length: 8,
				processDone: function() {
					$myattentionGoods.find('.v_datatables_list > ul').addClass('small-block-grid-2 medium-block-grid-4 large-block-grid-4');
					$myattentionGoods.find('.dataTables_empty').parent('li').addClass('empty-li');
					
					$page = $(document);
					_self = this;
					$page.find('.v_oper_delete').click(function(){
						
						_self.del($(this).parents('.datatable_row'));
					});
				},
				del: function($trs){
	            	//$trs 需要改
	            	if($trs.length == 0){
	            		
	            		$trs = $(this).find('.datatable_row');
	            	}
					if ( ! this.checkSelected($trs, "请选择需要删除的记录！") ) {
			    		return;
			    	}
			    	
					var self = this;
			    	this.get$delWarning().find('.v_oper_do_delete').off('click').click(function(){
			    		self.doDel( $trs && $trs.size() > 0 ? $trs : dtPageUtil.getSelectedVos(self.get$table()) );
			    	});
			    	this.get$delWarning().foundation("reveal", "open");
			    	$myattentionGoods.find('input[type=checkbox]').prop("checked",false);
				},
				row: {
					ulLiMode: true,
	            	display: function( elem, data, dataIndex ) {
	            		//console.info(elem);//img src = Cfg.tempPath +
	            		var htm = '<div class="img-container">\
			            	        <div class="border">\
			            	            <div class="block-img">\
	            							<input type="hidden" name="goods.goodsId" value="'+ data.goods.goodsId +'" />\
	            							<input type="hidden" name="attentionId" value="'+ data.id +'" />\
			            	                <a title="'+ data.goods.goodsTitle +'"><img title="'+ data.goods.goodsTitle +'" alt="'+ data.goods.goodsTitle +'" src="'+  data.goods.goodsFaceImage+'"></a>\
			            	                <a class="like">找相似</a>\
			            	                <a class="trash v_oper_delete" title="取消关注"><i class="fa fa-heart font-alert"><span></span><span></span></i></a>\
			            	            </div>\
			            	            <div class="des goods pad5">\
			            	                <span class="font-tiny block tit"><a title="'+ data.goods.goodsTitle +'">'+ data.goods.goodsTitle +'</a></span>\
			            	                <span class="block font-alert font-large">\
			            	                	￥'+ data.goods.goodsPrice +'元\
			            	                    <span class="label">满200减</span>\
			            	                </span>\
			            	                <span class="font-tiny block">\
			            	                    <span class="font-gray">销量</span><span class="p-color margin-r">264笔</span><span class="font-gray">评价</span><span class="p-\color">41</span>\
			            	                </span>\
			            	                <span class="font-tiny block clearfix">\
			            	                    <a class="left font-tiny font-gray" title="柔木旗舰店">柔木旗舰店</a>\
			            	                    <a class="right contact" title="联系店铺"></a>\
			            	                </span>\
			            	            </div>\
			            	        </div>\
			            	        <div class="edit-pop-bg"><a class="bg"></a><a class="chose"></a></div>\
			            	    </div>';
	
	            		$(elem).append(htm);
	            	}
	            }
			});
		}

	}
	
	myattention.addIndexHtml = function(data){//将数据放入到我是买家主页
		var headHtml = '<ul class="small-block-grid-3 medium-block-grid-5 large-block-grid-5 ">';
		var middleHtml = '';
		var data = data.data;
		for ( var i = 0; i < data.length; i++) {
			if( i > 4 ) break;//限制能显示的最大数据为5条
			middleHtml += '<li>\
							<div class="block-img">\
								<a title="来自：伙拼">\
									<img data-th-src="|${tempPath}'+data[i].goods.goodsFaceImage+'" title="来自：伙拼" alt=" 来自：伙拼" src="'+data[i].goods.goodsFaceImage+'" />\
								</a>\
							</div>\
							<div class="des">\
								<span class="font-alert font-tiny block">￥'+data[i].goods.goodsPrice+'</span>\
								<span class="font-tiny left">来自：</span>\
								<a class="store1"></a>\
							</div>\
						</li>';
		}
		var tailHtml = '</ul>';
		if(data.length == 0 || middleHtml == ''){
			tailHtml += '<p class="empty-h170 text-center p-color font-large"><i class="fa fa-info-circle"></i>您还没有关注任何商品哦！</p>';
		}
		$('.show_attention_index').html(headHtml + middleHtml + tailHtml);
		
		//猜我喜欢
		$guessLike = $('.guessLike');
		if($guessLike && $guessLike.length > 0){
			
			myattention.guessLike();//我是买家主页的猜我喜欢
		}
	}
	
	myattention.initStoreData = function(){//加载店铺数据
		var $myattentionStore = $("#data_wdgz_con_2");
		if ($myattentionStore.length > 0 && $myattentionStore.dataTablePage) {
			$myattentionStore.dataTablePage({
				url: dataStoreUrl, // 请求地址
				length: 8,
				processDone: function() {
					$myattentionStore.find('.v_datatables_list > ul').addClass('small-block-grid-2 medium-block-grid-4 large-block-grid-4');
					$myattentionStore.find('.dataTables_empty').parent('li').addClass('empty-li');
					$page = $(document);
					_self = this;
					$myattentionStore.find('.v_oper_delete').click(function(){
						
						_self.del($(this).parents('.datatable_row'));
					});
				},
				row: {
					ulLiMode: true,
	            	display: function( elem, data, dataIndex ) {
	            		//console.info(elem);//img src = Cfg.tempPath +
	            		if(data.store.user.nickName == null || data.store.user.nickName.length == 0){//处理如果昵称为null
	            			data.store.user.nickName = data.store.user.loginName;
	            		};
	            		var htm = '<li>\
	            					<div class="img-container">\
			            				<div class="border">\
			            			  		<div class="block-img small">\
	            					   			<input type="hidden" name="store.storeId" value="'+ data.store.storeId +'" />\
	            					   			<input type="hidden" name="attentionId" value="'+ data.id +'" />\
	            					   			<a title="'+ data.store.storeName +'"><img title="'+ data.store.storeName +'" alt="'+ data.store.storeName +'" src="'+ data.store.logo +'" /></a>\
	            					   			<a class="like">找相似</a><a class="trash v_oper_delete" title="取消关注"><i class="fa fa-heart font-alert"><span></span><span></span></i></a>\
	            					   		</div>\
	            					   		<div class="des pad5">\
	            					   			<span class="font-normal block store-name">\
	            					   			<a title="'+ data.store.storeName +'"><span class="label primary margin-r left">店铺</span>'+data.store.storeName+'</a>\
	            					   			</span>\
	            					   			<span>\
	            					   			<a class="font-small store-name">'+data.store.user.nickName+'</a><a class="contact"></a>\
	            					   			</span>\
	            					   		</div>\
	            					   	</div>\
	            					   	<div class="edit-pop-bg"><a class="bg"></a><a class="chose"></a>\
	            					   	</div>\
	            					 </div>\
	            					</li>';
	            		$(elem).append(htm);
	            	}
	            }
			});
		}
	}
	
	myattention.initActivityData = function(){//加载活动数据
		var $myattentionActivity = $("#data_wdgz_con_3");
		if ($myattentionActivity.length > 0 && $myattentionActivity.dataTablePage) {
			$myattentionActivity.dataTablePage({
				url: dataActivityUrl, // 请求地址
				length: 8,
				processDone: function() {
					$myattentionActivity.find('.v_datatables_list > ul').addClass('small-block-grid-2 medium-block-grid-4 large-block-grid-4');
					$myattentionActivity.find('.dataTables_empty').parent('li').addClass('empty-li');
				},
				row: {
					ulLiMode: true,
					display: function( elem, data, dataIndex ) {//class="block-img" -> class="block-img medium"
						//console.info(elem);//img src = Cfg.tempPath +
						var htm = '<li><div class="img-container"><div class="border"><div class="block-img">'
							+ '<a title="'+ data.goods.goodsTitle +'"><img title="'+ data.goods.goodsTitle +'" alt="'+ data.goods.goodsTitle +'" src="'+ data.goods.goodsFaceImage +'" /></a>'
							+ '<a class="like">找相似</a><a class="trash v_oper_delete" title="取消关注"><i class="fa fa-heart font-alert"><span></span><span></span></i></a><input type="hidden" name="id" value="'+ data.id +'"/></div><div class="des goods pad5"><span class="font-tiny block tit">'
							+ '<a title="'+ data.goods.goodsTitle +'">'+ data.goods.goodsTitle +'</a></span><span class="block font-alert font-large">'
							+ '￥'+ data.goods.goodsPrice +'元 <span class="label">满200减</span></span><span class="font-tiny block">'
							+ '<span class="font-gray">销量</span><span class="p-color margin-r">264笔</span><span class="font-gray">评价</span><span class="p-color">41</span></span><span class="font-tiny block clearfix"><a class="left font-tiny font-gray" title="柔木旗舰店">柔木旗舰店</a><a class="right contact" title="联系店铺"></a>'
							+ '</span></div></div><div class="edit-pop-bg"><a class="bg"></a><a class="chose"></a></div></div></li>';
						$(elem).append(htm);
					},
					alertInto: "#alert_into" // 提醒实现位置
				}
			});
		}
	}
	
	var getLikeDataUrl = url + '/buyer/myattention/get_like.json';
	
	myattention.guessLike = function(){
		$this = $('.guessLike');
		$this.html('<p class="empty-h170 text-center p-color font-large"><i class="fa fa-info-circle"></i>您还没有关注过任何商品~</p>');
		return;
		var goodsIds = "";
		//获取我的关注数据
		$.get(dataIndexUrl,function(data){
			var data = data.data;
			for ( var i = 0; i < data.length; i++) {
				
				if(i == data.length - 1) {
					goodsIds += data[i].goods.goodsId;
					break;
				}
				goodsIds += data[i].goods.goodsId + ",";
			}
			
			$.ajax({
				url : getLikeDataUrl,
				data : {"goodsIds":goodsIds},
				type : "POST",
	    		dataType : "json",
				success : function(res){
					
				}
			});
		});
	};
	
	$(function(){
		/**
		 * 初始化
		 */
		myattention.initData();//我关注的商品
		myattention.initStoreData();//我关注的店铺
		myattention.initActivityData();//我关注的活动
		
		
		if($('ul').hasClass('show_attention_index')){
			$.get(dataIndexUrl,function(data){
				myattention.addIndexHtml(data);
			},'json');
		}
	});
})(jQuery, RJ.E);