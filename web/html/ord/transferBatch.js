var departManageApp = angular.module("departManageApp", ['commonApp']);
departManageApp.controller("departManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var departManage={
		init:function(){
			this.userData();
			//this.doQuery();
			this.bindEvent();
		},
		bindEvent:function(){
			$scope.param=this.param;
			$scope.doQuery=this.doQuery;
		    $scope.deleteDepart=this.deleteDepart;
		    $scope.print=this.print;
		    $scope.toView=this.toView;
		    $scope.query=this.query;
		    $scope.clear=this.clear;
		    $scope.selectAll=this.selectAll;
		    $scope.addTrans=this.addTrans;
		    $scope.confirmMatchVehi=this.confirmMatchVehi;
		    $scope.doModify=this.doModify;
		    $scope.selectOne=this.selectOne;
		    $scope.query=this.query;
		    
		    
		    $scope.selectCurrOrgId=this.selectCurrOrgId;
		    $scope.desOrgData=this.desOrgData;
		    $scope.currOrgData=this.currOrgData;
		    $scope.commonExport=this.commonExport;
		    $scope.isTrue = false;
		    $scope.paramsExport = "{}";
		    $scope.tranSign=this.tranSign;
		    $scope.transferOfMakeup = this.transferOfMakeup;
		},
		params:{
			page:1	
		},
		query:{
			vehicleState:"-1"
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
				var excelLables = "中转批次,中转时间,总票数,总重量,总体积,中转网点,承运网点";

				var excelKeys = "batchNum,createDate,orderNum@,weight@,volume@,sourceOrgIdName,descOrgIdName";
				
				var params = $scope.paramsExport
				var queryUrl = "transferBO.ajax?cmd=batchQuery";
				commonService.downloadExcelFile(queryUrl,eval("("+params+")"),excelLables,excelKeys);
				 $scope.isTrue = true;
				$("#exportId").html("数据加载中...");
				//导出倒计时
				$timeout(function() {
					 $scope.isTrue = false;
					$("#exportId").html("导出");
				},3000);
				
			},
		userData:function(){
			
			
			/*var url = "staticDataBO.ajax?cmd=selectOrgInfo";
			var param={"orgId":userInfo.orgId};
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.orgInfoData=data;
				    $scope.orgType=data.orgType;
				    $scope.showData=true;
				    if($scope.orgType==2){
				    	 $scope.showData=false;
				    }
				    $scope.selectCurrOrgId($scope.orgType);
				    $scope.desOrgData($scope.orgType);
				    
	 	    	}
			});*/
			
			
			
			
			
			
			$scope.currOrgId=userInfo.orgId;
			$scope.currOrgName=userInfo.orgName;
			var url="orderInfoBO.ajax?cmd=queryOrgType";
			commonService.postUrl(url,"orgId="+$scope.currOrgId,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.orgInfoData=data;
				    $scope.orgType=data.orgType;
					 $scope.selectCurrOrgId($scope.orgType);
					    $scope.desOrgData($scope.orgType);
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
		/**列表查询*/
		doQuery:function(){
			departManage.params.vehicleState=departManage.query.vehicleState;
			departManage.params.descOrgId=departManage.query.descOrgId;
			/**TODO 中心能查看全部**/
			departManage.params.startOrgId=departManage.query.currOrgId;
			departManage.params.descOrgId=departManage.query.descOrgId;
			
			departManage.params.batchNum=departManage.query.batchNum;
			departManage.params.orderId=departManage.query.orderId;
			departManage.params.trackingNum=departManage.query.trackingNum;
			departManage.params.beginTime=document.getElementById("beginTime").value;
			departManage.params.endTime=document.getElementById("endTime").value;
			var url = "transferBO.ajax?cmd=batchQuery";
			departManage.params.page=1;

			$scope.tableCallBack=function(){
				setContentHegthDelay();
				 $scope.paramsExport = JSON.stringify(departManage.params);
			}
			
			
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:departManage.params,
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
			//$scope.query.descOrgId=-1;
			$scope.query.descOrgId="";
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
			var selectDatas =  $scope.page.getSelectData();
			if(selectDatas.length == 0){
				commonService.alert("请选择一条中转信息!");
				return false;
			}
			if(selectDatas.length > 1){
				commonService.alert("只能选择一条中转信息!");
				return false;
			}
			batchNum = selectDatas[0].batchNum;
			commonService.openTab(batchNum+"view","中转详情","/ord/viewDepartOtherTransitUser.html?view=1&batchNum="+batchNum);
		},
		/***保存**/
		addTrans:function(){
			commonService.openTab("11111","新增中转信息","/ord/addDepartOtherTransitUser.html");
		},
		/***修改**/
		doModify:function(){
			var batchNum='';
			var selectDatas =  $scope.page.getSelectData();
			if(selectDatas.length == 0){
				commonService.alert("请选择一条中转信息!");
				return false;
			}
			if(selectDatas.length > 1){
				commonService.alert("只能选择一条中转信息!");
				return false;
			}
			batchNum = selectDatas[0].batchNum;
			commonService.openTab(batchNum+"1","中转修改","/ord/addDepartOtherTransitUser.html?view=2&batchNum="+batchNum);
		},
		/**打印*/
		print:function(){
		},
		/**删除*/
		deleteDepart:function(){
			var batchNum='';
			var selectDatas =  $scope.page.getSelectData();
			if(selectDatas.length == 0){
				commonService.alert("请选择一条中转信息!");
				return false;
			}
			if(selectDatas.length > 1){
				commonService.alert("只能选择一条中转信息!");
				return false;
			}
			batchNum = selectDatas[0].batchNum;
			var param = {"batchNum":batchNum};
			var url = "transferBO.ajax?cmd=canlTrans";
			commonService.confirm("是否删除中转信息",function(){
				commonService.postUrl(url,param,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
							commonService.alert("删除成功!");
							$scope.doQuery();
		 	    	}
				});
			});
		},
		
		
		
		/**
		 * 查询目的网点
		 */
		selectCurrOrgId:function(orgType){
			if(orgType==2){
				var param = {"collectType":4,"addType":1,"collectState":1};
				var url = "routeBO.ajax?cmd=getUserRoute";	
				commonService.postUrl(url,param,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
					    $scope.desOrgData=data.routeList;
		 	    	}
				});
			}else{
				var param = {"collectType":3,"addType":1};
				var url = "routeBO.ajax?cmd=getUserRoute";	
				commonService.postUrl(url,param,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
					    $scope.desOrgData=data.routeList;
							 $scope.currOrgData=new Array(); 
							 if(data.routeList!=null && data.routeList!=undefined && data.routeList.length>0){
							       $scope.currOrgData.push(data.routeList[0]);
							       $scope.query.currOrgId=  $scope.desOrgData[0].beginOrgId;
					         } 
		 	    	}
				});
			}
		},
		
		/**
		 * 查询发车网点
		 */
		desOrgData:function(orgType){
			if(orgType==2){
				var param = {"collectType":4,"addType":1,"collectState":2};
				var url = "routeBO.ajax?cmd=getUserRoute";	
				commonService.postUrl(url,param,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
					    $scope.currOrgData=data.routeList;
					    $scope.query.currOrgId=parseInt(userInfo.orgId);
		 	    	}
				});
			}
			$timeout(function(){
				   $scope.doQuery();
			},500);
			
		},
		/**中转签收*/
		tranSign:function(){
			var batchNum='';
			var selectDatas =  $scope.page.getSelectData();
			if(selectDatas.length == 0){
				commonService.alert("请选择一条中转信息!");
				return false;
			}
			if(selectDatas.length > 1){
				commonService.alert("只能选择一条中转信息!");
				return false;
			}
			batchNum = selectDatas[0].batchNum;
			commonService.openTab(batchNum+"3","中转签收","/ord/signDepartOtherTransitUser.html?view=2&batchNum="+batchNum);
		},
		/**发车确认*/
		confirmMatchVehi:function(){
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
					if(data.state!=1){
						flag=true;
						commonService.alert("批次["+data.batchNum+"]状态为"+data.stateName+",不可以操作[发车确认]!");
						return false;
					}
					batchNum=data.batchNum;
				}
			});
			if(flag){
				return false;
			}
			var param = {"batchNum":batchNum};
			var url = "orderInfoBO.ajax?cmd=confirmMatchVehi";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
						commonService.alert("操作成功!");
						 $scope.query.currOrgId=parseInt(userInfo.orgId);
							$scope.query.vehicleState="-1";
						$scope.doQuery();
					

	 	    	}
			});
		},
		transferOfMakeup:function(){
			commonService.openTab("111111212","中转补录信息","/ord/signDepartOther.html?isMakeup=1");
		}
		
	};
	
	departManage.init();
}]);
