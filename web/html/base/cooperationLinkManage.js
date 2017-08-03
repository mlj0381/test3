var cooperationLinkManageApp = angular.module("cooperationLinkManageApp", ['commonApp']);
cooperationLinkManageApp.controller("cooperationLinkManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
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
			commonService.openTab("131232","新增交接方式","/ord/addBillingRecDelSys.html");
		},
		update:function(){
			var selectedDate=$scope.page.getSelectData();
			if(selectedDate==undefined || selectedDate.length == 0){
				commonService.alert("请选择一条数据！");
				return false;
			}
			commonService.openTab("1231231","修改交接方式","/ord/addBillingRecDelSys.html?flowId="+selectedDate[0].flowId);
		},
		doDel:function(){
			var selectedDate=$scope.page.getSelectData();
			if(selectedDate==undefined || selectedDate.length==0){
				commonService.alert("请选择一条数据！");
				return false;
			}
			commonService.confirm("确认要删除该交接方式!",function(){
				var flowId=selectedDate[0].flowId;
				commonService.postUrl("orderInfoBO.ajax?cmd=doDelSysSD",{flowId:flowId},function(data){
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
			var url = "organizationBO.ajax?cmd=doQuerySFLink";
			$scope.data.page = 1;
			$scope.tableCallBack=function(){
				if ($scope.page.data.items == undefined || $scope.page.data.items.length == 0) {// 没有数据，指定一个默认高度
					setContentHeightWithSpecialHeight(700);
				} else {// 有数据，则根据数据行计算高度
					var height = (700 - 200) + $scope.page.data.items.length * 48;
					height = height <= 700 ? 700 : height;
					setContentHeightWithSpecialHeight(height+40);
				}
			};
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:$scope.data,
							callBack:"$scope.tableCallBack"
						});
			},500);
		},
		clear:function(){
			$scope.doQuery();
			$scope.data.codeName = "";
		},
	};
	ord.init();
}]);