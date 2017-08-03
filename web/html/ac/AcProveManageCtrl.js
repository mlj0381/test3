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
		},
		bindEvent:function(){
			$scope.param = this.param;
			$scope.doQuery = this.doQuery;
		    $scope.deal = this.deal;
		    $scope.undeal = this.undeal;
		    $scope.clear = this.clear;
		    $scope.selectAll = this.selectAll;
		    $scope.selectOne = this.selectOne;
		    $scope.upNum = this.upNum;
		    $scope.toPostTrue = this.toPostTrue;
		    $scope.toDetailAllInfo = this.toDetailAllInfo;
		},
		
		params:{
		},
		/**全选*/
		selectAll : function() {
			var checkbox = document.getElementsByName("check-1");
			if (document.getElementById("checkbox").checked) {
				for (var i = 0; i < checkbox.length; i++) {
					checkbox[i].checked = true;
				}
			} else {
				for (var i = 0; i < checkbox.length; i++) {
					checkbox[i].checked = false;
				}
			}
		},
		
		upNum:function(valueName){
			var value = eval("$scope."+valueName).replace(/[^\d]/g, '');
			eval("$scope."+valueName+"=value");
		},
		/**列表查询*/
		doQuery:function(){
			$timeout(function(){
				//防止下拉框未执行完导致查询参数设置失败
					var checkType = getQueryString("checkType");
					$scope.query.checkType = checkType;
					$scope.query.beginDate= $("#beginDate").val();
					$scope.query.endDate = $("#endDate").val();
					$scope.query.page=1;
					var url = "acProveManageBO.ajax?cmd=doQueryPorve";
					$scope.tableCallBack=function(){
						setContentHegthDelay();
					}
					$timeout(function(){
						$scope.page.load({
									url:url,
									params:$scope.query,
									callBack:"$scope.tableCallBack"
								});
					},500);
			},500);
			
		},
		toDetailAllInfo: function(obj){
			commonService.openTab(obj.trackingNum,"运单详情","/ord/ordBillingDetail.html?view=1&trackingNum="+obj.trackingNum);
		},
		/**清空查询条件*/
		clear:function(){
			$scope.query.inoutSts="-1";
			$scope.query.checkSts="-1";
			$scope.query.orderId="";
			$scope.query.trackingNum="";
//			$scope.query.checkType="-1";
			$scope.query.inoutSts="-1";
			$scope.query.checkSts="-1";
			$scope.query.consignorName="";
			$scope.query.consignorBill="";
			$("#endDate").val("");
			$("#beginDate").val("");
			$scope.query.consigneeName="";
			$scope.query.consigneeBill="";
		},
		/**车辆状态查询*/
		doQueryCheckSts:function(){
		},
		/**选择一行**/
		selectOne : function(batchNum){
			if(document.getElementById("checkbox"+batchNum).checked && document.getElementById("checkbox"+batchNum) != undefined){
				document.getElementById("checkbox"+batchNum).checked=false;
			}else{
				document.getElementById("checkbox"+batchNum).checked=true;
			}
		},
		/**处理*/
		deal:function(){
			var arrays = $scope.page.getSelectData(); //获取选中的数据
			if(arrays.length == 0){
				commonService.alert("请至少选择一条核销数据!");
				return false;
			}
			var tip = "";
			var trackingNums = "";
			var isPayStsOk = false;
			$scope.checkIds = new Array();
			for(var i = 0; i<arrays.length; i++){
				var data = arrays[i];
				$scope.checkIds.push(data.checkedId);
				if(data.checkSts == 1){
					commonService.alert("核销状态为'已核销’的数据,不可以操作!");
					return false;
				}
				if(data.paySts == 1 ){
					isPayStsOk = true;
//					 if(trackingNums === "" ){
//						 trackingNums = data.trackingNum;
//					 }else{
//						 trackingNums = trackingNums + ";"+data.trackingNum;
//					 }
					 
				}else if(data.paySts == 3){
					/* if(trackingNums === "" ){
						 trackingNums = data.trackingNum;
					 }else{
						 trackingNums = trackingNums + ";"+data.trackingNum;
					 }
					 isPayStsOk = true;*/
				}
				
				
			}
			if(isPayStsOk){
				commonService.confirm("存在收支状态为'未收'的数据,是否继续核销?",function(){
					$scope.toPostTrue();
				});
			}else{
				$scope.toPostTrue();
			}
			
			

		},
		
		//核销请求
		 toPostTrue : function(){
			var param = {"checkIds":$scope.checkIds.join(",")};
			var url = "acProveManageBO.ajax?cmd=dealAcCashProve";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
						commonService.alert("核销成功!");
						$scope.doQuery();
	 	    	}else{
	 	    		commonService.alert("核销失败!");
	 	    	}
			});
		},
		/**处理*/
		undeal:function(){
			
			var arrays = $scope.page.getSelectData(); //获取选中的数据
			if(arrays.length == 0){
				commonService.alert("请至少选择一条核销数据!");
				return false;
			}
			$scope.unCheckIds = new Array();
			for(var i = 0; i<arrays.length; i++){
				var data = arrays[i];
				$scope.unCheckIds.push(data.checkedId);
				if(data.checkSts == 2){
					commonService.alert("核销状态为'未核销’的数据,不可以操作!");
					return false;
				}

			}
			var param = {"checkIds":$scope.unCheckIds.join(",")};
			var url = "acProveManageBO.ajax?cmd=dealUnAcCashProve";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("反核销成功!");
					$scope.doQuery();
				}else{
					commonService.alert("反核销失败!");
				}
			});
		}
	};
	proveManage.init();
}]);
