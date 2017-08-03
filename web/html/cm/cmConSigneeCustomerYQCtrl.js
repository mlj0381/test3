var cmConSigneeCustomerYQApp = angular.module("cmConSigneeCustomerYQApp", ['commonApp','tableCommon']);
cmConSigneeCustomerYQApp.controller("cmConSigneeCustomerYQCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	$scope.query = {type:2,orgId : -1};
	var ord ={
			init:function(){
				this.bindEvent();
				//this.doQuery();
				this.selectOrg();
			},
			 head:[
             {"name":"收货方","code":"name","width":"15%"},
             {"name":"联系人","code":"linkmanName","width":"15%"},
             {"name":"联系手机","code":"bill","width":"15%"},
             {"name":"客服号码","code":"telephone","width":"15%"},
             {"name":"归属网点","code":"orgName","width":"20%"},
             {"name":"详细地址","code":"address","width":"16%"}
             ],
			bindEvent:function(){
				$scope.url = "customerBO.ajax?cmd=pageCmCustomer";
				$scope.head = ord.head;
				$scope.urlParam = $scope.query;
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
				type :2
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
				$scope.query.type=2;
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
				commonService.openTab("updateConsignee"+selectedDate[0].id,"修改收货人信息","/cm/addCmCustomerYQ.html?id="+selectedDate[0].id+"&type=2");
			},
			toSave:function(){
				commonService.openTab("saveConsignee","新增收货人信息","/cm/addCmCustomerYQ.html?type=2");
			},
		};
		ord.init();
}]);