        var routeInfoApp = angular.module("routeInfoApp", ['commonApp']);
        routeInfoApp.controller("routeInfoCtrl", ["$scope","commonService",function($scope,commonService) {
        	var route={
        			init:function(){
        				this.toView();
        				this.bindEvent();
        			},
        			bindEvent:function(){
        			    $scope.toView=this.toView;
        			    $scope.close=this.close;
        			},
        			close:function(){
                		commonService.closeToParentTab(true);
                	},
                	toView:function(){
                		var towardsId=getQueryString("towardsId");
                		if(towardsId==null||towardsId==undefined|| towardsId==''){
                			commonService.alert("线路走向id为空!");
                			return false;
                		}
                		var queryString="towardsId="+towardsId;
                		var url="routeBO.ajax?cmd=toView";
						commonService.postUrl(url,queryString,function(data){
							$scope.routeDetail= data;
							$scope.towardsId=towardsId;
						});
                	},
        	}
        	route.init();
        }]);
