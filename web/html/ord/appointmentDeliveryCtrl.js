var appointmentDeliveryApp = angular.module("appointmentDeliveryApp", ['commonApp']);
appointmentDeliveryApp.controller("appointmentDeliveryCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.queryParam={};
	$scope.fromData={};
	$scope.state=[];
	$scope.periodType=[];
	$scope.isFlag=1;
	var appointmentDelivery={
		init:function(){
			this.getState();
			this.getPeriodType();
			this.doQuery();
			this.bindEvent();
		},
		bindEvent:function(){
			$scope.doQuery=this.doQuery;
			$scope.doAdd=this.doAdd;
			$scope.doCancel=this.doCancel;
			$scope.add=this.add;
			$scope.getPeriodType=this.getPeriodType;
			$scope.doUpdata=this.doUpdata;
			$scope.doSee=this.doSee;
			$scope.doReset=this.doReset;
		},
		getState:function(){
			var url = "staticDataBO.ajax?cmd=getOrdSellerNotifyState";
			commonService.postUrl(url,"",function(data){
				$scope.state=data.items;
        	});
		},
		getPeriodType:function(){
			var url = "staticDataBO.ajax?cmd=getOrdSellerNotifyPeriodType";
			commonService.postUrl(url,"",function(data){
				$scope.periodType=data.items;
        	});
		},
		doQuery:function(){
			var url="ordSellerNotifyBO.ajax?cmd=doQueryPage";
			$scope.page.load({
				url:url,
				params:$scope.queryParam
			});
			setContentHegthDelay();
		},
		doAdd:function(){
			var url = "ordSellerNotifyBO.ajax?cmd=doAdd";
			commonService.postUrl(url,"",function(data){
				$scope.fromData.orgContactor=data.orgPrincipal;
				$scope.fromData.orgTel=data.orgPrincipalPhone;
				$scope.fromData.orgAddress=data.departmentAddress;
        	});
			$scope.isFlag=2;
		},
		add:function(){
			if($scope.fromData.orgContactor==null || $scope.fromData.orgContactor==undefined || $scope.fromData.orgContactor==""){
				commonService.alert("请输入联系人！");
				return ;
			}
			if($scope.fromData.orgTel==null || $scope.fromData.orgTel==undefined || $scope.fromData.orgTel==""){
				commonService.alert("请输入联系号码！");
				return ;
			}
			if($scope.fromData.pickupDate==null || $scope.fromData.pickupDate==undefined || $scope.fromData.pickupDate==""){
				commonService.alert("请输入预约提货时间！");
				return ;
			}
			var url = "ordSellerNotifyBO.ajax?cmd=save";
			commonService.postUrl(url,$scope.fromData,function(data){
				commonService.alert("操作成功！");
				$scope.doCancel();
				$scope.doQuery();
        	});
		},
		doCancel:function(){
			$scope.isFlag=1;
			$scope.fromData={"periodType":1};
			$scope.doQuery();
		},
		doUpdata:function(){
			var selectedDate=$scope.page.getSelectData();
			if(selectedDate==undefined || selectedDate.length==0 || selectedDate.length>1){
				commonService.alert("请选择一条数据！");
				return ;
			}
			selectedDate=selectedDate[0];
			if(selectedDate.STATE==3){
				commonService.alert("已经提货不可以修改！");
				return ;
			}
			var url = "ordSellerNotifyBO.ajax?cmd=doQuery";
			var param = {"notifyId":selectedDate.NOTIFY_ID};
			commonService.postUrl(url,param,function(data){
				$scope.fromData=data;
				$scope.isFlag=2;
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
		appointmentDelivery.init();
    });
}]);