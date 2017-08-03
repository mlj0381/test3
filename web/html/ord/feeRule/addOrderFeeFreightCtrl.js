var addOrderFeeFreightApp = angular.module("addOrderFeeFreightApp", ['commonApp',"tableCommon"]);
addOrderFeeFreightApp.controller("addOrderFeeFreightCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.save ={};
	var freightId = commonService.getQueryString("freightId");
	var isSee = commonService.getQueryString("isSee");
	var fee={
			init:function(){
				this.bindEvent();
				this.selectCompany();//获取公司数据
				if(freightId != undefined && freightId != null && freightId != ""){
					if(isSee != undefined && isSee != null && isSee != ""){
						$scope.isSee = isSee;
					}
					this.getOrderFeeRuleData(freightId);
				}
				this.queryCompany();
			},
			bindEvent:function(){
				$scope.doSave=this.doSave;
				$scope.addOrDelFeeRule = this.addOrDelFeeRule;
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
				if($scope.save.minFeeString == undefined || $scope.save.minFeeString == null || $scope.save.minFeeString == ""){
					commonService.alert("请输入最低一票！");
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
				var minWeightFee  = false;
				var maxWeightFee = false;
				if($scope.save.minWeightFeeString != undefined && $scope.save.minWeightFeeString != null && $scope.save.minWeightFeeString != ""){
					if($scope.save.maxWeightFeeString == undefined || $scope.save.maxWeightFeeString == null || $scope.save.maxWeightFeeString == ""){
						commonService.alert("请输入下限重量！");
						return;
					}
					minWeightFee = true;
				}
				if($scope.save.maxWeightFeeString != undefined && $scope.save.maxWeightFeeString != null && $scope.save.maxWeightFeeString != ""){
					if($scope.save.minWeightFeeString == undefined || $scope.save.minWeightFeeString == null || $scope.save.minWeightFeeString == ""){
						commonService.alert("请输入上限重量！");
						return;
					}
					maxWeightFee = true;
				}
				if(minWeightFee && maxWeightFee){
					if(parseFloat($scope.save.maxWeightFeeString)  < parseFloat($scope.save.minWeightFeeString)){
						commonService.alert("上限重量价格不能小于下限重量价格！");
						return;
					}
				}
				var minVolumeFee = false;
				var maxVolumeFee = false;
				if($scope.save.minVolumeFeeString != undefined || $scope.save.minVolumeFeeString != null || $scope.save.minVolumeFeeString != ""){
					if($scope.save.maxVolumeFeeString == undefined || $scope.save.maxVolumeFeeString == null || $scope.save.maxVolumeFeeString == ""){
						commonService.alert("请输入体积的上限！");
						return;
					}
					minVolumeFee = true;
				}
				if($scope.save.maxVolumeFeeString != undefined || $scope.save.maxVolumeFeeString != null || $scope.save.maxVolumeFeeString != ""){
					if($scope.save.minVolumeFeeString == undefined || $scope.save.minVolumeFeeString == null || $scope.save.minVolumeFeeString == ""){
						commonService.alert("请输入体积的下限！");
						return;
					}
					maxVolumeFee = true;
				}
				if(minVolumeFee && maxVolumeFee){
					if(parseFloat($scope.save.minVolumeFeeString) > parseFloat($scope.save.maxVolumeFeeString)){
						commonService.alert("上限体积价格不能小于下限体积价格！");
						return;
					}
				}
				if( (($scope.save.maxWeightFeeString == undefined || $scope.save.maxWeightFeeString == null || $scope.save.maxWeightFeeString == "")
					&& ($scope.save.minWeightFeeString == undefined || $scope.save.minWeightFeeString == null || $scope.save.minWeightFeeString == ""))
					&& (($scope.save.minVolumeFeeString == undefined || $scope.save.minVolumeFeeString == null || $scope.save.minVolumeFeeString == "") 
					&& ($scope.save.maxVolumeFeeString == undefined || $scope.save.maxVolumeFeeString == null || $scope.save.maxVolumeFeeString == ""))
					){
						commonService.alert("请输入重量或者体积的上限和下限！");
						return;
				}
				var url = "orderFeeRuleBO.ajax?cmd=doSaveFreight";
				commonService.postUrl(url,$scope.save,function(data){
					if(data == "Y"){
						commonService.alert("保存成功！",function(){
							$scope.close();
						});
					}
				});
			},
			addOrDelFeeRule:function(num,obj){
				if(num == 1){
					$scope.orderTipRule.push({minWieght:"",maxWieght:"",fee:""});
				}
				if(num == 2){
					if($scope.orderTipRule.length <= 1){
						return;
					}
					$scope.orderTipRule.shift(obj);
					
				}
			},
			close:function(){
				commonService.closeToParentTab(true);
			},
			selectCompany:function(){
				if(userInfo.userType == 1){
					var url = "organizationBO.ajax?cmd=selectOrgByLink";
					commonService.postUrl(url,"",function(data){
						$scope.curOrgData = data;
						$scope.save.tenantId = data.items[0].tenantId;
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
					$scope.queryCompany(feeRule.tenantId,feeRule.beginOrgId,feeRule.endOrgId);
					$scope.save = feeRule;
					$scope.save.tenantId = feeRule.tenantId+"";
					
				});
			}
			
	};	
	fee.init();
}]);
