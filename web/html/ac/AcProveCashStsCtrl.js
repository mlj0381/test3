var proveCashApp = angular.module("AcProveCashStsApp", ['commonApp']);
proveCashApp.controller("AcProveCashStsCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var proveCash={
		init:function(){
			$scope.query = {};
			this.bindEvent();
			this.doQuery();
		},
		bindEvent:function(){
			$scope.param = this.param;
			$scope.doQuery = this.doQuery;
		    $scope.deal = this.deal;
		    $scope.clear = this.clear;
		    $scope.selectAll = this.selectAll;
		    $scope.selectOne = this.selectOne;
		    $scope.upNum = this.upNum;
		    
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
			var reg = /^\d{0,20}$/;
			if($scope.query.orderId!=undefined&&$scope.query.orderId!=""){
				if(!reg.test($scope.query.orderId)){
					alert("订单编号为20位以内数字");
					return;
				}
			}
			proveCash.params.checkType=$scope.query.checkType;
			proveCash.params.inoutSts=$scope.query.inoutSts;
			proveCash.params.checkSts=$scope.query.checkSts;
			proveCash.params.orderId=$scope.query.orderId;
			proveCash.params.trackingNum=$scope.query.trackingNum;
			$scope.query.page=1;
			var url = "acProveManageBO.ajax?cmd=doQuery";
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:$scope.query,
							callBack:'setContentHegthDelay'
						});
			},500);
		},
		/**清空查询条件*/
		clear:function(){
			$scope.query.checkType="-1";
			$scope.query.inoutSts="-1";
			$scope.query.checkSts="2";
			$scope.query.orderId="";
			$scope.query.trackingNum="";
			$scope.query.consignorName="";
			$scope.query.consignorLinkmanName="";
			$scope.query.consignorBill="";
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
			$scope.checkedId="";
			var isCheckStsOk = false;
			var isPayStsOk = false;
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条核销数据!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条核销数据!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					if(data.checkSts==1){
						isCheckStsOk = true;
						return false;
					}
					$scope.checkedId=data.checkedId;
				}
			});
			if(isCheckStsOk){
				commonService.alert("核销状态为'已审核’的数据,不可以操作!");
				return false;
			}
			var param = {"checkIds":$scope.checkedId};
			var url = "acProveManageBO.ajax?cmd=dealCashSts";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
						commonService.alert("核销成功!");
						$scope.doQuery();
	 	    	}
			});
		},
	};
	proveCash.init();
}]);
