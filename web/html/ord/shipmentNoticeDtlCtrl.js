var shipmentNoticeDtlApp = angular.module("shipmentNoticeDtlApp", ['commonApp']);
shipmentNoticeDtlApp.controller("shipmentNoticeDtlCtrl", ["$scope","commonService",function($scope,commonService) {
	$scope.ordNotify={};
	var ordSign={
			init:function(){
				this.bindEvent();
				this.doQuery();
			},
			bindEvent:function(){
				$scope.ordNum=this.ordNum;
				$scope.doQuery=this.doQuery;
				$scope.orderNotify=this.orderNotify;
				$scope.doSaveOrderNotify=this.doSaveOrderNotify;
				$scope.closePage=this.closePage;
			},
			ordNum:{
				num:0,
			},
			orderNotify:{
				orderId:"",
			},
			doQuery:function(){
				var orderId = getQueryString("orderId");
				$scope.orderNotify.orderId=orderId;
				var url = "orderInfoBO.ajax?cmd=queryshiptmentNotice";
				commonService.postUrl(url,$scope.orderNotify,function(data){
					if(data!=null && data!="" && data!=undefined){
						$scope.notify=data;
						$scope.myFile.initDate(data.imageId); 
						$scope.ordNotify.sourceInformation=data.sourceInformation; 
					}
					
				});
			},
			doSaveOrderNotify:function(){
				var url = "orderInfoBO.ajax?cmd=doSaveOrderNotify";
				$scope.ordNotify.orderId=$scope.notify.orderId;
				
				if($scope.ordNotify.orderId==null || $scope.ordNotify.orderId=="" || $scope.ordNotify.orderId==undefined){
					commonService.alert("请选择一条订单");
					return;
				}
				if($scope.ordNotify.sourceInformation==null || $scope.ordNotify.sourceInformation==undefined || $scope.ordNotify.sourceInformation==""){
					commonService.alert("请输入信息来源");
					return;
				}
				$scope.ordNotify.imagePath=$scope.myFile.get().flowUrl;
				$scope.ordNotify.imageId=$scope.myFile.get().flowId;
				var params =$scope.ordNotify;
				commonService.postUrl(url,params,function(data){
					if(data!=null && data!="" && data!=undefined){
						commonService.alert("保存成功");
						$scope.closePage();
					}
					
				});
			},
			closePage:function(){
				//关闭一个页面并回到父页面
					commonService.closeToParentTab(true);
		  },
	};
	ordSign.init();
}]);