var carrierCompanyUpdateApp = angular.module("carrierCompanyUpdateApp", ['commonApp']);
carrierCompanyUpdateApp.controller("carrierCompanyUpdateCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var carrierCompanyUpdate ={
		init:function(){
			this.bindEvent();
			this.orgByIdQuery();
		},
		orgByIdQuery:function(){
			var url = "organizationBO.ajax?cmd=queryByIdOrgCompanyList";
			var dataLine = getQueryString("tenantId");
			var p={};
			p.tenantId=dataLine;
			commonService.postUrl(url,p,function(data){
				 $scope.user = data.data;
				 if(data.data.corporateFrontCard){
					 $scope.corporateFrontCardId.initDate(data.data.corporateFrontCard);
				 }
				if(data.data.businesslicensePic){
					 $scope.businesslicensePicId.initDate(data.data.businesslicensePic);
				}
				if(data.data.corporateBackCard){
					$scope.corporateBackCardId.initDate(data.data.corporateBackCard);
				}
				 
			});
		},
		bindEvent:function(){
			$scope.user=this.user;
			$scope.doUpdate = this.doUpdate;
			$scope.close = this.close;
			$scope.user.tenantId = this.tenantId;
		},
		doUpdate:function(){
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
			$scope.user.corporatebackCardId=$scope.corporateBackCardId.get().flowId;
			$scope.user.businesslicensePicId=$scope.businesslicensePicId.get().flowId;
			var url = "organizationBO.ajax?cmd=updateByIdOrgCompanyList";
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
	carrierCompanyUpdate.init();
}]);