var towardsApp = angular.module("towardsApp", ['commonApp']);
towardsApp.controller("towardsCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var routingId = getQueryString("routingId");
	var tow={
			init:function(){
				this.bindEvent();
				this.selectRoutType();
				this.selectisStandardLine();
				this.queryCompany();
				if (routingId != undefined && routingId != "") {
					$scope.data.routingId = routingId;
					this.getRouteRoutingOut(routingId);
				}
			},
			bindEvent:function(){
				$scope.doSave = this.doSave;
				$scope.close = this.close;
				$scope.data = this.data;
				$scope.selectChange = this.selectChange;
				$scope.getRouteRoutingOut = this.getRouteRoutingOut;
			},
			data:{},
			queryCompany:function(){
				var that=this;
				//查询公司下面的所有的网点
				commonService.postUrl("organizationBO.ajax?cmd=queryOrgList","",function(data){
					if(data != null && data != undefined && data != ""){
						$scope.orgInfo = data;
//						$scope.orgInfo.unshift({"orgId":-1,"orgName":"全部"});
					}
				});
				
			},
			selectRoutType:function(){
				commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType","codeType=ROUTE_TYPE",function(data){
					if(data != null && data != undefined && data != ""){
						$scope.routTypeData = data;
						$scope.data.routeType = data.items[0].codeValue;
					}
				});
			},
			selectChange:function(){
				if($scope.data.beginOrgId == $scope.data.endOrgId){
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
						$scope.data.isStandardLine = data.items[0].codeValue;
					}
				});
			},
			doSave:function(){
				
				//$scope.data.beginOrgId=$scope.currOrgId;
				if($scope.data.beginOrgId == null || $scope.data.beginOrgId == undefined ){
					commonService.alert("请选择起始位置");
					return;
				}
				if($scope.data.endOrgId == null || $scope.data.endOrgId == undefined ){
					commonService.alert("请选择终止位置");
					return;
				}
				if($scope.data.routeType == null || $scope.data.routeType == undefined ){
					commonService.alert("请选择线路类型");
					return;
				}
				if($scope.data.isStandardLine == null || $scope.data.isStandardLine == undefined ){
					commonService.alert("请选择线路标准");
					return;
				}
				if ($scope.data.routingId !=undefined && $scope.data.routingId != null && $scope.data.routingId != "") {
					if(tow.selectChange() == true){
						commonService.postUrl("routeBO.ajax?cmd=doUpdateRouteRouting",$scope.data,function(data){
							if(data != null && data != undefined && data != ""){
								commonService.alert("设置成功!");
								commonService.closeToParentTab(true);
							}else{
								commonService.alert("设置失败!");
							}
						});
					}
				}else{
					if(tow.selectChange() == true){
						commonService.postUrl("routeBO.ajax?cmd=setRount",$scope.data,function(data){
							if(data != null && data != undefined && data != ""){
								commonService.alert("设置成功!");
								commonService.closeToParentTab(true);
							}else{
								commonService.alert("设置失败!");
							}
						});
					}
				}
				
			},
			close:function(){
				commonService.closeToParentTab(true);
			},
			getRouteRoutingOut:function(id){
				commonService.postUrl("routeBO.ajax?cmd=getRouteRoutingOut",{"routingId":id},function(data){
					$scope.data = data;
					$scope.data.routeType = data.routeType+"";
					$scope.data.isStandardLine = data.isStandardLine+"";
				});
			}
	};
	tow.init();
}]);
