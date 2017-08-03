var cmCustomerYQApp = angular.module("cmCustomerYQApp", ['commonApp','tableCommon']);
cmCustomerYQApp.controller("cmCustomerYQCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	$scope.query={type:1,orgId : -1};
	var ord ={
		init:function(){
			this.bindEvent();
			//this.doQuery();
			this.selectOrg();
		},
		head:[
        {"name":"发货方","code":"name","width":"10%"},
        {"name":"联系人","code":"linkmanName","width":"10%"},
        {"name":"联系手机","code":"bill","width":"10%"},
        {"name":"客服号码","code":"telephone","width":"10%"},
        {"name":"归属网点","code":"orgName","width":"10%"},
        {"name":"详细地址","code":"address","width":"36%"}
        ],
		bindEvent:function(){
			$scope.url="customerBO.ajax?cmd=pageCmCustomer";
			$scope.urlParam=$scope.query;
			$scope.head = ord.head;
			$scope.data = this.data;
			$scope.doQuery = this.doQuery;
			$scope.clear = this.clear;
			$scope.toSave = this.toSave;
			$scope.update = this.update;
			$scope.doDel = this.doDel;
		},
		query:{
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
						$scope.query.orgId=-1;
					}
	 	    	}
			});
		},
		doQuery:function(){
			var url = "customerBO.ajax?cmd=pageCmCustomer";
			$scope.query.page=1;
			$scope.query.type=1;
			$scope.tableCallBack=function(){
				$scope.paramsExport = JSON.stringify($scope.query);
			}
			$scope.page.load();
			$scope.page.callBack=function(){
				displayTable();
				setContentHeight();
			}
		},
		clear:function(){
			$scope.doQuery();
			$scope.query.name = "";
			$scope.query.bill = "";
			$scope.query.orgId = -1;
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
			commonService.openTab("updateConsignor"+selectedDate[0].id,"修改发货人信息","/cm/addCmCustomerYQ.html?id="+selectedDate[0].id+"&type=1");
		},
		toSave:function(){
			commonService.openTab("saveConsignor","新增发货人信息","/cm/addCmCustomerYQ.html?type=1");
		},
	};
	ord.init();
}]);