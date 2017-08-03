var billingManageApp = angular.module("billingManageApp", ['commonApp']);
billingManageApp.controller("billingManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var billIngManage={
		init:function(){
			this.userData();
			this.queryOrg();
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
		    
		    $scope.upNum = this.upNum;
		    $scope.isTrue = false;
		},
		upNum:function(valueName){
			var value = eval("$scope."+valueName).replace(/[^\d]/g, '');
			eval("$scope."+valueName+"=value");
		},
		query:{
			page:1,
			rows:50,
			orderState:1,
		},
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
			var url = "orderInfoBO.ajax?cmd=queryOrderInfo";
			$scope.query.beginTime = document.getElementById("beginTime").value;
			$scope.query.endTime = document.getElementById("endTime").value;
			billIngManage.query.page=1;
			billIngManage.query.orderType = 1;
			billIngManage.query.needIngoreOrgId=true;
			$scope.tableCallBack=function(){
				if ($scope.page.data.items == undefined || $scope.page.data.items.length == 0) {// 没有数据，指定一个默认高度
					setContentHeightWithSpecialHeight(683);
				} else {// 有数据，则根据数据行计算高度
					var height = (683 - 270) + $scope.page.data.items.length * 48 ;
					height = height <= 683 ? 683 : height;
					setContentHeightWithSpecialHeight(height);
				}
			}
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:billIngManage.query,
							callBack:"$scope.tableCallBack"
						});
			},500);
		},
		/**查询开单网点**/
		selectUserOrg:function(){
			commonService.postUrl("webCmUserInfoBO.ajax?cmd=getUserOrgType","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.orgData= data.orgList;
					$scope.orgType= data.orgType;
					if($scope.orgType==2){
						$scope.orgData.unshift({orgId:-1,orgName:'全部'});
						$scope.query.orgId=-1;
						$scope.isShow=false;
					}else{
						$scope.query.orgId=$scope.orgData[0].orgId;
						
						$scope.isShow=true;
					}
					
				}
			});
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
			commonService.postUrl("routeBO.ajax?cmd=queryRoateTowards","orgType=4&needFilter=true",function(data){
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
		/**运单录入**/
		addView:function(){
			commonService.openTab("1","第三方运单录入","/ord/thridPatryBilling.html");
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
			    commonService.openTab(orderId,"第三方运单详情","/ord/thridPatryBilling.html?view=1&orderId="+orderId+"&orgId="+orgId);
			}else{
				 commonService.openTab(orderId,"第三方运单详情","/ord/thridPatryBilling.html?view=1&orderId="+orderId);
			}
			//commonService.openTab(orderId,"第三方运单详情","/ord/thridPatryBilling.html?view=1&orderId="+orderId);
		},
		toAudit:function(){
			if($scope.isTrue){
				return false;
			}
			var orderId='';
			var orderIds = "";
			var documemnt =  $("input[name='check-1']:checked");
			var orderCheckLength = documemnt.length
			if(orderCheckLength == 0){
				commonService.alert("请至少选择一条运单信息!");
				return false;
			}
//		
//			if($("input[name='check-1']:checked").length>1){
//				commonService.alert("只能选择一条运单信息!");
//				return false;
//			}
			var postTrue = false;
			documemnt.each(function(){
				var dataStr= $(this).val();
				if(dataStr !=null && dataStr != undefined && dataStr != ''){
					var data = eval("("+dataStr+")");
					orderId = data.orderId;
					orderIds = orderIds + data.orderId+",";
					if(data.orderState != 1){
						if(orderCheckLength == 1){
							commonService.alert("运单["+data.trackingNum+"]状态为"+data.orderStateName+",不允许审核!");
						}else{
							commonService.alert("运单["+data.trackingNum+"]状态为"+data.orderStateName+",不允许批量审核,请去除该运单选项!");
						}
						postTrue =  true;
						return false;
					}
				}
			});
			//存在非待审核的订单
			if(postTrue){
				return false;
			}
			var url = "orderInfoBO.ajax?cmd=auditOrderInfo";
			$scope.isTrue = true;
			$("#auditId").html("审核中。");
			commonService.postUrl(url,"orderIds="+orderIds,function(data){
				//成功执行
				if(data != null && data != undefined && data!=""){
					if(orderCheckLength == 1){
						commonService.alert("审核成功!");
					}else{
						commonService.alert("批量审核成功!");
					}
					$scope.doQuery();
					$scope.isTrue = false;
					$("#auditId").html("运单审核");
	 	    	}
			});
			
			//如果后台报错（需要使用定时器还原）
			$timeout(function(){
				$scope.isTrue = false;
				$("#auditId").html("运单审核");
			},5000);
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
			commonService.openTab(orderId + 951951951,"第三方运单修改","/ord/thridPatryBilling.html?edit=1&orderId="+orderId);
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
			commonService.confirm("是否删除数据?",function(){
				commonService.postUrl(url,param,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
							commonService.alert("删除成功!");
							$scope.doQuery();
		 	    	}
				});
			});
		}
	};
	
	billIngManage.init();
}]);
