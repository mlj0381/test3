var addOrgInfoApp = angular.module("addOrgInfoApp", ['commonApp']);
addOrgInfoApp.controller("addOrgInfoCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var orgId=getQueryString("orgId");
	var addOrgInfo={
		init:function(){
			this.bindEvent();
			this.selectProvince();
			this.queryOrg();
		},
		bindEvent:function(){
			$scope.param=this.param;
		    $scope.clear=this.clear;
		    $scope.selectCity=this.selectCity;
		    $scope.selectProvince=this.selectProvince;
		    $scope.selectDistrict=this.selectDistrict;
		    $scope.streetCreat = this.streetCreat;
		    $scope.queryOrg=this.queryOrg;
		    $scope.doSave=this.doSave;
		    $scope.doModify=this.doModify;
		    $scope.close=this.close;
		    $scope.form=this.form;
		    $scope.selectOne=this.selectOne;
		    $scope.fixNumber=this.fixNumber;
		    //$scope.radio=this.radio;
		},
		params:{
			
		},
		form:{
			//isSettleDocket:1,
			//isPaymentCollection:1,
			//isDepartment:1
		},
		param:{
		isParent:1	
		},
		
		/** 清空查询条件 */
		clear:function(){
			addOrgInfo.form.provinceId='-1';
			addOrgInfo.form.regionId='-1';
			addOrgInfo.form.countyId='-1';
			addOrgInfo.form.orgType='-1';
			addOrgInfo.form.businessType='-1';
			addOrgInfo.form.orgName='';
			addOrgInfo.form.orgPrincipalPhone='';
			addOrgInfo.form.orgPrincipal='';
		},
		/** 查看* */
		toView:function(){
			var orgId='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条配载信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条配载信息!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					orgId=data.orgId;
				}
			});
			commonService.openTab(orgId,"配载详情","/ord/depearInfo.html?orgId="+orgId);
				},
		/** *保存* */
		doSave:function(){
			if($scope.form.orgName==null||$scope.form.orgName==undefined||$scope.form.orgName==''){
				commonService.alert("请输入网点名称!");
				return false;
			}
			if($scope.form.orgShorter==null||$scope.form.orgShorter==undefined||$scope.form.orgShorter==''){
				commonService.alert("请输入网点简称!");
				return false;
			}
			if($scope.form.departmentAddress==null||$scope.form.departmentAddress==undefined||$scope.form.departmentAddress==''){
				commonService.alert("请输入营业部详细地址!");
				return false;
			}
			if($scope.form.orgManager==null||$scope.form.orgManager==undefined||$scope.form.orgManager==''){
				commonService.alert("请输入网点经理!");
				return false;
			}/*
			if($scope.form.casReference==null||$scope.form.casReference==undefined||$scope.form.casReference==''){
				commonService.alert("请输入提现基准!");
				return false;
			}
			if($scope.form.lockLimit==null||$scope.form.lockLimit==undefined||$scope.form.lockLimit==''){
				commonService.alert("请输入锁机额度!");
				return false;
			}
			if($scope.form.warningLimit==null||$scope.form.warningLimit==undefined||$scope.form.warningLimit==''){
				commonService.alert("请输入预警额度!");
				return false;
			}*/
			//$scope.form.limitForCollection=$scope.form.limitForCollection*100;
			$scope.form.warningLimit=$scope.form.warningLimit*100;
			$scope.form.lockLimit=$scope.form.lockLimit*100;
			//var param = $scope.form;
			var url = "organizationBO.ajax?cmd=doSave";
			commonService.postUrl(url,$scope.form,function(data){
				// 成功执行
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("操作成功!",function(){
						commonService.closeToParentTab(true);
					});
//					addOrgInfo.form={
//						isSettleDocket:1,
//						isPaymentCollection:1,
//						isDepartment:1
//					},
//					$scope.selectProvince();
//					$scope.queryOrg();
//					$scope.form.warningLimit=$scope.form.warningLimit/100;
//					$scope.form.lockLimit=$scope.form.lockLimit/100;
//					$scope.form.limitForCollection=$scope.form.limitForCollection/100;
	 	    	}
			});
		},
		close:function(){
    		commonService.closeToParentTab(true);
    	},
		selectProvince:function(){
			var param = {};
			var url = "staticDataBO.ajax?cmd=selectProvince";
			commonService.postUrl(url,param,function(data){
				// 成功执行
				if(data!=null && data!=undefined && data!=""){
						$scope.provinceData=data;
						addOrgInfo.form.provinceId=data.items[0].id;
						addOrgInfo.selectCity(data.items[0].id);
						
	 	    	}
			});
		},
		selectCity:function(provinceId){
			if(parseInt(provinceId)>0){
				var param = {"provinceId":provinceId};
				var url = "staticDataBO.ajax?cmd=selectCity";
				commonService.postUrl(url,param,function(data){
					// 成功执行
					if(data!=null && data!=undefined && data!=""){
							$scope.cityData=data;
							addOrgInfo.selectDistrict(data.items[0].id);
							addOrgInfo.form.regionId=data.items[0].id;
		 	    	}
				});
			}
		},
		selectDistrict:function(cityId){
			if(parseInt(cityId)>0){
				var param = {"cityId":cityId};
				var url = "staticDataBO.ajax?cmd=selectDistrict";
				commonService.postUrl(url,param,function(data){
					// 成功执行
					if(data!=null && data!=undefined && data!=""){
							$scope.districtData=data;
							addOrgInfo.streetCreat(data.items[0].id);
							addOrgInfo.form.countyId=data.items[0].id;
		 	    	}
				});
			}
		},
		streetCreat : function (countyId){
			if(parseInt(countyId)>0){
				var param = {"districtId":countyId};
				var url = "staticDataBO.ajax?cmd=selectStreet";
				commonService.postUrl(url,param,function(data){
					// 成功执行
					if(data!=null && data!=undefined && data!=""){
							$scope.streetData=data;
							addOrgInfo.form.streetId=data.items[0].id;
		 	    	}
				});
			}
		},
		fixNumber:function (value){
			value =  value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
			value =  value.replace(/^\./g,"");  //验证第一个字符是数字而不是. 
			value =  value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.   
			value =  value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
		},
		queryOrg:function(){
			//$scope.param.isParent=1;
			//var param = $scope.param;
			var url = "staticDataBO.ajax?cmd=selectOrg";
			commonService.postUrl(url,$scope.param,function(data){
				// 成功执行
				if(data!=null && data!=undefined && data!=""){
						$scope.orgData=data;
						addOrgInfo.form.parentOrgId=data[0].orgId;
	 	    	}
			});
			if(orgId!=null && orgId!=undefined && orgId!=""){
				var url = "organizationBO.ajax?cmd=queryOrgDtllyh";
				commonService.postUrl(url,"orgId="+orgId,function(data){
					// 成功执行
					if(data!=null && data!=undefined && data!=""){
						$scope.form=data.organization;
						$scope.form.warningLimit=$scope.form.warningLimit/100;
						$scope.form.lockLimit=$scope.form.lockLimit/100;
						//$scope.form.limitForCollection=$scope.form.limitForCollection/100;
						$scope.form.orgType=data.organization.orgType+"";
						//$scope.form.currencyType = data.organization.currencyType+"";
						$scope.form.businessType = data.organization.businessType+"";
					$timeout(function(){
						$scope.form.provinceId=parseInt(data.organization.provinceId);
						addOrgInfo.selectCity(data.organization.provinceId);
						$scope.form.regionId=parseInt(data.organization.regionId);
						$timeout(function(){
							addOrgInfo.selectDistrict(data.organization.regionId);
							$scope.form.countyId=parseInt(data.organization.countyId);
							$scope.form.streetId = parseInt(data.organization.streetId);
						},300);
					},500);
							//addOrgInfo.form.parentOrgId=data[0].orgId;
		 	    	}
				});
			}
		},
		/*radio:function(value){
			addOrgInfo.form.isPaymentCollection=value;
		}*/
	}
	addOrgInfo.init();
}]);
