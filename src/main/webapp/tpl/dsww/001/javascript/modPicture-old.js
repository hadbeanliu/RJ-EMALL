

	var  img = new Image();

	var jcrop_api;
	var imgObjPreview;
	var isSave = false;
	var isBtnInit = false;

	var  geometric = 1; //比例
	var $ = jQuery,
		$modPic = $('#modPic'),
		$upArea = $modPic.find('.uploader-area'),
		$upList = $modPic.find('.uploader-list'),
		$list = $('#fileList'),
		// 优化retina, 在retina下这个值是2
		ratio = window.devicePixelRatio || 1,

		// 缩略图大小
		thumbnailWidth = 310 * ratio,
		thumbnailHeight = 466 * ratio,
		imgFile,

		// Web Uploader实例
		uploader;


	function saveHead() {
		$("#caseId").val("00000006");  
		$("#act").val("uploadAndCutImg");
		if ($('#x').val() == "0" && $('#x').val() == "0" && $('#y').val() == "0" && $('#w').val() == "0" && $('#h').val() == "0") {    
			var  width = img.width;    
			var  height = img.height;    
			$('#x').val("0");    
			$('#y').val("0");    
			$('#w').val(parseInt(100 * width / 400));    
			$('#h').val(parseInt(100 * height / 400)); 

		}
		uploader.option('formData', {
			"x": $('#x').val(),
			"y": $('#x').val(),
			"w": $('#w').val(),
			"h": $('#h').val()
		});
		uploader.option('server', 'http://192.168.4.211:8080/fileCenter/fileUpload.sp?act=uploadAndCutImg&appId=00000001&caseId=00000006');
		//uploader.option('server', '/fupload?act=uploadAndCutImg&appId=00000001&caseId=00000006');
		isSave = true;
		uploader.retry(imgFile);

	}

	function uHeadPic(url) {
		$.ajax({
			type: "get",
			data: {
				"urlHead" : url
			},
			url: "/emall/user/vo_edituserhead.json",
			cache: false,
			dataType: "json",
			success: function(data) {
				$(".v_info_success").addClass("show");
				$(".v_info_success").find(".v_show_info").text("保存头像成功");
	    		setTimeout(function() {
	    		$(".v_info_success").removeClass("show");
	    		}, 3000);
	    		location.reload();
			},
			error:function(e){
				$(".v_info_fail").addClass("show");
				$(".v_info_fail").find(".v_show_info").text("修改头像失败");
	    		setTimeout(function() {
	    		$(".v_info_fail").removeClass("show");
	    		}, 3000);
			}
		});
	}


	/*
	jQuery.ajax({
		type: "get",
		data: {
			"userId": baseMode.userId
		},
		url: "/redis?act=searchByUserId",
		cache: false,
		dataType: "json",
		success: function(data) {
			var preview1 = document.getElementById("preview1"),
				preview2 = document.getElementById("preview2"),
				preview3 = document.getElementById("preview3"),
				url = (typeof(data.result.headPic) == "undefined") ? "" : data.result.headPic;
			if (url = "") {
				return;
			}

			//var suffix = url.split('.')[(url.split('.')).length - 1];
			//url = url.replace('.' + suffix, "");      
			preview1.src = data.result.headPic;
			preview2.src = data.result.headPic;
			preview3.src = data.result.headPic;  //$("#cropbox").attr("src",data.result.headPic); 
			  //$("#cropbox").show();
			  //$("#preview-hidden").show();    
			//$("#upload_btnbar").show();                    
			//$("#cropbox").load(function(){
			     // initJcrop();
			      // });
		},
		error: function(error) {
			jqBaseapp.popup(error);
		}
	})

*/

	// 初始化Web Uploader
	uploader = WebUploader.create({

		// 自动上传。
		auto: true,
		fileVal: 'doc',

		// swf文件路径
		swf: './flash/Uploader.swf',

		// 文件接收服务端。
		server: 'http://192.168.4.211:8080/fileCenter/fileUpload.sp?act=uploadSave&appId=00000001&caseId=00000006',
		//server: '/fupload?act=uploadSave&appId=00000001&caseId=00000006',
		// 选择文件的按钮。可选。
		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
		pick: '#filePicker',


		// 只允许选择文件，可选。
		accept: {
			title: 'Images',
			extensions: 'gif,jpg,jpeg,bmp,png',
			mimeTypes: 'image/*'
		}
	});
	// 文件上传过程中创建进度条实时显示。
	uploader.on('uploadProgress', function(file, percentage) {
		var $li = $('#' + file.id),
			$percent = $li.find('.progress span');

		// 避免重复创建
		if (!$percent.length) {
			$percent = $('<p class="progress"><span></span></p>')
				.appendTo($li)
				.find('span');
		}

		$percent.css('width', percentage * 100 + '%');
	});
	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploader.on('uploadSuccess', function(file, response) {
		setTimeout(function(){
			
		
		//未剪切前上传头像
		if (!isSave) {
			$('.text-ali-center').removeClass("hide");
			imgFile = file;
			if (response.ret == "0") {
				if ($('.thumbnail').length > 0) {
					$('.thumbnail').remove();
				}
				$('#' + file.id).addClass('upload-state-done');
				var $li = $(
						'<div id="' + file.id + '" class="file-item thumbnail">' +
						'<img>' +
						'</div>'
					),
					$img = $li.find('img');
				$img.attr('src', response.url).attr('id', "cropbox");
				$list.append($li);
				$('#' + file.id).find('.progress').remove();
				$upArea.hide();

				var url = response.url 
				$("#cropbox").show();   
				$("#preview-hidden").show();  
				var preview1 = document.getElementById("preview1"),
					preview2 = document.getElementById("preview2"),
					preview3 = document.getElementById("preview3");
				preview1.src = url;  
				preview2.src = url;  
				preview3.src =  url;

				        
				img.src = $('#cropbox').attr("src");

				$('#cropbox').LoadImage(true, 400, 400, url);
				//initJcrop();
				$('#saveBtn').show();
				$('#filePicker2').show();

				if (!isBtnInit) {
					uploader.addButton({
						id: '#filePicker2'
					});
				}
			}
		} else {
			//保存剪切后的头像
			if (response.state == "SUCCESS") {
				var url = response.url;
				uHeadPic(url);
			} else { 
				jqBaseapp.popup("头像修改失败!","2");
				history.go(0);
			}
		}
		},5000);


	});

	// 文件上传失败，现实上传出错。
	uploader.on('uploadError', function(file) {
		var $li = $('#' + file.id),
			$error = $li.find('div.error');

		// 避免重复创建
		if (!$error.length) {
			$error = $('<div class="error"></div>').appendTo($li);
		}

		$(".v_info_fail").addClass("show");
		$(".v_info_fail").find(".v_show_info").text("头像上传失败");
		setTimeout(function() {
		$(".v_info_fail").removeClass("show");
		}, 3000);
	});

	// 完成上传完了，成功或者失败，先删除进度条。
	uploader.on('uploadComplete', function(file) {


	});



	function initJcrop() {

		jcrop_api = $.Jcrop("#cropbox", {
			minSize: [30, 30],
			maxSize: [400, 400],
			aspectRatio: 1,
			setSelect: [0, 0, 100, 100],
			onChange: updatePreview,
			onSelect: updatePreview
		});

	}

	function updatePreview(c) {
		if (parseInt(c.w) > 0) {
			var rx = 180 / c.w;
			var ry = 180 / c.h;

			jQuery('#preview1').css({

				width: Math.round(rx * parseInt(result_width)) + 'px',
				height: Math.round(ry * parseInt(result_height)) + 'px',
				marginLeft: '-' + Math.round(rx * c.x) + 'px',
				marginTop: '-' + Math.round(ry * c.y) + 'px'
			});

		}
		if (parseInt(c.w) > 0) {
			var rx = 60 / c.w;
			var ry = 60 / c.h;

			jQuery('#preview2').css({

				width: Math.round(rx * parseInt(result_width)) + 'px',
				height: Math.round(ry * parseInt(result_height)) + 'px',
				marginLeft: '-' + Math.round(rx * c.x) + 'px',
				marginTop: '-' + Math.round(ry * c.y) + 'px'
			});

		}
		if (parseInt(c.w) > 0) {
			var rx = 30 / c.w;
			var ry = 30 / c.h;

			jQuery('#preview3').css({

				width: Math.round(rx * parseInt(result_width)) + 'px',
				height: Math.round(ry * parseInt(result_height)) + 'px',
				marginLeft: '-' + Math.round(rx * c.x) + 'px',
				marginTop: '-' + Math.round(ry * c.y) + 'px'
			});

		}   
		var  width = img.width;
		var  height = img.height;
		$('#x').val(parseInt(c.x * width / result_width));
		$('#y').val(parseInt(c.y * height / result_height));

		$('#w').val(parseInt(c.w * width / result_width));
		$('#h').val(parseInt(c.h * height / result_height));
	};

	var  result_width = 0;
	var  result_height = 0;
	jQuery.fn.LoadImage = function(scaling, width, height, loadpic) {
		return this.each(function() {
			var t = $(this);
			var src = $(this).attr("src")
			var img = new Image();
			//alert("Loading") 
			img.src = src;
			//自动缩放图片 
			var autoScaling = function() {
					if (scaling) {

						if (img.width > 0 && img.height > 0) {
							if (img.width / img.height >= width / height) {
								if (img.width > width) {
									t.width(width);
									t.height((img.height * width) / img.width);
								} else {
									t.width(img.width);
									t.height(img.height);
								}
							} else {
								if (img.height > height) {
									t.height(height);
									t.width((img.width * height) / img.height);
								} else {
									t.width(img.width);
									t.height(img.height);
								}
							}
						}
					}
				}
				//处理ff下会自动读取缓存图片 
			if (img.complete) {
				//alert("getToCache!"); 
				autoScaling();
				return;
			}
			$(this).attr("src", "");
			var loading = $("<img alt=\"加载中\" title=\"图片加载中\" src=\"" + loadpic + "\" />");

			t.hide();
			t.after(loading);
			$(img).load(function() {
				autoScaling();
				loading.remove();
				t.attr("src", this.src);
				t.show();
				//alert("finally!") 
				$("#cropbox").width(t.width());
				$("#cropbox").height(t.height());
				result_width = t.width();
				result_height = t.height();
				initJcrop();
			});

		});

	}
