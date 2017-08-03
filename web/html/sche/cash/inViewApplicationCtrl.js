var toApp = angular.module("inViewApplicationApp", ['commonApp']);
toApp.controller("inViewApplicationCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var myManage={
		init:function(){
			$scope.query = {};
			$scope.add = {};
			$scope.audit = {};
			$scope.auditView = {};
			this.bindEvent();
			$scope.id = getQueryString("id");
			$scope.isView = getQueryString("isView");
			if($scope.id!=''&&$scope.id!=undefined&&$scope.id!=null){
				$scope.getApplicationByIdView($scope.id);
			}
		},
		close:function(){
			commonService.closeToParentTab(true);
		},
		bindEvent:function(){
			$scope.close=this.close;
		    $scope.getApplicationByIdView = this.getApplicationByIdView;
		    $scope.verification=this.verification;
		},
		/**提现查看**/
		getApplicationByIdView:function(id){
			var param = {"id":id};
			var url = "cashManageBO.ajax?cmd=getApplicationById";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.auditView = data;
					var param = {"serviceId":data.ca.gscode};
					var url = "cashManageBO.ajax?cmd=doQueryCom";
					commonService.postUrl(url,param,function(data){
						//成功执行
						if(data!=null && data!=undefined && data!=""){
							$scope.sf=data;
						}
					});
				}
			});
		},
		verification:function(){
			var param = {"appId":$scope.id,"verityType":2};
			var url = "cashManageBO.ajax?cmd=verification";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("处理成功!");
					$scope.close();
				}
				
			});
		}
	};
	myManage.init();
}]);
