var autoComplete = null;
var orderId = getQueryString("orderId");
var operType = getQueryString("operType");// 操作类型(1:录入; 2:修改)
var billApp = angular.module("billApp", ['commonApp']);

billApp.controller("billCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var bill = {
		init:function(){
			this.bindEvent();
			this.initData();
			this.userData();
			this.initStaticData();
			$scope.pageTitle = '录入';
			if (operType == 2) {
				$scope.pageTitle = '修改';
			} else {
				operType = 1;
			}
			$scope.totalCount = 0;
			this.initOrderView(orderId);
			this.initTransitOutgoing(orderId);
			$scope.all = true;// 控制全局是否修改
			$scope.canOperShouldReceivables = true;// 是否可以操作“应收”
			$scope.canOperShouldPay = true;// 是否可以操作“应收”
			// 注册键盘事件
			this.registerKeyEvent();
		},
		initData:function(){
			$scope.goodsDetailAmount = {
				goodsCount: 0,// 件数
				weight: 0.0,// 重量
				volume: 0.0,// 体积
				freight: 0.0,// 运费
				discount: 0.0, // 回扣
				deliveryCosts: 0.0,// 送货费 
				collectingMoney: 0.0,// 代收货款
				procedureFee: 0.0,// 代收手续费
				goodsPrice: 0.0, // 申明价值
				offer: 0.0,// 保险费
				handingCosts: 0.0,// 装卸费 
				packingCosts: 0.0,// 包装费
				pickingCosts: 0.0,// 提货费
				upstairsFee: 0.0, // 上楼费
				total: 0.0
			};
			//等放货通知默认 不选中 与form.order.releaseNote 对应 false==0 true ==1
			$scope.releaseNote = false;
			$scope.valuables = false;
			$scope.isPayDiscount = false;
			$scope.isPayCash = false;
			$scope.roateData = {};
			$scope.cmCarrCompanies = {};
		},
		updateGoodsDetailAmount: function(){
			$scope.goodsDetailAmount.goodsCount = bill.customParseInt($scope.form.goodsDetail.goods_0.goodsCount) + bill.customParseInt($scope.form.goodsDetail.goods_1.goodsCount);
			$scope.goodsDetailAmount.weight = bill.customParseDouble($scope.form.goodsDetail.goods_0.weight) + bill.customParseDouble($scope.form.goodsDetail.goods_1.weight);
			$scope.goodsDetailAmount.volume = bill.customParseDouble($scope.form.goodsDetail.goods_0.volume) + bill.customParseDouble($scope.form.goodsDetail.goods_1.volume);
			$scope.goodsDetailAmount.freight = bill.customParseDouble($scope.form.goodsDetail.goods_0.freight) + bill.customParseDouble($scope.form.goodsDetail.goods_1.freight);
			$scope.goodsDetailAmount.discount = bill.customParseDouble($scope.form.goodsDetail.goods_0.discount) + bill.customParseDouble($scope.form.goodsDetail.goods_1.discount);
			$scope.goodsDetailAmount.deliveryCosts = bill.customParseDouble($scope.form.goodsDetail.goods_0.deliveryCosts) + bill.customParseDouble($scope.form.goodsDetail.goods_1.deliveryCosts);
			$scope.goodsDetailAmount.collectingMoney = bill.customParseDouble($scope.form.goodsDetail.goods_0.collectingMoney) + bill.customParseDouble($scope.form.goodsDetail.goods_1.collectingMoney);
			$scope.goodsDetailAmount.procedureFee = bill.customParseDouble($scope.form.goodsDetail.goods_0.procedureFee) + bill.customParseDouble($scope.form.goodsDetail.goods_1.procedureFee);
			$scope.goodsDetailAmount.goodsPrice = bill.customParseDouble($scope.form.goodsDetail.goods_0.goodsPrice) + bill.customParseDouble($scope.form.goodsDetail.goods_1.goodsPrice);
			$scope.goodsDetailAmount.offer = bill.customParseDouble($scope.form.goodsDetail.goods_0.offer) + bill.customParseDouble($scope.form.goodsDetail.goods_1.offer);
			$scope.goodsDetailAmount.handingCosts = bill.customParseDouble($scope.form.goodsDetail.goods_0.handingCosts) + bill.customParseDouble($scope.form.goodsDetail.goods_1.handingCosts);
			$scope.goodsDetailAmount.packingCosts = bill.customParseDouble($scope.form.goodsDetail.goods_0.packingCosts) + bill.customParseDouble($scope.form.goodsDetail.goods_1.packingCosts);
			$scope.goodsDetailAmount.pickingCosts = bill.customParseDouble($scope.form.goodsDetail.goods_0.pickingCosts) + bill.customParseDouble($scope.form.goodsDetail.goods_1.pickingCosts);
			$scope.goodsDetailAmount.upstairsFee = bill.customParseDouble($scope.form.goodsDetail.goods_0.upstairsFee) + bill.customParseDouble($scope.form.goodsDetail.goods_1.upstairsFee);
			$scope.updateTotalFee();
		}, 
		updateTotalFee: function(){
			$scope.goodsDetailAmount.total = $scope.goodsDetailAmount.freight +  $scope.goodsDetailAmount.deliveryCosts
			+ $scope.goodsDetailAmount.collectingMoney + $scope.goodsDetailAmount.procedureFee + $scope.goodsDetailAmount.offer
			+ $scope.goodsDetailAmount.handingCosts + $scope.goodsDetailAmount.packingCosts + $scope.goodsDetailAmount.pickingCosts
			+ $scope.goodsDetailAmount.upstairsFee;
		},
		customParseInt: function(obj){
			if (obj == undefined || obj == null) {
				return 0;
			}
			if(isNaN(parseInt(obj))){
				return 0;
			}
			return parseInt(obj);
		},
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
		registerKeyEvent: function(){
			var returnKeyEventDomEleIds = ["_carrierCompanyId", "_outgoingTrackingNum", "_linkerName", "_linkerPhone", "_deliveryPhone", 
			                               "_deliveryAddress", "_outgoingFee", "_shouldReceivables", "_shouldPay", "_transitOutgoingRemarks"];
			this.registerKeyEventForDomsGetFocus('keydown', 'return', returnKeyEventDomEleIds);
			for (var i = 0; i < returnKeyEventDomEleIds.length; i++) {
				// 为整个页面绑定“return”按键事件
				jQuery("#" + returnKeyEventDomEleIds[i]).bind('keydown', 'ctrl+s', function(evt){
					$scope.submit();
					return false;
				});
			};
			jQuery("#_carrierCompanyId").focus();
		},
		registerKeyEventForDocument: function(keyEvent, key, callback){
			// 为整个页面绑定“return”按键事件
			jQuery(document).bind('keydown', key, function(evt){
				if (undefined != callback)
					callback(evt);
                return false;
            });
		},
		/**
		 * 为一组dom注册键盘按键时获取焦点方法
		 * keyEvent  : "keydown", "keyup", "keypress"
		 * key 		 : 键盘按钮，详细见jquery.hotkeys.js
		 * domEleIds : 一组dom元素的id
		 */
		registerKeyEventForDomsGetFocus: function(keyEvent, key, domEleIds, callback) {// 注册键盘事件
			// 为dom元素绑定“return”按键事件
			jQuery.each(domEleIds, function(i, e) { // i is element index. e is element as text.
				jQuery("#" + e).bind(keyEvent, key, function (evt){// 为元素绑定事件
					var nextDomEleId = undefined;
					var currentDomEleId = evt.target.id;
					for (var index = 0; index < domEleIds.length; index++){
						if(domEleIds[index] == currentDomEleId && index < domEleIds.length - 1) {
							nextDomEleId = domEleIds[index + 1];
							if (!jQuery("#" + nextDomEleId).is(":visible") || 'disabled' == jQuery("#" + nextDomEleId).attr("disabled")) {// 下一个元素不存在、不可见或不可用，则跳过这个元素
								currentDomEleId = nextDomEleId;
							} else {
								break;
							}
						}
					}
					if (undefined != nextDomEleId) {
						jQuery("#" + nextDomEleId).focus();
						if (undefined != callback) callback(evt, nextDomEleId);
					}
					return false;
				});
			});
		},
		bindEvent:function(){
			$scope.updateTotalCosts = this.updateTotalCosts;
			$scope.close = this.close;
			$scope.form = this.form;
			$scope.upNum = this.upNum; 
			$scope.upCosts = this.upCosts;
			//计算货物总数
			$scope.updateCount = this.updateCount;
			// 改变网点事件
			$scope.queryRoateRuting = this.queryRoateRuting;
			$scope.changeDistributionOrgId = this.changeDistributionOrgId;
			$scope.updateGoodsDetailAmount = this.updateGoodsDetailAmount;
			$scope.updateTotalFee = this.updateTotalFee;// 更新总费用
			$scope.refreshRouteTowardsDtl = this.refreshRouteTowardsDtl;//刷新线路路由 
			$scope.loadCityByProvinceId = this.loadCityByProvinceId;
			$scope.checkStrIsBlank = this.checkStrIsBlank;
			$scope.initTransitOutgoing = this.initTransitOutgoing;
			$scope.changeCarrierCompany = this.changeCarrierCompany;
			$scope.submit = this.submit;
			$scope.changeCheckbox = this.changeCheckbox;
			$scope.changeOutgoingFee = this.changeOutgoingFee;
		},
		changeOutgoingFee: function(){
			if ($scope.form.ordFee.paymentType == 2) {
				$scope.transitOutgoing.discountDouble = $scope.form.ordFee.freightCollect - $scope.transitOutgoing.outgoingFeeDouble;
				if ($scope.transitOutgoing.discountDouble < 0)
					$scope.transitOutgoing.discountDouble = 0;
			}
		},
		changeCheckbox: function(id, otherId){// 将checkbox改造成radio效果
			jQuery('#' + id).attr("checked", true);// 自己选中
			jQuery('#' + otherId).attr("checked", false);// 另一个
		},
		userData:function(){
			$scope.currOrgId=userInfo.orgId;
			$scope.currOrgName=userInfo.orgName;
			$scope.userName = userInfo.userName;
		},
		//查看初始化
		initOrderView:function(orderId){
			//-----页面展示后台显示数据逻辑
			commonService.postUrl("orderInfoBO.ajax?cmd=queryOrderInfoDetailForTransitOutgoing",{"orderId":orderId},function(data){
				if(data != null && data != undefined && data != ""){
					//返回数据，并且处理数据的显示
					$scope.form = {};
					$scope.form.orderInfo = data.items[0].orderInfo;
					$scope.form.orderInfo.address = data.items[0].orderInfo.address;
					$scope.form.ordFee = data.items[0].ordFee;
					
					$scope.form.goodsDetail={
						goods_0:{ isShow:true},
						goods_1:{ isShow:true},
						goods_2:{ isShow:false},
						goods_3:{ isShow:false},
						goods_4:{ isShow:false},
						goodsNum:2,
					};

					//下拉框
					$scope.form.orderInfo.descRegion = data.items[0].orderInfo.descRegion+"";
					
					//显示目的地和配送网点
					$scope.form.orderInfo.deliveryType = data.items[0].orderInfo.deliveryType+"";
					$scope.form.orderInfo.isInstall = data.items[0].orderInfo.isInstall+"";
					$scope.form.orderInfo.vehicleId = data.items[0].orderInfo.vehicleId+"";
					$scope.form.orderInfo.notes = data.items[0].orderInfo.notes+"";
					$scope.form.orderInfo.transportType = data.items[0].orderInfo.transportType+"";
					$scope.form.orderInfo.salesmanId = data.items[0].orderInfo.salesmanId;
					$scope.userName=data.items[0].orderInfo.inputUserName;
					$scope.form.orderInfo.inputUserId = data.items[0].orderInfo.inputUserId;
					$scope.form.orderInfo.floor = data.items[0].orderInfo.floor+"";
					$scope.form.ordFee.paymentType = data.items[0].ordFee.paymentType+"";
					
					//发货人和收货人
					$scope.form.goodsDetail.pName = data.items[0].orderInfo.consignorName+"";
					$scope.form.goodsDetail.pLinkmanName = data.items[0].orderInfo.consignorLinkmanName;
					$scope.form.goodsDetail.pTelephone = data.items[0].orderInfo.consignorTelephone+"";
					$scope.form.goodsDetail.pBill = data.items[0].orderInfo.consignorBill+"";
					$scope.form.goodsDetail.pId = data.items[0].orderInfo.consignorId;
					$scope.form.goodsDetail.rName = data.items[0].orderInfo.consigneeName+"";
					$scope.form.goodsDetail.rTelephone = data.items[0].orderInfo.consigneeTelephone;
					$scope.form.goodsDetail.rBill = data.items[0].orderInfo.consigneeBill+"";
					$scope.form.goodsDetail.rLinkmanName = data.items[0].orderInfo.consigneeLinkmanName+"";
					$scope.form.goodsDetail.rId = data.items[0].orderInfo.consigneeId;
					
					//单选
					$scope.releaseNote = data.items[0].orderInfo.releaseNote==0?false:true; 
					$scope.valuables = data.items[0].orderInfo.valuables==0?false:true; 
					$scope.isPayDiscount = data.items[0].ordFee.isPayDiscount==0?false:true; 
					$scope.isPayCash = data.items[0].ordFee.isPayCash==0?false:true; 
					
					//处理货物显示
					for(var i=0;i<data.items.length;i++){
						eval("$scope.form.goodsDetail.goods_"+i+".isShow=true");
						eval("$scope.form.goodsDetail.goods_"+i+".goodsName='"+data.items[i].goodsDetail.goodsName+"'");
						eval("$scope.form.goodsDetail.goods_"+i+".goodsCount='"+data.items[i].goodsDetail.goodsCount+"'");
						eval("$scope.form.goodsDetail.goods_"+i+".packingType='"+data.items[i].goodsDetail.packingType+"'");
						eval("$scope.form.goodsDetail.goods_"+i+".weight='"+data.items[i].goodsDetail.weight+"'");
						eval("$scope.form.goodsDetail.goods_"+i+".volume='"+data.items[i].goodsDetail.volume+"'");
						if (undefined != data.items[i].goodsDetail.freight && null != data.items[i].goodsDetail.freight) 
							eval("$scope.form.goodsDetail.goods_"+i+".freight='"+data.items[i].goodsDetail.freight/100+"'");
						if (undefined != data.items[i].goodsDetail.discount && null != data.items[i].goodsDetail.discount)
							eval("$scope.form.goodsDetail.goods_"+i+".discount='"+data.items[i].goodsDetail.discount/100+"'");
						if (undefined != data.items[i].goodsDetail.deliveryCosts && null != data.items[i].goodsDetail.deliveryCosts)
							eval("$scope.form.goodsDetail.goods_"+i+".deliveryCosts='"+data.items[i].goodsDetail.deliveryCosts/100+"'");
						if (undefined != data.items[i].goodsDetail.collectingMoney && null != data.items[i].goodsDetail.collectingMoney)
							eval("$scope.form.goodsDetail.goods_"+i+".collectingMoney='"+data.items[i].goodsDetail.collectingMoney/100+"'");
						if (undefined != data.items[i].goodsDetail.procedureFee && null != data.items[i].goodsDetail.procedureFee)
							eval("$scope.form.goodsDetail.goods_"+i+".procedureFee='"+data.items[i].goodsDetail.procedureFee/100+"'");
						if (undefined != data.items[i].goodsDetail.goodsPrice && null != data.items[i].goodsDetail.goodsPrice)
							eval("$scope.form.goodsDetail.goods_"+i+".goodsPrice='"+data.items[i].goodsDetail.goodsPrice / 100 +"'");
						if (undefined != data.items[i].goodsDetail.offer && null != data.items[i].goodsDetail.offer)
							eval("$scope.form.goodsDetail.goods_"+i+".offer='"+data.items[i].goodsDetail.offer/100+"'");
						if (undefined != data.items[i].goodsDetail.handingCosts && null != data.items[i].goodsDetail.handingCosts)
							eval("$scope.form.goodsDetail.goods_"+i+".handingCosts='"+data.items[i].goodsDetail.handingCosts/100+"'");
						if (undefined != data.items[i].goodsDetail.packingCosts && null != data.items[i].goodsDetail.packingCosts)
							eval("$scope.form.goodsDetail.goods_"+i+".packingCosts='"+data.items[i].goodsDetail.packingCosts/100+"'");
						if (undefined != data.items[i].goodsDetail.pickingCosts && null != data.items[i].goodsDetail.pickingCosts)
							eval("$scope.form.goodsDetail.goods_"+i+".pickingCosts='"+data.items[i].goodsDetail.pickingCosts/100+"'");
						if (undefined != data.items[i].goodsDetail.upstairsFee && null != data.items[i].goodsDetail.upstairsFee)
							eval("$scope.form.goodsDetail.goods_"+i+".upstairsFee='"+data.items[i].goodsDetail.upstairsFee/100+"'");
						eval("$scope.form.goodsDetail.goods_"+i+".billingType='"+data.items[i].goodsDetail.billingType+"'");
					}
					var destCity = $scope.form.orderInfo.destCity;
					commonService.postUrl("staticDataBO.ajax?cmd=revSelectCity", {"revSelectCity": destCity}, function(data){
						if(data != null && data != undefined && data!=""){
							$scope.provinceData.items = data.provinceList;// 省份
							$scope.cityData.items = data.provinceCityList;// 地市
							$scope.form.orderInfo.provinceId = data.city.provId;
							$scope.form.orderInfo.destCity = parseInt(destCity);
			 	    	}
					});
					
					// 这里的中转网点要根据订单的开单网点来
					$scope.queryRoateRuting($scope.form.orderInfo.orgId);
					
					//处理费用显示
					bill.divCost();
					//处理订单显示
					bill.form = $scope.form;
					bill.updateCount();
					$scope.updateGoodsDetailAmount();
					
					// 根据“付款方式”控制回扣，逻辑如下：
					/**
					 * 如果付款方式为现付、月结、回单付，只能填写“应付”
     				 * 如果付款方式为到付，只能填写“应收”
     				 * 如果付款方式为多比如“应收”和“应付”只能填写一个
     				 * 1: 现付; 2: 到付; 3: 月结; 4: 回单付; 5: 多笔付
					 */
					if ($scope.form.ordFee.paymentType == 1 || $scope.form.ordFee.paymentType == 3 || $scope.form.ordFee.paymentType == 4) {
						$scope.canOperShouldReceivables = true;
						$scope.canOperShouldPay = false;
					} else if ($scope.form.ordFee.paymentType == 2) {
						$scope.canOperShouldReceivables = false;
						$scope.canOperShouldPay = true;
					}
					if ($scope.form.ordFee.paymentType == 5) {
						$scope.canOperShouldReceivables = false;
						$scope.canOperShouldPay = false;
					} 
				}
			});
		},
		initTransitOutgoing: function(orderId) {
			commonService.postUrl("orderInfoBO.ajax?cmd=queryOrderRelTransitOutgoing",{"orderId":orderId},function(data){
				if (data.resultCode == '1') {
					$scope.transitOutgoing = data.resultObject;
					// 中转外发费
					if ($scope.transitOutgoing.outgoingFee != undefined)
						$scope.transitOutgoing.outgoingFeeDouble = $scope.transitOutgoing.outgoingFee / 100;

					// 应收
					if ($scope.transitOutgoing.shouldReceivables != undefined)
						$scope.transitOutgoing.shouldReceivablesDouble = $scope.transitOutgoing.shouldReceivables / 100;

					// 应付
					if ($scope.transitOutgoing.shouldPay != undefined)
						$scope.transitOutgoing.shouldPayDouble = $scope.transitOutgoing.shouldPay / 100;
					
					if ($scope.transitOutgoing.isReceivedShouldReceivables == 1)
						document.getElementById("check-33").checked = true;
					
					if ($scope.transitOutgoing.isReceivedShouldPay == 1)
						document.getElementById("check-44").checked = true;
					
					if ($scope.transitOutgoing.outgoingFeeDouble == 0)
						$scope.transitOutgoing.outgoingFeeDouble = ''
					if ($scope.transitOutgoing.shouldReceivablesDouble == 0)
						$scope.transitOutgoing.shouldReceivablesDouble = ''
					if ($scope.transitOutgoing.shouldPayDouble == 0)
						$scope.transitOutgoing.shouldPayDouble = ''
				}
			});
		},
		//－修改和显示－－处理费用金额
		divCost:function(){
			$scope.form.ordFee.freight = $scope.form.ordFee.freight/100; 
			$scope.form.ordFee.pickingCosts = $scope.form.ordFee.pickingCosts/100; 
			$scope.form.ordFee.handingCosts = $scope.form.ordFee.handingCosts/100; 
			$scope.form.ordFee.packingCosts = $scope.form.ordFee.packingCosts/100; 
			$scope.form.ordFee.deliveryCosts = $scope.form.ordFee.deliveryCosts/100;
			$scope.form.ordFee.collectingMoney = $scope.form.ordFee.collectingMoney/100;
			$scope.form.ordFee.procedureFee = $scope.form.ordFee.procedureFee/100;
			
			$scope.form.ordFee.cashPayment = $scope.form.ordFee.cashPayment/100; 
			$scope.form.ordFee.freightCollect = $scope.form.ordFee.freightCollect/100; 
			$scope.form.ordFee.receiptPayment = $scope.form.ordFee.receiptPayment/100; 
			$scope.form.ordFee.monthlyPaymeny = $scope.form.ordFee.monthlyPaymeny/100; 
			$scope.form.ordFee.discount = $scope.form.ordFee.discount/100;
			$scope.goodsDetailAmount.total = $scope.form.ordFee.discount/100;// 费用总额
			$scope.form.ordFee.offer = $scope.form.ordFee.offer/100; 
			//统计总费用 -显示费用合计
			bill.updateTotalCosts();
		},
		form:{
			orderInfo:{
				releaseNote:0,
				valuables:0,
				isLift:0,
			},
			goodsDetail:{
				goods_0:{
					isShow:true,
				},
				goods_1:{
					isShow:true,
				},
				goods_2:{
					isShow:false,
				},
				goods_3:{
					isShow:false,
				},
				goods_4:{
					isShow:false,
				},
				goodsNum:2,
			},
			ordFee:{
				isPayDiscount:0,
				isPayCash:0,
				paymentType:0,
				//支付信息
				cashPayment:0,
				freightCollect:0,
				monthlyPaymeny:0,
				receiptPayment:0,
			},
		},
		totalWeight:0,
		totalVolume:0,
		//获取静态数据
		initStaticData:function(){
			commonService.postUrl("staticDataBO.ajax?cmd=selectProvince", {"isAll": "N"}, function(data){
				// 成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.provinceData = data;
					$scope.form.orderInfo.provinceId = data.items[0].id;
					$scope.loadCityByProvinceId(data.items[0].id);
	 	    	}
			});
			
			//获取交接方式
			commonService.postUrl("staticDataBO.ajax?cmd=selectDeliveryType","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.deliveryTypeData = data;
					$scope.form.orderInfo.deliveryType = $scope.deliveryTypeData.items[0].codeValue;
				}
			});
			
			//获取包装类型
			commonService.postUrl("staticDataBO.ajax?cmd=selectPackType","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.packTypeData = data;
					if (undefined != data && data.items != undefined && data.items.length > 0) {
						$scope.form.goodsDetail.goods_0.packingType = data.items[0].codeValue;
					}
				}
			});
			
			//获取计费方式
			commonService.postUrl("staticDataBO.ajax?cmd=selectBillingType","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.billingTypeData = data;
					if (undefined != data && data.items != undefined && data.items.length > 0) {
						$scope.form.goodsDetail.goods_0.billingType = data.items[0].codeValue;
					}
				}
			});
			
			//获取注意事项
			commonService.postUrl("staticDataBO.ajax?cmd=selectNotesName","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.notesData = data;
				}
			});
			
			//运输方式
			commonService.postUrl("staticDataBO.ajax?cmd=selectTransportType","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.transportTypeData = data;
				}
			});
			
			//付款方式
			commonService.postUrl("staticDataBO.ajax?cmd=selectPaymentType","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.paymentTypeData = data;
					if (undefined != data && null != data && undefined != data.items && data.items.length > 0) {
						$scope.form.ordFee.paymentType = data.items[0].codeValue;
					}
				}
			});
			
			//是否需要安装车辆
			commonService.postUrl("staticDataBO.ajax?cmd=selectInstall","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.isInstallData = data;
					$scope.form.orderInfo.isInstall = data.items[0].codeValue;
				}
			});
			
			//查询网点开单人员、业务员
			commonService.postUrl("webCmUserInfoBO.ajax?cmd=doQueryOrgUser","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.userInfoData = data;
					// Add by chenjun. 默认选择开单员
					if (undefined != data && undefined != data.items) {
						for (var i = 0; i < data.items.length; i++) {
							if (data.items[i].userType == '3') {
								$scope.form.orderInfo.inputUserId = data.items[i].userId;
								break;
							}
						}
					}
				}
			});
			// 加载承运公司
			commonService.postUrl("cmCarrierCompanyBO.ajax?cmd=getCmCarrCompany",{state:1},function(data){
				$scope.cmCarrCompanies = data;
			});
		},
		changeCarrierCompany: function(carrierCompanyId) {
			var carrCompany = undefined;
			for (var i = 0; undefined != $scope.cmCarrCompanies.items && i < $scope.cmCarrCompanies.items.length; i++) {
				if (carrierCompanyId == $scope.cmCarrCompanies.items[i].id) {
					carrCompany = $scope.cmCarrCompanies.items[i];
					break;
				}
			}
			if (undefined != carrCompany) {
				$scope.transitOutgoing.carrierCompanyId = carrCompany.id;
				$scope.transitOutgoing.carrierCompanyName = carrCompany.carrierName;
				$scope.transitOutgoing.linkerName = carrCompany.linkMan;// 联系人
				$scope.transitOutgoing.linkerPhone = carrCompany.linkBill;// 联系电话
				$scope.transitOutgoing.deliveryPhone = carrCompany.bill;// 提货电话
				$scope.transitOutgoing.deliveryAddress = carrCompany.address;// 提货地址
			}
		},
		loadCityByProvinceId: function(provinceId) {
			commonService.postUrl("staticDataBO.ajax?cmd=selectCity", {"isAll": "N", "provinceId": provinceId}, function(data){
				// 成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.cityData = data;
					$scope.form.orderInfo.destCity = data.items[0].id;
	 	    	}
			});
		},
		//查询配送网点
		queryRoateRuting:function(orgId){
			commonService.postUrl("routeBO.ajax?cmd=queryRoateTowards","endOwnerRegion=&orgType=4&needFilter=true&orgId=" + orgId,function(data){
				if(data != null && data != undefined && data != ""){
					var defaultObj = {
						endOrgId: -1,
						endOwnerName: '空',
						endOwnerRegionName: ''
					};
					var array = new Array()
					array.push(defaultObj);
					for(var i = 0; i < data.items.length; i++) {
						array.push(data.items[i]);	
					}
					$scope.roateData.items = array;
					if ($scope.form.orderInfo.distributionOrgId == undefined) {
						$timeout(function(){
							$scope.changeDistributionOrgId($scope.form.orderInfo.distributionOrgId);
						},500);
					} else {
						$scope.changeDistributionOrgId($scope.form.orderInfo.distributionOrgId);
					}
				}
			});
		},
		//配送区域查询
		changeDistributionOrgId: function(distributionOrgId){// TODO Add by chenjun，“配送网点”发生改变时，需要加载“线路路由”，以及更新“目的地”等信息
			if (undefined != $scope.roateData.items) {
				var distribution = undefined;
				for (var i = 0; i < $scope.roateData.items.length; i++) {
					if(distributionOrgId != undefined && distributionOrgId == $scope.roateData.items[i].endOrgId){
						distribution = $scope.roateData.items[i];
						break;
					}
				}
				if (undefined != distribution) {
					$scope.form.orderInfo.hubName = distribution.endOwnerRegionName;
					$scope.refreshRouteTowardsDtl();
				}
			}
			if (distributionOrgId != undefined && distributionOrgId != -1) {// 需要重新更新计费类型，并将计费类型设置为不可编辑
				$scope.billingTypeIsEnable = true;// 选择了中转网点，则不可编辑
			} else {// 更新计费类型的编辑状态
				$scope.billingTypeIsEnable = false;// 选择了中转网点，则不可编辑
			}
		},
		refreshRouteTowardsDtl: function(){// 刷新线路路由
			if(undefined == $scope.form.orderInfo.distributionOrgId || -1 == $scope.form.orderInfo.distributionOrgId) {
				$scope.routeTowardsDtl = '';// 线路路由
				return;
			}
			var param = {"endOrgId": $scope.form.orderInfo.distributionOrgId, "beginOrgId": $scope.form.orderInfo.orgId};
			commonService.postUrl("routeBO.ajax?cmd=queryTowardsAndTowardsDetil", param, function(data){
				if(data != null && data != undefined && data != "" && data.RouteTowardsDtl != undefined){
					$scope.routeTowardsDtl = '';// 线路路由
					for (var i = 0; i < data.RouteTowardsDtl.length; i++) {
						if (i == 0) {
							$scope.routeTowardsDtl += data.RouteTowardsDtl[i].beginOrgName + '-' + data.RouteTowardsDtl[i].endOrgName;
						} else {
							$scope.routeTowardsDtl += '-' + data.RouteTowardsDtl[i].endOrgName;
						}
					}
				}
			});
		},
		//付款方式控制
		//统计费用合计
		updateTotalCosts:function(){
			$scope.totalCosts = 0;
			if($scope.form.ordFee.freight != null && $scope.form.ordFee.freight != undefined && $scope.form.ordFee.freight != ""){
				var value=Math.round($scope.form.ordFee.freight*100)/100;
				if(isNaN(value) || value == NaN || value =="NaN"){
					bill.updateTotalCosts();
				}else{
					$scope.totalCosts += value;
				}
			}
			if($scope.form.ordFee.pickingCosts != null && $scope.form.ordFee.pickingCosts != undefined && $scope.form.ordFee.pickingCosts != ""){
				var value=Math.round($scope.form.ordFee.pickingCosts*100)/100;
				if(isNaN(value) || value == NaN || value =="NaN"){
					bill.updateTotalCosts();
				}else{
					$scope.totalCosts += value;
				}
			}
			if($scope.form.ordFee.deliveryCosts != null && $scope.form.ordFee.deliveryCosts != undefined && $scope.form.ordFee.deliveryCosts != ""){
				var value=Math.round($scope.form.ordFee.deliveryCosts*100)/100;
				if(isNaN(value) || value == NaN || value =="NaN"){
					bill.updateTotalCosts();
				}else{
					$scope.totalCosts += value;
				}
			}
			if($scope.form.ordFee.handingCosts != null && $scope.form.ordFee.handingCosts != undefined && $scope.form.ordFee.handingCosts != ""){
				var value=Math.round($scope.form.ordFee.handingCosts*100)/100;
				if(isNaN(value) || value == NaN || value =="NaN"){
					bill.updateTotalCosts();
				}else{
					$scope.totalCosts += value;
				}
			}
			if($scope.form.ordFee.packingCosts != null && $scope.form.ordFee.packingCosts != undefined && $scope.form.ordFee.packingCosts != ""){
				var value=Math.round($scope.form.ordFee.packingCosts*100)/100;
				if(isNaN(value) || value == NaN || value =="NaN"){
					bill.updateTotalCosts();
				}else{
					$scope.totalCosts += value;
				}
			}
			if($scope.form.ordFee.collectingMoney != null && $scope.form.ordFee.collectingMoney != undefined && $scope.form.ordFee.collectingMoney != ""){
				var value=Math.round($scope.form.ordFee.collectingMoney*100)/100;
				if(isNaN(value) || value == NaN || value =="NaN"){
					bill.updateTotalCosts();
				}else{
					$scope.totalCosts += value;
				}
			}
			if($scope.form.ordFee.procedureFee != null && $scope.form.ordFee.procedureFee != undefined && $scope.form.ordFee.procedureFee != ""){
				var value=Math.round($scope.form.ordFee.procedureFee*100)/100;
				if(isNaN(value) || value == NaN || value =="NaN"){
					bill.updateTotalCosts();
				}else{
					$scope.totalCosts += value;
				}
			}
			if($scope.form.ordFee.offer != null && $scope.form.ordFee.offer != undefined && $scope.form.ordFee.offer != ""){
				var value=Math.round($scope.form.ordFee.offer*100)/100;
				if(isNaN(value) || value == NaN || value =="NaN"){
					bill.updateTotalCosts();
				}else{
					$scope.totalCosts += value;
				}
			}
			var t = Math.round($scope.totalCosts*100)/100;
			$scope.totalCosts = t;
		},
		//公共－返回的值为 保留小数点后面两位
		upCosts:function(valueName){
			if(eval("$scope."+valueName) != null && eval("$scope."+valueName) != undefined && eval("$scope."+valueName) != ""){
//				var value = eval("$scope."+valueName).replace(/^\D*(\d*(?:\.\d{0,2})?).*$/g, '$1');
				var value = eval("$scope."+valueName).replace(/[^\d]/g, '');
				eval("$scope."+valueName+"=value");
				eval("$scope.updateTotalCosts()");
			}
		},
		upNum:function(valueName){
			if(eval("$scope."+valueName) != null && eval("$scope."+valueName) != undefined && eval("$scope."+valueName) != ""){
				var value = eval("$scope."+valueName).replace(/[^\d]/g, '');
				eval("$scope."+valueName+"=value");
			}
		},
		//查看成本
		updateCount:function(){
			$scope.totalCount = 0;
			for(var i=0;i<bill.form.goodsDetail.goodsNum;i++){
				if(eval("bill.form.goodsDetail.goods_"+i+".isShow") == true){
					if(eval("bill.form.goodsDetail.goods_"+i+".goodsCount") == null || eval("bill.form.goodsDetail.goods_"+i+".goodsCount") == undefined || eval("bill.form.goodsDetail.goods_"+i+".goodsCount") == ""){
						return false;
					}else{
						var count=Math.round(eval("bill.form.goodsDetail.goods_"+i+".goodsCount")*100)/100;
						$scope.totalCount += count;
					}
				}
			}
		},
		checkStrIsBlank: function(str){
			if(str == undefined || null == str || '' == jQuery.trim(str))
				return false;
			return true;
		},
		submit: function(){
			if ($scope.transitOutgoing == undefined) {
				commonService.alert("请选择承运公司", function(){
					jQuery("#_carrierCompanyId").focus();
				});
				return false;
			}
			
			if(!$scope.checkStrIsBlank($scope.transitOutgoing.carrierCompanyId) || $scope.transitOutgoing.carrierCompanyId <= 0){
				commonService.alert("请选择承运公司", function(){
					jQuery("#_carrierCompanyId").focus();
				});
				return false;
			}
			if(!$scope.checkStrIsBlank($scope.transitOutgoing.outgoingTrackingNum)){
				commonService.alert("请填写外发单号", function(){
					jQuery("#_outgoingTrackingNum").focus();
				});
				return false;
			}
			var billRegexp =/^0?(13[0-9]|15[0-9]|18[0-9]|14[57]|17[0678])[0-9]{8}$/;
			if($scope.checkStrIsBlank($scope.transitOutgoing.linkerPhone) && !billRegexp.test($scope.transitOutgoing.linkerPhone)){// 联系电话格式
				commonService.alert("联系电话格式不正确", function(){
					jQuery("#_linkerPhone").focus();
				});
				return false;
			}
			
			var phoneNumberRegexp = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
			var phoneNumberRegexp2 = /^([0-9]{3,4})?[0-9]{7,8}$/;
			if($scope.checkStrIsBlank($scope.transitOutgoing.deliveryPhone) 
					&& !(phoneNumberRegexp.test($scope.transitOutgoing.deliveryPhone) 
							|| phoneNumberRegexp2.test($scope.transitOutgoing.deliveryPhone))){// 提货电话
				commonService.alert("提货电话格式不正确", function(){
					jQuery("#_deliveryPhone").focus();
				});
				return false;
			}
			
			if(!$scope.checkStrIsBlank($scope.transitOutgoing.outgoingFeeDouble)){
				commonService.alert("请填写外发费", function(){
					jQuery("#_outgoingFee").focus();
				});
				return false;
			}
			
			if(document.getElementById("check-33").checked == true){
				$scope.transitOutgoing.isReceivedShouldReceivables = 1;
			}else{
				$scope.transitOutgoing.isReceivedShouldReceivables = 0;
			}
			if(document.getElementById("check-44").checked == true){
				$scope.transitOutgoing.isReceivedShouldPay = 1;
			}else{
				$scope.transitOutgoing.isReceivedShouldPay = 0;
			}
			
			// 1: 现付; 2: 到付; 3: 月结; 4: 回单付; 5: 多笔付
			if ($scope.form.ordFee.paymentType == 1 || $scope.form.ordFee.paymentType == 3 || $scope.form.ordFee.paymentType == 4) {
				if (!$scope.checkStrIsBlank($scope.transitOutgoing.shouldPayDouble) || $scope.transitOutgoing.shouldPayDouble == 0) {
					commonService.alert("请填写应付", function(){
						jQuery("#_shouldPay").focus();
					});
					return false;
				}
				// 清空应收的信息
				$scope.transitOutgoing.isReceivedShouldReceivables = 0;
				document.getElementById("check-33").checked = false;
				$scope.transitOutgoing.shouldReceivables = 0;// 应收为0
			} else if ($scope.form.ordFee.paymentType == 2) {// 到付
				if (!$scope.checkStrIsBlank($scope.transitOutgoing.shouldReceivablesDouble) || $scope.transitOutgoing.shouldReceivablesDouble == 0) {
					commonService.alert("请填写应收", function(){
						jQuery("#_shouldReceivables").focus();
					});
					return false;
				}
				// 清空应付的信息
				$scope.transitOutgoing.isReceivedShouldPay = 0;
				document.getElementById("check-44").checked = false;
				$scope.transitOutgoing.shouldPay = 0;// 应付为0
			} else if ($scope.form.ordFee.paymentType == 5) {
				if (!$scope.checkStrIsBlank($scope.transitOutgoing.shouldPayDouble) && !$scope.checkStrIsBlank($scope.transitOutgoing.shouldReceivablesDouble)) {
					commonService.alert("当付款方式为［多笔付］时，应收和应付至少填写一项", function(){
						jQuery("#_shouldReceivables").focus();
					});
					return false;
				}
				
				if ($scope.transitOutgoing.shouldPayDouble == 0 && $scope.transitOutgoing.shouldReceivablesDouble == 0) {
					commonService.alert("当付款方式为［多笔付］时，应收和应付至少填写一项", function(){
						jQuery("#_shouldReceivables").focus();
					});
					return false;
				}
				
				if ($scope.checkStrIsBlank($scope.transitOutgoing.shouldPayDouble) 
						&& $scope.checkStrIsBlank($scope.transitOutgoing.shouldReceivablesDouble)
						&& $scope.transitOutgoing.shouldPayDouble > 0
						&& $scope.transitOutgoing.shouldReceivablesDouble > 0) {
					commonService.alert("当付款方式为［多笔付］时，应收和应付只能填写一项", function(){
						jQuery("#_shouldReceivables").focus();
					});
					return false;
				}
				
				if ($scope.checkStrIsBlank($scope.transitOutgoing.shouldPayDouble) && $scope.transitOutgoing.shouldPayDouble > 0) {
					$scope.transitOutgoing.isReceivedShouldReceivables = 0;
					document.getElementById("check-33").checked = false;
					$scope.transitOutgoing.shouldReceivables = 0;// 应收为0
					$scope.transitOutgoing.shouldReceivablesDouble = '';// 应收为0
				} else if ($scope.checkStrIsBlank($scope.transitOutgoing.shouldReceivablesDouble) && $scope.transitOutgoing.shouldReceivablesDouble > 0) {
					$scope.transitOutgoing.isReceivedShouldPay = 0;
					document.getElementById("check-44").checked = false;
					$scope.transitOutgoing.shouldPay = 0;// 应付为0
					$scope.transitOutgoing.shouldPayDouble = '';// 应付为0
				}
			} 
			
			if (!$scope.checkStrIsBlank(orderId) || orderId <= 0) {
				commonService.alert("无法获取需要中转外发的信息，请重新刷新页面或联系管理员")
				return false;
			}
			
			$scope.transitOutgoing.orderId = orderId;
			$timeout(function(){
				commonService.postUrl("orderInfoBO.ajax?cmd=doSaveTransitOutgoing", $scope.transitOutgoing, function(data){
					if(data != null && data != undefined && data != ""){
						if(data.resultCode == '1'){
							$scope.transitOutgoing.id = data.outgoing.id;
							commonService.alert("录入成功");
						} else { 
							commonService.alert(data.resultMessage);
						}
					}
				});
			}, 500);
		},
		//提交按钮
		close:function(){
    		commonService.closeToParentTab(true);
    	},
	};
	bill.init(); 
}]);
