var addSysUrlApp = angular.module("addSysUrlApp", ['commonApp']);
addSysUrlApp.controller("addSysUrlCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var id = getQueryString("id");
	var tow={
			init:function(){
				this.bindEvent();
				this.querySysUrlDtl();
			},
			bindEvent:function(){
				$scope.doSave = this.doSave;
				$scope.close = this.close;
				$scope.data = this.data;
				
			},
			data:{},
			/**查询Url详情**/
			querySysUrlDtl:function(){
				if(id!=null && id!=undefined && id!=""){
					commonService.postUrl("sysRoleBO.ajax?cmd=querySysUrlDtl","id="+id,function(data){
						if(data != null && data != undefined && data != ""){
							$scope.data=data.sysUrl;
							/*$scope.data.loginPwd=$scope.checkPassWord(data.cmUser.loginPwd);
							$scope.data.userType=data.cmUser.userType+"";*/
						}
					});
				}
			},
			doSave:function(){
				console.log($scope.data);
				if($scope.data.urlName==null || $scope.data.urlName==undefined || $scope.data.urlName==""){
					commonService.alert("URL名称不能为空");
					return false;
				}
				if($scope.data.urlPath==null || $scope.data.urlPath==undefined || $scope.data.urlPath==""){
					commonService.alert("URL路径不能为空");
					return false;
				}
				if($scope.data.groupName==null || $scope.data.groupName==undefined || $scope.data.groupName==""){
					commonService.alert("分组名称不能为空");
					return false;
				}
				commonService.postUrl("sysRoleBO.ajax?cmd=doSaveSysUrl",$scope.data,function(data){
					if(data != null && data != undefined && data != ""){
						commonService.alert("设置成功!");
					}else{
						commonService.alert("设置失败!");
					}
				});
			},
			close:function(){
				commonService.closeToParentTab(true);
			}
	};
	tow.init();
}]);
