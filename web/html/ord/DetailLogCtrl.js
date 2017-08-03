var orderId = getQueryString("orderId");
var billApp = angular.module("billApp", ['commonApp']);
billApp.controller("billCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var bill = {
			init:function(){
				this.bindEvent();
				this.queryLog();
			},
			bindEvent:function(){
			},
			//查询订单日志
			queryLog:function(){
				commonService.postUrl("orderInfoBO.ajax?cmd=queryOrderDetailLog",{"trackingNum":orderId},function(data){
					$scope.ordLogInfos=data.items;
				});
			},
	};
	bill.init();
}]);
