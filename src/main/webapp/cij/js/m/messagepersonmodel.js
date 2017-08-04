(function($, E){
	var messagepersonmodel = E.messagepersonmodel = {}; // 电商项目 个人消息 命名空间
	
	messagepersonmodel.getBySeller = function(){
		$.ajax({
			url:E.Cfg.contextPath+"/seller/message/model/info.json",
			dataType:"JSON",
			type : "POST",
			success: function(data) {
				var html="";
				var html1="";
				var html2="";
				var html3="";
				for(i=0;i<data.length;i++){
						//html='<label for="checkbox1">'+data[i].messageModel.title+'<input class="margin-l" id="checkbox1" name="smsList" type="checkbox" value="'+data[i].id+'" checked="true">短信</input><input id="checkbox1" name="smsList" type="checkbox" value="'+data[i].id+'" checked="true"/>邮件</label></br>';
						html='<div class="row"><div class="small-6 columns">'
                             +'<label for="checkbox1">'+data[i].messageModel.title+'</label></div><div class="small-6 columns">';
						if(data[i].smsStatus=="1"){
							html1='<input class="margin-l"  name="smsList" type="checkbox" value="'+data[i].id+'" checked="true" />短信';
						}else{
							html1='<input class="margin-l"  name="smsList" type="checkbox" value="'+data[i].id+'" />短信';
						}
						if(data[i].emailStatus=="1"){
							html2='<input name="emailList"  type="checkbox" value="'+data[i].id+'" checked="true"/>邮件</div></div>';
						}else{
							html2='<input name="emailList"  type="checkbox" value="'+data[i].id+'" />邮件</div></div>';
						}
						html3=html+html1+html2;
						//$("#isChoice").append(html).append(html1).append(html2);
						$("#isChoice").append(html3);
					}
					
			},
			error: function(error) {
				alert(error);
			}
		})
		
	}
	
	
	
	$(function(){
		messagepersonmodel.getBySeller();
		
//		$('#isChoice').on('click','[type=checkbox]',function(e){
//			if($(this).val()=="0"){
//				$(this).val(1);
//			}else {
//				$(this).val(0);
//			}
//			$
//		});
		
	});
	
})(jQuery, RJ.E);