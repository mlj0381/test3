var shipmentNoticeApp = angular.module("shipmentNoticeApp", ['commonApp']);
shipmentNoticeApp.controller("shipmentNoticeCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var referrer = '';
	$scope.ruting={};
	$scope.query={};
	$scope.distriOrgIdList=[];
	$scope.deliveryTypeList=[];
	$scope.shipmentNoticeList=[];
	var ordSign={
			init:function(){
				this.bindEvent();
				$scope.currOrgName = userInfo.orgName;
	        	$scope.currOrgId = userInfo.orgId;
	        	$scope.query.orgId=$scope.currOrgId;
	        	$scope.query.shipmentNotice=1;
				commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{"codeType":"DELIVERY_TYPE"},function(data){
					$scope.deliveryTypeList=data.items;
				});
				commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{"codeType":"SHIPMENT_NOTICE"},function(data){
					$scope.shipmentNoticeList=data.items;
				});
				this.selectDistriOrgId();
				this.doQuery();
			},
			bindEvent:function(){
				$scope.orderNotify=this.orderNotify;
				$scope.selectOne=this.selectOne;
				$scope.chcAll=this.chcAll;
				$scope.doSaveSign=this.doSaveSign;
				$scope.doQuery=this.doQuery;
				$scope.informGood=this.informGood;
				$scope.clearn=this.clearn;
			},
			//查询
			doQuery:function(){
	        	$scope.query.orgId = userInfo.orgId;
				$scope.page.load({
					url:"orderInfoBO.ajax?cmd=queryOrdOrderInfo",
					params:$scope.query,
					callBack:"setContentHegthDelay"
		        });
			},
			clearn:function(){
				 document.getElementById("beginTime").value="";
				 document.getElementById("endTime").value="";
				 $scope.query={"distributionOrgId":"-1","deliveryType":"-1","shipmentNotice":"1","orgId":$scope.currOrgId};
	        },
			selectDistriOrgId:function(){
				commonService.postUrl("routeBO.ajax?cmd=queryRoateTowards","",function(data){
					if(data != null && data != undefined && data != ""){
						$scope.distriOrgIdList = data.items;
					}
				});
			},
			/**选择一行**/
			selectOne : function(orderId){
				if(document.getElementById("checkbox"+orderId).checked && document.getElementById("checkbox"+orderId) != undefined){
					document.getElementById("checkbox"+orderId).checked=false;
				}else{
					document.getElementById("checkbox"+orderId).checked=true;
				}
			},
			informGood:function(){
	        	 var chk_value =[];//定义一个数组    
    	         $('input[name="checkbox2"]:checked').each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数    
    	        	 chk_value.push($(this).attr("attr"));//将选中的值添加到数组chk_value中    
    	         });
    	         if(chk_value.length==0){
    	        	commonService.alert("请选择数据");
						return;
    	         } 
    	         if(chk_value.length>1){
    	        	 commonService.alert("只能选择一行数据");
				     return;
    	         }
    	         var ordOrders= eval('(' + chk_value[0] + ')');
    	         if(ordOrders.releaseNote!=1){
    	        	 commonService.alert("不是等通知放货的运单,不能通知放货");
    	        	 return;
    	         }
    	         commonService.openTab(ordOrders.orderId,"通知放货","/ord/shipmentNoticeDtl.html?orderId="+ordOrders.orderId);
    	         //location.href="shipmentNoticeDtl.html?orderId="+orderId;
	         }
	};
	$scope.$watch('$viewContentLoaded', function() {
		ordSign.init();
	});
	
}]);