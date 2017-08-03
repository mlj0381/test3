var queryOrderFeePremiumApp = angular.module("queryOrderFeePremiumApp", ['commonApp','tableCommon']);
queryOrderFeePremiumApp.controller("queryOrderFeePremiumCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.query={};
	var fee={
			init:function(){
				this.bindEvent();
				//this.doQuery();
				this.selectCompany();//获取公司数据
				this.queryCompany();
			},
			head:[
			      {"name": "专线公司","code": "tenantName","width": "110","type":""},
			      {"name": "发站","code": "beginOrgName","width": "110","type":""},
			      {"name": "到站","code": "endOrgName","width": "110","type":""},
			      {"name": "保费（件/元）","code": "numberFeeString","width": "110", "type":""}
			],
			bindEvent:function(){
				$scope.head=fee.head;
				$scope.url="orderFeeRuleBO.ajax?cmd=queryOrderFeePremium";
				$scope.urlParam=$scope.query;
				$scope.doQuery = this.doQuery;//查询列表
				$scope.addPremiumFeeRule = this.addPremiumFeeRule;//新增
				$scope.updatePremiumFeeRule = this.updatePremiumFeeRule;//修改
				$scope.clean = this.clean;//清空
				$scope.seePremiumFeeRule = this.seePremiumFeeRule;//查看
				$scope.delPremiumFeeRule = this.delPremiumFeeRule;//删除
				$scope.queryCompany = this.queryCompany;
			},
			queryCompany:function(tenantId){
				var that=this;
				//查询公司下面的所有的网点
				commonService.postUrl("organizationBO.ajax?cmd=queryOrgList",{"tenantId":tenantId},function(data){
					if(data != null && data != undefined && data != ""){
						$scope.orgInfo = data;
						$scope.orgInfo.unshift({"orgId":-1,"orgName":"全部"});
						$scope.query.beginOrgId = -1;
						$scope.query.endOrgId = -1;
					}
				});
				
			},
			doQuery:function(){
				var url = "orderFeeRuleBO.ajax?cmd=queryOrderFeePremium";
				$scope.query.page=1;
				$scope.tableCallBack=function(){
					$scope.paramsExport = JSON.stringify($scope.query);
				}
				$scope.table.load();
				$scope.table.callBack=function(){
					displayTable();
					setContentHeight();
				}
			},
			addPremiumFeeRule:function(){
				commonService.openTab("Premium_22222_new","新增保费计费规则",encodeURI("/ord/feeRule/addOrderFeePremium.html"));
			},
			updatePremiumFeeRule:function(){
				var selectedDate = $scope.table.getSelectData()
				if(selectedDate == undefined || selectedDate.length == 0 || selectedDate.length > 1){
					commonService.alert("请选择一条数据！");
					return false;
				}
				var id = selectedDate[0].id;
				commonService.openTab("premium_"+id+"_update","修改保费计费规则",encodeURI("/ord/feeRule/addOrderFeePremium.html?premiumId="+id));
			},
			selectCompany:function(){
				if(userInfo.userType == 1){
					var url = "organizationBO.ajax?cmd=selectOrgByLink";
					commonService.postUrl(url,"",function(data){
						$scope.curOrgData = data;
						$scope.curOrgData.items.unshift({"tenantId":-1,"orgName":"全部"});
						$scope.query.tenantId = data.items[0].tenantId;
						$scope.queryCompany($scope.query.tenantId);
					});
				}else{
					var url = "staticDataBO.ajax?cmd=selectCurTenantId";
					commonService.postUrl(url,"",function(data){
						$scope.curOrgData = data;
						$scope.query.tenantId = data.items[0].tenantId+"";
					});
				}
			},
			clean:function(){
				$scope.query.ruleName = "";
				$scope.query.beginOrgId = -1;
				$scope.query.endOrgId = -1;
			},
			seePremiumFeeRule:function(){
				var selectedDate = $scope.table.getSelectData();
				if(selectedDate == undefined || selectedDate.length == 0 || selectedDate.length > 1){
					commonService.alert("请选择一条数据！");
					return false;
				}
				var id = selectedDate[0].id;
				commonService.openTab("premium_"+id+"_see","查看保费计算规则",encodeURI("/ord/feeRule/addOrderFeePremium.html?premiumId="+id+"&isSee=1"));
			},
			delPremiumFeeRule:function(){
				var selectedDate = $scope.table.getSelectData();
				if(selectedDate == undefined || selectedDate.length == 0 || selectedDate.length > 1){
					commonService.alert("请选择一条数据！");
					return false;
				}
				var id = selectedDate[0].id;
				commonService.confirm("确认删除规则",function(){
					commonService.postUrl("orderFeeRuleBO.ajax?cmd=delOrderFeeRule",{"ruleId":id},function(data){
						if(data != undefined && data != null && data != ""){
							commonService.alert("删除成功！",function(){
								$scope.doQuery();
							});
						}
					});
				});
			}
	};
	fee.init();
}]);
