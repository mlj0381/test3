var linkBusinessManageApp = angular.module("linkBusinessManageApp", ['commonApp']);
linkBusinessManageApp.controller("linkBusinessManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var businessManage={
		//一进入调用
		init:function(){
			this.bindEvent();
			this.doQuery();
			this.selectOrgLink();
		},
		
		bindEvent:function(){
			$scope.newBusinessAdd = this.newBusinessAdd;
			$scope.user = this.user;
			$scope.doQuery = this.doQuery;
			$scope.clear = this.clear;
			$scope.businessUpdate = this.businessUpdate;
			$scope.delBusiness = this.delBusiness;
			$scope.org = this.org;
		},
		//初始化queryParam值
		user:{
			page: 1,
			rows: 10
		},
		org:{
			orgId: -1,
		},
		newBusinessAdd:function(){
			commonService.openTab("4001","新增商家","/base/linkBusinessManageAdd.html");
		},
		selectOrgLink:function(){
			var param = {"isAll":"Y"};
			var url = "organizationBO.ajax?cmd=queryByAllLink";
			commonService.postUrl(url,param,function(data){
				// 成功执行
				if(data!=null && data!=undefined && data != ""){
					$scope.provinceData=data;
	 	    	}
			});
		},
		doQuery:function(){
			var url = "organizationBO.ajax?cmd=doLinkQueryBusiness";
			$scope.user.page = 1;
			$scope.tableCallBack=function(){
				setContentHegthDelay();
			};
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:$scope.user,
							callBack:"$scope.tableCallBack"
						});
			},500);
		},
		clear:function(){
			$scope.doQuery();
			$scope.user.name = "";
			$scope.user.linkMan = "";
			$scope.user.linkPhone = "";
			$scope.user.busiNotes = "";
		},
		delBusiness:function(){
			var selectedDate = $scope.page.getSelectData();
			if(selectedDate == undefined || selectedDate.length == 0){
				commonService.alert("请选择一条数据！");
				return false;
			}
			commonService.confirm("确认要删除该用户!",function(){
				var relId=selectedDate[0].relId;
				commonService.postUrl("organizationBO.ajax?cmd=doDelBusiness",{relId:relId},function(data){
					commonService.alert("删除成功！");
					$scope.doQuery();
				});
			});
		},
	};
	businessManage.init();
}]);
