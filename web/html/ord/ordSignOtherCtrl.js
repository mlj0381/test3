function changeTabCallback(){
	var appElement = document.querySelector('[ng-controller=ordSignCtrl]');
	var scope=angular.element(appElement).scope();
	scope.doQuery();
	scope.$apply();
}

var ordSignApp = angular.module("ordSignApp", ['commonApp']);
ordSignApp.controller("ordSignCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var exportExcel = false;
	var signManage={
		init:function(){
			this.userData();
			//this.doQuery();
			this.bindEvent();
			
			var returnKeyEventDomEleIds = ["beginTime","endTime","id1","id2"];
		    commonService.registerKeyEventForDoms(returnKeyEventDomEleIds, "keydown", "return", $scope.doQuery);
		},
		bindEvent:function(){
			$scope.param=this.param;
			$scope.doQuery=this.doQuery;
		    $scope.toView=this.toView;
		    $scope.clear=this.clear;
		    $scope.selectOne=this.selectOne;
		    $scope.query=this.query;
		    $scope.selectCurrOrgId=this.selectCurrOrgId;
		    $scope.desOrgData=this.desOrgData;
		    $scope.currOrgData=this.currOrgData;
		    $scope.selectOne=this.selectOne;
		    $scope.toSaveSign = this.toSaveSign;
			$scope.toView2 = this.toView2;
		},
		toView2:function(trackingNum){
			window.open("/ord/ordBillingDetail.html?view=1&trackingNum="+trackingNum+"&type=3&ver=${ver}");
		},
		params:{
			page:1,
			rows: 50
		},
		query:{
			vehicleState:"-1",
			signState : "-1"
		},
		/**签收录入**/
        toSaveSign:function(){
        	var orderId = '';
			var batchNum = '';
        	if($("input[name='checkbox2']:checked").length == 0){
				commonService.alert("请至少选择一条信息!");
				return false;
			}
			if($("input[name='checkbox2']:checked").length>1){
				commonService.alert("只能选择一条信息!");
				return false;
			}
			$("input[name='checkbox2']:checked").each(function(){
				if($(this).val() != null && $(this).val() != undefined && $(this).val() != ''){
					var data = eval("("+$(this).val()+")");
					orderId = data.orderId;
					batchNum = data.batchNum;
				}
			});
	         commonService.openTab(orderId,"签收录入","/ord/ordSignDtl.html?orderId="+orderId+"&batchNum="+batchNum+"&type=1");
        },
		/**选择一行**/
		selectOne : function(orderId){
			if(document.getElementById("checkbox"+orderId).checked && document.getElementById("checkbox"+orderId) != undefined){
				document.getElementById("checkbox"+orderId).checked=false;
			}else{
				document.getElementById("checkbox"+orderId).checked=true;
			}
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
					    $scope.desOrgData($scope.orgType);
	 	    	}
			});
		},
		/**列表查询*/
		doQuery : function(){
				signManage.params.batchNum=signManage.query.batchNum;
				signManage.params.beginTime = document.getElementById("beginTime").value;
				signManage.params.endTime = document.getElementById("endTime").value;
				var url = "orderInfoBO.ajax?cmd=doQuerySignBatch";
				signManage.params.page=1;
				signManage.params.signState = signManage.query.signState;
				$scope.tableCallBack = function(){
//					commonService.refreshPageContentHeight($scope.page.data.items.length, 663, 270);
					setContentHegthDelay();
				};
				$timeout(function(){
					$scope.page.load({
								url:url,
								params:signManage.params,
								callBack:"$scope.tableCallBack"
							});
				},500);
			
		},
		/**清空查询条件*/
		clear:function(){
			document.getElementById("beginTime").value='';
			document.getElementById("endTime").value='';
			signManage.params.beginTime = "";
			signManage.params.endTime = "";
			$scope.query.batchNum="";
			$scope.query.vehicleState='-1';
			//$scope.query.descOrgId=-1;
			$scope.query.descOrgId="";
			$scope.query.currOrgId="";
		},

  
		
		/**
		 * 查询目的网点
		 */
		selectCurrOrgId:function(orgType){
			if(orgType==2){
				var param = {"collectType":4,"addType":1,"collectState":1};
				var url = "routeBO.ajax?cmd=getUserRoute";	
				commonService.postUrl(url,param,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
					    $scope.desOrgData=data.routeList;
		 	    	}
				});
			}else{
				var param = {"collectType":3,"addType":1};
				var url = "routeBO.ajax?cmd=getUserRoute";	
				commonService.postUrl(url,param,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
					    $scope.desOrgData=data.routeList;
							 $scope.currOrgData=new Array(); 
							 if(data.routeList!=null && data.routeList!=undefined && data.routeList.length>0){
							       $scope.currOrgData.push(data.routeList[0]);
							       $scope.query.currOrgId=  $scope.desOrgData[0].beginOrgId;
					         } 
		 	    	}
				});
			}
		},
		 
		
		/**
		 * 查询发车网点
		 */
		desOrgData:function(orgType){
			if(orgType==2){
				var param = {"collectType":4,"addType":1,"collectState":2};
				var url = "routeBO.ajax?cmd=getUserRoute";	
				commonService.postUrl(url,param,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
					    $scope.currOrgData=data.routeList;
					    $scope.query.currOrgId=parseInt(userInfo.orgId);
		 	    	}
				});
			}
			$timeout(function(){
			   $scope.doQuery();
			},500);
			
		},

	};
	
	signManage.init();
}]);


