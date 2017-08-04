(function($, E){
	if ( ! E.buyer ) {
		E.buyer = {};
	}
	var evaluation = E.buyer.evaluation = {}; // 电商项目 评价模块 命名空间

	
	var RATY = {};
	RATY.images = [];
	RATY.arr_images = [];
	
	/*图片上传*/
	var uploader,
		$list = $('#fileList');

	uploader = WebUploader.create({

		// 选完文件后，是否自动上传。
		auto: true,

		// swf文件路径
		swf: '/Ebusiness0612/flash/Uploader.swf',

		// 文件接收服务端。
		//rver: '/fupload?act=uploadSave&appId=00000001&caseId=00000018',
		server: 'http://192.168.4.211:8080/fileCenter/fileUpload.sp?act=uploadSave&appId=00000001&caseId=00000018',
		// 选择文件的按钮。可选。
		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
		pick: '#filePicker',

		// 只允许选择图片文件。
		accept: {
			title: 'Images',
			extensions: 'gif,jpg,jpeg,bmp,png',
			mimeTypes: 'image/*'
		},
		fileNumLimit: 5
	});
	$('#fileList').on('click', '.reset', function() {
		var fileId = $(this).parent('.uploader').attr("data-id");
		uploader.removeFile(fileId);
		for (i = 0; i < RATY.images.length; i++) {
			if (RATY.images[i].wuFileId == fileId) {
				RATY.images.splice(i, 1);
				break;
			}
		};
		$(this).parent('.uploader').remove();
	});
	var sortable;
	uploader.on('uploadSuccess', function(file, response) {

		if (response.ret != 0) {
			fail.text('类型错误');
			$('.v_info_fail').addClass("show");
			setTimeout(function() {
				$('.v_info_fail').removeClass("show");
			}, 3000);
			return;
		}
		setTimeout(function() {
			var $li = '<div data-id="' + file.id + '" class="uploader uploaded"><span class="bg-preview"><img src="' + response.url + '"></span><span class="fa fa-times reset"></span></div>';

			var obj_pic = {
				wuFileId: file.id,
				url: response.url
			};

			RATY.images.push(obj_pic);
			//RATY.arr_images.push(response.url);

			$list.find('.no-bullet').append($li);


			var el = document.getElementById('sortable-list');
			sortable = new Sortable(el, {
				group: "name", // or { name: "...", pull: [true, false, clone], put: [true, false, array] }
				sort: true, // sorting inside list
				disabled: false, // Disables the sortable if set to true.
				store: null, // @see Store
				animation: 150, // ms, animation speed moving items when sorting, `0` — without animation
				ghostClass: "sortable-ghost", // Class name for the drop placeholder
				dataIdAttr: 'data-id',

				scroll: true, // or HTMLElement
				scrollSensitivity: 30, // px, how near the mouse must be to an edge to start scrolling.
				scrollSpeed: 10
			});
		}, 2000);

	});
	uploader.on('error', function(reason) {
		var fail = $('.v_info_fail').find('.v_show_info');
		switch (reason) {
			case 'Q_TYPE_DENIED':
				fail.text('类型错误');
				$('.v_info_fail').addClass("show");
				setTimeout(function() {
					$('.v_info_fail').removeClass("show");
				}, 3000);
				break;
			case 'F_DUPLICATE':
				fail.text('你已经选择过该图片了');
				$('.v_info_fail').addClass("show");
				setTimeout(function() {
					$('.v_info_fail').removeClass("show");
				}, 3000);
				break;
			case 'Q_EXCEED_NUM_LIMIT':
				fail.text('最多上传5张图片');
				$('.v_info_fail').addClass("show");
				setTimeout(function() {
					$('.v_info_fail').removeClass("show");
				}, 3000);
				break;
		}
	});
	
	$("#buyer-evaluation").dataTablePage({
		url :  E.Cfg.contextPath+"/buyer/evaluation", // 请求地址
		checkboxMode : false,
		 columns: 
		[{
			data: "sellerName",
			name: "被评价人",
			orderable: false,
			searchable: false,
			display: function(td, id, vo, rowIndex, colIndex) {			
				$(td).html('' +
                        '<div class="inline-block">' +
                        '<div class="des-reviewers"><span>卖家:'+vo.sellerName+'</span></div>' +
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
					if(vo.iscomment==true){
						doOper+='<div class="td_btn"><span class="button default v_oper_iscomment" objId="'+vo.comment.objId+'" cmtId="'+vo.comment.cmtId+'" userId="'+vo.comment.userId+'"><i title="评论"></i>评论</span></div>';
					}
					if(vo.isAdditionalComment==true){
						doOper+='<div class="td_btn"><span class="button default v_oper_isAdditionalComment" objId="'+vo.comment.objId+'" cmtId="'+vo.comment.cmtId+'" userId="'+vo.comment.userId+'"><i title="追加评论"></i>追加评论</span></div>';
					}
					$(td).html(doOper);	
					$(td).addClass('td_btn_wrap min_w120');
					$(td).find('.v_oper_isAdditionalComment').bind('click',function(){
						$('.commentTitle').html("添加追加评论");					
						$('.comment').html("追加评论");
						$('.comment').click(function(e){
							evaluation.addAdditionalcomment();			
							return false;
						});
						$("#userId").val($(this).attr("userId"));
						$("#objId").val($(this).attr("objId"));
						$("#cmtId").val($(this).attr("cmtId"));
						$.ajax({
				            type: "GET",
				            dataType: 'json',
				            data: {
				                "userId": $(this).attr("userId"),
				                "objId": $(this).attr("objId"),
				                "cmtId": $(this).attr("cmtId")
				            },
				            url:  E.Cfg.contextPath+"/buyer/evaluation/isSunPicture",
				            success: function(data) {              	
				            	if(data.Code=="204"){
				            	   $('.evaluate-uploaders').hide();
				            	}else{
				            	   $('.evaluate-uploaders').show();
				            	}	   
				            	$('.v_add_form').foundation("reveal", "open");
				            }
				        });                   
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
	
	//追加评论
	evaluation.addAdditionalcomment=function(){
		var userId=$("#userId").val();
		var objId=$("#objId").val();
		var cmtId=$("#cmtId").val();
		var comment=$("#comment").val();
		var sunAdditionalCommentPicture=$("#sunAdditionalCommentPicture").val();
		if(comment.length<2||comment.length>500){
			   $('.v_info_warning').addClass("show");
  			   $('.v_info_warning').find(".v_show_info").text("请输入2-500字内容!");
  	  		   setTimeout(function() {
  	  				 $('.v_info_warning').removeClass("show");
  	  		   }, 3000);
  	  		   return false;
		}
		var sunCommentPicture="";
		var  FileIdSort;
		if (RATY.images.length != 0) {
			FileIdSort = sortable.toArray(); //id排序
			var tem_images = RATY.images; //临时空间
			RATY.images = []; //清空 arr_images
			for (i = 0; i < FileIdSort.length; i++) {
				for (k = 0; k < tem_images.length; k++) {
					if (tem_images[k].wuFileId == FileIdSort[i]) {
						RATY.images.push(tem_images[k]);
						RATY.arr_images.push(tem_images[k].url);
						break;
					}
				}
			}

		}
		if (RATY.arr_images.length != 0) {
			sunCommentPicture = RATY.arr_images.join(",");
		}
		$.ajax({
            type: "GET",
            dataType: 'json',
            data: {
                "userId": userId,
                "objId": objId,
                "additionalComment":comment,
                "cmtId": cmtId,
                "sunAdditionalCommentPicture":sunCommentPicture,
            },
            url:  E.Cfg.contextPath+"/buyer/evaluation/setAdditionalComment",
            success: function(data) {              	
            	if(data.Code=="200"){
            	   location.reload();
            	}	             
            }
        });
	}
})(jQuery, RJ.E);