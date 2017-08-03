var myApp = angular.module("ApplicationApp", ['commonApp']);
myApp.controller("ApplicationCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var myManage={
		init:function(){
			$scope.query = {};
			$scope.add = {};
			$scope.audit = {};
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
		    $scope.getApplicationByIdView = this.getApplicationByIdView;
		    $scope.verification=this.verification;
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
		getApplicationById:function(){
			var batchNum=new Array();
			var isCheckStsOk = false;
			var isPayStsOk = false;
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请选择一条数据!");
				return false;
			}
			var count = 0;
			var authSts=null;
			var stateName=null;
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					authSts=data.state;
					stateName=data.stateName;
					batchNum.push(data.id);
					count = count + 1;
				}
			});
			if(count>1){
				commonService.alert("只能选择一条记录进行审批!");
				return false;
			}
			if(authSts!=0){
				commonService.alert("提现状态为"+stateName+",不可以操作!");
				return false;
			}
			commonService.openTab(batchNum.join(","),"提现审批","/sche/cash/authApplication.html?id="+batchNum.join(","));
		},
		/**提现查看**/
		getApplicationByIdView:function(){
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
			commonService.openTab(121,"提现申请","/sche/cash/toApplication.html");
		}
	};
	myManage.init();
}]);
