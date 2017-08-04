(function($, E){
	if ( ! E.seller ) {
		E.seller = {};
	}
	var order = E.seller.order = {}; // 电商项目 订单模块 命名空间
	
	
	$("#user_page").dataTablePage({
		url: "/emall/admin/store", // 请求地址
		//order: [[ 3, "asc" ]],
		columnDefs:[{
			index: 2, // 列号，从0开始
			
			display: function (td, value, vo, rowIndex, colIndex) {
				$(td).html('<div class="inline-block"><div class="img"><img height="70" width="70" src="'+ value +'"  title="'+ value +'"  onerror="cheakErroe(this)"></img></div></div>');
			}
			
		},
		{
			index: 3, // 列号，从0开始
			
			display: function (td, value, vo, rowIndex, colIndex) {
				if(value==1){
				$(td).html('<a  href="javascript:void(0)" class="v_oper_edit" value="1">审核通过</a>');
				}
				
				else if(value==0){
					$(td).html('<a href="javascript:void(0)" value="0" class="v_oper_edit" >待审核</a>');

				}else{
					
					$(td).html('<a href="javascript:void(0)" class="v_oper_edit" value="2">审核不通过</a>');
					
				}
				
			}
			
		},
		{
			index: 4, // 列号，从0开始
			
			display: function(td, id, vo, rowIndex, colIndex) {
				$(td).html('<span class="fa-stack v_oper_more"><i class="fa fa-circle"></i><i class="fa fa-ellipsis-h" title="更多"></i></span>'+
						   '<div class="more v_oper_more_panel hide"><span class="v_oper_delete"><i title="删除"></i>删除</span><span>|</span><span class="v_oper_edit"><i title="编辑"></i>编辑</span></div>');
			}
			
		}
	 	
	 	], 
	     add: function($form){
	    	 RJ.E.util.clearForm($form);
	    	$("#logoaddImg").attr("src","");
	    	$("#logoaddImg").hide();
	    	$form.foundation("reveal", "open");
	    },
	 	afterEdit: function($form, vo){
	 		if(vo){
	 			$("#userName").html(vo.user.nickName);
	 		$("#userId").val(vo.user.id);
	 		$("#userId").val(vo.user.id);
	 		$("#logoImg").attr("src",$("#logo").val());
	 		$form.foundation("reveal", "open");
	 		}
	 	}, 
	 	
		/* columns: [{
			data: "id",
			name: "商铺ID",
			orderable: true,
			searchable: true,
			display: function (td, value, vo, rowIndex, colIndex) {
				$(td).html('tttttt-<a href="javascript:void(0)" class="v_oper_edit" title="'+ value +'">'+ value +'</a>');
			}
		}, {
			data: "createTime",
			name: "创建时间",
			orderable: true,
			searchable: false
		}], */
		alertInto: "#alert_into" // 提醒实现位置
	});
/* 	  function clearVo($form) {
	    	var nv = "[type=checkbox], [type=radio]";
	    	$form.find(nv).prop("checked", false);
	    	$form.find("input, select, textarea").not(nv+", input[type=button], input[type=submit]").val(null);
	    }
	    
	  $(".v_oper_unckeak").click(function(event){
		  $("input[name=status]").val("2");
		  $('.v_oper_submit').trigger('click')
	  })
	    $(".v_oper_ckeak").click(function(event){
		  $("input[name=status]").val("1");
		  $('.v_oper_submit').trigger('click')
	  }) */
	  function  cheakErroe(e){
		  $(e).attr("src","http://tp4.sinaimg.cn/1746716343/180/40037138890/0");
	  }
	
})(jQuery, RJ.E);
$(function(){
		
	});
function  cheakErroe(e){
	  $(e).attr("src","http://tp4.sinaimg.cn/1746716343/180/40037138890/0");
}