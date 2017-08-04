(function($, E){
	if ( ! E.seller ) {
		E.seller = {};
	}
	var order = E.seller.order = {}; // 电商项目 订单模块 命名空间
	
	order.deliver = function(order) {
		console.log(order);
	}
	
	
	$(function(){ // 绑定操作
		var hdkssj, hdjssj;
		$('#hdkssj').daterangepicker({
			format: 'YYYY-MM-DD',
			startDate: new Date(),
			endDate: new Date(),
			//minDate: '2015-01-01',
			//maxDate: '2100-01-10',
			showWeekNumbers: true,
			showDropdowns: true,
			timePicker12Hour: false,
			singleDatePicker: true,
			timePicker: false
		});
		$('#hdjssj').daterangepicker({
			format: 'YYYY-MM-DD',
			startDate: new Date(),
			endDate: new Date(),
			//minDate: '2015-01-01',
			//maxDate: '2100-01-10',
			showWeekNumbers: true,
			showDropdowns: true,
			timePicker12Hour: false,
			singleDatePicker: true,
			timePicker: false
		});
		$('#bmsj').daterangepicker({
			format: 'YYYY-MM-DD HH:MM:SS',
			startDate: new Date(),
			endDate: new Date(),
			//minDate: '2015-01-01',
			//maxDate: '2100-01-10',
			showWeekNumbers: true,
			showDropdowns: true,
			timePicker12Hour: false,
			singleDatePicker: true,
			timePicker: false
		});
		$('#overdue').daterangepicker({
			format: 'YYYY-MM-DD HH:MM:SS',
			startDate: new Date(),
			endDate: new Date(),
			//minDate: '2015-01-01',
			//maxDate: '2100-01-10',
			showWeekNumbers: true,
			showDropdowns: true,
			timePicker12Hour: false,
			singleDatePicker: true,
			timePicker: false
		});
		
		hdkssj = $('#hdkssj').data('daterangepicker');
		hdjssj = $('#hdjssj').data('daterangepicker');
		
		$('#hdkssj').on('apply.daterangepicker', function(ev, picker) {
			var startDate = picker.startDate.format('YYYY-MM-DD');
			var kssj = $('#hdkssj').val();
			var jssj = $('#hdjssj').val();
			var KSSJOBJ = {
					//maxDate: jssj,
					format: 'YYYY-MM-DD',
					startDate: startDate,
					//endDate: new Date(),
					showWeekNumbers: true,
					showDropdowns: true,
					timePicker12Hour: false,
					singleDatePicker: true,
					timePicker: false
			};
			var JSSJOBJ = {
					minDate: startDate,
					format: 'YYYY-MM-DD',
					startDate: new Date(),
					//endDate: new Date(),
					showWeekNumbers: true,
					showDropdowns: true,
					timePicker12Hour: false,
					singleDatePicker: true,
					timePicker: false
			}
			if (jssj != "" && jssj != null) {
				KSSJOBJ = {
						maxDate: jssj,
						format: 'YYYY-MM-DD',
						startDate: startDate,
						//endDate: new Date(),
						showWeekNumbers: true,
						showDropdowns: true,
						timePicker12Hour: false,
						singleDatePicker: true,
						timePicker: false
				};
				JSSJOBJ = {
						minDate: startDate,
						format: 'YYYY-MM-DD',
						startDate: jssj,
						//endDate: new Date(),
						showWeekNumbers: true,
						showDropdowns: true,
						timePicker12Hour: false,
						singleDatePicker: true,
						timePicker: false
				};
			}
			hdkssj.setOptions(KSSJOBJ);
			hdjssj.setOptions(JSSJOBJ);
		});
		$('#hdjssj').on('apply.daterangepicker', function(ev, picker) {
			var startDate = picker.startDate.format('YYYY-MM-DD');
			var kssj = $('#hdkssj').val();
			var jssj = $('#hdjssj').val();
			var KSSJOBJ = {
					maxDate: startDate,
					format: 'YYYY-MM-DD',
					startDate: new Date(),
					showWeekNumbers: true,
					showDropdowns: true,
					timePicker12Hour: false,
					singleDatePicker: true,
					timePicker: false
			};
			var JSSJOBJ = {
					format: 'YYYY-MM-DD',
					startDate: startDate,
					showWeekNumbers: true,
					showDropdowns: true,
					timePicker12Hour: false,
					singleDatePicker: true,
					timePicker: false
			}
			if (kssj != "" && kssj != null) {
				KSSJOBJ = {
						maxDate: startDate,
						format: 'YYYY-MM-DD',
						startDate: kssj,
						showWeekNumbers: true,
						showDropdowns: true,
						timePicker12Hour: false,
						singleDatePicker: true,
						timePicker: false
				};
				JSSJOBJ = {
						minDate: kssj,
						format: 'YYYY-MM-DD',
						startDate: startDate,
						showWeekNumbers: true,
						showDropdowns: true,
						timePicker12Hour: false,
						singleDatePicker: true,
						timePicker: false
				};
			}
			hdkssj.setOptions(KSSJOBJ);
			hdjssj.setOptions(JSSJOBJ);
		});
		
		var buyerOrder = $("#user_page").dataTablePage({
//			checkboxMode: false,
//			paging: false,
//			info: false,
//			order: [["aa", "desc"]],
			order: false,
			url: Cfg.contextPath + "/buyer/order", // 请求地址
			columns: [{
				data: "createTime",
				name: "<th class='hide'>成交时间</th>",
				orderable: true,
				searchable: false,
				display: function (td, value, vo, rowIndex, colIndex) {
					$(td).html('<div class="left">' + value + '</div>').addClass('dealtime small-pad10 hide-for-small');
				}
			}, {
				data: "id",
				name: "<th class='hide'>订单编号</th>",
				orderable: true,
				searchable: true,
				display: function (td, value, vo, rowIndex, colIndex) {
					$(td).html('<a class="p-color" title="订单详情">订单编号：' + vo.orderNo + '</a>').addClass('data_id small-pad10 hide-for-small');
				}
			}, {
				data: "id",
				name: "<th class='hide'>店铺</th>",
				orderable: true,
				searchable: true,
				display: function (td, value, vo, rowIndex, colIndex) {
					$(td).html('<a class="p-color" title="文玩天下">店铺：'+ vo.store.storeName +'</a>').addClass('store small-pad10');
				}
			}, {
				data: "id",
				name: "<th class='small-table-cell'>宝贝</th>",
				orderable: true,
				searchable: true,
				display: function (td, value, vo, rowIndex, colIndex) {
					var goods = vo.goods;
					var htm = [];
					for ( var i = 0; i < goods.length; i++) {
						var gs = goods[i];
						htm.push('<div class="'+ (i === 0 ? 'padb10 ' : '') +'inline-block max_h95">\
									<div class="img">\
									<a title="'+ gs.goodsTitle +'"><img src="'+ gs.goodsImage + '" /></a>\
								</div>\
								<div class="des">\
									<a class="margin-b p-color" title="'+ gs.goodsTitle +'">'+ gs.goodsTitle +'</a>\
									<span class="classify"><a>'+ gs.sku +'</a></span>\
								</div>\
							</div>');
					}
					$(td).html(htm.join('')).addClass('goods padt50 td_block');
				}
			}, {
				data: "id",
				name: "<th class='min_w90'>单价（元）</th>",
				searchable: true,
				display: function (td, value, vo, rowIndex, colIndex) {
					var goods = vo.goods;
					var htm = [];
					htm.push('<div class="show-for-small left">单价：</div>');
					for ( var i = 0; i < goods.length; i++) {
						var gs = goods[i];
						htm.push('<div class="'+ (i !== goods.length-1 ? 'min_h95 ' : '') +'">'+ (gs.purchasePrice / 100.0).toFixed(2) +'</div>');
					}
					$(td).html(htm.join('')).addClass('padt50 price hide-for-small');
				}
			}, {
				data: "id",
				name: "<th class='min_w60'>数量</th>",
				orderable: true,
				searchable: true,
				display: function (td, value, vo, rowIndex, colIndex) {
					var goods = vo.goods;
					var htm = [];
					for ( var i = 0; i < goods.length; i++) {
						var gs = goods[i];
						htm.push('<div class="'+ (i !== goods.length-1 ? 'min_h95 ' : '') +'">'+ gs.purchaseQuantity +'</div>');
					}
					$(td).html(htm.join('')).addClass('hide-for-small font-bold padt50 ');
				}
			}, {
				data: "id",
				name: "<th class='min_w80'>售后</th>",
				orderable: true,
				searchable: true,
				display: function (td, value, vo, rowIndex, colIndex) {
					var htm = [];
					if (vo.status === '2' || vo.status === '3' || vo.status === '4') {
						htm.push('<a class="button small-marl10" target="_blank" href="'+ E.Cfg.contextPath +'/buyer/refund/toRefund.htm?orderId='+ vo.id +'">申请退款</a>');
					}
					if (vo.refund) {
						htm.push('<a class="button small-marl10" target="_blank" href="'+ E.Cfg.contextPath +'/buyer/refund/details.htm?orderId='+ vo.id +'">查看退款</a>');
					}
					$(td).html(htm.join('')).addClass('hide-for-small padt50');
				}
			}, {
				data: "store",
				name: "<th class='min_w120'>卖家</th>",
				orderable: true,
				searchable: true,
				display: function (td, value, vo, rowIndex, colIndex) {
					var name = vo.store.storeName;
					var username = vo.store.user.nickName ? vo.store.user.nickName : vo.store.user.loginName;
					$(td).html('<div class="small-left">\
							<a class="p-color" title="卖家-'+ name +'">'+ name +'</a>\
							<a title="查询该买家订单"></a>\
						</div>\
						<div class="hide-for-small">------</div>\
						<a class="contactme small-left font-success" title="和'+ username +'联系"></a>').addClass('td_buyer padt50 small-marl65 small-marb5 clearfix');
				}
			}, {
				data: "id",
				name: "<th class='min_w80'>交易状态</th>",
				orderable: false,
				searchable: true,
				display: function (td, value, vo, rowIndex, colIndex, dtPage) {
					var htm = [];
					htm.push('<div class="show-for-small left">交易状态：</div>\
							<div class="margin-b small-marb5 small-left">'+ vo.statusDesc +'</div>\
							<div class="margin-b small-marb5 small-left"><a href="'+ E.Cfg.contextPath +'/buyer/order/detail.htm?orderId='+ vo.id +'">订单详情</a></div>');
					if (vo.status === '1') {
						htm.push('<a class="button small-marl10" target="_blank" href="'+ E.Cfg.contextPath +'/pay/order/'+ vo.id +'.htm">立即付款</a>');
					} else if (vo.status === '3') {
						htm.push('<a class="button small-marl10" target="_blank" href="'+ E.Cfg.contextPath +'/buyer/order/confirm-goods.htm?orderId='+ vo.id +'">确认收货</a>');
					}
					$(td).html(htm.join('')).addClass('td_tradingstatus padt50 small-marl65 small-marb5');
				}
			}, {
				data: "price",
				name: "<th class='min_w110'>实收款（元）</th>",
				orderable: false,
				searchable: true,
				display: function (td, value, vo, rowIndex, colIndex) {
					$(td).html('<div class="show-for-small left">实收款：</div><a class="font-bold cursor-auto small-left">' + ((vo.price + vo.logisticsFee) / 100.0).toFixed(2) + '</a>\
							<div class="font-gray">（含快递：'+ vo.logisticsFeeString +'）</div>')
					.addClass('td_realcollection padt50 small-marl65 small-marb5');
				}
			}, {
				data: "id",
				name: "<th class='min_w60'>评价</th>",
				orderable: false,
				searchable: true,
				display: function (td, value, vo, rowIndex, colIndex) {
					$(td).html(vo.status === '4' ? '<span class="label secondary round">双方已评</span>' : '').addClass('td_evaluate padt50 small-marl65 small-marb10');
				}
			}],
            /*columnOpers: {
                name: "<th class='min_w80'>交易操作</th>",
                display: function(td, id, vo, rowIndex, colIndex) {
                    // 多余2个，省略号显示
    				$(td).html('<div class="td_more"><span class="fa-stack v_oper_more"><i class="fa fa-circle"></i><i class="fa fa-ellipsis-h" title="更多"></i></span>'+
                               '<div class="more v_oper_more_panel hide"><span class="v_oper_delete"><i title="删除"></i>删除</span><span>|</span><span style="display:none;"><i title="评论"></i>评论</span><span style="display:none;">|</span><span class="reveal_share"><i title="分享"></i>分享</span><span>|</span><span><i title="收货"></i>收货</span></div></div>').addClass('td_more_wrap padt50');
                    $(td).find('.reveal_share').bind('click',function(){
                        $('.v_share').foundation("reveal", "open");
                    });
                    // 少于3个，按钮显示
                    // $(td).html('<div class="td_btn"><span class="button default"><i title="评价"></i>评价</span></div>').addClass('td_btn_wrap');
                }
            },*/
			alertInto: "#alert_into" // 提醒实现位置
		});
	});
	
})(jQuery, RJ.E);
