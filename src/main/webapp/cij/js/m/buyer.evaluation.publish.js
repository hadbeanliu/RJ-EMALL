(function($, E){
	if ( ! E.buyer ) {
		E.buyer = {};
	}
	var evaluationPublish = E.buyer.evaluationPublish = {}; // 电商项目 评价模块 命名空间
	var RATY = {};
	$(function(){ // 绑定订单操作
		     $('.ceshi').click(function(e){
			//var order={};
	        //order.orderId='0ic78qxnl03pu8sp';  2015072809221797041102 0ic77jyv506y222q
	    	var form = E.util.createForm('/buyer/evaluation/publish.htm', '0ic77jyv506y222q', true);
			form.submit();	
					return false;
				});
		
		
		RATY.images = [];
		RATY.arr_images = [];
		
		RATY.sortable={};
		RATY.imageT={};
		RATY.uploader={};

		/*评分*/
		$('.raty1').raty({
			path: function() { //图片路径
				//return this.getAttribute('data-path');
				return "/emall/tpl/dsww/001/"+this.getAttribute('data-path');
			},
			number: function() { //星星数量
				return $(this).attr('data-number');
			},
			score: 5, //默认分数
			cancelOff: 'cancel-off-big.png', //未选中重新评分按钮
			cancelOn: 'cancel-on-big.png', //选中重新评分按钮
			starOn: 'star-smile-gorgeous.png', //激活星星图标
			starOff: 'star-smile-off.png', //未激活星星图标
			halfShow: 'false',
			target: '.raty1-hint', //目标div节点
			targetKeep: true, //点击后，内容的显示
			hints: ['很差', '一般', '好', '很好', '非常好'] //等级的内容
		});
		$('.raty2').raty({
			path: function() {
				//return this.getAttribute('data-path');
				return "/emall/tpl/dsww/001/"+this.getAttribute('data-path');
			},
			number: function() {
				return $(this).attr('data-number');
			},
			score: 5,
			cancelOff: 'cancel-off-big.png',
			cancelOn: 'cancel-on-big.png',
			starOn: 'star-smile-gorgeous.png',
			starOff: 'star-smile-off.png',
			halfShow: false,
			target: '.raty2-hint',
			targetKeep: true,
			targetType: 'hint',
			hints: ['很差', '一般', '好', '很好', '非常好']
		});
		$('.raty3').raty({
			path: function() {
				//return this.getAttribute('data-path');
				return "/emall/tpl/dsww/001/"+this.getAttribute('data-path');
			},
			number: function() {
				return $(this).attr('data-number');
			},
			score: 5,
			cancelOff: 'cancel-off-big.png',
			cancelOn: 'cancel-on-big.png',
			starOn: 'star-smile-gorgeous.png',
			starOff: 'star-smile-off.png',
			halfShow: false,
			target: '.raty3-hint',
			targetKeep: true,
			hints: ['很差', '一般', '好', '很好', '非常好'],
			targetType: 'hint'
		});
		$('.raty4').raty({
			path: function() {
				//return this.getAttribute('data-path');
				return "/emall/tpl/dsww/001/"+this.getAttribute('data-path');
			},
			number: function() {
				return $(this).attr('data-number');
			},
			score: 5,
			cancelOff: 'cancel-off-big.png',
			cancelOn: 'cancel-on-big.png',
			starOn: 'star-smile-gorgeous.png',
			starOff: 'star-smile-off.png',
			halfShow: false,
			target: '.raty4-hint',
			targetKeep: true,
			hints: ['很差', '一般', '好', '很好', '非常好'],
			targetType: 'hint'
		});
		
		var webUploadInit = function(DIV) {
			var $list = $('#' + DIV);
			
			RATY.imageT[DIV]=[];
			RATY.uploader[DIV]=[];
			RATY.sortable[DIV]=[];
			RATY.uploader[DIV] = WebUploader.create({

				// 选完文件后，是否自动上传。
				auto: true,

				// swf文件路径
				swf: '/Ebusiness0612/flash/Uploader.swf',

				// 文件接收服务端。
				//server: '/fupload?act=uploadSave&appId=00000001&caseId=00000018',
				server: 'http://192.168.4.211:8080/fileCenter/fileUpload.sp?act=uploadSave&appId=00000001&caseId=00000018',
				// 选择文件的按钮。可选。
				// 内部根据当前运行是创建，可能是input元素，也可能是flash.
				pick: '#filePicker-'+DIV,

				// 只允许选择图片文件。
				accept: {
					title: 'Images',
					extensions: 'gif,jpg,jpeg,bmp,png',
					mimeTypes: 'image/*'
				},
				fileNumLimit: 5
			});

			$list.on('click', '.reset', function() {
				var fileId = $(this).parent('.uploader').attr("data-id");
				RATY.uploader[DIV].removeFile(fileId);
				for (i = 0; i < RATY.imageT[DIV].length; i++) {
					if (RATY.imageT[DIV][i].wuFileId == fileId) {
						RATY.imageT[DIV].splice(i, 1);
						break;
					}
				};
				$(this).parent('.uploader').remove();
			});
			
			RATY.uploader[DIV].on('uploadSuccess', function(file, response) {

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

					RATY.imageT[DIV].push(obj_pic);
					//RATY.arr_images.push(response.url);

					$list.find('.no-bullet').append($li);

                    
					var el =  document.getElementById('sortable-list-'+DIV);
					RATY.sortable[DIV] = new Sortable(el, {
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
			RATY.uploader[DIV].on('error', function(reason) {
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

		}
		
		var goods=$('.evaluate-cont');
        for(var i=0;i<goods.length;i++){
        	var id=$(goods[i]).find("input[name='id']").val();
        	$(goods[i]).find('.uploader-list').attr('id',id);
        	$(goods[i]).find('.sortable-list').attr('id',"sortable-list"+"-"+id);
        	$(goods[i]).find('.filePicker').attr('id',"filePicker"+"-"+id);
        	webUploadInit(id)
        }
				
	});
	
	//公共方法
	function doAjaxPost(url, data, callBack, type) {

		$.ajax({
			type: "POST",
			dataType: type,
			url: url,
			data: data,
			success: function(retDate) {
				callBack(retDate);
			}
		});
	}
	
	
	//评价
	window['firstEvaluate'] = function(data) {
		var divData=$(data).parents('.evaluate-cont');
		var DIV=divData.find("input[name='id']").val();
		var content = divData.find('.evaluate-comment').find('textarea').val();

		if (content == "") {			
			 $('.v_info_warning').addClass("show");
			   $('.v_info_warning').find(".v_show_info").text("评价内容不能为空");
	  		   setTimeout(function() {
	  				 $('.v_info_warning').removeClass("show");
	  		   }, 3000);	
			return;
		}
		var msxfValue = divData.find(".raty1").find("input[name='score']").val(),
			fwtdValue = divData.find(".raty2").find("input[name='score']").val(),
			fhsdValue = divData.find(".raty3").find("input[name='score']").val(),
			wlfwValue = divData.find(".raty4").find("input[name='score']").val();

		var score = {
			"description": msxfValue,
			"goods": fwtdValue,
			"service": fhsdValue,
			"logistics": wlfwValue
		};

		var scoreJson = JSON.stringify(score);


		function callBack(data) {
			if (data.Code == "200") {				
				 $('.v_info_warning').addClass("show");
				   $('.v_info_warning').find(".v_show_info").text(""+data.Msg+"");
		  		   setTimeout(function() {
		  				 $('.v_info_warning').removeClass("show");
		  		   }, 3000);		
				$("#"+data.id).parents('.evaluate-cont').hide();
				//location.reload();
			}
		}
		var sunCommentPicture = "",
			anonymous = 0; //是否匿名
		//拖拽排序

		var  FileIdSort;
		if (RATY.imageT[DIV].length != 0) {
			FileIdSort = RATY.sortable[DIV].toArray(); //id排序
			var tem_images = RATY.imageT[DIV]; //临时空间
			RATY.imageT[DIV] = []; //清空 arr_images
			RATY.arr_images=[];//清空RATY.arr_images
			for (i = 0; i < FileIdSort.length; i++) {
				for (k = 0; k < tem_images.length; k++) {
					if (tem_images[k].wuFileId == FileIdSort[i]) {
						RATY.imageT[DIV].push(tem_images[k]);
						RATY.arr_images.push(tem_images[k].url);
						break;
					}
				}
			}
		}
		
		if (RATY.arr_images.length != 0) {
			sunCommentPicture =RATY.arr_images.join(",");
		}
		if (divData.find('#checkbox').is(':checked')) {
			anonymous = 1;
		}
		var orderId=$("#orderId").val();//订单ID
		var objId=divData.find("input[name='goodsId']").val();//商品ID
		var storeId=divData.find("input[name='storeId']").val();//店铺ID
		var id=divData.find("input[name='id']").val();//商品自定义ID
		var skuId=divData.find("input[name='skuId']").val();//规格ID
		var goodsTitle=divData.find('.evaluate-text').find('a').html();//商品标题
		var skuInfo=divData.find('.evaluate-sort1').find('ins').html();//规格信息
		var userId=$("#userId").val();//用户ID
		var dataObj = {
			serviceId: "0iaep3l8x06sp5mq",
			storeId: storeId, //店铺ID
			userId: userId, //用户ID
			dynamicStatus: 1, //添加
			goodsTitle: goodsTitle,
			skuInfo: skuInfo,
			objId: objId,
			id:id,
			orderId:orderId,
			skuId:skuId,
			anonymous: anonymous, //是否匿名 0:否1:是
			score: scoreJson,
			comment: content,
			sunCommentPicture: sunCommentPicture,
			determineReceipt: "2015-07-15 12:00:00" //收货时间
		};
		//doAjaxPost("http://192.168.4.125:8080/cmsredis/redisJsonp.sp?act=doService", dataObj, callBack, 'jsonp');
		doAjaxPost(E.Cfg.contextPath+"/redis/doService", dataObj, callBack, 'jsonp');

	}
})(jQuery, RJ.E);