var addCmCarrierCompanyApp = angular.module("addCmCarrierCompanyApp", ['commonApp']);
addCmCarrierCompanyApp.controller("addCmCarrierCompanyCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var lbCompanyAdd ={
		init:function(){
			this.bindEvent();
		},
		bindEvent:function(){
			$scope.user=this.user;
			$scope.doSave = this.doSave;
			$scope.close = this.close;
		},
		doSave:function(){
			if($scope.user.tenantCode==null || $scope.user.tenantCode==undefined || $scope.user.tenantCode==""){
				commonService.alert("公司编码不能为空");
				return false;
			}
			if($scope.user.name==null || $scope.user.name==undefined || $scope.user.name==""){
				commonService.alert("公司名称不能为空");
				return false;
			}
			
			if($scope.user.linkMan==null || $scope.user.linkMan==undefined || $scope.user.linkMan==""){
				commonService.alert("法人不能为空");
				return false;
			}
			if($scope.user.linkPhone==null || $scope.user.linkPhone==undefined || $scope.user.linkPhone==""){
				commonService.alert("联系电话不能为空");
				return false;
			}
			if(!validatemobile($scope.user.linkPhone)){
				commonService.alert("请输入正确的手机号码");
				return false;
			}
			$scope.user.corporateFrontCardId=$scope.corporateFrontCardId.get().flowId;
			$scope.user.corporatebackCardId=$scope.corporatebackCardId.get().flowId;
			$scope.user.businesslicensePicId=$scope.businesslicensePicId.get().flowId;
			var url = "organizationBO.ajax?cmd=doSavePullPagCompany";
			var dataCompany = $scope.user;
			commonService.postUrl(url,dataCompany,function(data){
					  commonService.alert("保存完成!");
					  commonService.closeToParentTab(true);
			});
		},
		user:{
			 corporateFrontCardId:null,
			 corporatebackCardId:null,
			 businesslicensePicId:null
			 
		},
		close:function(){
			commonService.closeToParentTab(true);
		},
		};
	lbCompanyAdd.init();
}]);