var billingComRemarkApp = angular.module("billingComRemarkApp", ['commonApp']);
billingComRemarkApp.controller("billingComRemarkCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var ord ={
		init:function(){
			this.bindEvent();
			this.doQuery();
		},
		bindEvent:function(){
			$scope.data = this.data;
			$scope.toSave = this.toSave;
			$scope.update = this.update;
			$scope.doDel = this.doDel;
			$scope.clear = this.clear;
			$scope.doQuery = this.doQuery;
		},
		toSave:function(){
			commonService.openTab("1221211","新增常用备注","/ord/addBillingComRemark.html");
		},
		update:function(){
			var selectedDate=$scope.page.getSelectData();
			if(selectedDate==undefined || selectedDate.length == 0){
				commonService.alert("请选择一条数据！");
				return false;
			}
			commonService.openTab("31231","修改常用备注","/ord/addBillingComRemark.html?RId="+selectedDate[0].RId);
		},
		doDel:function(){
			var selectedDate=$scope.page.getSelectData();
			if(selectedDate==undefined || selectedDate.length==0){
				commonService.alert("请选择一条数据！");
				return false;
			}
			commonService.confirm("确认要删除该备注!",function(){
				var RId=selectedDate[0].RId;
				commonService.postUrl("orderInfoBO.ajax?cmd=doDelCmOrgNotes",{RId:RId},function(data){
					commonService.alert("删除成功！");
					$timeout(function(){
						$scope.doQuery();
						},200);
					
				});
			});
		},
		data:{
			page:1,
			rows: 10
		},
		doQuery:function(){
			var url = "orderInfoBO.ajax?cmd=queryCmOrgNotes";
			$scope.data.page = 1;
			
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:$scope.data,
							callBack:"setContentHegthDelay"
						});
			},500);
		},
		clear:function(){
			$scope.doQuery();
			$scope.data.notes = "";
		},
	};
	ord.init();
}]);