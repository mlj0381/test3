var cmCustomerApp = angular.module("cmCustomerApp", ['commonApp']);
cmCustomerApp.controller("cmCustomerCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var ord ={
		init:function(){
			this.bindEvent();
			this.doQuery();
			this.selectOrg();
		},
		bindEvent:function(){
			$scope.data = this.data;
			$scope.doQuery = this.doQuery;
			$scope.clear = this.clear;
			$scope.toSave = this.toSave;
			$scope.update = this.update;
			$scope.doDel = this.doDel;
		},
		data:{
			orgId:-1,
			page: 1,
			rows: 10,
			type: 1
		},
		selectOrg:function(){
			var param = {"isAll":"Y"};
			var url = "organizationBO.ajax?cmd=getAllOrg";
			commonService.postUrl(url,param,function(date){
				if(date!=null && date!=undefined && date != ""){
					$scope.orgInfo = date;
					if(date.items != null && date.items != undefined && date != ""){
						$scope.orgInfo.items.unshift({orgId:-1,orgName:"全部"});
					}
	 	    	}
			});
		},
		doQuery:function(){
			var url = "customerBO.ajax?cmd=doQueryCus";
			$scope.data.page = 1;
			$scope.data.type = 1;
			$scope.tableCallBack=function(){
				setContentHegthDelay();
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
			$scope.data.name = "";
			$scope.data.telephone = "";
			$scope.date.orgId = -1;
		},
		doDel:function(){
			var selectedDate = $scope.page.getSelectData();
			if(selectedDate == undefined || selectedDate.length == 0){
				commonService.alert("请选择一条数据！");
				return false;
			}
			commonService.confirm("确认要删除该用户!",function(){
				var id=selectedDate[0].id;
				commonService.postUrl("customerBO.ajax?cmd=delCmById",{id:id},function(data){
					commonService.alert("删除成功！");
					$scope.doQuery();
				});
			});
		},
		update:function(){
			var selectedDate=$scope.page.getSelectData();
			if(selectedDate==undefined || selectedDate.length == 0){
				commonService.alert("请选择一条数据！");
				return false;
			}
			//console.log(selectedDate[0].id);
			commonService.openTab("50002","修改发货人信息","/cm/addCmCustomer.html?id="+selectedDate[0].id);
		},
		toSave:function(){
			commonService.openTab("5002","新增发货人信息","/cm/addCmCustomer.html");
		},
	};
	ord.init();
}]);