(function($, E){
	var pay = E.pay = {}; // 电商项目 支付模块 命名空间
	
	pay.doPay = function($form) { // 添加到购物车
		var orderId = $form.find('input[name=orderId]').val();
		var payCode = $form.find('input[name=payCode]:checked').val();
		
		if (!orderId) {
			alert('订单不存在');
			return;
		}
		
		if (! payCode) {
			alert('请先选择支付方式');
			return;
		}
		
		var form = E.util.createForm(payCode === 'weixinpay_micropay' ? '/pay/order_web_weixinpay_scan.htm' : '/pay/dopay.htm', {
			orderId: orderId,
			payCode: payCode
		});
		form.submit();
	};
	
	pay.getPayForm = function(pspc) {
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
	
	$(function(){ // 绑定支付相关操作
		
		// 添加倒计时
		var ct = $('#createTime');
		if (ct.size() > 0 && E.orderdetail) {
			E.orderdetail.countdown($('#createTime'), 3);
		}
		
		$('.E_dopay').click(function(){
			pay.doPay($('.main'));
		});
		
		var wxpayScan = $('#web_weixinpay_scan');
		var orderId = wxpayScan.attr('name');
		if ( wxpayScan.size() > 0 && orderId ) {
			setInterval(function(){
				$.get(E.Cfg.contextPath + "/pay/check_success.htm?orderId=" + orderId, function(res){
					if (res === 'true') {
						location.href = E.Cfg.contextPath + '/pay/info.htm?orderId=' + orderId;
					}
				});
			}, 3000);
		}
		
		var $userPayment = $('#used_payment');
		if ($userPayment.length > 0 && $userPayment.dataTablePage) {
			$userPayment.dataTablePage({
				url: E.Cfg.contextPath + "/pay/used",
				columns: [{
					data: "id",
					name: "支付方式",
					display: function(td, value, vo, rowIndex, colIndex) {
						$(td).html(vo.payServiceProvider.title);
					}
				},{
					data: "payServiceProvider.description",
					name: "说明",
				}],
				columnOpers: {
					name: '操作',
					display: function(td, value, vo, rowIndex, colIndex) {
						$(td).html('<a class="v_oper_edit">配置</a>');
					}
				},
				get$editForm: function(e) {
					var vo = this.getRowData(e.target);
					if (vo) {
						return pay.getPayForm(vo.payServiceProviderCode);
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
					
					var $eform = pay.getPayForm(vo.payServiceProviderCode);
					if ($eform) {
						this.def.editFormByVo($eform, data);
					}
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
					name: "支付方式",
//				orderable: true,
					display: function(td, value, vo, rowIndex, colIndex) {
						$(td).html(value);
					}
				},{
					data: "description",
					name: "说明",
				}],
				columnOpers: {
					name: '操作',
					display: function(td, value, vo, rowIndex, colIndex) {
						var self = this;
						var $td = $(td);
						if (vo.used) {
							$td.html('已开通');
						} else {
							$td.html('<a>开通</a>').click(function(){
								var dtp = $userPayment.getDtPage();
								var dtpo = dtp.options;
								
								var $form = pay.getPayForm(vo.id);
								if ($form) {
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
	});
	
})(jQuery, RJ.E);