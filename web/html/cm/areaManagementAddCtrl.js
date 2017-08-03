var areaManagementAddApp = angular.module("areaManagementAddApp", ['commonApp','tableCommon','angucompleteYQ']);
areaManagementAddApp.controller("areaManagementAddCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	$scope.data = {};
	$scope.selectVehicle = {};
	var id = getQueryString("id");
	var areaManagementAddApp ={
		init:function(){
			this.bindEvent();
			if (id != undefined && id != null && id != "") {
				this.getCmArea(id);
			}
		},
		bindEvent:function(){
			$scope.getAddressByCode = this.getAddressByCode;
			$scope.userCallBack = this.userCallBack;
			$scope.doSave = this.doSave;
			$scope.close = this.close;
		},
		getAddressByCode:function(){
			//$scope.data.address = $("#address_select_value").val();
			var url = "cmAreaBO.ajax?cmd=getAddressByCode";
			var param = {
					"_GRID_TYPE":"jq",
					"page":1,
					"rows":10,
					address :$scope.gain.inputValue.cityName+$scope.gain.inputValue.countyName+$("#address_select_value").val(),
					provinceName:$scope.gain.inputValue.provinceName
			};
			if(param.address != null && param.address != undefined && param.address != '' && param.address != $scope.searchPlate && param.provinceName !=null && param.provinceName != undefined){
				commonService.postUrl(url,param,function(data){
					if(data != null && data != undefined && data != ""){
						$scope.searchPlate = param.address;
						var aa=new Array();
						for(var i=0;i<data.rows.length;i++){
							aa.push(data.rows[i]);
						}
						if(aa.length <= 0){
							//commonService.alert("无该详细地址！");
							$scope.query={};
							$scope.addressData=[];
						}else{
							$scope.addressData = aa;
							$scope.userCallBack(aa[0]);
						}
					}
				});
				
			}else{
				$scope.addressData = new Array();
			}
		},
		userCallBack:function(name){
			$scope.data.latAndLng = name.lat+","+name.lng;
			$scope.data.longitude = name.lng;
			$scope.data.latitude = name.lat;
			$scope.data.address = name.name;
			if ($scope.gain.inputValue.countyName != name.district || $scope.gain.inputValue.cityName != name.cityName) {
				$scope.gain.auto(name.cityName,name.districtName);
			}
		},
		doSave:function(){
			$scope.data.province = $scope.gain.inputValue.provinceId;
			$scope.data.city = $scope.gain.inputValue.cityId;
			$scope.data.district = $scope.gain.inputValue.countyId;
			if ($scope.data.province == undefined || $scope.data.province == null || $scope.data.province == "") {
				commonService.alert("请选择省份！");
				return;
			}
			if ($scope.data.city == null || $scope.data.city == undefined || $scope.data.city == "") {
				commonService.alert("请选择城市！");
				return;
			}
			if ($scope.data.district == undefined || $scope.data.district == null || $scope.data.district == "") {
				commonService.alert("请选择区域！");
				return;
			}
			if ($scope.data.clothCityName == undefined || $scope.data.clothCityName == null || $scope.data.clothCityName == "") {
				commonService.alert("请填写服装城名称！");
				return;
			}
			if ($scope.data.keyWords == undefined || $scope.data.keyWords == null || $scope.data.keyWords == "") {
				commonService.alert("请填写关键字！");
				return;
			}
			if ($scope.data.latitude == undefined || $scope.data.latitude == null || $scope.data.latitude == "") {
				commonService.alert("请选择纬度！");
				return;
			}
			if ($scope.data.longitude == undefined || $scope.data.longitude == null || $scope.data.longitude == "") {
				commonService.alert("请选择经度！");
				return;
			}
			if ($scope.data.address == undefined || $scope.data.address == null || $scope.data.address == "" ) {
				commonService.alert("请选择详细地址！");
				return;
			}
			commonService.postUrl("cmAreaBO.ajax?cmd=doSaveOrUpdateArea",$scope.data,function(data){
				if (data == "Y") {
					commonService.alert("保存成功！");
//					$scope.data = {};
//					$("#address_select_value").val("");
					$scope.close();
				}else{
					commonService.alert("保存失败！");
				}
			});
			
		},
		close:function(){
			//$scope.data = {};
			commonService.closeToParentTab(true);
		},
		getCmArea:function(id){
			commonService.postUrl("cmAreaBO.ajax?cmd=getCmArea",{"id":id},function(data){
				$scope.data = data;
				$("#address_select_value").val(data.address);
				$scope.gain.initData(data.province,data.provinceName,data.city,data.cityName,data.district,data.districtName);
			});
		}
	};
	areaManagementAddApp.init();
}]);