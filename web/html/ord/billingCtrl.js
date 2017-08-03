var autoComplete = null;
var view = getQueryString("view");
var orderId = getQueryString("orderId");
var sellerOrderId = getQueryString("sellerOrderId");
var orgId = getQueryString("orgId");/** 分拨中心的人进来查看详情，需要做特殊控制，例如：无法查看费用等*/
var edit = getQueryString("edit");
var isSpell = getQueryString("isSpell");
var print = getQueryString("print");
var ids = getQueryString("ids");
var imports = getQueryString("imports");
var billApp = angular.module("billApp", ['commonApp','angucomplete','ngTouch']);
billApp.controller("billCtrl", ["$scope","commonService","$timeout",'$rootScope',function($scope,commonService,$timeout,$rootScope) {
	var bill = {
		init:function(){
			this.bindEvent();
			this.initTenantConfig();
			this.initData();
			this.userData();
			this.initStaticData();
			//初始化打印的信息，先直接从数据库读取，后续稳定后，读取缓存或者登录后请求一次
			this.getPrintConfigBean();
			//是否显示拼单勾选框
			$scope.isShowSpell = true;
			//发货人弹窗
			$scope.showPcum = false;
			$scope.showRcum = false;
			$scope.showGoodsName0 = false;
			$scope.showGoodsName1 = false;
			$scope.ShowTrackingNumBegin = true;
			//费用不显示
			$scope.priceShow = false;
			//标识发货人收货人是否从列表选择weiliang
			$scope.fhr = false;
			$scope.shr = false;
			this.initCityInfo();
			$scope.isSeaTransport =false;
			$scope.pageType = 1;// 页面类型。1--> 运单录入；2-->运单详情；3-->运单修改
			$scope.pageTitle = '运单录入';
			$scope.totalCount = 0;
			$scope.query = {};
			$scope.isRootOrg = false;
			$scope.firstCome = 0;
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
			} else if(sellerOrderId != null && sellerOrderId != undefined && sellerOrderId !=""){
				this.initSellerOrder(sellerOrderId);
			}
			else{
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
			
			if ($scope.pageType == 3){
				jQuery("#_distributionOrgId").focus();
			}
			
			$scope.doQueryFeePic = this.doQueryFeePic;
			if(print=='1'){
				jQuery("#_submit").hide();
				jQuery("#_save").hide();
				jQuery("#_reset").hide();
				jQuery('input,select,textarea').attr('disabled', true);
				jQuery('input,select,textarea').prop('readonly',true);
				$scope.view = false;
				jQuery("#check-1").attr('disabled', true);
				$scope.all = true;
			}
			this.importBill();
		},
		printConfigBean:{},
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
		//选择商家回选 价格和支付方式
		callPrice : function(isSign){
			if(isSign!=undefined && isSign != null &&isSign != ""){
				var param = {};
				var chooseCityId = $scope.form.orderInfo.provinceId;// 省份
				var chooseRegionId = $scope.form.orderInfo.cityId;// 地市
				var chooseCountyId = $scope.form.orderInfo.countyId;// 县区
				param.tenantId = $scope.form.goodsDetail.pTenantId;
				param.province = chooseCityId;
				param.city = chooseRegionId;
				param.county =	chooseCountyId;
				commonService.postUrl("cmTrunkCostBO.ajax?cmd=doQueryOneCostInfo",param,function(data){
					if(data == "" || data == null){
						//查找不到为初始化
//						$scope.form.goodsDetail.goods_0.volumeUnit = "";
//						$scope.form.goodsDetail.goods_0.weightUnit = "";
//						$scope.form.ordFee.paymentType = "-1";
					}else{
						if($scope.form.orderInfo.orderId > 0){
							
						}else{
							//新增才改变
							if(data.volumePrice != undefined &&  data.volumePrice != null &&data.volumePrice != ''){
								var volumePrice = parseInt(data.volumePrice);
								if(volumePrice > 0){
									$scope.form.goodsDetail.goods_0.volumeUnit = volumePrice/100;
								}
							}
		                    if(data.weightPrice != undefined &&  data.weightPrice != null &&data.weightPrice != ''){
		                    	var weightPrice = parseInt(data.weightPrice);
		                    	if(weightPrice > 0){
		                    		$scope.form.goodsDetail.goods_0.weightUnit = weightPrice / 100;
		                    	}
		                    	
							}
		                    if(data.paymentType != undefined &&  data.paymentType != null &&data.paymentType != ''){
		                    	$scope.form.ordFee.paymentType = data.paymentType;
							}
		                    $scope.updateGoodsDetailAmount();
		                    $scope.calculatePrice();
						}
				
	                    
					}
					//初始化支付2方式
					 $scope.filterPayment(bill.paymentTypeDataAll,$scope.form.ordFee.paymentType,$scope.form.ordFee.paymentType2);
					
				});
			}
		},
		//选择到达省市 回选 标准价格和支付方式
		callStandardPrice : function(isSign){
			    if($scope.form.goodsDetail.pTenantId != undefined && $scope.form.goodsDetail.pTenantId != null &&$scope.form.goodsDetail.pTenantId !=''){
				    //存在签约
			    	$scope.callPrice("1"); //回选商家价格
			    	return false;
			    }
				
				var param = {};
				var chooseCityId = $scope.form.orderInfo.provinceId;// 省份
				var chooseRegionId = $scope.form.orderInfo.cityId;// 地市
				var chooseCountyId = $scope.form.orderInfo.countyId;// 县区
				param.tenantId = -1; //标准的未选择商家的
				param.province = chooseCityId;
				param.city = chooseRegionId;
				param.county =	chooseCountyId;
				if(chooseCityId == undefined || chooseCityId == null || chooseCityId == ''){
					return false;
				}
				commonService.postUrl("cmTrunkCostBO.ajax?cmd=doQueryOneCostInfo",param,function(data){
					if(data == "" || data == null){
						//查找不到为初始化
//						$scope.form.goodsDetail.goods_0.volumeUnit = "";
//						$scope.form.goodsDetail.goods_0.weightUnit = "";
//						$scope.form.ordFee.paymentType = "1";
					}else{
						if($scope.form.orderInfo.orderId > 0){
							
						}else{
							//新增才改变
							if(data.volumePrice != undefined &&  data.volumePrice != null &&data.volumePrice != ''){
								var volumePrice = parseInt(data.volumePrice);
								if(volumePrice > 0){
									$scope.form.goodsDetail.goods_0.volumeUnit = volumePrice/100;
								}
							}
		                    if(data.weightPrice != undefined &&  data.weightPrice != null &&data.weightPrice != ''){
		                    	var weightPrice = parseInt(data.weightPrice);
		                    	if(weightPrice > 0){
		                    		$scope.form.goodsDetail.goods_0.weightUnit = weightPrice / 100;
		                    	}
		                    	
							}
		                    if(data.paymentType != undefined &&  data.paymentType != null &&data.paymentType != ''){
		                    	$scope.form.ordFee.paymentType = data.paymentType;
							}
		                    $scope.updateGoodsDetailAmount();
		                    $scope.calculatePrice();
						}
				
	                    
					}
					//初始化支付2方式
					 $scope.filterPayment(bill.paymentTypeDataAll,$scope.form.ordFee.paymentType,$scope.form.ordFee.paymentType2);
					
				});
		},
		//查询干线协议费用、或者配安费 协议
		doQueryFeePic : function(obj){
			var param = {};
			var chooseCityId = $scope.form.orderInfo.provinceId;// 省份
			var chooseRegionId = $scope.form.orderInfo.cityId;// 地市
			var chooseCountyId = $scope.form.orderInfo.countyId;// 县区
			param.tenantId = $scope.form.goodsDetail.pTenantId;
			var isSign = $scope.form.orderInfo.isSign;
			param.province = chooseCityId;
			param.city = chooseRegionId;
			param.county =	chooseCountyId;
			if(!isSign && (parseInt(obj) == 3 || parseInt(obj) == 4)){
				commonService.alert("协议费用请选择对应的协议商家");
				return false;
			}else{
				if(parseInt(obj) == 1 || parseInt(obj) == 2){
					//param = {}; //标准不需要配置入参省市
					param.tenantId = -1;
				}
				
			}
			commonService.postUrl("cmTrunkCostBO.ajax?cmd=doQueryOneCostInfo",param,function(data){
				if(data == "" || data == null){
					if(parseInt(obj) == 1 || parseInt(obj) == 2){
						commonService.alert("该地区未配置标准费用，请在基础管理菜单干线费用配置中添加。");
					}else{
						commonService.alert("商家["+$scope.form.goodsDetail.pName+"]该地区没找到协议费用配置，请在基础管理菜单干线费用配置中添加。");
					}
					return false;
				}else{
					
					if(parseInt(obj) == 1 || parseInt(obj) == 3){
						//干线费用图片
						$scope.imageFullUrl = data.costPicUrlFull;
					}else{
						//配安费用图片
						$scope.imageFullUrl = data.costInstallPicUrlFull;
					}
					if($scope.imageFullUrl != '' && $scope.imageFullUrl != null && $scope.imageFullUrl !='null'){
						$scope.isShowIMgBig = true;
					}else{
						commonService.alert("未配置费用图片信息，请在基础管理菜单干线费用配置中添加。");
						return false;
					}
					
					if($scope.form.orderInfo.orderId > 0){
						
					}else{
						//新增才改变
						if(data.volumePrice != undefined &&  data.volumePrice != null &&data.volumePrice != ''){
							var volumePrice = parseInt(data.volumePrice);
							if(volumePrice > 0){
								$scope.form.goodsDetail.goods_0.volumeUnit = volumePrice/100;
							}
						}
	                    if(data.weightPrice != undefined &&  data.weightPrice != null &&data.weightPrice != ''){
	                    	var weightPrice = parseInt(data.weightPrice);
	                    	if(weightPrice > 0){
	                    		$scope.form.goodsDetail.goods_0.weightUnit = weightPrice / 100;
	                    	}
	                    	
						}
	                    if(data.paymentType != undefined &&  data.paymentType != null &&data.paymentType != ''){
	                    	$scope.form.ordFee.paymentType = data.paymentType;
						}
	                    $scope.updateGoodsDetailAmount();
	                    $scope.calculatePrice();
					}
			
                    
				}
				//初始化支付2方式
				 $scope.filterPayment(bill.paymentTypeDataAll,$scope.form.ordFee.paymentType,$scope.form.ordFee.paymentType2);
				
			});
		},
		initCityInfo:function(provinceId){
			if(provinceId==undefined || provinceId==null || provinceId==""){
				$scope.cityInfo=_cityInfo;
			}else{
				var re = new RegExp("^"+provinceId, 'i');
				var provinceList=new Array();
				for(var i in _cityInfo){
					if(_cityInfo[i].districtId.match(re)==null){
						continue;
					}
					provinceList.push(_cityInfo[i]);
				}
				
				$scope.cityInfo=provinceList;
				
			}
			
		},
		resetData: function(){
			if ($scope.pageType != 1){
				return;
			}
			imoprts="";
			ids = '-1';
			var packingType = '';
			if(undefined != $scope.packTypeData && $scope.packTypeData.length > 0) {
				packingType = $scope.packTypeData[0].codeValue;
			}
			$scope.form.goodsDetail = {
				goodsNum: 2
			};
			$scope.form.goodsDetail.goods_0 = {isShow:true, goodsName: '', packingType: $scope.dfpackTypeValue, weight: '', volume: '',
												freight: '', discount: '', deliveryCosts: '', collectingMoney: '', procedureFee: '', 
												goodsPrice: '', offer: '', handingCosts: '', packingCosts: '', pickingCosts: '', 
												upstairsFee: '', billingType: ''};
			$scope.form.goodsDetail.goods_1 =  {isShow:true, goodsName: '', packingType: '', weight: '', volume: '',
					freight: '', discount: '', deliveryCosts: '', collectingMoney: '', procedureFee: '', 
					goodsPrice: '', offer: '', handingCosts: '', packingCosts: '', pickingCosts: '', 
					upstairsFee: '', billingType: ''};
			$scope.updateGoodsDetailAmount();
			$scope.form.orderInfo.address = "";
			var distributionOrgId = '';
			var destCityName = '';
			var destCity = '';
			if (undefined != $scope.roateData.items && $scope.roateData.items.length > 0){
				distributionOrgId = $scope.roateData.items[0].endOrgId;
				destCityName = $scope.roateData.items[0].endOwnerRegionName;// 目的地
				destCity = $scope.roateData.items[0].endOwnerRegion;
			}
			
			var deliveryType = '';
			if (undefined != $scope.deliveryTypeData && $scope.deliveryTypeData.length > 0) {
				deliveryType = $scope.deliveryTypeData[0].codeValue;
			}
			
			var inputUserId = userInfo.userId;
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
			jQuery("#cityName_value").val("");
			var paymentType = '';
			if (undefined != $scope.paymentTypeData && $scope.paymentTypeData.length > 0) {
				paymentType = $scope.paymentTypeData[0].codeValue;
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
			
			$scope.isShowSpell = true;
			
			$scope.areaEdit = false;
			$scope.showOrderId = false;
			$scope.doQueryArea();
			$scope.totalCount = '';
			$scope.refreshRouteTowardsDtl();// 刷新线路路由
			bill.initData();
			$scope.isPayCash = false;
			jQuery("#check-4").attr("checked",false);
			commonService.postUrl("staticDataBO.ajax?cmd=getTrackingNum","",function(data){
				$scope.form.orderInfo.trackingNum = data.trackingNum;
				$scope.form.orderInfo.trackingNumBegin = "";
				if(data.trackingNumBegin!=undefined&&data.trackingNumBegin!=null){
					$scope.form.orderInfo.trackingNumBegin = data.trackingNumBegin;
				}
				jQuery("#pName").focus();
				$scope.form.orderInfo.goodsNumber = $scope.form.orderInfo.trackingNumBegin + "" + $scope.form.orderInfo.trackingNum;
			});
			commonService.postUrl("organizationBO.ajax?cmd=getOrgInfo",{},function(data){
				$scope.form.orderInfo.fromAddress = data.organization.departmentAddress;
				if(data.organization.parentOrgId=="-1"){
					document.getElementById("kd_gz").style.display="block";
				}
			});
			$scope.form.orderInfo.deliveryType = "";
			$scope.form.ordFee.paymentType = "-1";
			$scope.form.ordFee.paymentType2 = "-1";
		},
		deleteGoodsDetail: function(){
			$scope.form.goodsDetail.goods_1 =  {isShow:true, goodsName: '',  packingType: '', weight: '', volume: '',
					freight: '', discount: '', deliveryCosts: '', collectingMoney: '', procedureFee: '', 
					goodsPrice: '', offer: '', handingCosts: '', packingCosts: '', pickingCosts: '', 
					upstairsFee: '', billingType: ''};
			$scope.updateGoodsDetailAmount();
		},
		calculatePrice: function(){
			if($scope.form.goodsDetail.goods_0.volumeUnit!=undefined && $scope.form.goodsDetail.goods_0.volumeUnit!=""){
				if($scope.form.goodsDetail.goods_0.volume!=undefined && $scope.form.goodsDetail.goods_0.volume!=""){
					$scope.form.goodsDetail.goods_0.freight = $scope.fixNumber($scope.form.goodsDetail.goods_0.volumeUnit, $scope.form.goodsDetail.goods_0.volume);
				}
			}
			if($scope.form.goodsDetail.goods_0.weightUnit!=undefined && $scope.form.goodsDetail.goods_0.weightUnit!=""){
				if($scope.form.goodsDetail.goods_0.weight!=undefined && $scope.form.goodsDetail.goods_0.weight!=""){
					$scope.form.goodsDetail.goods_0.freight = $scope.fixNumber($scope.form.goodsDetail.goods_0.weightUnit  , $scope.form.goodsDetail.goods_0.weight);
				}
			}
			if($scope.form.goodsDetail.goods_1.volumeUnit!=undefined && $scope.form.goodsDetail.goods_1.volumeUnit!=""){
				if($scope.form.goodsDetail.goods_1.volume!=undefined && $scope.form.goodsDetail.goods_1.volume!=""){
					$scope.form.goodsDetail.goods_1.freight = $scope.fixNumber($scope.form.goodsDetail.goods_1.volumeUnit  , $scope.form.goodsDetail.goods_1.volume);
				}
			}
			if($scope.form.goodsDetail.goods_1.weightUnit!=undefined && $scope.form.goodsDetail.goods_1.weightUnit!=""){
				if($scope.form.goodsDetail.goods_1.weight!=undefined && $scope.form.goodsDetail.goods_1.weight!=""){
					$scope.form.goodsDetail.goods_1.freight = $scope.fixNumber($scope.form.goodsDetail.goods_1.weightUnit  , $scope.form.goodsDetail.goods_1.weight);
				}
			}
		},
		fixNumber:function(x,y){
			return Math.round(parseFloat(((x*10000)*(y*10000))/100000000));
		},
		updateGoodsDetailAmount: function(type){
			$scope.goodsDetailAmount.packageCounts = bill.customParseInt($scope.form.goodsDetail.goods_0.packageCounts) + bill.customParseInt($scope.form.goodsDetail.goods_1.packageCounts);
			if($scope.goodsDetailAmount.packageCounts!=undefined && $scope.form.orderInfo.goodsNumber!=undefined){
				var goodsNumberTemp = $scope.form.orderInfo.goodsNumber;
				var goodsNumberSub=goodsNumberTemp.substr(0,goodsNumberTemp.indexOf("-")==-1?goodsNumberTemp.length:goodsNumberTemp.indexOf("-"));
				var trackingNumTemp=""+ $scope.form.orderInfo.trackingNum;
				if($scope.form.orderInfo.trackingNumBegin!=undefined ){
					trackingNumTemp=$scope.form.orderInfo.trackingNumBegin+trackingNumTemp;
				}
				if(trackingNumTemp==goodsNumberSub){
					//如果运单号跟货号是一样的，表示用户没有修改过货号，需要添加货号＋件数的逻辑
					$scope.form.orderInfo.goodsNumber = goodsNumberSub + "-" + $scope.goodsDetailAmount.packageCounts;
				}
			}
			$scope.goodsDetailAmount.installCount = bill.customParseInt($scope.form.goodsDetail.goods_0.installCount) + bill.customParseInt($scope.form.goodsDetail.goods_1.installCount);
			$scope.goodsDetailAmount.weight = bill.customParseDouble($scope.form.goodsDetail.goods_0.weight) + bill.customParseDouble($scope.form.goodsDetail.goods_1.weight);
			$scope.goodsDetailAmount.volume = bill.customParseDouble($scope.form.goodsDetail.goods_0.volume) + bill.customParseDouble($scope.form.goodsDetail.goods_1.volume);
			$scope.goodsDetailAmount.freight = bill.customParseDouble($scope.form.goodsDetail.goods_0.freight) + bill.customParseDouble($scope.form.goodsDetail.goods_1.freight);
			$scope.goodsDetailAmount.discount = bill.customParseDouble($scope.form.goodsDetail.goods_0.discount) + bill.customParseDouble($scope.form.goodsDetail.goods_1.discount);
			$scope.goodsDetailAmount.deliveryCosts = bill.customParseDouble($scope.form.goodsDetail.goods_0.deliveryCosts) + bill.customParseDouble($scope.form.goodsDetail.goods_1.deliveryCosts);
			$scope.goodsDetailAmount.installCosts = bill.customParseDouble($scope.form.goodsDetail.goods_0.installCosts) + bill.customParseDouble($scope.form.goodsDetail.goods_1.installCosts);
			if(orderId != null && orderId != undefined && orderId !=""){
				//修改无此逻辑
			}
			else
			{
				$scope.form.orderInfo.sfFee = $scope.goodsDetailAmount.installCosts;
			}
			$scope.goodsDetailAmount.collectingMoney = bill.customParseDouble($scope.form.goodsDetail.goods_0.collectingMoney) + bill.customParseDouble($scope.form.goodsDetail.goods_1.collectingMoney);
			$scope.goodsDetailAmount.procedureFee = bill.customParseDouble($scope.form.goodsDetail.goods_0.procedureFee) + bill.customParseDouble($scope.form.goodsDetail.goods_1.procedureFee);
			//垫付贷款
			$scope.goodsDetailAmount.advanceMoney = bill.customParseDouble($scope.form.goodsDetail.goods_0.advanceMoney) + bill.customParseDouble($scope.form.goodsDetail.goods_1.advanceMoney);
			$scope.goodsDetailAmount.goodsPrice = bill.customParseDouble($scope.form.goodsDetail.goods_0.goodsPrice) + bill.customParseDouble($scope.form.goodsDetail.goods_1.goodsPrice);
			$scope.goodsDetailAmount.offer = bill.customParseDouble($scope.form.goodsDetail.goods_0.offer) + bill.customParseDouble($scope.form.goodsDetail.goods_1.offer);
			$scope.goodsDetailAmount.handingCosts = bill.customParseDouble($scope.form.goodsDetail.goods_0.handingCosts) + bill.customParseDouble($scope.form.goodsDetail.goods_1.handingCosts);
			$scope.goodsDetailAmount.packingCosts = bill.customParseDouble($scope.form.goodsDetail.goods_0.packingCosts) + bill.customParseDouble($scope.form.goodsDetail.goods_1.packingCosts);
			$scope.goodsDetailAmount.pickingCosts = bill.customParseDouble($scope.form.goodsDetail.goods_0.pickingCosts) + bill.customParseDouble($scope.form.goodsDetail.goods_1.pickingCosts);
			//实际提货费
			$scope.goodsDetailAmount.actualBillCosts = bill.customParseDouble($scope.form.goodsDetail.goods_0.actualBillCosts)+bill.customParseDouble($scope.form.goodsDetail.goods_1.actualBillCosts)
			if($scope.goodsDetailAmount.pickingCosts!=undefined && $scope.goodsDetailAmount.pickingCosts!=0){
				var getGoodsType1 = $scope.form.orderInfo.getGoodsType1;
				var getGoodsType2 = $scope.form.orderInfo.getGoodsType2;
				if(getGoodsType1==false && getGoodsType2==false){
					$scope.form.orderInfo.getGoodsType1 = true;
				}
			}
			$scope.goodsDetailAmount.upstairsFee = bill.customParseDouble($scope.form.goodsDetail.goods_0.upstairsFee) + bill.customParseDouble($scope.form.goodsDetail.goods_1.upstairsFee);
			$scope.updateTotalFee();
			$scope.paymentChange($scope.form.ordFee.paymentType,type);
		}, 
		changeServiceType:function(){
			if($scope.goodsDetailAmount.installCosts!=undefined && $scope.goodsDetailAmount.installCosts>0){
//				$scope.form.orderInfo.serviceFix = true;
//				$scope.form.orderInfo.serviceDelivery = true;
				if($scope.form.orderInfo.serviceType==undefined || $scope.form.orderInfo.serviceType=="11"){
					$scope.form.orderInfo.serviceType = "12";
				}
			}
		},
		updateTotalFee: function(){
			var value = $scope.goodsDetailAmount.freight +  $scope.goodsDetailAmount.deliveryCosts
			 + $scope.goodsDetailAmount.offer
//			+ $scope.goodsDetailAmount.procedureFee + $scope.goodsDetailAmount.offer
			+ $scope.goodsDetailAmount.handingCosts + $scope.goodsDetailAmount.packingCosts + $scope.goodsDetailAmount.pickingCosts
			+ $scope.goodsDetailAmount.upstairsFee
			+ $scope.goodsDetailAmount.installCosts;
			var numberValue =  new Number(value);
			$scope.goodsDetailAmount.total = numberValue.toFixed(2);
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
			var returnKeyEventDomEleIds = ["pName","_linkbilling","cityName_value","_rName","_rTelephone","_address",
			                               "_deliveryType","_serviceType", "_goodsName0","_packingType0","_packageCounts0", "_volume0","_volumeUnit0","_weight0","_weightUnit0",
			                                "_installCosts0","_freight0",
			                               "_goodsName1","_packingType1","_packageCounts1","_volume1","_volumeUnit1",  "_weight1","_weightUnit1",
			                              "_installCosts1", "_freight1"];
			var quickKeyEventDomEleIds = ["cityName_value", "pName","_linkbilling","_rName","_rTelephone","_address",
			                               "_deliveryType","_serviceType","_goodsName0","_packageCounts0","_packingType0","_weightUnit0",
			                               "_weight0","_volumeUnit0", "_volume0", "_freight0","_installCosts0",
			                               "_goodsName1","_packageCounts1","_packingType1","_weightUnit1",
			                               "_weight1","_volumeUnit1", "_volume1", "_freight1","_installCosts1","_trackingNum","check-26","check-21",
			                               "_getGoodsAddress","check-1","check-23","check-24","_distributionOrgId","_deliveryType",
			                               "_discount0","_discount1","_collectingMoney0","_collectingMoney1","_procedureFee0","_procedureFee1",
			                               "_advanceMoney0","_advanceMoney1",
			                               "_goodsPrice0","_offer0","_offer1","_handingCosts0","_handingCosts1","_packingCosts0","_packingCosts1","_pickingCosts0",
			                               "_actualBillCosts0","_actualBillCosts1",
			                               "_pickingCosts1","_installCount0","_installCount1","_custOrder0","_custOrder1","_totalFee","_paymentType",
			                               "_paymentType2","_receiptNum","_receiptCount","_remarks","check-3","check-4","pTable","_sfName","_sfTel",
			                               "_sfFee"];
			var eles1 = ["_goodsName0", "_goodsName1", "_paymentType"];
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
			
			this.registerKeyEventForDomsGetFocus('keydown', 'tab', returnKeyEventDomEleIds, function(event, nextDomId,currentDomEleId){
				if($scope.form.goodsDetail.pName != null && $scope.form.goodsDetail.pName != undefined && $scope.form.goodsDetail.pName != ""){
					if(undefined!=currentDomEleId && currentDomEleId == 'pName') {
		                if ($scope.customerData.items && $scope.currentIndex >= 0 && $scope.currentIndex < $scope.customerData.items.length) {
	                        $scope.selectPcustomer($scope.customerData.items[$scope.currentIndex]);
	                  $scope.$apply();
					}}
				}

				if($scope.form.goodsDetail.rName != null && $scope.form.goodsDetail.rName != undefined && $scope.form.goodsDetail.rName != ""){
					if(undefined!=currentDomEleId && currentDomEleId == '_rName') {
						if ($scope.recCustomerData.items && $scope.currentIndex >= 0 && $scope.currentIndex < $scope.recCustomerData.items.length) {
							$scope.selectRcustomer($scope.recCustomerData.items[$scope.currentIndex]);
							$scope.$apply();
						}}
				}
			});
			
			this.registerKeyEventForDomsGetFocus('keydown', 'return', returnKeyEventDomEleIds, function(event, nextDomId,currentDomEleId){
				if($scope.form.goodsDetail.pName != null && $scope.form.goodsDetail.pName != undefined && $scope.form.goodsDetail.pName != ""){
				if(undefined!=currentDomEleId && currentDomEleId == 'pName') {
	                if ($scope.customerData.items && $scope.currentIndex >= 0 && $scope.currentIndex < $scope.customerData.items.length) {
                        $scope.selectPcustomer($scope.customerData.items[$scope.currentIndex]);
                  $scope.$apply();
	                }}
				}
				if($scope.form.goodsDetail.rName != null && $scope.form.goodsDetail.rName != undefined && $scope.form.goodsDetail.rName != ""){
				if(undefined!=currentDomEleId && currentDomEleId == '_rName') {
					if ($scope.recCustomerData.items && $scope.currentIndex >= 0 && $scope.currentIndex < $scope.recCustomerData.items.length) {
						$scope.selectRcustomer($scope.recCustomerData.items[$scope.currentIndex]);
						$scope.$apply();
					}}
				}
			});
			this.registerKeyEventForDocument('keydown', 'return', function(evt){
				jQuery("#" + returnKeyEventDomEleIds[0]).focus();
				return false;
			});
			
			this.registerKeyEventForDocument('keydown', 'ctrl+z', function(evt){
				$scope.resetData();
				return false;
			});
			
			this.registerKeyEventForDocument('keydown', 'f9', function(evt){
				$scope.saveAndPrintExpressInfo();
				return false;
			});
			
			this.registerKeyEventForDocument('keydown', 'ctrl+s', function(evt){
				if ($scope.pageType == 1 || $scope.pageType == 3) {
					$scope.submit();
				}
				return false;
			});
			
			for (var i = 0; i < quickKeyEventDomEleIds.length; i++) {
				// 为整个页面绑定“return”按键事件
				jQuery("#" + quickKeyEventDomEleIds[i]).bind('keydown', 'ctrl+z', function(evt){
					$scope.resetData();
					return false;
				});
			}
			
			for (var i = 0; i < quickKeyEventDomEleIds.length; i++) {
				// 为整个页面绑定“return”按键事件
				jQuery("#" + quickKeyEventDomEleIds[i]).bind('keydown', 'ctrl+s', function(evt){
					if ($scope.pageType == 1 || $scope.pageType == 3) {
						$scope.submit();
					}
					return false;
				});
			}
			
			for (var i = 0; i < quickKeyEventDomEleIds.length; i++) {
				// 为整个页面绑定“return”按键事件
				jQuery("#" + quickKeyEventDomEleIds[i]).bind('keydown', 'ctrl+d', function(evt){
					if ($scope.pageType == 1 || $scope.pageType == 3) {
						$scope.deleteGoodsDetail();
						jQuery("#_goodsName0").focus();
					}
					return false;
				});
			}
			
			for (var i = 0; i < quickKeyEventDomEleIds.length; i++) {
				// 为整个页面绑定“return”按键事件
				jQuery("#" + quickKeyEventDomEleIds[i]).bind('keydown', 'f9', function(evt){
					$scope.saveAndPrintExpressInfo();
					return false;
				});
			}
			if(orderId != null && orderId != undefined && orderId !=""){
				//修改运单不用默认
			}
			else
			{
				$timeout(function(){
					jQuery("#pName").focus();
				},500);
			}
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
						if (undefined != callback) callback(evt, nextDomEleId, currentDomEleId);
					}
					return false;
				});
			});
		},
		bindEvent:function(){
			$scope.openUp=this.openUp;
			$scope.closeUp=this.closeUp;
			$scope.getFlowId=this.getFlowId;
			$scope.queryCustomer = this.queryCustomer;
			$scope.queryRecCustomer = this.queryRecCustomer;
			$scope.createTable = this.createTable;
			$scope.delTable = this.delTable;
			$scope.updateChecked = this.updateChecked;
			$scope.paymentChange = this.paymentChange;
			$scope.paymentChange2 = this.paymentChange2;
			$scope.modifyMoney = this.modifyMoney;
			$scope.money2Change = this.money2Change;
			$scope.filterPayment = this.filterPayment;
			$scope.filterPayment1 = this.filterPayment1;
			$scope.showToFalse = this.showToFalse;
			$scope.selectPcustomer = this.selectPcustomer;
			$scope.selectRcustomer = this.selectRcustomer;
			$scope.selectSf = this.selectSf;
			$scope.updateTotalCosts = this.updateTotalCosts;
			$scope.doQueryArea = this.doQueryArea;
			$scope.doChangeDelivery = this.doChangeDelivery;
			$scope.close = this.close;
			$scope.fixNumber = this.fixNumber;
			//查看费用
			$scope.checkPrice = this.checkPrice;
			$scope.doSave=this.doSave;
			$scope.form = this.form;
			$scope.submit = this.submit;
			$scope.doConfirm = this.doConfirm;
			$scope.forEach=this.forEach;
			$scope.upNum = this.upNum; 
			$scope.upCosts = this.upCosts;
			$scope.doQueryVehicle = this.doQueryVehicle;
			$scope.selectDriver = this.selectDriver;
			$scope.clearDestCity = this.clearDestCity;
			$scope.arrivePointSet = this.arrivePointSet;
			$scope.changeDesPoint = this.changeDesPoint;
			
			//计算货物总数
			$scope.updateCount = this.updateCount;
			
			//打印的方法
			//打印运单
			$scope.printExpressInfo=this.printExpressInfo;
			$scope.printAndNew=this.printAndNew;
			//打印回单
			$scope.printEnvelopeInfo=this.printEnvelopeInfo;
			
			// 改变网点事件
			$scope.changeDistributionOrgId = this.changeDistributionOrgId;
			
			$scope.updateGoodsDetailAmount = this.updateGoodsDetailAmount;
			$scope.updateTotalFee = this.updateTotalFee;// 更新总费用
			
			$scope.checkTrackingNumUsedState = this.checkTrackingNumUsedState;// 校验运单号状态

			$scope.resetData = this.resetData;// 重置数据
			$scope.refreshRouteTowardsDtl = this.refreshRouteTowardsDtl;//刷新线路路由 
			$scope.deleteGoodsDetail = this.deleteGoodsDetail;// 删除第二条货物详细数据
			$scope.hidePrice = this.hidePrice;
			
			$scope.printStickerInfo = this.printStickerInfo;
			
			$scope.checkStrIsBlank = this.checkStrIsBlank;
			
			$scope.showOrHideGoodsNameSelect = this.showOrHideGoodsNameSelect;
			$scope.selectGoodsName = this.selectGoodsName;
			$scope.saveAndPrintExpressInfo = this.saveAndPrintExpressInfo;
			$scope.callBack = this.callBack;
			$scope.countyCreat = this.countyCreat;
			$scope.streetCreat = this.streetCreat;
			$scope.updateReceiver = this.updateReceiver;
			$scope.keyPressed = this.keyPressed;
			$scope.initKeyPressed = this.initKeyPressed;
			$scope.noKeyPress = this.noKeyPress;
			$scope.cancelSf = this.cancelSf;
			$scope.openRemark = this.openRemark;
			$scope.openSf = this.openSf;
			$scope.selectRemark = this.selectRemark;
			$scope.querySfClick = this.querySfClick;
			$scope.querySfInfo = this.querySfInfo;
			$scope.changeServiceType = this.changeServiceType;
			$scope.calculatePrice = this.calculatePrice;
			$scope.querySfInfoOnChange = this.querySfInfoOnChange;
			$scope.callPrice = this.callPrice;
			$scope.changeGetGoodsType = this.changeGetGoodsType;
			$scope.setGetGoodsType = this.setGetGoodsType;
			$scope.getGetGoodsType = this.getGetGoodsType;
			$scope.callStandardPrice = this.callStandardPrice;
			$scope.checkTelphone = this.checkTelphone;
			
			$scope.callSpellQueryOrder = this.callSpellQueryOrder;
			
			$scope.doCheckRemark = this.doCheckRemark;
			
			$scope.isDirectClick=this.isDirectClick;//直送点击的按钮
			
		},
		//勾选直送需要判断
		isDirectClick:function(){
			//勾选直送的时候南哥物流需要判断
			if($scope.directSendingInfo!=undefined && $scope.directSendingInfo!="" && $scope.directSendingInfo.length>0){
				if($scope.form.orderInfo.cityId!=undefined && $scope.form.orderInfo.cityId!=null
						 && $scope.form.orderInfo.cityId>0 && $scope.form.orderInfo.isDirect!=undefined
						  && $scope.form.orderInfo.isDirect!=null && 
						  $scope.form.orderInfo.isDirect==true){
					var isAlert=true;
					for(var i in $scope.directSendingInfo){
						var data=$scope.directSendingInfo[i];
						if($scope.form.orderInfo.cityId==data.codeValue){
							isAlert=false;
							break;
						}
					}
					
					if(isAlert){
						//如果配置了直送区域 就会进入这个逻辑
						//20161125 改 南哥物流-开单录入-选择地址时。弹出确认框，提示“请确认到达城市[广州]是否为直送？”，点击是 确认勾选，点击取消不勾选
						var message = "请确认到达城市["+$scope.form.orderInfo.cityName+"]是否为直送？";
						commonService.confirm(message,function(){
							//是
							document.getElementById("check-26").checked=true;
							$scope.form.orderInfo.isDirect= true;	
						},function(){
							//否
							document.getElementById("check-26").checked = false;
							$scope.form.orderInfo.isDirect = false;
						},"确认勾选","取消勾选");
					}
					
				}
				
			}
		},
		selectGoodsName: function(number, obj) {
			if (number == 0) {
				$scope.form.goodsDetail.goods_0.goodsName = obj.codeValue;
				jQuery("#_packageCounts0").focus();
			} else if (number == 1) {
				$scope.form.goodsDetail.goods_1.goodsName = obj.codeValue;
				jQuery("#_packageCounts1").focus();
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
			$scope.userId = userInfo.userId;
			$scope.form.orderInfo.inputUserId=parseInt(userInfo.userId);
			//setContentHeight();
		},
		//查看初始化
		initView:function(orderId){
			//-----页面展示后台显示数据逻辑
			//展示订单号
			$scope.showOrderId = false;
			$scope.ShowTrackingNumBegin = false;
			var param={"orderId":orderId};
			var datailUrl="";
			if(orgId!=null && orgId!=undefined && orgId!=""){
				datailUrl="orderInfoBO.ajax?cmd=queryOrderDetail";
				$scope.priceShow=false;
			}else{
				datailUrl="orderInfoBO.ajax?cmd=queryOrderInfoDetail";
				$scope.priceShow=true;
			}
			commonService.postUrl(datailUrl,param,function(data){
				if(data != null && data != undefined && data != ""){
					$scope.cashMoney=data.items[0].ordFee.cashMoney;
					$scope.cashMoney2=data.items[0].ordFee.cashMoney2;
					//返回数据，并且处理数据的显示
					$scope.form = {};
					$scope.form.orderInfo = data.items[0].orderInfo;
					$scope.form.orderInfo.address = data.items[0].orderInfo.address;
					$scope.form.ordFee = data.items[0].ordFee;
					$scope.isSeaTransport = data.items[0].orderInfo.isSeaTransport == 1 ? true : false;
					
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
					$scope.form.orderInfo.deliveryType = data.items[0].orderInfo.deliveryType+"";;
					$scope.form.orderInfo.isInstall = data.items[0].orderInfo.isInstall+"";
					$scope.form.orderInfo.vehicleId = data.items[0].orderInfo.vehicleId+"";
					$scope.form.orderInfo.notes = data.items[0].orderInfo.notes+"";
					$scope.form.orderInfo.transportType = data.items[0].orderInfo.transportType+"";
					$scope.form.orderInfo.salesmanId = data.items[0].orderInfo.salesmanId;
					$scope.form.orderInfo.inputUserId = data.items[0].orderInfo.inputUserId;
					$scope.form.orderInfo.floor = data.items[0].orderInfo.floor+"";
					$scope.form.ordFee.paymentType = data.items[0].ordFee.paymentType+"";
					$scope.form.ordFee.paymentType2 = data.items[0].ordFee.paymentType2+"";
					
					//发货人和收货人
					$scope.form.goodsDetail.pName = data.items[0].orderInfo.consignorName+"";
					$scope.form.goodsDetail.pLinkmanName = data.items[0].orderInfo.consignorLinkmanName;
					$scope.form.goodsDetail.pTelephone = data.items[0].orderInfo.consignorTelephone+"";
//					$scope.form.goodsDetail.pBill = data.items[0].orderInfo.consignorBill+"";
					$scope.form.goodsDetail.pId = data.items[0].orderInfo.consignorId;
					$scope.form.goodsDetail.rName = data.items[0].orderInfo.consigneeName+"";
					$scope.form.goodsDetail.rTelephone = data.items[0].orderInfo.consigneeTelephone;
					$scope.form.goodsDetail.rBill = data.items[0].orderInfo.consigneeBill+"";
					$scope.form.goodsDetail.rLinkmanName = data.items[0].orderInfo.consigneeLinkmanName+"";
					//$scope.form.goodsDetail.rId = data.items[0].orderInfo.consigneeId;
					//单选
					$scope.releaseNote = data.items[0].orderInfo.releaseNote==0?false:true; 
					$scope.valuables = data.items[0].orderInfo.valuables==0?false:true; 
					$scope.isPayDiscount = data.items[0].ordFee.isPayDiscount==0?false:true; 
					$scope.isPayCash = data.items[0].ordFee.isPayCash==0?false:true; 
					$scope.userName=data.items[0].orderInfo.inputUserName;
					$scope.currOrgName=data.items[0].orderInfo.orgIdName;
					$scope.form.orderInfo.inputUserId=data.items[0].orderInfo.inputUserId;
					$scope.form.orderInfo.serviceType=data.items[0].orderInfo.serviceType+"";
					//是否海运
					$scope.isSeatransport=data.items[0].orderInfo.isSeatransport == 1 ? true : false;
					//处理货物显示
					for(var i=0;i<data.items.length;i++){
						eval("$scope.form.goodsDetail.goods_"+i+".isShow=true");
						eval("$scope.form.goodsDetail.goods_"+i+".id='"+data.items[i].goodsDetail.id+"'");
						eval("$scope.form.goodsDetail.goods_"+i+".goodsName='"+data.items[i].goodsDetail.goodsName+"'");
						eval("$scope.form.goodsDetail.goods_"+i+".packingType='"+data.items[i].goodsDetail.packingType+"'");
						if (undefined != data.items[i].goodsDetail.weight && null != data.items[i].goodsDetail.weight && 0 != data.items[i].goodsDetail.weight) 
						eval("$scope.form.goodsDetail.goods_"+i+".weight='"+data.items[i].goodsDetail.weight+"'");
						if (undefined != data.items[i].goodsDetail.volume && null != data.items[i].goodsDetail.volume && 0 != data.items[i].goodsDetail.volume) 
						eval("$scope.form.goodsDetail.goods_"+i+".volume='"+data.items[i].goodsDetail.volume+"'");
						if (undefined != data.items[i].goodsDetail.freight && null != data.items[i].goodsDetail.freight) 
							eval("$scope.form.goodsDetail.goods_"+i+".freight='"+data.items[i].goodsDetail.freight/100+"'");
						if (undefined != data.items[i].goodsDetail.installCosts && null != data.items[i].goodsDetail.installCosts) 
							eval("$scope.form.goodsDetail.goods_"+i+".installCosts='"+data.items[i].goodsDetail.installCosts/100+"'");
						if (undefined != data.items[i].goodsDetail.discount && null != data.items[i].goodsDetail.discount && 0 != data.items[i].goodsDetail.discount)
							eval("$scope.form.goodsDetail.goods_"+i+".discount='"+data.items[i].goodsDetail.discount/100+"'");
						if (undefined != data.items[i].goodsDetail.deliveryCosts && null != data.items[i].goodsDetail.deliveryCosts && 0 != data.items[i].goodsDetail.deliveryCosts)
							eval("$scope.form.goodsDetail.goods_"+i+".deliveryCosts='"+data.items[i].goodsDetail.deliveryCosts/100+"'");
						if (undefined != data.items[i].goodsDetail.collectingMoney && null != data.items[i].goodsDetail.collectingMoney && 0 != data.items[i].goodsDetail.collectingMoney)
							eval("$scope.form.goodsDetail.goods_"+i+".collectingMoney='"+data.items[i].goodsDetail.collectingMoney/100+"'");
						if (undefined != data.items[i].goodsDetail.procedureFee && null != data.items[i].goodsDetail.procedureFee && 0 != data.items[i].goodsDetail.procedureFee)
							eval("$scope.form.goodsDetail.goods_"+i+".procedureFee='"+data.items[i].goodsDetail.procedureFee/100+"'");
						if (undefined != data.items[i].goodsDetail.goodsPrice && null != data.items[i].goodsDetail.goodsPrice && 0 != data.items[i].goodsDetail.goodsPrice)
							eval("$scope.form.goodsDetail.goods_"+i+".goodsPrice='"+data.items[i].goodsDetail.goodsPrice / 100 +"'");
						if (undefined != data.items[i].goodsDetail.offer && null != data.items[i].goodsDetail.offer && 0 != data.items[i].goodsDetail.offer)
							eval("$scope.form.goodsDetail.goods_"+i+".offer='"+data.items[i].goodsDetail.offer/100+"'");
						if (undefined != data.items[i].goodsDetail.handingCosts && null != data.items[i].goodsDetail.handingCosts && 0 != data.items[i].goodsDetail.handingCosts)
							eval("$scope.form.goodsDetail.goods_"+i+".handingCosts='"+data.items[i].goodsDetail.handingCosts/100+"'");
						if (undefined != data.items[i].goodsDetail.packingCosts && null != data.items[i].goodsDetail.packingCosts && 0 != data.items[i].goodsDetail.packingCosts)
							eval("$scope.form.goodsDetail.goods_"+i+".packingCosts='"+data.items[i].goodsDetail.packingCosts/100+"'");
						if (undefined != data.items[i].goodsDetail.pickingCosts && null != data.items[i].goodsDetail.pickingCosts && 0 != data.items[i].goodsDetail.pickingCosts)
							eval("$scope.form.goodsDetail.goods_"+i+".pickingCosts='"+data.items[i].goodsDetail.pickingCosts/100+"'");
						if (undefined != data.items[i].goodsDetail.upstairsFee && null != data.items[i].goodsDetail.upstairsFee && 0 != data.items[i].goodsDetail.upstairsFee)
							eval("$scope.form.goodsDetail.goods_"+i+".upstairsFee='"+data.items[i].goodsDetail.upstairsFee/100+"'");
						if (undefined != data.items[i].goodsDetail.installCount && null != data.items[i].goodsDetail.installCount && 0 != data.items[i].goodsDetail.installCount)
							eval("$scope.form.goodsDetail.goods_"+i+".installCount='"+data.items[i].goodsDetail.installCount+"'");
						if (undefined != data.items[i].goodsDetail.packageCounts && null != data.items[i].goodsDetail.packageCounts && 0 != data.items[i].goodsDetail.packageCounts)
							eval("$scope.form.goodsDetail.goods_"+i+".packageCounts='"+data.items[i].goodsDetail.packageCounts+"'");
						if (undefined != data.items[i].goodsDetail.custOrder && null != data.items[i].goodsDetail.custOrder)
							eval("$scope.form.goodsDetail.goods_"+i+".custOrder='"+data.items[i].goodsDetail.custOrder+"'");
						if (undefined != data.items[i].goodsDetail.advanceMoney && null != data.items[i].goodsDetail.advanceMoney)
							eval("$scope.form.goodsDetail.goods_"+i+".advanceMoney='"+data.items[i].goodsDetail.advanceMoney/100+"'");
						if (undefined != data.items[i].goodsDetail.actualBillCosts && null != data.items[i].goodsDetail.actualBillCosts)
							eval("$scope.form.goodsDetail.goods_"+i+".actualBillCosts='"+data.items[i].goodsDetail.actualBillCosts/100+"'");
						eval("$scope.form.goodsDetail.goods_"+i+".billingType='"+data.items[i].goodsDetail.billingType+"'");
						
						//计算价格回选 TODO
						if (undefined != data.items[i].goodsDetail.weightUnit && null != data.items[i].goodsDetail.weightUnit){
							eval("$scope.form.goodsDetail.goods_"+i+".weightUnit='"+(data.items[i].goodsDetail.weightUnit/100)+"'");
						}
						if (undefined != data.items[i].goodsDetail.volumeUnit && null != data.items[i].goodsDetail.volumeUnit){
							eval("$scope.form.goodsDetail.goods_"+i+".volumeUnit='"+(data.items[i].goodsDetail.volumeUnit/100)+"'");
						}
					}
					//处理订单显示
					bill.divCost();
					bill.form = $scope.form;
					//处理费用显示
					$scope.doChangeDelivery();
					$scope.refreshRouteTowardsDtl();
					$scope.updateGoodsDetailAmount("1");
					$scope.updateCount();
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

							if (undefined != $scope.form.orderInfo.cityId) {
								$scope.countyCreat($scope.form.orderInfo.cityId,data.districtId);
							}

							if (undefined != data.districtId) {
								$scope.streetCreat(data.districtId,data.streetId);
							}
							//师傅下拉框初始
							var provinceId = $scope.form.orderInfo.provinceId;
							var cityId = $scope.form.orderInfo.cityId;
							commonService.postUrl("cmSfUserInfoBO.ajax?cmd=queryUserInfoList",{"provinceId":provinceId,"cityId":cityId},function(data){
								if(data != null && data != undefined && data != "" && data.items.length>0){
									$scope.sfList = data;
								}
							});
						}
					});
					//合作商初始化
					if(data.items[0].orderInfo.sfName!=undefined){
						$scope.form.orderInfo.sfName = data.items[0].orderInfo.sfName;
					}
					if(data.items[0].orderInfo.sfTel!=undefined){
						$scope.form.orderInfo.sfTel = data.items[0].orderInfo.sfTel;
					}
					if(data.items[0].orderInfo.sfId!=undefined){
						$scope.form.orderInfo.sfId = data.items[0].orderInfo.sfId;
					}
					if(data.items[0].orderInfo.sfFee!=undefined){
						$scope.form.orderInfo.sfFee = data.items[0].orderInfo.sfFee/100;
					}
					//初始化付款方式2
					$scope.filterPayment(bill.paymentTypeDataAll,$scope.form.ordFee.paymentType,$scope.form.ordFee.paymentType2);
					//提货方式初始化
					$scope.getGetGoodsType();
					//对于是否拼单
					
					if($scope.form.orderInfo.orderState == 10 || $scope.form.orderInfo.orderState == "10" ){
						$scope.form.orderInfo.spell = true;
					}else{
						$scope.form.orderInfo.spell = false;
					}
					//如果是其他运单不显示拼单勾选框
					if($scope.form.orderInfo.orderId > 0 && !$scope.form.orderInfo.spell){
						$scope.isShowSpell = false;
					}else{
						$scope.isShowSpell = true;
					}
					$timeout(function(){
						if($scope.form.orderInfo.serviceType==undefined || $scope.form.orderInfo.serviceType==""){
											$scope.form.orderInfo.serviceType=data.items[0].orderInfo.serviceType+"";
						}
						if($scope.form.ordFee.paymentType2==undefined || $scope.form.ordFee.paymentType2==""){
							$scope.form.ordFee.paymentType2 = data.items[0].ordFee.paymentType2;
						}
					},1000);
					$scope.form.ordFee.cashMoney = $scope.cashMoney/100; 
					$scope.form.ordFee.cashMoney2 = $scope.cashMoney2/100; 
				}
			});
		},
		initSellerOrder:function(sellerOrderId){
			//-----页面展示后台显示数据逻辑
			//展示订单号
			$scope.showOrderId = false;
			$scope.view = true;
			this.initData();
			var param={"sellerOrderId":sellerOrderId};
			$scope.form.orderInfo.sellerOrderId = sellerOrderId;
			var datailUrl="";
			datailUrl="orderInfoBO.ajax?cmd=getSellerOrderInfo";
			commonService.postUrl(datailUrl,param,function(data){
				if(data != null && data != undefined && data != ""){
					//返回数据，并且处理数据的显示
					//$scope.form = {};
					//$scope.form.orderInfo = data.items[0].orderInfo;
					$scope.form.orderInfo.address = data.items[0].orderInfo.address;
//					$scope.form.ordFee = data.items[0].ordFee;
					//初始化付款方式2
					//$scope.filterPayment($scope.paymentTypeData.items,$scope.form.ordFee.paymentType,$scope.form.ordFee.paymentType2);
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
					
					//显示目?牡睾团渌屯?
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
					$scope.form.orderInfo.inputUserId = data.items[0].orderInfo.inputUserId;
					$scope.form.orderInfo.floor = data.items[0].orderInfo.floor+"";
					//$scope.form.ordFee.paymentType = data.items[0].ordFee.paymentType+"";
					//是否海运
					if(data.items[0].orderInfo.isSeaTransport == null || data.items[0].orderInfo.isSeaTransport == undefined || data.items[0].orderInfo.isSeaTransport == ""){
						$scope.isSeaTransport = false;
					}else{
						$scope.isSeaTransport = data.items[0].orderInfo.isSeaTransport==0?false:true; 
					}
					//发货人和收货人
					$scope.form.goodsDetail.pName = data.items[0].orderInfo.consignorName+"";
					$scope.form.goodsDetail.pLinkmanName = data.items[0].orderInfo.consignorLinkmanName;
					$scope.form.goodsDetail.pTelephone = data.items[0].orderInfo.consignorTelephone+"";
//					$scope.form.goodsDetail.pBill = data.items[0].orderInfo.consignorBill+"";
					$scope.form.goodsDetail.pId = data.items[0].orderInfo.consignorId;
					$scope.form.goodsDetail.rName = data.items[0].orderInfo.consigneeName+"";
					$scope.form.goodsDetail.rTelephone = data.items[0].orderInfo.consigneeTelephone;
					$scope.form.goodsDetail.rBill = data.items[0].orderInfo.consigneeBill+"";
					$scope.form.goodsDetail.rLinkmanName = data.items[0].orderInfo.consigneeLinkmanName+"";
					$scope.form.goodsDetail.rId = data.items[0].orderInfo.consigneeId;
					if(data.items[0].orderInfo.releaseNote == null || data.items[0].orderInfo.releaseNote == undefined || data.items[0].orderInfo.releaseNote == ""){
						$scope.releaseNote = false;
					}else{
						$scope.releaseNote = data.items[0].orderInfo.releaseNote==0?false:true; 
					}
					$scope.valuables = data.items[0].orderInfo.valuables==0?false:true; 
					$scope.userName=data.items[0].orderInfo.inputUserName;
					$scope.currOrgName=data.items[0].orderInfo.orgIdName;
					
					if(data.items[0].orderInfo.serviceType == null || data.items[0].orderInfo.serviceType == undefined || data.items[0].orderInfo.serviceType == ""){
						$scope.form.orderInfo.serviceType="11";
					}else{
						$scope.form.orderInfo.serviceType=data.items[0].orderInfo.serviceType+"";
					}
//					$scope.form.orderInfo.inputUserId=data.items[0].orderInfo.inputUserId;
					//处理货物显示
					for(var i=0;i<data.items.length;i++){
						eval("$scope.form.goodsDetail.goods_"+i+".isShow=true");
						eval("$scope.form.goodsDetail.goods_"+i+".goodsName='"+data.items[i].goodsDetail.goodsName+"'");
						eval("$scope.form.goodsDetail.goods_"+i+".packingType='"+data.items[i].goodsDetail.packingType+"'");
						if (undefined != data.items[i].goodsDetail.weight && null != data.items[i].goodsDetail.weight) 
							eval("$scope.form.goodsDetail.goods_"+i+".weight='"+data.items[i].goodsDetail.weight+"'");
						if (undefined != data.items[i].goodsDetail.volume && null != data.items[i].goodsDetail.volume) 
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
						if (undefined != data.items[i].goodsDetail.advanceMoney && null != data.items[i].goodsDetail.advanceMoney)
							eval("$scope.form.goodsDetail.goods_"+i+".advanceMoney='"+data.items[i].goodsDetail.advanceMoney/100+"'");
						if (undefined != data.items[i].goodsDetail.actualBillCosts && null != data.items[i].goodsDetail.actualBillCosts)
							eval("$scope.form.goodsDetail.goods_"+i+".actualBillCosts='"+data.items[i].goodsDetail.actualBillCosts/100+"'");
						
						eval("$scope.form.goodsDetail.goods_"+i+".billingType='"+data.items[i].goodsDetail.billingType+"'");
//						if (undefined != data.items[i].goodsDetail.packageCounts && null != data.items[i].goodsDetail.packageCounts)
//							eval("$scope.form.goodsDetail.goods_"+i+".packageCounts='"+data.items[i].goodsDetail.packageCounts+"'");
					}
					$scope.updateCount();
					// 刷新目的地信息
					commonService.postUrl("staticDataBO.ajax?cmd=revAddressInfo", {
						streetId: data.items[0].orderInfo.destStreet,
						districtId: data.items[0].orderInfo.destCounty,
						cityId: data.items[0].orderInfo.destCity,
						provinceId: data.items[0].orderInfo.destProvince
					},function(data){
						if(data != null && data != undefined && data != ""){
							if (undefined != data.provinceId&&0 != data.provinceId) {
								$scope.form.orderInfo.provinceId =  data.provinceId;
								$scope.form.orderInfo.provinceName =  data.provinceName;
							}
							if (undefined != data.cityId&&0 != data.cityId) {
								$scope.form.orderInfo.cityId =  data.cityId;
								jQuery("#cityName_value").val(data.cityName);
							}
							if (undefined != data.cityId) {
								$scope.countyCreat($scope.form.orderInfo.cityId,data.districtId);
							}
							
							if (undefined != data.districtId) {
								$scope.streetCreat(data.districtId,data.streetId);
							}
						}
					});
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
			
			$scope.form.ordFee.advanceMoney = $scope.form.ordFee.advanceMoney/100; 
			$scope.form.ordFee.actualBillCosts = $scope.form.ordFee.actualBillCosts/100; 
			
			$scope.form.ordFee.cashPayment = $scope.form.ordFee.cashPayment/100; 
			$scope.form.ordFee.freightCollect = $scope.form.ordFee.freightCollect/100; 
			$scope.form.ordFee.receiptPayment = $scope.form.ordFee.receiptPayment/100; 
			$scope.form.ordFee.monthlyPayment = $scope.form.ordFee.monthlyPayment/100; 
			$scope.form.ordFee.discount = $scope.form.ordFee.discount/100;
			$scope.form.ordFee.offer = $scope.form.ordFee.offer/100; 
			$scope.form.ordFee.cashMoney = $scope.cashMoney/100; 
			$scope.form.ordFee.cashMoney2 = $scope.cashMoney2/100; 
			$scope.form.orderInfo.totalFee = $scope.form.orderInfo.totalFee/100; 
			
			//统计总费用 -显示费用合计
			bill.updateTotalCosts();
		},
		form:{
			orderInfo:{
				releaseNote:0,
				valuables:0,
				isLift:0,
				isSeaTransport:2
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
				transPayment:0
			},
		},
		totalWeight:0,
		totalVolume:0,
		//保存初始化
		initData:function(){
			$scope.goodsDetailAmount = {
				weight: 0.0,// 重量
				volume: 0.0,// 体积
				freight: 0.0,// 运费
				discount: 0.0, // 回扣
				deliveryCosts: 0.0,// 送货费 
				collectingMoney: 0.0,// 代收货款
				procedureFee: 0.0,// 代收手续费
				advanceMoney:0.0,//垫付货款
				goodsPrice: 0.0, // 申明价值
				offer: 0.0,// 保险费
				handingCosts: 0.0,// 装卸费 
				packingCosts: 0.0,// 包装费
				pickingCosts: 0.0,// 提货费
				actualBillCosts:0.0,//实际提货费
				upstairsFee: 0.0, // 上楼费
				total: 0.0,
			};
			//等放货通知默认 不选中 与form.order.releaseNote 对应 false==0 true ==1
			$scope.releaseNote = false;
			$scope.valuables = false;
			$scope.isPayDiscount = false;
			$scope.isPayCash = false;
			$scope.currentIndex = -1;
			$scope.tempRname = "";
			$scope.tempRtel = "";
			$scope.isSeaTransport=false;//是否海运
			// 标识当前操作员是否为分拨中心的操作员
			$scope.isFbzxOperator = false;
			if (orgId != undefined && orgId > 0) {
				$scope.isFbzxOperator = true;
			}
		},
initStaticData:function(){
			
			/******把初始化的数据合并在一起进行处理,需要处理新增，修改的逻辑*********/
			var isAdd=true;//运单录入，新增
			if(orderId!=undefined && orderId!=null && orderId!="" && orderId>0){
				isAdd=false;
			}
			window.top.showLoad();
			commonService.postUrl("staticDataBO.ajax?cmd=queryOrderStaticInfo",{isAdd:isAdd},function(data){
				window.top.hideLoad();
				if(data != null && data != undefined && data != ""){
						var getTrackingNumMap=data.getTrackingNumMap;
						//运单号
						if(getTrackingNumMap!=undefined){
							$scope.form.orderInfo.trackingNum = getTrackingNumMap.trackingNum;
							$scope.form.orderInfo.trackingNumBegin = "";
							if(getTrackingNumMap.trackingNumBegin!=undefined&&getTrackingNumMap.trackingNumBegin!=null){
								$scope.form.orderInfo.trackingNumBegin = getTrackingNumMap.trackingNumBegin;
							}
							$scope.form.orderInfo.goodsNumber = $scope.form.orderInfo.trackingNumBegin + "" + $scope.form.orderInfo.trackingNum;
						}
						
						//获取交接方式
						$scope.deliveryTypeData = data.selectDeliveryType;
						//获取物流交接方式
						$scope.selectSysStaticDataByCodeType = data.selectSysStaticDataByCodeType;
						
						//包装类型
						$scope.packTypeData = data.selectPackType;
						if ( data.selectPackType != undefined && data.selectPackType.length > 0) {
							$scope.form.goodsDetail.goods_0.packingType = data.selectPackType[0].codeValue;
							$scope.dfpackTypeValue  = data.selectPackType[0].codeValue;
						}
						
						//获取常见品名
						$scope.commonGoodsName = data.loadCommonGoodsName;
						
						//付款方式 selectPaymentType
						var selectPaymentType=data.selectPaymentType;
						if(selectPaymentType != null && selectPaymentType != undefined && selectPaymentType != ""){
						    bill.paymentTypeDataAll=[];
						    angular.copy(selectPaymentType,bill.paymentTypeDataAll);
							$scope.paymentTypeData = selectPaymentType;
							
							if (undefined != selectPaymentType && null != selectPaymentType  && selectPaymentType.length > 0) {
								if(isAdd){
									$scope.filterPayment1(bill.paymentTypeDataAll);
									$scope.filterPayment(bill.paymentTypeDataAll,$scope.form.ordFee.paymentType,$scope.form.ordFee.paymentType2);
								}
							}
						}
						
						//只有新增的才有这个逻辑
						var getOpenButtonList=data.getOpenButtonList;
						if(getOpenButtonList != null && getOpenButtonList != undefined && getOpenButtonList != ""){
							for (var i = 0; i < getOpenButtonList.length; i++) {
								jQuery("#"+getOpenButtonList[i].codeValue).hide();
							}
						}
						
						//直送的逻辑
						var getDirectSendingInfo=data.getDirectSendingInfo;
						if(getDirectSendingInfo != null && getDirectSendingInfo != undefined  && getDirectSendingInfo != "" && getDirectSendingInfo.length>0){
							$scope.directSendingInfo=getDirectSendingInfo;
						}
				}else{
					commonService.alert("数据加载慢，请重新刷新页面!");
				}
				
			},function(){
				window.top.hideLoad();
			});
			
			
			
			
//			if(orderId==undefined || orderId==null || orderId==""){
//				commonService.postUrl("staticDataBO.ajax?cmd=getTrackingNum","",function(data){
//					$scope.form.orderInfo.trackingNum = data.trackingNum;
//					$scope.form.orderInfo.trackingNumBegin = "";
//					if(data.trackingNumBegin!=undefined&&data.trackingNumBegin!=null){
//						$scope.form.orderInfo.trackingNumBegin = data.trackingNumBegin;
//					}
//					$scope.form.orderInfo.goodsNumber = $scope.form.orderInfo.trackingNumBegin + "" + $scope.form.orderInfo.trackingNum;
//				});
//			}
			bill.queryRoateRuting('');
			/** Modify by chenjun 2016-06-03, 控制先加载网点，然后开单人员选择网点之后，在加载出目的地，和线路路由 Start*/
			
			//获取交接方式
//			commonService.postUrl("staticDataBO.ajax?cmd=selectDeliveryType","",function(data){
//				if(data != null && data != undefined && data != ""){
//					$scope.deliveryTypeData = data;
//					//$scope.form.orderInfo.deliveryType = $scope.deliveryTypeData.items[$scope.deliveryTypeData.items.length-1].codeValue;
//				}
//			});
			
			
			//获取包装类型
//			commonService.postUrl("staticDataBO.ajax?cmd=selectPackType","",function(data){
//				if(data != null && data != undefined && data != ""){
//					$scope.packTypeData = data;
//					if (undefined != data && data.items != undefined && data.items.length > 0) {
//						$scope.form.goodsDetail.goods_0.packingType = data.items[0].codeValue;
//						$scope.dfpackTypeValue  = data.items[0].codeValue;
//					}
//				}
//			});
			
			//获取常见品名
//			commonService.postUrl("staticDataBO.ajax?cmd=loadCommonGoodsName","",function(data){
//				if(data != null && data != undefined && data != ""){
//					$scope.commonGoodsName = data;
//				}
//			});
			
			//付款方式
//			commonService.postUrl("staticDataBO.ajax?cmd=selectPaymentType","",function(data){
//				if(data != null && data != undefined && data != ""){
//				    bill.paymentTypeDataAll={};
//				    angular.copy(data,bill.paymentTypeDataAll);
//					$scope.paymentTypeData = data;
//					
//					if (undefined != data && null != data && undefined != data.items && data.items.length > 0) {
//						//$scope.form.ordFee.paymentType = data.items[0].codeValue;
//						if(orderId != null && orderId != undefined && orderId !=""){
//							//修改则直接在修改方法里面初始化付款方式2
//						}
//						else
//						{
//							$scope.filterPayment1(bill.paymentTypeDataAll.items);
//							$scope.filterPayment(bill.paymentTypeDataAll.items,$scope.form.ordFee.paymentType,$scope.form.ordFee.paymentType2);
//						}
//					}
//				}
//			});
			
			
			//查询网点开单人员、业务员 众邦的代码 可以去掉
//			commonService.postUrl("webCmUserInfoBO.ajax?cmd=doQueryOrgUser",{state:1},function(data){
//				if(data != null && data != undefined && data != ""){
//					$scope.userInfoData = data;
//					// Add by chenjun. 默认选择开单员
//					/*if (undefined != data && undefined != data.items) {
//						for (var i = 0; i < data.items.length; i++) {
//							if (data.items[i].userType == '3') {
//								$scope.form.orderInfo.inputUserId = data.items[i].userId;
//								break;
//							}
//						}
//					}*/
//				}
//			});
			commonService.postUrl("orderInfoBO.ajax?cmd=queryCmOrgNotes",{},function(data){
				if(data != null && data != undefined && data != ""){
					$scope.customerRemark = data;
				}
			});
			commonService.postUrl("organizationBO.ajax?cmd=getOrgInfo",{},function(data){
				$scope.form.orderInfo.fromAddress = data.organization.departmentAddress;
				if(data.organization.parentOrgId=="-1"){
					document.getElementById("kd_gz").style.display="block";
					document.getElementById("buttonSave").style.display="none";
					$scope.isRootOrg = true;
				}
			});
//			if(orderId != null && orderId != undefined && orderId !=""){
//				//修改
//			}
//			else
//			{
//				commonService.postUrl("staticDataBO.ajax?cmd=getOpenButtonList","",function(data){
//					if(data != null && data != undefined && data != ""){
//						for (var i = 0; i < data.items.length; i++) {
//							jQuery("#"+data.items[i].codeValue).hide();
//						}
//					}
//				});
//			}
			//初始化直送的配置数据
//			this.getDirectSendingInfo();
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
		queryRoateRuting:function(cId){
			commonService.postUrl("routeBO.ajax?cmd=queryRoateTowards","endOwnerRegion="+cId+"&orgType=0",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.roateData = data;
					var defaultObj = {endOrgId: -1, endOwnerName: '空', endOwnerRegionName: ''};
					var array = new Array()
					array.push(defaultObj);
					for(var i = 0; i < data.items.length; i++) {
						array.push(data.items[i]);	
					}
					$scope.roateData.items = array;
					if ($scope.pageType == 1 && undefined != data && undefined != data.items && data.items.length > 0) {
						$scope.form.orderInfo.distributionOrgId = data.items[0].endOrgId;
						$scope.changeDistributionOrgId();
						$scope.doQueryArea();
					}
				}
			});
		},
		//配送区域查询
		doQueryArea:function(defaultDescRegion){
			if(undefined == $scope.form.orderInfo.distributionOrgId || -1 == $scope.form.orderInfo.distributionOrgId) {
				$scope.routeTowardsDtl = '';// 线路路由
				return;
			}
			$scope.areaData = [];
			//查询配送区域
//			if($scope.form.orderInfo.deliveryType ==2 || $scope.form.orderInfo.deliveryType == 3|| $scope.form.orderInfo.deliveryType == 4){
//				$scope.areaEdit = false;
//			}
			//自提时 设置详细地址
//			if($scope.form.orderInfo.deliveryType == 1){
//				$scope.areaEdit = true;
//				commonService.postUrl("staticDataBO.ajax?cmd=selectOrgInfo","orgId="+$scope.form.orderInfo.distributionOrgId,function(data){
//					bill.form.orderInfo.address = data.departmentAddress;
//				});
//			} 
			$scope.changeDistributionOrgId();
			if($scope.all == true){
				$scope.areaEdit = true;
			}
		},
		doChangeDelivery:function(){
			//交接方式属性为无需安装的，则安装师傅不可以选择
//			if($scope.form.orderInfo.deliveryObj!=undefined){
//				$scope.form.orderInfo.deliveryType = $scope.form.orderInfo.deliveryObj.codeValue;
//				var deliveryType = $scope.form.orderInfo.deliveryType;
//				if($scope.form.orderInfo.deliveryObj.codeId=='0'){
//					jQuery("#_sfName").attr('disabled', true);
//					jQuery("#_sfTel").attr('disabled', true);
//					jQuery("#_sfFee").attr('disabled', true);
//					$scope.form.orderInfo.sfName = "";
//					$scope.form.orderInfo.sfTel = "";
//					$scope.form.orderInfo.sfFee = "";
//					$scope.form.orderInfo.sfId = "";
//					
//				}
//				else
//				{
//					jQuery("#_sfName").attr('disabled', false);
//					jQuery("#_sfTel").attr('disabled', false);
//					jQuery("#_sfFee").attr('disabled', false);
//				}
//			}
		},
		changeDistributionOrgId: function(){// TODO Add by chenjun，“配送网点”发生改变时，需要加载“线路路由”，以及更新“目的地”等信息
			if ($scope.roateData!=undefined && undefined != $scope.roateData.items) {
				var distribution = undefined;
				for (var i = 0; i < $scope.roateData.items.length; i++) {
					if($scope.form.orderInfo.distributionOrgId != undefined && $scope.form.orderInfo.distributionOrgId == $scope.roateData.items[i].endOrgId){
						distribution = $scope.roateData.items[i];
						break;
					}
				}
				if (undefined != distribution) {
					// $scope.form.orderInfo.destCity = distribution.endOwnerRegion;
					// $scope.form.orderInfo.destCityName = distribution.endOwnerRegionName;
//					commonService.postUrl("staticDataBO.ajax?cmd=queryCityDetailInfo", {cityId:distribution.endOwnerRegion}, function(data){
//						if ('' != data) {
//							var inputValue = '';
//							if (undefined != data.provinceId) {// data.provinceId 省份
//								$scope.source.chooseCityId =  data.provinceId;
//								$scope.source.chooseCityName =  data.provinceName;
//								$scope.source.cityClick($scope.source.chooseCityId, data.provinceName);
//								inputValue = data.provinceName;
//							}
//							if (undefined != data.cityId) {
//								$scope.source.chooseRegionId =  data.cityId;
//								$scope.source.chooseRegionName =  data.cityName;
//								$scope.source.regionClick($scope.source.chooseRegionId, $scope.source.chooseRegionName);
//								inputValue = inputValue + "·" + data.cityName;
//							}
//							$scope.source.inputValue = inputValue;
//						}
//					});
					$scope.refreshRouteTowardsDtl();
				}
			}
		},
		refreshRouteTowardsDtl: function(){// 刷新线路路由
			var param = {"endOrgId": $scope.form.orderInfo.distributionOrgId};
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
					if($scope.form.orderInfo.desPointName!=null&&$scope.form.orderInfo.desPointName!=undefined&&$scope.form.orderInfo.desPointName!=""){
						$scope.routeTowardsDtl += '-' + $scope.form.orderInfo.desPointName;
					}
				}
				else
				{
					$scope.routeTowardsDtl = '';
				}
			});
		},
		//隐藏发货方和收货方的div
		showToFalse:function(type){
			$timeout(function(){
				if(type==1) {$scope.showPcum = false;};
				if(type==2) {$scope.showRcum = false;};
				if(type==3) {$scope.showRemark = false;};
				if(type==4) {$scope.showSf = false;};
				if(type==5) {$scope.querySf = false;};
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
						else
						{
							$scope.customerData = {};
							if($scope.fhr==true){
								$scope.form.goodsDetail.pTelephone = "";
								$scope.fhr = false;
							}
						}
					});
				}
			}else if(id == 1){
				var param={"type":"1"};
				commonService.postUrl("customerBO.ajax?cmd=getCustomer",param,function(data){
					if(data != null && data != undefined && data != ""){
						$scope.customerData = data;
					}
					else
					{
						$scope.customerData = {};
						if($scope.fhr==true){
							$scope.form.goodsDetail.pTelephone = "";
							$scope.fhr = false;
						}
					}
				});
			}
			if(pName == null || pName == undefined || pName == ""){
				$scope.showPcum = false;
				$scope.customerData = {};
			}
			$scope.currentIndex = -1 ;
		},
		//设置发货方
		selectPcustomer:function(obj){
			$scope.fhr = true;
			$scope.form.goodsDetail.pName = obj.name;
			$scope.form.goodsDetail.pLinkmanName = obj.linkmanName;
			$scope.form.orderInfo.fromAddress = obj.address;
			$scope.form.goodsDetail.pTelephone = obj.telephone;
			$scope.form.goodsDetail.pBill = obj.bill;
			$scope.form.goodsDetail.pId = obj.id;
			if(obj.signingType=='1'){
				$scope.form.goodsDetail.pOrgId = 0;
			}
			else
			{
				$scope.form.goodsDetail.pOrgId = -1;
			}
			$scope.form.goodsDetail.pTenantId = obj.tenantId;
//			if (obj.name == undefined || null == obj.name || '' == obj.name){
//				jQuery("#pName").focus();
//			} else if (obj.linkmanName == undefined || null == obj.linkmanName || '' == obj.linkmanName){
//				jQuery("#pLinkmanName").focus();
//			} else if (obj.telephone == undefined || null == obj.telephone || '' == obj.telephone){
//				jQuery("#_linkbilling").focus();
//			} else if (obj.bill == undefined || null == obj.bill || '' == obj.bill){
//				jQuery("#_pBill").focus();
//			} else {
//				jQuery("#_pBill").focus();
//			}
			jQuery("#_linkbilling").focus();
			if(obj.strSign!=undefined&&obj.strSign!=null&&obj.strSign!=""){
				$scope.form.orderInfo.isSign = true;
			}
			else
			{
				$scope.form.orderInfo.isSign = false;
			}
		},
		//显示收货方 并显示查询数据div
		queryRecCustomer:function(ids,rName){
			if(ids == 0){
				$scope.showRcum = false;
				//bill.form.goodsDetail.rId = "";
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
						else
						{
							$scope.recCustomerData = {};
							if($scope.shr==true){
								$scope.form.goodsDetail.rBill = "";
								$scope.shr = false;
							}
						}
					});
				}
			}else if(ids ==1){
				var param={"type":"2","id":id};
				commonService.postUrl("customerBO.ajax?cmd=getCustomer",param,function(data){
					if(data != null && data != undefined && data != ""){
						$scope.recCustomerData = data;
					}
					else
					{
						$scope.recCustomerData = {};
						if($scope.shr==true){
							$scope.form.goodsDetail.rBill = "";
							$scope.shr = false;
						}
					}
				});
			};
			if(rName == null || rName == undefined || rName == ""){
				$scope.showRcum = false;
				$scope.recCustomerData = {};
			}
			$scope.currentIndex = -1 ;
		},
		//设置收货人
		selectRcustomer:function(obj){
			$scope.shr = true;
			$scope.form.goodsDetail.rName = obj.name;
			$scope.form.goodsDetail.rLinkmanName = obj.linkmanName;
			$scope.form.goodsDetail.rTelephone = obj.telephone;
			$scope.form.goodsDetail.rBill = obj.bill;
			$scope.form.goodsDetail.rId = obj.id;
			$scope.form.orderInfo.address = obj.address;
//			if (obj.name == undefined || null == obj.name || '' == obj.name){
//				jQuery("#_rName").focus();
//			} else if (obj.linkmanName == undefined || null == obj.linkmanName || '' == obj.linkmanName){
//				jQuery("#_rLinkmanName").focus();
//			} else if (obj.telephone == undefined || null == obj.telephone || '' == obj.telephone){
//				jQuery("#_rTelephone").focus();
//			} else if (obj.bill == undefined || null == obj.bill || '' == obj.bill){
//				jQuery("#_rBill").focus();
//			} else {
//				jQuery("#_deliveryType").focus();
//			}
			jQuery("#_rTelephone").focus();
			
			//拼单执行
			$scope.callSpellQueryOrder();
		},
		//设置师傅
		selectSf:function(obj){
			$scope.form.orderInfo.sfTel = obj.phone;
			$scope.form.orderInfo.sfId = obj.sfUserId;
			$scope.form.orderInfo.sfName = obj.name;
			$scope.querySf = false;
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
				$scope.form.orderInfo.shipmentNotice = 1;
			}else{
				$scope.form.orderInfo.releaseNote = 0;
				$scope.form.orderInfo.shipmentNotice = 0;
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
			//是否海运
			if($scope.isSeaTransport == true){
				$scope.form.orderInfo.isSeaTransport = 1;
			}else{
				$scope.form.orderInfo.isSeaTransport = 2;
			}
		},
		//付款方式控制
		paymentChange:function(paymentType,type){
			$scope.filterPayment(bill.paymentTypeDataAll,bill.form.ordFee.paymentType,bill.form.ordFee.paymentType2);
			if($scope.goodsDetailAmount.total != undefined && $scope.goodsDetailAmount.total!=null && !isNaN($scope.goodsDetailAmount.total) && $scope.goodsDetailAmount.total != NaN && $scope.goodsDetailAmount.total !="NaN"){
				$scope.updateTotalFee();
				
				if(type=="1"){
					//修改运单初始化不用初始化总费用
				}
				else
				{
					bill.form.orderInfo.totalFee = isNaN($scope.goodsDetailAmount.total)==true? 0:$scope.goodsDetailAmount.total; 
				}
				if(orderId != null && orderId != undefined && orderId !=""){
					//修改运单
					if($scope.form.ordFee.paymentType2 == "-1"){
						bill.form.ordFee.cashMoney = isNaN($scope.goodsDetailAmount.total)==true ? 0:$scope.goodsDetailAmount.total; 
					}
					else
					{
						if(($scope.goodsDetailAmount.total - bill.form.ordFee.cashMoney2)>=0){
							bill.form.ordFee.cashMoney =  $scope.goodsDetailAmount.total - bill.form.ordFee.cashMoney2; 
						}
						else
						{
							bill.form.ordFee.cashMoney2 = 0;
							bill.form.ordFee.cashMoney =  $scope.goodsDetailAmount.total;
						}
					}
				}
				else
				{
					bill.form.ordFee.cashMoney = isNaN($scope.goodsDetailAmount.total)==true ? 0: $scope.goodsDetailAmount.total; 
					bill.form.ordFee.cashMoney2 = 0;
				}	
			}
			//7免费补发
			if($scope.form.ordFee.paymentType == '7'){
				jQuery("#_paymentType2").attr('disabled', true);
				$scope.form.ordFee.paymentType2 = "-1";
			}
			else
			{
				jQuery("#_paymentType2").attr('disabled', false);
			}
		},
		modifyMoney:function(){
			if(orderId != null && orderId != undefined && orderId !=""){
				bill.form.ordFee.cashMoney = $scope.goodsDetailAmount.total; 
				bill.form.ordFee.cashMoney2 = 0;
				$scope.form.ordFee.paymentType2 = "-1";
			}	
		},
		//付款方式2控制
		paymentChange2:function(payType2){
			
			if(payType2=="-1" || payType2==""){
				$scope.form.ordFee.cashMoney2 = "";
				$scope.form.ordFee.cashMoney = $scope.form.orderInfo.totalFee;
			}
		},
		money2Change:function(money2){
			if($scope.form.ordFee.payType2!="-1"){
				if($scope.form.orderInfo.totalFee - money2>=0){
					var value =  $scope.form.orderInfo.totalFee - money2;
					$scope.form.ordFee.cashMoney = value.toFixed(2);
				}
				else
				{
					$scope.form.ordFee.cashMoney;
				}
			}
		},
		//过滤支付方式2
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
			if(exceptValue!=undefined && selectedValue!=undefined && selectedValue==exceptValue){
				$scope.form.ordFee.paymentType2 = "-1";
			}
			
			if(selectedValue==undefined || selectedValue==null || selectedValue==""){
				$scope.form.ordFee.paymentType2 = "-1";
			}

		},
		
		//初始化支付方式1 的数据，添加一个“无”的数据
		filterPayment1:function(items){
			var tempItems = new Array();
			var allItem = {"codeName":"无","codeValue":"-1"};
			tempItems.push(allItem);
			for (var i = 0; i < items.length; i++) {
					tempItems.push(items[i]);
			}
			$scope.paymentTypeData = tempItems;
			
			$scope.form.ordFee.paymentType = "-1";
		},
		
		//统计费用合计
		updateTotalCosts:function(){
			$scope.totalCosts = 0;
			if($scope.form.ordFee.freight != null && $scope.form.ordFee.freight != undefined && $scope.form.ordFee.freight != ""){
				var value=Math.round($scope.form.ordFee.freight*100)/100;
				value = value.toFixed(2);
				if(isNaN(value) || value == NaN || value =="NaN"){
					bill.updateTotalCosts();
				}else{
					$scope.totalCosts += value;
				}
			}
			if($scope.form.ordFee.pickingCosts != null && $scope.form.ordFee.pickingCosts != undefined && $scope.form.ordFee.pickingCosts != ""){
				var value=Math.round($scope.form.ordFee.pickingCosts*100)/100;
				value = value.toFixed(2);
				if(isNaN(value) || value == NaN || value =="NaN"){
					bill.updateTotalCosts();
				}else{
					$scope.totalCosts += value;
				}
			}
			if($scope.form.ordFee.deliveryCosts != null && $scope.form.ordFee.deliveryCosts != undefined && $scope.form.ordFee.deliveryCosts != ""){
				var value=Math.round($scope.form.ordFee.deliveryCosts*100)/100;
				value = value.toFixed(2);
				if(isNaN(value) || value == NaN || value =="NaN"){
					bill.updateTotalCosts();
				}else{
					$scope.totalCosts += value;
				}
			}
			if($scope.form.ordFee.handingCosts != null && $scope.form.ordFee.handingCosts != undefined && $scope.form.ordFee.handingCosts != ""){
				var value=Math.round($scope.form.ordFee.handingCosts*100)/100;
				value = value.toFixed(2);
				if(isNaN(value) || value == NaN || value =="NaN"){
					bill.updateTotalCosts();
				}else{
					$scope.totalCosts += value;
				}
			}
			if($scope.form.ordFee.packingCosts != null && $scope.form.ordFee.packingCosts != undefined && $scope.form.ordFee.packingCosts != ""){
				var value=Math.round($scope.form.ordFee.packingCosts*100)/100;
				value = value.toFixed(2);
				if(isNaN(value) || value == NaN || value =="NaN"){
					bill.updateTotalCosts();
				}else{
					$scope.totalCosts += value;
				}
			}
			if($scope.form.ordFee.collectingMoney != null && $scope.form.ordFee.collectingMoney != undefined && $scope.form.ordFee.collectingMoney != ""){
				var value=Math.round($scope.form.ordFee.collectingMoney*100)/100;
				value = value.toFixed(2);
				if(isNaN(value) || value == NaN || value =="NaN"){
					bill.updateTotalCosts();
				}else{
					$scope.totalCosts += value;
				}
				$timeout(function(){
				    //bill.checkPrice(true);
				},500);
			}
			if($scope.form.ordFee.procedureFee != null && $scope.form.ordFee.procedureFee != undefined && $scope.form.ordFee.procedureFee != ""){
				var value=Math.round($scope.form.ordFee.procedureFee*100)/100;
				value = value.toFixed(2);
				if(isNaN(value) || value == NaN || value =="NaN"){
					bill.updateTotalCosts();
				}else{
					$scope.totalCosts += value;
				}
			}
			if($scope.form.ordFee.offer != null && $scope.form.ordFee.offer != undefined && $scope.form.ordFee.offer != ""){
				var value=Math.round($scope.form.ordFee.offer*100)/100;
				value = value.toFixed(2);
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
		//公共－返回的值为 保留小数点后面两位
		upCosts:function(valueName){
			if(eval("$scope."+valueName) != null && eval("$scope."+valueName) != undefined && eval("$scope."+valueName) != ""){
//				var value = eval("$scope."+valueName).replace(/^\D*(\d*(?:\.\d{0,2})?).*$/g, '$1');
				var value = eval("$scope."+valueName).replace(/^\D*(\d*(?:\.\d{0,2})?).*$/g, '$1');
				eval("$scope."+valueName+"=value");
				eval("$scope.updateTotalCosts()");
			}
		},
		upNum:function(valueName){
			if(eval("$scope."+valueName) != null && eval("$scope."+valueName) != undefined && eval("$scope."+valueName) != ""){
				var value = eval("$scope."+valueName).replace(/^\D*(\d*(?:\.\d{0,2})?).*$/g, '$1');
				eval("$scope."+valueName+"=value");
			}
		},
		//查看成本
		checkPrice:function(autoHide){
			//校验数据
			if(bill.checkPriceValue()){
				//代收货款
				var checkParam = {
						g_0:{},
						g_1:{},
				};
				checkParam.g_0.weight = bill.form.goodsDetail.goods_0.weight;
				checkParam.g_0.volume = bill.form.goodsDetail.goods_0.volume;
				checkParam.g_0.handingCosts = bill.form.goodsDetail.goods_0.handingCosts;
				if(bill.form.goodsDetail.goods_1.goodsName != undefined 
					&& bill.form.goodsDetail.goods_1.goodsName != null
					&& jQuery.trim(bill.form.goodsDetail.goods_1.goodsName) != ""){
					checkParam.g_1.weight = bill.form.goodsDetail.goods_1.weight;
					checkParam.g_1.volume = bill.form.goodsDetail.goods_1.volume;
					checkParam.g_1.handingCosts = bill.form.goodsDetail.goods_1.handingCosts;
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
				if(bill.form.orderInfo.deliveryType == 3||bill.form.orderInfo.deliveryType == 4){
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
				commonService.postUrl("routeCostBO.ajax?cmd=queryRouteCost",checkParam,function(data){
					if(data != null && data != undefined && data != ""){
						$scope.priceShow = true;
						$scope.allCost = data;
						if (autoHide == true) {
							$timeout(function(){
								$scope.hidePrice();
							}, 5000);
						}
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
			//$scope.totalCount = $scope.goodsDetailAmount.packageCounts;
			$scope.updateGoodsDetailAmount();
			$scope.totalCount = angular.copy($scope.goodsDetailAmount.packageCounts)
		},
		//查看成本数据校验
		checkPriceValue:function(){
			//如果交接方式是送货上楼和送货上门 楼层、是否为电梯、配送区域、详细地址为必填
			if(bill.form.orderInfo.deliveryType == 3||bill.form.orderInfo.deliveryType == 4){
				if(bill.form.orderInfo.floor == null || bill.form.orderInfo.floor == undefined || bill.form.orderInfo.floor == ""){
					commonService.alert("请选择楼层", function(){
						jQuery("#_floor").focus();
					});
					return false;
				}
//				if(bill.form.orderInfo.descRegion == null || bill.form.orderInfo.descRegion == undefined || bill.form.orderInfo.descRegion == ""){
//					commonService.alert("请选择配送区域", function(){
//						jQuery("#_descRegion").focus();
//					});
//					return false;
//				}
			}else if(bill.form.orderInfo.deliveryType == 2){
//				if(bill.form.orderInfo.descRegion == null || bill.form.orderInfo.descRegion == undefined || bill.form.orderInfo.descRegion == ""){
//					commonService.alert("请选择配送区域", function(){
//						jQuery("#_descRegion").focus();
//					});
//					return false;
//				}
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
		doConfirm:function(){
			if(orderId != null && orderId != undefined && orderId !=""){
				//修改
			}
			else
			{
				//新增
				commonService.confirm("请选择",function(){
				},function(){
					$scope.resetData();
				},"停在当前","再开一单");
			}
		},
		//提交按钮
		submit:function(successCallback){// 保存成功之后的回调
			if(bill.form.orderInfo.trackingNum == null || bill.form.orderInfo.trackingNum == undefined || bill.form.orderInfo.trackingNum == ""){
				commonService.alert("请输入运单号！", function(){
					jQuery("#_trackingNum").focus();
				});
				return false;
			}
			
			//南哥物流的特殊处理，判断运单好是否为8位
			if($scope.directSendingInfo!=undefined && $scope.directSendingInfo!="" && $scope.directSendingInfo.length>0){
				if(bill.form.orderInfo.trackingNum.length>8){
					commonService.alert("单号不是8位数，请确认单号是否正确");
					return false;
				}
			}
			
			if(bill.form.orderInfo.deliveryType!=1){
				if(bill.form.orderInfo.address == null || bill.form.orderInfo.address == undefined || bill.form.orderInfo.address == ""){
//					commonService.alert("请输入收货详细地址", function(){
//						jQuery("#_address").focus();
//					});
//					return false;
				}
			}
			if($scope.isRootOrg == true){
				commonService.alert("专线管理员不能开单", function(){
			});
			return false;
			}
			var chooseCityId = $scope.form.orderInfo.provinceId;// 省份
			var chooseRegionId = $scope.form.orderInfo.cityId;// 地市
			var chooseCountyId = $scope.form.orderInfo.countyId;// 县区
			var chooseStreetId = $scope.form.orderInfo.streetId;// 街道

			if (!$scope.checkStrIsBlank(chooseRegionId)) {
				commonService.alert("请选择目的地所在的地市!", function(){
					jQuery("#_source").focus();
				});
				return false;
			}
			
			bill.form.orderInfo.destProvince = chooseCityId;
			bill.form.orderInfo.destCity = chooseRegionId;
			bill.form.orderInfo.destCounty = chooseCountyId;
			bill.form.orderInfo.destStreet = chooseStreetId;
			
			/**
			//配送目的城市和 配送网点为必填
			if(bill.form.orderInfo.destCity == null || bill.form.orderInfo.destCity == undefined || bill.form.orderInfo.destCity == ""){
				commonService.alert("请填写目的城市!", function(){
					jQuery("#o").focus();
				});
				return;
			}
			*/
			
			/** 发货方和收获方信息校验 ** Start*/
			var billRegexp =/^0?(13[0-9]|15[0-9]|18[0-9]|14[57]|17[03678])[0-9]{8}$/;
			var phoneNumberRegexp2 = /^((0\\d{2,3}-\\d{7,8})|(1[3584]\\d{9}))$/;
			// 收货人和发货人两?跣畔⒈靥钇渲幸幌?
			if(!$scope.checkStrIsBlank(bill.form.goodsDetail.pName)){
				commonService.alert("请填写发货方或者发货方联系人", function(){
					jQuery("#pLinkmanName").focus();
				});
				return false;
			}
//			if(!$scope.checkStrIsBlank(bill.form.goodsDetail.pTelephone) && !$scope.checkStrIsBlank(bill.form.goodsDetail.pBill)){
//				commonService.alert("请填写发货方联系电话或者手机号码", function(){
//					jQuery("#_linkbilling").focus();
//				});
//				return false;
//			}
			
			if(!$scope.checkStrIsBlank(bill.form.goodsDetail.rName)){
				commonService.alert("请填写收货方或者收货方联系人", function(){
					jQuery("#_rLinkmanName").focus();
				});
				return false;
			}
			if(!$scope.checkStrIsBlank(bill.form.goodsDetail.rBill)){
				commonService.alert("请填写收货方联系电话或者手机号码", function(){
					jQuery("#_rTelephone").focus();
				});
				return false;
			}
//			if( (!phoneNumberRegexp2.test(bill.form.goodsDetail.rBill)&& !billRegexp.test(bill.form.goodsDetail.rBill))){
//				commonService.alert("收货方联系电话格式不正确!", function(){
//					jQuery("#_rTelephone").focus();
//				});
//				return false;
//			}
			/** 发货方和收获方信息校验 ** End*/
			if(bill.form.orderInfo.deliveryType == 1){
				$scope.form.orderInfo.isLift = null;	
			} else if (!$scope.checkStrIsBlank(bill.form.orderInfo.address)) {
//				commonService.alert("收货详细地址", function(){
//					jQuery("#_address").focus();
//				});
			}
			//bill.paymentChange();
			//bill.updateTotalCosts();
			
			// 检查货物
			for(var i = 0;i < bill.form.goodsDetail.goodsNum; i++){
				var check = true;
				if(i != 0){// 除了第一行，后面的货物，如果名字为空，则不需要校验
					var goodsName = eval("bill.form.goodsDetail.goods_" + i + ".goodsName");
					if(goodsName == undefined ||  goodsName == null || "" == goodsName){
						check = false;
						eval("bill.form.goodsDetail.goods_" + i + "={}");// 清空对应列的数据列的数据
						//$scope.updateGoodsDetailAmount();
					}
				}
				if(check) {
					var goodsName = eval("bill.form.goodsDetail.goods_" + i + ".goodsName");
					if(goodsName == undefined || goodsName == null || goodsName == ""){
						commonService.alert("请输入货物名称［品名］", function (){
							jQuery("#_goodsName" + i).focus();
						});
						return false;
					}
					
					var packingType = eval("bill.form.goodsDetail.goods_" + i + ".packingType");
					if(packingType == undefined || packingType == null || packingType == ""){
						commonService.alert("请选择包装类型", function(){
							jQuery("#_packingType" + i).focus();
						});
						return false;
					}
					
					var weight = eval("bill.form.goodsDetail.goods_" + i + ".weight");
					var volume = eval("bill.form.goodsDetail.goods_" + i + ".volume");
					var packageCounts = eval("bill.form.goodsDetail.goods_" + i + ".packageCounts");
					
					//限制所有的专线件数必填 
					if(packageCounts == undefined || packageCounts == null || packageCounts == ""){
						commonService.alert("请输入货品件数", function(){
							jQuery("#_packageCounts" + i).focus();
						});
						return false;
					}
					
					if((weight == undefined || weight == null || weight == "")&&(volume == undefined || volume == null || volume == "")&&(packageCounts == undefined || packageCounts == null || packageCounts == "")){
						commonService.alert("请输入体积、货品件数、重量必须3选1输入", function(){
							jQuery("#_volume" + i).focus();
						});
						return false;
					}
					if(volume != undefined && volume != null && volume != ""){
						eval("bill.form.goodsDetail.goods_" + i + ".billingType=2");
					}
					else if(weight != undefined && weight != null && weight != "")
					{
						eval("bill.form.goodsDetail.goods_" + i + ".billingType=1");
					}
					else if(packageCounts != undefined && packageCounts != null && packageCounts != "")
					{
						eval("bill.form.goodsDetail.goods_" + i + ".billingType=3");
					}
				}
				if (i == 0) {// 开单员可以将第二条货物的费用只输入第一条
					var freight = eval("bill.form.goodsDetail.goods_" + i + ".freight");
					if(freight == undefined || freight == null || freight == ""){
//						commonService.alert("请输入运费", function(){
//							jQuery("#_freight" + i).focus();
//						});
//						return false;
					}
				}
			}
			
			bill.totalWeight = $scope.goodsDetailAmount.weight;// 重量
			bill.totalVolume = $scope.goodsDetailAmount.volume;// 体积
			//物流交接
			if(bill.form.orderInfo.deliveryType == null || bill.form.orderInfo.deliveryType == undefined || bill.form.orderInfo.deliveryType == ""){
				commonService.alert("请选择物流交接", function(){
					jQuery("#_deliveryType").focus();
				});
				return false;
			};
			//家装服务
			if(bill.form.orderInfo.serviceType == null || bill.form.orderInfo.serviceType == undefined || bill.form.orderInfo.serviceType == "" || bill.form.orderInfo.serviceType == "-1"){
				commonService.alert("请选择家装服务", function(){
					jQuery("#_serviceType").focus();
				});
				return false;
			};
			//付款方式
			if(bill.form.ordFee.paymentType == null || bill.form.ordFee.paymentType == undefined || 
					bill.form.ordFee.paymentType == "" || bill.form.ordFee.paymentType == "-1"){
				commonService.alert("请输入付款方式", function(){
					jQuery("#_paymentType").focus();
				});
				return false;
			}else{
				//7免费补发
				if(bill.form.ordFee.paymentType == '7'){
					if(bill.form.ordFee.cashMoney > 0){
						commonService.alert("免费补发金额须为0元", function(){
							jQuery("#_cashMoney").focus();
						});
						return false;
					}
					if(bill.form.ordFee.cashMoney =="" || bill.form.ordFee.cashMoney==undefined){
						bill.form.ordFee.cashMoney = 0;
					}
				}
				//免费补发不需输入金额
				if(bill.form.ordFee.paymentType != -1 && bill.form.ordFee.paymentType != '7'  
					     && (bill.form.ordFee.cashMoney === "" || 
					    		  bill.form.ordFee.cashMoney == null || 
					    		    bill.form.ordFee.cashMoney == undefined ||  
					    		         bill.form.ordFee.cashMoney < 0)){
					commonService.alert("请输入付款方式1的金额", function(){
						jQuery("#_cashMoney").focus();
					});
					return false;
				}
				if(bill.form.ordFee.paymentType2 != -1 && (bill.form.ordFee.cashMoney2 === "" || 
			    		  bill.form.ordFee.cashMoney2 == null || 
			    		    bill.form.ordFee.cashMoney2 == undefined ||  
			    		         bill.form.ordFee.cashMoney2 < 0)){
					commonService.alert("请输入付款方式2的金额", function(){
						jQuery("#_cashMoney2").focus();
					});
					return false;
				}
			}
			
			if (bill.form.ordFee.paymentType == '4' || bill.form.ordFee.paymentType2 == '4' ) {
				if(bill.form.orderInfo.receiptNum == undefined  || bill.form.orderInfo.receiptNum == null || jQuery.trim(bill.form.orderInfo.receiptNum) == ""){
					commonService.alert("请输入回单号码", function(){
						jQuery("#_receiptNum").focus();
					});
					return false;
				}
				if(bill.form.orderInfo.receiptCount == undefined  || bill.form.orderInfo.receiptCount == null || jQuery.trim(bill.form.orderInfo.receiptCount) == "" || bill.form.orderInfo.receiptCount <= 0){
					commonService.alert("请输入回单数量", function(){
						jQuery("#_receiptCount").focus();
					});
					return false;
				}
			}

			if(document.getElementById("check-1").checked == true){
				$scope.form.orderInfo.releaseNote = 1;
			}
			if(document.getElementById("check-sea").checked == true){
				$scope.form.orderInfo.isSeaTransport = 1;
			}else{
				$scope.form.orderInfo.isSeaTransport = 0;
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
			$scope.form.orderInfo.isSeaTransport = $scope.isSeaTransport == true ? 1 : 2;
			if($scope.form.orderInfo.sfTel!="" && $scope.form.orderInfo.sfName!="" && $scope.form.orderInfo.sfTel!=undefined && $scope.form.orderInfo.sfName!=undefined){
				if($scope.form.orderInfo.sfId==undefined || $scope.form.orderInfo.sfId==""){
					commonService.alert("合作商请从列表中选择，不要手动输入合作商", function(){
						jQuery("#_sfName").focus();
					});
					return false;
				}
			}
			$scope.setGetGoodsType();
			if(!billRegexp.test(bill.form.goodsDetail.rBill)){
				commonService.confirm("收货方联系电话为:"+bill.form.goodsDetail.rBill+",是否继续保存？", function(){
					$scope.doSave(successCallback);
				}, function(){
					jQuery("#_rTelephone").focus();
				});
			}else{
				$scope.doSave(successCallback);
			}
			
		},
		//运单图片id
		getFlowId:function(){
			//运单图片id
			var flowId='';
			if($rootScope.idenCard1.get().flowId!=null && $rootScope.idenCard1.get().flowId!=undefined && $rootScope.idenCard1.get().flowId!=''){
				flowId=$scope.idenCard1.get().flowId;
			}
			if($rootScope.idenCard2.get().flowId!=null && $rootScope.idenCard2.get().flowId!=undefined && $rootScope.idenCard2.get().flowId!=''){
				if(flowId!=''){
					flowId+=","+$rootScope.idenCard2.get().flowId;
				}else{
					flowId+=$rootScope.idenCard2.get().flowId;
				}
			}
			if($rootScope.idenCard3.get().flowId!=null && $rootScope.idenCard3.get().flowId!=undefined && $rootScope.idenCard3.get().flowId!=''){
				if(flowId!=''){
					flowId+=","+$rootScope.idenCard3.get().flowId;
				}else{
					flowId+=$rootScope.idenCard3.get().flowId;
				}
			}
			if($rootScope.idenCard4.get().flowId!=null && $rootScope.idenCard4.get().flowId!=undefined && $rootScope.idenCard4.get().flowId!=''){
				if(flowId!=''){
					flowId+=","+$rootScope.idenCard4.get().flowId;
				}else{
					flowId+=$rootScope.idenCard4.get().flowId;
				}
			}
			if($rootScope.idenCard5.get().flowId!=null && $rootScope.idenCard5.get().flowId!=undefined && $rootScope.idenCard5.get().flowId!=''){
				if(flowId!=''){
					flowId+=","+$rootScope.idenCard5.get().flowId;
				}else{
					flowId+=$rootScope.idenCard5.get().flowId;
				}
		}
			return flowId;
		},
		doSave:function(successCallback){
			//运单底图Id
			bill.form.flowId=bill.getFlowId();
			if(imports != undefined && imports != "" && imports > 0){
				$scope.form.orderInfo.importBill = imports;
				$scope.form.orderInfo.importBillId = ids;
			}
			$timeout(function(){
				var url="orderInfoBO.ajax?cmd=doSave";
				if(bill.form.orderInfo.orderId!=undefined){
					if(isSpell == "true" || isSpell == true){
						if($scope.isShowSpell){
							url = url;
						}else{
							
							if(bill.form.orderInfo.orderState!=undefined 
									&& bill.form.orderInfo.orderState==5){
								//修改签收后的订单数据，
								url=url+"&alterSign=true";
							}else{
								url = url+"&modify=true";
							}
							
						}
					}else{
						if(bill.form.orderInfo.orderState!=undefined 
								&& bill.form.orderInfo.orderState==5){
							//修改签收后的订单数据，
							url=url+"&alterSign=true";
						}else{
							url=url+"&modify=true";
						}
					}
					
				}
				window.top.showLoad();
				commonService.postUrl(url,bill.form,function(data){
					window.top.hideLoad();
					if(data != null && data != undefined && data != ""){
						if(data.isOk == 'Y'){
							$scope.showOrderId = true;
							bill.form.orderInfo.orderId = data.orderId;
							bill.form.ordFee.orderId = data.orderId;
							//bill.form.goodsDetail.pId = data.pId;
							//bill.form.goodsDetail.rId = data.rId;
							bill.form.orderInfo.currentOrgId = data.currentOrgId;
							if($scope.form.orderInfo.spell){
								if(isSpell == "true" || isSpell == true){
									commonService.alert("拼单修改录入成功，如需改为正常单，请去除拼单勾选。");
								}else{
									commonService.alert("拼单录入成功，请前往拼单管理 管理拼单信息");
								}
							}else{
								if(isSpell == "true" || isSpell == true){
									commonService.alert("拼单结束，录入成功");
								}else{
									commonService.alert("录入成功");
								}
								
							}
							if(!$scope.form.orderInfo.spell){
								$scope.isShowSpell = false;
							}
							//修改后回写货物ID
							if(data.goodsId!=""&&data.goodsId!=undefined){
								if($scope.form.goodsDetail.goods_1.goodsName!=undefined&&$scope.form.goodsDetail.goods_1.goodsName!=""){
									$scope.form.goodsDetail.goods_1.id = data.goodsId;
								}
							}
							//新增后回写货物ID
							if(data.goodsId0!=""&&data.goodsId0!=undefined){
								if($scope.form.goodsDetail.goods_0.goodsName!=undefined&&$scope.form.goodsDetail.goods_0.goodsName!=""){
									$scope.form.goodsDetail.goods_0.id = data.goodsId0;
								}
								if(data.goodsId1!=""&&data.goodsId1!=undefined){
									if($scope.form.goodsDetail.goods_1.goodsName!=undefined&&$scope.form.goodsDetail.goods_1.goodsName!=""){
										$scope.form.goodsDetail.goods_1.id = data.goodsId1;
									}
								}
							}
							if (undefined != successCallback)
								successCallback();
						} else if(data.isOk == 'N'){ 
							commonService.alert(data.message);
						}
					}
				},function(response){
					window.top.hideLoad();
					commonService.alert(response.message);
				});
			},500);
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
			var goodsNo = $scope.form.orderInfo.trackingNum;
			
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
		//打印标签
		printStickerInfo: function(){
			if($scope.form.orderInfo.orderId ==null||$scope.form.orderInfo.orderId ==undefined||$scope.form.orderInfo.orderId ==''){
				commonService.confirm("运单信息未保存，不允许打印标签!");
				return false;
			}
			var serviceType = bill.getServiceTypeText($scope.form.orderInfo.serviceType);
			var trackingNum = ($scope.form.orderInfo.trackingNumBegin==undefined ? "":$scope.form.orderInfo.trackingNumBegin)+ $scope.form.orderInfo.trackingNum;//运单号
			var deliveryType = bill.getdeliveryTypeData($scope.form.orderInfo.deliveryType);//TODO 交货方式，需要转换成中文
			
			var deliveryTypeName = deliveryType;
			
			var goodsNo = trackingNum;
			var trackingNum = trackingNum;
			var isSeaTransport = $scope.isSeaTransport;
			var goodsNumberName = $scope.form.orderInfo.goodsNumber;
			
			if(!$scope.checkStrIsBlank(bill.form.goodsDetail.rName) && !$scope.checkStrIsBlank(bill.form.goodsDetail.rLinkmanName)){
				commonService.alert("请填写收货方", function(){
					jQuery("#_rName").focus();
				});
				return false;
			}

			var consignee = '';
			if ($scope.checkStrIsBlank(bill.form.goodsDetail.rName)) {
				consignee = bill.form.goodsDetail.rName;
			}
			
			var goodsName = $scope.form.goodsDetail.goods_0.goodsName == undefined ? '' : $scope.form.goodsDetail.goods_0.goodsName;
			if ($scope.checkStrIsBlank($scope.form.goodsDetail.goods_1.goodsName)) {
				goodsName = goodsName == '' ? $scope.form.goodsDetail.goods_1.goodsName : goodsName + '/' + $scope.form.goodsDetail.goods_1.goodsName;
			}
			
			var destCityName = $scope.form.orderInfo.cityName==undefined ? $scope.form.orderInfo.destCityName:$scope.form.orderInfo.cityName;
			//到达站
			var destCountyName = "";
			if($scope.form.orderInfo.countyId!=undefined && $scope.form.orderInfo.countyId!=null && $scope.form.orderInfo.countyId!=-1){
				destCountyName = bill.getCountyDataName($scope.form.orderInfo.countyId);
			}
			var desStation = destCityName;
			if(destCountyName != ""){
				desStation = destCountyName;
			}
			
			var totalPackageNum = 0;
			for(var i=0;i<4;i++){
				var goods = eval("$scope.form.goodsDetail.goods_"+i);
				if(goods == undefined){
					continue;
				}
				var isShow=eval("$scope.form.goodsDetail.goods_"+i+".isShow");
				if(isShow!=undefined && isShow==true){
					//包装件数
					var packageNum=eval("$scope.form.goodsDetail.goods_"+i+".packageCounts");
					if(!isNaN(packageNum)){
					   totalPackageNum = parseInt(totalPackageNum)+parseInt(packageNum);
					}
			   }
			
		   }
			// 这里需要需要的是开单网点的信息
			var orgId = $scope.currOrgId;// 开单网点
			commonService.postUrl("staticDataBO.ajax?cmd=queryOrgPhone",{"openOrgId":orgId,"orgId":$scope.form.orderInfo.distributionOrgId},function(data){
				var sourceCityName = data.openCityName;
				var sourceStation = data.openOrgName;
				var sourceStaffPhones = new Array(); //始发站客服
				var desStaffPhones = new Array(); //目的站客服
				var tenantName = data.tenantName;
				var createDate=data.createDate;//开单时间
				var creater=data.userName;//开单员名称
				
				var openOrgStaffPhone = data.openOrgStaffPhone; //始发站客服1
				var openOrgStaffPhone2 = data.openOrgStaffPhone2;//始发站客服2
				
				if (openOrgStaffPhone != undefined && openOrgStaffPhone != null && openOrgStaffPhone != ""){
					sourceStaffPhones.push(openOrgStaffPhone);
				}
                if (openOrgStaffPhone2 != undefined && openOrgStaffPhone2 != null && openOrgStaffPhone2 != ""){
                	sourceStaffPhones.push(openOrgStaffPhone2);
				}
				
                var descOrgStaffPhone = data.descOrgStaffPhone; //目的站客服1
				var descOrgStaffPhone2 = data.descOrgStaffPhone2;//目的站客服2
				
				if (descOrgStaffPhone != undefined && descOrgStaffPhone != null && descOrgStaffPhone != ""){
					desStaffPhones.push(descOrgStaffPhone);
				}
                if (descOrgStaffPhone2 != undefined && descOrgStaffPhone2 != null && descOrgStaffPhone2 != ""){
                	desStaffPhones.push(descOrgStaffPhone2);
				}
				var address = $scope.form.orderInfo.address;
				var stickerInfo = new StickerInfo(trackingNum,totalPackageNum,tenantName,sourceStaffPhones,
						desStaffPhones,sourceCityName,destCityName,deliveryTypeName,goodsName,consignee, 
						goodsNo, address,sourceStation,desStation);
						
						
				var stickerInfoDtlJH = new StickerInfoDtlJH(createDate,trackingNum,sourceStation,goodsNumberName,creater,desStation,isSeaTransport,consignee);
				var expressTagBean = new ExpressTagBean(stickerInfo,stickerInfoDtlJH);
				if ($scope.totalCount != undefined && $scope.totalCount > 10) {
					commonService.confirm("需要打印的标签数量大于10份，是否继续打印？", function(){
//						printStickerInfo(stickerInfo, "开单_打印标签", $scope.totalCount);
						commonPrint(bill.printConfigTagBean, expressTagBean, $scope.totalCount);
					}, function(){
						jQuery("#_totalCount").focus();
					});
				} else {
//					printStickerInfo(stickerInfo, "开单_打印标签", $scope.totalCount);
					var totalCount = $scope.totalCount ;
					if(totalCount == undefined || totalCount == null || totalCount == ""){
						totalCount = 1;
					}
					commonPrint(bill.printConfigTagBean, expressTagBean, totalCount);
				}
			});
		},
		saveAndPrintExpressInfo: function(){
			// 页面类型。1--> 运单录入；2-->运单详情；3-->运单修改
			if ($scope.pageType == 2) {// 保存
				$scope.printExpressInfo();
			} else if ($scope.pageType == 1 || $scope.pageType == 3) {
				$scope.submit($scope.printAndNew);
			}
		},
		printAndNew: function(fresh){
			if($scope.form.orderInfo.orderId ==null||$scope.form.orderInfo.orderId ==undefined||$scope.form.orderInfo.orderId ==''){
				commonService.confirm("运单信息未保存，不允许打印!");
				return false;
			}
			$scope.printExpressInfo(fresh);
		},
		//获取包装方式的中文
		getPackTypeData:function(type){
			if(type!=undefined && type!=''){
				var list=$scope.packTypeData;
				for(var i=0;i<$scope.packTypeData.length;i++){
					var elm=list[i];
					if(type==""+elm.codeValue){
						return elm.codeName;
					}
				}
			}
			return "";
			
		},
		//获取交接方式的中文
		getdeliveryTypeData:function(type){
			if(type!=undefined && type!=''){
				var list=$scope.deliveryTypeData;
				for(var i=0;i<$scope.deliveryTypeData.length;i++){
					var elm=list[i];
					if(type==""+elm.codeValue){
						return elm.codeName;
					}
				}
			}
			return "";
			
		},
		
		//获取支付方式的中文
		getPaymentTypeData:function(type){
			if(type!=undefined && type!=''){
				var list=$scope.paymentTypeData;
				for(var i=0;i<list.length;i++){
					var elm=list[i];
					if(type==""+elm.codeValue){
						return elm.codeName;
					}
				}
			}
			return "";
			
		},
		//如果有两个支付方式，需要金额
		getPaymentTypePrintText:function(paymentType1,fee1,paymentType2,fee2){
			var paymentTypeText1=this.getPaymentTypeData(paymentType1);
			var paymentTypeText2=this.getPaymentTypeData(paymentType2);
			var paymentTypeTexts = [];
			if(paymentTypeText2 == "" || paymentType2 == "-1" || paymentType2 == -1){
				//如果只有一种支付方式
				paymentTypeTexts.push(paymentTypeText1);
				//return paymentTypeText1;
			}else{
				//多笔付，需要打印金额
//				var paymentText=paymentTypeText1+" "+fee1+","+paymentTypeText2+" "+fee2;
//				return paymentText;
				paymentTypeTexts.push(paymentTypeText1+" "+fee1+"");
				paymentTypeTexts.push(paymentTypeText2 + " "+fee2+"");
			}
			return paymentTypeTexts;
		},
		//通过地市的id获取名称
		getCountyDataName:function(id){
			if(id!=undefined && id!=''){
				var list=$scope.countyData.items;
				for(var i=0;i<$scope.countyData.totalNum;i++){
					var elm=list[i];
					if(id==""+elm.id){
						return elm.name;
					}
				}
			}
			return "";
		},
		//获取家装服务的中文
		getServiceTypeText:function(type){
			if(type!=undefined && type!=''){
				var list=$scope.selectSysStaticDataByCodeType;
				for(var i=0;i<$scope.selectSysStaticDataByCodeType.length;i++){
					var elm=list[i];
					if(type==""+elm.codeValue){
						return elm.codeName;
					}
				}
			}
			return "";
		},
		//获取街道的中文名称
		getStreetName:function(id){
			if(id!=undefined && id!=''){
				var list=$scope.streetData.items;
				for(var i=0;i<$scope.streetData.totalNum;i++){
					var elm=list[i];
					if(id==""+elm.id){
						return elm.name;
					}
				}
			}
			return "";
		},
		
		/**
		 * 打印运单（面单）
		 */
		printExpressInfo:function(fresh){
			var that=this;
			//配送网点，开单网点
			var param={orgId:$scope.form.orderInfo.distributionOrgId,openOrgId:userInfo.orgId};
			commonService.postUrl("staticDataBO.ajax?cmd=queryOrgPhone",param,function(data){
			if(data != null && data != undefined && data != ""){
				
				var sourceStationPhone=data.openOrgStaffPhone;//开单网点的电话(客服1)
				
				//对于打印支持2个客服处理（新增2个打印字段（以前不处理））(目的站)
				var openOrgStaffPhone2 = data.openOrgStaffPhone2;//开单网点的电话(客服2)
				var sourceStaffPhones = new Array();
				if(sourceStationPhone != undefined && sourceStationPhone != '' &&sourceStationPhone != null ){
					sourceStaffPhones.push(sourceStationPhone);
				}
				if(openOrgStaffPhone2 != undefined && openOrgStaffPhone2 != '' && openOrgStaffPhone2 != null ){
					sourceStaffPhones.push(openOrgStaffPhone2);
				}
			
				var sourceStationName= data.openOrgName;//开单网点名称
				
				//发货人 名称
				var sendName=$scope.form.goodsDetail.pName;
				//发货电话
				var sendPhone=$scope.form.goodsDetail.pTelephone;
				//发货人地址
				var sendAddr=$scope.form.orderInfo.fromAddress;
				var desProvinceNg = $scope.form.orderInfo.destProvinceName; //南哥省
				var desCityNg = ""; //南哥市
				var	desCountyNg = ""; //南哥区
				var	desDtlNg = ""; //南哥详细地址
				//到达站 区域,市区还没有
				var destCityName=$scope.form.orderInfo.cityName==undefined ? $scope.form.orderInfo.destCityName:$scope.form.orderInfo.cityName;
				var destStationName=destCityName;
				var destCounty="";
				if($scope.form.orderInfo.countyId!=undefined && $scope.form.orderInfo.countyId!=null && $scope.form.orderInfo.countyId!=-1){
					destCounty=bill.getCountyDataName($scope.form.orderInfo.countyId);
					destStationName=destStationName+bill.getCountyDataName($scope.form.orderInfo.countyId);
				}
				//街道 
				var streetName=bill.getStreetName($scope.form.orderInfo.streetId);
				
				//鸿吉打印 到最后选中的地址级别 （选择到市 打印市选择到区就打印区、选择到镇就打印到镇） 20161129改
				var desStationNameHjwl = "";
				if(streetName != null && streetName != ""){
					desStationNameHjwl = streetName;
				}else if(destCounty != null && destCounty != ""){
					desStationNameHjwl = destCounty;
				}else{
					desStationNameHjwl = destCityName;
				}
				
				
				desCityNg = destCityName;
				desCountyNg = destCounty;
				var addressNg = $scope.form.orderInfo.address != undefined && $scope.form.orderInfo.address != null ? $scope.form.orderInfo.address : "";
				desDtlNg = streetName !=undefined && streetName !='' ?  streetName + " "+ addressNg : addressNg;
				
				if($scope.tenantId==220){
					//东邦的特殊要求，如果选择了街道名字为“燕郊镇”,达到目的打印“"燕郊镇"”
					if(streetName=="燕郊镇"){
						destCounty="燕郊镇";
					}else if(streetName=="白沟镇"){
						destCounty="白沟镇";
					}
					
				}
				//旭阳升打印 目的站 特殊要求 河北廊坊市霸州市胜芳镇 //打印为胜芳镇 
				if($scope.tenantId == 106){
					if(streetName == "胜芳镇"){
						destCounty = "胜芳镇";
					}
				}
				//安中物流   湖北随州市广水市应山 四级地址打印的时候特殊处理：选择 “应山” 的四级地址目的站打印“应山”  187
				//安中物流， 湖北武汉市新洲区阳逻开发区  到阳逻的直接打印阳逻
				if($scope.tenantId == 187){
					if(streetName == "应山" || streetName == "应山街道"){
						destStationName = "应山";
					}
					if(streetName == "阳逻开发区"){
						destStationName = "阳逻";
					}
				}
				
				//到达站电话
				var destStationPhone = data.descOrgStaffPhone;
				//对于打印支持2个客服处理（新增2个打印字段（以前不处理））(到达站)
				var descOrgStaffPhone2 = data.descOrgStaffPhone2;//开单网点的电话(客服2)
				var descStaffPhones = new Array();
				if(destStationPhone != undefined && destStationPhone != '' && destStationPhone != null ){
					descStaffPhones.push(destStationPhone);
				}
				if(descOrgStaffPhone2 != undefined && descOrgStaffPhone2 != '' && descOrgStaffPhone2 != null ){
					descStaffPhones.push(descOrgStaffPhone2);
				}
				
				
				//收货人名称
				var receiveName=$scope.form.goodsDetail.rName;
				//打印转师傅的运单时，要将收货人的手机号屏蔽掉 
				var rNameNotPhone=$scope.form.goodsDetail.rNameNotPhone;
				if(rNameNotPhone==undefined || rNameNotPhone==""){
					if($scope.form.goodsDetail.rName!=undefined){
						var index=$scope.form.goodsDetail.rName.indexOf("(");
						rNameNotPhone=$scope.form.goodsDetail.rName.substr(0,index);
					}
					if(rNameNotPhone==""){
						rNameNotPhone=receiveName;
					}
				}
				
				
				//收货人电话	
				var receivePhone=$scope.form.goodsDetail.rBill;
				//备用电话
				var standbyPhone=$scope.form.goodsDetail.rTelephone;
				
				//收货人地址	
				var receiveAddr=$scope.form.orderInfo.address;
				
				if(receiveAddr==undefined){
					receiveAddr="";
				}
				
				var destCountyStreetAddr = destCounty+streetName+receiveAddr;
				var freight=0;//运费
				var installCost=0;//配安费用
				var premiumCost=0;//保险费用
				var pickingCost=0;//提货费用
				var otherCost=0;//其他费用
				var collectingMoney=0;//代收货款
				var totalCost=$scope.form.orderInfo.totalFee;//总运费
				
				//亿航货物信息	
				var totalDeclaredValue=0;//亿航声明价格
				var totalWeight=0;//亿航重量
				var totalPackageNum=0;//亿航包装件数
				var goodsNameS="";//亿航品名
				var discountTotal=0;//回扣总额
				var totalVolume=0;
				var packageTypeNg = ""; //南哥旧面单包装处理
				var upstairsFeeTotal = 0; //上楼费
				//发货人信息	
				var sourceStation=new sourceStationInfo(sourceStationName,sourceStationPhone,sendName,sendPhone,sendAddr,sourceStaffPhones);
				//收货人信息
				var destStation=new destStationInfo(destStationName,destStationPhone,receiveName,receivePhone,receiveAddr,destCityName,destCounty,streetName,destCountyStreetAddr,rNameNotPhone,standbyPhone,$scope.tenantId,descStaffPhones,desStationNameHjwl);
				//货物信息
				var goodsDetailInfos=new Array();
				//第三方订单号
				var custOrder="";
				
				//吉航特殊要求
				var isSeaTransport = $scope.isSeaTransport;//是否海运（是：海运，否：陆运）
				
				for(var i=0;i<4;i++){
					var goods=eval("$scope.form.goodsDetail.goods_"+i);
					if(goods==undefined){
						continue;
					}
					var isShow=eval("$scope.form.goodsDetail.goods_"+i+".isShow");
					if(isShow!=undefined && isShow==true){
						//货物名称
						var goodsName=eval("$scope.form.goodsDetail.goods_"+i+".goodsName");
						if(goodsName!=null&&goodsName!=undefined&&goodsName!=""){
							if(goodsNameS!=""){
								goodsNameS=goodsNameS+"、"+goodsName;
							}else{
								goodsNameS=goodsName;
							}
						}
						//包装类型  需要转换中文
						var packageType=bill.getPackTypeData(eval("$scope.form.goodsDetail.goods_"+i+".packingType"));
						
						//包装件数
						var packageNum=eval("$scope.form.goodsDetail.goods_"+i+".packageCounts");
						if(!isNaN(packageNum)){
						totalPackageNum=parseInt(totalPackageNum)+parseInt(packageNum);
						}
						var installCount=eval("$scope.form.goodsDetail.goods_"+i+".installCount");
						if(!isNaN(installCount)){
							totalPackageNum=parseInt(totalPackageNum)+parseInt(installCount);
						}
						//重量
						var weight=eval("$scope.form.goodsDetail.goods_"+i+".weight");
						
						if(weight!=undefined && weight!=null && weight!=""){
							totalWeight=parseFloat(totalWeight)+parseFloat(weight);
						}
						
						//体积
						var volume=eval("$scope.form.goodsDetail.goods_"+i+".volume");
						
						if(volume!=undefined && volume!=null && volume!=""){
							totalVolume=parseFloat(totalVolume)+parseFloat(volume);
						}
						
						//申明价格
						var declaredValue=eval("$scope.form.goodsDetail.goods_"+i+".goodsPrice");
						if(declaredValue!=undefined && declaredValue!=null&&declaredValue!="" ){
							totalDeclaredValue=parseFloat(totalDeclaredValue)+parseFloat(declaredValue);
						}
						//配安费用
						var installCostTemp=parseInt(eval("$scope.form.goodsDetail.goods_"+i+".installCosts"));
						installCostTemp=(isNaN(installCostTemp)==true?0:installCostTemp);
						var installCost=installCost+installCostTemp;
						
						//保险费
						var premiumCostTemp=parseInt(eval("$scope.form.goodsDetail.goods_"+i+".offer"));
						premiumCostTemp=(isNaN(premiumCostTemp)==true?0:premiumCostTemp);
						var premiumCost=premiumCost+premiumCostTemp;
						//提货费
						var pickingCostTemp=parseInt(eval("$scope.form.goodsDetail.goods_"+i+".pickingCosts"));
						pickingCostTemp=(isNaN(pickingCostTemp)==true?0:pickingCostTemp);
						var pickingCost=pickingCost+pickingCostTemp;
						// 代收手续费 不确定
						var collectingMoneyTemp=parseInt(eval("$scope.form.goodsDetail.goods_"+i+".collectingMoney"));
						collectingMoneyTemp=(isNaN(collectingMoneyTemp)==true?0:collectingMoneyTemp);
						var collectingMoney=collectingMoney+collectingMoneyTemp;
						//货品里面的运费
						var freightTemp=parseInt(eval("$scope.form.goodsDetail.goods_"+i+".freight"));
						freightTemp=(isNaN(freightTemp)==true?0:freightTemp);
						var freight=freight+freightTemp;
						
						var discountTemp=parseInt(eval("$scope.form.goodsDetail.goods_"+i+".discount"));
						discountTemp=(isNaN(discountTemp)==true?0:discountTemp);
						discountTotal=discountTotal+discountTemp;
						 
						var isInsurance = (goodsName != null&& goodsName != undefined && goodsName != "" ? "已投保" : "");
						var goodsDetail =new goodsDetailInfoDtl(goodsName,packageType,packageNum,weight,volume,declaredValue,premiumCostTemp,installCostTemp,freightTemp,collectingMoneyTemp,isInsurance);
						goodsDetailInfos.push(goodsDetail);
						
						//第三方单号
						var custOrderTemp=eval("$scope.form.goodsDetail.goods_"+i+".custOrder");
						if(custOrderTemp!=null&&custOrderTemp!=undefined&&custOrderTemp!=""){
							custOrder=custOrder+eval("$scope.form.goodsDetail.goods_"+i+".custOrder")+",";
						}
						
						//20161110 新增
						//上楼费
						var upstairsFee = parseFloat(eval("$scope.form.goodsDetail.goods_"+i+".upstairsFee"));
						
						upstairsFeeTotal = upstairsFeeTotal + (isNaN(upstairsFee) ? "" : upstairsFee); 
						
						if(i == 0){
							packageTypeNg = packageTypeNg + packageType;
						}else{
							if(packageType != ""){
								packageTypeNg = "/"+packageTypeNg + packageType;
							}
							
						}
					}
				}
				
				if(custOrder!=""){
					custOrder=custOrder.substr(0,custOrder.length-1);
				}
				
				/**亿航*/
//				if($scope.form.orderInfo.weight!=undefined){
//					totalWeight=$scope.form.orderInfo.weight;
//				}
//				if($scope.form.orderInfo.volume!=undefined){
//					totalVolume=$scope.form.orderInfo.volume;
//				}
				
				var goodsDetailInfo=new goodsDetailInfoDtlOfYH(goodsNameS,totalPackageNum,"总重量:"+totalWeight,totalDeclaredValue.toFixed(2),"总体积:"+totalVolume);
				
				
				//南哥旧面单处理
				var goodsDetailInfoNg = new goodsDetailInfoDtlOfNG(goodsNameS,packageTypeNg,totalWeight,totalPackageNum,totalVolume,desProvinceNg,desCityNg,desCountyNg,desDtlNg);
				
				
				//费用信息
				var fee=new feeInfo(freight,installCost,premiumCost,pickingCost,otherCost,collectingMoney,totalCost,discountTotal,upstairsFeeTotal);
				var trackingNum =($scope.form.orderInfo.trackingNumBegin==undefined ? "":$scope.form.orderInfo.trackingNumBegin)+ $scope.form.orderInfo.trackingNum;//运单号
				var deliveryType= bill.getdeliveryTypeData($scope.form.orderInfo.deliveryType);//TODO 交货方式，需要转换成中文
				var isVerification=$scope.form.orderInfo.isVerification;//是否需要核销
				var isReceipt=$scope.form.orderInfo.isReceipt;//是否需要回单
				
				var consignee=$scope.form.orderInfo.consignee;//仓管员
				
//				var paymentType = bill.getPaymentTypePrintText($scope.form.ordFee.paymentType,$scope.form.ordFee.cashMoney,
//						$scope.form.ordFee.paymentType2,$scope.form.ordFee.cashMoney2);//付款方式，paymentType1, 付款金额：cashMoney,cashMoney1
				
				var paymentType = bill.getPaymentTypePrintText($scope.form.ordFee.paymentType,$scope.form.ordFee.cashMoney,
				        $scope.form.ordFee.paymentType2,$scope.form.ordFee.cashMoney2);//付款方式，paymentType1, 付款金额：cashMoney,cashMoney1
                
				var createDate=data.createDate;//开单时间
				var creater=data.userName;//开单员名称
				
				//运单修改的时候
				if($scope.form.orderInfo.createDate!=undefined && $scope.form.orderInfo.createDate!=""
					&& $scope.form.orderInfo.createDate!=null){
					createDate=$scope.form.orderInfo.createDate.substr(0,10);
				}
				
				if($scope.form.orderInfo.inputUserName!=undefined && $scope.form.orderInfo.inputUserName!=""
					&& $scope.form.orderInfo.inputUserName!=null){
					creater=$scope.form.orderInfo.inputUserName;
				}
				
				
				
				var remarks=$scope.form.orderInfo.remarks;//备注
				var serviceType=bill.getServiceTypeText($scope.form.orderInfo.serviceType);
				
				//旭阳升：家装服务  没有选或者无 ，就 打印配送方式。如果有家装服务，就不需要打印配送方式(备注) 106
				if($scope.tenantId == 106){
					if(serviceType == "无"){
						serviceType = deliveryType;
					}
				}
				
				
				
				//其他信息
				var other;
				
				var goodsNumberName = $scope.form.orderInfo.goodsNumber;//货号
				
				if(paymentType.length < 2){
					other = new otherInfo(trackingNum,deliveryType,isVerification,isReceipt,paymentType[0],null,null,createDate,creater,remarks,serviceType,consignee,custOrder,isSeaTransport,goodsNumberName);
				}else{
					other = new otherInfo(trackingNum,deliveryType,isVerification,isReceipt,null,paymentType[0],paymentType[1],createDate,creater,remarks,serviceType,consignee,custOrder,isSeaTransport,goodsNumberName);
				}
				var expressBean=new ExpressBean(sourceStation,destStation,goodsDetailInfos,fee,other,goodsDetailInfo,goodsDetailInfoNg);
//				printExpressInfo(expressBean,"打印");
				commonPrint(bill.printConfigBean, expressBean, 1);
				
				//打印完后，重置数据
				if(fresh==undefined || fresh==true){
					//最新需求打印完后（打开最新的开单TAB）
					//打印页面打印调取完后关闭打印页面（开单页面打印完后新增页面）
					
					if(print == 1){
						//打印直接关闭标签(点击确认关闭tab)
						commonService.alert("打印正在进行中",function(){
							commonService.closeToParentTab();
						});
						
					}else{
						//新增运单打印刷新数据，修改打印直接关闭
 						if(edit == 1){
 							//修改打印直接关闭(点击确认关闭tab)
 							commonService.alert("打印正在进行中",function(){
 								commonService.closeToParentTab();
 							});
 						}else{
 							$scope.resetData();
 						}
					}
				}
				
			}
		});
		},
		close:function(){
    		commonService.closeToParentTab(true);
    	},
    	/**
    	 * 获取打印配置（打印面单、打印标签）
    	 */
    	getPrintConfigBean:function(){
    		/*** 配载调试的方法
    		var config={};
    		var itemList=new Array();
    		
    		config.tenantId="";
    		config.orgId="";
    		config.bizCode="";
    		config.bizName="";
    		config.topOffset="0";
    		config.leftOffset="0";
    		config.editableWidth="233.89";
    		config.editableHeight="148";
    		config.intOrient="0";
    		config.pageWidth="240";
    		config.pageHeight="148";
    		config.pageName="LodopCustomPage";
    		config.bkimgName="110.jpg";
    		config.bkimgTop="-4.4";
    		config.bkimgLeft="-1.0";
    		config.bkimgWidth="203";
    		config.bkimgHeight="140";
    		config.bkimgPrint="";
    		config.state="";
    		
    		var item={};
    		
    		item.id="";
    		item.configId="";
    		item.itemType="1";
    		item.itemDesc="";
    		item.objectKey="sourceStationInfo.stationName";
    		item.specialObjectValue="";
    		item.formatFunc="";
    		item.posType="1";
    		item.relObjectKey="";
    		item.offsetValue="";
    		item.topOffset="20";
    		item.leftOffset="10.5";
    		item.itemWidth="53";
    		item.itemHeight="9";
    		item.fontSize="13";
    		item.fontBold="4";
    		item.state="";
    		
    		itemList.push(item);
    		
    		**/
    		
    		//面单
    		var url = "printBO.ajax?cmd=queryTrackingPrintConfig";
			commonService.postUrl(url,"",function(data){
				bill.printConfigBean=new PrintConfigBean(data.config,data.itemList);
				//设置一下打印的租户id
				$scope.tenantId=data.config.tenantId;
			});
			$scope.tagPrintTrue = true; //是否显示打印标签按钮
			//标签
			var url = "printBO.ajax?cmd=queryStickerPrintConfig";
			commonService.postUrl(url,"",function(data){
               if(data == null || data.config == undefined || data.config == null || data.config == ""){
            	   $scope.tagPrintTrue = false; 
				}else{
					bill.printConfigTagBean = new PrintConfigBean(data.config,data.itemList);
					$scope.tagPrintTrue = true; //是否显示打印标签按钮
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
		},
		arrivePointSet:function(){
			var provinceId = $scope.form.orderInfo.provinceId;
			var cityId = $scope.form.orderInfo.cityId;
			var countyId = $scope.form.orderInfo.countyId;
			var url = "routeAreaRelCfgBO.ajax?cmd=doQueryListNew";
			var isSeaTransport = $scope.isSeaTransport == true ? 1 : 0;
			var param = {"provinceId":provinceId,"cityId":cityId,"countyId":countyId,"isSeaTransport":isSeaTransport};
			//获取网点辐射区域配置
			commonService.postUrl(url,param,function(data){
				if(data!=undefined&&data!=null&&data!="" && data.destOrgId != null && data.destOrgId > 0){
					$scope.form.orderInfo.distributionOrgId = parseInt(data.destOrgId);
					$scope.form.orderInfo.desPointName = data.countyName;
					$scope.form.orderInfo.desPoint = data.countyId;
					$scope.refreshRouteTowardsDtl();
				}
				else
				{
					$scope.form.orderInfo.distributionOrgId = -1;
					$scope.refreshRouteTowardsDtl();
				}
        	});
			//获取安装师傅
			commonService.postUrl("cmSfUserInfoBO.ajax?cmd=queryUserInfoList",{"provinceId":provinceId,"cityId":cityId},function(data){
				if(data != null && data != undefined && data != "" && data.items.length>0){
					$scope.sfList = data;
					if(data.items.length==1){
						$scope.selectSf(data.items[0]);
					}
				}
				else
				{
					$scope.sfList = {};
		        	$scope.form.orderInfo.sfName = "" ;
		        	$scope.form.orderInfo.sfTel = "" ;
		        	$scope.form.orderInfo.sfFee = "" ;
		        	$scope.form.orderInfo.sfId = "";
				}
			});
			
		},
		changeDesPoint:function(){
			$scope.refreshRouteTowardsDtl();
		},
		callBack:function(r){
			var districtId=-1;
			var retdistrictId=r.originalObject.districtId;
			if(r.originalObject.districtId.length>5){
				districtId=r.originalObject.districtId;
			}
			
			var name =r.originalObject.name;
			var cityName =r.originalObject.cityName;
			var provinceName =r.originalObject.provinceName;
			var provinceId=retdistrictId.substr(0,2);
			var cityId=retdistrictId.substr(0,5);
			$scope.form.orderInfo.provinceName = provinceName;
			$scope.form.orderInfo.provinceId = provinceId;
			$scope.form.orderInfo.cityId = cityId;
			$scope.form.orderInfo.cityName=cityName;
			$scope.countyCreat(cityId,districtId);
			if(districtId.length>5){
				$scope.form.orderInfo.countyId = districtId;
			}
			$scope.form.orderInfo.streetId =null;
			$scope.arrivePointSet();
			jQuery("#_rName").focus();
			
			
			
			//回选商家协议价格或标准价格  TODO
			
			var  pTenantIdTrue = $scope.form.goodsDetail.pTenantId;
			if(pTenantIdTrue != undefined && pTenantIdTrue != null && pTenantIdTrue != ''){
				$scope.callPrice("1"); //回选商家价格
			}else{
				//只有标准才回选
				$scope.callStandardPrice();
				
				
			}
			
			//选择城市时候，设置一下是否配置了直送
			bill.setDirectSending(cityId,cityName);
			//选择城市后，需要判断一下直送跟城市的关系
//			bill.isDirectClick();
			
			return cityName;
		},
		updateReceiver:function(sfReceiver){
			if(sfReceiver==true){
				if($scope.form.goodsDetail.rName==undefined||$scope.form.goodsDetail.rName==""){
					jQuery("#_rName").focus();
					return;
				}
				if($scope.form.orderInfo.sfName==undefined||$scope.form.orderInfo.sfName==""){
					jQuery("#_sfName").focus();
					return;
				}
				if($scope.form.orderInfo.sfTel==undefined||$scope.form.orderInfo.sfTel==""){
					jQuery("#_sfTel").focus();
					return;
				}
				$scope.tempRname = $scope.form.goodsDetail.rName;
				$scope.tempRtel = $scope.form.goodsDetail.rBill;
				//打印转师傅的运单时，要将收货人的手机号屏蔽掉
				$scope.form.goodsDetail.rNameNotPhone=$scope.form.orderInfo.sfName + "-转:" + $scope.form.goodsDetail.rName;
				
				$scope.form.goodsDetail.rName = $scope.form.orderInfo.sfName + "-转:" + $scope.form.goodsDetail.rName + "(" + $scope.form.goodsDetail.rBill +")";
			
				
				
				$scope.form.goodsDetail.rBill = $scope.form.orderInfo.sfTel;
			}
			else
			{
				//这个是原来没有选中，选中后又取消
				if($scope.tempRname!=undefined && $scope.tempRname!=""){
					$scope.form.goodsDetail.rName = $scope.tempRname;
					$scope.form.goodsDetail.rBill = $scope.tempRtel;
					$scope.form.goodsDetail.rNameNotPhone=$scope.tempRname;
				}else{
					//刚开始进来是已经选中，后面取消了
					var index=$scope.form.goodsDetail.rName.indexOf(":");
					var tempStr=$scope.form.goodsDetail.rName.substr(index+1,$scope.form.goodsDetail.rName.length)
					var tempIndex=tempStr.indexOf("(");
					$scope.form.goodsDetail.rName=tempStr.substr(0,tempIndex);
					
					$scope.form.goodsDetail.rBill=tempStr.substr(tempIndex,tempStr.length).replace("(","").replace(")","");
					$scope.form.goodsDetail.rNameNotPhone=$scope.form.goodsDetail.rName;
				}
			}
		},
		countyCreat : function (regionId,value){
			var url="staticDataBO.ajax?cmd=selectDistrict&cityId="+regionId;
			commonService.postUrl(url,"",function(data){
				$scope.countyData=data;
				if(undefined !=value&&value!=0){
					$scope.form.orderInfo.countyId = parseInt(value);
					$scope.streetCreat(value);
				}
        	});
		},
		streetCreat : function (countyId,value){
			if(countyId==-1||countyId==undefined){
				$scope.streetData={};
				$scope.arrivePointSet();
				return;
			}
			$scope.firstCome = $scope.firstCome + 1;
			if(orderId != null && orderId != undefined && orderId !=""){
				if($scope.firstCome !=1 && $scope.firstCome !=2){
					$scope.arrivePointSet();
				}
			}
			else
			{
				$scope.arrivePointSet();
			}
			var url="staticDataBO.ajax?cmd=selectStreet&districtId="+countyId;
			commonService.postUrl(url,"",function(data){
				$scope.streetData=data;
				if(undefined !=value&&value!=0){
					$scope.form.orderInfo.streetId = parseInt(value);
				}
        	});
		},
		keyPressed : function(event,obj,type,id) {
            if(event.which === 40) {
                if (obj.items && ($scope.currentIndex + 1) < obj.items.length) {
                    $scope.currentIndex ++;
                    $scope.$apply();
//                    event.preventDefault;
//                    event.stopPropagation();
                    if($scope.currentIndex>1){
                    	$("#"+id).scrollTop($("#"+id).scrollTop()+34);
                    }
                }
                $scope.$apply();
            } else if(event.which == 38) {
                if ($scope.currentIndex >= 1) {
                    $scope.currentIndex --;
                    $scope.$apply();
//                    event.preventDefault;
//                    event.stopPropagation();
                    if($scope.currentIndex>1){
                        $("#"+id).scrollTop($("#"+id).scrollTop()-34);
                    }
                }

            } else if (event.which == 13) {
//                if (obj.items && $scope.currentIndex >= 0 && $scope.currentIndex < obj.items.length) {
//                	if(type==1){
//                        $scope.selectPcustomer(obj.items[$scope.currentIndex]);
//                	}
//                	else
//                	{
//                		  $scope.selectRcustomer(obj.items[$scope.currentIndex]);
//                	}
//                    $scope.$apply();
////                    event.preventDefault;
////                    event.stopPropagation();
//                } else {
////                    $scope.results = [];
////                    $scope.$apply();
////                    event.preventDefault;
////                    event.stopPropagation();
//                }
            } else if (event.which == 27) {
                $scope.results = [];
                $scope.showDropdown = false;
                $scope.$apply();
            } else if (event.which == 8) {
                $scope.selectedObject = null;
                $scope.$apply();
            }
        },
        initKeyPressed : function() {
        	$scope.currentIndex = 0 ;
        },
		noKeyPress : function() {
			$scope.currentIndex = -1 ;
		},
        cancelSf : function() {
        	$scope.form.orderInfo.sfName = "" ;
        	$scope.form.orderInfo.sfTel = "" ;
        	$scope.form.orderInfo.sfFee = "" ;
        	$scope.form.orderInfo.sfId = "";
        },
		openRemark : function() {
			$scope.showRemark = true;
		},
		querySfClick : function() {
			$scope.querySf = true;
		},
		openSf : function() {
			$scope.showSf = true;
		},
		//选择备注
		selectRemark:function(obj){
//			$scope.form.orderInfo.remarks = obj.notes;
            var remark_check =  document.getElementById("remark_check_"+obj.RId);
            if(remark_check == undefined){
				return false;
			}
			if(remark_check.checked){
				remark_check.checked=false;
			}else{
				remark_check.checked=true;
			}
		},
		doCheckRemark : function() {
			 var items = document.getElementsByName("check-100");
             var ss = "";
             for (var i = 0; i < items.length; i++) {
                 if (items[i].checked) {
           //           console.log(items[i].value);
                      var data = eval("("+items[i].value+")");
                      ss = ss + data.notes+"；";
                 }
             }
             $scope.showRemark = false;
             if(ss != "" ){
            	 $scope.form.orderInfo.remarks = ss.substring(0, ss.length-1);
             }
             //还原框框
             for (var i = 0; i < items.length; i++) {
                 if (items[i].checked) {
                	 items[i].checked = false;
                 }
             }
		},
		querySfInfo:function(){
			var provinceId = $scope.form.orderInfo.provinceId;
			var cityId = $scope.form.orderInfo.cityId;
			var sfQueryValue = undefined;
			var sfQueryType = undefined;
			if($scope.query!=undefined){
				sfQueryValue = $scope.query.sfQueryValue;
				sfQueryType = $scope.query.sfQueryType;
			}
			var sfUserName = "";
			var sfUserAcct = "";
			if(sfQueryType!=undefined&&sfQueryValue!=undefined){
				if(sfQueryType=="1"){
					sfUserName = sfQueryValue;
				}
				if(sfQueryType=="2"){
					sfUserAcct = sfQueryValue;
				}
			}
			//获取安装师傅
			commonService.postUrl("cmSfUserInfoBO.ajax?cmd=queryUserInfoList",{"provinceId":provinceId,"cityId":cityId,"sfUserName":sfUserName,"sfUserAcct":sfUserAcct},function(data){
				if(data != null && data != undefined && data != "" && data.items.length>0){
					$scope.sfQueryList = data;
				}
			});
		},
		querySfInfoOnChange:function(){
			var provinceId = $scope.form.orderInfo.provinceId;
			var cityId = $scope.form.orderInfo.cityId;
			var sfUserName = $scope.form.orderInfo.sfName;
			//获取安装师傅
			commonService.postUrl("cmSfUserInfoBO.ajax?cmd=queryUserInfoList",{"provinceId":provinceId,"cityId":cityId,"sfUserName":sfUserName},function(data){
				if(data != null && data != undefined && data != "" && data.items.length>0){
					$scope.sfList = data;
				}
				else
				{
					$scope.sfList = {};
				}
			});
		},
		changeGetGoodsType:function(obj){
			var getGoodsType1 = $scope.form.orderInfo.getGoodsType1;
			var getGoodsType2 = $scope.form.orderInfo.getGoodsType2;
			if('getGoodsType1'==obj){
				if(getGoodsType1==true){
					$scope.form.orderInfo.getGoodsType2=false;
				}
			}
			if('getGoodsType2'==obj){
				if(getGoodsType2==true){
					$scope.form.orderInfo.getGoodsType1=false;
				}
			}
		},
		setGetGoodsType:function(){
			var getGoodsType1 = $scope.form.orderInfo.getGoodsType1;
			var getGoodsType2 = $scope.form.orderInfo.getGoodsType2;
			if(getGoodsType1==true){
				$scope.form.orderInfo.getGoodsType="1";
			}
			if(getGoodsType2==true){
				$scope.form.orderInfo.getGoodsType="2";
			}
			if(getGoodsType1==false && getGoodsType2==false){
				$scope.form.orderInfo.getGoodsType="3";
			}
		},
		getGetGoodsType:function(){
			var getGoodsType = $scope.form.orderInfo.getGoodsType;
			if(getGoodsType=="1"){
				$scope.form.orderInfo.getGoodsType1=true;
			}
			if(getGoodsType=="2"){
				$scope.form.orderInfo.getGoodsType2=true;
			}
		},
		checkTelphone:function(){
			var billRegexp =/^0?(13[0-9]|15[0-9]|18[0-9]|14[57]|17[03678])[0-9]{8}$/;
			if(!billRegexp.test($scope.form.goodsDetail.rBill)){
					commonService.alert("收货方联系电话不是手机号码，请核对!", function(){
				});
			}
		},
		//拼单查询
		callSpellQueryOrder : function(){
			var rTelephone = $scope.form.goodsDetail.rBill; 
			var billRegexp =/^0?(13[0-9]|15[0-9]|18[0-9]|14[57]|17[03678])[0-9]{8}$/;
			if(!billRegexp.test(rTelephone)){
				//手机格式不正确不查询
				return false;
			}
			if(rTelephone == undefined || rTelephone == null || rTelephone == ''){
				return false;
			}
			
			//修改拼单、运单信息不查询
			if($scope.form.orderInfo.orderId > 0){
				return false;
			}
			var param = {};
			param.consigneeBill = rTelephone;
			var url = "orderInfoBO.ajax?cmd=querySpellOrder";
			commonService.postUrl(url,param,function(data){
				if(data != null && data != undefined && data != "" && data.items.length>0){
					commonService.confirm("该收货人电话在拼单管理已存在了拼单信息",function(){
						
					},
					function(){
						commonService.openTab("120111"+rTelephone,"拼单管理","/ord/ordSpellManage.html?consigneeBill="+rTelephone);
					},"继续开单","前往拼单管理");
				};
			});
		},
		//导入数据处理
		importBill:function(){
			if(imports == "5"){
				var url = "ordImportBillBO.ajax?cmd=getOrdBill";
				commonService.postUrl(url,{"ids":ids},function(data){
					if(data != null && data != undefined && data != ""){
						var ordImportBill=data.ordImport;
						$scope.form.goodsDetail.rName = ordImportBill.consignee;//收货人
						$scope.form.goodsDetail.rBill = ordImportBill.consigneeBill;//收货人电话
						$scope.form.goodsDetail.rTelephone= ordImportBill.consigneeTelephone//备用电话
						$scope.form.orderInfo.address = ordImportBill.address;//详细地址
						$scope.form.orderInfo.provinceId = ordImportBill.provinceId+"";
						$scope.form.orderInfo.provinceName = ordImportBill.destProvinceName;
						$scope.form.orderInfo.cityId = ordImportBill.destCity+"";
						$scope.form.orderInfo.countyId = ordImportBill.countyId+"";
						$scope.form.goodsDetail.goods_0.goodsName = ordImportBill.products;
						$scope.form.goodsDetail.goods_0.packageCounts = ordImportBill.count;
						$scope.form.goodsDetail.goods_0.volume = ordImportBill.volume;
						$scope.form.goodsDetail.goods_0.weight = ordImportBill.weight;
						$scope.form.goodsDetail.goods_0.installCosts = ordImportBill.installCostsString;
						
						$scope.form.orderInfo.remarks = ordImportBill.remarks;
						$scope.form.orderInfo.goodsNumber = ordImportBill.goodsNumber;
						$scope.form.goodsDetail.goods_0.custOrder = ordImportBill.sellerOrderId;
						$scope.form.goodsDetail.goods_0.volumeUnit = ordImportBill.volumeNum;
						$scope.form.goodsDetail.goods_0.weightUnit = ordImportBill.weightNum;
						$timeout(function(){
							$scope.form.ordFee.paymentType = ordImportBill.paymentType+"";
							$scope.form.orderInfo.serviceType = ordImportBill.serviceType+"";
							$scope.form.orderInfo.deliveryType = ordImportBill.deliveryType+"";
						});
						
						$scope.form.goodsDetail.pName = ordImportBill.consignorName;
						$scope.form.goodsDetail.pTelephone = ordImportBill.consignorTelephone;
						$scope.form.orderInfo.isSign = parseInt(ordImportBill.iSigningType) == 1 ? true : false;
						
						$scope.form.goodsDetail.goods_0.freight = ordImportBill.freightString;
						document.getElementById("_freight0").focus();
						//$scope.$apply();
						// 刷新目的地信息
						commonService.postUrl("staticDataBO.ajax?cmd=revAddressInfo", {
							districtId: ordImportBill.countyId,
							cityId: ordImportBill.destCity,
							provinceId: ordImportBill.provinceId
						},function(data){
							if(data != null && data != undefined && data != ""){
								if (undefined != data.provinceId&&0 != data.provinceId) {
									$scope.form.orderInfo.provinceId =  data.provinceId;
									$scope.form.orderInfo.provinceName =  data.provinceName;
								}
								if (undefined != data.cityId&&0 != data.cityId) {
									$scope.form.orderInfo.cityId =  data.cityId;
									jQuery("#cityName_value").val(data.cityName);
									$scope.arrivePointSet();
								}
								if (undefined != data.cityId) {
									$scope.countyCreat($scope.form.orderInfo.cityId,data.districtId);
								}
								
								if (undefined != data.districtId) {
									$scope.streetCreat(data.districtId,data.streetId);
								}
							}
						});
					}
				});
			}
		},
		//获取配载直送的数据
//		getDirectSendingInfo:function(){
//			var url = "staticDataBO.ajax?cmd=getDirectSendingInfo";
//			commonService.postUrl(url,"",function(data){
//				if(data != null && data != undefined && data != "" && data.items.length>0){
//					$scope.directSendingInfo=data.items;
//				}
//			});
//		},
		
		//上传图片
		openUp:function(){
			$scope.orderPic=false;
            $scope.orderUpPic=true;
            if(orderId!=null){
            	var param = {"orderId": orderId};
    			var url = "ordUploadPicBO.ajax?cmd=queryOrderPic";
    			commonService.postUrl(url,param,function(data){
    				if(data!=null && data!=undefined && data!=""){
    						if(data.flowId!=""&&data.flowId!=null&&data.flowId!=undefined){
    							var flowIdArr=data.flowId.split(",");
    							if(flowIdArr.length==5){
    								$scope.orderPic=false;
    							}
    							for (i=0;i<flowIdArr.length ;i++ )   
    						    {   
    						        eval("$rootScope.idenCard"+(i+1)+".initDate("+flowIdArr[i]+")");
    						        eval( "$rootScope.idenCard"+(i+1)+".isUpShow(false)"); 
    						    }   
    						}
    	 	    	}
    			});
            }
		},
		closeUp:function(){
			$scope.orderUpPic=false;

		},
		//传入选择的城市id，如果配置了是需要直送的，默认把直送的勾选上
		setDirectSending:function(cityId,cityName){
			if($scope.directSendingInfo!=undefined && $scope.directSendingInfo!="" && $scope.directSendingInfo.length>0){
				var isShowConfig=true;
				for(var i in $scope.directSendingInfo){
					var data=$scope.directSendingInfo[i];
					if(cityId == data.codeValue){
						document.getElementById("check-26").checked=true;
						$scope.form.orderInfo.isDirect=true;
						isShowConfig=false;
						break;
					}
				}
				if(isShowConfig && $scope.form.orderInfo.isDirect==true){
					//如果配置了直送区域 就会进入这个逻辑
					//20161125 改 南哥物流-开单录入-选择地址时。弹出确认框，提示“请确认到达城市[广州]是否为直送？”，点击是 确认勾选，点击取消不勾选
					var message = "请确认到达城市["+cityName+"]是否为直送？";
					commonService.confirm(message,function(){
						//是
						document.getElementById("check-26").checked=true;
						$scope.form.orderInfo.isDirect= true;	
					},function(){
						//否
						document.getElementById("check-26").checked = false;
						$scope.form.orderInfo.isDirect = false;
					},"确认勾选","取消勾选");
				}
			}
		}

		
	};
	bill.init(); 
}]);
