var cmPlatUserInfoApp = angular.module("cmPlatUserInfoApp", ['commonApp']);
cmPlatUserInfoApp.controller("cmPlatUserInfoCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var userInfo ={
		init:function(){
			this.queryCompany();
			this.queryRoleOfCompany();
			this.doQuery();
			this.bindEvent();
		},
		bindEvent:function(){
			$scope.queryParam=this.queryParam;
			$scope.addUser=this.addUser;
			$scope.doQuery=this.doQuery;
			$scope.updateUser=this.updateUser;
			$scope.roleManager=this.roleManager;
			$scope.cleanQuery=this.cleanQuery;
			$scope.delUser=this.delUser;
		},
		queryParam:{
			userName:"",
			roleId:"",
			tenantId:"",
			loginAcct:"",
			page:1
		},
		cleanQuery:function(){
			$scope.queryParam={
					userName:"",
					roleId:"",
					tenantId:"",
					loginAcct:""
				};
		},
		queryCompany:function(){
			//查询全部公司
			commonService.postUrl("organizationBO.ajax?cmd=queryCompanyList","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.orgInfos = data;
				}
			});
		},
		queryRoleOfCompany:function(){
			//查询公司管理员角色
			commonService.postUrl("sysRoleBO.ajax?cmd=queryRoleByPlat","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.roleInfos = data;
				}
			});
		},
		//查询列表
		doQuery:function(){
			var url = "cmUserInfoBO.ajax?cmd=queryCompanyUser";
		
			$timeout(function(){
				
				$scope.page.load({
							url:url,
							params:$scope.queryParam,
							callBack:'setContentHegthDelay'
						});
			},500);
		},
		delUser:function(){
			var selectedDate=$scope.page.getSelectData();
			if(selectedDate==undefined || selectedDate.length==0){
				commonService.alert("请选择一条数据！");
			}
			commonService.confirm("确认要删除该用户!",function(){
				var userId=selectedDate[0].userId;
				commonService.postUrl("cmUserInfoBO.ajax?cmd=delUser",{userId:userId},function(data){
					commonService.alert("删除成功！");
					$scope.doQuery();
				});
			});
			
		},
		updateUser:function(){
			var selectedDate=$scope.page.getSelectData();
			if(selectedDate==undefined || selectedDate.length==0){
				commonService.alert("请选择一条数据！");
			}
			selectedDate=selectedDate[0];
			delete selectedDate.$$hashKey;
			var queryParamStr="?";
			for(var key in selectedDate){
				if(selectedDate[key]!=undefined){
					queryParamStr=queryParamStr+key+"="+selectedDate[key]+"&";
				}
			}
			commonService.openTab(selectedDate.userId,"修改公司管理员",encodeURI("/cm/addCmPlatUserInfo.html"+queryParamStr));
		},
		addUser:function(){
			commonService.openTab("222222","新增公司管理员","/cm/addCmPlatUserInfo.html");
		},
		roleManager:function(){
			commonService.openTab("33333","公司角色管理","/cm/roleGrant.html");
		}
	};
	userInfo.init();
}]);