var $=jQuery.noConflict();

$(function(){
     
   /* top button */
    var wrapBox = $('.wrapper').outerHeight();
    var inbox = $('.inbox-scroll').outerHeight();
    $('.inbox-scroll').each(function(){
        if (wrapBox > inbox) {
            //console.log(inbox+', '+wrapBox);
            $('a.top').show();
        }
    }).on('scroll', function() {
        
    });
    
    $('a.top').on('click', function(e){
        e.preventDefault();
        $('.inbox-scroll').stop().animate({
            scrollTop:0
        }, 400);
    });
    
    /* tab menu */
    var tab_m = $('.tab-menu');
    var tab_sel = tab_m.find('ul>li>a');
    tab_sel.on('click', function(e){
        e.preventDefault();
        var t = $(this).parent();
        var path = $(this).attr('href');
        //console.log(path);
        tab_sel.parent().removeClass('on');
        t.addClass('on');    
        $('.inbox-scroll').not(path).removeClass('on');
        $(path).addClass('on');    
    });
    var tab_btn = $('.t-m').find('>a');
    var tab_msg = $('.t-m').find('.notice');
    tab_btn.on('click', function(e){
        e.preventDefault();
        var path = $(this).attr('href');
        var t = $(this).index();
        //console.log(path);
        tab_msg.removeClass('sel');
        tab_msg.eq(t).addClass('sel');
        tab_btn.removeClass('sel');
        $(this).addClass('sel');    
        $('.private').not(path).removeClass('sel');
        $(path).addClass('sel');    
    });
    
    /* dropdown popup */
    $('.select-box').on('click', function(e){
        e.preventDefault();
        var selBox = $(this);
        var path = $(this).attr('href');
        var dropdown = $(path).find('ul li');
        
        $('.select-box').removeClass('focus');
        $(this).addClass('focus');
        $(path).show();
        $('.dim').show();        
        
        dropdown.each(function(){
            $(this).each(function(){
                $(this).find('a').on('click', function(){
                    var txt = $(this).text();
                    $(this).parent().addClass('active').siblings().removeClass('active');
                    selBox.text(txt);
                    $(path).hide();
                    $('.dim').hide();
                });
            });
        });
        $('input').on('focus', function(){
            selBox.removeClass('focus');
        });
        $('.dim').on('click', function(){
            $('.dropdown-container').hide();
            $('.dim').hide();
        });
    });
 });