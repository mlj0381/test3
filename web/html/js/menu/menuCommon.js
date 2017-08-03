/**
 * 菜单管理
 * menuOut双向绑定个方法，调指令内部方法
 * callback(id,name,url,menuId)回调函数
 */
var menuCommon = angular.module('menuCommon',[]);

menuCommon.directive('myMenu',function(){
	var myMenu = {
		restrict:"AE",
		templateUrl : function(tElement,tAttrs){
			return '/js/menu/myMenu.html?ver='+tAttrs.ver;
		},
		//独立作用域
		scope : {
			'menuOut':'=',
			'callback':'&'
		},
		compile : function(element, attrs){
			
		},
		controller:['$scope','commonService',function($scope,commonService){
			$scope.menuDataGroupBy = [];
			var menu = {
				//初始化数据
				init:function(){
					this.bindEvent();
					this.bindOut();
					this.getMenu();
				},
				bindEvent:function(){
					$scope.getMenu = this.getMenu;
					$scope.menuShow = this.menuShow;
					$scope.menuClick = this.menuClick;
					$scope.hideAllMenu = this.hideAllMenu;
				},
				//对外提供方法
				bindOut:function(){
					$scope.menuOut = {};
					$scope.menuOut.hideAllMenu = this.hideAllMenu;
				},
				//初始化菜单数据
				getMenu : function(){
					commonService.postUrl("portalBusiBO.ajax?cmd=getTopMenu","",function(data){
						$scope.menuData = data;
						for(var k=0;k<$scope.menuData.childMenus.length;k++){
							$scope.menuData.childMenus[k].show=false;
							var map = {};
						    var  dest = [];
							var arr = $scope.menuData.childMenus[k].childMenus;
							for(var i = 0; i < arr.length; i++){
							    var ai = arr[i];
							    if(!map[ai.domainName]){
							        dest.push({
							            name: ai.domainName,
							            data: [ai]
							        });
							        map[ai.domainName] = ai;
							    }else{
							        for(var j = 0; j < dest.length; j++){
							            if(dest[j].name == ai.domainName){
							            	dest[j].data.push(ai);
							                break;
							            }
							        }
							    }
							}
							$scope.menuData.childMenus[k].checkedObj = dest;
						}
					});
				},
				//展示或者隐藏菜单
				menuShow:function(menuId){
					for(var i=0;i<$scope.menuData.childMenus.length;i++){
						if($scope.menuData.childMenus[i].menuId==menuId){
							$scope.menuData.childMenus[i].show=true;
						}else{
							$scope.menuData.childMenus[i].show=false;
						}
					}
				},
				//点击菜单打开tab
				menuClick:function(id,name,url,menuId){
					//回调函数
					$scope.callback({id:id,name:name,url:url,menuId:menuId});
					menu.hideAllMenu();
				},
				//异常所有的菜单
				hideAllMenu:function(){
					for(var i=0;i<$scope.menuData.childMenus.length;i++){
						$scope.menuData.childMenus[i].show=false;
					}
				}
			};
			return menu.init();
		}]
	};
	return myMenu;
});
