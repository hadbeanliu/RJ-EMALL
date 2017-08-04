(function($, E){
	var authentication = E.authentication = {}; //认证管理命名 

	var url = E.Cfg.contextPath;

	var saveUrlByUser =  url + "/buyer/authentication/savebyuser.json";
	var savecompanyUrl =  url + "/seller/authentication/v_save.json";
	var saveInfoUrl = url + "/seller/authentication/v_saveinfo.json";
	var saveserviceUrl =  url + "/seller/authentication/save_service.json";
	var getUserUrl = url + "/user/vo_getuser/";
	var getCompanyUrl =  url + "/seller/authentication/v_get/";
	var getVoUrl = function (url,voId) {//通过id获得单条数据
		if(voId === undefined) return "";
		return url + voId + ".json";
	};
	var saveVoUrl = url + "/buyer/authentication/v_update_user.json";
	//保存对各个认证的审核信息
	var saveExaminePersonUrl =  url + "/admin/authentication/examine/save_examine_person.json";
	var saveExamineCompanyUrl =  url + "/admin/authentication/examine/save_examine_company.json";
	var saveExamineExpertUrl =  url + "/admin/authentication/examine/save_examine_expert.json";
	var saveExamineServiceUrl =  url + "/admin/authentication/examine/save_examine_service.json";
	
	//获得错误字段数据
	var service_errorfield = url + "/seller/authentication/get_service_errorfield";
	var company_errorfield = url + "/seller/authentication/get_company_errorfield";
	var person_errorfield = url + "/buyer/authentication/get_person_errorfield";
	var expert_errorfield = url + "/buyer/authentication/get_expert_errorfield";
	
	var $form = $(".v_form");
	var $addForm = $(".v_add_form");
	
	setHidden = function(){
		//获得select值，根据值判断表单是否隐藏
		if($("#isResult").val() == "0"){
			$(".v_show_results").removeClass("hide");
			$("#results").val("审核失败");
		}else{
			$(".v_show_results").addClass("hide");
			$("#results").val("审核通过");
		}
	}
	
	authentication.setUserFormData = function($form){//获得表单数据从后台获得User对象值并放到表单上
		var id = $('#uid').val();
		if(id == undefined) id = null;
		/************ 初始化图片格式 ************/
		$('#head_photo').append('<img class="th" style="width:110px;height:110px;" src=""/>');
		$('#front_photo').append('<img class="th" style="width:220px;height:150px;" src=""/>');
		$('#back_photo').append('<img class="th" style="width:220px;height:150px;" src=""/>');
		$('#userCredentials_photo').append('<img class="th" style="width:220px;height:150px;" src=""/>');
		
		$.get(getUserUrl,function(vo){
			for(var prop in vo) {
				var field = $form.find('[name="' + prop + '"]');
				if (field && field.length > 0) {
					if (field[0].type === 'checkbox' || field[0].type === 'radio') {
						field.filter('[value="'+ vo[prop] +'"]').prop('checked', true);
					} else if(field[0].type === 'file'){//文件名的输出方式？
						field.val("");
					} else {
						field.val(vo[prop]);
					}
					if(field[0].type === 'hidden' && field[0].name != "id"){//照片的相关处理
						field.parent('div').find('.no-bullet').append('<div class="uploader uploaded">\
															<span class="bg-preview">\
																<img src="' + vo[prop] + '">\
															</span>\
															<span class="fa fa-times reset">\
															</span>\
														</div>');

						if(field.parent('div').find('span').hasClass('reset')){//如果有reset,隐藏添加功能。
//							$(this).parent('.uploader').remove();
							field.parent('div').find('.evaluate-uploaders').find('div.uploader:last').addClass('hide');
						}
						
						//绑定关闭图标操作
						field.parent('div').find('.reset').bind("click",function(){
							$(this).parent('.uploader').remove();
//							$(this).find('#'+ field[0].name +'').val('');
							//显示添加功能
							$(document).find('.evaluate-uploaders').each(function(){
								
								if($(this).find('div').find(".uploader.uploaded").length == 0){
									$(this).parents('div #data_modPic_con').find('.evaluate-uploaders').find('div.uploader:last').removeClass('hide');
								}
							})
							
								
						});
					}
//					if(field[0].name=="frontCard"){
//					}
//					if(field[0].name=="backCard"){
//					}
//					if(field[0].name=="userCredentials"){
//					}
						
				}
			}
			
			authentication.setDisabledIfValue(vo);
			if($('#person').length > 0){
				
				authentication.getErrorField(person_errorfield,$form);
			}else{
				
				authentication.getErrorField(expert_errorfield,$form);
			}
		},"json");
		
	}
	
	authentication.setDisabledIfValue = function(vo){//setUserFormData之后,判断个人认证信息是否存在数据，有则专家认证时不能修改数据
		for(var prop in vo) {
			var field = $('.person_info').find('[name="' + prop + '"]');
			if (field && field.length > 0) {
				if(field[0].value.length > 0) {
					
					field.prop('disabled', true);
					$('.person_info').find('.reset').remove();
				}
			}
			
		}
	}
	
	authentication.setCompanyFormData = function($form){//获得表单数据从后台获得Company对象值并放到表单上
		
		var id = $('#cid').val();
		/************* 初始化图片格式 *************/
		$('#licence_front_photo').append('<img class="th" style="width:220px;height:150px;" src=""/>');
		$('#licence_back_photo').append('<img class="th" style="width:220px;height:150px;" src=""/>');
		if(id == "") return;
		$.get(getVoUrl(getCompanyUrl,id),function(vo){
			if(vo == null) return;
			for(var prop in vo) {
				var field = $form.find('[name="' + prop + '"]');
				if (field && field.length > 0) {
					if (field[0].type === 'checkbox' || field[0].type === 'radio') {
						field.filter('[value="'+ vo[prop] +'"]').prop('checked', true);
					} else if(field[0].type === 'file'){//文件名的输出方式？
						field.val("");
					} else {
						field.val(vo[prop]);
					}
//					if(field[0].name=="companyContactAddress"){
//						E.areas.acccordToCodeMany(".address1",vo[prop]);
//					}
//					if(field[0].name=="companyRegisterAddress"){
//						E.areas.acccordToCodeMany(".address",vo[prop]);
//					}
					if(field[0].name=="licenceFrontcard"){//需判断是否错误
//						$('#fileList3').find('.no-bullet').append('<div class="uploader uploaded">\
//									<span class="bg-preview">\
//										<img src="'+ field[0].value +'">\
//									</span>\
//								</div>');
						field.val('');
					}
					if(field[0].name=="licenceBackcard"){
						field.val('');
					}
					if(field[0].name == "authenticationService"){//分解服务认证内容
						var str = vo[prop];
						if(str == null) str = "";
						var strArray = str.split("/");	
						for ( var i = 0; i < strArray.length; i++) {
							var obj = document.getElementsByName('checkbox');
							if(obj[i] == undefined) continue;
							if(obj[i].value == strArray[i]) {
								obj[i].checked = "checked";
							}else if(obj[i].value.length > 0){
								document.getElementById("s").value = strArray[i];
							}
						}
					}
				}
			}

			if($('#company')){
				authentication.getErrorField(company_errorfield,$form);
			}else{
				authentication.getErrorField(service_errorfield,$form);
			}
		},"json");
		
	}
	
	authentication.verificationForm = function($form){
		return RJ.E.util.verificationForm($form);
	}

	authentication.clearForm = function($form){
		if (Foundation && Foundation.libs && Foundation.libs.abide) {
    		Foundation.libs.abide.reset($form);
    	} else {
    		$(':input', $form).not(':button, :submit, :reset, :hidden').val('');
    	}
	}
	
	authentication.getFormdata = function($form){//获得表单数据
		var datas = {};
    	$form.find("[name]").each(function(){
    		var val = $(this).val();
    		if (this.type === 'checkbox' || this.type === 'radio') {
    			val = [];
    			$form.find("[name="+ this.name +"]:checked").each(function(){
    				val.push(this.value);
    			});
    			if (val.length === 1) {
    				val = val[0];
    			}
    		}
    		datas[this.name] = val;
    	});

    	return datas;
	}
	
	authentication.setExamineInfo = function($form){//获得表单数据
		var errorField = "";//错误字段
		var errorReason = "";//审核失败信息
		$('.field_form').find("[name]").each(function(){
			if($(this).val().length > 0){

				errorReason += $(this).val() + "/";
				errorField += this.name + ",";
			}
		});
		if(errorReason.length == 0){
			$addForm.find("[name=results]").val("审核通过");
		}else{
			$addForm.find("[name=results]").val("审核失败");
			$addForm.find("[name=errorReason]").val(errorReason.substr(0, errorReason.length-1));
		}
		if(errorField.length == 0){
			$addForm.find("[name=errorField]").val(errorField);
		}else{
			$addForm.find("[name=errorField]").val(errorField.substr(0, errorField.length-1));
		}
		
	}
	
	authentication.getErrorField = function(url,$form){//获得各个认证错误字段值，并在表单上显示错误信息
		$.ajax({
			url : url,
			type : "POST",
    		dataType : "json",
			success : function(examine){
				if(examine == "") return;
				if(examine.errorField == "" || examine.errorField==null) return;
				var errorField = examine.errorField;
				var errorReason = examine.errorReason;
				if(errorField.length > 0){
					var strArray = errorField.split(",");
					var strArray1 = errorReason.split("/");
					$form.find('[name]').each(function(){
						var $elem = $form.find('[name ='+this.name+']').parents('.per-in-ma-row1');
						if(this.name == "licenceStartPeriod") return;
						if(this.name == "file") return;
						if($form.find('[name ='+this.name+']').val() == "") return;
						for ( var i = 0; i < strArray.length; i++) {
							if(this.name == strArray[i]){
								if(this.name == 'licenceEndPeriod'){
									$elem.append('<span data-tooltip class="has-tip tip-right left fail" title="'+ strArray1[i] +'">\
			                                    <img class="" src="images/add-shop-2.png" alt=""/>\
			                                </span>\
									');
									continue;
								}
								if($elem.find('.fa-calendar').length > 0){
									$elem.append('<span data-tooltip class="has-tip tip-right left fail" title="'+ strArray1[i] +'">\
		                                    <img class="" src="/emall/tpl/dsww/001/images/add-shop-2.png" alt=""/>\
		                                </span>\
										');
									continue;
								}
								$elem.append('<div class="small-1 columns columns-1 mar-top-10 left fail">\
			                                <span data-tooltip class="has-tip tip-right left" title="'+ strArray1[i] +'">\
			                                    <img class="" src="/emall/tpl/dsww/001/images/add-shop-2.png" alt=""/>\
			                                </span>\
										</div>');
								
								$form.find('[name ='+this.name+']').prop('disabled', false);
							}
						}
						
						if(!$elem.find('div').hasClass('fail')){
							if(this.name == 'licenceEndPeriod'){
								$elem.find('div.left').append('<span data-tooltip class="has-tip tip-right left suc" title="通过">\
		                                    <img class="" src="/emall/tpl/dsww/001/images/add-shop-1.png" alt=""/>\
		                                </span>\
										');
							}
							if($elem.find('.fa-calendar').length > 0){
								$elem.find('div.left').append('<span data-tooltip class="has-tip tip-right left suc" title="通过">\
		                                    <img class="" src="/emall/tpl/dsww/001/images/add-shop-1.png" alt=""/>\
		                                </span>\
										');
							}
//							if($elem.find('.evaluate-uploaders').length > 0){
//								$elem.find('.evaluate-uploaders').append('<span data-tooltip class="has-tip tip-right left" title="通过">\
//	                                    <img class="" src="/emall/tpl/dsww/001/images/add-shop-1.png" alt=""/>\
//	                                </span>\
//									');
//							}
							
								
							$elem.append('<div class="small-1 columns columns-1 mar-top-10 left">\
									<span data-tooltip class="has-tip tip-right left suc" title="通过">\
									<img class="" src="/emall/tpl/dsww/001/images/add-shop-1.png" alt=""/>\
									</span>\
							</div>');
						}
					
					});
					
					$(document).foundation('tooltip', 'reflow');//回滚。重新绑定

				}
			}
		});
	}
	
	authentication.saveExamineForUser = function(urls){/* 添加审核 */
		$.ajax({
			type : "POST",
    		url : saveUrlByUser,
    		data : authentication.getFormdata($form),
    		success : function(){
    			location.href = urls;
    		}
		});
	}
	
	$(function(){
		$(document).foundation({//非公共部分，自定义验证方法
			abide : {
				validators : {
					getValues : function(el, required, parent) {
						var value1 = document.getElementById("s").value;
						var obj = document.getElementsByName('checkbox');
						//取到对象数组后，我们来循环检测它是不是被选中    
						var s = '';
						for ( var i = 0; obj.length > i; i++) {
							if (obj[i].checked) s += obj[i].value + '/'; //如果选中，将value添加到变量s中    
						}
						
						document.getElementById("authenticationService").value = s + el.value;
						var values = document.getElementById("authenticationService").value;
						var pattern = /^[^\/]*$/;
						if(!pattern.test(el.value)) return false;
						if(values == "") return false;
						else return true;
					},
				}
			}
		});
		
		/***************** 审核结果 *****************/
		var id = $("#examineId").val();
		var authentications = $("#authentications").val();
		/* 审核结果界面判断结果是成功还是失败 */
		var i = $("#errorReason").val();
		if(i == "f"){
			$(this).find(".v_failed").removeClass("hide");
		}
		
		$('.submitinfo').click(function(){//提交须知
			if(!$form.find('input[name=informance]').prop("checked")){
				$('#checkbox1').val("1");
				$(".v_info_fail").find(".v_show_info").text("只有同意该协议及授权才能进入下一步");
				$(".v_info_fail").addClass("show");
	    		setTimeout(function() {
	    			$(".v_info_fail").removeClass("show");
	    		}, 5000);
	    		return ;
			}
//			if ( ! authentication.verificationForm($form) ) { }
			$.ajax({
				type : "POST",
	    		url : saveInfoUrl,
	    		data : authentication.getFormdata($form),
	    		dataType : "json",
	    		success : function(res) {
	    			location.reload();//刷新
	    		},
			});
		});
		
		$('.v_reauth').click(function(){
			switch(authentications){
			case "1":
				location.href = url + "/seller/authentication/is_pass.htm?url=company";
				break;
			case "2":
				location.href = url + "/buyer/authentication/is_pass.htm?url=expert";		
				break;
			case "3":
				location.href = url + "/seller/authentication/is_pass.htm?url=service";
				break;
			case "4":
				location.href = url + "/buyer/authentication/is_pass.htm?url=person";
				break;
			}
		});
		
//		if($(".address") && $(".address").length != 0){
//			$(".address").initArea();//地址级联
//			$(".address1").initArea();//地址级联
//		}
		
		$('.timeEnd').daterangepicker({ 
			format: 'YYYY-MM-DD',// HH:mm:SS
			startDate: '2000-01-01',
			minDate: '1900-01-01',
			maxDate: new Date(),
			showWeekNumbers : true,
			showDropdowns: true,
			timePicker12Hour: false,
			singleDatePicker: true
		});
		
		/******** 绑定数据操作 ********/
		/* 添加模板 */
		$('.v_oper_add').click(function(e){
			authentication.clearForm($addForm);
	    	$addForm.foundation("reveal", "open");
	    	e.stopPropagation();
	    });
		
		/* 回到主页 */
		$('.v_return').click(function(e){
			location.href = url + "/buyer/authentication.htm";
	    });
		
		/* 用户提交审核 */
		$('.v_add_submit').click(function(e){//先保存用户企业表在提交审核信息
			if ( ! authentication.verificationForm($form) ) { return ;}

			//根据认证指数保存数据
			var auth = $form.find("[name=authentications]").val();
			if(auth == "2"){//添加专家信息
				$.ajax({
					type : "POST",
		    		url : saveVoUrl,
		    		data : authentication.getFormdata($form),
		    		dataType : "json",
		    		success : function(res) {
		    			authentication.saveExamineForUser(url + "/buyer/authentication/expert.htm");
		    		},
				});
			}else if (auth == "4"){//添加个人信息
				$.ajax({
					type : "POST",
		    		url : saveVoUrl,
		    		data : authentication.getFormdata($form),
		    		dataType : "json",
		    		success : function(res) {
		    			authentication.saveExamineForUser(url + "/buyer/authentication/person.htm");
		    		},
				});
			}else if (auth == "1"){//添加到企业表
				$.ajax({
					type : "POST",
		    		url : savecompanyUrl,
		    		data : authentication.getFormdata($form),
		    		dataType : "json",
		    		success : function(res) {
		    			authentication.saveExamineForUser(url + "/seller/authentication/company.htm");
		    		},
				});
			}else if(auth == "3"){//添加到企业表，主要是修改服务认证字段
				$.ajax({
					type : "POST",
		    		url : saveserviceUrl,
		    		data : authentication.getFormdata($form),
		    		dataType : "json",
		    		success : function(res) {
		    			authentication.saveExamineForUser(url + "/seller/authentication/service.htm");
		    		},
				});
			}
			
	    });
		
	    /* 管理员对个人认证添加审核表 */
		$('.submit_person').click(function(e){
			authentication.setExamineInfo();
			$.ajax({
				type : "POST",
	    		url : saveExaminePersonUrl,
	    		data : authentication.getFormdata($addForm),
	    		dataType : "json",
	    		success : function(res) {
	    			location.href = url + "/admin/authentication/examine/info.htm";
	    		},
			});
	    });
		
		/* 管理员对服务认证添加审核表 */
		$('.submit_service').click(function(e){
			authentication.setExamineInfo();
			$.ajax({
				type : "POST",
	    		url : saveExamineServiceUrl,
	    		data : authentication.getFormdata($addForm),
	    		dataType : "json",
	    		success : function(res) {
	    			location.href = url + "/admin/authentication/examine/info.htm";
	    		},
			});
	    });
		
		/* 管理员对专家认证添加审核表 */
		$('.submit_expert').click(function(e){
			authentication.setExamineInfo();
			$.ajax({
				type : "POST",
	    		url : saveExamineExpertUrl,
	    		data : authentication.getFormdata($addForm),
	    		dataType : "json",
	    		success : function(res) {
	    			location.href = url + "/admin/authentication/examine/info.htm";
	    		},
			});
	    });
		
		/* 管理员对企业认证添加审核表 */
		$('.submit_company').click(function(e){
			authentication.setExamineInfo();
			/* 添加审核 */
			$.ajax({
				type : "POST",
	    		url : saveExamineCompanyUrl,
	    		data : authentication.getFormdata($addForm),
	    		dataType : "json",
	    		success : function(res) {
	    			location.href = url + "/admin/authentication/examine/info.htm";
	    		},
			});
			
	    });

		/* 企业认证模块：个人开店和企业开店 */
		$('.v_store_panel').click(function(e){
			$('.v_store').toggleClass('hide');
	    });

		/* 重新认证专家 */
		$('.reauth_expert').click(function(e){
			location.href = url + "/buyer/authentication/ispass.htm?url=expert";
	    });
		
		/* 重新认证个人 */
		$('.reauth_person').click(function(e){
			location.href = url + "/buyer/authentication/ispass.htm?url=person";
	    });
		
		/* 重新认证企业 */
		$('.reauth_company').click(function(e){
			location.href = url + "/seller/authentication/ispass.htm?url=company";
	    });
		
		/* 重新认证服务 */
		$('.reauth_service').click(function(e){
			location.href = url + "/seller/authentication/ispass.htm?url=service";
	    });
		
		/*********** 绑定操作结束 **********/
		var $person = $('#person');
		var $company = $('#company');
		var $expert = $('#expert');
		var $service = $('#service');  
		var $lookCompany = $('#lookCompany'); 
		if($person.length > 0) {
			authentication.setUserFormData($form);
			E.util.bindFormVerificationByUrl($form, url + "/buyer/authentication/user_validator.json");
		}
		if($company.length > 0) {
			authentication.setCompanyFormData($form);
			E.util.bindFormVerificationByUrl($form, url + "/seller/authentication/company_validator.json");
		}
		if($expert.length > 0) {
			authentication.setUserFormData($form);
			E.util.bindFormVerificationByUrl($form, url + "/buyer/authentication/user_validator.json");
		}
		if($service.length > 0) {
			authentication.setCompanyFormData($form);
			E.util.bindFormVerificationByUrl($form, url + "/seller/authentication/company_validator.json");
		}
		if($lookCompany.length > 0) {
			$(".field_form").find("[id=cp]").val($("#cp").attr("value"));
			E.areas.acccordToCodeMany(".address",$("#address").attr("value"))
			E.areas.acccordToCodeMany(".address1",$("#address1").attr("value"))
		}

		var id = $("#id").val();
		var $results = $('#results');//审核结果
		//seller/store/add.htm创建店铺路径
		if ($results.length > 0){
			var status = $('#status').val();
			if(status == "0" || status == ""){//待审核
				$(".isprocess").removeClass("hide");
			}else if(status == "1"){//审核成功
				$(".issuccess").removeClass("hide");
			}else if(status == "-1"){//审核失败
				$(".isfailed").removeClass("hide");
			}else if(status == "-10"){
				$(".isprocessCompany").removeClass("hide");
			}else if(status == "-11"){
				$(".isprocessExpert").removeClass("hide");
			}else if(status == "-111"){
				$(".isprocessCompany").removeClass("hide");
			}else if(status == "error-expert"){
				$(".error-expert").removeClass("hide");
			}else if(status == "error-company"){
				$(".error-company").removeClass("hide");
				$('#frame_main_r2_col1').addClass("hide");
			}
		}
		
		var $info = $("#info");
		if ($info.length > 0 && $info.dataTablePage) {
			$.get(url + "/admin/authentication/examine/v_page.json", function(data){
				//console.info(data.data[0]);
				$.ajax({
					   type: "POST",
			           url: "http://192.168.18.54/dform?act=saveSubmitValues&categoryCode=examine",
					   data: data.data[0],//只能传一条数据？
					   success: function(msg){
						   
					   }
				});
			});
			$('.timeEnd').daterangepicker({ 
				format: 'YYYY-MM-DD',// HH:mm:SS
				startDate: '2015-07-01',
				minDate: '1900-01-01',
				maxDate: '2099-12-31',
				showWeekNumbers : true,
				showDropdowns: true,
				timePicker12Hour: false,
				singleDatePicker: true
			});
			$info.dataTablePage({
				url: url + "/admin/authentication/examine", // 请求地址
				order: [[ 4, "desc" ]],
				columns: [{
					data: "description",
					name: "描述",
					orderable: true,
					searchable: true
				}, {
					data: "status",
					name: "审核状态",
					orderable: true,
					searchable: true,
					display: function(td, value, vo, rowIndex, colIndex) {
						if(value == "1"){
							$(td).html('已审核');
						}else{
							$(td).html('未审核');
						}
					}
		 		}, {
		 			data: "authentications",
		 			name: "认证名称",
		 			orderable: false,
		 			searchable: true,
		 			display: function(td, value, vo, rowIndex, colIndex) {
						if(vo.authentications == "1") $(td).html('企业认证');
						if(vo.authentications == "2") $(td).html('专家认证');
						if(vo.authentications == "3") $(td).html('服务认证');
						if(vo.authentications == "4") $(td).html('个人认证');
					}
				}, {
		 			data: "startTime",
		 			name: "提交审核时间",
		 		    orderable: true, 
		 			searchable: true,
		 			display: function(td, value, vo, rowIndex, colIndex) {
//						if(value == "2015-07-15 13:15:43") $(td).html('2015-07-14 13:15:43');
					}
//		 		}, {
//					data: "results",
//					name: "审核结果（测试）",
//					orderable: true,
//					searchable: true
//				}, {
//					data: "errorReason",
//					name: "审核失败原因",
//					orderable: true,
//					searchable: true
				}],
				columnOpers: {
					name: "操作",
					display: function(td, id, vo, rowIndex, colIndex) {
						if(vo.status == "1"){
							//'<div class="td_more"><span class="fa-stack v_oper_more"><i class="fa fa-circle"></i><i class="fa fa-ellipsis-h" title="更多"></i></span>'+
							//'<div class="more v_oper_more_panel hide"><span><a href="#" class="v_oper_delete">删除</a></span></div>'
							$(td).html('<div class="td_btn"><span class="button default v_oper_delete"><i title="删除"></i>删除</span></div>').addClass('td_btn_wrap');
						} else {
							switch(vo.authentications){
							case "1": $(td).html('<div class="td_more"><span class="fa-stack v_oper_more"><i class="fa fa-circle"></i><i class="fa fa-ellipsis-h" title="更多"></i></span>'+
									   '<div class="more v_oper_more_panel hide"><span><a href="#" class="v_oper_delete">删除</a></span><span>|</span><span><a href="'+url+'/admin/authentication/examine/lookCompany.htm?id='+vo.id+'">审核</a></span></div>');
							break;
							case "2": $(td).html('<div class="td_more"><span class="fa-stack v_oper_more"><i class="fa fa-circle"></i><i class="fa fa-ellipsis-h" title="更多"></i></span>'+
									   '<div class="more v_oper_more_panel hide"><span><a href="#" class="v_oper_delete">删除</a></span><span>|</span><span><a href="'+url+'/admin/authentication/examine/lookExpert.htm?id='+vo.id+'">审核</a></span></div>');
							break;
							case "3": $(td).html('<div class="td_more"><span class="fa-stack v_oper_more"><i class="fa fa-circle"></i><i class="fa fa-ellipsis-h" title="更多"></i></span>'+
									   '<div class="more v_oper_more_panel hide"><span><a href="#" class="v_oper_delete">删除</a></span><span>|</span><span><a href="'+url+'/admin/authentication/examine/lookService.htm?id='+vo.id+'">审核</a></span></div>');
							break;
							case "4": $(td).html('<div class="td_more"><span class="fa-stack v_oper_more"><i class="fa fa-circle"></i><i class="fa fa-ellipsis-h" title="更多"></i></span>'+
									   '<div class="more v_oper_more_panel hide"><span><a href="#" class="v_oper_delete">删除</a></span><span>|</span><span><a href="'+url+'/admin/authentication/examine/lookPerson.htm?id='+vo.id+'">审核</a></span></div>');
							break;
							}
							
						}
					}
				},
				alertInto: "#alert_into" // 提醒实现位置
			});
			
		}

		var $examine = $("#examine");
		if ($examine.length > 0 && $examine.dataTablePage) {
			var i = $('#authentication').val();
			var examineUrl = url;
			switch (i) {
			case "1":
				examineUrl += "/admin/authentication/examine/company";
				break;
			case "2":
				examineUrl += "/admin/authentication/examine/expert";
				break;
			case "3":
				examineUrl += "/admin/authentication/examine/service";
				break;
			case "4":
				examineUrl += "/admin/authentication/examine/person";
				break;
			default:
				break;
			}
			$examine.dataTablePage({
				url: examineUrl, // 请求地址
				checkboxMode : false,
				columns: [{
//					data: "description",
//					name: "描述",
//					orderable: true,
//					searchable: true
//				}, {
		 			data: "startTime",
		 			name: "提交审核时间",
				}, {
		 			data: "endTime",
		 			name: "操作时间",
		 			display: function(td, value, vo, rowIndex, colIndex) {
		 				if(value == null) $(td).html('---');
					}
		 		}, {
					data: "results",
					name: "结果",
					display: function(td, value, vo, rowIndex, colIndex) {
		 				if(value == null) $(td).html('---');
					}
				}],
			});
			
		}
		
	});
})(jQuery, RJ.E);
