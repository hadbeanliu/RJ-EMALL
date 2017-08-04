(function($, E){
	var crowdfungding = E.crowdfungding = {}; // 电商项目 众筹模块 命名空间
	
	var userId=$("#userId").val();
	$("#crowdfunding").dataTablePage({
		url : "/emall/seller/user/crowdfunding", // 请求地址
		 columns: 
		[				
		{
			data: "projectName",
			name: "众筹名称",
			orderable: true,
			searchable: true
		},
		 {
			data: "targetAmount",
			name: "最低金额",
			orderable: true,
			searchable: true
		 },	
		 {
			data: "supportNumber",
			name: "支持人数",
			orderable: true,
			searchable: true
		 },	
		 {
			data: "raisingDays",
			name: "筹集天数",
			orderable: true,
			searchable: true
		 },	
		 {
			data: "currentTotalFunding",
			name: "目前累计资金",
			orderable: true,
			searchable: true
		 },	
		 {
			 	data: "crowdfundingStatus",
				name: "众筹状态",
			 	orderable: true,
			 	searchable: true,
			 	display: function (td, value, vo, rowIndex, colIndex) {
			 	//当前时间-开始时间-结束时间
			 	var nowTime=new Date().getTime();	
			 	var startTime=new Date(vo.startTime).getTime();
			 	var endTime=new Date(vo.endTime).getTime();
			 	 if(parseInt(nowTime)>parseInt(startTime)){		
				 		$(td).html('<div class=""><a class="show-for-small">众筹状态：</a>准备中</div>');
				 }else if(parseInt(nowTime)>parseInt(endTime)){
				 		$(td).html('<div class=""><a class="show-for-small">众筹状态：</a>已完成</div>');
				 }else{
				 		$(td).html('<div class=""><a class="show-for-small">众筹状态：</a>众筹中</div>');
				 }	
			    }
			 },
		 {
			data: "createTime",
			name: "创建时间",
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
		alertInto : "#alert_into" // 提醒实现位置
	});
    //1:更新 2:更新并跳转下一步
	crowdfungding.edit=function(type){            	
       if(type=="1"){
			$("#edit-crowdfunding-form").attr("action","./crowdfunding/vo_update");
		}else if(type=="2"){
			$("#edit-crowdfunding-form").attr("action","./crowdfunding/vo_update_next");
		}
       $("#edit-crowdfunding-form").submit();
	} 
    //1:保存 2:保存并跳转下一步
	crowdfungding.save=function(type){            	
       if(type=="1"){
			$("#add-crowdfunding-form").attr("action","./crowdfunding/vo_save");
		}else if(type=="2"){
			$("#add-crowdfunding-form").attr("action","./crowdfunding/vo_save_next");
		}
       $("#add-crowdfunding-form").submit();
	} 
	
	$(function(){ // 绑定操作
		$('.save').click(function(e){
			crowdfungding.save(1);			
			return false;
		});
		$('.save_next').click(function(e){
			crowdfungding.save(2);			
			return false;
		});
		$('.edit').click(function(e){
			crowdfungding.edit(1);			
			return false;
		});
		$('.edit_next').click(function(e){
			crowdfungding.edit(2);			
			return false;
		});		
	});
})(jQuery, RJ.E);