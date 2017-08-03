var billingManageApp = angular.module("billingManageApp", ['commonApp']);
billingManageApp.controller("billingManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var billIngManage={
		init:function(){
			this.userData();
			this.bindEvent();
			this.doQuery();
		},
		bindEvent:function(){
			$scope.param=this.param;
			$scope.doQuery=this.doQuery;
		    $scope.query=this.query;
		    $scope.clear=this.clear;
		    $scope.selectAll=this.selectAll;
		    $scope.selectOne=this.selectOne;
		    $scope.upNum = this.upNum;
		    $scope.printTable=this.printTable;
		    $scope.toTransitOutgoingManage = this.toTransitOutgoingManage;
		    $scope.cancelTransitOutgoing = this.cancelTransitOutgoing;
		    $scope.toView = this.toView;
		    $scope.toModifyTransitOutgoingManage = this.toModifyTransitOutgoingManage;
		    $scope.param = this.param;
		    $scope.outGoingData=this.outGoingData;
		    $scope.confirmReceived = this.confirmReceived;// 现金已收/已付
		    $scope.customParseDouble=this.customParseDouble;
		},
		confirmReceived: function(){
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条运单信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条运单信息!");
				return false;
			}
			
			var orderId;
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					orderId=data.orderId;
				}
			});
			commonService.confirm("请确认所选中的运单对应的［应收/应付］现金［已收/已付］？", function(){
				// 前置校验
				$timeout(function(){
					commonService.postUrl("orderInfoBO.ajax?cmd=confirmTransitOutgoingReceivedFee", {orderId: orderId}, function(data){
						if(data != null && data != undefined && data != ""){
							if(data.resultCode == '1'){
								commonService.alert("受理成功！");
							} else { 
								commonService.alert(data.resultMessage);
							}
						}
					});
				}, 500);
			});

		},
	
		toView:function(trackingNum){
			if (undefined == trackingNum) {
				var orgId='';
				if($("input[name='check-1']:checked").length==0){
					commonService.alert("请至少选择一条运单信息!");
					return false;
				}
				if($("input[name='check-1']:checked").length>1){
					commonService.alert("只能选择一条运单信息!");
					return false;
				}
				$("input[name='check-1']:checked").each(function(){
					if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
						var data=eval("("+$(this).val()+")");
						trackingNum=data.trackingNum;
						orgId=data.orgId;
					}
				});
			}
			window.open("/ord/billingDetail.html?view=1&orderId="+trackingNum+"&type=3&ver=${ver}");
		},
		upNum:function(valueName){
			var value = eval("$scope."+valueName).replace(/[^\d]/g, '');
			eval("$scope."+valueName+"=value");
		},
		query:{
			page:1,
			rows:50,
			orderState:-1,
			carrierCompanyId: '-1'
		},
		param:{},
		outGoingData:{},
		userData:function(){
			$scope.currOrgId=userInfo.orgId;
			$scope.currOrgName=userInfo.orgName;
			$scope.printTime = new Date();
			$scope.cmCarrCompanies = [{
				id: '-1',
				carrierName: '全部'
			}];
			commonService.postUrl("cmCarrierCompanyBO.ajax?cmd=getCmCarrCompany",{state:1},function(data){
				if (undefined != data && data.items != undefined && data.items.length > 0) {
					for(var i = 0; i < data.items.length; i++) {
						$scope.cmCarrCompanies.push({
							id: data.items[i].id,
							carrierName: data.items[i].carrierName
						});
					}
				}
			});
		},
		/**全选*/
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
		/**订单列表查询*/
		doQuery:function(){
			var url = "orderInfoBO.ajax?cmd=queryOutGoing";
			$scope.query.beginTime = document.getElementById("beginTime").value;
			$scope.query.endTime = document.getElementById("endTime").value;
			billIngManage.query.page=1;
			
			$timeout(function(){
				$scope.page.load({
								url:url,
								params:billIngManage.query,
								callBack:"$scope.selestData"
							});
				$scope.selestData=function(){
					var queryUrl = "orderInfoBO.ajax?cmd=queryOutGoing";
					$scope.param = $scope.query;
					$scope.param._ALLEXPORT=1;
					commonService.postUrl(queryUrl, $scope.param, function(ordData){
						$scope.outGoingData = ordData;
						$scope.goodCount=0;
						$scope.goodvolum=0;
						$scope.goodWeight=0;
						$scope.goodCashPayment=0;
						$scope.goodFreightCollect=0;
						$scope.goodCollectingMoney=0;
						
						$scope.goodOutgoingFee=0;
						$scope.goodShouldReceivables=0;
						$scope.goodShouldPay=0;
						for(var i=0;i<$scope.outGoingData.items.length;i++){
							$scope.goodCount+=$scope.outGoingData.items[i].count;
							$scope.goodvolum+=$scope.customParseDouble($scope.outGoingData.items[i].volume);
							$scope.goodWeight+=$scope.customParseDouble($scope.outGoingData.items[i].weight);
							$scope.goodCashPayment+=$scope.outGoingData.items[i].cashPayment;//现付
							$scope.goodFreightCollect+=$scope.outGoingData.items[i].freightCollect;//到付
							$scope.goodCollectingMoney+=$scope.outGoingData.items[i].collectingMoney;//代收货款
							
							$scope.goodOutgoingFee+=$scope.outGoingData.items[i].outgoingFee;//中转费
							$scope.goodShouldReceivables+=$scope.outGoingData.items[i].shouldReceivables;//应付中转费
							$scope.goodShouldPay+=$scope.outGoingData.items[i].shouldPay;//应收回扣
							
						};
					});
					$scope.param._ALLEXPORT = ""; //导出查询以后必须还原（不然分页查询无效）
					setContentHegthDelay();
				};
			}, 500);
			
			$scope.param._ALLEXPORT = ""; //导出查询以后必须还原（不然分页查询无效）
		},
		/**清空查询条件*/
		clear:function(){
			document.getElementById("beginTime").value='';
			document.getElementById("endTime").value='';
			$scope.query.beginTime='';
			$scope.query.endTime='';
			$scope.query.trackingNum="";
			$scope.query.descOrgId=-1;
			$scope.query.orderState="-1";
			$scope.query.consignorBill = "";
			$scope.query.consigneeBill = "";
			$scope.query.transitOutgoingState="0";
			$scope.query.carrierCompanyId="-1";
			$scope.query.consignorName = "";
			$scope.query.consigneeName = "";
		},
		printTable: function(){
			$scope.printTime = new Date();
			if($scope.query.transitOutgoingState==0){
				printTableInfo("printDiv", "众邦物流_中转外发_待中转");
			}else{
				printTableInfo("outGoingDiv", "众邦物流_中转外发_已中转");
			}
		
			
		},
		/**网点列表查询*/
		/**选择一行**/
		selectOne : function(orderId){
			if(document.getElementById("checkbox"+orderId).checked && document.getElementById("checkbox"+orderId) != undefined){
				document.getElementById("checkbox"+orderId).checked=false;
			}else{
				document.getElementById("checkbox"+orderId).checked=true;
			}
		},
		toTransitOutgoingManage: function(){
			var orderId='';
			var trackingNum = '';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条运单信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条运单信息!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					orderId=data.orderId;
					trackingNum= data.trackingNum;
				}
			});
			// 前置校验
			$timeout(function(){
				commonService.postUrl("orderInfoBO.ajax?cmd=preCheckForNewTransitOutgoing", {orderId: orderId}, function(data){
					if(data != null && data != undefined && data != ""){
						if(data.resultCode == '1'){
							if (data.resultObject != undefined) {
								commonService.confirm("您选中的运单已经关联了中转外发信息，是否需要修改对应的中转外发信息？", function(){
									commonService.openTab(orderId,"中转外发录入","/ord/ordBillingDetailTransfer.html?view=1&orderId="+orderId+"&trackingNum="+trackingNum);
								});
							} else {
								commonService.openTab(orderId,"中转外发录入","/ord/ordBillingDetailTransfer.html?view=1&orderId="+orderId+"&trackingNum="+trackingNum);
							}
						} else { 
							commonService.alert(data.resultMessage);
						}
					}
				});
			}, 500);
		},
		toModifyTransitOutgoingManage: function(){
			var orderId='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条运单信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条运单信息!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					orderId=data.orderId;
				}
			});
			var queryString="orderId="+orderId;
    		var url="orderInfoBO.ajax?cmd=queryOrderRelTransitOutgoing";
			commonService.postUrl(url,queryString,function(data){
				if (data.resultCode == '1') {
					commonService.openTab(orderId,"中转外发修改","/ord/transitOutgoing.html?operType=2&orderId="+orderId);
				} else {
					commonService.confirm("您选中的运单没有关联中转外发信息，是否需要录入一条新的纪录？", function(){
						commonService.openTab(orderId,"中转外发录入","/ord/transitOutgoing.html?operType=1&orderId="+orderId);
					});
				}
			});
		},
		/**
		 * 转换double类型
		 * @param obj
		 * @returns {Number}
		 */
		customParseDouble: function(obj){
			if (obj == undefined || obj == null) {
				return 0;
			}
			var ret = Math.round(obj * 100) / 100;
			if(isNaN(ret)){
				return 0;
			}
			return ret;
		},
		cancelTransitOutgoing: function(){
			var orderId='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条运单信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条运单信息!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					orderId=data.orderId;
				}
			});
			commonService.confirm("是否确认取消所选中的中转外发纪录？", function(){
				$timeout(function(){
					commonService.postUrl("orderInfoBO.ajax?cmd=cancelTransitOutgoing", {orderId: orderId}, function(data){
						if(data != null && data != undefined && data != ""){
							if (1 == data.resultCode) {
								commonService.alert("取消成功");
								$scope.doQuery();
							} else {
								commonService.alert(data.resultMessage);
							}
						}
					})
				},500);
			});
		}
	};
	billIngManage.init();
}]);
