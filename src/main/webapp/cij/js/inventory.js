var standardId="";

function getsum(classname, goodsId) {
	$.ajax({
		type : "get",
		url : "http://192.168.4.147:8080/emall/inventory/sum.json",
		data : {
			"goodsId" : goodsId
		},
		dataType : "json",
		success : function(res) {
			$("." + classname).html(res.sum);
		},error:function(e) {
		$("." + classname).html('12');

		}
	});

}
function getInventoryNumsum(classname, skuId, goodsId) {
	$.ajax({
		type : "get",
		url : "http://192.168.4.147:8080/emall/inventory/inventoryNum.json",
		data : {
			"skuId" : skuId,
			"goodsId" : goodsId
		},
		dataType : "json",
		success : function(res) {
			$("." + classname).html(res.inventory.inventory);
			$(".v_price").html(res.inventory.goodsPrice);
			goodsPrice=res.inventory.goodsPrice;
            if(res.inventory.inventory=='0'){
            alert('该型号缺货')
            }
		},error:function(e) {
			$("." + classname).html('7');
			$(".v_price").html('1234');
		}
	});

}

function getStandardHtml(res) {
	standard = res;
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
	//console.log(111111);
	console.log(submitstandard())
	if(submitstandard()){
			var id = rj.getQueryString("id");
		skuId=id+"_"+standardId;
		getInventoryNumsum("v_inventory",skuId,id);
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

		 function init(res,id) {
			$(".v_standard").html(getStandardHtml(res));
				selectProp();
				getsum("v_inventory",id);
				$('.addCart').on('click',function(){
				addCart();
		})

}
function buy(){
	
	if(rj.islogin()){
		
	}else{
		rj.openNativeWindow('login2.html', false);
	}
}

function getBuyInfo(){
	var  obj= new Object();

	if(submitstandard()){
	obj["skuId"]=rj.getQueryString("id")+"_"+standardId;
	obj["num"]=$(".group-inp").val();
        obj["goodsId"]=rj.getQueryString("id");
	return obj
	}else{
        alert('未选择类型')
		return null;
	}

}



