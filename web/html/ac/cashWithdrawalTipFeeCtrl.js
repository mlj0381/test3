var cashWithdrawalTipFeeApp = angular.module("cashWithdrawalTipFeeApp", ['commonApp','tableCommon']);
cashWithdrawalTipFeeApp.controller("cashWithdrawalTipFeeCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.query = {};
	var proveManage={
		init:function(){
			this.bindEvent();
			this.selectStaticData();
		},
		query:{},
		head:[
		      {"name": "提现流水","code": "accountNum","width": "110","type":""},
		      {"name": "拉包工","code": "userName","width": "110","type":""},
		      {"name": "拉包工帐号","code": "loginAcct","width": "110","type":""},
		      {"name": "提现金额","code": "amountString","width": "110", "type":"","isSum":"true","number": "2"},
		      {"name": "申请时间","code": "applyTimeString","width": "110", "type":""},
		      {"name": "申请人","code": "applyString","width": "110", "type":""},
		      {"name": "审核状态","code": "auditStatusString","width": "110", "type":""},
		      {"name": "审核人","code": "auditString","width": "110", "type":""},
		      {"name": "审核时间","code": "auditTimeString","width": "110","type":""},
		      {"name": "核销状态","code": "writeStateString","width": "110","type":""},
		      {"name": "核销时间","code": "writeTimeString","width": "110","type":""},
		      {"name": "核销人","code": "writeString","width": "110", "type":""},
		      {"name": "审核备注","code": "writeRemark","width": "110", "type":""}
		],
		bindEvent:function(){
			$scope.head = proveManage.head;
			$scope.url="acAccountWalletBO.ajax?cmd=queryCashTipFee";
			$scope.urlParam=$scope.query;
			$scope.doQuery = this.doQuery;
			$scope.selectStaticData = this.selectStaticData;
			$scope.applyTipFee = this.applyTipFee;
			$scope.writeTip = this.writeTip;
			$scope.seeTipFee = this.seeTipFee;
			$scope.cleanQuery= this.cleanQuery;
		},
		
		doQuery:function(){
			$scope.query.applyBegin = document.getElementById("applyBegin").value;
			$scope.query.applyEnd = document.getElementById("applyEnd").value;
			$scope.query.writeBegin = document.getElementById("writeBegin").value;
			$scope.query.writeEnd = document.getElementById("writeEnd").value;
			var url = "acAccountWalletBO.ajax?cmd=queryCashTipFee";
			$scope.query.page=1;
			$scope.tableCallBack=function(){
				$scope.paramsExport = JSON.stringify($scope.query);
			}
			$scope.page.load();
			$scope.page.callBack=function(){
				displayTable();
				setContentHeight();
			}
		},
		selectStaticData:function(){
			var url = "staticDataBO.ajax?cmd=selectSysStaticDataByCodeType";
			var param = {codeType:"AUDIT_STATUS_YQ"}
			commonService.postUrl(url,param,function(data){
				$scope.auditDate = data;
			});
			var param2 = {codeType:"WRITE_STATE_YQ"}
			commonService.postUrl(url,param2,function(data){
				$scope.writeData = data;
			});
		},
		applyTipFee:function(num){
			//$scope.page.
			if(num ==1){
				commonService.openTab("apply_tip_fee","申请提现",encodeURI("/ac/applyAcAccountWallet.html?applySee=1"));
			}else{
				var selectedDate = $scope.page.getSelectData();
				if(selectedDate == undefined || selectedDate.length == 0 || selectedDate.length > 1){
					commonService.alert("请选择一条数据！");
					return false;
				}
				commonService.openTab("apply_tip_fee"+selectedDate[0].id,"提现审批",encodeURI("/ac/applyAcAccountWallet.html?passSee=1&id="+selectedDate[0].id+"&userId="+selectedDate[0].userId));
			}
			
		},
		writeTip:function(num){
			var selectedDate = $scope.page.getSelectData();
			if(selectedDate == undefined || selectedDate.length == 0){
				commonService.alert("请选择一条数据，或多条！");
				return false;
			}
			var accountId = "";
			for(var i=0;i<selectedDate.length;i++){
				accountId = selectedDate[i].id + ","+accountId;
			}
			commonService.postUrl("acAccountWalletBO.ajax?cmd=writeTip",{"accountId":accountId,"write":num},function(data){
				if(data == "Y"){
					var message = "";
					if(num == 1){
						message = "核销成功！";
					}else{
						message = "反核销成功！";
					}
					commonService.alert(message,function(){
						$scope.doQuery();
					});
				}
			});
		},
		seeTipFee:function(){
			var selectedDate = $scope.page.getSelectData();
			if(selectedDate == undefined || selectedDate.length == 0 || selectedDate.length > 1){
				commonService.alert("请选择一条数据！");
				return false;
			}
			commonService.openTab("see_tip_fee"+selectedDate[0].id,"查看提现明细",encodeURI("/ac/applyAcAccountWallet.html?isSee=1&id="+selectedDate[0].id+"&userId="+selectedDate[0].userId));
		},
		cleanQuery:function(){
			$scope.query.applyBegin = "";
			$scope.query.applyEnd ="";
			$scope.query.writeBegin = "";
			$scope.query.writeEnd = "";
			
			document.getElementById("applyBegin").value="";
			document.getElementById("applyEnd").value="";
			document.getElementById("writeBegin").value="";
			document.getElementById("writeEnd").value="";
			
			$scope.query.auditStatus = "";
			$scope.query.writeState = "";
			$scope.query.userName="";
			$scope.query.loginAcct="";
		}
	};
	proveManage.init();
}]);
