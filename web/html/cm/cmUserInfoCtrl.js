var cmUserInfoApp = angular.module("cmUserInfoApp", ['commonApp']);
cmUserInfoApp.controller("cmUserInfoCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var userInfo ={
		init:function(){
			this.queryOrg();
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
			$scope.toUserArea = this.toUserArea;
			$scope.delUser=this.delUser;
		},
		queryParam:{
			userName:"",
			roleId:"",
			orgId:"",
			loginAcct:"",
			page:1,
			loginType:""
		},
		cleanQuery:function(){
			$scope.queryParam={
					userName:"",
					roleId:"",
					orgId:"",
					loginAcct:""
				};
		},
		queryOrg:function(){
			//查询公司下面的所有的网点
			commonService.postUrl("organizationBO.ajax?cmd=queryOrgList","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.orgInfos = data;
				}
			});
		},
		queryRoleOfCompany:function(){
			//查询公司管理员角色
			commonService.postUrl("sysRoleBO.ajax?cmd=queryRoleByTenant","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.roleInfos = data;
				}
			});
		},
		toUserArea:function(){
			var selectedDate=$scope.page.getSelectData();
			if(selectedDate==undefined || selectedDate.length==0){
				commonService.alert("请选择一条数据！");
			}
			var userId=selectedDate[0].userId;
			
			commonService.openTab(userId,"新增操作区域","/cm/addCmUserArea.html?userId="+userId);
		},
		//查询列表
		doQuery:function(){
			var url = "cmUserInfoBO.ajax?cmd=queryOrgUser";
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
			commonService.confirm("确认要注销该用户!",function(){
				var userId=selectedDate[0].userId;
				commonService.postUrl("cmUserInfoBO.ajax?cmd=delUser",{userId:userId},function(data){
					commonService.alert("注销成功！");
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
			commonService.openTab(selectedDate.userId,"修改普通员工",encodeURI("/cm/addCmUserInfo.html"+queryParamStr));
		},
		addUser:function(){
			commonService.openTab("222222","新增普通员工","/cm/addCmUserInfo.html");
		},
		roleManager:function(){
			commonService.openTab("33333","角色管理","/cm/roleGrantOfCompany.html");
		}
	};
	userInfo.init();
}]);