var addCmUserInfoApp = angular.module("addCmUserInfoApp", ['commonApp']);
addCmUserInfoApp.controller("addCmUserInfoCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	
	var userId = getQueryString("userId");
	var showTenant = getQueryString("showTenant");
	var tow={
			init:function(){
				this.bindEvent();
				this.selectOrg();
				this.selecUserInRole();
				this.queryCmUser();
				this.selectRootOrg();
				
				
			},
			bindEvent:function(){
				$scope.doSave = this.doSave;
				$scope.close = this.close;
				$scope.data = this.data;
				$scope.selectOrg = this.selectOrg;
				$scope.checkPassWord = this.checkPassWord;
				$scope.operWin = this.operWin;
				$scope.changeData = this.changeData;
				$scope.selecUserInRole = this.selecUserInRole;
				
			},
			data:{},
			selectOrg:function(){
				commonService.postUrl("staticDataBO.ajax?cmd=selectOrgByRole",$scope.data,function(data){
					if(data != null && data != undefined && data != ""){
						$scope.orgInfo = data;
						if(userId==null || userId==undefined || userId==""){
							$scope.data.orgId = data[0].orgId;
						}
						     
					     }
				});
			},
			
			selectRootOrg:function(){
				commonService.postUrl("cmCompanyBO.ajax?cmd=getCompanyList","",function(data){
					if(data != null && data != undefined && data != ""){
						$scope.companyData=data;
						if(userId==null || userId==undefined || userId==""){
						     $scope.data.tenantId=data.items[0].tenantId;
						}
					}
				});
			},
			queryCmUser:function(){
				if(userId!=null && userId!=undefined && userId!=""){
					commonService.postUrl("webCmUserInfoBO.ajax?cmd=doQueryCmUserByUserId","userId="+userId,function(data){
						if(data != null && data != undefined && data != ""){
							$scope.data=data.cmUser[0];
							
							//$scope.data.loginPwd=$scope.checkPassWord(data.cmUser.loginPwd);
							//$scope.data.userType=data.cmUser.userType+"";
							$scope.show=true;
						}
					});
				}
			},
			/**查询角色**/
			selecUserInRole:function(){
				commonService.postUrl("sysRoleBO.ajax?cmd=getRoleByTenantId",$scope.data,function(data){
					if(data != null && data != undefined && data != ""){
						$scope.userInRole = data;
						$scope.data.roleId=data[0].roleId;
						//$scope.data.userType = data.items[0].codeValue;
					}
				});
			},
			/**跳转角色管理页面**/
			operWin:function(){
				commonService.openTab("1122","角色管理","/sys/sysRole.html");
			},
			doSave:function(){
				if($scope.data.loginAcct==null || $scope.data.loginAcct==undefined || $scope.data.loginAcct==""){
					commonService.alert("登录账户不能为空");
					return false;
				}
				if($scope.data.userName==null || $scope.data.userName==undefined || $scope.data.userName==""){
					commonService.alert("用户名不能为空");
					return false;
				}
				if($scope.data.loginPwd==null || $scope.data.loginPwd==undefined || $scope.data.loginPwd==""){
					commonService.alert("账户密码不能为空");
					return false;
				}
				if($scope.data.roleId==null || $scope.data.roleId==undefined || $scope.data.roleId==""){
					commonService.alert("用户角色不能为空");
					return false;
				}
				if($scope.data.tenantId==null || $scope.data.tenantId==undefined || $scope.data.tenantId==""){
					commonService.alert("归属公司不能为空");
					return false;
				}
				if($scope.data.orgId==null || $scope.data.orgId==undefined || $scope.data.orgId==""){
					commonService.alert("归属网点不能为空");
					return false;
				}
				
				var first = Math.round(Math.random()*80) + 10;
				var last = Math.round(Math.random()*80) + 10;
				var base64 = new Base64();
				$scope.data.loginPwd=base64.encode(first+""+$scope.data.loginPwd+""+last+"{zx}");
				commonService.postUrl("webCmUserInfoBO.ajax?cmd=doSaveCmUserInfo",$scope.data,function(data){
					if(data != null && data != undefined && data != ""){
						$scope.data.loginPwd=$scope.checkPassWord($scope.data.loginPwd);
						
						if(userId!=null && userId!=undefined && userId!=""){
							commonService.alert("修改成功!");
							
						}else{
							commonService.alert("保存成功!");
						}
					}
				},function(data){
					$scope.data.loginPwd=$scope.checkPassWord($scope.data.loginPwd);
					window.top.alert(data.message);
					
				});
			},
			close:function(){
				commonService.closeToParentTab(true);
			},
			changeData:function(tenantId){
				$scope.data.tenantId;
				$scope.selectOrg();
				$scope.selecUserInRole();
			},
			checkPassWord:function(passWord){
				var base64 = new Base64();
				if(!isNaN(passWord)){
				}else{
					var pwd = base64.decode(passWord);
					if(pwd.indexOf("{zx}")>0){
						pwd=pwd.substring(2, (pwd.length-6));
						passWord=pwd;
					}
				}
				return passWord;
			}
	};
	tow.init();
}]);
