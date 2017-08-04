(function($, E){
	if ( ! E.buyer ) {
		E.buyer = {};
	}
	var myauction = E.buyer.myauction = {}; // 电商项目 我的拍卖模块 命名空间
	//拍卖记录
	myauction.record=function(){
		$("#auction-record").dataTablePage({
			url: E.Cfg.contextPath+"/buyer/myauction", // 请求地址			
			checkboxMode : false, // 是否出现多选框	
			columns: 
				[{
					data: "id",
					name: "宝贝名称",
					orderable: true,
					searchable: true,
					display: function(td, id, vo, rowIndex, colIndex) {
						$(td).html(''+vo.goods.goodsTitle+'');
					}						
				},{
					data: "id",
					name: "保证金",
					orderable: true,
					searchable: true,
					display: function(td, id, vo, rowIndex, colIndex) {
						$(td).html(''+vo.myBond.bondMonery+'');
					}					
				},{
					data: "shootingTime",
					name: "开拍时间",
					orderable: true,
					searchable: true
				},{
					data: "auctionQuantity",
					name: "竞拍数量",
					orderable: true,
					searchable: true					
				},{
					data: "accessQuantity",
					name: "获得数量",
					orderable: true,
					searchable: true					
				},{
					data: "currentPrice",
					name: "当前价格(元)",
					orderable: true,
					searchable: true					
				},{
					data: "myHighestPrice",
					name: "我的最高价(元)",
					orderable: true,
					searchable: true					
				},{
					data: "endTime",
					name: "剩余时间",
					orderable: true,
					searchable: true,
					display: function(td, id, vo, rowIndex, colIndex) {
						var time=((new Date(vo.endTime)-new Date())/(1000*60*60*24)).toFixed(2);
						if(time<0){
							time=0;
						}
						$(td).html(''+time+'天');
					}					
				}],
			columnOpers: {
				name: "状态",
				display: function(td, id, vo, rowIndex, colIndex) {
					if(vo.myBond.order.paymentTime!=null){
						$(td).html('<span class=""><i title="拍卖中"></i>拍卖中</span>');
					}else if(vo.myBond.order.doneTime!=null){
						$(td).html('<span class=""><i title="已退款"></i>已退款</span>');
					}else if(vo.myBond.order.rateTime!=null){
						$(td).html('<span class=""><i title="转订单"></i>转订单</span>');
					}else{			
						$(td).html('<span class=""><i title="未付款"></i>未付款</span>');						
					}
				}		
			}
		});
	}
	//保证金
	myauction.bond=function(){
		$("#auction-bond").dataTablePage({
			checkboxMode: false,
			/*		info: false,
			paging: false,*/
			url: E.Cfg.contextPath+"/buyer/auction/bond", // 请求地址
			columns: 
				[{
					data: "id",
					name: "宝贝名称",
					orderable: true,
					searchable: true,
					display: function(td, id, vo, rowIndex, colIndex) {
						$(td).html(''+vo.goods.goodsTitle+'');
					}
				},{
					data: "bondMonery",
					name: "金额(元)",
					orderable: true,
					searchable: true
				},{
					data: "submitTime",
					name: "锁定时间",
					orderable: true,
					searchable: true,
					display: function(td, id, vo, rowIndex, colIndex) {
						//对应订单付款时间
						if(vo.order.paymentTime!=null){
							$(td).html(''+vo.order.paymentTime+'');
						}
					}					
				},{
					data: "releaseTime",
					name: "释放时间",
					orderable: true,
					searchable: true,
					display: function(td, id, vo, rowIndex, colIndex) {
						//对应订单完成时间
						if(vo.order.doneTime!=null){
							$(td).html(''+vo.order.doneTime+'');
						}
					}											
				},{
					data: "transferTime",
					name: "转移时间",
					orderable: true,
					searchable: true	,
					display: function(td, id, vo, rowIndex, colIndex) {
						//对应订单评价时间
						if(vo.order.rateTime!=null){
							$(td).html(''+vo.order.rateTime+'');
						}
					}									
				}],
			columnOpers: {
				name: "状态",
				display: function(td, id, vo, rowIndex, colIndex) {
					if(vo.order.paymentTime!=null){
						$(td).html('<span class=""><i title="拍卖中"></i>拍卖中</span>');
					}else if(vo.order.doneTime!=null){
						$(td).html('<span class=""><i title="已释放"></i>已释放</span>');
					}else if(vo.order.rateTime!=null){
						$(td).html('<span class=""><i title="已完成"></i>已完成</span>');
					}else{			
						$(td).html('<span class=""><i title="未报名"></i>未报名</span>');						
					}
				}		
			}
		});
	}
   $(function(){
	   var hdkssj, hdjssj;
		$('#hdkssj').daterangepicker({
			format: 'YYYY-MM-DD HH:MM:SS',
			startDate: new Date(),
			endDate: new Date(),
			//minDate: '2015-01-01',
			//maxDate: '2100-01-10',
			showWeekNumbers: true,
			showDropdowns: true,
			timePicker12Hour: false,
			singleDatePicker: true,
			timePicker: true
		});
		$('#hdjssj').daterangepicker({
			format: 'YYYY-MM-DD HH:MM:SS',
			startDate: new Date(),
			endDate: new Date(),
			//minDate: '2015-01-01',
			//maxDate: '2100-01-10',
			showWeekNumbers: true,
			showDropdowns: true,
			timePicker12Hour: false,
			singleDatePicker: true,
			timePicker: true
		});
	    myauction.record();
	    myauction.bond();
		/*$("body").on('click','.record',function(e){
			$("#auction-bond").hide();
			$("#auction-record").show();
			myauction.record();		
		}).on('click','.bond',function(e){
			$("#auction-record").hide();
			$("#auction-bond").show();
			myauction.bond();
		})*/
	})
})(jQuery, RJ.E);