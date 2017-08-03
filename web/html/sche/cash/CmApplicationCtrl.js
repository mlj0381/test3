var myApp = angular.module("CmApplicationApp", ['commonApp']);
myApp.controller("CmApplicationCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var myManage={
		init:function(){
			$scope.query = {};
			$scope.add = {};
			$scope.audit = {};
			$scope.auditView = {};
			this.bindEvent();
			this.doQuery();
			this.querySFPartners();
		},
		bindEvent:function(){
			$scope.param = this.param;
			$scope.doQuery = this.doQuery;
		    $scope.getCmApplicationById = this.getCmApplicationById;
		    $scope.doSave = this.doSave;
		    $scope.clear = this.clear;
		    $scope.selectAll = this.selectAll;
		    $scope.selectOne = this.selectOne;
		    $scope.auditSts = this.auditSts;
		    $scope.getCmApplicationByIdView = this.getCmApplicationByIdView;
		    $scope.verification=this.verification;
		    $scope.querySFPartners=this.querySFPartners;
		},
		querySFPartners:function(){
			var param = {"isAll":"Y"};
			var url = "organizationBO.ajax?cmd=querySFPartners";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
						$scope.orgData=data;
						//myManage.query.tenantId=data.items[0].sfUserId;
	 	    	}
			});
		},
		auditSts:function(state){
			var auditNote = $scope.audit.auditNote;
			var appId = $scope.audit.id;
			var param = {"appId":appId,"state":state,"auditNote":auditNote};
			var url = "cashManageBO.ajax?cmd=doUpdateCmApplication";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("处理成功!");
					$scope.auditBack();
					$scope.doQuery();
				}
				
			});
		},
		verification:function(){
			var appId='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请选择一条数据!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条数据!");
				return false;
			}
			var withdrawSts=null;
			var withdrawStsName=null;
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					appId=data.id;
					withdrawSts=data.withdrawSts;
					withdrawStsName=data.withdrawStsName;
				}
			});
			if(withdrawSts!=5){
				commonService.alert("提现状态为"+withdrawStsName+",不允许核销!");
				return false;
			}
			var param = {"appId":appId};
			var url = "cashManageBO.ajax?cmd=doUpdateCmApplication";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("处理成功!");
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
			$scope.add.applyTotalMoney = 0;
			if (document.getElementById(id1).checked) {
//				if (document.getElementById("checkbox").checked) {
				for (var i = 0; i < checkbox.length; i++) {
					checkbox[i].checked = true;
					$scope.add.applyTotalMoney = $scope.add.applyTotalMoney + JSON.parse(checkbox[i].value).totalMoney;
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
			if(strId=="checkbox"){
				batchNum = t.id;
			}
			else
			{
				batchNum = t.taskId;
			}
			if(!$scope.add.applyTotalMoney){
				$scope.add.applyTotalMoney = 0;
			}
			if(document.getElementById(strId+batchNum).checked && document.getElementById(strId+batchNum) != undefined){
				document.getElementById(strId+batchNum).checked=false;
				$scope.add.applyTotalMoney = $scope.add.applyTotalMoney - t.totalMoney;
			}else{
				document.getElementById(strId+batchNum).checked=true;
				$scope.add.applyTotalMoney = $scope.add.applyTotalMoney + t.totalMoney;
			}
		},
		/**列表查询*/
		doQuery:function(){
			myManage.params.state=$scope.query.state;
			myManage.params.workerName=$scope.query.workerName;
			myManage.params.workerLoginAcct=$scope.query.workerLoginAcct;
			myManage.params.beginDate=$scope.query.beginDate;
			myManage.params.endDate=$scope.query.endDate;
			myManage.params.userType=6;
			myManage.params.page=1;
			var url = "cashManageBO.ajax?cmd=doQuery";
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:myManage.params,
							callBack:'setContentHegthDelay'
						});
			},500);
		},
		/**清空查询条件*/
		clear:function(){
			$scope.query = {};
			$scope.query.state="-1";
		},
		/**提现审批**/
		getCmApplicationById:function(){
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
			commonService.openTab(batchNum.join(","),"提现审批","/sche/cash/authApplication.html?id="+batchNum.join(","));
		},
		/**提现查看**/
		getCmApplicationByIdView:function(){
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
				commonService.alert("只能选择一条记录!");
				return false;
			}
			commonService.openTab(batchNum.join(","),"提现明细","/sche/cash/viewApplication.html?id="+batchNum.join(","));
		},
		/**申请提现**/
		doSave:function(){
			commonService.openTab(121,"提现申请","/sche/cash/toCmApplication.html");
		}
	};
	myManage.init();
}]);
