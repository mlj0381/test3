var myApp = angular.module("ApplicationApp", ['commonApp']);
myApp.controller("ApplicationCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var myManage={
		init:function(){
			$scope.query = {};
			$scope.add = {};
			$scope.audit = {};
			this.bindEvent();
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
    		$scope.add.companyName = belongObjName;
			var param = {"tenantId":belongObjId};
			var url = "cmSfUserInfoBO.ajax?cmd=queryUserInfo";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.add.items = data.items;
				}
			});
		},
		doSave:function(){
    		var wayBill=getQueryString("wayBill");
    		if(wayBill==null||wayBill==undefined|| wayBill==''){
    			commonService.alert("运单号为空!");
    			return false;
    		}
    		$scope.add.wayBill=wayBill;
    		$scope.add.level = selectStar;
			$scope.add.id=getQueryString("id");
			var url = "serveBO.ajax?cmd=doVisitSave";
			commonService.postUrl(url,$scope.add,function(data){
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
