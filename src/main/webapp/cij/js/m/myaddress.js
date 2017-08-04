(function($, E){
	
	var myaddress = E.myaddress = {}; // 电商项目 个人用户收货地址管理模块 命名空间
	
	myaddress.addressId="";
	myaddress.addressList=""; 
	var Addr={};//对象，防止全局变量冲突
	userId = $("#userId").val();

	Addr.defaultHtml=function(value,type){
		var returnHtml='';
		var firstHtml='<label class="label-radio square top-addr" for="addr-default" data-id="'+value.id+'">'
	            +'<input class="radio" type="radio" name="address" id="addr-default" checked="checked">'
	            +'<i class="icon-check"></i>'
	            +'<span class="name">'+value.receiver+'</span>'
	            +'<span class="addr">'+value.areas+' '+value.address+'</span>'
	            +'<span class="tel">'+value.mobiletel+'</span></label>'
	            +'<div data-id="'+value.id+'" class="action"><a href="#" class="del">删除</a><a href="#" class="edit" data-reveal-id="consignees_modal">编辑</a>';
	            +'<a href="#" class="set unique default">默认地址</a></div>';
	    if(type==1){
	    	returnHtml=firstHtml+'<a href="#" class="set unique default">默认地址</a></div>';
	    }else{
	    	returnHtml=firstHtml+'<a href="#" class="set">设为默认地址</a></div>'
	    }
		return returnHtml;
	}
	
	Addr.html=function(value,type){
		var html = '';
		if(type==1){
			html='<li class="address top-addr clearfix" data-id="'+value.id+'">'
            +'<label class="label-radio square" for="addr1">'
            +'<input class="radio" type="radio" name="address" id="addr1">'
            +'<i class="icon-check"></i>'
            +'<span class="name">'+value.receiver+'</span>'
            +'<span class="addr">'+value.areas+' '+value.address+'</span>'
            +'<span class="tel">'+value.mobiletel+'</span></label>'
            +'<div data-id="'+value.id+'" class="action">'
            +'<a href="#" class="del">删除</a><a href="#" class="edit" data-reveal-id="consignees_modal">编辑</a>';
		}else{
			html='<li class="address top-addr clearfix" data-id="'+value.id+'">'
            +'<label class="label-radio square" for="addr1">'
            +'<input class="radio" type="radio" name="address" id="addr1">'
            +'<i class="icon-check"></i>'
            +'<span class="name">'+value.receiver+'</span>'
            +'<span class="addr">'+value.areas+' '+value.address+'</span>'
            +'<span class="tel">'+value.mobiletel+'</span></label>'
            +'<div data-id="'+value.id+'"class="action">'
            +'<a href="#" class="del">删除</a><a href="#" class="edit" data-reveal-id="consignees_modal">编辑</a><a href="#" class="set">设为默认地址</a>';
		}
		 
		return html;
	}
	
     myaddress.getUserData = function(form) {
    	var datas = {};
    	form.find("[name]").each(function(){
    		var val = $(this).val();
    		if (this.type === 'checkbox') {
    			val = [];
    			form.find("[name="+ this.name +"]:checked").each(function(){
    				val.push(this.value);
    			});
    		}
    		datas[this.name] = val;
    	});
    	
    	if ( ! datas.id ) {
    		delete datas['id'];
    	}
    	return datas;
    }
     myaddress.addrInit = function(page){
    	$.ajax({
			url:E.Cfg.contextPath+"/user/address/vo_getUserAddress.json",
			dataType:"JSON",
			type : "POST",
			success: function(data) {
				$('.address.default-addr').html("");
				$('.address-box').html("");
				myaddress.addressList=data.length;
				if(data.length>0){
					var html="";
					var defaultHtml="";
					var isexitdef=false;
					 for (var i = data.length - 1; i >= 0; i--) {
						 if(data[i].isDefault=="1"){
							 isexitdef=true;
							 break;
						 }
					 }
					 
					if(isexitdef){
						 for (var i = data.length-1; i >= 0; i--) {
							 if(data[i].isDefault=="1"){
								 $('#addressId').val(data[i].id);
								 defaultHtml=defaultHtml+Addr.defaultHtml(data[i],1);
								 $('.address.default-addr').html("").append(defaultHtml);
								 $("#addressId").trigger("changeaddress",data[i].id);
							 }else{
								 html=html+Addr.html(data[i],0);
								 $('.address-box').html("").append(html);
							 }
						 }
					}else{
						var isfirst = true;
						
						 for (var i = 0 ; i <data.length ; i++) {
							 if(data.length=="1"){
								 $('#addressId').val(data[i].id);
								 defaultHtml=defaultHtml+Addr.defaultHtml(data[i],0);
								 $('.address.default-addr').html("").append(defaultHtml);
								 $("#addressId").trigger("changeaddress",data[i].id);
							 }else{
								 if(isfirst){
									 $('#addressId').val(data[0].id);
									 defaultHtml=defaultHtml+Addr.defaultHtml(data[0],0);
									 $('.address.default-addr').html("").append(defaultHtml);
									 $("#addressId").trigger("changeaddress",data[i].id);
									 isfirst=false;
								 }
								 html=html+Addr.html(data[i+1],0);
								 $('.address-box').html("").append(html);
							 }
							 
						 }
					}
					 
				}
			},
			error: function(error) {
				alert(error);
			}
		})
    }
     
    $(function(){
    	var $form = $(".form");
    	myaddress.addrInit();
		url = "/emall/user/address/address_validate.json";
		RJ.E.util.bindFormVerificationByUrl($form,url);
    	
    	$('#addr-cancel-btn').click(function(){
    		$('#consignees_modal').foundation('reveal', 'close');
    		 $("input[type=reset]").trigger("click");
    	});
    	
    	//监听open事件
    	$(document).on('open.fndtn.reveal', '#consignees_modal', function () {
    		if(myaddress.addressList>=20){
				alert("抱歉，地址最多创建20条！");
			}
	    	$("input[type=reset]").trigger("click");
//	    	$('.area-select').initArea();
//			E.selectareas.init($('#consignees_modal'));
			$('#consignees_modal').find(".city-province").val("");
			$('#consignees_modal').find(".city-city").empty();
			$("<option>").text("城市").val("").appendTo($('#consignees_modal').find(".city-city"));
			$('#consignees_modal').find(".city-district").empty();
			$("<option>").text("区县").val("").appendTo($('#consignees_modal').find(".city-district"));
    		});
    	//城市3级联动
//		$('.area-select').initArea();
		E.selectareas.init($('#consignees_modal'));
    	
		
		//编辑地址取该地址的值
    	$('body').on("click",".edit",function(){
    		var addressId=$(this).parents('.action').attr("data-id");
    		myaddress.addressId=addressId;
    		$.ajax({
    			url:E.Cfg.contextPath+"/user/address/vo_getAddress.json",
    			dataType:"JSON",
    			type : "POST",
    			data : {"addressId" : addressId},
    			success: function(data) {
    				$(".receiver").val(data.receiver);
    				$(".detail").val(data.address);
    				$(".telephone").val(data.mobiletel);
    				$(".phone").val(data.phone);
    				$(".isDefault").val(data.isDefault);
//    				E.areas.acccordToCode(data.district);
    				E.selectareas.acccordToCode($('#consignees_modal'),data.district);
    				
    			},
    			error: function(error) {
    				alert(error);
    			}
    		})
    	});
    	
    	//删除地址
    	$('body').on("click",".del",function(){
    		var addressId=$(this).parents('.action').attr("data-id");
    		var page=$(this);
    		$.ajax({
    			url:E.Cfg.contextPath+"/user/address/v_del/"+addressId+".json",
    			//url:"/emall/user/address/"+userId+"/vo_setIsDefault.json",
    			dataType:"JSON",
    			type : "POST",
    			data : {"addressId" : addressId},
    			success: function(data) {
    				myaddress.addrInit(page);
    			},
    			error: function(error) {
    				alert(error);
    			}
    		})
    	});
    	
		  //选择收货地址
		  $('.selectAddr').on('click','.label-radio',function(e){
			  
			  var inputID=$(this).parents(".top-addr").attr("data-id");
			  if(typeof(inputID)=="undefined"){
				  inputID=$(this).attr("data-id");
			  }
			  $("#addressId").val(inputID);
			  $("#addressId").trigger("changeaddress", inputID);
			  e.stopPropagation();
			  e.preventDefault();
			  var $defaultAddr = $('.selectAddr').find('.default-addr'),
			      name = $defaultAddr.find('.name').text(),
			      addr = $defaultAddr.find('.addr').text(),
				  tel = $defaultAddr.find('.tel').text(),
				  //旧的ID，原先最上面的
			  	  oldDataId = $defaultAddr.find('.top-addr').attr("data-id"),
			  	  oldType="0";
			  
		      $defaultAddr.find('.name').text($(this).find('.name').text());
			  $defaultAddr.find('.addr').text($(this).find('.addr').text());
			  $defaultAddr.find('.tel').text($(this).find('.tel').text());
			  if( $defaultAddr.find('.set').hasClass('default') ) {
				  oldType="1";
			  }
			  //新的 ID  要替换第一行的
			  var newDataId=$(this).parents('.top-addr').attr("data-id"),
			      newType="0";
			  if(typeof(newDataId)=="undefined"){
				  newDataId=$(this).attr("data-id");
			  }
			  if($(this).parents('.top-addr').find('.set').hasClass('default')){
				  newType="1";
			  }
			  
			  $defaultAddr.find('.top-addr').attr("data-id",newDataId);
			  $defaultAddr.find('.action').attr("data-id",newDataId);
			  if(newType=="1"){
				  $defaultAddr.find('.set').addClass('default').text('默认地址'); 
			  }else{
				  $defaultAddr.find('.set').removeClass('default').text('设为默认地址');
			  }
			  
			  $(this).find('.radio').prop('checked',false);
			  $('.selectAddr').find('.default-addr').find('.radio').prop('checked',true);
			  
			  if(document.body.clientWidth < 640){
				  $(this).find('.radio').prop('checked',true);
				  $('.header, .detail-list, .invoice, .settleA-header, .coupon, .submit, .footer').show();
				  $('.selectAddr').find('.address-box').removeClass('show').css('position','fixed');
				  
			  }else{
				  $(this).find('.name').text(name);
				  $(this).find('.addr').text(addr);
				  $(this).find('.tel').text(tel);
				  var $topAddr=$(this).parents('.top-addr');
				  if($topAddr.length=="0"){
					  $topAddr=$(this);
				  }
				  $topAddr.attr("data-id",oldDataId);
				  if(oldType=="1"){
					  $(this).parent().find('.set').addClass('default').text('默认地址');  
				  }else{
					  $(this).parent().find('.set').removeClass('default').text('设为默认地址');
				  }
				  $(this).parents('.top-addr').find('.action').attr("data-id",oldDataId);
				  
			  
				  $('.selectAddr').find('.address-box').slideUp(200);
			  }
		  }); 
    	
    	//设置默认地址
		$('body').on('click','.selectAddr .address .set',function(e){
		  var addressId=$(this).parents('.action').attr("data-id");
		  $.ajax({
    			url:E.Cfg.contextPath+"/user/address/vo_setIsDefault.json",
    			dataType:"JSON",
    			type : "POST",
    			data : {"addressId" : addressId},
    			success: function(data) {
    				if(JSON.stringify(data)=="true"){
    					alert('设置成功');
    				}else{
    					alert('设置不成功');
    				}
    			},
    			error: function(error) {
    				alert(error);
    			}
    		})
		  e.preventDefault();
		  e.stopPropagation();
		  $('.selectAddr').find('.address').find('.set').removeClass('default').text('设为默认地址');
		  $(this).addClass('default').text('默认地址');
		});
    	
		//新增收货地址
		$('body').on("click","#addr-save-btn",function(){
			if(! E.util.verificationForm($form)) return;
			var addressId=myaddress.addressId;
			var data = myaddress.getUserData($form);
			if(addressId!=""&&typeof(addressId)!="undefined"){
				 data['id']=addressId;
			}
    		$.ajax({
    			url:E.Cfg.contextPath+"/user/address/vo_saveAddress.json",
    			dataType:"JSON",
    			type : "POST",
    			data : data,
    			success: function(data) {
    					 $("input[type=reset]").trigger("click");
    					 $('#consignees_modal').foundation('reveal', 'close');
    					 myaddress.addrInit();
    			},
    			error: function(error) {
    				alert(error);
    			}
    		})
		});
		$('#add-address-btn').click(function(){
			myaddress.addressId="";
		});
		//打开新增收货地址
			$('.selectAddr').find('.address-box').find('li.add').on('click','a',function(e){
			$('.selectAddr').find('.address-box').hide();
			$('.consignees.reveal-modal').addClass('show');
			setTimeout("$('.consignees.reveal-modal').css('position','absolute')",500);
			});
			
		//打开编辑地址
			$('.selectAddr').find('.address').on('click','.edit',function(e){
				//myaddress.addressId=$(this).parents('.action').attr("data-id");
				e.preventDefault();  
				$('.selectAddr').find('.address-box').hide();
				$('.consignees.reveal-modal').addClass('show');
				setTimeout("$('.consignees.reveal-modal').css('position','absolute')",500);
			});
		//保存地址
			$('.consignees.reveal-modal').find('.confirm').on('click','.button',function(e){
				e.preventDefault();
				$('.selectAddr').find('.address-box').show();
				$(this).parent().parent().removeClass('show').css('position','fixed');
			});
			//返回到选择地址
			$('.consignees.reveal-modal').find('h3').on('click','.fa',function(e){
				e.preventDefault();
				$('.selectAddr').find('.address-box').show();
				$(this).parent().parent().removeClass('show').css('position','fixed');
			});
			
			//展开所有地址大屏
			if(document.body.clientWidth > 640){
				$('.selectAddr').find('.more-addr').on('click','.mod',function(e){
				  e.preventDefault();
				  e.stopPropagation();
				  $(this).parents('.selectAddr').find('.address-box').slideToggle(200);
				});
			}
			
			//小屏下展开所有地址
//			$('.selectAddr').find('.more-addr').on('click','.mod',function(e){
//				e.preventDefault();
//				$('.header, .detail-list, .invoice, .settleA-header, .coupon, .submit, .footer').hide();
//				$(this).parents('.selectAddr').find('.address-box').addClass('show');
//				setTimeout("$('.selectAddr').find('.address-box').css('position','absolute')",500);
//			});
		
    });
	
})(jQuery, RJ.E);