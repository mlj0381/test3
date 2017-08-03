var toApp = angular.module("serviceToApplicationApp", ['commonApp']);
toApp.controller("serviceToApplicationCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var myManage={
		init:function(){
			$scope.add={};
			$scope.query = {};
			this.bindEvent();
		},
		bindEvent:function(){
			$scope.params = this.params;
		    $scope.doSave = this.doSave;
		    $scope.close = this.close;
		    $scope.selectAll = this.selectAll;
		    $scope.selectOne = this.selectOne;
		    $scope.getComCashInfoByTentId=this.getComCashInfoByTentId;
			$scope.clear=this.clear;
			//加载省
			$scope.selectProvince=this.selectProvince;
			//加载市
			$scope.selectCity=this.selectCity;
			$scope.queryMaster=this.queryMaster;
			$scope.closeMaster=this.closeMaster;
			$scope.selectMaster=this.selectMaster;
			$scope.user=this.user;
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
						$scope.user.cityId=data.items[0].id;
		 	    	}
				});
			}
		},
		close:function(){
			commonService.closeToParentTab(true);
		},
		clear:function(){
			$scope.user.name = "";
			$scope.user.linkPhone = "";
			$scope.user.provinceId = "-1";
			$scope.user.cityId = "-1";
		},
		queryMaster:function(){
			document.getElementById('master').style.display='block';
			document.getElementById('alertMsgIsShow').style.display='block';
			myManage.selectProvince();
			$scope.user.isRootOrg=1;
			var url = "organizationBO.ajax?cmd=queryServiceOrg";
			$timeout(function(){
				$scope.masterInfo.load({
							url:url,
							params:$scope.user,
						});
			},500);
		},
		closeMaster:function(){
			document.getElementById('master').style.display='none';
			document.getElementById('alertMsgIsShow').style.display='none';
	         $scope.clear();
		},
		selectMaster:function(){
			var data=$scope.masterInfo.getSelectData();
			if(data==null||data==undefined||data.length==0){
				commonService.alert("请至少选择一条合作商信息!");
				return false;
			}
			$scope.params.tenantName=data[0].name;
			$scope.params.tenantPhone=data[0].phone;
			$scope.params.serviceId=data[0].tenantId;
			$scope.getComCashInfoByTentId(data[0].tenantId);
			document.getElementById('master').style.display='none';
			document.getElementById('alertMsgIsShow').style.display='none';
		},
		user:{
			
		},
		params:{
		},
		/**申请提现**/
		doSave:function(){
			var batchNum=new Array();
			var isSysDo = false;
			if($scope.params.serviceId==null||$scope.params.serviceId==undefined||$scope.params.serviceId==''){
				commonService.alert("请选择合作公司!");
				return false;
			}
			if($scope.params.receiType==1){
				if($scope.params.bankAccount==null||$scope.params.bankAccount==undefined||$scope.params.bankAccount==''){
					commonService.alert("请填写银行卡号!");
					return false;
				}
			}
			if($scope.params.receiType==2){
				if($scope.params.paypalAccount==null||$scope.params.paypalAccount==undefined||$scope.params.paypalAccount==''){
					commonService.alert("请填写支付宝号!");
					return false;
				}
			}
			if($scope.params.receiType==3){
				if($scope.params.wxAccount==null||$scope.params.wxAccount==undefined||$scope.params.wxAccount==''){
					commonService.alert("请填写微信号!");
					return false;
				}
			}
			if($scope.params.accountName==null||$scope.params.accountName==undefined||$scope.params.accountName==''){
				commonService.alert("请填写账户名称!");
				return false;
			}
			if($("input[name='check-2']:checked").length==0){
				commonService.alert("请至少选择一条数据!");
				return false;
			}
			
			$("input[name='check-2']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					batchNum.push(data.withId);
				}
			});
			$scope.params.taskIds=batchNum.join(",");
			var url = "cashManageBO.ajax?cmd=servieSaveApplication";
			commonService.postUrl(url,$scope.params,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("处理成功!");
					$scope.close();
				}
			});
		},
		/**全选*/
		selectAll : function(id,id1) {
			var checkbox = document.getElementsByName(id);
			$scope.add.applyTotalMoney = 0;
			if (document.getElementById(id1).checked) {
				for (var i = 0; i < checkbox.length; i++) {
					checkbox[i].checked = true;
					$scope.add.applyTotalMoney = $scope.add.applyTotalMoney + JSON.parse(checkbox[i].value).money;
				}
			} else {
				for (var i = 0; i < checkbox.length; i++) {
					checkbox[i].checked = false;
				}
			}
		},
		/**选择一行**/
		selectOne : function(t,strId){
			var batchNum = "";
				batchNum = t.withId;
			if(!$scope.add.applyTotalMoney){
				$scope.add.applyTotalMoney = 0;
			}
			if(document.getElementById(strId+batchNum).checked && document.getElementById(strId+batchNum) != undefined){
				document.getElementById(strId+batchNum).checked=false;
				$scope.add.applyTotalMoney = $scope.add.applyTotalMoney - t.money;
			}else{
				document.getElementById(strId+batchNum).checked=true;
				$scope.add.applyTotalMoney = $scope.add.applyTotalMoney + t.money;
			}
		},
		getComCashInfoByTentId:function(tenantId){
			var param = {"serviceId":tenantId};
			var url = "cashManageBO.ajax?cmd=doQueryCom";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.sf=data;
					setContentHegthDelay();
				}
			});
		}
	};
	myManage.init();
}]);
