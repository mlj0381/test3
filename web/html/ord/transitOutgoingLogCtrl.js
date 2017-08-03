var orderId=getQueryString("orderId");
var toView=getQueryString("toView");//1、查看详情
var transitOutgoingLogApp = angular.module("transitOutgoingLogApp", ['commonApp']);
transitOutgoingLogApp.controller("transitOutgoingLogCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var billIngManage={
		init:function(){
			this.userData();
			this.bindEvent();
			this.doQuery();
			//this.doDetail();
		},
		bindEvent:function(){
			$scope.param=this.param;
			$scope.notify=this.notify;
			$scope.doQuery=this.doQuery;
			$scope.doSave=this.doSave;
		    $scope.query=this.query;
		    $scope.clear=this.clear;
		    $scope.selectAll=this.selectAll;
		    $scope.selectOne=this.selectOne;
		    $scope.upNum = this.upNum;
		    $scope.outGoingData=this.outGoingData;
		    $scope.isTrue = false;
		    $scope.doSaveOrdTrackingInfo=this.doSaveOrdTrackingInfo;
		    $scope.doClear=this.doClear;
		    $scope.doDel=this.doDel;
		    $scope.close=this.close;
		    $scope.doDetail=this.doDetail;
		    $scope.doEnd=this.doEnd;
		    $scope.paramsExport = "{}";
		},
		
		upNum:function(valueName){
			var value = eval("$scope."+valueName).replace(/[^\d]/g, '');
			eval("$scope."+valueName+"=value");
		},
		query:{
	
		},
		param:{},
		notify:{},
		outGoingData:{},
		userData:function(){
			$scope.currOrgId=userInfo.orgId;
			$scope.currOrgName=userInfo.orgName;
			
		},
		/**全选*/
		selectAll : function() {
			var checkbox = document.getElementsByName("check-1");
			if (document.getElementById("checkbox").checked) {
				for (var i = 0; i < checkbox.length; i++) {
					checkbox[i].checked = true;
				}
			} else {
				for (var i = 0; i < checkbox.length; i++) {
					checkbox[i].checked = false;
				}
			}
		},
		/**订单列表查询*/
		doQuery:function(){
			var url = "orderInfoBO.ajax?cmd=doQueryTracking";
			$scope.param.orderId=orderId;
			commonService.postUrl(url,$scope.param,function(data){
				$scope.trackingInfo= data.trackingList;
				 $scope.doDetail();
			});
		},
		/**
		 * 保存运单跟踪信息
		 */
		doSaveOrdTrackingInfo:function(){
			var url = "orderInfoBO.ajax?cmd=doSaveTracking";
			$scope.param.orderId=orderId;
			commonService.postUrl(url,$scope.param,function(data){
				if (undefined != data && null!=data) {
					$scope.doQuery();
					commonService.alert("保存完成");
				}
			});
		},
		/**
		 * 删除运单跟踪信息
		 */
		doDel:function(trackingId){
			var url = "orderInfoBO.ajax?cmd=doDelTracking";
			$scope.param.trackingId=trackingId;
			commonService.postUrl(url,$scope.param,function(data){
				$scope.doQuery();
				commonService.alert("删除完成");
			});
		},
		doClear:function(){
			$scope.param.content="";
		},
		/**清空查询条件*/
		clear:function(){

		},
		doDetail:function(){
			var url = "orderInfoBO.ajax?cmd=doQueryOrdTrackingDetail";
			$scope.param.orderId=orderId;
			$scope.isAble=true;
			//查看详情
            if(toView!=null && toView!=undefined && toView==1){
            	$scope.isAble=false;
            	$scope.isShow=false;
            	$scope.check1=true;
            	$scope.check2=true;
            	$scope.check3=true;
            	$scope.check4=true;
            	$scope.check35=true;
            }else{
            	$scope.isAble=true;
            	$scope.isShow=true;
            }
			commonService.postUrl(url,$scope.param,function(data){
				if(data!=null && data!=undefined && data.detailList.length>0){
	                $scope.query=data.detailList[0];
	                data.detailList.arrGoodsTime;
	                if($scope.query.isArrGoods==1){
	                	document.getElementById("check-1").checked=true;
	                    $scope.check2=true;
	                    $scope.check1=true;
	                }
	                
	                
					if(data.detailList[0].signType!=null && data.detailList[0].signType!=undefined && data.detailList[0].signType>0){                
					    $scope.query.signType=data.detailList[0].signType+"";
					}
					if(data.detailList[0].signCertificateType!=null && data.detailList[0].signCertificateType!=undefined && data.detailList[0].signCertificateType>0){
						$scope.query.signCertificateType=data.detailList[0].signCertificateType+"";
					}
					if(data.detailList[0].signState!=null && data.detailList[0].signState!=undefined && data.detailList[0].signState==1){
						document.getElementById("check-3").checked=true;
						$scope.check3=true;
					}
					if((data.detailList[0].isReceipt!=null && data.detailList[0].isReceipt!=undefined && data.detailList[0].isReceipt)
							|| (data.isTracling!=null && data.isTracling!=undefined && data.isTracling==1)){
						document.getElementById("check-35").checked=true;
						$scope.check35=true;
					}
					if($scope.query.isTracking!=null && $scope.query.isTracking!=undefined && $scope.query.isTracking==1){
						$scope.isAble=false;
					}else{
						$scope.isAble=true;	
					}
				}
			});
		},
		/**网点列表查询*/
		/**选择一行**/
		selectOne : function(){
			if(document.getElementById("check-3").checked && document.getElementById("check-3") != undefined){
				document.getElementById("check-3").checked=true;
				document.getElementById("check-35").checked=true;
			}else{
				document.getElementById("check-3").checked=false;
				document.getElementById("check-35").checked=false;
			}
		},
		//返回
		close:function(){
			commonService.closeToParentTab(true);	
		},
		//跟踪
		doEnd:function(){
			$scope.query.isTracking=1;
			$scope.doSave();
			 $scope.check2=true;
             $scope.check1=true;
             $scope.check3=true;
             $scope.check35=true;
             $scope.isAble=false;
		},
		//保存
		doSave:function(){
			//当勾选已到货
			$scope.query.starVehicleTime = document.getElementById("starVehicleTime").value;
			$scope.query.expectDate = document.getElementById("expectDate").value;
			if($scope.query.starVehicleTime==null ||  $scope.query.starVehicleTime==undefined || $scope.query.starVehicleTime==""){
		    	commonService.alert("请填写发运时间");
		    	return;
		  }
			if($scope.query.expectDate==null ||  $scope.query.expectDate==undefined || $scope.query.expectDate==""){
		    	commonService.alert("请填写预计到达时间");
		    	return;
		  }
			if(document.getElementById("check-1").checked && document.getElementById("check-1") != undefined
					&& document.getElementById("check-1").checked==true){
				$scope.query.isArrGoods=1;//已到货
				$scope.query.arrGoodsTime = document.getElementById("arrGoodsTime").value;
			    if($scope.query.arrGoodsTime==null ||  $scope.query.arrGoodsTime==undefined || $scope.query.arrGoodsTime==""){
			    	commonService.alert("请填写到货时间");
			    	return;
			  }
			}
			//勾选发送短信
			if(document.getElementById("check-2").checked && document.getElementById("check-2") != undefined
					&& document.getElementById("check-2").checked==true){
				$scope.query.isSendMessage = 1;
			}
			//勾选签收
			if(document.getElementById("check-3").checked && document.getElementById("check-3") != undefined
					&& document.getElementById("check-3").checked==true){
				$scope.query.signDate = document.getElementById("signDate").value;
				$scope.query.signState=1;
				if($scope.query.signDate==null ||  $scope.query.signDate==undefined || $scope.query.signDate==""){
			    	commonService.alert("请填写签收时间");
			    	return;
			  }
			  if($scope.query.signType==null ||  $scope.query.signType==undefined || $scope.query.signType==""){
			    	commonService.alert("请选择签收类型");
			    	return;
			  }
			  if($scope.query.signName==null ||  $scope.query.signName==undefined || $scope.query.signName==""){
			    	commonService.alert("请填写签收人");
			    	return;
			  }
			  if($scope.query.signName==null ||  $scope.query.signName==undefined || $scope.query.signName==""){
			    	commonService.alert("请填写签收人");
			    	return;
			  }
			  if($scope.query.signCertificateType==null ||  $scope.query.signCertificateType==undefined || $scope.query.signCertificateType==""){
			    	commonService.alert("请选择证件类型");
			    	return;
			  }
			  if($scope.query.signCertificate==null ||  $scope.query.signCertificate==undefined || $scope.query.signCertificate==""){
			    	commonService.alert("请填写证件号");
			    	return;
			  }
			}
			//勾选已回单
			if(document.getElementById("check-35").checked && document.getElementById("check-35") != undefined
					&& document.getElementById("check-35").checked==true){
				$scope.query.isReceipt=1;
				$scope.query.receiptDate = document.getElementById("receiptDate").value;
				if($scope.query.receiptDate==null ||  $scope.query.receiptDate==undefined || $scope.query.receiptDate==""){
			    	commonService.alert("请填写回单时间");
			    	return;
			  }
			}
			$scope.query.orderId=orderId;
			var url="orderInfoBO.ajax?cmd=doSaveOrdTrackingDetail";
			commonService.postUrl(url,$scope.query,function(data){
				commonService.alert("保存完成");
			});
		},
		
	};
	billIngManage.init();
}]);
