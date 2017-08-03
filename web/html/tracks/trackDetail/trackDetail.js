

/**
 * 签收信息
 */
commonApp.directive("signShow",['commonService',"$timeout","$rootScope",function(commonService,$timeout,$rootScope){
	return {
		restrict: 'E',
		scope: {
			"orderNo":"=orderno",
			"orderId":"=orderid",
			"showSign":"=showsign",
			"signup":"=signup",
		    "type":"=type"
		},
		templateUrl : function(tElement,tAttrs){
	      	//return '../orders/ordDetail/signDetail.html?ver='+tAttrs.ver;
	      	return '../tracks/trackDetail/signDetail.html?ver='+tAttrs.ver;},
		link: function($scope, elem, attrs) {
			$scope.form={};
			$scope.load = function(orderNo){
				$scope.showData = {};
				if($scope.orderNo != null && $scope.orderNo != undefined && $scope.orderNo != ""&&$scope.signup!=1){
					var getTimestamp=new Date().getTime();
					commonService.postUrl("ordOrdersWebBO.ajax?cmd=querySignInfo&timestamp="+getTimestamp+"1",{"orderNo":orderNo},function(data){
						$scope.showData=data;
						$scope.form=data;
						console.log($rootScope);
						$scope.form.certificateType=data.certificateType+"";
						console.log($scope.form);
						for(var i=0;i<5;i++){
							if($scope.signup!=2){
								//idenCard1
								eval("$rootScope.idenCard"+(i+1)+".clean()");
								
							}else {
								eval("$rootScope.orderPicCard"+(i+1)+".clean()");
							}
							
						}
						if(data.flowId!=null&&data.flowId!=undefined&&data.flowId!=''){
							var flowId=data.flowId.split(",");
							for(var i=0;i<flowId.length;i++){
								if($scope.signup!=2){
									eval("$rootScope.idenCard"+(i+1)+".initDate("+flowId[i]+")");
								}else{
									eval("$rootScope.orderPicCard"+(i+1)+".initDate("+flowId[i]+")");
								}
								
							}
						}
						    //如果不存在也不能修改
						$rootScope.idenCard1.isUpShow(false);
						$rootScope.idenCard2.isUpShow(false); 
						$rootScope.idenCard3.isUpShow(false);
						$rootScope.idenCard4.isUpShow(false); 
						$rootScope.idenCard5.isUpShow(false); 
						
					});
				}
			};
			$scope.close = function(){
				commonService.closeToParentTab(true);
			};
			//保存图片
			$scope.doSavePic = function(){
				var flowId='';
				if($scope.orderId != null && $scope.orderId != undefined && $scope.orderId != ""){
					if($rootScope.orderPicCard1.get().flowId!=null && $rootScope.orderPicCard1.get().flowId!=undefined && $rootScope.orderPicCard1.get().flowId!=''){
						flowId=$rootScope.orderPicCard1.get().flowId;
					}
					if($rootScope.orderPicCard2.get().flowId!=null && $rootScope.orderPicCard2.get().flowId!=undefined && $rootScope.orderPicCard2.get().flowId!=''){
						if(flowId!=''){
							flowId+=","+$rootScope.orderPicCard2.get().flowId;
						}else{
							flowId+=$rootScope.orderPicCard2.get().flowId;
						}
					}
					if($rootScope.orderPicCard3.get().flowId!=null && $rootScope.orderPicCard3.get().flowId!=undefined && $rootScope.orderPicCard3.get().flowId!=''){
						if(flowId!=''){
							flowId+=","+$rootScope.orderPicCard3.get().flowId;
						}else{
							flowId+=$rootScope.orderPicCard3.get().flowId;
						}
					}
					if($rootScope.orderPicCard4.get().flowId!=null && $rootScope.orderPicCard4.get().flowId!=undefined && $rootScope.orderPicCard4.get().flowId!=''){
						if(flowId!=''){
							flowId+=","+$rootScope.orderPicCard4.get().flowId;
						}else{
							flowId+=$rootScope.orderPicCard4.get().flowId;
						}
					}
					if($rootScope.orderPicCard5.get().flowId!=null && $rootScope.orderPicCard5.get().flowId!=undefined && $rootScope.orderPicCard5.get().flowId!=''){
						if(flowId!=''){
							flowId+=","+$rootScope.orderPicCard5.get().flowId;
						}else{
							flowId+=$rootScope.orderPicCard5.get().flowId;
						}
					}
				}
				//查询运单信息
				var param = {"orderId":$scope.orderId,"type":$scope.type, "signDesc":$scope.form.signDesc,"flowId":flowId,"signName":$scope.form.signName,"ext1":$scope.form.ext1,"certificateNo":$scope.form.certificateNo,"certificateType":$scope.form.certificateType};
				var url = "ordOrdersWebBO.ajax?cmd=signUpOrderInfo";
				commonService.postUrl(url,param,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
						commonService.alert("签收成功",function close(){commonService.closeToParentTab(true);})
						
					}
				});
			};
			if($scope.signup!=1){
				$scope.load($scope.orderNo);
			}
			$scope.$watch('orderNo',function(oldVal,newVal){
				if(oldVal != newVal){
					$timeout(function(){
						$scope.load($scope.orderNo);
					},500);
				}
			});
		}
	};
}]);


/**
 * 子运单信息
 */
commonApp.directive("consignorInfo",['commonService',"$timeout",function(commonService,$timeout){
	var childTrackNum;
	var i=0;
	return {
		restrict: 'E',
		scope: {
			"trackingNum":"=trackingNum",
		},
		templateUrl : function(tElement,tAttrs){
	      	return '../tracks/trackDetail/consigorInfo.html?ver='+tAttrs.ver;
	    },
		link: function($scope, elem, attrs) {
			$scope.load = function(trackingNum){
				$scope.showData = {};
				if($scope.trackingNum != null && $scope.trackingNum != undefined && $scope.trackingNum != ""){
					var getTimestamp=new Date().getTime();
					commonService.postUrl("ordOrdersWebBO.ajax?cmd=querychildOrderInfo&timestamp="+getTimestamp,{"trackingNum":trackingNum},function(data){
						$scope.consignorInfo=data.childTrack;
						childTrackNum=data.childTrack.childOrderName;
					});
				}
			};
			$scope.load($scope.trackingNum);
			$scope.$watch('trackingNum',function(oldVal,newVal){
				if(oldVal != newVal){
					$timeout(function(){
						$scope.load($scope.trackingNum);
					},500);
				}
			});
			
			$scope.trackChildOrderInfo = function(id){
				
				$scope.yy = true;
				var childData=$scope.consignorInfo;
				if($scope.trackingNum != null && $scope.trackingNum != undefined && $scope.trackingNum != ""){
					var getTimestamp=new Date().getTime();
					commonService.postUrl("ordOrdersWebBO.ajax?cmd=queryChildOrderLogByRole&timestamp="+getTimestamp,{"childTrackingNum":id},function(data){
						$scope.showData=data.o;
					});
				}
				
			};
			$scope.close = function(){
				//commonService.closeToParentTab(true);
				$scope.yy = false;
			};
			
		
		}
	    
	};
}]);

/**
 * 运单跟踪
 */
commonApp.directive("trackShow",['commonService',"$timeout",function(commonService,$timeout){
	return {
		restrict: 'E',
        scope: {
            "orderNo":"=orderno",
        },
		templateUrl : function(tElement,tAttrs){
	      	return 'trackDetail/trackDetail.html?ver='+tAttrs.ver;
	    },
        link: function($scope, elem, attrs) {
        	$scope.load = function(orderNo){
        		$scope.showData = {};
        		if($scope.orderNo != null && $scope.orderNo != undefined && $scope.orderNo != ""){
        			var getTimestamp=new Date().getTime();
        			commonService.postUrl("ordOrdersWebBO.ajax?cmd=queryLogByRole&timestamp="+getTimestamp,{"orderNo":orderNo},function(data){
        				
        				$scope.showData=data;
        			});
        		}
        	};
        	$scope.load($scope.orderNo);
        	$scope.$watch('orderNo',function(oldVal,newVal){
        		if(oldVal != newVal){
        			$timeout(function(){
        				$scope.load($scope.orderNo);
        			},500);
        		}
        	});
          }
	};
}]);



/**
 * 配载信息
 */
commonApp.directive("departInfo",['commonService',"$timeout",function(commonService,$timeout){
	return {
		restrict: 'E',
		scope: {
			"trackingNum":"=trackingNum",
		},
		templateUrl : function(tElement,tAttrs){
	      	return '../tracks/trackDetail/departTracks.html?ver='+tAttrs.ver;
	    },
		link: function($scope, elem, attrs) {
			$scope.load = function(trackingNum){
				$scope.showData = {};
				if($scope.trackingNum != null && $scope.trackingNum != undefined && $scope.trackingNum != ""){
					var getTimestamp=new Date().getTime();
					commonService.postUrl("ordOrdersWebBO.ajax?cmd=queryDepart&timestamp="+getTimestamp,{"trackingNum":trackingNum},function(data){
						$scope.departInfo=data.departInfo;
					});
				}
			};
			$scope.load($scope.trackingNum);
			$scope.$watch('trackingNum',function(oldVal,newVal){
				if(oldVal != newVal){
					$timeout(function(){
						$scope.load($scope.trackingNum);
					},500);
				}
			});
		}
	};
}]);


/**
 * 费用信息
 */
commonApp.directive("feeInfo",['commonService',"$timeout",function(commonService,$timeout){
	return {
		restrict: 'E',
		scope: {
			"orderNo":"=orderno",
		},
		templateUrl : function(tElement,tAttrs){
	      	return '../tracks/trackDetail/feeInfo.html?ver='+tAttrs.ver;
	    },
		link: function($scope, elem, attrs) {
			$scope.load = function(orderNo){
				$scope.showData = {};
				if($scope.orderNo != null && $scope.orderNo != undefined && $scope.orderNo != ""){
					var getTimestamp=new Date().getTime();
					commonService.postUrl("ordOrdersWebBO.ajax?cmd=queryFee&timestamp="+getTimestamp,{"orderNo":orderNo},function(data){
						$scope.feeInfo=data;
					});
				}
			};
			$scope.load($scope.orderNo);
			$scope.$watch('orderNo',function(oldVal,newVal){
				if(oldVal != newVal){
					$timeout(function(){
						$scope.load($scope.orderNo);
					},500);
				}
			});
		}
	};
}]);







	

