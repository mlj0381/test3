var dedicatedQueryApp = angular.module("dedicatedQueryApp", ['commonApp']);
dedicatedQueryApp.controller("dedicatedQueryCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var ord ={
		init:function(){
			this.bindEvent();
			this.selectProvince();
			this.doQuery();
			
		},
		bindEvent:function(){
			$scope.newAdd = this.newAdd;
			$scope.doQuery = this.doQuery;
			$scope.update = this.update;
			$scope.delDedicated=this.delDedicated;
			$scope.params = this.params;
			$scope.clear=this.clear;
			$scope.delOrg = this.delOrg;
		},
		newAdd:function(){
			commonService.openTab("1001","新增专线","/base/dedicatedQueryAdd.html");
		},
		params:{
			provinceIds:-1,
			page: 1,
			rows: 10
		},
		selectProvince:function(){
			var param = {"isAll":"Y"};
			var url = "staticDataBO.ajax?cmd=selectProvince";
			commonService.postUrl(url,param,function(data){
				// 成功执行
				if(data!=null && data!=undefined && data!=""){
						$scope.provinceData=data;
	 	    	}
			});
		},
		doQuery:function(){
			var url = "organizationBO.ajax?cmd=queryOrgListByPage";
			$scope.params.page = 1;
//			$scope.tableCallBack=function(){
//				if ($scope.page.data.items == undefined || $scope.page.data.items.length == 0) {// 没有数据，指定一个默认高度
//					setContentHeightWithSpecialHeight(700);
//				} else {// 有数据，则根据数据行计算高度
//					var height = (700 - 200) + $scope.page.data.items.length * 48;
//					height = height <= 700 ? 700 : height;
//					setContentHeightWithSpecialHeight(height+40);
//				}
//			};
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:$scope.params,
						});
			},500);
			setContentHegthDelay();
	},
	delOrg:function(){
		var selectedDate=$scope.page.getSelectData();
		if(selectedDate==undefined || selectedDate.length==0){
			commonService.alert("请选择一条数据！");
			return false;
		}
		commonService.confirm("确认要删除该用户!",function(){
			var tenantId=selectedDate[0].tenantId;
			commonService.postUrl("organizationBO.ajax?cmd=delByIdOrg",{tenantId:tenantId},function(data){
				commonService.alert("删除成功！");
				$scope.doQuery();
			});
		});
	},
	clear:function(){
		$scope.params.name="";
		$scope.params.linkMan="";
		$scope.params.linkPhone="";
	    $scope.params.provinceIds = -1;
	    $scope.doQuery();
	},
	update:function(){
		var selectedDate=$scope.page.getSelectData();
		if(selectedDate==undefined || selectedDate.length==0){
			commonService.alert("请选择一条数据！");
			return false;
		}
		commonService.openTab("1002","修改专线信息","/base/dedicatedQueryUpdate.html?tenantId="+selectedDate[0].tenantId);
	}
	};
	ord.init();
}]);