var simulateFeeApp = angular.module("simulateFeeApp", ['commonApp']);
simulateFeeApp.controller("simulateFeeCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var ord ={
			init:function(){
			
				this.bindEvent();
				//this.query();
				this.userData();
				this.initStaticData();
				
			},
			bindEvent:function(){
				$scope.data = this.data;
				$scope.close = this.close;
				$scope.queryCost = this.queryCost;
				$scope.queryRoateRuting = this.queryRoateRuting;
				$scope.checkPrice = this.checkPrice;
				$scope.customParseInt = this.customParseInt;
				$scope.doQueryArea = this.doQueryArea;
				$scope.changeType = this.changeType;
				$scope.clean = this.clean;
			},
			orderInfo:{},
			//获取静态数据
			initStaticData:function(){
				/**开单网点*/
				var url = "staticDataBO.ajax?cmd=selectOrgByRole";
				commonService.postUrl(url,"",function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
						$scope.orgInfo=data;
						$scope.data.beginOrgId=data[0].orgId;
						if($scope.data.beginOrgId!=null && $scope.data.beginOrgId!=undefined){
						    $scope.queryRoateRuting($scope.data.beginOrgId);
						}
		 	    	}
				});
				
				commonService.postUrl("staticDataBO.ajax?cmd=selectDeliveryType","",function(data){
					if(data != null && data != undefined && data != ""){
						$scope.deliveryTypeData = data;
						$scope.data.deliveryType = $scope.deliveryTypeData.items[0].codeValue;
					}
				});
			},
			//查询配送网点
			queryRoateRuting:function(orgId){
				commonService.postUrl("routeBO.ajax?cmd=queryRoateTowards","beginOrgId="+orgId+"&isSimulaet=1",function(data){
					if(data != null && data != undefined && data != ""){
						$scope.roateData = data;
						if (undefined != data && undefined != data.items && data.items.length > 0) {
							$scope.data.endOrgId = data.items[0].endOrgId;
							$scope.doQueryArea($scope.data.endOrgId);
						}
					}
				});
			},
			data:{},
			userData:function(){
				$scope.currOrgId=userInfo.orgId;
				$scope.currOrgName=userInfo.orgName;
				
			},
		    //关闭页面
			close:function(){
				commonService.closeToParentTab(true);
			},
			//配送区域查询
			doQueryArea:function(defaultDescRegion){
				$scope.areaData = [];
				//查询配送区域
				if($scope.data.deliveryType ==2 || $scope.data.deliveryType == 3){
					if($scope.data.endOrgId>0){
						commonService.postUrl("webAcAreaFeeConfigBO.ajax?cmd=doQueryArea","orgId="+defaultDescRegion,function(data){
							if(data!=null && data!=undefined && data.numRows!=0){
								$scope.areaData = data;
							    $scope.data.descRegion =$scope.areaData.items[0].areaId;
							}
						});
					}
				}		
			},
			changeType:function(deliveryType){
				if(deliveryType==1 ){
					$scope.data.areaId="";
					$scope.data.floor="";
					$scope.data.isLift="";
				}
				if(deliveryType==2 ){
					$scope.data.floor="";
					$scope.data.isLift="";
				}
				if($scope.data.endOrgId!=null && $scope.data.endOrgId!=undefined && $scope.data.endOrgId!=""){
					$scope.doQueryArea($scope.data.endOrgId);
				}
				
			},
			//模拟算费
			checkPrice:function(){
				if($scope.data.deliveryType==null || $scope.data.deliveryType==undefined || $scope.data.deliveryType==""){
					commonService.alert("请选择交接方式");
					return;
				};
				if($scope.data.beginOrgId==null || $scope.data.beginOrgId==undefined || $scope.data.beginOrgId==""){
					commonService.alert("请选择开单网点");
					return;
				};
				if($scope.data.endOrgId==null || $scope.data.endOrgId==undefined || $scope.data.endOrgId==""){
					commonService.alert("请选择配送网点");
					return;
				};
				if($scope.data.volume==null || $scope.data.volume==undefined || $scope.data.volume==""){
					commonService.alert("请填写体积");
					return;
				};
				if($scope.data.weight==null || $scope.data.weight==undefined || $scope.data.weight==""){
					commonService.alert("请填写重量");
					return;
				};
				if($scope.data.deliveryType==3){
					if($scope.data.isLift==null || $scope.data.isLift==undefined || $scope.data.isLift==""){
						commonService.alert("请选择是否有电梯");
						return;
					};
					if($scope.data.floor==null || $scope.data.floor==undefined || $scope.data.floor==""){
						commonService.alert("请选择楼层");
						return;
					};
					if($scope.data.isCalHandling==null || $scope.data.isCalHandling==undefined || $scope.data.isCalHandling==""){
						commonService.alert("请选择是否收取装卸费");
						return;
					};
				}
				if($scope.data.deliveryType!=1){
					if($scope.data.descRegion==null || $scope.data.descRegion==undefined || $scope.data.descRegion==""){
						commonService.alert("请选择配送区域");
						return;
					};
				}
				$scope.data.transitionWay = $scope.data.deliveryType;
				if($scope.data.descRegion!=null && $scope.data.descRegion!=undefined && $scope.data.descRegion!=""){
					$scope.data.areaId=$scope.data.descRegion;
				}
				var url="routeCostBO.ajax?cmd=getRouteCost";
				commonService.postUrl(url,$scope.data,function(data){
					if(data.billingType!=null && data.billingType!=undefined && data.billingType!="" && data.billingType=="volume"){
						$scope.billingType="按体积";
					}
					if(data.billingType!=null && data.billingType!=undefined && data.billingType!="" && data.billingType=="weight"){
						$scope.billingType="按重量";
					}
					$scope.orgData=data.orgData;
					$scope.routeData=data.routeData;;
				});
			},
			/**
			 * 清空
			 */
			clean:function(){
					$scope.data.areaId="";
					$scope.data.floor="";
					$scope.data.isLift="";
					$scope.data.isLift="";
					$scope.data.volume="";
					$scope.data.weight="";
			},
			customParseInt: function(obj){
				if (obj == undefined || obj == null) {
					return 0;
				}
				if(isNaN(parseInt(obj))){
					return 0;
				}
				return parseInt(obj);
			},
	};
	ord.init();
}]);