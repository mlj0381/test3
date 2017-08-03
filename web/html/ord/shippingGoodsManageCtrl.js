//function changeTabCallback(){
//	var appElement = document.querySelector('[ng-controller=shippingGoodsManageCtrl]');
//	var scope=angular.element(appElement).scope();
//	scope.doQuery();
//	scope.$apply();
//}
var shippingGoodsManageApp = angular.module("shippingGoodsManageApp", ['commonApp','tableCommon']);
shippingGoodsManageApp.controller("shippingGoodsManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	 $scope.currOrgData = [];
	 $scope.query={};
	 $scope.vehicleStateList=[];
	 $scope.params={};
	 var shippingGoodsManage={
		init:function(){
			this.bindEvent();
			this.desOrgData();
			this.selectCurrOrgId();
			commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{"codeType":"SHIP_STATE"},function(data){
					$scope.vehicleStateList=data.items;
			});
		},
	head:[
	      {
    "name": "批次号",
    "code": "batchNumAlias",
    "width": "120"
	},
	{
	    "name": "运输合同",
	    "code": "transportContract",
	    "width": "130"
	},
	{
	    "name": "船名",
	    "code": "shipName",
	    "width": "130"
	},
	{
	    "name": "柜号",
	    "code": "cntrNo",
	    "width": "130"
	},
	{
	    "name": "封条号",
	    "code": "sealNo",
	    "width": "130"
	},
	{
	    "name": "运载工具号",
	    "code": "carrierNo",
	    "width": "130"
	},
	{
	    "name": "收箱地",
	    "code": "receivingAddress",
	    "width": "150"
	},
	{
	    "name": "发车网点",
	    "code": "sourceOrgIdName",
	    "width": "120"
	},
	{
	    "name": "目的网点",
	    "code": "descOrgIdName",
	    "width": "120"
	},
	{
	    "name": "配载人",
	    "code": "loadOpName",
	    "width": "120"
	},
	{
	    "name": "配载时间",
	    "code": "loadTime",
	    "width": "120"
	},
	{
	    "name": "开船时间",
	    "code": "shipDateStr",
	    "width": "120"
	},
	{
	    "name": "到达时间",
	    "code": "arrivalVehTime",
	    "width": "120"
	},
	{
	    "name": "车辆状态",
	    "code": "stateName",
	    "width": "100"
	},
	{
	    "name": "总票数",
	    "code": "orderNum",
	    "width": "70"
	},
	{
	    "name": "总体积",
	    "code": "volume",
	    "width": "100"
	},
	{
	    "name": "总重量",
	    "code": "weight",
	    "width": "100"
	},
	{
	    "name": "运费",
	    "code": "freightStr",
	    "width": "100"
	},
	{
	    "name": "装卸费",
	    "code": "stevedoringStr",
	    "width": "100"
	},
	{
	    "name": "到付",
	    "code": "freightCollectDouble",
	    "width": "100"
	},
	{
	    "name": "代收货款",
	    "code": "collectingMoneyDouble",
	    "width": "100"
	},
	{
	    "name": "备注",
	    "code": "remarks",
	    "width": "150"
	}
	  ],
	bindEvent:function(){
		$scope.head=shippingGoodsManage.head;
		$scope.url="ordShippingInfoBO.ajax?cmd=queryShipInfo";
		$scope.urlParam=$scope.query;
			$scope.param=this.param;
			$scope.doQuery=this.doQuery;
		    $scope.deleteDepart=this.deleteDepart;
		    $scope.print=this.print;
		    $scope.toView=this.toView;
		    $scope.clear=this.clear;
		    $scope.commonExport=this.commonExport;
		    $scope.isTrue = false;
		    $scope.paramsExport = "{}";
		    $scope.confirmGoodVehi=this.confirmGoodVehi;
		    $scope.arriveVehConf=this.arriveVehConf;
		},
		//导出方法
		commonExport : function(){
				if($scope.isTrue){
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
			$scope.urlParam=$scope.query;
			$scope.query.page=1;
			$scope.table.load($scope.urlParam);
			$scope.table.callBack=function(){
				displayTable();
				setContentHeight();
			}

		},
		/**清空查询条件*/
		clear:function(){
			$scope.query={};
			$scope.query.beginTime="";
			$scope.query.departBeginTime="";
			$scope.query.departEndTime="";
			$scope.query.endTime="";
			$scope.query.shipDateBegin="";
			$scope.query.shipDateEnd="";
			$scope.query.batchNumAlias="";
			$scope.query.startOrgId=-1;
			$scope.query.vehicleState="-1";
		},
		/**查看**/
		toView:function(){
			var batchNum='';
			var selectDatas =  $scope.table.getSelectData();
			if(selectDatas.length == 0){
				commonService.alert("请选择一条配载信息!");
				return false;
			}
			if(selectDatas.length > 1){
				commonService.alert("只能选择一条配载信息!");
				return false;
			}
			batchNum = selectDatas[0].batchNum;
			commonService.openTab(batchNum,"配载详情","/ord/shipDepeatInfo.html?batchNum="+batchNum);
		},
		/**到货确认*/
		confirmGoodVehi:function(){
			var batchNum='';
			var selectDatas =  $scope.table.getSelectData();
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
			commonService.openTab(batchNum+"22","到货确认","/ord/shipDepeatInfo.html?batchNum="+batchNum+"&flag=1");
		},
		/***到车确认**/
		arriveVehConf:function(){
			var batchNum='';
			var selectDatas =  $scope.table.getSelectData();
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
			   commonService.alert("批次["+data.batchNumAlias+"]状态为"+data.stateName+",不可以操作[到港确认]!");
			   flag=true;
			   return false;
		    }
			if(flag){
				return false;
			}
			commonService.confirm("批次号["+data.batchNumAlias+"]是否进行[到港确认]!",function(){
				var param = {"batchNum":batchNum};
				var url = "ordShippingInfoBO.ajax?cmd=ordArriveVehicle";
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
		 shippingGoodsManage.init();
	 });
	
}]);
