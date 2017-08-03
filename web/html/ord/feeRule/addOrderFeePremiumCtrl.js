var addOrderFeePremiumApp = angular.module("addOrderFeePremiumApp", ['commonApp',"tableCommon"]);
addOrderFeePremiumApp.controller("addOrderFeePremiumCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.save ={};
	var premiumId = commonService.getQueryString("premiumId");
	var isSee = commonService.getQueryString("isSee");
	var fee={
			init:function(){
				this.bindEvent();
				this.selectCompany();//获取公司数据
				if(premiumId != undefined && premiumId != null && premiumId != ""){
					if(isSee != undefined && isSee != null && isSee != ""){
						$scope.isSee = isSee;
					}
					this.getOrderFeeRuleData(premiumId);
				}
				this.queryCompany();
			},
			bindEvent:function(){
				$scope.doSave=this.doSave;
				$scope.close = this.close;
				$scope.selectCompany = this.selectCompany;//获取公司数据
				$scope.getOrderFeeRuleData = this.getOrderFeeRuleData;
				$scope.queryCompany = this.queryCompany;
			},
			queryCompany:function(tenantId,beginOrgId,endOrgId){
				var that=this;
				//查询公司下面的所有的网点
				$scope.save.beginOrgId = "";
				
				$scope.save.endOrgId = "";
				commonService.postUrl("organizationBO.ajax?cmd=queryOrgList",{"tenantId":tenantId},function(data){
					if(data != null && data != undefined && data != ""){
						$scope.orgInfo = data;
						if(beginOrgId!=undefined){
							$scope.save.beginOrgId=beginOrgId;
						}
						if(endOrgId!=undefined){
							$scope.save.endOrgId=endOrgId;
						}
					}
				});
			},
			doSave:function(){
				
				if($scope.save.tenantId == undefined || $scope.save.tenantId == null || $scope.save.tenantId == ""){
					commonService.alert("请选择归属专线！");
					return;
				}
				if($scope.save.beginOrgId == undefined || $scope.save.beginOrgId == null || $scope.save.beginOrgId == ""){
					commonService.alert("请选择起始网点！");
					return;
				}
				if($scope.save.endOrgId == undefined || $scope.save.endOrgId == null || $scope.save.endOrgId == ""){
					commonService.alert("请选择目的网点！");
					return;
				}
				if($scope.save.numberFeeString == undefined || $scope.save.numberFeeString == null || $scope.save.numberFeeString == ""){
					commonService.alert("请输入按件计算！");
					return;
				}
				var url = "orderFeeRuleBO.ajax?cmd=doSavePremium";
				commonService.postUrl(url,$scope.save,function(data){
					if(data == "Y"){
						commonService.alert("保存成功！",function(){
							$scope.close();
						});
					}
				});
			},
			close:function(){
				commonService.closeToParentTab(true);
			},
			selectCompany:function(){
				if(userInfo.userType == 1){
					var url = "organizationBO.ajax?cmd=selectOrgByLink";
					commonService.postUrl(url,"",function(data){
						$scope.curOrgData = data;
						$scope.save.tenantId = data.items[0].tenantId+"";
						//$scope.queryCompany($scope.save.tenantId);
					});
				}else{
					var url = "staticDataBO.ajax?cmd=selectCurTenantId";
					commonService.postUrl(url,"",function(data){
						$scope.curOrgData = data;
						$scope.save.tenantId = data.items[0].tenantId+"";
					});
				}
			},
			getOrderFeeRuleData:function(id){
				var url = "orderFeeRuleBO.ajax?cmd=getOrderFeeTipOut";
				commonService.postUrl(url,{"id":id},function(data){
					var feeRule = data.feeRule;
					$scope.save = feeRule;
					$scope.save.tenantId = feeRule.tenantId+"";
					$scope.queryCompany($scope.save.tenantId,feeRule.beginOrgId,feeRule.endOrgId);
				});
			}
	};	
	fee.init();
}]);
