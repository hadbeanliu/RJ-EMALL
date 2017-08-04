(function($, E){
	if ( ! E.seller ) {
		E.seller = {};
	}
	var evaluation = E.seller.evaluation = {}; // 电商项目 评价模块 命名空间
	
	$("#seller-evaluation").dataTablePage({
		url :  E.Cfg.contextPath+"/seller/evaluation", // 请求地址
		checkboxMode : false,
		 columns: 
		[		
		{
			data: "nickName",
			name: "评价人",
			orderable: false,
			searchable: false,
			display: function(td, id, vo, rowIndex, colIndex) {			
				$(td).html('' +
                        '<div class="inline-block">' +
                        '<div class="des-reviewers"><span>'+vo.nickName+'</span></div>' +
                        '</div>').addClass('small-block td_small_block small-marl10');
			}
		},{
			data: "goodsTitle",
			name: "商品信息",
			orderable: false,
			searchable: false,
			display: function(td, id, vo, rowIndex, colIndex) {				
				 $(td).html('<div class="inline-block"><div class="des text-left">'+vo.goodsTitle+'</div><div class="des-detail left text-left"><p>'+vo.skuInfo+'</p></div></div>').addClass('large-block td_small_block small-marl10');
			}
		},{
			data: "comment",
			name: "评论",
			orderable: false,
			searchable: false,
			display: function(td, id, vo, rowIndex, colIndex) {
			     var commentHtml="";
			     if(vo.comment.comment!=null){
			    	 commentHtml+='<div class="block"><div class="des clearfix"><span class="v_oper_edit left text-left">'+vo.comment.comment+'</span></div><div class="des-detail text-left"><p>['+vo.comment.commentTime+']</p></div></div>';
			     }
			     if(vo.comment.sunCommentPicture!=""&&vo.comment.sunCommentPicture!=null){
			    	 var picture=vo.comment.sunCommentPicture.split(',');
			    	 commentHtml+='<div class="mesboard-index"><div class="comment-img"><ul class="comment-list clearfix">';
			    	 for(var i=0;i<picture.length;i++){			    		
			    		 commentHtml+=' <li class="no-padding"><a href="javascript:void(0);"><img src="'+picture[i]+'" alt=""></a></li>';
			    	 }
			    	 commentHtml+='</ul></div></div>';
			     }
			     if(vo.comment.explain!=null){
			    	 commentHtml+='<div class="block"><div class="des clearfix"><span class="v_oper_edit left text-left"><a class="p-color">解释：</a>'+vo.comment.explain+'</span></div><div class="des-detail text-left"><p>['+vo.comment.explainTime+']</p></div></div>'
			     }
			     if(vo.comment.additionalComment!=null){
			    	 commentHtml+='<div class="block"><div class="des clearfix"><span class="v_oper_edit left text-left"><a class="p-color">追加评论：</a>'+vo.comment.additionalComment+'</span></div><div class="des-detail text-left"><p>['+vo.comment.additionalCommentTime+']</p></div></div>'
			     }
			     if(vo.comment.sunAdditionalCommentPicture!=""&&vo.comment.sunAdditionalCommentPicture!=null){
			    	 var picture=vo.comment.sunAdditionalCommentPicture.split(',');
			    	 commentHtml+='<div class="mesboard-index"><div class="comment-img"><ul class="comment-list clearfix">';
			    	 for(var i=0;i<picture.length;i++){			    		
			    		 commentHtml+=' <li class="no-padding"><a href="javascript:void(0);"><img src="'+picture[i]+'" alt=""></a></li>';
			    	 }
			    	 commentHtml+='</ul></div></div>';
			     }	
			     if(vo.comment.additionalExplain!=null){
			    	 commentHtml+='<div class="block"><div class="des clearfix"><span class="v_oper_edit left text-left"><a class="p-color">追加解释：</a>'+vo.comment.additionalExplain+'</span></div><div class="des-detail text-left"><p>['+vo.comment.additionalExplainTime+']</p></div></div>'
			     }
				 $(td).html(commentHtml);
				 $(td).addClass('large-block td_small_block small-marl10');
			}
		}					
		],columnOpers: {
			name: "操作",
			display: function(td, id, vo, rowIndex, colIndex) {
				//判断是否超过30天
				if(new Date().getTime()-new Date(vo.comment.determineReceipt).getTime()<1000*60*60*24*30){
					var doOper='';
					if(vo.isExplain==true){
						doOper+='<div class="td_btn"><span class="button default v_oper_isExplain" objId="'+vo.comment.objId+'" cmtId="'+vo.comment.cmtId+'" userId="'+vo.comment.userId+'"><i title="解释"></i>解释</span></div>';
					}
					if(vo.isAdditionalExplain==true){			
						doOper+='<div class="td_btn"><span class="button default v_oper_isAdditionalExplain" objId="'+vo.comment.objId+'" cmtId="'+vo.comment.cmtId+'" userId="'+vo.comment.userId+'"><i title="追加解释"></i>追加解释</span></div>';
					}			
					$(td).html(doOper);
					$(td).addClass('td_btn_wrap min_w120');
					$(td).find('.v_oper_isExplain').bind('click',function(){
						$('.explainTitle').html("添加解释");
						$('.explain').html("解释");
						$('.explain').click(function(e){
							evaluation.addExplain();			
							return false;
						});
						$("#userId").val($(this).attr("userId"));
						$("#objId").val($(this).attr("objId"));
						$("#cmtId").val($(this).attr("cmtId"));
	                    $('.v_add_form').foundation("reveal", "open");
	                });
					$(td).find('.v_oper_isAdditionalExplain').bind('click',function(){
						$('.explainTitle').html("添加追加解释");					
						$('.explain').html("追加解释");
						$('.explain').click(function(e){
							evaluation.addAdditionalExplain();			
							return false;
						});
						$("#userId").val($(this).attr("userId"));
						$("#objId").val($(this).attr("objId"));
						$("#cmtId").val($(this).attr("cmtId"));
	                    $('.v_add_form').foundation("reveal", "open");
	                });
				}
			}
		},
		processing:function(doing){
			if(!doing){
				new img();
				$('.v_table').removeClass('hide');
				$('.sorting_desc').removeClass('sorting_desc').addClass('sorting_disabled');
			}
		},		
		alertInto : "#alert_into" // 提醒实现位置
	});    
	
	//解释
	evaluation.addExplain=function(){
	var userId=$("#userId").val();
	var objId=$("#objId").val();
	var cmtId=$("#cmtId").val();
	var comment=$("#comment").val();
	if(comment.length<2){
		   $('.v_info_warning').addClass("show");
		   $('.v_info_warning').find(".v_show_info").text("请输入2-500字内容!");
 		   setTimeout(function() {
 				 $('.v_info_warning').removeClass("show");
 		   }, 3000);
 		   return false;
	}
	$.ajax({
            type: "GET",
            dataType: 'json',
            data: {
                "userId": userId,
                "objId": objId,
                "explain":comment,
                "cmtId": cmtId
            },
            url:  E.Cfg.contextPath+"/seller/evaluation/setExplainComment",
            success: function(data) { 
            	if(data.Code=="200"){
            	   location.reload();
            	}	             
            }
        });
	}
	//追加解释
	evaluation.addAdditionalExplain=function(){
		var userId=$("#userId").val();
		var objId=$("#objId").val();
		var cmtId=$("#cmtId").val();
		var comment=$("#comment").val();
		if(comment.length<2||comment.length>500){
			   $('.v_info_warning').addClass("show");
			   $('.v_info_warning').find(".v_show_info").text("请输入2-500字内容!");
	 		   setTimeout(function() {
	 				 $('.v_info_warning').removeClass("show");
	 		   }, 3000);
	 		   return false;
		}
		$.ajax({
            type: "GET",
            dataType: 'json',
            data: {
                "userId": userId,
                "objId": objId,
                "additionalExplain":comment,
                "cmtId": cmtId
            },
            url:  E.Cfg.contextPath+"/seller/evaluation/setAdditionalExplain",
            success: function(data) {             
            	if(data.Code=="200"){
            	   location.reload();
            	}	             
            }
        });
	}
})(jQuery, RJ.E);