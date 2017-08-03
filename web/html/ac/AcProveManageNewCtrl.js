function changeTabCallback(){
	var appElement = document.querySelector('[ng-controller=AcProveManageNewCtrl]');
	var scope=angular.element(appElement).scope();
	scope.doQuery();
	scope.$apply();
}
var proveManageNewApp = angular.module("AcProveManageNewApp", ['commonApp']);
proveManageNewApp.controller("AcProveManageNewCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var proveManage={
		init:function(){
			$scope.query = {};
			this.bindEvent();
			this.doQuery();
			this.selectZxOrg();
		},
		bindEvent:function(){
			$scope.param = this.param;
			$scope.doQuery = this.doQuery;
		    $scope.deal = this.deal;
		    $scope.undeal = this.undeal;
		    $scope.clear = this.clear;
		    $scope.upNum = this.upNum;
		    $scope.toPostTrue = this.toPostTrue;
		    $scope.toDetailAllInfo = this.toDetailAllInfo;
		    $scope.callbackCheck = this.callbackCheck;
		    $scope.toDetailAllInfo = this.toDetailAllInfo;
		    
		},
		
		params:{
		},
		 //获取协议商家
		selectZxOrg : function(){
			    var param = {};
			    param.type = 2;
			    param._ALLEXPORT = 1;
				var url = "cmTrunkCostBO.ajax?cmd=doQueryMerchant";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
							$scope.orgIds = data.items;
							$scope.orgIds.unshift({tenantId:"-1",orgName:"所有"});
							$scope.query.businessTenantId = "-1";
		 	    	}
				});
		},
		toDetailAllInfo: function(obj){
			commonService.openTab(obj.trackingNum,"运单详情","/ord/ordBillingDetail.html?view=1&trackingNum="+obj.trackingNum);
		},
		callbackCheck : function(){
		},
		upNum:function(valueName){
			var value = eval("$scope."+valueName).replace(/[^\d]/g, '');
			eval("$scope."+valueName+"=value");
		},
		/**列表查询*/
		doQuery:function(){
			$timeout(function(){
		            //是防止未解析完js请求
					var checkType = getQueryString("checkType");
					$scope.query.checkType = checkType;
					$scope.query.beginDate= $("#beginDate").val();
					$scope.query.endDate = $("#endDate").val();
					$scope.query.page=1;
					var url = "acProveManageBO.ajax?cmd=queryAccountDtlNew";
					$scope.tableCallBack=function(){
						setContentHegthDelay();
					};
					$timeout(function(){
						$scope.page.load({
									url:url,
									params:$scope.query
									
								});
					},500);
			},500);
			
		},
		toDetailAllInfo: function(obj){
			commonService.openTab(obj.trackingNum,"运单详情","/ord/ordBillingDetail.html?view=1&trackingNum="+obj.trackingNum);
		},
		/**清空查询条件*/
		clear:function(){
			$scope.query.checkSts="-1";
			$scope.query.trackingNum="";
			$scope.query.orderState="-1";
			$scope.query.checkSts="-1";
			$scope.query.consignorName="";
			$scope.query.consignorBill="";
			$("#endDate").val("");
			$("#beginDate").val("");
			$scope.query.businessTenantId = "-1";
			$scope.query.consigneeName="";
			$scope.query.consigneeBill="";
		},

		/**处理*/
		deal : function(){
			if($scope.dealTrue){
				return false;
			}
			var arrays = $scope.page.getSelectData(); //获取选中的数据
			if(arrays.length == 0){
				commonService.alert("请至少选择一条核销数据!");
				return false;
			}
			 
			var checkDatas = new Array();
			for(var i = 0; i<arrays.length; i++){
				var checks = {};
				var data = arrays[i];
				var feeTypeName = data.feeTypeName;
				var trackingNum = data.trackingNum;
				var feeDouble = data.feeDouble;
				var withoutAmountDouble = data.withoutAmountDouble;
				var checkAmountDouble = data.checkAmountDouble;
				var seeOrderState = data.seeOrderState;
				var feeType = data.feeType;
				if(data.checkSts == 1){
					commonService.alert("运单号["+trackingNum+"]["+feeTypeName+"]已核销,不可再核销。");
					return false;
				}
				if(isNaN(withoutAmountDouble)){
					commonService.alert("运单号["+trackingNum+"]输入的实际需核销金额无效。");
					return false;
				}
				var withoutAmountNumber = parseFloat(withoutAmountDouble);
			    if(feeDouble != 0){
					//0允许核销
					if(withoutAmountNumber <= 0){
				    	commonService.alert("运单号["+trackingNum+"]实际需核销金额不能小于等于0。");
						return false;
				    }
				}
				if(!(/^\d+(\.\d{1,2})?$/.test(withoutAmountNumber+""))){
					commonService.alert("运单号["+trackingNum+"]实际需核销金额最多支持2位小数。");
					return false;
				}
			    
				if(feeDouble < (withoutAmountNumber + checkAmountDouble) ){
					commonService.alert("运单号["+trackingNum+"]的实际需核销金额加上已核销金额不能大于核销金额,请输入正确的实际需核销金额。");
					return false;
				}
				if(seeOrderState != 5 && feeType == 2){
					commonService.alert("运单号["+trackingNum+"]未签收，到付科目不可进行核销操作。");
					return false;
				}
				if(seeOrderState != 5 && feeType == 5){
					commonService.alert("运单号["+trackingNum+"]未签收，代收货款科目不可进行核销操作。");
					return false;
				}
				if(feeDouble == 0){
					checks.withoutAmount = 0;
				}else{
					checks.withoutAmount = withoutAmountNumber;
				}	
				checks.checkedId = data.checkedId;
				checks.orderId = data.orderId;
				checks.feeType = data.feeType;
				checkDatas.push(checks);
			}
            
			var postData = angular.toJson(checkDatas);
			$scope.toPostTrue(postData);

		},
		
		//核销请求
		 toPostTrue : function(postData){
			$scope.dealTrue = true;
			$("#hexiao").html("核销中。。");
			var param = {"postData":postData};
			var url = "acProveManageBO.ajax?cmd=dealAcCashProveNew";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
						commonService.alert("核销成功!");
						$scope.doQuery();
	 	    	}else{
	 	    		commonService.alert("核销失败!");
	 	    	}
				$scope.dealTrue = false;
				$("#hexiao").html("核销");
			});
			
			$timeout(function(){
				$scope.dealTrue = false;
				$("#hexiao").html("核销");
			},5000);
		},
		/**处理*/
		undeal:function(){
			if($scope.undealTrue){
				return false;
			}
			var arrays = $scope.page.getSelectData(); //获取选中的数据
			if(arrays.length == 0){
				commonService.alert("请至少选择一条核销数据!");
				return false;
			}
		
			
			$scope.unCheckIds = new Array();
			for(var i = 0; i<arrays.length; i++){
				var data = arrays[i];
				var trackingNum = data.trackingNum;
				var feeTypeName = data.feeTypeName;
				var data = arrays[i];
				$scope.unCheckIds.push(data.checkedId);
				if(data.checkSts == 2){
					commonService.alert("运单号["+trackingNum+"]["+feeTypeName+"]未核销,不可操作反核销操作!");
					return false;
				}

			}
			var param = {"checkIds":$scope.unCheckIds.join(",")};
			var url = "acProveManageBO.ajax?cmd=dealUnAcCashProve";
			$scope.undealTrue = true;
			$("#hexiaofan").html("反核销。。");
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("反核销成功!");
					$scope.doQuery();
				}else{
					commonService.alert("反核销失败!");
				}
				$scope.undealTrue = false;
				$("#hexiaofan").html("反核销");
			});
			
			$timeout(function(){
				$scope.undealTrue = false;
				$("#hexiaofan").html("反核销");
			},5000);
		}
	};
	proveManage.init();
}]);
