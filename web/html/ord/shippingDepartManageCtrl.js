//function changeTabCallback(){
//	var appElement = document.querySelector('[ng-controller=shippingDepartManageCtrl]');
//	var scope=angular.element(appElement).scope();
//	scope.doQuery();
//	scope.$apply();
//}
var shippingDepartManageApp = angular.module("shippingDepartManageApp", ['commonApp','tableCommon']);
shippingDepartManageApp.controller("shippingDepartManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	 $scope.currOrgData = [];
	 $scope.query={};
	 $scope.vehicleStateList=[];
	 $scope.params={};
	 var shippingDepartManage={
		init:function(){
			this.bindEvent();
			this.selectCurrOrgId();
			commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{"codeType":"SHIP_STATE"},function(data){
				$scope.vehicleStateList=data.items;
			});
			this.desOrgData();
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
		$scope.head=shippingDepartManage.head;
		$scope.url="ordShippingInfoBO.ajax?cmd=queryShipInfo";
		$scope.urlParam=$scope.query;
			$scope.param=this.param;
			$scope.doQuery=this.doQuery;
		    $scope.deleteDepart=this.deleteDepart;
		    $scope.print=this.print;
		    $scope.toView=this.toView;
		    $scope.clear=this.clear;
		    $scope.selectAll=this.selectAll;
		    $scope.doSave=this.doSave;
		    $scope.confirmMatchVehi=this.confirmMatchVehi;
		    $scope.doModify=this.doModify;
		    $scope.selectOne=this.selectOne;
		    $scope.cancelMatchVehi=this.cancelMatchVehi;
		    $scope.commonExport=this.commonExport;
		    $scope.isTrue = false;
		    $scope.paramsExport = "{}";
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
			commonService.postUrl("routeBO.ajax?cmd=getCurrRoute","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.desOrgData = data.items;
					$scope.desOrgData.unshift({endOrgId:-1,endOrgName:"全部"});
					$scope.query.descOrgId = -1;
				}
			});
		},
		/**
		 * 查询发车网点
		 */
		desOrgData:function(orgType){
			$scope.currOrgId=userInfo.orgId;
			$scope.currOrgName=userInfo.orgName;
			var obj = new Object();
			obj.beginOrgId = $scope.currOrgId;
			obj.beginOrgName = $scope.currOrgName;
			$scope.currOrgData.push(obj);
			$scope.query.startOrgId = $scope.currOrgId;
			//$scope.doQuery();
		},
		/**列表查询*/
		doQuery:function(){
			$scope.urlParam=$scope.query;
			$scope.query.page=1;
			$scope.query.isGo=1;
			$scope.table.load();
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
			$scope.query.descOrgId=-1;
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
		/***保存**/
		doSave:function(){
			commonService.openTab("31231222","新增船运配载信息","/ord/addShipDepart.html");
		},
		/***修改**/
		doModify:function(){
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
			var batchNumAlias = selectDatas[0].batchNumAlias;
			var flag=false;
			var f=true;
			var err="";
			var state = selectDatas[0].state;
			var stateName = selectDatas[0].stateName;
			if(state == 2 || state == 3){
				err="批次["+batchNumAlias+"]状态为"+stateName+",是否确认修改？";
				f=false;
			}
			if(state != 1 && state != 2 && state != 3){
				flag=true;
				commonService.alert("批次["+batchNumAlias+"]状态为"+stateName+",不可以操作[修改]!");
				return false;
			}
			
    		if(!f){
				commonService.confirm(err,function(){
					commonService.openTab(batchNum,"修改配载信息","/ord/addShipDepart.html?batchNum="+batchNum);
				});
			}else{
				commonService.openTab(batchNum,"修改配载信息","/ord/addShipDepart.html?batchNum="+batchNum);
			}
		},
		/**打印*/
		print:function(){
		},
		/**删除*/
		deleteDepart:function(){
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
			var state = selectDatas[0].state;
			var stateName = selectDatas[0].stateName;
			var batchNumAlias = selectDatas[0].batchNumAlias;
			var flag=false;
			if(state != 1){
				flag=true;
				commonService.alert("批次["+batchNumAlias+"]状态为"+stateName+",不可以操作!");
				return false;
			}
			if(flag){
				return false;
			}
			var param = {"batchNum":batchNum};
			var url = "ordShippingInfoBO.ajax?cmd=doDel";
			commonService.confirm("是否删除配载?",function(){
				commonService.postUrl(url,param,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
							commonService.alert("删除成功!");
							$scope.doQuery();
		 	    	}
				});
			});
		},
		/**发车确认*/
		confirmMatchVehi:function(){
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
			var batchNumAlias = selectDatas[0].batchNumAlias;
			if(data.state!=1){
			     flag=true;
			     commonService.alert("批次["+batchNumAlias+"]状态为"+data.stateName+",不可以操作[发船确认]!");
			     return false;
		    }
			if(flag){
				return false;
			}
			var param = {"batchNum":batchNum};
			var url = "ordShippingInfoBO.ajax?cmd=confirmMatchVehi";
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
		cancelMatchVehi:function(){
			var batchNum = "";
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
			var batchNumAlias = selectDatas[0].batchNumAlias;
			if(data.state != 2){
				flag=true;
				commonService.alert("批次["+batchNumAlias+"]状态为"+data.stateName+",不可以操作[取消发船]!");
				return false;
			}
			
			if(flag){
				return false;
			}
			var param = {"batchNum":batchNum};
			var url = "ordShippingInfoBO.ajax?cmd=cancerShipGO";
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
	 $scope.$watch('$viewContentLoaded', function() {
		 shippingDepartManage.init();
	 });
	
}]);
