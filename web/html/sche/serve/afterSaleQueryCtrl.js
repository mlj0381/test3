var afterSaleQueryApp = angular.module("afterSaleQueryApp", ['commonApp']);
afterSaleQueryApp.controller("afterSaleQueryCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var afterSaleQuery={
		init:function(){
			this.bindEvent();
			this.doQuery();
		},
		bindEvent:function(){
			$scope.afterSale =this.afterSale;
			$scope.doQuery = this.doQuery;
			$scope.query = this.query;
			$scope.clear = this.clear;
			$scope.afterSaleTracking = this.afterSaleTracking;
			$scope.queryOrdSale = this.queryOrdSale;
		},
		query:{
			salesState:"-1",
			salesType:"-1",
			orderState:"-1",
		},
		doQuery:function(){
			$scope.query.beginTime = document.getElementById("beginTime").value;
			$scope.query.endTime = document.getElementById("endTime").value;
			$scope.query.createTimeR = document.getElementById("createTimeR").value;
			$scope.query.createTimeE = document.getElementById("createTimeE").value;
			var url = "ordSaleTrackingBO.ajax?cmd=doQuerySales";
			$timeout(function(){
				$scope.ordSales.load({
					url:url,
					params:$scope.query,
					callBack:'setContentHegthDelay'
				});
			},500);
		},
		afterSale:function(){
			commonService.openTab("afterSale1","售后登记","/sche/serve/afterSaleAdd.html?isShow=1");
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
			document.getElementById("beginTime").value = "";
			document.getElementById("endTime").value = "";
			document.getElementById("createTimeR").value = "";
			document.getElementById("createTimeE").value = "";
			$scope.query.receivePhone="";
			$scope.query.receiveName="";
			$scope.query.trackingNum="";
			$scope.query.provinceId=-1;
			$scope.query.cityId=-1;
			$scope.query.countyId=-1;
			$scope.query.excState="-1";
			$scope.query.excType="-1";
			$scope.cityData={};
			$scope.districtData={};
			$scope.query.salesState="-1";
			$scope.query.salesType="-1";
			$scope.query.orderState="-1";
			$scope.query.recRegion = "";
			$scope.query.beginTime = "";
			$scope.query.endTime = "";
			$scope.query.createTimeR="";
			$scope.query.createTimeE="";
		},
		selectOne : function(taskId){
			if(document.getElementById("checkbox"+taskId).checked && document.getElementById("checkbox"+taskId) != undefined){
				document.getElementById("checkbox"+taskId).checked=false;
			}else{
				document.getElementById("checkbox"+taskId).checked=true;
			}
		},
		afterSaleTracking:function(){
			var selectedValue =$scope.ordSales.getSelectData();
			if(selectedValue.length<=0){
				commonService.alert("请选择一条售后信息");
				return false;
			}
			if(selectedValue.length>1){
				commonService.alert("只能选择一条售后信息");
				return false;
			}
			commonService.openTab("afterSaleAdd"+selectedValue[0].id,"售后跟踪","/sche/serve/afterSaleAdd.html?isShow=1&isView==1&salesId="+selectedValue[0].id);
		},
		queryOrdSale:function(){
			var selectedValue =$scope.ordSales.getSelectData();
			if(selectedValue.length<=0){
				commonService.alert("请选择一条售后信息");
				return false;
			}
			if(selectedValue.length>1){
				commonService.alert("只能选择一条售后信息");
				return false;
			}
			commonService.openTab("afterSaleQuery"+selectedValue[0].id,"售后详情","/sche/serve/afterSaleAdd.html?isShow=1&isSales=1&salesId="+selectedValue[0].id);
		}
	};
	afterSaleQuery.init();
}]);
