var excQueryApp = angular.module("excQueryApp", ['commonApp']);
excQueryApp.controller("excQueryCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.query={};
	$scope.cityData=[];
	$scope.provinceData=[];
	$scope.districtData=[];
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
		    $scope.selectAll = this.selectAll;
		    $scope.selectOne = this.selectOne;
		    $scope.selectCity=this.selectCity;
		    $scope.selectProvince=this.selectProvince;
		    $scope.selectDistrict=this.selectDistrict;
		    $scope.selectStreet=this.selectStreet;
		    $scope.addException=this.addException;
		},
		selectProvince:function(){
			var url = "staticDataBO.ajax?cmd=selectProvince";
			commonService.postUrl(url,"",function(data){
				if(data!=null && data!=undefined && data!=""){
						$scope.provinceData=data.items;
	 	    	}
			});
		},
		selectCity:function(){
			var param = {"provinceId":$scope.query.provinceId};
			var url = "staticDataBO.ajax?cmd=selectCity";
			commonService.postUrl(url,param,function(data){
				$scope.query.cityId=-1;
 	    		$scope.query.countyId=-1;
				if(data!=null && data!=undefined && data!="" && data.items.length>0){
					$scope.cityData=data.items;
	 	    	}else{
	 	    		$scope.cityData=[];
	 	    		$scope.districtData=[];
	 	    	}
			});
		},
		selectDistrict:function(){
			var param = {"cityId":$scope.query.cityId};
			var url = "staticDataBO.ajax?cmd=selectDistrict";
			commonService.postUrl(url,param,function(data){
				$scope.query.countyId=-1;
				if(data!=null && data!=undefined && data!="" && data.items.length>0){
					$scope.districtData=data.items;
	 	    	}else{
	 	    		$scope.districtData=[];
	 	    	}
			});
		},
		doQuery:function(){
			var url = "exceptionInfoBO.ajax?cmd=doQueryForServe";
			$timeout(function(){
				$scope.page.load({
					url:url,
					params:$scope.query,
					callBack:'setContentHegthDelay'
				});
			},500);
		},
		params:{
			taskState:4
		},
		query:{
			provinceId:-1,
		},
		selectAll : function() {
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
		selectOne : function(taskId){
			if(document.getElementById("checkbox"+taskId).checked && document.getElementById("checkbox"+taskId) != undefined){
				document.getElementById("checkbox"+taskId).checked=false;
			}else{
				document.getElementById("checkbox"+taskId).checked=true;
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
		addException:function(){
			commonService.openTab("exc","异常登记","/sche/exc/excRegister.html?isShow=1");
		}
	};
	excQuery.init();
}]);
