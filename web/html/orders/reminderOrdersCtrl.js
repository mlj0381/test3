var reminderOrdersApp = angular.module("reminderOrdersApp", ['commonApp','tableCommon']);
reminderOrdersApp.controller("reminderOrdersCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var reminderOrders={
		init:function(){
			this.bindEvent();		
		},
		head:[
		      {
		    	  "name":"订单号",
			      "code":"orderNo",
			      "width":"120"
		      },
		      {
		    	  "name":"归属专线",
			      "code":"companyName",
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
		    	  "width":"120"
		      },
		      {
		    	  "name":"拉包工手机",
		    	  "code":"workerBill",
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
		    	  "name":"提货省市区",
		    	  "code":"pickStation",
		    	  "width":"140"
		      },
		      {
		    	  "name":"详细地址",
		    	  "code":"pickAddress",
		    	  "width":"120"
		      },
		      {
		    	  "name":"下单时间",
		    	  "code":"createTime",
		    	  "width":"120"
		      },
		      {
		    	  "name":"预约提货时间",
			      "code":"planPickupTime",
			      "width":"120"
		      },
		      {
		    	  "name":"催单时间",
		    	  "code":"reminderTime",
		    	  "width":"120"
		      },
		      {
		    	  "name":"催单次数",
			      "code":"reminderCount",
			      "width":"120"
		      },
		      ],
		bindEvent:function(){
			$scope.head=reminderOrders.head;
			$scope.url="ordOrdersWebBO.ajax?cmd=reminderQuery";
			$scope.param = this.param;
			$scope.query = this.query;
			$scope.doQuery = this.doQuery;
			$scope.clear = this.clear;
		    $scope.openWayDetail=this.openWayDetail;
		    $scope.commonExport = this.commonExport;
		    $timeout(function(){
				$scope.table.mouseleave = reminderOrders.mouseleave;
				$scope.table.enterCallback = reminderOrders.enterCallback;
			},200);
		    $scope.queryWorker = this.queryWorker;
		    $scope.params = this.params;
		    $scope.disReceipt = this.disReceipt;
		    $scope.openDis=this.openDis;
		    $scope.ngKeyUp=this.ngKeyUp;
		},
		ngKeyUp:function(e){
			var keycode = window.event?e.keyCode:e.which;
            if(keycode==13){
                $scope.queryWorker();
                e.stopPropagation();
                return false;
            }
		},
		openDis:function(data){
			$scope.childShow=true;
			$scope.userId=data.userId;
			$scope.wokerName=data.name;
		},
		disReceipt:function(){
			var url="ordOrdersWebBO.ajax?cmd=disReceipt";
			var param={"orderId":$scope.orderIdArr.join(","),"userId":$scope.userId,"notes":$scope.params.notes};
			commonService.postUrl(url,param,function(data){
				commonService.alert("操作成功!");
				$scope.childShow=false;
				$scope.show=false;
				$scope.isQuery=1;
				$scope.doQuery();
			});
		},
		params:{
			
		},
		queryWorker:function(){
			var dataArr=$scope.table.getSelectData();
			if(dataArr.length==0){
				commonService.alert("请至少选择一条订单信息");
				return false;
			}
			$scope.orderNoArr=new Array();
			$scope.orderIdArr=new Array();
			var tenantId=0;
			var boolean=true;
			for(var i=0;i<dataArr.length;i++){
				var data=dataArr[i];
				if(tenantId!=0&&tenantId!=data.tenantId){
					boolean=false;
				}
				$scope.orderIdArr.push(data.orderId);
				$scope.orderNoArr.push(data.orderNo);
			}
			if(!boolean){
				commonService.alert("订单归属专线公司不同，不能统一调单");
				return false;
			}
			if($scope.show==false||$scope.show==null||$scope.show==undefined){
				$scope.params={};
			}
			var url = "ordOrdersWebBO.ajax?cmd=queryWorker";
			$scope.param={"orderId":$scope.orderIdArr.join(","),"workerBill":$scope.params.workerBill};
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:$scope.param,
							callBack:'$scope.callBack'
						});
			},500);
			$scope.params={};
			$scope.callBack = function() {
				setContentHeigth();
				$scope.show=true;
				$scope.orderNo=$scope.orderNoArr.join(",");
			}
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
		},
		
		doQuery:function(){
//			$scope.query.provinceId = $scope.des.chooseCityId;
//			$scope.query.cityId = $scope.des.chooseRegionId;
//			$scope.query.countyId = $scope.des.chooseCountyId;
			$scope.query.beginCreateTime=document.getElementById("beginCreateTime").value;
			$scope.query.endCreateTime=document.getElementById("endCreateTime").value;
			$scope.query.page=1;
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
	reminderOrders.init();
}]);
