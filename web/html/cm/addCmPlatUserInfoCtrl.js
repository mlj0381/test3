var addCmPlatUserInfoApp = angular.module("addCmPlatUserInfoApp", ['commonApp']);
addCmPlatUserInfoApp.controller("addCmPlatUserInfoCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var userId = commonService.getQueryString("userId");
	var userName=commonService.getQueryString("userName");
	var tenantName=commonService.getQueryString("tenantName");
	var tenantId=commonService.getQueryString("tenantId");
	var loginAcct=commonService.getQueryString("loginAcct");
	var roleId=commonService.getQueryString("roleId");
	var showTenant = commonService.getQueryString("showTenant");
	var userInfo={
			init:function(){
				this.bindEvent();
				this.queryCompany();
//				this.queryRoleOfCompany();
				if(userId!=undefined && userId!="" && userId!=null){
					this.editInit();
				}
			},
			bindEvent:function(){
				$scope.user=this.user;
				$scope.doSave=this.doSave;
				$scope.isEdit=this.isEdit;
				$scope.roleManage=this.roleManage;
				$scope.close=this.close;
				$scope.checkPassWord=this.checkPassWord;
				$scope.companyChange=this.companyChange;
				$scope.clearPassword = this.clearPassword;
			},
			companyChange:function(){
				var menuType=userInfo.getMenuTypeOfComapny($scope.user.tenantId);
				
				userInfo.queryRoleOfCompany(menuType);
			},
			isEdit:false,
			editInit:function(){
				//修改初始化
				if(userId!=undefined && userId!="" && userId!=null){
					this.user.loginAcct=loginAcct;
					this.user.userId=userId;
					this.user.userName=userName;
					this.user.tenantId=tenantId;
					this.user.roleId=roleId;
					$scope.isEdit=true;
					this.isEdit=true;
					this.user.password = "      ";
				}
				
			},
			clearPassword:function(){
				$scope.user.password = "";
			},
			roleManage:function(){
				var roleName=userInfo.getRoleNameById($scope.user.roleId);
				var menuType=userInfo.getRoleOfMenuType($scope.user.roleId);
				commonService.openTab("role_"+$scope.user.roleId,"角色管理",encodeURI("/cm/roleGrant.html?roleId="+$scope.user.roleId+"&roleName="+roleName+"&tenantId="+tenantId+"&menuType="+menuType));
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
			user:{
				loginAcct:"",
				userName:"",
				password:"",
				tenantId:"",
				roleId:"",
				userId:""
			},
			queryCompany:function(){
				var that=this;
				//查询全部公司
				commonService.postUrl("organizationBO.ajax?cmd=queryCompanyList","",function(data){
					if(data != null && data != undefined && data != ""){
						$scope.orgInfos = data;
						if(that.isEdit){
							$scope.user.tenantId=tenantId;
						}else{
							$scope.user.tenantId=$scope.orgInfos[0].tenantId;
						}
						that.companyChange();
					}
				});
			},
			queryRoleOfCompany:function(menuType){
				var that=this;
				//查询公司管理员角色
				if(menuType==undefined){
					menuType="";
				}
				commonService.postUrl("sysRoleBO.ajax?cmd=queryRoleByPlat",{menuType:menuType},function(data){
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
				if(userId > 0){
					if($scope.user.password==null || $scope.user.password==undefined || $scope.user.password=="" || $scope.user.password=="      "){
						$scope.user.password="";
					}
				}
				if($scope.user.tenantId==null || $scope.user.tenantId==undefined || $scope.user.tenantId==""){
					commonService.alert("账号归属公司不能为空");
					return false;
				}
				
				if($scope.user.roleId==null || $scope.user.roleId==undefined || $scope.user.roleId==""){
					commonService.alert("角色不能为空");
					return false;
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
