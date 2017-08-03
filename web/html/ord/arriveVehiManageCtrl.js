function changeTabCallback(){
	var appElement = document.querySelector('[ng-controller=arriveVehiManageCtrl]');
	var scope=angular.element(appElement).scope();
	scope.doQuery();
	scope.$apply();
}
var arriveVehiManageApp = angular.module("arriveVehiManageApp", ['commonApp']);
arriveVehiManageApp.controller("arriveVehiManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var arriveVehiManage={
		init:function(){
			this.userData();
			//this.doQuery();
			this.bindEvent();
		},
		bindEvent:function(){
			$scope.param=this.param;
			$scope.doQuery=this.doQuery;
		    $scope.print=this.print;
		    $scope.toView=this.toView;
		    $scope.query=this.query;
		    $scope.clear=this.clear;
		    $scope.selectAll=this.selectAll;
		    $scope.arriveVehConf=this.arriveVehConf;
		    $scope.confirmGoodVehi=this.confirmGoodVehi;
		    $scope.selectOne=this.selectOne;
		    
		    $scope.selectCurrOrgId=this.selectCurrOrgId;
		    $scope.desOrgData=this.desOrgData;
		    $scope.currOrgData=this.currOrgData;
		    $scope.changeData=this.changeData;
		    
		    
		},
		params:{
			vehicleState:"",
			page:1
		},
		query:{
			vehicleState:"2"
		},
	   currOrgData:{
			
		},
		userData:function(){
			/**
			 * TODO
			 * 查询当前登录网点类型
			 */
			var url = "staticDataBO.ajax?cmd=selectOrgInfo";
			var param={"orgId":userInfo.orgId};
			commonService.postUrl(url,param,function(data){
				//成功执行
				if(data!=null && data!=undefined && data!=""){
					$scope.orgInfoData=data;
				    $scope.orgType=data.orgType;
				    $scope.showData=true;
				    /*if($scope.orgType==2){
				    	 $scope.showData=false;
				    }*/
				    $scope.selectCurrOrgId($scope.orgType);
				    $scope.desOrgData($scope.orgType);
				    
	 	    	}
			});
			$scope.currOrgId=userInfo.orgId;
			$scope.currOrgName=userInfo.orgName;
		},
		/**全选*/
		selectAll : function() {
			var checkbox = document.getElementsByName("check-1");
			if (document.getElementById("checkbox").checked) {
				for (var i = 0; i < checkbox.length; i++) {
					checkbox[i].checked = true;
				}
			} else {
				for (var i = 0; i < checkbox.length; i++) {
					checkbox[i].checked = false;
				}
			}
		},
		/**列表查询*/
		doQuery:function(){
			arriveVehiManage.params.vehicleState=arriveVehiManage.query.vehicleState;
			arriveVehiManage.params.startOrgId=arriveVehiManage.query.startOrgId;
			arriveVehiManage.params.descOrgId=arriveVehiManage.query.currOrgId;
			arriveVehiManage.params.batchNum=arriveVehiManage.query.batchNum;
			arriveVehiManage.params.beginTime=document.getElementById("beginTime").value;
			arriveVehiManage.params.endTime=document.getElementById("endTime").value;
			arriveVehiManage.params.batchNumAlias=arriveVehiManage.query.batchNumAlias;
			arriveVehiManage.params.page=1;
		//	if(arriveVehiManage.params.descOrgId!=null && arriveVehiManage.params.descOrgId!=undefined && arriveVehiManage.params.descOrgId!=""){
//				if(arriveVehiManage.params.descOrgId==userInfo.orgId){
//					$scope.showData=true;
//				}else{
//					$scope.showData=false;
//				}
		//	}
			arriveVehiManage.params.isShort=1; //短驳
			var url = "orderInfoBO.ajax?cmd=queryDepartInfo";
			
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:arriveVehiManage.params,
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
			$scope.query.startOrgId="";
			$scope.query.currOrgId="";
		},
		/**选择一行**/
		selectOne : function(batchNum){
			if(document.getElementById("checkbox"+batchNum).checked && document.getElementById("checkbox"+batchNum) != undefined){
				document.getElementById("checkbox"+batchNum).checked=false;
			}else{
				document.getElementById("checkbox"+batchNum).checked=true;
			}
		},
		/**查看**/
		toView:function(){
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
			commonService.openTab(batchNum,"配载详情","/ord/depearInfo.html?batchNum="+batchNum);
	   },
				/**TODO
				 * 查询目的网点
				 */
				selectCurrOrgId:function(orgType){
					if(orgType==2){
						var param = {"collectType":4,"addType":1,"collectState":2};
						var url = "routeBO.ajax?cmd=getUserRoute";	
						commonService.postUrl(url,param,function(data){
							//成功执行
							if(data!=null && data!=undefined && data!=""){
							    $scope.currOrgData=data.routeList;
							    $scope.query.currOrgId=parseInt(userInfo.orgId);
							    $scope.doQuery();
				 	    	}
						});
					}
				},
				  changeData:function(starOrgId){
					/*if(starOrgId!=userInfo.orgId){
					   $scope.showData=false;
					}  else{
						$scope.showData=true;
					}*/
				  },
				
				/**TODO
				 * 查询发车网点
				 */
				desOrgData:function(orgType){
//					if(orgType==2){
//						var param = {"collectType":4,"addType":1,"collectState":1};
//						var url = "routeBO.ajax?cmd=getUserRoute";	
//						commonService.postUrl(url,param,function(data){
//							//成功执行
//							if(data!=null && data!=undefined && data!=""){
//							    $scope.desOrgData=data.routeList;
//				 	    	}
//						});
//					}else{
//						var param = {"collectType":3,"addType":1};
//						var url = "routeBO.ajax?cmd=getUserRoute";	
//						commonService.postUrl(url,param,function(data){
//							//成功执行
//							if(data!=null && data!=undefined && data!=""){
//							    $scope.desOrgData=data.routeList;
//									 $scope.currOrgData=new Array(); 
//									 if(data.routeList!=null && data.routeList!=undefined && data.routeList.length>0){
//									       $scope.currOrgData.push(data.routeList[0]);
//									       $scope.query.currOrgId= $scope.currOrgData[0].beginOrgId;
//							         } 
//				 	    	}
//						});
//					}
					
					//所有当前短驳能到达的线路
					var url = "routeBO.ajax?cmd=queryRoateRutingIsShort";	
					commonService.postUrl(url,{"type":"1"},function(data){
						console.log(data);
						//成功执行
						if(data!=null && data!=undefined && data!=""){
						    $scope.beginOrgData = data.routeList;
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
				
				
				
				
		/***到车确认**/
		arriveVehConf:function(){
			var batchNum='';
			var flag=false;
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
						$scope.query.vehicleState="2";
						$scope.doQuery();
						
		 	    	}
				});
			});
		},
		
		/**到货确认*/
		confirmGoodVehi:function(){
			var batchNum='';
			var flag=false;
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
			if(data.state!=3&&data.state!=5){
			    flag=true;
			    commonService.alert("批次["+data.batchNumAlias+"]状态为"+data.stateName+",不可以操作[到货确认]!");
			    return false;
		    }
			if(flag){
				return false;
			}
			commonService.openTab(batchNum+"23","到货确认","/ord/depearInfo.html?batchNum="+batchNum+"&flag=1");
		},
		
		/**打印*/
		print:function(){
		}
		
	};
	
	arriveVehiManage.init();
}]);
