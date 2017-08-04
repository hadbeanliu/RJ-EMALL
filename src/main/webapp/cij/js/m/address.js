(function($, E){
	var address = E.address = {}; // 电商项目 用户模块 命名空间
	
	userId = $("#userId").val();
		
	$("#personaddress").dataTablePage({
		url: E.Cfg.contextPath+"/user/address", // 请求地址
		columns: [{
			data: "address",
			name: "收货地址",
			orderable: true,
			searchable: true,
			display: function (td, value, vo, rowIndex, colIndex) {
				$(td).html('<a class="editAddress" addressId="'+vo.id+'" >'+vo.areas+value+'</a>');
			}
		}, {
			data: "receiver",
			name: "收货人",
			orderable: false,
			searchable: true
			
		}, {
			data: "mobiletel",
			name: "手机号码",			
			orderable: true,
			searchable: true
		}, {
			data: "phone",
			name: "固定电话",			
			orderable: true,
			searchable: true
		}, {
			data: "zipcode",
			name: "邮政编码",			
			orderable: true,
			searchable: true
		}, {
			data: "isDefault",
			name: "设置默认地址",			
			orderable: true,
			searchable: true,
			display: function (td, value, vo, rowIndex, colIndex) {
				if(value=="1"){
					$(td).html('默认收货地址');
				}else{
					$(td).html(' <div class="v_oper_refresh"><a class="setIsDefault" addressId="'+vo.id+'");">设为默认收货地址</a></div>');
				}
			}
		}],
		alertInto: "#alert_into" // 提醒实现位置
	});
	
	
	address.setIsDefault = function(addressId){
		$.ajax({
			url:E.Cfg.contextPath+"/user/address/vo_setIsDefault.json",
			dataType:"JSON",
			type : "POST",
			data : {"addressId" : addressId},
			success: function(data) {
				if(JSON.stringify(data)=="true"){
					//alert('设置成功');
				}else{
					alert('设置不成功');
				}
			},
			error: function(error) {
				alert(error);
			}
		})
	}
	
	address.getUserAddress = function(addressId){
		
		$.ajax({
			url:E.Cfg.contextPath+"/user/address/vo_getAddress.json",
			dataType:"JSON",
			type : "POST",
			data : {"addressId" : addressId},
			success: function(data) {
				$(".receiver").val(data.receiver);
				$(".address").val(data.address);
				$(".mobiletel").val(data.mobiletel);
				$(".phone").val(data.phone);
				$(".zipcode").val(data.zipcode);
//				$(".city-province").val(data.province);
//				$(".city-city").val(data.city);
//				$(".city-district").val(data.district);
//				E.areas.acccordToCode(data.district);
				E.selectareas.acccordToCode($('#edit-address'),data.district);
			},
			error: function(error) {
				alert(error);
			}
		})
	}

	
	$(function(){ // 绑定操作
		$('body').on("click",".setIsDefault",function(){
			var addressId=$(this).attr("addressId");
			address.setIsDefault(addressId);
		});
		
		$('body').on("click",".editAddress",function(){
			var addressId=$(this).attr("addressId");
			address.getUserAddress(addressId);
			$(".v_oper_submit_and_edit_next").hide();
			$('[name=id]').val(addressId);
			$("#edit-address").foundation("reveal", "open");
		});

    	//城市3级联动
//		$('#personaddress').initArea();
		E.selectareas.init($('#personaddress'));
		
		$form = $("#form");
		url = "/emall/user/user_validate.json";
		RJ.E.util.bindFormVerificationByUrl($form,url);
		
	});

})(jQuery, RJ.E);