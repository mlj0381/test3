var addConsigneeCmCustomerApp = angular.module("addConsigneeCmCustomerApp", ['commonApp']);
addConsigneeCmCustomerApp.controller("addConsigneeCmCustomerCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var tow={
			init:function(){
				this.bindEvent();
				this.selectOrg();
				if(getQueryString("id") != undefined && getQueryString("id") != null &&getQueryString("id") != ""){
					this.doQueryCmId();
				}
			},
			bindEvent:function(){
				$scope.data = this.data;
				$scope.doSave = this.doSave;
				$scope.close = this.close;
			},
			data:{
				orgId:-1
			},
			doQueryCmId:function(){
				var url = "customerBO.ajax?cmd=doQueryByIdCm";
				var dataLine = getQueryString("id");
				var p = {};
				p.id = dataLine;
				commonService.postUrl(url,p,function(data){
					  console.log(data);
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
			doSave:function(){
				
				if($scope.data.name==null || $scope.data.name==undefined || $scope.data.name==""){
					commonService.alert("收货方不能为空");
					return false;
				}
				if($scope.data.linkmanName==null || $scope.data.linkmanName==undefined || $scope.data.linkmanName==""){
					commonService.alert("联系人不能为空");
					return false;
				}
				if($scope.data.orgId==null || $scope.data.orgId==undefined || $scope.data.orgId==""){
					commonService.alert("归属网点不能为空");
					return false;
				}
				if($scope.data.type==null || $scope.data.type==undefined || $scope.data.type==""){
					commonService.alert("类型不能为空");
					return false;
				}
				commonService.postUrl("customerBO.ajax?cmd=doSaveCm",$scope.data,function(data){
				    commonService.alert("保存完成!");
				    $scope.data.name = "";
				    $scope.data.linkmanName = "";
				    $scope.data.bill = "";
				    $scope.data.telephone = "";
				    $scope.data.orgId = -1;
				    $scope.data.type = 2 ;
				    $scope.data.signingType = 1;
				    $scope.data.address = "";
				});
			},
			close:function(){
				commonService.closeToParentTab(true);
			},
	};
	tow.init();
}]);
