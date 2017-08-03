var addCmUserInfoApp = angular.module("addCmUserInfoApp", ['commonApp']);
addCmUserInfoApp.controller("addCmUserInfoCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var id=getQueryString("id");
	var userType=userInfo.userType;
	var tow={
			init:function(){
				this.bindEvent();
				this.selectOrg();
				this.selecUserInfo();
			},
			bindEvent:function(){
				$scope.doSave = this.doSave;
				$scope.close = this.close;
				$scope.data = this.data;
				$scope.selectOrg = this.selectOrg;
				$scope.isOrgNameShow=true;
			},
			data:{
				orgId:-1
			},
			selectOrg:function(){
				commonService.postUrl("organizationBO.ajax?cmd=getAllOrg","",function(data){
					console.log(data);
					if(userType==6){
						$scope.isOrgNameShow=false;
					}
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
			doSave:function(){
				if($scope.data.name==null || $scope.data.name==undefined || $scope.data.name==""){
					commonService.alert("请输入姓名");
					return false;
				}
				if($scope.data.identityCardNo==null || $scope.data.identityCardNo==undefined || $scope.data.identityCardNo==""){
					commonService.alert("请输入司机身份证号");
					return false;
				}
				/*if($scope.data.drivingLicense==null || $scope.data.drivingLicense==undefined || $scope.data.drivingLicense==""){
					commonService.alert("请输入驾驶证编号");
					return false;
				}*/
				if($scope.data.bill==null || $scope.data.bill==undefined || $scope.data.bill==""){
					commonService.alert("请输入手机号码");
					return false;
				}
				if($scope.identityCard.get().flowId==''){
					commonService.alert("身份证正面不能为空");
					return false;
				}
				if($scope.drivingLicense.get().flowId==''){
					commonService.alert("驾驶证照片不能为空");
					return false;
				}
				
				$scope.data.imagePath=$scope.identityCard.get().flowUrl;
				$scope.data.identityCard=$scope.identityCard.get().flowId;
				
				$scope.data.imagePath=$scope.drivingLicense.get().flowUrl;
				$scope.data.drivingLicense=$scope.drivingLicense.get().flowId;
				//$scope.data.imagePath=$scope.certificate.get().flowUrl;
				//$scope.data.certificate=$scope.certificate.get().flowId;
				$scope.data.imagePath=$scope.identityCardBack.get().flowUrl;
				$scope.data.identityCardBack=$scope.identityCardBack.get().flowId;
				
				if($scope.data.orgId==-1 && userType!=6 ){
					commonService.alert("请选择所属网点");
					return false;
				}
				commonService.postUrl("cmDriverInfoBO.ajax?cmd=doSaveCmDriverInfo",$scope.data,function(data){
					if(data != null && data != undefined && data != ""){
						commonService.alert("保存成功!");
						commonService.closeToParentTab(false);
					}else{
						commonService.alert("保存失败!");
					}
				});
			},
			close:function(){
				commonService.closeToParentTab(false);
			}
	};
	tow.init();
}]);