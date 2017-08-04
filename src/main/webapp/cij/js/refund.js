
function cheak(e,type,vo) {
var id=	$($(e).parents("tr")).attr("id");
$("#refund").foundation("reveal", "open");
$("input[name=id]").val(id);
$("input[name=type]").val(type);
if(type=='2'){
	$("#sellerRefundReason").show();	
	$("#refundSum").hide();
	$("input[name=refundSum]").removeAttr("required");
	$("textarea[name=handlingSuggestion]").attr("required","required");
}else if(type=='1'){
	$("#sellerRefundReason").hide();
	$("#refundSum").show();

	$("input[name=refundSum]").val(vo.order.price/100);
	$("input[name=refundSum]").attr("required","required");
	$("textarea[name=handlingSuggestion]").removeAttr("required");
}
//cheaksubmit(id,type,$("#storeId").val());
}
function cheaksubmit(id) {
date=$('#'+id).serializeObject();	
var iSright=RJ.E.util.verificationForm($("#"+id));
if(iSright){
$.ajax({
    type: "post",
    url: "/emall/seller/refund/cheak.json",
    data: date,
    dataType: "json",   
    success: function(res){
    	$("#refund").foundation("reveal", "close");
    	if(res.refund){
    		if(res.refund.status=='0'){
    			
    			window.open("/emall/pay/dorefund.htm?money="+res.refund.refundSum+"&refundId="+res.refund.refundId+"");
    			$("#delete_warning").foundation("reveal", "open");
    		}else if(res.refund.status=='2'){
    	    	$("#user_page").getDtPage().datatable.draw(false);
    		}else{
    			$(".v_info_fail").addClass("show");
        		$(".v_info_fail").find(".v_show_info").text("您已退款");
        		setTimeout(function() {
        			alert("您已退款成功");
        			location.reload();
        		}, 3000);
    		
    		}
    	}else{
    		$(".v_info_fail").addClass("show");
    		$(".v_info_fail").find(".v_show_info").text("价格格式错误或者退款金额高于原价");
    		setTimeout(function() {
    			$panel.removeClass("show");
    		}, 3000);
    	
    	}
             }
});
}
}

$(".v_oper_cheak").click(function(){
	cheak($(".v_oper_cheak"),'1');
});
$(".v_oper_uncheak").click(function(){
	cheak($(".v_oper_uncheak"),'2');
});

function submitRefund(e) {
date=$('form').serializeObject();

	$.ajax({
	    type: "get",
	    url: "/emall/buyer/refund/save.json",
	    data: date,
	    dataType: "json",   
	    success: function(res){
	    	if('0'==res.ratcode){
	    	    window.location.href="/emall/buyer/refund/details.htm?orderId="+date.orderId;
	    	}else{
	    		alert(res.result);
	    	}
	             }
	});
}


$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [o[this.name]];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
}