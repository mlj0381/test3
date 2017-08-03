function changeTabCallback(){
	var appElement = document.querySelector('[ng-controller=AcProveManageCtrl]');
	var scope=angular.element(appElement).scope();
	scope.doQuery();
	scope.$apply();
}

var proveManageApp = angular.module("AcProveManageApp", ['commonApp']);
proveManageApp.controller("AcProveManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var proveManage={
		init:function(){
			$scope.query = {};
			this.bindEvent();
			this.doQuery();
			$scope.isTrue = false;
			var returnKeyEventDomEleIds = ["beginTime","endTime","id1","id2","id3"];
			commonService.registerKeyEventForDoms(returnKeyEventDomEleIds, "keydown", "return", $scope.doQuery);
		},
		bindEvent:function(){
			
			$scope.param = this.param;
			$scope.doQuery = this.doQuery;
		    $scope.deal = this.deal;
		    $scope.undeal = this.undeal;
		    $scope.clear = this.clear;
		    $scope.toPostTrue = this.toPostTrue;
		    $scope.toDetailAllInfo = this.toDetailAllInfo;
		},
		toDetailAllInfo: function(obj){
			commonService.openTab(obj.trackingNum,"运单详情","/ord/ordBillingDetail.html?view=1&trackingNum="+obj.trackingNum);
		},
		params:{
		},
		/**列表查询*/
		doQuery:function(){
			$timeout(function(){
					$scope.query.beginTime = document.getElementById("beginTime").value;
					$scope.query.endTime = document.getElementById("endTime").value;
					$scope.query.page=1;
					var url = "acProveManageBO.ajax?cmd=queryOutGoingCheckInfo";
					$scope.tableCallBack=function(data){
						setContentHegthDelay();
					};
					$timeout(function(){
						$scope.page.load({url:url, params:$scope.query, callBack:"$scope.tableCallBack" });
					},500);
			},500);
		},
		/**清空查询条件*/
		clear:function(){
			document.getElementById("beginTime").value='';
			document.getElementById("endTime").value='';
			$scope.query.trackingNum='';
			$scope.query.checkSts="-1";// 核销类型
			$scope.query.carrierCompanyName = "";
		},
	
		/**处理*/
		deal:function(){
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
				var trackingNum = data.trackingNum;
				var checkFee = data.checkFeeDouble;
				var withoutAmountDouble = data.withoutAmountDouble;
				var checkAmountDouble = data.checkAmountDouble;
				var checkId = data.checkId;
				var seeOrderState = data.seeOrderState;
//				if(checkFee == 0 || checkId == 0){
//					commonService.alert("运单号["+trackingNum+"]中转费为0无需核销。");
//					return false;
//				}
				if(seeOrderState != 5){
					commonService.alert("运单号["+trackingNum+"]未签收，中转费不可进行核销操作。");
					return false;
				}
				if(data.checkSts == 1){
					commonService.alert("运单号["+trackingNum+"]中转已核销,无需再核销。");
					return false;
				}
				if(isNaN(withoutAmountDouble)){
					commonService.alert("运单号["+trackingNum+"]输入的实际需核销金额无效。");
					return false;
				}
				var withoutAmountNumber = parseFloat(withoutAmountDouble);
				if(checkFee != 0){
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
			    
				if(checkFee < (withoutAmountNumber + checkAmountDouble) ){
					commonService.alert("运单号["+trackingNum+"]的实际需核销金额加上已核销金额不能大于核销金额,请输入正确的实际需核销金额。");
					return false;
				}
				checks.checkedId = checkId;
				checks.withoutAmount = withoutAmountNumber;
				if(checkFee == 0){
					checks.withoutAmount = 0;
				}
				checks.feeType = 13;
				checks.orderId = data.orderId;
				checkDatas.push(checks);
			}
			var postData   = angular.toJson(checkDatas);
			$scope.dealTrue = true;
			$("#hexiao").html("核销中。。");
			$scope.toPostTrue(postData);
		},
		//核销请求
		toPostTrue : function(postData){
			var param = {"postData": postData,"type":1};
			var url = "acProveManageBO.ajax?cmd=dealAcCashProveNew";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("核销成功!");	
					$scope.doQuery();
				} else{
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
				var data = arrays[i];
				$scope.unCheckIds.push(data.checkId);
				if(data.checkSts == 2){
					commonService.alert("运单号["+trackingNum+"]中转未核销,不可操作反核销操作!");
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
