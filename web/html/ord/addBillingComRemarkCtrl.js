var addBillingComRemarkApp = angular.module("addBillingComRemarkApp", ['commonApp']);
addBillingComRemarkApp.controller("addBillingComRemarkCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var RId = getQueryString("RId");
	var tow={
			init:function(){
				this.bindEvent();
				if(RId != undefined && RId != null && RId != ""){
					this.doQueryCmId();
				}
			},
			bindEvent:function(){
				$scope.data = this.data;
				$scope.doSave = this.doSave;
				$scope.close = this.close;
			},
			data:{},
			doQueryCmId:function(){
				var url = "orderInfoBO.ajax?cmd=doQueryCmOrgNotesById";
				var dataLine = getQueryString("RId");
				var p = {};
				p.RId = dataLine;
				commonService.postUrl(url,p,function(data){
					  console.log(data);
					  $scope.data = data;
				});
			},
			doSave:function(){
				if($scope.data.sortId==null || $scope.data.sortId==undefined || $scope.data.sortId==""){
					commonService.alert("请填排序！");
					return false;
				}
				if($scope.data.notes==null || $scope.data.notes==undefined || $scope.data.notes==""){
					commonService.alert("请填备注！");
					return false;
				}
				commonService.postUrl("orderInfoBO.ajax?cmd=doSaveCmOrgNotes",$scope.data,function(data){
				    commonService.alert("保存完成!");
				    $scope.close();
				});
			},
			close:function(){
				commonService.closeToParentTab(true);
			},
	};
	tow.init();
}]);
