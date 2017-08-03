var cmDriverInfoDetailApp = angular.module("cmDriverInfoDetailApp", ['commonApp']);
cmDriverInfoDetailApp.controller("cmDriverInfoDetailCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var id=getQueryString("id");
	var tow={
			init:function(){
				this.bindEvent();
				this.selectOrg();
				this.selecUserInfo();
			},
			bindEvent:function(){
				$scope.close = this.close;
				$scope.data = this.data;
				$scope.selectOrg = this.selectOrg;
			},
			data:{
				orgId:-1
			},
			selectOrg:function(){
				commonService.postUrl("organizationBO.ajax?cmd=getAllOrg","",function(data){
					console.log(data);
					if(data != null && data != undefined && data != ""){
						$scope.orgInfo = data;
						$scope.orgInfo.items.unshift({orgId:-1,orgName:"请选择"});
					}
				});
				
				if(id!=null && id!=undefined && id!=""){
					var url="cmDriverInfoBO.ajax?cmd=queryDriverDtl";
					commonService.postUrl(url,"id="+id,function(data){
						//成功执行
						if(data!=null && data!=undefined && data!=""){
							$scope.data = data.cmDriverInfo;
							$scope.identityCard.initDate(data.cmDriverInfo.identityCard); 
							$scope.drivingLicense.initDate(data.cmDriverInfo.drivingLicense); 
							$scope.identityCardBack.initDate(data.cmDriverInfo.identityCardBack); 
							$scope.identityCard.isUpShow(false);
							$scope.identityCardBack.isUpShow(false);
							$scope.drivingLicense.isUpShow(false);
							$scope.data.orgId;
							//$scope.acFeeConfig=data;
						}
					});
				}
			},
			selecUserInfo:function(){
				commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType","codeType=USER_TYPE",function(data){
					if(data != null && data != undefined && data != ""){
						$scope.userData = data;
						$scope.data.userType = data.items[0].codeValue;
					}
				});
			},
			close:function(){
				commonService.closeToParentTab(true);
			}
	};
	tow.init();
}]);
