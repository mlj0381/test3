var departApp = angular.module("departYQApp", ['commonApp']);
departApp.controller("departYQCtrl", ["$scope","commonService","$timeout","$sce",function($scope,commonService,$timeout,$sce) {
	var batchNum = getQueryString("batchNum");
	var depart={
			init:function(){
				this.bindEvent();
				this.queryDepart(batchNum);
			},
			bindEvent:function(){
			  
			},
			queryDepart:function(batchNum){
				var url = "ordDepartInfoYqBO.ajax?cmd=getOrdDepartInfoWeb";
				commonService.postUrl(url,{"batchNum":batchNum},function(data){
					if (data!=null&&data!="") {
						$scope.departDetail = data;
						$scope.pageData.clearAllCheckbox();
						$scope.pageData.loadData(data.childList);
						window.top.hideLoad();
					    setContentHegthDelay();
					}
				});
			}
	};
    depart.init();
}]);
     