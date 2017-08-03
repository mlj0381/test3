function changeTabCallback(){
	var appElement = document.querySelector('[ng-controller=ordSignCtrl]');
	var scope=angular.element(appElement).scope();
	scope.doQuery();
	scope.$apply();
}
var ordSignApp = angular.module("ordSignApp", ['commonApp']);
ordSignApp.controller("ordSignCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var referrer = '';
	$scope.ruting={};
	$scope.startOrgData=[];
	$scope.deliveryTypeList=[];
	$scope.signStatusList=[];
	$scope.query={};
	var ordSign={
			init:function(){
				$scope.currOrgName = userInfo.orgName;
	        	$scope.currOrgId = userInfo.orgId;
	        	$scope.query.distributionOrgId=$scope.currOrgId;
				this.bindEvent();
				commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{"codeType":"DELIVERY_TYPE"},function(data){
					$scope.deliveryTypeList=data.items;
				});
				commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{"codeType":"SIGN_STATUS"},function(data){
					$scope.signStatusList=data.items;
				});
				this.desOrgData();
				this.doQuery();
			},
			bindEvent:function(){
				$scope.orderNotify=this.orderNotify;
				$scope.selectOne=this.selectOne;
				$scope.chcAll=this.chcAll;
				$scope.doSaveSign=this.doSaveSign;
				$scope.doQuery=this.doQuery;
				$scope.clearn=this.clearn;
				
			},
			desOrgData:function(){
				var param = {"endOrgId":userInfo.orgId};
				var url = "routeBO.ajax?cmd=getTowards";	
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
					    $scope.startOrgData=data.items;
		 	    	}
				});
			},
			//查询
			doQuery:function(){
				$scope.page.load({
					url:"orderInfoBO.ajax?cmd=querySign",
					params:$scope.query,
					callBack:"setContentHegthDelay"
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
			/**签收录入**/
	         doSaveSign:function(){
	        	 var data;
//	        	 var chk_value =[];//定义一个数组    
//   	         $('input[name="checkbox2"]:checked').each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数    
//   	        	 data=eval("("+$(this).val()+")");
//   	        	 chk_value.push(data.orderId);//将选中的值添加到数组chk_value中    
//   	         });
//   	         if(chk_value.length==0){
//   	        	commonService.alert("请选择一行数据");
//						return;
//   	         } 
//   	         if(chk_value.length>1){
//   	        	 commonService.alert("只能选择一行数据");
//				     return;
//   	         }
   	         
   	         var selectedValue =$scope.page.getSelectData();
   			 if(selectedValue.length<=0){
   				 commonService.alert("请选择一条运单信息");
   				 return false;
   			 }
   			 if(selectedValue.length>1){
   				 commonService.alert("只能选择一条运单信息");
   				 return false;
   			  }
   			 data =  selectedValue[0];
   	         if(data.releaseNote==1 && data.shipmentNotice==1){
   	        	 commonService.alert("需要等放货通知后才能操作！");
				     return;
   	         }
   	        commonService.openTab(data.orderId,"签收录入","/ord/ordSignDtl.html?orderId="+data.orderId);
                //location.href="ordSignDtl.html?orderId="+orderId;
	         },
	         clearn:function(){
	        	 $scope.query={"orgId":"-1","deliveryType":"-1","signStatus":"-1","distributionOrgId":$scope.currOrgId};
	        	 document.getElementById("beginTime").value="";
				 document.getElementById("endTime").value="";
	         }

	};
	$scope.$watch('$viewContentLoaded', function() {
		ordSign.init();
	});
}]);