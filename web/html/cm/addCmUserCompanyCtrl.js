function changeTabCallback(){
	console.log(changeTabCallback);
	var appElement = document.querySelector('[ng-controller=addCmUserCompanyCtrl]');
	var scope=angular.element(appElement).scope();
	scope.queryRoleOfCompany();
}

var addCmUserCompanyApp = angular.module("addCmUserCompanyApp", ['commonApp']);
addCmUserCompanyApp.controller("addCmUserCompanyCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var userId = commonService.getQueryString("userId");
	var userName=commonService.getQueryString("userName");
	var tenantName=commonService.getQueryString("tenantName");
	var orgId=commonService.getQueryString("orgId");
	var loginAcct=commonService.getQueryString("loginAcct");
	var roleId=commonService.getQueryString("roleId");
	var loginType = commonService.getQueryString("loginType");
	var userType = commonService.getQueryString("userType");
	
	var userInfo={
			init:function(){
				this.bindEvent();
				this.queryCompany();
				this.queryRoleOfCompany();
				this.editInit();
				this.queryUserType();
				this.queryUserInfoType();
			},
			bindEvent:function(){
				$scope.user=this.user;
				$scope.doSave=this.doSave;
				$scope.isEdit=this.isEdit;
				$scope.roleManage=this.roleManage;
				$scope.close=this.close;
				$scope.checkPassWord=this.checkPassWord;
				$scope.clearPassword = this.clearPassword;
				$scope.queryRoleOfCompany=this.queryRoleOfCompany;
			},
			isEdit:false,
			editInit:function(){
				//修改初始化
				if(userId!=undefined && userId!="" && userId!=null){
					this.user.loginAcct=loginAcct;
					this.user.userId=userId;
					this.user.userName=userName;
					this.user.orgId=orgId;
					this.user.roleId=roleId;
					$scope.isEdit=true;
					this.isEdit=true;
					
					this.user.password = "      ";
					
				}
			},
			queryUserType:function(){
				commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{codeType:"LOGIN_TYPE"},function(data){
					if(data != null && data != undefined && data != ""){
						$scope.userTypeList=data.items; 
						if($scope.isEdit){
							$scope.user.loginType  = loginType+"";
						}else{
							$scope.user.loginType  =$scope.userTypeList[0].codeValue;
						}
					}
				});
			},
			//查询用户类型
			queryUserInfoType:function(){
				commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeUserType",{codeType:"USER_TYPE_ZXCOMPANY"},function(data){
					if(data != null && data != undefined && data != ""){
						$scope.userTypeInfoList=data.items; 
						if($scope.isEdit){
							$scope.user.userType  = userType+"";
						}else{
							$scope.user.userType  =$scope.userTypeInfoList[0].codeValue;
						}
					}
				});
			},
			roleManage:function(){
				var roleName=userInfo.getRoleNameById($scope.user.roleId);
				var menuType=userInfo.getRoleOfMenuType($scope.user.roleId);
				commonService.openTab("role_"+$scope.user.roleId,"角色管理",encodeURI("/cm/roleGrantOfCompany.html?roleId="+$scope.user.roleId+"&roleName="+roleName+"&menuType="+menuType));
			},
			clearPassword:function(){
				$scope.user.password = "";
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
			user:{
				loginAcct:"",
				userName:"",
				password:"",
				orgId:"",
				roleId:"",
				userId:"",
				loginType:""
			},
			queryCompany:function(){
				var that=this;
				//查询公司下面的所有的网点
				commonService.postUrl("organizationBO.ajax?cmd=queryOrgList","",function(data){
					if(data != null && data != undefined && data != ""){
						$scope.orgInfos = data;
						if(that.isEdit){
							$scope.user.orgId=parseInt(orgId);
						}else{
							$scope.user.orgId=parseInt($scope.orgInfos[0].orgId);
						}
					}
				});
				
			},
			queryRoleOfCompany:function(){
				var that=this;
				//查询当前租户的角色
				commonService.postUrl("sysRoleBO.ajax?cmd=queryRoleByTenant","",function(data){
					if(data != null && data != undefined && data != ""){
						$scope.roleInfos = data;
						if(that.isEdit){
							$scope.user.roleId=parseInt(roleId);
						}else{
							$scope.user.roleId=parseInt($scope.roleInfos[0].roleId);
						}
						
					}
				});
			},
			queryCmUser:function(){
				if(userId!=null && userId!=undefined && userId!=""){
					commonService.postUrl("webCmUserInfoBO.ajax?cmd=doQueryCmUserByUserId","userId="+userId,function(data){
						if(data != null && data != undefined && data != ""){
							$scope.data=data.cmUser;
							$scope.data.loginPwd=$scope.checkPassWord(data.cmUser.loginPwd);
							$scope.data.userType=data.cmUser.userType+"";
						}
					});
				}
			},
			
			doSave:function(){
				var that=this;
				if($scope.user.loginAcct==null || $scope.user.loginAcct==undefined || $scope.user.loginAcct==""){
					commonService.alert("登录账户不能为空");
					return false;
				}
				if($scope.user.userName==null || $scope.user.userName==undefined || $scope.user.userName==""){
					commonService.alert("姓名不能为空");
					return false;
				}
				if(userId < 0){
					if($scope.user.password==null || $scope.user.password==undefined || $scope.user.password==""){
						commonService.alert("密码不能为空");
						return false;
					}
				}
				if($scope.user.orgId==null || $scope.user.orgId==undefined || $scope.user.orgId==""){
					commonService.alert("账号归属公司不能为空");
					return false;
				}
				
				if($scope.user.roleId==null || $scope.user.roleId==undefined || $scope.user.roleId==""){
					commonService.alert("角色不能为空");
					return false;
				}
				 
				if(userId > 0){
					if($scope.user.password==null || $scope.user.password==undefined || $scope.user.password=="" || $scope.user.password=="      "){
						$scope.user.password="";
					}
				}
				var first = Math.round(Math.random()*80) + 10;
				var last = Math.round(Math.random()*80) + 10;
				var base64 = new Base64();
				$scope.user.password=base64.encode(first+""+$scope.user.password+""+last+"{zx}");
				commonService.postUrl("cmUserInfoBO.ajax?cmd=doSaveCmUserInfo",$scope.user,function(data){
					$scope.user.password=$scope.checkPassWord($scope.user.password);
					commonService.alert("设置成功!",function(){
						$scope.close();
					});
				},function(data){
					$scope.user.password=$scope.checkPassWord($scope.user.password);
					commonService.alert(data.message);
				});
			},
			close:function(){
				commonService.closeToParentTab(true);
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
	userInfo.init();
}]);
