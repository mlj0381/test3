var shareOrderApp = angular.module("shareOrderApp", ['commonApp']);
//QR_CODE_PRE=201703070044
var url = "";
var orderNo = getQueryString("NQR_");
shareOrderApp.controller("shareOrderCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.order={};
	var fee={
			init:function(){
				this.bindEvent();
				this.doQuery();
				this.isIOS();
			},
			bindEvent:function(){
				$scope.doQuery = this.doQuery;
				$scope.isIOS = this.isIOS;
			},
			doQuery:function(){
				var url = "ordOrdersWebBO.ajax?cmd=getOrdersByOrderNo";
				commonService.postUrl(url,{"orderNo":orderNo},function(data){
					$scope.order = data;
				});
			},
			isIOS:function(){
				var u = navigator.userAgent;
				var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
				var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
		        if (isAndroid) {
		        	appType = 2;
				}else if (isiOS) {
					appType = 1;
				}else {
					appType = 2;
				}
		        commonService.postUrl("staticDataBO.ajax?cmd=getAppUrl",{appType:isiOS == true ? 1 : 0},function(data){
		        	if (data!=null&&data!="") {
		        		url = data;
					}
		        });
			}
	};
	fee.init();
}]);

function openApp(){
	 window.open(url);
}