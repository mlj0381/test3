function changeTabCallback(){
	var appElement = document.querySelector('[ng-controller=departManageCtrl]');
	var scope=angular.element(appElement).scope();
	scope.doQuery();
	scope.$apply();
}
var departManageApp = angular.module("departManageApp", ['commonApp','tableCommon']);
departManageApp.controller("departManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	 $scope.currOrgData = [];
	 $scope.query={};
	 $scope.vehicleStateList=[];
	 $scope.params={};
	 var departManage={
		init:function(){
			this.bindEvent();
			this.selectCurrOrgId();
			commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{"codeType":"VEHICLE_STATE"},function(data){
				$scope.vehicleStateList=data.items;
			});
			this.desOrgData();
		},
		head:[
		      {
		          "name": "发车批次",
		          "code": "batchNumAlias",
		          "width": "120",
		          "type":"text"
		     },
		     {
		          "name": "运输合同",
		          "code": "transportContract",
		          "width": "130"
		     },
		     {
		          "name": "配载人",
		          "code": "loadOpName",
		          "width": "110",
		          "type":"text"
		     },
		     {
		          "name": "配载时间",
		          "code": "loadTime",
		          "width": "120"
		     },
		     {
		          "name": "发车时间",
		          "code": "departTime",
		          "width": "120"
		     },
		     {
		          "name": "车辆状态",
		          "code": "stateName",
		          "width": "100",
		          "type":"select",
		          "selectSource":"VEHICLE_STATE"
		     },
		     {
		          "name": "运费",
		          "code": "freightDb",
		          "width": "100",
		          "isSum": "true",
			      "number": "2"
		     },
		     {
		          "name": "装卸费",
		          "code": "stevedoringDb",
		          "width": "110",
		          "isSum": "true",
			      "number": "2"
		     },
		     {
		          "name": "总票数",
		          "code": "orderNum",
		          "isSum": "true",
		          "width": "110"
		     },
		     {
		          "name": "总体积",
		          "code": "volume",
		          "isSum": "true",
		          "number": "2",
		          "width": "120"
		     },
		     {
		          "name": "总重量",
		          "code": "weight",
		          "isSum": "true",
		          "number": "2",
		          "width": "120"
		     },
		     {
		          "name": "目的网点",
		          "code": "descOrgIdName",
		          "width": "120"
		     },
		     {
		          "name": "车牌号",
		          "code": "plateNumber",
		          "width": "110",
		          "type":"text"
		     },
		     {
		          "name": "司机姓名",
		          "code": "driverName",
		          "width": "110"
		     },
		     {
		          "name": "司机手机",
		          "code": "driverBill",
		          "width": "110"
		     }
		      ],
		bindEvent:function(){
			$scope.head=departManage.head;
			$scope.url="orderInfoBO.ajax?cmd=queryMatchInfo";
			$scope.urlParam=departManage.query;
			$scope.param=this.param;
			$scope.doQuery=this.doQuery;
		    $scope.deleteDepart=this.deleteDepart;
		    $scope.print=this.print;
		    $scope.toView=this.toView;
		    $scope.clear=this.clear;
		    $scope.doSave=this.doSave;
		    $scope.confirmMatchVehi=this.confirmMatchVehi;
		    $scope.doModify=this.doModify;
		    $scope.cancelMatchVehi=this.cancelMatchVehi;
		    $scope.commonExport=this.commonExport;
		    $scope.isTrue = false;
		    $scope.paramsExport = "{}";
		    //导出
		    $scope.commonExport = this.commonExport;
		},
		commonExport : function(){
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
			$scope.query.page=1;
			$scope.query.flag=1;
			$scope.query.flagSts=1;
			$scope.query.beginTime =  document.getElementById("beginTime").value;
			$scope.query.endTime = document.getElementById("endTime").value;
			var url = "orderInfoBO.ajax?cmd=queryMatchInfo";
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
			document.getElementById("beginTime").value='';
			document.getElementById("endTime").value='';
			$scope.query.beginTime="";
			$scope.query.endTime="";
			$scope.query.batchNumAlias="";
			$scope.query.vehicleState="-1";
			$scope.query.descOrgId=-1;
			$scope.query.loadOpName="";
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
			commonService.openTab(batchNum,"配载详情","/ord/depearInfo.html?batchNum="+batchNum);
				},
		/***保存**/
		doSave:function(){
			commonService.openTab("11111","新增配载信息","/ord/addDepart.html");
		},
		/***修改**/
		doModify:function(){
			var batchNum='';
			//var selectDatas =  $scope.page.getSelectData();
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
					commonService.openTab(batchNum,"修改配载信息","/ord/addDepart.html?batchNum="+batchNum);
				});
			}else{
				commonService.openTab(batchNum,"修改配载信息","/ord/addDepart.html?batchNum="+batchNum);
			}
		},
		/**打印*/
		print:function(){
		},
		/**删除*/
		deleteDepart:function(){
			var batchNum='';
			//var selectDatas =  $scope.page.getSelectData();
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
			var url = "orderInfoBO.ajax?cmd=deleteDepartInfo";
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
			
			//var selectDatas =  $scope.page.getSelectData();
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
			var batchNumAlias = selectDatas[0].batchNumAlias;
			if(data.state!=1){
			     flag=true;
			     commonService.alert("批次["+batchNumAlias+"]状态为"+data.stateName+",不可以操作[发车确认]!");
			     return false;
		    }
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
		cancelMatchVehi:function(){
			var batchNum = "";
			//var selectDatas =  $scope.page.getSelectData();
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
			var batchNumAlias = selectDatas[0].batchNumAlias;
			if(data.state != 2){
				flag=true;
				commonService.alert("批次["+batchNumAlias+"]状态为"+data.stateName+",不可以操作[取消发车]!");
				return false;
			}
			
			if(flag){
				return false;
			}
			var param = {"batchNum":batchNum};
			var url = "orderInfoBO.ajax?cmd=cancelMatchVehi";
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
		 departManage.init();
	 });
	
}]);
