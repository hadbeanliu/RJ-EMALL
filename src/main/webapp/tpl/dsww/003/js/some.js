$(function () {

    $('.email-reg-box').hide();
    //var isOpen=false;
    $('.phone-reg').click(function(){
        $('.phone-reg-box').show();
        $('.email-reg-box').hide();

        $('.email-reg').removeClass('border-tab');
        $(this).addClass('border-tab');

    });
    $('.email-reg').click(function(){
        $('.phone-reg-box').hide();
        $('.email-reg-box').show();

        $('.phone-reg').removeClass('border-tab');
        $(this).addClass('border-tab');
    });
    //添加小图标样式
    $('.user-bg').css({'background':'url(/emall/tpl/dsww/003/img/reg-6.png) 10px 9px no-repeat','padding-left':'35px'});
    $('.phone-bg').css({'background':'url(/emall/tpl/dsww/003/img/reg-3.png) 10px 9px no-repeat','padding-left':'35px'});
    $('.email-bg').css({'background':'url(/emall/tpl/dsww/003/img/reg-8.png) 10px 9px no-repeat','padding-left':'35px'});
    $('.lock-bg').css({'background':'url(/emall/tpl/dsww/003/img/reg-7.png) 10px 9px no-repeat','padding-left':'35px'});

    $('.user-bg').focus(function(){
        $('.user-bg').css('background-color','#fafafa');
    })
    $('.user-bg').focusout(function(){
        $('.user-bg').css('background-color','white');
    })

    $('.phone-bg').focus(function(){
        $(this).css({'background-color':'#fafafa'});
    })
    $('.phone-bg').focusout(function(){
        $(this).css({'background-color':'white'});
    })
    $('.email-bg').focus(function(){
        $(this).css({'background-color':'#fafafa'});
    })
    $('.email-bg').focusout(function(){
        $(this).css({'background-color':'white'});
    })
    $('.lock-bg').focus(function(){
        $(this).css({'background-color':'#fafafa'});
    })
    $('.lock-bg').focusout(function(){
        $(this).css({'background-color':'white'});
    })

    //登入界面登入方法登入方式切换
    $('.login-normal').hide();
    $('.phone-in-one-a').click(function(){
        $('.login-normal').show();
        $('.login-verification').hide();
    });

    $('.phone-in-two-a').click(function(){
        $('.login-normal').hide();
        $('.login-verification').show();
    });

    $('.btn-check').focus(function(){
        $(this).css({'box-shadow':'inset 0px 3px 5px rgba(0, 0, 0, 0.125)','border':'none'});
    });

    $(function () {
        var mWidth=window.matchMedia("(max-width:768px) and (min-width:320px)");
        if(mWidth.matches){
            $('.padding-right-none').css('padding-right','0px');
            //$('.row>div:last-child').css('width','100%');
        }else{
            $('.padding-right-none').css('padding-right','0px');
        };

    });


    $(function () {
        var mWidth=window.matchMedia("(max-width:360px) and (min-width:320px)");
        if(mWidth.matches){
            $('.min-sma-pad').css('padding','0px');
        }else{
            $('.pmin-sma-pad').css('none');
        };

    })



})
