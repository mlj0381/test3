var batchNum=getQueryString("batchNum");
var signDepartOtherApp = angular.module("signDepartOtherTransitApp", ['commonApp']);
signDepartOtherApp.controller("signDepartOtherTransitCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.vehicleTypeList=[];
	$scope.printTime = new Date();
	var signDepart={
		init:function(){
			setContentHegthDelay();
			this.userData();
			this.bindEvent();
			$scope.list=new Array();
			$scope.orderInfo=new Array();
			this.registerKeyEvent(); //键盘绑定事件
			this.getEndOrg();
			
			$scope.initObject();
			//发货人弹窗
			$scope.gatherValue = 0;
			this.toView(batchNum);
		},
		bindEvent:function(){
			$scope.param=this.param;
			$scope.doQuery=this.doQuery;
		    $scope.form=this.form;
		    $scope.fixNumber=this.fixNumber;
		    //新增查询条件
		    $scope.query=this.query;
		    $scope.selectVehicle=this.selectVehicle;
		    $scope.toView=this.toView;
		    //车辆管理－
		    $scope.initObject = this.initObject;
		    $scope.transferInfo = this.transferInfo;
		    $scope.showToFalse = this.showToFalse;
		    //导出
		    $scope.exportOrd = this.exportOrd;
		    //打印
		    $scope.printTable = this.printTable;
		    $scope.tranSign=this.tranSign;
		    $scope.close=this.close;
		},
		//隐藏div
		showToFalse:function(){
			$timeout(function(){
				$scope.showPcum = false;
				$scope.showRcum = false;
			},200);
		},
		/**中转签收*/
		tranSign:function(){
			var selectDatas =  $scope.diy.getSelectData();
			if(selectDatas.length == 0){
				commonService.alert("请选择一条中转信息!");
				return false;
			}
			var orderId =new Array();
			var tips="";
			var pay="";
			for(var i in selectDatas){
				orderId.push(selectDatas[i].orderId);
			}
			if($scope.gatherShow==true){
				tips="应收";
				if($scope.ordTransferCost.isGet==1){
					pay="已收";
				}else{
					pay="未收";
				}
			}
			if($scope.gatherShow==false){
				tips="应付";
				if($scope.ordTransferCost.isPay==1){
					pay="已付";
				}else{
					pay="未付";
				}
			}
			window.top.showLoad();
			var url="ordSignBO.ajax?cmd=batchSign";
			commonService.confirm("请确认所选订单"+tips+"款"+pay+",是否确认签收？", function(){
				commonService.postUrl(url,{"batchNum":batchNum,"orderId":orderId.join(","),"isGet":$scope.ordTransferCost.isGet,"isPay":$scope.ordTransferCost.isPay},function(data){
					window.top.hideLoad();
					commonService.alert("签收成功!");
					$scope.toView(batchNum);
				});
			},function(response){
				window.top.hideLoad();
				commonService.alert(response.message);
			});
		},
		//设置发货方
		selectVehicle:function(obj){
			if(obj != null){
				signDepart.form.driverName = obj.name;
				signDepart.form.driverBill = obj.bill;
				signDepart.form.vehicleId = obj.vehicleId;
				signDepart.form.vehicleTypeName = obj.vehicleTypeName;
				$scope.form.vehicleType= obj.vehicleType+"";
				signDepart.form.weight= obj.actualWeight;
				signDepart.form.volume= obj.actualVolume;
				signDepart.form.businessType=obj.businessType;
				signDepart.form.plateNumber = obj.plateNumber;
			}
			signDepart.showToFalse();
		},
		selectCheck:function(id){
			if(document.getElementById(id).checked==true){
				document.getElementById(id).checked=false;
			}else{
				document.getElementById(id).checked=true;
			}
		},
		transferInfo:{
			"name":"",
			"csPhones":"",
			"linkPhone":"",
			"address":"",
			"orgId":""
		},
		initObject:function(){
			//初始化数据
			$scope.ordTransferCost = {
					"stevedoringPayState":2,
					"payState":2,
					//到付
					"freightCollect":0,
					//应付
					"collectingMoney":0,
					//中转费
					"transferValue":0,
					//票数
					"transferNum":0,
					"actualWeight":0,
					"actualVolume":0,
			};
			$scope.transferInfo = {
				"name":"",
				"csPhones":"",
				"linkPhone":"",
				"departmentAddress":"",
				"orgId":""
			};
			signDepart.form.driverName = "";
			signDepart.form.driverBill = "";
			signDepart.form.vehicleId = "";
			signDepart.form.vehicleTypeName = "";
			$scope.form.vehicleType= -1+"";
			signDepart.form.weight= "";
			signDepart.form.volume= "";
			signDepart.form.businessType="-1";
			signDepart.form.plateNumber = "";
			//document.getElementById("isGet").checked=false;
			//document.getElementById("isPay").checked=false;
			document.getElementById("freight").checked=false;
			document.getElementById("stevedoring").checked=false;
			
			
		},
		//初始化 到付，代收货款，中转费用
		initFeeInfo:function(){
			var view = getQueryString("view");
			if( view==1 || view==2){
    			$scope.freightCollect=$scope.ordTransferCost.freightCollect;
    			$scope.collectingMoney=$scope.ordTransferCost.collectingMoney;
    			$scope.transferMoney=$scope.ordTransferCost.transferValue;
    			var value = 0;
        		if($scope.freightCollect != null){
        			value = value + Number($scope.freightCollect);
        		}
        		if($scope.collectingMoney != null){
        			value = value + Number($scope.collectingMoney);
        		}
        		value = value - $scope.transferMoney;
        		if(value >=0){
        			$scope.gatherShow = true;
        		}else{
        			$scope.gatherShow = false;
        		}
        		$scope.gatherValueNumber = value.toFixed(2);
        		$scope.gatherValue = Math.abs(value.toFixed(2));
    		}
		},
		params:{
			totalPage:1,
			vehicleState:1
		},
		query:{
			orderState:2
		},
		selectData:{
			trackingNums:"",
			gtWeight:"",
			gtVolume:"",
			gtCount:"",
			geTrackingNums:"",
			geWeight:"",
			geVolume:"",
			geCount:"",
		},
		
		form:{
			batchNum:'',
			freight:'',
			descOrgId:'',
			plateNumber:'',
			vehicleId:'',
			driverBill:'',
			driverName:'',
			vehicleType:'',
			weight:'',
			volume:'',
			vehicleType:'',
			vehicleTypeName:'',
			currOrgId:'',
			currOrgName:''
			},
		userData:function(){
			$scope.currOrgId=userInfo.orgId;
			$scope.currOrgName=userInfo.orgName;
			commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{"codeType":"VEHICLE_TYPE"},function(data){
				$scope.vehicleTypeList=data.items;
			});
			commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{"codeType":"BUSINESS_TYPE"},function(data){
				$scope.businessTypeList=data.items;
			});
			commonService.postUrl("organizationBO.ajax?cmd=getOrgInfo","orgId="+userInfo.orgId,function(data){
				$scope.userInfoOrg = data.organization;
			});
			$scope.twoOrg = [{"orgId":userInfo.orgId+"","orgName":userInfo.orgName}];
		},
		getEndOrg:function(){
//			commonService.postUrl("routeBO.ajax?cmd=queryRoateTowards","orgType=0",function(data){
//				//成功执行
//				if(data!=null && data!=undefined && data!=""){
//					$scope.desOrgData=data.items;
//					$scope.desOrgData.unshift({endOrgId:-1,endOrgName:'所有'});
//				}
//			});
		},
		//键盘绑定事
		registerKeyEvent :function(){
			this.registerKeyEventForDomsGetFocus('keydown', 'return', "trackingNumOneEnter", function(evt){
				if($scope.trackingNumOne != undefined && $scope.trackingNumOne != null && $scope.trackingNumOne != ''){
					signDepart.query.trackingNum = $scope.trackingNumOne;
					signDepart.doQuery(); 
				}else{
					signDepart.query.trackingNum="";
					signDepart.doQuery(); 
				}
				return false;
			});
		},
		//键盘绑定事件 (为某个元素绑定)
		registerKeyEventForDomsGetFocus: function(keyEvent, key, domEleIds, callback) { // 注册键盘事件
			jQuery("#"+domEleIds).bind('keydown', key, function(evt){
				if (undefined != callback)
					callback(evt);
                return false;
            });
		},
		fixNumber:function (){
			$scope.form.freight =  $scope.form.freight.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
			$scope.form.freight =  $scope.form.freight.replace(/^\./g,"");  //验证第一个字符是数字而不是. 
			$scope.form.freight =  $scope.form.freight.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.   
			$scope.form.freight =  $scope.form.freight.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
		},
		//隐藏div
		showToFalse:function(){
			$timeout(function(){
				$scope.showPcum = false;
				$scope.showRcum = false;
			},200);
		},
		/**
		 *拷贝数组
		 * @param obj
		 * @returns
		 */
		deepCopy:function (obj) {
		    if (typeof obj != "object") {
		        return obj;
		    }
		    var valueList=new Array();
		    var result={};
		    for (var key in obj) {
		        if (undefined != obj[key] && null != obj[key] && typeof obj[key] == "object") {
		        	result[key] = this.deepCopy(obj[key]);
		        } else {
		        	result[key] = obj[key];
		        }
		    }
		
		    return result;
		},
		calculate:function(){
			//这是配在统计
			$scope.freightCollect = 0;//到付
			$scope.collectingMoney = 0;//代收货款
			$scope.actualVolume = 0;//体积
			$scope.actualWeight = 0;//重量
			$scope.transferMoney = 0;//中转费
			$scope.totalCount = 0;//件数
			$scope.totalAllFee = 0;//费用合计
			$scope.totalAllDiscount = 0; //回扣
		
			for(var i=0;i<$scope.list.length;i++){
				if($scope.list[i].orderState!=5){
					$scope.actualVolume = $scope.actualVolume+$scope.list[i].volume;
					$scope.actualWeight = $scope.actualWeight+$scope.list[i].weight;
					
					//如果订单是有配安任务的，代收，到付的钱不是给到承运商
					//11 是服务方式为“无”的数据
					
					//if($scope.list[i].taskId==null){
						$scope.freightCollect = $scope.freightCollect+($scope.list[i].freightCollect/100);
						$scope.collectingMoney = $scope.collectingMoney+($scope.list[i].collectingMoney/100);
				//	}
					if($scope.list[i].transferValue != null && $scope.list[i].transferValue!=undefined){
						$scope.transferMoney = $scope.transferMoney+Number($scope.list[i].transferValue);
					}
					$scope.totalCount = $scope.totalCount + $scope.list[i].count;
					if($scope.list[i].totalFeeString != null && $scope.list[i].totalFeeString != undefined){
						$scope.totalAllFee = $scope.totalAllFee + Number($scope.list[i].totalFeeString)/100;
					}
					if($scope.list[i].discountString != null && $scope.list[i].discountString != undefined){
						$scope.totalAllDiscount = $scope.totalAllDiscount + Number($scope.list[i].discountString)/100;
					}
			   }
			}
			if($scope.totalAllFee != 0){
				$scope.totalAllFee = $scope.totalAllFee.toFixed(2);
			}
			if($scope.totalAllDiscount !=0 ){
				$scope.totalAllDiscount = $scope.totalAllDiscount.toFixed(2);
			}
			if($scope.actualVolume == 0){
			}else{
				$scope.actualVolume = $scope.actualVolume.toFixed(2);
			}
			if($scope.actualWeight == 0){
			}else{
				$scope.actualWeight = $scope.actualWeight.toFixed(0);
			}
			if($scope.order.data!=null && $scope.order.data!=undefined && $scope.order.data!=""){
				var orderData = $scope.order.data.items;
				$scope.freightCollectWait = 0;
				$scope.collectingMoneyWait = 0;
				$scope.actualVolumeWait = 0;
				$scope.actualWeightWait = 0;
				for(var i=0;i<orderData.length;i++){
					if(orderData[i].orderState != 5){
						$scope.actualVolumeWait = $scope.actualVolumeWait+orderData[i].volume;
						$scope.actualWeightWait = $scope.actualWeightWait+orderData[i].weight;
						$scope.freightCollectWait = $scope.freightCollectWait+(orderData[i].freightCollect/100);
						$scope.collectingMoneyWait = $scope.collectingMoneyWait+(orderData[i].collectingMoney/100);
				   };
				};
				if($scope.freightCollectWait != 0){
					$scope.freightCollectWait = $scope.freightCollectWait.toFixed(2);
				}
				if($scope.collectingMoneyWait != 0){
					$scope.collectingMoneyWait = $scope.collectingMoneyWait.toFixed(2);
				}
				if($scope.actualVolumeWait != 0){
					$scope.actualVolumeWait = $scope.actualVolumeWait.toFixed(2);
				}
				if($scope.actualWeightWait != 0){
					$scope.actualWeightWait = $scope.actualWeightWait.toFixed(1);
				}
			}
			var value = 0;
    		if($scope.freightCollect != null){
    			value = value + Number($scope.freightCollect);
    		}
    		if($scope.collectingMoney != null){
    			value = value + Number($scope.collectingMoney);
    		}
    		value = value - $scope.transferMoney;
    		if(value >=0){
    			$scope.gatherShow = true;
    		}else{
    			$scope.gatherShow = false;
    		}
    		$scope.gatherValueNumber = value.toFixed(2);
    		$scope.gatherValue = Math.abs(value.toFixed(2));
    		//查看页面的需要设置一下
    		
		},
		updateTransferValue:function(){
			$scope.diy.setTransferValue("transferValue");
		},
		/**订单列表查询*/
		doQuery : function(){
				signDepart.query._ALLEXPORT=1;
				signDepart.query.page=1;
				signDepart.query.type = 2;
//				if($scope.order !=null && $scope.order !=undefined && $scope.order!=""){
//					$scope.order.total = 0;
//					$scope.list.length = 0;
//				}
				
				$scope.query.beginDate = $("#beginDate").val();
				$scope.query.endDate = $("#endDate").val();
				
				$scope.query.beginDateArrive = $("#beginDateArrive").val();
				$scope.query.endDateArrive = $("#endDateArrive").val();
				
				$scope.query.proviceId=$scope.des.chooseCityId;//省份
				$scope.query.cityId=$scope.des.chooseRegionId;//市
				$scope.query.countyId=$scope.des.chooseCountyId;//区域
				var url = "transferBO.ajax?cmd=queryOther";
				commonService.postUrl(url,signDepart.query,function(data){
					$scope.order = {};
					$scope.order.data = data;
					$scope.orderInfo= signDepart.deepCopy(data.items);
					$scope.orderInfo.length=data.items.length;
					
					for(var i=0;i<$scope.list.length;i++){
						$scope.orderInfo[i].transferValue = 0;
						$scope.remove($scope.list[i]);	
					}
					$scope.matchListTable.loadData($scope.order.data.items);
					
					signDepart.calculate(); //计算待发货统计
					setContentHegthDelay();
						$scope.selectData.trackingNums="";
						$scope.selectData.gtWeight="";
						$scope.selectData.gtVolume="";
						$scope.selectData.gtCount="";
						$scope.selectData.geTrackingNums="";
						$scope.selectData.geWeight="";
						$scope.selectData.geVolume="";
						$scope.selectData.geCount="";
				});
		},
		printTable:function(){
			printTableInfo("printDiv", "联运会_中转详情");
		},
		exportOrd:function(){
			if($scope.isTrue){
				return false;
			}
			var batchNum = $scope.batchNum;
			var url = "transferBO.ajax?cmd=getByBacthInfoExport";
			var toUrl = signUrl(url+"&batchNum="+batchNum);
			var iframe = document.createElement("iframe");
		    iframe.id = "frameDownloading";
		    iframe.src = toUrl;
		    iframe.style.display = "none";
		    document.body.appendChild(iframe);
		    
		    var selectExport = document.getElementById("exportId");
			$scope.isTrue = true;
			selectExport.innerHTML = "导出中。";
			//导出倒计时
			$timeout(function() {
				$scope.isTrue = false;
			    selectExport.innerHTML = "导出";
			},3000);
		},
		close:function(){
    		commonService.closeToParentTab(true);
    	},
    	toView:function(batchNum){
    		var that = this;
    		$scope.batchNum = batchNum;
    		var view = getQueryString("view");
    		if(batchNum!=null&&batchNum!=undefined&& batchNum!=''){
    			//直接显示详情第二页
    			$scope.nextShow = false;
    			var queryString={
    					"batchNum":batchNum
    			}
    			var url="transferBO.ajax?cmd=getByBacthInfoOther";
    			commonService.postUrl(url,queryString,function(data){
    				setContentHegthDelay();
    				if(view == 1){
    					//查看
    					$scope.view = true;
    				}else{
    					//修改
    					$scope.view = false;
    					$scope.nextButton = false;
    				}
    				if(data.organization != null && data.organization != undefined && data.organization !=""){
    					var obj = data.organization;
    					$scope.transferInfo.orgName = obj.orgName;
    					$scope.transferInfo.name = obj.orgName;
    					$scope.transferInfo.linkMan = obj.orgPrincipal;
    					$scope.transferInfo.provinceName = obj.provinceName;
    					$scope.transferInfo.regionName = obj.regionName;
    					$scope.transferInfo.countyName = obj.countyName;
    					$scope.transferInfo.orgId = obj.orgId;
						if(obj.orgType==5){
							//--- 测试
							$scope.tabShow = true;
						}else{
							$scope.tabShow = false;
						}
						$scope.twoOrg = [{"orgId":userInfo.orgId+"","orgName":userInfo.orgName}];
						$scope.twoOrg.push({"orgId":$scope.transferInfo.orgId+"","orgName":$scope.transferInfo.orgName});
    				}
    				if(data.ordTransferCost != null && data.ordTransferCost != undefined && data.ordTransferCost != ""){
    					$scope.ordTransferCost = data.ordTransferCost;
    					signDepart.initFeeInfo();
//    					if(data.ordTransferCost.isGet == 0){
//    						document.getElementById("isGet").checked=true;
//    					}else{
//    						document.getElementById("isGet").checked=false;
//    					}
//    					if(data.ordTransferCost.isPay == 0){
//    						document.getElementById("isPay").checked=true;
//    					}else{
//    						document.getElementById("isPay").checked=false;
//    					}
    					if(data.ordTransferCost.payState == 1){
    						document.getElementById("freight").checked=true;
    					}else{
    						document.getElementById("freight").checked=false;
    					}
    					if(data.ordTransferCost.stevedoringPayState == 1){
    						document.getElementById("stevedoring").checked=true;
    					}else{
    						document.getElementById("stevedoring").checked=false;
    					}
    					if(data.ordTransferCost.stevedoringPayDot != null){
    						$scope.ordTransferCost.stevedoringPayDot = data.ordTransferCost.stevedoringPayDot+"" ;
    					}
    					if(data.ordTransferCost.freightPayDot != null){
    						$scope.ordTransferCost.freightPayDot = data.ordTransferCost.freightPayDot+"";
    					}
    					$scope.transferInfo.linkPhone = data.ordTransferCost.linkPhone;
    					$scope.transferInfo.departmentAddress = data.ordTransferCost.departmentAddress;
    					//提货电话
    					$scope.transferInfo.csPhones = data.ordTransferCost.csPhones;
    				}
    				$scope.selectVehicle(data.vehicleInfo);
    				if(data.orderInfoList != null && data.orderInfoList != undefined && data.orderInfoList != ""){
    					$scope.orderInfoList = new Array();
    					$scope.order = {};
    					$scope.order.data = {
    							"items": new Array(),
    					};
    					var list = data.orderInfoList;
    					var waitSignlist=new Array();
    					var signList=new Array();
    					for(var index in list){
    						var obj=list[index];
    						if(obj.orderState==5||(obj.sfUserId!=null&&obj.sfUserId!=undefined&&obj.sfUserId!="")){
    							signList.push(obj);
    						}else if(obj.orderState==7){
    							waitSignlist.push(obj);
    						}
    					}
    					$scope.list = list;
    					$scope.diy.loadData(waitSignlist);
    					$scope.page.loadData(signList);
    					setContentHegthDelay();
    					if(view!=1 && view!=2){
    						signDepart.calculate();
    					}
	    				
    				}
    			});
    		}else{
    			//初始化不执行写
//    			$scope.doQuery();
    		}
    	},
    	sumOfTransfer:function(){
    		signDepart.calculate();
    	}
		
	};
	
	signDepart.init();
}]);
