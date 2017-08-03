function changeTabCallback(){
	var appElement = document.querySelector('[ng-controller=searchCtrl]');
	var scope=angular.element(appElement).scope();
	scope.doQuery();
	scope.$apply();
}
var searchApp = angular.module("searchApp", ['commonApp']);
searchApp.controller("searchCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var referrer = '';
	$scope.ruting={};
	$scope.query={};
	$scope.deliveryTypeList=[];
	$scope.shipmentNoticeList=[];
	$scope.typeList=[];
	$scope.smsShow=false;
	$scope.consigneeBillArry=[];
	$scope.consigneeObjArry=[];
	$scope.sms={};
	$scope.sysSmsTemplateT="";
	$scope.sysSmsTemplateL="";
	var search={
			init:function(){
				this.bindEvent();
				$scope.currOrgName = userInfo.orgName;
	        	$scope.currOrgId = userInfo.orgId;
	        	commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{"codeType":"DELIVERY_TYPE"},function(data){
					$scope.deliveryTypeList=data.items;
				});
	        	commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{"codeType":"SHIPMENT_NOTICE"},function(data){
					$scope.shipmentNoticeList=data.items;
				});
	        	commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{"codeType":"NOTIFY_TYPE"},function(data){
					$scope.typeList=data.items;
				});
	        	$scope.doQuery();
	        	this.getTemplate();
			},
			bindEvent:function(){
				$scope.selectOne=this.selectOne;
				$scope.chcAll=this.chcAll;
				$scope.doSaveNotify=this.doSaveNotify;
				$scope.doQuery=this.doQuery;
				$scope.clearn=this.clearn;
				$scope.sendMessage=this.sendMessage;
				$scope.params=this.params;
				$scope.doSms=this.doSms;
				$scope.colseSms=this.colseSms;
				$scope.delSms=this.delSms;
			},
			//查询
			doQuery:function(){
				$scope.query.distributionOrgId = userInfo.orgId;
				$scope.page.load({
					url:"orderInfoBO.ajax?cmd=querOrdNotify",
					params:$scope.query,
					callBack:"setContentHegthDelay"
		        });
			},	
			getTemplate:function(){
				commonService.postUrl("sysSmsSendBO.ajax?cmd=getTemplate","",function(data){
					if(data!=null && data!=undefined && data!=""){
						$scope.sysSmsTemplateT=data.sysSmsTemplateT;
						$scope.sysSmsTemplateL=data.sysSmsTemplateL;
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
			/**全选**/
			chcAll:function(){
	         	   var checkbox=document.getElementsByName("checkbox2"); 
	         	   if(document.getElementById("checkbox").checked){
	         		   for(var i=0;i<checkbox.length;i++){
	       	   	         checkbox[i].checked = true;
	           	      } 
	         	   }else{
	         		   for(var i=0;i<checkbox.length;i++){
	         	   	         checkbox[i].checked = false;
	         	   	         contactNumber = "";
	             	     } 
	         	   }
	            },
	          doSms:function(){
	        	  var selectedValue =$scope.page.getSelectData();
		 		  if(selectedValue.length<=0){
		 				commonService.alert("请至少选择一条运单信息!");
		 				return false;
		 		  }
	        	  var billArray = [];
	        	  var billObjArray = [];
	        	  var errStr = "";
	        	  for(var i=0;i<selectedValue.length;i++){
	        		  var data=selectedValue[i];
	        		  if(data.shipmentNotice=="1"){
	        			  errStr+=data.trackingNum+",";
	        		  }
	        		  if(data.consigneeBill!=null && data.consigneeBill!=undefined 
								&& data.consigneeBill!=""){
							if(billArray.toString().indexOf(data.consigneeBill)<0){
								billArray.push(data.consigneeBill);
								var obj =  new Object();
								obj.consigneeBill = data.consigneeBill;
								obj.orderId = data.orderId;
								obj.deliveryType = data.deliveryType;
								obj.shipmentNotice = data.shipmentNotice;
								obj.trackingNum = data.trackingNum;
								obj.goodsName = data.goodsName;
								billObjArray.push(obj);
							}
						}
	        	  }
	        	  if(errStr!=""){
	        		  commonService.alert("运单号"+errStr+"通知状态为[等通知放货]，不允许发送短信！");
	        		  return false;
	        	  }
	        	  $scope.consigneeBillArry=billArray;
	        	  $scope.consigneeObjArry=billObjArray;
	        	  $scope.smsShow=true;
	          },
	          colseSms:function(){
	        	  $scope.consigneeBillArry=[];
	        	  $scope.consigneeObjArry=[];
	        	  $scope.sms={};
	        	  $scope.smsShow=false;
	          },
	          delSms:function(index,o){
	        	  $scope.consigneeBillArry.splice(index, 1);
	        	  $scope.consigneeObjArry.splice(index, 1);
	          },
	          sendMessage:function(){
	        	  $scope.sms.bills = $scope.consigneeObjArry;
	        	  if($scope.sms.bills==null || $scope.sms.bills==undefined || $scope.sms.bills.length<=0){
	        		  commonService.alert("没有收货人的手机号码！");
	        		  return false;
	        	  }
	        	  var url = "sysSmsSendBO.ajax?cmd=doSearchMsm";
	        	  commonService.postUrl(url,$scope.sms,function(data){
	        		$scope.colseSms();
					commonService.alert("发送完成！");
	        	  });

	    	 },
	         doSaveNotify:function(){
	        	 var selectedValue =$scope.page.getSelectData();
	 			if(selectedValue.length<=0){
	 				commonService.alert("请选择一条运单信息");
	 				return false;
	 			}
	 			if(selectedValue.length>1){
	 				commonService.alert("只能选择一条运单信息");
	 				return false;
	 			}
	 			var flag=true;
				if(selectedValue[0].shipmentNotice=="1"){
					flag = false;
				}
    	         if(!flag){
    	        	commonService.alert("还没有通知放货不允许预约操作!");
    	        	return false;
    	         }
    	         commonService.openTab(selectedValue[0].orderId,"到货通知（预约送货）","/ord/notifyDtl.html?orderId="+selectedValue[0].orderId+"&needIngoreOrgId=");
    	         
	         },
	         clearn:function(){
	        	 $scope.query={"distributionOrgId":$scope.currOrgId,"deliveryType":"-1","shipmentNotice":"-1","type":-1};
	        	 document.getElementById("beginTime").value="";
				 document.getElementById("endTime").value="";
				 document.getElementById("schedulingDate").value="";
	         },
	};
	$scope.$watch('$viewContentLoaded', function() {
		search.init();
	});
	
}]);
