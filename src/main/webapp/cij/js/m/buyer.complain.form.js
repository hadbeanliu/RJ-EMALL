

$(document).foundation();
/*图片上传*/
var uploader,
	$list = $('#fileList'),
	arr_images = [];
uploader = WebUploader.create({

	// 选完文件后，是否自动上传。
	auto: true,

	// swf文件路径
	

	// 文件接收服务端。
	server: 'http://192.168.4.211:8080/fileCenter/fileUpload.sp?act=uploadSave&appId=00000001&caseId=00000006',


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

uploader.on('uploadSuccess', function(file, response) {

//	var $li = '<li class="th"><img class="activity-img shop-show-img" src=' + response.url + '></li>';
	var $li = '<div class="uploader uploaded"><span class="bg-preview"><img src='+response.url+' onerror="cheakErroe(this)"  /></span><span class="fa fa-times reset"></span></div>';

	
	arr_images.push(response.url);
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
	
	$('.reset').on('click',function(){
		$(this).parent('.uploader').remove();
		uploader.removeFile( file.id );
	
	})
	
});
function submitcomplaint(e) {
	var a=$('#sortable-list').find('img');
	temp="";
	for (var i=0; i<a.length; i++)
	  {
		 if(a.length-1==i){
			 temp=temp+$(a[i]).attr("src"); 
		 }else{
			 temp=temp+$(a[i]).attr("src")+','; 
		 }
	  }

	$("#pic").val(temp);
	date=$('form').serializeObject();

		$.ajax({
		    type: "get",
		    url: "http://192.168.4.147/msg?act=saveSubmitValues&categoryCode=tsmk",
		    data: date,
		    dataType: "json",   
		    success: function(res){		
		    if(res.ret==0){
		    	$.ajax({
				    type: "get",
				    url: "/emall/buyer/refund/complaint.json?refundId="+$("#refundId").val(),
				    dataType: "json",   
				 
				    success: function(res){		
				    	alert("12")
				    }
				    })
		     window.location.href="/emall/buyer/complaint.htm";	
		    }else{
		    	fail.text('表单提交错误');
				$('.v_info_fail').addClass("show");
				setTimeout(function() {
					$('.v_info_fail').removeClass("show");
				}, 3000);	
		    }
		            
		    }
		});
	}
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
function  cheakErroe(e){
	setTimeout(function(){
		$(e).attr("src", $(e).attr("src"));
	},3000)
	  
}
$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [o[this.name]];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
}
