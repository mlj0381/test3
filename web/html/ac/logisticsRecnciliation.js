function changeTabCallback(){
	var appElement = document.querySelector('[ng-controller=AcProveManageCtrl]');
	var scope=angular.element(appElement).scope();
	scope.doQuery();
	scope.$apply();
}
var proveManageApp = angular.module("AcProveManageApp", ['commonApp']);
proveManageApp.controller("AcProveManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var proveManage={
		init:function(){
			$scope.query = {};
			this.bindEvent();
			this.doQuery();
			this.selectZxOrg();
		},
		bindEvent:function(){
			$scope.query = this.query;
			$scope.doQuery = this.doQuery;
		    $scope.clear = this.clear;
		},
		
		query:{
			"page" : 1,
			"count" : 10
		},
		 //获取协议专线
		 selectZxOrg:function(){
			    var param = {};
			    param.type = 1;
				var url = "cmTrunkCostBO.ajax?cmd=doQueryMerchant";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
							$scope.orgIds = data.items;
							$scope.orgIds.unshift({orgId:"-1",orgName:"所有"});
							$scope.query.orgId = "-1";
		 	    	}
				});
		},
		

		/**列表查询*/
		doQuery:function(){
			$scope.query.page = 1;
			$scope.query.count = 10;
			$scope.query.beginDate = $("#beginDate").val();
			$scope.query.endDate = $("#endDate").val();
			$scope.param = {};
			$scope.param = $scope.query;
			var url = "acProveManageBO.ajax?cmd=queryBusinessOrgAcDtl";
			
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:$scope.param,
							callBack:"setContentHegthDelay"
						});
			},100);
		},
		/**清空查询条件*/
		clear:function(){
			$scope.query.checkSts="-1";
			$scope.query.trackingNum="";
			$scope.query.checkSts="-1";
			$scope.query.feeType="-1";
			$scope.query.beginDate = "";
			$scope.query.endDate = "";
			$scope.query.orgId = "-1";
			$scope.query.payType="-1";
		},
	};
	proveManage.init();
}]);
