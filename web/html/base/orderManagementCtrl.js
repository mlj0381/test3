var orderApp = angular.module("orderApp", ['commonApp']);
orderApp.controller("orderCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var order ={
		init:function(){
			this.bindEvent();
		},
		bindEvent:function(){
			$scope.doQuery = this.doQuery;
			$scope.add = this.add;
		},
		//查询订单
		 doQuery : function(){
			 
		},
		//跳转到新增页面
		add : function(obj){
			if(obj == 1){
				commonService.openTab("1314151"+obj,"新增订单","/base/orderManagementAdd.html?is=1");
			}else{
				commonService.openTab("1314151"+obj,"修改订单","/base/orderManagementAdd.html?is=2");
			}
		
		},
	};
	order.init();
}]);
 