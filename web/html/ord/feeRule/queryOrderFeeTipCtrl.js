var queryOrderFeeTipApp = angular.module("queryOrderFeeTipApp", ['commonApp','tableCommon']);
queryOrderFeeTipApp.controller("queryOrderFeeTipCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.query={};
	var fee={
			init:function(){
				this.bindEvent();
				//this.doQuery();
				this.selectCompany();//获取公司数据
				this.queryCompany();
			},
			head:[	
			       {"name":"小费规则名称","code":"ruleName","width":"110"},
	               {"name":"归属专线","code":"tenantName","width":"110"},
	               {"name":"线路","code":"link","width":"110"},
	               {"name":"按首重计算","code":"isFirst","width":"110"},
	               {"name":"按重量范围","code":"isWeight","width":"110"},
	               {"name":"按运费占比计费","code":"isFreight","width":"110"},
	               {"name":"是否通用模板","code":"isCurrencyString","width":"110"},
	               {"name":"创建时间","code":"createTimeString","width":"110"}
			],
			bindEvent:function(){
				$scope.head=fee.head;
				$scope.url="orderFeeRuleBO.ajax?cmd=queryOrderFeeTip";
				$scope.urlParam=$scope.query;
				$scope.doQuery = this.doQuery;//查询列表
				$scope.addTipFeeRule = this.addTipFeeRule;//新增
				$scope.updateTipFeeRule = this.updateTipFeeRule;//修改
				$scope.clean = this.clean;//清空
				$scope.seeTipFeeRule = this.seeTipFeeRule;//查看
				$scope.delTipFeeRule = this.delTipFeeRule;//删除
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
				var url = "orderFeeRuleBO.ajax?cmd=queryOrderFeeTip";
				$scope.query.page=1;
				$scope.tableCallBack=function(){
					$scope.paramsExport = JSON.stringify($scope.query);
				}
				$scope.page.load();
				$scope.page.callBack=function(){
					displayTable();
					setContentHeight();
				}
			},
			addTipFeeRule:function(){
				commonService.openTab("tip_22222_new","新增小费计算规则",encodeURI("/ord/feeRule/addOrderFeeTip.html"));
			},
			updateTipFeeRule:function(){
				var selectedDate = $scope.page.getSelectData();
				if(selectedDate == undefined || selectedDate.length == 0){
					commonService.alert("请选择一条数据！");
					return false;
				}
				if (selectedDate.length > 1) {
					commonService.alert("请选择一条数据！");
					return false;
				}
				var id = selectedDate[0].id;
				commonService.openTab("tip_"+id+"_update","修改小费计算规则",encodeURI("/ord/feeRule/addOrderFeeTip.html?tipId="+id));
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
			seeTipFeeRule:function(){
				var selectedDate = $scope.page.getSelectData();
				if(selectedDate == undefined || selectedDate.length == 0 || selectedDate.length > 1){
					commonService.alert("请选择一条数据！");
					return false;
				}
				var id = selectedDate[0].id;
				commonService.openTab("tip_"+id+"_see","查看小费计算规则",encodeURI("/ord/feeRule/addOrderFeeTip.html?tipId="+id+"&isSee=1"));
			},
			delTipFeeRule:function(){
				var selectedDate = $scope.page.getSelectData();
				if(selectedDate == undefined || selectedDate.length == 0 || selectedDate.length > 1){
					commonService.alert("请选择一条数据！");
					return false;
				}
				var id = selectedDate[0].id;
				commonService.confirm("确认删除规则名称【"+selectedDate[0].ruleName+"】",function(){
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
