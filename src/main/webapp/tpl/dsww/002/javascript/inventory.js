var standardId="";

function getsum(classname, goodsId) {
	$.ajax({
		type : "post",
		url : "/emall/Inventory/sum.json",
		data : {
			"goodsId" : goodsId
		},
		dataType : "json",
		success : function(res) {
			$("." + classname).html(res.sum);
		}
	});

}
function getInventoryNumsum(classname, inventorySearchId, goodsId) {
	$.ajax({
		type : "post",
		url : "/emall/Inventory/InventoryNum.json",
		data : {
			"inventorySearchId" : inventorySearchId,
			"goodsId" : goodsId
		},
		dataType : "json",
		success : function(res) {
			$("." + classname).html(res.inventory);
		}
	});

}

function getStandardHtml(res) {
	standard = res.wsStandardData.selectedStandards;
	html = ""
	if (standard && standard.length > 0) {
		for ( var i = 0; i < standard.length; i++) {
			var itemlist = standard[i].itemList;
			if (itemlist && itemlist.length > 0) {
				html = html+"<div class='goods-size clearfix'><div class='size-tit '>"
						+ standard[i].title
						+ "</div><div class='size-con param-"+i+"'><ul class='clearfix'>";
				for ( var j = 0; j < itemlist.length; j++) {
					if (itemlist[j].uploadImageThumb) {
						html = html + "<li class='v_vary' id='" + itemlist[j].id
								+ "'><span><img src='"
								+ itemlist[j].uploadImageThumb + "' >"
								+ itemlist[j].title + "</span></li>"
					} else {
						html = html + "<li  class='v_vary' id='"  + itemlist[j].id + "'><span>"
								+ itemlist[j].title + "</span></li>"
					}
				}
				html = html + "</ul></div></div>"
			}

		}
	}
	return html;
}

function submitstandard() {
	standardId=""
	list=$(".v_standard")[1];
	ul=$(list).find("ul");
	boolean=false;
	for ( var  i= 0; i < ul.length; i++) {
		li=$(ul[i]).find("li");
		for(var  j= 0; j < li.length; j++){
			isselecet=$(li[j]).attr("class");
			//console.log(isselecet);
			if(isselecet){
			if(isselecet.indexOf("selected")>-1){
				if(i!=(ul.length-1)){
					standardId=standardId+$(li[j]).attr("id")+"_";
				}else{
					standardId=standardId+$(li[j]).attr("id");
				}
				break;
			}}
			if(j==li.length-1){
				return false;
			}
			
		}
		
	}
	return true;
}
function vary(){
	if(submitstandard()){
		inventorySearchId="2015061215000001_"+standardId;
		getInventoryNumsum("v_inventory",inventorySearchId,"2015061215000001");
	}
}

function selectProp(){
	var $gpJs = $('.gp-js');
		
	$gpJs.find('li:not(.no)').each(function(){
		$(this).click(function(){
			var index = $(this).index();
			$(this).siblings().removeClass('selected');
			$(this).addClass('selected');
			
			var $propEle = $(this).parents('.prop'),
				parentEle = /param-\d/.exec($(this).parent().parent()[0].className)[0];
				
			if($propEle.hasClass('spe-con')){
				var $target = $('.goods-prop').find('.' + parentEle).children().children();
				$target.removeClass('selected');
				$target.eq(index).addClass('selected');
			}else if($propEle.hasClass('goods-prop')){
				var $target = $('.spe-con').find('.' + parentEle).children().children();
				$target.removeClass('selected');
				$target.eq(index).addClass('selected');
			}
			vary();
		});
	});
}
$(function() {
	$.ajax({
		type : "post",
		url : "/emall/tpl/002/goods.json",
		data : {
			"goodsId" : "2015061215000001"
		},
		dataType : "json",
		success : function(res) {
			$(".v_standard").html(getStandardHtml(res));
			selectProp();
		}
	});
	getsum("v_inventory","2015061215000001");
	

	$('.addCart').on('click',function(){
		addCart();
	})
});
function getBuyInfo(){
	var  obj= new Object();
	
	if(submitstandard()){
	obj["inventorySearchId"]="2015061215000001_"+standardId;;
	obj["num"]=$(".group-inp").val();
	return obj
	}else{
		return null;
	}

	
}


function addCart() { // 添加到购物车
	
	var getBuyInfo=this.getBuyInfo();
	if(getBuyInfo==null){
		alert("请选择!");
	}else{
    $('#cartSuccess').foundation('reveal', 'open');
    var goodsId="2015061215000001";
    var shopId="00000002";
	   $.ajax({
         type: "POST",
         dataType: 'json',
         data: {           
             "goods.goodsId": goodsId,
             "store.storeId":shopId,
             "inventory.inventorySearchId": getBuyInfo.inventorySearchId,
             "buyNumber":getBuyInfo.num
         },
         url: "/emall/buyer/mycart/v_save.json",
         success: function(data) {  
      	 
            if(data.id!=""){
              alert("添加购物车成功");
            //显示加入成功层
              //data-reveal-id="cartSuccess"
              $('#cartSuccess').foundation('reveal', 'open');
           } 
          
         },error:function(error){
      	 alert(error);  
         }
     });
	}
}
