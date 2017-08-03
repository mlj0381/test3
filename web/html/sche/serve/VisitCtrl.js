var myApp = angular.module("ApplicationApp", ['commonApp']);
myApp.controller("ApplicationCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.provinceData=[];
	$scope.cityData=[];
	$scope.districtData=[];
	$scope.visitInfoState=[];
	$scope.query={};
	var myManage={
		init:function(){
			$scope.query = {};
			$scope.add = {};
			$scope.audit = {};
			$scope.auditView = {};
			this.bindEvent();
			this.doQuery();
			this.selectProvince();
			this.getState();
		},
		bindEvent:function(){
			$scope.param = this.param;
			$scope.doQuery = this.doQuery;
		    $scope.doSave = this.doSave;
		    $scope.clear = this.clear;
		    $scope.selectAll = this.selectAll;
		    $scope.selectOne = this.selectOne;
		    $scope.keypress = this.keypress;
		    $scope.mouseLeave = this.mouseLeave;
		    $scope.auditSts = this.auditSts;
		    $scope.selectProvince = this.selectProvince;
		    $scope.selectCity = this.selectCity;
		    $scope.selectDistrict = this.selectDistrict;
		    $scope.modifyVisit = this.modifyVisit;
		    $scope.addVisit = this.addVisit;
		    $scope.getState=this.getState;
		},
		selectProvince:function(){
			var url = "staticDataBO.ajax?cmd=selectProvince";
			commonService.postUrl(url,"",function(data){
				if(data!=null && data!=undefined && data!="" && data.items.length>0){
					$scope.provinceData=data.items;
	 	    	}
			});
		},
		selectCity:function(){
			var param = {"provinceId":$scope.query.provinceId};
			var url = "staticDataBO.ajax?cmd=selectCity";
			commonService.postUrl(url,param,function(data){
				$scope.query.cityId=-1;
 	    		$scope.query.countyId=-1;
				if(data!=null && data!=undefined && data!="" && data.items.length>0){
					$scope.cityData=data.items;
	 	    	}else{
	 	    		$scope.cityData=[];
	 	    		$scope.districtData=[];
	 	    	}
			});
		},
		selectDistrict:function(){
			var param = {"cityId":$scope.query.cityId};
			var url = "staticDataBO.ajax?cmd=selectDistrict";
			commonService.postUrl(url,param,function(data){
				$scope.query.countyId=-1;
				if(data!=null && data!=undefined && data!="" && data.items.length>0){
					$scope.districtData=data.items;
	 	    	}else{
	 	    		$scope.districtData=[];
	 	    	}
			});
		},
		getState:function(){
			var url = "staticDataBO.ajax?cmd=getVisitInfoState";
			commonService.postUrl(url,"",function(data){
				if(data!=null && data!=undefined && data!=""){
					$scope.visitInfoState=data.items;
	 	    	}
			});
		},
		doQuery:function(){
			var url = "serveBO.ajax?cmd=doQueryVisit";
			$scope.query.page=1;
			$timeout(function(){
				$scope.page.load({
					url:url,
					params:$scope.query,
					callBack:'setContentHegthDelay'
				});
			},500);
		},
		addVisit:function(){
			var selectedDate=$scope.page.getSelectData();
			if(selectedDate==undefined || selectedDate.length==0 || selectedDate.length>1){
				commonService.alert("请选择一条数据！");
				return ;
			}
			selectedDate=selectedDate[0];
			commonService.openTab(selectedDate.TRACKING_NUM,"回访录入","/sche/serve/VisitAdd.html?id="+selectedDate.ID+"&wayBill="+selectedDate.TRACKING_NUM+"&flag=1");
		},
		modifyVisit:function(){
			var selectedDate=$scope.page.getSelectData();
			if(selectedDate==undefined || selectedDate.length==0 || selectedDate.length>1){
				commonService.alert("请选择一条数据！");
				return ;
			}
			selectedDate=selectedDate[0];
			commonService.openTab(selectedDate.TRACKING_NUM,"回访跟进","/sche/serve/VisitModify.html?id="+selectedDate.ID+"&wayBill="+selectedDate.TRACKING_NUM+"&flag=2");
		},
		auditBack:function(){
			document.getElementById('vehicle_1').style.display='none';
			document.getElementById('vehicle').style.display='block';
			$scope.audit = {};
		},
		applyBack:function(){
			document.getElementById('vehicle_3').style.display='none';
			document.getElementById('vehicle').style.display='block';
			$scope.add = {};
		},
		keypress:function(ev){
			if (ev.keyCode !== 13) return;
			var loginAcct = $scope.add.loginAcct;
			var param = {"phone":loginAcct};
			var url = "cashManageBO.ajax?cmd=doQuerySf";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.add = data;
					$scope.add.loginAcct = loginAcct;
				}
			});
		},
		mouseLeave:function(){
			var loginAcct = $scope.add.loginAcct;
			var param = {"phone":loginAcct};
			var url = "cashManageBO.ajax?cmd=doQuerySf";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.add = data;
					$scope.add.loginAcct = loginAcct;
				}
				
			});
		},
		auditSts:function(state){
			var auditNote = $scope.audit.auditNote;
			var appId = $scope.audit.id;
			var param = {"appId":appId,"state":state,"auditNote":auditNote};
			var url = "cashManageBO.ajax?cmd=doUpdateApplication";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("处理成功!");
					$scope.auditBack();
					$scope.auditBack();
					$scope.doQuery();
				}
				
			});
		},
		params:{
		},
		/**全选*/
		selectAll : function(id,id1) {
			var checkbox = document.getElementsByName(id);
//			var checkbox = document.getElementsByName("check-1");
			if (document.getElementById(id1).checked) {
//				if (document.getElementById("checkbox").checked) {
				for (var i = 0; i < checkbox.length; i++) {
					checkbox[i].checked = true;
				}
			} else {
				for (var i = 0; i < checkbox.length; i++) {
					checkbox[i].checked = false;
				}
			}
		},
		/**清空查询条件*/
		clear:function(){
			$scope.query={"provinceId":-1,"cityId":-1,"countyId":-1};
			$scope.query.state=-1;
		},
		/**车辆状态查询*/
		doQueryCheckSts:function(){
		},
		/**网点列表查询*/
		queryOrg:function(){
			var param = {"addType":2,"collectType":3};
			var url = "routeBO.ajax?cmd=queryRoateRuting";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.orgInfo=data;
					$scope.query.descOrgId=data.items[0].endOrgId;
	 	    	}
			});
			
		},
		/**选择一行**/
		selectOne : function(id,strId){
			if(document.getElementById(strId+id).checked && document.getElementById(strId+id) != undefined){
				document.getElementById(strId+id).checked=false;
			}else{
				document.getElementById(strId+id).checked=true;
			}
		}
	};
	myManage.init();
	
}]);
