var logisticsCompanyInfoApp = angular.module("logisticsCompanyInfoApp", ['commonApp','tableCommon']);
logisticsCompanyInfoApp.controller("logisticsCompanyInfoCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	$scope.query = {type:2,orgId : -1};
	var ord ={
			init:function(){
				this.bindEvent();
				
			},
			 head:[
             {"name":"物流公司名称","code":"logisticsName","width":"15%"},
             {"name":"联系人","code":"linkName","width":"10%"},
             {"name":"联系人电话","code":"telephone","width":"15%"},
             {"name":"地址","code":"address","width":"15%"},
             {"name":"备注","code":"remark","width":"15%"},
             {"name":"登记人","code":"boardName","width":"10%"},
             {"name":"登记时间","code":"boardTime","width":"20%"}
             ],
			bindEvent:function(){
				$scope.url = "sysTenantRegisterBO.ajax?cmd=querySysTenantRegister";
				$scope.head = ord.head;
				$scope.urlParam = $scope.query;
				$scope.doQuery = this.doQuery;
				$scope.clear = this.clear;
				$scope.del = this.del;
				$scope.add = this.add;
				$scope.update = this.update;
			},
			query:{
				page: 1,
				rows: 10
			},
			doQuery:function(){
				var url = "sysTenantRegisterBO.ajax?cmd=querySysTenantRegister";
				$scope.query.page=1;
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
				$scope.query.logisticsName = "";
				$scope.query.linkName = "";
				$scope.query.telephone = "";
			},
			
			add:function(){
				commonService.openTab("11229_addSysTenantRegisterCtrl","物流公司信息登记新增","/cm/addSysTenantRegister.html");
			},
			update:function(){
				var selectedDate=$scope.page.getSelectData();
				if(selectedDate==undefined || selectedDate.length == 0){
					commonService.alert("请选择一条数据！");
					return false;
				}
				var id = selectedDate[0].id;
				console.log(selectedDate[0].id);
				commonService.openTab("addSysTenantRegisterCtrl"+id,"物流公司信息登记修改","/cm/addSysTenantRegister.html?id="+selectedDate[0].id);
			},
			del:function(){
				var selectedDate = $scope.page.getSelectData();
				if(selectedDate == undefined || selectedDate.length == 0){
					commonService.alert("请选择一条数据！");
					return false;
				}
				commonService.confirm("确认要删除这条数据!",function(){
					var id=selectedDate[0].id;
					commonService.postUrl("sysTenantRegisterBO.ajax?cmd=delSysTenantRegister",{id:id},function(data){
						commonService.alert("删除成功！");
						$scope.doQuery();
					});
					
				});	
				
			}
				
		};
		ord.init();
}]);