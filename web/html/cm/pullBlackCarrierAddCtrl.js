var pullBlackCarrierAddApp=angular.module("pullBlackCarrierAddApp",['commonApp']);
pullBlackCarrierAddApp.controller("pullBlackCarrierAddCtrl",["$scope","commonService",function($scope,commonService){
	var pullBlackCarrierAdd={
		init:function(){
			this.bindEvent();
		},
		bindEvent:function(){
			$scope.myKeyup=this.myKeyup;
			$scope.queryInfo=this.queryInfo;
			$scope.user=this.user;
			$scope.data=this.data;
			$scope.doSave=this.doSave;
			$scope.close=this.close;
	      },
	      data:{
	    	  bill:"",
	      },
		  user:{},
		 queryInfo:function(){
			 var url="pullBlackCarrierBO.ajax?cmd=doQueryPullBlackCarrierByAccount";
			 var dataBill=$scope.user.carrierAccount;
			 $scope.data.bill=dataBill;
			 commonService.postUrl(url,$scope.data,function(data){
				 $scope.user=data.item;
			 });
		 },  
		 
		  myKeyup:function(e){
	            var keycode = window.event?e.keyCode:e.which;
	            if(keycode==13){
	                $scope.queryInfo();
	            }
	        },
	        doSave:function(){
	        	var remark=$scope.user.remark;
	        	var userName=$scope.user.userName;
	        	var bill=$scope.user.carrierAccount;
	        	var userId=$scope.user.userId;
	        	var url="pullBlackCarrierBO.ajax?cmd=doSavePullBlackCarrierByAccount";
	        	var param={"remark":remark,"userName":userName,"bill":bill,"userId":userId};
	        	commonService.postUrl(url,param,function(data){
	        		commonService.alert("操作成功!");
	        		$scope.close();
	        	});
	        },
	        close:function(){
	        	commonService.closeToParentTab(true);
	        },
	};
	pullBlackCarrierAdd.init();
}]);