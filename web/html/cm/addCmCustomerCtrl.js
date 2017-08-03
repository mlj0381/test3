var addCmCustomerApp = angular.module("addCmCustomerApp", ['commonApp']);
addCmCustomerApp.controller("addCmCustomerCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var tow={
			init:function(){
				this.bindEvent();
				this.selectOrg();
				if(getQueryString("id") != undefined && getQueryString("id") != null &&getQueryString("id") != ""){
					this.doQueryCmId();
				}
				this.selectBusi();
			},
			bindEvent:function(){
				$scope.data = this.data;
				$scope.doSave = this.doSave;
				$scope.close = this.close;
			},
			data:{
				orgId:-1,
				tenantId:-1
			},
			doQueryCmId:function(){
				var url = "customerBO.ajax?cmd=doQueryByIdCm";
				var dataLine = getQueryString("id");
				var p = {};
				p.id = dataLine;
				commonService.postUrl(url,p,function(data){
					  $scope.data = data;
					  $scope.data.type = data.type+"";
					  $scope.data.signingType = data.signingType+"";
				});
			},
			selectOrg:function(){
				var param = {"isAll":"Y"};
				var url = "organizationBO.ajax?cmd=getAllOrg";
				commonService.postUrl(url,param,function(date){
					if(date!=null && date!=undefined && date != ""){
						$scope.orgInfo = date;
						if(date.items != null && date.items != undefined && date != ""){
							$scope.orgInfo.items.unshift({orgId:-1,orgName:"请选择"});
						}
		 	    	}
				});
				
			},
			selectBusi:function(){
				//var param = {"isAll":"Y"};
				var page={"isAll":"Y"};
				url = "organizationBO.ajax?cmd=doLinkQueryBusiness&_ALLEXPORT=1";
				commonService.postUrl(url,page,function(date){
					if(date!=null && date!=undefined && date != ""){
						$scope.busiInfo = date;
						if(date.items != null && date.items != undefined && date != ""){
							$scope.busiInfo.items.unshift({tenantId:-1,name:"请选择"});
						}
		 	    	}
				});
			},
			doSave:function(){
				if($scope.data.telephone!=null &&$scope.data.telephone!=undefined &&$scope.data.telephone!=""){
					if(!validatemobile($scope.data.telephone)){
						commonService.alert("请输入正确的手机号码");
						return false;
					}
				}
				if($scope.data.name==null || $scope.data.name==undefined || $scope.data.name==""){
					commonService.alert("发货方不能为空");
					return false;
				}
				if($scope.data.linkmanName==null || $scope.data.linkmanName==undefined || $scope.data.linkmanName==""){
					commonService.alert("联系人不能为空");
					return false;
				}
				if($scope.data.orgId==null || $scope.data.orgId==undefined || $scope.data.orgId=="" || $scope.data.orgId== -1){
					commonService.alert("归属网点不能为空");
					return false;
				}
				if($scope.data.type==null || $scope.data.type==undefined || $scope.data.type==""){
					commonService.alert("类型不能为空");
					return false;
				}
				/*if($scope.data.signingType==null || $scope.data.signingType==undefined || $scope.data.signingType==""){
					commonService.alert("请选择客户类型");
					return false;
				}*/
				commonService.postUrl("customerBO.ajax?cmd=doSaveCm",$scope.data,function(data){
				    commonService.alert("保存完成!");
				    $scope.data.name = "";
				    $scope.data.linkmanName = "";
				    $scope.data.bill = "";
				    $scope.data.telephone = "";
				    $scope.data.orgId = -1;
				    $scope.data.type = 1 ;
				    //$scope.data.signingType = 1;
				    $scope.data.address = "";
				    $scope.data.tenantId = -1;
				});
			},
			close:function(){
				commonService.closeToParentTab(true);
			},
	};
	tow.init();
}]);
