
/**
 * 新增物流公司登记信息
 */
addSysTenantRegisterApp = angular.module("addSysTenantRegisterApp", ['commonApp']);
addSysTenantRegisterApp.controller("addSysTenantRegisterCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var id = getQueryString("id");
	console.log(id);
	var tow={
			init:function(){
				this.bindEvent();
				if(id != null && id != undefined && id != "" ){
					this.queryById();
					
				}
			},
			bindEvent:function(){
				$scope.doSave = this.doSave;
				$scope.close = this.close;
				$scope.data = this.data;
			},
			data:{
//				remaek:""
//				logisticsName:""
//				linkName:""
//				telephone:""
//				address:""
				id:""
			},
			queryById:function(){
				var url = "sysTenantRegisterBO.ajax?cmd=queryById";
				var data = {};
				commonService.postUrl(url,"id="+id,function(data){
					if(data != null && data != undefined && data != ""){
						 console.log(data);
						  $scope.data = data;
						  $scope.data.remark=data.data.remark;
						  $scope.data.telephone = data.data.telephone;
						  $scope.data.linkName = data.data.linkName;
						  $scope.data.logisticsName =data.data.logisticsName;
						  $scope.data.address = data.data.address;
						  $scope.data.id = data.data.id;
						  $scope.show=true;
					}
					 					  
				});
			},
			
			doSave:function(){
				if($scope.data.logisticsName==null || $scope.data.logisticsName==undefined || $scope.data.logisticsName==""){
					commonService.alert("物流公司名称不能为空");
					return false;
				}
				if($scope.data.linkName==null || $scope.data.linkName==undefined || $scope.data.linkName==""){
					commonService.alert("联系人不能为空");
					return false;
				}
				if($scope.data.telephone==null || $scope.data.telephone==undefined || $scope.data.telephone==""){
					commonService.alert("联系电话不能为空");
					return false;
				}
				if($scope.data.address==null || $scope.data.address==undefined || $scope.data.address==""){
					commonService.alert("公司地址不能为空");
					return false;
				}
				var url="sysTenantRegisterBO.ajax?cmd=addSysTenantRegister";
				commonService.postUrl(url,$scope.data,function(data){
				commonService.alert("保存完成!",function(){
				commonService.closeToParentTab(true);
				});
			})
				
				
			},
			close:function(){
				commonService.closeToParentTab(true);
			}
	};
	tow.init();
}]);
