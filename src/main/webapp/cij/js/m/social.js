(function($, E){
	var social = E.social = {}; // 电商项目 社会化媒体 命名空间
	
 social.outlined = function(csmaId){
	url=E.Cfg.contextPath+"/user/social/vo_outline.json";
	option={"csmaId":csmaId};
	$.ajax({
		type: "post",
		url: url,
		data: option,
		cache: false,
		dataType: "text",
		timeout: 20000,
		success: function(data){
		 window.location.reload()
		}
		}
	)
}

 social.getMedia2 = function(refid,proid,objId){
	url=E.Cfg.contextPath+"/user/social/vo_getMedie.json";
	//url="/emall/user/social/vo_getMedieByUser.json";
	option={"refId":refid,"pId":proid};
	//option={"refId":refid,"pId":proid,"objId":objId};
	$.ajax({
		type: "post",
		url: url,
		data: option,
		cache: false,
		dataType: "json",
		timeout: 20000,
		success: function(data){
		if (!data.availableMedia || data.availableMedia.length==0) {
		}else{
			var html ="<ul>";
			for ( var int = 0; int < data.availableMedia.length; int++) {
				if(data.availableMedia[int].status!=1){
				html=html+"<li><a href='"+data.availableMedia[int].socialHref+"' target='_blank' rel='social' >"+data.availableMedia[int].mediaName+"</a>&nbsp; </li>";
				}else{
					$("#form").show();
					html=html+"<li status='1' id='"+data.availableMedia[int].csmaId+"'>"+data.availableMedia[int].mediaName+" &nbsp;<a href='javascript:void(0)' onclick=\"share('"+data.availableMedia[int].csmaId+"')\">分享</a> <a href='javascript:void(0)' onclick=\"getUserInfo('"+data.availableMedia[int].csmaId+"')\">获取用户信息</a>&nbsp;<a href='javascript:void(0)' onclick=\"outlined('"+data.availableMedia[int].csmaId+"')\">解绑</a>&nbsp; </li>";
	
				}
			}
			var html =html+"</ul>";
			$("#list").html(html);
		}},
		error: function(e){
			console.log(JSON.stringify(data));
		}
	})
}
 
$(function(){
	$('body').on("click",".outline",function(){
	   var csmaId=$(this).attr("csmaId");
	   social.outlined(csmaId);
});
})

$(function(){
	//DOM层加载完后自动执行
	social.addrInit();
	
	 $('.panel-body').on("click",".btn-share-binding-two",function() {
         $('#share-order').foundation('reveal','open');
     });
});

 social.addrInit = function(){
	$.ajax({
		url:E.Cfg.contextPath+"/user/social/vo_getMedieByUser.json",
		dataType:"JSON",
		type : "POST",
		success: function(data) {
          // alert(JSON.stringify(data));
           //console.log(JSON.stringify(data));
           //var data=JSON.stringify(data);
           var html="";
           if(data.availableMedia.length>0){
				var html="";
				var btnhtml="";
				var bindvalue="";
				var overtimehtml="";
				 for (var i = data.availableMedia.length - 1; i >= 0; i--) {
					 if(typeof(data.availableMedia[i].expire)!="undefined"&&data.availableMedia[i].socialType=="qq"){
						 html=' <li class="p-14  left"><p class="left font-14-gray">设置信息：</p><img src="'+data.availableMedia[i].head+'" class="left" style="margin: 0px 10px" alt=""/><b class="left" style="margin: 0px 10px">'+data.availableMedia[i].nickName+'</b></li>'
                              +'<li class="p-14 left"><p class="font-14-gray left">有效期至：</p><b class="left">'+data.availableMedia[i].expire+'</b></li>';
						 if(data.availableMedia[i].status=="0"){
							 overtimehtml='<li class="p-14 left"><a href="'+data.availableMedia[i].socialHref+'" target="_blank"  class="a-underline font-14-gray" style="margin: 0px 10px">延长时间</a></li>';
							 $('.QQzone').html("").append(html).append(overtimehtml);
						 }else{
							 $('.QQzone').html("").append(html);
						 }
						 btnhtml='<button class="btn-safe-center-apply  outline" csmaId='+data.availableMedia[i].csmaId+' style="background: #DDDDDD">解除绑定</button>';
						 $('#QQzonebtn').html("").append(btnhtml);
					 }else if(typeof(data.availableMedia[i].expire)!="undefined"&&data.availableMedia[i].socialType=="sina"){
						 html=' <li class="p-14  left"><p class="left font-14-gray">设置信息：</p><img src="'+data.availableMedia[i].head+'" class="left" style="margin: 0px 10px" alt=""/><b class="left" style="margin: 0px 10px">'+data.availableMedia[i].nickName+'</b></li>'
                         +'<li class="p-14 left"><p class="font-14-gray left">有效期至：</p><b class="left">'+data.availableMedia[i].expire+'</b></li>';
						 if(data.availableMedia[i].status=="0"){
							 overtimehtml='<li class="p-14 left"><a href="'+data.availableMedia[i].socialHref+'" target="_blank"  class="a-underline font-14-gray" style="margin: 0px 10px">延长时间</a></li>';
							 $('.sina').html("").append(html).append(overtimehtml);
						 }else{
							 $('.sina').html("").append(html);
						 }
					     btnhtml='<button class="btn-safe-center-apply  outline" csmaId='+data.availableMedia[i].csmaId+' style="background: #DDDDDD">解除绑定</button>';
					     $('#sinabtn').html("").append(btnhtml);
					 }else if(typeof(data.availableMedia[i].expire)!="undefined"&&data.availableMedia[i].socialType=="tcweibo"){
						 html=' <li class="p-14  left"><p class="left font-14-gray">设置信息：</p><img src="'+data.availableMedia[i].head+'" class="left" style="margin: 0px 10px" alt=""/><b class="left" style="margin: 0px 10px">'+data.availableMedia[i].nickName+'</b></li>'
                         +'<li class="p-14 left"><p class="font-14-gray left">有效期至：</p><b class="left">'+data.availableMedia[i].expire+'</b></li>';
						 if(data.availableMedia[i].status=="0"){
							 overtimehtml='<li class="p-14 left"><a href="'+data.availableMedia[i].socialHref+'" target="_blank"  class="a-underline font-14-gray" style="margin: 0px 10px">延长时间</a></li>';
							 $('.TXblog').html("").append(html).append(overtimehtml);
						 }else{
							 $('.TXblog').html("").append(html);
						 }
					     btnhtml='<button class="btn-safe-center-apply  outline" csmaId='+data.availableMedia[i].csmaId+' style="background: #DDDDDD">解除绑定</button>';
					     $('#TXblogbtn').html("").append(btnhtml);
					 }else if(typeof(data.availableMedia[i].expire)!="undefined"&&data.availableMedia[i].socialType=="renren"){
						 html=' <li class="p-14  left"><p class="left font-14-gray">设置信息：</p><img src="'+data.availableMedia[i].head+'" class="left" style="margin: 0px 10px" alt=""/><b class="left" style="margin: 0px 10px">'+data.availableMedia[i].nickName+'</b></li>'
                         +'<li class="p-14 left"><p class="font-14-gray left">有效期至：</p><b class="left">'+data.availableMedia[i].expire+'</b></li>';
						 if(data.availableMedia[i].status=="0"){
							 overtimehtml='<li class="p-14 left"><a href="'+data.availableMedia[i].socialHref+'" target="_blank"  class="a-underline font-14-gray" style="margin: 0px 10px">延长时间</a></li>';
							 $('.renren').html("").append(html).append(overtimehtml);
						 }else{
							 $('.renren').html("").append(html);
						 }
					     btnhtml='<button class="btn-safe-center-apply  outline" csmaId='+data.availableMedia[i].csmaId+' style="background: #DDDDDD">解除绑定</button>';
					     $('#renrenbtn').html("").append(btnhtml);
					 }else if(typeof(data.availableMedia[i].expire)!="undefined"&&data.availableMedia[i].socialType=="kaixin"){
						 html=' <li class="p-14  left"><p class="left font-14-gray">设置信息：</p><img src="'+data.availableMedia[i].head+'" class="left" style="margin: 0px 10px" alt=""/><b class="left" style="margin: 0px 10px">'+data.availableMedia[i].nickName+'</b></li>'
                         +'<li class="p-14 left"><p class="font-14-gray left">有效期至：</p><b class="left">'+data.availableMedia[i].expire+'</b></li>';
						 if(data.availableMedia[i].status=="0"){
							 overtimehtml='<li class="p-14 left"><a href="'+data.availableMedia[i].socialHref+'" target="_blank"  class="a-underline font-14-gray" style="margin: 0px 10px">延长时间</a></li>';
							 $('.kaixin').html("").append(html).append(overtimehtml);
						 }else{
							 $('.kaixin').html("").append(html);
						 }
					     btnhtml='<button class="btn-safe-center-apply  outline" csmaId='+data.availableMedia[i].csmaId+' style="background: #DDDDDD">解除绑定</button>';
					     $('#kaixinbtn').html("").append(btnhtml);
					 }else if(typeof(data.availableMedia[i].expire)!="undefined"&&data.availableMedia[i].socialType=="douban"){
						 html=' <li class="p-14  left"><p class="left font-14-gray">设置信息：</p><img src="'+data.availableMedia[i].head+'" class="left" style="margin: 0px 10px" alt=""/><b class="left" style="margin: 0px 10px">'+data.availableMedia[i].nickName+'</b></li>'
                         +'<li class="p-14 left"><p class="font-14-gray left">有效期至：</p><b class="left">'+data.availableMedia[i].expire+'</b></li>';
						 if(data.availableMedia[i].status=="0"){
							 overtimehtml='<li class="p-14 left"><a href="'+data.availableMedia[i].socialHref+'" target="_blank"  class="a-underline font-14-gray" style="margin: 0px 10px">延长时间</a></li>';
							 $('.douban').html("").append(html).append(overtimehtml);
						 }else{
							 $('.douban').html("").append(html);
						 }
					     btnhtml='<button class="btn-safe-center-apply  outline" csmaId='+data.availableMedia[i].csmaId+' style="background: #DDDDDD">解除绑定</button>';
					     $('#doubanbtn').html("").append(btnhtml);
					 }else{
						 btnhtml='<button class="btn-safe-center-apply btn-share-binding-two btn-10-undone" onclick="window.open(\''+data.availableMedia[i].socialHref+'\')">立即设置</button>';
						 if(data.availableMedia[i].socialType=="qq"){
							 $('#QQzonebtn').html("").append(btnhtml);
						 }else if(data.availableMedia[i].socialType=="sina"){
							// $('#sinabtn').html("").append(btnhtml);
						 }else if(data.availableMedia[i].socialType=="tcweibo"){
							 $('#TXblogbtn').html("").append(btnhtml);
						 }else if(data.availableMedia[i].socialType=="renren"){
							// $('#renrenbtn').html("").append(btnhtml);
						 }else if(data.availableMedia[i].socialType=="kaixin"){
							// $('#kaixinbtn').html("").append(btnhtml);
						 }else if(data.availableMedia[i].socialType=="douban"){
							 $('#doubanbtn').html("").append(btnhtml);
						 }
						 
					 }
					 }
				 }
		},
		error: function(error) {
			alert(error);
		}
	})
}
})(jQuery, RJ.E);

