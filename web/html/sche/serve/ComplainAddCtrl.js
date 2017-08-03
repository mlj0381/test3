var myApp = angular.module("ApplicationApp", ['commonApp']);
myApp.controller("ApplicationCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var myManage={
		init:function(){
			$scope.query = {};
			$scope.add = {};
			$scope.audit = {};
			$scope.itemsSf=[];
			this.bindEvent();
			this.initQuery();
			setContentHeight();
		},
		bindEvent:function(){
			$scope.param = this.param;
		    $scope.doSave = this.doSave;
			$scope.close = this.close;
		},
		close:function(){
			commonService.closeToParentTab();
		},
		params:{
		},
		initQuery:function(){
    		var belongObjId = getQueryString("belongObjId");
    		var belongObjName = getQueryString("belongObjName");
    		if(belongObjName==null || belongObjName==undefined || belongObjName=="" || belongObjName=="null"){
    			belongObjName="";
    		}
    		$scope.add.companyName = belongObjName;
    		var sfUserId = getQueryString("sfUserId");
    		var orderId = getQueryString("orderId");
			var param = {"tenantId":belongObjId,"orderId":orderId};
			var url = "cmSfUserInfoBO.ajax?cmd=queryByOrderIdTask";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.itemsSf = data.items;
					$timeout(function(){
						$scope.add.bcSfUserId = sfUserId;
					},1000);
					
				}
			});
		}
		,
		doSave:function(){
    		var batchNum=getQueryString("batchNum");
    		if(batchNum==null||batchNum==undefined|| batchNum==''){
    			commonService.alert("运单号为空!");
    			return false;
    		}
    		myManage.params.relateOrder = getQueryString("wayBillId");
			myManage.params.level = selectStar;
			myManage.params.sourceType = $scope.add.sourceType;
			myManage.params.complaintContent = $scope.add.complaintContent;
			myManage.params.bcSfUserId = $scope.add.bcSfUserId;
			myManage.params.state = $scope.add.state;
			myManage.params.id = getQueryString("id");
			console.log(myManage.params.id);
			var url = "serveBO.ajax?cmd=doSave";
			commonService.postUrl(url,myManage.params,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("处理成功!");
					$scope.close();
				}
			});
		}
	};
	myManage.init();
}]);
