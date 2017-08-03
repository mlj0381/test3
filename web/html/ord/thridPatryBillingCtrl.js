var autoComplete = null;
var view = getQueryString("view");
var orderId = getQueryString("orderId");
var edit = getQueryString("edit");
var orgId = getQueryString("orgId");/** 分拨中心的人进来查看详情，需要做特殊控制，例如：无法查看费用等*/
var billApp = angular.module("billApp", ['commonApp']);
billApp.controller("billCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var bill = {
		init:function(){
			this.initData();
			this.bindEvent();
			this.userData();
			this.initStaticData();
			//发货人弹窗
			$scope.showPcum = false;
			$scope.showRcum = false;
			$scope.showGoodsName0 = false;
			$scope.showGoodsName1 = false;
			//费用不显示
			$scope.priceShow = false;
			// 计费类型是否可用
			$scope.billingTypeIsEnable = false;
			
			$scope.pageType = 1;// 页面类型。1--> 运单录入；2-->运单详情；3-->运单修改
			$scope.pageTitle = '运单录入'
			$scope.totalCount = 0;
			//查看逻辑
			if(view != null && view != undefined && view != "" && orderId != null && orderId != undefined && orderId !=""){
				//控制全局是否修改
				$scope.all = true;
				this.initView(orderId);
				$scope.isInstall = true;
				$scope.view = false;
				$scope.pageType = 2;
				$scope.pageTitle = '运单详情';
				$scope.showOrderId = true;
				jQuery("#_source").attr('disabled', true);
			}else{
				$scope.view = true;
				//控制全局是否修改
				$scope.all = false;
				//修改
				if(orderId != null && orderId != undefined && orderId !=""){
					this.initView(orderId);
					$scope.pageType = 3;
					$scope.pageTitle = '运单修改';
					$scope.showOrderId = true;
				}else{
					//新增
					$scope.showOrderId = false;
					this.initData();
				}
			}
			// 注册键盘事件
			this.registerKeyEvent();
		},
		resetData: function(){
			if ($scope.pageType != 1){
				return;
			}
			var packingType = '';
			if(undefined != $scope.packTypeData.items && $scope.packTypeData.items.length > 0) {
				packingType = $scope.packTypeData.items[0].codeValue;
			}
			$scope.form.goodsDetail = {
				goodsNum: 2
			};
			$scope.form.goodsDetail.goods_0 = {isShow:true, goodsName: '', goodsCount: '', packingType: packingType, weight: '', volume: '',
												freight: '', discount: '', deliveryCosts: '', collectingMoney: '', procedureFee: '', 
												goodsPrice: '', offer: '', handingCosts: '', packingCosts: '', pickingCosts: '', 
												upstairsFee: '', billingType: $scope.billingTypeData.items[0].codeValue};
			$scope.form.goodsDetail.goods_1 =  {isShow:true, goodsName: '', goodsCount: '', packingType: '', weight: '', volume: '',
					freight: '', discount: '', deliveryCosts: '', collectingMoney: '', procedureFee: '', 
					goodsPrice: '', offer: '', handingCosts: '', packingCosts: '', pickingCosts: '', 
					upstairsFee: '', billingType: ''};
			$scope.updateGoodsDetailAmount();
			
			var distributionOrgId = '';
			var destCityName = '';
			var destCity = '';
			
			var deliveryType = '';
			if (undefined != $scope.deliveryTypeData.items && $scope.deliveryTypeData.items.length > 0) {
				deliveryType = $scope.deliveryTypeData.items[0].codeValue;
			}
			
         	var inputUserId = userInfo.userId;
			/*if (undefined != $scope.userInfoData.items && $scope.userInfoData.items.length > 0) {
				for (var i = 0; i < $scope.userInfoData.items.length; i++) {
					if ($scope.userInfoData.items[i].userType == '3') {
						inputUserId = $scope.userInfoData.items[i].userId;
						break;
					}
				}
			}*/
			
			$scope.form.orderInfo = {// 更新订单数据
				distributionOrgId: distributionOrgId,
				destCityName: destCityName,
				destCity: destCity,
				deliveryType: deliveryType,
				isLift: '',
				floor: '',
				inputUserId: inputUserId,
				isInstall: '1'
			};
			
			var paymentType = '';
			if (undefined != $scope.paymentTypeData.items && $scope.paymentTypeData.items.length > 0) {
				paymentType = $scope.paymentTypeData.items[0].codeValue;
			}
			
			$scope.form.ordFee = {
				paymentType: paymentType,
				cashPayment: 0,
				freightCollect: 0,
				receiptPayment: 0,
				monthlyPayment: 0,
				isPayCash: 0,
				isPayDiscount:0
			};
			
			$scope.areaEdit = true;
			$scope.showOrderId = false;
			$scope.priceShow = false;
			$scope.totalCount = '';
			bill.initData();
			$scope.isPayCash = false;
			$scope.form.orderInfo.provinceId = $scope.provinceData.items[0].id;
			$scope.loadCityByProvinceId($scope.form.orderInfo.provinceId);
			$scope.queryRoateRuting();
			jQuery("#check-4").attr("checked",false);
			jQuery("#_trackingNum").focus();
		},
		deleteGoodsDetail: function(){
			$scope.form.goodsDetail.goods_1 =  {isShow:true, goodsName: '', goodsCount: '', packingType: '', weight: '', volume: '',
					freight: '', discount: '', deliveryCosts: '', collectingMoney: '', procedureFee: '', 
					goodsPrice: '', offer: '', handingCosts: '', packingCosts: '', pickingCosts: '', 
					upstairsFee: '', billingType: ''};
			$scope.updateGoodsDetailAmount();
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
			$scope.paymentChange($scope.form.ordFee.paymentType);
		}, 
		updateTotalFee: function(){
			$scope.goodsDetailAmount.total = $scope.goodsDetailAmount.freight +  $scope.goodsDetailAmount.deliveryCosts
			// + $scope.goodsDetailAmount.collectingMoney 
			+ $scope.goodsDetailAmount.procedureFee + $scope.goodsDetailAmount.offer
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
			var returnKeyEventDomEleIds = ["_trackingNum", "_source", "_destCity", "_address", "pName", "pLinkmanName", "_linkbilling", "_pBill", "_rName", 
			                               "_rLinkmanName", "_rTelephone", "_rBill", "_deliveryType", "_isExistsElevator", "_floor", "_descRegion",  
			                               "_goodsName0", "_goodsCount0", "_packingType0", "_weight0", "_volume0", "_freight0", "_discount0", "_deliveryCosts0", "_collectingMoney0", "_procedureFee0", "_goodsPrice0", "_offer0", "_handingCosts0", "_packingCosts0", "_pickingCosts0", "_upstairsFee0", "_billingType0",
			                               "_goodsName1", "_goodsCount1", "_packingType1", "_weight1", "_volume1", "_freight1", "_discount1", "_deliveryCosts1", "_collectingMoney1", "_procedureFee1", "_goodsPrice1", "_offer1", "_handingCosts1", "_packingCosts1", "_pickingCosts1", "_upstairsFee1", "_billingType1",
			                               "_paymentType", "_cashPayment", "_freightCollect", "_receiptPayment", "_monthlyPayment", "_receiptNum", "_receiptCount", "_remarks", "_inputUserId", "_totalCount", "_distributionOrgId"
			                               ];
			var eles1 = ["_goodsName0", "_goodsName1", "_paymentType"];
			var eles2 = ["_goodsCount0", "_goodsCount1", "_paymentType"];
			var eles3 = ["_weight0", "_weight1", "_paymentType"];
			var eles4 = ["_volume0", "_volume1", "_paymentType"];
			var eles5 = ["_freight0", "_freight1", "_paymentType"];
			var eles6 = ["_discount0", "_discount1", "_paymentType"];
			var eles7 = ["_deliveryCosts0", "_deliveryCosts1", "_paymentType"];
			var eles8 = ["_collectingMoney0", "_collectingMoney1", "_paymentType"];
			var eles9 = ["_procedureFee0", "_procedureFee1", "_paymentType"];
			var eles10 = ["_goodsPrice0", "_goodsPrice1", "_paymentType"];
			var eles11 = ["_offer0", "_offer1", "_paymentType"];
			var eles12 = ["_handingCosts0", "_handingCosts1", "_paymentType"];
			var eles13 = ["_packingCosts0", "_packingCosts1", "_paymentType"];
			var eles14 = ["_pickingCosts0", "_pickingCosts1", "_paymentType"];
			var eles15 = ["_upstairsFee0", "_upstairsFee1", "_paymentType"];
			
			this.registerKeyEventForDomsGetFocus('keydown', 'down', eles1);
			this.registerKeyEventForDomsGetFocus('keydown', 'down', eles2);
			this.registerKeyEventForDomsGetFocus('keydown', 'down', eles3);
			this.registerKeyEventForDomsGetFocus('keydown', 'down', eles4);
			this.registerKeyEventForDomsGetFocus('keydown', 'down', eles5);
			this.registerKeyEventForDomsGetFocus('keydown', 'down', eles6);
			this.registerKeyEventForDomsGetFocus('keydown', 'down', eles7);
			this.registerKeyEventForDomsGetFocus('keydown', 'down', eles8);
			this.registerKeyEventForDomsGetFocus('keydown', 'down', eles9);
			this.registerKeyEventForDomsGetFocus('keydown', 'down', eles10);
			this.registerKeyEventForDomsGetFocus('keydown', 'down', eles11);
			this.registerKeyEventForDomsGetFocus('keydown', 'down', eles12);
			this.registerKeyEventForDomsGetFocus('keydown', 'down', eles13);
			this.registerKeyEventForDomsGetFocus('keydown', 'down', eles14);
			this.registerKeyEventForDomsGetFocus('keydown', 'down', eles15);
			
			this.registerKeyEventForDomsGetFocus('keydown', 'return', returnKeyEventDomEleIds);
			
			this.registerKeyEventForDocument('keydown', 'return', function(evt){
				jQuery("#" + returnKeyEventDomEleIds[0]).focus();
				return false;
			});
			
			this.registerKeyEventForDocument('keydown', 'ctrl+n', function(evt){
				$scope.resetData();
				return false;
			});
			
			this.registerKeyEventForDocument('keydown', 'ctrl+s', function(evt){
				if ($scope.pageType == 1 || $scope.pageType == 3) {
					$scope.submit();
				}
				return false;
			});
			
			for (var i = 0; i < returnKeyEventDomEleIds.length; i++) {
				// 为整个页面绑定“return”按键事件
				jQuery("#" + returnKeyEventDomEleIds[i]).bind('keydown', 'ctrl+n', function(evt){
					$scope.resetData();
					return false;
				});
			}
			
			for (var i = 0; i < returnKeyEventDomEleIds.length; i++) {
				// 为整个页面绑定“return”按键事件
				jQuery("#" + returnKeyEventDomEleIds[i]).bind('keydown', 'ctrl+s', function(evt){
					if ($scope.pageType == 1 || $scope.pageType == 3) {
						$scope.submit();
					}
					return false;
				});
			}
			
			for (var i = 0; i < returnKeyEventDomEleIds.length; i++) {
				// 为整个页面绑定“return”按键事件
				jQuery("#" + returnKeyEventDomEleIds[i]).bind('keydown', 'ctrl+d', function(evt){
					if ($scope.pageType == 1 || $scope.pageType == 3) {
						$scope.deleteGoodsDetail();
						jQuery("#_goodsName0").focus();
					}
					return false;
				});
			}
			
			jQuery("#_trackingNum").focus();
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
			$scope.queryCustomer = this.queryCustomer;
			$scope.queryRecCustomer = this.queryRecCustomer;
			$scope.createTable = this.createTable;
			$scope.delTable = this.delTable;
			$scope.updateChecked = this.updateChecked;
			$scope.paymentChange = this.paymentChange;
			$scope.showToFalse = this.showToFalse;
			$scope.selectPcustomer = this.selectPcustomer;
			$scope.selectRcustomer = this.selectRcustomer;
			$scope.updateTotalCosts = this.updateTotalCosts;
			$scope.close = this.close;
			//查看费用
			$scope.checkPrice = this.checkPrice;
			
			$scope.form = this.form;
			$scope.submit = this.submit;
			$scope.forEach=this.forEach;
			$scope.upNum = this.upNum; 
			$scope.upCosts = this.upCosts;
			$scope.doQueryVehicle = this.doQueryVehicle;
			$scope.selectDriver = this.selectDriver;
			
			//计算货物总数
			$scope.updateCount = this.updateCount;
			
			//打印的方法
			//打印运单
			$scope.printExpressInfo=this.printExpressInfo;
			//打印回单
			$scope.printEnvelopeInfo=this.printEnvelopeInfo;
			
			// 改变网点事件
			$scope.queryRoateRuting = this.queryRoateRuting;
			$scope.changeDistributionOrgId = this.changeDistributionOrgId;
			
			$scope.updateGoodsDetailAmount = this.updateGoodsDetailAmount;
			$scope.updateTotalFee = this.updateTotalFee;// 更新总费用
			
			$scope.checkTrackingNumUsedState = this.checkTrackingNumUsedState;// 校验运单号状态

			$scope.resetData = this.resetData;// 重置数据
			$scope.refreshRouteTowardsDtl = this.refreshRouteTowardsDtl;//刷新线路路由 
			$scope.deleteGoodsDetail = this.deleteGoodsDetail;// 删除第二条货物详细数据
			$scope.hidePrice = this.hidePrice;
			
			$scope.printStickerInfo = this.printStickerInfo;
			$scope.loadCityByProvinceId = this.loadCityByProvinceId;
			$scope.checkStrIsBlank = this.checkStrIsBlank;
			$scope.getDestCityName = this.getDestCityName;
			
			$scope.showOrHideGoodsNameSelect = this.showOrHideGoodsNameSelect;
			$scope.selectGoodsName = this.selectGoodsName;
			$scope.saveAndPrintExpressInfo = this.saveAndPrintExpressInfo;
		},
		selectGoodsName: function(number, obj) {
			if (number == 0) {
				$scope.form.goodsDetail.goods_0.goodsName = obj.codeValue;
				jQuery("#_goodsCount0").focus();
			} else if (number == 1) {
				$scope.form.goodsDetail.goods_1.goodsName = obj.codeValue;
				jQuery("#_goodsCount1").focus();
			}
		},
		/**
		 * @param number : 0或1，分别代表第一条货物和第二条货物
		 * @param type : 0或1，分别代表隐藏和显示
		 */
		showOrHideGoodsNameSelect: function(number, type){
			if (number == 0) {// 第一条
				if(type == 0) {// 隐藏
					$timeout(function(){
						$scope.showGoodsName0 = false;
					},200);
				} else if (type == 1) {
					$scope.showGoodsName0 = true;
				}
			} else if (number == 1) {// 第二条
				if(type == 0) {// 隐藏
					$timeout(function(){
						$scope.showGoodsName1 = false;
					},200);
				} else if (type == 1) {
					$scope.showGoodsName1 = true;
				}
			}
		},
		hidePrice: function(){
			$scope.priceShow = false;
		},
		userData:function(){
			$scope.currOrgId=userInfo.orgId;
			$scope.currOrgName=userInfo.orgName;
			$scope.userName = userInfo.userName;
			$scope.form.orderInfo.inputUserId=parseInt(userInfo.userId);
		},
		//查看初始化
		initView:function(orderId){
			//-----页面展示后台显示数据逻辑
			$scope.showOrderId = false; //展示订单号
			var datailUrl="";
			if(orgId!=null && orgId!=undefined && orgId!=""){
				datailUrl="orderInfoBO.ajax?cmd=queryOrderDetail";
				$scope.priceShow=false;
			}else{
				datailUrl="orderInfoBO.ajax?cmd=queryOrderInfoDetail";
				$scope.priceShow=true;
			}
			commonService.postUrl(datailUrl,{"orderId":orderId},function(data){
				if(data != null && data != undefined && data != ""){
					//返回数据，并且处理数据的显示
					$scope.form = {};
					$scope.form.orderInfo = data.items[0].orderInfo;
					$scope.form.orderInfo.address = data.items[0].orderInfo.address;
					$scope.form.ordFee = data.items[0].ordFee;
					
					$scope.form.goodsDetail={
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
					//$scope.form.orderInfo.inputUserId = data.items[0].orderInfo.inputUserId;
					$scope.userName=data.items[0].orderInfo.inputUserName;
					$scope.form.orderInfo.inputUserId=data.items[0].orderInfo.inputUserId;
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
					/**
					var destCity = $scope.form.orderInfo.destCity;
					commonService.postUrl("staticDataBO.ajax?cmd=revSelectCity", {"revSelectCity": destCity}, function(data){
						if(data != null && data != undefined && data!=""){
							$scope.provinceData.items = data.provinceList;// 省份
							$scope.cityData.items = data.provinceCityList;// 地市
							$scope.form.orderInfo.provinceId = data.city.provId;
							$scope.form.orderInfo.destCity = parseInt(destCity);
			 	    	}
					});
					*/
					// 刷新目的地信息
					commonService.postUrl("staticDataBO.ajax?cmd=revAddressInfo", {
						streetId: $scope.form.orderInfo.destStreet,
						districtId: $scope.form.orderInfo.destCounty,
						cityId: $scope.form.orderInfo.destCity,
						provinceId: $scope.form.orderInfo.destProvince
					},function(data){
						if(data != null && data != undefined && data != ""){
							if (undefined != data.provinceId) {
								$scope.source.chooseCityId =  data.provinceId;
								$scope.source.chooseCityName =  data.provinceName;
								$scope.source.cityClick($scope.source.chooseCityId, $scope.source.chooseCityName);
								inputValue = data.provinceName;
							}
							if (undefined != data.cityId) {
								$scope.source.chooseRegionId =  data.cityId;
								$scope.source.chooseRegionName =  data.cityName;
								$scope.source.regionClick($scope.source.chooseRegionId, $scope.source.chooseRegionName);
								inputValue = inputValue + "·" + data.cityName;
							}
							if (undefined != data.districtId) {
								$scope.source.chooseCountyId =  data.districtId;
								$scope.source.chooseCountyName =  data.districtName;
								$scope.source.countyClick($scope.source.chooseCountyId, $scope.source.chooseCountyName);
								inputValue = inputValue + "·" + data.districtName;
							}
							
							if (undefined != data.streetId) {
								$scope.source.chooseStreetId =  data.streetId;
								$scope.source.chooseStreetName =  data.streetName;
								$scope.source.streetClick($scope.source.chooseStreetId, $scope.source.chooseStreetName);
								inputValue = inputValue + "·" + data.streetName;
							}
							$scope.source.inputValue = inputValue;
						}
					});
					
					//处理费用显示
					bill.divCost();
					//处理订单显示
					bill.form = $scope.form;
					bill.updateCount();
					$scope.updateGoodsDetailAmount();
					if(orgId==null || orgId==undefined || orgId==""){
						$timeout(function(){
						    bill.checkPrice(true);
						},500);
				}
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
			$scope.form.ordFee.monthlyPayment = $scope.form.ordFee.monthlyPayment/100; 
			$scope.form.ordFee.discount = $scope.form.ordFee.discount/100;
			$scope.goodsDetailAmount.total = $scope.form.ordFee.discount/100;// 费用总额
			$scope.form.ordFee.offer = $scope.form.ordFee.offer/100; 
			//统计总费用 -显示费用合计
			if(orgId==null || orgId==undefined || orgId==""){
			    bill.updateTotalCosts();
			}
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
				monthlyPayment:0,
				receiptPayment:0,
			},
		},
		totalWeight:0,
		totalVolume:0,
		//保存初始化
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
			
			// 标识当前操作员是否为分拨中心的操作员
			$scope.isFbzxOperator = false;
			if (orgId != undefined && orgId > 0) {
				$scope.isFbzxOperator = true;
			}
		},
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
			
			//获取常见品名
			commonService.postUrl("staticDataBO.ajax?cmd=loadCommonGoodsName","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.commonGoodsName = data;
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
			commonService.postUrl("webCmUserInfoBO.ajax?cmd=doQueryOrgUser",{state:1},function(data){
				if(data != null && data != undefined && data != ""){
					$scope.userInfoData = data;
					// Add by chenjun. 默认选择开单员
					/*if (undefined != data && undefined != data.items) {
						for (var i = 0; i < data.items.length; i++) {
							if (data.items[i].userType == '3') {
								$scope.form.orderInfo.inputUserId = data.items[i].userId;
								break;
							}
						}
					}*/
				}
			});
			
			$scope.queryRoateRuting();
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
		doQueryVehicle:function(){
			if($scope.form.orderInfo.isInstall == 2){
				$scope.isInstall = false;
				//查询目的车辆信息
				if(bill.form.orderInfo.distributionOrgId == null || bill.form.orderInfo.distributionOrgId == undefined || bill.form.orderInfo.distributionOrgId == ""){
					commonService.alert("请选择配送网点!");
					return ;
				}
				var param={"orgId":bill.form.orderInfo.distributionOrgId,"vehicleState":1};
				commonService.postUrl("vehicleInfoBO.ajax?cmd=doQuery",param,function(data){
					if(data != null && data != undefined && data != ""){
						$scope.vehicleData = data;
					}
				});
			}else{
				$scope.isInstall = true;
				$scope.form.orderInfo.vehicleId = null;
				$scope.form.orderInfo.driverName = "";
			}
			// jQuery("#_isInstall").focus();
		},
		selectDriver:function(obj){
			var id = bill.form.orderInfo.vehicleId;
			var data = $scope.vehicleData;
			for(var i=0;i<data.items.length;i++){
				if(id== data.items[i].vehicleId){
					bill.form.orderInfo.driverName = data.items[i].name;
				}
			}
		},
		//查询配送网点
		queryRoateRuting:function(){
			commonService.postUrl("routeBO.ajax?cmd=queryRoateTowards","endOwnerRegion=&orgType=4&needFilter=true",function(data){
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
					if ($scope.pageType == 1) {
						$scope.form.orderInfo.distributionOrgId = -1;
						$scope.changeDistributionOrgId(-1);
					} else {
						if ($scope.form.orderInfo.distributionOrgId == undefined) {
							$timeout(function(){
								$scope.changeDistributionOrgId($scope.form.orderInfo.distributionOrgId);
							},500);
						} else {
							$scope.changeDistributionOrgId($scope.form.orderInfo.distributionOrgId);
						}
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
				// 更新计费类型
				$scope.checkPrice(false, false);
			} else {// 更新计费类型的编辑状态
				$scope.billingTypeIsEnable = false;// 选择了中转网点，则不可编辑
			}
		},
		refreshRouteTowardsDtl: function(){// 刷新线路路由
			if(undefined == $scope.form.orderInfo.distributionOrgId || -1 == $scope.form.orderInfo.distributionOrgId) {
				$scope.routeTowardsDtl = '';// 线路路由
				return;
			}
			var param = {"endOrgId": $scope.form.orderInfo.distributionOrgId};
			if($scope.form.orderInfo.distributionOrgId!=null && $scope.form.orderInfo.distributionOrgId!=undefined && $scope.form.orderInfo.distributionOrgId!=""){
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
			}
		},
		//隐藏发货方和收货方的div
		showToFalse:function(){
			$timeout(function(){
				$scope.showPcum = false;
				$scope.showRcum = false;
			},200);
		},
		//查询发货方
		queryCustomer:function(id,pName){
			if(id == 0){
				$scope.showPcum = false;
				bill.form.goodsDetail.pId = "";
			}else{
				$scope.showPcum = true;
			}
			if(id ==1 && pName != null && pName != undefined && pName != ""){
				if(pName.length>=1){
					var param={"name":pName,"type":"1"};
					commonService.postUrl("customerBO.ajax?cmd=getCustomer",param,function(data){
						if(data != null && data != undefined && data != ""){
							$scope.customerData = data;
						}
					});
				}
			}else if(id == 1){
				var param={"type":"1"};
				commonService.postUrl("customerBO.ajax?cmd=getCustomer",param,function(data){
					if(data != null && data != undefined && data != ""){
						$scope.customerData = data;
					}
				});
			}
		},
		//设置发货方
		selectPcustomer:function(obj){
			$scope.form.goodsDetail.pName = obj.name;
			$scope.form.goodsDetail.pLinkmanName = obj.linkmanName;
			$scope.form.goodsDetail.pTelephone = obj.telephone;
			$scope.form.goodsDetail.pBill = obj.bill;
			$scope.form.goodsDetail.pId = obj.id;
			if (obj.name == undefined || null == obj.name || '' == obj.name){
				jQuery("#pName").focus();
			} else if (obj.linkmanName == undefined || null == obj.linkmanName || '' == obj.linkmanName){
				jQuery("#pLinkmanName").focus();
			} else if (obj.telephone == undefined || null == obj.telephone || '' == obj.telephone){
				jQuery("#_linkbilling").focus();
			} else if (obj.bill == undefined || null == obj.bill || '' == obj.bill){
				jQuery("#_pBill").focus();
			} else {
				jQuery("#_pBill").focus();
			}
		},
		//显示收货方 并显示查询数据div
		queryRecCustomer:function(ids,rName){
			if(ids == 0){
				$scope.showRcum = false;
				bill.form.goodsDetail.rId = "";
			}else{
				$scope.showRcum = true;
			}
			var id ="";
			if($scope.form.goodsDetail.pId != null && $scope.form.goodsDetail.pId != undefined && $scope.form.goodsDetail.pId != ""){
				id=$scope.form.goodsDetail.pId;
			}
			if(ids == 1 && rName != null && rName != undefined && rName != ""){
				if(rName.length>=1){
					var param={"name":rName,"type":"1","id":id};
					commonService.postUrl("customerBO.ajax?cmd=queryCustomer",param,function(data){
						if(data != null && data != undefined && data != ""){
							$scope.recCustomerData = data;
						}
					});
				}
			}else if(ids ==1){
				var param={"type":"1","id":id};
				commonService.postUrl("customerBO.ajax?cmd=queryCustomer",param,function(data){
					if(data != null && data != undefined && data != ""){
						$scope.recCustomerData = data;
					}
				});
			}
		},
		//设置收货人
		selectRcustomer:function(obj){
			$scope.form.goodsDetail.rName = obj.name;
			$scope.form.goodsDetail.rLinkmanName = obj.linkmanName;
			$scope.form.goodsDetail.rTelephone = obj.telephone;
			$scope.form.goodsDetail.rBill = obj.bill;
			$scope.form.goodsDetail.rId = obj.id;
			if (obj.name == undefined || null == obj.name || '' == obj.name){
				jQuery("#_rName").focus();
			} else if (obj.linkmanName == undefined || null == obj.linkmanName || '' == obj.linkmanName){
				jQuery("#_rLinkmanName").focus();
			} else if (obj.telephone == undefined || null == obj.telephone || '' == obj.telephone){
				jQuery("#_rTelephone").focus();
			} else if (obj.bill == undefined || null == obj.bill || '' == obj.bill){
				jQuery("#_rBill").focus();
			} else {
				jQuery("#_deliveryType").focus();
			}
		},
		//创建货物条目
		createTable:function(){
			if($scope.form.goodsDetail.goodsNum<2){
				for(var i=1;i<2;i++){
					if(eval("$scope.form.goodsDetail.goods_"+i+".isShow") == false){
						eval("$scope.form.goodsDetail.goods_"+i+".isShow=true");
						$scope.form.goodsDetail.goodsNum = $scope.form.goodsDetail.goodsNum+1;
						break;
					}
				}
			}else{
				commonService.alert("最多只能增加2条货物信息");
			}
		},
		//删除货物条目
		delTable:function(id){
			bill.form.goodsDetail.goodsNum = bill.form.goodsDetail.goodsNum-1;
			eval("$scope.form.goodsDetail.goods_"+id+"={}");
			eval("$scope.form.goodsDetail.goods_"+id+".isShow=false");
		},
		//单选控制
		updateChecked:function(){
			if($scope.releaseNote == true){
				$scope.form.orderInfo.releaseNote = 1;
			}else{
				$scope.form.orderInfo.releaseNote = 0;
			}
			if($scope.valuables == true){
				$scope.form.orderInfo.valuables = 1;
			}else{
				$scope.form.orderInfo.valuables = 0;
			}
			if($scope.isPayDiscount == true){
				$scope.form.ordFee.isPayDiscount = 1;
			}else{
				$scope.form.ordFee.isPayDiscount = 0;
			}
			if($scope.isPayCash == true){
				$scope.form.ordFee.isPayCash = 1;
			}else{
				$scope.form.ordFee.isPayCash = 0;
			}
		},
		//付款方式控制
		paymentChange:function(){
			$scope.updateTotalFee();
			if(bill.form.ordFee.paymentType == 1){// 现付
				bill.form.ordFee.freightCollect=0;
				bill.form.ordFee.monthlyPayment=0;
				bill.form.ordFee.receiptPayment=0;
				bill.form.ordFee.cashPayment = $scope.goodsDetailAmount.total; 
			}else if(bill.form.ordFee.paymentType == 2){// 到付
				bill.form.ordFee.cashPayment=0;
				bill.form.ordFee.monthlyPayment=0;
				bill.form.ordFee.receiptPayment=0;
				bill.form.ordFee.freightCollect = $scope.goodsDetailAmount.total; 
			}else if(bill.form.ordFee.paymentType == 3){// 月结
				bill.form.ordFee.cashPayment=0;
				bill.form.ordFee.freightCollect=0;
				bill.form.ordFee.receiptPayment=0;
				bill.form.ordFee.monthlyPayment = $scope.goodsDetailAmount.total; 
			}else if(bill.form.ordFee.paymentType == 4){// 回单付
				bill.form.ordFee.cashPayment=0;
				bill.form.ordFee.freightCollect=0;
				bill.form.ordFee.monthlyPayment=0;
				bill.form.ordFee.receiptPayment = $scope.goodsDetailAmount.total; 
			}
		},
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
				$timeout(function(){
				    bill.checkPrice(true);
				},500);
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
			bill.paymentChange();
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
		checkPrice:function(autoHide, needShowPriceInfo){
			//校验数据
			if(bill.checkPriceValue()){
				// 没有选择中转网点，则无法查看成本
				if (bill.form.orderInfo.distributionOrgId==undefined || -1 == bill.form.orderInfo.distributionOrgId) {
					if (undefined != needShowPriceInfo && needShowPriceInfo == true) {
						$scope.priceShow = true;
						var tipInfo = new Array();
						tipInfo.push({
							feeName: '提示：选择中转网点后，方可查看出货成本',
							costAmount: undefined
						});
						if ($scope.allCost == undefined) {
							$scope.allCost = {}
						}
						$scope.allCost.costList = tipInfo;
					}
					return;
				}
				//代收货款
				var checkParam = {
						g_0:{},
						g_1:{},
				};
				checkParam.g_0.weight = bill.form.goodsDetail.goods_0.weight;
				checkParam.g_0.volume = bill.form.goodsDetail.goods_0.volume;
				if(bill.form.goodsDetail.goods_1.goodsName != undefined 
					&& bill.form.goodsDetail.goods_1.goodsName != null
					&& jQuery.trim(bill.form.goodsDetail.goods_1.goodsName) != ""){
					checkParam.g_1.weight = bill.form.goodsDetail.goods_1.weight;
					checkParam.g_1.volume = bill.form.goodsDetail.goods_1.volume;
				}
				checkParam.endOrgId = bill.form.orderInfo.distributionOrgId;
				if($scope.form.orderInfo.deliveryType!=1){
				     checkParam.areaId = bill.form.orderInfo.descRegion;
			    }
				checkParam.transitionWay = bill.form.orderInfo.deliveryType;
				/*checkParam.weight = bill.totalWeight;
				checkParam.volume = bill.totalVolume;*/
				//代收货款
				if(bill.form.orderInfo.collectingMoney !=null && bill.form.orderInfo.collectingMoney != undefined && bill.form.orderInfo.collectingMoney !=""){
					checkParam.collectingMoney = bill.form.orderInfo.collectingMoney;
				}
				if(bill.form.orderInfo.deliveryType == 3){
					//楼层
					checkParam.floor = bill.form.orderInfo.floor;
					//配送区域
						checkParam.descRegion = bill.form.orderInfo.descRegion;
					//是否有电梯
					checkParam.isLift = bill.form.orderInfo.isLift;
					checkParam.isMap = "Y";
				}else if(bill.form.orderInfo.deliveryType == 1){
					checkParam.isMap = "N";
				}else if(bill.form.orderInfo.deliveryType == 2){
					checkParam.descRegion = bill.form.orderInfo.descRegion;
					checkParam.isMap = "Y";
				}
				if(bill.form.orderInfo.salesmanId !=null && bill.form.orderInfo.salesmanId != undefined && bill.form.orderInfo.salesmanId !=""){
					checkParam.salesmanId = bill.form.orderInfo.salesmanId;
				}
				if($scope.goodsDetailAmount.handingCosts !=null && $scope.goodsDetailAmount.handingCosts != undefined && $scope.goodsDetailAmount.handingCosts !=""){
					checkParam.handingCosts=$scope.goodsDetailAmount.handingCosts;
				}
				setContentHeight();
				console.log("aa"+orgId);
				if(orgId==null || orgId==undefined || orgId==""){
					commonService.postUrl("routeCostBO.ajax?cmd=queryRouteCost",checkParam,function(data){
						if(data != null && data != undefined && data != ""){
							if (undefined != needShowPriceInfo && needShowPriceInfo == true) {
								$scope.priceShow = true;
								$scope.allCost = data;
								if (autoHide == true) {
									$timeout(function(){
										$scope.hidePrice();
									}, 5000);
								}
							}
							for(var i=0;i<5;i++){
								if(eval("bill.form.goodsDetail.goods_"+i+".isShow") == true){
									eval("bill.form.goodsDetail.goods_"+i+".billingType ='"+eval("data.g_"+i)+"'");
								}
							}
						}
					});
				}
			}
		},
		//计算货物数量
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
		//查看成本数据校验
		checkPriceValue:function(){
			//如果交接方式是送货上楼和送货上门 楼层、是否为电梯、配送区域、详细地址为必填
			if(bill.form.orderInfo.deliveryType == 3){
				if(bill.form.orderInfo.floor == null || bill.form.orderInfo.floor == undefined || bill.form.orderInfo.floor == ""){
					commonService.alert("请选择楼层", function(){
						jQuery("#_floor").focus();
					});
					return false;
				}
			}
			return true;
		},
		checkTrackingNumUsedState: function(callback){
			if ($scope.pageType == 2) {// 订单详情不校验
				return;
			}
			if ($scope.form.orderInfo.trackingNum == undefined || jQuery.trim($scope.form.orderInfo.trackingNum) == ''){
				return false;
			}
			if (undefined != bill.form.orderInfo.orderId && bill.form.orderInfo.orderId > 0){// 已经保存的订单就无需校验运单号了
				return true;
			}
			var trackingNum = $scope.form.orderInfo.trackingNum;
			commonService.postUrl("orderInfoBO.ajax?cmd=checkTrackingNumUsedState",{trackingNum: trackingNum},function(data){
				if(data != undefined && data != null && data != ""){
					if (undefined != callback && typeof callback == 'function') {
						callback(data);
					} else {
						if(data.usedState == true) {
							commonService.alert("运单号[" + trackingNum + "]已经被使用！", function(){
								jQuery("#_trackingNum").focus();
							});
						} else if (data.message != undefined){
							commonService.alert(data.message, function(){
								jQuery("#_trackingNum").focus();
							});
						}
					}
				}
			});
		},
		checkStrIsBlank: function(str){
			if(str == undefined || null == str || '' == jQuery.trim(str))
				return false;
			return true;
		},
		//提交按钮
		submit:function(successCallback){
			if(!$scope.checkStrIsBlank($scope.form.orderInfo.trackingNum)){
				commonService.alert("请输入运单号！", function(){
					jQuery("#_trackingNum").focus();
				});
				return false;
			}
			
			var chooseCityId = $scope.source.chooseCityId;// 省份
			var chooseRegionId = $scope.source.chooseRegionId;// 地市
			var chooseCountyId = $scope.source.chooseCountyId;// 县区
			var chooseStreetId = $scope.source.chooseStreetId;// 县区
			if (!$scope.checkStrIsBlank(chooseRegionId)) {
				commonService.alert("请选择到达城市所在的地市!", function(){
					jQuery("#_source").focus();
				});
				return false;
			}
			bill.form.orderInfo.destProvince = chooseCityId;
			bill.form.orderInfo.destCity = chooseRegionId;
			bill.form.orderInfo.destCounty = chooseCountyId;
			bill.form.orderInfo.destStreet = chooseStreetId;
			
			/**
			if(!$scope.checkStrIsBlank($scope.form.orderInfo.destCity)) {
				commonService.alert("请选择到达城市！", function(){
					jQuery("#_destCity").focus();
				});
				return false;
			}
			*/
			
			if(!$scope.checkStrIsBlank($scope.form.orderInfo.address)) {
				commonService.alert("请输入收获详细地址", function(){
					jQuery("#_address").focus();
				});
				return false;
			}
			
			/** 发货方和收获方信息校验 ** Start*/
			var billRegexp =/^0?(13[0-9]|15[0-9]|18[0-9]|14[57]|17[0678])[0-9]{8}$/;
			var phoneNumberRegexp = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
			var phoneNumberRegexp2 = /^([0-9]{3,4})?[0-9]{7,8}$/;
			// 收货人和发货人两条信息必填其中一项
			if(!$scope.checkStrIsBlank(bill.form.goodsDetail.pName) && !$scope.checkStrIsBlank(bill.form.goodsDetail.pLinkmanName)){
				commonService.alert("请填写发货人或者发货方联系人", function(){
					jQuery("#pLinkmanName").focus();
				});
				return false;
			}
			if(!$scope.checkStrIsBlank(bill.form.goodsDetail.pTelephone) && !$scope.checkStrIsBlank(bill.form.goodsDetail.pBill)){
				commonService.alert("请填写发货方联系电话或者手机号码", function(){
					jQuery("#_linkbilling").focus();
				});
				return false;
			}
			if($scope.checkStrIsBlank(bill.form.goodsDetail.pTelephone) && !(phoneNumberRegexp.test(bill.form.goodsDetail.pTelephone) || phoneNumberRegexp2.test(bill.form.goodsDetail.pTelephone))){
				commonService.alert("发货方联系电话格式不正确!", function(){
					jQuery("#_linkbilling").focus();
				});
				return false;
			}
			if ($scope.checkStrIsBlank(bill.form.goodsDetail.pBill) && !billRegexp.test(bill.form.goodsDetail.pBill)){
				commonService.alert("发货方手机号码格式不正确!", function(){
					jQuery("#_pBill").focus();
				});
		        return false;
			}
			
			if(!$scope.checkStrIsBlank(bill.form.goodsDetail.rName) && !$scope.checkStrIsBlank(bill.form.goodsDetail.rLinkmanName)){
				commonService.alert("请填写收货方或者收货方联系人", function(){
					jQuery("#_rLinkmanName").focus();
				});
				return false;
			}
			if(!$scope.checkStrIsBlank(bill.form.goodsDetail.rTelephone) && !$scope.checkStrIsBlank(bill.form.goodsDetail.rBill)){
				commonService.alert("请填写收货方联系电话或者手机号码", function(){
					jQuery("#_rTelephone").focus();
				});
				return false;
			}
			if($scope.checkStrIsBlank(bill.form.goodsDetail.rTelephone) && !(phoneNumberRegexp.test(bill.form.goodsDetail.rTelephone) || phoneNumberRegexp2.test(bill.form.goodsDetail.rTelephone))){
				commonService.alert("收货方联系电话格式不正确!", function(){
					jQuery("#_rTelephone").focus();
				});
				return false;
			}
			if ($scope.checkStrIsBlank(bill.form.goodsDetail.rBill) && !billRegexp.test(bill.form.goodsDetail.rBill)){
				commonService.alert("收货方手机号码格式不正确!", function(){
					jQuery("#_rBill").focus();
				});
		        return false;
			}
			/** 发货方和收获方信息校验 ** End*/
			
			// 检查货物
			for(var i = 0;i < bill.form.goodsDetail.goodsNum; i++){
				var check = true;
				if(i != 0){// 除了第一行，后面的货物，如果名字为空，则不需要校验
					var goodsName = eval("bill.form.goodsDetail.goods_" + i + ".goodsName");
					if(!$scope.checkStrIsBlank(goodsName)){
						check = false;
						eval("bill.form.goodsDetail.goods_" + i + "={}");// 清空对应列的数据列的数据
						$scope.updateGoodsDetailAmount();
					}
				}
				if(check) {
					var goodsName = eval("bill.form.goodsDetail.goods_" + i + ".goodsName");
					if(!$scope.checkStrIsBlank(goodsName)){
						commonService.alert("请输入货物名称［品名］", function (){
							jQuery("#_goodsName" + i).focus();
						});
						return false;
					}
					var goodsCount = eval("bill.form.goodsDetail.goods_" + i + ".goodsCount");
					if(!$scope.checkStrIsBlank(goodsCount)){
						commonService.alert("请输入货物数量［件数］", function(){
							jQuery("#_goodsCount" + i).focus();
						});
						return false;
					}
					var packingType = eval("bill.form.goodsDetail.goods_" + i + ".packingType");
					if(!$scope.checkStrIsBlank(packingType)){
						commonService.alert("请选择包装类型", function(){
							jQuery("#_packingType" + i).focus();
						});
						return false;
					}
					var weight = eval("bill.form.goodsDetail.goods_" + i + ".weight");
					if(!$scope.checkStrIsBlank(weight)){
						commonService.alert("请输入货物重量", function(){
							jQuery("#_weight" + i).focus();
						});
						return false;
					}
					var volume = eval("bill.form.goodsDetail.goods_" + i + ".volume");
					if(!$scope.checkStrIsBlank(volume)){
						commonService.alert("请输入货物体积", function(){
							jQuery("#_volume" + i).focus();
						});
						return false;
					}
				}
				if (i == 0) {// 开单员可以将第二条货物的费用只输入第一条
					var freight = eval("bill.form.goodsDetail.goods_" + i + ".freight");
					if(!$scope.checkStrIsBlank(freight)){
						commonService.alert("请输入运费", function(){
							jQuery("#_freight" + i).focus();
						});
						return false;
					}
				}
			}

			bill.paymentChange();
			bill.updateTotalCosts();
			bill.totalWeight = $scope.goodsDetailAmount.weight;// 重量
			bill.totalVolume = $scope.goodsDetailAmount.volume;// 体积
			if(!$scope.checkStrIsBlank(bill.form.ordFee.paymentType)){// 付款方式
				commonService.alert("请输入付款方式", function(){
					jQuery("#_paymentType").focus();
				});
				return false;
			}else{
				if(bill.form.ordFee.paymentType == 1 && bill.form.ordFee.cashPayment <=0){
					commonService.alert("请输入现付金额", function(){
						jQuery("#_cashPayment").focus();
					});
					return false;
				}else if(bill.form.ordFee.paymentType == 2 && bill.form.ordFee.freightCollect <=0){
					commonService.alert("请输入到付金额", function(){
						jQuery("#_freightCollect").focus();
					});
					return false;
				}else if(bill.form.ordFee.paymentType == 3 && bill.form.ordFee.monthlyPayment <=0){
					commonService.alert("请输入月结金额",function(){
						jQuery("#_monthlyPayment").focus();
					});
					return false;
				}else if(bill.form.ordFee.paymentType == 4 && bill.form.ordFee.receiptPayment <=0){
					commonService.alert("请输入回单付金额", function(){
						jQuery("#_receiptPayment").focus();
					});
					return;
				}else if(bill.form.ordFee.paymentType == 5){
					if(bill.form.ordFee.cashPayment<=0 && bill.form.ordFee.freightCollect <=0 && bill.form.ordFee.monthlyPayment<=0 && bill.form.ordFee.receiptPayment){
						commonService.alert("请选择输入付款金额");
						return;
					}
					var payNumber=parseFloat(bill.form.ordFee.cashPayment)+parseFloat(bill.form.ordFee.freightCollect)+parseFloat(bill.form.ordFee.monthlyPayment)+parseFloat(bill.form.ordFee.receiptPayment);
					if(payNumber!=$scope.goodsDetailAmount.total){ 
						commonService.alert("付款金额必须等于费用合计");
						return;
					}
				}
			}
			
			if (bill.form.ordFee.receiptPayment > 0) {
				if(!$scope.checkStrIsBlank(bill.form.orderInfo.receiptNum)){
					commonService.alert("请输入回单号码", function(){
						jQuery("#_receiptNum").focus();
					});
					return false;
				}
				if(!$scope.checkStrIsBlank(bill.form.orderInfo.receiptCount)){
					commonService.alert("请输入回单数量", function(){
						jQuery("#_receiptCount").focus();
					});
					return false;
				}
			}

			if(document.getElementById("check-1").checked == true){
				$scope.form.orderInfo.releaseNote = 1;
			}
			if(bill.form.ordFee.paymentType == 1){
				if(document.getElementById("check-4").checked == true){
					$scope.form.ordFee.isPayCash = 1;
				}else{
					$scope.form.ordFee.isPayCash = 0;
				}
			}
			if(document.getElementById("check-3").checked == true){
				$scope.form.ordFee.isPayDiscount = 1;
			}else{
				$scope.form.ordFee.isPayDiscount = 0;
			}
			if(bill.form.orderInfo.deliveryType == 1){
				$scope.form.orderInfo.isLift = null;	
			}
			$scope.form.ordFee.discount = $scope.goodsDetailAmount.total;// 费用总额

			bill.form.orderInfo.orderType = 1;// 表示订单是第三方订单
			$timeout(function(){
				commonService.postUrl("orderInfoBO.ajax?cmd=doSave", bill.form, function(data){
					if(data != null && data != undefined && data != ""){
						if(data.isOk == 'Y'){
							$scope.showOrderId = true;
							bill.form.orderInfo.orderId = data.orderId;
							bill.form.ordFee.orderId = data.orderId;
							bill.form.goodsDetail.pId = data.pId;
							bill.form.goodsDetail.rId = data.rId;
							commonService.alert("录入成功");
							if (undefined != successCallback)
								successCallback();
						} else if(data.isOk == 'N'){ 
							commonService.alert(data.message);
						}
					}
				});
			}, 500);
		},
		getDestCityName: function(){
			var destInfo = $scope.source.chooseRegionName;
			if($scope.checkStrIsBlank($scope.source.chooseStreetName)) {
				destInfo = destInfo + "·" + $scope.source.chooseStreetName;
			} else if ($scope.checkStrIsBlank($scope.source.chooseCountyName)) {
				destInfo = destInfo + "·" + $scope.source.chooseCountyName;
			}
			return destInfo;// 地市;
			/**
			if(!$scope.checkStrIsBlank($scope.form.orderInfo.destCity)){
				return '';
			}
			if($scope.cityData.items == undefined){
				return '';
			}
			for (var i = 0; i < $scope.cityData.items.length; i++) {
				if ($scope.cityData.items[i].id == $scope.form.orderInfo.destCity) {
					return $scope.cityData.items[i].name;
				}
			}
			return '';
			*/
		},
		/**
		 * 打印回单（打印信封）
		 */
		printEnvelopeInfo:function(){
			var trackingNum = $scope.form.orderInfo.trackingNum;
			if (undefined == trackingNum || null == trackingNum || "" == jQuery.trim(trackingNum)){
				commonService.alert("请输入运单号", function(){
					jQuery("#_trackingNum").focus();
				});
				return false;
			}
			var goodsNo = $scope.form.orderInfo.trackingNum + '-' + $scope.goodsDetailAmount.goodsCount;
			
			var freight = $scope.goodsDetailAmount.freight;// 运费
			if(undefined == freight) freight = 0;
			
			var sender = $scope.form.goodsDetail.pName;
			if(!$scope.checkStrIsBlank(sender)) {
				sender = $scope.form.goodsDetail.pLinkmanName;
			}
			if (sender == undefined)
				sender = '';
			
			var goodsName = $scope.form.goodsDetail.goods_0.goodsName == undefined ? '' : $scope.form.goodsDetail.goods_0.goodsName;
			if ($scope.checkStrIsBlank($scope.form.goodsDetail.goods_1.goodsName)) {
				goodsName = goodsName == '' ? $scope.form.goodsDetail.goods_1.goodsName : goodsName + '/' + $scope.form.goodsDetail.goods_1.goodsName;
			}
			
			commonService.postUrl("staticDataBO.ajax?cmd=queryOrgPhone",{"orgId":-1},function(data){
				var source = data.beginOrgName;
				var supportStaffPhone = data.beginOrgId;// 开单网点联系电话
				
				
				//邮编、出发地、目的地、运单号、时间、货物名称、运费、发货人、联系方式、经办人、勾选、身份证
				var envelopeInfo = new EnvelopeInfo("", source, $scope.getDestCityName(), goodsNo, new Date(), goodsName, freight, sender, supportStaffPhone, userInfo.userName, "", "");
				if ($scope.form.orderInfo.receiptCount != undefined && $scope.form.orderInfo.receiptCount > 10) {
					commonService.confirm("回单数量大于10份，是否继续打印？", function(){
						printEnvelopeInfo(envelopeInfo, "众邦_开单_开单录入", $scope.form.orderInfo.receiptCount);
					}, function(){
						jQuery("#_receiptCount").focus();
					});
				} else {
					printEnvelopeInfo(envelopeInfo, "众邦_开单_打印回单", $scope.form.orderInfo.receiptCount);
				}
			});
			
		},
		printStickerInfo: function(){
			var destInfo = $scope.source.chooseRegionName;
			if ($scope.checkStrIsBlank($scope.source.chooseCountyName)) {
				destInfo = destInfo + "·" + $scope.source.chooseCountyName;
			}
			var dest = destInfo; // 目的地
			var deliveryType = $scope.form.orderInfo.deliveryType;
			if (!(deliveryType == 1 || deliveryType == 2 || deliveryType == 3)) {
				commonService.alert("请选择交接方式", function(){
					jQuery("#_deliveryType").focus();
				});
				return false;
			}
			
			var deliveryTypeName = '';
			if (deliveryType == 2 || deliveryType == 3) {
				deliveryTypeName = '配送'; 
			} else {
				deliveryTypeName = '自提';
			}
			
			var trackingNum = $scope.form.orderInfo.trackingNum;
			if (undefined == trackingNum || null == trackingNum || "" == jQuery.trim(trackingNum)){
				commonService.alert("请输入运单号", function(){
					jQuery("#_trackingNum").focus();
				});
				return false;
			}
			var goodsNo = $scope.form.orderInfo.trackingNum + '-' + $scope.goodsDetailAmount.goodsCount;
			
			if(!$scope.checkStrIsBlank(bill.form.goodsDetail.rName) && !$scope.checkStrIsBlank(bill.form.goodsDetail.rLinkmanName)){
				commonService.alert("请填写收货方或者联系人", function(){
					jQuery("#_rLinkmanName").focus();
				});
				return false;
			}
			
			var consignee = '';
			if ($scope.checkStrIsBlank(bill.form.goodsDetail.rName)) {
				consignee = bill.form.goodsDetail.rName;
			}
			if ($scope.checkStrIsBlank(bill.form.goodsDetail.rLinkmanName)) {
				consignee = bill.form.goodsDetail.rLinkmanName;
			}
			
			var goodsName = $scope.form.goodsDetail.goods_0.goodsName == undefined ? '' : $scope.form.goodsDetail.goods_0.goodsName;
			if ($scope.checkStrIsBlank($scope.form.goodsDetail.goods_1.goodsName)) {
				goodsName = goodsName == '' ? $scope.form.goodsDetail.goods_1.goodsName : goodsName + '/' + $scope.form.goodsDetail.goods_1.goodsName;
			}
			
			commonService.postUrl("staticDataBO.ajax?cmd=queryOrgPhone",{"orgId":$scope.form.orderInfo.distributionOrgId, "openOrgId": $scope.form.orderInfo.orgId},function(data){
				var lineName = '珠三角至潮汕往返甩挂专线';
				var tenantName = data.tenantName;
				// 先取中转网点，如果没有的话，再开单网点.实在没有就取当前网点的客服电话
				var tenantStaffPhone = data.descOrgId;
				if (undefined == tenantStaffPhone)
					tenantStaffPhone = data.openOrgStaffPhone;
				if (undefined == tenantStaffPhone)
					tenantStaffPhone = data.beginOrgId;
				
				var source = data.destOrgName;// 首先看看有没有中转网点，没有的话，就取开单网点
				if (undefined == source)
					source = data.beginOrgName;
				
				var stickerInfo = new StickerInfo(tenantName,tenantStaffPhone,lineName,source,dest,deliveryTypeName,goodsName,consignee, goodsNo, $scope.form.orderInfo.address);
				if ($scope.totalCount != undefined && $scope.totalCount > 10) {
					commonService.confirm("需要打印的标签数量大于10份，是否继续打印？", function(){
						printStickerInfo(stickerInfo, "众邦_开单_打印标签", $scope.totalCount);
					}, function(){
						jQuery("#_totalCount").focus();
					});
				} else {
					printStickerInfo(stickerInfo, "众邦_开单_打印标签", $scope.totalCount);
				}
			});
		},
		saveAndPrintExpressInfo: function(){
			// 页面类型。1--> 运单录入；2-->运单详情；3-->运单修改
			if ($scope.pageType == 2) {// 保存
				$scope.printExpressInfo();
			} else if ($scope.pageType == 1 || $scope.pageType == 3) {
				$scope.submit($scope.printExpressInfo);
			}
		},
		/**
		 * 打印运单
		 */
		printExpressInfo:function(){
			var startPhone="";
			var param={"orgId": -1};
			commonService.postUrl("staticDataBO.ajax?cmd=queryOrgPhone",param,function(data){
				if(data != null && data != undefined && data != ""){
					startPhone=data.beginOrgId;
					var addressBean = new ExpressAddressBean(data.beginOrgName, startPhone, $scope.getDestCityName(), $scope.form.goodsDetail.rBill);
					var othersInfo = new ExpressOthersInfo(new Date(), $scope.form.orderInfo.remarks, new Date(), $scope.userName);

					var pName = $scope.form.goodsDetail.pName == undefined ? "" : $scope.form.goodsDetail.pName;
					var pLinkmanName = $scope.form.goodsDetail.pLinkmanName == undefined ? "" : $scope.form.goodsDetail.pLinkmanName; 
					var pTelephone = $scope.form.goodsDetail.pTelephone == undefined ? "" : $scope.form.goodsDetail.pTelephone;
					var pBill = $scope.form.goodsDetail.pBill == undefined ? "" : $scope.form.goodsDetail.pBill;
					var sourceNumber = pTelephone == "" ? pBill : pTelephone + (pBill == "" ? "" : "/" + pBill);
					var sourceName = pName == "" ? pLinkmanName : pName + (pLinkmanName == "" ? "" : "/" + pLinkmanName);
					// var sender = new ExpressParticipantBean(sourceName, sourceNumber, data.beginAddress);
					var sender = new ExpressParticipantBean(sourceName, sourceNumber, '');
					
					var rName = $scope.form.goodsDetail.rName == undefined ? "" : $scope.form.goodsDetail.rName;
					var rLinkmanName = $scope.form.goodsDetail.rLinkmanName == undefined ? "" : $scope.form.goodsDetail.rLinkmanName;
					var rTelephone = $scope.form.goodsDetail.rTelephone == undefined ? "" : $scope.form.goodsDetail.rTelephone;
					var rBill = $scope.form.goodsDetail.rBill == undefined ? "" : $scope.form.goodsDetail.rBill;
					var destNumber = rTelephone == "" ? rBill : rTelephone + (rBill == "" ? "" : "/" + rBill);
					var destName = rName == "" ? rLinkmanName : rName + (rLinkmanName == "" ? "" : "/" + rLinkmanName);
					var addressee = new ExpressParticipantBean(destName, destNumber, $scope.form.orderInfo.address);
					
					var goodsInfo1 = new ExpressGoodsInfo($scope.form.goodsDetail.goods_0.goodsName, $scope.form.goodsDetail.goods_0.goodsPrice, $scope.form.goodsDetail.goods_0.goodsCount, bill.forEach($scope.packTypeData.items,$scope.form.goodsDetail.goods_0.packingType), $scope.form.goodsDetail.goods_0.weight, $scope.form.goodsDetail.goods_0.volume);
					var goodsInfoArray = new Array();
					if($scope.form.goodsDetail.goods_1.goodsName!=null&&$scope.form.goodsDetail.goods_1.goodsName!=undefined&&$scope.form.goodsDetail.goods_1.goodsName!=''){
						var goodsInfo2 = new ExpressGoodsInfo($scope.form.goodsDetail.goods_1.goodsName, $scope.form.goodsDetail.goods_1.goodsPrice, $scope.form.goodsDetail.goods_1.goodsCount, bill.forEach($scope.packTypeData.items,$scope.form.goodsDetail.goods_1.packingType), $scope.form.goodsDetail.goods_1.weight, $scope.form.goodsDetail.goods_1.volume);
						goodsInfoArray.push(goodsInfo2);
					}
					goodsInfoArray.push(goodsInfo1);
					//付款方式
					var payType=new Array();
					if(bill.form.ordFee.paymentType == null || bill.form.ordFee.paymentType == undefined || bill.form.ordFee.paymentType == ""){
						commonService.alert("请输入付款方式");
						return;
					}
					if(bill.form.ordFee.paymentType == 1){
						payType.push(4);
					}else if(bill.form.ordFee.paymentType == 2 ){
						payType.push(1);
					}else if(bill.form.ordFee.paymentType == 3 ){
						payType.push(3);
					}else if(bill.form.ordFee.paymentType == 4 ){
						payType.push(2);
					}else{
						if(bill.form.ordFee.cashPayment>0){
							payType.push(4);
						}
						if(bill.form.ordFee.receiptPayment>0){
							payType.push(2);				
						}
						if(bill.form.ordFee.monthlyPayment>0){
							payType.push(3);
						}
						if(bill.form.ordFee.freightCollect>0){
							payType.push(1);
						}
					}
					var deliveryTypes=new Array();
					if($scope.form.orderInfo.deliveryType==1){
						deliveryTypes.push(1);
					}else{
						deliveryTypes.push(2);
					}
					// modify by 陈俊，只要有回单号码，就表示需要回单 2016-07-01
					// if($scope.form.ordFee.receiptPayment>0){
					if($scope.checkStrIsBlank($scope.form.orderInfo.receiptNum)){
						deliveryTypes.push(4);				
					}
					if(document.getElementById("check-1").checked == true){
						deliveryTypes.push(3);				
					}
					var deliverInfo = new ExpressDeliverInfo(payType,deliveryTypes,1,1);
					
					var deliveryCosts="";
					if($scope.goodsDetailAmount.deliveryCosts>0){// 送货费
						deliveryCosts=$scope.goodsDetailAmount.deliveryCosts;
					}
					var freight="";
					if($scope.goodsDetailAmount.freight>0){// 运费
						freight=$scope.goodsDetailAmount.freight;
					}
					var offer="";
					if($scope.goodsDetailAmount.offer>0){// 保险费
						offer=$scope.goodsDetailAmount.offer;
					}
					var pickingCosts="";
					if($scope.goodsDetailAmount.pickingCosts>0){//提货费
						pickingCosts=$scope.goodsDetailAmount.pickingCosts;
					}
					var collectingMoney="";
					if($scope.goodsDetailAmount.collectingMoney>0){// 代收货款
						collectingMoney=$scope.goodsDetailAmount.collectingMoney;
					}
					
					// 提现、代收款累计
					var collectingMoneyAdd=$scope.goodsDetailAmount.collectingMoney * 1 + $scope.form.ordFee.freightCollect * 1;
					if(collectingMoneyAdd<=0){
						collectingMoneyAdd="";
					}
					// 其他费用procedureFee handingCosts packingCosts upstairsFee
					var otherFee = $scope.goodsDetailAmount.handingCosts * 1 
								 + $scope.goodsDetailAmount.packingCosts * 1
								 //+ $scope.goodsDetailAmount.collectingMoney * 1
								 + $scope.goodsDetailAmount.upstairsFee * 1
								 + $scope.goodsDetailAmount.procedureFee * 1;
					if(otherFee<=0){
						otherFee="";
					}
					var discount = ($scope.goodsDetailAmount.discount == undefined || $scope.goodsDetailAmount.discount == 0) ? '' : $scope.goodsDetailAmount.discount;
					var costInfo = new ExpressCostInfo(freight, deliveryCosts, offer, pickingCosts, otherFee, $scope.goodsDetailAmount.total, collectingMoney, collectingMoneyAdd, discount);
					var expressBean = new ExpressBean(addressBean, sender, addressee, goodsInfoArray, deliverInfo, costInfo, othersInfo);
				 	printExpressInfo(expressBean,"sss");
				}
			});
		},
		close:function(){
    		commonService.closeToParentTab(true);
    	},
		forEach:function(data,id){
			var codeName='';
			for(var i=0;i<data.length;i++){
				if(id==data[i].codeValue){
					codeName= data[i].codeName;
					break;
				}
			}
			return codeName;
		}
	};
	bill.init(); 
}]);
