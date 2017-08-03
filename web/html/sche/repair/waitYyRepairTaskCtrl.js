var waitYyRepairTaskApp = angular.module("waitYyRepairTaskApp", ['commonApp']);
waitYyRepairTaskApp.controller("waitYyRepairTaskCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var waitYyRepairTask={
		init:function(){
			$scope.des={};
			this.bindEvent();
			this.doQuery();
			this.statisticsTask();
		},
		bindEvent:function(){
			$scope.param = this.param;
			$scope.query = this.query;
			$scope.doQuery = this.doQuery;
		    $scope.clear = this.clear;
		    $scope.selectAll = this.selectAll;
		    $scope.selectOne = this.selectOne;
		    $scope.statisticsTask=this.statisticsTask;
		    $scope.showCredit=this.showCredit;
		    $scope.showGoodsDetail=this.showGoodsDetail;
		    $scope.starOn=this.starOn;
		    $scope.showOrClose=this.showOrClose;
		    $scope.openExc=this.openExc;
		    $scope.dealExc=this.dealExc;
		    $scope.closeYy=this.closeYy;
		    $scope.openYy=this.openYy;
		    $scope.yy=this.yy;
		    $scope.cancerDis=this.cancerDis;
		    $scope.fixNumber=this.fixNumber;
		    $scope.doModifyFee=this.doModifyFee;
		},
		doModifyFee:function(){
			var taskId=new Array();
			var fee=new Array();
			var trackingNum='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			var isError=false;
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					taskId.push(data.taskId);
					if(document.getElementById(data.taskId+"repair").value==null||document.getElementById(data.taskId+"repair").value==undefined||document.getElementById(data.taskId+"repair").value==''){
						isError=true;
					}
					fee.push(document.getElementById(data.taskId+"repair").value);
					trackingNum=data.wayBillId;
				}
			});
			if(isError){
				commonService.alert("请填写合理的费用信息!");
				return false;
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
		cancerDis:function(){
			var taskId='';
			var wayBillId='';
			var orderId='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条任务信息!");
				return false;
			}
			var isOK=true;
			var taskStateName='';
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					taskId=data.taskId;
					wayBillId=data.wayBillId;
					orderId=data.orderId;
					taskStateName=data.taskStateName;
					if(data.taskState==4){
						isOK=false;
					}
				}
			});
			if(!isOK){
				commonService.alert("运单状态为["+taskStateName+"],不能取消分配!");
				return false;
			}
			commonService.confirm("运单号["+wayBillId+"],是否确认取消分配？",function(){
				var param = {"taskId":taskId,"orderId":orderId,"isExceSche":1};
				var url = "scheTaskBO.ajax?cmd=cancerDis";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
						$scope.statisticsTask();
						$scope.doQuery();
						commonService.alert("取消分配成功!");
		 	    	}
				});
			});
		},
		openYy:function(){
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			var trackingNum='';
			var isAddress=true;
			var isYy=true;
			var cityName="";
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					if(data.yyTime!=null&&data.yyTime!=undefined&&data.yyTime!=''){
						trackingNum=data.wayBillId;
						isYy=false;
						return false;
					}
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
			if(!isYy){
				commonService.alert("["+trackingNum+"]等运单已操作过预约，无需再操作!");
				return false;
			}
			document.getElementById('vehicle_yy').style.display='block';
			document.getElementById('fade1_xz').style.display='block';
		},
		closeYy:function(){
			document.getElementById('vehicle_yy').style.display='none';
			document.getElementById('fade1_xz').style.display='none';
			document.getElementById('yyTime').value='';
		},
		yy:function(){
			var orderIdArr =new Array();
			var taskIdArr =new Array();
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					orderIdArr.push(data.orderId);
					taskIdArr.push(data.taskId);
				}
			});
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
		dealExc:function(isView){
			var excId='';
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
					excId=data.excId;
					}
			});
			var excState=null;
			if(isView==2){
				excState=3;
			}
			commonService.openTab("exc"+excId,"任务详情","/sche/exc/excRegister.html?isShow=2"+"&excId="+excId+"&isView="+isView+"&excState="+excState);
		},
		openExc:function(){
			var wayBillId='';
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
			commonService.openTab("exc"+orderId,"异常登记","/sche/exc/excRegister.html?isShow=1&orderId="+orderId);
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
			taskState:7
		},
		query:{
			provinceId:-1,
			taskState:6,
			queryType:-1,
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
		statisticsTask:function(){
			var param = {};
			var url = "repairScheTaskBO.ajax?cmd=statisticsTaskCount";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
						$scope.statisticsTaskCount=data;
	 	    	}
			});
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
			var url = "repairScheTaskBO.ajax?cmd=doTaskQuery";
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:$scope.query,
							callBack:'setContentHegthDelay'
						});
			},500);
		},
		clear:function(){
			$scope.query.sfName="";
			$scope.query.sfPhone="";
			$scope.des.clean();
			$scope.query.receiveName="";
			$scope.query.clientName="";
			$scope.query.wayBillId="";
			$scope.query.isTmall="-1";
		},
		selectOne : function(taskId){
			if(document.getElementById("checkbox"+taskId).checked && document.getElementById("checkbox"+taskId) != undefined){
				document.getElementById("checkbox"+taskId).checked=false;
			}else{
				document.getElementById("checkbox"+taskId).checked=true;
			}
		},
	};
	waitYyRepairTask.init();
}]);
