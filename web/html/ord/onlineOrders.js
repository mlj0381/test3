var onlineOrdersApp = angular.module("onlineOrdersApp", ['commonApp']);
onlineOrdersApp.controller("onlineOrdersCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.queryParam={};
	$scope.singlesStsList=[];
	$scope.lineOrgIdList=[];
	var onlineOrders={
		init:function(){
			this.bindEvent();
			this.getSinglesSts();
			this.getLineOrgId();
			this.doQuery();
		},
		bindEvent:function(){
			$scope.doQuery=this.doQuery;
			$scope.clear=this.clear;
			$scope.doMeet=this.doMeet;
			$scope.clearMeet=this.clearMeet;
			$scope.turnWaybill=this.turnWaybill;
			$scope.meetInfo=this.meetInfo;
		},
		getSinglesSts:function(){
			var url = "staticDataBO.ajax?cmd=selectSysStaticDataByCodeType";
			commonService.postUrl(url,{"codeType":"ORD_SELLER_ORDER@SINGLES_STS"},function(data){
				$scope.singlesStsList=data.items;
        	});
		},
		getLineOrgId:function(){
			var url = "ordTransitOutgoingBO.ajax?cmd=getCarrierOrgId";
			commonService.postUrl(url,"",function(data){
				$scope.lineOrgIdList=data.items;
        	});
		},
		doQuery:function(){
			var url="ordSellerOrderBO.ajax?cmd=meetSingleList";
			$scope.page.load({
				url:url,
				params:$scope.queryParam
			});
			setContentHegthDelay();
		},
		clear:function(){
			$scope.queryParam={"carrierOrgId":-1,"singlesSts":-1,"createDate":""};
			document.getElementById("createDate").value="";
		},
		doMeet:function(){
			var selectedDate=$scope.page.getSelectData();
			if(selectedDate==undefined || selectedDate.length==0 ){
				commonService.alert("请选择数据！");
				return ;
			}
			var sellerOrderIds="";
			var err="";
			for(var i=0;i<selectedDate.length;i++){
				if(i==0){
					sellerOrderIds+=selectedDate[i].SELLER_ORDER_ID;
				}else{
					sellerOrderIds+=","+selectedDate[i].SELLER_ORDER_ID;
				}
				if(selectedDate[i].SINGLES_STS==1){
					err+=selectedDate[i].ORDER_ID+",";
				}
			}
			if(err!=""){
				err+="已经接单，不允许操作！";
				commonService.alert(err);
				return ;
			}
			commonService.confirm("是否确认接单？",function(){
				var url = "ordSellerOrderBO.ajax?cmd=batchMeetSing";
				var param = {"singlesSts":"1","sellerOrderIds":sellerOrderIds};
				commonService.postUrl(url,param,function(data){
					commonService.alert("操作成功！");
					$scope.doQuery();
	        	});
			});
		},
		clearMeet:function(){
			var selectedDate=$scope.page.getSelectData();
			if(selectedDate==undefined || selectedDate.length==0 ){
				commonService.alert("请选择数据！");
				return ;
			}
			var sellerOrderIds="";
			var err="";
			var error="";
			for(var i=0;i<selectedDate.length;i++){
				if(i==0){
					sellerOrderIds+=selectedDate[i].SELLER_ORDER_ID;
				}else{
					sellerOrderIds+=","+selectedDate[i].SELLER_ORDER_ID;
				}
				if(selectedDate[i].outgoing_tracking_num!=null && selectedDate[i].outgoing_tracking_num!=undefined && selectedDate[i].outgoing_tracking_num!=""){
					error+=selectedDate[i].TRACKING_NUM+",";
				}
				if(selectedDate[i].SINGLES_STS==0){
					err+=selectedDate[i].ORDER_ID+",";
				}
			}
			if(err!=""){
				err+="已经取消，不允许操作！";
				commonService.alert(err);
				return ;
			}
			if(error!=""){
				error+="已经生成运单不能取消！";
				commonService.alert(error);
				return ;
			}
			commonService.confirm("是否取消接单？",function(){
				var url = "ordSellerOrderBO.ajax?cmd=batchMeetSing";
				var param = {"singlesSts":"0","sellerOrderIds":sellerOrderIds};
				commonService.postUrl(url,param,function(data){
					commonService.alert("操作成功！");
					$scope.doQuery();
	        	});
			});
		},
		turnWaybill:function(){
			var selectedDate=$scope.page.getSelectData();
			if(selectedDate==undefined || selectedDate.length==0 || selectedDate.length>1){
				commonService.alert("请选择一条数据！");
				return ;
			}
			selectedDate=selectedDate[0];
			if(selectedDate.SINGLES_STS!=1){
				commonService.alert("请“接单”后再操作“订单转运单”");
				return ;
			}
			if(selectedDate.outgoing_tracking_num!=null && selectedDate.outgoing_tracking_num!=undefined && selectedDate.outgoing_tracking_num!=""){
				commonService.alert("已经生成运单，不允许操作！”");
				return ;
			}
			commonService.confirm("是否订单转运单？",function(){
				commonService.openTab(selectedDate.SELLER_ORDER_ID,"订单转运单","/ord/billing.html?sellerOrderId="+selectedDate.SELLER_ORDER_ID);
			});
		},
		meetInfo:function(){
			var selectedDate=$scope.page.getSelectData();
			if(selectedDate==undefined || selectedDate.length==0 || selectedDate.length>1){
				commonService.alert("请选择一条数据！");
				return ;
			}
			selectedDate=selectedDate[0];
			if(selectedDate.SINGLES_STS!=1){
				commonService.alert("请“接单”后再操作“运单详情”");
				return ;
			}
			if(selectedDate.outgoing_tracking_num==null || selectedDate.outgoing_tracking_num==undefined || selectedDate.outgoing_tracking_num==""){
				commonService.alert("请“订单转运单”后再操作“运单详情”");
				return ;
			}
			commonService.openTab(selectedDate.SELLER_ORDER_ID,"运单详情","/ord/ordBillingDetail.html?view=1&trackingNum="+selectedDate.outgoing_tracking_num);
		}
	};
	$scope.$watch('$viewContentLoaded', function() {
		onlineOrders.init();
    });
}]);