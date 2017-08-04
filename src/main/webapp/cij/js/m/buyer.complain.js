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
			url: "/emall/buyer/complaint", // 请求地址
			
			checkboxMode : false, // 是否出现多选框
			columns: [{
				
					data: "formJson",
					name: "标题",
					orderable: true,
					searchable: true,
					display: function (td, value, vo, rowIndex, colIndex) {
						var temp = eval('(' + value + ')');
						$(td).html('<div class="inline-block "><div class="des "><a href="/emall/buyer/complaint/detail.htm?id='+vo.msgId+'"><span class="">'+ temp.title +'</span></a></div></div>').addClass('text-center');
					}
				
			},
		
	
		{
			
			data: "createDate",
			name: "创建时间",
			orderable: true,
			searchable: true,
			display: function (td, value, vo, rowIndex, colIndex) {
				$(td).html('<div class="inline-block "><div class="des "><span class="">'+ value +'</span></div></div>').addClass('text-center');				}
		
	},
	{
		
		data: "endDate",
		name: "截止时间时间",
		orderable: true,
		searchable: true,
		display: function (td, value, vo, rowIndex, colIndex) {
			$(td).html('<div class="inline-block "><div class="des "><span class="">'+ value +'</span></div></div>').addClass('text-center');				}

	},
	{
		
		data: "status",
		name: "办理情况",
		orderable: true,
		searchable: true,
		display: function (td, value, vo, rowIndex, colIndex) {
			var temp="";
			if('3'==value){
				temp="正在办理";
			}else if('4'==value){
				temp="已办理";
			}else if('5'==value){
				temp="不办理";
			}else {
				temp="未办理";
			}
			$(td).html('<div class="inline-block "><div class="des t"><span class="">'+ temp +'</span></div></div>').addClass('text-center');				}
	
	}
			]
		
	
		
		}
		);
	
})(jQuery, RJ.E);


function  cheakErroe(e){
	  $(e).attr("src","http://tp4.sinaimg.cn/1746716343/180/40037138890/0");
}