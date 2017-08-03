var arriveGoodManageApp = angular.module("arriveGoodManageApp", ['commonApp','tableCommon']);
arriveGoodManageApp.controller("arriveGoodManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.currOrgData = [];
	$scope.startOrgData=[];
	$scope.query={};
	$scope.vehicleStateList=[];
	$scope.params={};
	var arriveGoodManage={
		init:function(){
			this.bindEvent();
			this.desOrgData();
			this.selectCurrOrgId();
			commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{"codeType":"VEHICLE_STATE"},function(data){
				$scope.vehicleStateList=data.items;
				$scope.query.vehicleState=2;
			});
		},
		head:[
		     {
		    	 "name":"发车批次",
			     "code":"batchNumAlias",
			     "width":"120",
			     "type":"text"
		     },
		     {

		    	"name":"运输合同",
			     "code":"transportContract",
			     "width":"130"
		     },
		     {

		    	 "name":"配载时间",
			     "code":"loadTime",
			     "width":"120"
		     },
		     {

		    	 "name":"发车时间",
			     "code":"departTime",
			     "width":"120"
		     },
		     {

		    	 "name":"车辆状态",
			     "code":"stateName",
			     "width":"100", 
			     "type":"select",
		         "selectSource":"VEHICLE_STATE"
		     },
		     {
		    	 "name":"总票数",
			     "code":"orderNum",
			     "width":"70", 
			     "isSum": "true"
		     }, 
		     {
		    	 "name":"总体积",
			     "code":"volume",
			     "width":"100", 
			     "isSum":"true",
			     "number": "2"
		     }, 
		     {
		    	 "name":"总重量",
			     "code":"weight",
			     "width":"100",
			     "isSum": "true",
			     "number": "2"
		     },
		     {
		    	 "name":"发车网点",
			     "code":"sourceOrgIdName",
			     "width":"120"
		     },
		     {
		    	 "name":"目的网点",
			     "code":"descOrgIdName",
			     "width":"120"
		     },
		     {
		    	 "name":"车牌号",
			     "code":"plateNumber",
			     "width":"110",
			     "type":"text"
		     },
		     {
		    	 "name":"司机姓名",
			     "code":"driverName",
			     "width":"110"
		     },
		     {
		    	 "name":"司机手机",
			     "code":"driverBill",
			     "width":"110"
		     },
		      ],
		bindEvent:function(){
			$scope.head=arriveGoodManage.head;
			$scope.url="orderInfoBO.ajax?cmd=queryMatchInfo";
			$scope.urlParam=arriveGoodManage.query;
			$scope.doQuery=this.doQuery;
		    $scope.print=this.print;
		    $scope.toView=this.toView;
		    $scope.clear=this.clear;
		    $scope.confirmGoodVehi=this.confirmGoodVehi;
		    $scope.arriveVehConf=this.arriveVehConf;
		    $scope.isTrue = false;
		    $scope.commonExport = this.commonExport;
		    $scope.paramsExport = "{}";
		},
		//导出方法
		commonExport : function(){
			$scope.isTrue = true;
			$("#exportId").html("数据加载中...");
			$scope.table.downloadExcelFile();
			//导出倒计时
			$timeout(function() {
				 $scope.isTrue = false;
				$("#exportId").html("导出");
			},3600);
			
		},
		/**
		 * 查询目的网点
		 */
		selectCurrOrgId:function(){
			$scope.currOrgId=userInfo.orgId;
			$scope.currOrgName=userInfo.orgName;
			var obj = new Object();
			obj.beginOrgId = $scope.currOrgId;
			obj.beginOrgName = $scope.currOrgName;
			$scope.currOrgData.push(obj);
			$scope.query.descOrgId= $scope.currOrgId;
		},
		/**
		 * 查询发车网点
		 */
		desOrgData:function(){
			var param = {"endOrgId":userInfo.orgId};
			var url = "routeBO.ajax?cmd=getTowards";	
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined && data!=""){
				    $scope.startOrgData=data.items;
	 	    	}
			});
		},
		/**列表查询*/
		doQuery:function(){
			$scope.query.page=1;
			$scope.query.flag=1;
			$scope.query.flagSts=1;
			$scope.table.load();
			$scope.table.callBack=function(){
				displayTable();
				setContentHeight();
			}
		},
		/**清空查询条件*/
		clear:function(){
			document.getElementById("beginTime").value='';
			document.getElementById("endTime").value='';
			$scope.query.beginTime="";
			$scope.query.endTime="";
			$scope.query.batchNumAlias="";
			$scope.query.vehicleState="2";
			$scope.query.startOrgId="-1";
			$scope.table.clearInput();
		},
		/**查看**/
		toView:function(){
			var batchNum='';
			var selectDatas =$scope.table.getSelectData();
			if(selectDatas.length == 0){
				commonService.alert("请选择一条配载信息!");
				return false;
			}
			if(selectDatas.length > 1){
				commonService.alert("只能选择一条配载信息!");
				return false;
			}
			batchNum = selectDatas[0].batchNum;
			var data =  selectDatas[0];
			
			commonService.openTab(batchNum,"配载详情","/ord/depearInfo.html?batchNum="+batchNum);
				},
		/**打印*/
		print:function(){
		},
		/**到货确认*/
		confirmGoodVehi:function(){
			var batchNum='';
			var selectDatas =$scope.table.getSelectData();
			if(selectDatas.length == 0){
				commonService.alert("请选择一条配载信息!");
				return false;
			}
			if(selectDatas.length > 1){
				commonService.alert("只能选择一条配载信息!");
				return false;
			}
			
			batchNum = selectDatas[0].batchNum;
			var data =  selectDatas[0];
			var flag=false;
			if(data.state!=3&&data.state!=5){
				 flag=true;
				 commonService.alert("批次["+data.batchNumAlias+"]状态为"+data.stateName+",不可以操作[到货确认]!");
				 return false;
			}
			if(flag){
				return false;
			}
			commonService.openTab(batchNum+"22","到货确认","/ord/depearInfo.html?batchNum="+batchNum+"&flag=1");
		},
		/***到车确认**/
		arriveVehConf:function(){
			var batchNum='';
			var selectDatas =$scope.table.getSelectData();
			if(selectDatas.length == 0){
				commonService.alert("请选择一条配载信息!");
				return false;
			}
			if(selectDatas.length > 1){
				commonService.alert("只能选择一条配载信息!");
				return false;
			}
			
			batchNum = selectDatas[0].batchNum;
			var data =  selectDatas[0];
			var flag=false;
			if(data.state!=2){
			   commonService.alert("批次["+data.batchNumAlias+"]状态为"+data.stateName+",不可以操作[到车确认]!");
			   flag=true;
			   return false;
		    }
			if(flag){
				return false;
			}
			commonService.confirm("批次号["+data.batchNumAlias+"]是否进行[到车确认]!",function(){
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
	$scope.$watch('$viewContentLoaded', function() {
		arriveGoodManage.init();
	 });
}]);
