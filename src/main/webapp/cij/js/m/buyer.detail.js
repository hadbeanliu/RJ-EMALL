(function($, E){
	var orderdetail = E.orderdetail = {};
	/**
	 * 调用倒计时函数
	 * @param $o 起始时间的标签
	 * @param n 天数
	 */
	orderdetail.countdown=function($o,n){
		var begindate = $o.val();
		if(begindate === "" || begindate === undefined){
			return;
		}
		begindate = new Date(Date.parse(begindate));
		var enddate = new Date(begindate.getTime()+n*24*60*60*1000);
		console.info(begindate);
		console.info(enddate);
		var now = new Date();
		
		var date3=enddate.getTime()-now.getTime();  //时间差的毫秒数

		//计算出相差天数
		var days=Math.floor(date3/(24*3600*1000));
		 
		//计算出小时数

		var leave1=date3%(24*3600*1000);    //计算天数后剩余的毫秒数
		var hours=Math.floor(leave1/(3600*1000));
		//计算相差分钟数
		var leave2=leave1%(3600*1000);        //计算小时数后剩余的毫秒数
		var minutes=Math.floor(leave2/(60*1000));
		//计算相差秒数
		var leave3=leave2%(60*1000);      //计算分钟数后剩余的毫秒数
		var seconds=Math.round(leave3/1000);
		console.info(" 相差 "+days+"天 "+hours+"小时 "+minutes+" 分钟"+seconds+" 秒");
	    var countTo = enddate.valueOf();
	    
	    $('.timer').countdown(countTo, function(event) {
	        var $this = $(this);
	        switch(event.type) {
	            case "seconds":
	            case "minutes":
	            case "hours":
	            case "days":
	            case "weeks":
	            case "daysLeft":
	                $this.find('span.'+event.type).html(event.value);
	                break;
	            case "finished":
	                $this.hide();
	                break;
	        }
	    });
	};
	
	$(function() {
		var status=$('#orderStatus').val();
		var days=7;
		if(status==='1'){
			days=3;
		}
		E.orderdetail.countdown($('#createTime'),days);
		if(status==='4' || status==='3'){
			var $logdiv=$('#v_logInfo');
			$logdiv.html();
			var mailno=$('#mailno').val(),
			comcode=$('#comcode').val(),
			comName="",
			orderNo=$('#orderNo').val();
			E.logistics.getCompanyListData(function(data){
				var list=data.showapi_res_body;
				for(var i=0;list.expressList.length-1;i++){
					var simp=list.expressList[i].simpleName;
					if(comcode==simp){
						comName=list.expressList[i].expName;
						E.logistics.anotherLogisticsData($logdiv,mailno,comcode,comName,orderNo);
						break;
					}
				}
			});
		}
//		if(status==='3'){
//			var $logdiv=$('#v_logInfo');
//			var comcode=$('#comcode').val();
//			E.logistics.getCompanyListData(function(data){
//				var list=data.showapi_res_body;
//				for(var i=0;list.expressList.length-1;i++){
//					var simp=list.expressList[i].simpleName;
//					if(comcode==simp){
//						$logdiv.find('.logcode').text==list.expressList[i].expName;
//						break;
//					}
//				}
//			});
//		}
	});
})(jQuery, RJ.E);