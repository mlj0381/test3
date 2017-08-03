var cooperationLinkManageApp = angular.module("cooperationLinkManageApp", ['commonApp']);
cooperationLinkManageApp.controller("cooperationLinkManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var ord ={
		init:function(){
			this.bindEvent();
			this.doQuery();
		},
		bindEvent:function(){
			$scope.data = this.data;
			$scope.clear = this.clear;
			$scope.doQuery = this.doQuery;
		},
		data:{
			page:1,
			rows: 10
		},
		doQuery:function(){
			var url = "organizationBO.ajax?cmd=doQuerySFLink";
			$scope.data.page = 1;
			
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:$scope.data,
							callBack:"setContentHegthDelay"
						});
			},500);
		},
		clear:function(){
			$scope.doQuery();
			$scope.data.orgName = "";
		},
	};
	ord.init();
}]);