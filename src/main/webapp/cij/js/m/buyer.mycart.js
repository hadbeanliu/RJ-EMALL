(function($, E){
	if ( ! E.buyer ) {
		E.buyer = {};
	}
	var mycart = E.buyer.mycart = {}; // 电商项目 购物车模块 命名空间	
	
	/*mycart.dataTablePage = function(id,shopId) { // 显示datatable
		$("#"+id).dataTablePage({
			url :  E.Cfg.contextPath++"/buyer/mycart/"+shopId, // 请求地址
			 columns: 
					[			
					{
						data: "id",
						name: "<th class='hide'>店铺名称</th>",
						name: "",
						orderable: false,
						searchable: false,
						display: function(td, id, vo, rowIndex, colIndex) {
							$(td).html('<a class="p-color" title="店铺">店铺名称：' + vo.store.storeName + '</a>').addClass('data_id small-pad10');
							$(td).html('<span>'+vo.goods.goodsTitle+'</span>');
						}
					},{
						data: "createTime",
						name: "",
						orderable: false,
						searchable: false,
						display: function(td, id, vo, rowIndex, colIndex) {
							$(td).html('<td scope="col" class="info-category-col"><span class="spe" data-reveal-id="modSpe">'+vo.inventorySearchInfo+'</span></td>');
						}
					},
					{
						data: "userId",
						name: "",
						orderable: false,
						searchable: false,
						display: function(td, id, vo, rowIndex, colIndex){
							$(td).html('<span class="original-price"></span><span class="latest-price">'+vo.goods.goodsPrice+'</span>')
						}
						
					},{
						data: "buyNumber",
						name: "",
						orderable: false,
						searchable: false,
						display: function(td, id, vo, rowIndex, colIndex){
							$(td).html('<div class="quantity-input-group"><div class="group-btn reduce"><button type="button" class="button outlined"><i class="fa fa-minus"></i></button></div><input type="text" class="group-inp" maxlength="'+vo.goods.goodsNum+'" readonly="" value="'+vo.buyNumber+'"><div class="group-btn add"><button type="button" class="button outlined"><i class="fa fa-plus"></i></button></div></div>').addClass("quantity-col").attr("scope","col");	
						}
					}], 
					columnOpers: {
						name: "",
						display: function(td, id, vo, rowIndex, colIndex) {
							$(td).html('<span class="v_oper_remove" voId='+id+'><a title="删除" class="delete"><i class="fa fa-2x fa-trash-o font-secondary"></i></a></span>').addClass("acticon-col").attr("scope","col");
						}
					},
					processing: function(isProcessing) {	
						if(isProcessing){
							$('.sorting_asc').removeClass().html("xxxxx");		
						}
					},
					afterDelete: function(dels){
						$("#"+dels).remove();
					    this.get$delWarning().foundation("reveal", "close");	
					    mycart.getSelectTotal();					   
					 },
			checkboxMode: true,
			info: false, 
			paging: false
		});
	}*/

	mycart.checkAll=function(isTrue){
		var $check=$("input[name='v_select_id']");
		for(var i=0;i<$check.length;i++){
			$check[i].checked=isTrue;
		}		
		var $v_oper_select_all=$(".v_oper_select_all");
		for(var i=0;i<$v_oper_select_all.length;i++){
			$v_oper_select_all[i].checked=isTrue;
		}		
		mycart.getTotal($check,isTrue);
	}

	$(function(){ //  初始化绑定购物车操作		
		
		$.ajax({
	           type: "POST",
	           dataType: 'json',	          
	           url: E.Cfg.contextPath+"/buyer/mycart/getShopIds.json",
	           success: function(data) {  
	        	   for(var i=0;i<data.length;i++){
/*	        		   $('#data_datatable').append('<div id="'+data[i]+'" class="data_datatable"><div class="v_table"></div><!--确认删除警告--><div id="delete_warning" class="reveal-modal tiny v_del_waring warning-panel" data-reveal="" aria-labelledby="modalTitle" aria-hidden="true" role="dialog"><div class="row"><div class="small-3 columns"><div class="row"><div class="small-12 columns"><i class="fa font-warning fa-warning font-i"></i></div>	</div></div><div class="small-9 columns"><div class="row">	<div class="small-12 columns">确定要删除吗？</div></div><div class="row dele"><div class="small-12 columns"><a class="right button radius tiny v_oper_do_delete">确定</a><a class="close-reveal-modal right button radius tiny secondary margin-r close_btn" aria-label="Close">取消</a></div></div></div></div><a class="close-reveal-modal" aria-label="Close">&#215;</a></div><!--操作成功提示框--><div class="fixed fbottom" style="display:none"><div class="row"><div class="large-12 columns" id="operate_alert_into"><div data-alert=""class="success alert-box warning radius nomarg v_show_oper_info"><div class="v_show_info"><i class="fa fa-fw fa-check"></i>操作成功.</div></div></div></div></div><!--未选中提示框--><div class="fixed v_info_fail hide">	<div class="row"><div class="large-12 columns" id="error_alert_into"><div data-alert="" class="error alert-box warning radius nomarg v_show_oper_info"><div><i class="fa fa-fw fa-exclamation-circle"></i><span class="v_show_info">未选择.</span></div></div></div></div></div><div class="fixed v_info_warning hide"><div class="row"><div class="large-12 columns" id="error_alert_into"><div data-alert="" class="error alert-box warning radius nomarg v_show_oper_info"><div><i class="fa fa-fw fa-exclamation-circle"></i><span class="v_show_info">未选择.</span></div></div></div></div></div></div><br>');
	        			mycart.dataTablePage(data[i],data[i]);*/
	        		   $('.data').append('<table class="merchandise data_datatable" cellspacing="0" id="'+data[i]+'"></table>')
	        		   mycart.getGoodsByStoreId(data[i]);
	        	 }	
	        	},error:function(error){
	        	 alert(error);  
	           }
	       });
		
		//全选绑定		
		$('#above1').click(function(e){
			var isTrue=$('#above1')[0].checked;
			$('#above2')[0].checked=isTrue;
			mycart.checkAll(isTrue);
		});
		$('#above2').click(function(e){
			var isTrue=$('#above2')[0].checked;
			$('#above1')[0].checked=isTrue;
			mycart.checkAll(isTrue);
		});		
		
		//绑定数量加减
		$("#cartForm").on('click','.reduce',function(e){
  		      var num = $(this).parents('tr').find('.group-inp').val();			  			 
			  if (num > 1) {
				num = num - 1;
				cartId=$(this).parents('tr').attr("id");
				var $this=$(this);
				$.ajax({
			           type: "POST",
			           dataType: 'json',
			           data:{
			        	 "cartId":cartId,
			        	 "num":num
			           },
			           url: E.Cfg.contextPath+"/buyer/mycart/update.json",
			           success: function(data) { 
			          /* if(data){   
			        	   $this.parents('tr').find('.group-inp').val(num);
			        	   var isTrue=$this.parents('tr').find('[name=v_select_id]').is(":checked");			        	  
							if(isTrue){
								 var price = parseFloat( $this.parents('tr').find('.latest-price').html());
								 var total= $("#priceTotal").html().replace("￥","");
								 $("#priceTotal").html("￥"+(parseFloat(total)-parseFloat(price)).toFixed(2));		
								 mycart.getSelectTotal();
							}
							
			            }	*/
			        	   
			        	 if(data.Code=="404"){
			        		   $('.v_info_warning').addClass("show");
			      			   $('.v_info_warning').find(".v_show_info").text("最大库存为"+data.maxNum+"件");
			      	  		   setTimeout(function() {
			      	  			   $('.v_info_warning').removeClass("show");
			      	  		   }, 3000);	
			      	  		   $this.parents('tr').find('.group-inp').val(data.maxNum);		        		   
			        	   }else{
			        		   $this.parents('tr').find('.group-inp').val(num);
			        	   }
			        	   var isTrue=$this.parents('tr').find('[name=v_select_id]').is(":checked");				        	  
						   if(isTrue){
								mycart.getSelectTotal();
						   }	
						   
			           },error:function(error){
			        	 alert(error);  
			           }
			       });				
			}	
		    return false;
		    
		}).on('click','.add',function(e){
			var num = $(this).parents('tr').find('.group-inp').val();
			
			var maxNum=$(this).parents('tr').find(".group-inp").attr("maxLength");
			if(parseFloat(num)<parseFloat(maxNum)){
				num=parseFloat(num)+1;			
				
				cartId=$(this).parents('tr').attr("id");
				var $this=$(this);
				$.ajax({
			           type: "POST",
			           dataType: 'json',
			           data:{
			        	 "cartId":cartId,
			        	 "num":num
			           },
			           url: E.Cfg.contextPath+"/buyer/mycart/update.json",
			           success: function(data) { 
			        	   /*if(data){
			        		   $this.parents('tr').find('.group-inp').val(num);
				        	   var isTrue=$this.parents('tr').find('[name=v_select_id]').is(":checked");				        	  
								if(isTrue){
									var price = parseFloat($this.parents('tr').find('.latest-price').html());	
									var total= $("#priceTotal").html().replace("￥","");
									$("#priceTotal").html("￥"+(parseFloat(total)+parseFloat(price)).toFixed(2));			
									mycart.getSelectTotal();
								}			        		   
			        	   }*/
			        	   
			        	   if(data.Code=="404"){
			        		   $('.v_info_warning').addClass("show");
			      			   $('.v_info_warning').find(".v_show_info").text("最大库存为"+data.maxNum+"件");
			      	  		   setTimeout(function() {
			      	  				 $('.v_info_warning').removeClass("show");
			      	  		   }, 3000);	
			      	  		   $this.parents('tr').find('.group-inp').val(data.maxNum);			        		   
			        	   }else{
			        		   $this.parents('tr').find('.group-inp').val(num);
			        	   }
			        	   var isTrue=$this.parents('tr').find('[name=v_select_id]').is(":checked");				        	  
						   if(isTrue){
								mycart.getSelectTotal();
						   }	
			           },error:function(error){
				        	 alert(error);  
				           }
				       });					
			}	
			return false;
			
		}).on('click','input[name="v_select_id"]',function(e){
			var num =$(this).parents('tr').find('.group-inp').val();
			var price=parseFloat($(this).parents('tr').find('.latest-price').html());	
			var isTrue=$(this).parents('tr').find('[name=v_select_id]').is(":checked");
			var total=  $("#priceTotal").html().replace("￥","");
			if(isTrue){
				$("#priceTotal").html("￥"+(parseFloat(total)+parseFloat(price)*num).toFixed(2));
				$("#selectedTotal").html(parseFloat(parseFloat($("#selectedTotal").html())+1));
				mycart.isShopAllCheck($(this).parents(".data_datatable").attr("id"));
			}else{
				$("#selectedTotal").html(parseFloat(parseFloat($("#selectedTotal").html())-1));
				$("#priceTotal").html("￥"+(parseFloat(total)-parseFloat(price)*num).toFixed(2));
				$(this).parents('.data_datatable').find('.v_oper_select_all').attr('checked',false);
				$('#above1')[0].checked=false;
				$('#above2')[0].checked=false;
			}
			mycart.isSettleAccounts();
		}).on('click','.v_oper_select_all',function(e){
			var isTrue=$(this).is(":checked");
			var checks=$(this).parents('.data_datatable').find('[name=v_select_id]');
			for(var i=0;i<checks.length;i++){	
				if(isTrue){	
					checks[i].checked=true;	
					//全选判断
				}else{
					$('#above1')[0].checked=false;
					$('#above2')[0].checked=false;
					checks[i].checked=false;					
				}
				mycart.getSelectTotal();
				mycart.isAllCheck();
			}	
			mycart.isSettleAccounts();
		}).on('click','#deleteAll',function(e){
			mycart.deleteSelect();
			mycart.isSettleAccounts();
		}).on('click','.v_oper_remove',function(e){			
			mycart.remove($(this).attr("voId"));
			mycart.isSettleAccounts();
		}).on('click','.settleAccounts',function(e){
			// 获取选择商品到结算页
			mycart.settleAccounts();
		}).on('blur','.group-inp',function(e){
			var cartId=$(this).parents('tr').attr("id");
			var num=$(this).val();
			var $this=$(this);
			$.ajax({
		           type: "POST",
		           dataType: 'json',
		           data:{
		        	 "cartId":cartId,
		        	 "num":num
		           },
		           url: E.Cfg.contextPath+"/buyer/mycart/update.json",
		           success: function(data) { 
		        	   if(data.Code=="404"){
		        		   $('.v_info_warning').addClass("show");
		      			   $('.v_info_warning').find(".v_show_info").text("最大库存为"+data.maxNum+"件");
		      	  			setTimeout(function() {
		      	  				 $('.v_info_warning').removeClass("show");
		      	  		}, 3000);	
		        		   $this.val(data.maxNum);		        		   
		        	   }
		        	   var isTrue=$this.parents('tr').find('[name=v_select_id]').is(":checked");				        	  
					   if(isTrue){
							mycart.getSelectTotal();
					   }	
		           },error:function(error){
			        	 alert(error);  
			           }
			       });
		}).on('keyup','.group-inp',function(e){	
			this.value=this.value.replace(/\D/g,'');
			if(this.value==""||this.value=="0"){
				this.value="1";
			}
		});		
	});
	//获取选中值
	mycart.getSelectTotal=function(){
		var total=0;
		var totalSize=0;
		var $check=$("#cartForm").find('[name=v_select_id]');		
		for(var i=0;i<$check.length;i++){			
			var isTrue=$check[i].checked;
			if(isTrue){
				var dataCheck=$($check[i]).parents('tr');
				total+=parseFloat(dataCheck.find('.latest-price').html()*dataCheck.find(".group-inp").val());
				totalSize+=1;
			}
		}
		$("#selectedTotal").html(totalSize);
		$("#priceTotal").html("￥"+total.toFixed(2));
	}
	//全选
	mycart.getTotal=function($check,isTrue){		
		var total=0;
		var totalSize=0;
		if(isTrue){
			for(var i=0;i<$check.length;i++){
				var isTrue=$check[i].checked;
					total+=parseFloat($($check[i]).parents('.data_datatable').find('.latest-price').html()*$($check[i]).parents('tr').find(".group-inp").val());
					totalSize+=1;
			}
		}
		//$("#selectedTotal").html(totalSize);
		//$("#priceTotal").html("￥"+total.toFixed(2));
		mycart.getSelectTotal();
		mycart.isSettleAccounts();
	}
	//是否全选
	mycart.isAllCheck=function(){
		var v_oper_select_all=$('.v_oper_select_all');
		var above=true;
		for(var i=0;i<v_oper_select_all.length;i++){
			if(!v_oper_select_all[i].checked){
				above=false;
				break
			}
		}
		if(above){
			$('#above1')[0].checked=true;
			$('#above2')[0].checked=true;
		}else{
			$('#above1')[0].checked=false;
			$('#above2')[0].checked=false;
		}
	}
    //单选时 是否该店铺下是否已全选
	mycart.isShopAllCheck=function(id){	
		var $check=$("#"+id).find('[name=v_select_id]');
		var $v_oper_select_all=true;
		for(var i=0;i<$check.length;i++){
			if(!$check[i].checked){
				$v_oper_select_all=false;
				break;
			}
		}
		if($v_oper_select_all){			
			document.getElementById(id).getElementsByClassName('v_oper_select_all')[0].checked=true;
		}else{
			document.getElementById(id).getElementsByClassName('v_oper_select_all')[0].checked=false;
		}
		mycart.isAllCheck();
	}
	//删除选中商品
	mycart.deleteSelect=function(){
		//获取选中商品
		var $selectCheck=$('#cartForm').find('[name=v_select_id]');	
		var voIds=[];
	/*	var parentIds=[];*/
		for(var i=0;i<$selectCheck.length;i++){
			if($($selectCheck[i]).is(":checked")){
				var voId=$($selectCheck[i]).parents("tr").attr("id");
				voIds.push(voId);
				/*var id=$("#"+voId).parents(".data_datatable").attr("id");
				parentIds.push(id);*/
			}
		}
	   if(voIds.length==0){
		  $('.v_info_warning').addClass("show");
		  $('.v_info_warning').find(".v_show_info").text("未选择");
  			setTimeout(function() {
  				 $('.v_info_warning').removeClass("show");
  			}, 3000);		
		}else{
		  //批量删除		
		$.ajax({
	           type: "POST",
	           dataType: 'json',
	           data:{"voIds":voIds+""},
	           url: E.Cfg.contextPath+"/buyer/mycart/v_dels.json",
	           success: function(data) {  
	        	for(var i=0;i<voIds.length;i++){
	        		/* mycart.isShopAllCheck(parentIds[i]);*/
	        		 $("#"+voIds[i]).remove();			
	        	}	        	 
	        	 mycart.removeNullDatatable();
	        	 mycart.getSelectTotal();	   	 
	           },error:function(error){
	        	 //alert(error);  
	           }
	       });
	   }
	}
	//删除单条记录
	mycart.remove=function(voId){
		var id=$("#"+voId).parents(".data_datatable").attr("id");
		$.ajax({
	           type: "POST",
	           dataType: 'json',	   
	           url: E.Cfg.contextPath+"/buyer/mycart/v_del/"+voId+".json",
	           success: function(data) {  
	        	$("#"+voId).remove(); 
	        	mycart.isShopAllCheck(id);        	           	    
        	    mycart.removeNullDatatable();
			    mycart.getSelectTotal();	   	 
	           },error:function(error){
	        	 //alert(error);  
	           }
	     });		
	}
	//为空时去掉datatable
	mycart.removeNullDatatable=function(){
		var $v_oper_select_all=$('.v_oper_select_all');
		for(var i=0;i<$v_oper_select_all.length;i++){
			//是否有数据
			var $check=$($v_oper_select_all[i]).parents('.data_datatable').find('[name=v_select_id]');	
			if($check.length==0){
				//去掉datatable
				var id=$($v_oper_select_all[i]).parents('.data_datatable').attr('id');
				$("#"+id).remove();
			}
		}
	}
	//根据店铺ID获取用户收藏该店铺的商品信息
	mycart.getGoodsByStoreId=function(storeId){
		$.ajax({
	           type: "POST",
	           dataType: 'json',	   
	           url: E.Cfg.contextPath+"/buyer/mycart/"+storeId+"/v_page.json",
	           success: function(data) {  	        
	        	 mycart.addHtml(data.data);
	           },error:function(error){
	        	 //alert(error);  
	           }
	     });		
	}
	//判断是否可到结算页(多家店铺不可用)
	mycart.isSettleAccounts=function(){
		var $check=$("#cartForm").find('[name=v_select_id]');	
		var storeId=null;
		for(var i=0;i<$check.length;i++){
			if($check[i].checked){
				var storeIdTemp=$($check[i]).parents('.data_datatable').attr('id');				
				if(storeIdTemp!=storeId&&storeId!=null){			
					$('.settlement').html('不支持合并结算');
					return false;
				}else{
					storeId=storeIdTemp;
				}
			}
		}
		$('.settlement').html('<a class="button expand alert settleAccounts">结算</a>');
		return true;
	}
	//转到结算页
	mycart.settleAccounts=function(){	
		var b=[];
		var $check=$("#cartForm").find('[name=v_select_id]');	
		for(var i=0;i<$check.length;i++){
			var selectCheck=$($check[i]);
			if(selectCheck.is(":checked")){
				var skuId=selectCheck.attr("id");
				var goodsInfo={};
				goodsInfo.carId=selectCheck.parents("tr").attr('id');
				goodsInfo.skuId=skuId;
				goodsInfo.purchaseQuantity=selectCheck.parents("tr").find('.group-inp').val();
				goodsInfo.goodsId=skuId.substring(0,16);
			    b.push(goodsInfo);	
			}
		}
		if(b.length>0&&mycart.isSettleAccounts()){
			var form = E.util.createForm('/buyer/order/confirm.htm', b, true);
			form.submit();	
		}else{
			 $('.v_info_warning').addClass("show");
			 $('.v_info_warning').find(".v_show_info").text("未选择");
	  			setTimeout(function() {
	  				 $('.v_info_warning').removeClass("show");
	  		}, 3000);	
		}	
	}
	//往表格添加数据
	mycart.addHtml=function(data){
		//店铺头
		var headHtml='<thead class="shop"><tr><th scope="col" class="chose-col"><label class="label-checkbox nomarg square" for="above_'+data[0].store.storeId+'"><input type="checkbox" class="check-shop check v_oper_select_all" id="above_'+data[0].store.storeId+'" /><i class="icon-check"></i></label></th><th scope="col" colspan="6" class="merchandise-column"><strong>店铺：</strong><a href="#">'+data[0].store.storeName+'</a><a class="font-warning font-normal"><i class="fa fa-comment fa-fw"></i></a></th></tr></thead>'
		//商品
		var goodsHtml="";
		for(var i=0;i<data.length;i++){			
			goodsHtml+='<tr id="'+data[i].id+'"><th scope="col" class="chose-col"><label class="label-checkbox square" for="'+data[i].skuId+'"><input type="checkbox" class="check"  name="v_select_id" id="'+data[i].skuId+'"/><i class="icon-check"></i></label></th><td scope="col" class="info-img-col"><a><img src="'+data[0].goods.goodsFaceThumb+'" /></a></td><td scope="col" class="info-name-col"><a>'+data[i].goods.goodsTitle+'</a></td><td scope="col" class="info-category-col"><span class="spe" data-reveal-id="modSpe">'+data[i].skuInfo+'</span></td><td scope="col" class="price-col"><span class="original-price">'+data[i].goods.goodsPrice+'</span><span class="latest-price">'+data[i].sku.goodsPrice+'</span></td><td scope="col" class="quantity-col"><div class="quantity-input-group"><div class="group-btn reduce"><button type="button" class="button outlined"><i class="fa fa-minus"></i></button></div><input type="text" class="group-inp" maxlength="'+data[i].goods.goodsNum+'"  value="'+data[i].buyNumber+'" /><div class="group-btn add"><button type="button" class="button outlined"><i class="fa fa-plus"></i></button></div></div></td><td scope="col" class="acticon-col"><a title="删除"  voId="'+data[i].id+'" class="delete v_oper_remove" data-reveal-id="warning"><i class="fa fa-2x fa-trash-o font-secondary"></i></a></td>';	
		}		
		var dataHtml=headHtml+"<tbody class=\"merchandise-list\">"+goodsHtml+"</tbody>";
		$("#"+data[0].store.storeId).html(dataHtml);
	}
})(jQuery, RJ.E);