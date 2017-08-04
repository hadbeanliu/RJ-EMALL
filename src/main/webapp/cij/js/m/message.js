(function($, E){
	var message = E.message = {}; // 电商项目 消息模块 命名空间
	
	$("#message_model").dataTablePage({
		url: "/emall/admin/message/model", // 请求地址
		columns: [{
			data: "receiverType",
			name: "用户类型",
			orderable: true,
			searchable: true,
			display: function(td, id, vo, rowIndex, colIndex) {
				if(vo.receiverType=="0"){
					$(td).html('所有用户');
				}else if(vo.receiverType=="1"){
					$(td).html('买家');
				}else{
					$(td).html('卖家');
				}
				
			}
		}, {
			data: "title",
			name: "标题",
		    orderable: true, 
			searchable: true
		}, {
			data: "content",
			name: "消息内容",
			orderable: false,
			searchable: true
		}],
		alertInto: "#alert_into" // 提醒实现位置
	});

	
	
})(jQuery, RJ.E);