var myApp = angular.module("CreditApp", ['commonApp']);
myApp.controller("CreditCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var myManage={
		init:function(){
			$scope.query = {};
			$scope.auditView = {};
			this.bindEvent();
			this.doQuery();
		},
		bindEvent:function(){
			$scope.param = this.param;
			$scope.doQuery = this.doQuery;
		    $scope.getApplicationById = this.getApplicationById;
		    $scope.doSave = this.doSave;
		    $scope.clear = this.clear;
		    $scope.selectAll = this.selectAll;
		    $scope.selectOne = this.selectOne;
		    $scope.keypress = this.keypress;
		    $scope.mouseLeave = this.mouseLeave;
		    $scope.applyBack = this.applyBack;
		    $scope.auditBack = this.auditBack;
		    $scope.auditSts = this.auditSts;
		    $scope.getDtlByIdView = this.getDtlByIdView;
		},
		auditBack:function(){
			document.getElementById('vehicle_1').style.display='none';
			document.getElementById('vehicle').style.display='block';
			$scope.audit = {};
		},
		applyBack:function(){
			document.getElementById('vehicle_3').style.display='none';
			document.getElementById('vehicle').style.display='block';
			$scope.add = {};
		},
		keypress:function(ev){
			if (ev.keyCode !== 13) return;
			var loginAcct = $scope.add.loginAcct;
			var param = {"phone":loginAcct};
			var url = "cashManageBO.ajax?cmd=doQuerySf";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.add = data;
					$scope.add.loginAcct = loginAcct;
				}
			});
		},
		mouseLeave:function(){
			var loginAcct = $scope.add.loginAcct;
			var param = {"phone":loginAcct};
			var url = "cashManageBO.ajax?cmd=doQuerySf";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.add = data;
					$scope.add.loginAcct = loginAcct;
				}
				
			});
		},
		auditSts:function(state){
			var auditNote = $scope.audit.auditNote;
			var appId = $scope.audit.id;
			var param = {"appId":appId,"state":state,"auditNote":auditNote};
			var url = "cashManageBO.ajax?cmd=doUpdateApplication";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("处理成功!");
					$scope.auditBack();
					$scope.auditBack();
					$scope.doQuery();
				}
				
			});
		},
		params:{
		},
		/**全选*/
		selectAll : function(id,id1) {
			var checkbox = document.getElementsByName(id);
//			var checkbox = document.getElementsByName("check-1");
			if (document.getElementById(id1).checked) {
//				if (document.getElementById("checkbox").checked) {
				for (var i = 0; i < checkbox.length; i++) {
					checkbox[i].checked = true;
				}
			} else {
				for (var i = 0; i < checkbox.length; i++) {
					checkbox[i].checked = false;
				}
			}
		},
		/**列表查询*/
		doQuery:function(){
			myManage.params.workerName=$scope.query.workerName;
			myManage.params.workerLoginAcct=$scope.query.workerLoginAcct;
			var url = "creditManageBO.ajax?cmd=doQuery";
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:myManage.params
						});
			},500);
		},
		/**清空查询条件*/
		clear:function(){
			$scope.query={};
		},
		/**车辆状态查询*/
		doQueryCheckSts:function(){
		},
		/**网点列表查询*/
		queryOrg:function(){
			var param = {"addType":2,"collectType":3};
			var url = "routeBO.ajax?cmd=queryRoateRuting";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.orgInfo=data;
					$scope.query.descOrgId=data.items[0].endOrgId;
	 	    	}
			});
			
		},
		/**选择一行**/
		selectOne : function(batchNum){
				document.getElementById("checkboxOther"+batchNum).checked=true;
		},
		/**提现审批**/
		getApplicationById:function(){
			var batchNum=new Array();
			var isCheckStsOk = false;
			var isPayStsOk = false;
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请选择一条数据!");
				return false;
			}
			var count = 0;
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					batchNum.push(data.id);
					count = count + 1;
				}
			});
			if(count>1){
				commonService.alert("只能选择一条记录进行审批!");
				return false;
			}
			var param = {"id":batchNum.join(",")};
			var url = "cashManageBO.ajax?cmd=getApplicationById";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					document.getElementById('vehicle_1').style.display='block';
					document.getElementById('vehicle').style.display='none';
					$scope.audit = data.ca;
					
	 	    	}
			});
		},
		/**提现查看**/
		getDtlByIdView:function(){
			var batchNum=new Array();
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请选择一条数据!");
				return false;
			}
			var count = 0;
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					batchNum.push(data.sfUserId);
					count = count + 1;
				}
			});
			if(count>1){
				commonService.alert("只能选择一条记录!");
				return false;
			}
			commonService.openTab(batchNum,"信用详情","/sche/credit/CreditDtl.html?batchNum="+batchNum);
		},
		/**申请提现**/
		doSave:function(){
			var batchNum=new Array();
			var isSysDo = false;
			if($("input[name='check-2']:checked").length==0){
				commonService.alert("请至少选择一条数据!");
				return false;
			}
			
			$("input[name='check-2']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					batchNum.push(data.taskId);
				}
			});
			var param = {"taskIds":batchNum.join(","),"userId":$scope.add.userId};
			var url = "cashManageBO.ajax?cmd=doSave";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("处理成功!");
					$scope.applyBack();
				}
			});
		}
	};
	myManage.init();
}]);
