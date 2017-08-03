var towardsManageApp = angular.module("towardsManageApp", ['commonApp','tableCommon']);
towardsManageApp.controller("towardsManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	$scope.query = {};
	var ord ={
		init:function(){
			this.bindEvent();
			this.selectRoutType();
			this.selectisStandardLine();
			this.queryCompany();
		},
		head:[
		      {name:"起始位置",code:"beginOrgName"},
		      {name:"终止位置",code:"endOrgName"},
		      {name:"线路类型",code:"routeTypeName"},
		      {name:"线路标准",code:"isStandardLineName"}
		      ],
		bindEvent:function(){
			$scope.url = "routeBO.ajax?cmd=queryTowards";
			$scope.head = ord.head;
			$scope.urlParam=$scope.query;
			$scope.toSave = this.toSave;
			$scope.doQuery = this.doQuery;
			$scope.close = this.close;
			$scope.selectOrg = this.selectOrg;
			$scope.selectChange = this.selectChange;
			$scope.selectEndOrg = this.selectEndOrg;
			$scope.clear = this.clear;
			$scope.toUpdate = this.toUpdate;
			$scope.toDel = this.toDel;
		},
		//查询事件
		doQuery:function(){
			$scope.tableCallBack=function(){
				$scope.paramsExport = JSON.stringify($scope.query);
			}
			$scope.page.load();
			$scope.page.callBack=function(){
				displayTable();
				setContentHeight();
			}
		},
		clear:function(){
			$scope.query.beginOrgId="";
			$scope.query.endOrgId = "";
			$scope.query.routeType = "-1";
			$scope.query.isStandardLine = "-1";
			$scope.doQuery();
		},
		queryCompany:function(){
			var that=this;
			//查询公司下面的所有的网点
			commonService.postUrl("organizationBO.ajax?cmd=queryOrgList","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.orgInfo = data;
					$scope.orgInfo.unshift({"orgId":-1,"orgName":"全部"});
				}
			});
			
		},
		
		//线路类型
		selectRoutType:function(){
			commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType","codeType=ROUTE_TYPE",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.routTypeData = data;
					$scope.routTypeData.items.unshift({"codeValue":"-1","codeName":"全部"});
					$scope.query.routeType = "-1";
				}
			});
		},
		selectChange:function(){
			if($scope.query.beginOrgId != "-1" && $scope.query.beginOrgId == $scope.query.endOrgId){
				commonService.alert("起始位置和终止位置不能相同");
				return false;
			}else{
				return true;
			}
		},
		selectisStandardLine:function(){
			commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType","codeType=IS_STANDARD_LINE",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.isStandarLineData = data;
					$scope.isStandarLineData.items.unshift({"codeValue":"-1","codeName":"全部"});
					$scope.query.isStandardLine = data.items[0].codeValue;
				}
			});
		},
		toSave:function(){
			commonService.openTab("11222","线路新增","/base/towards.html");
		},
		toUpdate:function(){
			var selectData = $scope.page.getSelectData();
			if (selectData == undefined || selectData.length == 0) {
				commonService.alert("请选择数据！");
			}
			var routingId = selectData[0].routingId;
			commonService.openTab("11222"+routingId,"线路修改","/base/towards.html?routingId="+routingId);
		},
		toDel:function(){
			var selectData = $scope.page.getSelectData();
			if (selectData == undefined || selectData.length == 0) {
				commonService.alert("请选择数据！");
			}
			var routingId = selectData[0].routingId;
			commonService.confirm("确认要删除该区域!",function(){
				commonService.postUrl("routeBO.ajax?cmd=doDelRouteRoutin",{"routingId":routingId},function(data){
					if (data == "Y") {
						commonService.alert("删除成功！");
						$scope.doQuery();
					}
				});
			});
		}
	};
	ord.init();
}]);