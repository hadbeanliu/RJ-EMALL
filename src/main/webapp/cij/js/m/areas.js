(function($, E){
	var area = E.area = {}; 
	area.dataInit=function(){
		$.ajax({
			url: Cfg.contextPath + "/admin/areas/datainit",
			dataType:"JSON",
			type : "POST",
			success: function(data) {
				window.location.href=Cfg.contextPath + "/admin/areas.htm";
				alert("地区数据初始化成功！");
			},
			error: function(error) {
				alert("初始化失败！原因："+error);
			}
		});
	};
	var $AreasPage = $('#user_page');
	$(function(){
		$('.v_oper_init').click(function(){
			area.dataInit();
		});
		$('#user_page').find('.area-select').each(function(){
			$(this).find(".city-district").change(function(){
				$(this).find(".pcode").val($(this).find(".city-district").val());
			});
		});
		
		if($AreasPage.length > 0 && $AreasPage.dataTablePage){
			$AreasPage.dataTablePage({
				url: Cfg.contextPath + "/admin/areas",
				columns: [{
					data: "id",
					name: "编号",
					orderable: true,
					searchable: true,
					display: function (td, value, vo, rowIndex, colIndex) {
						$(td).html('<div class="inline-block"><div class="des"><span class="v_oper_edit">'+ value +'</span></div></div>').addClass('areaid');
					}
				}, {
					data: "areaName",
					name: "地区名称",
					orderable: true,
					searchable: true,
					display: function (td, value, vo, rowIndex, colIndex) {
						$(td).html('<div class="">'+value+'</div>').addClass('areaName');
					}
				}, {
					data: "areaCode",
					name: "地区区号",
					orderable: true,
					searchable: true,
					display: function (td, value, vo, rowIndex, colIndex) {
						$(td).html('<div class="">'+value+'</div>').addClass('areaCode');
					}
				}, {
					data: "parentCode",
					name: "上级区号",
					orderable: true,
					searchable: true,
					display: function (td, value, vo, rowIndex, colIndex) {
						$(td).html('<div class="">'+value+'</div>').addClass('parentCode');
					}
				}],
				columnOpers: {
					name: "",
					display: function(td, id, vo, rowIndex, colIndex) {
						$(td).html('<span class="fa-stack v_oper_more"><i class="fa fa-circle"></i><i class="fa fa-ellipsis-h" title="更多"></i></span>'+
								   '<div class="more v_oper_more_panel hide"><span class="v_oper_delete"><i title="删除"></i>删除</span><span>|</span><span class="v_oper_edit"><i title="编辑"></i>编辑</span></div>');
					}
				},
				edit: function($trs, $form){
					/*				    	if ( ! $trs || $trs.length === 0) { */
					/*				    		alert('未选择！'); */
					/*				    		return; */
					/*				    	} */
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
//						    		    					dtPageUtil.editFormByUrl($form, getVoUrl(trs.shift().id), function(vo){
//						    		    						self.afterEdit($form, vo);
//						    		    					});
						    		    					self.editFormByUrl($form, Cfg.contextPath +"/areas/v_get/" + trs.shift().id + ".json");
//						    		    					E.areas.acccordToCode("110106");
						    		    					E.selectareas.acccordToCode($form,trs.shift().id);
						    		    				}
								    				});
								    			});
								    		}
								    		var first = trs.shift();
//								    		dtPageUtil.editFormByUrl($form, getVoUrl(first.id), function(vo){
//								    			self.afterEdit($form, vo);
//								    		});
								    		this.editFormByUrl($form, Cfg.contextPath +"/areas/v_get/" + first.id + ".json");
//								    		E.areas.acccordToCode("110106");
		    		    					E.selectareas.acccordToCode($form,trs.shift().id);
									    }
			});
		}
		
		//城市3级联动user_page
//		$('.area-select').initArea();
//		$('.area-select1').initArea();
		E.selectareas.init($('#user_page'));
		
	});
		
	
})(jQuery,RJ.E);
