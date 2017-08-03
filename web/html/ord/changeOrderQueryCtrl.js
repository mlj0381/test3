var changeOrderQueryApp = angular.module("changeOrderQueryApp", ['commonApp','tableCommon']);
changeOrderQueryApp.controller("changeOrderQueryCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.query = {};
	var proveManage={
		init:function(){
			this.bindEvent();
		},
		query:{},
		head:[
		      {"name": "订单号","code": "orderNo","width": "110","type":""},
		      {"name": "运单号","code": "orderNumber","width": "110","type":""},
		      {"name": "开单时间","code": "createDataString","width": "110","type":""},
		      {"name": "开单网点","code": "billingOrgName","width": "110","type":""},
		      {"name": "开单物流","code": "ordTenantName","width": "110", "type":""},
		      {"name": "客户选择物流","code": "ordsTenantName","width": "90", "type":""},
		      {"name": "到站","code": "cityName","width": "110", "type":""},
		      {"name": "拉包工","code": "pullName","width": "110", "type":""},
		      {"name": "拉包工电话","code": "pullPhone","width": "110", "type":""},
		      {"name": "发货人","code": "consignor","width": "110","type":""},
		      {"name": "发货人号码","code": "consignorPhone","width": "110","type":""},
		      {"name": "发货地址","code": "consignorAddress","width": "110","type":""},
		      {"name": "小费","code": "tipString","isSum":"true","width": "110", "type":"","number": "2"}
		      ],
		bindEvent:function(){
			$scope.head = proveManage.head;
			$scope.url="orderInfoYQBO.ajax?cmd=queryOrderInfo";
			$scope.urlParam=$scope.query;
			$scope.doQuery = this.doQuery;
			$scope.cleanQuery=this.cleanQuery;
		},
		doQuery:function(){
			$scope.query.billingBegin = document.getElementById("billingBegin").value;
			$scope.query.billingEnd = document.getElementById("billingEnd").value;
			var url = "orderInfoYQBO.ajax?cmd=queryOrderInfo";
			$scope.query.page=1;
			$scope.tableCallBack=function(){
				$scope.paramsExport = JSON.stringify($scope.query);
			}
			$scope.page.load();
			$scope.page.callBack=function(){
				displayTable();
				setContentHeight();
			}
		},
		cleanQuery:function(){
			document.getElementById("billingBegin").value ="";
			document.getElementById("billingEnd").value ="";
			$scope.query.billingBegin = "";
			$scope.query.billingEnd = "";
			$scope.query.ordTenantName = "";
			$scope.query.ordsTenantName = "";
			$scope.query.pullName = "";
		}
	};
	proveManage.init();
}]);
