var orderAddApp = angular.module("orderAddApp", ['commonApp']);
orderAddApp.controller("orderAddCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var orderAdd  ={
		init:function(){
			this.bindEvent();
		},
		bindEvent:function(){
			$scope.doQuery = this.doQuery;
		},
		//查询订单
		 doQuery : function(){
			 
		},
		 
	};
	orderAdd.init();
}]);
 