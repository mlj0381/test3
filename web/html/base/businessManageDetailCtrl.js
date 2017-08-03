var businessManageDetailApp = angular.module("businessManageDetailApp", ['commonApp']);
businessManageDetailApp.controller("businessManageDetailCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var businessManage={
		init:function(){
			this.bindEvent();
			this.orgByIdQuery();
			this.selectServe();
			this.linkBus();
		},
		bindEvent:function(){
			$scope.user = this.user;
			$scope.user.tenantId = getQueryString("tenantId");
		},
		user:{
			businessType:3,
		},
		orgByIdQuery:function(){
			var url = "organizationBO.ajax?cmd=queryByIdBus";
			var dataLine = $scope.user.tenantId;
			var p = {};
			p.tenantId = dataLine;
			commonService.postUrl(url,p,function(data){
				  $scope.user = data;
				  $scope.user.businessType = parseInt(data.businessType);
			});
		},
		selectServe:function(){
			var param = {};
			var url = "staticDataBO.ajax?cmd=getOrdSellerServeType";
			commonService.postUrl(url,param,function(user){
				// 成功执行
				if(user!=null && user!=undefined && user!=""){
					$scope.roleInfos=user;
	 	    	}
			});
		},
		linkBus:function(){
			var url = "organizationBO.ajax?cmd=queryBusByLink";
			var dataLine = $scope.user.tenantId;
			var p = {};
			p.tenantId = dataLine;
			commonService.postUrl(url,p,function(data){
				  $scope.provinceArrays = data;
			});
		},
	};
	businessManage.init();
}]);
