var dedicatedQueryApp = angular.module("dedicatedQueryApp", ['commonApp']);
dedicatedQueryApp.controller("dedicatedQueryCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var ord ={
		init:function(){
			commonService.alert("dffasd");
			$scope.newAdd = this.newAdd,
			this.bindEvent();
			
		},
		bindEvent:function(){
			
			
		},
		newAdd:function(){
			commonService.openTab("10001","新增专线","/base/dedicatedQueryAdd.html");
		}
	};
	ord.init();
}]);