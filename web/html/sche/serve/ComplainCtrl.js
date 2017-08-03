var myApp = angular.module("ApplicationApp", ['commonApp']);
myApp.controller("ApplicationCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.query={};
	$scope.cityData=[];
	$scope.provinceData=[];
	$scope.districtData=[];
	var myManage={
		init:function(){
			$scope.query = {};
			$scope.add = {};
			$scope.audit = {};
			$scope.auditView = {};
			this.bindEvent();
			this.doQuery();
			this.selectProvince();
		},
		bindEvent:function(){
			$scope.param = this.param;
			$scope.doQuery = this.doQuery;
		    $scope.addComplaint = this.addComplaint;
		    $scope.modifyComplaint = this.modifyComplaint;
		    $scope.doSave = this.doSave;
		    $scope.clear = this.clear;
		    $scope.selectAll = this.selectAll;
		    $scope.selectOne = this.selectOne;
		    $scope.keypress = this.keypress;
		    $scope.mouseLeave = this.mouseLeave;
		    $scope.applyBack = this.applyBack;
		    $scope.auditBack = this.auditBack;
		    $scope.auditSts = this.auditSts;
		    $scope.getApplicationByIdView = this.getApplicationByIdView;
		    $scope.selectProvince = this.selectProvince;
		    $scope.selectCity = this.selectCity;
		    $scope.selectDistrict = this.selectDistrict;
		},
		selectProvince:function(){
			var url = "staticDataBO.ajax?cmd=selectProvince";
			commonService.postUrl(url,"",function(data){
				if(data!=null && data!=undefined && data!=""){
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
		clear:function(){
			$scope.query={"provinceId":-1,"cityId":-1,"countyId":-1,"state":-1};
		},
		doQuery:function(){
			var url = "serveBO.ajax?cmd=doQuery";
			$timeout(function(){
				$scope.page.load({
					url:url,
					params:$scope.query,
					callBack:'setContentHegthDelay'
				});
				
			},500);
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
		selectOne : function(id,strId,complainId){
			if(complainId==null||complainId==undefined){
				complainId = "";
			}
			if(document.getElementById(strId+id+complainId).checked && document.getElementById(strId+id+complainId) != undefined){
				document.getElementById(strId+id+complainId).checked=false;
			}else{
				document.getElementById(strId+id+complainId).checked=true;
			}
		},
		addComplaint:function(){
			var selectedDate=$scope.page.getSelectData();
			if(selectedDate==undefined || selectedDate==null || selectedDate.length==0 ){
				commonService.alert("请选择一条数据！");
				return ;
			}
			selectedDate=selectedDate[0];
			var sfUserId;
			if(selectedDate.bcSfUserId==null||selectedDate.bcSfUserId==""||selectedDate.bcSfUserId==undefined){
				sfUserId = selectedDate.doObjId;
			}else{
				sfUserId = selectedDate.bcSfUserId;
			}
			commonService.openTab(selectedDate.wayBillId,"投诉录入","/sche/serve/ComplainAdd.html?id="+selectedDate.id+"&batchNum="+selectedDate.wayBillId+"&belongObjId="+selectedDate.belongObjId+"&belongObjName="+escape(selectedDate.belongObjName,"gbk")+"&wayBillId="+selectedDate.wayBillId+"&sfUserId="+sfUserId+"&orderId="+selectedDate.orderId);
		},
		modifyComplaint:function(){
			var selectedDate=$scope.page.getSelectData();
			if(selectedDate==undefined || selectedDate==null || selectedDate.length==0 ){
				commonService.alert("请选择一条数据！");
				return ;
			}
			selectedDate=selectedDate[0];
			var sfUserId;
			if(selectedDate.bcSfUserId==null||selectedDate.bcSfUserId==""||selectedDate.bcSfUserId==undefined){
				sfUserId = selectedDate.doObjId;
			}else{
				sfUserId = selectedDate.bcSfUserId;
			}
			commonService.openTab(selectedDate.wayBillId,"投诉跟进","/sche/serve/ComplainModify.html?batchNum="+selectedDate.wayBillId+"&belongObjId="+selectedDate.belongObjId+"&belongObjName="+escape(selectedDate.belongObjName,"gbk")+"&wayBillId="+selectedDate.wayBillId+"&sfUserId="+sfUserId+"&state="+selectedDate.state+"&id="+selectedDate.id+"&orderId="+selectedDate.orderId);
		},
		/**提现查看**/
		getApplicationByIdView:function(){
			var batchNum=new Array();
			var isCheckStsOk = false;
			var isPayStsOk = false;
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请选择一条数据!");
				return false;
			}
			var count = 0;
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					batchNum.push(data.id);
					count = count + 1;
				}
			});
			if(count>1){
				commonService.alert("只能选择一条记录!");
				return false;
			}
			var param = {"id":batchNum.join(",")};
			var url = "cashManageBO.ajax?cmd=getApplicationById";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					document.getElementById('vehicle_2').style.display='block';
					document.getElementById('vehicle').style.display='none';
					$scope.auditView = data;
					
				}
			});
		},
		/**申请提现**/
		doSave:function(){
			var batchNum=new Array();
			var isSysDo = false;
			if($("input[name='check-2']:checked").length==0){
				commonService.alert("请至少选择一条数据!");
				return false;
			}
			
			$("input[name='check-2']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					batchNum.push(data.taskId);
				}
			});
			var param = {"taskIds":batchNum.join(","),"userId":$scope.add.userId};
			var url = "cashManageBO.ajax?cmd=doSave";
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					commonService.alert("处理成功!");
					$scope.applyBack();
				}
			});
		}
	};
	myManage.init();
}]);
