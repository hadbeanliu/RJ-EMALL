/******************* 头像图片 *******************/
var uploader,
	$list = $('#fileList'),
	arr_images = [];
uploader = WebUploader.create({

	// 选完文件后，是否自动上传。
	auto: true,
	
	server:'http://192.168.4.211:8080/fileCenter/fileUpload.sp?act=uploadSave',

	// 选择文件的按钮。可选。
	// 内部根据当前运行是创建，可能是input元素，也可能是flash.
	pick: '#filePicker',

	// 只允许选择图片文件。
	accept: {
		title: 'Images',
		extensions: 'gif,jpg,jpeg,bmp,png',
		mimeTypes: 'image/*'
	},
	fileNumLimit: 1,
	fileSingleSizeLimit: 5 * 1024 * 1024 //1M 限制单张图片的上传大小
});

uploader.option('formData', {
	"appId" : "00000001",
	"caseId": "00000018"
});

uploader.on('uploadSuccess', function(file, response) {//延时2秒
	$('#head').val(response.url);
	
	setTimeout(function(){
		var $li = '<div class="uploader uploaded"><span class="bg-preview"><img src="' + response.url + '"></span><span class="fa fa-times reset"></span></div>';
		arr_images.push(response.url);
	
		$list.find('.no-bullet').append($li);
	
		var sortable;
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
			uploader.removeFile(file.id);
			$('#head').val('');
			arr_images = [];
		})
	}, 5000);
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
			fail.text('你已经上传了图片了');
			$('.v_info_fail').addClass("show");
			setTimeout(function() {
				$('.v_info_fail').removeClass("show");
			}, 3000);	
			break;
		case 'F_EXCEED_SIZE':
			fail.text('图片大小超出限制了');
			$('.v_info_fail').addClass("show");
			setTimeout(function() {
				$('.v_info_fail').removeClass("show");
			}, 3000);	
			break;
	}
});
/******************* 身份证正面图片 *******************/
var uploader1,
	$list1 = $('#fileList1'),
	arr_images = [];
uploader1 = WebUploader.create({

	auto: true,
	server:'http://192.168.4.211:8080/fileCenter/fileUpload.sp?act=uploadSave',
	pick: '#filePicker1',
	accept: {
		title: 'Images',
		extensions: 'gif,jpg,jpeg,bmp,png',
		mimeTypes: 'image/*'
	},
	fileNumLimit: 1,
	fileSingleSizeLimit: 5 * 1024 * 1024 //1M 限制单张图片的上传大小
});

uploader1.option('formData', {
	"appId" : "00000001",
	"caseId": "00000018"
});

uploader1.on('uploadSuccess', function(file, response) {
		
	$('#frontCard').val(response.url);
	setTimeout(function(){
		var $li = '<div class="uploader uploaded"><span class="bg-preview"><img src="' + response.url + '"></span><span class="fa fa-times reset"></span></div>';
		arr_images.push(response.url);
	
		$list1.find('.no-bullet').append($li);
	
		var sortable1;
		var el1 = document.getElementById('sortable-list1');
		sortable1 = new Sortable(el1, {
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
			uploader1.removeFile(file.id);
			$('#frontCard').val('');
			arr_images = [];
		})
	}, 5000);
});
uploader1.on('error', function(reason) {
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
			fail.text('你已经上传了图片了');
			$('.v_info_fail').addClass("show");
			setTimeout(function() {
				$('.v_info_fail').removeClass("show");
			}, 3000);	
			break;
		case 'F_EXCEED_SIZE':
			fail.text('图片大小超出限制了');
			$('.v_info_fail').addClass("show");
			setTimeout(function() {
				$('.v_info_fail').removeClass("show");
			}, 3000);	
			break;
	}
});
/******************* 身份证背面图片 *******************/
var uploader2,
	$list2 = $('#fileList2'),
	arr_images = [];
uploader2 = WebUploader.create({

	auto: true,

	server:'http://192.168.4.211:8080/fileCenter/fileUpload.sp?act=uploadSave',
	pick: '#filePicker2',

	accept: {
		title: 'Images',
		extensions: 'gif,jpg,jpeg,bmp,png',
		mimeTypes: 'image/*'
	},
	fileNumLimit: 1,
	fileSingleSizeLimit: 5 * 1024 * 1024 //1M 限制单张图片的上传大小
});

uploader2.option('formData', {
	"appId" : "00000001",
	"caseId": "00000018"
});

uploader2.on('uploadSuccess', function(file, response) {
		
	$('#backCard').val(response.url);
	setTimeout(function(){
		var $li = '<div class="uploader uploaded"><span class="bg-preview"><img src="' + response.url + '"></span><span class="fa fa-times reset"></span></div>';
		arr_images.push(response.url);
	
		$list2.find('.no-bullet').append($li);
	
		var sortable2;
		var el2 = document.getElementById('sortable-list1');
		sortable2 = new Sortable(el2, {
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
			uploader2.removeFile(file.id);
			$('#backCard').val('');
			arr_images = [];
		})
	}, 5000);
});
uploader2.on('error', function(reason) {
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
			fail.text('你已经上传了图片了');
			$('.v_info_fail').addClass("show");
			setTimeout(function() {
				$('.v_info_fail').removeClass("show");
			}, 3000);	
			break;
		case 'F_EXCEED_SIZE':
			fail.text('图片大小超出限制了');
			$('.v_info_fail').addClass("show");
			setTimeout(function() {
				$('.v_info_fail').removeClass("show");
			}, 3000);	
			break;
	}
});
/******************* 营业执照图片 *******************/
var uploader3,
	$list3 = $('#fileList3'),
	arr_images = [];
uploader3 = WebUploader.create({

	auto: true,

	server:'http://192.168.4.211:8080/fileCenter/fileUpload.sp?act=uploadSave',
	pick: '#filePicker3',

	accept: {
		title: 'Images',
		extensions: 'gif,jpg,jpeg,bmp,png',
		mimeTypes: 'image/*'
	},
	fileNumLimit: 1,
	fileSingleSizeLimit: 5 * 1024 * 1024 //1M 限制单张图片的上传大小
});

uploader3.option('formData', {
	"appId" : "00000001",
	"caseId": "00000018"
});

uploader3.on('uploadSuccess', function(file, response) {
//	if($('#licenceFrontcard').val().length > 0){
//		uploader3.removeFile(file.id);
//		arr_images = [];
//	}
	$('#licenceFrontcard').val(response.url);
	setTimeout(function(){
		var $li = '<div class="uploader uploaded"><span class="bg-preview"><img src="' + response.url + '"></span><span class="fa fa-times reset"></span></div>';
		arr_images.push(response.url);
	
		$list3.find('.no-bullet').append($li);
	
		var sortable3;
		var el3 = document.getElementById('sortable-list3');
		sortable3 = new Sortable(el3, {
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
			uploader3.removeFile(file.id);
			$('#licenceFrontcard').val('');
			arr_images = [];
		})
	}, 5000);
});
uploader3.on('error', function(reason) {
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
			fail.text('你已经上传了图片了');
			$('.v_info_fail').addClass("show");
			setTimeout(function() {
				$('.v_info_fail').removeClass("show");
			}, 3000);	
			break;
		case 'F_EXCEED_SIZE':
			fail.text('图片大小超出限制了');
			$('.v_info_fail').addClass("show");
			setTimeout(function() {
				$('.v_info_fail').removeClass("show");
			}, 3000);	
			break;
	}
});
var uploader5,
	$list5 = $('#fileList5'),
	arr_images = [];
	uploader5 = WebUploader.create({

	auto: true,

	server:'http://192.168.4.211:8080/fileCenter/fileUpload.sp?act=uploadSave',
	pick: '#filePicker5',

	accept: {
		title: 'Images',
		extensions: 'gif,jpg,jpeg,bmp,png',
		mimeTypes: 'image/*'
	},
	fileNumLimit: 1,
	fileSingleSizeLimit: 5 * 1024 * 1024 //1M 限制单张图片的上传大小
});

uploader5.option('formData', {
	"appId" : "00000001",
	"caseId": "00000018"
});

uploader5.on('uploadSuccess', function(file, response) {
	$('#licenceBackcard').val(response.url);
	setTimeout(function(){
		var $li = '<div class="uploader uploaded"><span class="bg-preview"><img src="' + response.url + '"></span><span class="fa fa-times reset"></span></div>';
		arr_images.push(response.url);
	
		$list5.find('.no-bullet').append($li);
	
		var sortable5;
		var el5 = document.getElementById('sortable-list5');
		sortable5 = new Sortable(el5, {
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
			uploader5.removeFile(file.id);
			$('#licenceBackcard').val('');
			arr_images = [];
		})
	}, 5000);
});
uploader5.on('error', function(reason) {
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
			fail.text('你已经上传了图片了');
			$('.v_info_fail').addClass("show");
			setTimeout(function() {
				$('.v_info_fail').removeClass("show");
			}, 3000);	
			break;
		case 'F_EXCEED_SIZE':
			fail.text('图片大小超出限制了');
			$('.v_info_fail').addClass("show");
			setTimeout(function() {
				$('.v_info_fail').removeClass("show");
			}, 3000);	
			break;
	}
});
/******************* 专家个人凭证图片 *******************/
var uploader4,
$list4 = $('#fileList4'),
arr_images = [];
uploader4 = WebUploader.create({

auto: true,

server:'http://192.168.4.211:8080/fileCenter/fileUpload.sp?act=uploadSave',
pick: '#filePicker4',

accept: {
	title: 'Images',
	extensions: 'gif,jpg,jpeg,bmp,png',
	mimeTypes: 'image/*'
},
fileNumLimit: 1,
fileSingleSizeLimit: 5 * 1024 * 1024 //1M 限制单张图片的上传大小
});

uploader4.option('formData', {
	"appId" : "00000001",
	"caseId": "00000018"
});

uploader4.on('uploadSuccess', function(file, response) {
	$('#userCredentials').val(response.url);
	setTimeout(function(){
		var $li = '<div class="uploader uploaded"><span class="bg-preview"><img src="' + response.url + '"></span><span class="fa fa-times reset"></span></div>';
		arr_images.push(response.url);
	
		$list4.find('.no-bullet').append($li);
	
		var sortable4;
		var el4 = document.getElementById('sortable-list4');
		sortable4 = new Sortable(el4, {
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
			uploader4.removeFile(file.id);
			$('#userCredentials').val('');
			arr_images = [];
		})
	}, 5000);
});
uploader4.on('error', function(reason) {
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
			fail.text('你已经上传了图片了');
			$('.v_info_fail').addClass("show");
			setTimeout(function() {
				$('.v_info_fail').removeClass("show");
			}, 3000);	
			break;
		case 'F_EXCEED_SIZE':
			fail.text('图片大小超出限制了');
			$('.v_info_fail').addClass("show");
			setTimeout(function() {
				$('.v_info_fail').removeClass("show");
			}, 3000);	
			break;
	}
});