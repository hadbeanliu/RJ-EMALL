(function($, E){
	if ( ! E.seller ) {
		E.seller = {};
	}
	var order = E.seller.order = {}; // 电商项目 订单模块 命名空间
	
	

	$("#user_page").dataTablePage({
		url: "/emall/seller/refund", // 请求地址
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
			$(td).html('<div class="inline-block"><div class="des"><span class="">'+ vo.order.orderTitle +'</span></div></div>').addClass('goods');				}
	
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
			$(td).html('<div class="inline-block"><div class="des"><span class="">'+ vo.buyerRefundReason +'</span></div></div>').addClass('goods');				}
	
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
			temp="未处理"
		}else if(value=='1'){
			temp="退款成功";
		}else{
			temp="退款关闭";
		}
		$(td).html('<div class="inline-block"><div class="des"><span class="">'+ temp +'</span></div></div>').addClass('goods');				}
}
		]
	,
	columnOpers: {
		name: "<th class='min_w100'>操作</th>",
		display: function(td, id, vo, rowIndex, colIndex) {
			if(vo.status=='0'){
			/*$(td).html('<div class="td_more"><span class="fa-stack v_oper_more"><i class="fa fa-circle"></i><i class="fa fa-ellipsis-h" title="更多"></i></span>'+
					   '<div class="more v_oper_more_panel hide"><span class="v_oper_cheak" ><i title="同意退款"></i>同意退款</span><span>|</span><span style="display:none;"><i title="评论"></i>评论</span><span style="display:none;">|</span><span class="v_oper_uncheak" ><i title="拒绝退款"></i>拒绝退款</span></div></div>');*/
			  $(td).html('<div class="td_btn"><span class="button default margin-b v_oper_cheak" ><i title="同意退款"></i>同意退款</span></div><div class="td_btn"><span class="button default margin-b v_oper_uncheak"" ><i title="拒绝退款"></i>拒绝退款</span></div>');
			$(td).find(".v_oper_cheak").click(function(){
				cheak(this,'1',vo);
			})
				$(td).find(".v_oper_uncheak").click(function(){
				cheak(this,'2',vo);
			})
			}else{
				$(td).html('<div class="inline-block"><div class="des"><span class="">关闭 </span></div></div>').addClass('goods');				}

			}
		}
	
	
	});
	
})(jQuery, RJ.E);
function  cheakErroe(e){
	  $(e).attr("src","http://tp4.sinaimg.cn/1746716343/180/40037138890/0");
}