var addCmCarrierCompanyApp = angular.module("addCmCarrierCompanyApp", ['commonApp']);
addCmCarrierCompanyApp.controller("addCmCarrierCompanyCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var orgId = getQueryString("orgId");
	var tow={
			init:function(){
				this.bindEvent();
				//this.selectOrg();
				this.selectProvince();
				this.queryCmCarrCompany();
				
			},
			bindEvent:function(){
				$scope.doSave = this.doSave;
				$scope.close = this.close;
				$scope.data = this.data;
				//$scope.selectOrg = this.selectOrg;
				$scope.selectCity = this.selectCity;
				$scope.selectDistrict = this.selectDistrict;
			},
			data:{
				
			},/*
			selectOrg:function(){
				commonService.postUrl("organizationBO.ajax?cmd=getAllOrg","",function(data){
					if(data != null && data != undefined && data != ""){
						$scope.orgInfo = data;
						$scope.orgInfo.items.unshift({orgId:-1,orgName:"全部"});
					}
				});
			},*/
			/**
			 * 查询省份
			 */
			selectProvince:function(){
				var param = {"isAll":"Y"};
				commonService.postUrl("staticDataBO.ajax?cmd=selectProvince",param,function(data){
					if(data != null && data != undefined && data != ""){
						$scope.provinces = data.items;
						$scope.data.provinceId=data.items[0].id;
						if(orgId==null||orgId==undefined||orgId==''){
							$scope.selectCity($scope.data.provinceId);
							$timeout(function(){
								$scope.selectDistrict($scope.data.regionId);
							},100);
						}
					}
				});
			},
			/**
			 * 查询城市
			 */
			selectCity:function(provinceId,regionId){
				var param = {"isAll":"Y","provinceId":provinceId};
				commonService.postUrl("staticDataBO.ajax?cmd=selectCity",param,function(data){
					if(data != null && data != undefined && data != ""){
						$scope.citys = data.items;
						if(regionId == undefined || regionId == null || regionId == ''){
							$scope.data.regionId=data.items[0].id;
						}else{
							$scope.data.regionId= regionId;
						}
						$timeout(function(){
							if(regionId == undefined || regionId == null || regionId == ''){
								$scope.selectDistrict($scope.data.regionId);
							}
						},100);
					}
				});
			},
			/**
			 * 查询区域
			 */
			selectDistrict:function(regionId,countyId){
				var param = {"isAll":"Y","cityId":regionId};
				commonService.postUrl("staticDataBO.ajax?cmd=selectDistrict",param,function(data){
					if(data != null && data != undefined && data != ""){
						$scope.districts = data.items;
						if(countyId == undefined || countyId == null || countyId == ''){
							$scope.data.countyId=data.items[0].id;
						}else{
							$scope.data.countyId= countyId;
						}
						
					}
				});
			},
			/**
			 * 查询承运商详情
			 */
			queryCmCarrCompany:function(){
				if(orgId != null && orgId != undefined && orgId != ""){
					var url = "organizationBO.ajax?cmd=doQueryCmC";
					commonService.postUrl(url,{"orgId":orgId},function(obj){
						if(obj != null && obj != undefined && obj != ""){
							$scope.data = obj.items[0];
							$scope.data.provinceId = parseInt(obj.items[0].provinceId);
							$scope.selectCity(parseInt(obj.items[0].provinceId),parseInt($scope.data.regionId));
							$scope.selectDistrict(parseInt(obj.items[0].regionId),parseInt($scope.data.countyId));
						}
					});
				}
			},
			doSave:function(){
//				if(!validatemobile($scope.data.orgPrincipalPhone)){
//					commonService.alert("请输入正确的手机号码");
//					return false;
//				}
				/*if(!validatemobile($scope.data.carryLinkPhone)){
					commonService.alert("请输入正确的手机号码");
					return false;
				}*/
				if($scope.data.orgName==null || $scope.data.orgName==undefined || $scope.data.orgName==""){
					commonService.alert("承运商不能为空");
					return false;
				}
				if($scope.data.provinceId == null || $scope.data.provinceId == undefined || $scope.data.provinceId == "" || $scope.data.provinceId == "-1"){
					commonService.alert("请选择省份");
					return false;
				}
				if($scope.data.orgPrincipalPhone==null || $scope.data.orgPrincipalPhone==undefined || $scope.data.orgPrincipalPhone==""){
					commonService.alert("联系电话不能为空");
					return false;
				}
				/*if($scope.data.signingType==null || $scope.data.signingType==undefined || $scope.data.signingType==""){
					commonService.alert("请选择客户类型");
					return false;
				}*/
				commonService.postUrl("organizationBO.ajax?cmd=saveCmC",$scope.data,function(data){
					if(orgId>0){
						commonService.alert("修改完成!",function(){
							commonService.closeToParentTab(false);
						});
					}else{
					    commonService.alert("保存完成!",function(){
							commonService.closeToParentTab(false);
						});
				    }
					
				});
			},
			close:function(){
				var flag = getQueryString("flag");
				if(flag == 2){
					commonService.closeToParentTab(false);
				}else{
					commonService.closeToParentTab(true);
				}
			},
	};
	tow.init();
}]);
