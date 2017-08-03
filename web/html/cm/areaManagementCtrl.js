var areaManagementApp = angular.module("areaManagementApp", ['commonApp','tableCommon']);
areaManagementApp.controller("areaManagementCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	$scope.query={};
	var ord ={
		init:function(){
			//this.doQuery();
			this.bindEvent();
			this.getProvinceData();
		},
		head:[
		      {name:"服装城名称",code:"clothCityName"},
		      {name:"省市区",code:"provinceCityDistrict"},
		      {name:"详细地址",code:"address"},
		      {name:"关键字",code:"keyWords"},
		      {name:"经纬度",code:"latAndLng"}
		      ],
		bindEvent:function(){
			$scope.url = "cmAreaBO.ajax?cmd=getCmAreaList";
			$scope.head = ord.head;
			$scope.urlParam=$scope.query;
			$scope.doQuery = this.doQuery;
			$scope.newAdd = this.newAdd;
			$scope.update = this.update;
			$scope.getProvinceData = this.getProvinceData;//获取省份数据
			$scope.getCityData = this.getCityData;//获取城市信息
			$scope.getDistrictData = this.getDistrictData;//获取区域信息
			$scope.clear = this.clear;
			$scope.delOrg = this.delOrg;
		},
		doQuery:function(){
			var url = "cmAreaBO.ajax?cmd=getCmAreaList";
			//$scope.query.page=1;
			//$scope.urlParam.page = 1;
			$scope.tableCallBack=function(){
				$scope.paramsExport = JSON.stringify($scope.query);
			}
			$scope.page.load();
			$scope.page.callBack=function(){
				displayTable();
				setContentHeight();
			}
		},
		newAdd:function(){
			commonService.openTab("areaManagement","新增区域","/cm/areaManagementAdd.html");
		},
		update:function(){
			var selectData = $scope.page.getSelectData();
			if (selectData == undefined || selectData.length == 0 || selectData.length > 1 ) {
				commonService.alert("请选择一条数据！");
			}
			commonService.openTab("areaManagement"+selectData[0].id,"修改区域","/cm/areaManagementAdd.html?id="+selectData[0].id);
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
		clear:function(){
			$scope.query.province = "";
			$scope.query.city = "";
			$scope.query.district = "";
			$scope.query.keyWords = "";
			$scope.query.clothCityName = "";
		},
		delOrg:function(){
			var selectData = $scope.page.getSelectData();
			if (selectData == undefined || selectData.length == 0) {
				commonService.alert("请选择数据！");
			}
			var ids = "";
			for(var i=0;i<selectData.length;i++){
				ids = ids+selectData[i].id+",";
			}
			commonService.confirm("确认要删除该区域!",function(){
				commonService.postUrl("cmAreaBO.ajax?cmd=delCmArea",{"ids":ids},function(data){
					if (data!=null&&data!=undefined&&data!="") {
						commonService.alert("删除成功！");
						$scope.doQuery();
					}
				});
			});
		}
	};
	ord.init();
}]);