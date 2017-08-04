$(document).ready(function(){
	'use strict';
	
	$.extend( $.fn.dataTable.defaults, {
    	dom:"<'row'<'small-6 columns'><'small-6 columns'>r>"+
			"t"+
			"<'row'<'small-6 columns'i><'small-6 columns'p>>",
		language: {
	        "url": "cij/datatables-1.10.6/plugins/i18n/Chinese.lang"
	    },
    	order: [[ 1, "asc" ]],
    	lengthChange: false,
    	searching: true,
    	processing: true,
    	serverSide: true,
	} );
//	$('#user_page').dataTablePage("user_page");
	
	var selected = [];
	
	var columns = [
         { data: 'id', orderable: false },
         { data: 'userName' },
         { data: 'age' },
         { data: 'description', orderable: false }
	];
	
    var userPage = $('#user_page').DataTable({
//    	dom:"<'row'<'small-6 columns'><'small-6 columns'>r>"+
//			"t"+
//			"<'row'<'small-6 columns'i><'small-6 columns'p>>",
//    	language: {
//            "url": "cij/datatables-1.10.6/plugins/i18n/Chinese.lang"
//        },
    	columns: columns,
    	columnDefs: [ {
		    targets: 0,
		    createdCell: function (td, cellData, rowData, row, col) {
		    	var index = $.inArray(cellData, selected);
		    	$(td).html('<input type="checkbox" name="id" value="' + cellData + '"' + (index !== -1 ? ' checked' : '') + '/>')
		    }
		},{
			targets: 1,
			createdCell: function (td, cellData, rowData, row, col) {
				$(td).html('<a href="javascript:openEditUser(\''+ rowData.id +'\')" title="'+ cellData +'">'+ cellData +'</a>')
			}
		} ],
//    	order: [[ 1, "asc" ]],
//    	lengthChange: false,
//    	searching: true,
//    	processing: true,
//    	serverSide: true,
    	rowCallback: function( row, data ) { // 记录之前已经选择的列
    		row.id = data.id;
//          if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
            if ( $.inArray(data.id, selected) !== -1 ) {
                $(row).addClass('selected');
            }
        },
    	ajax: {
    		url: "page/userlist.json",
//    		dataSrc: "userList"
    		type: "POST"
    	},
    });
    
    // 选择记录
    $('#user_page tbody').on( 'click', 'tr', function () {
    	var id = this.id;
        var index = $.inArray(id, selected);
        var check = $(this).find('input[name=id]');
 
        if ( index === -1 ) {
            selected.push( id );
            check.attr("checked", "checked");
        } else {
            check.removeAttr("checked");
            selected.splice( index, 1 );
        }
        
        $(this).toggleClass('selected');
    } );
    $('#user_oper .select').click(function(){
    	var trs = $('#user_page tbody tr');
    	var notSelected = trs.not('.selected');
    	var isSelect = notSelected.size() > 0; // 存在未选择的列,则进行全部选择
    	if ( isSelect ) {
    		notSelected.addClass('selected');
	    	notSelected.each(function(){
    			selected.push( this.id );
    		});
	    	trs.find("input[name=id]").attr("checked", "checked");
	    	
    	} else { // 清空选择操作
    		trs.removeClass('selected');
    		trs.each(function(){
        		var id = this.id;
                var index = $.inArray(id, selected);
                
                if (index > 0) {
                	selected.splice( index, 1 );
                }
    		});
	    	trs.find("input[name=id]").removeAttr("checked");
    	}
    });
    
    // 删除操作
    $('#user_oper .delete').click(function(){
    	var trs = getSelectedUsers();
    	if ( trs.size() ) {
    		var dels = [];
    		trs.find("input[name=id]").each(function(){
    			dels.push(this.value);
    		})
    		$.get('user/dels.json', 'userIds='+dels.join(","), function(){
    			trs.each(function(){
    				userPage.row(this).remove();
    			});
    			userPage.draw(false);
    		});
    	}
    	
    });
    
    // 添加
    $('#user_oper .add').click(function(){
    	var form = $('#user_form');
    	form.find("[name]").val(null);
    	form.foundation("reveal", "open");
    });
    
    // 修改
    $('#user_oper .edit').click(function(){
    	var trs = getSelectedUsers().toArray();
    	if (trs.length > 0) {
    		var saen = $('#submit_and_edit_next');
    		saen.css('display', trs.length > 1 ? 'block' : 'none');
    		$('#ajax_submit').css('display', trs.length > 1 ? 'none' : 'block');
    		if (trs.length > 1) {
    			saen.unbind('click');
    			saen.click(function(){
    		    	$.ajax({
    		    		type : "POST",
    		    		url : "user/save.json",
    		    		data : getUserData($('#user_form')),
    		    		dataType : "json",
    		    		success : function(res) {
    		    			if (res.errorId) {
    		    				alert(res.errorMsg);
    		    			} else {
    		    				$("#user_page").DataTable().draw(false);
    		    				if (trs.length > 0) {
    		    					if (trs.length == 1) {
    		    						$('#ajax_submit').css('display', 'block');
    		    						saen.css('display', 'none');
    		    					}
    		    					editUser(trs.shift().id);
    		    				}
    		    			}
    		    		},
    		    		error : function(e) {
    		    			console.log(e);
    		    		}
    		    	});
    			});
    		}
    		var first = trs.shift();
    		editUser(first.id);
    		var form = $("#user_form");
    		form.foundation("reveal", "open");
    	}
    });
    $("#canel_submit").click(function(){
    	// $("#user_form .close-reveal-modal").trigger("click");
    	$("#user_form").foundation("reveal", "close");
    })
    $("#ajax_submit").click(function(){
    	$.ajax({
    		type : "POST",
    		url : "user/save.json",
    		data : getUserData($('#user_form ')),
    		dataType : "json",
    		success : function(res) {
    			if (res.errorId) {
    				alert(res.errorMsg);
    			} else {
    				$("#user_page").DataTable().draw(false);
    				$("#user_form .close-reveal-modal").click();
//    				alert("保存成功");
    			}
    		},
    		error : function(e) {
    			console.log(e);
    		}
    	});
    });
    
    $('#user_oper .search').click(function(){
    	$('#filter').toggleClass('hide');
    });
    $('#filter .dosearch').click(function(){
    	var filters = $('#filter [name]');
    	var iss = false;
    	filters.each(function(){
			if (this.value) {
				var idx = -1;
				for(var i=0; i<columns.length; i++) {
					if (columns[i].data === this.name) {
						idx = i;
						break;
					}
				}
				if (idx > 0) {
					iss = true;
					userPage.columns(idx).search(this.value);
				}
			}
    	})
    	
    	if ( ! iss ) {
    		userPage.columns().search('');
    	}
    	userPage.draw();
    });
    
    $('#user_oper .refresh').click(function(){
    	userPage.draw(true);
    });
    
    
    function getSelectedUsers() {
    	return $('#user_page tbody tr.selected');
    }
    
    function editUser(userId) {
		$.get("user/"+ userId + ".json", null, function(u){
//			var form = $("#user_form");
			for(var prop in u) {
				var field = $('#user_form [name="' + prop + '"]');
				if (field) {
					field.val(u[prop]);
				}
			}
//			form.foundation("reveal", "open");
		}, 'json')
    }
    
    window.openEditUser = function(userId) {
    	editUser(userId);
    	$("#user_form").foundation("reveal", "open");
    	return false;
    }
    
    function getUserData(form) {
    	var datas = {
    			id: '',
    			userName: '',
    			description: '',
    			age: 0
    	}
    	for(var pr in datas) {
    		datas[pr] = form.find("[name="+ pr +"]").val();
    	}
    	
    	if ( ! datas.id ) {
    		delete datas['id'];
    	}
    	
    	return datas;
    }
    
});