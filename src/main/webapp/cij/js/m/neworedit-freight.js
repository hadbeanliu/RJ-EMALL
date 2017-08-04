(function($, E){
	var ftemp = E.freight = {}; 

	var defaultOptions = {};
	var pageInfo = {};

	var $FreightPage = $('#commodity_post_page');
	var $form = $(".v_form");
	var $operpost=$('.v_oper_post');
	var $operexpress=$('.v_oper_express');
	var $operems=$('.v_oper_ems');
	var $datapost=$('.v_data_post');
	var $dataexpress=$('.v_data_express');
	var $dataems=$('.v_data_ems');
	var $trpost=$('.tr_post');
	var $trexpress=$('.tr_express');
	var $trems=$('.tr_ems');
	
	var saveVoUrl = E.Cfg.contextPath + "/seller/freight/addsave.json";
	var updateVoUrl = E.Cfg.contextPath + "/seller/freight/updatesave.json";
	
	$.extend(defaultOptions, pageInfo); /* 加载服务器配置 */
	
	ftemp.getFreightForm = function($form,idColumn, exId){
		var i=-1;
		var datas = {};
    	$form.find("[name]").each(function(){
    		var val = $(this).val();
    		if ((this.type === 'checkbox' && this.name != "serviceType") || this.type === 'radio') {
    			val = [];
    			$form.find("[name="+ this.name +"]:checked").each(function(){
    				val.push(this.value);
    			});
    			if (val.length === 1) {
    				val = val[0];
    			}
    		}
    		if(this.name != "name" &&  this.name != "id" && this.name != "status" && this.name != "remark"){
    			if(this.name === "serviceType"){
    				i++;
    			}
    			if(this.name === "serviceType"){
    				if($(this).is(':checked')){
    					datas["carryMode["+i+"]."+this.name] = val;
    				}else{
    					datas["carryMode["+i+"]."+this.name] = "";
    				}
    			}else if(this.name === "carryId"){
    				datas["carryMode["+i+"].id"] = val;
    			}else{
    				datas["carryMode["+i+"]."+this.name] = val;
    			}
    		}else {
    			datas[this.name] = val;
			}
    	});
    	
    	if ( !exId && idColumn && ! datas[idColumn.data] ) {
    		delete datas[idColumn.data];
    	}
    	return datas;
	};
	
	ftemp.editForm = function ($form, url, call) { /* 根据url获取数据并填充到$form中 */
	$.get(url, null, function(u){
		for(var prop in u) {
			if(prop != "carryMode"){
				var field = $form.find('[name="' + prop + '"]');
				if (field && field.length > 0) {
					if (field[0].type === 'checkbox' || field[0].type === 'radio') {
						field.filter('[value="'+ u[prop] +'"]').prop('checked', true);
					} else {
						field.val(u[prop]);
					}
				}
			}else {
				for(var cprop in u[prop]){
					for(var car in u[prop][cprop]){
						var field = $form.find('[id="' + u[prop][cprop]["serviceType"].toLowerCase()+"_"+car + '"]');
						if(u[prop][cprop]["isDefault"] === "1"){
//							field = $form.find('[id="' + u[prop][cprop]["serviceType"].toLowerCase()+'_'+car + '_'+(i-1)+ '"]');
							field = $form.find('[id="' + u[prop][cprop]["serviceType"].toLowerCase()+'_'+car + '_'+ '1"]');
						}
						if (field && field.length > 0) {
							if (field[0].type === 'checkbox' || field[0].type === 'radio') {
								field.filter('[value="'+ u[prop][cprop][car] +'"]').prop('checked', true);
							} else {
								field.val(u[prop][cprop][car]);
							}
						}
					}
				}
			}
		}
		if (call) {
			call(u);
		}
	}, 'json');
};
	//显示信息
    ftemp.errorShow=function(){
		if(!$operpost.is(':checked') && !$operexpress.is(':checked') && !$operems.is(':checked')){
			$('.errorMsg').css('display','');
		}else{
			$('.errorMsg').css('display','none');
		}
    };
    //选框改变影响tr的改变
	ftemp.operChange=function($oper,$tr){
		if($oper.is(':checked')){
			$tr.css('display','');
			$tr.find('[name="serviceType"]').prop('checked',true);
			$tr.find('[type="text"]').removeAttr("disabled");
			ftemp.errorShow();
		}else{
			$tr.css('display','none');
			$tr.find('[name="serviceType"]').prop('checked',false);
			ftemp.errorShow();
		}
	};
	//选框改变影响input的改变
	ftemp.dataChange=function($oper){
		var $tr=$oper.parent('td').parent('tr');
		if($oper.is(':checked')){
			$tr.find('[type="text"]').removeAttr("disabled");
		}else{
			$tr.find('[type="text"]').prop('disabled',true);
		}
	};
	
	ftemp.dataandoperChange=function($oper){
		if($oper.find('[name="fee"]').val()==""&&$oper.find('[name="addedFee"]').val()==""){
			$oper.find('[name="serviceType"]').prop('checked',false);
			$oper.find('[type="text"]').prop('disabled',true);
		}
	};
	
	$form.find($datapost).each(function(){
		$(this).change(function(){
			ftemp.dataChange($(this));
		});
	});
	
	$form.find($dataexpress).each(function(){
		$(this).change(function(){
			ftemp.dataChange($(this));
		});
	});
	
	$form.find($dataems).each(function(){
		$(this).change(function(){
			ftemp.dataChange($(this));
		});
	});
	
	$(function(){
		var url = $('.urljson').val();
		$FreightPage.dataTablePage({
		});
		ftemp.editForm($FreightPage.find('.v_form'),Cfg.contextPath+ url, function(vo){
			$form.find($trpost).each(function(){
				ftemp.dataandoperChange($(this));
			});
			
			$form.find($trexpress).each(function(){
				ftemp.dataandoperChange($(this));
			});
			
			$form.find($trems).each(function(){
				ftemp.dataandoperChange($(this));
			});
		});
		//checkbox事件
		$operpost.click(function(){
			ftemp.operChange($operpost,$('.tr_post'));
		});
		$operexpress.click(function(){
			ftemp.operChange($operexpress,$('.tr_express'));
		});
		$operems.click(function(){
			ftemp.operChange($operems,$('.tr_ems'));
		});
		$(".v_oper_submit").click(function(){
			if(!RJ.E.util.verificationForm($form)){
				return;
			}
			if($('.errorMsg').css("display")!="none"){
				var $form1=$('#commodity_post_page');
				var $panel=$form1.find('.v_info_warning');
				$panel.addClass("show");
		    	$panel.find(".v_show_info").text("请至少选择1种运送方式！");
	    		setTimeout(function() {
	    			$panel.removeClass("show");
	    		}, 3000);
				$form1.foundation("reveal", "close");
				return;
			}
			var tempId=$('.tempId').val();
			$.ajax({
				type : "POST",
				url : tempId=="" ? saveVoUrl : updateVoUrl,
				data : ftemp.getFreightForm($form),
				success : function(data){
					location.href=Cfg.contextPath+"/seller/freight.htm";
				},
				error : function(e){
					alert("出错："+e);
				}
			});
		});
		
		$('.v_oper_cancel').click(function(){
			location.href=Cfg.contextPath+"/seller/freight.htm";
		});
	});
	
})(jQuery,RJ.E);