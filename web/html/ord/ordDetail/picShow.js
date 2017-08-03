
/**
 * 鍗曚釜锲剧墖鏀惧ぇ
 */
commonApp.directive("picShow",['commonService',"$timeout",function(commonService,$timeout){
	return {
		restrict: 'E',
        scope: {
            "isShowIMgBig":"=isShow",
            "imageFullUrl" :"=isImage"
        },
//       templateUrl:'ordDetail/picShow.html',
        templateUrl : function(tElement,tAttrs){
        	return 'ordDetail/picShow.html?ver='+tAttrs.ver;
        },
        link: function($scope, elem, attrs) {
        	//鍏抽棴锲剧墖
        	$scope.loadcosle =  function(){
        		$scope.isShowIMgBig = false;
//        		var img = document.getElementById("picId");
//        		var n = img.getAttribute('step'); 
//        		if(n != null && n != 0 && n != undefined){
//        			for(var i = 0; i< parseInt(n);i++){
//            			$scope.imgRotate('picId','left');
//            		}
//        		}
        		
			};
			//镞嬭浆锲剧墖
			$scope.imgRotate = function(o,p){
				var img = document.getElementById(o);
			    if(!img || !p) return false; 
			    var n = img.getAttribute('step'); 
			    if(n== null) n=0; 
			    if(p=='right'){ 
			        (n==3)? n=0:n++; 
			    }else if(p=='left'){ 
			        (n==0)? n=3:n--; 
			    } 
			    img.setAttribute('step',n); 
			    //MSIE 
			    if(document.all) {
			        img.style.filter = 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ n +')'; 
			        //HACK FOR MSIE 8 
			        switch(n){ 
			            case 0: 
			                img.parentNode.style.height = img.height; 
			                break; 
			            case 1: 
			                img.parentNode.style.height = img.width; 
			                break; 
			            case 2: 
			                img.parentNode.style.height = img.height; 
			                break; 
			            case 3: 
			                img.parentNode.style.height = img.width; 
			                break; 
			        } 
			    //DOM 
			    }else{
			        var c = document.getElementById('canvas_'+o);
			        if(c==null || c==undefined){ 
			            img.style.visibility = 'hidden'; 
			            img.style.position = 'absolute'; 
			            c = document.createElement('canvas'); 
			            c.setAttribute("id",'canvas_'+o); 
			            img.parentNode.appendChild(c); 
			        } 
			        var canvasContext = c.getContext('2d');
			        switch(n) { 
			            default : 
			            case 0 : 
			                c.setAttribute('width', img.width); 
			                c.setAttribute('height', img.height); 
			                canvasContext.rotate(0 * Math.PI / 180); 
			                canvasContext.drawImage(img, 0, 0,img.width,img.height); 
			                break; 
			            case 1 : 
			                c.setAttribute('width', img.height); 
			                c.setAttribute('height', img.width); 
			                canvasContext.rotate(90 * Math.PI / 180); 
			                canvasContext.drawImage(img, 0, -img.height,img.width,img.height); 
			                break; 
			            case 2 : 
			                c.setAttribute('width', img.width); 
			                c.setAttribute('height', img.height); 
			                canvasContext.rotate(180 * Math.PI / 180); 
			                canvasContext.drawImage(img, -img.width, -img.height,img.width,img.height); 
			                break; 
			            case 3 : 
			                c.setAttribute('width', 465); 
			                c.setAttribute('height', 700); 
			                canvasContext.rotate(270 * Math.PI / 180); 
			                canvasContext.drawImage(img, -img.width, 0,img.width,img.height); 
			                break; 
			        } 
			    } 
			};
			
			//锲剧墖鍙树负澶у浘
			$scope.changeBigPic = function(src){
				if(src.indexOf("big") > 0){
					return false;
				}
				var srcArray=src.split("?");
				var urlImg = "";
				if(srcArray!=null && srcArray!=undefined){
					var srcArrayTow = srcArray[0].split("/");
					if(srcArrayTow!=null && srcArrayTow!=undefined ){
						if(srcArrayTow.length>1){
							for(var i=0;i<srcArrayTow.length;i++){
								if(i==(srcArrayTow.length-1)){
									var str=srcArrayTow[i];
									str=str.replace(".","_big.");
									urlImg+=str;
								}else{
									if(i==(srcArrayTow.length-1)){
										urlImg+=srcArrayTow[i]+"//";	
									}else{
										urlImg+=srcArrayTow[i]+"/";
									}
								}
							}
						}
					}
					$scope.imageFullUrl = urlImg;
				}
			};

        	$scope.$watch('isShowIMgBig',function(oldVal,newVal){
        		if(oldVal != newVal){
        			$timeout(function(){
        				if($scope.isShowIMgBig){
        					$scope.changeBigPic($scope.imageFullUrl);
        				}
        			},500);
        		}
        	});
        	
        }
	};
}]);





