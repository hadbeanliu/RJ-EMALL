$(function(){
	function preventDefault(e) {
		e.preventDefault(); 
	};
	$(document).on('open.fndtn.reveal', '[data-reveal]', function (e) {
		scrollHide();
	}).on('closed.fndtn.reveal', '[data-reveal]', function () {
		scrollShow();
	});
	
	$(document).on('open.fndtn.offcanvas', '[data-offcanvas]', function() {
		var _h = $(window).height();
		$('.inner-wrap').css('height',_h);
	}).on('close.fndtn.offcanvas', '[data-offcanvas]', function() {
		$('.inner-wrap').removeAttr('style');
	})
	
	var divSize = $(".modal_wrap").size();

	function scrollShow() {
		$('body').removeAttr('style');
		$('.header').removeAttr('style');
		$('.main').removeAttr('style');
		$('.footer').removeAttr('style');
		document.body.removeEventListener('touchmove', preventDefault, false);
		//enableMouseWheel();
		if(document.body.scrollWidth < 768){
			for(i=0;i<divSize;i++){
				$(".modal_wrap").removeAttr('style');
			}
		}
	}
	
	function scrollHide() {
		$('body').css('overflow','hidden');
		$('.header').css('padding-right','17px');
		$('.main').css('padding-right','17px');
		$('.footer').css('padding-right','17px');
		document.body.addEventListener('touchmove', preventDefault, false);
		//disabledMouseWheel();
		if(document.body.scrollWidth < 768){
			for(i=0;i<divSize;i++){
				var _h = $(window).height();
				$(".modal_wrap").css('height', _h - 120 + 'px');
			}
		}
	}
	
	function disabledMouseWheel() {
		if (document.addEventListener) {
			document.addEventListener('DOMMouseScroll', scrollFunc, false);
		}//W3C
		window.onmousewheel = document.onmousewheel = scrollFunc;//IE/Opera/Chrome
	}
	function enableMouseWheel() {
		if (document.removeEventListener) {
			document.removeEventListener('DOMMouseScroll', scrollFunc, false);
		}//W3C
		window.onmousewheel = document.onmousewheel = null;  //IE/Opera/Chrome
	}
	function scrollFunc(evt) {
		evt = evt || window.event;
		if(evt.preventDefault) {
			// Firefox
			evt.preventDefault();
			evt.stopPropagation();
		} else {
			// IE
			evt.cancelBubble=true;
			evt.returnValue = false;
		}
		return false;
	}
	
	/*阻止滚动条联动
	$(function(){
		var SmoothScroll = function (win, opt) {   
			//操作对象   
			this.win = win; 
			//每次滚动位移 
			this.step = opt ? opt.step || 180 : 180;   
			//缓动系数   
			this.f = opt ? opt.f || 0.1 : 0.1;   
			this.interval = 10;   
			this.intervalID = null;   
			this.isFF = navigator.userAgent.toLowerCase().indexOf("firefox") >= 0;   
			this.upOrDown = "";   
			this.init();   
		}   
		SmoothScroll.prototype = {   
			init: function () {   
				var _this = this;   
				if (_this.isFF) {   
					_this.win.addEventListener('DOMMouseScroll', function (e) {   
						_this.upOrDown = e.detail < 0 ? "up" : "down";   
						_this.scrollHander();   
						e.preventDefault();   
					}, false);   
				} else {   
					_this.win.onmousewheel = function (e) {   
						e = e || window.event;   
						_this.upOrDown = e.wheelDelta > 0 ? "up" : "down";   
						_this.scrollHander();   
						e.returnValue = false;   
					}   
				}   
			},scrollHander: function () {   
				var _this = this;   
				clearInterval(_this.intervalID);   
				//目标位置   
				var tar = _this.win.scrollTop + _this.step * (_this.upOrDown == "up" ? -1 : 1);   
				_this.intervalID = setInterval(function () {   
					//缓动   
					_this.win.scrollTop += (tar - _this.win.scrollTop) * _this.f;   
					if (tar == _this.win.scrollTop) {   
						clearInterval(_this.intervalID);   
					}   
				}, _this.interval);   
			}   
		} 
		for(i=0;i<divSize;i++){
			var div = $(".modal_wrap")[i];
			var opt = {step:200,f:0.2};
			new SmoothScroll(div,opt);
		}
	})*/

})	