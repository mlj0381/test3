var addAcFeeConfigApp = angular.module("addAcFeeConfigApp", ['commonApp']);
addAcFeeConfigApp.controller("addAcFeeConfigCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var tow={
			init:function(){
				this.bindEvent();
				//this.selectOrg();
			},
			bindEvent:function(){
				$scope.doSave = this.doSave;
				$scope.close = this.close;
				$scope.data = this.data;
			},
			data:{},
			/**新增科目**/
			doSave:function(){
				if($scope.data.feeName==null || $scope.data.feeName==undefined || $scope.data.feeName==""){
					commonService.alert("科目名称不能为空");
					return false;
				}
				if($scope.data.feeType==null || $scope.data.feeType==undefined || $scope.data.feeType==""){
					commonService.alert("科目类型不能为空");
					return false;
				}
				if($scope.data.costType==null || $scope.data.costType==undefined || $scope.data.costType==""){
					commonService.alert("费用类型不能为空");
					return false;
				}
				if($scope.data.routeOrgType==null || $scope.data.routeOrgType==undefined || $scope.data.routeOrgType==""){
					commonService.alert("请选择线路类型");
					return false;
				}
				commonService.postUrl("acFeeConfigBO.ajax?cmd=doFeeConfig",$scope.data,function(data){
					    $scope.data.feeName="";
						commonService.alert("操作成功!");
				});
			},
			close:function(){
				commonService.closeToParentTab(true);
			}
	};
	tow.init();
}]);
