var addBillingRecDelSysApp = angular.module("addBillingRecDelSysApp", ['commonApp']);
addBillingRecDelSysApp.controller("addBillingRecDelSysCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var flowId = getQueryString("flowId");
	var tow={
			init:function(){
				this.bindEvent();
				if(flowId != undefined && flowId != null && flowId != ""){
					this.doQueryCmId();
				}
				this.selectAllSys();
			},
			bindEvent:function(){
				$scope.data = this.data;
				$scope.doSave = this.doSave;
				$scope.close = this.close;
			},
			data:{
				codeValue:-1
			},
			doQueryCmId:function(){
				var url = "orderInfoBO.ajax?cmd=doQuerySysSDById";
				var dataLine = getQueryString("flowId");
				var p = {};
				p.flowId = dataLine;
				commonService.postUrl(url,p,function(data){
					  console.log(data);
					  $scope.data = data;
				});
			},
			selectAllSys:function(){
				var url = "orderInfoBO.ajax?cmd=queryAllSysSD";
				var p = {};
				commonService.postUrl(url,p,function(data){
					  $scope.roleInfos = data;
					  if(data != null && data!= undefined && data != ""){
							$scope.roleInfos.items.unshift({codeValue:-1,codeName:"请选择"});
					  }
					  console.log($scope.roleInfos);
					  
				});
			},
			doSave:function(){
				if($scope.data.sortId==null || $scope.data.sortId==undefined || $scope.data.sortId==""){
					commonService.alert("请填排序！");
					return false;
				}
				if($scope.data.codeValue == -1 ||$scope.data.codeValue==null ||$scope.data.codeValue==undefined || $scope.data.codeValue==""){
					commonService.alert("请选择交接方式！");
					return false;
				}
				commonService.postUrl("orderInfoBO.ajax?cmd=doSaveSysSD",$scope.data,function(data){
				    commonService.alert("保存完成!");
				    $scope.close();
				});
			},
			close:function(){
				commonService.closeToParentTab(true);
			},
	};
	tow.init();
}]);
