var addSysMenuApp = angular.module("addSysMenuApp", ['commonApp','ngTouch','angucomplete']);
addSysMenuApp.controller("addSysMenuCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var menuId = getQueryString("menuId");
	
	$scope.countries = [];
	var tow={
			init:function(){
				this.bindEvent();
				this.sysMenu();
				this.querySysMenuDtl();
			},
			bindEvent:function(){
				$scope.doSave = this.doSave;
				$scope.close = this.close;
				$scope.data = this.data;
				$scope.form = this.form;
				//$scope.getMenuName = this.getMenuName;
			},
			data:{},
			form:{},
			sysMenu:function(){
					commonService.postUrl("sysRoleBO.ajax?cmd=queryAllSysMenu","",function(data){
						$scope.sysMenu=data;
						if(menuId==null || menuId==undefined || menuId==""){
						   $scope.data.parentId=$scope.sysMenu.items[0].menuId;
						}
					});
			},
			
			/**查询Url详情**/
			querySysMenuDtl:function(){
				if(menuId!=null && menuId!=undefined && menuId!=""){
					commonService.postUrl("sysRoleBO.ajax?cmd=querySysMenuDtl","menuId="+menuId,function(data){
						if(data != null && data != undefined && data != ""){
							$scope.data=data.sysMenu;
							  $scope.data.parentId=data.sysMenu.parentId;
							
						}
					});
				}
			},
			doSave:function(){
				//var parentId = $scope.selectedCountry.originalObject.menuId;
				//$scope.data.parentId=parentId;
				if($scope.data.menuName==null || $scope.data.menuName==undefined || $scope.data.menuName==""){
					commonService.alert("菜单名称不能为空");
					return false;
				}
				commonService.postUrl("sysRoleBO.ajax?cmd=doSaveSysMenu",$scope.data,function(data){
					if(data != null && data != undefined && data != ""){
						commonService.alert("设置成功!");
					}else{
						commonService.alert("设置失败!");
					}
				});
			},
			close:function(){
				commonService.closeToParentTab(true);
			},
			/**检索主驾驶员名称**/
			
	};
	tow.init();
}]);
