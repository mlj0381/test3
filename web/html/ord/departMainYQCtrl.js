var departManageApp = angular.module("departManageYQApp", ['commonApp','tableCommon']);
departManageApp.controller("departManageYQCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	 $scope.currOrgData = [];
	 $scope.query={};
	 $scope.vehicleStateList=[];
	 $scope.params={};
	 var departManage={
		init:function(){
			this.bindEvent();
			this.selectCurrOrgId();
			commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{"codeType":"VEHICLE_STATE_YQ"},function(data){
				$scope.vehicleStateList=data.items;
			});
			this.desOrgData();
		},
		head:[
		     {"name": "批次号","code": "batchNumAlias","width": "120","type":"text"},
		     {"name": "状态","code": "state","width": "100","type":"select","selectSource":"VEHICLE_STATE_YQ"},
		     {"name": "配载人","code": "loadOpName","width": "110","type":"text"},
		     {"name": "配载时间","code": "loadTime","width": "120"},
		     {"name": "发车时间","code": "departTime","width": "120"},
		     {"name": "车牌号","code": "plateNumber","width": "110","type":"text"},
		     {"name": "司机","code": "driverName","width": "110"},
		     {"name": "司机手机","code": "driverBill","width": "110"},
		     {"name": "物流公司","code": "tenantName","width": "110"},
		     {"name": "发车网点","code": "sourceOrgName","width": "120"},
		     {"name": "目的网点","code": "descOrgName","width": "120"},
		     {"name": "总票数","code": "orderNum","isSum": "true","width": "110"},
		     {"name": "总件数","code": "number","isSum": "true","width": "110"},
		     {"name": "总体积","code": "volume","isSum": "true","number": "3","width": "120"},
		     {"name": "总重量","code": "weight","isSum": "true","number": "3","width": "120"},
		     {"name": "开单运费总额","code": "freight","isSum": "true","number": "2","width": "120"},
		     {"name": "开单到付总额","code": "totalFee","isSum": "true","number": "2","width": "120"},
		     {"name": "代收货款总额","code": "collectMoney","isSum": "true","number": "2","width": "120"},
		     {"name": "备注","code":"remarks","width": "120"}
		      ],
		bindEvent:function(){
			$scope.head=departManage.head;
			$scope.url="ordDepartInfoYqBO.ajax?cmd=doQuery";
			$scope.urlParam=departManage.query;
			$scope.doQuery=this.doQuery;
		    $scope.toView=this.toView;
		    $scope.paramsExport = "{}";
		   $scope.clear = this.clear;
		},
		/**
		 * 查询目的网点
		 */
		selectCurrOrgId:function(){
			/**网点列表查询*/
			var url = "staticDataBO.ajax?cmd=getOrgan";
			commonService.postUrl(url,"",function(data){
				if(data!=null && data!=undefined && data!=""){
					if(data!=null && data!=undefined && data.items!=null && data.items!=undefined && data.items.length>0){
						$scope.desOrgData = data.items;
						$scope.desOrgData.unshift({endOrgId:-1,endOrgName:"全部"});
						$scope.query.descOrgId = -1;
					}
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
			$scope.query.page=1;
			$scope.query.flag=1;
			$scope.query.flagSts=1;
			$scope.query.loadTimeBegin =  document.getElementById("loadTimeBegin").value;
			$scope.query.loadTimeEnd = document.getElementById("loadTimeEnd").value;
			var url = "ordDepartInfoYqBO.ajax?cmd=doQuery";
			$scope.tableCallBack=function(){
				$scope.paramsExport = JSON.stringify(departManage.query)
			}
			$scope.table.load();
			$scope.table.callBack=function(){
				displayTable();
				setContentHeight();
			}
		},
		/**清空查询条件*/
		clear:function(){
			document.getElementById("loadTimeBegin").value='';
			document.getElementById("loadTimeEnd").value='';
			$scope.query.loadTimeBegin="";
			$scope.query.loadTimeEnd="";
			$scope.query.batchNumAlias="";
			$scope.query.state="-1";
			$scope.query.descOrgId=-1;
			$scope.query.loadOpName="";
			$scope.query.driverName="";
			$scope.query.driverBill="";
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
			commonService.openTab(batchNum,"配载详情","/ord/depearInfoYQ.html?batchNum="+batchNum);
		}
	};
	 $scope.$watch('$viewContentLoaded', function() {
		 departManage.init();
	 });
	
}]);
