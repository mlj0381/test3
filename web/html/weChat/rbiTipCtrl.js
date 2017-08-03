var rbiTipeApp = angular.module("rbiTipeApp", ['commonApp']);
//QR_CODE_PRE=201703070044
var orderId = getQueryString("NQR_");
var url = "";
rbiTipeApp.controller("rbiTipeCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.query={};
	$scope.goods={};
	var fee={
			init:function(){
				this.bindEvent();
				this.doQuery();
				this.isIOS();
			},
			bindEvent:function(){
				$scope.doQuery = this.doQuery;
				$scope.otherInfo = this.otherInfo;
				$scope.isIOS = this.isIOS;
			},
			doQuery:function(){
				var url = "ordOrdersWebBO.ajax?cmd=getOrdersInfo";
				commonService.postUrl(url,{orderId:orderId},function(data){
					$scope.query = data.orders;
					$scope.query.ordersQrCodeUrl = data.orders.ordersQrCodeUrl;
					$scope.goods = [];
					$scope.goods.push(data.goods[0]);
					$scope.goodsList = data.goods;
				});
			},
			otherInfo:function(num){
				if(num == 1){
					$scope.isShow = false;
					$scope.goods = $scope.goodsList;
				}else if(num == 2){
					$scope.isShow = true;
					$scope.goods = [];
					$scope.goods.push($scope.goodsList[0]);
				}
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
