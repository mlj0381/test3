var lineCompanyNexusManageApp = angular.module("lineCompanyNexusManageApp", ['commonApp','tableCommon']);
lineCompanyNexusManageApp.controller("lineCompanyNexusManageCtrl", ["$scope","commonService",function($scope,commonService) {
	var lineCompanyNexusManage={
		//一进入调用
		init:function(){
			this.bindEvent();
			this.selectOrgCompany();
			this.selectOrgLink();
			if(userInfo.userType == 10){
				$scope.userType = userInfo.userType;
			}
			
		},
		head:[
		        {
		        	"name":"专线公司",
		        	"code":"speciaLineName",
		        	"width":"100"
		        },
                {
		        	"name":"拉包公司",
		        	"code":"carrierCompanyName",
		        	"width":"120"
		        },
                {
		        	"name":"拉包公司法人",
		        	"code":"carrierCompanyPrincipal",
		        	"width":"100"
		        },
                {
		        	"name":"拉包公司电话",
		        	"code":"carrierCompanyBill",
		        	"width":"100"
		        },
                {
		        	"name":"专线联系人",
		        	"code":"specialLinePrincipal",
		        	"width":"100"
		        },
            	{
		        	"name":"专线公司电话",
		        	"code":"specialLineBill",
		        	"width":"100"
		        },
            	{
		        	"name":"创建时间",
		        	"code":"createDate",
		        	"width":"100"
		        },
            	{
		        	"name":"创建人",
		        	"code":"creatorName",
		        	"width":"100"
		        }
		     ],
		bindEvent:function(){
			$scope.head=lineCompanyNexusManage.head;
			$scope.url="specialLineCompanyNexusBO.ajax?cmd=doQuerySpecialLineCompanyNexus";
			$scope.urlParam = lineCompanyNexusManage.user;
			$scope.user=this.user;
			$scope.doQuery = this.doQuery;
			$scope.clear = this.clear;
			$scope.doUpdate = this.doUpdate;
			$scope.check = this.check;
			$scope.newAdd = this.newAdd;
			$scope.doDel=this.doDel;
			 
		},
		//初始化queryParam值
		user:{
			tenantId: -1,
			relId:""
		},
		doDel:function(){
			var selectedDate=$scope.table.getSelectData();
			if(selectedDate==undefined || selectedDate.length==0){
				commonService.alert("请选择一条数据！");
				return false;
			}
			commonService.confirm("确认要删除该关系!",function(){
				var relId=selectedDate[0].relId;
				var url="specialLineCompanyNexusBO.ajax?cmd=deleteSpecialLineCompanyNexus";
				commonService.postUrl(url,{relId:relId},function(data){
					commonService.alert("删除成功！");
					$scope.doQuery();
				});
			});
		},
		selectOrgLink:function(){
			var param = {"isAll":"Y"};
			var url = "organizationBO.ajax?cmd=queryByAllLink";
			commonService.postUrl(url,param,function(data){
				// 成功执行
				if(data!=null && data!=undefined && data != ""){
					$scope.orgLink=data;
					$scope.orgLink.items.unshift({tenantId:-1,orgName:"请选择"});
	 	    	}
			});
		},
		selectOrgCompany:function(){
			var param = {"isAll":"Y"};
			var url = "organizationBO.ajax?cmd=queryByCompany";
			commonService.postUrl(url,param,function(data){
				// 成功执行
				if(data!=null && data!=undefined && data != ""){
					$scope.companyData=data;
					if(userInfo.userType == 10){
						$scope.user.companyId = data.items[0].orgId;
					}
					$scope.companyData.items.unshift({tenantId:-1,orgName:"请选择"});
	 	    	}
			});
		},
		newAdd:function(){
			commonService.openTab("20","新增专线与拉包公司关系","/cm/addLineCompanyNexus.html");
		},
		doQuery:function(){
			var url = "specialLineCompanyNexusBO.ajax?cmd=doQuerySpecialLineCompanyNexus";
			$scope.table.load();
			$scope.table.callBack=function(){
				displayTable();
				setContentHeight();
			}
		},
		clear:function(){
			$scope.user.companyId = -1;
			$scope.user.lineId = -1;
			$scope.user.tenantId=-1;
		},
		doUpdate:function(){
			var selectedDate=$scope.table.getSelectData();
			if(selectedDate==undefined || selectedDate.length == 0){
				commonService.alert("请选择一条数据！");
				return false;
			}
			commonService.openTab("3002","修改拉包公司信息","/cm/lineCompanyNexusUpdate.html?relId="+selectedDate[0].relId);
		},
	};
	lineCompanyNexusManage.init();
}]);
