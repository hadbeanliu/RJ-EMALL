;
(function(){
	var $commentImg= $('.comment-item .comment-img');

	$commentImg.each(function(){
		var $this = $(this),
    	    $commentLi = $this.find('.comment-list li'),
    		$img = $commentLi.find('img'),
    		
    		rotateCount = 0,
    		contentSwiper,navSwiper;

		var $picGalleryWrapper = $('<div class="picGalleryWrapper comment"><div class="swiper-container"></div></div>'),
		    $rotate = $('<div class="rotate"><a href="#" class="rotate-left"></a><a href="#" class="rotate-right">向右旋转</a></div>'),
		    $pagination = $('<div class="pagination"></div>'),
			$closeGallery = $('<a class="closeGallery"></a>'),
			$swiperBtn = $('<div class="swiper-btn"><a class="arrow-left"></a><a class="arrow-right"></a></div>'),
	        $swiperContent= $('<div class="swiper-content"><div class="swiper-wrapper"></div></div>'),
	        $swiperNav = $('<div class="swiper-nav"><div class="swiper-wrapper"></div></div>');
	       
	        	
	    init();

    	function init(){
    		var $swiperSlide,$slideTemp,imgL,
    			sps = '<span class="swiper-pagination-switch"></span>';
			for(var i = 0; i < $commentLi.length; i++){
				imgL = '<img src=' + $img[i].src + '>';
				$swiperSlide = $('<div class="swiper-slide"><div class="inner"></div></div>');
				$slideTemp = $('<div class="swiper-slide"></div>');
	    		$swiperSlide.find('.inner').append(imgL);
	    		$slideTemp.append(imgL);
	    		$pagination.append(sps);
	    		$swiperContent.find('.swiper-wrapper').append($swiperSlide[0]);
	    		$swiperNav.find('.swiper-wrapper').append($slideTemp[0]);
	    	}

	    	$picGalleryWrapper.find('.swiper-container').append($rotate[0]).append($pagination[0])
	    	    .append($closeGallery[0]).append($swiperBtn[0]).append($swiperContent[0]).append($swiperNav[0]);

	    	$picGalleryWrapper.attr('id','cis_' + genRNumber(6));


	    	$this.append($picGalleryWrapper[0]);

	    	showSwiper();
		};

        function initSwiper(num){
        	contentSwiper = new Swiper('#' + $picGalleryWrapper[0].id + ' .swiper-content', {
        		useCSS3Transforms : false,
				simulateTouch: false,
				watchActiveIndex : true,
		        pagination: '#' + $picGalleryWrapper[0].id + ' .pagination',
				paginationClickable: true,
				initialSlide: num,
				onSlideChangeStart: function(){
				    updateNavPosition();
					rotateCount = 0;
					$('#' + $picGalleryWrapper[0].id + ' .swiper-content .swiper-slide img').removeAttr('style');
				}
	        });

	        navSwiper = $('#' + $picGalleryWrapper[0].id + ' .swiper-nav').swiper({
				visibilityFullFit: true,
				slidesPerView:'auto',
				initialSlide: num,
				onSlideClick: function(){
					contentSwiper.swipeTo( navSwiper.clickedSlideIndex )
				}
			});
            $swiperNav.find('.swiper-slide').eq(num).addClass('active-nav');

		    $('#' + $picGalleryWrapper[0].id + ' .arrow-left').on('click', function(e){
				e.preventDefault();
				contentSwiper.swipePrev();
			});
			$('#' + $picGalleryWrapper[0].id + ' .arrow-right').on('click', function(e){
				e.preventDefault();
				contentSwiper.swipeNext();
			});
	    };
		function updateNavPosition(){
			$('#' + $picGalleryWrapper[0].id + ' .swiper-nav .active-nav').removeClass('active-nav')
			var activeNav = $('#' + $picGalleryWrapper[0].id + ' .swiper-nav .swiper-slide').eq(contentSwiper.activeIndex).addClass('active-nav');
			if (!activeNav.hasClass('swiper-slide-visible')) {
				if (activeNav.index() > navSwiper.activeIndex) {
					var thumbsPerNav = Math.floor(navSwiper.width/activeNav.width())-1
					navSwiper.swipeTo(activeNav.index()-thumbsPerNav)
				}
				else {
					navSwiper.swipeTo(activeNav.index())
				}	
			}
		};

	    function showSwiper(){
        	$commentLi.on('click',function(){
        		$picGalleryWrapper.show();
        		
        		if(detectEq()){
        			$('.wrapper').css({'overflow':'hidden','height':'100%'});

        			document.body.addEventListener('touchmove', function(e){
				    	e.preventDefault();
				    } ,false);

        		}else{
        			$(document.body).css('overflow','hidden');
        		}
        		
        		initSwiper($(this).index());
        		resizeGallery();
        		
        	});
	    };

	    function detectEq(){
	    	if(navigator.userAgent.match(/Android/i) || (navigator.userAgent.indexOf('iPhone') != -1) || (navigator.userAgent.indexOf('iPod') != -1) || (navigator.userAgent.indexOf('iPad') != -1)){
	    		return true;
	    	}

	    };

	    function setRotate($ele, str){
	    	if(str === 'left'){
	    		$ele.css("transform", "rotate(" + 90*(++rotateCount) + "deg)");
	    	}else{
				$ele.css("transform", "rotate(" + 90*(--rotateCount) + "deg)");
	    	}
	    };

	    function genRNumber(num){
			var str = '';
			for(var i = 0; i < num; i += 1){
			    str += Math.floor(Math.random() * 10);
			}
			return str;
		};

	    $rotate.on('click','.rotate-left',function(e){
	    	e.preventDefault();
	    	var $rorateImg = $swiperContent.find('.swiper-slide-active img');
	    	setRotate($rorateImg, 'left');
	    })
	    .on('click','.rotate-right',function(e){
	    	e.preventDefault();
	    	var $rorateImg = $swiperContent.find('.swiper-slide-active img');
	    	setRotate($rorateImg, 'right');
	    });

	    $closeGallery.on('click',function(e){
	    	hideSwiper();
	    	rotateCount = 0;
	    });

	    function hideSwiper(){
	    	$swiperNav.find('.swiper-slide').removeClass('active-nav');
	    	$picGalleryWrapper.hide();
	    	if(detectEq()){
	    		$('.wrapper').removeAttr('style');

	    		document.body.removeEventListener('touchmove', function(e){
			    	e.preventDefault();
			    } ,false);

	    	}else{
	    		$(document.body).removeAttr('style');
	    	}
        	contentSwiper.destroy(false);
        	navSwiper.destroy(false);
	    };

	    
    	$swiperContent.on('click','.swiper-slide img',function(e){
	    	hideSwiper();
	    });
	    

	    function setContentSize() {
			if(document.body.scrollWidth < 769){
				$swiperContent.css({ height: $(window).height()});
			} else {
				$swiperContent.css({height: ($(window).height() - $swiperNav.height())});
			}
		};

		$(window).resize(function(){
			setContentSize();
		});
	    
    	function resizeGallery(){
			setContentSize();
			contentSwiper.resizeFix();
			navSwiper.resizeFix();
		}
	});
}());

