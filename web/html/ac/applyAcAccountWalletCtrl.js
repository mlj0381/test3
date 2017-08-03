var applyAcAccountWalletApp = angular.module("applyAcAccountWalletApp", ['commonApp','ngTouch','tableCommon','angucompleteYQ']);
applyAcAccountWalletApp.controller("applyAcAccountWalletCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.query = {};
	$scope.checkedData=[];
	var applySee = getQueryString("applySee");
	var passSee = getQueryString("passSee");
	var id = getQueryString("id");
	var userId = getQueryString("userId");
	var isSee = getQueryString("isSee");
	var apply={
		init:function(){
			this.bindEvent();
			this.selectStaticData();
			if(id != null && id != undefined && id != ""){
				
				if(isSee != undefined && isSee != null && isSee != ""){
					this.getAuditPull(userId,id,2);
					$scope.isSee = isSee;
					$scope.accountId = id;
				}else{
					this.getAuditPull(userId,id,1);
				}
				$scope.accountId = id;
			}
			
		},
		bindEvent:function(){
			if(applySee != undefined){
				$scope.applySee = applySee;
			}
			if(passSee != undefined){
				$scope.passSee = passSee;
			}
			$scope.selectStaticData = this.selectStaticData;
			$scope.getPullName = this.getPullName;
			$scope.userCallBack = this.userCallBack;
			$scope.loadPullInfo = this.loadPullInfo;
			$scope.selectStaticData = this.selectStaticData;
			$scope.selectAll = this.selectAll;
			$scope.selectObj = this.selectObj;
			$scope.doApply = this.doApply;
			$scope.close = this.close;
			$scope.clean=this.clean;
			$scope.closePass = this.closePass;
			$scope.doPass = this.doPass;
			$scope.toFixed = this.toFixed;
		},
		getPullName:function(){
			var url = "cmUserInfoPullBO.ajax?cmd=getCmUserInfoPullByBill";
			var param = {
					"_GRID_TYPE":"jq",
					"page":1,
					"rows":10,
					loginAcct : $scope.query.loginAcct
			};
			if(param.loginAcct != null && param.loginAcct != undefined && param.loginAcct != '' && param.loginAcct != $scope.searchPlate){
				commonService.postUrl(url,param,function(data){
					if(data != null && data != undefined && data != ""){
						$scope.searchPlate = param.loginAcct;
						var aa=new Array();
						for(var i=0;i<data.rows.length;i++){
							aa.push(data.rows[i]);
						}
						if(aa.length <= 0){
							commonService.alert("无改账号信息！");
							$scope.query={};
							$scope.acMyWalletData=[];
						}else{
							$scope.userInfoData = aa;
							$scope.userCallBack(aa[0]);
						}
					}
				});
				
			}else{
				$scope.userInfoData = new Array();
			}
		},
		userCallBack:function(name,id){
			$scope.query = name;
			var userId = "";
			if(name != undefined && name != null){
				userId = name.userId;
			}
			if(id != undefined && id != null){
				userId = id;
			}
			commonService.postUrl("acAccountWalletBO.ajax?cmd=getAcMyWalletByUserId",{"userId":userId},function(data){
				$scope.acMyWalletData = data;
				setContentHegth();
			});
		},
		selectStaticData:function(){
			var url = "staticDataBO.ajax?cmd=selectSysStaticDataByCodeType";
			var param = {codeType:"RECEI_TYPE_YQ"}
			commonService.postUrl(url,param,function(data){
				$scope.paymentDate = data;
			});
		},
		selectAll:function(){
			var all = document.getElementById("checkbox").checked;
			var table = $scope.acMyWalletData.items;
			if(table != undefined && table != null && table != ""){
				for(var i=0;i<table.length;i++){
					document.getElementById("checkbox_"+table[i].id).checked = all;
				}
			}
			if(all){
				$scope.checkedData = $scope.acMyWalletData.items;
			}else{
				$scope.checkedData =[];
			}
			$scope.query.applyFee = 0;
			if($scope.checkedData != undefined && $scope.checkedData != null && $scope.checkedData.length > 0){
				for(var i=0;i<$scope.checkedData.length;i++){
					$scope.query.applyFee = parseFloat($scope.checkedData[i].amount)+parseFloat($scope.query.applyFee);
					$scope.query.applyFee = $scope.toFixed($scope.query.applyFee,2);
				}
			}
		},
		selectObj:function(obj){
			var selectData = document.getElementById("checkbox_"+obj.id).checked;
			if(selectData){
				$scope.checkedData.push(obj);
			}else{
				if($scope.checkedData != undefined && $scope.checkedData != null && $scope.checkedData.length > 0){
					$scope.checkedData.shift(obj);
				}
			}
			$scope.query.applyFee = 0;
			if($scope.checkedData != undefined && $scope.checkedData != null && $scope.checkedData.length > 0){
				for(var i=0;i<$scope.checkedData.length;i++){
					$scope.query.applyFee = parseFloat($scope.checkedData[i].amount)+parseFloat($scope.query.applyFee);
					$scope.query.applyFee = $scope.toFixed($scope.query.applyFee,2);
				}
			}
		},
		doApply:function(num){
			var tipId = "";
			if($scope.checkedData != undefined && $scope.checkedData != null && $scope.checkedData.length > 0){
				for(var i=0;i<$scope.checkedData.length;i++){
					tipId = $scope.checkedData[i].id + "," + tipId;
				}
			}else{
				commonService.alert("请选择需要申请的数据！");
				return;
			}
			if(num == 3){
				$scope.isApllyOrPass = true;
				$scope.isShow=true;
				return;
			}
			commonService.postUrl("acAccountWalletBO.ajax?cmd=applyTipFee",{"tipId":tipId,"userId":$scope.query.userId,"audit":num,"remark":$scope.query.remark},function(data){
				if(data == "Y"){
					
					var message ="";
					if(num == 2){
						 $scope.closePass();
						 message = "申请并审批通过！"
					}else{
						message = "申请成功！";
					}
					$scope.checkedData=[];
					$scope.userCallBack($scope.query,$scope.query.userId);
					$scope.query.applyFee=0;
					commonService.alert(message);
					
				}
			});
		},
		close:function(){
			if($scope.isSee == 1){
				commonService.closeToParentTab(false);
			}else{
				commonService.closeToParentTab(true);
			}
			
		},
		clean:function(){
			$scope.query={};
			$scope.isApllyOrPass =false;
			$scope.isShow=false;
			$scope.userInfoData = new Array();
			$scope.checkedData=[];
			$scope.acMyWalletData =[];
		},
		closePass:function(){
			$scope.isApllyOrPass =false;
			$scope.isShow=false;
		},
		doPass:function(num){
			commonService.postUrl("acAccountWalletBO.ajax?cmd=auditTip",{"remark":$scope.query.remark,"isPass":num,"id":$scope.accountId},function(data){
				if(data == "Y"){
					var message ="";
					if(num == 1){
						message ="审批通过成功！";
					}else{
						message ="审批不通过成功！";
					}
					commonService.alert(message,function(){
						$scope.close();
					});
				}
			});
		},
		getAuditPull:function(id,acId,see){
			var url = "cmUserInfoPullBO.ajax?cmd=getAccountPullByUserId";
			commonService.postUrl(url,{"userId":userId,"accountId":acId},function(data){
				$scope.query = data;
			});
			commonService.postUrl("acAccountWalletBO.ajax?cmd=getAcMyWalletByUserId",{"userId":userId,"audit":see,"accountId":acId},function(data){
				$scope.query.applyFee = 0;
				if(data != undefined && data != null){
					$scope.acMyWalletData = data;
					for(var i=0;i<$scope.acMyWalletData.items.length;i++){
						$scope.query.applyFee = parseFloat($scope.acMyWalletData.items[i].amount) + parseFloat($scope.query.applyFee);
						$scope.query.applyFee = $scope.toFixed($scope.query.applyFee,2);
					}
				}
			});
		},
		toFixed:function(num, s) {
  			 var times = Math.pow(10, s);
  			 var des = num * times + 0.5;
  			 des = parseInt(des, 10) / times;
  			 return des + '';
		}
	};
	apply.init();
}]);
