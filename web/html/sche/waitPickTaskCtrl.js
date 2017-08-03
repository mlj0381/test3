var waitPIckTaskApp = angular.module("waitPIckTaskApp", ['commonApp','tableCommon']);
waitPIckTaskApp.controller("waitPIckTaskCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var waitPIckTask={
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
			      "width":"100"
		     },
		     {
		    	 "name":"服务类型",
			      "code":"serverType",
			      "width":"100"
		     },
		     {
		    	 "name":"发货人",
			      "code":"clientName",
			      "type":"text",
			      "width":"100"
		     },
		     {
		    	 "name":"收货人",
			     "code":"receiverName",
			     "type":"text",
			     "width":"100"
		     },
		     {
		    	 "name":"货品",
			     "code":"products",
			     "width":"100"
		     },
		     {
		    	 "name":"安装件数",
			     "code":"count",
			     "isSum":"true",
			     "width":"100"
		     },
		     {
		    	 "name":"体积",
			     "code":"volumes",
			     "width":"100",
			     "isSum": "true",
			     "number": "2"
		     },
		     {
		    	 "name":"重量",
			     "code":"weight",
			     "width":"100",
			     "isSum": "true",
			     "number": "2"
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
			     "width":"200"
		     },
		     {
		    	 "name":"师傅",
			      "code":"sfName",
			      "type":"text",
			      "width":"100"
		     },
		     {
		    	 "name":"师傅帐号",
			      "code":"sfPhone",
			      "type":"text",
			      "width":"100"
		     },
		     {
		    	 "name":"配安费(元)",
			      "code":"branchAndInstall",
			      "width":"100",
			      "itemType":"text",
			      "isSum": "true",
			      "number": "2"
		     },
		     {
		    	 "name":"代收货款(元)",
			      "code":"collectingMoney",
			      "width":"100",
		    	  "isSum": "true",
		    	  "number": "2"
		     },
		     {
		    	 "name":"到付(元)",
			      "code":"freightCollect",
			      "width":"100",
		    	  "isSum": "true",
		    	  "number": "2"
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
			      "width":"100"
		     }
		      ],
		bindEvent:function(){
			$scope.head=waitPIckTask.head;
			$scope.url="scheTaskBO.ajax?cmd=doTaskQuery";
			$scope.param = this.param;
			$scope.query = this.query;
			$scope.doQuery = this.doQuery;
		    $scope.clear = this.clear;
		    $scope.statisticsTask=this.statisticsTask;
		    $scope.closeYy=this.closeYy;
		    $scope.openYy=this.openYy;
		    $scope.showCredit=this.showCredit;
		    $scope.showGoodsDetail=this.showGoodsDetail;
		    $scope.starOn=this.starOn;
		    $scope.showOrClose=this.showOrClose;
		    $scope.pickUp=this.pickUp;
		    $scope.yy=this.yy;
		    $scope.openWayDetail=this.openWayDetail;
		    $scope.openExc=this.openExc;
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
				var data =ordArray[i];
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
						commonService.alert("修改成功!");
						$scope.doQuery();
		 	    	}
				});
			});
		},
		//取消分配
		cancerDis:function(){
			var ordArray=$scope.table.getSelectData();
			if(ordArray.length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			if(ordArray.length>1){
				commonService.alert("只能选择一条任务信息!");
				return false;
			}
			var orderId='';
			var taskId='';
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
			var data = ordArray[0];
			orderId=data.orderId;
			commonService.openTab("exc"+orderId,"异常登记","/sche/exc/excRegister.html?isShow=1&orderId="+orderId);
		},
		//查看详情
		openWayDetail:function(){
			var ordArray=$scope.table.getSelectData();
			if(ordArray.length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			if(ordArray.length>1){
				commonService.alert("只能选择一条任务信息!");
				return false;
			}
			var orderId='';
			var taskId='';
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
			taskState:5,
			isTmall:"-1"
		},
		//订单流程信息
		showGoodsDetail:function(orderId){
			var url="scheTaskBO.ajax?cmd=goodsDetail";
			var param="orderId="+orderId;
			var html="";
			commonService.postUrl(url,param,function(data){
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
		//预约
		openYy:function(){
			var trackingNum='';
			var cityName='';
			var ordArray=$scope.table.getSelectData();
			if(ordArray.length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			 for(var i=0;i<ordArray.length;i++){
				 var data=ordArray[i]; 
				 if(data.yyTime!=null&&data.yyTime!=undefined&&data.yyTime!=''){
					trackingNum=data.wayBillId;
					commonService.alert("["+trackingNum+"]等运单已操作过预约，无需再操作!");
						return false;
					}
				if(cityName!=''){
					if(cityName!=data.cityName){
						commonService.alert("所选任务，目的城市不一样,请重新选择!");
						return false;
					}
					}
					cityName=data.cityName;
			 }
			document.getElementById('vehicle_yy').style.display='block';
			document.getElementById('fade1_xz').style.display='block';
		},
		closeYy:function(){
			document.getElementById('vehicle_yy').style.display='none';
			document.getElementById('fade1_xz').style.display='none';
			document.getElementById('yyTime').value='';
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
		//预约保存
		yy:function(){
			var ordArray=$scope.table.getSelectData();
			var orderId="";
			var taskId="";
			for(var i=0;i<ordArray.length;i++){
				var data=ordArray[i];
				orderId+=data.orderId+",";
				taskId+=data.taskId+",";
			}
			var yyTime=document.getElementById('yyTime').value;
			if(yyTime==null||yyTime==undefined||yyTime==''){
				commonService.alert("请选择上门时间!");
				return false;
			}
			var param = {"orderId":orderId,"taskId":taskId,"yyTime":yyTime,"ipFixTime":document.getElementById('ipFixTime').value};
			var url = "scheTaskBO.ajax?cmd=yy";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
					$scope.closeYy();
					commonService.alert("预约成功!");
					$scope.doQuery();
					$scope.statisticsTask();
	 	    	}
			});
		},
		//提货
		pickUp:function(){
			var orderIdArr =new Array();
			var taskIdArr =new Array();
			var ordArray=$scope.table.getSelectData();
			if(ordArray.length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			for(var i=0;i<ordArray.length;i++){
				var data=ordArray[i];
				orderIdArr.push(data.orderId);
				taskIdArr.push(data.taskId);
				if(data.pickTime!=null&&data.pickTime!=undefined&&data.pickTime!=''){
					trackingNum=data.wayBillId;
					commonService.alert("运单["+trackingNum+"]已经操作过提货,无需再操作!");
					return false;
				}
			}
			var param={"orderId":orderIdArr.join(","),"taskId":taskIdArr.join(",")};
			var url = "scheTaskBO.ajax?cmd=pickUp";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("操作成功!");
					$scope.doQuery();
					$scope.statisticsTask();
	 	    	}
			});
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
			$scope.des.clear();
			$scope.query.sfPhone="";
			$scope.query.sfName="";
			$scope.query.clientName="";
			$scope.query.wayBillId="";
			$scope.query.isTmall="-1";
			$scope.query.receiveName='';
			document.getElementById('endTime').value="";
			document.getElementById('beginTime').value="";
			$scope.table.clearInput();
			
		},
	};
	waitPIckTask.init();
}]);
