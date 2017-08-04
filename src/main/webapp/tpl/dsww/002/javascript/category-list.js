$(document).ready(function(){
	var $category = $('#category'),
	    $first = $category.find('.first'),
	    $second = $category.find('.sub-category').find('.second'),
	    relativeTop = $category.parent().offset().top;

	    $first.find('li').each(function(index){
	    	var $this = $(this);
	    	$this.mouseover(function(){
	    		var catTop,
	    		    borderTop = $this.offset().top,
					viewHeight = $( window ).height(),
					scrollTop = $( document ).scrollTop(),
					relaxHeight = viewHeight - ( borderTop - scrollTop );

	    		$this.siblings().removeClass('hover');
	    		$this.addClass('hover');

	    		$second.find('li').removeClass('active');
	    		$second.find('li').eq(index).addClass('active');

	    		var secondHeight = $second.height();

	    		if( secondHeight <= relaxHeight ){
					catTop = borderTop - relativeTop - 60;
				}else if( secondHeight > relaxHeight && secondHeight < viewHeight ){
					catTop = scrollTop + viewHeight - secondHeight - relativeTop - 60;
				}else{
					catTop = scrollTop - relativeTop - 60;
				}

				if(document.body.clientWidth > 640){
					$second.parents('.sub-category').css({'top':catTop});
				}
				

	    		$category.mouseleave(function(){
	    			$first.find('li').removeClass('hover');
	    			$second.find('li').removeClass('active');
	    		});
	    	});
	    });
	
	$('.category-btn').click(function(){
		$('.top-nav-box').find('.top-nav').slideUp(200);
		$category.toggleClass('show');
		$('.main').toggle();
	});

	$('.banner-img').click(function(){
		$category.removeClass('show');
		$('.top-nav-box').find('.top-nav').slideToggle(200);
	});

	$('.suspension-menu').on('click','.menu-btn',function(){
		$(this).parent().toggleClass('hide');
	});


});