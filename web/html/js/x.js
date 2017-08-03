

//tab
function selectTag(showContent,selfObj){
	var tag = document.getElementById("tags").getElementsByTagName("li");
	var taglength = tag.length;
	for(i=0; i<taglength; i++){
		tag[i].className = "";
	}
	selfObj.parentNode.className = "hover";
	for(i=0; j=document.getElementById("tagContent"+i); i++){
		j.style.display = "none";
	}
	document.getElementById(showContent).style.display = "block";
}


//閰嶈浇鍙戣溅
$(function(){

	//椤甸溃锷犺浇
	var $window = $(window),
		$doc = $(document),
		$body = $("body"),
	    winWidth = $window.width(),
		docWidth = $doc.width(),
		docHeight = $doc.height(),
		winHeight = $window.height(),
		$minute = $(""),
		$container = $(""),
		minuteHeight = $minute.height(),
		afterheadHeight = $("").height()+$("").height()+30,
		speed = 250;
	
	//璋幂敤tips
	fnTips(fnEach($("*[tips]")),speed);

	// tabs璋幂敤
	function fnEach(Dom){
		if(Dom.length !=0 ){
			return Dom;
		} else {
			return $(null);
		};
	};
	
	//tips
	function fnTips(list,speed){
		if(list.length === 0) { return false; };
		var tipsDom = "<div class='jctips' style=\"display:none;\"><span></span><b></b><em></em></div>";
		$body.append(tipsDom);
		var $tips = $(".jctips"),
		    $text = $tips.find("span");
		list.css("cursor","pointer")
		    .bind("mousemove",function(e){
			var _self = $(this),
			    tipsText =  _self.attr("tips"),
				X = e.pageX + 30,
				Y = e.pageY - 10;
			$tips.css({"left":X,"top":Y}).find("span").text(tipsText).parents($tips).show();
		}).bind("mouseleave",function(){
			$tips.hide();
		});
		return false;
	};
	
	
});


//鎻愮ず notice_tip
$(function(){
		$(".notice_tip").hover(function(){
			$(this).find("span.notice_tipnr").show();
		},function(){
			$(this).find("span.notice_tipnr").hide();
		});
	});



//input 涓嬫媺阃夋嫨
jQuery.fn.selectCity = function(targetId) {
	var _seft = this;
	var targetId = $(targetId);

	this.click(function(){
		var A_top = $(this).offset().top + $(this).outerHeight(true);  //  1
		var A_left =  $(this).offset().left;
		targetId.bgiframe();
		targetId.show().css({"position":"absolute","top":A_top+"px" ,"left":A_left+"px"});
	});

	targetId.find("#selectItemClose").click(function(){
		targetId.hide();
	});

	targetId.find("#selectSub :checkbox").click(function(){		
		targetId.find(":checkbox").attr("checked",false);
		$(this).attr("checked",true);	
		_seft.val( $(this).val() );
		targetId.hide();
	});

	$(document).click(function(event){
		if(event.target.id!=_seft.selector.substring(1)){
			targetId.hide();	
		}
	});

	targetId.click(function(e){
		e.stopPropagation(); //  2
	});

    return this;
}
 
$(function(){
	//test1:
	$("#address").selectCity("#selectItem");
	//test2锛?
	$("#address2").selectCity("#selectItem2");
});




