function changeTabCallback(){
	var appElement = document.querySelector('[ng-controller=cmCarrierCompanyCtrl]');
	var scope=angular.element(appElement).scope();
	scope.doQuery();
	scope.$apply();
}
var cmCarrierCompanyApp = angular.module("cmCarrierCompanyApp", ['commonApp']);
cmCarrierCompanyApp.controller("cmCarrierCompanyCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var ord ={
		init:function(){
			this.bindEvent();
			//this.selectOrg();
			this.doQuery();
			this.selectProvince();
			
		},
		bindEvent:function(){
			$scope.selectOne = this.selectOne;
			$scope.selectAll = this.selectAll;
			$scope.toSave = this.toSave;
			$scope.doQuery = this.doQuery;
			$scope.doSave = this.doSave;
			$scope.close = this.close;
			$scope.data = this.data;
			$scope.selectOrg = this.selectOrg;
			$scope.doDel = this.doDel;
			$scope.params = this.params;
			$scope.toUpdate = this.toUpdate;
			$scope.selectCity = this.selectCity;
			$scope.selectDistrict = this.selectDistrict;
			$scope.clear = this.clear;
		},
		data:{
			orgId:-1
		},
		params:{
			
		},
		clear:function(){
			$scope.data.provinceId = {};
			$scope.data.regionId = {};
			$scope.data.countyId = {};
		},
		//查询事件
		doQuery:function(){
			var url = "organizationBO.ajax?cmd=doQueryCmC";
			$scope.data.page=1;
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:$scope.data,
							callBack:'setContentHegthDelay'
						});
			},500);
		},
		//查询所有网点
		/*selectOrg:function(){
			commonService.postUrl("organizationBO.ajax?cmd=getAllOrg","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.orgInfo = data;
					$scope.orgInfo.items.unshift({orgId:-1,orgName:"全部"});
				}
			});
		},*/
		/**选择一行**/
		/*selectOne : function(userId){
			if(document.getElementById("checkbox"+userId).checked && document.getElementById("checkbox"+userId) != undefined){
				document.getElementById("checkbox"+userId).checked=false;
			}else{
				document.getElementById("checkbox"+userId).checked=true;
			}
		},*/
		/**全选*/
		/*selectAll : function() {
			var checkbox = document.getElementsByName("check-1");
			if (document.getElementById("checkbox").checked) {
				for (var i = 0; i < checkbox.length; i++) {
					checkbox[i].checked = true;
				}
			} else {
				for (var i = 0; i < checkbox.length; i++) {
					checkbox[i].checked = false;
				}
			}
		},*/
		toSave:function(){
			commonService.openTab("11223","新增承运商","/cm/addCmCarrierCompany.html");
			//location.href="addCmCustomer.html";
		},
		doDel:function(){
			var selectedDate=$scope.page.getSelectData();
			if(selectedDate==undefined || selectedDate.length == 0){
				commonService.alert("请选择一条数据！");
				return false;
			}
	        commonService.confirm("确认删除数据?",function(){
		         commonService.postUrl("organizationBO.ajax?cmd=delCmC",{"orgId":selectedDate[0].orgId},function(data){
		        	 $scope.doQuery();
		        	 commonService.alert("操作完成");
				});
	         });
		},
		toUpdate:function(){
			var selectedDate=$scope.page.getSelectData();
			if(selectedDate==undefined || selectedDate.length == 0){
				commonService.alert("请选择一条数据！");
				return false;
			}
			commonService.openTab(selectedDate[0].orgId+"2","修改","/cm/addCmCarrierCompany.html?orgId="+selectedDate[0].orgId);
			//location.href="addCmCustomer.html?id="+id;
		},
		
		selectProvince:function(){
			commonService.postUrl("staticDataBO.ajax?cmd=selectProvince","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.provinces = data.items;
				}
			});
		},
		
		selectCity:function(provinceId){
			if(provinceId > 0){
				var param={provinceId:provinceId};
				commonService.postUrl("staticDataBO.ajax?cmd=selectCity",param,function(data){
					if(data != null && data != undefined && data != ""){
						$scope.citys = data.items;
					}
				});
			}else{
				$scope.citys = {};
				$scope.districts={};
				$scope.data.regionId = {};
				$scope.data.countyId = {};
			}
		},
		selectDistrict:function(cityId){
			if(cityId > 0){
				var param={cityId:cityId};
				commonService.postUrl("staticDataBO.ajax?cmd=selectDistrict",param,function(data){
					if(data != null && data != undefined && data != ""){
						$scope.districts = data.items;
					}
				});
			}else{
				$scope.districts = {};	
				$scope.data.countyId = {};
			}
		},
	};
	ord.init();
}]);