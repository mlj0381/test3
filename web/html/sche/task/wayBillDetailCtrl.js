var wayBillDetailApp = angular.module("wayBillDetailApp", ['commonApp']);
wayBillDetailApp.controller("wayBillDetailCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var wayBillDetail={
		init:function(){
			this.bindEvent();
			var orderId=getQueryString("orderId");
			var taskId=getQueryString("taskId");
			if(orderId!=null && orderId!=undefined && orderId!=''){
				$scope.toView(orderId,taskId);
			}
		},
		bindEvent:function(){
			$scope.param = this.param;
			$scope.toView = this.toView;
			$scope.tabShow=this.tabShow;
			$scope.close=this.close;
		},
		toView:function(orderId,taskId){
			var param={"orderId":orderId,"taskId":taskId};
			var url="scheTaskBO.ajax?cmd=queryWayBillDetail";
			commonService.postUrl(url,param,function(data){
				$scope.showData=data;
				if(data.signInfo!=null&&data.signInfo!=undefined&&data.signInfo!=''){
					var flowId=data.signInfo.signPic.split(",");
					for(var i=0;i<flowId.length;i++){
						 eval("$scope.idenCard"+(i+1)+".initDate("+flowId[i]+")");
						 
						 
					}
					$scope.idenCard1.isUpShow(false);
					$scope.idenCard2.isUpShow(false); 
					$scope.idenCard3.isUpShow(false);
					$scope.idenCard5.isUpShow(false); 
					$scope.idenCard4.isUpShow(false); 
				}
				setContentHegthDelay();
			});
		},
		tabShow:function(name,cursel,n){
			 for(i=0;i<n;i++){
				  var menu=document.getElementById(name+i);
				  var con=document.getElementById("con_"+name+"_"+i);
				  menu.className=i==cursel?"hover":"";
				  con.style.display=i==cursel?"block":"none";
				 }
		},
		close:function(){
	    		commonService.closeToParentTab();
		}
	};
	wayBillDetail.init();
}]);
