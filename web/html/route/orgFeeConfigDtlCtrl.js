var orgFeeConfigDtlApp = angular.module("orgFeeConfigDtlApp", ['commonApp']);
orgFeeConfigDtlApp.controller("orgFeeConfigDtlCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var orgObj=getQueryString("orgId");
	var ord ={
			init:function(){
			
				this.bindEvent();
				this.initStaticData();
				//this.doQuery();
			},
			bindEvent:function(){
				$scope.data = this.data;
				$scope.close = this.close;
				$scope.queryCost = this.queryCost;
				$scope.queryRoateRuting = this.queryRoateRuting;
				$scope.checkPrice = this.checkPrice;
				$scope.customParseInt = this.customParseInt;
				$scope.doQueryArea = this.doQueryArea;
				$scope.changeType = this.changeType;
				$scope.doQuery = this.doQuery;
			},
			
			orderInfo:{},
			//获取静态数据
			initStaticData:function(){
				/**网点*/
				
				$scope.orderInfo = eval('(' + orgObj + ')');
				$scope.all=true;
				var url = "staticDataBO.ajax?cmd=selectOrgByRole";
				commonService.postUrl(url,"",function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
						$scope.orgInfo=data;
						$scope.data.orgId = $scope.orderInfo.orgId;
		 	    	}
				});
				/**归属公司*/
				commonService.postUrl("staticDataBO.ajax?cmd=selectRootOrgByRole","",function(data){
					if(data != null && data != undefined && data != ""){
						$scope.tenantData = data;
						$scope.data.tenantId = $scope.orderInfo.tenantId;
					}
				});
				/**
				 * 费用科目
				 */
				var url = "staticDataBO.ajax?cmd=queryAcFeeConfig";
				commonService.postUrl(url,"",function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
						$scope.acFeeConfig=data;
						$scope.data.feeId = $scope.orderInfo.feeId;
					}
				});
				
				commonService.postUrl("staticDataBO.ajax?cmd=selectDeliveryType","",function(data){
					if(data != null && data != undefined && data != ""){
						$scope.deliveryTypeData = data;
						$scope.data.deliveryType = $scope.deliveryTypeData.items[0].codeValue;
					}
				});
				commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType","codeType=COLLECT_TYPE",function(data){
					if(data != null && data != undefined && data != ""){
						$scope.stateDate=data.items;
						$scope.data.collectType=$scope.orderInfo.collectType+"";
					}
				});
			},
			doQuery:function(){
					var url="orgFeeConfigManageBO.ajax?cmd=getOrgFeeDtl";
					commonService.postUrl(url,$scope.data,function(data){
						if(data != null && data != undefined && data != "" && data.numRows!=0){
							$scope.routeData=data.items;
							$scope.data.tenantId=$scope.routeData[0].tenantId;
							$scope.data.orgId=$scope.routeData[0].orgId;
							$scope.all=true;
						}
					});
			},
			data:{},
		    //关闭页面
			close:function(){
				commonService.closeToParentTab(true);
			},
			
	};
	ord.init();
}]);