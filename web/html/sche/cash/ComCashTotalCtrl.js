var myApp = angular.module("GeneralApp", ['commonApp']);
myApp.controller("GeneralCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var myManage={
		init:function(){
			$scope.query = {};
			this.bindEvent();
			$scope.querySFPartners();
			$scope.doQuerySf();
		},
		bindEvent:function(){
			$scope.param = this.param;
			$scope.doQuery = this.doQuery;
			$scope.clear = this.clear;
			$scope.querySFPartners=this.querySFPartners;
			$scope.query=this.query;
			$scope.totalMoney=this.totalMoney;
			$scope.doQuerySf=this.doQuerySf;
			$scope.sf=this.sf;
			$scope.toDetailAllInfo=this.toDetailAllInfo;
		},
		toDetailAllInfo: function(obj){
			window.open("/ord/ordBillingDetail.html?view=1&trackingNum="+obj.bussId);
		},
		query:{
			
		},
		sf:{
			hasCash:"0",
			waitCash:"0",
			getCash:"0"
		},
		querySFPartners:function(){
			var param = {"isAll":"Y"};
			var url = "organizationBO.ajax?cmd=doQuerySFPartnersExc";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
						$scope.orgData=data;
						//myManage.query.tenantId=data.items[0].sfUserId;
	 	    	}
			});
		},
		params:{
		},
		/**列表查询*/
		doQuery:function(){
			myManage.params.doObjId=$scope.query.doObjId;
			myManage.params.doTel=$scope.query.doTel;
			myManage.params.state=$scope.query.state;
			myManage.params.signTimeBegin=$scope.query.signTimeBegin;
			myManage.params.signTimeEnd=$scope.query.signTimeEnd;
			myManage.doQuerySf();
		},
		/**列表查询*/
		doQuerySf:function(){
			myManage.params.doTel = $scope.query.doTel;
			myManage.params.doObjId=$scope.query.doObjId;
			if(myManage.params.doObjId>0){
				myManage.totalMoney(myManage.params.doObjId);
			}
			myManage.params.state = $scope.query.state;
			myManage.params.signTimeBegin = $scope.query.signTimeBegin;
			myManage.params.signTimeEnd = $scope.query.signTimeEnd;
			var url = "cashManageBO.ajax?cmd=doCompanyAccountTotalQuery";
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:myManage.params,
							callBack:'setContentHegthDelay'
						});
			},500);
		},
		/**清空查询条件*/
		clear:function(){
			$scope.query = {};
			$scope.query.state="-1";
		},
		totalMoney:function(serviceId){
			var param = {"serviceId":serviceId};
			var url = "cashManageBO.ajax?cmd=doQueryCom";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.sf=data;
				}
			});
		}
	};
	myManage.init();
}]);
