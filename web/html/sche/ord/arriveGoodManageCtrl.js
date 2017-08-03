var arriveGoodManageApp = angular.module("arriveGoodManageApp", ['commonApp']);
arriveGoodManageApp.controller("arriveGoodManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var arriveGoodManage={
		init:function(){
			this.userData();
			this.doQuery();
			this.bindEvent();
			/*this.selectCurrOrgId();
			this.desOrgData();*/
			
		},
		bindEvent:function(){
			$scope.param=this.param;
			$scope.doQuery=this.doQuery;
		    $scope.print=this.print;
		    $scope.toView=this.toView;
		    $scope.query=this.query;
		    $scope.clear=this.clear;
		    $scope.selectAll=this.selectAll;
		    $scope.confirmGoodVehi=this.confirmGoodVehi;
		    $scope.selectOne=this.selectOne;
		    $scope.arriveVehConf=this.arriveVehConf;
            /**TODO**/
//		    $scope.selectCurrOrgId=this.selectCurrOrgId;
		    $scope.desOrgData=this.desOrgData;
		    $scope.currOrgData=this.currOrgData;
		    $scope.destOrgData=this.destOrgData;
		    $scope.isTrue = false;
		    $scope.commonExport = this.commonExport;
		    $scope.paramsExport = "{}";
		},
		params:{
		},
		query:{
			vehicleState:"2"
		},
		currOrgData:{
			
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
			var excelLables = "发车批次,配载时间,发车时间,车辆状态,总票数,总重量,总体积,发车网点,目的网点,车牌号,司机姓名,司机手机";
			var excelKeys = "batchNum,loadTimeStr*,departTimeStr,stateName,orderNum@,weight@,volume@,sourceOrgIdName,descOrgIdName,plateNumber,driverName,driverBill";
			var params = $scope.paramsExport;
			var queryUrl = "orderInfoBO.ajax?cmd=queryDepartInfo";
			commonService.downloadExcelFile(queryUrl,eval("("+params+")"),excelLables,excelKeys);
			 $scope.isTrue = true;
			$("#exportId").html("数据加载中...");
			//导出倒计时
			$timeout(function() {
				 $scope.isTrue = false;
				$("#exportId").html("导出");
			},3600);
			
		},
		//目的网点都是当前网点
		destOrgData:new Array(),
		userData:function(){
			var url = "staticDataBO.ajax?cmd=selectOrgInfo";
			var param={"orgId":userInfo.orgId};
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.orgInfoData=data;
				    $scope.orgType=data.orgType;
				    
				    $scope.destOrgData.push(data);
				    
				    $scope.desOrgData($scope.orgType);
				    
	 	    	}
			});
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
		/**列表查询*/
		doQuery:function(){
			arriveGoodManage.params.vehicleState=arriveGoodManage.query.vehicleState;
			arriveGoodManage.params.startOrgId=arriveGoodManage.query.startOrgId;
			arriveGoodManage.params.descOrgId=arriveGoodManage.query.currOrgId;
			arriveGoodManage.params.batchNum=arriveGoodManage.query.batchNum;
			
			arriveGoodManage.params.beginTime=document.getElementById("beginTime").value;
			arriveGoodManage.params.endTime=document.getElementById("endTime").value;
			var url = "orderInfoBO.ajax?cmd=serviceQueryMatchInfo";
			
			$scope.tableCallBack=function(){
				setContentHegthDelay();
				 $scope.paramsExport = JSON.stringify(arriveGoodManage.params);
			}
			
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:arriveGoodManage.params,
							callBack:"$scope.tableCallBack"
						});
			},500);
		},
		/**清空查询条件*/
		clear:function(){
			document.getElementById("beginTime").value='';
			document.getElementById("endTime").value='';
			$scope.query.batchNum="";
			$scope.query.vehicleState='-1';
			$scope.query.startOrgId="";
			$scope.query.currOrgId="";
		},
		/**选择一行**/
		selectOne : function(batchNum){
			if(document.getElementById("checkbox"+batchNum).checked && document.getElementById("checkbox"+batchNum) != undefined){
				document.getElementById("checkbox"+batchNum).checked=false;
			}else{
				document.getElementById("checkbox"+batchNum).checked=true;
			}
		},
		/**查看**/
		toView:function(){
			var batchNum='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条配载信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条配载信息!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					batchNum=data.batchNum;
				}
			});
			commonService.openTab(batchNum,"配载详情","/sche/ord/depearInfo.html?batchNum="+batchNum);
				},
		/**打印*/
		print:function(){
		},
		/**
		 * 查询目的网点
		 */
//		selectCurrOrgId:function(orgType){
//			if(orgType==2){
//				console.log("orgType:"+orgType);
//				var param = {"collectType":4,"addType":1,"collectState":2};
//				var url = "routeBO.ajax?cmd=getUserRoute";	
//				commonService.postUrl(url,param,function(data){
//					//成功执行
//					if(data!=null && data!=undefined && data!=""){
//					    $scope.currOrgData=data.routeList;
//					    $scope.query.currOrgId=parseInt(userInfo.orgId);
//					    $scope.doQuery();
//		 	    	}
//				});
//			}
//		},
		/**
		 * 查询发车网点
		 */
		desOrgData:function(orgType){
//			if(orgType==2){
//				var param = {"collectType":4,"addType":1,"collectState":1};
//				var url = "routeBO.ajax?cmd=getUserRoute";	
//				commonService.postUrl(url,param,function(data){
//					//成功执行
//					if(data!=null && data!=undefined && data!=""){
//					    $scope.desOrgData=data.routeList;
//		 	    	}
//				});
//			}else{
				var param = {"collectType":3,"addType":1};
				var url = "routeBO.ajax?cmd=getUserRoute";	
				commonService.postUrl(url,param,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
					    $scope.desOrgData=data.routeList;
							 $scope.currOrgData=new Array(); 
							 if(data.routeList!=null && data.routeList!=undefined && data.routeList.length>0){
							       $scope.currOrgData.push(data.routeList[0]);
							       $scope.query.currOrgId= $scope.currOrgData[0].beginOrgId;
					         } 
		 	    	}
				});
//			}
			
		},
		/**到货确认*/
		confirmGoodVehi:function(){
			var batchNum='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条配载信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条配载信息!");
				return false;
			}
			var flag=false;
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					if(data.state!=3&&data.state!=5){
						flag=true;
						commonService.alert("批次["+data.batchNum+"]状态为"+data.stateName+",不可以操作[到货确认]!");
						return false;
					}
					batchNum=data.batchNum;
				}
			});
			if(flag){
				return false;
			}
			commonService.openTab(batchNum+"22","到货确认","/sche/ord/depearInfo.html?batchNum="+batchNum+"&flag=1");
		},
		/***到车确认**/
		arriveVehConf:function(){
			var batchNum='';
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请至少选择一条配载信息!");
				return false;
			}
			if($("input[name='check-1']:checked").length>1){
				commonService.alert("只能选择一条配载信息!");
				return false;
			}
			var flag=false;
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					if(data.state!=2){
						commonService.alert("批次["+data.batchNum+"]状态为"+data.stateName+",不可以操作[到车确认]!");
						flag=true;
						return false;
					}
					batchNum=data.batchNum;
				}
			});
			if(flag){
				return false;
			}
			commonService.confirm("批次号["+batchNum+"]是否进行[到车确认]!",function(){
				var param = {"batchNum":batchNum};
				var url = "orderInfoBO.ajax?cmd=ordArriveVehicle";
				commonService.postUrl(url,param,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
						commonService.alert("操作成功!");
						$scope.clear();
						$scope.query.currOrgId=parseInt(userInfo.orgId);
						$scope.query.vehicleState="3";
						$scope.doQuery();
		 	    	}
				});
			});
		},
		
		
	};
	
	arriveGoodManage.init();
}]);
