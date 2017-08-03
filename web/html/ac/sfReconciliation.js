function changeTabCallback(){
	var appElement = document.querySelector('[ng-controller=AcSfCtrl]');
	var scope=angular.element(appElement).scope();
	scope.doQuery();
	scope.$apply();
}
var AcZxApp = angular.module("AcSfApp", ['commonApp']);
AcZxApp.controller("AcSfCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var proveManage={
		init:function(){
			$scope.query = {};
			this.bindEvent();
			$scope.des={};
			this.doQuery();
			this.selectZxOrg();
			this.queryOrg();
		},
		bindEvent:function(){
			$scope.query = this.query;
			$scope.doQuery = this.doQuery;
		    $scope.clear = this.clear;
		    $scope.toDetailAllInfo = this.toDetailAllInfo;
		    $scope.queryOrg=this.queryOrg;
		},
		toDetailAllInfo: function(obj){
			commonService.openTab(obj.trackingNum,"运单详情","/ord/ordBillingDetail.html?view=1&trackingNum="+obj.trackingNum);
		},
		query:{
			"page" : 1,
			"count" : 10
		},
		/**网点列表查询*/
		queryOrg:function(){
			var beginOrgId = userInfo.orgId;
			commonService.postUrl("routeBO.ajax?cmd=getCurrRoute","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.orgInfo = data;
					if(data.items != null && data.items != undefined && data.items != ""){
						$scope.orgInfo.items.unshift({endOrgId:-1,endOrgName:'全部'});
					}
					$scope.query.distributionOrgId = -1;
				}
			});
		},
		 //获取协议专线
		 selectZxOrg:function(){
			    var param = {};
			    param.type = 2;
			    param._ALLEXPORT = 1;
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
			$scope.query.beginDate = $("#beginDistributionTime").val();
			$scope.query.beginDate = $("#endDistributionTime").val();
			$scope.query.beginDate = $("#beginDate").val();
			$scope.query.endDate = $("#endDate").val();
			$scope.query.endDateSign = $("#endDateSign").val();
			$scope.query.endDateSign = $("#endDateSign").val();
			$scope.query.provinceId = $scope.des.chooseCityId;
			$scope.query.cityId = $scope.des.chooseRegionId;
			$scope.query.countyId = $scope.des.chooseCountyId;
			$scope.query.streetId = $scope.des.chooseStreetId;
			$scope.param = {};
			$scope.param = $scope.query;
			var url = "acProveManageBO.ajax?cmd=querySfFeeDtl";
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:$scope.param
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
			$scope.query.beginDateSign = "";
			$scope.query.endDateSign = "";
			$scope.query.orgId = "-1";
			$scope.query.payType="-1";
			$scope.query.sfName = "";
			$scope.query.sfPhone = "";
			$scope.query.beginDistributionTime = "";
			$scope.query.endDistributionTime = "";
			$scope.query.tenantId ="-1";
			$scope.query.orderState = "-1";
			$scope.query.paymentType = "-1";
			 $scope.query.distributionOrgId=-1;
			 $scope.des.clear();
			$scope.query.consignorName = "";
		},
	};
	proveManage.init();
}]);
