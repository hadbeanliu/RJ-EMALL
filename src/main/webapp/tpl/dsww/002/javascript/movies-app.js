$(function(){
	var  picID = '#pic_gallery';
	function setContentSize() {
		if($('.picGallery').children()[0] == undefined && document.body.scrollWidth < 769){
			$(picID + ' .swiper-content').css({
				height: $(window).height()
			});
		} else {
			$(picID + ' .swiper-content').css({height: ($(window).height() - $(picID + ' .swiper-nav').height())});
		}
	};

	setContentSize();
	$(window).resize(function(){
		setContentSize();
	});
    
	//Swiper Content
	var contentSwiper = $(picID + ' .swiper-content').swiper({
		useCSS3Transforms : false,
		simulateTouch: false,
		watchActiveIndex : true,
		pagination: picID + ' .pagination',
		paginationClickable: true,
		onSlideChangeStart: function(){
			updateNavPosition();
			rotateCount = 0;
			$(picID + ' .swiper-content .swiper-slide img').removeAttr('style');
		}
	})
	//Nav
	var navSwiper = $(picID + ' .swiper-nav').swiper({
		visibilityFullFit: true,
		slidesPerView:'auto',
		onSlideClick: function(){
			contentSwiper.swipeTo( navSwiper.clickedSlideIndex )
		}
	})

	//Update Nav Position
	function updateNavPosition(){
		$(picID + ' .swiper-nav .active-nav').removeClass('active-nav')
		var activeNav = $(picID + ' .swiper-nav .swiper-slide').eq(contentSwiper.activeIndex).addClass('active-nav');
		if (!activeNav.hasClass('swiper-slide-visible')) {
			if (activeNav.index()>navSwiper.activeIndex) {
				var thumbsPerNav = Math.floor(navSwiper.width/activeNav.width())-1
				navSwiper.swipeTo(activeNav.index()-thumbsPerNav)
			}
			else {
				navSwiper.swipeTo(activeNav.index())
			}	
		}
	}
	$(picID + ' .arrow-left').on('click', function(e){
		e.preventDefault()
		contentSwiper.swipePrev()
	})
	$(picID + ' .arrow-right').on('click', function(e){
		e.preventDefault()
		contentSwiper.swipeNext()
	})
	
	/*关闭图片弹出层*/
	$(picID + ' .closeGallery').bind('click',function(){
		closeGallery();
	})

	$(picID + ' .movie-pic').bind('click',function(){
		if($('.picGallery').children()[0] !== undefined){
			/*打开图片弹出层*/
			openGallery();
		} else {
			/*关闭图片弹出层*/
			closeGallery();
		}
	})
	
	var $picGalleryWrapper;
	function openGallery(){
		$('body').css('overflow','hidden');
		$picGalleryWrapper = $('<div class="picGalleryWrapper pic"></div>');
		$('body').append($picGalleryWrapper[0]);
		$(picID).appendTo($picGalleryWrapper[0]);
		resizeGallery();
		document.body.addEventListener('touchmove', preventDefault ,false);	
	}	
	
	function closeGallery() {
		$('body').removeAttr('style');
		$(picID).appendTo($('.picGallery'));
		$picGalleryWrapper.remove();
		resizeGallery();
		document.body.removeEventListener('touchmove', preventDefault ,false);		
		$(picID + ' .swiper-content .swiper-slide img').removeAttr('style');
		rotateCount = 0;
	}
	
	function resizeGallery(){
		setContentSize();
		contentSwiper.resizeFix();
		navSwiper.resizeFix();
	}
	
	function preventDefault(e) { e.preventDefault(); };  
	
	/*图片旋转*/
	var rotateCount = 0;
	function setRotate($ele, str){
		if(str === 'left'){
			$ele.css({"transform":"rotate(" + 90*(--rotateCount) + "deg)","transition":"transform 1s"});
		}else{
			$ele.css({"transform":"rotate(" + 90*(++rotateCount) + "deg)","transition":"transform 1s"});
		}
	};
	$(picID + ' .rotate').on('click','.rotate-left',function(e){
		e.preventDefault();
		var $rorateImg = $(picID + ' .swiper-content').find('.swiper-slide-active img');
		setRotate($rorateImg, 'left');
	}).on('click','.rotate-right',function(e){
		e.preventDefault();
		var $rorateImg = $(picID + ' .swiper-content').find('.swiper-slide-active img');
		setRotate($rorateImg, 'right');
	})
	
})



