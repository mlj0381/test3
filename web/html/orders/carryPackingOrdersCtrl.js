var carryPackingOrdersApp = angular.module("carryPackingOrdersApp", ['commonApp','tableCommon']);
carryPackingOrdersApp.controller("carryPackingOrdersCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var carryPackingOrders={
		init:function(){
			this.bindEvent();		
		},
		head:[
		      {
		    	  "name":"订单号",
			      "code":"orderNo",
			      "type":"text",
			      "width":"120"
		      },
		      {
		    	  "name":"归属省市区",
			      "code":"pickStation",
			      "width":"140"
		      },
		      {
		    	  "name":"提货地址",
			      "code":"pickAddress",
			      "width":"120"
		      },
		      {
		    	  "name":"到站",
			      "code":"cityName",
			      "width":"80"
		      },
		      {
		    	  "name":"所属专线",
			      "code":"tenantName",
			      "width":"120"
		      },
		      {
		    	  "name":"拉包工",
			      "code":"workerName",
			      "type":"text",
			      "width":"120"
		      },
		      {
		    	  "name":"拉包工手机",
			      "code":"workerBill",
			      "type":"text",
			      "width":"120"
		      },
		      {
		    	  "name":"发货人",
			      "code":"consignorName",
			      "width":"120",
			      "mouseenter":"enterCallback","mouseleave":"mouseleave"
		      },
		      {
		    	  "name":"发货人手机",
			      "code":"consignorBill",
			      "width":"120"
		      },
		      {
		    	  "name":"收货人",
			      "code":"consigneeName",
			      "width":"120"
		      },
		      {
		    	  "name":"收货人手机",
			      "code":"consigneeBill",
			      "width":"120"
		      },
		      {
		    	  "name":"派单人",
			      "code":"disOpName",
			      "width":"120"
		      },
		      {
		    	  "name":"派单时间",
			      "code":"disTime",
			      "width":"120"
		      },
		      {
		    	  "name":"收货地址",
			      "code":"receiverAddress",
			      "width":"160"
		      },
		      {
		    	  "name":"服务方式",
			      "code":"serviceTypeName",
			      "width":"120"
		      },
		      {
		    	  "name":"付款方式",
			      "code":"paymentTypeName",
			      "width":"120"
		      },
		      {
		    	  "name":"下单时间",
			      "code":"doOrderTime",
			      "width":"120"
		      },
		      {
		    	  "name":"预约提货时间",
		    	  "code":"planPickupTime",
		    	  "width":"120"
		      },
		      {
		    	  "name":"实际提货时间",
		    	  "code":"pickupTime",
		    	  "width":"120"
		      },
		      {
		    	  "name":"备注",
			      "code":"remark",
			      "width":"160"
		      }
		      ],
		bindEvent:function(){
			$scope.head=carryPackingOrders.head;
			$scope.url="ordOrdersWebBO.ajax?cmd=doQuery";
			$scope.param = this.param;
			$scope.query = this.query;
			$scope.doQuery = this.doQuery;
			$scope.clear = this.clear;
		    $scope.openWayDetail=this.openWayDetail;
		    $scope.commonExport = this.commonExport;
		    $timeout(function(){
				$scope.table.mouseleave = carryPackingOrders.mouseleave;
				$scope.table.enterCallback = carryPackingOrders.enterCallback;
			},200);
		},
		mouseleave:function(x,y,data){
			$scope.isConsignor = false;
		},
		enterCallback:function(x,y,data){
			$scope.isConsignor = true;
			$scope.clientX = x;
			$scope.clientY = y;
			$scope.consignorChange.load(data.orderId);
		},
		//导出方法
		commonExport : function(){
			$scope.isTrue = true;
			$("#exportId").html("数据加载中...");
			$scope.table.downloadExcelFile();
			//导出倒计时
			$timeout(function() {
				 $scope.isTrue = false;
				$("#exportId").html("导出");
			},3600);
			
		},
		//查看详情
		openWayDetail:function(){
			var orderNo='';
			var ordArray = new Array();
            ordArray=$scope.table.getSelectData();
            if(ordArray.length==0){
				commonService.alert("请至少选择一条订单信息!");
				return false;
			}
			if(ordArray.length>1){
				commonService.alert("只能选择一条订单信息!");
				return false;
			}
			var data= ordArray[0];
			orderNo=data.orderNo;
			window.open("/orders/orderDetailInfo.html?orderNo="+orderNo);
		},
		params:{
		},
		query:{
			orderState:3
		},
		
		doQuery:function(){
//			$scope.query.provinceId = $scope.des.chooseCityId;
//			$scope.query.cityId = $scope.des.chooseRegionId;
//			$scope.query.countyId = $scope.des.chooseCountyId;
			$scope.query.beginCreateTime=document.getElementById("beginCreateTime").value;
			$scope.query.endCreateTime=document.getElementById("endCreateTime").value;
			var url = "ordOrdersWebBO.ajax?cmd=doQuery";
			$scope.table.load();
			$scope.table.callBack=function(){
				displayTable();
				setContentHeight();
			}
		},
		clear:function(){
			//$scope.des.clear();
			$scope.query={};
			document.getElementById("beginCreateTime").value='';
			document.getElementById("endCreateTime").value='';
		}
	};
	carryPackingOrders.init();
}]);
