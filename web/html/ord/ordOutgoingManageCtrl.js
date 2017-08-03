var ordOutgoingManageApp = angular.module("ordOutgoingManageApp", ['commonApp']);
ordOutgoingManageApp.controller("ordOutgoingManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var billIngManage={
		init:function(){
			this.userData();
			//this.queryOrg();
			this.bindEvent();
			this.doQuery();
			this.selectUserOrg();
		},
		bindEvent:function(){
			$scope.param=this.param;
			$scope.doQuery=this.doQuery;
		    $scope.toDel=this.toDel;
		    $scope.print=this.print;
		    $scope.doQueryVehicleState=this.doQueryVehicleState;
		    $scope.queryOrg=this.queryOrg;
		    $scope.addView= this.addView;
		    $scope.toView=this.toView;
		    $scope.toEdit=this.toEdit;
		    $scope.query=this.query;
		    $scope.clear=this.clear;
		    $scope.selectAll=this.selectAll;
		    $scope.doSave=this.doSave;
		    $scope.selectOne=this.selectOne;
		    $scope.toAudit = this.toAudit;
		    $scope.toTransitOutgoingManage = this.toTransitOutgoingManage;
		    $scope.upNum = this.upNum;
		    $scope.toDetailAllInfo = this.toDetailAllInfo;
		},
		toDetailAllInfo: function(trackingNum){
			window.open("/ord/billingDetail.html?view=1&orderId="+trackingNum);
		},
		upNum:function(valueName){
			var value = eval("$scope."+valueName).replace(/[^\d]/g, '');
			eval("$scope."+valueName+"=value");
		},
		query:{
			page:1,
			rows:10,
			orderState:1,
			orderType:1,
		},
		userData:function(){
			$scope.currOrgId=userInfo.orgId;
			$scope.currOrgName=userInfo.orgName;
		},
		/**查询开单网点**/
		selectUserOrg:function(){
			commonService.postUrl("webCmUserInfoBO.ajax?cmd=getUserOrgType","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.orgData= data.orgList;
					$scope.orgType= data.orgType;
					if($scope.orgType==2){
						$scope.isShow=false;
						$("#delOrder").hide();
						$scope.orgData.unshift({orgId:-1,orgName:'全部'});
						$scope.query.orgId=-1;
					}else{
						$scope.query.orgId=$scope.orgData[0].orgId;
						$scope.queryOrg();
						$scope.isShow=true;
						$("#delOrder").show();
					}
					
				}
			});
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
			var url = "orderInfoBO.ajax?cmd=queryOutGoing";
			$scope.query.page=1;
			$scope.query.needIngoreOrgId=true;
			$scope.query.beginTime = document.getElementById("beginTime").value;
			$scope.query.endTime = document.getElementById("endTime").value;
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:$scope.query,
							callBack:"$scope.select"
						});
				$scope.select=function(data){
					console.log(data);
				}
			},500);
		},
		/**清空查询条件*/
		clear:function(){
			document.getElementById("beginTime").value='';
			document.getElementById("endTime").value='';
			$scope.query.beginTime='';
			$scope.query.endTime='';
			$scope.query.trackingNum="";
			$scope.query.descOrgId=-1;
			$scope.query.orderState="-1";
			$scope.query.consignorBill = "";
			$scope.query.consigneeBill = "";
		},
		/**网点列表查询*/
		queryOrg:function(){
			var beginOrgId = $scope.query.orgId;
			commonService.postUrl("routeBO.ajax?cmd=queryRoateTowards","beginOrgId="+beginOrgId+"&orgType=1",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.orgInfo = data;
					if(data.items != null && data.items != undefined && data.items != ""){
						$scope.orgInfo.items.unshift({endOrgId:-1,endOwnerName:'全部'});
					}
					$scope.query.descOrgId = -1;
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
		/**查看**/
		toView:function(){
			var orderId='';
			var orgId='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条运单信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条运单信息!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					orderId=data.orderId;
					orgId=data.orgId;
				}
			});
			if($scope.orgType==2){
			    commonService.openTab(orderId,"运单详情","/ord/billing.html?view=1&orderId="+orderId+"&orgId="+orgId);
			}else{
				 commonService.openTab(orderId,"运单详情","/ord/billing.html?view=1&orderId="+orderId);
			}
		},
		toAudit:function(){
			var orderId='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条运单信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条运单信息!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					orderId=data.orderId;
				}
			});
			var url = "orderInfoBO.ajax?cmd=auditOrderInfo";
			commonService.postUrl(url,"orderId="+orderId,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
						commonService.alert("操作成功!");
						$scope.doQuery();
	 	    	}
			});
		},
		//修改
		toEdit:function(){
			var orderId='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条运单信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条运单信息!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					orderId=data.orderId;
				}
			});
			commonService.openTab(orderId+9519235,"中转外发修改","/ord/ordOutgoing.html?operType=2&orderId="+orderId);
			//commonService.openTab(orderId + 951951951,"运单修改","/ord/billing.html?edit=1&orderId="+orderId);
		},
		/***保存**/
		doSave:function(){
			var param = {"isAll":"Y"};
			var url = "orderInfoBO.ajax?cmd=matchVehicle";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
						commonService.alert("操作成功!");
						$scope.doQuery();
	 	    	}
			});
		},
		/**打印*/
		print:function(){
		},
		/**删除*/
		toDel:function(){
			$scope.orderId="";
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条订单信息!");
				return false;
			}
			var flag=false;
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					if(data.orderState!=1){
						flag=true;
						commonService.alert("订单["+data.trackingNum+"]状态为"+data.orderStateName+",不可以操作!");
						return false;
					}
					$scope.orderId += data.orderId+",";
				}
			});
			if(flag){
				return false;
			}
			var param = {"orderId":$scope.orderId};
			var url = "orderInfoBO.ajax?cmd=delOrderInfo";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
						commonService.alert("删除成功!");
						$scope.doQuery();
	 	    	}
			});
		},
		
		//查看详情
		toTransitOutgoingManage: function(){
			var orderId='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条运单信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条运单信息!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					orderId=data.orderId;
				}
			});
			commonService.openTab(orderId,"中转外发详情","/ord/ordOutgoing.html?operType=1&orderId="+orderId);
			// 前置校验
			/*$timeout(function(){
				commonService.postUrl("orderInfoBO.ajax?cmd=preCheckForNewTransitOutgoing", {orderId: orderId}, function(data){
					if(data != null && data != undefined && data != ""){
						if(data.resultCode == '1'){
							commonService.openTab(orderId,"中转外发详情","/ord/transitOutgoing.html?operType=1&orderId="+orderId);
						} else { 
							commonService.alert(data.resultMessage);
						}
					}
				});
			}, 500);*/
		},
	};
	
	billIngManage.init();
}]);