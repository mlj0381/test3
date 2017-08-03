var cmConSigneeCustomerApp = angular.module("cmConSigneeCustomerApp", ['commonApp']);
cmConSigneeCustomerApp.controller("cmConSigneeCustomerCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
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
						}
		 	    	}
				});
			},
			doQuery:function(){
				console.log($scope.data);
				var url = "customerBO.ajax?cmd=doQueryCus";
				$scope.data.page = 1;
				$scope.data.type = 2;
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
				commonService.openTab("70002","修改收货人信息","/cm/addConsigneeCmCustomer.html?id="+selectedDate[0].id);
			},
			toSave:function(){
				commonService.openTab("7002","新增收货人信息","/cm/addConsigneeCmCustomer.html");
			},
		};
		ord.init();
}]);