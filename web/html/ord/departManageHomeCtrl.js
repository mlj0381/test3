function changeTabCallback(){
	var appElement = document.querySelector('[ng-controller=departManageCtrl]');
	var scope=angular.element(appElement).scope();
	scope.doQuery();
	scope.$apply();
}
var departManageApp = angular.module("departManageApp", ['commonApp']);

departManageApp.controller("departManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var exportExcel = false;
	var departManage={
		init:function(){
			this.userData();
			//this.doQuery();
			this.bindEvent();
			$scope.paramsExport = "{}";
			var returnKeyEventDomEleIds = ["beginTime","endTime","id1"];
			commonService.registerKeyEventForDoms(returnKeyEventDomEleIds, "keydown", "return", $scope.doQuery);
		},
		bindEvent:function(){
			$scope.param=this.param;
			$scope.doQuery=this.doQuery;
		    $scope.deleteDepart=this.deleteDepart;
		    $scope.print=this.print;
		    $scope.toView=this.toView;
		    $scope.query=this.query;
		    $scope.clear=this.clear;
		    $scope.doSave=this.doSave;
		    $scope.confirmMatchVehi=this.confirmMatchVehi;
		    $scope.doModify=this.doModify;
		    $scope.selectOne=this.selectOne;
		    $scope.query=this.query;
		    $scope.commonExport = this.commonExport;
		    $scope.paramsExport = "{}";
		    
		    
		    $scope.selectCurrOrgId=this.selectCurrOrgId;
		    $scope.desOrgData=this.desOrgData;
		    $scope.currOrgData=this.currOrgData;
		    $scope.changeData=this.changeData;
		},
		params:{
			page:1,
			rows: 50
		},
		query:{
			vehicleState:"-1"
		},
		currOrgData:{
			
		},
		userData:function(){
			$scope.currOrgId=userInfo.orgId;
			$scope.currOrgName=userInfo.orgName;
			var url="orderInfoBO.ajax?cmd=queryOrgType";
			commonService.postUrl(url,"orgId="+$scope.currOrgId,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.orgInfoData=data;
				    $scope.orgType=data.orgType;
				    $scope.isShow=true;
					/*if(data.orgType==2){
						$scope.isShow=false;
					}*/
					
					 $scope.selectCurrOrgId($scope.orgType);
					    $scope.desOrgData($scope.orgType);
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
			var excelLables = "发车批次,配载时间,发车时间,车辆状态,总票数,总重量,总体积,发车网点,目的网点,车牌号,司机姓名,司机手机";
			var excelKeys = "batchNum,loadTimeStr*,departTimeStr,stateName,orderNum@,weight@,volume@,sourceOrgIdName,descOrgIdName,plateNumber,driverName,driverBill";
			var params = $scope.paramsExport;
			var queryUrl = "orderInfoBO.ajax?cmd=queryMatchInfoOther";
			commonService.downloadExcelFile(queryUrl,eval("("+params+")"),excelLables,excelKeys);
			 $scope.isTrue = true;
			$("#exportId").html("正在导出...");
			//导出倒计时
			$timeout(function() {
				 $scope.isTrue = false;
				$("#exportId").html("导出所有");
			},3600);
			
		},
		/**列表查询*/
		doQuery : function(){
			
			$timeout(function(){
					$scope.businessType = getQueryString("type");
					if($scope.businessType != undefined && $scope.businessType != null && $scope.businessType != ''){
						departManage.params.businessType = $scope.businessType;
					}else{
						departManage.params.businessType = 1;
						$scope.businessType = 1;
					}
					
					departManage.params.vehicleState=departManage.query.vehicleState;
					departManage.params.startOrgId=departManage.query.currOrgId;
					departManage.params.descOrgId=departManage.query.descOrgId;
					departManage.params.batchNum=departManage.query.batchNum;
					departManage.params.beginTime=document.getElementById("beginTime").value;
					departManage.params.endTime=document.getElementById("endTime").value;
					var url = "orderInfoBO.ajax?cmd=queryMatchInfoOther";
					departManage.params.page=1;
					$scope.tableCallBack=function(){
//						if ($scope.page.data.items == undefined || $scope.page.data.items.length == 0) {// 没有数据，指定一个默认高度
//							setContentHeightWithSpecialHeight(700);
//						} else {// 有数据，则根据数据行计算高度
//							var height = (700 - 200) + $scope.page.data.items.length * 48;
//							height = height <= 700 ? 700 : height;
//							setContentHeightWithSpecialHeight(height+40);
//						}
						$scope.paramsExport = JSON.stringify(departManage.params)
						commonService.refreshPageContentHeight($scope.page.data.items.length, 663, 270);
						 $scope.paramsExport = JSON.stringify(departManage.params);
					}
					$timeout(function(){
						$scope.page.load({
									url:url,
									params:departManage.params,
									callBack:"$scope.tableCallBack"
								});
					},500);
			},200)
			
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

		/**查看**/
		toView:function(){
			var batchNum=$scope.page.getOneSelected();;
			if(batchNum == "" || batchNum == null || batchNum == undefined){
				commonService.alert("请至少选择一条送货信息!");
				return false;
			}
			if($scope.businessType != 2){
				commonService.openTab(batchNum,"送货详情","/ord/depearInfoOther.html?batchNum="+batchNum);
			}else{
				commonService.openTab(batchNum,"中转送货详情","/ord/depearInfoOther.html?batchNum="+batchNum);
			}
		},
		/***保存**/
		doSave:function(){
			if($scope.businessType != 2){
				commonService.openTab("11111","新增送货信息","/ord/addDepartOther.html");
			}else{
				commonService.openTab("111111","新增中转送货信息","/ord/addDepartOtherTransit.html?type=2");
			}
			
		},
		/***修改**/
		doModify:function(){
			var batchNum=$scope.page.getOneSelected();;
			if(batchNum == "" || batchNum == null || batchNum == undefined){
				commonService.alert("请至少选择一条送货信息!");
				return false;
			}
			var flag=false;
			var selectData=$scope.page.getSelectData()[0];
//			if(selectData.state!=1){
//				flag=true;
//				commonService.alert("批次["+selectData.batchNum+"]状态为"+selectData.stateName+",不可以操作[修改]!");
//				return false;
//			}
			if($scope.businessType != 2){
				commonService.openTab(batchNum+"2","修改送货信息","/ord/addDepartOther.html?batchNum="+batchNum);
			}else{
				commonService.openTab(batchNum+"2","修改中转送货信息","/ord/addDepartOtherTransit.html?batchNum="+batchNum);
			}
			
		},
		/**打印*/
		print:function(){
		},
		/**删除*/
		deleteDepart:function(){
			var batchNum = $scope.page.getOneSelected();;
			if(batchNum == "" || batchNum == null || batchNum == undefined){
				commonService.alert("请至少选择一条送货信息!");
				return false;
			}
			var param = {"batchNum":batchNum};
			
			var url = "orderInfoBO.ajax?cmd=deleteDepartInfoOther";
			if($scope.businessType == 2 || $scope.businessType == "2"){
				url = "orderInfoBO.ajax?cmd=deleteDepartInfoTransitOther";
			}
			commonService.confirm("是否删除送货信息?",function(){
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
		 * 发车网点修改
		 * @param starOrgId
		 */
		  changeData:function(starOrgId){
			/*if(starOrgId!=userInfo.orgId){
			   $scope.isShow=false;
			}  else{
				$scope.isShow=true;
			}*/
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
		
		
		
		/**发车确认*/
		confirmMatchVehi:function(){
			var batchNum=$scope.page.getOneSelected();;
			if(batchNum==""){
				commonService.alert("请至少选择一条送货信息!");
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
		}
	};
	
	departManage.init();
}]);


