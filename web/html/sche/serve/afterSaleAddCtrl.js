var afterSaleAddApp = angular.module("afterSaleAddApp", ['commonApp']);
afterSaleAddApp.controller("afterSaleAddCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var excRegister={
		init:function(){
			this.bindEvent();
			this.getUserInfo();
			$scope.isComit=true;
			$scope.isCheckTrackingNum = false;
			var salesId = getQueryString("salesId");
			var orderId=getQueryString("orderId");
			$scope.isShow=getQueryString("isShow");
			$scope.isView=getQueryString("isView");
			$scope.isSales = getQueryString("isSales");
			if(orderId!=null&&orderId!=undefined&&orderId!=''){
				$scope.query.orderId=orderId;
				$scope.toView(orderId);
			}
			if(salesId!=null&&salesId!=undefined&&salesId!=''){
				$scope.queryOrdSales(salesId);
			}
		},
		bindEvent:function(){
			$scope.params = this.params;
			$scope.query = this.query;
			$scope.close=this.close;
			$scope.toView=this.toView;
			$scope.doSave=this.doSave;
			$scope.hideTask=this.hideTask;//生成维修任务
			$scope.showTask=this.showTask;//取消维修任务
			$scope.doQuery=this.doQuery;
			$scope.selectSf=this.selectSf;
			$scope.form=this.form;
			$scope.doQueryOrd=this.doQueryOrd;
			$scope.closeView=this.closeView;
			$scope.selectOrder=this.selectOrder;
			$scope.clearModel=this.clearModel;
			$scope.querySf=this.querySf;
			$scope.user=this.user;
			$scope.clear=this.clear;
			//加载省
			$scope.selectProvince=this.selectProvince;
			//加载市
			$scope.selectCity=this.selectCity;
			$scope.queryMaster=this.queryMaster;
			$scope.closeMaster=this.closeMaster;
			$scope.selectMaster=this.selectMaster;
			$scope.querySfOrService=this.querySfOrService;
			$scope.userData=this.userData;
			$scope.userTypeInfo=this.userTypeInfo;
			$scope.getUserInfo=this.getUserInfo;
			$scope.userTypeData=this.userTypeData;
			$scope.showOrHideGoodsNameSelect = this.showOrHideGoodsNameSelect;//承担方
			$scope.selectGoodsName = this.selectGoodsName;
			$scope.saleOrderInfo = this.saleOrderInfo;//售后登记运单详情信息
			$scope.queryOrdSales = this.queryOrdSales;
			$scope.checkTrackingNumOrdSale = this.checkTrackingNumOrdSale;//检验运单号是否存在售后信息
		},
		userData:{
			
		},
		userTypeData:{
			
		},
		user:{
			
		},
		params:{
		},
		form:{
		},
		clear:function(){
			$scope.user.name = "";
			$scope.user.linkMan = "";
			$scope.user.linkPhone = "";
			$scope.user.busiNotes = "";
		},
		query:{
			provinceId:-1,
		},
		/***用户数据*/
		getUserInfo:function(){
			$scope.userData=userInfo;
			if(userInfo.userType==6){
				$scope.isHide=true;
			}else{
				$scope.isHide=false;
			}
			$scope.userTypeInfo();
		},
	/**用户类型*/
		userTypeInfo:function(){
			$scope.userTypeData=userType;
		},

		toView:function(orderId){
			var param={"orderId":orderId,"trackingNum":$scope.query.trackingNum};
			var url="scheTaskBO.ajax?cmd=queryWayBillDetail";
			commonService.postUrl(url,param,function(data){
				$scope.showData={};
				$scope.showData=data;
				$scope.query.trackingNum=data.ordOrderInfo.trackingNum;
				$scope.params.provinceId=data.ordOrderInfo.destProvince;
				$scope.params.cityId=data.ordOrderInfo.destCity;
				if($scope.userData.orgId==data.ordOrderInfo.orgId){
					$scope.isHide=true;
				}
				$scope.query.trackingNum=data.ordOrderInfo.trackingNum;
				$scope.query.inputOrgId=data.ordOrderInfo.orgId;
				$scope.query.orderId=data.ordOrderInfo.orderId;
				setContentHegthDelay();
			});
		},
		close:function(){
	    		commonService.closeToParentTab(true);
		},
		hideTask:function(flag){
			if(flag==1){
				$scope.isTaskShowRepair=false;
				$scope.query.isRepair=0;
			}else{
				$scope.query.isExc=0;
				$scope.isTaskShowExc=false;
				$scope.query.excSfUserId="";
			}
		},
		showTask:function(flag){
			if(flag==1){
				$scope.isTaskShowRepair=true;
				$scope.query.isRepair=1;
				$scope.query.repairFee="";
				$scope.query.sfPhone="";
				$scope.query.sfName="";
			}else{
				$scope.isTaskShowExc=true;
				$scope.query.isExc=1;
				$scope.query.excRepairFee="";
				$scope.query.excSfPhone="";
				$scope.query.excSfUserId="";
				$scope.query.excSfName="";
			}
			setContentHegthDelay();
		},
		doSave:function(){
			$scope.isComit = true;
			if(!$scope.isComit){
				commonService.alert("正在提交,请不要重复提交");
				return;
			}
			if($scope.query.trackingNum==null||$scope.query.trackingNum==''||$scope.query.trackingNum==undefined){
				commonService.alert("运单号不能为空!");
				return false;
			}
			if($scope.query.sfPhone!=null&&$scope.query.sfPhone!=''&&$scope.query.sfPhone!=undefined){
				if($scope.query.isRepair==1){
					if($scope.query.sfUserId==null||$scope.query.sfUserId==''||$scope.query.sfUserId==undefined){
						commonService.alert("师傅不存在，请重新选择师傅!");
						return false;
					}
				}
			}
			var flowId="";
			if($scope.exceImage1.get().flowId!=null&&$scope.exceImage1.get().flowId!=undefined&&$scope.exceImage1.get().flowId!=''){
				flowId=$scope.exceImage1.get().flowId;
			}
			if($scope.exceImage2.get().flowId!=null&&$scope.exceImage2.get().flowId!=undefined&&$scope.exceImage2.get().flowId!=''){
				if(flowId!=''){
					flowId+=","+$scope.exceImage2.get().flowId;
				}else{
					flowId+=$scope.exceImage2.get().flowId;
				}
			}
			if($scope.exceImage3.get().flowId!=null&&$scope.exceImage3.get().flowId!=undefined&&$scope.exceImage3.get().flowId!=''){
				if(flowId!=''){
					flowId+=","+$scope.exceImage3.get().flowId;
				}else{
					flowId+=$scope.exceImage3.get().flowId;
				}
			}
			if($scope.exceImage4.get().flowId!=null&&$scope.exceImage4.get().flowId!=undefined&&$scope.exceImage4.get().flowId!=''){
				if(flowId!=''){
					flowId+=","+$scope.exceImage4.get().flowId;
				}else{
					flowId+=$scope.exceImage4.get().flowId;
				}
			}
			if($scope.exceImage5.get().flowId!=null&&$scope.exceImage5.get().flowId!=undefined&&$scope.exceImage5.get().flowId!=''){
				if(flowId!=''){
					flowId+=","+$scope.exceImage5.get().flowId;
				}else{
					flowId+=$scope.exceImage5.get().flowId;
				}
			}
			//-------承担方判断----------------
			if($scope.query.bearPartyOneName  != null  && $scope.query.bearPartyOneName  != "" && $scope.query.bearPartyOneName  != undefined){
				if($scope.query.bearPartyOneMoney == null || $scope.query.bearPartyOneMoney == ''  || $scope.query.bearPartyOneMoney == undefined){
					commonService.alert("请输入承担方1金额!");
					return;	
				}
				if(parseFloat($scope.query.bearPartyOneMoney) < 0){
					commonService.alert("承担方1金额，不能为负数!");
					return;	
				}
			}
			if($scope.query.bearPartyTwoName  != null && $scope.query.bearPartyTwoName != "" && $scope.query.bearPartyTwoName  != undefined){
				if($scope.query.bearPartyTwoMoney == null || $scope.query.bearPartyTwoMoney == '' || $scope.query.bearPartyTwoMoney == undefined){
					commonService.alert("请输入承担方2金额!");
					return;	
				}
				if(parseFloat($scope.query.bearPartyTwoMoney) < 0){
					commonService.alert("承担方2金额，不能为负数!");
					return;	
				}
			}
			if($scope.query.bearPartyThreeName != null  && $scope.query.bearPartyThreeName  != "" && $scope.query.bearPartyThreeName  != undefined){
				if($scope.query.bearPartyThreeMoney == null || $scope.query.bearPartyThreeMoney == '' || $scope.query.bearPartyThreeMoney == undefined){
					commonService.alert("请输入承担方3金额!");
					return;	
				}
				if(parseFloat($scope.query.bearPartyThreeMoney) < 0){
					commonService.alert("承担方3金额，不能为负数!");
					return;	
				}
			}
			if($scope.query.bearPartyOneMoney != null && parseFloat($scope.query.bearPartyOneMoney) > 0 && $scope.query.bearPartyOneMoney != "" && $scope.query.bearPartyOneMoney != undefined){
				if($scope.query.bearPartyOneName  == null || $scope.query.bearPartyOneId == '' ||  $scope.query.bearPartyOneName  == undefined){
					commonService.alert("请输入承担方1!");
					return;	
				}
				if($scope.query.bearPartyOneName == $scope.query.bearPartyTwoName 
						|| $scope.query.bearPartyOneName == $scope.query.bearPartyThreeName ){
					commonService.alert("已有该承担方，不能重复输入承担方信息");
					return;	
				}
			}
			if($scope.query.bearPartyTwoMoney != null && parseFloat($scope.query.bearPartyTwoMoney) > 0 && $scope.query.bearPartyTwoMoney != "" && $scope.query.bearPartyTwoMoney != undefined){
				if($scope.query.bearPartyTwoName  == null || $scope.query.bearPartyTwoName  == ''  || $scope.query.bearPartyTwoName  == undefined){
					commonService.alert("请输入承担方2!");
					return;	
				}
				if($scope.query.bearPartyTwoName == $scope.query.bearPartyOneName 
						|| $scope.query.bearPartyTwoName == $scope.query.bearPartyThreeName ){
					commonService.alert("已有该承担方，不能重复输入承担方信息");
					return;	
				}
			}
			if($scope.query.bearPartyThreeMoney != null && parseFloat($scope.query.bearPartyThreeMoney) > 0 && $scope.query.bearPartyThreeMoney != "" && $scope.query.bearPartyThreeMoney != undefined){
				if($scope.query.bearPartyThreeName == null || $scope.query.bearPartyThreeName  == ''  || $scope.query.bearPartyThreeName  == undefined){
					commonService.alert("请输入承担方3!");
					return;	
				}
				if($scope.query.bearPartyThreeName == $scope.query.bearPartyOneName 
						|| $scope.query.bearPartyThreeName == $scope.query.bearPartyTwoName ){
					commonService.alert("已有该承担方，不能重复输入承担方信息");
					return;	
				}
			}
			if($scope.query.bearPartyOneMoney != null && isNaN($scope.query.bearPartyOneMoney) && $scope.query.bearPartyOneMoney != undefined){
				commonService.alert("请输入正确的承担方1金额!");
				return;
			}
			if($scope.query.bearPartyTwoMoney != null && isNaN($scope.query.bearPartyTwoMoney) && $scope.query.bearPartyTwoMoney != undefined){
				commonService.alert("请输入正确的承担方2金额!");
				return;
			}
			if($scope.query.bearPartyThreeMoney != null && isNaN($scope.query.bearPartyThreeMoney)&& $scope.query.bearPartyThreeMoney != undefined){
				commonService.alert("请输入正确的承担方3金额!");
				return;
			}
			
			//-------承担方判断end----------------
			if($scope.query.trackDate == null || $scope.query.trackDate == '' || $scope.query.trackDate == undefined){
				commonService.alert("请输入下次跟踪时间!");
			}
			$scope.isComit = false;
			$scope.query.flowId=flowId;
			$scope.query.trackingNum=$scope.query.trackingNum;
			var param=$scope.query;
			var url = "ordSaleTrackingBO.ajax?cmd=doSaveSales";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("保存成功!");
					if($scope.isView == null ||$scope.isView == undefined || $scope.isView == "" || $scope.isView != 1 ){
						$scope.query={};
						$scope.exceImage1.clean();
						$scope.exceImage2.clean();
						$scope.exceImage3.clean();
						$scope.exceImage4.clean();
						$scope.exceImage5.clean();
						$scope.isComit = true;
						$scope.query.excState=1;
						$scope.isTaskShowRepair=false;
						$scope.query.isRepair=0;
						$scope.query.repairFee="";
						$scope.query.sfPhone="";
						$scope.query.sfName="";
						$scope.isTaskShowExc=false;
						$scope.query.isExc=0;
						$scope.query.excRepairFee="";
						$scope.query.excSfPhone="";
						$scope.query.excSfUserId="";
						$scope.query.excSfName="";
						$scope.query.salesState = "1";
						$scope.query.salesType = "1";
						$scope.ordSales = null;
						$scope.fixSales = null;
						$scope.ordSaleOrderInfoData = null;
						setContentHegthDelay();
					}else{
						$scope.saleOrderInfo($scope.query.trackingNum);
					}
	 	    	}
			},function(data){
				$scope.isComit = true;
				commonService.alert(data.message);
				return;
			});
		},
		doQuery:function(){
			$scope.isSfSelect=true;
			$scope.isAlertMsgIsShow =true
			$timeout(function(){
				$scope.page.load({
							url:'cmSfUserInfoBO.ajax?cmd=queryUserInfo',
							params:$scope.params
						});
			},500);
		},
		selectSf:function(){
			var data=$scope.page.getSelectData();
			if(data==null||data==undefined||data.length==0){
				commonService.alert("请至少选择一条师傅信息!");
				return false;
			}
			if($scope.flag==1){
				$scope.query.sfPhone=data[0].phone;
				$scope.query.sfUserId=data[0].sfUserId;
				$scope.query.userType=1;
				$scope.query.sfName=data[0].name;
			}else{
				$scope.query.excSfPhone=data[0].phone;
				$scope.query.excSfUserId=data[0].sfUserId;
				$scope.query.excSfName=data[0].name;
			}
			$scope.isSfSelect = false;
			$scope.isAlertMsgIsShow = false;
		},
		//--------查询运单
		doQueryOrd:function(){
			$scope.isCheckTrackingNum = true;
			$scope.isAlertMsgIsShow=true;
			var url = "exceptionInfoBO.ajax?cmd=queryOrderInfoOfExc";
			$scope.form.page=1;
			$scope.form.signState=-1;
			$scope.form.count = 10;
			$timeout(function(){
				$scope.orderInfo.load({
							url:url,
							params:$scope.form
						});
			},500);
		},
		//----------
		selectOrder:function(){
			$scope.isCheckTrackingNum = false;
			$scope.isAlertMsgIsShow = false;
			var data=$scope.orderInfo.getSelectData();
			if(data==null||data==undefined||data.length==0){
				commonService.alert("请至少选择一条运单信息!");
				return false;
			}
			$scope.toView(data[0].orderId);
			$scope.saleOrderInfo(data[0].trackingNum);	
			$scope.checkTrackingNumOrdSale(data[0].trackingNum);
			$scope.form = {};
		},
		clearModel : function(){
		     $scope.form = {};
		},
		closeView : function(){
			$scope.isCheckTrackingNum = false;
			$scope.isAlertMsgIsShow=false;
	         $scope.clearModel();
		},
		selectProvince:function(){
			var param = {"isAll":"Y"};
			var url = "staticDataBO.ajax?cmd=selectProvince";
			commonService.postUrl(url,param,function(data){
				// 成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.provinceData=data;
					$scope.user.provinceId=data.items[0].id;
	 	    	}
			});
		},
		selectCity:function(provinceId){
			if(parseInt(provinceId)>0){
				var param = {"isAll":"Y","provinceId":provinceId};
				var url = "staticDataBO.ajax?cmd=selectCity";
				commonService.postUrl(url,param,function(data){
					// 成功执行
					if(data!=null && data!=undefined && data!=""){
						$scope.cityData=data;
						$scope.user.regionId=data.items[0].id;
		 	    	}
				});
			}
		},
		//------查找师傅-------sta----------------------------------------------------
		querySf:function(flag){
			if(userInfo.userType==6){
				if($scope.query.sfPhone!=null&&$scope.query.sfPhone!=undefined&&$scope.query.sfPhone!=""){
					var param={"phone":$scope.query.sfPhone};
					var url="exceptionInfoBO.ajax?cmd=queryMatchSf";
					commonService.postUrl(url,param,function(data){
						if(data!=null&&data!=undefined&&data!=''){
							if(flag==1){
								$scope.query.sfPhone=data.phone;
								$scope.query.sfUserId=data.sfUserId;
								$scope.query.sfName=data.name;
								$scope.query.userType=1;
							}else{
								$scope.query.excSfPhone=data[0].phone;
								$scope.query.excSfUserId=data[0].sfUserId;
								$scope.query.excSfName=data[0].name;
							}
						}else{
							if(flag==1){
								$scope.query.sfUserId='';
								$scope.query.sfName='';
							}else{
								$scope.query.excSfUserId="";
								$scope.query.excSfName="";
							}
						}
					});
				}
			}
		},
		queryMaster:function(){
			$scope.isCheckMaster = true;
			$scope.isAlertMsgIsShow = true;
			excRegister.selectProvince();
			var url = "organizationBO.ajax?cmd=doQuerySFPartnersExc";
			$timeout(function(){
				$scope.masterInfo.load({
							url:url,
							params:$scope.user,
						});
			},500);
		},
		closeMaster:function(){
			$scope.isCheckMaster = false;
			$scope.isAlertMsgIsShow = false;
	         $scope.clear();
		},
		selectMaster:function(){
			var data=$scope.masterInfo.getSelectData();
			if(data==null||data==undefined||data.length==0){
				commonService.alert("请至少选择一条合作商信息!");
				return false;
			}
			$scope.query.sfPhone=data[0].linkPhone;
			$scope.query.sfUserId=data[0].tenantId;
			$scope.query.sfName=data[0].name;
			$scope.query.userType=2;
			$scope.isCheckMaster = false;
			$scope.isAlertMsgIsShow = false;
		},
		querySfOrService:function(flag){
			if(userInfo.userType==6){
				$scope.doQuery();
			}else{
				$scope.params.sfUserName="";
				$scope.params.sfUserAcct="";
				$scope.queryMaster();
			}
			$scope.flag=flag;
		},
		//------查找师傅--------end---------------------------------------------------
		
		//-------承担方控制----------------------------------
		selectGoodsName: function(number, obj) {
			if (number == 0) {
				$scope.query.bearPartyOneName = obj.name;
				$scope.query.bearPartyOneId = obj.id;
				$scope.query.bearPartyOneType = obj.type;
				jQuery("#_packageCounts0").focus();
			} else if (number == 1) {
				$scope.query.bearPartyTwoName = obj.name;
				$scope.query.bearPartyTwoId = obj.id;
				$scope.query.bearPartyTwoType = obj.type;
				jQuery("#_packageCounts1").focus();
			} else if(number == 2){
				$scope.query.bearPartyThreeName = obj.name;
				$scope.query.bearPartyThreeId = obj.id;
				$scope.query.bearPartyThreeType = obj.type;
				jQuery("#_packageCounts2").focus();
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
				} else if(type == 2){
					$scope.showGoodsName0 = true;
				}
			} else if (number == 1) {// 第二条
				if(type == 0) {// 隐藏
					$timeout(function(){
						$scope.showGoodsName1 = false;
					},200);
				} else if (type == 1) {
					$scope.showGoodsName1 = true;
				} else if (type == 2){
					$scope.showGoodsName1 = true;
				}
			}else if (number == 2) {// 第二条
				if(type == 0) {// 隐藏
					$timeout(function(){
						$scope.showGoodsName2 = false;
					},200);
				} else if (type == 1) {
					$scope.showGoodsName2 = true;
				} else if (type == 2){
					$scope.showGoodsName2 = true;
				}
			}
		},
		//-------承担方控制----end------------------------------
		
		
		saleOrderInfo:function(trackingNum){
			if(trackingNum != null && trackingNum != '' && trackingNum != undefined){
				var param = {"trackingNum":trackingNum,"time":new Date()};
				var url = "ordSaleTrackingBO.ajax?cmd=ordSaleOrderInfo";
				commonService.postUrl(url,param,function(data){
					$scope.ordSalesTracking = data;
					// 成功执行
					if(data!=null && data!=undefined && data!=""){
						$scope.ordSaleOrderInfoData= data.ordCaSf;
						$scope.commonGoodsName= data.bearParty;
						$scope.ordSales = data.salesTracking;
						if($scope.ordSaleOrderInfoData.paymentType2String != null && $scope.ordSaleOrderInfoData.paymentType2String != "" && $scope.ordSaleOrderInfoData.paymentType2String != undefined){
							$scope.ordSaleOrderInfoData.paymentTypeString=$scope.ordSaleOrderInfoData.paymentTypeString +","+$scope.ordSaleOrderInfoData.paymentType2String;
						}
						//$scope.fixSales = data.fixSales;
						setContentHegthDelay();
		 	    	}
				});
			}
		},
		//加载售后详情
		queryOrdSales:function(salesId){
			if(salesId!=null&&salesId!=""&&salesId!=undefined){
				$scope.isView = 1;
				var param={"salesId":salesId};
				var url="ordSaleTrackingBO.ajax?cmd=getOrdSalesById";
				commonService.postUrl(url,param,function(data){
					$scope.query = data;
					$scope.query.trackingNum=data.trackingNum;
					$scope.query.id=data.id;
					$scope.query.trackRemark = data.trackRemark;
					$scope.query.bearPartyOneId = data.bearPartyOneId;
					$scope.query.bearPartyOneMoney = data.bearPartyOneMoneyString;
					$scope.query.bearPartyOneType = data.bearPartyOneType;
					$scope.query.bearPartyTwoId = data.bearPartyTwoId;
					$scope.query.bearPartyTwoType = data.bearPartyTwoType;
					$scope.query.bearPartyTwoMoney = data.bearPartyTwoMoneyString;
					$scope.query.bearPartyThreeId = data.bearPartyThreeId;
					$scope.query.bearPartyThreeType = data.bearPartyThreeType;
					$scope.query.bearPartyThreeMoney = data.bearPartyThreeMoneyString;
					$timeout(function(){
						$scope.query.bearPartyOneName = data.bearPartyOneIdName+"";
						$scope.query.bearPartyTwoName = data.bearPartyTwoIdName+"";
						$scope.query.bearPartyThreeName = data.bearPartyThreeIdName;
						$scope.query.salesState = data.salesState+"";
						$scope.query.salesType = data.salesType+"";
						$scope.query.trackDate = data.trackDateString;
					},500);
					
					excRegister.toView(data.orderId);
					excRegister.saleOrderInfo(data.trackingNum);
					
					if(data.imagId!=null && data.imagId!=undefined && data.imagId!=''){
						var flowId=data.imagId.split(",");
						for(var i=0;i<flowId.length;i++){
							 eval("$scope.exceImage"+(i+1)+".initDate("+flowId[i]+")");
						}
					}
					if($scope.isSales==1){
						$scope.exceImage1.isUpShow(false);
						$scope.exceImage2.isUpShow(false); 
						$scope.exceImage3.isUpShow(false);
						$scope.exceImage4.isUpShow(false);
						$scope.exceImage5.isUpShow(false);

					}
				});
			}
		},
		//检验运单是否已经存在售后跟踪
		checkTrackingNumOrdSale:function(trackingNum){
			var param={"trackingNum":trackingNum};
			var url="ordSaleTrackingBO.ajax?cmd=checkTrackingNumOrdSale";
			commonService.postUrl(url,param,function(data){
				if(data != null && data != undefined && data != ""&&data.id!=""&&data.id!=null){
					commonService.confirm("已存在该运单的售后信息，是否录入!",function(){
						$scope.query.salesId=data.id;
						$scope.query.id=data.id;
						$scope.query.trackRemark = data.trackRemark;
						$scope.query.bearPartyOneId = data.bearPartyOneId;
						$scope.query.bearPartyOneType = data.bearPartyOneType;
						$scope.query.bearPartyOneMoney = data.bearPartyOneMoneyString;
						$scope.query.bearPartyTwoId = data.bearPartyTwoId;
						$scope.query.bearPartyTwoType = data.bearPartyTwoType;
						$scope.query.bearPartyTwoMoney = data.bearPartyTwoMoneyString;
						$scope.query.bearPartyThreeId = data.bearPartyThreeId;
						$scope.query.bearPartyThreeType = data.bearPartyThreeType;
						$scope.query.bearPartyThreeMoney = data.bearPartyThreeMoneyString;
						$scope.query.bearPartyOneName = data.bearPartyOneIdName+"";
						$scope.query.bearPartyTwoName = data.bearPartyTwoIdName+"";
						$scope.query.bearPartyThreeName = data.bearPartyThreeIdName;
						$scope.query.salesState = data.salesState+"";
						$scope.query.salesType = data.salesType+"";
						$scope.query.trackDate = data.trackDateString;
						//$scope.saleOrderInfo(data.trackingNum);
						if(data.imagId!=null && data.imagId!=undefined && data.imagId!=''){
							var flowId=data.imagId.split(",");
							for(var i=0;i<flowId.length;i++){
								 eval("$scope.exceImage"+(i+1)+".initDate("+flowId[i]+")");
							}
							
						}
						if($scope.isSales==1){
							$scope.exceImage1.isUpShow(false);
							$scope.exceImage2.isUpShow(false); 
							$scope.exceImage3.isUpShow(false);
							$scope.exceImage4.isUpShow(false);
							$scope.exceImage5.isUpShow(false);
						}
						$scope.isView=1;
						$scope.$apply();
					},function(){
						$scope.query.trackingNum = "";
						$scope.form={};
						$scope.ordSales = null;
						$scope.fixSales = null;
						$scope.ordSaleOrderInfoData = null;
					});
				}
			});
		},
	};
	excRegister.init();
}]);
