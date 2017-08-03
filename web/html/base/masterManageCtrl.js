var masterManageApp = angular.module("masterManageApp", ['commonApp']);
masterManageApp.controller("masterManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var businessManage={
		//一进入调用
		init:function(){
			this.bindEvent();
			//查询
			this.doQuery();
			this.selectProvince();
		},
		
		bindEvent:function(){
			$scope.user = this.user;
			//查询
			$scope.doQuery = this.doQuery;
			//清空
			$scope.clear = this.clear;
			//新增
			$scope.newSFPartnersAdd = this.newSFPartnersAdd;
			//修改
			$scope.sfPartnersUpdate = this.sfPartnersUpdate;
			//删除
			$scope.delSFPartners = this.delSFPartners;
			//加载省
			$scope.selectProvince=this.selectProvince;
			//加载市
			$scope.selectCity=this.selectCity;
		},
		//初始化queryParam值
		user:{
			orgId: -1,
			page: 1,
			rows: 10,
			provinceId:"",
			regionId:""
		},
		selectProvince:function(){
			var param = {"isAll":"Y"};
			var url = "staticDataBO.ajax?cmd=selectProvince";
			commonService.postUrl(url,param,function(data){
				// 成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.provinceData=data;
					$scope.user.provinceId=data.items[0].id;
	 	    	}
			});
		},
		selectCity:function(provinceId){
			if(parseInt(provinceId)>0){
				var param = {"isAll":"Y","provinceId":provinceId};
				var url = "staticDataBO.ajax?cmd=selectCity";
				commonService.postUrl(url,param,function(data){
					// 成功执行
					if(data!=null && data!=undefined && data!=""){
						$scope.cityData=data;
						$scope.user.regionId=data.items[0].id;
		 	    	}
				});
			}
		},
		doQuery:function(){
			var url = "organizationBO.ajax?cmd=doQuerySFPartners";
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
			$scope.user.regionId = -1;
			$scope.user.provinceId = -1;
			
		},
		newSFPartnersAdd:function(){
			commonService.openTab("300122","新增师傅合作商信息","/base/masterManageAdd.html");
		},
		sfPartnersUpdate:function(){
			var selectedDate=$scope.page.getSelectData();
			if(selectedDate==undefined || selectedDate.length == 0){
				commonService.alert("请选择一条数据！");
				return false;
			}
			commonService.openTab("302","修改师傅合作商信息","/base/masterManageAdd.html?tenantId="+selectedDate[0].tenantId + "&relId=" +selectedDate[0].relId);
		},
		delSFPartners:function(){
			var selectedDate = $scope.page.getSelectData();
			if(selectedDate == undefined || selectedDate.length == 0){
				commonService.alert("请选择一条数据！");
				return false;
			}
			commonService.confirm("确认要删除该师傅合作商!",function(){
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
