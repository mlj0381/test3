var masterManageAddApp = angular.module("masterManageAddApp", ['commonApp']);
masterManageAddApp.controller("masterManageAddCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var tenantId = getQueryString("tenantId");
	var relId = getQueryString("relId");
	var businessManageAdd={
		init:function(){
			this.bindEvent();
			this.selectProvince();
			if(tenantId != null && tenantId != "" && tenantId != undefined){
				this.orgByIdQuery();
				$scope.isDisabled = true;
			}
		},
		bindEvent:function(){
			$scope.user = this.user;
			$scope.doSave = this.doSave;
			$scope.close = this.close;
			$scope.user.tenantId = tenantId;
			$scope.user.relId = relId;
			$scope.check=this.check;
			$scope.selectProvince=this.selectProvince;
			$scope.selectCity=this.selectCity;
		},
		orgByIdQuery:function(){
			var url = "organizationBO.ajax?cmd=doQuerySFPartners";
			var dataLine = relId;
			var p = {};
			p.relId = dataLine;
			commonService.postUrl(url,p,function(data){
				  var ob = data.items;
				  $scope.user = ob[0];
				  var regionId = ob[0].regionId;
				  $scope.user.lineOrgId = ob[0].lineOrgId;
				  $scope.user.provinceId = parseInt(ob[0].provinceId);
				  $scope.selectCity($scope.user.provinceId);
				  $timeout(function(){
					  $scope.user.regionId = parseInt(regionId);
				  },200);
				  console.log($scope.user);
			});
		},
		params:{
		},
		user:{
			orgId:"",
			lineOrgId:"",
			provinceId:"",
			regionId:""
		},
		check:function(){
			var param = $scope.user;
			var url = "organizationBO.ajax?cmd=queryBySFPartners";
			commonService.postUrl(url,param,function(data){
				// 成功执行
				console.log(data);
				if(data!=null && data != undefined && data != "" && data.tenantId > 0 && data.tenantId != null && $scope.user.tenantId != data.tenantId){
					commonService.confirm("该师傅合作商用户已存在，可以添加关系，是否录入数据!",function(){
						$scope.user=data;
						//TODO 后续需要添加一下查询租户下面的管理员账号 
						$scope.user.loginAcct=data.linkPhone;
						var regionId = data.regionId;
						$scope.user.orgId = data.orgId;
						$scope.user.lineOrgId = data.lineOrgId;
						$scope.isDisabled = true;
						$scope.user.provinceId = parseInt(data.provinceId);
						$scope.selectCity($scope.user.provinceId);
						$timeout(function(){
							$scope.user.regionId = parseInt(regionId);
						},200);
						
						$scope.$apply();
					});
	 	    	}
			});
		},
		selectProvince:function(){
			var param = {"isAll":"Y"};
			var url = "staticDataBO.ajax?cmd=selectProvince";
			commonService.postUrl(url,param,function(data){
				// 成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.provinceData=data;
					$scope.user.provinceId=data.items[0].id;
					$scope.selectCity($scope.user.provinceId);
	 	    	}
			});
		},
		selectCity:function(provinceId){
			if(parseInt(provinceId)>0){
				var param = {"isAll":"Y","provinceId":provinceId};
				var url = "staticDataBO.ajax?cmd=selectCity";
				commonService.postUrl(url,param,function(data){
					// 成功执行
					if(data!=null && data!=undefined && data!=""){
						$scope.cityData=data;
						$scope.user.regionId=data.items[0].id;
		 	    	}
				});
			}
		},
		doSave:function(){
			if($scope.user.tenantCode==null || $scope.user.tenantCode==undefined || $scope.user.tenantCode==""){
				commonService.alert("师傅合作商编码不能为空");
				return false;
			}
			
			if($scope.user.name==null || $scope.user.name==undefined || $scope.user.name==""){
				commonService.alert("师傅合作商名称不能为空");
				return false;
			}
			if($scope.user.linkMan==null || $scope.user.linkMan==undefined || $scope.user.linkMan==""){
				commonService.alert("联系人不能为空");
				return false;
			}
			if($scope.user.loginAcct==null || $scope.user.loginAcct==undefined || $scope.user.loginAcct==""){
				commonService.alert("登录账号不能为空");
				return false;
			}
//			if(!validatemobile($scope.user.linkPhone)){
//				commonService.alert("请输入正确的手机号码");
//				return false;
//			}
			var url  = "organizationBO.ajax?cmd=doSaveSFPartners";
			var dataLine = {};
			dataLine = $scope.user;
			commonService.postUrl(url,dataLine,function(data){
				  commonService.alert("保存完成!");
				  $scope.close();
			});
		},
		close:function(){
			commonService.closeToParentTab(true);
		},
	};
	businessManageAdd.init();
}]);
