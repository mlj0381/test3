var businessManageAddApp = angular.module("businessManageAddApp", ['commonApp']);
businessManageAddApp.controller("businessManageAddCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	//var tenantId = getQueryString("tenantId");
	var relId = getQueryString("relId");
	var businessManageAdd={
		init:function(){
			this.bindEvent();
			this.selectServe();
			this.orgByIdQuery();
			this.selectProvince();
			this.selectOrg();
			
		},
		
		bindEvent:function(){
			$scope.user = this.user;
			$scope.doSave = this.doSave;
			$scope.close = this.close;
			//$scope.user.tenantId = tenantId;
			$scope.user.relId = relId;
			$scope.checktt = this.checktt;
			$scope.selectProvince = this.selectProvince;
			$scope.selectCity = this.selectCity;
			$scope.selectDistrict = this.selectDistrict;
			$scope.streetCreat = this.streetCreat;
			$scope.selectAll = this.selectAll;
		},
		orgByIdQuery:function(){
			var url = "organizationBO.ajax?cmd=queryByRelIdBus";
			var dataLine = $scope.user.relId;
			var p = {};
			p.relId = dataLine;
			commonService.postUrl(url,p,function(data){
				$scope.user = data;
				$scope.user.businessType = parseInt(data.businessType);
				$scope.selectAll(data.provinceId,data.regionId,data.countyId,data.streetId);
				$scope.user.orgId = data.lineOrgId;
				$scope.user.relId = dataLine;
			});
		},
		selectAll : function(provinceId,regionId,countyId,streetId){
			$scope.user.provinceId = parseInt(provinceId);
			$scope.selectCity(provinceId,regionId);
			$scope.selectDistrict(regionId,countyId);
			$scope.streetCreat(countyId,streetId);
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
		user:{
			businessType:3,
			orgId:-1
		},
		selectServe:function(){
			var param = {};
			var url = "staticDataBO.ajax?cmd=getOrdSellerServeType";
			commonService.postUrl(url,param,function(user){
				// 成功执行
				if(user!=null && user!=undefined && user!=""){
					$scope.roleInfos=user;
	 	    	}
			});
		},
		selectProvince:function(regionId){
			var param = {};
			var url = "staticDataBO.ajax?cmd=selectProvince";
			commonService.postUrl(url,param,function(data){
				// 成功执行
				if(data!=null && data!=undefined && data!=""){
						$scope.provinceData=data;
						businessManageAdd.user.provinceId=data.items[0].id;
						if(relId == null || relId == undefined || relId == ""){
							businessManageAdd.selectCity(data.items[0].id);
						}
	 	    	}
			});
		},
		selectCity:function(provinceId,regionId){
			
			if(parseInt(provinceId)>0){
				var param = {"provinceId":provinceId};
				var url = "staticDataBO.ajax?cmd=selectCity";
				commonService.postUrl(url,param,function(data){
					// 成功执行
					if(data!=null && data!=undefined && data!=""){
							$scope.cityData=data;
							if(regionId!=null && regionId!=undefined && regionId!=""){
								$scope.user.regionId = parseInt(regionId);
							}else{
								businessManageAdd.selectDistrict(data.items[0].id);
								businessManageAdd.user.regionId=data.items[0].id;
							}
							
		 	    	}
				});
			};
		},
		selectDistrict:function(cityId,countyId){
			if(parseInt(cityId)>0){
				var param = {"cityId":cityId};
				var url = "staticDataBO.ajax?cmd=selectDistrict";
				commonService.postUrl(url,param,function(data){
					// 成功执行
					if(data!=null && data!=undefined && data!=""){
							$scope.districtData=data;
							if(countyId!=null && countyId!=undefined && countyId!=""){
								$scope.user.countyId = parseInt(countyId);
							}else{
								businessManageAdd.streetCreat(data.items[0].id);
								businessManageAdd.user.countyId=data.items[0].id;
							}
		 	    	}
				});
			};
		},
		streetCreat : function (countyId,streetId){
			if(parseInt(countyId)>0){
				var param = {"districtId":countyId};
				var url = "staticDataBO.ajax?cmd=selectStreet";
				commonService.postUrl(url,param,function(data){
					// 成功执行
					
					if(data!=null && data!=undefined && data!=""){
							$scope.streetData=data;
							if(streetId!=null && streetId!=undefined && streetId!=""){
								$scope.user.streetId = parseInt(streetId);
							}else{
								businessManageAdd.user.streetId=data.items[0].id;
							}
		 	    	}
				});
			};
		},
		doSave:function(){
			if($scope.user.tenantCode==null || $scope.user.tenantCode==undefined || $scope.user.tenantCode==""){
				commonService.alert("商家编码不能为空");
				return false;
			}
			if($scope.user.name==null || $scope.user.name==undefined || $scope.user.name==""){
				commonService.alert("公司名称不能为空");
				return false;
			}
			if($scope.user.linkMan==null || $scope.user.linkMan==undefined || $scope.user.linkMan==""){
				commonService.alert("联系人不能为空");
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
			if($scope.user.address==null || $scope.user.address==undefined || $scope.user.address=="" ||$scope.user.provinceId== -1 ){
				commonService.alert("提货地址不能为空");
				return false;
			}
			
			var url  = "organizationBO.ajax?cmd=doUpdateBusiness";
			var dataLine = {};
			dataLine = $scope.user;
			commonService.postUrl(url,dataLine,function(data){
				  commonService.alert("保存完成!");
				  commonService.closeToParentTab(true);
			});
		},
		close:function(){
			commonService.closeToParentTab(true);
		},
	};
	businessManageAdd.init();
}]);
