/**
 * 侧边栏 加载事件
 * 
 * 由于初始化的时候，侧边栏是隐藏的，事件添加不了,只能在侧边栏显示后再加载该方法
 * 
 */
 function sideBarInit(){
	$(".sidebar .toggle").click(function() { 

		if($(this).hasClass('active')) {
			$(this).removeClass('active')
			$('.sidebar .active').css({"float":"none"});
			$('.sidebar').animate({ left:-84 }, 100);
//			$('.main_content').css({"width":"auto", "margin-left":"77px"});
//			$('.sidebar .side_search').css({"text-align":"center"});
//			$('.sidebar li.li span').css({"display":"none"});
//			$('.sidebar .side_top a').css({"display":"inline-block", "float":"right"});
//			$('.sidebar li.li i').css({"left":"auto", "right":"10px"});
//			$('.side_search .btn_inverse').css({"left":"auto", "right":"5px"});
//			$('.side_search input').css({"margin-left":"auto"});
//			$('.side_search input').css({"margin-left":"110px"});
			$('.tooltip-tip').removeClass('tooltipster-disable');
			

			
		} else {
			$(this).addClass('active')
//			$('.sidebar .active').css({"float":"right"});
//			$('.sidebar').animate({ left:0 }, 100);	
//			$('.main_content').css({"width":"auto","margin-left":"77px"});
//			$('.sidebar li').css({"text-align":"left"});
//			$('.sidebar li span,.sidebar .side_top a').css({"display":"inline-block", "float":"right"});
//			$('.sidebar li.li i').css({"left":"10px", "right":"none"});
//			$('.sidebar li title').css({"display":"none"});
//			$('.side_search input').css({"margin-left":"0px"});
//			$('.tooltip-tip').addClass('tooltipster-disable');
//			$('.side_search .btn_inverse').css({"left":"3px", "right":"auto"});
//			$('.side_search input').css({"margin-left":"35px"});
			
		}
		return false;
	});
	
	setTimeout(function(){ $(".sidebar .toggle").addClass('active').trigger('click'); },1)
	


	
	$('.tooltip-tip').tooltipster({
        position: 'right',
        animation: 'slide',
        theme: '.tooltipster-shadow',
        delay: 1,
        offsetX: '-4px',
        onlyOne: true
    });
};

