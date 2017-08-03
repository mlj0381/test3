var linkBusinessManageAddApp = angular.module("linkBusinessManageAddApp", ['commonApp']);
linkBusinessManageAddApp.controller("linkBusinessManageAddCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var businessManageAdd={
		init:function(){
			this.bindEvent();
			this.selectServe();
			if($scope.user.tenantId != null){
				this.orgByIdQuery();
			}
			this.selectProvince();
			this.selectOrg();
		},
		
		bindEvent:function(){
			$scope.user = this.user;
			$scope.doSave = this.doSave;
			$scope.close = this.close;
			$scope.user.tenantId = getQueryString("tenantId");
			$scope.check = this.check;
			$scope.selectProvince = this.selectProvince;
			$scope.selectCity = this.selectCity;
			$scope.selectDistrict = this.selectDistrict;
			$scope.streetCreat = this.streetCreat;
			$scope.selectAll = this.selectAll;
			$scope.selectOrg = this.selectOrg;
		},
		user:{
			businessType:3,
			orgId:-1,
		},
		orgByIdQuery:function(){
			var url = "organizationBO.ajax?cmd=queryByIdBus";
			var dataLine = $scope.user.tenantId;
			var p = {};
			p.tenantId = dataLine;
			commonService.postUrl(url,p,function(data){
				  $scope.user = data;
				  $scope.user.orgId = data.lineOrgId;
				  $scope.user.businessType = parseInt(data.businessType);
				  $scope.selectAll(data.provinceId,data.regionId,data.countyId,data.streetId);
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
		selectAll : function(provinceId,regionId,countyId,streetId){
			$scope.user.provinceId = parseInt(provinceId);
			$scope.selectCity(provinceId,regionId);
			$scope.selectDistrict(regionId,countyId);
			$scope.streetCreat(countyId,streetId);
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
						businessManageAdd.selectCity(data.items[0].id);
						
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
		check:function(){
			var param = $scope.user;
			var url = "organizationBO.ajax?cmd=queryByAllBus";
			
			if((param.tenantCode==undefined  || param.tenantCode=="" ) 
					  && (param.name==undefined || param.name=="")   
					  && (param.linkPhone==undefined || param.linkPhone=="")){
				return;
			}
			
			commonService.postUrl(url,param,function(data){
				// 成功执行
				if(data!=null && data != undefined && data != "" && data.tenantId > 0 && data.tenantId != null && $scope.user.tenantId != data.tenantId){
					commonService.confirm("该商家用户已存在，是否录入数据!",function(){
						$scope.user=data;
						$scope.user.orgId = data.orgId;
						//$scope.user.busiOrgId = data.orgId;
						$scope.selectAll(data.provinceId,data.regionId,data.countyId,data.streetId);
						$scope.$apply();
					});
	 	    	}
			});
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
			if($scope.user.provinceId == null || $scope.user.provinceId == undefined || $scope.user.provinceId == "" || $scope.user.provinceId == "-1"){
				commonService.alert("请选择省份");
				return false;
			}
			if($scope.user.regionId == null || $scope.user.regionId == undefined || $scope.user.regionId == "" || $scope.user.regionId == "-1"){
				commonService.alert("请选择城市");
				return false;
			}
			if($scope.user.countyId == null || $scope.user.countyId == undefined || $scope.user.countyId == "" || $scope.user.countyId == "-1"){
				commonService.alert("请选择区域");
				return false;
			}
			if($scope.user.lineOrgId == null || $scope.user.lineOrgId == undefined || $scope.user.lineOrgId =="" || $scope.user.lineOrgId == "-1"){
				commonService.alert("请选择归属网点");
				return false;
			}
			
			if($scope.user.address==null || $scope.user.address==undefined || $scope.user.address=="" ||$scope.user.provinceId== -1 ){
				commonService.alert("提货地址不能为空");
				return false;
			}
			var url  = "organizationBO.ajax?cmd=doSaveBusiness";
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
