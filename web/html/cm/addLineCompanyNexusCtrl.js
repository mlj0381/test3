var addLineCompanyNexusApp = angular.module("addLineCompanyNexusApp", ['commonApp']);
addLineCompanyNexusApp.controller("addLineCompanyNexusCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var addLineCompanyNexus={
		//一进入调用
		init:function(){
			this.selectOrgCompany();
			this.selectOrgLink();
			this.bindEvent();
		},
		
		bindEvent:function(){
			$scope.close = this.close;	 
			$scope.doSave=this.doSave;
		},
		selectOrgLink:function(){
			var param = {"isAll":"Y"};
			var url = "organizationBO.ajax?cmd=queryByAllLink";
			commonService.postUrl(url,param,function(data){
				// 成功执行
				if(data!=null && data!=undefined && data != ""){
					$scope.orgLink=data;
					$scope.orgLink.items.unshift({tenantId:-1,orgName:"请选择"});
	 	    	}
			});
		},
		selectOrgCompany:function(){
			var param = {"isAll":"Y"};
			var url = "organizationBO.ajax?cmd=queryByCompany";
			commonService.postUrl(url,param,function(data){
				// 成功执行
				if(data!=null && data!=undefined && data != ""){
					$scope.companyData=data;
					$scope.companyData.items.unshift({tenantId:-1,orgName:"请选择"});
	 	    	}
			});
		},
		doSave:function(){
			var url="specialLineCompanyNexusBO.ajax?cmd=doSaveSpecialLineCompanyNexus";
			commonService.postUrl(url,$scope.user,function(data){
			commonService.alert("保存完成!",function(){
			commonService.closeToParentTab(true);
			});
		})
		},
		close:function(){
			commonService.closeToParentTab(true);
		}
	};
	addLineCompanyNexus.init();
}]);
