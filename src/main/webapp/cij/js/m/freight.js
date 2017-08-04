(function($, E){
	var freight = E.freight = {}; 

	var defaultOptions = {};
	var pageInfo = {};

	var $FreightPage = $('#commodity_post_page');
	var $addForm=$('.v_add_form');
	var $editForm=$('.v_edit_form');
	var $addpost=$('.add_line_post');
	var $addexpress=$('.add_line_express');
	var $addems=$('.add_line_ems');
	var $tbody=$('.v_oper_click');
	
	$.extend(defaultOptions, pageInfo); /* 加载服务器配置 */
	var idColumn = pageInfo.idColumn;
	
	freight.getFreightForm = function($form,idColumn, exId){
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
    		if(this.name != "name" &&  this.name != "id" && this.name != "status"){
    			if(this.name === "serviceType"){
    				i++;
    			}
    			if(this.name === "carryId"){
    				datas["carryMode["+(i+1)+"].id"] = val;
    			}else{
    				datas["carryMode["+i+"]."+this.name] = val;
    			}
    		}else {
    			datas[this.name] = val;
			}
    			
//    		console.info("00000000000000:::::"+datas["carryMode.isDefault"]);
    	});
    	
    	if ( !exId && idColumn && ! datas[idColumn.data] ) {
    		delete datas[idColumn.data];
    	}
    	return datas;
	};
	freight.clearForm = function ($form) { /* 把表单$form表单的值清除 */
		if (Foundation && Foundation.libs && Foundation.libs.abide) {
//    		Foundation.libs.abide.reset($form); // 隐藏也需要重新设置，不满足条件
    		
    	    var self = Foundation.libs.abide;
    	    $form.removeAttr(self.invalid_attr);

    	    $('[' + self.invalid_attr + ']', $form).removeAttr(self.invalid_attr);
    	    $('.' + self.settings.error_class, $form).not('small').removeClass(self.settings.error_class);
    	    $(':input', $form).not(':radio, :checkbox, :button, :submit, :reset, [data-abide-ignore] ,[name="areas"] ,[name="isDefault"]').val('').removeAttr(self.invalid_attr);
    	    $(':input,:checkbox', $form).not(':radio').attr("checked",false);
//    	    $(':input', form).not(':button, :submit, :reset, :hidden, [data-abide-ignore]').val('').removeAttr(self.invalid_attr);
    	} else {
//    		$(':input', $form).not(':button, :submit, :reset, :hidden').val('');
    		$(':input', $form).not(':button, :submit, :reset, :radio').val(''); // 隐藏也需要重新设置
    	}
    };
//	freight.editForm = function ($form, url, call) { /* 根据url获取数据并填充到$form中 */
//    	freight.clearForm($form);
//		$.get(url, null, function(u){
//			for(var prop in u) {
//				if(prop != "carryMode"){
//					var field = $form.find('[name="' + prop + '"]');
//					if (field && field.length > 0) {
//						if (field[0].type === 'checkbox' || field[0].type === 'radio') {
//							field.filter('[value="'+ u[prop] +'"]').prop('checked', true);
//						} else {
//							field.val(u[prop]);
//						}
//					}
//				}else {
//					var i=1;
//					var fna="";
//					for(var cprop in u[prop]){
//						for(var car in u[prop][cprop]){
//							var vc = $editForm.find('[id="edit_' + u[prop][cprop]["serviceType"].toLowerCase()+'"]');
//							if(vc && vc.length > 0){
//								vc.prop("checked",true);
//								if(vc.is(":checked")==true){
//									$editForm.find(".v_"+u[prop][cprop]["serviceType"].toLowerCase()).css("display","block");
//								}
//							}
//							var field = $form.find('[id="' + u[prop][cprop]["serviceType"].toLowerCase()+"_"+car + '"]');
//							if(u[prop][cprop]["serviceType"] != fna && u[prop][cprop]["isDefault"] === "1" ){
//								if(u[prop][cprop]["serviceType"] != fna){
//									i=1;
//								}
//								fna=u[prop][cprop]["serviceType"];
//								var $table=$editForm.find("."+u[prop][cprop]["serviceType"].toLowerCase()+"Tab");
//								var $trl=$form.find('[id="' + u[prop][cprop]["serviceType"].toLowerCase()+'_'+car + '_'+i+ '"]');
//								if(!($trl && $trl.length > 0)){
//									freight.createLine($table,u[prop][cprop]["serviceType"]);
//								}
//								i++;
//							}
//							if(u[prop][cprop]["isDefault"] === "1"){
////								field = $form.find('[id="' + u[prop][cprop]["serviceType"].toLowerCase()+'_'+car + '_'+(i-1)+ '"]');
//								field = $form.find('[id="' + u[prop][cprop]["serviceType"].toLowerCase()+'_'+car + '_'+ '1"]');
//							}
//							if (field && field.length > 0) {
//								if (field[0].type === 'checkbox' || field[0].type === 'radio') {
//									field.filter('[value="'+ u[prop][cprop][car] +'"]').prop('checked', true);
//								} else {
//									field.val(u[prop][cprop][car]);
//								}
//							}
//						}
//					}
//				}
//			}
//			if (call) {
//				call(u);
//			}
//		}, 'json');
//    };
	
	freight.editForm = function ($form, url, call) { /* 根据url获取数据并填充到$form中 */
	freight.clearForm($form);
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
    
    freight.createLine=function($tb,vl){
    	vl=vl.toLowerCase();
    	var _len = $tb.find("tr").length;
    	if(_len === 1){
    		$tb.parents(".v_form_area").css("display","block");
    	}
        $tb.append("<tr id=\""+_len+"\" align=\"center\">"
                    +"<td><span style=\"width: 90%;\">未添加地区</span><span style=\"width: 10%;float:right;\">" 
                    +"<a>编辑</a></span><input type=\"hidden\" name=\"carryId\" id=\""+vl+"_id_"+_len+"\"/>" 
                    +"<input type=\"hidden\" name=\"areas\" id=\""+vl+"_areas_"+_len+"\"/>" 
                    +"<input type=\"hidden\" name=\"id\" id=\""+vl+"_id_"+_len+"\"/>" 
                    +"<input type=\"hidden\" name=\"serviceType\" id=\""+vl+"_serviceType_"+_len+"\" value=\""+vl.toUpperCase()+"\"/>" 
                    +"<input type=\"hidden\" name=\"isDefault\" id=\""+vl+"_isDefault_"+_len+"\" value=\"1\"/></td>"
                    +"<td><input type=\"text\" name=\"fee\" id=\""+vl+"_fee_"+_len+"\"/></td>"
                    +"<td><input type=\"text\" name=\"addedFee\" id=\""+vl+"_addedFee_"+_len+"\"/></td>"
                    +"<td><a onclick=\"deltr(this);\">删除</a></td>"
                    +"</tr>");
    };
    freight.checkChange=function($f,str,type){
    	var $v=str+""+type;
    	if($f.find($v).is(":checked")==true){
			$f.find(".v_"+type).css("display","block");
			$f.find(".v_"+type).find('[name="serviceType"]').val(type.toUpperCase());
			$f.find(".v_"+type).find('[name="isDefault"]').val("0");
		}else{
			$f.find(".v_"+type).css("display","none");
			$f.find(".v_"+type).find(':input').val("");
			$f.find(".v_"+type).find(':input').val("");
		}
    };
	
	$(function(){
//		测试获取运送方式和计算运费
//		$(".post-way").change(function(){
//			E.carry.countFee($('.re_fee'),this.options[this.selectedIndex].value,2);//买家改变运送方式后计算运费
//		});
		
		$(".v_button_add").click(function(){
			$.ajax({
				type:"POST",
				url:Cfg.contextPath + "/seller/freight/addfreight.json",
				success:function(data){
					location.href=Cfg.contextPath+"/seller/freight/addfreight.htm";
				},
				error:function(e){
					alert("出错："+e);
				}
			});
		});
		$(".v_button_edit").click(function(){
			var tempId=$(this).parent("td").find('.tempId').val();
			$.ajax({
				type:"POST",
				url:Cfg.contextPath + "/seller/freight/edit/"+tempId+".json",
				success:function(data){
					location.href=Cfg.contextPath+"/seller/freight/edit/"+tempId+".htm";
				},
				error:function(e){
					alert("出错："+e);
				}
			});
			return;
		});
		$addForm.find($addpost).click(function(){
			freight.createLine($addForm.find(".postTab"),"post");
		});
		$addForm.find($addexpress).click(function(){
			freight.createLine($addForm.find(".expressTab"),"express");
		});
		$addForm.find($addems).click(function(){
			freight.createLine($addForm.find(".emsTab"),"ems");
		});
		$editForm.find($addpost).click(function(){
			freight.createLine($editForm.find(".postTab"),"post");
		});
		$editForm.find($addexpress).click(function(){
			freight.createLine($editForm.find(".expressTab"),"express");
		});
		$editForm.find($addems).click(function(){
			freight.createLine($editForm.find(".emsTab"),"ems");
		});
		
		$addForm.find("#add_post").change(function(){
			freight.checkChange($addForm,"#add_","post");
		});
		$addForm.find("#add_express").change(function(){
			freight.checkChange($addForm,"#add_","express");
		});
		$addForm.find("#add_ems").change(function(){
			freight.checkChange($addForm,"#add_","ems");
		});
		$editForm.find("#edit_post").change(function(){
			freight.checkChange($editForm,"#edit_","post");
		});
		$editForm.find("#edit_express").change(function(){
			freight.checkChange($editForm,"#edit_","express");
		});
		$editForm.find("#edit_ems").change(function(){
			freight.checkChange($editForm,"#edit_","ems");
		});
		
		//点击table显示运送方式
		$tbody.each(function(){
			$(this).click(function(){
				$(this).find('.tr_oper_data').each(function(){
					if($(this).css("display")=="none"){
						$(this).css('display','');
						$(this).parent('tbody').removeClass('od-expand-btn');
						$(this).parent('tbody').addClass('od-fold-btn');
					}else{
						$(this).css('display','none');
						$(this).parent('tbody').removeClass('od-fold-btn');
						$(this).parent('tbody').addClass('od-expand-btn');
					}
				});
			});
		});
		
		if($FreightPage.length > 0 && $FreightPage.dataTablePage){
			$FreightPage.dataTablePage({
				checkboxMode: false,
				url: Cfg.contextPath + "/seller/freight", // 请求地址
				columns: [{
					data: "name",
					name: "<th class='hide'>模版名称</th>",
					orderable: false,
					searchable: true,
					display: function (td, value, vo, rowIndex, colIndex) {
						$(td).html('模版名称：<a class="v_oper_edit p-color" title="模版详情">' + value + '</a>').addClass('dealtime small-pad10');
					}
				}, {
					data: "status",
					name: "<th class='hide'>模版状态</th>",
					orderable: false,
					searchable: false,
					display: function (td, value, vo, rowIndex, colIndex) {
						$(td).html('<div class="hide-for-small left">是否启用：</div><div class="left p-color">' + (value=="0"?"禁用":"已启用") + '</div>\
								<div class="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\
								<a class="v_oper_edit" style="text-decoration: underline;color: blue">修改</a>&nbsp;\
                                <a class="v_oper_delete" style="text-decoration: underline;color:#ff0000">删除</a></div>').addClass('data_id small-pad10');
					}
				}, {
					data: "id",
					name: "<th class='small-table-cell'>运费范畴</th>",
					orderable: false,
					searchable: true,
					display: function (td, value, vo, rowIndex, colIndex) {
						var carrys = vo.carryMode;
						var htm = [];
						var str="";
						for ( var i = 0; i < carrys.length; i++) {
							var gs = carrys[i];
							if(gs.areas==str){
								htm.push('<div class="'+ (i !== carrys.length-1 ? 'padb20 ' : '') +'">　</div>');
							}else{
								htm.push('<div class="'+ (i !== carrys.length-1 ? 'padb20 ' : '') +'">'+ gs.areas +'</div>');
							}
							str=gs.areas;
						}
						$(td).html(htm.join('')).addClass('padt50 td_block');
					}
				}, {
					data: "id",
					name: "<th class='min_w90'>运费方式</th>",
					searchable: false,
					display: function (td, value, vo, rowIndex, colIndex) {
						var carrys = vo.carryMode;
						var htm = [];
						htm.push('<div class="show-for-small left">运费方式：</div>');
						for ( var i = 0; i < carrys.length; i++) {
							var gs = carrys[i];
							htm.push('<div class="'+ (i !== carrys.length-1 ? 'padb20 ' : '') +'">'+ (gs.serviceType=="POST"?"平邮":(
									gs.serviceType=="EMS"?"EMS":"快递")) +'</div>');
						}
						$(td).html(htm.join('')).addClass('padt50 small-marl65 small-marb5 price hide-for-small');
					}
				}, {
					data: "id",
					name: "<th class='min_w60'>运费（元）</th>",
					orderable: false,
					searchable: true,
					display: function (td, value, vo, rowIndex, colIndex) {
						var carrys = vo.carryMode;
						var htm = [];
						for ( var i = 0; i < carrys.length; i++) {
							var gs = carrys[i];
							htm.push('<div class="'+ (i !== carrys.length-1 ? 'padb20 ' : '') +'">'+ gs.fee +'</div>');
						}
						$(td).html(htm.join('')).addClass('hide-for-small font-bold padt50 ');
					}
				}, {
					data: "id",
					name: "<th class='min_w60'>每多一件商品增加运费（元）</th>",
					orderable: false,
					searchable: true,
					display: function (td, value, vo, rowIndex, colIndex) {
						var carrys = vo.carryMode;
						var htm = [];
						for ( var i = 0; i < carrys.length; i++) {
							var gs = carrys[i];
							htm.push('<div class="'+ (i !== carrys.length-1 ? 'padb20 ' : '') +'">加'+ gs.addedFee +'</div>');
						}
						$(td).html(htm.join('')).addClass('hide-for-small font-bold padt50 ');
					}
				}
//				, {
//					data: "remark",
//					name: "<th class='small-table-cell'>运费说明</th>",
//					orderable: false,
//					searchable: false,
//					display: function (td, value, vo, rowIndex, colIndex) {
//						$(td).html('<div class="margin-b padb50  inline-block">快递统一发申通，当天18:00前拍下付款成功，当天发货；否则次日发货。</div>').addClass('padt50 td_block');
//					}
//				}
				],
//	        columnOpers: {
//	            name: "评价",
//	            display: function(td, id, vo, rowIndex, colIndex) {
//	                /*多余2个，省略号显示*/
//					$(td).html('<div class="td_more"><span class="fa-stack v_oper_more"><i class="fa fa-circle"></i><i class="fa fa-ellipsis-h" title="更多"></i></span>'+
//	                           '<div class="more v_oper_more_panel hide"><span class="v_oper_delete"><i title="删除"></i>删除</span><span>|</span><span style="display:none;"><i title="评论"></i>评论</span><span style="display:none;">|</span><span class="reveal_share"><i title="分享"></i>分享</span><span>|</span><span><i title="收货"></i>收货</span></div></div>').addClass('td_more_wrap');
//	                $(td).find('.reveal_share').bind('click',function(){
//	                    $('.v_share').foundation("reveal", "open");
//	                });
				/*少于3个，按钮显示*/
				/*                $(td).html('<div class="td_btn"><span class="button default"><i title="评价"></i>评价</span></div>').addClass('td_btn_wrap');
				 */				
//	            }
//	        },
				getVoData: function($form, exId) {
			    	return freight.getFreightForm($form, idColumn, exId);
			    },
			    add: function($form){
			    	freight.clearForm($form);
			    	$('.ban').prop("checked",true);
					if($addForm.find("#add_post").is(":checked")==false){
						$addForm.find(".v_post").css("display","none");
					}
					if($addForm.find("#add_express").is(":checked")==false){
						$addForm.find(".v_express").css("display","none");
					}
					if($addForm.find("#add_ems").is(":checked")==false){
						$addForm.find(".v_ems").css("display","none");
					}
			    	var saen = $form.find('.v_oper_submit_and_edit_next');
			    	saen.css('display', 'none');
			    	$form.foundation("reveal", "open");
			    },doDel: function($trs){
					if ( ! this.beforeDelete($trs) ) {
						return;
					}
			    	/*var trs = getSelectedVos(); */
			    	if ( $trs.size() ) {
			    		var dels = [];
			    		$trs.find("input[name=v_select_id]").each(function(){
			    			dels.push(this.value);
			    		});
			    		
			    		if (dels.length > 0) {
			    			var self = this;
			    			$.get(Cfg.contextPath + "/seller/freight/v_dels.json", 'voIds='+dels.join(","), function(){
			    				location.reload();
			    				self.afterDelete(dels);
			    			});
			    		}
			    	}
			    },edit: function($trs, $form){
			    	/*if ( ! $trs || $trs.length === 0) { */
			    	/*alert('未选择！'); */
			    	/*return; */
			    	/*} */
			    	if ( ! this.checkSelected($trs, "请选择需要编辑的记录！") ) {
			    		return;
			    	}
			    	
			    	if ( ! this.beforeEdit($trs, $form) ) {
						return;
					}
			    	
			    	var trs = $trs.toArray(); /* getSelectedVos().toArray(); */
		    		var self = this;
		    		
		    		var saen = $form.find('.v_oper_submit_and_edit_next');
		    		saen.css('display', trs.length > 1 ? 'block' : 'none');
		    		$form.find('.v_oper_submit').css('display', trs.length > 1 ? 'none' : 'block');
		    		if (trs.length > 1) {
		    			saen.unbind('click');
		    			saen.click(function(){
		    				self.save($form, function(res){
		    					datatable.draw(false);
    		    				if (trs.length > 0) {
    		    					if (trs.length == 1) {
    		    						$form.find('.v_oper_submit').css('display', 'block');
    		    						saen.css('display', 'none');
    		    					}
    		    					freight.editForm($form, "/emall/seller/freight/v_get/" + trs.shift().id + ".json", function(vo){
    		    						self.afterEdit($form, vo);
    		    						if($editForm.find("#edit_post").is(":checked")==false){
    		    							$editForm.find(".v_post").css("display","none");
    		    						}
    		    		    			if($editForm.find("#edit_express").is(":checked")==false){
    		    							$editForm.find(".v_express").css("display","none");
    		    						}
    		    		    			if($editForm.find("#edit_ems").is(":checked")==false){
    		    							$editForm.find(".v_ems").css("display","none");
    		    						}
    		    					});
    		    				}
		    				});
		    			});
		    		}
		    		var first = trs.shift();
		    		freight.editForm($form, "/emall/seller/freight/v_get/" + first.id + ".json", function(vo){
		    			self.afterEdit($form, vo);
		    			if($editForm.find("#edit_post").is(":checked")==false){
							$editForm.find(".v_post").css("display","none");
						}
		    			if($editForm.find("#edit_express").is(":checked")==false){
							$editForm.find(".v_express").css("display","none");
						}
		    			if($editForm.find("#edit_ems").is(":checked")==false){
							$editForm.find(".v_ems").css("display","none");
						}
		    		});
			    },
			    alertInto: "#alert_into" // 提醒实现位置
			});
		}
	});
	
})(jQuery,RJ.E);

function deltr(k){
	var _len = $(k).parents("tr").parents("table").find("tr").length;
	var $tab=$(k).parents("tr").parents("table").parents(".v_form_area");
	$(k).parents("tr").remove(); 
	if(_len === 2){
		$tab.css("display","none");
	}
	
}