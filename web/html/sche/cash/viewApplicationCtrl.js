var toApp = angular.module("viewApplicationApp", ['commonApp']);
toApp.controller("viewApplicationCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var service=getQueryString("service");
	var myManage={
		init:function(){
			$scope.query = {};
			$scope.add = {};
			$scope.audit = {};
			$scope.auditView = {};
			this.bindEvent();
			 $scope.id = getQueryString("id");
			$scope.isView = getQueryString("isView");
			if( $scope.id!=''&& $scope.id!=undefined&& $scope.id!=null){
				$scope.getApplicationByIdView( $scope.id);
			}
		},
		close:function(){
			commonService.closeToParentTab(true);
		},
		bindEvent:function(){
			$scope.close=this.close;
		    $scope.getApplicationByIdView = this.getApplicationByIdView;
		    $scope.verification=this.verification;
		    $scope.getCashInfoByPhone=this.getCashInfoByPhone;
		    $scope.getComCashInfoByTentId=this.getComCashInfoByTentId;
		},
		/**提现查看**/
		getApplicationByIdView:function(id){
			var param = {"id":id};
			var url = "cashManageBO.ajax?cmd=getApplicationById";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.auditView = data;
					console.info(data);
					if(service==1){
						$scope.getComCashInfoByTentId(data.ca.gscode);
					}else{
						$scope.getCashInfoByPhone(data.ca.workerLoginAcct);
					}
				}
			});
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
		/**申请人是合作商*/
		getComCashInfoByTentId:function(tenantId){
			var param = {"serviceId":tenantId};
			var url = "cashManageBO.ajax?cmd=doQueryCom";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.sf=data;
				}
			});
		},
		getCashInfoByPhone:function(phone){
			var param = {"phone":phone};
			var url = "cashManageBO.ajax?cmd=doQuerySf";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.sf = data;
				}
			});
		}
	};
	myManage.init();
}]);
