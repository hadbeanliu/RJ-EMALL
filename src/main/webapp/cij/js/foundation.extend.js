$(function(){
	function preventDefault(e) {
		e.preventDefault(); 
	};
	$(document).on('open.fndtn.reveal', '[data-reveal]', function (e) {
		$('body').css('overflow','hidden');
		document.body.addEventListener('touchmove', preventDefault, false);
	});
	
	$(document).on('close.fndtn.reveal', '[data-reveal]', function () {
		$('body').removeAttr('style');
		document.body.removeEventListener('touchmove', preventDefault, false);
	});
})