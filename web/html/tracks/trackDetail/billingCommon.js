var billingCommon=angular.module("billingCommon", []);
var paramOrderId = getQueryString("orderId");
var orderNo = getQueryString("orderNo");
var trackingNum =  getQueryString("trackingNum");
billingCommon.directive("billing", function () {
	var myBilling=
	{
	  restrict: 'E',
	  templateUrl : function(tElement,tAttrs){
      	return '../tracks/trackDetail/billingTemplate.html?ver='+tAttrs.ver;
      },
      scope: {
          "initView":"=initView",
      },
	  compile: function(element, attrs){
		  if(orderNo!=undefined){
			  orderNo = orderNo;
		  }
		  else 
		  {
			  trackingNum = trackingNum;
		  } 
	  },
	  controller: ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
		  var bill = {
					init:function(){
						this.bindEvent();
						this.userInfo();
						this.initView(orderNo,trackingNum);
					},
					userInfo:function(){
						$scope.userData=userInfo;
						$scope.userTypeInfo();
					},
				/**用户类型*/
					userTypeInfo:function(){
						$scope.userTypeData=userType;
					},
					bindEvent:function(){
						$scope.userTypeInfo=this.userTypeInfo;
						$scope.userInfo = this.userInfo;
						$scope.initView = this.initView;
					},
					//查看初始化
					initView:function(orderNo,trackingNum){
						//-----页面展示后台显示数据逻辑
						//展示订单号和运单号
						var param={"orderNo":orderNo,"trackingNum":trackingNum,"paramOrderId":paramOrderId};
						var datailUrl="";
						datailUrl="ordOrdersWebBO.ajax?cmd=ordInfoDetails";
						commonService.postUrl(datailUrl,param,function(data){
							if(data != null && data != undefined && data != ""){
								$scope.orderInfo=data;
							}
						});
					}
				};
				bill.init(); 
	  }]
 };
return myBilling;
});