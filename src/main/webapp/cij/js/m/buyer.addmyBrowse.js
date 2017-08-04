(function($, E){
	if ( ! E.buyer ) {
		E.buyer = {};
	}
	var addMyBrowse = E.addMyBrowse = {}; // 电商项目 我的浏览模块 命名空间	
	var beginTime=new Date();
	addMyBrowse.addMyBrowse=function(){
		var gId = goodsId;
		if(gId == undefined || gId == "") gId = "2015062915000006";
		//2015062915000006手串 2015071488000002 2015071016000004
		if(goodsId!=""){
			$.ajax({
	            type: "post",
//	            dataType: 'json',
	            data: {
	                "goodsId": goodsId,
	                "residenceTime":(new Date()).getTime()-beginTime.getTime(),
	            },
	            url:  E.Cfg.contextPath+"/buyer/mybrowse/saveMyBrowseToCookie",
	            success: function(data) {  
//	               var data=data;	       
	            },error:function(error){            	
//	            	alert(error);
	            }
	        });	
		}
	}

	$(function(){ // 绑定订单操作
	   $(window).bind('beforeunload',function(){ 
		setTimeout(addMyBrowse.addMyBrowse(),5000);			
	   });	
	});

})(jQuery, RJ.E);