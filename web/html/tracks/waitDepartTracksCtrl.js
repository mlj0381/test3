var waitDepartTracksApp = angular.module("waitDepartTracksApp", ['commonApp','tableCommon']);
waitDepartTracksApp.controller("waitDepartTracksCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var waitDepartTracks={
		init:function(){
			this.bindEvent();	
			$scope.childShow=false;
			$scope.show=false;
			this.queryTenantInfo();
		},
		head:[
				{
					"name":"运单号",
				    "code":"trackingNum",
				    "type":"text",
				    "width":"120"
				},
				{
					"name":"运单状态",
				    "code":"trackingStateName",
				    "width":"120"
				},
				{
					"name":"订单号",
				    "code":"orderNo",
				    "type":"text",
				    "width":"120"
				},
				{
					 "name":"开单网点",
				    "code":"orgName",
				    "type":"text",
				    "width":"120"
				},
				{
					  "name":"开单物流",
				    "code":"tenantId",
				    "width":"120"
				},
				{
					"name":"开单人",
				    "code":"inputUserName",
				    "type":"text",
				    "width":"140"
				},
				{
					  "name":"开单时间",
					  "code":"inputTime",
					  "width":"120"
				},
				{
					"name":"到站",
				    "code":"cityName",
				    "width":"80"
				},
				{
					"name":"拉包工",
				    "code":"workerName",
				    "type":"text",
				    "width":"120"
				},
				{
					"name":"拉包工电话",
				    "code":"workerBill",
				    "type":"text",
				    "width":"120"
				},
				{
					"name":"发货人",
				    "code":"consignorName",
				    "type":"text",
				    "width":"120"
				},
				{
					"name":"发货人号码",
				    "code":"consignorBill",
				    "type":"text",
				    "width":"120"
				},
				{
					"name":"发货人地址",
				    "code":"pickAddress",
				    "width":"120"
				},
				{
					"name":"收货人",
				    "code":"consigneeName",
				    "type":"text",
				    "width":"120"
				},
				{
				    "name":"收货人手机",
				    "code":"consigneeBill",
				    "type":"text",
				    "width":"120"
				},
				{
					  "name":"收货地址",
				    "code":"receiverAddress",
				    "width":"160"
				},
				{
					  "name":"配送方式",
				    "code":"deliverTypeName",
				    "width":"120"
				},
				{
					"name":"付款方式",
				    "code":"paymentTypeName",
				    "width":"120"
				},
				{
					"name":"品名",
				    "code":"productName",
				    "width":"120"
				},
				{
					  "name":"件数",
					  "code":"count",
					  "isSum":"true",
					  "width":"120"
				},
				{
					  "name":"包装",
					  "code":"packingName",
					  "width":"120"
				},
				{
					  "name":"重量",
					  "code":"weight",
					  "isSum":"true",
					  "width":"120"
				},
				{
					  "name":"体积",
					  "code":"volume",
					  "isSum":"true",
					  "width":"120"
				},
				{
					  "name":"运费",
					  "code":"freight",
					  "isSum":"true",
					  "width":"120"
				},
				{
					  "name":"声价",
					  "code":"reputation",
					  "isSum":"true",
					  "width":"120"
				},
				{
					  "name":"保费",
					  "code":"premium",
					  "isSum":"true",
					  "width":"120"
				},
				{
					  "name":"送货费",
					  "code":"deliveryCharge",
					  "isSum":"true",
					  "width":"120"
				},
				{
					  "name":"中转费",
					  "code":"transitFee",
					  "isSum":"true",
					  "width":"120"
				},
				{
					  "name":"小费",
					  "code":"tipsMonery",
					  "isSum":"true",
					  "width":"120"
				},
				{
					  "name":"贷款",
					  "code":"collectMoney",
					  "isSum":"true",
					  "width":"120"
				},
				{
					  "name":"落地费",
					  "code":"landFee",
					  "isSum":"true",
					  "width":"120"
				},
				{
					  "name":"服务费",
					  "code":"serviceCharge",
					  "isSum":"true",
					  "width":"120"
				},
				 {   "name":"包装费",
			    	"code":"pickFee",
			    	 "isSum":"true",
			    	"width":"120"
			    },
				{
					  "name":"其他费用",
					  "code":"otherFee",
					  "isSum":"true",
					  "width":"120"
				},
				{
					  "name":"总运费",
					  "code":"totalFee",
					  "isSum":"true",
					  "width":"120"
				},
				{
					 "name":"备注",
				    "code":"remark",
				    "width":"160"
				}
		      ],
		bindEvent:function(){
			$scope.head=waitDepartTracks.head;
			$scope.url="ordOrdersWebBO.ajax?cmd=queryTracks";
			$scope.param = this.param;
			$scope.query = this.query;
			$scope.doQuery = this.doQuery;
			$scope.clear = this.clear;
		    $scope.openWayDetail=this.openWayDetail;
		    $scope.commonExport = this.commonExport;
		    $scope.params = this.params;
		    $scope.queryTenantInfo=this.queryTenantInfo; 
		    $scope.cancel=this.cancel;
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
			trackingNum=data.trackingNum;
			window.open("/tracks/trackDetailInfo.html?trackingNum="+trackingNum+"&orderNo="+orderNo);

		},
		params:{
		},
		query:{
			orderState:13
		},
		doQuery:function(){
			$scope.query.beginCreateTime=document.getElementById("beginInputTime").value;
			$scope.query.endCreateTime=document.getElementById("endInputTime").value;
			$scope.query.page=1;
			var url = "ordOrdersWebBO.ajax?cmd=queryTracks";
			$scope.table.load();
			$scope.table.callBack=function(){
				displayTable();
				setContentHeight();
			}
		},
		clear:function(){
			$scope.query={};
			$scope.query.orderState=13;
			document.getElementById("beginInputTime").value='';
			document.getElementById("endInputTime").value='';
		},
		cancel:function(){
			var trackingNum='';
			var orderNo='';
			var ordArray = new Array();
            ordArray=$scope.table.getSelectData();
            if(ordArray.length==0){
				commonService.alert("请至少选择一条运单信息!");
				return false;
			}
			if(ordArray.length>1){
				commonService.alert("只能选择一条运单信息!");
				return false;
			}
			var data= ordArray[0];
			orderNo=data.orderNo;
			trackingNum=data.trackingNum;
			var param = {"trackingNum":trackingNum};
			var url = "ordOrdersWebBO.ajax?cmd=cancelOrderInfo";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("运单取消成功",function close()
					{/*commonService.closeToParentTab(true);*/})
				}
			});

		}
	};
	waitDepartTracks.init();
}]);
