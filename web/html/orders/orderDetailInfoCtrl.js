/*var orderId = getQueryString("orderId");*/
var orderNo = getQueryString("orderNo");
var orderId = getQueryString("orderId");
var signUp = getQueryString("signUp");
var isShowReturn = getQueryString("isShowReturn");
var type = getQueryString("type");
var orderDetailInfoApp = angular.module("orderDetailInfoApp", ['commonApp','angucomplete','ngTouch','billingCommon']);
orderDetailInfoApp.controller("orderDetailInfoCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.orderNo = orderNo;
	$scope.signUp = signUp;
	$scope.orderId=orderId;
	$scope.type=type;
	var detail = {
		init:function(){
			$scope.toInitView = this.toInitView;
			$scope.initView = this.initView;
			if(isShowReturn == "false" || isShowReturn == false){
				$scope.isShowReturn = false;
			}else{
				$scope.isShowReturn = true;
			}
			$scope.isShowTab = this.isShowTab;
			$scope.showSign= false;
			$scope.userInfo=this.userInfo;
			$scope.userTypeInfo=this.userTypeInfo;
			$scope.userInfo();
			$scope.close = this.close;
			$scope.selectDate=this.selectDate;
		},
		close : function(){
			commonService.closeToParentTab(true);
		},
		selectDate:function(){
			
		},
		toInitView : function(){
			if($scope.selectDate.orderNo != undefined && $scope.selectDate.orderNo != '' && $scope.selectDate.orderNo != null){
					$scope.orderNo = $scope.selectDate.orderNo+""; //值改变
					$scope.initView("",$scope.orderNo);
					$scope.userInfo();
			}
		},
		initView : function(o1,o2){
			//回调标签的方法
		},
		userInfo:function(){
			$scope.userData=userInfo;
			$scope.userTypeInfo();
		},
	/**用户类型*/
		userTypeInfo:function(){
			$scope.userTypeData=userType;
		}
	};
	$scope.$watch('$viewContentLoaded', function() {  
		$timeout(function(){
			setContentHegthDelay();
		},500);
	}); 
	detail.init(); 
}]);
