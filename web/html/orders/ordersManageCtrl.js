var ordersManageApp = angular.module("ordersManageApp", ['commonApp','tableCommon']);
ordersManageApp.controller("ordersManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var ordersManage={
		init:function(){
			this.bindEvent();	
			this.queryTenantInfo();
		},
		head:[
		      { "name":"订单号","code":"orderNo","type":"text","width":"120"},
		      {"name":"订单状态","code":"orderStateName","width":"100" },
		      {"name":"运单号","code":"trackingNum","type":"text","width":"120","clickLocation":"clickCallback"},
		      {"name":"下单人","code":"createName","type":"text","width":"120"},
		      {"name":"下单时间","code":"createDate","type":"text","width":"120"},
		      {"name":"开单时间","code":"createTime", "type":"text","width":"120"},
		      {"name":"开单网点","code":"orgName","type":"text","width":"80"},
		      {"name":"开单物流","code":"companyName","type":"text","width":"120"},
		      {"name":"到站","code":"cityName","type":"text", "width":"120"},
		      {"name":"拉包工","code":"workerName","type":"text","width":"120"},
		      {"name":"拉包工手机","code":"workerBill","type":"text","width":"120"},
		      {"name":"发货人","code":"consignorName","type":"text","width":"120","mouseenter":"enterCallback","mouseleave":"mouseleave","type":"text"},
		      {"name":"发货人手机","code":"consignorBill","type":"text","width":"120"},
		      {"name":"发货地址","code":"pickAddress","width":"160"},
		      {"name":"收货人","code":"consigneeName","type":"text","width":"120"},
		      {"name":"收货人手机","code":"consigneeBill","type":"text","width":"120"},
		      {"name":"收货地址","code":"receiverAddress","width":"160"},
		      {"name":"服务方式","code":"serviceTypeName","type":"text","width":"120"},
		      {"name":"付款方式","code":"paymentTypeName","type":"text","width":"120"},
		      {"name":"品名","code":"productName","type":"text","width":"120"},
		      {"name":"件数","code":"count","type":"text","isSum":"true","width":"120"},
		      {"name":"包装","code":"packingName", "type":"text","width":"120"},
		      {"name":"重量","code":"weight","type":"text","isSum":"true","width":"120"},
		      {"name":"体积","code":"volume","type":"text","isSum":"true","width":"120"},
		      {"name":"运费","code":"freight","type":"text","isSum":"true","width":"120"},
		      {"name":"声价","code":"reputation","type":"text","isSum":"true","width":"120"},
		      {"name":"保费","code":"premium","type":"text","isSum":"true","width":"120"},
		      {"name":"送货费","code":"deliveryCharge", "type":"text","isSum":"true","width":"120"},
		      {"name":"中转费","code":"transitFee","type":"text","isSum":"true","width":"120"},
		      {"name":"小费","code":"tipsMonery","type":"text","isSum":"true","width":"120"},
		      {"name":"代收货款","code":"collectMoney","type":"text","isSum":"true","width":"120"},
		      {"name":"落地费", "code":"landFee","type":"text","isSum":"true","width":"120"},
		      {"name":"服务费","code":"serviceCharge","type":"text","isSum":"true","width":"120"},
		      {"name":"包装费","code":"pickFee","type":"text","isSum":"true","width":"120"},
		      {"name":"其它费用","code":"otherFee","type":"text","isSum":"true","width":"120"},
		      {"name":"总费用", "code":"totalFee","type":"text","isSum":"true","width":"120"},
		      {"name":"备注","code":"remark","type":"text","width":"160"},
		      {"name":"开单人","code":"inputUserName","type":"text","width":"120"}
		      ],
		bindEvent:function(){
			$scope.head=ordersManage.head;
			$scope.url="ordOrdersWebBO.ajax?cmd=queryOrders";
			$scope.param = this.param;
			$scope.query = this.query;
			$scope.doQuery = this.doQuery;
			$scope.clear = this.clear;
		    $scope.openWayDetail=this.openWayDetail;
		    $scope.commonExport = this.commonExport;
		    $timeout(function(){
				$scope.table.mouseleave = ordersManage.mouseleave;
				$scope.table.enterCallback = ordersManage.enterCallback;
				$scope.table.clickCallback = ordersManage.clickCallback;
			},200);
		    $scope.queryTenantInfo=this.queryTenantInfo;
		},
		queryTenantInfo:function(){
			commonService.postUrl("staticDataBO.ajax?cmd=queryTenantInfo","",function(data){
				$scope.tenantData=data.items;
				if(data.items.length==2)
				{
					$scope.query.tenantId=data.items[1].tenantId;
				}else{
					$scope.query.tenantId=data.items[0].tenantId;
				}
			});
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
		clickCallback:function(data){
			console.log(data);
			window.open("/orders/orderDetailInfo.html?orderNo="+data.orderNo);
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
			orderState:"-1"
		},
		
		doQuery:function(){
//			$scope.query.provinceId = $scope.des.chooseCityId;
//			$scope.query.cityId = $scope.des.chooseRegionId;
//			$scope.query.countyId = $scope.des.chooseCountyId;
			$scope.query.beginCreateTime=document.getElementById("beginCreateTime").value;
			$scope.query.endCreateTime=document.getElementById("endCreateTime").value;
			$scope.query.beginInputTime=document.getElementById("beginInputTime").value;
			$scope.query.endInputTime=document.getElementById("endInputTime").value;
			//$scope.query.createName=document.getElementById("createName").value;
			$scope.query.page=1;
			$scope.tableCallBack=function(){
				console.log($scope.query.orderState);
				$scope.paramsExport = JSON.stringify($scope.query);
			}
			$scope.table.load();
			$scope.table.callBack=function(){
				displayTable();
				setContentHeight();
			}
		},
		clear:function(){
			//$scope.des.clear();
			$scope.query={};
			$scope.query.orderState='-1';
			document.getElementById("beginCreateTime").value='';
			document.getElementById("endCreateTime").value='';
			document.getElementById("beginInputTime").value='';
			document.getElementById("endInputTime").value='';
			//document.getElementById("createName").value='';
		}
	};
	ordersManage.init();
}]);
