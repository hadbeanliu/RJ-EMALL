(function($){
	'use strict';
	
	$.extend( $.fn.dataTable.defaults, {
    	dom:"<'row'<'small-12 medium-6 large-6 columns'><'small-12 medium-6 large-6 columns'>>"+
			"t"+
			"<'row'<'small-12 medium-6 large-6 columns'i><'small-12 medium-6 large-6 columns'p>>",
		language: {
	        "url": Cfg.contextPath + "/cij/datatables/plugins/i18n/Chinese.lang"
	    },
//    	order: [[ 1, "asc" ]],
    	lengthChange: false,
    	searching: true,
    	processing: true,
    	serverSide: true,
	} );
	if ($.fn.dataTable.moment) {
		$.fn.dataTable.moment( 'yyyy-MM-dd HH:mm:ss' );
	}
	$.fn.dataTable.ext.errMode = 'none';
	$(document).on('error.dt', function(e, settings, techNote, message){
		console.log(message);
		if (techNote === 7 && settings.jqXHR && settings.jqXHR.responseJSON
				 && settings.jqXHR.responseJSON.errorId ) {
			alert('Ajax，'+settings.jqXHR.responseJSON.errorMsg + "\n地址：" + settings.ajax.url);
		} else {
			alert(message);
		}
	});
	
	
	var dtPageUtil = {};
	dtPageUtil.isError = function(json) {
		if (json && json.errorId && json.errorMsg) {
			alert(json.errorMsg);
			return true;
		}
		return false;
	};
    dtPageUtil.getSelectedVos = function ($table) { /* 获取DataTable中已选择的记录 */
    	return $table.find('.datatable_row.selected');
    };
    
    dtPageUtil.clearForm = function ($form) { /* 把表单$form表单的值清除 */
    	RJ.E.util.clearForm($form);
    };
    
    dtPageUtil.editFormByUrl = function ($form, url, call) { /* 根据url获取数据并填充到$form中 */
    	RJ.E.util.editFormByUrl($form, url, call);
    };
    
    dtPageUtil.fillFormValue = function ($form, vo) { /* 填充u到$form中 */
    	RJ.E.util.fillFormValue($form, vo);
    } 

    dtPageUtil.getFormDataArrays = function ($form) { /* 从表单$form中获取值 */
    	return RJ.E.util.getFormDataArrays($form);
    };

    dtPageUtil.getFormDataArray = function ($form) { /* 从表单$form中获取值 */
    	return RJ.E.util.getFormDataArray($form);
    };    
    dtPageUtil.getFormData = function ($form, idColumn, exId) { /* 从表单$form中获取值 */
    	return RJ.E.util.getFormData($form, idColumn, exId);
    };
    
    dtPageUtil.bindFormVerification = function($form, vds) { /* 绑定表单校验 */ 
    	return RJ.E.util.bindFormVerification($form, vds);
    };
    
    dtPageUtil.verificationForm = function($form) { /* 绑定表单校验 */
    	return RJ.E.util.verificationForm($form);
    };
    
    dtPageUtil.applySelectedAllCheckbox = function (selected, $table) { /* 对选择框进行判断是否为全选 */
        if (selected.length === 0) {
        	$table.find('.v_oper_select_all').prop("checked", false);
        } else {
//        	var currSelected = $table.find('input[name=v_select_id]:checked');
//        	$table.find('.v_oper_select_all').prop("checked", $table.find('.datatable_row').size() === currSelected.size());
        	var currSelected = $table.find('.datatable_row.selected');
        	$table.find('.v_oper_select_all').prop("checked", $table.find('.datatable_row').size() === currSelected.size());
        }
    };
    
    dtPageUtil.getForm = function(elem) {
    	var $form = $(elem).parents('.v_add_form');
    	return $form.size() > 0 ? $form : $(elem).parents('.v_edit_form');
    }

    dtPageUtil.bindPageOpers = function (options, $page, $oper, bindFormOpers) { /* 绑定CURD和分页的操作命令 */
    	var self = this;
//    	var $table, $addForm, $editForm, $search;
    	
	    /* 折叠功能 */
	    $oper.find("[data-v-oper-id]").click(function(e){
	    	var $el = $(this);
	    	var oper = $el.attr('value');
	    	if ( ! oper || oper.indexOf("v_oper_") !== 0 ) {
	    		var doOper = $page.find( "#" + $el.data("v-oper-id") );
		    	var select_oper = doOper.val();
		    	if (doOper.size() === 1 && select_oper.indexOf("v_oper_") === 0) {
		    		oper = select_oper.substring(7);
		    	}
	    	}
    		if (oper && oper.indexOf("v_oper_") === 0) {
    			oper = oper.substring(7);
    			if (options[oper]) {
    				options[oper](self.getSelectedVos(options.get$table()), options.get$editForm(e));
    				return;
    			}
    		}
	    	options.showWarning("请选择操作！！！");
	    });
	    /* 开启批量操作 */
	    $oper.find('.v_oper_enable_batch_edit').click(function(){
	    	$page.toggleClass('batch-edit-status');
	    	var $el = $(this);
	    	var tname = $el.attr('tname');
	    	$el.attr('tname', $el.text());
	    	$el.text(tname);
	    });
	    
	    /* 选择记录 */
	    $oper.find('.v_oper_select_all').click(function(){
	    	var options = $page[0].dtPage.options;
	    	options.selectAll( options.get$table().find('.datatable_row') );
	    });
	    
	    
	    /* 删除操作 */
	    $oper.find('.v_oper_delete').click(function(e){
//	    	options.del( $(this).parents("tr") );
	    	options.del( $(this).parents('.datatable_row') );
	    	
	    	e.stopPropagation();
	    });

	    
	    /* 添加 */
	    $oper.find('.v_oper_add').click(function(e){
/*	    	options.add( $form ); */
	    	options.add( options.get$addForm() );
	    	
	    	e.stopPropagation();
	    });
	    
	    
	    /* 修改 */
	    $oper.find('.v_oper_edit').click(function(e){
	    	var $tr = $(this).parents('.datatable_row');
/*	    	options.edit( $tr.size() > 0 ? $tr : getSelectedVos(), $form); */
	    	options.edit( $tr.size() > 0 ? $tr : dtPageUtil.getSelectedVos(options.get$table()), options.get$editForm(e));
	    	
	    	e.stopPropagation();
	    });

	    if (bindFormOpers === true) {
	    	/* 取消提交 */
	    	$oper.find(".v_oper_canel_submit").click(function(e){
//	    		($(this).parents('.v_add_form').size() > 0 ? options.get$addForm() : options.get$editForm(e)).foundation("reveal", "close");
	    		dtPageUtil.getForm(this).foundation("reveal", "close");
/*	    		$form.foundation("reveal", "close"); */
	    	});
	    	
	    	/* 保存 */
	    	$oper.find(".v_oper_submit").click(function(e){
//	    		options.save($(this).parents('.v_add_form').size() > 0 ? options.get$addForm() : options.get$editForm(e));
	    		options.save(dtPageUtil.getForm(this));
/*	    		options.save($form); */
	    	});
	    }
	    
	    /* 搜索 */
	    var $qsearch = $oper.find('.v_oper_quick_search').keyup(function(evt){
	    	var e = evt || window.event;
	    	var code = e.keyCode || e.which;
	    	
	    	if (code == 13) { /* 回车触发查询 */
//	    		options.search($(this).parent());
	    		options.search($(this));
	    	}
	    })
	    $oper.find('.v_oper_click_quick_search').click(function(evt){
	    	options.search($qsearch);
	    });
	    $oper.find('.v_oper_advanced_search').click(function(){
	    	options.get$search().foundation("reveal", "open");
	    });
	    $oper.find('.v_oper_reset_search').click(function(){
	    	var $form = options.get$search();
	    	dtPageUtil.clearForm($form);
	    	doCustomSearch($page, '');
	    });
	    $oper.find('.v_oper_do_search').click(function(){
	    	options.search(options.get$search(), true);
	    });
	    var $csearch = $oper.find('.v_oper_click_search').click(function(){
	    	var $elem = $(this);
	    	if ($elem.hasClass('active')) {
//	    		var datatable = $page[0].dtPage.datatable;
//    			datatable.search('');
//    			datatable.draw();
//    			options.afterSearch();
	    		
	    		if ($elem.parent().hasClass('v_oper_click_search_enable_remove_active')) {
	    			doCustomSearch($page, '');
	    			$elem.removeClass('active');
	    		}
	    		
	    	} else {
	    		if (this.name) {
//	    			var datatable = $page[0].dtPage.datatable;
//	    			datatable.search(this.name + '=' + $elem.attr('value'));
//	    			datatable.draw();
//	    			options.afterSearch();
	    			var v = '';
	    			if ($elem.attr('value')) {
	    				v = this.name + '=' + $elem.attr('value');
	    			}
	    			doCustomSearch($page, v);
	    		}
	    		
	    		$csearch.removeClass('active');
	    		$elem.addClass('active');
	    	}
	    });
	    function doCustomSearch($page, value) {
	    	var datatable = $page[0].dtPage.datatable;
			datatable.search(value);
			datatable.draw();
			options.afterSearch();
	    }
	    
	    /* 刷新 */
	    $oper.find('.v_oper_refresh').click(function(){
	    	var datatable = $page[0].dtPage.datatable;
	    	datatable.draw(true);
	    });
	    
	    /* 更多操作 */
	    $oper.find('.v_oper_more').click(function(e){
	    	var $more = $(this);
	    	
	    	$page.find(".v_oper_more").not($more).removeClass("active");
	    	var $morePanel = $more.parent().find('.v_oper_more_panel');
	    	$page.find(".v_oper_more_panel").not($morePanel).addClass("hide");
	    	
	    	$more.toggleClass("active");
	    	$morePanel.toggleClass('hide');
	    	
	    	e.stopPropagation();
	    });
    };
	
	
	
	var dataTablePage = function(options){
		if (options) {
			var url;
			if (typeof options === 'string') {
				url = options;
				options = { url: url};
			} else if (typeof options === 'object') {
				url = options.url;
			} else {
				return;
			}
			if ( ! url ) {
				return;
			}
			
			var selected = [];
			
			var $page = $(this);
			if ($page.length === 0) {
				alert('DataTablePage初始选择器 '+ $page.selector +' 不存在');
				return;
			}

			if ($page.lenth > 0) {
				alert('DataTablePage初始选择器 '+ $page.selector +' 不存在');
				return;
			}
			
			var pageInfoUrl = url+"/v_page_info.json";
			var pageUrl = url+"/v_page.json";
			var saveVoUrl = url+"/v_save.json";
			var updateVoUrl = url+"/v_update.json";
			var getVoUrl = function (voId) {
				return url+"/v_get/" + voId + ".json";
			};
/*			var delVoUrl = function(voId) { */
/*				return url+"/v_del/" + voId + ".json"; */
/*			}; */
			var delVosUrl = url+"/v_dels.json";
			var voValidationUrl = url+"/v_valid.json";
			
			$.get(pageInfoUrl, function(pageInfo){
				if ( ! pageInfo.idColumn ) {
					alert('ID列未设置！');
					return;
				}
				if ( ! pageInfo || !pageInfo.columns || pageInfo.columns.length < 0 ) {
					if ( ! options.row && (! options.columns || options.columns.length < 0) ) {
						alert('列未设置！');
						return;
					}
				}

				var $table = $page.find(".v_table");
				var $oper = $page; /* $page.find(".v_oper"); */
				var $addForm = $page.find(".v_add_form");
				var $editForm = $page.find(".v_edit_form");
				var $search = $page.find(".v_search");
				var $delWarning = $page.find(".v_del_waring");
				var $successInfo = $page.find(".v_info_success");
				var $failInfo = $page.find(".v_info_fail");
				var $warningInfo = $page.find(".v_info_warning");
				var $processing = $page.find(".v_page_processing");
				
				if (pageInfo.fieldValidDefines && pageInfo.fieldValidDefines.length > 0) {
					dtPageUtil.bindFormVerification($addForm, pageInfo.fieldValidDefines);
					dtPageUtil.bindFormVerification($editForm, pageInfo.fieldValidDefines);
					dtPageUtil.bindFormVerification($search, pageInfo.fieldValidDefines);
				}
				
				var defaultOptions = {
/*					rowOpers: ['<span><i class="fa fa-pencil v_oper_edit" title="编辑"></i></span>', */
/*					           '<span><i class="fa fa-trash v_oper_delete" title="删除"></i></span>'], */
/*			   		columnOpers: {
						name: "",
						display: function(td, vo, rowIndex, colIndex) {
							$(td).html('<span><i class="fa fa-pencil v_oper_edit" title="编辑"></i></span>',
							           '<span><i class="fa fa-trash v_oper_delete" title="删除"></i></span>');
						}
					},*/
//					order: [[ 1, "asc" ]],
					processing: function(isProcessing) {
						if (isProcessing) {
							this.get$table().find('.dataTables_wrapper').addClass("hide");
							this.get$processing().removeClass("hide"); /* 加载中。。。 */
						} else {
							this.get$table().find('.dataTables_wrapper').removeClass("hide");
							this.get$table().removeClass("hide");
							this.get$processing().addClass("hide"); /* 加载完成 */
						}
					},
					beforeSelected: function(id, $tr){return true;},
					select: function($tr){ /* 选择行 */
						
						if ($page.find('.v_oper_enable_batch_edit').size() > 0 && ! $page.hasClass('batch-edit-status')) {
							return; // 当且仅当存在批量操作且开启时，可以进行选择操作
						}
						
						var check = $tr.find('input[name=v_select_id]');
						var id = $tr.attr('id');
						
						if ( ! this.beforeSelected(id, $tr) || 'row' !== $tr.attr('role') ) {
							return;
						}
				        var index = $.inArray(id, selected);
				 
				        if ( index === -1 ) {
				            selected.push( id );
				            check.prop("checked", true);
				            
				            this.afterSelected( [ id ] );
				        } else {
				            selected.splice( index, 1 );
				            check.prop("checked", false);
				            
			            	this.afterDeselected( [ id ] );
				        }
				        $tr.toggleClass('selected');
				        
				        this.applySelectedAllCheckbox();
					},
					selectAll: function($trs){ /* 选择全部列 */
						if ( ! this.beforeSelected(null, $trs) ) {
							return;
						}
						$trs = $trs.filter('[role=row]');
				    	var notSelected = $trs.not('.selected');
				    	var isSelect = notSelected.size() > 0; /* 存在未选择的列,则进行全部选择 */
				    	if ( isSelect ) {
				    		notSelected.addClass('selected');
					    	notSelected.each(function(){
				    			selected.push( this.id );
				    		});
					    	$trs.find("input[name=v_select_id]").prop("checked", true);
					    	
					    	this.afterSelected( selected );
				    	} else { /* 清空选择操作 */
				    		$trs.removeClass('selected');
				    		$trs.each(function(){
				        		var id = this.id;
				                var index = $.inArray(id, selected);
				                
				                if (index >= 0) {
				                	selected.splice( index, 1 );
				                }
				    		});
					    	$trs.find("input[name=v_select_id]").prop("checked", false);
					    	
					    	this.afterDeselected( selected );
				    	}
					},
					afterSelected: function(){},
					afterDeselected: function(){},
					del: function($trs){
						if ( ! this.checkSelected($trs, "请选择需要删除的记录！") ) {
				    		return;
				    	}
				    	
						var self = this;
				    	this.get$delWarning().find('.v_oper_do_delete').off('click').click(function(){
				    		self.doDel( $trs && $trs.size() > 0 ? $trs : dtPageUtil.getSelectedVos(self.get$table()) );
				    	});
				    	this.get$delWarning().foundation("reveal", "open");
					},
					beforeDelete: function($trs){
						return true;
					},
					doDel: function($trs){
						if ( ! this.beforeDelete($trs) ) {
							return;
						}
				    	/*var trs = getSelectedVos(); */
				    	if ( $trs.size() ) {
				    		var dels = [];
//				    		$trs.find("input[name=v_select_id]").each(function(){
//				    			dels.push(this.value);
//				    		});
				    		$trs.each(function(){
				    			dels.push($(this).attr('id'));
				    		});
				    		
				    		if (dels.length > 0) {
				    			var self = this;
				    			$.get(delVosUrl, 'voIds='+dels.join(","), function(){
				    				$trs.each(function(){
				    					datatable.row(this).remove();
				    				});
				    				datatable.draw(false);
				    				self.afterDelete(dels);
				    			});
				    		}
				    	}
				    },
				    afterDelete: function(dels){
				    	this.get$delWarning().foundation("reveal", "close");
				    },
				    add: function($form){
				    	dtPageUtil.clearForm($form);
				    	var saen = $form.find('.v_oper_submit_and_edit_next');
				    	saen.css('display', 'none');
				    	$form.foundation("reveal", "open");
				    },
				    beforeEdit: function($trs, $form){return true;},
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
//	    		    					dtPageUtil.editFormByUrl($form, getVoUrl(trs.shift().id), function(vo){
//	    		    						self.afterEdit($form, vo);
//	    		    					});
	    		    					self.editFormByUrl($form, getVoUrl(trs.shift().id));
	    		    				}
			    				});
			    			});
			    		}
			    		var first = trs.shift();
//			    		dtPageUtil.editFormByUrl($form, getVoUrl(first.id), function(vo){
//			    			self.afterEdit($form, vo);
//			    		});
			    		this.editFormByUrl($form, getVoUrl(first.id));
				    },
				    editFormByUrl: function($form, url) {
				    	var self = this;
				    	dtPageUtil.editFormByUrl($form, url, function(vo){
				    		self.editFormByVo($form, vo);
			    		});
				    },
				    editFormByVo: function($form, vo) {
				    	this.fillFormValue($form, vo);
				    	
				    	var idName = this.getIdName();
				    	var $id = $form.find('input[name='+ idName  + ']');
				    	if ($id.size() === 0) {
				    		$id = $('<input type="hidden" name="'+ idName +'" />');
				    		$form.append($id);
				    	}
				    	$id.val(vo[idName]);

		    			this.afterEdit($form, vo);
				    },
				    bindFormVaild: function($form) {
				    	dtPageUtil.bindFormVerification($form, pageInfo.fieldValidDefines ? pageInfo.fieldValidDefines : []);
				    },
				    fillFormValue: function($form, vo) {
				    	dtPageUtil.clearForm($form);
				    	dtPageUtil.fillFormValue($form, vo);
				    },
				    afterEdit: function($form, vo) {
				    	if ( ! $form.hasClass("open") ) {
				    		$form.foundation("reveal", "open");
				    	}
				    },
				    getVo: function($form) {
				    	if ( ! dtPageUtil.verificationForm($form) ) {
				    		return false; /* 校验表单不通过 */
				    	}
				    	return this.getVoData($form);
				    },
				    save: function($form, call){
				    	var vo = this.getVo($form);
				    	if ( ! vo ) {
							return;
						}
/*				    	var voId = idColumn && idColumn.data ? vo[idColumn.data] : ''; */
				    	var voId = vo[this.getIdName()];
				    	
//				    	var self = this;
//				    	$.ajax({
//				    		type : "POST",
//				    		url : voId ? updateVoUrl : saveVoUrl,
//				    		data : $.param(vo, true),
//				    		dataType : "json",
//				    		success : function(res) {
//			    				if (res.errorId) {
///*			    					self.saveError(res); */
//			    					self.saveError("保存失败：" + res.errorMsg);
//			    				} else {
//			    					if (typeof call === 'function') {
//			    						call(res);
//			    					} else {
//			    						self.saveSuccess(res);
//			    						self.afterSave($form);
//			    					}
//			    				}
//				    		},
//				    		error : function(e) {
//				    			self.saveError(e);
//				    		}
//				    	});
				    	this.doSave($.param(vo, true), voId ? updateVoUrl : saveVoUrl, $form, call);
				    },
				    getUpdateVoUrl: function() { return updateVoUrl; },
				    getSaveVoUrl: function() { return saveVoUrl; },
				    doSave: function(vo, url, $form, call){
				    	var self = this;
				    	$.ajax({
				    		type : "POST",
				    		url : url,
				    		data : vo,
				    		dataType : "json",
				    		success : function(res) {
			    				if (res.errorId) {
/*			    					self.saveError(res); */
			    					self.saveError("保存失败：" + res.errorMsg);
			    				} else {
			    					if (typeof call === 'function') {
			    						call(res);
			    					} else {
			    						self.saveSuccess(res);
			    						self.afterSave($form);
			    					}
			    				}
				    		},
				    		error : function(e) {
				    			self.saveError(e);
				    		}
				    	});
				    },
				    saveSuccess: function(vo, msg){
				    	this.showMsg(this.get$successInfo(), msg ? msg : "保存成功");
				    	datatable.draw(false);
				    },
				    saveError: function(e, msg){
				    	this.showMsg(this.get$failInfo(), msg ? msg : "保存失败");
				    	console.log(e);
				    },
				    afterSave: function($form){
				    	$form.foundation("reveal", "close");
				    },
				    getSearchData: function($search) {
				    	if ( ! dtPageUtil.verificationForm($search) ) {
				    		return false; /* 校验表单不通过 */
				    	}
//				    	return this.getVoData($search, true);
				    	return dtPageUtil.getFormDataArray($search);
				    },
				    search: function($search, isCustom){
				    	var filters = this.getSearchData($search);
				    	if ( ! filters ) {
				    		this.showWarning('查询条件不存在');
				    		return;
				    	}
				    	
				    	var iss = false;
				    	isCustom = true;
				    	if (isCustom) { /* 定制搜索 */
//				    		var ss = [];
//				    		for (var name in filters) {
//				    			if (filters[name]) {
//				    				iss = true;
//				    			}
//				    			ss.push(name + '=' + filters[name]);
//				    		}
//				    		if (ss.length > 0) {
//				    			datatable.search(ss.join('&'));
//				    		}
				    		iss = true;
				    		datatable.search(filters.join('&'));
				    		
//				    	} else {
//					    	for (var name in filters) {
//					    		var value = filters[name];
//								if (value) {
//									var idx = -1;
//									for(var i=0; i<columns.length; i++) {
//										if ( !columns[i].notData && columns[i].data === name) {
//											idx = i;
//											break;
//										}
//									}
//									if (idx > 0) {
//										iss = true;
//										datatable.columns(idx).search(value);
//									}
//								}
//					    	}
				    	}

				    	
				    	if ( ! iss ) {
				    		if ($search.is(':input') && $search.hasClass('v_oper_quick_search')) {
				    			datatable.columns().search('');
				    		} else {
				    			this.showWarning('请输入查询条件');
				    			return;
				    		}
				    	}
				    	datatable.draw();
				    	this.afterSearch();
				    },
				    afterSearch: function(){
				    	this.get$search().foundation("reveal", "close");
				    },
				    bindOpers: function ($oper, bindFormOpers) {
				    	dtPageUtil.bindPageOpers(options, $page, $oper, bindFormOpers);
				    },
				    getVoData: function($form, exId) {
				    	return dtPageUtil.getFormData($form, idColumn, exId);
				    },
				    applySelectedAllCheckbox: function() {
//				    	dtPageUtil.applySelectedAllCheckbox(selected, this.get$table().find('.v_datatables_list'));
				    	dtPageUtil.applySelectedAllCheckbox(selected, $page);
				    },
				    checkSelected: function($trs, msg){
				    	if ( ! $trs || $trs.length === 0) {
				    		this.showMsg(this.get$warningInfo(), msg);
				    		return false;
				    	}
				    	return true;
				    },
				    showWarning: function(msg) {
				    	this.showMsg(this.get$warningInfo(), msg);
				    },
				    showMsg: function($panel, msg) {
				    	$panel.addClass("show");
				    	$panel.find(".v_show_info").text(msg);
			    		setTimeout(function() {
			    			$panel.removeClass("show");
			    		}, 3000);
				    },
				    refresh: function(r) {
				    	datatable.draw(r ? true : false);
				    },
				    getIdName: function() {
				    	return idColumn.data;
				    },
				    getRow: function(elem){
				    	if (elem) {
				    		var tr = null;
				    		if (elem.getAttribute('data-v-oper-id')) {
				    			var $trs = dtPageUtil.getSelectedVos(this.get$table());
				    			if ($trs.length > 0) {
				    				tr = $trs[0];
				    			} else {
				    				
				    			}
				    			
				    		} else if ($(elem).hasClass('.datatable_row')) {
				    			tr = elem;
				    		} else {
				    			tr = $(elem).parents('.datatable_row')[0];
				    		}
				    		
				    		if (tr) {
				    			return datatable.row(tr);
				    		}
				    	}
				    },
				    getRowData: function(elem) {
				    	var row = this.getRow(elem);
				    	if (row) {
				    		return row.data();
				    	}
				    },
				    getDataTable: function() {
				    	return datatable;
				    },
					get$table: function(){ return $table; },
					get$addForm: function(){ return $addForm; },
					get$editForm: function(e){ return $editForm; },
					get$search: function(){ return $search; },
					get$delWarning: function(){ return $delWarning; },
					get$successInfo: function(){ return $successInfo; },
					get$failInfo: function(){ return $failInfo; },
					get$warningInfo: function(){ return $warningInfo; },
					get$processing: function(){ return $processing; }
				};
				
				
//				defaultOptions = $.extend(pageInfo, defaultOptions); /* 加载服务器配置 */
//				options = $.extend(defaultOptions, options); /* 重载用户配置 */
//				options.def = defaultOptions; /* 提供默认操作 */
				$.extend(defaultOptions, pageInfo); /* 加载服务器配置 */
				options = $.extend({}, defaultOptions, options); /* 重载用户配置 */
				options.def = defaultOptions; /* 提供默认操作 */

				var ulLiMode = options.row ? options.row.ulLiMode : false; // 是否为列表模式，列表模式则不采用table结构
				var tableSelector = ulLiMode ? 'v_datatables_list' : 'table';
				var tableSelector = 'v_datatables_list';
				
				var tableHead = [];
				var columnDefs = [];
				var idColumn = pageInfo.idColumn;
				var colIndex = 0;
				var columns = [];
				var searchs = [];
				
				if ( ! ulLiMode && options.columns && options.columns.length > 0 ) {
					tableHead.push('<table class="display '+ tableSelector +'"><thead><tr>');
					if (options.checkboxMode) {
						columnDefs.push({
							targets: colIndex++,
							createdCell: function (td, cellData, rowData, row, col) {
								var index = $.inArray(cellData, selected);
								$(td).addClass("input_w40").html('<input type="checkbox" name="v_select_id" value="' + cellData + '"' + (index !== -1 ? ' checked' : '') + '/>');
							}
						});
						tableHead.push('<th class="input_w40"><input type="checkbox" class="v_oper_select_all" /></th>');
						columns.push(idColumn);
						
						defaultOptions.order = [[ 1, "desc" ]]; // 有多选框时第二列排序
						
					} else {
						defaultOptions.order = [[ 0, "desc" ]]; // 否则第一列排序
					}
					if ( typeof options.order === 'undefined' ) {
						options.order = defaultOptions.order;
					}
					
					var cdefs = null;
					if (options.columnDefs && defaultOptions.columns) {
						cdefs = {};
						for ( var i = 0; i < options.columnDefs.length; i++) {
							var cdef = options.columnDefs[i];
							cdefs[cdef.index] = cdef.display;
						}
					}
					for ( var i = 0; i < options.columns.length; i++) {
						var column = options.columns[i];
						if (!column.display && cdefs && cdefs[i]) {
							column.display = cdefs[i];
						}
						if ( typeof column.orderable === 'undefined') {
							column.orderable = false;
						}
						if ( typeof column.searchable === 'undefined') {
							column.searchable = false;
						}
						
						var def = null;
						if (column.display) {
							def = {
									/*							targets: colIndex + i, */
									createdCell: function (td, cellData, rowData, row, col) {
										options.columns[col-colIndex].display(td, cellData, rowData, row, col, dtPage);
										options.bindOpers( $(td) );
									}
							};
							
						} else if (column.linkable === true) {
							def = {
									/*							targets: colIndex + i, */
									createdCell: function (td, cellData, rowData, row, col) {
										var $td = $(td).html('<a href="javascript:void(0)" class="v_oper_edit" title="'+ cellData +'">'+ cellData +'</a>');
										options.bindOpers( $td );
									}
							};
						}
						
						if (! def && (column.type || column.sType)) {
							def = {};
						}
						if (def) {
							def.targets = colIndex + i;
							if (column.type) {
								def.type = column.type;
							}
							/*						if (column.sType) { */
							/*							def.sType = column.sType; */
							/*						} */
							columnDefs.push(def);
						}
						
						if (column.searchable) {
							searchs.push(column);
						}
						tableHead.push(column.name.indexOf("<th") >= 0 ? column.name : "<th>"+ column.name +"</th>");
						columns.push(column);
						
					}
					
					/*每一行的操作 */
					if (options.columnOpers) {
						var name = options.columnOpers.name;
						tableHead.push(name && name.indexOf("<th") >= 0 ? name : ("<th>"+ (name ? name : "") +"</th>"));
						columns.push({
							data: idColumn.data,
							orderable: false,
							searchable: false
						});
						columnDefs.push({
							targets: columns.length-1,
							createdCell: function (td, cellData, rowData, row, col) {
								/*							var opers = $(td).html(options.rowOpers.join('')); */
								options.columnOpers.display(td, cellData, rowData, row, col, dtPage);
								options.bindOpers($(td).addClass('td_more_wrap'));
							}
						});
					}
					
					tableHead.push("</tr></thead><tbody></tbody></table>");
				}
				if ( ! options.order) {
					options.order = [];
				}
				
				if ( ulLiMode ) {
					tableHead.push('<div class="'+ tableSelector +'"></div>');
//					tableSelector = '.' + tableSelector;
				}
				
				$table.addClass("hide");
				$table.append(tableHead.join("\n"));
				
				options.bindOpers($oper, true);
/*				$page.click(function(){ */
				$(document).click(function(){
					$page.find(".v_oper_more_panel").addClass("hide");
					$page.find(".v_oper_more").removeClass("active");
				});
				
			    var datatable = $table.find('.' + tableSelector).on("xhr.dt", function(e, settings, json){
			    	dtPageUtil.isError(json);
				}).on('processing.dt', function(e, settings, processing){
/*			    	console.log(processing ? '加载中。。。' : '加载完成'); */
			    	options.processing(processing);
			    	
			    	/* 选择记录 */
			    	if ( ! processing ) {
			    		$table.find('.datatable_row').click(function () {
			    			options.select($(this));
			    		});
			    		if (typeof options.processDone === 'function') {
			    			options.processDone();
			    		}
			    	}
			    	
			    }).DataTable({
			    	pageLength: options.length,
			    	columns: columns,
			    	columnDefs: columnDefs,
			    	order: options.order,
			    	info: typeof options.info === 'undefined' ? true : options.info,
			    	paging: typeof options.paging === 'undefined' ? true : options.paging,
			    	ulLiMode: ulLiMode,
			    	createdRow: ulLiMode ? options.row.display : null,
			    	rowCallback: function( row, data ) { /* 记录之前已经选择的列 */
			    		if (options.rowDisplay) {
			    			options.rowDisplay(row, data, dtPage);
			    			options.bindOpers($(row));
			    		}
			    		row.id = data[idColumn.data];
			            if ( $.inArray(row.id, selected) !== -1 ) {
			                $(row).addClass('selected');
			            }
			            $(row).addClass('datatable_row');
			        },
			    	ajax: {
			    		url: pageUrl,
			    		type: "POST"
			    	},
			    });
			    var dtPage = {
			    	datatable: datatable,
			    	options: options
			    };
			    $page[0].dtPage = dtPage;
			    
			    $table.on('draw.dt', function(){
/*			    	dtPage.applySelectedAllCheckbox(selected, $table.find('table')); */
			    	options.applySelectedAllCheckbox();
			    });
			    
			    
/*			    function openEditVo($tr) { */
/*			    	options.edit( $tr, $editForm); */
/*			    	return false; */
/*			    } */
/*			     */
/*			    function bindOpers($oper, bindFormOpers) { */
/*			    	dtPageUtil.bindPageOpers(options, $page, $oper, $table, $addForm, $editForm, bindFormOpers); */
/*			    } */
			    
			}, "json");
			
			return $page;
		}
	};
	
	
	
	$.fn.extend({
		dataTablePage: dataTablePage,
		dtPageUtil: dtPageUtil,
		getDtPage: function() {
			if (this.length === 1) {
				return this[0].dtPage;
			}
		}
	});
})(jQuery);
