(function($, E){
	if ( ! E.seller ) {
		E.seller = {};
	}
	var order = E.seller.order = {}; // 电商项目 订单模块 命名空间
	
	

	userId= $("#userId").val();
	 /* 	if($("#userId").val()){
			urltemp="/emall/refund/user/";
		}else if($("#storeId").val()){
			urltemp="/emall/refund/sell/"+ $("#storeId").val();
		}else{
			urltemp="/emall/refund";
		}  */
		$("#user_page").dataTablePage({
			url: "/emall/buyer/refund", // 请求地址
			
			checkboxMode : false, // 是否出现多选框
			columns: [
			{
				
				data: "order",
				name: "订单编号",
				orderable: true,
				searchable: true,
				display: function (td, value, vo, rowIndex, colIndex) {
					$(td).html('<div class="inline-block"><div class="des"><span class="">'+ vo.order.orderNo +'</span></div></div>').addClass('goods');				}
			
		},
		
		{
			
			data: "order",
			name: "商品名称",
			orderable: true,
			searchable: true,
			display: function (td, value, vo, rowIndex, colIndex) {
		
				$(td).html('<div class="inline-block"><a href="/emall/buyer/refund/details.htm?orderId='+vo.order.id+'"><div class="des"><span class="">'+ vo.order.orderTitle +'</span></a></div></div>').addClass('goods');				}
			
		
	},
	{
		
		data: "order",
		name: "店铺",
		orderable: true,
		searchable: true,
		display: function (td, value, vo, rowIndex, colIndex) {
			$(td).html('<div class="inline-block"><div class="des"><span class="">'+ vo.order.store.storeName +'</span></div></div>').addClass('goods');				}

	},
		{
			
			data: "order",
			name: "退款金额",
			orderable: true,
			searchable: true,
			display: function (td, value, vo, rowIndex, colIndex) {
				$(td).html('<div class="inline-block"><div class="des"><span class="">'+ vo.order.priceString +'</span></div></div>').addClass('goods');				}
		
	},
	{
		
		data: "buyerRefundReason",
		name: "退款理由",
		orderable: true,
		searchable: true,
		display: function (td, value, vo, rowIndex, colIndex) {
			$(td).html('<div class="inline-block"><div class="des"><span class="">'+ vo.buyerRefundReason +'</span></div></div>').addClass('goods max_w80');				}

	},
		{
			
			data: "creatTime",
			name: "创建时间",
			orderable: true,
			searchable: true,
			display: function (td, value, vo, rowIndex, colIndex) {
				$(td).html('<div class="inline-block"><div class="des"><span class="">'+ value +'</span></div></div>').addClass('goods');				}
		
	},
	{
		
		data: "endTime",
		name: "截止时间时间",
		orderable: true,
		searchable: true,
		display: function (td, value, vo, rowIndex, colIndex) {
			$(td).html('<div class="inline-block"><div class="des"><span class="">'+ value +'</span></div></div>').addClass('goods');				}

	},
	{
		
		data: "status",
		name: "状态",
		orderable: true,
		searchable: true,
		display: function (td, value, vo, rowIndex, colIndex) {
			var temp
			if(value=='0'){
				temp="正在办理"
			}else if(value=='1'){
				temp="退款成功";
			}else if(value=='2'){
				temp="卖家拒绝退款，拒绝理由:"+vo.handlingSuggestion;
			}else if(value=='3'){
				temp="客服介入中";
			}
			$(td).html('<div class="inline-block"><div class="des"><span class="">'+ temp +'</span></div></div>').addClass('goods');				}
	}
			]
		,
		columnOpers: {
			name: "操作",
			display: function(td, id, vo, rowIndex, colIndex) {
				if(vo.status=='2'){
					$(td).html('<div class="inline-block"><div class="des"><a href="/emall/buyer/complaint/add.htm?orderId='+vo.order.id+'"><span class="">申请客服介入</span></a></div></div>').addClass('goods');				}
			

				else{
					$(td).html('').addClass('goods');				

				}}
			}
		
		}
		);
	
})(jQuery, RJ.E);
function  cheakErroe(e){
	  $(e).attr("src","http://tp4.sinaimg.cn/1746716343/180/40037138890/0");
}