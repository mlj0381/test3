var notifyDtlApp = angular.module("notifyDtlApp", ['commonApp','billingCommon']);
notifyDtlApp.controller("notifyDtlCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.showPcum = false;
	var number=0;
	$scope.ordNotify={};
	var search={
		init:function(){
			this.bindEvent();
			setContentHegthDelay();
			this.doQuery();
			
		},
		bindEvent:function(){
			$scope.ordNum=this.ordNum;
			$scope.doQuery=this.doQuery;
			$scope.orderNotify=this.orderNotify;
			$scope.hasNext=this.hasNext;
			$scope.single=this.single;
			$scope.doSaveNotify=this.doSaveNotify;
			$scope.doQueryVehile=this.doQueryVehile;
			$scope.showTotrue=this.showTotrue;
			$scope.showToFalse=this.showToFalse;
			$scope.params=this.params;
			$scope.selectVehicle=this.selectVehicle;
			$scope.closePage=this.closePage;
		},
		ordNum:{
			num:0,
		},
		orderNotify:{
			orderId:"",
		},
		params:{
			vehicleState:1
		},
		doQuery:function(){
			var orderId = getQueryString("orderId");
			$scope.orderNotify.orderId=orderId;
			var url = "orderInfoBO.ajax?cmd=queryNotifyDtl";
			commonService.postUrl(url,$scope.orderNotify,function(data){
				if(data!=null && data!="" && data!=undefined){
					$scope.notify=data;
					setContentHeigth();
				}
			});
		},
		//下一单
		hasNext:function(){
			$scope.ordNum.num=$scope.ordNum.num+1;
			if($scope.ordNum.num>=number){
				commonService.alert("已没有数据");
				$scope.ordNum.num=number-1;
				return;
			};
			$scope.doQuery();
		},
		//上一单
		single:function(){
			$scope.ordNum.num=$scope.ordNum.num-1;
			if($scope.ordNum.num<0){
				commonService.alert("已到首条数据");
				$scope.ordNum.num=0;
				return;
			};
			$scope.doQuery();
		},
		//预约签收录入
		doSaveNotify:function(orderId){
			var url = "orderInfoBO.ajax?cmd=doSaveOrdNotify";
			var params =$scope.notify;
			commonService.postUrl(url,params,function(data){
				if(data!=null && data!="" && data!=undefined){
					$scope.notify.collectingMoney=0;
					$scope.notify.freightCollect=0;
					commonService.alert("预约成功");
					commonService.closeToParentTab(false);
				}
			});
		},
         
 		/**车辆列表查询*/
 		doQueryVehile:function(){
 			$scope.params.plateNumber=$scope.notify.platenumber;
 			$scope.params.descOrgId=$scope.notify.distributionOrgId;
 			var url = "vehicleInfoBO.ajax?cmd=doQuery";
			$scope.page.load({
				url:url,
				params:$scope.params
			});
 			$scope.showPcum = true;
 		},
 		//设置发货方
		selectVehicle:function(obj){
			$scope.notify.driverPhone = obj.bill;
			$scope.notify.vehicleId = obj.vehicleId;
			$scope.notify.platenumber = obj.plateNumber;
			$scope.notify.driverName=obj.name;
			$scope.showToFalse();
		},
         
       //隐藏div
 		showToFalse:function(){
			$scope.showPcum = false;
			$scope.showRcum = false;
 		},
 		showTotrue:function(){
			$scope.doQueryVehile();
			$scope.showPcum = true;
		},
		closePage:function(){
		//关闭一个页面并回到父页面
			commonService.closeToParentTab(false);
		}
	};
	$scope.$watch('$viewContentLoaded', function() {
		search.init();
		
	});
}]);
