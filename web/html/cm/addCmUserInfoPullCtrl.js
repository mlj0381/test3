var addCmUserInfoPullApp = angular.module("addCmUserInfoPullApp", ['commonApp']);
addCmUserInfoPullApp.controller("addCmUserInfoPullCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var userId = getQueryString("userId");//修改是传过的用户id
	var verify = getQueryString("verify");//审核
	var see = getQueryString("see");//审核
	$scope.verifys = {};
	$scope.save = {};//初始化数据
	var userInfos={
			init:function(){
				this.bindEvent();
				this.getSysStaticData();//获取静态数据
				this.selectCompany();//获取公司数据
				this.getProvinceData();//获取省份数据
				$scope.userType = userInfo.userType;
				if(userId != undefined && userId != null && userId != ""){
					if(verify != undefined && verify != null && verify != ""){
						$scope.verify = verify;
					}
					if(see != undefined && see != null && see != ""){
						$scope.see = see;
					}
					this.getCmUserInfoPullData();
				}
			},
			bindEvent:function(){
				$scope.getSysStaticData = this.getSysStaticData;//获取静态数据
				$scope.close = this.close;//关闭页面
				$scope.doSave = this.doSave;//保存信息
				$scope.selectCompany = this.selectCompany;//获取公司数据
				$scope.getProvinceData = this.getProvinceData;//获取省份数据
				$scope.getCityData = this.getCityData;//获取城市信息
				$scope.getDistrictData = this.getDistrictData;//获取区域信息
				$scope.getCmUserInfoPullData = this.getCmUserInfoPullData;//获取用户信息
				$scope.isVerify = this.isVerify;//审核
			},
			getSysStaticData:function(){
				var url = "staticDataBO.ajax?cmd=selectSysStaticDataByCodeType";
				//账户类型
				commonService.postUrl(url,{codeType:"RECEI_TYPE_YQ"},function(data){
					$scope.paymentTypeData = data;
				});
				//合作方式
				commonService.postUrl(url,{codeType:"COOPERATION_MODE_YQ"},function(data){
					$scope.cooperationModeData = data;
				});
				//归属银行
				commonService.postUrl(url,{codeType:"BANK_TYPE_YUNQI"},function(data){
					$scope.bankNameData = data;
				});
			},
			close:function(){
				$scope.save = {};
				commonService.closeToParentTab(true);
			},
			selectCompany:function(){
				var url = "staticDataBO.ajax?cmd=selectCurTenantId";
				commonService.postUrl(url,"",function(data){
					$scope.curOrgData = data;
					$scope.save.tenantId = data.items[0].tenantId+"";
				});
			},
			getProvinceData:function(){
				var url = "staticDataBO.ajax?cmd=selectProvince";
				commonService.postUrl(url,"",function(data){
					$scope.provinceData = data;
				});
			},
			getCityData:function(province,city){
				var url = "staticDataBO.ajax?cmd=selectCity";
				commonService.postUrl(url,{"provinceId":province},function(data){
					$scope.cityData = data;
				});
			},
			getDistrictData:function(city,district){
				var url = "staticDataBO.ajax?cmd=selectDistrict";
				commonService.postUrl(url,{"cityId":city},function(data){
					$scope.districtData = data;
				});
			},
			doSave:function(){
				if($scope.save.userName == undefined || $scope.save.userName == null || $scope.save.userName == ""){
					commonService.alert("请输入拉包工名称！");
					return;
				}
				if($scope.save.loginAcct == undefined || $scope.save.loginAcct == null || $scope.save.loginAcct == ""){
					commonService.alert("请输入拉包工手机！");
					return;
				}
				
				if($scope.save.tenantId == undefined || $scope.save.tenantId == null || $scope.save.tenantId == ""){
					var tips = "";
					if($scope.userType = userType.CHAIN){
						tips = "请选择归属专线公司！";
					}else if($scope.userType = userType.PULL_COMPANY){
						tips = "请选择归属拉包公司！";
					}
					commonService.alert(tips);
					return;
				}
				
				if($scope.save.paymentType != undefined && $scope.save.paymentType !=  null && $scope.save.paymentType !=  ""){
					if($scope.save.bankICard == undefined || $scope.save.bankICard == null || $scope.save.bankICard == ""){
						commonService.alert("请输入卡号/帐号！");
						return;
					}
					if($scope.save.bankHolder == undefined || $scope.save.bankHolder == null || $scope.save.bankHolder == ""){
						commonService.alert("请输入持卡人/帐号名！");
						return;
					}
					if(parseInt($scope.save.paymentType) == 1){//银行卡
						if($scope.save.bankCode == undefined || $scope.save.bankCode == null || $scope.save.bankCode == ""){
							commonService.alert("请选择归属银行！");
							return;
						}
					}
				}
				if($scope.save.defaultSingularNum == undefined || $scope.save.defaultSingularNum == null || $scope.save.defaultSingularNum == ""){
					commonService.alert("请输入最大接单数！");
					return;
				}
				$scope.save.frontIdCard=$scope.frontIdCard.get().flowId;
				$scope.save.backIdCard=$scope.backIdCard.get().flowId;
				var url = "cmUserInfoPullBO.ajax?cmd=doSavePull";
				commonService.postUrl(url,$scope.save,function(data){
					if(data == "Y"){
						commonService.alert("保存成功！",function(){
							$scope.close();
						});
					}
				});
			},
			getCmUserInfoPullData:function(){
				var url = "cmUserInfoPullBO.ajax?cmd=getCmUserInfoPull";
				commonService.postUrl(url,{"userId":userId},function(data){
					var userInfo = data.userInfo;
					$scope.save.userId = data.userInfo.userId;
					$scope.save.userName = userInfo.userName;
					$scope.save.loginAcct = userInfo.loginAcct;
					var pull = data.pull;
					$scope.save.cooperationMode = pull.cooperationMode+"";
					$scope.save.tenantId = pull.tenantId+"";
					$scope.save.province = pull.province;
					$scope.getCityData(pull.province,pull.city);
					$scope.save.city = pull.city;
					$scope.getDistrictData(pull.city,pull.district);
					$scope.save.district = pull.district;
					$scope.frontIdCard.initDate(pull.frontIdCard);
					$scope.backIdCard.initDate(pull.backIdCard);
					$scope.save.address=pull.address;
					$scope.save.pullState = pull.pullState;
					$scope.save.jobNumber = pull.jobNumber.replace(pull.city,"");
					$scope.save.defaultSingularNum = pull.defaultSingularNum;
					if(verify != undefined && verify != null && verify != ""){
						$scope.verifys.remark = pull.remark;
					}
					var payment = data.payment;
					if(payment != undefined && payment != null){
						$scope.save.paymentType = payment.paymentType+"";
						$scope.save.bankHolder = (payment.bankHolder != null && payment.bankHolder != "") ? payment.bankHolder : payment.accountName;
						$scope.save.bankCode = payment.bankCode;
						$scope.save.bankICard = (payment.bankCard != null && payment.bankCard != "") ? payment.bankCard : payment.accountNum;	
					}
					
				});
			},
			isVerify:function(num){
				$scope.verifys.verify = num;
				$scope.verifys.userId = userId;
				var url = "cmUserInfoPullBO.ajax?cmd=verifyCmUserInfoPull";
				commonService.postUrl(url,$scope.verifys,function(data){
					if(data!=undefined && data !=null&&data!=""){
						var message = "";
						if(num == 1){
							message = "审核通过！";
						}
						if(num == 2){
							message ="审核不通过！";
						}
						commonService.alert(message,function(){
							$scope.close();
						});
					}
				});
			}
	};
	userInfos.init();
}]);
