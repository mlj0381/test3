
var proveManageNewApp = angular.module("sysShortCtrlApp", ['commonApp']);
proveManageNewApp.controller("sysShortCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	
	var proveManage={
		init:function(){
			this.bindEvent();
			this.queryModule();
		},
		bindEvent:function(){
			$scope.queryModule=this.queryModule;
			$scope.doQuery = this.doQuery;
			$scope.toShow = this.toShow;
			$scope.query = this.query;
		
		},
		query:{
			templateId :"",
			yearAndMonth :"",
		},
		
		
		doQuery:function(){
			if($scope.query.yearAndMonth == null || $scope.query.yearAndMonth == undefined || $scope.query.yearAndMonth == ""){
				commonService.alert("请选择月份");
				return;
			}
			
	        
				var url = "shortMessageBO.ajax?cmd=queryMessage";
				var yearAndMonth=$scope.query.yearAndMonth;
				var y = yearAndMonth.substring(0,4);
				var m = yearAndMonth.substring(5,7);
				var result = y+m;
				if(result<"201610"){
					commonService.alert("最小月份是2016年10月");
					return;
				}
				$scope.tableCallBack=function(){
					setContentHegthDelay();
				};
				
				$timeout(function(){
					$scope.page.load({
								url:url,
								params:proveManage.query,
								callBack:"$scope.tableCallBack"
							});
				},500);
	       
		},
		
		
		toShow:function(tempId){
			var yearAndMonth=$scope.query.yearAndMonth;
			var y = yearAndMonth.substring(0,4);
			var m = yearAndMonth.substring(5,7);
			var ym= y+m;
			commonService.openTab("21212303","短信明细","/sys/sysShortMessageNew.html?templateId="+tempId+"&yearAndMonth="+ym);
		},
		
		queryModule:function(){
			var url = "shortMessageBO.ajax?cmd=queryModule";
			commonService.postUrl(url,"",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.templateData= data;
					$scope.query.templateId="-1";
					//console.log($scope.templateData);
				}
			});
		},
		
	};
	proveManage.init();
}]);
