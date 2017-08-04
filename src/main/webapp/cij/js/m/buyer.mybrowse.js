(function($, E){
	var mybrowse = E.mybrowse = {}; // 电商项目 购物车模块 命名空间	
	
	var url = E.Cfg.contextPath;
	
	var browseDataUrl = url + '/buyer/mybrowse';
	var browseDataIndexUrl = url + '/buyer/mybrowse/v_page.json';
	
	$page = $(this);
	
	mybrowse.getBrowseData = function(){//获取数据
		$.get(browseDataIndexUrl,function(data){
			mybrowse.initBrowse(data);
		},'json');
	}
	
	mybrowse.initBrowse = function(data){//将数据显示到html中
		var headHtml = '<ul class="small-block-grid-2 medium-block-grid-1 large-block-grid-1">';
		var middleHtml = '';
		var data = data.data;
		var num = 0;
		for ( var i = 0; i < data.length; i++) {
			for(var j = 0; j < data[i].goodsList.length; j++){
				if(data[i].goodsList[j].goods == null) continue;
				if( num > 3 ) break;//限制能显示的最大数据为4条
				num ++;
				//alt="精品越南老挝大红酸枝手串红木佛珠念珠车挂件摆件酸枝原木手串链"，此处省略
				middleHtml += '<li><div class="img left margin-r"><a title="'+data[i].goodsList[j].goods.goodsTitle+'"><img data-th-src="'+data[i].goodsList[j].goods.goodsTitle+'" title="'+data[i].goodsList[j].goods.goodsTitle+'" alt="'+data[i].goodsList[j].goods.goodsTitle+'" src="'+data[i].goodsList[j].goods.goodsFaceImage+'" /></a></div>'
				+ '<div class="des auto_list"><span class="font-tiny title"><a title="'+data[i].goodsList[j].goods.goodsTitle+'">'+data[i].goodsList[j].goods.goodsTitle+'</a></span><span class="block">'
				+ '<a class="font-alert font-tiny cursor-auto">'+data[i].goodsList[j].goods.goodsPrice+'</a><a class="font-tiny line-through cursor-auto">￥469</a></span><a class="font-tiny pad1 p-border">找相似</a></div></li>';
			}
		}
		var tailHtml = '</ul>';
		if(data.length == 0 || middleHtml == ""){
			tailHtml += '<p class="empty-h170 text-center p-color font-large"><i class="fa fa-info-circle"></i>暂无浏览记录！</p>';
		}else if(num < 4){
			for ( var i = 0; i < 4 - num; i++) {
				middleHtml += '<li><div class="img left margin-r"></div></li>';
			}
		}
		$('.show_mybrowse_index').html(headHtml + middleHtml + tailHtml);
	}
	
	mybrowse.mybrowseAllData = function(){//我的浏览全部数据
		$mybrowseAll = $('#mybrowse');
		if($mybrowseAll.length > 0 && $mybrowseAll.dataTablePage){
			
			$mybrowseAll.dataTablePage({
				url: browseDataUrl, // 请求地址-时间表
				length: 3,
				processDone: function() {
//					$mybrowseAll.find('.v_datatables_list > ul').addClass('small-block-grid-2 medium-block-grid-4 large-block-grid-4');
					$mybrowseAll.find('.dataTables_empty').parent('li').addClass('empty-li');
					$page = $(document);
					$('.perList').click(function(){//点击关注图标触发事件
						if ($(this).parents('.batch-edit-status').length == 0) {
							return; // 当且仅当存在批量操作且开启时，可以进行选择操作
						}

						$(this).toggleClass('selected');
						if($mybrowseAll.find('.perList.selected').length == $mybrowseAll.find('.perList').length){
							$mybrowseAll.find('input[type=checkbox]').prop("checked",true);
						}else{
							if($mybrowseAll.find('input[type=checkbox]').prop("checked")){//如果本来是选中的
								$mybrowseAll.find('input[type=checkbox]').prop("checked",false);
							}
						}
						$mybrowseAll.find('.datatable_row').removeClass('selected');
					});
					$page.find('.select_all').click(function(){//全选
						if($mybrowseAll.find('input[type=checkbox]').prop('checked')){//如果有选中-全选了
							$mybrowseAll.find('.perList').addClass('selected');
						}else{
								$mybrowseAll.find('.perList').removeClass('selected');
						}
						$mybrowseAll.find('.datatable_row').removeClass('selected');
					});
					_self = this;
					$page.find('.v_oper_delete').click(function(){//删除
						
						_self.del($(this).parents('.perList'));
					});
				},
				row: {
					ulLiMode: true,
	            	display: function( elem, data, dataIndex ) {
	            		//console.info(elem);//img src = Cfg.tempPath +
	            		//var date = data.createTime.substring(0,10);
	            		var headhtm = '<ul>\
	            	        			<li class="time-line">\
	            							<div class="data-tit">\
	                        					<a class="font-large font-gray">'+ data.createTime.substring(0,10) +'</a><a class="font-gray">共'+ data.perNum +'件宝贝</a>\
	            							</div>\
	            							<div class="clearfix">\
	                        					<ul class="small-block-grid-2 medium-block-grid-4 large-block-grid-4">';
            			if("1" == data.currLast){
            				headhtm = '<ul>\
	            	        			<li class="time-line no-border">\
		        							<div class="data-tit">\
		                    					<a class="font-large font-gray">'+ data.createTime.substring(0,10) +'</a><a class="font-gray">共'+ data.perNum +'件宝贝</a>\
		        							</div>\
		                    				<div class="clearfix">\
		                    					<ul class="small-block-grid-2 medium-block-grid-4 large-block-grid-4">';
            			}
	            		var middelhtm = '';
	            		//console.info(data.goodsList);
	            		for ( var i = 0; i < data.goodsList.length; i++) {
	            			if(data.goodsList[i].goods == null) continue;
	            			middelhtm += '<li class="perList" id="'+ data.goodsList[i].goods.goodsId +'">\
	            							<div class="img-container">\
	            								<div class="border">\
					                                <div class="block-img">\
	            										<input type="hidden" name="goods.goodsId" value="'+ data.goodsList[i].goods.goodsId +'" />\
					                                    <a title="'+ data.goodsList[i].goods.goodsTitle +'"><img title="'+ data.goodsList[i].goods.goodsTitle +' '+ data.goodsList[i].browseDate +'" alt="'+ data.goodsList[i].goods.goodsTitle +'" src="'+ data.goodsList[i].goods.goodsFaceImage +'" /></a>\
					                                    <a class="like">找相似</a>\
					                                    <a class="del v_oper_delete" title="删除"><i class="fa fa-trash"></i></a>\
					                                </div>\
					                                <div class="des goods pad5">\
					                                    <span class="font-tiny block tit"><a title="'+ data.goodsList[i].goods.goodsTitle +'">'+ data.goodsList[i].goods.goodsTitle +'</a></span>\
					                                    <span class="block font-alert font-large">\
					                                        ￥'+ data.goodsList[i].goods.goodsPrice +'元\
					                                        <span class="label">满200减</span>\
					                                    </span>\
					                                </div>\
					                            </div>\
					                            <div class="edit-pop-bg"><a class="bg"></a><a class="chose"></a></div>\
					                        </div>\
					                    </li>';
						}
				        var tailhtm = 			'</ul>\
				                        	</div>\
				            			</li>\
				            		</ul>';
	            		$(elem).append(headhtm + middelhtm + tailhtm);
	            		
	            	},
	            },
	            del: function($trs){
	            	//$trs 需要改
	            	if($trs.length == 0){
	            		
	            		$trs = $mybrowseAll.find('.perList.selected');
	            	}
					if ( ! this.checkSelected($trs, "请选择需要删除的记录！") ) {
			    		return;
			    	}
			    	
					var self = this;
			    	this.get$delWarning().find('.v_oper_do_delete').off('click').click(function(){
			    		self.doDel( $trs && $trs.size() > 0 ? $trs : dtPageUtil.getSelectedVos(self.get$table()) );
			    	});
			    	this.get$delWarning().foundation("reveal", "open");
			    	$mybrowseAll.find('input[type=checkbox]').prop("checked",false);
				},
				select: function($tr){ /* 选择行 */
					var selected = [];
					$tr = $(this).find('.perList.selected');
					
					if ($mybrowseAll.find('.v_oper_enable_batch_edit').size() > 0 && ! $mybrowseAll.hasClass('batch-edit-status')) {
						return; // 当且仅当存在批量操作且开启时，可以进行选择操作
					}
					
					var check = $tr.find('input[name=v_select_id]');
					var id = $tr.attr('id');
					
//					if ( ! this.beforeSelected(id, $tr) || 'row' !== $tr.attr('role') ) {
//						return;
//					}
			        var index = $.inArray(id, selected);
			 
			        if ( index === -1 ) {
			            selected.push( id );
			            check.prop("checked", true);
			            
			            this.afterSelected( [ id ] );
			        } else {
			            selected.splice( index, 1 );
			            check.prop("checked", false);
			            
		            	this.afterDeselected( [ id ] );
			        }
			        $tr.toggleClass('selected');
			        
			        this.applySelectedAllCheckbox();
				},
			});
			
		}
	};
	
	$(function(){
		
		/**
		 * 初始化
		 */
		if($('div').hasClass('show_mybrowse_index')){//遍历我的浏览数据
			mybrowse.getBrowseData();
		}
		mybrowse.mybrowseAllData();
		
	});
})(jQuery, RJ.E);