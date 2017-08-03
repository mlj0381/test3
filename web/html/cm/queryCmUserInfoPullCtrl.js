var queryCmUserInfoInfoPullApp = angular.module("queryCmUserInfoInfoPullApp", ['commonApp','tableCommon']);
queryCmUserInfoInfoPullApp.controller("queryCmUserInfoPullCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.query={};
	$scope.black={};
	var userInfo={
			init:function(){
				this.bindEvent();
				this.getSysStaticData();
				//this.doQuery();
			},
			head:[
			       {"name":"拉包公司","code":"tenantName","width":"100"},
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
				$scope.pullBlack = this.pullBlack;//拉黑展示处理
				$scope.doSaveIsBlack = this.doSaveIsBlack;//拉黑
				$scope.addUserInfoPull = this.addUserInfoPull;//新增
				$scope.updateUserInfoPull = this.updateUserInfoPull;//修改
				$scope.delUserInfoPull = this.delUserInfoPull;//删除
				$scope.verifyUserInfoPull = this.verifyUserInfoPull;//审核
				$scope.seeUserInfoPull = this.seeUserInfoPull;//查看
			},
			doQuery:function(){
				var url = "cmUserInfoPullBO.ajax?cmd=queryCmUserInfoPull";
				$scope.query.page=1;
				$scope.tableCallBack=function(){
					$scope.paramsExport = JSON.stringify($scope.query);
				}
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
			doSaveIsBlack:function(userId){
				$scope.black.userId = userId;
				var url = "cmUserInfoPullBO.ajax?cmd=isBlackPull";
				commonService.postUrl(url,$scope.black,function(data){
					if(data == "Y"){
						commonService.alert("拉黑成功！",function(){
							$scope.doQuery();
						});
					}
				});
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
				var url = "cmUserInfoPullBO.ajax?cmd=delUserInfoPull";
				commonService.confirm("确认删除用户【"+selectedDate[0].userName+"】",function(){
					commonService.postUrl(url,{"userId":userId},function(data){
						if(data !=undefined&&data != null && data != ""){
							commonService.alert("删除成功！",function(){
								$scope.doQuery();
							});
						}
					});
				})
			},
			verifyUserInfoPull:function(){
				var selectedDate = $scope.page.getSelectData();
				if(selectedDate == undefined || selectedDate.length == 0 || selectedDate.length > 1){
					commonService.alert("请选择一条数据！");
					return false;
				}
				var userId = selectedDate[0].userId;
				commonService.openTab("pull_"+userId+"_verify","修改拉包工信息",encodeURI("/cm/addCmUserInfoPull.html?userId="+userId+"&verify=1"));
			},
			seeUserInfoPull:function(){
				var selectedDate = $scope.page.getSelectData();
				if(selectedDate == undefined || selectedDate.length == 0 || selectedDate.length > 1){
					commonService.alert("请选择一条数据！");
					return false;
				}
				var userId = selectedDate[0].userId;
				commonService.openTab("pull_"+userId+"_see","查看拉包工信息",encodeURI("/cm/addCmUserInfoPull.html?userId="+userId+"&see=1"));
			}
	};
	userInfo.init();
}]);
