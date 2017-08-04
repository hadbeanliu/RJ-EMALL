(function($, E){
	if ( ! E.buyer ) {
		E.buyer = {};
	}
	var records = E.buyer.records = {}; // 电商项目 消费记录模块 命名空间
	
	
	$(function(){ // 绑定操作
		
		var buyerRecords = $("#buyer_consumerecords").dataTablePage({
			checkboxMode: false,
//			paging: false,
//			info: false,
//			order: [["aa", "desc"]],
			order: false,
			url: Cfg.contextPath + "/user/consumerecords", // 请求地址
			columns: [{
				data: "doneTime",
				name: "时间",
				orderable: true,
				searchable: false
				
			}, {
				data: "orderNo",
				name: "涉及订单编号",
				orderable: true,
				searchable: true
				
			}, {
				data: "price",
				name: "消费金额",
				orderable: true,
				searchable: true,
				display: function (td, value, vo, rowIndex, colIndex) {
					$(td).html('￥'+ vo.price);
				}
			}, {
				data: "remark",
				name: "备注",
				orderable: false,
				searchable: false,
				display: function (td, value, vo, rowIndex, colIndex) {
					if(vo.remark==null||vo.remark==""){
						$(td).html('订单'+vo.id+'完成增加消费额');
					}
					
				}
			}],

			alertInto: "#alert_into" // 提醒实现位置
		});
	});
	
})(jQuery, RJ.E);
