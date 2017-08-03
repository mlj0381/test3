var autoComplete = null;
var view = getQueryString("view");//view=8 中转管理那边跳转过来的详情
/*var orderId = getQueryString("orderId");*/
var trackingNum = getQueryString("trackingNum");
var orgId = getQueryString("orgId");/** 分拨中心的人进来查看详情，需要做特殊控制，例如：无法查看费用等*/
var type = getQueryString("type");
var ordBillingDetailApp = angular.module("ordBillingDetailApp", ['commonApp','angucomplete','ngTouch','billingCommon']);
ordBillingDetailApp.controller("ordBillingDetailCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var bill = {
		init:function(){
			$scope.totalValue = 0;
			this.bindEvent();
			this.initData();
			this.initStaticData();
			this.initView();
			this.getTransinfo();
			$scope.transfer={};
			
		},
		
		bindEvent:function(){
			//查询异常和问题件信息
			$scope.queryException = this.queryException;
			//查询中转信息
			$scope.queryOrderRelTransitOutgoing = this.queryOrderRelTransitOutgoing;
			//差询核销信息
			$scope.queryPorve = this.queryPorve;
			$scope.queryLog = this.queryLog;
			
			$scope.setTab = this.setTab;
			$scope.isShow = this.isShow;
			$scope.refreshRouteTowardsDtl = this.refreshRouteTowardsDtl;
			$scope.updateGoodsDetailAmount = this.updateGoodsDetailAmount;
			$scope.forEach=this.forEach;
			//打印运单
			$scope.printExpressInfo=this.printExpressInfo;
			//打印回单
			$scope.printEnvelopeInfo=this.printEnvelopeInfo;
			$scope.printStickerInfo = this.printStickerInfo;
			$scope.checkStrIsBlank = this.checkStrIsBlank;
			$scope.changeDistributionOrgId = this.changeDistributionOrgId;
			$scope.initView = this.initView;
			$scope.filterPayment = this.filterPayment;
			$scope.countyCreat = this.countyCreat;
			$scope.streetCreat = this.streetCreat;
			$scope.callBack = this.callBack;
			//选择承运商
			$scope.changeCarrierCompany = this.changeCarrierCompany;
			$scope.getTransferValue = this.getTransferValue;
			//提交中转s
			$scope.doSave = this.doSave;
			$scope.close = this.close;
		},
		close:function(){
			commonService.closeToParentTab(true);
		},
		doSave:function(){
			if($scope.transfer.orgId == null || $scope.transfer.orgId == undefined || $scope.transfer.orgId == ""){
				commonService.alert("请选择承运商");
				return false;
			}
			var param = {};
			param.batchNum = $scope.batchNum;
			param.orgId = $scope.transfer.orgId;
			var objList = {};
			objList.consignorName = $scope.transfer.linkMan;
			objList.consignorBill = $scope.transfer.linkPhone;
			objList.orderId = $scope.form.orderInfo.orderId;
			objList.transferValue = $scope.ordTransferCost.transferValue;
			var b = new Array();
			b.push(objList);
			param.num = 1;
			param.orderList = b;
			param.ordTransferCost = {};
			param.ordTransferCost.getherValue = $scope.ordTransferCost.gather;
			param.ordTransferCost.remark = $scope.ordTransferCost.remark;
			param.ordTransferCost.transferNum = 1;
			param.ordTransferCost.actualWeight = $scope.actualWeight;
			param.ordTransferCost.actualVolume = $scope.actualVolume;
			
			if($('#check-33').is(':checked')){
				//未收
				param.ordTransferCost.isGet = 0;
			}else{
				//已收
				param.ordTransferCost.isGet = 1;
			}
			if($('#check-44').is(':checked')){
				//未付
				param.ordTransferCost.isPay = 0;
			}else{
				//已付
				param.ordTransferCost.isPay = 1;
			}
			var url = "transferBO.ajax?cmd=doSave";
			commonService.postUrl(url,param,function(data){
				if(data != null && data != undefined && data != ""){
					commonService.alert("提交成功");
					$scope.batchNum = data.batchNum;
				}
			});
		},
		//计算应收还是应付
		getTransferValue:function(){
			var value  = $scope.totalValue - $scope.ordTransferCost.transferValue;
			if(value >=0){
				$scope.ordTransferCost.gather =  Math.abs(value.toFixed(2));
				$scope.ordTransferCost.payther = "";
			}else{
				$scope.ordTransferCost.payther = Math.abs(value.toFixed(2));
				$scope.ordTransferCost.gather = "";
			}
		},
		getTransinfo:function(){
			//专线查询
			var url = "organizationBO.ajax?cmd=queryOrgListByPage";
			commonService.postUrl(url,"_ALLEXPORT=1&myself=0",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.transferInfo = data.items;
					var url2 = "organizationBO.ajax?cmd=getCarri";
					//合作商查询
					commonService.postUrl(url2,"",function(data){
						if(data != null && data != undefined && data != ""){
							for(var i=0;i<data.items.length;i++){
								var b = {
										"name":data.items[i].orgName,
										"linkMan":data.items[i].orgPrincipal,
										"linkPhone":data.items[i].orgPrincipalPhone,
										"address":data.items[i].departmentAddress,
										//提货电话
										"carryLinkPhone":data.items[i].carryLinkPhone,
										"provinceName":data.items[i].provinceName,
										"regionName":data.items[i].regionName,
										"countyName":data.items[i].countyName,
										"orgId":data.items[i].orgId,
								};
								$scope.transferInfo.push(b);
							}
						}
					});
				}
			});
			
		},
		//选择承运商
		changeCarrierCompany: function(obj) {
			$scope.transfer.orgId = obj.orgId;
			$scope.transfer.linkMan = obj.linkMan;
			$scope.transfer.linkPhone = obj.linkPhone;
			$scope.transfer.carryLinkPhone=obj.carryLinkPhone;
			$scope.transfer.address=obj.address;
		},
		//保存初始化
		initData:function(){
			$scope.orgName = userInfo.orgName;
			$scope.roateData = {};
			$scope.selectDate={};
			$scope.all=true;
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
				total: 0.0,
			};
		//	$scope.form.orderInfo = {};
			//等放货通知默认 不选中 与form.order.releaseNote 对应 false==0 true ==1
			$scope.releaseNote = false;
			$scope.valuables = false;
			$scope.isPayDiscount = false;
			$scope.isPayCash = false;

			// 标识当前操作员是否为分拨中心的操作员
			$scope.isFbzxOperator = false;
			if (orgId != undefined && orgId > 0) {
				$scope.isFbzxOperator = true;
			}
		},
		callBack:function(r){
			var districtId=r.originalObject.districtId;
			var name =r.originalObject.name;
			var cityName =r.originalObject.cityName;
			var provinceName =r.originalObject.provinceName;
			var provinceId=districtId.substr(0,2);
			var cityId=districtId.substr(0,5);
			$scope.form.orderInfo.provinceName = provinceName;
			$scope.form.orderInfo.provinceId = provinceId;
			$scope.form.orderInfo.cityId = cityId;
			$scope.countyCreat(cityId,districtId);
			$scope.form.orderInfo.countyId = districtId;
			$scope.arrivePointSet();
			jQuery("#_address").focus();
			return cityName;
		},
		filterPayment:function(items,exceptValue,selectedValue){
			var tempItems = new Array();
			var allItem = {"codeName":"无","codeValue":"-1"};
			tempItems.push(allItem);
			for (var i = 0; i < items.length; i++) {
				if(items[i].codeValue!=exceptValue){
					tempItems.push(items[i]);
				}
			}
			$scope.paymentTypeData2 = tempItems;
			if(selectedValue!=undefined){
				$scope.form.ordFee.paymentType2 = selectedValue.toString() ;
			}
			else
			{
				$scope.form.ordFee.paymentType2 = "-1";
			}

		},
		//获取静态数据
		initStaticData:function(){
			//bill.queryRoateRuting();
			/** Modify by chenjun 2016-06-03, 控制先加载网点，然后开单人员选择网点之后，在加载出目的地，和线路路由 Start*/
			
			//获取交接方式
			commonService.postUrl("staticDataBO.ajax?cmd=selectDeliveryType","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.deliveryTypeData = data;
				}
			});
			commonService.postUrl("webCmUserInfoBO.ajax?cmd=getUserOrgType","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.orgType= data.orgType;
				}
			});
			//获取货物类型
			commonService.postUrl("staticDataBO.ajax?cmd=selectGoodsType","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.goodsTypeData = data;
				}
			});
			
			//获取包装类型
			commonService.postUrl("staticDataBO.ajax?cmd=selectPackType","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.packTypeData = data;
				}
			});
			
			//获取常见品名
			commonService.postUrl("staticDataBO.ajax?cmd=loadCommonGoodsName","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.commonGoodsName = data;
				}
			});
			
			//获取计费方式
			commonService.postUrl("staticDataBO.ajax?cmd=selectBillingType","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.billingTypeData = data;
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
				}
			});
			
			//是否需要安装车辆
			commonService.postUrl("staticDataBO.ajax?cmd=selectInstall","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.isInstallData = data;
				}
			});
		},
		setTab : function(obj){
			$scope.isShow = {};
			eval("$scope.isShow.isShow"+obj+"=true");
			 for(i=1;i<=5;i++){
				  var menu=document.getElementById("one"+i);
				  if(menu != undefined){
					  menu.className=i==obj?"hover":"";
				  }
				  
			 }
		},
		isShow :{
			isShow1:true,
			isShow2:false,
			isShow3:false,
			isShow4:false,
			isShow5:false,
		},
		// 刷新线路路由
		refreshRouteTowardsDtl: function(){
			if($scope.form.orderInfo.distributionOrgId!=null && $scope.form.orderInfo.distributionOrgId!=undefined && $scope.form.orderInfo.distributionOrgId!=-1){
			    var param = {"endOrgId": $scope.form.orderInfo.distributionOrgId,"beginOrgId": $scope.form.orderInfo.orgId};
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
		
		queryOrderRelTransitOutgoing:function(orderId){
			//查询中转信息
    		var url="orderInfoBO.ajax?cmd=queryOrderRelTransitOutgoing";
			commonService.postUrl(url,{"orderId":orderId,"needControlPriv":true},function(objData){
				$scope.orderRelTransitOut = objData.resultObject;
				if($scope.orderRelTransitOut!=null && $scope.orderRelTransitOut!=undefined){
				   $scope.orderRelTransitOut.outgoingFee = $scope.orderRelTransitOut.outgoingFee/100; 
				}
			});
			//查询配载信息
    		var url="orderInfoBO.ajax?cmd=QueryOrderDepar";
			commonService.postUrl(url,{"orderId":orderId},function(deparData){
					$scope.OrderDepar = deparData.items;
			});
		},
			
		//查询异常和问题件信息
		queryException:function(){
			commonService.postUrl("ordExceptionBO.ajax?cmd=doQuery",{"page":"1","rows":"100","trackingNum":trackingNum},function(data){
				$scope.exceptionInfos=data.items;
			});
			
			commonService.postUrl("ordQuestionBO.ajax?cmd=doQuery",{"page":"1","rows":"100","trackingNum":trackingNum},function(data){
				$scope.questtionInfos=data.items;
			});
		},
		//查询订单日志
		queryLog:function(){
			commonService.postUrl("orderInfoBO.ajax?cmd=queryOrderLog",{"trackingNum":trackingNum},function(data){
				$scope.ordLogInfos=data.items;
			});
		},
		//查询核销信息
		queryPorve:function(orderId,orgId){
		       var url = "acProveManageBO.ajax?cmd=doQueryPorve";
		       commonService.postUrl(url,{"_ALLEXPORT":"1","trackingNum":trackingNum,"orgId":orgId},function(data){
					$scope.PorveInfos=data.items;
				});
		       var url = "routeCostBO.ajax?cmd=queryOrderCost";
		       commonService.postUrl(url,{"orderId":orderId},function(routeData){
					$scope.RouteCost=routeData.ordList;
				});
		},
		//订单详情
		initView:function(){
			//控制全局是否修改
			$scope.all = true;
			$scope.isInstall = true;
			$scope.view = false;
			$scope.pageType = 2;
			$scope.pageTitle = '运单详情';
			$scope.showOrderId = true;
			$scope.showOther = true;
			jQuery("#_source").attr('disabled', true);
			//展示订单号
			if($scope.selectDate.trackingNum!=null &&  $scope.selectDate.trackingNum !=undefined && $scope.selectDate.trackingNum!="" ){
				trackingNum=$scope.selectDate.trackingNum;
			}
			var param={"trackingNum":trackingNum};
			var datailUrl="";
//			if(type!=null && type!=undefined && type!="" && type=="3" ){
				datailUrl="orderInfoBO.ajax?cmd=queryOrderDetail";
//			}else{
//				datailUrl="orderInfoBO.ajax?cmd=queryOrderInfoDetail";
//			}
			commonService.postUrl(datailUrl,param,function(data){
				if(data != null && data != undefined && data != ""){
					//返回数据，并且处理数据的显示
					$scope.form = {};
					$scope.form.orderInfo = data.items[0].orderInfo;
					$scope.form.orderInfo.address = data.items[0].orderInfo.address;
					$scope.form.ordFee = data.items[0].ordFee;
					//初始化付款方式2
					$scope.filterPayment($scope.paymentTypeData.items,$scope.form.ordFee.paymentType,$scope.form.ordFee.paymentType2);
					//师傅相关
//					$scope.form.orderInfo.sfFee
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
					$scope.form.orderInfo.distributionOrgId  = $scope.form.orderInfo.distributionOrgId;
					//下拉框
					$scope.form.orderInfo.descRegion = data.items[0].orderInfo.descRegion+"";

					//显示目的地和配送网点
					bill.queryRoateRuting('');
					bill.doQueryArea(data.items[0].orderInfo.descRegion);
//					var selectedDelivery ;
//					for(var i = 0;i<$scope.deliveryTypeData.items.length;i++){
//						if(data.items[0].orderInfo.deliveryType==$scope.deliveryTypeData.items[i].codeValue){
//							selectedDelivery = $scope.deliveryTypeData.items[i];
//							break;
//						}
//					}
//					$scope.form.orderInfo.deliveryObj = selectedDelivery;
					$scope.form.orderInfo.deliveryType = data.items[0].orderInfo.deliveryType+"";;
					$scope.form.orderInfo.isInstall = data.items[0].orderInfo.isInstall+"";
					$scope.form.orderInfo.vehicleId = data.items[0].orderInfo.vehicleId+"";
					$scope.form.orderInfo.notes = data.items[0].orderInfo.notes+"";
					$scope.form.orderInfo.transportType = data.items[0].orderInfo.transportType+"";
					$scope.form.orderInfo.salesmanId = data.items[0].orderInfo.salesmanId;
					$scope.form.orderInfo.inputUserId = userInfo.userId;
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
					$scope.userName=data.items[0].orderInfo.inputUserName;
					$scope.currOrgName=data.items[0].orderInfo.orgIdName;
					$scope.form.orderInfo.inputUserId=data.items[0].orderInfo.inputUserId;
					$scope.form.orderInfo.serviceType=data.items[0].orderInfo.serviceType+"";
					$scope.totalValue = 0;
					$scope.actualWeight = 0;
					$scope.actualVolume = 0;
					if(data.items[0].ordFee.paymentType == 2){
						$scope.totalValue = $scope.totalValue + data.items[0].ordFee.cashMoney/100;
					}
					if(data.items[0].ordFee.paymentType2 == 2){
						$scope.totalValue = $scope.totalValue + data.items[0].ordFee.cashMoney2/100;
					}
					//处理货物显示
					for(var i=0;i<data.items.length;i++){
						eval("$scope.form.goodsDetail.goods_"+i+".isShow=true");
						eval("$scope.form.goodsDetail.goods_"+i+".goodsName='"+data.items[i].goodsDetail.goodsName+"'");
						if (undefined != data.items[i].goodsDetail.goodsCount && null != data.items[i].goodsDetail.goodsCount) 
						eval("$scope.form.goodsDetail.goods_"+i+".goodsCount='"+data.items[i].goodsDetail.goodsCount+"'");
						eval("$scope.form.goodsDetail.goods_"+i+".packingType='"+data.items[i].goodsDetail.packingType+"'");
						if (undefined != data.items[i].goodsDetail.weight && null != data.items[i].goodsDetail.weight) 
							eval("$scope.form.goodsDetail.goods_"+i+".weight='"+data.items[i].goodsDetail.weight+"'");
							$scope.actualWeight = $scope.actualWeight + data.items[i].goodsDetail.weight;
						if (undefined != data.items[i].goodsDetail.volume && null != data.items[i].goodsDetail.volume)
							$scope.actualVolume = $scope.actualVolume + data.items[i].goodsDetail.volume;
							eval("$scope.form.goodsDetail.goods_"+i+".volume='"+data.items[i].goodsDetail.volume+"'");
						if (undefined != data.items[i].goodsDetail.freight && null != data.items[i].goodsDetail.freight) 
							eval("$scope.form.goodsDetail.goods_"+i+".freight='"+data.items[i].goodsDetail.freight/100+"'");
						if (undefined != data.items[i].goodsDetail.installCosts && null != data.items[i].goodsDetail.installCosts) 
							eval("$scope.form.goodsDetail.goods_"+i+".installCosts='"+data.items[i].goodsDetail.installCosts/100+"'");
						if (undefined != data.items[i].goodsDetail.discount && null != data.items[i].goodsDetail.discount)
							eval("$scope.form.goodsDetail.goods_"+i+".discount='"+data.items[i].goodsDetail.discount/100+"'");
						if (undefined != data.items[i].goodsDetail.deliveryCosts && null != data.items[i].goodsDetail.deliveryCosts)
							eval("$scope.form.goodsDetail.goods_"+i+".deliveryCosts='"+data.items[i].goodsDetail.deliveryCosts/100+"'");
						if (undefined != data.items[i].goodsDetail.collectingMoney && null != data.items[i].goodsDetail.collectingMoney)
							eval("$scope.form.goodsDetail.goods_"+i+".collectingMoney='"+data.items[i].goodsDetail.collectingMoney/100+"'");
							$scope.totalValue = $scope.totalValue + data.items[i].goodsDetail.collectingMoney/100;
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
						if (undefined != data.items[i].goodsDetail.installCount && null != data.items[i].goodsDetail.installCount)
							eval("$scope.form.goodsDetail.goods_"+i+".installCount='"+data.items[i].goodsDetail.installCount+"'");
						if (undefined != data.items[i].goodsDetail.packageCounts && null != data.items[i].goodsDetail.packageCounts)
							eval("$scope.form.goodsDetail.goods_"+i+".packageCounts='"+data.items[i].goodsDetail.packageCounts+"'");
						if (undefined != data.items[i].goodsDetail.custOrder && null != data.items[i].goodsDetail.custOrder)
							eval("$scope.form.goodsDetail.goods_"+i+".custOrder='"+data.items[i].goodsDetail.custOrder+"'");
						eval("$scope.form.goodsDetail.goods_"+i+".billingType='"+data.items[i].goodsDetail.billingType+"'");
//						if (undefined != data.items[i].goodsDetail.packageCounts && null != data.items[i].goodsDetail.packageCounts)
//							eval("$scope.form.goodsDetail.goods_"+i+".packageCounts='"+data.items[i].goodsDetail.packageCounts+"'");
						//计算价格回选
						if (undefined != data.items[i].goodsDetail.weightUnit && null != data.items[i].goodsDetail.weightUnit){
							eval("$scope.form.goodsDetail.goods_"+i+".weightUnit='"+(data.items[i].goodsDetail.weightUnit/100)+"'");
						}
						if (undefined != data.items[i].goodsDetail.volumeUnit && null != data.items[i].goodsDetail.volumeUnit){
							eval("$scope.form.goodsDetail.goods_"+i+".volumeUnit='"+(data.items[i].goodsDetail.volumeUnit/100)+"'");
						}
					
					}
					//处理费用显示
					bill.divCost();
					//处理订单显示
					bill.form = $scope.form;
					bill.updateCount();
					$scope.refreshRouteTowardsDtl();
					$scope.getTransferValue();
//					$scope.form.orderInfo.deliveryType = $scope.form.orderInfo.deliveryObj.codeValue;
					$scope.updateGoodsDetailAmount();
					// 刷新目的地信息
					commonService.postUrl("staticDataBO.ajax?cmd=revAddressInfo", {
						streetId: $scope.form.orderInfo.destStreet,
						districtId: $scope.form.orderInfo.destCounty,
						cityId: $scope.form.orderInfo.destCity,
						provinceId: $scope.form.orderInfo.destProvince
					},function(data){
						if(data != null && data != undefined && data != ""){
							if (undefined != data.provinceId) {
								$scope.form.orderInfo.provinceId =  data.provinceId;
								$scope.form.orderInfo.provinceName =  data.provinceName;
							}
							if (undefined != data.cityId) {
								$scope.form.orderInfo.cityId =  data.cityId;
								jQuery("#cityName_value").val(data.cityName);
							}
							if (undefined != data.districtId) {
								$scope.countyCreat($scope.form.orderInfo.cityId,data.districtId);
							}
							
							if (undefined != data.streetId) {
								$scope.streetCreat(data.districtId,data.streetId);
							}
						}
					});
				}
			});
		},
		countyCreat : function (regionId,value){
			var url="staticDataBO.ajax?cmd=selectDistrict&cityId="+regionId;
			commonService.postUrl(url,"",function(data){
				$scope.countyData=data;
				$scope.form.orderInfo.countyId = parseInt(value);
				$scope.streetCreat(value);
        	});
		},
		streetCreat : function (countyId,value){
			var url="staticDataBO.ajax?cmd=selectStreet&districtId="+countyId;
			commonService.postUrl(url,"",function(data){
				$scope.streetData=data;
				if(value==null||value==undefined||value==null){
					$scope.form.orderInfo.streetId = data.items[0].id;
				}
				else
				{
					$scope.form.orderInfo.streetId = parseInt(value);
				}
        	});
		},
		checkStrIsBlank: function(str){
			if(str == undefined || null == str || '' == jQuery.trim(str))
				return false;
			return true;
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
			$scope.form.ordFee.offer = $scope.form.ordFee.offer/100; 
			$scope.form.ordFee.cashMoney = $scope.form.ordFee.cashMoney/100; 
			$scope.form.ordFee.cashMoney2 = $scope.form.ordFee.cashMoney2/100; 
			$scope.form.orderInfo.totalFee = $scope.form.orderInfo.totalFee/100; 
			$scope.form.orderInfo.sfFee = $scope.form.orderInfo.sfFee != undefined &&  $scope.form.orderInfo.sfFee != null &&  $scope.form.orderInfo.sfFee != '' ?  $scope.form.orderInfo.sfFee /100 : "";
			//统计总费用 -显示费用合计
			bill.updateTotalCosts();
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
				/*$timeout(function(){
				    bill.checkPrice(true);
				},500);*/
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
			//bill.paymentChange();
		},
		/**
		 * 费用合计
		 */
		updateGoodsDetailAmount: function(){
			$scope.goodsDetailAmount.packageCounts = bill.customParseInt($scope.form.goodsDetail.goods_0.packageCounts) + bill.customParseInt($scope.form.goodsDetail.goods_1.packageCounts);
			$scope.goodsDetailAmount.goodsCount = bill.customParseInt($scope.form.goodsDetail.goods_0.goodsCount) + bill.customParseInt($scope.form.goodsDetail.goods_1.goodsCount);
			$scope.goodsDetailAmount.installCount = bill.customParseInt($scope.form.goodsDetail.goods_0.installCount) + bill.customParseInt($scope.form.goodsDetail.goods_1.installCount);
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
			$scope.goodsDetailAmount.installCosts = bill.customParseDouble($scope.form.goodsDetail.goods_0.installCosts) + bill.customParseDouble($scope.form.goodsDetail.goods_1.installCosts);
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
		//计算货物数量
		updateCount:function(){
			$scope.totalCount = 0;
			/**
			 * Modify by chenjun.目前固定两行
			 */
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
		
		//查询配送网点
		queryRoateRuting:function(orgId){
			if(view!=null && view!=undefined && view!="" && (view=="8" || $scope.form.orderInfo.orderType==1)){
				commonService.postUrl("routeBO.ajax?cmd=queryRoateTowards","endOwnerRegion=&orgType=4&needFilter=true&orgId="+orgId,function(data){
					if(data != null && data != undefined && data != ""){
						var defaultObj = {
							endOrgId: -1,
							endOwnerName: '空',
							endOwnerRegionName: ''
						};
						var array = new Array();
						array.push(defaultObj);
						for(var i = 0; i < data.items.length; i++) {
							array.push(data.items[i]);	
						}
						
						$scope.roateData.items = array;
						/*$scope.form.orderInfo.descRegion =  .orderInfo.descRegion+"";*/
							$scope.form.orderInfo.distributionOrgId=parseInt($scope.form.orderInfo.distributionOrgId);
							if ($scope.form.orderInfo.distributionOrgId == undefined) {
								$timeout(function(){
									$scope.changeDistributionOrgId($scope.form.orderInfo.distributionOrgId);
								},500);
							} else {
								$scope.changeDistributionOrgId($scope.form.orderInfo.distributionOrgId);
							}
					}
				});
			}else{
				commonService.postUrl("routeBO.ajax?cmd=queryRoateTowards","endOwnerRegion=&orgType=1",function(data){
					if(data != null && data != undefined && data != ""){
						$scope.roateData = data;
						if ($scope.pageType == 1 && undefined != data && undefined != data.items && data.items.length > 0) {
							$scope.form.orderInfo.distributionOrgId = data.items[0].endOrgId;
							//$scope.changeDistributionOrgId();
							$scope.doQueryArea();
						}
					}
				});
				
			}
		
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
			
		},
		//配送区域查询
		doQueryArea:function(defaultDescRegion){
			/**Modify by chenjun, 目前没有“是否安装”选项，也就无需有车辆信息
				bill.doQueryVehicle();
			*/
			$scope.areaData = [];
			//查询配送区域
			if($scope.form.orderInfo.deliveryType ==2 || $scope.form.orderInfo.deliveryType == 3){
				$scope.areaEdit = false;
				if($scope.form.orderInfo.distributionOrgId>0){
					commonService.postUrl("webAcAreaFeeConfigBO.ajax?cmd=doQueryArea","orgId="+$scope.form.orderInfo.distributionOrgId,function(data){
						$scope.areaData = data;
						$scope.form.orderInfo.descRegion = bill.customParseInt(defaultDescRegion);
					});
				}
			}
			
		},
		
		
		
		/**
		 * 打印运单
		 */
		printExpressInfo:function(){
			var descPhone='';
			var startPhone="";
			var param={"orgId":bill.form.orderInfo.distributionOrgId};
			var chooseRegionId = $scope.source.chooseRegionId;// 地市
			if (!$scope.checkStrIsBlank(chooseRegionId)) {
				commonService.alert("请选择目的地所在的地市!", function(){
					jQuery("#_source").focus();
				});
				return false;
			}
			
			var destInfo = $scope.source.chooseRegionName;
			if($scope.checkStrIsBlank($scope.source.chooseStreetName)) {
				destInfo = destInfo + "·" + $scope.source.chooseStreetName;
			} else if ($scope.checkStrIsBlank($scope.source.chooseCountyName)) {
				destInfo = destInfo + "·" + $scope.source.chooseCountyName;
			}
			commonService.postUrl("staticDataBO.ajax?cmd=queryOrgPhone",param,function(data){
				if(data != null && data != undefined && data != ""){
					descPhone=data.descOrgId;
					startPhone=data.beginOrgId;
					// var addressBean = new ExpressAddressBean(userInfo.cityName, startPhone, $scope.form.orderInfo.destCityName, descPhone);
					var addressBean = new ExpressAddressBean(data.beginOrgName, startPhone, data.destOrgName, descPhone, destInfo);
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
					var collectingMoneyAdd= $scope.goodsDetailAmount.collectingMoney * 1 +$scope.form.ordFee.freightCollect * 1;
					if(collectingMoneyAdd<=0){
						collectingMoneyAdd="";
					}
					var otherFee = $scope.goodsDetailAmount.handingCosts * 1 
								 + $scope.goodsDetailAmount.packingCosts * 1
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
			
			var dest = '';
			for (var i = 0; i < $scope.roateData.items.length; i++) {
				var item = $scope.roateData.items[i];
				if ($scope.form.orderInfo.distributionOrgId == item.endOrgId) {
					dest = item.endOwnerName;
					break;
				}
			}
			
			var sender = $scope.form.goodsDetail.pName;
			if(!$scope.checkStrIsBlank(sender)) {
				sender = $scope.form.goodsDetail.pLinkmanName;
			}
			if (sender == undefined)
				sender = '';
			
			var freight = $scope.goodsDetailAmount.freight;// 运费
			if(undefined == freight) freight = 0;
			
			var goodsName = $scope.form.goodsDetail.goods_0.goodsName == undefined ? '' : $scope.form.goodsDetail.goods_0.goodsName;
			if ($scope.checkStrIsBlank($scope.form.goodsDetail.goods_1.goodsName)) {
				goodsName = goodsName == '' ? $scope.form.goodsDetail.goods_1.goodsName : goodsName + '/' + $scope.form.goodsDetail.goods_1.goodsName;
			}
			
			commonService.postUrl("staticDataBO.ajax?cmd=queryOrgPhone",{"orgId":-1},function(data){
				var source = data.beginOrgName;
				var supportStaffPhone = data.beginOrgId;// 开单网点联系电话
				//邮编、出发地、目的地、运单号、时间、货物名称、运费、发货人、联系方式、经办人、勾选、身份证
				var envelopeInfo = new EnvelopeInfo("", source, dest, goodsNo, new Date(), goodsName, freight, sender, supportStaffPhone, userInfo.userName, "", "");
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
			/****************配送网点******************/
			var source = userInfo.cityName;
			for (var i = 0; i < $scope.roateData.items.length; i++) {
				var item = $scope.roateData.items[i];
				if ($scope.form.orderInfo.distributionOrgId == item.endOrgId) {
					dest = item.endOwnerName;
					break;
				}
			}
			// 交接方式为“配送”，则需要打印配送网点+区域
			if ($scope.form.orderInfo.deliveryType == 2 || $scope.form.orderInfo.deliveryType == 3) {
				var areaName = '';
				for (var i = 0; undefined != $scope.areaData.items && i < $scope.areaData.items.length; i++) {
					if ($scope.form.orderInfo.descRegion == $scope.areaData.items[i].areaId) {
						areaName = $scope.areaData.items[i].areaName;
						break;
					}
				}
				dest = dest + '/' + areaName;
			}
			/****************配送网点******************/
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
			
			// 这里需要需要的是开单网点的信息
			var orgId = $scope.form.orderInfo.orgId;// 开单网点
			commonService.postUrl("staticDataBO.ajax?cmd=queryOrgPhone",{"orgId":orgId},function(data){
				var source = data.beginOrgName;
				var tenantName = data.tenantName;
				var tenantStaffPhone = data.descOrgId;// 开单网点
				if (tenantStaffPhone == undefined)// 如果为空（新单录入的时候为空），则为当前操作员所在的网点
					tenantStaffPhone = data.beginOrgId;
				var lineName = '珠三角至潮汕往返甩挂专线';
				
				address = $scope.form.orderInfo.address;
				if (deliveryType == 1)
					address = '';
				var stickerInfo = new StickerInfo(tenantName,tenantStaffPhone,lineName,source,dest,deliveryTypeName,goodsName,consignee, goodsNo, address);
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
