var waitDepartOrdersApp = angular.module("waitDepartOrdersApp", ['commonApp','tableCommon']);
waitDepartOrdersApp.controller("waitDepartOrdersCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var waitDepartOrders={
		init:function(){
			this.bindEvent();	
			$scope.childShow=false;
			$scope.show=false;
		},
		head:[
			     {"name":"订单号", "code":"orderNo","type":"text","width":"120"},
			      {"name":"运单号","code":"trackingNum","type":"text", "width":"120","clickLocation":"clickCallback","css":"text_m"},
			      {"name":"归属省市区","code":"pickStation","width":"140"},
			      {"name":"提货地址","code":"pickAddress","width":"120"},
			      {"name":"到站","code":"cityName","width":"120"},
			      {"name":"所属专线","code":"tenantName", "width":"120"},
			      {"name":"拉包工","code":"workerName","type":"text","width":"120"},
			      {"name":"拉包工手机","code":"workerBill","type":"text","width":"120"},
			      {"name":"发货人","code":"consignorName","width":"120","mouseenter":"enterCallback","mouseleave":"mouseleave","type":"text"},
			      {"name":"发货人手机","code":"consignorBill","width":"120"},
			      {"name":"发货地址","code":"pickAddress","width":"160"},
			      {"name":"收货人","code":"consigneeName","width":"120"},
			      {"name":"收货人手机","code":"consigneeBill","width":"120"},
			      {"name":"收货地址","code":"receiverAddress","width":"160"},
			      {"name":"服务方式","code":"serviceTypeName","width":"120"},
			      {"name":"付款方式","code":"paymentTypeName","width":"120"},
			      {"name":"派单人","code":"disOpName","width":"120"},
			      {"name":"派单时间","code":"disTime","width":"120"},
			      {"name":"下单时间","code":"doOrderTime","type":"text","width":"120"},
			      {"name":"预约提货时间","code":"planPickupTime","width":"120"},
			      {"name":"提货时间","code":"pickupTime","width":"120"},
				  {"name":"开单人","code":"inputUserName","width":"140"},
				  {"name":"开单时间","code":"inputTime","width":"120"},
				  {"name":"备注","code":"remark","width":"160"}
		      ],
		bindEvent:function(){
			$scope.head=waitDepartOrders.head;
			$scope.url="ordOrdersWebBO.ajax?cmd=doQuery";
			$scope.param = this.param;
			$scope.query = this.query;
			$scope.doQuery = this.doQuery;
			$scope.clear = this.clear;
		    $scope.openWayDetail=this.openWayDetail;
		    $scope.commonExport = this.commonExport;
		    $scope.params = this.params;
		    $timeout(function(){
				$scope.table.mouseleave = waitDepartOrders.mouseleave;
				$scope.table.enterCallback = waitDepartOrders.enterCallback;
				$scope.table.clickCallback = waitDepartOrders.clickCallback;
			},200);
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
		mouseleave:function(x,y,data){
			$scope.isConsignor = false;
		},
		clickCallback:function(data){
			console.log(data);
			var trackingNum="";
			var orderNo="";
			trackingNum=data.trackingNum;
			orderNo=data.orderNo;
			window.open("/tracks/trackDetailInfo.html?trackingNum="+trackingNum+"&orderNo="+orderNo);
			/*window.open("/orders/orderDetailInfo.html?orderNo="+data.orderNo);*/
		},
		//查看详情
		openWayDetail:function(){
			var trackingNum='';
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
			orderState:10
		},
		doQuery:function(){
			$scope.query.beginCreateTime=document.getElementById("beginCreateTime").value;
			$scope.query.endCreateTime=document.getElementById("endCreateTime").value;
			$scope.query.beginInputTime=document.getElementById("beginInputTime").value;
			$scope.query.endInputTime=document.getElementById("endInputTime").value;
			$scope.query.page=1;
			/*var url = "ordOrdersWebBO.ajax?cmd=queryWaitDeparts";*/
			var url = "ordOrdersWebBO.ajax?cmd=doQuery";
			$scope.table.load();
			$scope.table.callBack=function(){
				displayTable();
				setContentHeight();
			}
		},
		clear:function(){
			$scope.query={};
			$scope.query.orderState=3;
			document.getElementById("beginCreateTime").value='';
			document.getElementById("endCreateTime").value='';
			document.getElementById("beginInputTime").value='';
			document.getElementById("endInputTime").value='';
		}
	};
	waitDepartOrders.init();
}]);
