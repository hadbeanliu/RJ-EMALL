(function($, E){	
	$(function(){ // 绑定操作
		var hdkssj, hdjssj;
		var LogisticsData;
		if(LogisticsData==null){
			$.ajax({
				url:"/emall/cij/js/logistics-company.json",
				dataType:"JSON",
				type : "POST",
				success:function(data){
					LogisticsData=data;
				}
			});
		}
		$('#hdkssj').daterangepicker({
			format: 'YYYY-MM-DD HH:MM:SS',
			startDate: new Date(),
			endDate: new Date(),
			//minDate: '2015-01-01',
			//maxDate: '2100-01-10',
			showWeekNumbers: true,
			showDropdowns: true,
			timePicker12Hour: false,
			singleDatePicker: true,
			timePicker: true
		});
		$('#hdjssj').daterangepicker({
			format: 'YYYY-MM-DD HH:MM:SS',
			startDate: new Date(),
			endDate: new Date(),
			//minDate: '2015-01-01',
			//maxDate: '2100-01-10',
			showWeekNumbers: true,
			showDropdowns: true,
			timePicker12Hour: false,
			singleDatePicker: true,
			timePicker: true
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
			timePicker: true
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
			timePicker: true
		});
		
		hdkssj = $('#hdkssj').data('daterangepicker');
		hdjssj = $('#hdjssj').data('daterangepicker');
		
		$('#hdkssj').on('apply.daterangepicker', function(ev, picker) {
			var startDate = picker.startDate.format('YYYY-MM-DD HH:MM:SS');
			var kssj = $('#hdkssj').val();
			var jssj = $('#hdjssj').val();
			var KSSJOBJ = {
					//maxDate: jssj,
					format: 'YYYY-MM-DD HH:MM:SS',
					startDate: startDate,
					//endDate: new Date(),
					showWeekNumbers: true,
					showDropdowns: true,
					timePicker12Hour: false,
					singleDatePicker: true,
					timePicker: true
			};
			var JSSJOBJ = {
					minDate: startDate,
					format: 'YYYY-MM-DD HH:MM:SS',
					startDate: new Date(),
					//endDate: new Date(),
					showWeekNumbers: true,
					showDropdowns: true,
					timePicker12Hour: false,
					singleDatePicker: true,
					timePicker: true
			};
			if (jssj != "" && jssj != null) {
				KSSJOBJ = {
						maxDate: jssj,
						format: 'YYYY-MM-DD HH:MM:SS',
						startDate: startDate,
						//endDate: new Date(),
						showWeekNumbers: true,
						showDropdowns: true,
						timePicker12Hour: false,
						singleDatePicker: true,
						timePicker: true
				};
				JSSJOBJ = {
						minDate: startDate,
						format: 'YYYY-MM-DD HH:MM:SS',
						startDate: jssj,
						//endDate: new Date(),
						showWeekNumbers: true,
						showDropdowns: true,
						timePicker12Hour: false,
						singleDatePicker: true,
						timePicker: true
				};
			}
			hdkssj.setOptions(KSSJOBJ);
			hdjssj.setOptions(JSSJOBJ);
		});
		$('#hdjssj').on('apply.daterangepicker', function(ev, picker) {
			var startDate = picker.startDate.format('YYYY-MM-DD HH:MM:SS');
			var kssj = $('#hdkssj').val();
			var jssj = $('#hdjssj').val();
			var KSSJOBJ = {
					maxDate: startDate,
					format: 'YYYY-MM-DD HH:MM:SS',
					startDate: new Date(),
					showWeekNumbers: true,
					showDropdowns: true,
					timePicker12Hour: false,
					singleDatePicker: true,
					timePicker: true
			};
			var JSSJOBJ = {
					format: 'YYYY-MM-DD HH:MM:SS',
					startDate: startDate,
					showWeekNumbers: true,
					showDropdowns: true,
					timePicker12Hour: false,
					singleDatePicker: true,
					timePicker: true
			};
			if (kssj != "" && kssj != null) {
				KSSJOBJ = {
						maxDate: startDate,
						format: 'YYYY-MM-DD HH:MM:SS',
						startDate: kssj,
						showWeekNumbers: true,
						showDropdowns: true,
						timePicker12Hour: false,
						singleDatePicker: true,
						timePicker: true
				};
				JSSJOBJ = {
						minDate: kssj,
						format: 'YYYY-MM-DD HH:MM:SS',
						startDate: startDate,
						showWeekNumbers: true,
						showDropdowns: true,
						timePicker12Hour: false,
						singleDatePicker: true,
						timePicker: true
				};
			}
			hdkssj.setOptions(KSSJOBJ);
			hdjssj.setOptions(JSSJOBJ);
		});
		
		$('.v_oper_click_search').each(function(){
			var $o = $(this);
			$o.click(function(){
				var status = $o.attr('value');
				if(status === ""){
					$('title').text("全部订单");
					$('#statuschange').text("全部订单");
					$('.panel-title').text("全部订单");
				}else if(status === "2"){
					$('title').text("等待发货的订单");
					$('#statuschange').text("等待发货的订单");
					$('.panel-title').text("等待发货的订单");
				}else{
					$('title').text("已发货的订单");
					$('#statuschange').text("已发货的订单");
					$('.panel-title').text("已发货的订单");
				}
			});
		});
		
		var orderDelivery = $("#user_page").dataTablePage({
			//checkboxMode: false,
			url: Cfg.contextPath + "/seller/order/delivery", // 请求地址
			columns: [{
				data: "createTime",
				name: "<th class='hide'>成交时间</th>",
				orderable: true,
				searchable: false,
				display: function (td, value, vo, rowIndex, colIndex) {
					$(td).html('<div class="left">' + value + '</div>')
					.addClass('dealtime small-pad10 hide-for-small');
				}
			}, {
				data: "id",
				name: "<th class='hide'>订单编号</th>",
				orderable: true,
				searchable: true,
				display: function (td, value, vo, rowIndex, colIndex) {
					$(td).html('<a class="p-color" title="订单详情">订单编号：' + vo.orderNo + '</a>')
					.addClass('data_id small-pad10 hide-for-small');
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
						htm.push('<div class="'+ (i === 0 ? 'padb10 ' : '') +'inline-block">\
									<div class="img">\
									<a title="'+ gs.goodsTitle +'"><img src="'+ gs.goodsImage + '" /></a>\
								</div>\
								<div class="des">\
									<a class="v_oper_edit margin-b p-color" title="'+ gs.goodsTitle +'">'+ gs.goodsTitle +'</a>\
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
						htm.push('<div class="'+ (i !== goods.length-1 ? 'padb70 ' : '') +'">'+ (gs.purchasePrice / 100.0).toFixed(2) +'</div>');
					}
					$(td).html(htm.join('')).addClass('padt50 small-marl65 small-marb5 price hide-for-small');
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
						htm.push('<div class="'+ (i !== goods.length-1 ? 'padb70 ' : '') +'">'+ gs.purchaseQuantity +'</div>');
					}
					$(td).html(htm.join('')).addClass('hide-for-small font-bold padt50 ');
				}
			}, {
				data: "price",
				name: "<th class='min_w110'>实收款（元）</th>",
				orderable: false,
				searchable: true,
				display: function (td, value, vo, rowIndex, colIndex) {
					$(td).html('<div class="show-for-small left">实收款：</div><a class="font-bold cursor-auto small-left">' + ((vo.price + vo.logisticsFee)  / 100.0).toFixed(2) + '</a><div class="font-gray">（含快递：'+ vo.logisticsFee +'）</div>').addClass('td_realcollection padt50 small-marl65 small-marb5');
				}
			}, {
				data: "store",
				name: "<th class='min_w120'>买家</th>",
				orderable: true,
				searchable: true,
				display: function (td, value, vo, rowIndex, colIndex) {
					var lgn;
					if(vo.logistics!=null){
						for(var ld in LogisticsData){
							if(vo.logistics.logiscompanycode==LogisticsData[ld].simpleName){
								lgn=LogisticsData[ld].expName;
								break;
							}
						}
					}
					$(td).html('<div class="small-left" style="float:left;">收货信息：<a class="p-color" title="'+vo.address.areas+'">'+vo.address.areas+' '
							+(vo.address.address==null?"":(" "+vo.address.address))
							+(vo.address.postcode==null?"":(","+vo.address.postcode))
							+(vo.address.receiver==null?"":(","+vo.address.receiver))+'</a></div>\
							</div><br/>\
							<div class="small-left" style="float:left;">物流公司：'+(vo.logistics==null?"未选择物流公司":lgn)+'</div>')
							.addClass('td_buyer padt50 small-marl65 small-marb5 clearfix');
				}
			}, {
				data: "id",
				name: "<th class='min_w80'>交易状态</th>",
				orderable: false,
				searchable: true,
				display: function (td, value, vo, rowIndex, colIndex, dtPage) {
					var htm = [];
					htm.push('<div class="show-for-small left">交易状态：</div>\
							<div class="margin-b small-marb5 small-left">'+ vo.statusDesc +'</div>');
							if (vo.status === '2') {
								htm.push('<span class="button small-marl10 e_seller_deliver">发货</span>');
							}
							$(td).html(htm.join('')).addClass('td_tradingstatus padt50 small-marl65 small-marb5').find(".e_seller_deliver").click(function(e){
//							console.log(sellerOrder.getDtPage());
//							console.log(dtPage.options.getRowData(this));
//							order.deliver(vo);
							console.info(vo);
							$.ajax({
								type:"POST",
								url:Cfg.contextPath+"/seller/order/delivery/"+value+".json",
								success:function(data){
									location.href=Cfg.contextPath+"/seller/order/delivery/"+value+".htm";
								},
								error:function(e){
									alert("出错："+e);
								}
							});
							return false;
						});
				}
			}],
//        columnOpers: {
//            name: "评价",
//            display: function(td, id, vo, rowIndex, colIndex) {
//                /*多余2个，省略号显示*/
//				$(td).html('<div class="td_more"><span class="fa-stack v_oper_more"><i class="fa fa-circle"></i><i class="fa fa-ellipsis-h" title="更多"></i></span>'+
//                           '<div class="more v_oper_more_panel hide"><span class="v_oper_delete"><i title="删除"></i>删除</span><span>|</span><span style="display:none;"><i title="评论"></i>评论</span><span style="display:none;">|</span><span class="reveal_share"><i title="分享"></i>分享</span><span>|</span><span><i title="收货"></i>收货</span></div></div>').addClass('td_more_wrap');
//                $(td).find('.reveal_share').bind('click',function(){
//                    $('.v_share').foundation("reveal", "open");
//                });
			/*少于3个，按钮显示*/
			/*                $(td).html('<div class="td_btn"><span class="button default"><i title="评价"></i>评价</span></div>').addClass('td_btn_wrap');
			 */				
//            }
//        },
			alertInto: "#alert_into" // 提醒实现位置
		});
	});
	
})(jQuery, RJ.E);