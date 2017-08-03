var billingCommon=angular.module("billingCommon", []);
var paramOrderId = getQueryString("orderId");
var orderNo = getQueryString("orderNo");
billingCommon.directive("billing", function () {
	var orderId;
	var trackingNum;
	var myBilling=
	{
	  restrict: 'E',
	  templateUrl : function(tElement,tAttrs){
      	return '../orders/ordDetail/billingTemplate.html?ver='+tAttrs.ver;
      },
      scope: {
          "initView":"=initView",
      },
	  compile: function(element, attrs){
		  if(paramOrderId!=undefined){
			  orderId = paramOrderId;
		  }
		  else
		  {
			  orderNo = orderNo;
		  }
	  },
	  controller: ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
		  var bill = {
					init:function(){
						this.bindEvent();
						this.userInfo();
						this.initView(orderId,orderNo);
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
					initView:function(orderId,orderNo){
						//-----页面展示后台显示数据逻辑
						//展示订单号
						var param={"orderId":orderId,"orderNo":orderNo};
						var datailUrl="";
						datailUrl="ordOrdersWebBO.ajax?cmd=ordDetails";
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