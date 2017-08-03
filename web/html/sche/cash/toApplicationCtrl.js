var toApp = angular.module("toApplicationApp", ['commonApp']);
toApp.controller("toApplicationCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var myManage={
		init:function(){
			$scope.query = {};
			$scope.add = {};
			$scope.audit = {};
			$scope.auditView = {};
			this.bindEvent();
		},
		bindEvent:function(){
			$scope.param = this.param;
		    $scope.doSave = this.doSave;
		    $scope.keypress = this.keypress;
		    $scope.mouseLeave = this.mouseLeave;
		    $scope.close = this.close;
		    $scope.selectAll = this.selectAll;
		    $scope.selectOne = this.selectOne;
		},
		close:function(){
			commonService.closeToParentTab(true);
		},
		keypress:function(ev){
			if (ev.keyCode !== 13) return;
			var loginAcct = $scope.add.loginAcct;
			var param = {"phone":loginAcct};
			var url = "cashManageBO.ajax?cmd=doQuerySf";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					setContentHegthDelay();
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
					setContentHegthDelay();
					$scope.add = data;
					$scope.add.loginAcct = loginAcct;
				}
				
			});
		},
		params:{
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
					batchNum.push(data.withId);
				}
			});
			var param = {"taskIds":batchNum.join(","),"userId":$scope.add.userId};
			var url = "cashManageBO.ajax?cmd=doSave";
			commonService.postUrl(url,param,function(data){
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
	};
	myManage.init();
}]);
