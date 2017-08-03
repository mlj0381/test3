var addSysRoleApp = angular.module("addSysRoleApp", ['commonApp']);
addSysRoleApp.controller("addSysRoleCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var roleId = getQueryString("roleId");
	var tow={
			init:function(){
				this.bindEvent();
				this.selectOrg();
				this.doQuery();
			},
			bindEvent:function(){
				$scope.doSave = this.doSave;
				$scope.close = this.close;
				$scope.data = this.data;
			},
			data:{},
			selectOrg:function(){
				commonService.postUrl("staticDataBO.ajax?cmd=queryOrganization","",function(data){
					if(data != null && data != undefined && data != ""){
						$scope.orgInfo = data;
						if(roleId==null || roleId==undefined || roleId==""){
						   $scope.data.orgId = data[0].orgId;
						}
					}
				});
			},
			doQuery:function(){
				if(roleId!=null && roleId!=undefined && roleId!=""){
					$scope.data.roleId=roleId;
					commonService.postUrl("sysRoleBO.ajax?cmd=querySysRoleDtl",$scope.data,function(data){
						if(data!=null && data!=undefined && data!=""){
							$scope.data=data.sysRole;
						}
					});
				}
			},
			doSave:function(){
				if($scope.data.roleName==null || $scope.data.roleName==undefined || $scope.data.roleName==""){
					commonService.alert("角色名称不能为空");
					return false;
				}
				if($scope.data.orgId==null || $scope.data.orgId==undefined || $scope.data.orgId==""){
					commonService.alert("角色所属组织不能为空");
					return false;
				}
				commonService.postUrl("sysRoleBO.ajax?cmd=doSaveSysRole",$scope.data,function(data){
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

