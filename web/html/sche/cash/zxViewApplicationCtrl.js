var toApp = angular.module("zxViewApplicationApp", ['commonApp']);
toApp.controller("zxViewApplicationCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
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
		bindEvent:function(){
			$scope.close=this.close;
			$scope.getApplicationByIdView = this.getApplicationByIdView;
			$scope.verification = this.verification;
			$scope.getComCashInfoByTentId=this.getComCashInfoByTentId;
		},
		verification:function(){
			var param = {"appId":$scope.id};
			var url = "cashManageBO.ajax?cmd=verification";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("处理成功!");
					$scope.close();
				}
				
			});
		},
		close:function(){
			commonService.closeToParentTab(true);
		},
		/**提现查看**/
		getApplicationByIdView:function(id){
			var param = {"id":id};
			var url = "cashManageBO.ajax?cmd=getApplicationById";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.auditView = data;
					$scope.getComCashInfoByTentId(data.ca.workerId);

				}
			});
		},
		getComCashInfoByTentId:function(tenantId){
			var param = {"serviceId":tenantId};
			var url = "cashManageBO.ajax?cmd=doQueryCom";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.sf=data;
				}
			});
		}
	};
	myManage.init();
}]);
