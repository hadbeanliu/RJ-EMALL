(function($, E){
	var logistics = E.logistics = {}; //认证管理命名 

	var url = E.Cfg.contextPath;

	var initLogisticsUrl =  url + "/buyer/get_logistics.json";
	var getCompanyListUrl =  url + "/buyer/get_companyList.json";
	
	/**
	 * 获取物流跟踪信息
	 * 可切换单条多条的功能效果
	 * @param $elem 元素，用于存放跟踪信息的位置
	 * @param mailno 运单号码
	 * @param comId 公司编号
	 * @param comName 公司名称
	 * @param orderId 订单编号
	 */
	logistics.anotherLogisticsData = function( $elem, mailno, comId, comName,orderId){
		$elem.html();
		var data = {};
		if(mailno.length == 0 || comId.length == 0) return;
		data['order'] = mailno;
		data['comId'] = comId;
		$.ajax({
			url : initLogisticsUrl,
			data : data,
			type : "POST",
    		dataType : "json",
			success : function(logi){
				if(logi == undefined || logi=="") return;
				
				var headHtm = '<span class="dotted-node"> &nbsp; </span> \
					<span> 物流： </span>\
					<div class="trade-detail-logistic package-main">\
					<span class="package-detail logcode"">'+comName+'</span>\
					<span class="package-detail"> 运单号码: </span> \
					<span class="mailno">'+logi.showapi_res_body.mailNo+'</span>\
					<div class="logistic-detail">';
				var middleHtm = '';
				if(logi.showapi_res_body.msg != ""){
					var logibody = logi.showapi_res_body;
					for ( var i = 0; i < logibody.data.length; i++) {
						if(i == 0) {
							middleHtm += '<span  class="font-warning">'+ logibody.data[i].time +'</span> \
								<span class="package-detail package-address-detail font-warning">\
								'+ logibody.data[i].context +' </span>\
								<span>&nbsp; &nbsp;<a class="more-detail" data-dropdown="drop3" data-options="align : left" aria-expanded="false">更多》</a></span>\
								<ul id="drop3" class="f-dropdown" data-dropdown-content="">';
							continue;
						}
						if(i == logibody.data.length - 1) {
							middleHtm += '<li><a><span >'+ logibody.data[i].time +'</span> \
								<span">'+ logibody.data[i].context +' </span></li></a></ul>';
							continue;
						}
						middleHtm += '<li><a><span >'+ logibody.data[i].time +'</span> \
								<span">'+ logibody.data[i].context +' </span></a></li>';
					}
				}else{
					middleHtm = '<span  data-th-text="${order.logistics.createtime}==null?\"\":${#dates.format(order.logistics.createtime, \"yyyy-MM-dd HH:mm:ss\")}"> 2015-07-01 22:33:12 </span>\
					<span class="package-detail package-address-detail font-warning">\
					买家已发货 </span>';
				}
				var tailHtm = '</div></div>';
				
				$elem.append(headHtm + middleHtm + tailHtm);
			}
		});
	};
	
	/**
	 * 获取物流跟踪信息
	 * @param $elem 元素，用于存放跟踪信息的位置
	 * @param order 快递单号
	 * @param comId 公司编号
	 * @param comName 公司名称
	 * @param orderId 订单编号
	 */
	logistics.getLogisticsData = function( $elem, order, comId, comName, orderId){
		$elem.html();
		var data = {};
		if(order.length == 0 || comId.length == 0) return;
		data['order'] = order;
		data['comId'] = comId;
		$.ajax({
			url : initLogisticsUrl,
			data : data,
			type : "POST",
    		dataType : "json",
			success : function(logi){
				if(logi == undefined || logi=="") return;
				
				var headHtm = '<div class="logistics-con">\
			                	<div class="font-small">物流公司：' + comName + '</div>\
			                	<div class="font-small">运单号码：' + logi.showapi_res_body.mailNo + '</div>\
			                	<div class="font-small">订单编号：' + orderId + '</div>\
			                    <div class="position">\
                    				<ul>';
				var middleHtm = '';
				if(logi.showapi_res_body.msg == "" || logi.showapi_res_body.msg == undefined){
					var logibody = logi.showapi_res_body;
					for ( var i = 0; i < logibody.data.length; i++) {
						if(i == 0) {
							middleHtm += '<li class="light p-color">\
		                    	<span class="block">' + logibody.data[i].time + '</span>\
		                        <span class="block">' + logibody.data[i].context + '</span>\
							</li>';
							continue;
						}
						if(i == logibody.data.length - 1) {
							middleHtm += '<li class="no-border">\
		                    	<span class="block">' + logibody.data[i].time + '</span>\
		                        <span class="block">' + logibody.data[i].context + '</span>\
							</li>';
							continue;
						}
						middleHtm += '<li>\
				                    	<span class="block">' + logibody.data[i].time + '</span>\
				                        <span class="block">' + logibody.data[i].context + '</span>\
									</li>';
					}
				}else{
					middleHtm = '<li class="light">\
			                    	<span class="block">错误信息：</span>\
								</li>';
				}
				var tailHtm = 		'</ul>\
			                    </div>\
			                </div>';
				
				$elem.append(headHtm + middleHtm + tailHtm);
			}
		});
	};
	
	/**
	 * 获取公司列表，并执行相关操作
	 * @param callback 回调函数
	 */
	logistics.getCompanyListData = function(callback){
		$.ajax({
			url : getCompanyListUrl,
			type : "POST",
    		dataType : "json",
			success : function(comList){
				callback(comList);
			}
		});
	};
	
	logistics.initLogistics = function( order, comId){
		var data = {};
//		if(order.length == 0 || comId.length == 0) {
//			order = "710032690831";
//			comId = "yuantong";
////			return;
//		}
		data['order'] = order;
		data['comId'] = comId;
		
		$.ajax({
			url : initLogisticsUrl,
			type : "POST",
    		dataType : "json",
    		data : data,
			success : function(logi){
				if(logi == undefined || logi=="") return;
//				$elem = $('#logistics');
				var headHtm = '<div class="logistics-con">\
			                	<div class="font-small">物流公司：顺丰快递</div>\
			                	<div class="font-small">运单号码：' + logi.showapi_res_body.mailNo + '</div>\
			                	<div class="font-small">订单编号：LP986823657236</div>\
			                    <div class="position">\
                    				<ul>';
				var middleHtm = '';
				if(logi.showapi_res_body.msg != ""){
					var logibody = logi.showapi_res_body;
					for ( var i = 0; i < logibody.data.length; i++) {
						if(i == 0) {
							middleHtm += '<li class="light p-color">\
		                    	<span class="block">' + logibody.data[i].time + '</span>\
		                        <span class="block">' + logibody.data[i].context + '</span>\
							</li>';
							continue;
						}
						if(i == logibody.data.length - 1) {
							middleHtm += '<li class="no-border">\
		                    	<span class="block">' + logibody.data[i].time + '</span>\
		                        <span class="block">' + logibody.data[i].context + '</span>\
							</li>';
							continue;
						}
						middleHtm += '<li>\
				                    	<span class="block">' + logibody.data[i].time + '</span>\
				                        <span class="block">' + logibody.data[i].context + '</span>\
									</li>';
					}
				}else{
					middleHtm = '<li class="light">\
			                    	<span class="block">错误信息：</span>\
								</li>';
				}
				var tailHtm = 		'</ul>\
			                    </div>\
			                </div>';
				
				$elem.append(headHtm + middleHtm + tailHtm);
			}
		});
	};
	
	$(function(){
//		logistics.initLogistics( "", "");
		logistics.getLogisticsData( $('#logistics'), "710032690831", "yuantong", "圆通快递", "");
//		logistics.getCompanyListData(function(data){
//			
//		});
	});
})(jQuery, RJ.E);
