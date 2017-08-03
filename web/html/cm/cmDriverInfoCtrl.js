var cmDriverInfoApp = angular.module("cmDriverInfoApp", ['commonApp','tableCommon']);
cmDriverInfoApp.controller("cmDriverInfoCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var ord ={
		init:function(){
			this.bindEvent();
			this.selectOrg();
		},
		head:[
              {name:"司机姓名","code":"name","width":"100","type":"text"},
              {name:"司机手机","code":"bill","width":"100","type":"text"},
              {name:"联系电话","code":"telephone","width":"100","type":"text"},
              {name:"身份证号","code":"identityCardNo","width":"100","type":"text"},
              {name:"银行名称","code":"bankName","width":"100","type":"text"},
              {name:"银行账号","code":"bankAccount","width":"100","type":"text"},
              {name:"归属网点","code":"orgName","width":"100"},
              {name:"创建时间","code":"createDate","width":"100"},
              {name:"创建人","code":"creatorName","width":"100"}
              ],
		bindEvent:function(){
			$scope.head=ord.head;
			$scope.url="cmDriverInfoBO.ajax?cmd=doQueryCmDriver";
			$scope.toSave = this.toSave;
			$scope.doQuery = this.doQuery;
			$scope.toView=this.toView;
			$scope.doSave = this.doSave;
			$scope.close = this.close;
			$scope.data = this.data;
			$scope.selectOrg = this.selectOrg;
			$scope.doDel = this.doDel;
			$scope.params = this.params;
			$scope.toUpdate = this.toUpdate;
			$scope.clear = this.clear;
		},
		data:{
			orgId:-1
		},
		params:{
			
		},
		clear:function(){
			$scope.data.name = "";
			$scope.data.bill = "";
			$scope.data.identityCardNo="";
			$scope.data.orgId = -1;
			$scope.table.clearInput();
		},
		//查询事件
		doQuery:function(){
			var url = "cmDriverInfoBO.ajax?cmd=doQueryCmDriver";
			$scope.data.page=1;
			$scope.table.load();
			$scope.table.callBack=function(){
				displayTable();
				setContentHeight();
			} 
		},
		//查询所有网点
		selectOrg:function(){
			commonService.postUrl("organizationBO.ajax?cmd=getAllOrg","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.orgInfo = data;
					$scope.orgInfo.items.unshift({orgId:-1,orgName:"全部"});
				}
			});
		},
		/*selecUserInfo:function(){
			commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType","codeType=USER_TYPE",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.userData = data;
					$scope.userData.items.unshift({"codeValue":"-1","codeName":"全部"});
					$scope.data.userType = data.items[0].codeValue;
				}
			});
		},*/
		toSave:function(){
			commonService.openTab("11223","司机新增","/cm/addCmDriverInfo.html");
			//location.href="addCmDriverInfo.html";
		},
		doDel:function(){
			var id=""
			var array=$scope.table.getSelectData();
			if(array.length==0){
				commonService.alert("请选择数据");
				return;
			}
			if(array!=null&&array!=""){
				for(var i=0;i<array.length;i++){
					 var data=array[i];
					 id+= data.id+",";
				}
			}
			var param={"driverIds":id};
			 commonService.confirm("确认删除数据?",function(){
		         commonService.postUrl("cmDriverInfoBO.ajax?cmd=doDelDriverInfo",param,function(data){
						if(data != null && data != undefined && data != ""){
							commonService.alert("操作完成");
							$scope.doQuery();
						}
					});
	         });
		},
		toView:function(){
			var array=$scope.table.getSelectData();
			if(array.length==0){
				commonService.alert("请选择数据");
				return;
			}
			if(array.length>1){
				 commonService.alert("只能选择的一条数据");
					return;
			}
			var id="";
			var data=array[0];
			id=data.id;
			commonService.openTab(id+"2","司机详情","/cm/cmDriverInfoDetail.html?id="+id);
		},
		toUpdate:function(){
			var array=$scope.table.getSelectData();
			if(array.length==0){
				commonService.alert("请选择数据");
				return;
			}
			if(array.length>1){
				 commonService.alert("只能选择的一个修改");
					return;
			}
			var id="";
			var data=array[0];
			id=data.id;
			commonService.openTab(id+"2","司机修改","/cm/addCmDriverInfo.html?id="+id);
		}
	};
	ord.init();
}]);