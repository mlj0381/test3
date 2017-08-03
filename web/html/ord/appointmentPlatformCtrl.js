var appointmentPlatformApp = angular.module("appointmentPlatformApp", ['commonApp']);
appointmentPlatformApp.controller("appointmentPlatformCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.queryParam={};
	$scope.state=[];
	$scope.fromData={};
	$scope.isFlag=1;
	$scope.notifyIds="";
	$scope.ordFromData={};
	var appointmentPlatform={
		init:function(){
			this.getState();
			this.doQuery();
			this.bindEvent();
		},
		bindEvent:function(){
			$scope.doQuery=this.doQuery;
			$scope.doAdd=this.doAdd;
			$scope.add=this.add;
			$scope.doCancel=this.doCancel;
			$scope.doUpdata=this.doUpdata;
			$scope.update=this.update;
			$scope.doFinish=this.doFinish;
			$scope.doSee=this.doSee;
			$scope.doReset=this.doReset;
		},
		getState:function(){
			var url = "staticDataBO.ajax?cmd=getOrdSellerNotifyState";
			commonService.postUrl(url,"",function(data){
				$scope.state=data.items;
        	});
		},
		doQuery:function(){
			var url="ordSellerNotifyBO.ajax?cmd=doQueryPageAll";
			$scope.page.load({
				url:url,
				params:$scope.queryParam
			});
			setContentHegthDelay();
		},
		doAdd:function(){
			var selectedDate=$scope.page.getSelectData();
			if(selectedDate==undefined || selectedDate.length==0){
				commonService.alert("请选择一条数据！");
				return ;
			}
			var notifyIds = "";
			for(var i=0;i<selectedDate.length;i++){
				if(selectedDate[i].STATE!=1){
					commonService.alert("您已经做了提货操作！");
					return ;
				}
				if(i==0){
					notifyIds+=selectedDate[i].NOTIFY_ID;
				}else{
					notifyIds+=","+selectedDate[i].NOTIFY_ID;
				}
			}
			$scope.notifyIds = notifyIds;
			$scope.isFlag=2;
		},
		add:function(){
			if($scope.fromData.contactor==null || $scope.fromData.contactor==undefined || $scope.fromData.contactor==""){
				commonService.alert("请输入提货人！");
				return ;
			}
			if($scope.fromData.driverPhone==null || $scope.fromData.driverPhone==undefined || $scope.fromData.driverPhone==""){
				commonService.alert("请输入联系人手机！");
				return ;
			}
			if($scope.fromData.platenumber==null || $scope.fromData.platenumber==undefined || $scope.fromData.platenumber==""){
				commonService.alert("请输入车牌号！");
				return ;
			}
			if($scope.fromData.finishDate==null || $scope.fromData.finishDate==undefined || $scope.fromData.finishDate==""){
				commonService.alert("请输入提货时间！");
				return ;
			}
			if($scope.notifyIds!=""){
				$scope.fromData.notifyIds=$scope.notifyIds;
			}else{
				commonService.alert("没有选择数据！");
				return ;
			}
			var url = "ordSellerNotifyBO.ajax?cmd=pickGoods";
			commonService.postUrl(url,$scope.fromData,function(data){
				commonService.alert("操作成功！");
				$scope.doQuery();
				$scope.doCancel();
        	});
		},
		doCancel:function(){
			$scope.notifyIds="";
			$scope.fromData={};
			$scope.isFlag=1;
		},
		doUpdata:function(){
			var selectedDate=$scope.page.getSelectData();
			if(selectedDate==undefined || selectedDate.length==0 || selectedDate.length>1){
				commonService.alert("请选择一条数据！");
				return ;
			}
			selectedDate=selectedDate[0];
			if(selectedDate.STATE!=2){
				commonService.alert("提货状态在“提货中”的，才可以操作！");
				return ;
			}
			var url = "ordSellerNotifyBO.ajax?cmd=doQuery";
			var param = {"notifyId":selectedDate.NOTIFY_ID};
			commonService.postUrl(url,param,function(data){
				$scope.ordFromData=data;
				$scope.isFlag=3;
        	});
		},
		update:function(){
			if($scope.ordFromData.contactor==null || $scope.ordFromData.contactor==undefined || $scope.ordFromData.contactor==""){
				commonService.alert("请输入提货人！");
				return ;
			}
			if($scope.ordFromData.driverPhone==null || $scope.ordFromData.driverPhone==undefined || $scope.ordFromData.driverPhone==""){
				commonService.alert("请输入联系人手机！");
				return ;
			}
			if($scope.ordFromData.platenumber==null || $scope.ordFromData.platenumber==undefined || $scope.ordFromData.platenumber==""){
				commonService.alert("请输入车牌号！");
				return ;
			}
			if($scope.ordFromData.finishDate==null || $scope.ordFromData.finishDate==undefined || $scope.ordFromData.finishDate==""){
				commonService.alert("请输入提货时间！");
				return ;
			}
			$scope.ordFromData.notifyIds=$scope.ordFromData.notifyId;
			var url = "ordSellerNotifyBO.ajax?cmd=pickGoods";
			commonService.postUrl(url,$scope.ordFromData,function(data){
				commonService.alert("操作成功！");
				$scope.doQuery();
				$scope.doCancel();
        	});
		},
		doFinish:function(){
			var selectedDate=$scope.page.getSelectData();
			if(selectedDate==undefined || selectedDate.length==0){
				commonService.alert("请选择一条数据！");
				return ;
			}
			var notifyIds = "";
			for(var i=0;i<selectedDate.length;i++){
				if(selectedDate[i].STATE!=2){
					commonService.alert("提货状态在“提货中”的，才可以操作！");
					return ;
				}
				if(i==0){
					notifyIds+=selectedDate[i].NOTIFY_ID;
				}else{
					notifyIds+=","+selectedDate[i].NOTIFY_ID;
				}
			}
			commonService.confirm("是否完成提货？",function(){
				var url = "ordSellerNotifyBO.ajax?cmd=patchSaveState";
				var param = {"notifyIds":notifyIds};
				commonService.postUrl(url,param,function(data){
					commonService.alert("操作成功！");
					$scope.doQuery();
					$scope.doCancel();
	        	});
			});
		},
		doSee:function(){
			var selectedDate=$scope.page.getSelectData();
			if(selectedDate==undefined || selectedDate.length==0 || selectedDate.length>1){
				commonService.alert("请选择一条数据！");
				return ;
			}
			selectedDate=selectedDate[0];
			var url = "ordSellerNotifyBO.ajax?cmd=doQuery";
			var param = {"notifyId":selectedDate.NOTIFY_ID};
			commonService.postUrl(url,param,function(data){
				$scope.ordFromData=data;
				$scope.isFlag=4;
        	});
		},
		doReset:function(){
			$scope.queryParam={"state":1,"pickupDate":"","createDate":""};
			document.getElementById("pickupDate").value="";
			document.getElementById("createDate").value="";
		}
	};
	$scope.$watch('$viewContentLoaded', function() {
		appointmentPlatform.init();
    });
}]);