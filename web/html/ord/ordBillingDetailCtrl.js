var autoComplete = null;
var view = getQueryString("view");//view=8 中转管理那边跳转过来的详情
/*var orderId = getQueryString("orderId");*/
var trackingNum = getQueryString("trackingNum");
var orderId = getQueryString("orderId");
var orgId = getQueryString("orgId");/** 分拨中心的人进来查看详情，需要做特殊控制，例如：无法查看费用等*/
var type = getQueryString("type");
var isShowReturn = getQueryString("isShowReturn");
var ordBillingDetailApp = angular.module("ordBillingDetailApp", ['commonApp','angucomplete','ngTouch','billingCommon']);
ordBillingDetailApp.controller("ordBillingDetailCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.trackingNum = trackingNum;
	//$scope.initView = function(){alert('a');};
	var detail = {
		init:function(){
			$scope.createDateDetail="";
			$scope.orgNameDetail = "";
			$scope.inputUserNameDetail = "";
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
			
		},
		close : function(){
			commonService.closeToParentTab(true);
		},
		toInitView : function(){
			if($scope.selectDate.trackingNum != undefined && $scope.selectDate.trackingNum != '' && $scope.selectDate.trackingNum != null){
					$scope.trackingNum = $scope.selectDate.trackingNum+""; //值改变
					$scope.initView("",$scope.trackingNum);
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
		},
		isShow :{
			isShow1:true,
			isShow2:false,
			isShow3:false,
			isShow4:false,
			isShow5:false,
		},
		isShowTab : {
			isShow1:true,
			isShow2:false,
			isShow3:false,
			isShow4:false,
			isShow5:false,
			isShow6:false,
			isShow7:false,
			isShow8:false
		},
	};
	$scope.$watch('$viewContentLoaded', function() {  
		$timeout(function(){
			setContentHegthDelay();
		},500);
	}); 
	detail.init(); 
}]);
