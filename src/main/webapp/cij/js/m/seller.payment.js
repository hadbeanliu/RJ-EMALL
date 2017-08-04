(function($, E){
	if ( ! E.seller ) {
		E.seller = {};
	}
	var payment = E.seller.payment = {}; // 电商项目 卖家支付方式模块 命名空间
	
	payment.getPayForm = function(pspc) {
		if (pspc) {
			var $form = null;
			if (pspc.indexOf('alipay_') === 0) {
				$form = $('#alipay_edit_form');
			} else if (pspc.indexOf('weixinpay_') === 0){
				$form = $('#weixinpay_edit_form');
			}
			return $form;
		}
	};
	
	$(function(){ // 绑定操作
		
		var $userPayment = $('#used_payment');
		if ($userPayment.length > 0 && $userPayment.dataTablePage) {
			$userPayment.dataTablePage({
				url: E.Cfg.contextPath + "/seller/payment",
				columns: [{
					data: "id",
					name: '<th class="small-table-cell">支付方式</th>',
					display: function(td, value, vo, rowIndex, colIndex) {
						$(td).html(vo.payServiceProviderTitle);
					}
				},{
					data: "payServiceProviderDesc",
					name: "说明",
				}],
				columnOpers: {
					name: '<th class="min_w60">操作</th>',
					display: function(td, value, vo, rowIndex, colIndex) {
						$(td).html('<a class="v_oper_edit">配置</a>');
					}
				},
				get$editForm: function(e) {
					var vo = this.getRowData(e.target);
					if (vo) {
						return payment.getPayForm(vo.payServiceProviderCode);
					}
				},
				getVo: function($form) {
					var data = this.def.getVo($form); // 调用默认的获取方法，可进行校验
					if ( data ) {
						var ret = {};
						if (data.id) {
							ret.id = data.id;
							delete data.id;
						} else {
							ret.payServiceProviderCode = data.payServiceProviderCode;
							delete data.payServiceProviderCode;
						}
						ret.config = JSON.stringify(data);
						return ret;
					} else {
						return; // 校验不通过
					}
				},
				editFormByVo: function($form, vo) {
					
					
					var data = {};
					if (vo.config) {
						data = JSON.parse(vo.config);
					}
					data.id = vo.id;
					data.payServiceProviderCode = vo.payServiceProviderCode;
					
					var $eform = payment.getPayForm(vo.payServiceProviderCode);
					if ($eform) {
//						this.def.editFormByVo($eform, vo);
						this.def.editFormByVo($eform, data);
					}
				},
				save: function($form, call){
			    	var vo = this.getVo($form);
			    	if ( ! vo ) {
						return;
					}
			    	var voId = vo[this.getIdName()];
			    	$form = $form.find('form');
			    	$form.append('<input type="hidden" name="config" />').find('input[name=config]').val(vo.config);
			    	
			    	var url = voId ? '/seller/payment/update.htm' : '/seller/payment/create.htm';
			    	$form.attr('action', E.Cfg.contextPath + url).submit();
			    	
//			    	url = voId ? this.getUpdateVoUrl() : this.getSaveVoUrl();
//			    	var formData = new FormData($form[0]);
//			    	var self = this;
//			    	$.ajax({
//			    		type : "POST",
//			    		url : url,
//			    		data : vo,
//			    		dataType : "json",
//			    		processData: false,
//			    		cache: false,
//			            contentType: false,
//			            async: false,
//			    		success : function(res) {
//		    				if (res.errorId) {
///*			    					self.saveError(res); */
//		    					self.saveError("保存失败：" + res.errorMsg);
//		    				} else {
//		    					if (typeof call === 'function') {
//		    						call(res);
//		    					} else {
//		    						self.saveSuccess(res);
//		    						self.afterSave($form);
//		    					}
//		    				}
//			    		},
//			    		error : function(e) {
//			    			self.saveError(e);
//			    		}
//			    	});
			    },
				saveSuccess: function(vo, msg) {
					this.def.saveSuccess(vo, msg);
					$providers.getDtPage().datatable.draw(false);
					$('#used_payment_tab').click();
				}
			});
		}
		
		var $providers = $('#providers');
		if ($providers.length > 0 && $providers.dataTablePage) {
			$providers.dataTablePage({
				checkboxMode: false,
				info: false,
				paging: false,
//			order: [[ 1, "asc" ]], // 默认排序列和方式
				url: E.Cfg.contextPath + "/pay/providers",
				columns: [{
					data: "title",
					name: '<th class="small-table-cell">支付方式</th>',
//				orderable: true,
					display: function(td, value, vo, rowIndex, colIndex) {
						$(td).html(value).addClass('td_block');
					}
				},{
					data: "description",
					name: "说明",
				}],
				columnOpers: {
					name: '<th class="min_w60">操作</th>',
					display: function(td, value, vo, rowIndex, colIndex) {
						var self = this;
						var $td = $(td);
						if (vo.used) {
							$td.html('已开通');
						} else {
							$td.html('<a>开通</a>').click(function(){
								var dtp = $userPayment.getDtPage();
								var dtpo = dtp.options;
								
								var $form = payment.getPayForm(vo.id);
								if ($form) {
									E.util.clearForm($form);
									$form.find('input[name=payServiceProviderCode]').val(vo.id);
									dtpo.editFormByVo($form, {
										payServiceProviderCode: vo.id
									});
								}
								
								
//							$.get(E.Cfg.contextPath + "/pay/add_payment.json", "code=" + vo.code, function(vo){
//								var dtp = $userPayment.getDtPage();
//								var dtpo = dtp.options;
//								if ( ! vo.config ) {
//									dtpo.editFormByVo(dtpo.$editForm, vo);
//								}
//							});
							});
						}
					}
				}
			});
		}
		
		
		
//		$("#user_page").dataTablePage({
//			//checkboxMode: false,
//			url: Cfg.contextPath + "/order/00000001/goods", // 请求地址
//			columns: [{
//				data: "id",
//				name: "<th class='small-table-cell'>宝贝</th>",
//				orderable: true,
//				searchable: true,
//				display: function (td, value, vo, rowIndex, colIndex) {
//					$(td).html('<div><a class="v_oper_edit margin-b p-color" title="'+ vo.goodsTitle +'">'+ vo.goodsTitle +'</a></div>').addClass('goods small-marb5');
//				}
//			}, {
//				data: "goodsPrice",
//				name: "<th class='min_w90'>单价（元）</th>",
//				searchable: true,
//				display: function (td, value, vo, rowIndex, colIndex) {
//					$(td).html('<div class="show-for-small left">单价：</div><div>' + value + '</div>').addClass('small-marb5 price hide-for-small');
//				}
//			}, {
//				data: "purchaseQuantity",
//				name: "<th class='min_w60'>数量</th>",
//				orderable: true,
//				searchable: true,
//				display: function (td, value, vo, rowIndex, colIndex) {
//					$(td).html( '<div>' + value + '</div>').addClass('hide-for-small font-bold');
//				}
//			}, {
//				data: "purchasePrice",
//				name: "<th class='min_w60'>售后</th>",
//				orderable: true,
//				searchable: true,
//				display: function (td, value, vo, rowIndex, colIndex) {
//					$(td).html("").addClass('hide-for-small');
//				}
//			}, {
//				data: "purchasePrice",
//				name: "<th class='min_w120'>买家</th>",
//				orderable: true,
//				searchable: true,
//				display: function (td, value, vo, rowIndex, colIndex) {
//					$(td).html('<div class="small-left"><a class="p-color" title="买家tb66881213">tb66881213</a></div><div class="hide-for-small">------</div><a class="contactme small-left font-success" title="和我联系">和我联系</a>').addClass('td_buyer small-marb5 clearfix');
//				}
//			}, {
//				data: "purchasePrice",
//				name: "<th class='min_w80'>交易状态</th>",
//				orderable: false,
//				searchable: true,
//				display: function (td, value, vo, rowIndex, colIndex) {
//					$(td).html('<div class="show-for-small left">交易状态：</div><div class="margin-b small-marb5 small-left">买家已付款</div><span class="button small-marl10">发货</span>').addClass('td_tradingstatus small-marb5');
//				}
//			}, {
//				data: "purchasePrice",
//				name: "<th class='min_w110'>实收款（元）</th>",
//				orderable: false,
//				searchable: true,
//				display: function (td, value, vo, rowIndex, colIndex) {
//					$(td).html('<div class="show-for-small left">实收款：</div><a class="font-bold cursor-auto small-left">' + value + '</a><div class="font-gray">（含快递：10）</div>').addClass('td_realcollection small-marb10');
//				}
//			}],
//			columnOpers: {
//				name: "<th class='min_w80'>交易操作</th>",
//				display: function(td, id, vo, rowIndex, colIndex) {
//					/*多余2个，省略号显示*/
//					$(td).html('<div class="td_more"><span class="fa-stack v_oper_more"><i class="fa fa-circle"></i><i class="fa fa-ellipsis-h" title="更多"></i></span>'+
//					'<div class="more v_oper_more_panel hide"><span class="v_oper_delete"><i title="删除"></i>删除</span><span>|</span><span style="display:none;"><i title="评论"></i>评论</span><span style="display:none;">|</span><span class="reveal_share"><i title="分享"></i>分享</span><span>|</span><span><i title="收货"></i>收货</span></div></div>').addClass('td_more_wrap');
//					$(td).find('.reveal_share').bind('click',function(){
//						$('.v_share').foundation("reveal", "open");
//					});
//					/*少于3个，按钮显示*/
//					/*                $(td).html('<div class="td_btn"><span class="button default"><i title="评价"></i>评价</span></div>').addClass('td_btn_wrap');
//					 */				
//				}
//			},
//			alertInto: "#alert_into" // 提醒实现位置
//		});
	});
	
})(jQuery, RJ.E);