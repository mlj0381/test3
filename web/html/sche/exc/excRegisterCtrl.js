var excState="";
var excRegisterApp = angular.module("excRegisterApp", ['commonApp']);
excRegisterApp.controller("excRegisterCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var excRegister={
		init:function(){
			this.bindEvent();
			this.getUserInfo();
			$scope.isTaskShow=false;
			$scope.isComit=true;
			var orderId=getQueryString("orderId");
			$scope.isShow=getQueryString("isShow");
			$scope.isView=getQueryString("isView");
			excState=getQueryString("excState");
			if(orderId!=null&&orderId!=undefined&&orderId!=''){
				$scope.query.orderId=orderId;
				$scope.toView(orderId);
			}
			var excId=getQueryString("excId");
			if(excId!=null&&excId!=undefined&&excId!=''){
				$scope.queryExceInfo(excId);
			}
		},
		bindEvent:function(){
			$scope.params = this.params;
			$scope.query = this.query;
			$scope.doQueryOrg = this.doQueryOrg;
			$scope.tabShow=this.tabShow;
			$scope.close=this.close;
			$scope.toView=this.toView;
			$scope.doSave=this.doSave;
			$scope.queryExceInfo=this.queryExceInfo;
			$scope.hideTask=this.hideTask;
			$scope.showTask=this.showTask;
			$scope.doQuery=this.doQuery;
			$scope.selectSf=this.selectSf;
			$scope.form=this.form;
			$scope.doQueryOrd=this.doQueryOrd;
			$scope.closeView=this.closeView;
			$scope.selectOrder=this.selectOrder;
			$scope.clearModel=this.clearModel;
			$scope.querySf=this.querySf;
			$scope.user=this.user;
			$scope.clear=this.clear;
			//加载省
			$scope.selectProvince=this.selectProvince;
			//加载市
			$scope.selectCity=this.selectCity;
			$scope.queryMaster=this.queryMaster;
			$scope.closeMaster=this.closeMaster;
			$scope.selectMaster=this.selectMaster;
			$scope.querySfOrService=this.querySfOrService;
			$scope.userData=this.userData;
			$scope.userTypeInfo=this.userTypeInfo;
			$scope.getUserInfo=this.getUserInfo;
			$scope.userTypeData=this.userTypeData;
		},
		userData:{
			
		},
		userTypeData:{
			
		},
		user:{
			
		},
		params:{
		},
		form:{
		},
		clear:function(){
			$scope.user.name = "";
			$scope.user.linkMan = "";
			$scope.user.linkPhone = "";
			$scope.user.busiNotes = "";
		},
		query:{
			provinceId:-1,
		},
		/***用户数据*/
		getUserInfo:function(){
			$scope.userData=userInfo;
			if(userInfo.userType==6){
				$scope.isHide=true;
			}else{
				$scope.isHide=false;
			}
			$scope.userTypeInfo();
		},
	/**用户类型*/
		userTypeInfo:function(){
			$scope.userTypeData=userType;
		},
		queryExceInfo:function(excId){
			if(excId!=null&&excId!=""){
				var param={"excId":excId};
				var url="exceptionInfoBO.ajax?cmd=toView";
				commonService.postUrl(url,param,function(data){
					$scope.query=data.exceptionInfo;
					$scope.hisExc=data.hisExceptionInfo;
					$scope.taskList=data.taskInfo;
					$scope.query.wayBillId=data.exceptionInfo.trackingNum;
					$scope.query.excId=data.exceptionInfo.id;
					if(excState!=null&&excState!=""&&excState!=''){
						$scope.query.excState=excState+"";
					}else{
						$scope.query.excState=data.exceptionInfo.excState+"";
					}
					$scope.query.isRepair=2;
					$scope.dutyObjId=data.exceptionInfo.dutyObjId;
					$scope.dutyType=data.exceptionInfo.dutyType;
					$scope.query.exceFee=data.exceptionInfo.exceFee/100;
					excRegister.toView(data.exceptionInfo.orderId);
					if(data.exceptionInfo!=null&&data.exceptionInfo!=undefined&&data.exceptionInfo!=''){
						var flowId=data.exceptionInfo.imageId.split(",");
						for(var i=0;i<flowId.length;i++){
							 eval("$scope.exceImage"+(i+1)+".initDate("+flowId[i]+")");
						}
						if($scope.isView==1){
							$scope.exceImage1.isUpShow(false);
							$scope.exceImage2.isUpShow(false); 
							$scope.exceImage3.isUpShow(false);
							$scope.exceImage4.isUpShow(false);
							$scope.exceImage5.isUpShow(false);
	
						}
					}
					//当前网点异常登记对应单的开单网点
					if($scope.userData.orgId==$scope.query.inputOrgId){
						$scope.isHide=true;
					}
				});
			}
		},
		toView:function(orderId){
//			if($scope.query.wayBillId!=null&&$scope.query.wayBillId!=undefined&&$scope.query.wayBillId!=""){
				var param={"orderId":orderId,"trackingNum":$scope.query.wayBillId};
				var url="scheTaskBO.ajax?cmd=queryWayBillDetail";
				commonService.postUrl(url,param,function(data){
					$scope.showData={};
					$scope.showData=data;
					$scope.doQueryOrg(data.ordOrderInfo.orderId);
					$scope.query.wayBillId=data.ordOrderInfo.trackingNum;
					$scope.params.provinceId=data.ordOrderInfo.destProvince;
					$scope.params.cityId=data.ordOrderInfo.destCity;
					if(data.signInfo!=null&&data.signInfo!=undefined&&data.signInfo!=''){
						var flowId=data.signInfo.signPic.split(",");
						for(var i=0;i<flowId.length;i++){
							 eval("$scope.idenCard"+(i+1)+".initDate("+flowId[i]+")");
						}
						$scope.idenCard1.isUpShow(false);
						$scope.idenCard2.isUpShow(false); 
						$scope.idenCard3.isUpShow(false);
						$scope.idenCard4.isUpShow(false); 
						$scope.idenCard5.isUpShow(false); 
					}
					if(data.repairSign!=null&&data.repairSign!=undefined&&data.repairSign!=''){
						var flowId=data.repairSign.fixImageId.split(",");
						for(var i=0;i<flowId.length;i++){
							 eval("$scope.idenCard"+(i+1)+".initDate("+flowId[i]+")");
						}
						$scope.identityCard1.isUpShow(false);
						$scope.identityCard2.isUpShow(false); 
						$scope.identityCard3.isUpShow(false);
						$scope.identityCard4.isUpShow(false); 
						$scope.identityCard5.isUpShow(false); 
					}
					$scope.showData.ordFee.otherFee = $scope.showData.ordFee.freight/100
					+$scope.showData.ordFee.discount/100+
					$scope.showData.ordFee.deliveryCosts/100+
					$scope.showData.ordFee.packingCosts/100+
					$scope.showData.ordFee.handingCosts/100+
					$scope.showData.ordFee.pushMoney/100+
					$scope.showData.ordFee.collectingMoney/100+
					$scope.showData.ordFee.procedureFee/100+
					$scope.showData.ordFee.offer/100+
					$scope.showData.ordFee.upstairsFee/100;
					
					if(isNaN($scope.showData.ordFee.otherFee)){
						$scope.showData.ordFee.otherFee = '';
					}

					$scope.showData.ordFee.sumFee = $scope.showData.ordFee.freightCollect/100+
					$scope.showData.ordFee.cashPayment/100+
					$scope.showData.ordFee.receiptPayment/100+
					$scope.showData.ordFee.monthlyPayment/100+
					$scope.showData.ordFee.freight/100+
					$scope.showData.ordFee.discount/100+
					$scope.showData.ordFee.deliveryCosts/100+
					$scope.showData.ordFee.packingCosts/100+
					$scope.showData.ordFee.handingCosts/100+
					$scope.showData.ordFee.pushMoney/100+
					$scope.showData.ordFee.collectingMoney/100+
					$scope.showData.ordFee.procedureFee/100+
					$scope.showData.ordFee.offer/100+
					$scope.showData.ordFee.upstairsFee/100;
					
					if(isNaN($scope.showData.ordFee.sumFee)){
						$scope.showData.ordFee.sumFee = '';
					}
					if($scope.userData.orgId==data.ordOrderInfo.orgId){
						$scope.isHide=true;
					}
					$scope.query.wayBillId=data.ordOrderInfo.trackingNum;
					$scope.query.inputOrgId=data.ordOrderInfo.orgId;
					$scope.query.orderId=data.ordOrderInfo.orderId;
					setContentHegthDelay();
				});
//			}
		},
		doQueryOrg:function(orderId){
			var param={"orderId":orderId};
			var url = "dutyInfoBO.ajax?cmd=doQuery";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
						$scope.orgInfo=data;
						$scope.query.dutyObjId=data.items[0].id;
						for(var j=0;j<data.items.length;j++){
							var obj=data.items[j];
							if($scope.dutyObjId==obj.targetId&&$scope.dutyType==obj.type){
								$scope.query.dutyObjId=obj.id;
							}
						}
	 	    	}
			});
		},
		tabShow:function(name,cursel,n){
			 for(i=1;i<n+1;i++){
				  var menu=document.getElementById(name+i);
				  var con=document.getElementById("con_"+name+"_"+i);
				  menu.className=i==cursel?"hover":"";
				  con.style.display=i==cursel?"block":"none";
				 }
		},
		close:function(){
	    		commonService.closeToParentTab(true);
		},
		hideTask:function(flag){
			if(flag==1){
				$scope.isTaskShowRepair=false;
				$scope.query.isRepair=0;
			}else{
				$scope.query.isExc=0;
				$scope.isTaskShowExc=false;
				$scope.query.excSfUserId="";
			}
		},
		showTask:function(flag){
			if(flag==1){
				$scope.isTaskShowRepair=true;
				$scope.query.isRepair=1;
				$scope.query.repairFee="";
				$scope.query.sfPhone="";
				$scope.query.sfName="";
			}else{
				$scope.isTaskShowExc=true;
				$scope.query.isExc=1;
				$scope.query.excRepairFee="";
				$scope.query.excSfPhone="";
				$scope.query.excSfUserId="";
				$scope.query.excSfName="";
			}
			setContentHegthDelay();
		},
		doSave:function(){
			$scope.isComit = true;
			if(!$scope.isComit){
				commonService.alert("正在提交,请不要重复提交");
				return;
			}
			if($scope.query.wayBillId==null||$scope.query.wayBillId==''||$scope.query.wayBillId==undefined){
				commonService.alert("运单号不能为空!");
				return false;
			}
			if($scope.query.dutyObjId==null||$scope.query.dutyObjId==''||$scope.query.dutyObjId==undefined){
				commonService.alert("责任网点不能为空!");
				return false;
			}
			if($scope.query.sfPhone!=null&&$scope.query.sfPhone!=''&&$scope.query.sfPhone!=undefined){
				if($scope.query.isRepair==1){
					if($scope.query.sfUserId==null||$scope.query.sfUserId==''||$scope.query.sfUserId==undefined){
						commonService.alert("师傅不存在，请重现选择师傅!");
						return false;
					}
				}
			}
			if($scope.query.excSfPhone!=null&&$scope.query.excSfPhone!=''&&$scope.query.excSfPhone!=undefined){
				if($scope.query.isExc==1){
					if($scope.query.excSfUserId==null||$scope.query.excSfUserId==''||$scope.query.excSfUserId==undefined){
						commonService.alert("异常金额承担师傅不存在，请选择师傅!");
						return false;
					}
				}
			}
			var flowId="";
			if($scope.exceImage1.get().flowId!=null&&$scope.exceImage1.get().flowId!=undefined&&$scope.exceImage1.get().flowId!=''){
				flowId=$scope.exceImage1.get().flowId;
			}
			if($scope.exceImage2.get().flowId!=null&&$scope.exceImage2.get().flowId!=undefined&&$scope.exceImage2.get().flowId!=''){
				if(flowId!=''){
					flowId+=","+$scope.exceImage2.get().flowId;
				}else{
					flowId+=$scope.exceImage2.get().flowId;
				}
			}
			if($scope.exceImage3.get().flowId!=null&&$scope.exceImage3.get().flowId!=undefined&&$scope.exceImage3.get().flowId!=''){
				if(flowId!=''){
					flowId+=","+$scope.exceImage3.get().flowId;
				}else{
					flowId+=$scope.exceImage3.get().flowId;
				}
			}
			if($scope.exceImage4.get().flowId!=null&&$scope.exceImage4.get().flowId!=undefined&&$scope.exceImage4.get().flowId!=''){
				if(flowId!=''){
					flowId+=","+$scope.exceImage4.get().flowId;
				}else{
					flowId+=$scope.exceImage4.get().flowId;
				}
			}
			if($scope.exceImage5.get().flowId!=null&&$scope.exceImage5.get().flowId!=undefined&&$scope.exceImage5.get().flowId!=''){
				if(flowId!=''){
					flowId+=","+$scope.exceImage5.get().flowId;
				}else{
					flowId+=$scope.exceImage5.get().flowId;
				}
			}
			$scope.isComit = false;
			$scope.query.flowId=flowId;
			$scope.query.trackingNum=$scope.query.wayBillId;
			var param=$scope.query;
			var url = "exceptionInfoBO.ajax?cmd=doSave";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("保存成功!");
					var excId=getQueryString("excId");
					if(excId!=null&&excId!=undefined&&excId!=''){
						$scope.queryExceInfo(excId);
					}else{
						//登记－保存数据－刷新页面
						$scope.query={};
						$scope.exceImage1.clean();
						$scope.exceImage2.clean();
						$scope.exceImage3.clean();
						$scope.exceImage4.clean();
						$scope.exceImage5.clean();
					}
					$scope.isComit = true;
					$scope.query.excState=1;
					$scope.isTaskShowRepair=false;
					$scope.query.isRepair=0;
					$scope.query.repairFee="";
					$scope.query.sfPhone="";
					$scope.query.sfName="";
					$scope.isTaskShowExc=false;
					$scope.query.isExc=0;
					$scope.query.excRepairFee="";
					$scope.query.excSfPhone="";
					$scope.query.excSfUserId="";
					$scope.query.excSfName="";
	 	    	}
			},function(data){
				$scope.isComit = true;
				commonService.alert(data.message);
				return;
			});
		},
		doQuery:function(){
			document.getElementById('exc_2').style.display='block';
			document.getElementById('alertMsgIsShow').style.display='block';
			$timeout(function(){
				$scope.page.load({
							url:'cmSfUserInfoBO.ajax?cmd=queryUserInfo',
							params:$scope.params
						});
			},500);
		},
		selectSf:function(){
			var data=$scope.page.getSelectData();
			if(data==null||data==undefined||data.length==0){
				commonService.alert("请至少选择一条师傅信息!");
				return false;
			}
			if($scope.flag==1){
				$scope.query.sfPhone=data[0].phone;
				$scope.query.sfUserId=data[0].sfUserId;
				$scope.query.userType=1;
				$scope.query.sfName=data[0].name;
			}else{
				$scope.query.excSfPhone=data[0].phone;
				$scope.query.excSfUserId=data[0].sfUserId;
				$scope.query.excSfName=data[0].name;
			}
			document.getElementById('exc_2').style.display='none';
			document.getElementById('alertMsgIsShow').style.display='none';
		},
		doQueryOrd:function(){
			document.getElementById('exc_1').style.display='block';
			document.getElementById('alertMsgIsShow').style.display='block';
			var url = "exceptionInfoBO.ajax?cmd=queryOrderInfoOfExc";
			$scope.form.page=1;
			$scope.form.signState=-1;
			$scope.form.count = 10;
			$timeout(function(){
				$scope.orderInfo.load({
							url:url,
							params:$scope.form
						});
			},500);
		},
		selectOrder:function(){
			document.getElementById('exc_1').style.display='none';
			document.getElementById('alertMsgIsShow').style.display='none';
			var data=$scope.orderInfo.getSelectData();
			if(data==null||data==undefined||data.length==0){
				commonService.alert("请至少选择一条运单信息!");
				return false;
			}
			$scope.toView(data[0].orderId);
			$scope.form = {};
		},
		clearModel : function(){
		     $scope.form = {};
		},
		closeView : function(){
			document.getElementById('exc_1').style.display='none';
			document.getElementById('alertMsgIsShow').style.display='none';
	         $scope.clearModel();
		},
		querySf:function(flag){
			if(userInfo.userType==6){
				if($scope.query.sfPhone!=null&&$scope.query.sfPhone!=undefined&&$scope.query.sfPhone!=""){
					var param={"phone":$scope.query.sfPhone};
					var url="exceptionInfoBO.ajax?cmd=queryMatchSf";
					commonService.postUrl(url,param,function(data){
						if(data!=null&&data!=undefined&&data!=''){
							if(flag==1){
								$scope.query.sfPhone=data.phone;
								$scope.query.sfUserId=data.sfUserId;
								$scope.query.sfName=data.name;
								$scope.query.userType=1;
							}else{
								$scope.query.excSfPhone=data[0].phone;
								$scope.query.excSfUserId=data[0].sfUserId;
								$scope.query.excSfName=data[0].name;
							}
						}else{
							if(flag==1){
								$scope.query.sfUserId='';
								$scope.query.sfName='';
							}else{
								$scope.query.excSfUserId="";
								$scope.query.excSfName="";
							}
						}
					});
				}
			}
		},
		selectProvince:function(){
			var param = {"isAll":"Y"};
			var url = "staticDataBO.ajax?cmd=selectProvince";
			commonService.postUrl(url,param,function(data){
				// 成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.provinceData=data;
					$scope.user.provinceId=data.items[0].id;
	 	    	}
			});
		},
		selectCity:function(provinceId){
			if(parseInt(provinceId)>0){
				var param = {"isAll":"Y","provinceId":provinceId};
				var url = "staticDataBO.ajax?cmd=selectCity";
				commonService.postUrl(url,param,function(data){
					// 成功执行
					if(data!=null && data!=undefined && data!=""){
						$scope.cityData=data;
						$scope.user.regionId=data.items[0].id;
		 	    	}
				});
			}
		},
		queryMaster:function(){
			document.getElementById('master').style.display='block';
			document.getElementById('alertMsgIsShow').style.display='block';
			excRegister.selectProvince();
			var url = "organizationBO.ajax?cmd=doQuerySFPartnersExc";
			$timeout(function(){
				$scope.masterInfo.load({
							url:url,
							params:$scope.user,
						});
			},500);
		},
		closeMaster:function(){
			document.getElementById('master').style.display='none';
			document.getElementById('alertMsgIsShow').style.display='none';
	         $scope.clear();
		},
		selectMaster:function(){
			var data=$scope.masterInfo.getSelectData();
			if(data==null||data==undefined||data.length==0){
				commonService.alert("请至少选择一条合作商信息!");
				return false;
			}
			$scope.query.sfPhone=data[0].linkPhone;
			$scope.query.sfUserId=data[0].tenantId;
			$scope.query.sfName=data[0].name;
			$scope.query.userType=2;
			document.getElementById('master').style.display='none';
			document.getElementById('alertMsgIsShow').style.display='none';
		},
		querySfOrService:function(flag){
			if(userInfo.userType==6){
				$scope.doQuery();
			}else{
				$scope.params.sfUserName="";
				$scope.params.sfUserAcct="";
				$scope.queryMaster();
			}
			$scope.flag=flag;
		}
	};
	excRegister.init();
}]);
