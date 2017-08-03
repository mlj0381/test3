var transitTrackingManageApp = angular.module("transitTrackingManageApp", ['commonApp']);
transitTrackingManageApp.controller("transitTrackingManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var billIngManage={
		init:function(){
			$scope.isShow = false;
			this.userData();
			this.bindEvent();
			this.doQuery();
			this.selectUserOrg();
			this.changeCarrierCompany();
		},
		bindEvent:function(){
			$scope.param=this.param;
		
			$scope.doQuery=this.doQuery;
		    $scope.query=this.query;
		    $scope.clear=this.clear;
		    $scope.selectAll=this.selectAll;
		    $scope.selectOne=this.selectOne;
		    $scope.upNum = this.upNum;
		    $scope.param = this.param;
		    $scope.outGoingData=this.outGoingData;
		    $scope.customParseDouble=this.customParseDouble;
		    $scope.selectTracking=this.selectTracking;
		    
		    $scope.isTrue = false;
		    $scope.paramsExport = "{}";
		    $scope.addTracking=this.addTracking;
		    $scope.toView = this.toView;
		    $scope.changeCarrierCompany = this.changeCarrierCompany;
		    $scope.toDetailAllInfo = this.toDetailAllInfo;
		    $scope.transferArriveGoods=this.transferArriveGoods;
		    $scope.openTransferArriveGoods=this.openTransferArriveGoods;
		    $scope.secondTransfer=this.secondTransfer;
		    $scope.openSecondTransfer=this.openSecondTransfer;
		    $scope.modifyInfo=this.modifyInfo;
		},
		modifyInfo:function(){
			var selectedValue =$scope.p.getSelectData();
			if(selectedValue.length<=0){
				commonService.alert("请选择一条中转信息进行修改保存");
				return false;
			}
			var orderList=new Array();
			for(var index in selectedValue){
				var obj = {};
				obj.trackingNum = selectedValue[index].trackingNum;
				obj.linkerName =selectedValue[index].linkerName;
				obj.orderId = selectedValue[index].orderId;
				obj.deliveryAddress = selectedValue[index].deliveryAddress;
				obj.linkPhone = selectedValue[index].linkerPhone;
				obj.csPhones = selectedValue[index].deliveryPhone;
				obj.transferValue = selectedValue[index].outgoingFeeDouble;
				obj.outgoingTrackingNum = selectedValue[index].outgoingTrackingNumString;
				orderList.push(obj);
			}
			var param ={};
			param.orderList= orderList;
			commonService.confirm("请确认["+orderList[0].trackingNum+"]的等运单的中转费,外发单号,联系人、联系电话、提货电话、提货地址等信息是否录入正确,总共是【"+orderList.length+"】票,是否继续保存？", function(){
				window.top.showLoad();
				var url ="transferBO.ajax?cmd=modifyInfo"
					commonService.postUrl(url,{"list":angular.toJson(param.orderList,true)},function(data){
						if(data != null && data != undefined && data != ""){
							window.top.hideLoad();
							commonService.alert("操作成功");
							$scope.doQuery();
						}
					},function(data){
						window.top.hideLoad();
						commonService.alert(data.message);
					});
			});
		},
		toDetailAllInfo:function(obj){
			window.open("/ord/ordBillingDetail.html?view=1&orderId="+obj.orderId+"&trackingNum="+obj.trackingNum);
		},
		toView:function(trackingNum){
			window.open("/ord/ordBillingDetail.html?view=8&trackingNum="+trackingNum+"&type=3&ver=${ver}");
		},
		changeCarrierCompany:function(){
			commonService.postUrl("organizationBO.ajax?cmd=getCarri","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.cmCarrCompanies= data.items;
					$scope.cmCarrCompanies.unshift({orgId:-1,orgName:'全部'});
					$scope.query.carrierCompanyId = -1;
				}
			});
		},
		/**查询开单网点**/
		selectUserOrg : function(){
			commonService.postUrl("webCmUserInfoBO.ajax?cmd=getUserOrgType","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.orgData= data.orgList;
					$scope.orgType= data.orgType;
					if($scope.orgType==2){
						 $scope.query.transitOutgoingState = 1+"";
						 document.getElementById("stateId").disabled = true;
					}else{
						 $scope.isShow=true;
					}
					
				}
			});
		},
		addTracking:function(){
				var orderId='';
				var orderState=0;
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
						trackingNum=data.trackingNum;
						orderId=data.orderId;
						orderState = data.orderState;
					}
				});
				/*if(orderState!=7){
					commonService.alert("不是待签收的中转单不能新增跟踪信息");
					return;
				}*/
		  //window.open("/ord/transitOutgoingLog.html?trackingNum="+trackingNum+"&orderId="+orderId+"&ver=${ver}");
		  commonService.openTab(orderId,"中转跟踪","/ord/transitOutgoingLog.html?trackingNum="+trackingNum+"&orderId="+orderId+"&ver=${ver}");
		},
		upNum:function(valueName){
			var value = eval("$scope."+valueName).replace(/[^\d]/g, '');
			eval("$scope."+valueName+"=value");
		},
		query:{
			page:1,
			rows:50,
			isSecondTransfer:"-1",
			isTransferGoods:"-1",
			carrierCompanyId: '-1'
		},
		param:{},
	
		outGoingData:{},
		userData:function(){
			$scope.currOrgId=userInfo.orgId;
			$scope.currOrgName=userInfo.orgName;
			$scope.printTime = new Date();
			$scope.cmCarrCompanies = [{
				id: '-1',
				carrierName: '全部'
			}];
			commonService.postUrl("cmCarrierCompanyBO.ajax?cmd=getCmCarrCompany",{state:1},function(data){
				if (undefined != data && data.items != undefined && data.items.length > 0) {
					for(var i = 0; i < data.items.length; i++) {
						$scope.cmCarrCompanies.push({
							id: data.items[i].id,
							carrierName: data.items[i].carrierName
						});
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
			$scope.query.beginTime = document.getElementById("beginTime").value;
			$scope.query.endTime = document.getElementById("endTime").value;
			billIngManage.query.page=1;
			billIngManage.query.transitOutgoingState=1;
			$timeout(function(){
				$scope.p.load({
								url:url,
								params:billIngManage.query,
								callBack:"setContentHegthDelay"
							}); },500);
		},
		/**清空查询条件*/
		clear:function(){
			document.getElementById("beginTime").value='';
			document.getElementById("endTime").value='';
			document.getElementById("transitTimeS").value='';
			document.getElementById("transitTimeE").value='';
			$scope.query.transitTimeS='';
			$scope.query.transitTimeE='';
			$scope.query.beginTime='';
			$scope.query.endTime='';
			$scope.query.trackingNum="";
			$scope.query.descOrgId=-1;
			$scope.query.orderState="-1";
			$scope.query.consignorBill = "";
			$scope.query.consigneeBill = "";
			$scope.query.transitOutgoingState="0";
			$scope.query.carrierCompanyId="-1";
			$scope.query.consignorName = "";
			$scope.query.consigneeName = "";
			$scope.query.isTransferGoods="-1";
			$scope.query.isSecondTransfer="-1";
		},
	
		/**网点列表查询*/
		/**选择一行**/
		selectOne : function(orderId){
			if(document.getElementById("checkbox"+orderId).checked && document.getElementById("checkbox"+orderId) != undefined){
				document.getElementById("checkbox"+orderId).checked=false;
			}else{
				document.getElementById("checkbox"+orderId).checked=true;
			}
		},
		//查看跟踪详情
		selectTracking:function(){
			var orderId='';
			var orderState=0;
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
					trackingNum=data.trackingNum;
					orderId=data.orderId;
					orderState = data.orderState;
				}
			});
	  commonService.openTab(orderId,"中转跟踪详情","/ord/transitOutgoingLog.html?trackingNum="+trackingNum+"&orderId="+orderId+"&toView=1&ver=${ver}");

		},
	
		/**
		 * 转换double类型
		 * @param obj
		 * @returns {Number}
		 */
		customParseDouble: function(obj){
			if (obj == undefined || obj == null) {
				return 0;
			}
			var ret = Math.round(obj * 100) / 100;
			if(isNaN(ret)){
				return 0;
			}
			return ret;
		},
		openSecondTransfer:function(){
			var selectedValue =$scope.p.getSelectData();
			if(selectedValue.length<=0){
				commonService.alert("请至少选择一条中转信息");
				return false;
			}
			for(var index in selectedValue){
				if(selectedValue[index].isSeconfTrasfer!=null&&selectedValue[index].isSeconfTrasfer!=undefined&&selectedValue[index].isSeconfTrasfer!=''){
					commonService.alert("运单["+selectedValue[index].trackingNum+"]已操作过二次中转，不允许再操作!");
					return false;
				}
			}
			document.getElementById('secondTransfer').style.display='block';
			document.getElementById('fade1_xz').style.display='block';
		},
		secondTransfer:function(){
			if (document.getElementById("remark1").value==null||document.getElementById("remark1").value==undefined||document.getElementById("remark1").value==null) {
				commonService.alert("请填写备注信息!");
				return false;
			}
			var selectedValue =$scope.p.getSelectData();
			var orderList = new Array();
			var trackingNumArr=new Array();
			for(var index in selectedValue){
				trackingNumArr.push(selectedValue[index].trackingNum);
				orderList.push(selectedValue[index].orderId);
			}
			var param ={"orderId":orderList.join(","),"trackingNum":trackingNumArr.join(","),"remark":document.getElementById("remark1").value,"time":document.getElementById("time1").value};
			window.top.showLoad();
			var url ="transferBO.ajax?cmd=secondTransfer"
			commonService.postUrl(url,param,function(data){
				if(data != null && data != undefined && data != ""){
					document.getElementById('secondTransfer').style.display='none';
					document.getElementById('fade1_xz').style.display='none';
					document.getElementById("time1").value="";
					document.getElementById("remark1").value="";
					window.top.hideLoad();
					commonService.alert("操作成功");
					$scope.doQuery();
				}
			});
			$timeout(function(){
				window.top.hideLoad();
			},700);
		},
		openTransferArriveGoods:function(){
			var selectedValue =$scope.p.getSelectData();
			if(selectedValue.length<=0){
				commonService.alert("请至少选择一条中转信息");
				return false;
			}
			for(var index in selectedValue){
				if(selectedValue[index].transferArriveGoodsTime!=null&&selectedValue[index].transferArriveGoodsTime!=undefined&&selectedValue[index].transferArriveGoodsTime!=''){
					commonService.alert("运单["+selectedValue[index].trackingNum+"]已操作过中转到货，不允许再操作!");
					return false;
				}
			}
			document.getElementById('transferGoods').style.display='block';
			document.getElementById('fade1_xz').style.display='block';
		},
		transferArriveGoods:function(){
			if (document.getElementById("time").value==null||document.getElementById("time").value==undefined||document.getElementById("time").value==null) {
				commonService.alert("请选择到货时间!");
				return false;
			}
			if (document.getElementById("remark").value==null||document.getElementById("remark").value==undefined||document.getElementById("remark").value==null) {
				commonService.alert("请填写备注信息!");
				return false;
			}
			var selectedValue =$scope.p.getSelectData();
			var orderList = new Array();
			var trackingNumArr=new Array();
			for(var index in selectedValue){
				trackingNumArr.push(selectedValue[index].trackingNum);
				orderList.push(selectedValue[index].orderId);
			}
			var param ={"orderId":orderList.join(","),"trackingNum":trackingNumArr.join(","),"time":document.getElementById("time").value,"remark":document.getElementById("remark").value};
			window.top.showLoad();
			var url ="transferBO.ajax?cmd=transferArriveGoods"
				commonService.postUrl(url,param,function(data){
					if(data != null && data != undefined && data != ""){
						document.getElementById('transferGoods').style.display='none';
						document.getElementById('fade1_xz').style.display='none';
						document.getElementById("time").value="";
						document.getElementById("remark").value="";
						window.top.hideLoad();
						commonService.alert("操作成功");
						$scope.doQuery();
					}
				});
			$timeout(function(){
				window.top.hideLoad();
			},700);
		}
	};
	billIngManage.init();
}]);
