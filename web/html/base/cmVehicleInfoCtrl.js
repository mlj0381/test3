function changeTabCallback(){
	var appElement = document.querySelector('[ng-controller=cmDriverInfoCtrl]');
	var scope=angular.element(appElement).scope();
	scope.doQuery();
	scope.$apply();
}
var cmDriverInfoApp = angular.module("cmDriverInfoApp", ['commonApp','tableCommon']);
cmDriverInfoApp.controller("cmDriverInfoCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var ord ={
		init:function(){
			this.bindEvent();
			this.selectOrg();
			 
		},
		head:[
		      	{name:"车牌号","code":"plateNumber","width":"100","type":"text"},
		      	{name:"车辆类型","code":"vehicleTypeName","width":"100", "type":"select","selectSource":"VEHICLE_TYPE"},
		      	{name:"核载体积","code":"actualVolume","width":"100","type":"text"},
		      	{name:"核载重量","code":"actualWeight","width":"100","type":"text"},
		      	{name:"发动机号","code":"engineNo","width":"100","type":"text"},
		      	{name:"车架号","code":"frameNo","width":"100","type":"text"},
		      	{name:"车厢长","code":"length","width":"100"},
		      	{name:"车厢宽","code":"wide","width":"100"},
		      	{name:"车厢高","code":"high","width":"100"},
		      	{name:"品牌型号","code":"systemType","width":"100","type":"text"},
		      	{name:"归属网点","code":"orgName","width":"100"},
		      	{name:"创建时间","code":"createDate","width":"100"},
		      	{name:"创建人","code":"creatorName","width":"100"}
		      ],
		bindEvent:function(){
			$scope.head=ord.head;
			$scope.url="vehicleInfoBO.ajax?cmd=doQueryVehicle";
			$scope.toSave = this.toSave;
			$scope.doQuery = this.doQuery;
			$scope.selecBusinessType = this.selecBusinessType;
			$scope.close = this.close;
			$scope.data = this.data;
			$scope.selectOrg = this.selectOrg;
			$scope.doDel = this.doDel;
			$scope.params = this.params;
			$scope.toUpdate = this.toUpdate;
			$scope.clear = this.clear;
			$scope.toView=this.toView;
			 
		},
		userData:function(){
			$scope.currOrgId=userInfo.orgId;
			$scope.currOrgName=userInfo.orgName;
		},
		data:{
			//orgId:-1
		},
		params:{},
		//查询事件
		doQuery:function(){
			var url = "vehicleInfoBO.ajax?cmd=doQueryVehicle";
			$scope.data.page=1;
			$scope.table.load();
			$scope.table.callBack=function(){
				displayTable();
				setContentHeight();
			} 
			
		},
		clear:function(){
			$scope.data.plateNumber = "";
			$scope.data.vehicleType = "-1";
			$scope.data.vehicleState = "-1";
			$scope.data.businessType = "-1";
			$scope.data.orgId = -1;
			$scope.table.clearInput();
		},
		//查询所有网点
		selectOrg:function(){
			commonService.postUrl("organizationBO.ajax?cmd=getAllOrg","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.orgInfo = data;
					//$scope.orgInfo.items.unshift({orgId:-1,orgName:"全部"});
				}
			});
		},
		toSave:function(){
			commonService.openTab("11223","车辆新增","/cm/cmVehicleInfoAdd.html");
			//location.href="cmVehicleInfoAdd.html";
		},
		doDel:function(){
			var vehicleIds=""
				var array=$scope.table.getSelectData();
				if(array.length==0){
					commonService.alert("请选择数据");
					return;
				}
				if(array!=null&&array!=""){
					for(var i=0;i<array.length;i++){
						 var data=array[i];
						 vehicleIds+= data.vehicleId+",";
					}
				}
				var param={"vehicleIds":vehicleIds};
				commonService.confirm("确认删除车辆信息?",function(){
			         commonService.postUrl("vehicleInfoBO.ajax?cmd=doDelVehicle",param,function(data){
							if(data != null && data != undefined && data != ""){
								commonService.alert("操作完成");
								$scope.doQuery();
							}
						});
		         });
		},
		toView:function(vehicleId){
			var array=$scope.table.getSelectData();
			if(array.length==0){
				commonService.alert("请选择数据");
				return;
			}
			if(array.length>1){
				 commonService.alert("只能选择的一个修改");
					return;
			}
			var vehicleId="";
			var data=array[0];
			vehicleId=data.vehicleId;
			commonService.openTab(vehicleId+"1","车辆详情","/cm/cmVehicleInfoDetail.html?vehicleId="+vehicleId);
		},
		toUpdate:function(vehicleId){
			var array=$scope.table.getSelectData();
			if(array.length==0){
				commonService.alert("请选择数据");
				return;
			}
			if(array.length>1){
				 commonService.alert("只能选择的一个修改");
					return;
			}
			var vehicleId="";
			var data=array[0];
			vehicleId=data.vehicleId;
			commonService.openTab(vehicleId+"1","车辆修改","/cm/cmVehicleInfoAdd.html?vehicleId="+vehicleId);
		}
	};
	ord.init();
}]);