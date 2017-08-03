var branchFeeApp = angular.module("branchFeeApp", ['commonApp']);
branchFeeApp.controller("branchFeeCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var branchFee={
		init:function(){
			this.bindEvent();
			$scope.flag=getQueryString("flag");
			$scope.branchId=getQueryString("branchId");
			if($scope.branchId!=null&&$scope.branchId!=undefined&&$scope.branchId!=''){
				$scope.toView($scope.branchId);
			}else{
				this.selectProvince();
			}
		},
		bindEvent:function(){
			$scope.param = this.param;
			$scope.query = this.query;
		    $scope.doSave = this.doSave;
		    $scope.toView = this.toView;
		    $scope.selectCity=this.selectCity;
		    $scope.selectProvince=this.selectProvince;
		    $scope.selectDistrict=this.selectDistrict;
		    $scope.selectStreet=this.selectStreet;
		    $scope.close=this.close;
		},
		params:{
		},
		query:{
			provinceId:-1
		},
		doSave:function(){
			if($scope.query.provinceId==null||$scope.query.provinceId==undefined||$scope.query.provinceId==-1||$scope.query.provinceId==''){
				commonService.alert("请选择省份!");
				return false;
			}
			if($scope.query.regionId==null||$scope.query.regionId==undefined||$scope.query.regionId==-1||$scope.query.regionId==''){
				commonService.alert("请选择城市!");
				return false;
			}
			if($scope.query.countyId==null||$scope.query.countyId==undefined||$scope.query.countyId==-1||$scope.query.countyId==''){
				commonService.alert("请选择县区!");
				return false;
			}
			if($scope.query.townId==null||$scope.query.townId==undefined||$scope.query.townId==-1||$scope.query.townId==''){
				commonService.alert("请选择乡镇!");
				return false;
			}
			if($scope.query.priceUnit==null||$scope.query.priceUnit==undefined||$scope.query.priceUnit==''){
				commonService.alert("请填写支线费用!");
				return false;
			}
			if($scope.query.exceedVolume==null||$scope.query.exceedVolume==undefined||$scope.query.exceedVolume==''){
				commonService.alert("请填写超方标准!");
				return false;
			}
			if($scope.query.exceedVolumePrice==null||$scope.query.exceedVolumePrice==undefined||$scope.query.exceedVolumePrice==''){
				commonService.alert("请填写超方价格!");
				return false;
			}
			if($scope.query.exceedDisPrice==null||$scope.query.exceedDisPrice==undefined||$scope.query.exceedDisPrice==''){
				commonService.alert("请填写超远价格!");
				return false;
			}
			if($scope.query.exceedDis==null||$scope.query.exceedDis==undefined||$scope.query.exceedDis==''){
				commonService.alert("请填写超远标准!");
				return false;
			}
			if($scope.query.exceedDis<1){
				commonService.alert("超远标准不能低于1KM!");
				return false;
			}
			if($scope.query.exceedDisPrice<1){
				commonService.alert("超远价格不能低于1元!");
				return false;
			}
			if($scope.query.exceedVolume<1){
				commonService.alert("超方标准不能低于1立方米!");
				return false;
			}
			if($scope.query.exceedVolumePrice<1){
				commonService.alert("超方价格不能低于1元!");
				return false;
			}
			var url = "acBranchFeeBO.ajax?cmd=doSave";
			commonService.postUrl(url,$scope.query,function(data){
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("操作成功!");
					$scope.query={};
	 	    	}
			});
		},
		toView:function(branchId){
			var param = {"branchId":branchId};
			var url = "acBranchFeeBO.ajax?cmd=toView";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
						$scope.query=data;
						$scope.selectProvince();
	 	    	}
			});
		},
		selectProvince:function(){
			var param = {};
			var url = "staticDataBO.ajax?cmd=selectProvince";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
						$scope.provinceData=data;
						if($scope.query.provinceId==null&&$scope.query.provinceId==undefined&&$scope.query.provinceId==''){
							$scope.query.provinceId=data.items[0].id;
						}
						$scope.selectCity($scope.query.provinceId);
	 	    	}
			});
		},
		selectCity:function(provinceId){
			if(parseInt(provinceId)>0){
				var param = {"provinceId":provinceId};
				var url = "staticDataBO.ajax?cmd=selectCity";
				commonService.postUrl(url,param,function(data){
					// 锟缴癸拷执锟斤拷
					if(data!=null && data!=undefined && data!=""){
							$scope.cityData=data;
							if($scope.query.regionId==null&&$scope.query.regionId==undefined&&$scope.query.regionId==''){
								$scope.query.regionId=data.items[0].id;
							}
							$scope.selectDistrict($scope.query.regionId);
		 	    	}
				});
			}
		},
		selectDistrict:function(cityId){
			if(parseInt(cityId)>0){
				var param = {"cityId":cityId};
				var url = "staticDataBO.ajax?cmd=selectDistrict";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
							$scope.districtData=data;
							if($scope.query.countyId==null&&$scope.query.countyId==undefined&&$scope.query.countyId==''){
								$scope.query.countyId=data.items[0].id;
							}
							$scope.selectStreet($scope.query.countyId);
		 	    	}
				});
			}
		},
		selectStreet:function(districtId){
			if(parseInt(districtId)>0){
				var param = {"districtId":districtId};
				var url = "staticDataBO.ajax?cmd=selectStreet";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
							$scope.streetData=data;
							if($scope.query.townId==null&&$scope.query.townId==undefined&&$scope.query.townId==''){
								$scope.query.townId=data.items[0].id;
							}
		 	    	}
				});
			}
		},
		close:function(){
    		commonService.closeToParentTab();
    	}
	};
	branchFee.init();
}]);
