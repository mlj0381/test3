var myApp = angular.module("CreditDtlApp", ['commonApp']);
myApp.controller("CreditDtlCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var myManage={
		init:function(){
			$scope.ca= {};
			$scope.cad= {};
			this.doQuery();
			$scope.starOn = this.starOn;
			$scope.close = this.close;
		},
		close:function(){
			commonService.closeToParentTab();
		},
		params:{
		},
		/**列表查询*/
		doQuery:function(){
    		var batchNum=getQueryString("batchNum");
    		if(batchNum==null||batchNum==undefined|| batchNum==''){
    			commonService.alert("师傅ID为空!");
    			return false;
    		}
			var param = {"userId":batchNum};
    		var url="creditManageBO.ajax?cmd=getDtlById";
			commonService.postUrl(url,param,function(data){
				$scope.ca= data.ca;
				$scope.cad= data.cad;
				$scope.starOn(data.ca.creditLevel);
			});
		},
		starOn:function(level){
			if(level==1){
				$(".one-star").addClass("on");
			}
			if(level==2){
				$(".one-star").addClass("on");
				$(".two-stars").addClass("on");
			}
			if(level==3){
				$(".one-star").addClass("on");
				$(".two-stars").addClass("on");
				$(".three-stars").addClass("on");
			}
			if(level==4){
				$(".one-star").addClass("on");
				$(".two-stars").addClass("on");
				$(".three-stars").addClass("on");
				$(".four-stars").addClass("on");
			}
			if(level==5){
				$(".one-star").addClass("on");
				$(".two-stars").addClass("on");
				$(".three-stars").addClass("on");
				$(".four-stars").addClass("on");
				$(".five-stars").addClass("on");
			}
		}
	};
	myManage.init();
}]);
