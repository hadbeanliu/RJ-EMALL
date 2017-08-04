//返回头部、底部
function goTop() { window.scroll(0, 0)}
function goBottom(){var bottomH=Math.max(document.body.scrollHeight,document.documentElement.scrollHeight);window.scroll(0,bottomH);}


//返回头部、底部
$(function () {
	$(".gotop").click(/*定义返回顶部点击向上滚动的动画*/
	function () {
		$('html,body').animate({ scrollTop: 0 }, 700);
	});
	$(".gohome").click(/*定义返回顶部点击向上滚动的动画*/
	function(){
		/*HomepageFavorite.Homepage();*/
	});
	$(".gobottom").click(/*定义返回顶部点击向上滚动的动画*/
	function () {
		var bottomH=Math.max(document.body.scrollHeight,document.documentElement.scrollHeight);window.scroll(0,bottomH);
	});
	$(document).scroll(
		function(){
			if($(document.body).scrollTop() + $(document).scrollTop() > 300 && $(document.body).scrollTop() + $(document).scrollTop() < 300){
				$(".go").css("display","block");
				$(".gobottom").css("display","block"),$(".gotop").css("display","none");
			}else if($(document.body).scrollTop() + $(document).scrollTop() > 300){
				$(".go").css("display","block");
				$(".gobottom").css("display","none"),$(".gotop").css("display","block");				
			}else{
				$(".go").css("display","none");
			}
	    }
	);
});

//网站导航
$(function () {
	var _dropBar = $('.drop-bar');
	_dropBar.bind('click',function(){
		_dropId = $(this).attr('id');
		_dropBarTit = $(this).find(".drop-bar-tit");
		_dropBarCon = $(this).find(".drop-bar-con");
		$(".drop-bar-tit").not(_dropBarTit).removeClass('active');
		$(".drop-bar-con").not(_dropBarCon).slideUp(100);
		_dropBarTit.toggleClass('active');
		_dropBarCon.slideToggle(100);
		$(document).bind('click',function(e){
			var e = e || window.event; //浏览器兼容性 
			var elem = e.target || e.srcElement; 
			while (elem) { //循环判断至根节点，防止点击的是div子元素 
				if ( elem.id && elem.id == _dropId ) { 
					return; 
				} 
				elem = elem.parentNode; 
			} 
			_dropBarTit.removeClass('active');
			_dropBarCon.slideUp(100); 
		}); 
	});
})


