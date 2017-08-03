
/**
 * 费用
 */
commonApp.directive("costShow",['commonService',"$timeout",function(commonService,$timeout){
	return {
		restrict: 'E',
        scope: {
            "trackingNum":"=trackingnum",
        },
//      templateUrl: 'ordDetail/costShow.html',
		templateUrl : function(tElement,tAttrs){
	      	return 'ordDetail/costShow.html?ver='+tAttrs.ver;
	    },
        link: function($scope, elem, attrs) {
        	$scope.load = function(trackingNum){
        		$scope.porveInfos = {};
	        	if($scope.trackingNum != null && $scope.trackingNum != undefined && $scope.trackingNum != ""){
	        		var url = "acProveManageBO.ajax?cmd=doQueryAcAccountDtlByOrderId";
	        		commonService.postUrl(url,{"_ALLEXPORT":"1","trackingNum":trackingNum},function(data){
	        			$scope.porveInfos=data.items;
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
 * 专线签收详情
 */
commonApp.directive("zxSignShow",['commonService',"$timeout",function(commonService,$timeout){
	return {
		restrict: 'E',
        scope: {
            "trackingNum":"=trackingnum"
        },
		templateUrl : function(tElement,tAttrs){
	      	return 'ordDetail/signDtl.html?ver='+tAttrs.ver;
	    },
        link: function($scope, elem, attrs) {
        	$scope.load = function(trackingNum){
        		$scope.porveInfos = {};
	        	if($scope.trackingNum != null && $scope.trackingNum != undefined && $scope.trackingNum != ""){
	        		var url = "orderInfoBO.ajax?cmd=querySignDtl";
	        		commonService.postUrl(url,{"trackingNum":trackingNum},function(data){
	        			$scope.notify=data;
	        			
						commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{"codeType":"SIGN_TYPE"},function(data1){
							if(data.signType!=null && data.signType!=undefined && data.signType!=""){
								$scope.notify.signType = data.signType;
							}else{
								$scope.notify.signType = 1;
							}
							$scope.signTypeList = data1.items;
						});
						
						commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",{"codeType":"CERTIFICATE_TYPE"},function(data1){
							if(data.certificateType!=null && data.certificateType!=undefined && data.certificateType!=""){
								$scope.notify.certificateType = data.certificateType;
							}else{
								$scope.notify.certificateType = -1;
							}
							$scope.certificateTypeList = data1.items;
						});
						
						if(data.imageId!=null && data.imageId!=undefined && data.imageId!=""){
						  $scope.myFile.initDate(data.imageId);
						}
						$scope.myFile.isUpShow(false); 
						if($scope.notify.collectingMoney==undefined){
							$scope.notify.collectingMoney=0;
						}
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
 * 费用核销
 */
commonApp.directive("excverShow",['commonService',"$timeout",function(commonService,$timeout){
	return {
		restrict: 'E',
        scope: {
            "trackingNum":"=trackingnum",
        },
		templateUrl : function(tElement,tAttrs){
	      	return 'ordDetail/expenseVerificationDetail.html?ver='+tAttrs.ver;
	    },
        link: function($scope, elem, attrs) {
        	$scope.load = function(trackingNum){
        		$scope.exceptionInfo = {};
        		if($scope.trackingNum != null && $scope.trackingNum != undefined && $scope.trackingNum != ""){
        			var getTimestamp=new Date().getTime();
        			commonService.postUrl("orderInfoBO.ajax?cmd=queryOrdFreeAcc&timestamp="+getTimestamp,{"trackingNum":trackingNum},function(data){
        				$scope.showData=data;
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
 * 异动费用
 */
commonApp.directive("trancostShow",['commonService',"$timeout",function(commonService,$timeout){
	return {
		restrict: 'E',
        scope: {
            "trackingNum":"=trackingnum",
        },
		templateUrl : function(tElement,tAttrs){
	      	return 'ordDetail/transactionCostDetail.html?ver='+tAttrs.ver;
	    },
        link: function($scope, elem, attrs) {
        	$scope.load = function(trackingNum){
        		$scope.exceptionInfo = {};
        		if($scope.trackingNum != null && $scope.trackingNum != undefined && $scope.trackingNum != ""){
        			var getTimestamp=new Date().getTime();
        			commonService.postUrl("orderInfoBO.ajax?cmd=queryTranCostShowByTrackingNum&timestamp="+getTimestamp,{"trackingNum":trackingNum},function(data){
        				$scope.showData=data;
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
 * 异常
 */
commonApp.directive("excShow",['commonService',"$timeout",function(commonService,$timeout){
	return {
		restrict: 'E',
        scope: {
            "trackingNum":"=trackingnum",
        },
//      templateUrl: 'ordDetail/excDetail.html',
		templateUrl : function(tElement,tAttrs){
	      	return 'ordDetail/excDetail.html?ver='+tAttrs.ver;
	    },
        link: function($scope, elem, attrs) {
        	$scope.load = function(trackingNum){
        		$scope.exceptionInfo = {};
        		//$scope.questtionInfos = {};
        		if($scope.trackingNum != null && $scope.trackingNum != undefined && $scope.trackingNum != ""){
        			var getTimestamp=new Date().getTime();
        			commonService.postUrl("scheTaskBO.ajax?cmd=queryOrdBillDetailOfExp&timestamp="+getTimestamp,{"trackingNum":trackingNum},function(data){
        				$scope.showData=data;
//        				exceptionInfo
        			});/*
        			commonService.postUrl("ordQuestionBO.ajax?cmd=doQuery",{"page":"1","rows":"100","trackingNum":trackingNum},function(data){
        				$scope.questtionInfos=data.items;
        			});*/
        		}
        	};
        	$scope.load($scope.trackingNum);
        	$scope.$watch('trackingNum',function(oldVal,newVal){
        		if(oldVal != newVal){
        			$timeout(function(){
//        				console.info("异常更新");
        				$scope.load($scope.trackingNum);
        			},500);
        		}
        	});
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
            "trackingNum":"=trackingnum",
        },
//      templateUrl: 'ordDetail/trackDetail.html',
		templateUrl : function(tElement,tAttrs){
	      	return 'ordDetail/trackDetail.html?ver='+tAttrs.ver;
	    },
        link: function($scope, elem, attrs) {
        	$scope.load = function(trackingNum){
        		$scope.showData = {};
        		if($scope.trackingNum != null && $scope.trackingNum != undefined && $scope.trackingNum != ""){
        			var getTimestamp=new Date().getTime();
        			commonService.postUrl("scheTaskBO.ajax?cmd=queryOrdBillDetailOfLog&timestamp="+getTimestamp,{"trackingNum":trackingNum},function(data){
        				$scope.showData=data;
//        				opLog
        			});
        		}
        	};
        	$scope.load($scope.trackingNum);
        	$scope.$watch('trackingNum',function(oldVal,newVal){
        		if(oldVal != newVal){
        			$timeout(function(){
//        				console.info("运单更新");
        				$scope.load($scope.trackingNum);
        			},500);
        		}
        	});
        	$scope.update = function(logId){
        		
        		var ff = $("#"+logId);
        		var hh = ff.position().top+53+"px";		
        		$("#xgdd").css("top",hh);
        		document.getElementById("xgdd").style.display="block";
        		
        		$scope.updateOrdBusi = [];
        		//document.getElementById('xgdd').style.display='block';
        		
    			var url = "scheTaskBO.ajax?cmd=getOrdLogAndOrdBusi";
    			commonService.postUrl(url,{"logId":logId,time:new Date().getTime()},function(data){
    				if(data != null && data != "" && data != undefined){
    					$scope.updateOrdBusi = data.updateOrdLog;
    				}
    			});
        	};
        }
	};
}]);

/**
 * 安装信息
 */
commonApp.directive("installShow",['commonService',"$timeout",function(commonService,$timeout){
	return {
		restrict: 'E',
		scope: {
			"trackingNum":"=trackingnum",
		},
//		templateUrl: 'ordDetail/installDetail.html',
		templateUrl : function(tElement,tAttrs){
	      	return 'ordDetail/installDetail.html?ver='+tAttrs.ver;
	    },
		link: function($scope, elem, attrs) {
			$scope.load = function(trackingNum){
				$scope.showData = {};
				if($scope.trackingNum != null && $scope.trackingNum != undefined && $scope.trackingNum != ""){
					var getTimestamp=new Date().getTime();
					commonService.postUrl("scheTaskBO.ajax?cmd=queryOrdBillDetailOfTask&timestamp="+getTimestamp,{"trackingNum":trackingNum},function(data){
						$scope.showData=data;
//						taskInfo
					});
				}
			};
			$scope.load($scope.trackingNum);
			$scope.$watch('trackingNum',function(oldVal,newVal){
				if(oldVal != newVal){
					$timeout(function(){
//						console.info("安装更新");
						$scope.load($scope.trackingNum);
					},500);
				}
			});
		}
	};
}]);
/**
 * 上传底单图片
 */
commonApp.directive("orderPicShow",['commonService',"$timeout",function(commonService,$timeout){
	return {
		restrict: 'E',
		scope: {
			"trackingNum":"=trackingnum"
		},
		templateUrl : function(tElement,tAttrs){
	      	return 'ordDetail/orderPicDetail.html?ver='+tAttrs.ver;
	    },
		link: function($scope, elem, attrs) {
			$scope.load = function(trackingNum){
				$scope.showData = {};
				$scope.showSign = false;
				if($scope.trackingNum != null && $scope.trackingNum != undefined && $scope.trackingNum != ""){
					var getTimestamp=new Date().getTime();
					commonService.postUrl("ordUploadPicBO.ajax?cmd=queryOrderPic&timestamp="+getTimestamp+"1",{"trackingNum":trackingNum},function(data){
						$scope.showData=data;
						if(data.flowId!=null && data.flowId!=undefined && data.flowId!=''){
							var flowId=data.flowId.split(",");
							if(flowId.length==5){
								$scope.isShow=false;
							}
							for(var i=0;i<flowId.length;i++){
								eval("$scope.orderPicCard"+(i+1)+".initDate("+flowId[i]+")");
								eval( "$scope.orderPicCard"+(i+1)+".isUpShow(false)"); 
							}
							$scope.showSign = true;
						}else{
						    //如果不存在可以修改
							$scope.orderPicCard1.isUpShow(true);
							$scope.orderPicCard2.isUpShow(true); 
							$scope.orderPicCard3.isUpShow(true);
							$scope.orderPicCard4.isUpShow(true); 
							$scope.orderPicCard5.isUpShow(true); 
						}
					});
				}
			};
			//保存图片
			$scope.doSavePic = function(){
				var flowId='';
				var orderId='';
				if($scope.trackingNum != null && $scope.trackingNum != undefined && $scope.trackingNum != ""){
					if($scope.orderPicCard1.get().flowId!=null && $scope.orderPicCard1.get().flowId!=undefined && $scope.orderPicCard1.get().flowId!=''){
						flowId=$scope.orderPicCard1.get().flowId;
					}
					if($scope.orderPicCard2.get().flowId!=null && $scope.orderPicCard2.get().flowId!=undefined && $scope.orderPicCard2.get().flowId!=''){
						if(flowId!=''){
							flowId+=","+$scope.orderPicCard2.get().flowId;
						}else{
							flowId+=$scope.orderPicCard2.get().flowId;
						}
					}
					if($scope.orderPicCard3.get().flowId!=null && $scope.orderPicCard3.get().flowId!=undefined && $scope.orderPicCard3.get().flowId!=''){
						if(flowId!=''){
							flowId+=","+$scope.orderPicCard3.get().flowId;
						}else{
							flowId+=$scope.orderPicCard3.get().flowId;
						}
					}
					if($scope.orderPicCard4.get().flowId!=null && $scope.orderPicCard4.get().flowId!=undefined && $scope.orderPicCard4.get().flowId!=''){
						if(flowId!=''){
							flowId+=","+$scope.orderPicCard4.get().flowId;
						}else{
							flowId+=$scope.orderPicCard4.get().flowId;
						}
					}
					if($scope.orderPicCard5.get().flowId!=null && $scope.orderPicCard5.get().flowId!=undefined && $scope.orderPicCard5.get().flowId!=''){
						if(flowId!=''){
							flowId+=","+$scope.orderPicCard5.get().flowId;
						}else{
							flowId+=$scope.orderPicCard5.get().flowId;
						}
					}
				}
				//查询运单信息
				var param = {"trackingNum":trackingNum};
				var url = "orderInfoBO.ajax?cmd=queryOrderInfo";
				commonService.postUrl(url,param,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
						orderId=data.items[0].orderId; 
						//保存运单图
						var param = {"orderId":orderId,"flowId":flowId,"trackingNum":trackingNum};
						var url = "ordUploadPicBO.ajax?cmd=doSaveOrderPic";
						commonService.postUrl(url,param,function(data){
							if(data!=null && data!=undefined && data!=""){
								 alert("上传成功!");
								 //重新加载图片
								 $scope.load($scope.trackingNum);
				 	    	}
						});	
		 	    	}
				});
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
 * 签收信息
 */
commonApp.directive("signShow",['commonService',"$timeout",function(commonService,$timeout){
	return {
		restrict: 'E',
		scope: {
			"trackingNum":"=trackingnum",
			 "showSign":"=showSign"
		},
//		templateUrl: 'ordDetail/signDetail.html',
		templateUrl : function(tElement,tAttrs){
	      	return 'ordDetail/signDetail.html?ver='+tAttrs.ver;
	    },
		link: function($scope, elem, attrs) {
			$scope.load = function(trackingNum){
				$scope.showData = {};
				$scope.showSign = false;
				if($scope.trackingNum != null && $scope.trackingNum != undefined && $scope.trackingNum != ""){
					var getTimestamp=new Date().getTime();
					commonService.postUrl("scheTaskBO.ajax?cmd=queryOrdBillDetailOfSign&timestamp="+getTimestamp+"1",{"trackingNum":trackingNum},function(data){
						$scope.showData=data;
						if(data.signPic!=null&&data.signPic!=undefined&&data.signPic!=''){
							var flowId=data.signPic.split(",");
							for(var i=0;i<flowId.length;i++){
								eval("$scope.idenCard"+(i+1)+".initDate("+flowId[i]+")");
							}
							$scope.idenCard1.isUpShow(false);
							$scope.idenCard2.isUpShow(false); 
							$scope.idenCard3.isUpShow(false);
							$scope.idenCard4.isUpShow(false); 
							$scope.idenCard5.isUpShow(false); 
							$scope.showSign = true;
						}else{
						    //如果不存在也不能修改
							$scope.idenCard1.isUpShow(false);
							$scope.idenCard2.isUpShow(false); 
							$scope.idenCard3.isUpShow(false);
							$scope.idenCard4.isUpShow(false); 
							$scope.idenCard5.isUpShow(false); 
						}
					});
				}
			};
			$scope.load($scope.trackingNum);
			$scope.$watch('trackingNum',function(oldVal,newVal){
				if(oldVal != newVal){
					$timeout(function(){
//						console.info("签收更新");
						$scope.load($scope.trackingNum);
					},500);
				}
			});
		}
	};
}]);

/**
 * 配载信息&中转
 */
commonApp.directive("departShow",['commonService',"$timeout",function(commonService,$timeout){
	return {
		restrict: 'E',
		scope: {
			"trackingNum":"=trackingnum",
		},
//		templateUrl: 'ordDetail/departDetail.html',
		templateUrl : function(tElement,tAttrs){
		      	return 'ordDetail/departDetail.html?ver='+tAttrs.ver;
		},
		link: function($scope, elem, attrs) {
			$scope.load = function(trackingNum){
				if(trackingNum != null && trackingNum != undefined && trackingNum != ""){
					commonService.postUrl("orderInfoBO.ajax?cmd=QueryOrderDepar","trackingNum="+trackingNum,function(data){
						if(data != null && data != undefined && data != ""){
							$scope.departInfo = data;
						}
					});
					commonService.postUrl("transferBO.ajax?cmd=getOutgoing","trackingNum="+trackingNum,function(data){
						if(data != null && data != undefined && data != ""){
							$scope.outgoing = data;
							$scope.all = true;
						}
					});
				}
			};
			$scope.load($scope.trackingNum);
			$scope.$watch('trackingNum',function(oldVal,newVal){
				if(oldVal != newVal){
					$timeout(function(){
//						console.info("配载更新");
						$scope.load($scope.trackingNum);
					},500);
				}
			});
		}
	};
}]);
	/**
	 * 修改运单信息
	 */
	commonApp.directive("updateShow",['commonService',"$timeout",function(commonService,$timeout){
		return {
			restrict: 'E',
			scope: {
				"trackingNum":"=trackingnum",
			},
			templateUrl : function(tElement,tAttrs){
			      	return 'ordDetail/ordBilingUpdate.html?ver='+tAttrs.ver;
			},
			link: function($scope, elem, attrs) {
				$scope.load = function(trackingNum){
					if(trackingNum != null && trackingNum != undefined && trackingNum != ""){
						commonService.postUrl("scheTaskBO.ajax?cmd=queryOrdBusiLog","trackingNum="+trackingNum,function(data){
							if(data != null && data != undefined && data != ""){
								$scope.showData = data;
							}
						});
					}
				};
				$scope.load($scope.trackingNum);
				$scope.$watch('trackingNum',function(oldVal,newVal){
					if(oldVal != newVal){
						$timeout(function(){
							console.info("运单修改");
							$scope.load($scope.trackingNum);
						},500);
					}
				});
			}
		};
	
}]);
	
	/**
	 * 售后跟踪
	 */
	commonApp.directive("aftersalesShow",['commonService',"$timeout",function(commonService,$timeout){
		return {
			restrict: 'E',
	        scope: {
	            "trackingNum":"=trackingnum",
	        },
			templateUrl : function(tElement,tAttrs){
		      	return 'ordDetail/afterSalesTrackingDetail.html?ver='+tAttrs.ver;
		    },
	        link: function($scope, elem, attrs) {
	        	$scope.load = function(trackingNum){
	        		$scope.exceptionInfo = {};
	        		if($scope.trackingNum != null && $scope.trackingNum != undefined && $scope.trackingNum != ""){
	        			var getTimestamp=new Date().getTime();
	        			commonService.postUrl("orderInfoBO.ajax?cmd=queryOrdSalesByTrackingNum&timestamp="+getTimestamp,{"trackingNum":trackingNum},function(data){
	        				$scope.showData=data;
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

