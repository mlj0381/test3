var autoComplete = null;
var view = getQueryString("view");
var type = getQueryString("type");
var orderId = getQueryString("orderId");// 此处的orderId是运单号
var edit = getQueryString("edit");
var billApp = angular.module("billApp", ['commonApp']);
billApp.controller("billCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.exceptionShow = true;
	$scope.dataShow = true;
	var bill = {
			init:function(){
				this.initData();
				this.bindEvent();
				this.userData();
				this.initStaticData();
				this.queryException();
				this.queryLog();
				this.queryQuestion();
				
				//发货人弹窗
				$scope.showPcum = false;
				$scope.showRcum = false;
				//费用不显示
				$scope.priceShow = false;
				$scope.totalCount = 0;
				$scope.dataShow = false;
				//查看逻辑
				if(view != null && view != undefined && view != "" && orderId != null && orderId != undefined && orderId !=""){
					//控制全局是否修改
					$scope.all = true;
					this.initView(orderId);
					$scope.isInstall = true;
					$scope.view = false;
				}else{
					$scope.view = true;
					//控制全局是否修改
					$scope.all = false;
					//修改
					if(orderId != null && orderId != undefined && orderId !=""){
						$scope.showOrderId = true;
						this.initView(orderId);
					}else{
						//新增
						$scope.showOrderId = false;
						this.initData();
					}
				}
				if(type=="3"){
					$scope.isShow=false;
				}else{
					$scope.isShow=true;
				}
			//	this.queryOrderRelTransitOutgoing();
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
				$scope.doQueryArea = this.doQueryArea;
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
				$scope.clearDestCity = this.clearDestCity;
				
				//计算货物总数
				$scope.updateCount = this.updateCount;
				
				//打印的方法
				//打印运单
				$scope.printExpressInfo=this.printExpressInfo;
				//打印回单
				$scope.printEnvelopeInfo=this.printEnvelopeInfo;
				
				$scope.setTab=this.setTab;
				//中转信息
				$scope.queryOrderRelTransitOutgoing=this.queryOrderRelTransitOutgoing;
			},
			//dhl tab
			setTab: function(name,cursel,n){
				if($scope.dataShow == false)// 隐藏“中转信息”的时候，tab为1
					n = 1;
				else 
					n = 2;
				 for(i=1;i<=n;i++){
					  var menu=document.getElementById(name+i);
					  menu.className=i==cursel?"hover":"";
					//  con.style.display=i==cursel?"block":"none";
				 }
				 $scope.notData = false;
				 if(cursel == 1){
					 $scope.exceptionShow = true;
					 $scope.con_one_show = false;
				 }else{
					 $scope.exceptionShow = false;
					 $scope.con_one_show = true;
					 // 如果订单查询的请求还没有结束，然后切换tab，岂不是查询不出数据？！
					 $scope.queryOrderRelTransitOutgoing($scope.form.orderInfo.orderId);
				 }
			},
			queryOrderRelTransitOutgoing : function(orderId){
			//	var orderId = $scope.form.orderInfo.orderId;
				var queryString="orderId="+orderId;
        		var url="orderInfoBO.ajax?cmd=queryOrderRelTransitOutgoing";
				commonService.postUrl(url,queryString,function(data){
					if (data.resultCode == '1') {
						$scope.dataShow = true;	
						$scope.notData = false;
						$scope.orderRelTransitOut = data.resultObject;
					} else {
						$scope.dataShow = false;	
						$scope.notData = true;
					}
				});
			},
			//查询异常信息
			queryException:function(){
				commonService.postUrl("ordExceptionBO.ajax?cmd=doQuery",{"page":"1","rows":"100","trackingNum":orderId},function(data){
					$scope.exceptionInfos=data.items;
				});
			},
			//查询问题件信息
			queryQuestion:function(){
				commonService.postUrl("ordQuestionBO.ajax?cmd=doQuery",{"page":"1","rows":"100","trackingNum":orderId},function(data){
					$scope.questtionInfos=data.items;
				});
			},
			//查询订单日志
			queryLog:function(){
				commonService.postUrl("orderInfoBO.ajax?cmd=queryOrderLog",{"trackingNum":orderId},function(data){
					$scope.ordLogInfos=data.items;
				});
			},
			userData:function(){
				$scope.currOrgId=userInfo.orgId;
				$scope.currOrgName=userInfo.orgName;
				$scope.userName = userInfo.userName;
			},
			//查看初始化
			initView:function(orderId){
				//-----页面展示后台显示数据逻辑
				//展示订单号
				$scope.showOrderId = false;
				var param={"trackingNum":orderId};
				var datailUrl="";
				if(type!=null && type!=undefined && type!="" && type=="3"){
					datailUrl="orderInfoBO.ajax?cmd=queryOrderDetail";
				}else{
					datailUrl="orderInfoBO.ajax?cmd=queryOrderInfoDetail";
				}
				commonService.postUrl(datailUrl,param,function(data){
					if(data != null && data != undefined && data != ""){
						
						//返回数据，并且处理数据的显示
						$scope.form = {};
						$scope.form.orderInfo = data.items[0].orderInfo;
						//$scope.form.orderInfo.address=data.items[1].orderInfo.address;
						$scope.queryOrderRelTransitOutgoing($scope.form.orderInfo.orderId);
						$scope.form.ordFee = data.items[0].ordFee;
						$scope.form.goodsDetail={
								goods_0:{
									isShow:true,
								},
								goods_1:{
									isShow:false,
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
								goodsNum:1,
						};
						//显示目的地和配送网点
						bill.queryRoateRuting("");
						
						$scope.form.orderInfo.distributionOrgId  = $scope.form.orderInfo.distributionOrgId;
						//下拉框
						//$scope.form.orderInfo.descRegion = data.items[0].orderInfo.descRegion+"";
						$scope.form.orderInfo.deliveryType = data.items[0].orderInfo.deliveryType+"";
						
						$scope.form.orderInfo.isInstall = data.items[0].orderInfo.isInstall+"";
						$scope.form.orderInfo.vehicleId = data.items[0].orderInfo.vehicleId+"";
						$scope.form.orderInfo.notes = data.items[0].orderInfo.notes+"";
						$scope.form.orderInfo.transportType = data.items[0].orderInfo.transportType+"";
						$scope.form.orderInfo.salesmanId = data.items[0].orderInfo.salesmanId;
						$scope.form.orderInfo.inputUserId = data.items[0].orderInfo.inputUserId;
						$scope.form.orderInfo.floor = data.items[0].orderInfo.floor+"";
						$scope.form.ordFee.paymentType = data.items[0].ordFee.paymentType+"";
						$scope.currOrgName = data.items[0].orderInfo.orgIdName;
						$scope.userName=data.items[0].orderInfo.inputUserName;
						//$scope.currOrgId = data.items[0].orderInfo.orgIdName;
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
						$scope.form.orderInfo.descRegion = data.items[0].orderInfo.descRegion;
						if($scope.form.orderInfo.distributionOrgId != null && $scope.form.orderInfo.distributionOrgId != undefined && $scope.form.orderInfo.distributionOrgId !=""){
							$timeout(function(){
							    bill.doQueryArea();
							},500);
						}else{
							
							$timeout(function(){
							  bill.doQueryVehicle();
							},500);
						}
						//处理货物显示
						for(var i=0;i<data.items.length;i++){
							eval("$scope.form.goodsDetail.goods_"+i+".isShow=true");
							eval("$scope.form.goodsDetail.goods_"+i+".goodsName='"+data.items[i].goodsDetail.goodsName+"'");
							eval("$scope.form.goodsDetail.goods_"+i+".goodsCount='"+data.items[i].goodsDetail.goodsCount+"'");
							if(data.items[i].goodsDetail.weight == null || data.items[i].goodsDetail.weight == undefined ){
								eval("$scope.form.goodsDetail.goods_"+i+".weight=0");
							}else{
								eval("$scope.form.goodsDetail.goods_"+i+".weight='"+data.items[i].goodsDetail.weight+"'");
							}
							eval("$scope.form.goodsDetail.goods_"+i+".volume='"+data.items[i].goodsDetail.volume+"'");
							eval("$scope.form.goodsDetail.goods_"+i+".goodsPrice='"+data.items[i].goodsDetail.goodsPrice/100+"'");
							eval("$scope.form.goodsDetail.goods_"+i+".deliveryCosts='"+data.items[i].goodsDetail.deliveryCosts/100+"'");
							eval("$scope.form.goodsDetail.goods_"+i+".freight='"+data.items[i].goodsDetail.freight/100+"'");
							eval("$scope.form.goodsDetail.goods_"+i+".collectingMoney='"+data.items[i].goodsDetail.collectingMoney/100+"'");
							if(data.items[i].goodsDetail.discount != undefined)
								eval("$scope.form.goodsDetail.goods_"+i+".discount='"+data.items[i].goodsDetail.discount/100+"'");
//							//三个下拉框
							eval("$scope.form.goodsDetail.goods_"+i+".billingType='"+data.items[i].goodsDetail.billingType+"'");
							eval("$scope.form.goodsDetail.goods_"+i+".goodsType='"+data.items[i].goodsDetail.godsType+"'");
							eval("$scope.form.goodsDetail.goods_"+i+".packingType='"+data.items[i].goodsDetail.packingType+"'");
						}
						//处理费用显示
						bill.divCost();
						//处理订单显示
						bill.form = $scope.form;
						bill.updateCount();
						if(type!="3"){
							$timeout(function(){
							    bill.checkPrice();
							},500);
						}
					}
				});
			},
			//－修改和显示－－处理费用金额
			divCost:function(){
				if($scope.form.ordFee!=null && $scope.form.ordFee!=undefined && $scope.form.ordFee!=""){
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
					//统计总费用 -显示费用合计
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
						isShow:false,
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
					goodsNum:1,
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
				//等放货通知默认 不选中 与form.order.releaseNote 对应 false==0 true ==1
				$scope.releaseNote = false;
				$scope.valuables = false;
				$scope.isPayDiscount = false;
				$scope.isPayCash = false;
			},
			//获取静态数据
			initStaticData:function(){
				//获取目的城市信息
				commonService.postUrl("staticDataBO.ajax?cmd=selectCity","",function(data){
					if(data != null && data != undefined && data != ""){
						autoComplete = new AutoComplete('o','auto',data,["name","nameAlpha"],["id","name"],function(data,cId){
							bill.queryRoateRuting(cId);
							$scope.form.orderInfo.destCity = cId;
						});
					}
				});
				//获取交接方式
				commonService.postUrl("staticDataBO.ajax?cmd=selectDeliveryType","",function(data){
					if(data != null && data != undefined && data != ""){
						$scope.deliveryTypeData = data;
						$scope.form.orderInfo.deliveryType = $scope.deliveryTypeData.items[0].codeValue;
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
						$scope.form.orderInfo.isInstall = data.items[0].codeValue;
					}
				});
				//查询网点开单人员、业务员
				commonService.postUrl("webCmUserInfoBO.ajax?cmd=doQueryOrgUser","",function(data){
					if(data != null && data != undefined && data != ""){
						$scope.userInfoData = data;
					}
				});
			},
			clearDestCity:function(){
				bill.form.orderInfo.destCity = null;
				$scope.roateData = {};
			},
			doQueryVehicle:function(){
				if($scope.form.orderInfo.isInstall == 2){
					$scope.isInstall = false;
					//查询目的车辆信息
					if(bill.form.orderInfo.distributionOrgId == null || bill.form.orderInfo.distributionOrgId == undefined || bill.form.orderInfo.distributionOrgId == ""){
						commonService.alert("请选择配送网点!");
						return ;
					}
//					bill.form.orderInfo.driverName = "";
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
				
				commonService.postUrl("routeBO.ajax?cmd=queryRoateTowards","orgId="+$scope.form.orderInfo.orgId,function(data){
					if(data != null && data != undefined && data != ""){
						$scope.roateData = data;
					}
				});
			},
			//配送区域查询
			doQueryArea:function(){
				bill.doQueryVehicle();
				//查询配送区域
				if($scope.form.orderInfo.deliveryType ==2 || $scope.form.orderInfo.deliveryType == 3){
					$scope.areaEdit = false;
					if($scope.form.orderInfo.distributionOrgId>0){
						commonService.postUrl("webAcAreaFeeConfigBO.ajax?cmd=doQueryArea","orgId="+$scope.form.orderInfo.distributionOrgId,function(data){
							$scope.areaData = data;
						});
					}
				}
				//自提时 设置详细地址
				if($scope.form.orderInfo.deliveryType == 1){
					$scope.areaEdit = true;
					commonService.postUrl("staticDataBO.ajax?cmd=selectOrgInfo","orgId="+$scope.form.orderInfo.distributionOrgId,function(data){
						bill.form.orderInfo.address = data.departmentAddress;
					});
				}
				if($scope.all == true){
					$scope.areaEdit = true;
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
						var param={"name":rName,"type":"2","id":id};
						commonService.postUrl("customerBO.ajax?cmd=getCustomer",param,function(data){
							if(data != null && data != undefined && data != ""){
								$scope.recCustomerData = data;
							}
						});
					}
				}else if(ids ==1){
					var param={"type":"2","id":id};
					commonService.postUrl("customerBO.ajax?cmd=getCustomer",param,function(data){
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
				if(bill.form.ordFee.paymentType == 1){
					//现付
					bill.form.ordFee.freightCollect=0;
					bill.form.ordFee.monthlyPayment=0;
					bill.form.ordFee.receiptPayment=0;
					bill.form.ordFee.cashPayment = $scope.totalCosts;
				}else if(bill.form.ordFee.paymentType == 2){
					//到付
					bill.form.ordFee.cashPayment=0;
					bill.form.ordFee.monthlyPayment=0;
					bill.form.ordFee.receiptPayment=0;
					bill.form.ordFee.freightCollect = $scope.totalCosts;
				}else if(bill.form.ordFee.paymentType == 3){
					//月结
					bill.form.ordFee.cashPayment=0;
					bill.form.ordFee.freightCollect=0;
					bill.form.ordFee.receiptPayment=0;
					bill.form.ordFee.monthlyPayment = $scope.totalCosts;
				}else if(bill.form.ordFee.paymentType == 4){
					//回单付
					bill.form.ordFee.cashPayment=0;
					bill.form.ordFee.freightCollect=0;
					bill.form.ordFee.monthlyPayment=0;
					bill.form.ordFee.receiptPayment = $scope.totalCosts;
				}
			},
			//统计费用合计
			updateTotalCosts:function(){
				$scope.totalCosts = 0;
				if($scope.form.ordFee.freight != null && $scope.form.ordFee.freight != undefined && $scope.form.ordFee.freight != ""){
					var value=Math.round($scope.form.ordFee.freight*100)/100;
					if(value == NaN || value =="NaN"){
						bill.updateTotalCosts();
					}else{
						$scope.totalCosts += value;
					}
				}
				if($scope.form.ordFee.pickingCosts != null && $scope.form.ordFee.pickingCosts != undefined && $scope.form.ordFee.pickingCosts != ""){
					var value=Math.round($scope.form.ordFee.pickingCosts*100)/100;
					if(value == NaN || value =="NaN"){
						bill.updateTotalCosts();
					}else{
						$scope.totalCosts += value;
					}
				}
				if($scope.form.ordFee.deliveryCosts != null && $scope.form.ordFee.deliveryCosts != undefined && $scope.form.ordFee.deliveryCosts != ""){
					var value=Math.round($scope.form.ordFee.deliveryCosts*100)/100;
					if(value == NaN || value =="NaN"){
						bill.updateTotalCosts();
					}else{
						$scope.totalCosts += value;
					}
				}
				if($scope.form.ordFee.handingCosts != null && $scope.form.ordFee.handingCosts != undefined && $scope.form.ordFee.handingCosts != ""){
					var value=Math.round($scope.form.ordFee.handingCosts*100)/100;
					if(value == NaN || value =="NaN"){
						bill.updateTotalCosts();
					}else{
						$scope.totalCosts += value;
					}
				}
				if($scope.form.ordFee.packingCosts != null && $scope.form.ordFee.packingCosts != undefined && $scope.form.ordFee.packingCosts != ""){
					var value=Math.round($scope.form.ordFee.packingCosts*100)/100;
					if(value == NaN || value =="NaN"){
						bill.updateTotalCosts();
					}else{
						$scope.totalCosts += value;
					}
				}
				if($scope.form.ordFee.collectingMoney != null && $scope.form.ordFee.collectingMoney != undefined && $scope.form.ordFee.collectingMoney != ""){
					var value=Math.round($scope.form.ordFee.collectingMoney*100)/100;
					if(value == NaN || value =="NaN"){
						bill.updateTotalCosts();
					}else{
						$scope.totalCosts += value;
					}
					$timeout(function(){
						if(type!="3"){
							 bill.checkPrice();	
						}
					   
					},500);
				}
				if($scope.form.ordFee.procedureFee != null && $scope.form.ordFee.procedureFee != undefined && $scope.form.ordFee.procedureFee != ""){
					var value=Math.round($scope.form.ordFee.procedureFee*100)/100;
					if(value == NaN || value =="NaN"){
						bill.updateTotalCosts();
					}else{
						$scope.totalCosts += value;
					}
				}
				if($scope.form.ordFee.offer != null && $scope.form.ordFee.offer != undefined && $scope.form.ordFee.offer != ""){
					var value=Math.round($scope.form.ordFee.offer*100)/100;
					if(value == NaN || value =="NaN"){
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
					var value = eval("$scope."+valueName).replace(/^\D*(\d*(?:\.\d{0,2})?).*$/g, '$1');
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
			checkPrice:function(){
				//校验数据
				if(bill.checkPriceValue()){
					//代收货款

					var checkParam = {
							g_0:{},
							g_1:{},
					};
					checkParam.g_0.weight = bill.form.goodsDetail.goods_0.weight;
					checkParam.g_0.volume = bill.form.goodsDetail.goods_0.volume;
					if(bill.form.goodsDetail.goods_1.isShow == true){
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
					commonService.postUrl("routeCostBO.ajax?cmd=queryRouteCost",checkParam,function(data){
						if(data != null && data != undefined && data != ""){
							$scope.priceShow = true;
							$scope.allCost = data;
							for(var i=0;i<5;i++){
								if(eval("bill.form.goodsDetail.goods_"+i+".isShow") == true){
									eval("bill.form.goodsDetail.goods_"+i+".billingType ='"+eval("data.g_"+i)+"'");
								}
							}
						}
					});
				}
			},
			//计算货物数量
			updateCount:function(){
				$scope.totalCount = 0;
				for(var i=0;i<2;i++){
		//			console.log(bill.form.goodsDetail);
					if(eval("bill.form.goodsDetail.goods_"+i+".isShow") == true){
	//					console.log(eval("bill.form.goodsDetail.goods_"+i+".goodsCount"));
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
					if(bill.form.orderInfo.distributionOrgId == null || bill.form.orderInfo.distributionOrgId == undefined || bill.form.orderInfo.distributionOrgId== ""){
						commonService.alert("请选择配送网点");
						return false;
					}
				//如果交接方式是送货上楼和送货上门 楼层、是否为电梯、配送区域、详细地址为必填
			
				if(bill.form.orderInfo.deliveryType == 3){
					if(bill.form.orderInfo.floor == null || bill.form.orderInfo.floor == undefined || bill.form.orderInfo.floor == ""){
						commonService.alert("请输入楼层");
						return false;
					}
					if(bill.form.orderInfo.descRegion == null || bill.form.orderInfo.descRegion == undefined || bill.form.orderInfo.descRegion == ""){
						commonService.alert("请选择配送区域");
						return false;
					}
				}else if(bill.form.orderInfo.deliveryType == 2){
					if(bill.form.orderInfo.descRegion == null || bill.form.orderInfo.descRegion == undefined || bill.form.orderInfo.descRegion == ""){
						commonService.alert("请选择配送区域");
						return false;
					}
				}
				return true;
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


