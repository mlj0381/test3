var timeOutTaskApp = angular.module("timeOutTaskApp", ['commonApp','tableCommon']);
timeOutTaskApp.controller("timeOutTaskCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var timeOutTask={
		init:function(){
			this.bindEvent();
			this.statisticsTask();
		},
		head:[
		      {
		    	"name":"运单号",
		    	"code":"wayBillId",
		    	"width":"100",
		    	"type":"text"
		      },
		      {
		    	"name":"服务类型",
		    	"code":"serverType",
		    	"width":"100"
		      },
		      {
		    	"name":"配安费",
		    	"code":"branchAndInstallFee",
		    	"width":"100",
		    	"number":"2",
		    	"isSum":"true"
		    		
		      },
		      {
		    	"name":"异常类型",
		    	"code":"timeOutTypeName",
		    	"width":"100"
		      },
		      {
		    	"name":"异常状态",
		    	"code":"dealStateName",
		    	"width":"100"
		      },
		      {
		    	"name":"安装师傅",
		    	"code":"sfName",
		    	"width":"100",
		    	"type":"text"
		    		
		      },
		      {
		    	"name":"安装师傅账号",
		    	"code":"sfPhone",
		    	"width":"100",
		    	"type":"text"
		      },
		      {
		    	"name":"任务状态",  
		    	"code":"taskStateName",
		    	"width":"100"
		      },
		      {
		    	"name":"开始时间",  
		    	"code":"startTime",
		    	"width":"130"
		      },
		      {
		    	  "name":"截止时间",
		    	  "code":"endTime",
		    	  "width":"130"
		      },
		      {
		    	  "name":"时限（h）",
		    	  "code":"timeLimit",
		    	  "width":"130"
		      },
		      {
		    	  "name":"超时（h）",
		    	  "code":"remainingTime",
		    	  "width":"130"
		      }
		      ],
		bindEvent:function(){
			$scope.head=timeOutTask.head;
			$scope.url="timeOutBO.ajax?cmd=doQuery";
			$scope.urlParam=timeOutTask.query;
			$scope.query = this.query;
			$scope.doQuery = this.doQuery;
		    $scope.clear = this.clear;
		    $scope.selectAll = this.selectAll;
		    $scope.selectOne = this.selectOne;
		    $scope.statisticsTask=this.statisticsTask;
		    $scope.closeSign=this.closeSign;
		    $scope.openSign=this.openSign;
		    $scope.showCredit=this.showCredit;
		    $scope.starOn=this.starOn;
		    $scope.doSign=this.doSign;
		    $scope.closeYy=this.closeYy;
		    $scope.openYy=this.openYy;
		    $scope.yy=this.yy;
		    $scope.closeGxEnd=this.closeGxEnd;
		    $scope.openGxEnd=this.openGxEnd;
		    $scope.gxEnd=this.gxEnd;
		    $scope.timeOutDeal=this.timeOutDeal;
		    $scope.doSave=this.doSave;
		    $scope.form=this.form;
		    $scope.closeTimeOut=this.closeTimeOut;
		    $scope.gotoAI=this.gotoAI;
		    $scope.openWayDetail=this.openWayDetail;
		    $scope.openExc=this.openExc;
		},
		//异常登记
		openExc:function(){
			var ordArray=$scope.table.getSelectData();
			var orderId='';
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
		form:{
			taskState:4
		},
		query:{
			provinceId:-1,
		},
		showCredit:function(sfUserId){
    		if(sfUserId==null||sfUserId==undefined|| sfUserId==''){
    			commonService.alert("师傅ID为空!");
    			
    			
    			return false;
    		}
			var param = {"userId":sfUserId};
    		var url="creditManageBO.ajax?cmd=getDtlById";
			commonService.postUrl(url,param,function(data){
				setContentHeigth();
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
		//时效异常处理
		timeOutDeal:function(){
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
			timeOutId=data.timeOutId;
			$scope.dealState=data.dealState;
			var param = {"timeOutId":timeOutId};
			var url = "timeOutBO.ajax?cmd=doQueryDtl";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
						$scope.timeOutDtl=data;
						document.getElementById('vehicle_sx').style.display='block';
						document.getElementById('fade1_xz').style.display='block';
						$scope.form.dealIdea='';
	 	    	}
			});
		},
		closeTimeOut:function(){
			document.getElementById('vehicle_sx').style.display='none';
			document.getElementById('fade1_xz').style.display='none';
		},
		//保存时效异常
		doSave:function(){
			var timeOutId=null;
			var ordArray=$scope.table.getSelectData();
			var data=ordArray[0];
			timeOutId=data.timeOutId;
			if($scope.form.dealIdea==null||$scope.form.dealIdea==undefined||$scope.form.dealIdea==''){
				commonService.alert("请填写处理意见!");
				return false;
			}
			var param = {"timeOutId":timeOutId,"dealState":$scope.form.dealState,"dealIdea":$scope.form.dealIdea};
			var url = "timeOutBO.ajax?cmd=doSave";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("提交成功!");
					$scope.closeTimeOut();
					$scope.doQuery();
	 	    	}
			});
		},
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
			if(data.taskState!=7){
				taskStateName=data.taskStateName;
				commonService.alert("任务状态为"+taskStateName+",不允许操作签收!");
				return false;
			}
			document.getElementById('vehicle_qs').style.display='block';
			document.getElementById('fade1_xz').style.display='block';
		},
		closeSign:function(){
			document.getElementById('vehicle_qs').style.display='none';
			document.getElementById('fade1_xz').style.display='none';
			document.getElementById('yyTime').value='';
		},
		//预约
		openYy:function(){
			var ordArray=$scope.table.getSelectData();
			if(ordArray.length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			var trackingNum='';
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
			var orderIdArr =new Array();
			var taskIdArr =new Array();
			for(var i=0;i<ordArray.length;i++){
				var data=ordArray[0];
				orderIdArr.push(data.orderId);
				taskIdArr.push(data.taskId);
			}
			var yyTime=document.getElementById('yyTime').value;
			if(yyTime==null||yyTime==undefined||yyTime==''){
				commonService.alert("请选择上门时间!");
				return false;
			}
			var param = {"orderId":orderIdArr.join(","),"taskId":taskIdArr.join(","),"yyTime":yyTime,"ipFixTime":document.getElementById('ipFixTime').value};
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
		//签收保存
		doSign:function(){
			var taskId='';
			var orderId='';
			var ordArray=$scope.table.getSelectData();
			var data=ordArray[0];
			taskId=data.taskId;
			orderId=data.orderId;
			if($scope.query.receiverName==null||$scope.query.receiverName==undefined||$scope.query.receiverName==''){
				commonService.alert("请填写签收人!");
				return false;
			}
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
	 	    	}
			});
		},
		//前往人工调度
		gotoAI:function(){
			var ordArray=$scope.table.getSelectData();
			if(ordArray.length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			if(ordArray.length>1){
				commonService.alert("只能选择一条任务信息!");
				return false;
			}
			var wayBillId='';
			var data=ordArray[0];
			wayBillId=data.wayBillId;
			window.location.href=commonService.getRootPath()+"/sche/AIscheTask.html?wayBillId="+wayBillId;
		},
		doQuery:function(){
			var url = "timeOutBO.ajax?cmd=doQuery";
			$scope.table.load();
			$scope.table.callBack=function(){
				displayTable();
				setContentHeight();
			} 
		},
		clear:function(){
			$scope.query.sfPhone="";
			$scope.query.sfName="";
			$scope.query.wayBillId="";
			$scope.query.taskState="-1";
			$scope.query.timeOutType="-1";
			$scope.query.dealState="-1";
			$scope.cityData={};
			$scope.districtData={};
			$scope.table.clearInput();
			
		},
		selectOne : function(taskId){
			if(document.getElementById("checkbox"+taskId).checked && document.getElementById("checkbox"+taskId) != undefined){
				document.getElementById("checkbox"+taskId).checked=false;
			}else{
				document.getElementById("checkbox"+taskId).checked=true;
			}
		},
		openGxEnd:function(){
			var cityName='';
			var isAddress=true;
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					if(cityName!=''){
						if(cityName!=data.cityName){
							isAddress=false;
							return false;
						}
					}
					cityName=data.cityName;
				}
			});
			if(!isAddress){
				commonService.alert("所选任务，目的城市不一样,请重新选择!");
				return false;
			}
			document.getElementById('vehicle_qxjs').style.display='block';
			document.getElementById('fade1_xz').style.display='block';
		},
		closeGxEnd:function(){
			document.getElementById('vehicle_qxjs').style.display='none';
			document.getElementById('fade1_xz').style.display='none';
			$scope.query.pickUpAddr='';
			$scope.query.pickUpPhone='';
			$scope.query.pickCode='';
		},
		gxEnd:function(){
			var orderIdArr =new Array();
			var taskIdArr =new Array();
			var pickUpAddr=$scope.query.pickUpAddr;
			var pickUpPhone=$scope.query.pickUpPhone;
			var pickCode=$scope.query.pickCode;
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					orderIdArr.push(data.orderId);
					taskIdArr.push(data.taskId);
				}
			});
//			if(pickUpAddr==null||pickUpAddr==undefined||pickUpAddr==''){
//				commonService.alert("请填写提货地址!");
//				return false;
//			}
//			if(pickUpPhone==null||pickUpPhone==undefined||pickUpPhone==''){
//				commonService.alert("请填写提货手机!");
//				return false;
//			}
//			if(pickCode==null||pickCode==undefined||pickCode==''){
//				commonService.alert("请填写提货验证码!");
//				return false;
//			}
		    var param={"orderId":orderIdArr.join(","),"taskId":taskIdArr.join(","),"pickUpPhone":pickUpPhone,"pickUpAddr":pickUpAddr,"pickCode":pickCode};
			var url = "scheTaskBO.ajax?cmd=savePickInfo";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("操作成功!");
					$scope.doQuery();
					$scope.closeGxEnd();
	 	    	}
			});
		}, 
	};
	timeOutTask.init();
}]);
