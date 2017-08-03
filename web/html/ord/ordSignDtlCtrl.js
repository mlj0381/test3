var ordSignDtlApp = angular.module("ordSignDtlApp", ['commonApp']);
ordSignDtlApp.controller("ordSignDtlCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.ordNotify;
	$scope.notify={};
	$scope.orderNotify={};
	$scope.signTypeList=[];
	$scope.certificateTypeList=[];
	var ordSign={
			init:function(){
				this.bindEvent();
				this.doQuery();
			},
			bindEvent:function(){
				$scope.doQuery=this.doQuery;
				$scope.doSaveOrdSign=this.doSaveOrdSign;
				$scope.closePage=this.closePage;
			},
			doQuery:function(){
				var orderId = getQueryString("orderId");
				$scope.orderNotify.orderId=orderId;
				var url = "orderInfoBO.ajax?cmd=querySignDtl";
				$timeout(function(){
				commonService.postUrl(url,$scope.orderNotify,function(data){
					if(data!=null && data!="" && data!=undefined){
						$scope.notify=data;
						commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{"codeType":"SIGN_TYPE"},function(data1){
							if(data.signType!=null && data.signType!=undefined && data.signType!=""){
								$scope.notify.signType=data.signType;
							}else{
								$scope.notify.signType=1;
							}
							$scope.signTypeList=data1.items;
						});
						commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{"codeType":"CERTIFICATE_TYPE"},function(data1){
							if(data.certificateType!=null && data.certificateType!=undefined && data.certificateType!=""){
								$scope.notify.certificateType=data.certificateType;
							}else{
								$scope.notify.certificateType=-1;
							}
							$scope.certificateTypeList=data1.items;
						});
						if(data.imageId!=null && data.imageId!=undefined && data.imageId!=""){
						  $scope.myFile.initDate(data.imageId);
						}
						if($scope.notify.collectingMoney==undefined){
							$scope.notify.collectingMoney=0;
						}
					}
					setContentHegthDelay();
				});
				},200);
			},
			doSaveOrdSign:function(){
				if($scope.notify.freightCollect/100>0){
					if(document.getElementById("check-3").checked == true){
						$scope.notify.isPay = 1;
					}else{
						$scope.notify.isPay = 2;
					}
				}
				if($scope.notify.collectingMoney/100>0){
					if(document.getElementById("check-4").checked == true){
						$scope.notify.isGet = 1;
					}else{
						$scope.notify.isGet = 2;
					}
				}

				$scope.notify.imagePath=$scope.myFile.get().flowUrl;
				$scope.notify.imageId=$scope.myFile.get().flowId;
				var url = "orderInfoBO.ajax?cmd=doSaveOrdSign";
				//$scope.ordNotify.orderId=orderId;
				 // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X 
//				if($scope.notify.certificateType!=null && $scope.notify.certificateType!=undefined && $scope.notify.certificateType!=""){
//					if($scope.notify.certificateNumber==null || $scope.notify.certificateNumber==""){
//						commonService.alert("请输入证件号码");  
//					       return;  
//					}
//					if($scope.notify.certificateType==1 && $scope.notify.certificateNumber!=null && $scope.notify.certificateNumber!=undefined){
//						var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/; 
//						   if(reg.test($scope.notify.certificateNumber) === false)  
//						   {  
//							   commonService.alert("身份证输入不合法");  
//						       return;  
//						   } 
//					}
//				}
				    
				var params =$scope.notify;
				window.top.showLoad();
				commonService.postUrl(url,params,function(data){
					if(data!=null && data!="" && data!=undefined){
						window.top.hideLoad();
						commonService.alert("签收完成");
						//$scope.notify=data;
						ordSign.closePage();
					}
					
				},function(response){
					window.top.hideLoad();
					commonService.alert(response.message);
				});
			},
			closePage:function(){
				//关闭一个页面并回到父页面
					commonService.closeToParentTab(false);
		  },
	};
	$scope.$watch('$viewContentLoaded', function() {
		ordSign.init();
	});
}]);