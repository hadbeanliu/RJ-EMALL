     $(function(){
                var ret=$("#ret").val();
                var status=$("#status").val();
                if(ret==0){
                	$(".panel-body").html("未添加店铺>>>>>>>>>>><a href='/emall/seller/store/add.htm' >请跳转</a>");
                }else if(ret==1){
                	$(".panel-body").html("商铺正在审核");
                }else{
                	if(status==2){
                		status="失败理由1";
                	}else if(status==3){
                		status="失败理由2";
                	}else{
                		status="其他失败理由";
                	}
                	$(".panel-body").html("商铺审核失败，请重新提交失败理由："+status+">>>>>>>>>>><a href='/emall/seller/store/failure.htm' >请重新填写</a>");
                }
                })
                
                
             