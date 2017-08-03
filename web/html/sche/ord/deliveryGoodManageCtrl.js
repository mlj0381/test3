var deliveryGoodManageApp = angular.module("deliveryGoodManageApp", ['commonApp']);
deliveryGoodManageApp.controller("deliveryGoodManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var deliveryGoodManage={
		init:function(){
			this.doQuery();
			this.bindEvent();
			/*this.selectCurrOrgId();
			this.desOrgData();*/
			
		},
		bindEvent:function(){
			$scope.param=this.param;
			$scope.doQuery=this.doQuery;
		    $scope.print=this.print;
		    $scope.toView=this.toView;
		    $scope.query=this.query;
		    $scope.clear=this.clear;
		    $scope.selectAll=this.selectAll;
		    $scope.selectOne=this.selectOne;
		    $scope.isTrue = false;
		    $scope.commonExport = this.commonExport;
		    $scope.paramsExport = "{}";
		    $scope.deleteDepart=this.deleteDepart;
		    $scope.doSave=this.doSave;
		    $scope.doModify=this.doModify;
		},
		params:{
		},
		query:{
		},
		doModify:function(){
			var data=$scope.page.getSelectData();
			if(data==null||data==undefined||data.length==0){
				commonService.alert("请选择一条送货信息!");
				return false;
			}
			if(data.length>1){
				commonService.alert("只能选择一条送货信息!");
				return false;
			}
			commonService.openTab(data[0].batchNum,"修改送货信息","/sche/ord/addDelivery.html?batchNum="+data[0].batchNum);
		},
		doSave:function(){
			commonService.openTab(batchNum,"新增送货信息","/sche/ord/addDelivery.html");
		},
		deleteDepart:function(){
			var data=$scope.page.getSelectData();
			if(data==null||data==undefined||data.length==0){
				commonService.alert("请至少选择一条送货信息!");
				return false;
			}
			commonService.confirm("批次号["+data[0].batchNum+"]是否进行[删除]操作,删除后数据将无法恢复!",function(){
				var param = {"batchNum":data[0].batchNum};
				var url = "deliveryGoodsBO.ajax?cmd=doDelete";
				commonService.postUrl(url,param,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
						commonService.alert("操作成功!");
						$scope.clear();
						$scope.doQuery();
		 	    	}
				});
			});
		},
		//导出方法
		commonExport : function(){
			$scope.page.downloadExcelFile();
		},
		/**全选*/
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
		/**列表查询*/
		doQuery:function(){
			deliveryGoodManage.params.batchNum=deliveryGoodManage.query.batchNum;
			deliveryGoodManage.params.beginTime=document.getElementById("beginTime").value;
			deliveryGoodManage.params.endTime=document.getElementById("endTime").value;
			var url = "deliveryGoodsBO.ajax?cmd=doQuery";
			$scope.tableCallBack=function(){
				setContentHegthDelay();
				 $scope.paramsExport = JSON.stringify(deliveryGoodManage.params);
			}
			
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:deliveryGoodManage.params,
							callBack:"$scope.tableCallBack"
						});
			},500);
		},
		/**清空查询条件*/
		clear:function(){
			document.getElementById("beginTime").value='';
			document.getElementById("endTime").value='';
			$scope.query.batchNum="";
		},
		/**选择一行**/
		selectOne : function(batchNum){
			if(document.getElementById("checkbox"+batchNum).checked && document.getElementById("checkbox"+batchNum) != undefined){
				document.getElementById("checkbox"+batchNum).checked=false;
			}else{
				document.getElementById("checkbox"+batchNum).checked=true;
			}
		},
		/**查看**/
		toView:function(){
			var data=$scope.page.getSelectData();
			if(data==null||data==undefined||data.length==0){
				commonService.alert("请选择一条送货信息!");
				return false;
			}
			if(data.length>1){
				commonService.alert("只能选择一条送货信息!");
				return false;
			}
			commonService.openTab(data[0].batchNum,"送货详情","/sche/ord/deliveryInfo.html?batchNum="+data[0].batchNum);
				},
		/**打印*/
		print:function(){
		}
	};
	
	deliveryGoodManage.init();
}]);
