var acMyWalletTipFeeApp = angular.module("acMyWalletTipFeeApp", ['commonApp','tableCommon']);
acMyWalletTipFeeApp.controller("acMyWalletTipFeeCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.query = {};
	$scope.save={};
	$scope.account={};
	if(userInfo.userType == 10){
		$scope.userType = userInfo.userType;
	}
	var proveManage={
		init:function(){
			this.bindEvent();
		},
		query:{},
			headPull:[
			      {"name": "订单号","code": "orderNo","width": "110","type":""},
			      {"name": "运单号","code": "orderNumber","width": "110","type":""},
			      {"name": "开单网点","code": "billingOrgName","width": "110","type":""},
			      {"name": "到站","code": "desCityName","width": "90", "type":""},
			      {"name": "拉包工","code": "pullName","width": "110", "type":""},
			      {"name": "拉包工电话","code": "pullPhone","width": "110", "type":""},
			      {"name": "小费","code": "tipString","isSum":"true","width": "110", "type":"","number": "2"},
			      {"name": "件数","code": "number","isSum":"true","width": "80","type":""},
			      {"name": "重量","code": "weight","isSum":"true","width": "110","type":"","number": "2"},
			      {"name": "体积","code": "volume","isSum":"true","width": "110", "type":"","number": "2"},
			      {"name": "运费","code": "freightString","isSum":"true","width": "110", "type":"","number": "2"}
			      ],
			head:[
			      {"name": "订单号","code": "orderNo","width": "110","type":""},
			      {"name": "运单号","code": "orderNumber","width": "110","type":""},
			      {"name": "开单网点","code": "billingOrgName","width": "110","type":""},
			      {"name": "归属物流","code": "tenantName","width": "110", "type":""},
			      {"name": "到站","code": "desCityName","width": "90", "type":""},
			      {"name": "拉包工","code": "pullName","width": "110", "type":""},
			      {"name": "拉包工电话","code": "pullPhone","width": "110", "type":""},
			      {"name": "实际小费","code": "tipString","isSum":"true","width": "110", "type":"","number": "2"},
			      {"name": "预计小费","code": "defaultTipString","isSum":"true","width": "110","type":"","number": "2"},
			      {"name": "件数","code": "number","isSum":"true","width": "80","type":""},
			      {"name": "重量","code": "weight","isSum":"true","width": "110","type":"","number": "2"},
			      {"name": "体积","code": "volume","isSum":"true","width": "110", "type":"","number": "2"},
			      {"name": "运费","code": "freightString","isSum":"true","width": "110", "type":"","number": "2"},
			      {"name": "发货人","code": "consignor","width": "110", "type":"","mouseenter":"enterCallback","mouseleave":"mouseleave"},
			      {"name": "发货人号码","code": "consignorPhone","width": "110", "type":""},
			      {"name": "发货地址","code": "consignorAddress","width": "110", "type":""},
			      {"name": "收货人","code": "consignee","width": "110", "type":""},
			      {"name": "收货人号码","code": "consigneePhone","width": "110", "type":""},
			      {"name": "收货地址","code": "consigneeAddress","width": "110", "type":""},
			      {"name": "服务方式","code": "interchangeString","width": "110", "type":""},
			      {"name": "付款方式","code": "paymentString","width": "100", "type":""},
			      {"name": "品名","code": "productName","width": "110", "type":""},
			      {"name": "包装","code": "packName","width": "110", "type":""},
			      {"name": "开单人","code": "createName","width": "110", "type":""},
			      {"name": "备注","code": "remarks","width": "110", "type":""}
			      ],
		
		bindEvent:function(){
			if(userInfo.userType == 10){
				$scope.head = proveManage.headPull;
				
			}else{
				$scope.head = proveManage.head;
			}
			$scope.url="acMyWalletBO.ajax?cmd=billingAcMyWallet";
			$scope.urlParam=$scope.query;
			$scope.doQuery = this.doQuery;
			$scope.accountTip = this.accountTip;
			$scope.doSave = this.doSave;
			$scope.seeAccount = this.seeAccount;
			$scope.cleanQuery =this.cleanQuery;
			$timeout(function(){
				$scope.page.mouseleave = proveManage.mouseleave;
				$scope.page.enterCallback = proveManage.enterCallback;
			},200);
			
		},
		
		doQuery:function(){
			$scope.query.ordersBegin = document.getElementById("ordersBegin").value;
			$scope.query.ordersEnd = document.getElementById("ordersEnd").value;
			$scope.query.billingBegin = document.getElementById("billingBegin").value;
			$scope.query.billingEnd = document.getElementById("billingEnd").value;
			var url = "acMyWalletBO.ajax?cmd=billingAcMyWallet";
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
		accountTip:function(num){
			var selectData = $scope.page.getSelectData();
			if(selectData == undefined || selectData == null || selectData.length == 0 || selectData.length > 1){
				commonService.alert("请选择一条数据！");
				return;
			}
			if(num == 1){
				$scope.isAccount = true;
				$scope.isShow = true;
				$scope.save.tipString = selectData[0].tipString;
				$scope.account.pullName = selectData[0].pullName;
				$scope.account.pullPhone = selectData[0].pullPhone;
			}
			if(num == 2){
				$scope.isAccount = false;
				$scope.isShow = false;
			}
			if(num == 3){
				$scope.doSave(selectData[0]);
			}
		},
		doSave:function(obj){
			$scope.save.id = obj.id;
			$scope.save.ordersId = obj.ordersId;
			$scope.save.orderId = obj.orderId;
			if($scope.save.tipString == undefined || $scope.save.tipString == null || $scope.save.tipString == ''){
				commonService.alert("请输入调整的小费！");
				return ;
			}else{
				var reg=/^[-\+]?\d+(\.\d+)?$/;
				if(!reg.test($scope.save.tipString)){
					commonService.alert("请输入正确费用金额！");
					return;
				}
			}
			var url = "acMyWalletBO.ajax?cmd=accountTip";
			commonService.postUrl(url,$scope.save,function(data){
				if(data == "Y"){
					commonService.alert("保存成功！",function(){
						$scope.account={};
						$scope.save={};
						$scope.isAccount = false;
						$scope.isShow = false;
						$scope.doQuery();
					});
				}
			});
		},
		seeAccount:function(){
			var orderNo='';
			var ordArray = new Array();
            ordArray=$scope.page.getSelectData();
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
		cleanQuery:function(){
			document.getElementById("ordersBegin").value ="";
			document.getElementById("ordersEnd").value ="";
			document.getElementById("billingBegin").value ="";
			document.getElementById("billingEnd").value ="";
			$scope.query.ordersBegin = "";
			$scope.query.ordersEnd = "";
			$scope.query.billingBegin = "";
			$scope.query.billingEnd = "";
			$scope.query.billingOrgName = "";
			$scope.query.orderNo = "";
			$scope.query.orderNumber= "";
			$scope.query.consignor="";
			$scope.query.consignorPhone = "";
			$scope.query.pullName="";
			$scope.query.createName="";
			$scope.query.consignee="";
			$scope.query.consigneePhone="";
			$scope.query.pullPhone = "";
		},
		mouseleave:function(x,y,data){
			$scope.isConsignor = false;
		},
		enterCallback:function(x,y,data){
			$scope.isConsignor = true;
			$scope.clientX = x;
			$scope.clientY = y;
			$scope.consignorChange.load(data.ordersId);
		}
	};
	proveManage.init();
}]);
