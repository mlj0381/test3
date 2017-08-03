
/**
 * 发货人列表
 */
commonApp.directive("consignorShow",['commonService',"$timeout",function(commonService,$timeout){
	return {
		restrict: 'E',
        scope: {
            "orderId":"=orderId",
            "consignorChange":"=consignorChange"
        },
		templateUrl : function(tElement,tAttrs){
	      	return '/ord/ordDetail/consignorDtl.html?ver='+tAttrs.ver;
	    },
        link: function($scope, elem, attrs) {
        	
        },
        controller : [ "$scope", "commonService", "$timeout","$interval",
       				function($scope, commonService, $timeout,$interval) {
        	var consignorShow = {
        			init : function() {
						this.bindEvent();
						this.bindOut();
					},
					bindEvent : function() {
						$scope.load = this.load;
					},
					bindOut:function(){
						$scope.consignorChange={};
						$scope.consignorChange.load = this.load;
					},
					load:function(orderId){
		        		$scope.goodsInfo = [];
		        		if(orderId != null && orderId != undefined && orderId != ""){
		        			var getTimestamp=new Date().getTime();
		        			commonService.postUrl("ordOrdersWebBO.ajax?cmd=queryPickInfo&timestamp="+getTimestamp,{"orderId":orderId},function(data){
		        				$scope.goodsInfo=data;
		        			});
		        		}
					}
        	};
        	consignorShow.init();
        }]
	};
	return consignorShow;
}]);