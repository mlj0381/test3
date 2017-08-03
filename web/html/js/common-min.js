/**获取cookie**/
 function getCookie(name)
 {
	 var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	 if(arr=document.cookie.match(reg))
	 return decodeURIComponent(arr[2].replace("\"","").replace("\"",""));
	 else
	 return null;
 }

var urlManager={};
var userInfo={
		orgId:getCookie("orgId"),//orgId 加密
		orgName:getCookie("orgName"),
		userId:getCookie("userId"),//userId 加密
		userName:getCookie("userName"),
		cityName:getCookie("cityName"),
		userType:getCookie("userType"),
		orgName:getCookie("orgName"),
		tenantName:getCookie("tenantName"),
		logo:getCookie("logo")
};
var userType={
		//联运汇类型
		/**平台**/
		PLATFORM:1,
		/**专线**/
		 CHAIN:2,
		/**商家**/
		 BUSINESS:3,
		/**师傅**/
		 MASTER:4,
		/**司机**/
		 DRIVER:5,
		/**师傅合作商**/
		 MASTER_PARTNERS:6
}
	
var commonApp=angular.module("commonApp", []);
/**
 * 设置iframe里面的高度
 */

	
function setContentHeight(height) {
	if(isNaN(height)){
		height=0;
	}
	var id=window.top.getCurrentOpenId();
	var hg=document.getElementsByTagName("div")[0].scrollHeight;
	if(height!=undefined && height!=NaN){
		hg=hg+height;
	}
	window.top.document.getElementById('tab_id_'+id).style.height =hg+"px";
}


function displayTable(){
	
	 var body=window.parent.document.getElementsByTagName("body");
	 var height=body[0].clientHeight;
	 var Width=body[0].clientWidth;
	 try {
		 var ss=document.getElementsByClassName("search_table")[0].scrollHeight;
		 document.getElementsByClassName("table_height")[0].style.height=parseInt(height)-ss-180;
		 window.top.resizeTo(Width,height+170);
	 	} catch (e) {
	 		console.log("表格自适应报错");
	 	}
}

/**
 * table 自适应浏览器高度  $(".search_table ").outerHeight(true);
 */
function display(){
	
	 var body=window.parent.document.getElementsByTagName("body");
	 var height=body[0].clientHeight;
	 var Width=body[0].clientWidth;
	 try {
		 var ss=document.getElementsByClassName("search_table")[0].scrollHeight;
		 var cc=document.getElementsByClassName("chey_b_1")[0].scrollHeight;	 
		 document.getElementsByClassName("table_height")[0].style.height=parseInt(height)-ss-cc-160;
		 document.getElementsByClassName("table_height")[1].style.height=parseInt(height)-ss-cc-160;
//		 document.getElementById("size2").style.height=parseInt(height)-ss-cc-160;
		 window.top.resizeTo(Width,height+170);
	 	} catch (e) {
	 		console.log("表格自适应报错");
	 	}
	 
	 
}


function scrollTopTable(){
		var x = document.getElementsByClassName("table_height");
	    var i;
	    for (i = 0; i < x.length; i++) {
	        x[i].scrollTop = "0";
	    }
		$('.fixed-thead').css({"margin-top":"0"});
		$('.fixed-tfoot').css({"bottom":"0"});
//	document.getElementsByClassName('table_height')[0].scrollTop = 0;
}


function setContentHeigth(height){
	setTimeout(function(){
		setContentHeight(height);
	},500);
//	window.parent.document.getElementsByTagName("body")[0].style.overflow="auto";
}



function setContentHegth(height){
	setTimeout(function(){
//			setContentHeight(height);
			display();
			tableHeadSroll();
		},500);
}
function setContentHegthDelay(height){
	setTimeout(function(){
		display();
		setContentHeight(height); 
		tableHeadSroll();
		tableHeadSroll1();
		scrollTopTable();
	},500);
	
}
function tableHeadSroll1(){
	 function $(id){return document.getElementById(id);}
	    var size=$('size'),tb1=$('tb1');
	    if(size!=null&&size!=undefined){
	    	size.onscroll=function(){
		        tb1.style.marginTop = size.scrollTop*+1 +'px'
		       };
	    }
	    
}

function bind(el,name,fn){ //绑定事件
	return el.addEventListener?el.addEventListener(name,fn,false):el.attachEvent('on'+name,fn);
} 

function tableHeadSroll(){

 var size=document.getElementsByClassName("table_height")[0];
 var tb1=document.getElementsByClassName("fixed-thead")[0];
 var tb3=document.getElementsByClassName("fixed-tfoot")[0];
 if(size!=null&&size!=undefined){
	 bind(size,"scroll",function(){
	        tb1.style.marginTop = size.scrollTop*+1 +'px';	        
	        tb3.style.bottom = -(size.scrollTop*+1) +'px'; 
	       });
 }
 var size1=document.getElementsByClassName("table_height")[1];
 var tb11=document.getElementsByClassName("fixed-thead")[1];
 var tb31=document.getElementsByClassName("fixed-tfoot")[1];
 if(size1!=null&&size1!=undefined){
 	 bind(size1,"scroll",function(){
	    	   	tb11.style.marginTop = size1.scrollTop*+1 +'px';	        
		        tb31.style.bottom = -(size1.scrollTop*+1) +'px'; 
		       });
 }
 var size2=document.getElementsByClassName("table_height")[2];
 var tb12=document.getElementsByClassName("fixed-thead")[2];
 var tb32=document.getElementsByClassName("fixed-tfoot")[2];
 var tb33=document.getElementsByClassName("fixed-tfoot")[0];
 if(size2!=null&&size2!=undefined){
	
	 bind(size2,"scroll",function(){
		 	tb12.style.marginTop = size2.scrollTop*+1 +'px';	
	        if(tb32 != undefined){
	        	tb32.style.bottom = -(size2.scrollTop*+1) +'px'; 
	        }
	        if(tb33 != undefined){
	        	tb33.style.bottom = -(size2.scrollTop*+1) +'px'; 
	        }
	       });
	
 }

}

//get URL加密
function signUrl(orgiUrl) {
	var paramArray = new Array();
	var name,value,paramStr; 
	if (orgiUrl != undefined) {
		 var url = orgiUrl.substring(orgiUrl.lastIndexOf("/")+1);
		 if ((idx = url.indexOf("&")) > 0) {
			 paramStr = url.substring(0, idx);
			 var params = url.substring(idx+1).split("&");
			 for (var i in params) {
				 if (params[i].split("=")[1] !== "null" && params[i].split("=")[1] !== "") {
					 paramArray.push(params[i]);
				 }
			 }
		 } else {
			 paramStr = url;
		 }
	}
    if (paramArray.length > 0)
    	paramStr += "&"+ paramArray.sort().join("&");
        paramStr += "&sign=" +md5(paramStr+getCookie("token"));
    return paramStr;
}

/**
 * 设置内容高度为制定高度，height是多少就设置多少
 * @param height
 */
function setContentHeightWithSpecialHeight(height) {
	if(isNaN(height)){
		height=0;
	}
	var id=window.top.getCurrentOpenId();
	if(height==0){
		window.top.document.getElementById('tab_id_'+id).style.height ="100%";
	} else {
		window.top.document.getElementById('tab_id_'+id).style.height =height+"px";
	}
	tableHeadSroll();
}




/**
 * 回车 触发 绑定的属性的ngclick事件
 */
commonApp.directive("ngEnter",['commonService','$document',function(commonService,$document){
	return {
	    restrict:"A",
	    link:function(scope, element, attr){
	    	$document.bind("keydown", function (event) {
	        	if(event.which === 13) {
	        		scope.$apply(function (){
	        			//&& (scope.preventEnter!=undefined && scope.preventEnter!=true
	        			if (!attr.disabled){
	        				scope.$eval(attr.ngClick);
	        			}
	                });
	                event.preventDefault();
	            }
	        });
	    }
  };
}]);



/**
 * 头部菜单
 * 
 * 标签的属性值表示第几个菜单被选中
 */

commonApp.directive('myTopMenu',['commonService',function(commonService){
	var myTopMenu = {};
	myTopMenu.restrict="AE";
	myTopMenu.compile=function(element,attrs,$scope){
		var objName=attrs.name;
		if(objName == undefined){
			objName="myTopMenu";
		}
		var menu = {
				menuData:{},
				getMenu:function(){
					commonService.postUrl("portalBusiBO.ajax?cmd=getTopMenu","",function(data){
						menu.menuData = data;
						for(var i=0;i<menu.menuData.childMenus.length;i++){
							menu.menuData.childMenus[i].show=false;
						}
					});
				},
				setMenuShow:function(menuId){
					for(var i=0;i<menu.menuData.childMenus.length;i++){
						if(menu.menuData.childMenus[i].menuId==menuId){
							menu.menuData.childMenus[i].show=true;
						}else{
							menu.menuData.childMenus[i].show=false;
						}
						
					}
				},
				hideAllMenu:function(){
					for(var i=0;i<menu.menuData.childMenus.length;i++){
							menu.menuData.childMenus[i].show=false;
					}
				}
		};


		 var template='<li  ng-repeat="obj in '+objName+'.menuData.childMenus" class="nav_li"><a href="#" ng-mouseover="'+objName+'.show(obj.menuId)"; class="nav_li_a"><i class="block"><img alt="" ng-src="{{obj.menuIcon}}" /></i><span>{{obj.menuName}}<i class="icon_down icon"></i></span></a>'
			+'<span class="submenu clearfix"   ng-show="obj.show" >'
			+'    <a ng-repeat="menu in obj.childMenus" href="javascript:void(0)"  ng-click="'+objName+'.menuClick(menu.menuId,menu.menuName,menu.menuPath,obj.menuId)">{{menu.menuName}}</a>'
			+'</span>'
			+'</li>';
		
		element.html(template);
		return function($scope,element,attrs){
			menu.getMenu();
			menu.menuClick=function(id,name,url,menuId){
				$scope.myTab.open(id,name,url);
				$scope.isShowIndex=false;
				$scope.isShow=false;
				menu.hideAllMenu();
			},
			menu.show=function(menuId){
				menu.setMenuShow(menuId,true);
			},
			eval("$scope."+objName+"=menu");
		};
	};
	return myTopMenu;
}]);


/**
 * tab页面
 * 
 * 标签的属性值表示第几个菜单被选中
 */

commonApp.directive('myTab',['commonService','$timeout',function(commonService,$timeout){
	var myTab = {};
	myTab.restrict="AE";

	myTab.compile=function(element,attrs,$scope){
		var objName=attrs.name;
		var callBack=attrs.callback;
		if(objName == undefined){
			objName="myTab";
		}
		var ver=attrs.ver;
		if(ver == undefined){
			ver="1";
		}
		var n=0;
		var tab = {
				list:[],
				firstOpen:true,
				addVersion:function(noVerUrl){
					if(noVerUrl.indexOf("?")!=-1){
						return noVerUrl+"&v="+ver;
					}else{
						return noVerUrl+"?v="+ver;
					}
				},
				getCurrentOpenId:function(){
					var currentTab=this.getCurrentTab();
					if(currentTab!=undefined){
						return currentTab.id;
					}
				},
				getCurrentTab:function(){
					for(var index=0;index<this.list.length;index++ ){
						  if(this.list[index].type=="show"){
							  return this.list[index];
						  }
					}
				},
				//打开一个tab,如果该tab已经存在，就显示，如果没有，新开一个
				open:function(id,name,url,parentId){
					if(this.firstOpen){
						this.firstOpen=false;
						if(callBack!=undefined){
							eval(callBack+"()");
						}
					}
					if(!this.changeTab(id)){
						var elm={};
						elm.id=id;
						elm.parentId="";
						if(parentId!=undefined && parentId!=""){
							elm.parentId=parentId;
						}
						
						elm.name=name;
						elm.url=this.addVersion(url);
						elm.type="show";
						elm.index=this.maxIndexTab()+1;
						this.list.push(elm);
						this.changeTabIndex(elm.index);
						
						//绑定点击事件，隐藏菜单
						$timeout(function() {
							document.getElementById("tab_id_"+elm.id).contentDocument.addEventListener('click',function(){
								var appElement = document.querySelector('[ng-controller=mainCtrl]');
								var scope=angular.element(appElement).scope();
								scope.myTopMenu.hideAllMenu();
								scope.$apply();
							});

						},500);
						
						
						
					}
					
				},
				changeCurrentTabUrl:function(url){
						var currentTab=this.getCurrentTab();
						url=this.addVersion(url);
						currentTab.url=url;
						//绑定点击事件，隐藏菜单
						$timeout(function() {
							document.getElementById("tab_id_"+currentTab.id).contentDocument.addEventListener('click',function(){
								var appElement = document.querySelector('[ng-controller=mainCtrl]');
								var scope=angular.element(appElement).scope();
								scope.myTopMenu.hideAllMenu();
								scope.$apply();
							});

						},500);
					
					
				},
				/***
				 * id  新开页面的id
				 * name 新开页面的名称
				 * url   新开页面的地址
				 * params 表示要传入新开页面的参数
				 */
				openWithParent:function(id,name,url){
					var parentId=this.getCurrentOpenId();
					var childId=parentId+"_"+id;
					if(!this.changeTab(childId)){
						var elm={};
						elm.id=childId;
						elm.parentId=parentId;
						elm.name=name;
						elm.url=this.addVersion(url);
						elm.type="show";
						elm.index=this.maxIndexTab()+1;
						this.list.push(elm);
						this.changeTabIndex(elm.index);
						//绑定点击事件，隐藏菜单
						$timeout(function() {
							document.getElementById("tab_id_"+elm.id).contentDocument.addEventListener('click',function(){
								var appElement = document.querySelector('[ng-controller=mainCtrl]');
								var scope=angular.element(appElement).scope();
								scope.myTopMenu.hideAllMenu();
								scope.$apply();
							});
						},500);
					}
					
				},
				//把所有tab页面变成不可显示
				hideAll:function(){
					for(var index=0;index<this.list.length;index++ ){
						  this.list[index].type="hide";
					}
				},
				//切换tab页面
				changeTab:function(id){
					//如果传入的id等于当前的页面的id,不需要进行什么处理
					if(id==this.getCurrentOpenId()){
						return true;
					}
					
					this.hideAll();
					for(var index=0;index<this.list.length;index++ ){
						  if(this.list[index].id==id){
//							  if(this.list[index].parentId==""){
								  // this.refCurrentTab(this.list[index]);
								  var appElement = document.getElementById("tab_id_"+id).contentWindow;
								  // currentTab对应iframe页面中的回调方法，由各个页面控制是否需要刷新
								  if (undefined != appElement && appElement.changeTabCallback != undefined && typeof appElement.changeTabCallback == 'function') {
									  appElement.changeTabCallback();
								  }
//							  }
							  this.list[index].type="show";
							  this.changeTabIndex(this.list[index].index);
							  appElement.setContentHegthDelay();
//							  setTimeout(function(){
//								  if (document.getElementById("tab_id_"+id) != null)
//									  document.getElementById("tab_id_"+id).style="height:100%;visibility:inherit; width:100%;z-index:1;";
//							  },500);
							  
							  //
							  return true;
						  }
					}
					
					return false;
				},
				cleanAll:function(){
					this.list=[];
				},
				refCurrentTab:function(currentTab){
					if(currentTab==undefined){
						currentTab=this.getCurrentTab();
					}
					
					if(currentTab.url.indexOf("?_refCurrentTab=")!=-1){
						currentTab.url=currentTab.url.substr(0,currentTab.url.indexOf("?_refCurrentTab="));
					}
					currentTab.url=currentTab.url+"?_refCurrentTab="+ (new Date()).getTime();
				},
				closeToParentTab:function(isReload){
					var currentTab=this.getCurrentTab();
					//判断父窗口是否已经关闭，如果已经关闭，直接调用close了
					var isExist=false;
					for(var i=0;i<this.list.length;i++ ){
						  if(this.list[i].id==currentTab.parentId){
							  isExist=true;
						  }
					}
					if(!isExist){
						tab.close(currentTab.id);
						return;
					}
					
					if(currentTab!=undefined){
						var index=0;
						//关闭当前的页面
						for(var i=0;i<this.list.length;i++ ){
							  if(this.list[i].id==currentTab.id){
								  index=this.list[i].index;
								  this.list.splice(i,1);
								  break;
							  }
						}
						tab.reduceIndex(index);
						
						//切换到父页面
						this.changeTab(currentTab.parentId);
						
						if(isReload!=undefined && isReload==true){
							this.refCurrentTab();
						}
					}
				},
				
				rightMoveTo:function(){ 
					if(this.rightMove(1)){
						for(var i=0;i<this.list.length;i++ ){
							  this.list[i].index+=1;
						}
					}
				},
				leftMoveTo:function(){ 
					if(this.leftMove(1)){
						for(var i=0;i<this.list.length;i++ ){
							  this.list[i].index-=1;
						}
					}
				},
				
				rightMove:function(num){ 
					var length=num*120;
					var leftNum='+='+length+'px';
					
					if(!$('.gd').is(':animated') && this.isCanMoveRight()){ 
					      $('.gd').animate({left:leftNum},150);
					      return true;
					 } 
					return false;
				},
				leftMove:function(num){ 
					var length=num*120;
					var leftNum='-='+length+'px';
					if (!$('.gd').is(':animated') && this.isCanMoveLeft()){ 
					      $('.gd').animate({left:leftNum},150); 
					      return true;
					 } 
					return false;
				},
				//判断是否可以向左移动，如果index的最大值＝＝1 的时候，不能再向左移动
				isCanMoveLeft:function(){
					if(this.maxIndexTab()<=1){
						return false;
					}
					return true;
				},
				//判断是否可以向右移动，如果index的最小值＝＝1的是，不能向右移动
				isCanMoveRight:function(){
					if(this.minIndexTab()==1){
						return false;
					}
					return true;
				},
				//返回当前的最大的tab的数值
				maxIndexTab:function(){
					var maxIndex=0;
					for(var index=0;index<this.list.length;index++ ){
						  var temp=this.list[index].index;
						  if(temp>maxIndex){
							  maxIndex=temp;
						  }
					}
					return maxIndex;
				},
				//返回当前的最小的tab的数值
				minIndexTab:function(){
					var minIndex=1000000;
					for(var index=0;index<this.list.length;index++ ){
						  var temp=this.list[index].index;
						  if(temp<minIndex){
							  minIndex=temp;
						  }
					}
					return minIndex;
				},
				/**
				 * 
				 * tab 页面按数据进行排列，1到7表示该tab页面是在用户能看到的区域
				 * 
				 * indexNum 如果这个值是小于1的，表示菜单是被左边的隐藏掉，需要向右移动到1的位置
				 * 
				 * indexNum 如果这个值是大于7的，表示菜单是在右?叩囊氐簦枰蜃笠贫?7的位置
				 * 
				 */
				changeTabIndex:function(indexNum){
					if(indexNum<1){
						var step=1-indexNum;
						if(this.rightMove(step)){
							for(var index=0;index<this.list.length;index++ ){
								  this.list[index].index+=step;
							}
						}
					}else if(indexNum>7){
						var step=indexNum-7;
						if(this.leftMove(step)){
							for(var index=0;index<this.list.length;index++ ){
								  this.list[index].index-=step;
							}
						}
					}
				},
				//减少 大于index的数据
				reduceIndex:function(index){
					for(var i=0;i<this.list.length;i++ ){
						  if(this.list[i].index>index){
							  this.list[i].index-=1;
						  }
					}
					
					//如果等于7，表示没有出现左右的按钮，如果这个时候，有些页面隐藏起来，就出不来了，需要把隐藏的页面都弄出来
					if(this.list.length==7){
						var min=this.minIndexTab();
						if(min<1){
						var step=1-min;
						if(this.rightMove(step)){
							for(var i=0;i<this.list.length;i++ ){
									  this.list[i].index+=step;
							}
						}
							
						}
					}
				}
				
		};
		 var template=
		 	'<div class="Hui-tabNav hidden-xs subnav">'
			
		 	+'<div class="main">'
			+'<ul id="tags" class="gd" ng-show="'+objName+'.list.length>0">'
         	+'<li id="dh1" ng-class="{\'hover\':obj.type==\'show\'}" ng-repeat="obj in '+objName+'.list" ><a href="javascript:void(0)" ng-click="'+objName+'.changeTab(obj.id)" class="a1">{{obj.name}}</a><i class="subnav_icon icon"><a ng-click="'+objName+'.close(obj.id)" href="javascript:void(0)" class="gone"></a></i></li>'
         	+'</ul>'
			+'</div>'
			+'<div class="Hui-tabNav-more btn-group" style="display: block;">'
			+'<a href="#" ng-show="'+objName+'.list.length>7" ng-click="'+objName+'.rightMoveTo()" class="toleft js-tabNav-prev"><i class="Hui-iconfont"></i></a>'
			+'<a href="#" ng-show="'+objName+'.list.length>7" ng-click="'+objName+'.leftMoveTo()" class="toright js-tabNav-next"><i class="Hui-iconfont"></i></a>'
			+'</div>'

			+'</div>'
         	+'<div ng-repeat="obj in '+objName+'.list" ng-show="obj.type==\'show\'"  ><iframe frameborder="0" style="height:100%;visibility:inherit; width:100%;z-index:1;" ng-src="{{obj.url}}" scrolling="yes"  id="tab_id_{{obj.id}}" ></iframe></div>';
		 	
//			'<ul id="tags" class="subnav" ng-show="'+objName+'.list.length>0">'
//         	+'<li id="dh1" ng-class="{\'hover\':obj.type==\'show\'}" ng-repeat="obj in '+objName+'.list" ><a href="javascript:void(0)" ng-click="'+objName+'.changeTab(obj.id)" class="a1">{{obj.name}}</a><i class="subnav_icon icon"><span ng-click="'+objName+'.close(obj.id)" href="javascript:void(0)" class="gone"></span></i></li>'
//         	+'</ul>'
 //        	+'<div ng-repeat="obj in '+objName+'.list" ng-show="obj.type==\'show\'"  ><iframe frameborder="0" style="height:100%;visibility:inherit; width:100%;z-index:1;" ng-src="{{obj.url}}" scrolling="yes"  id="tab_id_{{obj.id}}" ></iframe></div>';
		element.html(template);
		return function($scope,element,attrs){
			tab.close=function(id){
				var i=0;
				var index=0;
				for(;i<this.list.length;i++ ){
					  if(this.list[i].id==id){
						  index=this.list[i].index;
						  this.list.splice(i,1);
						  break;
					  }
				}
				if(this.list.length==0){
					$scope.loadIndex();
				}else{
					var showTabIndex=0;
					if(i-1>0){
						showTabIndex=i-1;
					}
					tab.hideAll();
					this.list[showTabIndex].type="show";
					tab.changeTabIndex(this.list[showTabIndex].index);
					tab.reduceIndex(index);
				}
				
			};
			$scope[objName]=tab;
//			eval("$scope."+objName+"=tab");
		};
	};
	return myTab;
}]);

/**
 * 上传指令
 * 
 */
commonApp.directive('myFileModel',['commonService','$parse','$sce','$compile','$rootScope',function(commonService,parse,$sce,$compile,$rootScope) {
	var myFileUp={};
	myFileUp.restrict="A";
	myFileUp.replace=true;
	myFileUp.compile=function(element, attrs){
		return function($scope, element, attrs){
			var objName=attrs.myFileModel;
			var objClose=attrs.isClose;
			var objSee=attrs.isSee;
			var objDefinedImg=attrs.definedImg;
			if(attrs.index!=undefined){
				var parseFun=parse(attrs.index);
				objName=objName+parseFun($scope);
			}
			if(objSee==null || objSee==undefined){
				objSee=true;
			}
			var fileUp={
				isShow:false,
				show:true,
				callbackUrl:"",
				isShowIMgBig:false,
				initDate: function (flowId){
					if(flowId!=null && flowId!=undefined && flowId!=""){
						var jdt=document.getElementById(objName+"Jdt");
						if(jdt!=null && jdt!=undefined && jdt!=""){
							jdt.style.display="block";
						}
						var url="attach.ajax?cmd=doQuery&_GRID_TYPE=jq&flowIds="+flowId;
						commonService.postUrl(url,"",function(data){
							if(data.total>0){
								document.getElementById(objName+"Id").value=flowId;
								document.getElementById(objName+"Img").src=data.rows[0].fullPathUrl;
								document.getElementById(objName+"Url").value=data.rows[0].storePath;
							}else{
								document.getElementById(objName+"Id").value="";
								document.getElementById(objName+"Url").value="";
							}
							if(jdt!=null && jdt!=undefined && jdt!=""){
								jdt.style.display="none";
							}
						});
					}
				},
				clike: function (){
					this.iframeClick();
				},
				iframeClick:function(){
					eval(objName+'Iframe.onClick("'+objName+'Id","'+objName+'Url","'+objName+'Img","'+objName+'Close","'+objDefinedImg+'","'+objName+'Jdt","'+this.callbackUrl+'")'); 
				},
				del:function (){
					var id=document.getElementById(objName+"Id").value;
					if(id!=null && id!=undefined && id!=""){
						var jdt=document.getElementById(objName+"Jdt");
						if(jdt!=null && jdt!=undefined && jdt!=""){
							jdt.style.display="block";
						}
						var url="attach.ajax?cmd=doDel&flowId="+id;
						commonService.postUrl(url,"", function(res) {
							if(objDefinedImg!=null && objDefinedImg!=undefined && objDefinedImg!="" && objDefinedImg!="null" && objDefinedImg!="undefined"){
								document.getElementById(objName+"Img").src=commonService.getRootPath()+objDefinedImg;
							}else{
								document.getElementById(objName+"Img").src=commonService.getRootPath()+"/image/$tenantId$/carLine_tp.jpg";
							}
							document.getElementById(objName+"Id").value="";
							document.getElementById(objName+"Url").value="";
							if(jdt!=null && jdt!=undefined && jdt!=""){
								jdt.style.display="none";
							}
							document.getElementById(objName+"Img").style.cursor="";
							document.getElementById(objName+"ImgBig").src="";
							document.getElementById(objName+"Img").title="";
						});
					}
				},
				get:function (){
					var id=document.getElementById(objName+"Id").value;
					var url=document.getElementById(objName+"Url").value;
					return {flowId:id,flowUrl:url};
				},
				clean:function (){
					document.getElementById(objName+"Id").value="";
					document.getElementById(objName+"Url").value="";
					if(objDefinedImg!=null && objDefinedImg!=undefined && objDefinedImg!="" && objDefinedImg!="null" && objDefinedImg!="undefined"){
						document.getElementById(objName+"Img").src=commonService.getRootPath()+objDefinedImg;
					}else{
						document.getElementById(objName+"Img").src=commonService.getRootPath()+"/image/$tenantId$/carLine_tp.jpg";
					}
				},
				isUpShow:function(flag){
					this.show=flag;
				},
				mouseenter:function(){
					var obj = document.getElementById(objName+"Iframe");
					if(obj==null && obj==undefined){
						document.getElementById(objName+"spanIframe").innerHTML="<iframe id='"+objName+"Iframe' name='"+objName+"Iframe' src='"+commonService.getRootPath()+"/js/fileUpLoad.html' style='display: none;'></iframe>";	
					}
					if(this.show){
						this.isShow=true;
					}else{
						this.isShow=false;
					}
					if(objSee=="true"){
						var obj = document.getElementById(objName+"Url").value;
						if(obj != null && obj!=undefined && obj!=""){
							document.getElementById(objName+"Img").style.cursor="pointer";
						}
					}
				},
				mouseleave:function(){
					this.isShow=false;
				},
				loadCheck:function(){
					if(objSee=="true"||objSee==true){
						var urlImg="";
						var obj = document.getElementById(objName+"Url").value;
						if(obj != null && obj!=undefined && obj!=""){
							var src = document.getElementById(objName+"Img").src;
							var srcArray=src.split("?");
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
							}
							if(urlImg!=""){
								this.isShowIMgBig=true;
								document.getElementById(objName+"ImgBig").src=urlImg;
								document.getElementById(objName+"Img").title="点击查看大图";
								var obj = document.getElementById('canvas_'+objName+'ImgBig');
								if(obj!=null && obj!=undefined){
									obj.parentNode.removeChild(obj);
									var img = document.getElementById(objName+'ImgBig');
									img.style.visibility = '';
									img.setAttribute('step',0);
								}
							}
						}	
					}
				},
				loadcosle:function(){
					this.isShowIMgBig=false;
				},
				imgRotate:function(o,p){
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
				}
			};
			var html=
				'<div class="controls_pz" id="'+objName+'UpFile" ng-mouseenter="'+objName+'.mouseenter()" ng-mouseleave="'+objName+'.mouseleave()">'+
				'	<div class="controls_pz">'+
				'		<div class="image-tips-wrap">';
				if(objClose!=null && objClose!=undefined && objClose!="" && objClose=="true"){
					html+='	<i class="s_bh" ng-click="'+objName+'.del()" id="'+objName+'Cl" ng_show="'+objName+'.isShow" id="'+objName+'Close" name="'+objName+'Close">x</i>';
				}else{
					html+='	<i id="'+objName+'Cl" style="display: none;" id="'+objName+'Close" name="'+objName+'Close"></i>';
				}
				if(objDefinedImg!=null && objDefinedImg!=undefined && objDefinedImg!=""){
					html+='	<img src="'+commonService.getRootPath()+objDefinedImg+'" id="'+objName+'Img" name="'+objName+'Img" ng-click="'+objName+'.loadCheck()"  class="image-border" width="180" height="122"/>';
				}else{
					html+='	<img src="'+commonService.getRootPath()+'/image/$tenantId$/carLine_tp.jpg" id="'+objName+'Img" ng-click="'+objName+'.loadCheck()"  name="'+objName+'Img" class="image-border" width="180" height="122"/>';
				}
				html+='		<div class="a11" id="'+objName+'Ck" ng_show="'+objName+'.isShow">'+
				'				<a class="tx" href="javascript:void(0);" ng-click="'+objName+'.clike()">修改图片</a>'+
				//'				<iframe name="'+objName+'Iframe" src="'+getRootPath()+'/js/fileUpLoad.html" style="display: none;"></iframe>'+
				'				<span id="'+objName+'spanIframe"></span>'+
				'				<input type="hidden" id="'+objName+'Id" name="'+objName+'Id"/>'+
				'				<input type="hidden" id="'+objName+'Url" name="'+objName+'Url"/>'+
				'			</div>'+
				'		</div>'+
				'	</div>'+
				'	<div class="kaa" style="display: none;" id="'+objName+'Jdt" name="'+objName+'Jdt"><img src="'+commonService.getRootPath()+'/image/$tenantId$/jdt.gif" class="image-border" width="100" height="100"/> </div>'+
				'	<div class="popup ng-hide" ng-show="'+objName+'.isShowIMgBig" style="position: fixed; z-index: 2000000; border-radius: 5px;border-top:0; background: white;width: 750px;height: 520px;margin-top: -300px;margin-left: -370px;left: 50%;top:50%">'+
				'		<div ng-click="'+objName+'.loadcosle()" style="cursor: pointer;overflow-y: hidden;width: 750px;height: 490px;" id="'+objName+'ImgBigDIV"><img width="750" id="'+objName+'ImgBig" height="480" /></div>'+
				'		<div style=" width: 710px; display: block; overflow: hidden; padding-left: 20px; padding-right: 20px;position:absolute;bottom: 0;" class="MagicThumb-caption"><b style="float: right;">点击图片关闭</b><div class="img2"><img title="旋转" ng-click="'+objName+'.imgRotate(\''+objName+'ImgBig\',\'left\')" style="cursor: pointer;" src="'+commonService.getRootPath()+'/image/$tenantId$/xz.png"></div></div>'+
				'	</div>'+
				'	<div class="ng-hide" ng-show="'+objName+'.isShowIMgBig" style="position: fixed; top: 0px; left: 0px; width: 100%; height: 100%; z-index: 1999999; opacity: 0.5; text-align: center; background: rgb(102, 102, 102);"></div>'+
				'</div>';
			element.html($sce.trustAsHtml(html));
			$compile(element.contents())($scope);
			eval("$scope."+objName+"=fileUp");
		};
	};
	return myFileUp;
}]);


/**
 * @TODO  wangbq
 */

commonApp.directive('vehicleType',['commonService',function(commonService){
	var myVehicleType={};
	myVehicleType.restrict="AE";
	myVehicleType.replace=true;
	myVehicleType.compile=function(element, attrs){
		var placeholder = attrs.placeholder;
		if(placeholder == undefined || placeholder ==null){
			placeholder = "";
		}
		var vehicleTypeWin={
			isShow:false,//整个弹窗是否显示
			inputValue:"",//输入框的数据
			inputId:"",
			isDisabel:false, // 控制是否显示数据
			vehicleTypeData:{},
			vehicleLen:"",
			initDate:function(inputId){
				if(inputId!=null && inputId!=undefined && inputId!=""){
					var url = "selectContainer.ajax?cmd=doQueryContainer";
					var that=this;
					var param="";
					commonService.postUrl(url,param,function(data){
						that.vehicleTypeData=data.items;
						for(var i=0;i< that.vehicleTypeData.length;i++){
							var obj=that.vehicleTypeData[i];
							if(obj.containerId==inputId){
								that.inputValue=obj.containerName;
								that.inputId=obj.containerId;
							}
						}
	            	});
				}
			},
			setData:function (vehicleLen){
				this.vehicleLen=vehicleLen;
				if(this.vehicleLen!=null && this.vehicleLen!=undefined && this.vehicleLen!=""){
					var that=this;
					var url = "selectContainer.ajax?cmd=doQueryContainer&vehicleLength="+vehicleLen;
					var param="";
					commonService.postUrl(url,param,function(data){
						that.vehicleTypeData=data.items;
	            	});
				}
			},
			disable:function(val){
				this.isDisabel=val;
			},
			inputClick: function (){
				alert(1);
				this.isShow=true;
				if(this.isDisabel){
					this.isShow=false;
				}
				if(this.isShow){
					this.creatData();
				}
				
			},
			
			confirm:function(objName,objId){
				this.inputValue=objName;
				this.inputId=objId;
				this.isShow=false;
				this.executive(this.inputId,this.inputValue);
			},
			clear:function(){
				this.inputValue="";
				this.inputId="";
				this.focus();
			},
			creatData:function(){
				if(this.vehicleLen==null || this.vehicleLen==undefined || this.vehicleLen==""){
					alert(2);
					var url = "selectContainer.ajax?cmd=doQueryContainer";
					var that=this;
					var param="";
					commonService.postUrl(url,param,function(data){
						that.vehicleTypeData=data.items;
	            	});
				}
			},
			close:function(){
				this.isShow=false;
			},
			mouseleave : function(){
				this.focus();
				this.mouseenterDiv = false;
			},
			blur : function(){
				if(!this.mouseenterDiv){
					this.close();
				}
			},
			mouseenter : function(){
				this.mouseenterDiv = true;
			}
		};
		//每个标签的对象不一样
		var objName=attrs.vehicleType;
		var root = attrs.roots;
		var exec=attrs.exec;
//		<div class="list_stock" style="display:">
//    	<h2>请选择车辆类型</h2>
//        <i class="list_xl_icon icon"><a class="gone" href="#dh3"></a></i>
//        <ul class="area_list">
//            	<li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="1" type="checkbox">普通货车</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="15" type="checkbox">平板</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="12" type="checkbox">厢式</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="20" type="checkbox">厢式尾板</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="3" type="checkbox">集装箱</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="5" type="checkbox">高栏</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="21" type="checkbox">冷藏车</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="23" type="checkbox">罐体专用车</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="4" type="checkbox">自卸</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="22" type="checkbox">危险品车</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="1" type="checkbox">普通货车</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="15" type="checkbox">平板</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="12" type="checkbox">厢式</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="20" type="checkbox">厢式尾板</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="3" type="checkbox">集装箱</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="5" type="checkbox">高栏</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="21" type="checkbox">冷藏车</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="23" type="checkbox">罐体专用车</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="4" type="checkbox">自卸</a></li>
//                <li class="area_list_li ng-scope"><a href="javascript:void(0);" class="ng-binding"><input id="22" type="checkbox">危险品车</a></li>
//           </ul>
//           <div class="qc_q"><a class="ui-city-close" href="javascript:void(0);" ng-click="manyVehicleType.clear()">清除</a></div>
//           <div class="qr_q"><a class="ui-city-close" href="javascript:void(0);" ng-click="manyVehicleType.confirm()">确定</a></div>
//    </div>
		var html="<input type=\"text\" placeholder='"+placeholder+"' ng-blur=\"" + objName + ".blur()\"   readonly=\"readonly\" my-Focus=\""+objName+"focus\" ng-click=\""+objName+".inputClick()\" ng-model=\""+objName+".inputValue\" class=\"form_billing\" style=\"width:"+attrs.width+"px;height:"+attrs.height+"px\"></input>";
		html+="<div class=\"list_stock\" ng-mouseenter=\""+ objName + ".mouseenter()\" ng-mouseleave=\""+ objName + ".mouseleave()\" ng-show=\""+objName+".isShow\" style=\"position: absolute;z-index: 999999;width: 390px;\">";
		html+="<h2>选择车辆类型信息</h2>";
		html+="<div data-widget=\"tabs\" class=\"m JD-stock\" id=\"JD-stock\">";
		html+="<input type=\"hidden\"/>";
		html+="<div class=\"mc\" data-area=\"0\" data-widget=\"tab-content\" id=\"stock_province_item\" >";
		html+="<ul class=\"area-list\" >";
		html+="<li ng-click=\""+objName+".confirm(vehicletype.containerName,vehicletype.containerId)\" ng-repeat=\"vehicletype in "+objName+".vehicleTypeData\" style=\"width: 75px;\"><a href=\"javascript:void(0);\">{{vehicletype.containerName}}</a></li>";
		html+="</ul>";
		html+="</div>";
		html+="</div>";
		html+="<div class=\"close\"> <i class=\"list_xl_icon icon\"><a class=\"gone\" ng-click=\""+objName+".close()\" href=\"javascript:void();\"></a></i></div>";
		html+="<div class=\"qc_q\"><a class=\"ui-city-close\" href=\"javascript:void(0);\" ng-click=\""+objName+".clear()\">清除</a></div>";
		html+="<div class=\"qr_q\"><a class=\"ui-city-close\" href=\"javascript:void(0);\" ng-click=\"vehicleType.confirm()\">确定</a></div>";
		html+="</div>";
		element.html(html);
		return function($rootScope,$scope, element, attrs){
//			vehicleTypeWin.executive=function (inputId,inputValue){
//				if(exec!=null && exec!=undefined && exec!=""){
//					eval(exec+"('"+inputId+"','"+inputValue+"')");
//				}
//			};
//			vehicleTypeWin.focus=function(){
//				if(root == null){
//					eval("$scope."+objName+"focus.focus(true)");
//				}else{
//					eval(root+"."+objName+"focus.focus(true)");
//				}
//			};
//			if(root == null || root==undefined){
//				eval("$scope."+objName+"=vehicleTypeWin");
//			}else{
//				
//			}
			eval("$scope."+objName+"=vehicleTypeWin");
			$scope.test=function(){
				alert(1);
			};
		};
	};
	return myVehicleType;
}]);


/**
 * 日期标签
 * 
 *  该属性值为对象，如果不传，按默认格式。 对象属性：dateFmt，autoPickDate
 * 
 */
commonApp.directive("myDatePicker",[function($parse){
                    return {
                        restrict:"A",
                        link:function(scope, element, attr){
                        	var obj={};
                        	var field="";
                        	if(attr.myDatePicker!=undefined  && attr.myDatePicker!="" && attr.myDatePicker!=null){
                        		obj=angular.fromJson(attr.myDatePicker,false);
                        	}
                        	if(attr.ngModel!=undefined  && attr.ngModel!="" && attr.ngModel!=null){
                        		field = attr.ngModel;
                        	}
                        	var dateFmt="yyyy-MM-dd";
                        	var autoPickDate=true;
                        	var callBack = "";
                        	var maxDate = "";
                        	var minDate="";
                        	if(obj.dateFmt!=undefined){
                        		dateFmt=obj.dateFmt;
                        	}
                        	if(obj.autoPickDate!=undefined){
                        		autoPickDate=obj.autoPickDate;
                        	}
                        	if(obj.callBack != undefined){
                        		callBack = obj.callBack
                        	}
                        	if(obj.maxDate != undefined){
                        		maxDate = obj.maxDate
                        	}
                        	if(obj.minDate != undefined){
                        		minDate = obj.minDate
                        	}
                            element.bind("click", function () {
                            	WdatePicker({dateFmt:dateFmt, autoPickDate: autoPickDate,minDate:minDate,maxDate:maxDate,onpicked: function(){
                            		$parse(attr['ngModel']).assign(scope, this.value);
                            		if(callBack != null && callBack != undefined && callBack != ""){
                            			eval("scope."+callBack+"(this.value)");
                            		}
                            	},onclearing:function(){
                            		if(field!=""){
                            			eval("scope."+field+"=''");
                            		}
                        		}});
                            });
                        }
       };
}]);


/**
 * 用户需要登录才能操作
 */
commonApp.directive("isCheckLogin",['commonService',function(commonService){
                    return {
                        restrict:"A",
                        link:function(scope, element, attr){
                            element.bind("click", function (event) {
            					var url = "logisticsTrack.ajax?cmd=userDateInfoOfIndex";
            					commonService.postUrl(url,"",function(data){
            						if(data != null && data != undefined && data !=""){
            							//表示已经登录了
            							if(attr.isCheckLogin.indexOf(".html")==-1){
            								var isCheckLogin="scope."+attr.isCheckLogin;
            								eval(isCheckLogin);
            							}else{
            								window.location.href=attr.isCheckLogin;
            							}
            						}else{
            							//表示没有登录，需要提示用户登录
            							commonService.confirm("系统异常，请联系管理员！",function(){
            								var loginLocStr=getRootPath()+'/'+'login.html';
            								if(loginLocStr != null && loginLocStr != undefined && loginLocStr !=""){
            									window.location.href=loginLocStr;
            								}
			                             });
            						}
            					});
                            });
                        }
       };
}]);


/** 是否有权限访问  */
commonApp.directive("isCanVisit",['commonService',function(commonService){
                    return {
                        restrict:"A",
                        link:function(scope, element, attr){
                        	var entityId = attr.isCanVisit.split("|")[0];
                        	var reg = new RegExp("^[0-9]*$");
                        	if(reg.test(entityId)){
                        		element.bind("click", function (event) {
                        			var url = "logisticsTrack.ajax?cmd=isCanVisit";
                        			commonService.postUrl(url,"entityId="+entityId,function(data){
                        				if(data != null && data != undefined && data !=""){
                        					if(data == "Y"){
                        						//表示已经登录了
                        						if(attr.isCanVisit.indexOf(".html")==-1){
                        							var isCanVisit="scope."+attr.isCanVisit.split("|")[1];
                        							eval(isCanVisit);
                        						}else{
                        							window.location.href=attr.isCanVisit;
                        						}
                        					}else{
                        						commonService.alert("亲，您没有该使用权限！");
                        					}
                        				}else{
                        					//表示没有登录，需要提示用户登录
                        					commonService.alert("亲，您没有该使用权限！");
                        				}
                        			});
                        		});
                        	}else{
                        		element.bind("click", function (event) {
                        			//这个是配置错误 标签填写的不是数字
                					commonService.alert("亲，您没有该使用权限！");
                        		});
                        	}
                        }
       };
}]);

/**
 * 分页表格
 * 
 * 如果没有设置name属性值，默认取page做为该表格的对象，如果name属性有传入值，则以该属性值为对象
 * 
 * 对外提供：
 *      load方法，加载表格数据入参为对象：
 *      		    属性：url 表示请求的地址
 *      			属性：params 表示请求的参数
 *                  属性：callBack 表示数据请求后，调用的回调方法，入参为请求返回的数据对象
 *      reload方法，重新请求当前页的数据。
 * 
 */
commonApp.directive('myTable',['commonService',function(commonService){
	var myTable={};
	myTable.restrict="E";
	myTable.transclude=true;
	myTable.compile=function(element, attrs){
		var objName=attrs.name;
		var isShowTotal=attrs.showtotal;
		isShowTotal=isShowTotal=="false"?false:true;
		if(objName==undefined){
			objName="page";
		}
		var template='<div class="dhl_td_bj" style="position: relative;">'
			+'<div ng-transclude style="position: relative;">'
			+'</div>'
			+'<div ng-show="'+objName+'.total==0 && !'+objName+'.isLoad" class="search_page" style=" text-align:center; top: 100px;line-height:50px; color:#ff7800;">没有符合条件的数据'
			+'</div>'
			+'<div class="kaa ng-hide"  ng-show="'+objName+'.isLoad" ><img src="'+commonService.getRootPath()+'/image/$tenantId$/jdt.gif" class="image-border" /> </div>'
			+'<div class="chey_b_1 list_fye clearfix ">'
			+'<div class="chey_xs fl">'
			+'<span>显示</span><a ng-click="'+objName+'.changeRows(10);" href="javascript:void(0)" ng-class="{hover:'+objName+'.pageCount==10}" class="hover">10</a><a ng-click="'+objName+'.changeRows(20);" href="javascript:void(0)" ng-class="{hover:'+objName+'.pageCount==20}">20</a><a ng-click="'+objName+'.changeRows(50);" href="javascript:void(0)" ng-class="{hover:'+objName+'.pageCount==50}">50</a><span>条</span>共{{'+objName+'.total}}条数据'
			+'</div>'
			+' <ul class="pagination fr" ng-show="'+objName+'.total>0">'
			+'  <li ng-class="{active:obj.isChoose==true}" ng-repeat="obj in '+objName+'.pageList" ><a ng-if="obj.pageNum==-1" ng-click="'+objName+'.prePage();" title="" href="javascript:void(0)"><</a>'
			+'  <a ng-if="obj.pageNum>=1"  ng-click="'+objName+'.goPage(obj.pageNum);" href="javascript:void(0)">{{obj.pageNum}}</a>'
			+' <div ng-if="obj.isEllipsis==true" class="dhl_break">...</div>'
			+'  <a ng-if="obj.pageNum==-2" ng-click="'+objName+'.nextPage();" title="" href="javascript:void(0)">></a></li>'
			+' </ul>'
			+' </div>'
			+' </div>';
		
		
		element.html(template);
		return function( $scope, element, attrs ) {

			var page={
	        		pageCount:10,//分页数据的大小
	        		pageNum:1,//当前的分页的第几页
	        		totalNum:1,//总的页数
	        		total:0,//总记录数
	        		params:{},//请求的参数
	        		isLoad:false,
	        		isShowTotal:isShowTotal,
	        		pageList:new Array(),
	        		load:function(pageParms,type){
	        			var that=this;
	        			that.isLoad=false;
	        			window.top.showLoad();
	        			
	        			//bug 当用户选择不是第一页的按钮后，如果再查询的时候，回不到第一页的问题
	        			if(type==undefined){
	        				pageParms.params.page=1;
	        			}
//	        			pageParms.params.page=that.pageNum;
	        			that.pageNum=pageParms.params.page;
	        			
	        			if(pageParms.params.rows!=undefined && pageParms.params.rows!=null){
	        				that.pageCount=pageParms.params.rows;
	        			}
	        			
	        			pageParms.params.rows=that.pageCount;
	        			this.params=pageParms;
	        			
	        			
	        			
	        			commonService.postUrl(pageParms.url,pageParms.params,function(data){
	        				window.top.hideLoad();
	        				that.isLoad=false;
	        				that.data = data;
	        				if(data.total!=undefined && data.total!=null ){
	        					that.total=data.total;
	        					if(that.total==0){
	        						that.pageNum=0;
	        					}
	        				}
	        				if(data.totalNum!=undefined && data.totalNum!=null ){
	        					that.total=data.totalNum;
	        					if(that.total==0){
	        						that.pageNum=0;
	        					}
	        				}
	        				if(data.totalNum!=undefined && data.totalNum!=null ){
	        					that.total=data.totalNum;
	        					if(that.total==0){
	        						that.pageNum=0;
	        					}
	        				}
	        				if(pageParms.callBack!=null && pageParms.callBack!=undefined && pageParms.callBack!=""){
	        					eval(pageParms.callBack+"(data)");
	        				}
	        				
	        				
	        				that.initPage();
	        				that.pageList=that.getPageArray(that.totalNum,that.pageNum);
	                	},function(){
	                		window.top.hideLoad();
	                	});
	        		},
	        		reLoad:function(toChangePage){
	        			var that=this;
	        			if(toChangePage!=undefined && toChangePage!=""){
	        				if(toChangePage<0){
	        					if((this.total+toChangePage)%this.pageCount==0){
	        						that.params.params.page=that.params.params.page-1;
	        					}
	        				}else {
	        					if((this.total+toChangePage)%this.pageCount==1){
	        						that.params.params.page=that.params.params.page+1;
	        					}
	        				}
	        				
	        			}
	        			commonService.postUrl(that.params.url,that.params.params,function(data){
	        				that.data = data;
	        				if(data.total!=undefined && data.total!=null){
	        					that.total=data.total;
	        				}
	        				if(data.totalNum!=undefined && data.totalNum!=null){
	        					that.total=data.totalNum;
	        				}
	        				if(that.params.params.callBack!=undefined ){
	        					eval(that.params.params.callBack+"(data)");
	        				}
	        				that.initPage();
	                	});
	        		},
	        		initPage : function(){
	        			if(this.total%this.pageCount !=0){
	        				this.totalNum = Math.floor(this.total/this.pageCount)+1;
	        			}else{
	        				this.totalNum = Math.floor(this.total/this.pageCount);
	        			}
	        			if(this.pageNum>this.totalNum){
	        				this.pageNum=this.totalNum;
        				}
	        		},
	        		
	        		//更改分页大小
	        		changeRows : function(rowsNum){
	        			this.pageCount = rowsNum;
	        			if(rowsNum==-1){
	        				this.params.params._ALLEXPORT=1;
	        				this.params.params.rows=undefined;
	        			}else{
		        			this.params.params.rows=rowsNum;
		        			this.params.params.page=1;
		        			delete this.params.params._ALLEXPORT;
	        			}
	        			this.load(this.params,1);
	        		},
	        		//指定页
	        		changePage : function(page){
	        			if(this.totalNum==0){
	        				commonService.alert("没有数据");
	        				return;
	        			}
	        			if(page<=1 && this.params.params.page==page){
	        				commonService.alert("已经是第一页了");
	        			}else if(page>this.totalNum ){
	        				commonService.alert("已经是最后一页了");
	        			}else {
	        				this.params.params.page = page;
	        				this.load(this.params,1);
	        			}
	        		},
	        		//下一页
	        		nextPage : function(){
	        			if(this.totalNum==0){
	        				commonService.alert("没有数据");
	        				return;
	        			}
	        			if(this.params.params.page>=this.totalNum){
	        				commonService.alert("超出最大页数");
	        			
	        				return ;
	        			}
	        			this.params.params.page = parseInt(this.params.params.page) +1;
	        			this.load(this.params,1);
	        		},
	        		//上一页
	        		prePage : function(){
	        			if(this.totalNum==0){
	        				commonService.alert("没有数据");
	        				return;
	        			}
	        			if(this.params.params.page<=1){
	        				commonService.alert("已经是第一页了");
	        				return;
	        			}
	        			this.params.params.page = parseInt(this.params.params.page) -1;
	        			this.load(this.params,1);
	        		},
	        		
	        		//跳转到指定页面
	        		goPage : function(num){
	        			this.changePage(num);
	        			eval("$scope."+objName+".govalue=''");
	        		},
	        		//跳转到最后一页
	        		toEndPage : function(){
	        			if(this.params.params.page>=this.totalNum){
	        				commonService.alert("超出最大页数");
	        				return ;
	        			}
	        			page.changePage(this.totalNum);
	        		},
	        		toIndexPage:function(){
	        			if(this.params.params.page<=1){
	        				commonService.alert("已经是第一页了");
	        				return;
	        			}
	        			page.changePage(1);
	        		},
	        		/**
	        		 * 入参为 分页的总页数，当前页数
	        		 * 返回值：返回一个展示分页的列表对象
	        		 *     该对象
	        		 *    ｛
	        		 *    		isChoose:是否选中状态, 当前页的数据
	        		 *          pageNum:第几页：－1 为上一页，－2 为下一页
	        		 *          isEllipsis:是否是省略号
	        		 *     ｝
	        		 */
	        		getPageArray:function(totalNum,pageNum){
	        			var retList=new Array();
	        			var prePage={
		        				isChoose:false,
		        				pageNum:-1,
		        				isEllipsis:false
		        			};
	        			var nextPage={
		        				isChoose:false,
		        				pageNum:-2,
		        				isEllipsis:false
		        			};
		        		retList.push(prePage);
		        		
		        		//省略号
        				var ellipsis={
		        				isChoose:false,
		        				pageNum:0,
		        				isEllipsis:true
		        			};
		        		
	        			//第一种情况：页数没有超过 6页
	        		    // 上一页  1 2 3 4 5 6下一页
	        			if(totalNum<=6){
	        				for(var i=1;i<=totalNum;i++){
	        					var pageTemp={};
	        					pageTemp.pageNum=i;
	        					pageTemp.isEllipsis=false;
	        					if(i==pageNum){
	        						pageTemp.isChoose=true;
	        					}else{
	        						pageTemp.isChoose=false;
	        					}
	        					retList.push(pageTemp);
	        				}
	        			}
	        			//第二种情况，页数超过6页的时候，需要添加省略号
	        			if(totalNum>6){
	        				//如果当前页数小于6的，123456...
	        				if(pageNum<6){
		        				for(var i=1;i<=6;i++){
		        					var pageTemp={};
		        					pageTemp.pageNum=i;
		        					pageTemp.isEllipsis=false;
		        					if(i==pageNum){
		        						pageTemp.isChoose=true;
		        					}else{
		        						pageTemp.isChoose=false;
		        					}
		        					retList.push(pageTemp);
		        				}
		        				
		        				retList.push(ellipsis);
	        				}else{
	        					//计算需要展示的数字，在当前页的前后加起来5个数字
	        					var endNum=pageNum+2;
	        					if(totalNum<endNum){
	        						endNum=totalNum;
	        					}
	        					
	        					var firsPage={
	        							isChoose:false,
				        				pageNum:1,
				        				isEllipsis:false
	        					}
	        					
	        					retList.push(firsPage);
	        					
	        					ellipsis={
	    		        				isChoose:false,
	    		        				pageNum:0,
	    		        				isEllipsis:true
	    		        			};
		        				retList.push(ellipsis);
	        					
		        				//展示的数据
		        				for(var i=endNum-5;i<=endNum;i++){
		        					var pageTemp={};
		        					pageTemp.pageNum=i;
		        					pageTemp.isEllipsis=false;
		        					if(i==pageNum){
		        						pageTemp.isChoose=true;
		        					}else{
		        						pageTemp.isChoose=false;
		        					}
		        					retList.push(pageTemp);
		        				}
		        				//如果展示的数据最大的值比总数小2以上，展示省略号和最后一页
		        				if(totalNum-endNum>=2){
		        					ellipsis={
		    		        				isChoose:false,
		    		        				pageNum:0,
		    		        				isEllipsis:true
		    		        			};
		        					retList.push(ellipsis);
		        					var lastPage={
		        							isChoose:false,
					        				pageNum:totalNum,
					        				isEllipsis:false
		        					};
		        					retList.push(lastPage);
		        				}
		        				
	        				}
	        			}
	        			retList.push(nextPage);
	        			return retList;
	        		}
	        	};
				$scope[objName]=page;
            };
	};
	return myTable;
}]);


function sumOfItems() {
	return function(data, key) {
		if (data==undefined  || typeof (data) === undefined || typeof (key) === undefined) {
			return 0;
		}
		var r= /^-*[1-9]?[0-9]*\.[0-9]*$/;
		var sum = 0, i = data.length - 1;
		for (; i >= 0; i--) {
			var vl=data[i][key];
			if(isNaN(vl) || vl == null || vl == '' || vl == undefined){
				//不是数字的，直接设置为0
				vl=0;
			}else{
				if(r.test(vl)){
					//是浮点数
					vl = parseFloat(vl);
				}else{
					vl = parseInt(vl);
				}
			}
			sum += vl;
		}
		if(r.test(sum)){// 浮点型，保存小数点后两位
			sum = sum.toFixed(2);
		}
		return sum;
	};
};
commonApp.filter('sumOfItems', sumOfItems);

commonApp.filter('filterString', filterString);
/**空字符 number 时 会导致变为0.0
   值为0的时候，也会变成null 的，为了number过滤器，当数据为0的时候，显示空
 */
function filterString() {
	return function(data, key) {
		if (data == undefined  || typeof (data) === undefined || data=="0" || data==0  || data == "") {
			return null;
		}
		if(parseInt(data)==0&&parseFloat(data)==0.0&&parseDouble(data)==0.00){
			return null;
		}
		return data;
	};
}
/**
 * 表格标签
 * 
 * 对外提供的属性：
 *    @param head :传入头部的标签，格式为数组对象 [{},{}]
 *            对象支持的参数：
 *            	name 表头的名称
 *              code 列表返回的数据对应的字段的key值
 *              isSum 值为：true/false ，表示该列是否需要汇总
 *    @param  isSum 表示是否在列表底部显示汇总行
 *    @param  isFilter 表示是否在表格的头部下展示输入框，用于过滤表格数据
 *    @param  isShowSeq 表示是否在表格的第一列显示一个递增的序号
 *    @param  isShowCheck 表示是否显示checkbox的
 *    @param  isMultiSelect 表示checkbox 是支持单选，还是多选
 *    @param  id 表示每一行的数据的一个组件的字段key值
 *    @param  name 表示这个标签的对象，如果页面只有一个表格，可以不传入该值，默认值为“page”
 *    @param  isShowBt 表示是否显示前面的按钮 ,true 表示显示
 * 
 * 对外提供的方法：
 *    getSelected :返还所有选中的数据的主键
 *    getOneSelected: 如果选择的是单选的，可以直接调用该方法，返还一条数据的主键
 *    getSelectData: 返还选择的数据的全部数据内容
 *    
 *    downloadExcelFile:导出excel方法，请求的bo需要继承baseBo
 * 	  load  发送表格的请求，格式为对象
 *                url 请求的地址
				  params 请求的参数
 *    reload
 *    btclick  按钮的回调 方法，两个入参：第一个是选择的数据的id,第二个如果是等于－1表示是全选的按钮
 *    getFilterData 获取过滤后的数据
 * 
 **/
commonApp.directive('myAdvTable',['commonService','$filter','$timeout','$interval',function(commonService,$filter,$timeout,$interval){
	var myTable={};
	myTable.restrict="E";
	myTable.transclude=true;
	myTable.compile=function(element, attrs){
		var objName=attrs.name;
		var headList=angular.fromJson(attrs.head);
		var isSum=attrs.issum;
		var isFilter=attrs.isfilter;
		var isShowTotal=attrs.showtotal;
		var isShowSeq=attrs.isshowseq;//是否显示序号
		var filterStr="";//过滤的字符串
		var id=attrs.id;//每一行的数据的主键
		var isMultiSelect=attrs.ismultiselect;//单选框是否可以多选
		var isShowCheck=attrs.isshowcheck;//是否显示checkbox
		var doubleClickCallMethod = attrs.doubleclick; //双击调用方法
		var isAutoHide = attrs.isautohide; //是否随着列表增高自动加高
		var isShowBt=attrs.isshowbt;//是否在最前面显示＋按钮
		var btClickCallMethod=attrs.btclick;
		var isShowPage=attrs.isshowpage;//是否显示分页的样式
		var btCssType=attrs.btcsstype;//按钮的样式，true 表示是加号按钮，false表示是减号按钮
		var btTip=attrs.bttip;//
		var heightSize = "";
		var h2Size = "";
		var diytableName = attrs.tablename;
		if(isAutoHide == "false" || isAutoHide == false){
			heightSize = "";
//			h2Size = "min-height:220px";
		}else{
//			h2Size = "min-height:220px";
		}
		
		
		if(isShowPage==undefined){
			//如果这个值没有传，默认是需要显示分页
			isShowPage="true";
		}
		
		if(doubleClickCallMethod == undefined){
			doubleClickCallMethod = "doubleClickCallMethod";
		}
		isShowTotal=isShowTotal=="false"?false:true;
		if(objName==undefined){
			objName="page";
		}
		var tableWidth = attrs.width;
		if(tableWidth == undefined || tableWidth == null || tableWidth == ""){
			tableWidth = "";
		}
		//拼装过滤的字符串
		if(isFilter=="true"){
			for(var head in headList){
				filterStr+="|filter :{"+headList[head].code+":"+objName+".filterInput."+headList[head].code+"}";
			};
		}
		
		var template='<div style="position: relative;">'
					+'<div style=" '+heightSize+'; position: relative;">'
					+'<div class="table_height" style="/*min-height:270px;*/  '+h2Size+'; width:100%;overflow:auto;" >'
					+'<table class="search_lista" width="100%" border="0" cellspacing="0" cellpadding="0" style="width:'+tableWidth+';">'
					+' <thead class="fixed-thead" style="width:'+tableWidth+';">'
					+'<tr >'
					+'<th valign="top" width="25px" style="padding-left: 14px;" ng-show="'+objName+'.isShowBt==\'true\'" ng-click="'+objName+'.btClick($event,\'all\',-1)"><a ng-click="" style="cursor: pointer;margin-top: 5px;" class="icon" ng-class="{true:\'pcar_j\',false:\'pcar_m\'}['+objName+'.btCssType]"  tips="全部{{'+objName+'.btTip}}"></a></th>'
					+'   <th width="60" class="search_list_border" ng-if="'+objName+'.isShowCheck==\'true\'" ><div class="controls" ng-if="'+objName+'.isShowCheck==\'true\' && '+objName+'.isMultiSelect==\'true\'"><input id="'+objName+'.checkIdall" name="'+objName+'.checkName" class="none"  type="checkbox"><label for="checkbox" ng-click="'+objName+'.updateSelection($event,\'all\',-1)"></label></div></th>'
					
					+'   <th width="60" class="xlh" ng-if="'+objName+'.isShowSeq==\'true\'"><div style="font-family:宋体;font-size: 12px;font-weight: bold;">序号</div></th>'
					
					+'   <th style="text-align: left;" width="{{o.width == undefined ? 140 : o.width}}" ng-repeat="o in '+objName+'.headList" ng-click="'+objName+'.sort(o.code)" ><span style=" padding: 0 5px;">{{o.name}}<i ng-if="'+objName+'.orderFile==\'+\'+o.code"  class="list_top_tp"></i><i ng-if="'+objName+'.orderFile==\'-\'+o.code"  class="list_top_tpx"></i></span></th>'
					+'</tr>'
					
					+'<tr ng-if="'+objName+'.isFilter==\'true\'" class="ng-cloak" >'
					+'<th valign="top" width="25px" style="padding-left: 14px;" ng-if="'+objName+'.isShowBt==\'true\'"></th>'
					+'<th ng-if="'+objName+'.isShowCheck==\'true\'" width="60" class="search_list_border"></th>'
					+'<th ng-if="'+objName+'.isShowSeq==\'true\'" width="60" class="xlh" ></th>'
					+'<th width="{{t.width == undefined ? 140 : t.width}}" valign="top" ng-repeat="t in '+objName+'.headList" >'
					+'<input value=""  class="inp_sr"  type="text" ng-change="$parent.'+objName+'.changeData();" ng-model="$parent.'+objName+'.filterInput[t.code]" onclick="scrollTopTable()">'
					+'</th>'
					+' </tr>'
					
					+'</thead>'

					+'<tbody class="fixed-tbody" ng-class="{true:\'m_true\', false: \'m_false\'}['+objName+'.isFilter]" style="width:'+tableWidth+';">'
					//+'<tbody ng-class="fixed-tbody" style="width:'+tableWidth+';margin-bottom: 21px;margin-top:53px;">'
					
					
					+'<tr class="ng-cloak " ng-dblclick="'+doubleClickCallMethod+'(d)"  ng-class="{\'selected1\': '+objName+'.isSelected(d.'+id+')}" ng-repeat="d in '+objName+'.data.items '+filterStr+' | orderBy:'+objName+'.orderFile"  ng-click="'+objName+'.updateSelection($event,d.'+id+')" >'
					+'<td valign="top" width="25px" style="padding-left: 14px;" ng-if="'+objName+'.isShowBt==\'true\'" ng-click="'+objName+'.btClick($event,d.'+id+')"><a ng-click="" style="cursor: pointer;margin-top: 3px;" class="icon" ng-class="{\'true\':\'pcar_j icon\',\'false\':\'pcar_m icon\'}['+objName+'.btCssType]"  tips="{{'+objName+'.btTip}}"></a></td>'
					+'<td ng-if="'+objName+'.isShowCheck==\'true\'" width="60" class="search_list_border" ><div class="controls"><input  id="{{\''+objName+'.checkId\'+d.'+id+'}}" value="{{d.'+id+'}}" class="none"  type="checkbox" name="'+objName+'.checkName" ><label for="check-1"   ></label></div></td>'
					+'<td ng-if="'+objName+'.isShowSeq==\'true\'" width="60" class="xlh" ><div>{{$index+1}}</div></td>'
					+'<td width="{{t.width == undefined ? 140 : t.width}}" class="{{d.rowColor}}"  style="white-space: nowrap !important;word-break: keep-all;overflow: hidden;text-overflow: ellipsis;"   ng-repeat="t in '+objName+'.headList" >'
					+'<span ng-if="t.isEdit==undefined||t.isEdit==true">'
					+	'<span ng-if="t.number!=undefined"  style="width:{{t.width == undefined ? 140 : t.width}}px;white-space: nowrap !important;word-break: keep-all;overflow: hidden;text-overflow: ellipsis;display: block;text-align:left;padding: 0 5px;">{{(t.isEdit)}}{{$eval("d."+t.code) | filterString | number: t.number }}</span>'
					+	'<span ng-if="t.number==undefined"  style="width:{{t.width == undefined ? 140 : t.width}}px;white-space: nowrap !important;word-break: keep-all;overflow: hidden;text-overflow: ellipsis;display: block;text-align: left;padding: 0 5px;" title="{{$eval(\'d.\'+t.code)}}">{{(t.isEdit)}}{{$eval("d."+t.code)}}</span>'
					+'</span>'
					+'<span ng-if="t.isEdit!=undefined&&!(t.isEdit==true)">'
					+	'<input ng-if="t.number==undefined" class="form_term1"  title="{{$eval(\'d.\'+t.code)}}" value="{{$eval(\'d.\'+t.code)}}" onkeyup="this.value=this.value.replace(/^\\D*(\\d*(?:\\.\\d{0,2})?).*$/g, \'$1\');" ng-blur="'+objName+'.setTransferValue(t.code)" ng-model="d[t.code]"/>'
					+'</span>'
					+'</td>'
					+' </tr>'
//					+'<tr ng-if="'+objName+'.isSum==\'true\'" class="ng-cloak" >'
//					+'<td valign="top" width="25px" style="padding-left: 14px;" ng-if="'+objName+'.isShowBt==\'true\'" ></td>'
//					+'<td width="60" class="" ng-if="'+objName+'.isShowCheck==\'true\'"></td>'
//					+'<td ng-if="'+objName+'.isShowSeq==\'true\'" width="60" class="xlh" ></td>'
//					+'<td width="{{t.width == undefined ? 140 : t.width}}" ng-repeat="t in '+objName+'.headList" >'
//					+'<span class="text_list" ng-if="t.number!=undefined && t.isSum==\'true\'">{{'+objName+'.data.items '+filterStr+' |sumOfItems:t.code |number: t.number}}</span>'
//					+'<span class="text_list" ng-if="t.number==undefined && t.isSum==\'true\'">{{'+objName+'.data.items '+filterStr+' |sumOfItems:t.code }}</span>'
//					+'</td>'
//					+' </tr>'
					+'</tbody>'
					+'<tfoot ng-if="'+objName+'.isSum==\'true\'"  class="fixed-tfoot tfoot" style="width:'+tableWidth+';">'
					+'<tr class="ng-cloak" >'
					+'<td valign="top" width="25px" style="padding-left: 14px;" ng-if="'+objName+'.isShowBt==\'true\'" ></td>'
					+'<td width="60" class="" ng-if="'+objName+'.isShowCheck==\'true\'"></td>'
					+'<td ng-if="'+objName+'.isShowSeq==\'true\'" width="60" class="xlh" ></td>'
					+'<td width="{{t.width == undefined ? 140 : t.width}}" ng-repeat="t in '+objName+'.headList" >'
					+'<span class="text_list" ng-if="t.number!=undefined && t.isSum==\'true\'">{{'+objName+'.data.items '+filterStr+' |sumOfItems:t.code | filterString |number: t.number}}</span>'
					+'<span class="text_list" ng-if="t.number==undefined && t.isSum==\'true\'">{{'+objName+'.data.items '+filterStr+' |sumOfItems:t.code }}</span>'
					+'</td>'
					+' </tr>'
					+'</tfoot>'
					+'</table>'
					+' </div>'
					+' </div>'
					+'<div class="kaa ng-hide"  ng-show="'+objName+'.isLoad" ><img src="'+commonService.getRootPath()+'/image/$tenantId$/jdt.gif" class="image-border" width="100" height="100"/> </div>'
			+'<div ng-show="'+objName+'.total==0 && !'+objName+'.isLoad" class="search_page ng-hide" style=" text-align:center; line-height:50px; color:#ff7800;top:100px;">没有符合条件的数据'
			+'</div>'
			+'<div ng-if="'+objName+'.isShowPage==\'true\'"  class="chey_b_1 list_fye clearfix ">'
			+'<div class="chey_xs fl">'
			+'<span>显示</span><a ng-click="'+objName+'.changeRows(10);" href="javascript:void(0)" ng-class="{hover:'+objName+'.pageCount==10}" class="hover">10</a><a ng-click="'+objName+'.changeRows(20);" href="javascript:void(0)" ng-class="{hover:'+objName+'.pageCount==20}">20</a><a ng-click="'+objName+'.changeRows(50);" href="javascript:void(0)" ng-class="{hover:'+objName+'.pageCount==50}">50</a><span>条</span>共{{'+objName+'.total}}条数据'
			+'</div>'
			+' <ul class="pagination fr" ng-show="'+objName+'.total>0">'
			+'  <li ng-class="{active:obj.isChoose==true}" ng-repeat="obj in '+objName+'.pageList" ><a ng-if="obj.pageNum==-1" ng-click="'+objName+'.prePage();" title="" href="javascript:void(0)"><</a>'
			+'  <a ng-if="obj.pageNum>=1"  ng-click="'+objName+'.goPage(obj.pageNum);" href="javascript:void(0)">{{obj.pageNum}}</a>'
			+' <div ng-if="obj.isEllipsis==true" class="dhl_break">...</div>'
			+'  <a ng-if="obj.pageNum==-2" ng-click="'+objName+'.nextPage();" title="" href="javascript:void(0)">></a></li>'
			+' </ul>'
			+' </div>'
		+'</div>';
		element.html(template);
		
//		$timeout(function(){
//			console.info(window.top.getTablDiyData());
//			console.info(headList);
			if(diytableName != null && diytableName != undefined){
				var obj = window.top.getTablDiyData();
				if(obj!=null && obj != undefined && obj != '' && obj.tableDiy != null && obj.tableDiy != undefined && obj.tableDiy != ''){
					var titleStr = "";
					for(var i=0;i<obj.tableDiy.length;i++){
						if(diytableName == obj.tableDiy[i].tableName){
							titleStr = obj.tableDiy[i].title;
							break;
						}
					}
					var titles = titleStr.split(',');
					var newHeadList = new Array();
					for(var j=0;j<titles.length;j++){
						for(var h=0;h<headList.length;h++){
							if(titles[j] == headList[h].name){
								newHeadList.push(headList[h]);
								break;
							}
						}
					}
					headList = newHeadList;
					if(isFilter=="true"){
						filterStr = "";
						for(var head in headList){
							filterStr+="|filter :{"+headList[head].code+":"+objName+".filterInput."+headList[head].code+"}";
						}
						element.html(template);
					}
				}
			}
//		},2000);
		return function( $scope, element, attrs ) {
			var page={
	        		pageCount:10,//分页数据的大小
	        		pageNum:1,//当前的分页的第几页
	        		totalNum:1,//总的页数
	        		total:0,//总记录数
	        		params:{},//请求的参数
	        		isLoad:false,
	        		isShowTotal:isShowTotal,
	        		pageList:new Array(),
	        		headList:headList,//头部的标题
	        		isPage:true,//是否分页
	        		isSum:isSum,//是否需要显示汇总的行
	        		urlInfo:{},//发送的请求的信息
	        		data:{},//页面的加载的数据
	        		sumList:{},//汇总的数据
	        		isFilter:isFilter,//是否需要过滤数据
	        		filterInput:{},//保存哪些字段需要过滤
	        		orderFile:"",//排序的字段
	        		selected:new Array(),//checkbox选中的数据
	        		isMultiSelect:isMultiSelect,//checkbox 是否可以多选
	        		isShowCheck:isShowCheck,//是否显示checkbox
	        		isShowBt:isShowBt,
	        		isShowPage:isShowPage,
	        		btCssType:btCssType,
	        		btTip:btTip,
	        		id:id,
	        		totalValue:{
	        			"transferValue":0
	        		},
	        		rowColorCallback: undefined,
	        		isShowSeq:isShowSeq,//是否显示序号
	        		
	        		getFilterData:function(){
	        			if(isFilter=="true"){
	        				var originalData=this.data.items;
	        				for(var head in headList){
	        					var inputVl=$scope.$eval(objName+".filterInput."+headList[head].code);
	        					if(inputVl!=undefined){
	        						var filterObj={};
	        						filterObj[headList[head].code]=inputVl;
	        						originalData=$filter('filter')(originalData, filterObj);
	        					}
	        				};
	        				return originalData;
	        			}
	        		},
	        		isSelected:function(inputId){
	        			//判断输入的值是否已经选中了
	        			
	        			inputId=""+inputId;
	        			return this.selected.indexOf(inputId)==-1 ?false:true;
	        		},
	        		setTransferValue:function(code){
	        			var that = this;
	        			var data = that.data.items;
	        			var totalValue = 0;
	        			for(var i=0;i<data.length;i++){
	        				if(eval("data["+i+"]."+code) != null && eval("data["+i+"]."+code) != undefined ){
        						totalValue = totalValue + Number(eval("data["+i+"]."+code));
	        				}
	        			}
        				eval("that.totalValue."+code+"="+totalValue);
	        		},
	        		downloadExcelFile:function(){
	        			var queryUrl=this.params.url;
	        			var queryBo=queryUrl.substr(0,queryUrl.indexOf(".ajax"));
	        			var queryCmd=queryUrl.substr(queryUrl.indexOf("cmd=")+4,queryUrl.length);
	        			
	        			var url = queryBo+".ajax?cmd=downloadExcelFile";
	        			
	        			var excelKeys=new Array();
	        			var excelLables=new Array();
	        			
	        			for(var index in this.headList){
	        				var el=this.headList[index];
	        				if(index == 0){
	        					//对于统计处理
	        					excelKeys.push(el.code+"*");
	        					excelLables.push(el.name);
	        				}else{
	        					excelLables.push(el.name);
		        				if(el.isSum){
		        					//对于统计处理
		        					excelKeys.push(el.code+"@");
		        				}else{
		        					excelKeys.push(el.code);
		        				}
	        				}
	        				
	        					
	        			}
	        			
	        			var queryParamStr="";
	        			for(var key in this.params.params){
	        				if(this.params.params[key]!=undefined){
	        					queryParamStr=queryParamStr+"&"+key+"="+this.params.params[key];
	        				}
	        			}
	        			//isAscTrue == 1 导出顺序 业务代码自己处理（web页面是倒序的）
	        			var toUrl = signUrl(url+"&queryUrl="+queryBo+"|"+queryCmd+"&excelKeys="+excelKeys+"&excelLables="+excelLables+queryParamStr+"&_ALLEXPORT=1&isAscTrue=1");
	        			var iframe = document.createElement("iframe");
	        		    iframe.id = "frameDownloading";
	        		    iframe.src = encodeURI(toUrl);
	        		    iframe.style.display = "none";
	        		    document.body.appendChild(iframe);
	        		    var that=this;
	        		    
	        		    
	        		    var checkIframe = document.createElement("iframe");
	        		    checkIframe.id = "checkIframe";
	        		    checkIframe.style.display = "none";
	        		    document.body.appendChild(checkIframe);
	        		    window.top.showLoad();
	        		    that.inter=$interval(function() {
	        		    	var checkUrl=queryBo+".ajax?cmd=checkFinishDownLoad&time="+(new Date()).getTime();
	        		    	commonService.postUrl(checkUrl,"",function(data){
		        				if(data.result=="true"){
		        					$interval.cancel(page.inter);
		        		        	var frameDownloadExcel = document.createElement("iframe");
		        		        	frameDownloadExcel.id = "frameDownloadExcel";
		        		        	frameDownloadExcel.src = encodeURI(signUrl(queryBo+".ajax?cmd=downloadExcelFileFromSession"));
		        		        	frameDownloadExcel.style.display = "none";
		    	        		    document.body.appendChild(frameDownloadExcel);
		    	        		    window.top.hideLoad();
		        				}
		        				
		                	});
	        		    }, 5000);
	        		},
	        		btClick:function($event,id,isAll){
	        			this.selected=new Array();
	        			if(isAll==-1){
	        				//加载全部数据
	        				var list=this.data.items;
		        			if(list!=null && list!=undefined && list.length>0){
		        				for(var index in list){
		        					if(list[index]!=null && list[index]!=undefined){
		        						var dateId=list[index][this.id];
			        					this.selected.push(dateId);
		        					}
			        			}
			        		}
	        			}else{
	        				this.selected.push(id);
	        			}
	        			
	        			if(btClickCallMethod!=undefined){
	        				this.getFilterData();
	        				eval("$scope."+btClickCallMethod+"('"+id+"',"+isAll+")");
	        			}
	        		},
	        		//更新选中的数据
	        		
	        		//由于原生的input框
	        		updateSelection:function($event,id,isAll){
	        			id=""+id;
	        			var checkbox = document.getElementById(objName+".checkId"+id);
	        			if(checkbox == undefined){
	        				return ;
	        			}
			        	var action = (checkbox.checked?'remove':'add');
			        	
	        			if(this.isMultiSelect!="true"){
	        				this.clearAllCheckbox();
	        			}
	        			//全选的操作
	        			if(isAll!=undefined && isAll==-1){
	        				
	        				if(action == 'add' && this.selected.indexOf(id) == -1){
	        					this.selectAllCheckbox();
	    			         }
	    			         if(action == 'remove'){
	    			        	 this.clearAllCheckbox();
	    			         }
	        				
	        			}else{
	        				if(action == 'add'){
	        					if(this.selected.indexOf(id) == -1){
	        						this.selected.push(id);
	        					}
	    			            checkbox.checked=true;
	    			         }
	    			         if(action == 'remove' ){
	    			        	 if(this.selected.indexOf(id)!=-1){
	    			        		 var idx = this.selected.indexOf(id);
		    			             this.selected.splice(idx,1);
	    			        	 }
	    			            
	    			             checkbox.checked=false;
	    			         }
	        			}
	        			
	        		},
	        		changeData:function(){
	        			this.clearAllCheckbox();
	        		},
	        		//清除所有的checkbox
	        		clearAllCheckbox:function(){
	        			var checkbox = document.getElementsByName(objName+".checkName");
	        			for(var index in checkbox){
	        				checkbox[index].checked=false;
	        			}
	        			this.selected=[];
	        		},
	        		
	        		//清除所有的checkbox
	        		selectAllCheckbox:function(){
	        			this.selected=[];
	        			var checkbox = document.getElementsByName(objName+".checkName");
	        			for(var index in checkbox){
	        				checkbox[index].checked=true;
	        				if(checkbox[index].value!=undefined && checkbox[index].value!=""){
	        					this.selected.push(checkbox[index].value);
	        				}
	        			}
	        			
	        		},
	        		//获取选中的数据
	        		getSelected:function(){
	        			return this.selected;
	        		},
	        		//获取单选方式选中的数据
	        		getOneSelected:function(){
	        			if(this.selected.length==1){
	        				return this.selected[0];
	        			}else{
	        				return "";
	        			}
	        		},
	        		//获取选中的数据
	        		getSelectData:function(){
	        			var selectArray=new Array();
	        			var list=this.data.items;
	        			if(list!=null && list!=undefined && list.length>0){
	        				for(var index in list){
	        					if(list[index]!=null && list[index]!=undefined){
	        						var dateId=list[index][this.id];
			        				for(var selectIndex in this.selected){
			        					if(this.selected[selectIndex]==dateId){
			        						selectArray.push(list[index]);
			        					}
			        				}
	        					}
		        			}
	        			}
	        			return selectArray;
	        		},
	        		
	        		//排序触发的方法
	        		sort:function(code){
	        			var asc="+";
	        			if(this.orderFile!=undefined){
	        				if(this.orderFile.indexOf("+")!=-1){
	        					asc="-";
	        				}
	        			}
	        			this.orderFile=asc+code;
	        		},
	        		//加载数据
	        		load:function(pageParms,type){
	        			var that=this;
	        			that.isLoad=false;
	        			window.top.showLoad();
	        			//bug 当用户选择不是第一页的按钮后，如?俨檠氖焙颍夭坏降谝灰车奈侍?
	        			if(type==undefined){
	        				pageParms.params.page=1;
	        			}	        			
	        			if(pageParms==undefined){
	        				pageParms=this.urlInfo;
	        			}else{
	        				this.urlInfo=pageParms;
	        			}
	        			that.pageNum=pageParms.params.page;
	        			if(pageParms.params.rows!=undefined && pageParms.params.rows!=null){
	        				that.pageCount=pageParms.params.rows;
	        			}
	        			pageParms.params.rows=that.pageCount;
	        			this.params=pageParms;
	        			commonService.postUrl(pageParms.url,pageParms.params,function(data){
	        				that.clearAllCheckbox();
	        				that.isLoad=false;
	        				window.top.hideLoad();
	        				that.data = data;
	        				if(data.total!=undefined && data.total!=null ){
	        					that.total=data.total;
	        					if(that.total==0){
	        						that.pageNum=0;
	        					}
	        				}
	        				if(data.totalNum!=undefined && data.totalNum!=null ){
	        					that.total=data.totalNum;
	        					if(that.total==0){
	        						that.pageNum=0;
	        					}
	        				}
	        				if(data.totalNum!=undefined && data.totalNum!=null ){
	        					that.total=data.totalNum;
	        					if(that.total==0){
	        						that.pageNum=0;
	        					}
	        				}
	        				if(pageParms.callBack!=null && pageParms.callBack!=undefined && pageParms.callBack!=""){
	        					eval(pageParms.callBack+"('"+data+"')");
	        				}
	        				
	        				// data
	        				// add by yangy, 增加回调函数，给外部提供一个回调函数来支持行颜色的变化
	        				var rowColorCallbackIsFunction = that.rowColorCallback != undefined && that.rowColorCallback instanceof Function;
	        				for(var i = 0; undefined != data && undefined != data.items && i < data.items.length; i++) {
	        					if (rowColorCallbackIsFunction) {
	        						var rowColor = that.rowColorCallback(data.items[i]);
	        						if (undefined == rowColor)
	        							rowColor = '';
	        						data.items[i].rowColor = rowColor;
	        					} else {
	        						data.items[i].rowColor = '';
	        					}
	        				}
	        				// add by yangy, 增加回调函数，给外部提供一个回调函数来支持行颜色的变化
	        				
	        				that.initPage();
	        				that.pageList=that.getPageArray(that.totalNum,that.pageNum);
	                	},function(){
	                		window.top.hideLoad();
	                	});
	        		},
	        		loadData:function(data){
	        			var that = this;
	        			that.data.items = data;
	        			that.total=data.length;
	        		},
	        		//重新加载数据
	        		reLoad:function(toChangePage){
	        			var that=this;
	        			if(toChangePage!=undefined && toChangePage!=""){
	        				if(toChangePage<0){
	        					if((this.total+toChangePage)%this.pageCount==0){
	        						that.params.params.page=that.params.params.page-1;
	        					}
	        				}else {
	        					if((this.total+toChangePage)%this.pageCount==1){
	        						that.params.params.page=that.params.params.page+1;
	        					}
	        				}
	        				
	        			}
	        			commonService.postUrl(that.params.url,that.params.params,function(data){
	        				that.data = data;
	        				if(data.total!=undefined && data.total!=null){
	        					that.total=data.total;
	        				}
	        				if(data.totalNum!=undefined && data.totalNum!=null){
	        					that.total=data.totalNum;
	        				}
	        				if(that.params.params.callBack!=undefined ){
	        					eval(that.params.params.callBack+"(data)");
	        				}
	        				that.initPage();
	                	});
	        		},
	        		initPage : function(){
	        			if(this.total%this.pageCount !=0){
	        				this.totalNum = Math.floor(this.total/this.pageCount)+1;
	        			}else{
	        				this.totalNum = Math.floor(this.total/this.pageCount);
	        			}
	        			if(this.pageNum>this.totalNum){
	        				this.pageNum=this.totalNum;
        				}
	        		},
	        		
	        		//更改分页大小
	        		changeRows : function(rowsNum){
	        			this.pageCount = rowsNum;
	        			if(rowsNum==-1){
	        				this.params.params._ALLEXPORT=1;
	        				this.params.params.rows=undefined;
	        			}else{
		        			this.params.params.rows=rowsNum;
		        			this.params.params.page=1;
		        			delete this.params.params._ALLEXPORT;
	        			}
	        			this.load(this.params,1);
	        		},
	        		//指定页
	        		changePage : function(page){
	        			if(this.totalNum==0){
	        				commonService.alert("没有数据");
	        				return;
	        			}
	        			if(page<=1 && this.params.params.page==page){
	        				commonService.alert("已经是第一页了");
	        			}else if(page>this.totalNum ){
	        				commonService.alert("已经是最后一页了");
	        			}else {
	        				this.params.params.page = page;
	        				this.load(this.params,1);
	        			}
	        		},
	        		//下一页
	        		nextPage : function(){
	        			if(this.totalNum==0){
	        				commonService.alert("没有数据");
	        				return;
	        			}
	        			if(this.params.params.page>=this.totalNum){
	        				commonService.alert("超出最大页数");
	        			
	        				return ;
	        			}
	        			this.params.params.page = parseInt(this.params.params.page) +1;
	        			this.load(this.params,1);
	        		},
	        		//上一页
	        		prePage : function(){
	        			if(this.totalNum==0){
	        				commonService.alert("没有数据");
	        				return;
	        			}
	        			if(this.params.params.page<=1){
	        				commonService.alert("已经是第一页了");
	        				return;
	        			}
	        			this.params.params.page = parseInt(this.params.params.page) -1;
	        			this.load(this.params,1);
	        		},
	        		
	        		//跳转到指定页面
	        		goPage : function(num){
	        			this.changePage(num);
	        			eval("$scope."+objName+".govalue=''");
	        		},
	        		//跳转到最后一页
	        		toEndPage : function(){
	        			if(this.params.params.page>=this.totalNum){
	        				commonService.alert("超出最大页数");
	        				return ;
	        			}
	        			page.changePage(this.totalNum);
	        		},
	        		toIndexPage:function(){
	        			if(this.params.params.page<=1){
	        				commonService.alert("已经是第一页了");
	        				return;
	        			}
	        			page.changePage(1);
	        		},
	        		/**
	        		 * 入参为 分页的总页数，当前页数
	        		 * 返回值：返回一个展示分页的列表对象
	        		 *     该对象
	        		 *    ｛
	        		 *    		isChoose:是否选中状态, 当前页的数据
	        		 *          pageNum:第几页：－1 为上一页，－2 为下一页
	        		 *          isEllipsis:是否是省略号
	        		 *     ｝
	        		 */
	        		getPageArray:function(totalNum,pageNum){
	        			var retList=new Array();
	        			var prePage={
		        				isChoose:false,
		        				pageNum:-1,
		        				isEllipsis:false
		        			};
	        			var nextPage={
		        				isChoose:false,
		        				pageNum:-2,
		        				isEllipsis:false
		        			};
		        		retList.push(prePage);
		        		
		        		//省略号
        				var ellipsis={
		        				isChoose:false,
		        				pageNum:0,
		        				isEllipsis:true
		        			};
		        		
	        			//第一种情况：页数没有超过 6页
	        		    // 上一页  1 2 3 4 5 6下一页
	        			if(totalNum<=6){
	        				for(var i=1;i<=totalNum;i++){
	        					var pageTemp={};
	        					pageTemp.pageNum=i;
	        					pageTemp.isEllipsis=false;
	        					if(i==pageNum){
	        						pageTemp.isChoose=true;
	        					}else{
	        						pageTemp.isChoose=false;
	        					}
	        					retList.push(pageTemp);
	        				}
	        			}
	        			//第二种情况，页数超过6页的时候，需要添加省略号
	        			if(totalNum>6){
	        				//如果当前页数小于6的，123456...
	        				if(pageNum<6){
		        				for(var i=1;i<=6;i++){
		        					var pageTemp={};
		        					pageTemp.pageNum=i;
		        					pageTemp.isEllipsis=false;
		        					if(i==pageNum){
		        						pageTemp.isChoose=true;
		        					}else{
		        						pageTemp.isChoose=false;
		        					}
		        					retList.push(pageTemp);
		        				}
		        				
		        				retList.push(ellipsis);
	        				}else{
	        					//计算需要展示的数字，在当前页的前后加起来5个数字
	        					var endNum=pageNum+2;
	        					if(totalNum<endNum){
	        						endNum=totalNum;
	        					}
	        					
	        					var firsPage={
	        							isChoose:false,
				        				pageNum:1,
				        				isEllipsis:false
	        					}
	        					
	        					retList.push(firsPage);
	        					
	        					ellipsis={
	    		        				isChoose:false,
	    		        				pageNum:0,
	    		        				isEllipsis:true
	    		        			};
		        				retList.push(ellipsis);
	        					
		        				//展示的数据
		        				for(var i=endNum-5;i<=endNum;i++){
		        					var pageTemp={};
		        					pageTemp.pageNum=i;
		        					pageTemp.isEllipsis=false;
		        					if(i==pageNum){
		        						pageTemp.isChoose=true;
		        					}else{
		        						pageTemp.isChoose=false;
		        					}
		        					retList.push(pageTemp);
		        				}
		        				//如果展示的数据最大的值比总数小2以上，展示省略号和最后一页
		        				if(totalNum-endNum>=2){
		        					ellipsis={
		    		        				isChoose:false,
		    		        				pageNum:0,
		    		        				isEllipsis:true
		    		        			};
		        					retList.push(ellipsis);
		        					var lastPage={
		        							isChoose:false,
					        				pageNum:totalNum,
					        				isEllipsis:false
		        					};
		        					retList.push(lastPage);
		        				}
		        				
	        				}
	        			}
	        			retList.push(nextPage);
	        			return retList;
	        		}
	        	};
				$scope[objName]=page;
            	//eval($scope+"."+objName+"=page");
            };
	};
	return myTable;
}]);

/**
 * 表格标签
 * 
 * 对外提供的属性：
 *    @param head :传入头部的标签，格式为数组对象 [{},{}]
 *            对象支持的参数：
 *            	name 表头的名称
 *              code 列表返回的数据对应的字段的key值
 *              isSum 值为：true/false ，表示该列是否需要汇总
 *    @param  isSum 表示是否在列表底部显示汇总行
 *    @param  isFilter 表示是否在表格的头部下展示输入框，用于过滤表格数据
 *    @param  isShowSeq 表示是否在表格的第一列显示一个递增的序号
 *    @param  isShowCheck 表示是否显示checkbox的
 *    @param  isMultiSelect 表示checkbox 是支持单选，还是多选
 *    @param  isShowTab  表示是否显示页码
 *    @param  id 表示每一行的数据的一个组件的字段key值
 *    @param  name 表示这个标签的对象，如果页面只有一个表格，可以不传入该值，默认值为“page”
 * 
 * 对外提供的方法：
 *    getSelected :返还所有选中的数据的主键
 *    getOneSelected: 如果选择的是单选的，可以直接调用该方法，返还一条数据的主键
 *    getSelectData: 返还选择的数据的全部数据内容
 *    
 *    downloadExcelFile:导出excel方法，请求的bo需要继承baseBo
 * 	  load  发送表格的请求，格式为对象
 *                url 请求的地址
				  params 请求的参数
 *    reload
 * 
 **/
commonApp.directive('myDiyTable',['commonService','$interval',function(commonService,$interval){
	var myTable={};
	myTable.restrict="E";
	myTable.transclude=true;
	myTable.compile=function(element, attrs){
		var objName=attrs.name;
		var headList=angular.fromJson(attrs.head);
		var isSum=attrs.issum;
		var isFilter=attrs.isfilter;
		var isShowTotal=attrs.showtotal;
		var isShowTab = attrs.isshowtab;
		var isShowSeq=attrs.isshowseq;//是否显示序号
		var filterStr="";//过滤的字符串
		var id=attrs.id;//每一行的数据的主键
		var isMultiSelect=attrs.ismultiselect;//单选框是否可以多选
		var isShowCheck=attrs.isshowcheck;//是否显示checkbox
		var doubleClickCallMethod = attrs.doubleclick; //双击调用方法
		var isAutoHide = attrs.isautohide; //是否随着列表增高自动加高
		var heightSize = "min-height:220px";
		var h2Size = "";
		var diytableName = attrs.tablename;
		if(isAutoHide == "false" || isAutoHide == false){
			heightSize = "";
			h2Size = "height:220px";
		}else{
			h2Size = "min-height:220px";
		}
		
		if(doubleClickCallMethod == undefined){
			doubleClickCallMethod = "doubleClickCallMethod";
		}
		isShowTab=isShowTab=="false"?false:true;
		isShowTotal=isShowTotal=="false"?false:true;
		if(objName==undefined){
			objName="diy";
		}
		var tableWidth = attrs.width;
		if(tableWidth == undefined || tableWidth == null || tableWidth == ""){
			tableWidth = "";
		}
		
		//拼装过滤的字符串
		if(isFilter=="true"){
			for(var head in headList){
				filterStr+="|filter :{"+headList[head].code+":"+objName+".filterInput."+headList[head].code+"}";
			};
		}
		
		var template='<div style="position: relative;">'
					+'<div style=" '+heightSize+'; position: relative;">'
					+'<div  class="table_height" style="/*min-height:270px;*/  '+h2Size+'; width:100%;overflow:auto;" >'
					+'<table class="search_lista" width="100%" border="0" cellspacing="0" cellpadding="0" style="width:'+tableWidth+';">'
					+' <thead class="fixed-thead" style="width:'+tableWidth+';">'
					+'<tr style="width:'+tableWidth+';">'
					+'   <th width="60" class="search_list_border" ng-if="'+objName+'.isShowCheck==\'true\'" ><div class="controls" ng-if="'+objName+'.isShowCheck==\'true\' && '+objName+'.isMultiSelect==\'true\'"><input id="'+objName+'.checkIdall" name="'+objName+'.checkName" class="none"  type="checkbox"><label for="checkbox" ng-click="'+objName+'.updateSelection($event,\'all\',-1)"></label></div></th>'
					
					+'   <th width="60" class="xlh" ng-if="'+objName+'.isShowSeq==\'true\'"><div style="font-family:宋体;font-size: 12px;font-weight: bold;">序号</div></th>'
					
					+'   <th width="{{o.width == undefined ? 140 : o.width}}" ng-repeat="o in '+objName+'.headList" ng-click="'+objName+'.sort(o.code)" >{{o.name}}<i ng-if="'+objName+'.orderFile==\'+\'+o.code"  class="list_top_tp"></i><i ng-if="'+objName+'.orderFile==\'-\'+o.code"  class="list_top_tpx"></i></th>'
					+'</tr>'
					+'<tr ng-if="'+objName+'.isFilter==\'true\'" class="ng-cloak " >'
					+'<th valign="top" width="25px" style="padding-left: 14px;" ng-if="'+objName+'.isShowBt==\'true\'"></th>'
					+'<th ng-if="'+objName+'.isShowCheck==\'true\'" width="60" class="search_list_border"></th>'
					+'<th ng-if="'+objName+'.isShowSeq==\'true\'" width="60" class="xlh" ></th>'
					+'<th width="{{t.width == undefined ? 140 : t.width}}" valign="top" ng-repeat="t in '+objName+'.headList" >'
					+'<input value=""  class="inp_sr"  type="text" maxlength="20" ng-change="$parent.'+objName+'.changeData();" ng-model="$parent.'+objName+'.filterInput[t.code]" onclick="scrollTopTable()">'
					+'</th>'
					+'</thead>'
					+'<tbody class="fixed-tbody" ng-class="{true:\'m_true\', false: \'m_false\'}['+objName+'.isFilter]" style="width:'+tableWidth+';">'
//					+'<tr ng-if="'+objName+'.isFilter==\'true\'" class="ng-cloak " >'
//					+'<td ng-if="'+objName+'.isShowCheck==\'true\'" width="79" class="search_list_border"></td>'
//					+'<td ng-if="'+objName+'.isShowSeq==\'true\'" width="79" class="xlh" ></td>'
//					+'<td width="{{t.width == undefined ? 140 : t.width}}" valign="top" ng-repeat="t in '+objName+'.headList" >'
//					+'<input value=""  class="inp_sr"  type="text" ng-model="$parent.'+objName+'.filterInput[t.code]">'
//					+'</td>'
//					+' </tr>'
					+'<tr class="ng-cloak " ng-dblclick="'+doubleClickCallMethod+'(d)"  ng-class="{\'selected1\': '+objName+'.isSelected(d.'+id+')}" ng-repeat="d in '+objName+'.data.items '+filterStr+' | orderBy:'+objName+'.orderFile"  ng-click="'+objName+'.updateSelection($event,d.'+id+')" >'
					+'<td ng-if="'+objName+'.isShowCheck==\'true\'" width="60" class="search_list_border" ><div class="controls"><input  class="none"  id="{{\''+objName+'.checkId\'+d.'+id+'}}" value="{{d.'+id+'}}"  type="checkbox" name="'+objName+'.checkName" ><label for="check-1"   ></label></div></td>'
					+'<td ng-if="'+objName+'.isShowSeq==\'true\'" width="60" class="xlh" ><div>{{$index+1}}</div></td>'
					+'<td width="{{t.width == undefined ? 140 : t.width}}" style="white-space: nowrap !important;word-break: keep-all;overflow: hidden;text-overflow: ellipsis;"   ng-repeat="t in '+objName+'.headList" >'
					+'<span ng-if="t.isEdit==undefined||t.isEdit==true&&!(t.onlySum!=undefined&&t.onlySum==\'false\')">'
					+	'<span ng-if="t.number!=undefined && t.isshow0 " style="width:{{t.width == undefined ? 140 : t.width}}px;white-space: nowrap !important;word-break: keep-all;overflow: hidden;text-overflow: ellipsis;display: block;text-align:left;padding: 0 5px;">{{(t.isEdit)}}{{$eval("d."+t.code) == 0 ? "0" : $eval("d."+t.code) | number: t.number}}</span>'
					+	'<span ng-if="t.number!=undefined && (t.isshow0 ==undefined || !t.isshow0) " style="width:{{t.width == undefined ? 140 : t.width}}px;white-space: nowrap !important;word-break: keep-all;overflow: hidden;text-overflow: ellipsis;display: block;text-align:left;padding: 0 5px;">{{(t.isEdit)}}{{$eval("d."+t.code) | filterString | number: t.number}}</span>'
					+	'<span ng-if="t.number==undefined" style="width:{{t.width == undefined ? 140 : t.width}}px;white-space: nowrap !important;word-break: keep-all;overflow: hidden;text-overflow: ellipsis;display: block;text-align:left;padding: 0 5px;" title="{{$eval(\'d.\'+t.code)}}">{{(t.isEdit)}}{{$eval("d."+t.code)}}</span>'
					+'</span>'
					+'<span ng-if="t.isEdit!=undefined&&!(t.isEdit==true)&&!(t.onlySum!=undefined&&t.onlySum==\'false\')&&t.isDate==undefined">'
					+	'<input ng-if="t.number==undefined" class="form_term1" maxlength="20" style="border: 1px solid #ff0000;height: 18px;background-color: #fff;font-size: 12px;"  title="{{$eval(\'d.\'+t.code)}}" value="{{$eval(\'d.\'+t.code)}}" onkeyup="this.value=this.value.replace(/^\\D*(\\d*(?:\\.\\d{0,2})?).*$/g, \'$1\');" ng-blur="'+objName+'.setTransferValue(t.code,t.callback)" ng-model="d[t.code]"/>'
					+'</span>'
					+'<span ng-if="t.onlySum!=undefined&&t.onlySum==\'false\'">'
					+	'<input  class="form_term1" maxlength="20" style="border: 1px solid #ff0000;height: 18px;background-color: #fff;font-size: 12px;"  title="{{$eval(\'d.\'+t.code)}}" value="{{$eval(\'d.\'+t.code)}}"  ng-model="d[t.code]"/>'
					+'</span>'
					+'<span ng-if="t.isDate!=undefined&&t.isDate==\'true\'">'
					+	'<input  class="form_term1 Wdate"  my-Date-Picker=\'{"minDate":"2016-10-01"}\'   style="border: 1px solid #ff0000;height: 18px;background-color: #fff;font-size: 12px;" id="{{d.'+id+'}}time" title="{{$eval(\'d.\'+t.code)}}" value="{{$eval(\'d.\'+t.code)}}"  ng-model="d[t.code]"/>'
					+'</span>'
					+'</td>'
					+' </tr>'
//					+'<tr ng-if="'+objName+'.isSum==\'true\'" class="ng-cloak" >'
//					+'<td width="79" class="" ng-if="'+objName+'.isShowCheck==\'true\'"></td>'
//					+'<td ng-if="'+objName+'.isShowSeq==\'true\'" width="79" class="xlh" ></td>'
//					+'<td ng-repeat="t in '+objName+'.headList" >'
//					+'<span class="text_list"  ng-if="t.number!=undefined && t.isSum==\'true\'">{{'+objName+'.data.items '+filterStr+' |sumOfItems:t.code |number: t.number}}</span>'
//					+'<span class="text_list" ng-if="t.number==undefined && t.isSum==\'true\'">{{'+objName+'.data.items '+filterStr+' |sumOfItems:t.code }}</span>'
//					+'</td>'
//					+' </tr>'
					+'</tbody>'
					+'<tfoot class="fixed-tfoot tfoot" style="width:'+tableWidth+';">'
					+'<tr ng-if="'+objName+'.isSum==\'true\'" class="ng-cloak" >'
					+'<td valign="top" width="25px" style="padding-left: 14px;" ng-if="'+objName+'.isShowBt==\'true\'" ></td>'
					+'<td width="60" class="" ng-if="'+objName+'.isShowCheck==\'true\'"></td>'
					+'<td ng-if="'+objName+'.isShowSeq==\'true\'" width="60" class="xlh" ></td>'
					+'<td width="{{t.width == undefined ? 140 : t.width}}" ng-repeat="t in '+objName+'.headList" >'
					+'<span class="text_list" ng-if="t.number!=undefined && t.isSum==\'true\'">{{'+objName+'.data.items '+filterStr+' |sumOfItems:t.code | filterString |number: t.number}}</span>'
					+'<span class="text_list" ng-if="t.number==undefined && t.isSum==\'true\'">{{'+objName+'.data.items '+filterStr+' |sumOfItems:t.code }}</span>'
					+'</td>'
					+' </tr>'
					+'</tfoot>'
					+'</table>'
					+' </div>'
					+' </div>'
			+'<div ng-show="'+objName+'.isShowTab">'
				+'<div ng-show="'+objName+'.total==0 && !'+objName+'.isLoad" class="search_page" style=" text-align:center; line-height:50px; color:#ff7800;top:160px;">没有符合条件的数据'
				+'</div>'
				+'<div class="chey_b_1 list_fye clearfix ">'
				+'<div class="chey_xs fl">'
				+'<span>显示</span><a ng-click="'+objName+'.changeRows(10);" href="javascript:void(0)" ng-class="{hover:'+objName+'.pageCount==10}" class="hover">10</a><a ng-click="'+objName+'.changeRows(20);" href="javascript:void(0)" ng-class="{hover:'+objName+'.pageCount==20}">20</a><a ng-click="'+objName+'.changeRows(50);" href="javascript:void(0)" ng-class="{hover:'+objName+'.pageCount==50}">50</a><span>条</span>共{{'+objName+'.total}}条数据'
				+'</div>'
				+' <ul class="pagination fr" ng-show="'+objName+'.total>0">'
				+'  <li ng-class="{active:obj.isChoose==true}" ng-repeat="obj in '+objName+'.pageList" ><a ng-if="obj.pageNum==-1" ng-click="'+objName+'.prePage();" title="" href="javascript:void(0)"><</a>'
				+'  <a ng-if="obj.pageNum>=1"  ng-click="'+objName+'.goPage(obj.pageNum);" href="javascript:void(0)">{{obj.pageNum}}</a>'
				+' <div ng-if="obj.isEllipsis==true" class="dhl_break">...</div>'
				+'  <a ng-if="obj.pageNum==-2" ng-click="'+objName+'.nextPage();" title="" href="javascript:void(0)">></a></li>'
				+' </ul>'
				+' </div>'
			+'</div>'
		+'</div>';
		element.html(template);
		if(diytableName != null && diytableName != undefined){
			var obj = window.top.getTablDiyData();
			if(obj!=null && obj != undefined && obj != '' && obj.tableDiy != null && obj.tableDiy != undefined && obj.tableDiy != ''){
				var titleStr = "";
				for(var i=0;i<obj.tableDiy.length;i++){
					if(diytableName == obj.tableDiy[i].tableName){
						titleStr = obj.tableDiy[i].title;
						break;
					}
				}
				var titles = titleStr.split(',');
				var newHeadList = new Array();
				for(var j=0;j<titles.length;j++){
					for(var h=0;h<headList.length;h++){
						if(titles[j] == headList[h].name){
							newHeadList.push(headList[h]);
							break;
						}
					}
				}
				headList = newHeadList;
				if(isFilter=="true"){
					filterStr = "";
					for(var head in headList){
						filterStr+="|filter :{"+headList[head].code+":"+objName+".filterInput."+headList[head].code+"}";
					}
					element.html(template);
				}
			}
		}
		return function( $scope, element, attrs ) {
			var page={
	        		pageCount:10,//分页数据的大小
	        		pageNum:1,//当前的分页的第几页
	        		totalNum:1,//总的页数
	        		total:0,//总记录数
	        		params:{},//请求的参数
	        		isLoad:true,
	        		isShowTotal:isShowTotal,
	        		isShowTab:isShowTab,
	        		pageList:new Array(),
	        		headList:headList,//头部的标题
	        		isPage:true,//是否分页
	        		isSum:isSum,//是否需要显示汇总的行
	        		urlInfo:{},//发送的请求的信息
	        		data:{},//页面的加载的数据
	        		sumList:{},//汇总的数据
	        		isFilter:isFilter,//是否需要过滤数据
	        		filterInput:{},//保存哪些字段需要过滤
	        		orderFile:"",//排序的字段
	        		selected:new Array(),//checkbox选中的数据
	        		isMultiSelect:isMultiSelect,//checkbox 是否可以多选
	        		isShowCheck:isShowCheck,//是否显示checkbox
	        		id:id,
	        		totalValue:{
	        			"transferValue":0
	        		},
	        		isShowSeq:isShowSeq,//是否显示序号
	        		isSelected:function(inputId){
	        			//判断输入的值是否已经选中了
	        			inputId=""+inputId;
	        			return this.selected.indexOf(inputId)==-1 ?false:true;
	        		},
	        		setTransferValue:function(code,callback){
	        			var that = this;
	        			var data = that.data.items;
	        			var totalValue = 0;
	        			for(var i=0;i<data.length;i++){
	        				if(eval("data["+i+"]."+code) != null && eval("data["+i+"]."+code) != undefined ){
        						totalValue = totalValue + Number(eval("data["+i+"]."+code));
	        				}
	        			}
        				eval("that.totalValue."+code+"="+totalValue);
        				if(callback != undefined){
        					eval(callback+"()");
        				}
	        		},
	        		downloadExcelFile:function(){
	        			var queryUrl=this.params.url;
	        			var queryBo=queryUrl.substr(0,queryUrl.indexOf(".ajax"));
	        			var queryCmd=queryUrl.substr(queryUrl.indexOf("cmd=")+4,queryUrl.length);
	        			
	        			var url = queryBo+".ajax?cmd=downloadExcelFile";
	        			
	        			var excelKeys=new Array();
	        			var excelLables=new Array();
	        			
	        			for(var index in this.headList){
	        				var el=this.headList[index];
	        				if(index == 0){
	        					//对于统计处理
	        					excelKeys.push(el.code+"*");
	        					excelLables.push(el.name);
	        				}else{
	        					excelLables.push(el.name);
		        				if(el.isSum){
		        					//对于统计处理
		        					excelKeys.push(el.code+"@");
		        				}else{
		        					excelKeys.push(el.code);
		        				}
	        				}
	        				
	        					
	        			}
	        			
	        			var queryParamStr="";
	        			for(var key in this.params.params){
	        				if(this.params.params[key]!=undefined){
	        					queryParamStr=queryParamStr+"&"+key+"="+this.params.params[key];
	        				}
	        			}
	        			//isAscTrue == 1 导出顺序 业务代码自己处理（web页面是倒序的）
	        			var toUrl = signUrl(url+"&queryUrl="+queryBo+"|"+queryCmd+"&excelKeys="+excelKeys+"&excelLables="+excelLables+queryParamStr+"&_ALLEXPORT=1&isAscTrue=1");
	        			var iframe = document.createElement("iframe");
	        		    iframe.id = "frameDownloading";
	        		    iframe.src = encodeURI(toUrl);
	        		    iframe.style.display = "none";
	        		    document.body.appendChild(iframe);
	        		    var that=this;
	        		    
	        		    
	        		    var checkIframe = document.createElement("iframe");
	        		    checkIframe.id = "checkIframe";
	        		    checkIframe.style.display = "none";
	        		    document.body.appendChild(checkIframe);
	        		    window.top.showLoad();
	        		    that.inter=$interval(function() {
	        		    	var checkUrl=queryBo+".ajax?cmd=checkFinishDownLoad&time="+(new Date()).getTime();
	        		    	commonService.postUrl(checkUrl,"",function(data){
		        				if(data.result=="true"){
		        					$interval.cancel(page.inter);
		        		        	var frameDownloadExcel = document.createElement("iframe");
		        		        	frameDownloadExcel.id = "frameDownloadExcel";
		        		        	frameDownloadExcel.src = encodeURI(signUrl(queryBo+".ajax?cmd=downloadExcelFileFromSession"));
		        		        	frameDownloadExcel.style.display = "none";
		    	        		    document.body.appendChild(frameDownloadExcel);
		    	        		    window.top.hideLoad();
		        				}
		        				
		                	});
	        		    }, 5000);
	        		},
	        		//更新选中的数据
	        		//由于原生的input框
	        		updateSelection:function($event,id,isAll){
	        			id=""+id;
	        			var checkbox = document.getElementById(objName+".checkId"+id);
	        			if(checkbox == undefined){
	        				return ;
	        			}
			        	var action = (checkbox.checked?'remove':'add');
			        	
	        			if(this.isMultiSelect!="true"){
	        				this.clearAllCheckbox();
	        			}
	        			//全选的操作
	        			if(isAll!=undefined && isAll==-1){
	        				
	        				if(action == 'add' && this.selected.indexOf(id) == -1){
	        					this.selectAllCheckbox();
	    			         }
	    			         if(action == 'remove'){
	    			        	 this.clearAllCheckbox();
	    			         }
	        				
	        			}else{
	        				if(action == 'add'){
	        					if(this.selected.indexOf(id) == -1){
	        						this.selected.push(id);
	        					}
	    			            checkbox.checked=true;
	    			         }
	    			         if(action == 'remove' ){
	    			        	 if(this.selected.indexOf(id)!=-1){
	    			        		 var idx = this.selected.indexOf(id);
		    			             this.selected.splice(idx,1);
	    			        	 }
	    			            
	    			             checkbox.checked=false;
	    			         }
	        			}
	        			
	        		},
	        		changeData:function(){
	        			this.clearAllCheckbox();
	        		},
	        		//清除所有的checkbox
	        		clearAllCheckbox:function(){
	        			var checkbox = document.getElementsByName(objName+".checkName");
	        			for(var index in checkbox){
	        				checkbox[index].checked=false;
	        			}
	        			this.selected=[];
	        		},
	        		//清除所有的checkbox
	        		selectAllCheckbox:function(){
	        			this.selected=[];
	        			var checkbox = document.getElementsByName(objName+".checkName");
	        			for(var index in checkbox){
	        				checkbox[index].checked=true;
	        				if(checkbox[index].value!=undefined && checkbox[index].value!=""){
	        					this.selected.push(checkbox[index].value);
	        				}
	        			}
	        			
	        		},
	        		//获取选中的数据
	        		getSelected:function(){
	        			return this.selected;
	        		},
	        		//获取单选方式选中的数据
	        		getOneSelected:function(){
	        			if(this.selected.length==1){
	        				return this.selected[0];
	        			}else{
	        				return "";
	        			}
	        		},
	        		//获取选中的数据
	        		getSelectData:function(){
	        			var selectArray=new Array();
	        			var list=this.data.items;
	        			if(list.length>0){
	        				for(var index in list){
	        					var dateId=list[index][this.id];
		        				for(var selectIndex in this.selected){
		        					if(this.selected[selectIndex]==dateId){
		        						selectArray.push(list[index]);
		        					}
		        				}
		        			}
	        			}
	        			return selectArray;
	        		},
	        		
	        		//排序触发的方法
	        		sort:function(code){
	        			var asc="+";
	        			if(this.orderFile!=undefined){
	        				if(this.orderFile.indexOf("+")!=-1){
	        					asc="-";
	        				}
	        			}
	        			this.orderFile=asc+code;
	        		},
	        		//加载数据
	        		load:function(pageParms){
	        			if(pageParms==undefined){
	        				pageParms=this.urlInfo;
	        			}else{
	        				this.urlInfo=pageParms;
	        			}
	        			var that=this;
	        			that.pageNum=pageParms.params.page;
	        			if(pageParms.params.rows!=undefined && pageParms.params.rows!=null){
	        				that.pageCount=pageParms.params.rows;
	        			}
	        			pageParms.params.rows=that.pageCount;
	        			this.params=pageParms;
	        			
	        			that.isLoad=false;
	        			window.top.showLoad();
	        			
	        			commonService.postUrl(pageParms.url,pageParms.params,function(data){
	        				that.clearAllCheckbox();
	        				that.isLoad=false;
	        				window.top.hideLoad();
	        				that.data = data;
	        				if(data.total!=undefined && data.total!=null ){
	        					that.total=data.total;
	        					if(that.total==0){
	        						that.pageNum=0;
	        					}
	        				}
	        				if(data.totalNum!=undefined && data.totalNum!=null ){
	        					that.total=data.totalNum;
	        					if(that.total==0){
	        						that.pageNum=0;
	        					}
	        				}
	        				if(data.totalNum!=undefined && data.totalNum!=null ){
	        					that.total=data.totalNum;
	        					if(that.total==0){
	        						that.pageNum=0;
	        					}
	        				}
	        				if(pageParms.callBack!=null && pageParms.callBack!=undefined && pageParms.callBack!=""){
	        					eval(pageParms.callBack+"('"+data+"')");
	        				}
	        				that.initPage();
	        				that.pageList=that.getPageArray(that.totalNum,that.pageNum);
	                	},function(){
	                		window.top.hideLoad();
	                	});
	        		},
	        		loadData:function(data){
	        			var that = this;
	        			that.data.items = data;
	        		},
	        		//重新加载数据
	        		reLoad:function(toChangePage){
	        			var that=this;
	        			if(toChangePage!=undefined && toChangePage!=""){
	        				if(toChangePage<0){
	        					if((this.total+toChangePage)%this.pageCount==0){
	        						that.params.params.page=that.params.params.page-1;
	        					}
	        				}else {
	        					if((this.total+toChangePage)%this.pageCount==1){
	        						that.params.params.page=that.params.params.page+1;
	        					}
	        				}
	        				
	        			}
	        			commonService.postUrl(that.params.url,that.params.params,function(data){
	        				that.data = data;
	        				if(data.total!=undefined && data.total!=null){
	        					that.total=data.total;
	        				}
	        				if(data.totalNum!=undefined && data.totalNum!=null){
	        					that.total=data.totalNum;
	        				}
	        				if(that.params.params.callBack!=undefined ){
	        					eval(that.params.params.callBack+"('"+data+"')");
	        				}
	        				that.initPage();
	                	});
	        		},
	        		initPage : function(){
	        			if(this.total%this.pageCount !=0){
	        				this.totalNum = Math.floor(this.total/this.pageCount)+1;
	        			}else{
	        				this.totalNum = Math.floor(this.total/this.pageCount);
	        			}
	        			if(this.pageNum>this.totalNum){
	        				this.pageNum=this.totalNum;
        				}
	        		},
	        		
	        		//更改分页大小
	        		changeRows : function(rowsNum){
	        			this.pageCount = rowsNum;
	        			if(rowsNum==-1){
	        				this.params.params._ALLEXPORT=1;
	        				this.params.params.rows=undefined;
	        			}else{
		        			this.params.params.rows=rowsNum;
		        			this.params.params.page=1;
		        			delete this.params.params._ALLEXPORT;
	        			}
	        			this.load(this.params,1);
	        		},
	        		//指定页
	        		changePage : function(page){
	        			if(this.totalNum==0){
	        				commonService.alert("没有数据");
	        				return;
	        			}
	        			if(page<=1 && this.params.params.page==page){
	        				commonService.alert("已经是第一页了");
	        			}else if(page>this.totalNum ){
	        				commonService.alert("已经是最后一页了");
	        			}else {
	        				this.params.params.page = page;
	        				this.load(this.params,1);
	        			}
	        		},
	        		//下一页
	        		nextPage : function(){
	        			if(this.totalNum==0){
	        				commonService.alert("没有数据");
	        				return;
	        			}
	        			if(this.params.params.page>=this.totalNum){
	        				commonService.alert("超出最大页数");
	        			
	        				return ;
	        			}
	        			this.params.params.page = parseInt(this.params.params.page) +1;
	        			this.load(this.params,1);
	        		},
	        		//上一页
	        		prePage : function(){
	        			if(this.totalNum==0){
	        				commonService.alert("没有数据");
	        				return;
	        			}
	        			if(this.params.params.page<=1){
	        				commonService.alert("已经是第一页了");
	        				return;
	        			}
	        			this.params.params.page = parseInt(this.params.params.page) -1;
	        			this.load(this.params,1);
	        		},
	        		
	        		//跳转到指定页面
	        		goPage : function(num){
	        			this.changePage(num);
	        			$scope[objName].govalue='';
	        			//eval("$scope."+objName+".govalue=''");
	        		},
	        		//跳转到最后一页
	        		toEndPage : function(){
	        			if(this.params.params.page>=this.totalNum){
	        				commonService.alert("超出最大页数");
	        				return ;
	        			}
	        			page.changePage(this.totalNum);
	        		},
	        		toIndexPage:function(){
	        			if(this.params.params.page<=1){
	        				commonService.alert("已经是第一页了");
	        				return;
	        			}
	        			page.changePage(1);
	        		},
	        		/**
	        		 * 入参为 分页的总页数，当前页数
	        		 * 返回值：返回一个展示分页的列表对象
	        		 *     该对象
	        		 *    ｛
	        		 *    		isChoose:是否选中状态, 当前页的数据
	        		 *          pageNum:第几页：－1 为上一页，－2 为下一页
	        		 *          isEllipsis:是否是省略号
	        		 *     ｝
	        		 */
	        		getPageArray:function(totalNum,pageNum){
	        			var retList=new Array();
	        			var prePage={
		        				isChoose:false,
		        				pageNum:-1,
		        				isEllipsis:false
		        			};
	        			var nextPage={
		        				isChoose:false,
		        				pageNum:-2,
		        				isEllipsis:false
		        			};
		        		retList.push(prePage);
		        		
		        		//省略号
        				var ellipsis={
		        				isChoose:false,
		        				pageNum:0,
		        				isEllipsis:true
		        			};
		        		
	        			//第一种情况：页数没有超过 6页
	        		    // 上一页  1 2 3 4 5 6下一页
	        			if(totalNum<=6){
	        				for(var i=1;i<=totalNum;i++){
	        					var pageTemp={};
	        					pageTemp.pageNum=i;
	        					pageTemp.isEllipsis=false;
	        					if(i==pageNum){
	        						pageTemp.isChoose=true;
	        					}else{
	        						pageTemp.isChoose=false;
	        					}
	        					retList.push(pageTemp);
	        				}
	        			}
	        			//第二种情况，页数超过6页的时候，需要添加省略号
	        			if(totalNum>6){
	        				//如果当前页数小于6的，123456...
	        				if(pageNum<6){
		        				for(var i=1;i<=6;i++){
		        					var pageTemp={};
		        					pageTemp.pageNum=i;
		        					pageTemp.isEllipsis=false;
		        					if(i==pageNum){
		        						pageTemp.isChoose=true;
		        					}else{
		        						pageTemp.isChoose=false;
		        					}
		        					retList.push(pageTemp);
		        				}
		        				
		        				retList.push(ellipsis);
	        				}else{
	        					//计算需要展示的数字，在当前页的前后加起来5个数字
	        					var endNum=pageNum+2;
	        					if(totalNum<endNum){
	        						endNum=totalNum;
	        					}
	        					
	        					var firsPage={
	        							isChoose:false,
				        				pageNum:1,
				        				isEllipsis:false
	        					}
	        					
	        					retList.push(firsPage);
	        					
	        					ellipsis={
	    		        				isChoose:false,
	    		        				pageNum:0,
	    		        				isEllipsis:true
	    		        			};
		        				retList.push(ellipsis);
	        					
		        				//展示的数据
		        				for(var i=endNum-5;i<=endNum;i++){
		        					var pageTemp={};
		        					pageTemp.pageNum=i;
		        					pageTemp.isEllipsis=false;
		        					if(i==pageNum){
		        						pageTemp.isChoose=true;
		        					}else{
		        						pageTemp.isChoose=false;
		        					}
		        					retList.push(pageTemp);
		        				}
		        				//如果展示的数据最大的值比总数小2以上，展示省略号和最后一页
		        				if(totalNum-endNum>=2){
		        					ellipsis={
		    		        				isChoose:false,
		    		        				pageNum:0,
		    		        				isEllipsis:true
		    		        			};
		        					retList.push(ellipsis);
		        					var lastPage={
		        							isChoose:false,
					        				pageNum:totalNum,
					        				isEllipsis:false
		        					};
		        					retList.push(lastPage);
		        				}
		        				
	        				}
	        			}
	        			retList.push(nextPage);
	        			return retList;
	        		}
	        	};
				$scope[objName]=page;
//            	eval($scope+"."+objName+"=page");
            };
	};
	return myTable;
}]);



commonApp.directive('repeatFinish',['commonService',function(commonService){
	 return {
        link: function(scope,element,attr){
            if(scope.$last == true){
            	setTimeout(function(){
            		setContentHegthDelay();
            	},500);
            }
        }
    };
}]);




/**
 * 自定义下拉列表标签
 * 实例：<my-select id="query.inoutSts" code-type="AC_CASH_PROVE@INOUT_STS"  default-value="" url="" url-param='' show-name='' show-value=''/>
 * id：ng-model
 * code-type：基础数据sys_static_data的code-type字段
 * has-all==true：所有下拉项
 * default-value：默认值
 * url：自定义查询方法出参为：codeValue=codeName键值对
 * url-param：string类型json格式数据,给url传参
 * show-name：自定义获取下拉数据名称展示
 * show-value：自定义获取下拉数据名称展示对应的值
 * change：值改变时执行方法
 * disabled="disabled='true'"  disabled='true' 禁用下拉
 * PS:程序设置下拉列表选中值的话，直接通过$scope.id = ***即可设置
 * filter 支持过滤多个以逗号分开   例如 拼单中,已删除
 */
commonApp.directive('mySelect',['commonService',function(commonService){
	var mySelect={};
	mySelect.restrict="E";
	mySelect.transclude=true;
	mySelect.compile=function(element, attrs){
		element.html("");
		var id=attrs.id;
		var htmlId=attrs.htmlId;
		var showName=attrs.showName;
		var showValue=attrs.showValue;
		var change=attrs.change;
		var disabled = attrs.disabled;
		if(disabled == undefined || disabled == null || disabled == ''){
			disabled = '';
		}
		var filterName=attrs.filter;
		if(!showName){
			showName = "codeName";
		}
		if(!showValue){
			showValue = "codeValue";
		}
		var dataId=attrs.id.replace(/\./g,"_") + "_data";
		var objId=attrs.id.replace(/\./g,"_") + "_obj";
		var template='';
		var strHtmlId = "";
		if(htmlId!=undefined&&htmlId!=""){
			strHtmlId = " id='" + htmlId + "' ";
		}
		
		var filterData = "";
        if(filterName != null&& filterName != undefined && filterName != ''){
           var fts = filterName.split(",");
           if(fts.length == 1){
        	   filterData="{\""+showName+  "\"" + ":"+"\"!"+filterName+"\""+"}";
           }else{
        	   //需要过滤多个字符串下拉菜单
        	   for(var j = 0;j<fts.length;j++){
        		   if(j != fts.length -1){
        			   filterData = filterData + "{\""+showName+  "\"" + ":"+"\"!"+fts[j]+"\""+"} | filter:";
        		   }else{
        			   filterData = filterData + "{\""+ showName+ "\""+ ":"+"\"!"+fts[j]+"\"}";
        		   }
        		   
        	   }
        	  
           }
           
        }
		
		if(filterData != ""){
			if(change!=undefined){
				template="<select "+disabled + strHtmlId + " ng-change='"+change+"' ng-model='" + id + "' ng-options='" + objId + "." + showValue + " as " + objId + "." + showName + " for " + objId + " in " + dataId + ".items | filter:"+filterData+"' />";
			}else{
				template="<select "+disabled + strHtmlId + "  ng-model='" + id + "' ng-options='" + objId + "." + showValue + " as " + objId + "." + showName + " for " + objId + " in " + dataId + ".items | filter:"+filterData+"' />";
			}
			
		}else{
			if(change!=undefined){
				template="<select "+disabled + strHtmlId + " ng-change='"+change+"' ng-model='" + id + "' ng-options='" + objId + "." + showValue + " as " + objId + "." + showName + " for " + objId + " in " + dataId + ".items' />";
			}else{
				template="<select "+disabled + strHtmlId + "  ng-model='" + id + "' ng-options='" + objId + "." + showValue + " as " + objId + "." + showName + " for " + objId + " in " + dataId + ".items' />";
			}
			
		}
		element.html(template);
		return function( $scope, element, attrs ) {
			var url = attrs.url;
			var codeType = attrs.codeType;
			var defaultValue = attrs.defaultValue;
			var hasAll = attrs.hasAll;
			var elseValue=attrs.elseValue;
			var valueArr=new Array();
			if(elseValue!=null&&elseValue!=undefined&&elseValue!=''){
				valueArr=elseValue.split("-");
			}
			var param = attrs.urlParam;
			if(param==undefined||param==""){
				param = {"hasAll":hasAll,"codeType":codeType};
			}
			else
			{
				param = eval('(' + param + ')');;
			}
			if(url==undefined||url==""){
				url = "staticDataBO.ajax?cmd=selectSysStaticDataByCodeType&time="+(new Date()).valueOf();
			}
			if(defaultValue==undefined||defaultValue==""||defaultValue==null){
				if(hasAll == "true"||hasAll == "TRUE"){
					defaultValue = "-1";
				}
			}
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					if(valueArr.length>0){
						for(var i=data.items.length-1;i>0;i--){
							for(var j=0;j<valueArr.length;j++){
								var value=eval("data.items["+i+"]."+showValue);
								if(value==valueArr[j]){
									data.items.splice(i,1);
									break;
								}
							}
						}
					}
					eval("$scope."+ dataId + "=data");
					if(defaultValue==undefined||defaultValue==""){
						if(data.items.length>0){
							eval("$scope."+ id + "=data.items[0]."+showValue);
						}
					}
					else
					{
						var tempValue = eval("$scope."+ id);
						if(tempValue=="" || tempValue == undefined){
							eval("$scope."+ id + "=defaultValue");
						}
					}
	 	    	}
			})
		};
	};
	return mySelect;
}]);




/*******************************指令 end************************************************************/

/*******************************form 表单 字段验证属性 start************************************************************/

/**
 * 浮点数校验
 * 只是保留小数点两位
 * 在需要校验的输入框加入该属性
 * 
 */
commonApp.directive("myDoubleVal", [function ($parse) {
        return {
            require:"ngModel",
            link:function(scope,ele,attrs,ctrl) {
            	var methodName='change';
            	if(attrs.myDoubleVal!=null&&attrs.myDoubleVal!=undefined&&attrs.myDoubleVal!=''){
            		methodName=attrs.myDoubleVal;
            	}
            	ele.bind(methodName,function(){
            		if(ctrl.$viewValue!=undefined){
            			var vl=ctrl.$viewValue.replace(/^((-)?\d*(?:\.\d{0,2})?).*$/g, '$1');
                		$parse(attrs['ngModel']).assign(scope, vl);
            			scope.$apply();
            		}
            	});
            	
            }
        };
 }]);

/**
 * 正整数校验
 * 
 */
commonApp.directive("myNumVal", [function ($parse) {
        return {
            require:"ngModel",
            link:function(scope,ele,attrs,ctrl) {
            	var methodName='change';
            	if(attrs.myNumVal!=null&&attrs.myNumVal!=undefined&&attrs.myNumVal!=''){
            		methodName=attrs.myNumVal;
            	}
            	ele.bind(methodName,function(){
            		if(ctrl.$viewValue!=undefined){
            			var vl=ctrl.$viewValue.replace(/[^\d]/g, '');
                		$parse(attrs['ngModel']).assign(scope, vl);
            			scope.$apply();
            		}
            	});
            	
            }
        };
 }]);


/**
 * 手机号码校验
 * 
 * 在需要校验的输入框加入该属性
 */
commonApp.directive(
				'myRequired',
				[function() {
					return {
					    require: '?ngModel',
					    link: function(scope, elm, attr, ctrl) {
					      if (!ctrl) return;
					      attr.myRequired = true; // force truthy in case we are on non input element

					      var validator = function(value) {
					        if (attr.myRequired && ctrl.$isEmpty(value)) {
					          ctrl.$setValidity('myRequired', false);
					          return;
					        } else {
					          ctrl.$setValidity('myRequired', true);
					          return value;
					        }
					      };

					      ctrl.$formatters.push(validator);
					      ctrl.$parsers.unshift(validator);

					      attr.$observe('myRequired', function() {
					        validator(ctrl.$viewValue);
					      });
					    }
					  };
}]);

/**
 * 手机号码校验
 * 
 * 在需要校验的输入框加入该属性
 */
commonApp.directive(
				'myMobileVal',
				[function() {
					return {
						require : "ngModel",
						link : function(scope, element, attr, ngModel) {
							if (ngModel) {
								var billRegexp =/^0?(13[0-9]|15[0-9]|18[0-9]|14[57]|17[0678])[0-9]{8}$/;
							}

							var customValidator = function(value) {
								var validity = ngModel.$isEmpty(value)
										|| billRegexp.test(value);
								ngModel.$setValidity("myMobileVal", validity);
								return validity ? value : undefined;
							};
							ngModel.$formatters.push(customValidator);
							ngModel.$parsers.push(customValidator);
						}
					};
}]);
/**
 * 浮点数校验
 * 在需要校验的输入框加入该属性
 * 
 */
commonApp.directive("myFloatVal", [function () {
		var FLOAT_REGEXP = /^\-?\d+(?:[.,]\d+)?$/;
        return {
            require:"ngModel",
            link:function(scope,ele,attrs,ctrl) {
            	
            	var customValidator=function(viewValue) {
            		var validity=ctrl.$isEmpty(viewValue)
					|| FLOAT_REGEXP.test(viewValue);
                    if(validity) {
                        ctrl.$setValidity("myFloatVal", true);
                        if(viewValue!=undefined && viewValue.replace!=undefined){
                        	 return parseFloat(viewValue.replace(",", "."));
                        }
                        return undefined;
                    }else {
                        ctrl.$setValidity("myFloatVal", false);
                        return undefined;
                    }
                };
                ctrl.$formatters.push(customValidator);
                ctrl.$parsers.unshift(customValidator);
            }
        };
 }]);
/**
 * 身份证校验
 * 
 * 
 */
commonApp.directive("myCardnoVal", [function () {
	
		var cardVal=function IdentityCodeValid(code) {
			var city = {
					11 : "北京",
					12 : "天津",
					13 : "河北",
					14 : "山西",
					15 : "内蒙古",
					21 : "辽宁",
					22 : "吉林",
					23 : "黑龙江 ",
					31 : "上海",
					32 : "江苏",
					33 : "浙江",
					34 : "安徽",
					35 : "福建",
					36 : "江西",
					37 : "山东",
					41 : "河南",
					42 : "湖北 ",
					43 : "湖南",
					44 : "广东",
					45 : "广西",
					46 : "海南",
					50 : "重庆",
					51 : "四川",
					52 : "贵州",
					53 : "云南",
					54 : "西藏 ",
					61 : "陕西",
					62 : "甘肃",
					63 : "青海",
					64 : "宁夏",
					65 : "新疆",
					71 : "台湾",
					81 : "香港",
					82 : "澳门",
					91 : "国外 "
				};
				var tip = "";
				var pass = true;
				
				if(!(code.length == 18 || code.length == 15)){
					tip = "身份证号码长度不对";
					pass = false;
				}
				
				if (!code
						|| !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i
								.test(code)) {
					tip = "身份证号格式错误";
					pass = false;
				}

				else if (!city[code.substr(0, 2)]) {
					tip = "地址编码错误";
					pass = false;
				} else {
					// 18位身份证需要验证最后一位校验位
					if (code.length == 18) {
						code = code.split('');
						// ∑(ai×Wi)(mod 11)
						// 加权因子
						var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
						// 校验位
						var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
						var sum = 0;
						var ai = 0;
						var wi = 0;
						for (var i = 0; i < 17; i++) {
							ai = code[i];
							wi = factor[i];
							sum += ai * wi;
						}
						var last = parity[sum % 11];
						if (parity[sum % 11] != code[17]) {
							tip = "校验位错误";
							pass = false;
						}
					}
				}
				
				return pass;
			};
        return {
            require:"ngModel",
            link:function(scope,ele,attrs,ctrl) {
            	
            	var customValidator=function(viewValue) {
            		var validity=ctrl.$isEmpty(viewValue)
					|| cardVal(viewValue);
                    if(validity) {
                        ctrl.$setValidity("myCardnoVal", true);
                        return viewValue;
                    }else {
                        ctrl.$setValidity("myCardnoVal", false);
                        return undefined;
                    }
                };
                ctrl.$formatters.push(customValidator);
                ctrl.$parsers.unshift(customValidator);
            }
        };
 }]);
/**
 * 车牌号校验
 * 
 */
 commonApp.directive("myPlateVal", [function () {
		var plateVal = function validatePlateNumber(plateNumber) {
						var chare1 = /^[\u4e00-\u9fa5]{1}[A-Z_0-9]{6}$/;
						var chare2 = /^[A-Z_0-9]{5}[\u4e00-\u9fa5]{1}$/;
						var chare3 = /^[A-Z]{2}[A-Z_0-9]{7}$/;
						var chare4 = /^[A-Z]{2}[-]{1}[0-9]{5}$/;
						var chare5 = /^[A-Z]{2}[0-9]{5}$/;
						var chare6 = /^[A-Z]{2}[A-Z_0-9]{5}$/;
						var chare7 = /^[A-Z]{2}[\u4e00-\u9fa5]{1}[A-Z_0-9]{4}$/;
						var chare8 = /^[A-Z]{2}[0-9]{8}$/;
						var chare9 = /^[A-Z]{2}[\u4e00-\u9fa5]{1}[A-Z_0-9]{5}$/;
						var chare10 = /^[A-Z]{2}[\u4e00-\u9fa5]{1}[0-9]{5}$/;
						var chare11 = /^[A-Z]{2}[0-9]{5}$/;
						var chare12 = /^[A-Z]{2}[0-9]{7}$/;
						var chare13 = /^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{3}$/;
						var arr = [ chare1, chare2, chare3, chare4, chare5,
								chare6, chare7, chare8, chare9, chare10,
								chare11, chare12, chare13 ];
						for (var k = 0; k < arr.length; k++) {
							if (plateNumber.match(arr[k])) {
								return true;
							}
						}
						return false;
			};
        return {
            require:"ngModel",
            link:function(scope,ele,attrs,ctrl) {
            	
            	var customValidator=function(viewValue) {
            		var validity=ctrl.$isEmpty(viewValue)
					|| plateVal(viewValue);
                    if(validity) {
                        ctrl.$setValidity("myPlateVal", true);
                        return viewValue;
                    }else {
                        ctrl.$setValidity("myPlateVal", false);
                        return undefined;
                    }
                };
                ctrl.$formatters.push(customValidator);
                ctrl.$parsers.unshift(customValidator);
            }
        };
 }]);
 /**
  * 正数校验
  * 
  */
 commonApp.directive("myNumberVal", [function () {
		var FLOAT_REGEXP = /^\d+(?=\.{0,1}\d+$|$)/;
        return {
            require:"ngModel",
            link:function(scope,ele,attrs,ctrl) {
            	
            	var customValidator=function(viewValue) {
            		var validity=ctrl.$isEmpty(viewValue)
					|| FLOAT_REGEXP.test(viewValue);
                    if(validity) {
                        ctrl.$setValidity("myNumberVal", true);
                        
                        return viewValue;
                    }else {
                        ctrl.$setValidity("myNumberVal", false);
                        return undefined;
                    }
                };
                ctrl.$formatters.push(customValidator);
                ctrl.$parsers.unshift(customValidator);
            }
        };
 }]);
 
 
/**
 * 密码校验
 * 校验输入的两个密码是否一致
 * 
 * 在需要校验的输入框加入该标签，属性值为另外一个被校验的值的 id
 * 
 */ 
commonApp.directive('myPwdVal', [function () {
	 var pwdObj={
		        require: 'ngModel',
		        compile: function (elem, attrs, ctrl) {
		            var firstPassword = '#' + attrs.myPwdVal;
//		            var first=$(firstPassword);
		            return function(scope,elem, attrs, ctrl){
		            	elem.on('keyup', function () {
			                scope.$apply(function () {
			                    var v = elem.val()===$(firstPassword).val();
			                    ctrl.$setValidity('myPwdVal', v);
			                });
			            });
		            };
		        }
		    };
	    return pwdObj;
	}]);

/**
 * 图片加载失败，加载默认的图片
 * 
 * errSrc 
 *     1 表示车辆的默认图片 小的
 *     2 表示用户的默认图片 小的
 *     3 表示用户的默认图片 大的
 * 
 */
commonApp.directive("errSrc", [function() {
	var rt={
			  getImg:function(type){
				  if(type==1){
					  return getRootPath()+"/image/default/default_min.jpg";
				  }
				  if(type==2){
					  return getRootPath()+"/image/default/user_min.jpg";
				  }
				  if(type==3){
					  return getRootPath()+"/image/default/user_big.jpg";
				  }
				  
				  return "";
			  },
		      link: function(scope, element, attrs) {
		      if(attrs.ngSrc=="" || attrs.ngSrc==undefined){
		    	  	 var img=rt.getImg(attrs.errSrc);
		    		 attrs.$set("src", img);
		    	 }
		      element.bind("error", function() {
		        if (attrs.src != attrs.errSrc) {
		        	var img=rt.getImg(attrs.errSrc);
		          attrs.$set("src", img);
		        }
		      });
		    }
		  };
	  return rt
	}]);




/**
 * @TODO wangbq
 * 
 */
commonApp.directive('plate',[function(){
//commonApp.directive('plate',['commonService','$rootScope',function(commonService,parse,$sce,$compile,$rootScope) {
	var myPlate={};
	myPlate.restrict="AE";
	myPlate.replace=true;
	myPlate.truefalse=false;
	myPlate.compile=function(element, attrs){
		var placeholder = attrs.placeholder;
		
		if(placeholder == undefined || placeholder == null){
			placeholder = "";
		}
		var plateWin={
			isShow:false,//整个弹窗是否显示
			inputValue:"",//输入框的数据
			typeface:"",
			letter:"",
			initDate:function(inputValue){
				this.inputValue=inputValue;
			},
			inputClick: function (){
				var readonly = attrs.readonly;
				if(readonly!=null && readonly!=undefined && readonly!=""){	
					this.isShow=false;
				}else{
					this.isShow=true;
				}
				
			},
			confirmT:function(obj){
				this.typeface=obj;
				this.confirm("");
			},
			confirmL:function(obj){
				this.letter=obj;
				this.confirm("");
			},
			clear:function(){
				this.inputValue="";
				this.typeface="";
				this.letter="";
				this.focus();
			},
			close:function(){
				this.isShow=false;
			},
			mouseleave : function(){
				this.focus();
				this.mouseenterDiv = false;
			},
			blur : function(){
			
				if(!this.mouseenterDiv){
					this.close();
				}
			},
			mouseenter : function(){
				this.mouseenterDiv = true;
			}
		};
		//每个标签的对象不一样
		var objName=attrs.plate;
		var readonly = attrs.readonly;
		if(readonly!=null && readonly!=undefined && readonly!=""){	
			readonly="true";
		}else{
			readonly="false";
		}
		
		var objClassName=attrs.className;
		if(objClassName==null || objClassName==undefined || objClassName==""){
			objClassName = "controls_text";
		}
		
		
		var html='<input ng-blur="' + objName + '.blur()" placeholder= "'+placeholder+'"  type="text" my-focus="'+objName+'focus" ng-click="'+objName+'.inputClick()" ng-readonly="{{'+readonly+'}}" ng-model="'+objName+'.inputValue" class="'+objClassName+'" style="width:'+attrs.width+'px;height:'+attrs.height+'px" maxlength="10"></input>';
		html+='<div ng-mouseenter="'+ objName + '.mouseenter()" ng-mouseleave="'+ objName + '.mouseleave()" ng-show="'+objName+'.isShow" style="position: absolute;z-index: 999999;" class="chooseCard ng-hide">';
		html+=' <div class="left_choose">';
		html+='  <ul style="list-style-type: none;">';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'京\')">京</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'津\')">津</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'沪\')">沪</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'渝\')">渝</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'冀\')">冀</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'晋\')">晋</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'辽\')">辽</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'吉\')">吉</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'黑\')">黑</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'苏\')">苏</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'浙\')">浙</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'皖\')">皖</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'闽\')">闽</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'赣\')">赣</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'鲁\')">鲁</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'豫\')">豫</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'鄂\')">鄂</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'湘\')">湘</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'粤\')">粤</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'琼\')">琼</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'川\')">川</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'黔\')">黔</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'滇\')">滇</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'贵\')">贵</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'云\')">云</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'陕\')">陕</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'秦\')">秦</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'甘\')">甘</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'陇\')">陇</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'青\')">青</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'藏\')">藏</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'桂\')">桂</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'蒙\')">蒙</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'宁\')">宁</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'新\')">新</li>';
		html+='  	<li class="ChineseCls" ng-click="'+objName+'.confirmT(\'蜀\')">蜀</li>';
		html+='  	</ul></div><div class="right_choose">';
		html+='  	<ul style="list-style-type: none;" id="english">';
		html+='  	<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'A\')">A</li>';
		html+='  	<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'B\')">B</li>';
		html+='  	<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'C\')">C</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'D\')">D</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'E\')">E</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'F\')">F</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'G\')">G</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'H\')">H</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'I\')">I</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'J\')">J</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'K\')">K</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'L\')">L</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'M\')">M</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'N\')">N</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'O\')">O</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'P\')">P</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'Q\')">Q</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'R\')">R</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'S\')">S</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'T\')">T</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'U\')">U</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'V\')">V</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'W\')">W</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'X\')">X</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'Y\')">Y</li>';
		html+='		<li class="EnglishCls" ng-click="'+objName+'.confirmL(\'Z\')">Z</li>';
		html+='		<li style="width:40px!important" class="ClearCls" ng-click="'+objName+'.clear()">清空</li>';
		html+='		<li style="width:40px!important;height:20px" class="closeT2" ng-click="'+objName+'.close()">关闭</li>';
		html+='	</ul>';
		html+='</div>';
		html+='</div>';
		element.html(html);
		return function($scope, element, attrs){
			plateWin.focus=function(){
				eval("$scope."+objName+"focus.focus(true)");
			};
			plateWin.confirm=function(obj){
				this.inputValue=this.typeface+this.letter+obj;
				if(this.typeface!=null && this.typeface!=undefined && this.typeface!=""
					&& this.letter!=null && this.letter!=undefined && this.letter!=""){
					this.isShow=false;
					eval("$scope."+objName+"focus.focus(true)");
				}
			},
			eval("$scope."+objName+"=plateWin");
		};
	};
	return myPlate;
}]);

/**
 * 获取焦点的属性
 * 
 * 属性名称做为对象
 * 
 * 对外提供一个方法：focus方法，调用该方法时，该标签获得焦点
 * 
 * 
 */
commonApp.directive('myFocus',[function(){
	var myFouce={};
	myFouce.restrict="A";
	myFouce.replace=true;
	myFouce.priority=21001;
	myFouce.compile=function(element, attrs){
		var objName=attrs.myFocus;
		return function($scope, element, attrs){
			var fouceWin={
					focus:function(val){
						if(val==true){
							element[0].focus();
						}
					}
			};
			eval("$scope."+objName+"=fouceWin");
		};
	};
	return myFouce;
	
}]);




 /*******************************form 表单 字段验证属性 end************************************************************/
 
 /*******************************service start************************************************************/
commonApp.service( 'commonService', ['$http','$rootScope','$sce', '$interval',function( $http,$rootScope,$sce,$interval ) {
	 
	var urlEncode=function (param, key, encode) {
		 if(param==null) return '';
		 var paramStr = '';
		 var t = typeof (param);
		 if (t == 'string' || t == 'number' || t == 'boolean' ||  param instanceof Array) {
			 if (param !== "") {
				 paramStr = key + '=' + ((encode==null||encode) ? encodeURIComponent(param) : param);
			 }
		 } else {
			 var idx = 0;
			 var paramArray = new Array();
			 if (param.url != undefined) {
				 if ((idx = param.url.indexOf("&")) > 0) {
					 paramStr = param.url.substring(0, idx);
					 var params = param.url.substring(idx+1).split("&");
					 for (var i in params) {
						 if (params[i].split("=")[1] !== "null" && params[i].split("=")[1] !== "") {
							 paramArray.push(params[i]);
						 }
					 }
				 } else {
					 paramStr = param.url;
				 }
			 }
			 if (param.data != undefined) {
				 for (var i in param.data) {
					 if (param.data[i] != null && param.data[i] !== "null" && param.data[i] !== "") {
						 paramArray.push(urlEncode(param.data[i], i, encode));
					 }
				 }
			 }
			 if (paramArray.length > 0)
				 paramStr += "&"+ paramArray.sort().join("&");
		 }
		 return paramStr;
	 };
	 
	var idEncode=function(param) {
		if(param==null) return '';
		var base64 = new Base64();
		return base64.encode(param);
	}
	/**
	 * 用于解析的内容是对象
	 */
	var objToPostObj=function(obj,key,retmap){
		var j=0;
		for(var i in obj){
			if(obj[i] instanceof Array==true){
				objToPostList(obj[i],key+"["+j+"]",retmap);
			}else if(obj[i] instanceof Object==true){
				objToPostObj(obj[i],key+"."+i,retmap);
			}else{
				retmap[key+"."+i]=obj[i];
			}
			j++;
		}
	}
	/**
	 * 用于解析的内容是列表
	 * 
	 * 
	 */
	var objToPostList=function(obj,key,retmap){
		var j=0;
		for(var i in obj){
			if(obj[i] instanceof Array==true){
				objToPostList(obj[i],key+"["+j+"]",retmap);
			}else if(obj[i] instanceof Object==true){
				objToPostObj(obj[i],key+"["+j+"]",retmap);
			}
			j++;
		}
		retmap[key]=obj.toString();
	}
	/**
	 * 对于传入的对象转换成可以post的形式
	 * 
	 * 
	 */
	var objToPostParam=function(obj){
		var map={};
		for(var i in obj){
			if(obj[i] instanceof Array==true){
				objToPostList(obj[i],i,map);
			}else if(obj[i] instanceof Object==true){
				objToPostObj(obj[i],i,map);
			}else{
				map[i]=obj[i];
			}
		}
		return map;
	}
	
	var tranParam = function (obj) {  
        var query = "", name, value, fullSubName, subName, subValue, innerObj, i;  
        for (name in obj) {  
            value = obj[name];  
            if (value instanceof Array) {  
                for (i = 0; i < value.length; ++i) {  
                    subValue = value[i];  
                    fullSubName = name + "[" + i + "]";  
                    innerObj = {};  
                    innerObj[fullSubName] = subValue;  
                    query += param(innerObj) + "&";  
                }  
            } else if (value instanceof Object) {  
                for (subName in value) {  
                    subValue = value[subName];  
                    fullSubName = name + "[" + subName + "]";  
                    innerObj = {};  
                    innerObj[fullSubName] = subValue;  
                    query += param(innerObj) + "&";  
                }  
            } else if (value !== undefined && value !== null) {  
                query += encodeURIComponent(name) + "=" + encodeURIComponent(value) + "&";  
            }  
        }  
        return query.length ? query.substr(0, query.length - 1) : query;  
    }; 
	
	var postUrl=function postUrl(url,param,successFun,errorFun,type){
		try {
			if(window.top.tokenId!=undefined && window.top.tokenId!=getCookie("token")){
				var text="你当前登陆的网点["+getCookie("orgName")+"],用户名["+getCookie("userName")+"]";
				window.top.alert(text,function(){
					window.top.location.reload();
				});
				return ;
			}
		} catch (e) {
			// TODO: handle exception
		}
		if(type==undefined || type=="" || type==null){
			 type="POST";
		}
		if(url==null || url==undefined || url==""){
			alert("提交URL为空！");
			return ;
		}
		if(param==null || param==undefined ){
			alert("提交参数为空！");
			return ;
		}
		var queryObject;
		if(typeof param == "string"){
			if (param !== "") {
				if(url.indexOf("?")==-1){
					url=url+"?"+param;
				}else{
					url=url+"&"+param;
				}
			}
			queryObject = {
				method : type,
			    url : url
			};
		}else {
			queryObject = {
				headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
				transformRequest: tranParam, 
				method : type,
			    data : objToPostParam(param),
			    url : url
			};
		}
		if (queryObject.data != undefined && queryObject.data.sign != undefined) {
			delete queryObject.data.sign;
		}
		var urlStr=urlEncode(queryObject, null, false);
		var sign=md5(urlStr+getCookie("token"));
		if (queryObject.data != undefined) {
			queryObject.data.sign=sign;
		} else {
			queryObject.url+="&sign="+sign;
		}
		if (type == "POST") {
			//限制重复请求
			if(urlManager[urlStr]==undefined){
				 urlManager[urlStr]="1";
				 httpPost(queryObject, successFun, errorFun, urlStr);
			}else{
				try {
					 window.top.hideLoad();
				} catch (e) {
					// TODO: handle exception
				}
				
			}
		} else {
			httpPost(queryObject, successFun, errorFun, urlStr);
		}
	};
	
	getCookie = function(name) {
		var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
		if(arr=document.cookie.match(reg))
			return decodeURIComponent(arr[2]);
		else
			return "";
	}
	
	//发送请求
	var httpPost = function(queryObject, successFun, errorFun, urlStr) {
		//调用发送请求
		 $http(queryObject).success(function(response, status, headers, config) {
			 	setTimeout(function(){
			 		delete urlManager[urlStr];
				},2000);
		 		 successFun(response, status, headers, config);
		 	}).error(function(response, status, headers, config) {
		 		setTimeout(function(){
			 		delete urlManager[urlStr];
				},2000);
				
				if (status == 403) {
					window.top.location.href = "/index.html";
				}else if(status==500){
					window.top.hideLoad();
					window.top.alert("系统搬砖去啦，请稍等片刻~~~  ");
				}else if(status==501){
					if(typeof errorFun == "function") {
						errorFun(response); 
					} else {
						window.top.alert(response.message);
					}
				}else if(typeof errorFun == "function") {
					errorFun(response); 
				}else if(status==0){
					
				}else if(errorFun== undefined || errorFun=="" || errorFun==null){
					window.top.alert(response.message);
				}
		 });
	}
	
	var tksign = function(queryObject, url) {
		var sign="xxxx";
		if (queryObject.params != undefined) {
			queryObject.params.sign=sign;
		} else {
			queryObject.url+="&sign="+sign;
		}
	}
		
	var getRootPath=function(){
		//获取当前网址
	    var curWwwPath=window.document.location.href;
	    //获取主机地址之后的目录
	    var pathName=window.document.location.pathname;
	    if (pathName == "" || pathName == "/") {
	    	if(curWwwPath.substr(curWwwPath.length-1,curWwwPath.length)=="/"){
	    		return curWwwPath.substr(0,curWwwPath.length-1);
	    	}
	    	return curWwwPath;
	    }
	    var pos=curWwwPath.indexOf(pathName);
	    //获取主机地址，
	    var localhostPath=curWwwPath.substring(0,pos);
	 /*   if(localhostPath.indexOf("localhost")!=-1 || localhostPath.indexOf("127.0.0.1")!=-1){
	    	//获取带"/"的项目名
	        var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	        
	        return(localhostPath+projectName);
	    }else{*/
	    	return localhostPath;
	   // }
	    
	};
	var getQueryString= function(name) {
		   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		   var url=decodeURI(window.location.search.substr(1));
		   var r = url.match(reg);
		   if (r != null) return unescape(r[2]); return null;
	   };
	
	 var alertMsg={
			 text:"",
			 isShow:false,
			 callBack:"",
			 alert:function(text,callBack){
				 alertMsg.isShow=true;
				 alertMsg.text=$sce.trustAsHtml(text);
				 if(callBack!=undefined && callBack!="" && callBack!=null){
					 alertMsg.callBack=callBack; 
				 }else{
					 alertMsg.callBack=null;
				 }
			 },
			 sure:function(){
				 alertMsg.isShow=false;
				 alertMsg.text="";
				 if(alertMsg.callBack!=undefined && alertMsg.callBack!="" && alertMsg.callBack!=null){
					 alertMsg.callBack();
				 }
			 },
			 close:function(){
				 alertMsg.isShow=false;
				 alertMsg.text="";
			 }
	 };
	 
	 $rootScope.alertMsg=alertMsg;
	
	 var confirmMsg={
			 text:"",
			 isShow:false,
			 callBack:"",
			 close:function(){
				 confirmMsg.isShow=false;
				 confirmMsg.text="";
			 },
			 cancel:function(){
				 if(confirmMsg.cancelCallBack!=undefined && confirmMsg.cancelCallBack!="" &&
					 confirmMsg.cancelCallBack!=null){
					 confirmMsg.cancelCallBack();
				 }
				confirmMsg.isShow=false;
				confirmMsg.text="";
			 },
			 confirm:function(text,sureCallBack,cancelCallBack,sureText,cancelText){
				 confirmMsg.text=text;
				 confirmMsg.isShow=true;
				 confirmMsg.callBack=sureCallBack;
				 if(cancelCallBack!=undefined){
					 confirmMsg.cancelCallBack=cancelCallBack;
				 }else{
					 confirmMsg.cancelCallBack=null;
				 }
				 if(sureText!=undefined&&sureText!=""){
					 confirmMsg.sureText=sureText;
				 }else{
					 confirmMsg.sureText="确认";
				 }
				 if(cancelText!=undefined&&cancelText!=""){
					 confirmMsg.cancelText=cancelText;
				 }else{
					 confirmMsg.cancelText="取消";
				 }
			 },
			 sure:function(){
				 confirmMsg.isShow=false;
				 confirmMsg.text="";
				 confirmMsg.callBack();
			 }
			 
	 };
	 $rootScope.confirmMsg=confirmMsg;
	 
	 var time={
			 getNowDate:function(){
					var myDate = new Date();
				    var fullYear=myDate.getFullYear();
				    var month=myDate.getMonth()+1;
				    var months = myDate.getMonth();
				    var date=myDate.getDate();
				    if(month<10){
				  	  month="0"+month;
				    }
				    if(date<10){
				  	  date="0"+date;
				    }
				   var starTime = fullYear+"-"+month+"-"+date;
				   return starTime;
				 }
	 };
	 //打开tab页面的方法
	 var openTab=function(id,name,url){
		 try {
			 window.top.openTab(id,name,url);
		 } catch (e) {
			 window.open(url, name);
		 }
	 };
	//关闭一个页面并回到父页面
	 var closeToParentTab=function(isReload){
		 window.top.closeToParentTab(isReload);
	 };
	//关闭一个页面
	 var closeTab=function(){
		 window.top.close();
	 }
	
	var topAlert="";
	try{
		topAlert=window.top.alert;
	}catch(e){
		
	}
	
	var topConfirm="";
	try{
		topConfirm=window.top.confirm;
	}catch(e){
		
	}
	
	
	// 为一组dom元素注册键盘事件
	var registerKeyEventForDoms = function(domIdArray, keyEvent, key, callback){
		// 为整个页面绑定“return”按键事件
		for (var i = 0; undefined != domIdArray && i < domIdArray.length; i++) {
			jQuery("#"+domIdArray[i]).bind(keyEvent, key, function(evt){
				if (undefined != callback)
					callback(evt);
				return false;
			});
		}
	}
	
	
	//导出功能
	/**
	 * queryUrl  格式如：commonExportBO.ajax?cmd=downloadExcelFile
	 * params   请求的参数对象:{"date":"2016-07-12"}
	 * excelLables  excel的列名: 批次号，时间
	 * excelKeys    excel的字段名称:batchNum,date 
	 */
	var downloadExcelFile=function(queryUrl,params,excelLables,excelKeys,filename){
		
		var queryBo=queryUrl.substr(0,queryUrl.indexOf(".ajax"));
		var queryCmd=queryUrl.substr(queryUrl.indexOf("cmd=")+4,queryUrl.length);
		
		var url = queryBo+".ajax?cmd=downloadExcelFile";
		
		var queryParamStr="";
		for(var key in params){
			if(params[key]!=undefined){
				queryParamStr=queryParamStr+"&"+key+"="+params[key];
			}
		}
		var toUrl = signUrl(url+"&queryUrl="+queryBo+"|"+queryCmd+"&excelKeys="+excelKeys+"&excelLables="+excelLables+queryParamStr+"&_ALLEXPORT=1&fileName="+filename);
		var iframe = document.createElement("iframe");
	    iframe.id = "frameDownloading";
	    iframe.src = encodeURI(toUrl);
	    iframe.style.display = "none";
	    document.body.appendChild(iframe);
	    
	    
	   
	    var that=this;
	    var checkIframe = document.createElement("iframe");
	    checkIframe.id = "checkIframe";
	    checkIframe.style.display = "none";
	    document.body.appendChild(checkIframe);
	    window.top.showLoad();
	    that.inter=$interval(function() {
	    	var checkUrl=queryBo+".ajax?cmd=checkFinishDownLoad&time="+(new Date()).getTime();
	    	that.postUrl(checkUrl,"",function(data){
				if(data.result=="true"){
					$interval.cancel(that.inter);
		        	var frameDownloadExcel = document.createElement("iframe");
		        	frameDownloadExcel.id = "frameDownloadExcel";
		        	frameDownloadExcel.src = encodeURI(signUrl(queryBo+".ajax?cmd=downloadExcelFileFromSession"));
		        	frameDownloadExcel.style.display = "none";
        		    document.body.appendChild(frameDownloadExcel);
        		    window.top.hideLoad();
				}
				
        	});
	    }, 5000);
	    
	}
	
	
   var service = {
  	    "postUrl":postUrl,//发送ajax请求
  	    "getRootPath":getRootPath,//获取网站的根路径
  	    "getQueryString":getQueryString,//获取地址里面某个属性的值
  	    "alert":topAlert,
  	    "confirm":topConfirm,
  	    "getNowDate":time.getNowDate,// 获取当前时间，格式  yyyy-mm-dd
  	    "idEncode":idEncode,
  	    "openTab":openTab,//打开一个tab页面
  	    "closeToParentTab":closeToParentTab,//关闭一个页面后，回到父页面
  	    "closeTab":closeTab,//关闭一个页面
  	    "downloadExcelFile":downloadExcelFile, //导出方法
  	    "registerKeyEventForDoms": registerKeyEventForDoms
   };
   
   return service;
}]);


commonApp.directive('myCity',['commonService',function(commonService){
	/**
	 * 地市选择框
	 * 使用属性传入的值做为该标签的对象名称
	 * 
	 * 对外提供：
	 * 			initDate 初始化方法，入参分别为：省id,市id,县id
	 *          clear    清除数据方法
	 * 	return 返回六个属性值，表示选中的数据：	
	 * 				chooseCityId 省id
					chooseCityName:省名称,
					chooseRegionName:市名称,
					chooseRegionId:市id,
					chooseCountyId:县id,
					chooseCountyName:县名称
	 * 			
	 */
	var myCity={};
	myCity.restrict="AE";
	myCity.replace=true;
	myCity.priority=21001;
	myCity.compile=function(element, attrs){
		//每个标签的对象不一样
		var objName=attrs.myCity;
		var isParent=attrs.isParent;
		var exec=attrs.exec;
		var placeholder=attrs.placeholder;
		var callBack=attrs.callBack;
		if(placeholder==undefined){
			placeholder="";
		}
		var classValue=attrs.classvalue;
		if(classValue==undefined){
			classValue="form_term w_b60";
		}
		var cityWin={
			isShow:false,//整个弹窗是否显示
			inputValue:"",//输入框的数据
			cityData:{},//省份数据
			regionData:{},//地市数据
			countyData:{},//县区数据
			streetData:{},// 镇乡街道数据
			countyQueryData:{},//县区查询数据
			tabShowIndex:"1",//显示tab页的值，1，2，3
			chooseCityId:"",
			chooseCityName:"",
			chooseRegionName:"",
			chooseRegionId:"",
			chooseCountyId:"",
			chooseCountyName:"",
			chooseStreetId:"",
			chooseStreetName:"",
			inputClick:function(){
				this.isShow=true;
				this.cityCreat();
			},
			changeTab:function(index){
				this.tabShowIndex=index;
			},
			//点击省份
			cityClick:function(cityId,cityName){
				this.chooseCityId=cityId;
				this.chooseCityName=cityName;
				this.regionCreat(cityId);
				this.changeTab("2");
				this.chooseRegionId="";
				this.chooseRegionName="";
				this.chooseCountyId="";
				this.chooseCountyName="";
				this.chooseStreetId="";
				this.chooseStreetName="";
				this.regionData={};//地市数据
				this.countyData={};//县区数据
				this.streetData={};// 镇乡街道数据
				this.countyQueryData={};
			},
			//点击地市
			regionClick:function(regionId,regionName){
				this.chooseRegionId=regionId;
				this.chooseRegionName=regionName;
				this.countyCreat(regionId);
				this.changeTab("3");
				this.chooseCountyId="";
				this.chooseCountyName="";
				this.chooseStreetId="";
				this.chooseStreetName="";
				this.countyData={};//县区数据
				this.streetData={};// 镇乡街道数据
			},
			//点击县市
			countyClick:function(countyId,countyName){
				this.chooseCountyId=countyId;
				this.chooseCountyName=countyName;
				this.streetCreat(countyId);
				this.changeTab("5");
				this.chooseStreetId="";
				this.chooseStreetName="";
				this.streetData={};// 镇乡街道数据
			},
			streetClick: function(streeId, streeName){
				this.chooseStreetId=streeId;
				this.chooseStreetName=streeName;
				this.setInputValue(this.chooseCityName,this.chooseRegionName,this.chooseCountyName,this.chooseStreetName);
				this.isShow=false;
				this.executive(this.chooseCityId,this.chooseCityName,this.chooseRegionId,this.chooseRegionName,this.chooseCountyId,this.chooseCountyName,this.chooseStreetId,this.chooseStreetName);
				this.callBack();
			},
			//点击县市
			countyQueryClick:function(countyId,countyName){
				var that = this;
				var url = "staticDataBO.ajax?cmd=doAreaByCountyId&countyId="+countyId;
				commonService.postUrl(url,"",function(data){
					that.chooseCityId=data.proviceId;
					that.chooseCityName=data.proviceName;
					that.chooseRegionId=data.cityId;
					that.chooseRegionName=data.cityName;
					that.chooseCountyId=countyId;
					that.chooseCountyName=countyName;
					that.setInputValue(data.proviceName,data.cityName,countyName);
					that.isShow=false;
					that.executive(data.proviceId,data.proviceName,data.cityId,data.cityName,countyId,countyName);
				});
			},
			//点击县市查询
			countyQuery:function(countyName){
				this.countyCreatByName(countyName);
				this.changeTab("4");
				this.chooseCountyId="";
				this.chooseCountyName="";
			},
			//创建省数据
			cityCreat: function (){
				var that=this;
				var url = "staticDataBO.ajax?cmd=selectProvince";
				commonService.postUrl(url,"",function(data){
					that.cityData=data.items;
            	});
				
			},
			//创建地市数据
			regionCreat: function (cityId){
				var that=this;
				var url = "staticDataBO.ajax?cmd=selectCity&provinceId="+cityId;
				commonService.postUrl(url,"",function(data){
					that.regionData=data.items;
            	});
			},
			countyCreat : function (regionId){
				var that=this;
				var url="staticDataBO.ajax?cmd=selectDistrict&cityId="+regionId;
				commonService.postUrl(url,"",function(data){
					that.countyData=data.items;
            	});
			},
			streetCreat : function (countyId){
				var that=this;
				var url="staticDataBO.ajax?cmd=selectStreet&districtId="+countyId;
				commonService.postUrl(url,"",function(data){
					that.streetData=data.items;
            	});
			},
			countyCreatByName : function (name){
				var that=this;
				var url="selectCity.ajax?cmd=doQueryCountyByName&countyName="+name;
				commonService.postUrl(url,"",function(data){
					that.countyQueryData=data.items;
				});
				
			},
			initDate:function(cityId,regionId,countyId,streetId){
				var that=this;
				if(cityId!=null && cityId!=undefined && cityId!=""){
					var urlProvi = "staticDataBO.ajax?cmd=selectProvince";
					commonService.postUrl(urlProvi,"",function(data){

						that.cityData=data.items;
						for(var i=0;i<data.items.length;i++){
							var objProvi=data.items[i];
							if(objProvi.id==cityId){
								that.chooseCityId=objProvi.id;
								that.chooseCityName=objProvi.name;
								that.chooseRegionName="";
								that.chooseRegionId="";
								that.chooseCountyId="";
								that.chooseCountyName="";
								that.setInputValue(objProvi.name, "", "");
								break;
							}
						}
						that.changeTab("1");
						if(regionId!=null && regionId!=undefined && regionId!=""){
							var urlCity = "staticDataBO.ajax?cmd=selectCity&provinceId="+that.chooseCityId;

							commonService.postUrl(urlCity,"",function(data){
								that.regionData=data.items;
								for(var i=0;i<data.items.length;i++){
									var objRegion=data.items[i];
									if(objRegion.id==regionId){
										that.chooseRegionName=objRegion.name;
										that.chooseRegionId=objRegion.id;
										that.chooseCountyId="";
										that.chooseCountyName="";
										that.setInputValue(that.chooseCityName, objRegion.name, "");
										break;
									}
								}
								that.changeTab("2");
								
								if(countyId!=null && countyId!=undefined && countyId!=""){
									commonService.postUrl("staticDataBO.ajax?cmd=selectDistrict&cityId="+that.chooseRegionId,"",function(data){
										that.countyData=data.items;
										for(var i=0;i<data.items.length;i++){
											var objCounty=data.items[i];
											if(objCounty.id==countyId){
												that.chooseCountyId=objCounty.id;
												that.chooseCountyName=objCounty.name;
												that.setInputValue(that.chooseCityName,that.chooseRegionName,objCounty.name);
												break;
											}
										}
										that.changeTab("3");
										
										if (streetId != undefined && streetId != null && streetId != '') {
											commonService.postUrl("staticDataBO.ajax?cmd=selectStreet&districtId="+that.countyId,"",function(data){
												that.streetData=data.items;
												for(var i=0;i<data.items.length;i++){
													var objStreet=data.items[i];
													if(objStreet.id==streetId){
														that.chooseStreetId=objCounty.id;
														that.chooseStreetName=objCounty.name;
														that.setInputValue(that.chooseCityName, that.chooseRegionName, that.chooseCountyName, objCounty.name);
														break;
													}
												}
												that.changeTab("5");
											});
										} else {
											that.streetData={};		
										}
					            	});
								}else{
									that.countyData={};
								}
			            	});
							
						}else{
							that.regionData={};
						}
	            	});
				}
			},
			//设置输入框的值
			setInputValue:function(cityName,regionName,countyName,streetName){
				if(cityName!=null && cityName!=undefined && cityName!="" ){
					this.inputValue = cityName;
				}
				if(regionName!=null && regionName!=undefined && regionName!=""){
					this.inputValue = this.inputValue+"·"+regionName;
				}
				if(countyName!=null && countyName!=undefined && countyName!=""){
					this.inputValue = this.inputValue+"·"+countyName;
				}
				if(streetName!=null && streetName!=undefined && streetName!=""){
					this.inputValue = this.inputValue+"·"+streetName;
				}
			},
			close:function(){
				this.isShow=false;
			},
			clear:function(){
				this.inputValue="";
				this.tabShowIndex="1";
				this.regionData={};
				this.countyData={};
				this.countyQueryData={};
				this.chooseCityId="";
				this.chooseCityName="";
				this.chooseRegionName="";
				this.chooseRegionId="";
				this.chooseCountyId="";
				this.chooseCountyName="";
				this.chooseStreetId=""
				this.chooseStreetName="";
				this.focus();
				this.executive(this.chooseCityId,this.chooseCityName,this.chooseRegionId,this.chooseRegionName,this.chooseCountyId,this.chooseCountyName);
				this.isShow=false;
			},
			confirm:function(){
				this.isShow=false;
				this.setInputValue(this.chooseCityName,this.chooseRegionName,this.chooseCountyName);
				this.executive(this.chooseCityId,this.chooseCityName,this.chooseRegionId,this.chooseRegionName,this.chooseCountyId,this.chooseCountyName);
				this.callBack();
			},
			mouseleave : function(){
				this.focus();
				this.mouseenterDiv = false;
			},
			blur : function(){
				if(!this.mouseenterDiv){
					this.close();
				}
			},
			mouseenter : function(){
				this.mouseenterDiv = true;
			}
		};
		
		var html=
			'<input id="'+attrs.inputId+'" class="'+classValue+'" placeholder="'+placeholder+'" type="text" ng-blur="' + objName + '.blur()" ng-focus="'+objName+'.inputClick()" value="{{'+objName+'.inputValue}}" readonly="readonly" style="width:'+attrs.width+'px;height:'+attrs.height+'px"></input>'
			+'<div  ng-mouseenter="'+ objName + '.mouseenter()" ng-mouseleave="'+ objName + '.mouseleave()" ng-show="'+objName+'.isShow" style="position: absolute;z-index: 999999;">'
			+'<div class="content1">'
				+'<div class="m JD-stock">'
							+'<span>'
							+'<div class="dx_a" style="margin-top:5px;">您的筛选条件：<span>{{'+objName+'.chooseCityName}}<em></em></span><span>{{'+objName+'.chooseRegionName}}<em></em></span><span>{{'+objName+'.chooseCountyName}}<em></em></span><span>{{'+objName+'.chooseStreetName}}<em></em></span></div>'
							+'</span>'
						+'<div class="mt">'
							+'<ul class="tab">'
							+'<li data-index="0" ng-click="'+objName+'.changeTab(\'1\')"  ng-class="{curr:'+objName+'.tabShowIndex==\'1\'}" ><a href="javascript:void(0);" ng-class="{hover:'+objName+'.tabShowIndex==\'1\'}" ><em>省份</em><i></i></a></li>'
							+'<li data-index="1" ng-click="'+objName+'.changeTab(\'2\')" ng-class="{curr:'+objName+'.tabShowIndex==\'2\'}" ><a href="javascript:void(0);" ng-class="{hover:'+objName+'.tabShowIndex==\'2\'}" ><em>地市</em><i></i></a></li>'
							+'<li data-index="2" ng-click="'+objName+'.changeTab(\'3\')" ng-class="{curr:'+objName+'.tabShowIndex==\'3\'}" ><a href="javascript:void(0);" ng-class="{hover:'+objName+'.tabShowIndex==\'3\'}" ><em>县区</em><i></i></a></li>'
							+'<li data-index="4" ng-click="'+objName+'.changeTab(\'5\')" ng-class="{curr:'+objName+'.tabShowIndex==\'5\'}" ><a href="javascript:void(0);" ng-class="{hover:'+objName+'.tabShowIndex==\'5\'}" ><em>镇/乡/街道</em><i></i></a></li>'
							+'<li style="display:none;" data-index="3" ng-click="'+objName+'.changeTab(\'4\')" ng-class="{curr:'+objName+'.tabShowIndex==\'4\'}" ><a href="javascript:void(0);" ng-class="{hover:'+objName+'.tabShowIndex==\'4\'}" ><em>筛选结果</em><i></i></a></li>'
							+'</ul>'
						+'<div class="stock-line"></div>'
					+'</div>'
					+'<div class="mc" ng-show="'+objName+'.tabShowIndex==\'1\'">'
						+'<ul  class="area-list" >'
							+'<li ng-click="'+objName+'.cityClick(city.id,city.name)" ng-repeat="city in '+objName+'.cityData"><a ng-class="{bh:'+objName+'.chooseCityId==city.id}" href="javascript:void(0);">{{city.name}}</a></li>'
						+'</ul>'
					+'</div>'
			 		+'<div class="mc" ng-show="'+objName+'.tabShowIndex==\'2\'">'
				 		+'<ul  class="area-list" >'
				 		+'<li ng-click="'+objName+'.regionClick(region.id,region.name)" ng-repeat="region in '+objName+'.regionData"><a ng-class="{bh:'+objName+'.chooseRegionId==region.id}" href="javascript:void(0);">{{region.name}}</a></li>'
						+'</ul>'
					+'</div>'
			 		+'<div class="mc" ng-show="'+objName+'.tabShowIndex==\'3\'">'
				 		+'<ul  class="area-list" >'
				 			+'<li ng-click="'+objName+'.countyClick(county.id,county.name)" ng-repeat="county in '+objName+'.countyData"><a ng-class="{bh:'+objName+'.chooseCountyId==county.id}" href="javascript:void(0);">{{county.name}}</a></li>'
						+'</ul>'
					+'</div>'
					+'<div class="mc" ng-show="'+objName+'.tabShowIndex==\'5\'">'
				 		+'<ul  class="area-list" >'
				 			+'<li ng-click="'+objName+'.streetClick(street.id,street.name)" ng-repeat="street in '+objName+'.streetData"><a ng-class="{bh:'+objName+'.chooseStreetId==street.id}" href="javascript:void(0);">{{street.name}}</a></li>'
						+'</ul>'
					+'</div>'
					+'<div class="mc" ng-show="'+objName+'.tabShowIndex==\'4\'">'
					+'<ul  class="area-list" >'
					+'<li ng-click="'+objName+'.countyQueryClick(county.id,county.name)" ng-repeat="county in '+objName+'.countyQueryData"><a ng-class="{bh:'+objName+'.chooseCountyId==county.id}" href="javascript:void(0);">{{county.name}}</a></li>'
					+'</ul>'
					+'</div>'
			 		+'<div class="titleCity"><h5>选择地市信息</h5></div>'
			 		+'<div class="qx" style="display:none;"><label>区县：</label><input class="whe form-control" style="font-size: 11px;height: 26px;"type="text" ng-model="'+objName+'.countyName" ng-change="'+objName+'.countyQuery('+objName+'.countyName)"></div>'
			 		+'<div class="close_m"><a class="ui-city-close" href="javascript:void(0);" ng-click="'+objName+'.close()">x</a></div>'
			 		+'<div class="qc_q"><a class="ui-city-clos" href="javascript:void(0);" ng-click="'+objName+'.clear()">清除</a></div>'
			 		+'<div class="qr_q"><a class="ui-city-clos" href="javascript:void(0);" ng-click="'+objName+'.confirm()">确认</a></div>'
		 		+'</div>'
	 		+'</div>'
	 		+'</div>';
		element.html(html);
		return function($scope, element, attrs){
			cityWin.executive=function (chooseCityId,chooseCityName,chooseRegionId,chooseRegionName,chooseCountyId,chooseCountyName){
				if(exec!=null && exec!=undefined && exec!=""){
					eval(exec+"('"+chooseCityId+"','"+chooseCityName+"','"+chooseRegionId+"','"+chooseRegionName+"','"+chooseCountyId+"','"+chooseCountyName+"')");
				}
			};
			cityWin.focus=function(){
				//eval("$scope."+objName+"focus.focus(true)");
			};
			cityWin.callBack=function(){
				if(callBack!=undefined&&callBack!=null&&callBack!=""){
					eval("$scope."+callBack+"()");
				}
			};
			
			if(isParent!=undefined){
				eval("$scope.$parent."+objName+"=cityWin");
			}else{
				eval("$scope."+objName+"=cityWin");
			}
			eval("$scope."+objName+".countyName=''");
		};
	};
	return myCity;
}]);

 /**这个用于获取url的参数  **/
 function getQueryString(name) {
	   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	   var r = window.location.search.substr(1).match(reg);
	   if (r != null) return unescape(r[2]); return null;
 } 
 /**设置cookie**/
 function setCookie(name,value)
 {
	 var Days = 30;
	 var exp = new Date();
	 exp.setTime(exp.getTime() + Days*24*60*60*1000);
	 document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
 }
 
 /**检查手机号码 新增属性进行校验 很多地方用到**/
 function validatemobile(mobile){
     if(mobile.length==0 || mobile==undefined){
        return false;
     }
     if(mobile.length!=11){
         return false;
     }
     var myreg = /^0?(13[0-9]|15[012356789]|18[0-9]|14[57]|17[03678])[0-9]{8}$/;
     if(!myreg.test(mobile)){
         return false;
     }
    return true;
 }
 

 function getTableWidth(){
 		var divWidth=document.getElementsByTagName("table")[0].parentElement.clientWidth;
 		var thArr=document.getElementsByTagName("th");
 		var tdArr=document.getElementsByTagName("td");
 		var firstWidth=thArr[0].clientWidth;
 		var secondWidth=thArr[1].clientWidth;
 		if(divWidth>10+(firstWidth+secondWidth*(thArr.length-1))){
 			for(i=1;i<thArr.length;i++){
 				thArr[i].style.width=divWidth/(thArr.length)+"px";
 				tdArr[i].style.width=divWidth/(tdArr.length)+"px";
 			}
 			document.getElementsByTagName("table")[0].style.width=divWidth+"px";
 		}else{
 			document.getElementsByTagName("table")[0].style.width=10+(firstWidth+secondWidth*(thArr.length-1))+"px";
 		}
 }

 
 
 /** 是否有权限访问  */
 commonApp.directive("myHasAuth",['commonService',function(commonService){
                     return {
                         restrict:"A",
                         link:function(scope, element, attr){
                         	var entityId = attr.myHasAuth;
                         	var css=attr.css;
                         	var reg = new RegExp("^[0-9]*$");
                         	
                         	if(reg.test(entityId)){
                         		var url = "portalBusiBO.ajax?cmd=hasAuth";
                     			commonService.postUrl(url,"entityId="+entityId,function(data){
                     				if(data==1){
                     					if(css==undefined){
	                     					element.removeClass("ng-hide");
	                     					element.addClass("ng-show");
                     					}else{
                     						element.removeClass(css);
                     					}
                     				}else{
                     					if(css==undefined){
	                     					element.removeClass("ng-show");
	                     					element.addClass("ng-hide");
                     					}else{
                     						element.addClass(css);
                     					}
                     				}
                     				
                     			});
                         	}
                         }
        };
 }]);
 
 
//table 列表头部随滚动条滚动
// window.onload=function(){
//         function $(id){return document.getElementById(id)}
//         var hDiv=$('hDiv'),dDiv=$('dDiv'),tb0=$('tb0'),tb1=$('tb1');
//         if(hDiv!=null&&hDiv!=undefined){
//		         dDiv.onscroll=function(){
//		           tb0.style.left = dDiv.scrollLeft*-1 +'px'
//		       };
//         }
//     
//}

 /**
  * 表格选中的样式
  * 
  * 只需要在表格的tr标签上加上这个属性 
  * 
  
 commonApp.directive('select',function(){
 	return {
 		restrict:'A',
 		link: function( $scope, element, attrs ) {
 			var classNameTemp="selected1";
 			if(attrs.mySelect!=null && attrs.mySelect!=undefined && attrs.mySelect!=""){
 				classNameTemp=attrs.mySelect;
 			}
 			element.bind("click",function(){
 				for(var i=0;i< element.parent().children().length;i++){
 					console.info(element.parent().children());
 					var className=element.parent().children()[i].className;
 					if(className.indexOf(classNameTemp)!=-1){
 						className=className.replace(classNameTemp,"");
 					}
 					element.parent().children()[i].className=className;
 				};
 				element.addClass(className);
 			});
 		}
 	};
 });
*/
