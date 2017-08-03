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
		},
		bindEvent:function(){
			$scope.param=this.param;
			$scope.doQuery=this.doQuery;
		    $scope.deleteDepart=this.deleteDepart;
		    $scope.print=this.print;
		    $scope.toView=this.toView;
		    $scope.query=this.query;
		    $scope.clear=this.clear;
		    $scope.selectAll=this.selectAll;
		    $scope.doSave=this.doSave;
		    $scope.confirmMatchVehi=this.confirmMatchVehi;
		    $scope.doModify=this.doModify;
		    $scope.selectOne=this.selectOne;
		    $scope.query=this.query;
		    $scope.commonExport = this.commonExport;
		    $scope.selectCurrOrgId=this.selectCurrOrgId;
		    // $scope.desOrgData=this.desOrgData;
		    // $scope.currOrgData=this.currOrgData;
		    $scope.cancelMatchVehi=this.cancelMatchVehi;
		    
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
			//		$scope.desOrgData($scope.orgType);
	 	    	}
			});
		},
		 
		//导出方法
		commonExport : function(){
			if(exportExcel){
				return false;
			}
			var vehicleState = departManage.query.vehicleState;
			var descOrgId = departManage.query.descOrgId;
			var sourceOrgId = $scope.currOrgId;
			var batchNum = departManage.query.batchNum;
			var beginTime = $("#beginTime").val();
			var endTime = $("#endTime").val();
			
			var sqlKey = "departKey"; //查询SQL
			var sqlParams = {};
			if(batchNum != undefined && batchNum != null && batchNum != ''){
				sqlParams.batchNum =batchNum;
			}
//			console.log(vehicleState);
			if(vehicleState != undefined && vehicleState != null && vehicleState != ''){
				if(parseInt(vehicleState) >= 0){
					sqlParams.state =vehicleState;
				}
				
			}
//			console.log(descOrgId);
			if(descOrgId != undefined && descOrgId != null && descOrgId != ''){
				if(parseInt(descOrgId) >= 0){
					sqlParams.descOrgId = descOrgId;
				}
				
			}
//			console.log(beginTime);
			if(beginTime != undefined && beginTime != null && beginTime != ''){
				sqlParams.beginTime = beginTime+" 00:00:00";
			}
			if(endTime != undefined && endTime != null && endTime != ''){
				sqlParams.endTime =  endTime+" 23:59:59";
			}
			sqlParams.sourceOrgId =  sourceOrgId;
//			console.log(JSON.stringify(sqlParams));
			var url = "commonExportBO.ajax?cmd=commonExport";
			var toUrl = signUrl(url+"&sqlKey="+sqlKey+"&sqlParams="+JSON.stringify(sqlParams)+"&_ALLEXPORT=1");
			var iframe = document.createElement("iframe");
		    iframe.id = "frameDownloading";
		    iframe.src = toUrl;
		    iframe.style.display = "none";
		    document.body.appendChild(iframe);
		    
		    var selectExport = document.getElementById("selectExport");
			exportExcel = true;
			selectExport.innerHTML = "导出中。";
			//导出倒计时
			$timeout(function() {
				exportExcel = false;
					selectExport.innerHTML = "导出";
			},3000);
			
		},

		/**列表查询*/
		doQuery:function(){
			departManage.params.vehicleState=departManage.query.vehicleState;
			/**TODO 中心能查看全部**/
			departManage.params.startOrgId=departManage.query.currOrgId;
			departManage.params.descOrgId=departManage.query.descOrgId;
			
			
			//if(departManage.params.startOrgId!=null && departManage.params.startOrgId!=undefined && departManage.params.startOrgId!=""){
//				if(departManage.params.startOrgId==userInfo.orgId){
//					$scope.isShow=true;
//				}else{
//					$scope.isShow=false;
//				}
			//}
			
			departManage.params.batchNum=departManage.query.batchNum;
			departManage.params.beginTime=document.getElementById("beginTime").value;
			departManage.params.endTime=document.getElementById("endTime").value;
			departManage.params.batchNumAlias=departManage.query.batchNumAlias;
			var url = "orderInfoBO.ajax?cmd=queryMatchInfo";
			departManage.params.page=1;
			departManage.params.isShort=1; //短驳
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:departManage.params,
							callBack:"setContentHegthDelay"
						});
			},500);
		},
		/**清空查询条件*/
		clear:function(){
			document.getElementById("beginTime").value='';
			document.getElementById("endTime").value='';
			$scope.query.batchNumAlias="";
			$scope.query.vehicleState='-1';
			//$scope.query.descOrgId=-1;
			$scope.query.descOrgId="";
//			$scope.query.currOrgId="";
		},

		/**查看**/
		toView:function(){
			var batchNum=$scope.page.getOneSelected();;
			
			if(batchNum==""){
				commonService.alert("请至少选择一条配载信息!");
				return false;
			}
			
			commonService.openTab(batchNum,"配载详情","/ord/depearInfo.html?batchNum="+batchNum);
				},
		/***保存**/
		doSave:function(){
			commonService.openTab(new Date().getDay()+"12345","短驳新增配载信息","/ord/addDepart.html?isShort=1");
		},
		/***修改**/
		doModify:function(){
			var batchNum='';
			var selectDatas =  $scope.page.getSelectData();
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
					commonService.openTab(batchNum,"短驳修改配载信息","/ord/addDepart.html?batchNum="+batchNum);
				});
			}else{
				commonService.openTab(batchNum,"短驳修改配载信息","/ord/addDepart.html?batchNum="+batchNum);
			}
		},
		/**打印*/
		print:function(){
		},
		/**删除*/
		deleteDepart:function(){
			var batchNum='';
			var selectDatas =  $scope.page.getSelectData();
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
		
		/**
		 * 查询目的网点
		 */
		selectCurrOrgId:function(orgType){
//			if(orgType==2){
//				var param = {"collectType":4,"addType":1,"collectState":1};
//				var url = "routeBO.ajax?cmd=getUserRoute";	
//				commonService.postUrl(url,param,function(data){
//					//成功执行
//					if(data!=null && data!=undefined && data!=""){
//					    $scope.desOrgData=data.routeList;
//		 	    	}
//				});
//			}else{
//				var param = {"collectType":3,"addType":1};
//				var url = "routeBO.ajax?cmd=getUserRoute";	
//				commonService.postUrl(url,param,function(data){
//					//成功执行
//					if(data!=null && data!=undefined && data!=""){
//					    $scope.desOrgData=data.routeList;
//							 $scope.currOrgData=new Array(); 
//							 if(data.routeList!=null && data.routeList!=undefined && data.routeList.length>0){
//							       $scope.currOrgData.push(data.routeList[0]);
//							       $scope.query.currOrgId=  $scope.desOrgData[0].beginOrgId;
//					         } 
//		 	    	}
//				});
//			}
			//所有当前短驳能到达的线路
			var url = "routeBO.ajax?cmd=queryRoateRutingIsShort";	
			commonService.postUrl(url,{},function(data){
				console.log(data);
				//成功执行
				if(data!=null && data!=undefined && data!=""){
				    $scope.desOrgData=data.routeList;
	 	    	}
			});
			
			//当前网点
			$scope.currOrgData = [];
			$scope.currOrgId=userInfo.orgId;
			$scope.currOrgName=userInfo.orgName;
			var obj = new Object();
			obj.beginOrgId = $scope.currOrgId;
			obj.beginOrgName = $scope.currOrgName;
			$scope.currOrgData.push(obj);
			$scope.query.currOrgId = $scope.currOrgId;
			$scope.doQuery();
			
		},
		
//		/**
//		 * 查询发车网点
//		 */
//		desOrgData:function(orgType){
//			if(orgType==2){
//				var param = {"collectType":4,"addType":1,"collectState":2};
//				var url = "routeBO.ajax?cmd=getUserRoute";	
//				commonService.postUrl(url,param,function(data){
//					//成功执行
//					if(data!=null && data!=undefined && data!=""){
//					    $scope.currOrgData=data.routeList;
//					    $scope.query.currOrgId=parseInt(userInfo.orgId);
//		 	    	}
//				});
//			}
//			$timeout(function(){
//			   $scope.doQuery();
//			},500);
//			
//		},
		
		
		
		/**发车确认*/
		confirmMatchVehi:function(){
            var batchNum='';
			var selectDatas =  $scope.page.getSelectData();
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

			if(data.state!=1){
			     flag=true;
			     commonService.alert("批次["+data.batchNumAlias+"]状态为"+data.stateName+",不可以操作[发车确认]!");
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
		//发车取消
		cancelMatchVehi:function(){
			var batchNum = "";
			var selectDatas =  $scope.page.getSelectData();
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

			if(data.state != 2){
				flag=true;
				commonService.alert("批次["+data.batchNumAlias+"]状态为"+data.stateName+",不可以操作[取消发车]!");
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
	
	departManage.init();
}]);
//get URL加密
function signUrl(orgiUrl) {
	var paramArray = new Array();
	var name,value,paramStr; 
	if (orgiUrl != undefined) {
		 var url = orgiUrl.substring(orgiUrl.lastIndexOf("/")+1);
		 if ((idx = url.indexOf("&")) > 0) {
			 paramStr = url.substring(0, idx);
			 var params = url.substring(idx+1).split("&");
			 for (var i in params) {
				 if (params[i].split("=")[1] !== "null" && params[i].split("=")[1] !== "") {
					 paramArray.push(params[i]);
				 }
			 }
		 } else {
			 paramStr = url;
		 }
	}
    if (paramArray.length > 0)
    	paramStr += "&"+ paramArray.sort().join("&");
        paramStr += "&sign=" +md5(paramStr+getCookie("token"));
    return paramStr;
}
