var billingCommon=angular.module("billingCommon", []);
var paramOrderId = getQueryString("orderId");
var paramTrackingNum = getQueryString("trackingNum");
var needIngoreOrgId = getQueryString("needIngoreOrgId");
var isTab = getQueryString("isTab");
billingCommon.directive("billing", function () {
	var orderId;
	var trackingNum;
	var myBilling=
	{
	  restrict: 'E',
	  templateUrl : function(tElement,tAttrs){
      	return '../js/billingTemplate.html?ver='+tAttrs.ver;
      },
      scope: {
          "initView":"=initView",
          "createDateDetail":"=createDateDetail",//开单时间
          "orgNameDetail":"=orgNameDetail", //开单时间
          "inputUserNameDetail":"=inputUserNameDetail", //开单员
          "isShowReturn":"=isShowReturn", //返回按钮是否显示
          "isShowTab":"=isShowTab",  //TAB 显示的控制
          "setTab":"=setTab"         //查询运单信息时默认显示基本运单信息
      },
	  compile: function(element, attrs){
		  if(paramOrderId!=undefined){
			  orderId = paramOrderId;
		  }
		  else
		  {
			  trackingNum = paramTrackingNum;
		  }
	  },
	  controller: ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
		  var bill = {
					init:function(){
						this.bindEvent();
						this.userInfo();
						this.initView(orderId,trackingNum,needIngoreOrgId);
						this.initBusiConfig();
						
					},
					userInfo:function(){
						$scope.userData=userInfo;
						$scope.userTypeInfo();
					},
				/**用户类型*/
					userTypeInfo:function(){
						$scope.userTypeData=userType;
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
					bindEvent:function(){
						$scope.userTypeInfo=this.userTypeInfo;
						$scope.userInfo = this.userInfo;

						$scope.close = this.close;
						$scope.isShow = this.isShow;
						$scope.initView = this.initView;

						$scope.form = this.form;
						$scope.forEach=this.forEach;
						$scope.upNum = this.upNum; 
						$scope.upCosts = this.upCosts;
						$scope.initTenantConfig=this.initTenantConfig;
						
						$scope.checkStrIsBlank = this.checkStrIsBlank;

						$scope.openRemark = this.openRemark;
						$scope.toNewRemark = this.toNewRemark;
						$scope.newRemark = this.newRemark;
						$scope.queryRemark = this.queryRemark;
						$scope.selectRemark = this.selectRemark;
						$scope.initBusiConfig=this.initBusiConfig;
						$scope.saveRemark ={};
						$scope.saveRemark.remarks = "";
					},
					initBusiConfig:function(){
						var url = "sysBusiConfigBO.ajax?cmd=queryBusiConfig";
						commonService.postUrl(url,"",function(data){
							if(data!="" && data!=null){
								if(data.resultCode=="1"){
									//返回有数据的
									
									window.top.tenantConfig.initData(data.busiConfig);
									$scope.initTenantConfig();
								}
							}
						});
					},
					initTenantConfig:function(){
						//初始化  货品列表展示的数据
						$scope.orderGoodsRowConfig={};
						if(window.top.tenantConfig!=undefined && window.top.tenantConfig.orderGoodsRowConfig!=undefined){
							var orderGoodsRowConfig=window.top.tenantConfig.orderGoodsRowConfig;
						
							$scope.orderGoodsRowConfig.discount=orderGoodsRowConfig.discount;
							
							$scope.orderGoodsRowConfig.collectingMoney=orderGoodsRowConfig.collectingMoney;
							
							$scope.orderGoodsRowConfig.procedureFee=orderGoodsRowConfig.procedureFee;
							$scope.orderGoodsRowConfig.goodsPrice=orderGoodsRowConfig.goodsPrice;
							$scope.orderGoodsRowConfig.offer=orderGoodsRowConfig.offer;
							$scope.orderGoodsRowConfig.handingCosts=orderGoodsRowConfig.handingCosts;
							$scope.orderGoodsRowConfig.packingCosts=orderGoodsRowConfig.packingCosts;
							$scope.orderGoodsRowConfig.pickingCosts=orderGoodsRowConfig.pickingCosts;
							$scope.orderGoodsRowConfig.deliveryCosts=orderGoodsRowConfig.deliveryCosts;
							$scope.orderGoodsRowConfig.goodsType=orderGoodsRowConfig.goodsType;
							$scope.orderGoodsRowConfig.upstairsFee=orderGoodsRowConfig.upstairsFee;
							$scope.orderGoodsRowConfig.installCount=orderGoodsRowConfig.installCount;
							$scope.orderGoodsRowConfig.custOrder=orderGoodsRowConfig.custOrder;
							
						}
						$scope.shipTypeConfig={};
						if(window.top.tenantConfig!=undefined && window.top.tenantConfig.shipTypeConfig!=undefined){
							var shipTypeConfig=window.top.tenantConfig.shipTypeConfig;
							$scope.shipTypeConfig.isSeaTransport=shipTypeConfig.isSeaTransport;
						}
					},
					//查看初始化
					initView:function(orderId,trackingNum){
						//-----页面展示后台显示数据逻辑
						//展示订单号
						$scope.showOrderId = false;
						$scope.ShowTrackingNumBegin = false;
						var param={"orderId":orderId,"trackingNum":trackingNum,"needIngoreOrgId":needIngoreOrgId};
						var datailUrl="";
						datailUrl="orderInfoBO.ajax?cmd=queryOrderInfoDetailPage";
						$scope.priceShow=true;
						if($scope.setTab != undefined){
							//每次查询运单详情默认都是 运单基本信息(运单详情查询)
							 $scope.setTab(1);
						}
//						jQuery("#_submit").hide();
//						jQuery("#_save").hide();
//						jQuery("#_reset").hide();
//						jQuery('input,select,textarea').attr('disabled', true);
//						jQuery('input,select,textarea').prop('readonly',true);
//						$scope.view = false;
//						jQuery("#check-1").attr('disabled', true);
//						
//						//查询不disabled
//						jQuery('#queryOrderInfoId').attr('disabled', false);
//						jQuery('#queryOrderInfoId').prop('readonly',false);
//						
//						jQuery('#textAreaId').attr('disabled', false);
//						jQuery('#textAreaId').prop('readonly',false);
//						
//						
//						
//						jQuery('#notifyDtlName1').attr('disabled', false);
//						jQuery('#notifyDtlName1').prop('readonly',false);
//						
//						jQuery('#notifyDtlName2').attr('disabled', false);
//						jQuery('#notifyDtlName2').prop('readonly',false);
//						
//						jQuery('#notifyDtlName3').attr('disabled', false);
//						jQuery('#notifyDtlName3').prop('readonly',false);
//						
//						jQuery('#notifyDtlName4').attr('disabled', false);
//						jQuery('#notifyDtlName4').prop('readonly',false);
//						
//						jQuery('#notifyDtlName5').attr('disabled', false);
//						jQuery('#notifyDtlName5').prop('readonly',false);
//						
//						jQuery('#_rName').attr('disabled', false);
//						jQuery('#_goodsName0').attr('disabled', false);
						
						
						
						
						jQuery("input,select,textarea").css('background-color', "#fff");
						
						$scope.all = true;
						$scope.isShowTab = {};
						$scope.isShowTab.isShow1 = true; //运单详情Tab
						$scope.isShowTab.isShow2 = false; //费用信息Tab
						$scope.isShowTab.isShow3 = false; //配载信息Tab
						$scope.isShowTab.isShow4 = false; //异常信息Tab
						$scope.isShowTab.isShow5 = false; //运单日志Tab
						$scope.isShowTab.isShow6 = false; //安装信息Tab
						$scope.isShowTab.isShow7 = false; //签收信息Tab
						$scope.isShowTab.isShow8 = false; //签收信息Tab
						commonService.postUrl(datailUrl,param,function(data){
							if(data != null && data != undefined && data != ""){
								//返回数据，并且处理数据的显示
								$scope.form = {};
								$scope.form.orderInfo = data.ordOrderInfo;
								$scope.form.goodsDetails=data.ordGoodsDetails;
								$scope.form.ordFee = data.ordFee;
								$scope.form.goodsDetailSum=data.ordGoodsDetailSum;
								$scope.tranferFeeFlag = data.tranferFeeFlag;
								$scope.isSeaTransport = data.ordOrderInfo.isSeaTransport == 1 ? true : false;

								
								
								$scope.createDateDetail= $scope.form.orderInfo.createDate;
								$scope.orgNameDetail= $scope.form.orderInfo.orgIdName;
								$scope.inputUserNameDetail = $scope.form.orderInfo.inputUserName;

								//单选
								$scope.releaseNote = $scope.form.orderInfo.releaseNote==0?false:true; 
								$scope.valuables = $scope.form.orderInfo.valuables==0?false:true; 
								$scope.isPayDiscount = data.ordFee.isPayDiscount == 1 ? true:false; 
								$scope.isPayCash = data.ordFee.isPayCash==1?true:false; 

								bill.form = $scope.form;

								//合作商初始化
								if($scope.form.orderInfo.sfName!=undefined){
									$scope.form.orderInfo.sfName = $scope.form.orderInfo.sfName;
								}
								if($scope.form.orderInfo.sfTel!=undefined){
									$scope.form.orderInfo.sfTel = $scope.form.orderInfo.sfTel;
								}
								if($scope.form.orderInfo.sfId!=undefined){
									$scope.form.orderInfo.sfId = $scope.form.orderInfo.sfId;
								}
								if($scope.form.orderInfo.sfFee!=undefined){
									$scope.form.orderInfo.sfFee = $scope.form.orderInfo.sfFee/100;
								}
								if(isTab==1){
									document.getElementById("body").style.overflow="hidden";
								}
							}
						});
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

					isShow :{
						isShow1:true,
						isShow2:false,
						isShow3:false,
						isShow4:false,
						isShow5:false,
					},
					

					//公共－返回的值为 保留小数点后面两位
					upCosts:function(valueName){
						if(eval("$scope."+valueName) != null && eval("$scope."+valueName) != undefined && eval("$scope."+valueName) != ""){
//							var value = eval("$scope."+valueName).replace(/^\D*(\d*(?:\.\d{0,2})?).*$/g, '$1');
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

					checkStrIsBlank: function(str){
						if(str == undefined || null == str || '' == jQuery.trim(str))
							return false;
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
					},

					openRemark : function(obj) {
						if(obj == 1 || obj == "1"){
							$scope.showRemark = false;
						}else{
							$scope.queryRemark();
							$scope.showRemark = true;
							$scope.showNewRemark = false;
							$scope.showBack = false;
						}
						
					},
					toNewRemark : function(obj,remarks){
						if(obj == 1 || obj == "1"){
							$scope.showNewRemark = false;
							$scope.showBack = false;
						}else{
							$scope.showNewRemark = true;
							$scope.showBack = true;
							$scope.showRemark = false;
 							$scope.saveRemark.remarks = remarks;
						}
						
					},
					newRemark : function(){
						if($scope.saveRemark.remarks == undefined || $scope.saveRemark.remarks == null ||$scope.saveRemark.remarks == ''){
							try{
								commonService.alert("请输入备注信息");
							}catch (e) {
								alert("请输入备注信息");
							}
							return false;
						}
						$scope.saveRemark.orderId = $scope.form.orderInfo.orderId;
						var url = "orderInfoBO.ajax?cmd=doSaveRemarks";
						commonService.postUrl(url,$scope.saveRemark,function(data){
							if(data != null && data != undefined && data != ""){
								$scope.toNewRemark(1);
								var rs = $scope.form.orderInfo.remarks;
									$scope.form.orderInfo.remarks = $scope.saveRemark.remarks;
								try{
									commonService.alert("保存成功");
									$scope.saveRemark.remarks = "";
								}catch (e) {
									alert("保存成功");
									$scope.saveRemark.remarks = "";
								}
								
								
							}
						});
					},
					queryRemark : function(){
						$scope.queryRemarkData = {};
						$scope.queryRemarkData.orderId = $scope.form.orderInfo.orderId;
						var url = "orderInfoBO.ajax?cmd=queryRemarks";
						commonService.postUrl(url,$scope.queryRemarkData,function(data){
							if(data != null && data != undefined && data != ""){
								$scope.remarkData = data;
							}
						});
					},

					//设置发货方
					selectRemark:function(obj){
						$scope.form.orderInfo.remarks = obj.notes;
					},

				};
				bill.init(); 
}]
 };
return myBilling;
});