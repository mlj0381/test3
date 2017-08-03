var queryOrderFeeFreightApp = angular.module("queryOrderFeeFreightApp", ['commonApp','tableCommon']);
queryOrderFeeFreightApp.controller("queryOrderFeeFreightCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
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
			      {"name": "下限（计重模式 元/kg）","code": "minWeightFeeString","width": "110", "type":""},
			      {"name": "上限（计重模式 元/kg）","code": "maxWeightFeeString","width": "110", "type":""},
			      {"name": "下限（立方模式 元/方）","code": "minVolumeFeeString","width": "110", "type":""},
			      {"name": "上限（立方模式 元/方）","code": "maxVolumeFeeString","width": "110", "type":""},
			      {"name": "最低一票（元）","code": "minFeeString","width": "110", "type":""}
			],
			bindEvent:function(){
				$scope.head=fee.head;
				$scope.url="orderFeeRuleBO.ajax?cmd=queryOrderFeeFreight";
				$scope.urlParam=$scope.query;
				$scope.doQuery = this.doQuery;//查询列表
				$scope.addFreightFeeRule = this.addFreightFeeRule;//新增
				$scope.updateFreightFeeRule = this.updateFreightFeeRule;//修改
				$scope.clean = this.clean;//清空
				$scope.seeFreightFeeRule = this.seeFreightFeeRule;//查看
				$scope.delFreightFeeRule = this.delFreightFeeRule;//删除
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
				var url = "orderFeeRuleBO.ajax?cmd=queryOrderFeeFreight";
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
			addFreightFeeRule:function(){
				commonService.openTab("freight_22222_new","新增运费计费规则",encodeURI("/ord/feeRule/addOrderFeeFreight.html"));
			},
			updateFreightFeeRule:function(){
				var selectedDate = $scope.table.getSelectData()
				if(selectedDate == undefined || selectedDate.length == 0 || selectedDate.length > 1){
					commonService.alert("请选择一条数据！");
					return false;
				}
				var id = selectedDate[0].id;
				commonService.openTab("freight_"+id+"_update","修改运费计费规则",encodeURI("/ord/feeRule/addOrderFeeFreight.html?freightId="+id));
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
			seeFreightFeeRule:function(){
				var selectedDate = $scope.table.getSelectData();
				if(selectedDate == undefined || selectedDate.length == 0 || selectedDate.length > 1){
					commonService.alert("请选择一条数据！");
					return false;
				}
				var id = selectedDate[0].id;
				commonService.openTab("freight_"+id+"_see","查看运费计算规则",encodeURI("/ord/feeRule/addOrderFeeFreight.html?freightId="+id+"&isSee=1"));
			},
			delFreightFeeRule:function(){
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
