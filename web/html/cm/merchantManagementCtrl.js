var merchantManagementApp = angular.module("merchantManagementApp", ['commonApp','tableCommon']);
merchantManagementApp.controller("merchantManagementCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	$scope.query = {};
	var ord ={
			init:function(){
				this.bindEvent();
			},
			 head:[
             {"name":"商户名称","code":"userName"},
             {"name":"联系号码","code":"loginAcct"},
             {"name":"地址","code":"address"},
             {"name":"注册时间","code":"createTimeString"}
             ],
			bindEvent:function(){
				$scope.url = "cmUserInfoBO.ajax?cmd=queryMerchan";
				$scope.head = ord.head;
				$scope.urlParam = $scope.query;
				$scope.doQuery = this.doQuery;
				$scope.clear = this.clear;
			},
			query:{
				page: 1,
				rows: 10
			},
			doQuery:function(){
				var url = "cmUserInfoBO.ajax?cmd=queryMerchan";
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
			clear:function(){
				$scope.doQuery();
				$scope.query.name = "";
				$scope.query.bill = "";
			}
		};
		ord.init();
}]);