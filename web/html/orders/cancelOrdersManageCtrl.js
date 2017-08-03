var cancelOrdersManageApp = angular.module("cancelOrdersManageApp", ['commonApp','tableCommon']);
cancelOrdersManageApp.controller("cancelOrdersManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var cancelOrdersManage={
		init:function(){
			this.bindEvent();
			this.queryTenantInfo();
			this.queryOrgInfo();
		},
		head:[
		      { "name":"订单号","code":"orderNo","type":"text","width":"120"},
		      {"name":"订单状态","code":"orderStateName","width":"100" },
		      {"name":"运单号","code":"trackingNum","type":"text","width":"120" },
		      {"name":"下单人","code":"createName","type":"text","width":"120"},
		      {"name":"下单时间","code":"createDate","width":"120"},
		      {"name":"开单时间","code":"createTime", "width":"120"},
		      {"name":"取消时间","code":"opTime", "width":"120"},
		      {"name":"开单网点","code":"orgName","width":"80"},
		      {"name":"开单物流","code":"companyName","type":"text","width":"120"},
		      {"name":"到站","code":"cityName", "width":"120"},
		      {"name":"拉包工","code":"workerName","type":"text","width":"120"},
		      {"name":"拉包工手机","code":"workerBill","type":"text","width":"120"},
		      {"name":"发货人","code":"consignorName","width":"120","mouseenter":"enterCallback","mouseleave":"mouseleave","type":"text"},
		      {"name":"发货人手机","code":"consignorBill","type":"text","width":"120"},
		      {"name":"发货地址","code":"pickAddress","width":"160"},
		      {"name":"收货人","code":"consigneeName","type":"text","width":"120"},
		      {"name":"收货人手机","code":"consigneeBill","type":"text","width":"120"},
		      {"name":"收货地址","code":"receiverAddress","width":"160"},
		      {"name":"服务方式","code":"serviceTypeName","width":"120"},
		      {"name":"付款方式","code":"paymentTypeName","width":"120"},
		      {"name":"品名","code":"productName","width":"120"},
		      {"name":"件数","code":"count","isSum":"true","width":"120"},
		      {"name":"包装","code":"packingName", "width":"120"},
		      {"name":"重量","code":"weight","isSum":"true","width":"120"},
		      {"name":"体积","code":"volume","isSum":"true","width":"120"},
		      {"name":"运费","code":"freight","isSum":"true","width":"120"},
		      {"name":"声价","code":"reputation","isSum":"true","width":"120"},
		      {"name":"保费","code":"premium","isSum":"true","width":"120"},
		      {"name":"送货费","code":"deliveryCharge", "isSum":"true","width":"120"},
		      {"name":"中转费","code":"transitFee","isSum":"true","width":"120"},
		      {"name":"小费","code":"tipsMonery","isSum":"true","width":"120"},
		      {"name":"代收货款","code":"collectMoney","isSum":"true","width":"120"},
		      {"name":"落地费", "code":"landFee","isSum":"true","width":"120"},
		      {"name":"服务费","code":"serviceCharge","isSum":"true","width":"120"},
		      {"name":"包装费","code":"pickFee","isSum":"true","width":"120"},
		      {"name":"其它费用","code":"otherFee","isSum":"true","width":"120"},
		      {"name":"总费用", "code":"totalFee","isSum":"true","width":"120"},
		      {"name":"备注","code":"remark","width":"160"},
		      {"name":"开单人","code":"inputUserName","type":"text","width":"120"}
		      ],
		bindEvent:function(){
			$scope.head=cancelOrdersManage.head;
			$scope.url="ordOrdersWebBO.ajax?cmd=queryOrders";
			$scope.param = this.param;
			$scope.query = this.query;
			$scope.doQuery = this.doQuery;
			$scope.clear = this.clear;
		    $scope.openWayDetail=this.openWayDetail;
		    $scope.commonExport = this.commonExport;
		    $timeout(function(){
				$scope.table.mouseleave = cancelOrdersManage.mouseleave;
				$scope.table.enterCallback = cancelOrdersManage.enterCallback;
			},200);
		    $scope.queryTenantInfo=this.queryTenantInfo;
		    $scope.queryOrgInfo=this.queryOrgInfo;
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
		queryOrgInfo:function(){
			commonService.postUrl("staticDataBO.ajax?cmd=queryOrganization","",function(data){
				$scope.orgData=data;
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
			orderState:"-2"
		},

		doQuery:function(){
			$scope.query.beginCreateTime=document.getElementById("beginCreateTime").value;
			$scope.query.endCreateTime=document.getElementById("endCreateTime").value;
			$scope.query.beginInputTime=document.getElementById("beginInputTime").value;
			$scope.query.endInputTime=document.getElementById("endInputTime").value;
			$scope.query.beginCancelTime=document.getElementById("beginCancelTime").value;
            $scope.query.endCancelTime=document.getElementById("endCancelTime").value;
			//$scope.query.createName=document.getElementById("createName").value;
			$scope.query.page=1;
			$scope.tableCallBack=function(){
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
			$scope.query.orderState='-2';
			document.getElementById("beginCreateTime").value='';
			document.getElementById("endCreateTime").value='';
			document.getElementById("beginInputTime").value='';
			document.getElementById("endInputTime").value='';
			document.getElementById("beginCancelTime").value='';
            document.getElementById("endCancelTime").value='';
		}
	};
	cancelOrdersManage.init();
}]);
