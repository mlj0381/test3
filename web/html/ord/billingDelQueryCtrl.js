function changeTabCallback(){
	var appElement = document.querySelector('[ng-controller=billingDelQueryCtrl]');
	var scope=angular.element(appElement).scope();
	scope.doQuery();
	scope.$apply();
}

var billingManageApp = angular.module("billingDelQueryApp", ['commonApp']);
billingManageApp.controller("billingDelQueryCtrl", ["$scope","commonService","$timeout",'$filter',function($scope,commonService,$timeout,$filter) {
	var billIngManage={
		init:function(){
			this.userData();
			//this.queryOrg();
			this.bindEvent();
			this.doQuery();
			this.selectUserOrg();
			this.initStaticData();
			var returnKeyEventDomEleIds = ["beginTime","endTime","id3","id4","id5","id6","id7","id8","id9","id10"];
		    commonService.registerKeyEventForDoms(returnKeyEventDomEleIds, "keydown", "return", $scope.doQuery);
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
		    $scope.toDetailAllInfo = this.toDetailAllInfo;
		    $scope.isTrue = false;
		    $scope.commonExport = this.commonExport;
		    $scope.paramsExport = "{}";
		    $scope.showView = this.showView;
		    $scope.toTransfer = this.toTransfer;
		    $scope.posting = this.posting;
		},
		//获取静态数据
		initStaticData:function(){
			//获取查询类型
			commonService.postUrl("staticDataBO.ajax?cmd=selectQueryType","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.queryTypeDate = data;
					$scope.query.queryType = $scope.queryTypeDate.items[0].codeValue;
				}
			});
		},
		//导出方法
		commonExport : function(){
			if($scope.isTrue){
				return false;
			}
			/**
			 * queryUrl  格式如：commonExportBO.ajax?cmd=downloadExcelFile
			 * params   请求的参数对象:{"date":"2016-07-12"}
			 * excelLables  excel的列名: 批次号,时间
			 * excelKeys    excel的字段名称:batchNum,date 
			 */
			var excelLables = "运单号,开单网点,开单时间,库存网点,订单状态,配送网点,目的城市,收货人,收货人电话,货品,交接方式," +
					"总体积(方),件数,总重量(kg),付款方式1,金额(元),付款方式2,金额(元),收货地址,发货人,发货人电话,合作商,合作商电话,备注";
			var excelKeys = "trackingNum,orgIdName,createDateString,currentOrgIdName,orderStateName,distributionOrgName,destCityName,consigneeName,consigneeBill,products,deliveryTypeName," +
					"volume,count,weight,paymentTypeName,cashMoneyString,paymentType2Name,cashMoney2String,address,consignorName,consignorBill,doObjName,doTel,remarks";
			var params = $scope.paramsExport;
			var queryUrl = "orderInfoBO.ajax?cmd=queryOrderInfo";
			commonService.downloadExcelFile(queryUrl,eval("("+params+")"),excelLables,excelKeys);
			
			$scope.isTrue = true;
			$("#exportId").html("导出中。");
			//导出倒计时
			$timeout(function() {
				 $scope.isTrue = false;
				$("#exportId").html("导出");
			},3600);
			
		},
		toTransfer:function(){
			var selectedValue =$scope.page.getSelectData();
			if(selectedValue.length<=0){
				commonService.alert("请选择一条运单信息");
				return false;
			}
			if(selectedValue.length>1){
				commonService.alert("只能选择一条运单信息");
				return false;
			}
			var url ="transferBO.ajax?cmd=isTransfer"
			commonService.postUrl(url,"orderId="+selectedValue[0].orderId,function(data){
				if(data != null && data != undefined && data != ""){
					if(data.isOk == "Y"){
						commonService.openTab(orderId,"运单中转","/ord/ordBillingDetailTransfer.html?view=1&trackingNum="+selectedValue[0].trackingNum+"&orderId="+selectedValue[0].orderId);
					}
				}
			});
		},
		toDetailAllInfo: function(obj){
			//window.open("/ord/ordBillingDetail.html?view=1&orderId="+obj.orderId+"&trackingNum="+obj.trackingNum);
			commonService.openTab(obj.orderId,"运单详情","/ord/ordBillingDetail.html?view=1&orderId="+obj.orderId+"&trackingNum="+obj.trackingNum);
			
		},
		upNum:function(valueName){
			var value = eval("$scope."+valueName).replace(/[^\d]/g, '');
			eval("$scope."+valueName+"=value");
		},
		query:{
			page:1,
			rows:50,
			orderState:2,
		},
		userData:function(){
			$scope.currOrgId=userInfo.orgId;
			$scope.currOrgName=userInfo.orgName;
		},
		/**查询开单网点**/
		selectUserOrg:function(){
			$scope.isShow=true;
			commonService.postUrl("webCmUserInfoBO.ajax?cmd=getUserOrgType","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.orgData= data.orgList;
					$scope.orgType= data.orgType;
					if($scope.orgType==2){
						$scope.isShow=false;
						$scope.orgData.unshift({orgId:-1,orgName:'全部'});
						$scope.query.orgId=-1;
					}else{
						$scope.query.orgId=$scope.orgData[0].orgId;
						$scope.queryOrg();
						$scope.isShow=true;
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
		doQuery:function(flag){
			var url = "orderInfoBO.ajax?cmd=queryOrderInfo";
			billIngManage.query.page=1;
			billIngManage.query.needIngoreOrgId=true;
			billIngManage.query.orderType = 0;
			billIngManage.query.orderState = 6;//查询删除数据
			$scope.query.beginTime = document.getElementById("beginTime").value;
			$scope.query.endTime = document.getElementById("endTime").value;
			$scope.tableCallBack=function(){
//				commonService.refreshPageContentHeight($scope.page.data.items.length, 663, 270);
				setContentHegthDelay();
				$scope.paramsExport = JSON.stringify(billIngManage.query)
			}
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:billIngManage.query,
							callBack:"$scope.tableCallBack"
						});
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
			$scope.query.inputUserName = "";
			$scope.query.delBeginTime = "";
			$scope.query.delEndTime = "";
			$scope.query.queryValue = "";
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
		/**运单录入**/
		addView:function(){
			commonService.openTab("1","运单录入","/ord/billing.html");
			//commonService.openTab("1","运单录入","/ord/billing.html?sellerOrderId=7");
		},
		/**打印**/
		toView:function(viewType){
			var orderId='';
			var orgId='';
			var trackingNum='';
			var ordArray = new Array();
            ordArray=$scope.page.getSelectData();
            if(ordArray.length==0){
            	commonService.alert("请至少选择一条运单信息!");
				return false;
            }
            if(ordArray.length>1){
				commonService.alert("只能选择一条运单信息!");
				return false;
			}
            for(var i=0;i<ordArray.length;i++){
            	var data= ordArray[0];
            	orderId=data.orderId;
				orgId=data.orgId;
				trackingNum=data.trackingNum;
            }
			if($scope.orgType==2){
			    commonService.openTab("/ord/billing.html?view=1&orderId="+orderId+"&orgId="+orgId);
			}else{
				if(viewType==1){
					 commonService.openTab(orderId,"运单详情","/ord/ordBillingDetail.html?view=1&orderId="+orderId+"&trackingNum="+trackingNum);
				}
				else
				{
					 commonService.openTab(orderId,"运单打印详情","/ord/ordBillingDetail.html?view=1&orderId="+orderId+"&trackingNum="+trackingNum);
				}
			}
		},
		
		/**查看**/
		showView:function(){
			var orderId='';
			var orgId='';
			var trackingNum='';
			var ordArray = new Array();
             ordArray=$scope.page.getSelectData();
            if(ordArray.length==0){
            	commonService.alert("请至少选择一条运单信息!");
				return false;
            }
            if(ordArray.length>1){
				commonService.alert("只能选择一条运单信息!");
				return false;
			}
            for(var i=0;i<ordArray.length;i++){
            	var data= ordArray[0];
            	orderId=data.orderId;
				orgId=data.orgId;
				trackingNum=data.trackingNum;
            }
			window.open("/ord/ordBillingDetail.html?view=1&orderId="+orderId+"&trackingNum="+trackingNum);
		},
		
		
		toAudit:function(){
			if($scope.isTrue){
				return false;
			}
			var orderId='';
			var orderIds = "";
			var ordArray = new Array();
            ordArray=$scope.page.getSelectData();
			var orderCheckLength = ordArray.length;
            if(ordArray.length==0){
            	commonService.alert("请至少选择一条运单信息!");
				return false;
            }
            var postTrue = false;
            for(var i=0;i<ordArray.length;i++){
            	var dataStr= ordArray[i];
            	if(dataStr !=null && dataStr != undefined && dataStr != ''){
            		var data = ordArray[i];
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
            }
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
		toEdit:function(oper){
			var orderId='';
			var ordArray = new Array();
            ordArray=$scope.page.getSelectData();
            if(ordArray.length==0){
            	commonService.alert("请至少选择一条运单信息!");
				return false;
            }
            if(ordArray.length>1){
				commonService.alert("只能选择一条运单信息!");
				return false;
			}
            var flag=false;
            for(var i=0;i<ordArray.length;i++){
            	var data= ordArray[0];
            	if(data.orderState==5){
					flag=true;
					commonService.alert("订单["+data.trackingNum+"]状态为"+data.orderStateName+",不可以操作!");
					return false;
				}
            	orderId=data.orderId;
            }
			if(flag){
				return false;
			}
			if(oper=='print')
			{
				commonService.openTab(orderId + 951951951,"打印运单","/ord/billing.html?edit=1&print=1&orderId="+orderId);
			}
			else
			{
				commonService.openTab(orderId + 951951951,"运单修改","/ord/billing.html?edit=1&orderId="+orderId);
			}
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
			var ordArray = new Array();
            ordArray=$scope.page.getSelectData();
            if(ordArray.length==0){
            	commonService.alert("请至少选择一条运单信息!");
				return false;
            }
            for(var i=0;i<ordArray.length;i++){
            	var data= ordArray[0];
            	$scope.orderId += data.orderId+",";
            }
			var flag=false;
			var ordArray = new Array();
            ordArray=$scope.page.getSelectData();
            if(ordArray.length==0){
            	commonService.alert("请至少选择一条运单信息!");
				return false;
            }
            var flag=false;
            for(var i=0;i<ordArray.length;i++){
            	var data= ordArray[i];
            	if(data.seeOrderState != 1){
					flag = true;
					commonService.alert("运单["+data.trackingNum+"]状态为"+data.seeOrderStateName+",不可以操作!");
					return false;
				}
            	$scope.orderId += data.orderId+",";
            }
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
		},
		/**过账*/
		posting:function(){
			$scope.orderId="";
			var ordArray = new Array();
            ordArray=$scope.page.getSelectData();
            if(ordArray.length==0){
            	commonService.alert("请至少选择一条运单信息!");
				return false;
            }
            for(var i=0;i<ordArray.length;i++){
            	var data= ordArray[0];
            	$scope.orderId += data.orderId+",";
            }
			var flag=false;
			var ordArray = new Array();
            ordArray=$scope.page.getSelectData();
            if(ordArray.length==0){
            	commonService.alert("请至少选择一条运单信息!");
				return false;
            }
            var flag=false;
            for(var i=0;i<ordArray.length;i++){
            	var data= ordArray[i];
//            	if(data.orderState!=2&&data.orderState!=9){
//					flag=true;
//					commonService.alert("订单["+data.trackingNum+"]状态为"+data.orderStateName+",不可以操作!");
//					return false;
//				}
            	$scope.orderId += data.orderId+",";
            }
			if(flag){
				return false;
			}
			var param = {"orderId":$scope.orderId};
			var url = "orderInfoBO.ajax?cmd=postingOrderInfo";
			 commonService.confirm("是否过账?",function(){
				commonService.postUrl(url,param,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
							commonService.alert("过账成功!");
							$scope.doQuery();
		 	    	}
				});
			 });
		}
	};
	
	billIngManage.init();
}]);