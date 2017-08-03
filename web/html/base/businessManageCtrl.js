var businessManageApp = angular.module("businessManageApp", ['commonApp','tableCommon']);
businessManageApp.controller("businessManageCtrl", ["$scope","commonService",function($scope,commonService) {
	var businessManage={
		//一进入调用
		init:function(){
			this.bindEvent();
			this.selectOrgCompany();
		},
		head:[
 		      {
					"name" : "公司编码",
					"code" : "tenantCode",
					"width" : "100",
					"type" : "text"
				},
				{
					"name" : "拉包公司",
					"code" : "name",
					"width" : "100",
					"type" : "text"
				},
				{
					"name" : "法人",
					"code" : "linkMan",
					"width" : "100"
				},
				{
					"name" : "联系电话",
					"code" : "linkPhone",
					"width" : "100",
					"type" : "text"
				},
				{
					"name":"客服电话",
					"code":"csPhones",
					"width":"100"
				},
				{
					"name":"公司地址",
					"code":"address",
					"width":"100"
				},
				{
					"name":"公司简介",
					"code":"sellerNotes",
					"width":"130"
				},
				{
					"name":"创建时间",
					"code":"createDate",
					"width":"120"
				},
				{
					"name":"创建人",
					"code":"createName",
					"width":"100"
				},
		      ],
		bindEvent:function(){
			$scope.head=businessManage.head;
			$scope.url="organizationBO.ajax?cmd=doQueryPullPagCompany";
			$scope.urlParam=businessManage.user;
			$scope.user = this.user;
			$scope.doQuery = this.doQuery;
			$scope.clear = this.clear;
			$scope.businessUpdate = this.businessUpdate;
			$scope.check = this.check;
			$scope.newAdd = this.newAdd;
			$scope.delOrg=this.delOrg;
		},
		//初始化queryParam值
		user:{
			orgId: -1,
			page: 1,
			rows: 10,
			relId:""
		},
		delOrg:function(){
			var selectedData=$scope.table.getSelectData();
			if(selectedData==undefined || selectedData.length==0){
				commonService.alert("请选择一条数据！");
				return false;
			}
			commonService.confirm("确认要删除该用户!",function(){
				var tenantId=selectedData[0].tenantId;
				commonService.postUrl("organizationBO.ajax?cmd=delByIdOrg",{tenantId:tenantId},function(data){
					commonService.alert("删除成功！");
					$scope.doQuery();
				});
			});
		},
		selectOrgCompany:function(){
			var param = {"isAll":"Y"};
			var url = "organizationBO.ajax?cmd=queryByCompany";
			commonService.postUrl(url,param,function(data){
				// 成功执行
				if(data!=null && data!=undefined && data != ""){
					$scope.companyData=data;
					$scope.companyData.items.unshift({orgId:-1,orgName:"请选择"});
	 	    	}
			});
		},
		newAdd:function(){
			commonService.openTab("1001","新增拉包公司","/base/addCmCarrierCompany.html");
		},
		doQuery:function(){
			var url = "organizationBO.ajax?cmd=doQueryPullPagCompany";
			$scope.table.load();
			$scope.table.callBack=function(){
				displayTable();
				setContentHeight();
			}
		},
		clear:function(){
			$scope.doQuery();
			$scope.user.tenantCode = "";
			$scope.user.linkPhone = "";
			$scope.user.orgId = -1;
			$scope.table.clearInput();
		},
		businessUpdate:function(){
			var selectedData=$scope.table.getSelectData();
			if(selectedData==undefined || selectedData.length == 0){
				commonService.alert("请选择一条数据！");
				return false;
			}
			commonService.openTab("3002","修改拉包公司信息","/base/carrierCompanyUpdate.html?tenantId="+selectedData[0].tenantId);
		},
	};
	businessManage.init();
}]);
