(function($, E){
	var crowdfundingReturn = E.crowdfundingReturn = {}; // 电商项目 众筹回报模块 命名空间
	
	 var crowdfundingId=$("#crowdfundingId").val();
		$("#crowdfunding-return").dataTablePage({
			url : "/emall/seller/crowdfunding/"+crowdfundingId+"/return", // 请求地址
			  columns: 
			[				
			{
				data: "title",
				name: "标题",
				orderable: true,
				searchable: true
			},
			 {
				data: "money",
				name: "金额",
				orderable: true,
				searchable: true
			 },	
			 {
				data: "returnDetails",
				name: "回报详情",
				orderable: true,
				searchable: true
			 },	
			 {
				data: "returnNumber",
				name: "回报数量",
				orderable: true,
				searchable: true
			 },	
			 {
				data: "individualPurchase",
				name: "个人限购",
				orderable: true,
				searchable: true
			 },	
			 {
				 	data: "logisticsWay",
					name: "物流方式",
				 	orderable: true,
				 	searchable: true,
				 	display: function (td, value, vo, rowIndex, colIndex) {
		
				 	 if(vo.logisticsWay=="0"){		
					 		$(td).html('<div class=""><a class="show-for-small">物流方式：</a>无需物流</div>');
					 }else if(vo.logisticsWay=="1"){
					 		$(td).html('<div class=""><a class="show-for-small">众筹状态：</a>全国包邮（港澳台用户不可购买）</div>');
					 }else{
					 		$(td).html('<div class=""><a class="show-for-small">众筹状态：</a>全国包邮（港澳台用户可购买）</div>');
					 }	
				    }
				 },
			 {
				data: "releaseDays",
				name: "回报天数",
				orderable: true,
				searchable: true
			 },	
			 {
						data: "returnPicture",
						name: "回报图片",
						orderable: true,
						searchable: true
		    	}	
			 ], 
			 columnOpers: {
					name: "",
					display: function(td, id, vo, rowIndex, colIndex) {
						$(td).html('<span class="fa-stack v_oper_more"><i class="fa fa-circle"></i><i class="fa fa-ellipsis-h" title="更多"></i></span>'+
								   '<div class="more v_oper_more_panel hide"><span class="v_oper_delete"><i title="删除"></i>删除</span></div>');
					}
				},
				getVo: function($form) {						
					var data = this.def.getVo($form); // 调用默认的获取方法，可进行校验
					if ( data ) {
						var ret = {};		
						if (data.id) {
							ret.id = data.id;
							delete data.id;
						}												
						data.crowdfundingId=crowdfundingId;
						ret.config = JSON.stringify(data);
						return ret;
					} else {
						return; // 校验不通过
					}
				},
			alertInto : "#alert_into" // 提醒实现位置
		});
	//0:上一步 1:更新 2:更新并跳转下一步
	crowdfundingReturn.edit=function(type){            	
       if(type=="1"){
			$("#edit-crowdfunding-return-form").attr("action","./return/vo_update");
		}else if(type=="2"){
			$("#edit-crowdfunding-return-form").attr("action","./return/vo_update_next");
		}
        $("#edit-crowdfunding-return-form").submit();
	} 
    //0:上一步 1:保存 2:保存并跳转下一步
	crowdfundingReturn.save=function(type){            	
       if(type=="1"){
			$("#add-crowdfunding-return-form").attr("action","./return/vo_save");
		}else if(type=="2"){
			$("#add-crowdfunding-return-form").attr("action","./return/vo_save_next");
		}
       $("#add-crowdfunding-return-form").submit();
	} 
	
	$(function(){ // 绑定操作
		$('.save').click(function(e){
			crowdfundingReturn.save(1);			
			return false;
		});
		$('.save_next').click(function(e){
			crowdfundingReturn.save(2);			
			return false;
		});
		$('.edit').click(function(e){
			crowdfundingReturn.edit(1);			
			return false;
		});
		$('.edit_next').click(function(e){
			crowdfundingReturn.edit(2);			
			return false;
		});		
	});
})(jQuery, RJ.E);