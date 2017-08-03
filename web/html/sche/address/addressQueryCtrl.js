var addressQueryApp = angular.module("addressQueryApp", ['commonApp']);
addressQueryApp.controller("addressQueryCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var addressQuery={
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
		   // $scope.selectStreet=this.selectStreet;
		},
		query:{
			
		},
		doQuery:function(){
			var url = "addressBO.ajax?cmd=doQuery";
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:$scope.query,
							callBack:'setContentHegthDelay'
						});
			},500);
		},
		clear:function(){
			$scope.query.provinceId=-1;
			$scope.query.cityId=-1;
			$scope.query.countyId=-1;
			$scope.cityData={};
			$scope.districtData={};
			
		},
		selectProvince:function(){
			var param = {"isAll":"Y"};
			var url = "staticDataBO.ajax?cmd=selectProvince";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
						$scope.provinceData=data;
						addressQuery.query.provinceId=data.items[0].id;
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
							addressQuery.query.cityId=data.items[0].id;
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
							addressQuery.query.countyId=data.items[0].id;
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
							addressQuery.query.townId=data.items[0].id;
		 	    	}
				});
			}
		},
	};
	addressQuery.init();
}]);
