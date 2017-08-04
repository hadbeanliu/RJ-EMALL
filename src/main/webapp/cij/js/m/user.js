(function($, E){
	var user = E.user = {}; // 电商项目 用户模块 命名空间
	
//	$("#user").dataTablePage({
//		url: E.Cfg.contextPath+"/user", // 请求地址
//		columnOpers: {
//			name: "",
//			display: function(td, id, vo, rowIndex, colIndex) {
//				$(td).html('<span class="fa-stack v_oper_more"><i class="fa fa-circle"></i><i class="fa fa-ellipsis-h" title="更多"></i></span>'+
//						   '<div class="more v_oper_more_panel hide"><span class="v_oper_delete"><i title="删除"></i>删除</span><span>|</span><span><a href="/emall/user/address/'+id+'.htm">收货</a></span></div>');
//			}
//		},
//		
//		alertInto: "#alert_into" // 提醒实现位置
//	});
	
	var setValue = [];
	
	user.checkLoginNmae = function(){            	
		var $form = $(".form");
		var loginName = $form.find("input[name='loginName']").val();
		$.ajax({
			url:E.Cfg.contextPath+"/user/novalidate/vo_checkName.json",
			dataType:"JSON",
			type : "POST",
			data : {"loginName":loginName},
			success: function(data) {
				//alert(JSON.stringify(data));
				if(JSON.stringify(data)=="true"){
				}else{
					alert('用户名已存在');
				}
				
			},
			error: function(error) {
				alert(error);
			}
		})
	}
	
	user.edituser = function(){            	
		var $form = $("#form");
		var userId = $("#userId").val();
		var data = user.getUserData($form);
		data['id']=userId; 
		/*data['interestList']=data['interestList'].toString();*/
		data['sex']=$form.find('input[name=sex]:checked').val();
		$.ajax({
			url:E.Cfg.contextPath+"/user/vo_edituser.json",
			dataType:"JSON",
			type : "POST",
			data : data,
			success: function(data) {
				$(".v_info_success").addClass("show");
				$(".v_info_success").find(".v_show_info").text("保存成功");
	    		setTimeout(function() {
	    		$(".v_info_success").removeClass("show");
	    		}, 3000);
				//alert("保存成功");
			},
			error: function(error) {
				$(".v_info_fail").addClass("show");
				$(".v_info_fail").find(".v_show_info").text("修改失败");
	    		setTimeout(function() {
	    		$(".v_info_fail").removeClass("show");
	    		}, 3000);
			}
		})
	}
	
	user.getUserData = function(form) {
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
	
	user.initTag = function(){
		var tag = new Tag('e6',{
			listUrl:'/emall/tpl/dsww/001/javascript/tag.json',
			recoUrl: '/emall/tpl/dsww/001/javascript/rec-tag.json',
			isReco:true,
			isdataPs1:true,
			width:300,
			maxInput:5,
			//grayTip:'点击空白处输入更多标签',
			isdataPs2:true,
			setValue: setValue,
		});
		tag.setDataParser1(function(data1){//下拉框
			var v = {};
			v.text = data1.tagName;
			v.id=data1.tagName;
			v.isSystem=data1.isSystem;
			return v;
		});
		tag.setDataParser2(function(data2){//推荐框
			var vl = {};
			vl.text = data2.tagName;
			vl.id=data2.tagName;
			vl.isSystem=data2.isSystem;
			return vl;
		});
		tag.loadTag();//加载插件
		$('.select2-container').css('width','97%');
	}
	
	user.getUserInfo = function(){
		
		 var userJson=$("#userJson").val();
         var vo=eval('(' + userJson + ')');
         var interest;
         if(typeof(vo.nickName)=="undefined"||vo.nickName==null||vo.nickName==""){
        	 $('[name=nickName]').val(vo.loginName);
         }
         RJ.E.util.fillFormValue($('form'),vo);
         var reg = /(.{2}).+(.{2}@.+)/g;
         if(typeof(vo.email)!="undefined"&&vo.email!=null&&vo.email!=""){
        	 $(".emaileditvalue").html(vo.email.replace(reg, "$1****$2"));
        	 $(".isExitEmail").html('<a class="in-ma-p-red infor-man-p-check left editemail">修改</a>');
         }else{
        	 $(".isExitEmail").html('<a class="in-ma-p-red infor-man-p-check left bindemail">绑定</a>');
         }
         if(typeof(vo.interestList)!="undefined"){
         	interest=vo.interestList.split(",");
         	for(var i=0;i<interest.length;i++){
         		//{"tagName":"梦想"}JSON.stringify
         		var temp={};
         		temp['tagName']=interest[i];
         		setValue.push(temp);
         		
         	}
         	//var tagValue=JSON.stringify(setValue);
         	setValue = JSON.stringify(setValue);
         	 user.initTag();
         }else{
         	 user.initTag();
         }
		 
         //$('#head-photo').attr("src",vo.head);
         var preview1 = document.getElementById("preview1"),
			preview2 = document.getElementById("preview2"),
			preview3 = document.getElementById("preview3"),
			url = (typeof(vo.head) == "undefined") ? "" : vo.head;
		if (url == "") {
			return;
		}
    
		preview1.src = vo.head;
		preview2.src = vo.head;
		preview3.src = vo.head;  //$("#cropbox").attr("src",data.result.headPic); 
       
		
	}
	
	$(function(){ // 绑定操作
		$('body').on("blur",".checkNmae",function(){
			user.checkLoginNmae();
		});
		
		$('body').on("click",".v_edit_user",function(){
			user.edituser();
		});
		
		$('body').on("click",".editemail",function(){
			window.location.href = "safe/checkemail.htm";
		});
		
		$('body').on("click",".bindemail",function(){
			window.location.href = "safe/checkpwdtoemail.htm";
		});
		
		$('body').on("click",".interestButton",function(){
			var interestList = $('#input-e6').val();
			var userId = $("#userId").val();
			var data = user.getUserData($form);
			data['interestList']=interestList; 
			data['id']=userId;
			$.ajax({
				url:E.Cfg.contextPath+"/user/vo_edituser.json",
				dataType:"JSON",
				type : "POST",
				data : data,
				success: function(data) {
					$(".v_info_success").addClass("show");
					$(".v_info_success").find(".v_show_info").text("保存成功");
		    		setTimeout(function() {
		    		$(".v_info_success").removeClass("show");
		    		}, 3000);
					//alert("保存成功");
				},
				error: function(error) {
					$(".v_info_fail").addClass("show");
					$(".v_info_fail").find(".v_show_info").text("保存失败");
		    		setTimeout(function() {
		    		$(".v_info_fail").removeClass("show");
		    		}, 3000);
				}
			})
		});
		
		$(document).foundation();
		
			
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
		user.getUserInfo();
		$form = $("#form");
		url = "/emall/user/user_validate.json";
		RJ.E.util.bindFormVerificationByUrl($form,url);
	});

})(jQuery, RJ.E);