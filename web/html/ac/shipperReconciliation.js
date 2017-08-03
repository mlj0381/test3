function changeTabCallback(){
	var appElement = document.querySelector('[ng-controller=AcZxCtrl]');
	var scope=angular.element(appElement).scope();
	scope.doQuery();
	scope.$apply();
}
var AcZxApp = angular.module("AcZxApp", ['commonApp']);
AcZxApp.controller("AcZxCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
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
		    $scope.toDetailAllInfo = this.toDetailAllInfo;
		},
		toDetailAllInfo: function(obj){
			commonService.openTab(obj.trackingNum,"运单详情","/ord/ordBillingDetail.html?view=1&trackingNum="+obj.trackingNum);
		},
		query:{
			"page" : 1,
			"count" : 10
		},
		 //获取协议商家
		 selectZxOrg:function(){
			    var param = {};
			    param.type = 2;
			    param._ALLEXPORT = 1;
				var url = "cmTrunkCostBO.ajax?cmd=doQueryMerchant";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
							$scope.orgIds = data.items;
							$scope.orgIds.unshift({tenantId:"-1",orgName:"所有"});
							$scope.query.tenantId = "-1";
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
			var url = "acProveManageBO.ajax?cmd=queryZxOrgAcDtl";
			$scope.tableCallBack=function(){
//				if ($scope.page.data.items == undefined || $scope.page.data.items.length == 0) {// 没有数据，指定一个默认高度
//					setContentHeightWithSpecialHeight(683);
//				} else {// 有数据，则根据数据行计算高度
//					var height = (683 - 270) + $scope.page.data.items.length * 48 ;
//					height = height <= 683 ? 683 : height;
//					setContentHeightWithSpecialHeight(height);
//				}
			};
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:$scope.param,
							callBack:"$scope.tableCallBack"
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
			$scope.query.tenantId = "-1";
		},
	};
	proveManage.init();
}]);
