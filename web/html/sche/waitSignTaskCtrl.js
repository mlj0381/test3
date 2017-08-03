var waitSignTaskApp = angular.module("waitSignTaskApp", ['commonApp','tableCommon']);
waitSignTaskApp.controller("waitSignTaskCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var waitSignTask={
		init:function(){
			$scope.des={};
			this.bindEvent();
			this.statisticsTask();
		},
		head:[
		      {
		    	  "name":"运单号",
		    	  "code":"wayBillId",
		    	  "type":"text",
		    	  "width":"110"
		      },
		      {
		    	  "name":"服务类型",
		    	  "code":"serverType",
		    	  "width":"110"
		      },
		      {
		    	  "name":"发货人",
		    	  "code":"clientName",
		    	  "type":"text",
		    	  "width":"110"
		      },
		      {
		    	  "name":"收货人",
		    	  "code":"receiverName",
		    	  "type":"text",
		    	  "width":"110"
		      },
		      {
		    	  "name":"货品",
		    	  "code":"products",
		    	  "width":"100"
		      },
		      {
		    	  "name":"安装件数",
		    	  "code":"count",
		    	  "width":"110",
		    	  "isSum":"true"
		      },
		      {
		    	  "name":"体积",
		    	  "code":"volumes",
		    	  "width":"110",
		    	  "isSum":"true",
		    	  "number":"2"
		   
		      },
		      {
		    	  "name":"重量",
		    	  "code":"weight",
		    	  "width":"110",
		    	  "isSum":"true",
		    	  "number":"2"
		      },
		      {
		    	  "name":"开单日期",
		    	  "code":"inputOrdTime",
		    	  "width":"120"
		      },
		      {
		    	  "name":"目的市",
		    	  "code":"destCity",
		    	  "width":"100"
		      },
		      {
		    	  "name":"目的区域",
		    	  "code":"destCounty",
		    	  "width":"100"
		      },
		      {
		    	  "name":"详细地址",
		    	  "code":"receAddr",
		    	  "width":"100"
		      },
		      {
		    	  "name":"师傅",
		    	  "code":"sfName",
		    	  "type":"text",
		    	  "width":"110"
		      },
		      {
		    	  "name":"师傅帐号",
		    	  "code":"sfPhone",
		    	  "type":"text",
		    	  "width":"110"
		      },
		      {
		    	  "name":"配安费(元)",
		    	  "code":"branchAndInstall",
		    	  "width":"110",
		    	  "itemType":"text",
		    	  "isSum":"true",
		    	  "number":"2"
		      },
		      {
		    	  "name":"代收货款(元)",
		    	  "code":"collectingMoney",
		    	  "width":"100",
		    	  "isSum":"true",
		    	  "number":"2"
		      },
		      {
		    	  "name":"到付(元)",
		    	  "code":"freightCollect",
		    	  "width":"100",
		    	  "isSum":"true",
		    	  "number":"2"
		      },
		      {
		    	  "name":"到货时间",
		    	  "code":"gxEndTime",
		    	  "width":"130"
		      },
		      {
		    	  "name":"接单时间",
		    	  "code":"acceptTime",
		    	  "width":"130"
		      },
		      {
		    	  "name":"提货时间",
		    	  "code":"pickTime",
		    	  "width":"130"
		      },
		      {
		    	  "name":"预约时间",
		    	  "code":"upFixTime",
		    	  "width":"130"
		      },
		      {
		    	  "name":"上门时间",
		    	  "code":"yyTime",
		    	  "width":"130"
		      },
		      {
		    	  "name":"倒计时",
		    	  "code":"remainingTime",
		    	  "width":"110"
		      }
		      ],
		bindEvent:function(){
			$scope.head=waitSignTask.head;
			$scope.url="scheTaskBO.ajax?cmd=doTaskQuery";
			$scope.param = this.param;
			$scope.query = this.query;
			$scope.doQuery = this.doQuery;
		    $scope.clear = this.clear;
		    $scope.selectAll = this.selectAll;
		    $scope.selectOne = this.selectOne;
		    $scope.statisticsTask=this.statisticsTask;
		    $scope.closeSign=this.closeSign;
		    $scope.openSign=this.openSign;
		    $scope.showCredit=this.showCredit;
		    $scope.showGoodsDetail=this.showGoodsDetail;
		    $scope.starOn=this.starOn;
		    $scope.showOrClose=this.showOrClose;
		    $scope.openWayDetail=this.openWayDetail;
		    $scope.openExc=this.openExc;
		    $scope.doSign=this.doSign;
		    $scope.cancerDis=this.cancerDis;
		    $scope.doModifyFee=this.doModifyFee;
		    $scope.fixNumber=this.fixNumber;
		},
		fixNumber:function (id,name){
			var value=document.getElementById(id+name).value;
			value =  value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
			value =  value.replace(/^\./g,"");  //验证第一个字符是数字而不是. 
			value =  value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.   
			value =  value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
			value=value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数 
			document.getElementById(id+name).value=value;
			document.getElementById("checkbox"+id).checked=true;
		},
		//更新费用
		doModifyFee:function(){
			var taskId=new Array();
			var fee=new Array();
			var ordArray=$scope.table.getSelectData();
			if(ordArray.length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			var trackingNum='';
			for(var i=0;i<ordArray.length;i++){
				var data=ordArray[i];
				taskId.push(data.taskId);
				if(data.branchAndInstall==null||data.branchAndInstall==undefined||data.branchAndInstall==''){
					commonService.alert("请填写合理的费用信息!");
					return false;
				}
				fee.push(data.branchAndInstall);
				trackingNum=data.wayBillId;
			}
			var tips="是否确认修改运单["+trackingNum+"]等任务的金额?";
			var param = {"taskId":taskId.join(","),"fee":fee.join(",")};
				commonService.confirm(tips,function(){
				var url = "scheTaskBO.ajax?cmd=doModifyFee";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
						$scope.doQuery();
						commonService.alert("修改成功!");
		 	    	}
				});
			});
		},
		//取消分配
		cancerDis:function(){
			var taskId='';
			var orderId='';
			var ordArray=$scope.table.getSelectData();
			if(ordArray.length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			if(ordArray.length>1){
				commonService.alert("只能选择一条任务信息!");
				return false;
			}
			var taskStateName='';
			var wayBillId='';
			var data=ordArray[0];
			taskId=data.taskId;
			orderId=data.orderId;
			taskStateName=data.orderStateName;
			wayBillId=data.wayBillId;
			if(data.orderState==8){
				commonService.alert("运单状态为["+taskStateName+"],不能取消分配!");
				return false;
			}
			commonService.confirm("运单号["+wayBillId+"],是否确认取消分配？",function(){
				var param = {"taskId":taskId,"orderId":orderId};
				var url = "scheTaskBO.ajax?cmd=cancerDis";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
						commonService.alert("取消分配成功!");
						$scope.doQuery();
						$scope.statisticsTask();
		 	    	}
				});
			});
		},
		//异常登记
		openExc:function(){
			var orderId='';
			var ordArray=$scope.table.getSelectData();
			if(ordArray.length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			if(ordArray.length>1){
				commonService.alert("只能选择一条任务信息!");
				return false;
			}
			var data=ordArray[0];
			orderId=data.orderId;
			commonService.openTab("exc"+orderId,"异常登记","/sche/exc/excRegister.html?isShow=1&orderId="+orderId);
			/*var orderId='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条任务信息!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					orderId=data.orderId;
					}
			});
			commonService.openTab("exc"+orderId,"异常登记","/sche/exc/excRegister.html?isShow=1&orderId="+orderId);*/
		},
		//查看详情
		openWayDetail:function(){
			var orderId='';
			var taskId='';
			var ordArray=$scope.table.getSelectData();
			if(ordArray.length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			if(ordArray.length>1){
				commonService.alert("只能选择一条任务信息!");
				return false;
			}
			var data=ordArray[0];
			orderId=data.orderId;
			taskId=data.taskId;
			commonService.openTab(taskId+orderId,"任务详情","/sche/task/waybill_details.html?orderId="+orderId+"&taskId="+taskId);
		},
		showOrClose:function(){
			// 收缩展开效果
			if(document.getElementById("ul").style.display=='none'){
				$("#ul").show(1000);
			}else{
				$("#ul").hide(1000);
			}
		},
		params:{
			taskState:4
		},
		query:{
			provinceId:-1,
			taskState:7,
			isTmall:"-1"
		},
		//订单流程信息
		showGoodsDetail:function(orderId){
			var url="scheTaskBO.ajax?cmd=goodsDetail";
			var param="orderId="+orderId;
			var html="";
			commonService.postUrl(url,param,function(data){
				setContentHeigth();
				$scope.goodsInfo=data.goodsInfo;
			    $(".cplx").hover(function(){
                	 $(this).find("span").show();
                },function(){
            	     $(this).find("span").hide();
                });

			});
		},
		showCredit:function(sfUserId){
    		if(sfUserId==null||sfUserId==undefined|| sfUserId==''){
    			commonService.alert("师傅ID为空!");
    			return false;
    		}
			var param = {"userId":sfUserId};
    		var url="creditManageBO.ajax?cmd=getDtlById";
			commonService.postUrl(url,param,function(data){
				$scope.ca= data.ca;
				$scope.cad= data.cad;
				$scope.starOn(data.ca.creditLevel);
				 $(".cplx").hover(function(){
                	 $(this).find("span").show();
                },function(){
            	     $(this).find("span").hide();
                });
			});
		},
		starOn:function(level){
			if(level==1){
				$(".one-star").addClass("on");
			}
			if(level==2){
				$(".one-star").addClass("on");
				$(".two-stars").addClass("on");
			}
			if(level==3){
				$(".one-star").addClass("on");
				$(".two-stars").addClass("on");
				$(".three-stars").addClass("on");
			}
			if(level==4){
				$(".one-star").addClass("on");
				$(".two-stars").addClass("on");
				$(".three-stars").addClass("on");
				$(".four-stars").addClass("on");
			}
			if(level==5){
				$(".one-star").addClass("on");
				$(".two-stars").addClass("on");
				$(".three-stars").addClass("on");
				$(".four-stars").addClass("on");
				$(".five-stars").addClass("on");
			}
		},
		//签收
		openSign:function(){
			var ordArray=$scope.table.getSelectData();
			if(ordArray.length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			if(ordArray.length>1){
				commonService.alert("只能选择一条任务信息!");
				return false;
			}
			var taskStateName='';
			var data=ordArray[0];
			if(data.taskState&&data.orderState!='7'){
				taskStateName=data.orderStateName;
				commonService.alert("任务状态为"+taskStateName+",不允许操作签收!");
				return false;
			}
			document.getElementById('vehicle_qs').style.display='block';
			document.getElementById('fade1_xz').style.display='block';
		},
		closeSign:function(){
			$scope.query.receiverName="";
			$scope.query.IDCard="";
			$scope.query.signDesc="";
			$scope.identityCard1.clean();
			$scope.identityCard2.clean();
			$scope.identityCard3.clean();
			$scope.identityCard4.clean();
			$scope.identityCard5.clean();
			document.getElementById('vehicle_qs').style.display='none';
			document.getElementById('fade1_xz').style.display='none';
		},
		statisticsTask:function(){
			var param = {};
			var url = "scheTaskBO.ajax?cmd=statisticsTaskCount";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
						$scope.statisticsTaskCount=data;
	 	    	}
			});
		},
		//签收保存
		doSign:function(){
			var ordArray=$scope.table.getSelectData();
			if(ordArray.length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			if(ordArray.length>1){
				commonService.alert("只能选择一条任务信息!");
				return false;
			}
			var taskId='';
			var orderId='';
			var data= ordArray[0];
			taskId=data.taskId;
			orderId=data.orderId;
			if($scope.query.receiverName==null||$scope.query.receiverName==undefined||$scope.query.receiverName==''){
				commonService.alert("请填写签收人!");
				return false;
			}
//			if($scope.query.IDCard==null||$scope.query.IDCard==undefined||$scope.query.IDCard==''){
//				commonService.alert("请填写身份证号!");
//				return false;
//			}
			var flowId="";
			if($scope.identityCard1.get().flowId!=null&&$scope.identityCard1.get().flowId!=undefined&&$scope.identityCard1.get().flowId!=''){
				flowId=$scope.identityCard1.get().flowId;
			}
			if($scope.identityCard2.get().flowId!=null&&$scope.identityCard2.get().flowId!=undefined&&$scope.identityCard2.get().flowId!=''){
				if(flowId!=''){
					flowId+=","+$scope.identityCard2.get().flowId;
				}else{
					flowId+=$scope.identityCard2.get().flowId;	
				}
			}
			if($scope.identityCard3.get().flowId!=null&&$scope.identityCard3.get().flowId!=undefined&&$scope.identityCard3.get().flowId!=''){
				if(flowId!=''){
					flowId+=","+$scope.identityCard3.get().flowId;
				}else{
					flowId+=$scope.identityCard3.get().flowId;	
				}
			}
			if($scope.identityCard4.get().flowId!=null&&$scope.identityCard4.get().flowId!=undefined&&$scope.identityCard4.get().flowId!=''){
				if(flowId!=''){
					flowId+=","+$scope.identityCard4.get().flowId;
				}else{
					flowId+=$scope.identityCard4.get().flowId;	
				}
			}
			if($scope.identityCard5.get().flowId!=null&&$scope.identityCard5.get().flowId!=undefined&&$scope.identityCard5.get().flowId!=''){
				if(flowId!=''){
					flowId+=","+$scope.identityCard5.get().flowId;
				}else{
					flowId+=$scope.identityCard5.get().flowId;	
				}
			}
			var param = {"taskId":taskId,"orderId":orderId,"receiverName":$scope.query.receiverName,"IDCard":$scope.query.IDCard,"flowId":flowId,"signDesc":$scope.query.signDesc,"isException":$scope.query.isException};
			var url = "scheTaskBO.ajax?cmd=doSign";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
					$scope.closeSign();
					commonService.alert("签收成功!");
					$scope.doQuery();
					$scope.statisticsTask();
	 	    	}
			});
			/*
			var taskId='';
			var orderId='';
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					taskId=data.taskId;
					orderId=data.orderId;
				}
			});
			if($scope.query.receiverName==null||$scope.query.receiverName==undefined||$scope.query.receiverName==''){
				commonService.alert("请填写签收人!");
				return false;
			}
//			if($scope.query.IDCard==null||$scope.query.IDCard==undefined||$scope.query.IDCard==''){
//				commonService.alert("请填写身份证号!");
//				return false;
//			}
			var flowId="";
			if($scope.identityCard1.get().flowId!=null&&$scope.identityCard1.get().flowId!=undefined&&$scope.identityCard1.get().flowId!=''){
				flowId=$scope.identityCard1.get().flowId;
			}
			if($scope.identityCard2.get().flowId!=null&&$scope.identityCard2.get().flowId!=undefined&&$scope.identityCard2.get().flowId!=''){
				if(flowId!=''){
					flowId+=","+$scope.identityCard2.get().flowId;
				}else{
					flowId+=$scope.identityCard2.get().flowId;	
				}
			}
			if($scope.identityCard3.get().flowId!=null&&$scope.identityCard3.get().flowId!=undefined&&$scope.identityCard3.get().flowId!=''){
				if(flowId!=''){
					flowId+=","+$scope.identityCard3.get().flowId;
				}else{
					flowId+=$scope.identityCard3.get().flowId;	
				}
			}
			if($scope.identityCard4.get().flowId!=null&&$scope.identityCard4.get().flowId!=undefined&&$scope.identityCard4.get().flowId!=''){
				if(flowId!=''){
					flowId+=","+$scope.identityCard4.get().flowId;
				}else{
					flowId+=$scope.identityCard4.get().flowId;	
				}
			}
			if($scope.identityCard5.get().flowId!=null&&$scope.identityCard5.get().flowId!=undefined&&$scope.identityCard5.get().flowId!=''){
				if(flowId!=''){
					flowId+=","+$scope.identityCard5.get().flowId;
				}else{
					flowId+=$scope.identityCard5.get().flowId;	
				}
			}
			var param = {"taskId":taskId,"orderId":orderId,"receiverName":$scope.query.receiverName,"IDCard":$scope.query.IDCard,"flowId":flowId,"signDesc":$scope.query.signDesc,"isException":$scope.query.isException};
			var url = "scheTaskBO.ajax?cmd=doSign";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
					$scope.closeSign();
					commonService.alert("签收成功!");
					$scope.doQuery();
					$scope.statisticsTask();
	 	    	}
			});*/
		},
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
		doQuery:function(){
			$scope.query.provinceId = $scope.des.chooseCityId;
			$scope.query.cityId = $scope.des.chooseRegionId;
			$scope.query.countyId = $scope.des.chooseCountyId;
			if($scope.query.orderState>0){
				$scope.query.taskState=$scope.query.orderState;
			}
			$scope.query.endTime=document.getElementById('endTime').value;
			$scope.query.beginTime=document.getElementById('beginTime').value;
			var url = "scheTaskBO.ajax?cmd=doTaskQuery";
			$scope.table.load();
			$scope.table.callBack=function(){
				displayTable();
				setContentHeight();
			}
		},
		clear:function(){
			$scope.query.sfPhone="";
			$scope.query.sfName="";
			$scope.query.clientName="";
			$scope.query.wayBillId="";
			$scope.query.isTmall="-1";
			$scope.query.receiveName='';
			document.getElementById('endTime').value="";
			document.getElementById('beginTime').value="";
			$scope.des.clear();
			$scope.table.clearInput();
			
		},
		selectOne : function(taskId){
			if(document.getElementById("checkbox"+taskId).checked && document.getElementById("checkbox"+taskId) != undefined){
				document.getElementById("checkbox"+taskId).checked=false;
			}else{
				document.getElementById("checkbox"+taskId).checked=true;
			}
		},
	};
	waitSignTask.init();
}]);
