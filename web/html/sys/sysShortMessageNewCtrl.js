var templateId = getQueryString("templateId");
var yearAndMonth = getQueryString("yearAndMonth");
var proveManageNewApp = angular.module("sysShortMessageNewApp", ['commonApp']);
proveManageNewApp.controller("sysShortMessageNewCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	
	
	var proveManage={
		init:function(){
			this.bindEvent();
			
			this.doQuery();
		},
		bindEvent:function(){
			
			$scope.query = this.query;
			$scope.data = this.data;
		},
		query:{
			templateId :'',
			yearAndMonth :'',
		},
		data:{},
		
		doQuery:function(){
			proveManage.query.templateId=templateId;
			proveManage.query.yearAndMonth=yearAndMonth;
			//console.log("yearAndMonth="+yearAndMonth);输出测试数据
			var url = "shortMessageBO.ajax?cmd=allShortMessage";
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
		
		/*queryModule:function(){
			var url = "shortMessageBO.ajax?cmd=queryModule";
			commonService.postUrl(url,"",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.templateData= data;
					$scope.query.templateId="-1";
					//console.log($scope.templateData);
				}
			});
		},*/
	};
	proveManage.init();
}]);
