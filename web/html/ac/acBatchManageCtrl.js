function changeTabCallback(){
	var appElement = document.querySelector('[ng-controller=AcBatchManageCtrl]');
	var scope=angular.element(appElement).scope();
	scope.doQuery();
	scope.$apply();
}
var proveManageApp = angular.module("AcBatchManageApp", ['commonApp']);
proveManageApp.controller("AcBatchManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var proveManage={
		init:function(){
			$scope.query = {};
			this.bindEvent();
			this.doQuery();
			$scope.query.queryType=1+"";
		},
		bindEvent:function(){
			$scope.param = this.param;
			$scope.doQuery = this.doQuery;
		    $scope.deal = this.deal;
		    $scope.undeal = this.undeal;
		    $scope.clear = this.clear;
		    $scope.toPostTrue = this.toPostTrue;
		},
		
		params:{
		},
		/**列表查询*/
		doQuery:function(){
			$timeout(function(){
				    var checkType = getQueryString("checkType");
				    $scope.query.checkType = checkType;
					$scope.query.page=1;
					var url = "acProveManageBO.ajax?cmd=doQueryPorveBatchNew";
					$scope.tableCallBack=function(){
						setContentHegthDelay();
					};
					$timeout(function(){
						$scope.page.load({
									url:url,
									params:$scope.query,
									callBack:"$scope.tableCallBack"
								});
					},500);
			},500);
			
		},
		/**清空查询条件*/
		clear:function(){
			$scope.query.checkSts="-1";
			$scope.query.orderId="";
			$scope.query.trackingNum="";
			$scope.query.driverName="";
			$scope.query.driverPhone="";
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
				var feeTypeName = data.feeTypeName;
				var batchNumAlias = data.batchNumAlias;
				var feeDouble = data.feeDouble;
				var withoutAmountDouble = data.withoutAmountDouble;
				var checkAmountDouble = data.checkAmountDouble;
				if(data.checkSts == 1){
					commonService.alert("批次号["+batchNumAlias+"]["+feeTypeName+"]已核销,不可再核销。");
					return false;
				}
				if(isNaN(withoutAmountDouble)){
					commonService.alert("批次号["+batchNumAlias+"]输入的实际需核销金额无效。");
					return false;
				}
				var withoutAmountNumber = parseFloat(withoutAmountDouble);
			    if(withoutAmountNumber <= 0){
			    	commonService.alert("批次号["+batchNumAlias+"]实际需核销金额不能小于等于0。");
					return false;
			    }
				if(!(/^\d+(\.\d{1,2})?$/.test(withoutAmountNumber+""))){
					commonService.alert("批次号["+batchNumAlias+"]实际需核销金额最多支持2位小数。");
					return false;
				}
			    
				if(feeDouble < (withoutAmountNumber + checkAmountDouble) ){
					commonService.alert("批次号["+batchNumAlias+"]的实际需核销金额加上已核销金额不能大于核销金额,请输入正确的实际需核销金额。");
					return false;
				}
					
				checks.checkedId = data.checkedId;
				checks.withoutAmount = withoutAmountNumber;
				checks.feeType = $scope.query.checkType;
				checks.batchNum = data.batchNum; 
				checks.queryType = $scope.query.queryType; 
				checkDatas.push(checks);
			}
			var postData = angular.toJson(checkDatas);
			$scope.toPostTrue(postData);

		},
		
		//核销请求
		 toPostTrue : function(postData){
		    $scope.dealTrue = true;
			$("#hexiao").html("核销中。。");
			var param = {"postData":postData,"type":3,"queryType":$scope.query.queryType};
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
				var batchNumAlias = data.batchNumAlias;
				var feeTypeName = data.feeTypeName;
				var data = arrays[i];
				$scope.unCheckIds.push(data.checkedId);
				if(data.checkSts == 2){
					commonService.alert("批次号["+batchNumAlias+"]["+feeTypeName+"]未核销,不可操作反核销操作!");
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
