var scheQueryApp = angular.module("scheQueryApp", ['commonApp','tableCommon']);
scheQueryApp.controller("scheQueryCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var scheQuery={
		init:function(){
			$scope.des={};
			this.bindEvent();
		},
		head:[
		     {
		          "name": "运单号",
		          "code": "wayBillId",
		          "width": "110",
		          "type":"text"
		     },
		     {
		    	 "name":"服务类型",
		    	 "code":"serverType",
		    	 "width":"90",
		     },
		     {
		    	 "name":"发货人",
		    	 "code":"clientName",
		    	 "width":"130",
		    	 "type":"text"
		     },
		     {
		    	 "name":"收货人",
		    	 "code":"receiverName",
		    	 "width":"130",
		    	 "type":"text"
		     },
		     {
		    	"name":"收货人手机",
		    	"code":"receiverBillId",
		    	"width":"110",
		    	"type":"text"
		     },
		     {
		    	"name":"品名",
		    	"code":"products",
		    	"width":"90"
		     },
		     {
		    	"name":"件数",
		    	"code":"count",
		    	"isSum": "true",
		    	"width":"60"
		     },
		     {
		    	"name":"体积",
		    	"code":"volumes",
		    	"number": "2",
		    	"isSum": "true",
		    	"width":"60"
		     },
		     {
		    	 "name":"重量",
		    	 "code":"weight",
		    	 "number": "1",
		    	 "isSum": "true",
		    	 "width":"60"
		     },
		     {
		    	"name": "开单日期",
		    	"code":"inputOrdTime",
		    	"width":"130"
		     },
		     {
		    	 "name":"目的市",
		    	 "code":"destCity",
		    	 "width":"90"
		     },
		     {
		    	 "name":"目的区域",
		    	 "code":"destCounty",
		    	 "width":"90"
		     },
		     {
		    	 "name":"详细地址",
		    	 "code":"receAddr",
		    	 "width":"130"
		     },
		     {
		    	 "name":"代收货款(元)",
		    	 "code":"collectingMoney",
		    	 "isSum": "true",
		    	 "number": "2",
		    	 "width":"110"
		     },
		     {
		    	 "name":"到付(元)",
		    	 "code":"freightCollect",
		    	 "isSum": "true",
		    	 "number": "2",
		    	 "width":"110"
		     },
		     {
		    	"name":"干线状态",
		    	"code":"gxState",
		    	"width":"90"
		     },
		     {
		    	 "name":"干线结束时间",
		    	 "code":"gxTime",
		    	 "width":"130"
		     },
		     {
		    	 "name":"到货状态",
		    	 "code":"dhState",
		    	 "width":"90"
		     },
		     {
		    	 "name":"到货时间",
		    	 "code":"gxEndTime",
		    	 "width":"130"
		     },
		     {
		    	 "name":"任务状态",
		    	 "code":"orderStateName",
		    	 "selectSource":"TASK_STATE",
		    	 "width":"130",
		    	 "type":"select",
		    	 "exclude":"未匹配,已受理（公司转公司）,取消签收"
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
		    	 "name":"签收时间",
		    	 "code":"signTime",
		    	 "width":"130"
		     },
		     {
		    	 "name":"师傅",
		    	 "code":"sfName",
		    	 "width":"110",
		    	 "type":"text"
		     },
		     {
		    	 "name":"师傅帐号",
		    	 "code":"sfPhone",
		    	 "width":"130",
		    	 "type":"text"
		     },
		     {
		    	 "name":"配安费(元)",
		    	 "code":"branchAndInstall",
		    	 "isSum": "true",
		    	 "number": "2",
		         "width": "80"
		     },
		     {
		    	 "name":"付款方式",
		    	 "code":"payTypeName",
			     "width": "70"
		     } ,
		     {
		    	 "name":"配送网点",
		    	 "code":"disOrgName",
		    	 "width":"90"
		     },
		     {
		    	 "name":"备注",
		    	 "code":"remark",
		    	 "width":"130"
		     }
		     ],
		bindEvent:function(){
			$scope.head=scheQuery.head;
			$scope.url="scheTaskBO.ajax?cmd=doTaskQuery";
			$scope.urlParam=scheQuery.query;
			
			$scope.param = this.param;
			$scope.query = this.query;
			$scope.doQuery = this.doQuery;
		    $scope.clear = this.clear;
		    $scope.selectCity=this.selectCity;
		    $scope.selectProvince=this.selectProvince;
		    $scope.selectDistrict=this.selectDistrict;
		    $scope.selectStreet=this.selectStreet;
		    $scope.closeSign=this.closeSign;
		    $scope.openSign=this.openSign;
		    $scope.showCredit=this.showCredit;
		    $scope.showGoodsDetail=this.showGoodsDetail;
		    $scope.starOn=this.starOn;
		    $scope.showOrClose=this.showOrClose;
		    $scope.doSign=this.doSign;
		    $scope.gxEnd=this.gxEnd;
		    $scope.closeGxEnd=this.closeGxEnd;
		    $scope.openGxEnd=this.openGxEnd;
		    $scope.closeYy=this.closeYy;
		    $scope.openYy=this.openYy;
		    $scope.openUp=this.openUp;
		    $scope.pickUp=this.pickUp;
		    $scope.yy=this.yy;
		    $scope.openUp=this.openUp;
		    $scope.closeUp=this.closeUp;
		    $scope.doSavePic=this.doSavePic;
		    $scope.openWayDetail=this.openWayDetail;
		    $scope.openExc=this.openExc;
		    $scope.arriveGoods=this.arriveGoods;
		    $scope.deleteTask=this.deleteTask;
		    $scope.cancerDis=this.cancerDis;
		    $scope.downloadExcelFile=this.downloadExcelFile;
		    
		    $scope.isTrue = false;
		    $scope.commonExport = this.commonExport;
		    $scope.paramsExport = "{}";
		},
		//导出方法
		commonExport : function(){
			if($scope.isTrue){
				return false;
			}
			var beginTime = document.getElementById("beginDate").value;
			var endTime = document.getElementById("endDate").value;
			
			if(beginTime==undefined || beginTime==null || beginTime==""){
				commonService.alert("导出运单数据的开始时间不能为空，请选择开始时间！");
				return false;
			}
			
			if(endTime==undefined || endTime==null || endTime==""){
				commonService.alert("导出运单数据的结束时间不能为空，请选择结束时间！");
				return false;
			}
			
			var date1=new Date(beginTime);
			var date2=new Date(endTime);
			var date3=date2.getTime()-date1.getTime();//时间差的毫秒数
			//计算出相差天数
			var days=Math.floor(date3/(24*3600*1000));
			
			if(days>31){
				commonService.alert("只能导出一个月的数据，请重新选择时间！");
				return false;
			}		
			$scope.isTrue = true;
			$("#exportId").html("导出中。");
			
			$scope.table.downloadExcelFile();
			
			//导出倒计时
			$timeout(function() {
				 $scope.isTrue = false;
				$("#exportId").html("导出");
			},3600);
			
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
        	var data= ordArray[0];
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
		 	    	}
				});
			})
		},
		/*deleteTask:function(){
			var orderIdArr =new Array();
			var taskIdArr =new Array();
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			var taskStateName='';
			var trackingNum='';
			var isOk=true;
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					orderIdArr.push(data.orderId);
					taskIdArr.push(data.taskId);
					if(data.orderState!=2&&data.orderState!=4){
						taskStateName=data.orderStateName;
						trackingNum=data.wayBillId;
						isOk=false;
					}
					}
			});
			if(!isOk){
				commonService.alert("运单["+trackingNum+"]任务状态为"+taskStateName+",不允许删除!");
				return false;
			}
			commonService.confirm("是否删除所选中的任务？",function(){
				var url="scheTaskBO.ajax?cmd=deleteScheTask";
				var param={"orderId":orderIdArr.join(","),"taskId":taskIdArr.join(",")};
				commonService.postUrl(url,param,function(data){
					commonService.alert("操作成功!");
					$scope.doQuery();
				});
			});
		},*/
		//到货
		arriveGoods:function(){
			var orderId="";
			var taskId="";
			var ordArray =$scope.table.getSelectData();
			if(ordArray.length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			var arriveGoodsTime='';
			var trackingNum='';
			for(var i=0;i<ordArray.length;i++){
				var data= ordArray[i];
            	orderId+= data.orderId+",";
            	taskId+=data.taskId+",";
				if(arriveGoodsTime==''){
					arriveGoodsTime=data.gxEndTime;
					trackingNum=data.wayBillId;
					break;
				}
			if(arriveGoodsTime!=''){
				commonService.alert("运单["+trackingNum+"]到货状态为[已到货]，无需再操作!");
				return false;
			}
				
			}
			var url="scheTaskBO.ajax?cmd=arriveGoods";
			var param={"orderId":orderId,"taskId":taskId};
			commonService.postUrl(url,param,function(data){
				commonService.alert("操作成功!");
				$scope.doQuery();
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
				commonService.alert("只能选择一条任务信息");
				return false;
			}
				var data=ordArray[0];
				orderId=data.orderId;
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
			taskState:4
		},
		query:{
			provinceId:-1,
			isGxEnd:"-1",
			isArriveGoods:"-1"
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
           	var data= ordArray[0];
			orderId=data.orderId;
			taskId=data.taskId;
           commonService.openTab(taskId+orderId,"任务详情","/sche/task/waybill_details.html?orderId="+orderId+"&taskId="+taskId);
			
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
		//签收
		openSign:function(){
			var taskStateName=''; 
			var ordArray=$scope.table.getSelectData();
			if(ordArray.length==0){
				commonService.alert("请至少选择一条任务信息！");
				return false;
			}
			if(ordArray.length>1){
				commonService.alert("只能选择一条任务信息！");
				return false;
			}
				var data=ordArray[0];
				if(data.orderState!=7&&data.orderState!='7'){
					taskStateName=data.orderStateName;
					commonService.alert("任务状态为"+taskStateName+",不允许操作签收！");
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
		doSign:function(){
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
				var data= ordArray[0];
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
		doQuery:function(){
			$scope.query.provinceId = $scope.des.chooseCityId;
			$scope.query.cityId = $scope.des.chooseRegionId;
		    $scope.query.countyId = $scope.des.chooseCountyId;
			//$scope.query.endDate = $("#endDate").val();
			//$scope.query.beginDate = $("#beginDate").val();
			var url = "scheTaskBO.ajax?cmd=doTaskQuery";
			$scope.tableCallBack=function(){
				$scope.paramsExport = JSON.stringify(scheQuery.query)
			}
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
			$scope.query.receiveName="";
			$scope.query.wayBillId="";
			$scope.query.isTmall="-1";
			$scope.query.taskState="-1";
			$scope.query.isArriveGoods="-1";
			$scope.query.isGxEnd="-1";
			$scope.query.endDate="";
			$scope.query.beginDate="";
			$("#endDate").val("");
			$("#beginDate").val("");
			
			
		},
		//补录提货信息
		openGxEnd:function(){
			orderIdArr=$scope.table.getSelectData();
			var cityName='';
			if(orderIdArr==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			for(var i=0;i<orderIdArr;i++){
				var data= orderIdArr[i];
				if(cityName!=""){
					if(cityName!=data.cityName){
						commonService.alert("所选任务，目的城市不一样,请重新选择!");
						return false;
					}
				}
				cityName=data.cityName;
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
			var orderId="";
			var taskId="";
			var pickUpAddr=$scope.query.pickUpAddr;
			var pickUpPhone=$scope.query.pickUpPhone;
			var pickCode=$scope.query.pickCode;
			var orderIdArr=$scope.table.getSelectData();
			if(orderIdArr.length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			for(var i=0;i<orderIdArr.length;i++){
				var data=orderIdArr[i];
				orderId += data.orderId+",";
				taskId+=data.taskId+",";
			}

			 var param={"orderId":orderId,"taskId":taskId,"pickUpPhone":pickUpPhone,"pickUpAddr":pickUpAddr,"pickCode":pickCode};
				var url = "scheTaskBO.ajax?cmd=savePickInfo";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
						commonService.alert("操作成功!");
						$scope.doQuery();
						$scope.closeGxEnd();
		 	    	}
				});
		},
		//预约保存
		yy:function(){
			var orderId="";
			var taskId="";
			var ordArray=$scope.table.getSelectData();
             for(var i=0;i<ordArray.length;i++){
        	   var data=ordArray[i];
               if(data!=null && data!=undefined && data!=''){
               		orderId+=data.orderId+",";
               		taskId+=data.taskId+",";
               	}
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
	 	    	}
			});
		},
		//提货
		pickUp:function(){
			var ordArray=$scope.table.getSelectData();
			var orderId = "";
			var taskId="";
			 if(ordArray.length==0){
				 commonService.alert("请至少选择一条任务信息!");
				 return false;
	            }
			 var trackingNum='';
			 for(var i=0;i<ordArray.length;i++){
	             var data= ordArray[i];
	             if(data !=null && data != undefined && data != ''){
	            		orderId += data.orderId+",";
	            		taskId+=data.taskId+",";
	            		if(data.pickTime!=null&&data.pickTime!=undefined&&data.pickTime!=''){
							trackingNum=data.wayBillId;
							commonService.alert("运单["+trackingNum+"]已经操作过提货,无需再操作!");
							return false;
						}
	            	}
			 }
				var param={"orderId":orderId,"taskId":taskId};
				var url = "scheTaskBO.ajax?cmd=pickUp";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
						commonService.alert("操作成功!");
						$scope.doQuery();
		 	    	}
				});
		},
		//预约
		openYy:function(){
			var ordArray=$scope.table.getSelectData();
			if(ordArray.length==0){
				commonService.alert("请至少选择一条任务信息!");
				return false;
			}
			var trackingNum='';
			var cityName='';
			for(var i=0;i<ordArray.length;i++){
            	var data= ordArray[i];
            	if(data !=null && data != undefined && data != ''){
					var data =ordArray[i];
					if(data.yyTime!=null && data.yyTime!=undefined && data.yyTime!=''){
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
			}
			document.getElementById('vehicle_yy').style.display='block';
			document.getElementById('fade1_xz').style.display='block';
			
		},
		closeYy:function(){
			document.getElementById('vehicle_yy').style.display='none';
			document.getElementById('fade1_xz').style.display='none';
			document.getElementById('yyTime').value='';
		},
		//上传签收图
		openUp:function(){
			var ordArray = new Array();
            ordArray=$scope.table.getSelectData();
            if(ordArray.length==0){
            	commonService.alert("请至少选择一条任务信息!");
				return false;
            }
            if(ordArray.length>1){
            	commonService.alert("只能选择一条任务信息!");
				return false;
            }
			var taskStateName='';
			var taskId="";
			var orderId="";
				var data=ordArray[0];
				taskStateName=data.orderStateName;
				taskId=data.taskId;
				orderId=data.orderId;
				if(data.orderState!=8){
					commonService.alert("任务状态为"+taskStateName+",不允许上传签收图!");
					return false;
				}
			var param = {"taskId":taskId,"orderId":orderId};
			var url = "scheTaskBO.ajax?cmd=querySignPic";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
					if(data.isData=='Y'){
						$scope.openSign();
					}else{
						if(data.flowId!=""&&data.flowId!=null&&data.flowId!=undefined){
							var flowIdArr=data.flowId.split(",");
							if(flowIdArr.length==5){
								$scope.isShow=false;
							}
							for (i=0;i<flowIdArr.length ;i++ )   
						    {   
						        eval("$scope.idenCard"+(i+1)+".initDate("+flowIdArr[i]+")");
						        eval( "$scope.idenCard"+(i+1)+".isUpShow(false)"); 
						    }   
						}
						document.getElementById('vehicle_up_qs').style.display='block';
						document.getElementById('fade1_xz').style.display='block';
					}
	 	    	}
			});
		},
		closeUp:function(){
			$scope.idenCard1.clean();
			$scope.idenCard2.clean();
			$scope.idenCard3.clean();
			$scope.idenCard4.clean();
			$scope.idenCard5.clean();
			$scope.idenCard1.isUpShow(true);
			$scope.idenCard2.isUpShow(true);
			$scope.idenCard3.isUpShow(true);
			$scope.idenCard4.isUpShow(true);
			$scope.idenCard5.isUpShow(true);
			document.getElementById('vehicle_up_qs').style.display='none';
			document.getElementById('fade1_xz').style.display='none';
		},
		//保存签收图
		doSavePic:function(){
			var flowId='';
			var taskId='';
			var orderId='';
			var ordArray =$scope.table.getSelectData();
            	var data= ordArray[0];
            	taskId=data.taskId;
				orderId=data.orderId;
			if($scope.idenCard1.get().flowId!=null&&$scope.idenCard1.get().flowId!=undefined&&$scope.idenCard1.get().flowId!=''){
				flowId=$scope.idenCard1.get().flowId;
			}
			if($scope.idenCard2.get().flowId!=null&&$scope.idenCard2.get().flowId!=undefined&&$scope.idenCard2.get().flowId!=''){
				if(flowId!=''){
					flowId+=","+$scope.idenCard2.get().flowId;
				}else{
					flowId+=$scope.idenCard2.get().flowId;
				}
			}
			if($scope.idenCard3.get().flowId!=null&&$scope.idenCard3.get().flowId!=undefined&&$scope.idenCard3.get().flowId!=''){
				if(flowId!=''){
					flowId+=","+$scope.idenCard3.get().flowId;
				}else{
					flowId+=$scope.idenCard3.get().flowId;
				}
			}
			if($scope.idenCard4.get().flowId!=null&&$scope.idenCard4.get().flowId!=undefined&&$scope.idenCard4.get().flowId!=''){
				if(flowId!=''){
					flowId+=","+$scope.idenCard4.get().flowId;
				}else{
					flowId+=$scope.idenCard4.get().flowId;
				}
			}
			if($scope.idenCard5.get().flowId!=null&&$scope.idenCard5.get().flowId!=undefined&&$scope.idenCard5.get().flowId!=''){
				if(flowId!=''){
					flowId+=","+$scope.idenCard5.get().flowId;
				}else{
					flowId+=$scope.idenCard5.get().flowId;
				}
			}
			var param = {"taskId":taskId,"orderId":orderId,"flowId":flowId};
			var url = "scheTaskBO.ajax?cmd=doSaveSignPic";
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
					$scope.closeUp();
					commonService.alert("签收成功!");
					$scope.doQuery();
	 	    	}
			});
		}
			
	};
	scheQuery.init();
}]);
