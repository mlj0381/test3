var querySpecialLineUserInfoPullApp = angular.module("querySpecialLineUserInfoPullApp", ['commonApp','tableCommon']);
querySpecialLineUserInfoPullApp.controller("querySpecialLineUserInfoPullCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.query={};
	$scope.black={};
	var userInfo={
			init:function(){
				this.bindEvent();
				this.getSysStaticData();
			},
			head:[
			       {"name":"专线公司","code":"tenantName","width":"100"},
			       {"name":"拉包工工号","code":"jobNumber","width":"100"},
	               {"name":"拉包工","code":"userName","width":"100"},
	               {"name":"拉包工手机","code":"billId","width":"100"},
	               {"name":"服务地区","code":"address","width":"100"},
	               {"name":"账户类型","code":"paymentTypeString","width":"100"},
	               {"name":"归属银行","code":"bankName","width":"100"},
	               {"name":"持卡人/帐号名","code":"accountName","width":"100"},
	               {"name":"卡号/帐号","code":"bankICard","width":"100"},
	               {"name":"认证状态","code":"pullStateString","width":"100"},
	               {"name":"合作方式","code":"cooperationMode","width":"100"},
	               {"name":"默认最大接单数","code":"defaultSingularNum","width":"100"}
			      ],
			bindEvent:function(){
				$scope.head=userInfo.head;
				$scope.url="cmUserInfoPullBO.ajax?cmd=queryCmUserInfoPull";
				$scope.urlParam=$scope.query;
				$scope.doQuery = this.doQuery;//查询列表
				$scope.getSysStaticData = this.getSysStaticData;//获取静态数据
				$scope.clean = this.clean;//清除所有数据
				$scope.verifyUserInfoPull = this.verifyUserInfoPull;//审核
				$scope.addUserInfoPull = this.addUserInfoPull;
				$scope.updateUserInfoPull = this.updateUserInfoPull;
				$scope.delUserInfoPull = this.delUserInfoPull;
			},
			doQuery:function(){
				var url = "cmUserInfoPullBO.ajax?cmd=queryCmUserInfoPull";
				$scope.query.page=1;
				$scope.page.load();
				$scope.page.callBack=function(){
					displayTable();
					setContentHeight();
				}
			},
			getSysStaticData:function(){
				var url = "staticDataBO.ajax?cmd=selectSysStaticDataByCodeType";
				var param = {codeType:"PULL_STATE_YQ"}
				commonService.postUrl(url,param,function(data){
					$scope.pullStaticDate = data;
				});
				var param2 = {codeType:"COOPERATION_MODE_YQ"}
				commonService.postUrl(url,param2,function(data){
					$scope.cooperationModeData = data;
				});
			},
			clean:function(){
				$scope.query.pullState="";
				$scope.query.tenantName = "";
				$scope.query.userName = "";
				$scope.query.billId = "";
				$scope.query.cooperationMode = "";
				$scope.query.pullState = "";
				$scope.query.jobNumber="";
			},
			pullBlack:function(num){
				var selectedDate = $scope.page.getSelectData();
				if(selectedDate == undefined || selectedDate.length == 0 || selectedDate.length > 1){
					commonService.alert("请选择一条数据！");
					return false;
				}
				var userName = selectedDate[0].userName;
				if(num == 1){
					$scope.black.userName = userName;
					$scope.isBlack = true;
					$scope.notBlack = true;
				}
				if(num == 2){
					$scope.black={};
					$scope.isBlack = false;
					$scope.notBlack = false;
				}
				if(num == 3){
					$scope.doSaveIsBlack(selectedDate[0].userId);
					$scope.black={};
					$scope.isBlack = false;
					$scope.notBlack = false;
				}
			},
			verifyUserInfoPull:function(){
				var selectedDate = $scope.page.getSelectData();
				if(selectedDate == undefined || selectedDate.length == 0 || selectedDate.length > 1){
					commonService.alert("请选择一条数据！");
					return false;
				}
				var userId = selectedDate[0].userId;
				commonService.openTab("pull_"+userId+"_verify","审核拉包工信息",encodeURI("/cm/addCmUserInfoPull.html?userId="+userId+"&verify=1"));
			},
			addUserInfoPull:function(){
				commonService.openTab("pull_222_new","新增拉包工信息",encodeURI("/cm/addCmUserInfoPull.html"));
			},
			updateUserInfoPull:function(){
				var selectedDate = $scope.page.getSelectData();
				if(selectedDate == undefined || selectedDate.length == 0 || selectedDate.length > 1){
					commonService.alert("请选择一条数据！");
					return false;
				}
				var userId = selectedDate[0].userId;
				commonService.openTab("pull_"+userId+"_update","修改拉包工信息",encodeURI("/cm/addCmUserInfoPull.html?userId="+userId));
			},
			delUserInfoPull:function(){
				var selectedDate = $scope.page.getSelectData();
				if(selectedDate == undefined || selectedDate.length == 0 || selectedDate.length > 1){
					commonService.alert("请选择一条数据！");
					return false;
				}
				var userId = selectedDate[0].userId;
				commonService.confirm("确认要注销该用户!",function(){
					commonService.postUrl("cmUserInfoPullBO.ajax?cmd=delCmUserInfoPull",{"userId":userId},function(data){
						if(data == "Y"){
							commonService.alert("注销成功！");
							$scope.doQuery();
						}
					});
				});
			}
	};
	userInfo.init();
}]);
