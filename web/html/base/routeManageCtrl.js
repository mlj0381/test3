var routeManageApp = angular.module("routeManageApp", ['commonApp']);
routeManageApp.controller("routeManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var routeManage={
		init:function(){
			this.doQuery();
			this.bindEvent();
			this.userData();
			this.queryOrg();
		},
		bindEvent:function(){
			$scope.param=this.param;
			$scope.doQuery=this.doQuery;
		    $scope.print=this.print;
		    $scope.toView=this.toView;
		    $scope.clear=this.clear;
		    $scope.selectAll=this.selectAll;
		    $scope.selectOne=this.selectOne;
		    $scope.query=this.query;
		    $scope.queryOrg=this.queryOrg;
		},
		params:{
			
		},
		query:{
		},
		queryOrg:function(){
			var param = {};
			var url = "staticDataBO.ajax?cmd=selectOrg";
			commonService.postUrl(url,param,function(data){
				// 成功执行
				if(data!=null && data!=undefined && data!=""){
						$scope.orgData=data;
						$scope.orgData.unshift({"orgId":-1,"orgName":"全部"});
						routeManage.query.endOrgId=data[0].orgId;
	 	    	}
			});
		},
		userData:function(){
			$scope.currOrgId=userInfo.orgId;
			$scope.currOrgName=userInfo.orgName;
		},
		/** 全选 */
		selectAll : function() {
			var checkbox = document.getElementsByName("check-1");
			if (document.getElementById("checkbox").checked) {
				for (var i = 0; i < checkbox.length; i++) {
					checkbox[i].checked = true;
				}
			} else {
				for (var i = 0; i < checkbox.length; i++) {
					checkbox[i].checked = false;
				}
			}
		},
		/** 列表查询 */
		doQuery:function(){
			routeManage.params.endOrgId=routeManage.query.endOrgId;
			var url = "routeBO.ajax?cmd=doQuery";
			routeManage.params.page=1;
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:routeManage.params
						});
			},500);
		},
		/** 清空查询条件 */
		clear:function(){
			$scope.query.endOrgId=-1;
		},
		/** 选择一行* */
		selectOne : function(towardsId){
			if(document.getElementById("checkbox"+towardsId).checked && document.getElementById("checkbox"+towardsId) != undefined){
				document.getElementById("checkbox"+towardsId).checked=false;
			}else{
				document.getElementById("checkbox"+towardsId).checked=true;
			}
		},
		/** 查看* */
		toView:function(){
			var towardsId='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条路由信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条路由信息!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					towardsId=data.towardsId;
				}
			});
			commonService.openTab(towardsId,"路由详情","/base/routeInfo.html?towardsId="+towardsId);
				},
	
	}
	routeManage.init();
}]);
