var excQueryApp = angular.module("excQueryApp", ['commonApp']);
excQueryApp.controller("excQueryCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var excQuery={
		init:function(){
			this.bindEvent();
			this.doQuery();
			this.selectProvince();
		},
		bindEvent:function(){
			$scope.param = this.param;
			$scope.query = this.query;
			$scope.doQuery = this.doQuery;
		    $scope.clear = this.clear;
		   
		    $scope.selectCity=this.selectCity;
		    $scope.selectProvince=this.selectProvince;
		    $scope.selectDistrict=this.selectDistrict;
		    $scope.selectStreet=this.selectStreet;
		    $scope.openExc=this.openExc;
		    $scope.dealExc=this.dealExc;
		    $scope.queryExceinfo=this.queryExceinfo;
		},
		params:{
			taskState:4
		},
		query:{
			provinceId:-1,
		},
		
		doQuery:function(){
			var url = "exceptionInfoBO.ajax?cmd=doQuery";
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:$scope.query,
							callBack:'setContentHegthDelay'
						});
			},500);
		},
		clear:function(){
			$scope.query.receivePhone="";
			$scope.query.receiveName="";
			$scope.query.wayBillId="";
			$scope.query.provinceId=-1;
			$scope.query.cityId=-1;
			$scope.query.countyId=-1;
			$scope.query.excState="-1";
			$scope.query.excType="-1";
			$scope.cityData={};
			$scope.districtData={};
			
		},
		
		selectProvince:function(){
			var param = {"isAll":"Y"};
			var url = "staticDataBO.ajax?cmd=selectProvince";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
						$scope.provinceData=data;
						excQuery.query.provinceId=data.items[0].id;
	 	    	}
			});
		},
		selectCity:function(provinceId){
			if(parseInt(provinceId)>0){
				var param = {"isAll":"Y","provinceId":provinceId};
				var url = "staticDataBO.ajax?cmd=selectCity";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
							$scope.cityData=data;
							excQuery.query.cityId=data.items[0].id;
		 	    	}
				});
			}
		},
		selectDistrict:function(cityId){
			if(parseInt(cityId)>0){
				var param = {"isAll":"Y","cityId":cityId};
				var url = "staticDataBO.ajax?cmd=selectDistrict";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
							$scope.districtData=data;
							excQuery.query.countyId=data.items[0].id;
		 	    	}
				});
			}
		},
		selectStreet:function(districtId){
			if(parseInt(districtId)>0){
				var param = {"isAll":"Y","districtId":districtId};
				var url = "staticDataBO.ajax?cmd=selectStreet";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
							$scope.streetData=data;
							excQuery.query.townId=data.items[0].id;
		 	    	}
				});
			}
		},
		openExc:function(){
			commonService.openTab("exc"+21212,"异常登记","/sche/exc/excRegister.html?isShow=1");
		},
		dealExc:function(){
			var excId='';
			var excState='';
			var selectData=$scope.page.getSelectData();
			
			if(selectData.length==0){
				commonService.alert("请至少选择一条异常信息!");
				return false;
			}
			excId=selectData[0].excId;
			excState=selectData[0].excState;
			if(excState==3){
				commonService.alert("异常已处理完成，不允许再处理!");
				return false;
			}
			//责任网点
			var dutyObjId=selectData[0].dutyObjId;
			//开单网点
			var inputOrgId=selectData[0].inputOrgId;
			//如果当前登录的网点为该异常的开单网点，责任网点，可以进行异常处理
			if(userInfo.orgId==dutyObjId || userInfo.orgId==inputOrgId){
				commonService.openTab("exc"+excId,"异常处理","/sche/exc/excRegister.html?isShow=2"+"&excId="+excId+"&excState="+excState);
			}else{
				commonService.alert("你不是该异常的开单网点或责任网点，没有权限处理！");
			}
			
		},
		queryExceinfo:function(){
			var excId='';
			var selectData=$scope.page.getSelectData();
			
			if(selectData.length==0){
				commonService.alert("请至少选择一条异常信息!");
				return false;
			}
			excId=selectData[0].excId;
			commonService.openTab("exc"+excId,"异常处理","/sche/exc/excRegister.html?isShow=2"+"&excId="+excId+"&isView=1");
		}
		
	};
	excQuery.init();
}]);
