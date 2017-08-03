var staffRoleManagementApp = angular.module("staffRoleManagementApp", ['commonApp']);
staffRoleManagementApp.controller("staffRoleManagementCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var userInfo ={
		init:function(){
			this.bindEvent();
			this.doQuery();
		},
		bindEvent:function(){
			$scope.doQuery = this.doQuery;
			$scope.queryParam = this.queryParam;
			$scope.addUser = this.addUser;
			$scope.updateUser = this.updateUser;
			$scope.getMenuTypeOfComapny = this.getMenuTypeOfComapny;
			$scope.getRoleOfMenuType = this.getRoleOfMenuType;
			$scope.getRoleNameById = this.getRoleNameById;
			$scope.companyChange = this.companyChange;
			$scope.queryRoleOfCompany = this.queryRoleOfCompany;
			$scope.clear = this.clear;
			$scope.delUser = this.delUser;
		},
		queryParam:{
			page:1,
			rows:10
		},
		companyChange:function(){
			var menuType=userInfo.getMenuTypeOfComapny($scope.queryParam.tenantId);
			userInfo.queryRoleOfCompany(menuType);
		},
		//查询列表
		doQuery:function(){
			var url = "sysRoleBO.ajax?cmd=queryRoleST";
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:$scope.queryParam,
							callBack:'setContentHegthDelay'
						});
			},500);
		},
		addUser:function(){
			commonService.openTab("333331","新增员工角色","/cm/roleGrantOfCompany.html");
		},
		updateUser:function(){
			var selectedDate = $scope.page.getSelectData();
			if(selectedDate == undefined || selectedDate.length == 0){
				commonService.alert("请选择一条数据！");
				return false;
			}
			var tenantId = selectedDate[0].tenantId;
			var roleName=selectedDate[0].roleName;
			var menuType=$scope.getRoleOfMenuType(selectedDate[0].roleId);
			commonService.openTab("role_"+selectedDate[0].roleId,"修改员工角色",encodeURI("/cm/roleGrantOfCompany.html?roleId="+selectedDate[0].roleId+"&roleName="+roleName+"&tenantId="+tenantId+"&menuType="+menuType));
		},
		getMenuTypeOfComapny:function(tenantId){
			for(var index in $scope.orgInfos){
				if($scope.orgInfos[index].tenantId==tenantId){
					return $scope.orgInfos[index].tenantType;
				}
			}
		},
		getRoleOfMenuType:function(roleId){
			//获取角色对应的菜单类型，一个角色只能赋权一种菜单类型，为了进去的页面展示
			for(var index in $scope.roleInfos){
				if($scope.roleInfos[index].roleId==roleId){
					if($scope.roleInfos[index].menuType==""){
						return 1;
					}
					return $scope.roleInfos[index].menuType;
				}
			}
			return 1;
		},
		getRoleNameById:function(roleId){
			for(var index in $scope.roleInfos){
				if($scope.roleInfos[index].roleId==roleId){
					return $scope.roleInfos[index].roleName;
				}
			}
			return "";
		},
		clear:function(){
			$scope.queryParam.roleName = "";
			$scope.doQuery;
		},
		delUser:function(){
			var selectedDate = $scope.page.getSelectData();
			if(selectedDate == undefined || selectedDate.length == 0){
				commonService.alert("请选择一条数据！");
				return false;
			};
			commonService.confirm("确认要删除该用户!",function(){
				var roleId=selectedDate[0].roleId;
				commonService.postUrl("sysRoleBO.ajax?cmd=delSysRoleAllByRoleId",{roleId:roleId},function(data){
					commonService.alert("删除成功！");
					$scope.doQuery();
				});
			});
		}
	};
	userInfo.init();
}]);