function changeTabCallback(){
	var appElement = document.querySelector('[ng-controller=ordSpellCtrl]');
	var scope=angular.element(appElement).scope();
	scope.doQuery();
	scope.$apply();
}

var ordSpellApp = angular.module("ordSpellApp", ['commonApp']);
var consigneeBill = getQueryString("consigneeBill");
ordSpellApp.controller("ordSpellCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var ordSpell = {
		init:function(){
			this.bindEvent();
			$scope.query.consigneeBill = consigneeBill;
			this.doQuery();
		},
		bindEvent:function(){
			$scope.query = this.query;
			$scope.doQuery = this.doQuery;
			$scope.toView = this.toView;
			$scope.delSpellOrder = this.delSpellOrder;
			$scope.modifySpellOrder = this.modifySpellOrder;
			
			$scope.clear = this.clear;
		},
		//查看拼单
		toView:function(){
			var orderId = '';
			var trackingNum = '';
			var  ordArray = $scope.page.getSelectData();
            if(ordArray.length == 0){
            	commonService.alert("请至少选择一条拼单中运单信息!");
				return false;
            }
            if(ordArray.length > 1){
				commonService.alert("只能选择一条拼单中的运单信息!");
				return false;
			}
            orderId = ordArray[0].orderId;
            trackingNum = ordArray[0].trackingNum;
			commonService.openTab(orderId + 951951951312,"拼单详情","/ord/ordBillingDetail.html?view=1&orderId="+orderId+"&trackingNum="+trackingNum+"&type=3&ver=${ver}");
		},
		//移除运单
		delSpellOrder : function(){
			var orderId = '';
			var trackingNum = '';
			var  ordArray = $scope.page.getSelectData();
            if(ordArray.length == 0){
            	commonService.alert("请选择一条拼单中运单信息!");
				return false;
            }
            if(ordArray.length > 1){
				commonService.alert("只能选择一条拼单中的运单信息!");
				return false;
            }
            
            orderId = ordArray[0].orderId;
            trackingNum = ordArray[0].trackingNum;
            var url = "orderInfoBO.ajax?cmd=delOrderInfo";
            var param = {};
            param.orderId = orderId;
            param.trackingNum = trackingNum;
			commonService.confirm("是否删除拼单中的运单数据?",function(){
				commonService.postUrl(url,param,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
							commonService.alert("移除运单成功!");
							$scope.doQuery();
		 	    	}
				});
			});
		},
		//拼单修改
		modifySpellOrder : function(trackingNum,orderId){
			var orderId = '';
			var  ordArray = $scope.page.getSelectData();
            if(ordArray.length == 0){
            	commonService.alert("请至少选择一条拼单中运单信息!");
				return false;
            }
            if(ordArray.length > 1){
				commonService.alert("只能选择一条拼单中的运单信息!");
				return false;
			}
            orderId = ordArray[0].orderId;
			commonService.openTab(orderId + 9519519512,"拼单修改","/ord/billing.html?edit=1&orderId="+orderId+"&isSpell=true");
		},
		query:{
			page:1,
			rows: 10
		},

		/**列表查询*/
		doQuery : function(){
				$scope.query.beginTime = document.getElementById("beginTime").value;
				$scope.query.endTime = document.getElementById("endTime").value;
				$scope.query.orderState = 7;
				var url = "orderInfoBO.ajax?cmd=queryOrderInfo";
				$scope.query.page = 1;
				$scope.query.rows = 10;
				$scope.tableCallBack = function(){
					setContentHegthDelay();
				};
				$timeout(function(){
					$scope.page.load({
								url:url,
								params:$scope.query,
								callBack:"$scope.tableCallBack"
							});
				},500);
			
		},
		/**清空查询条件*/
		clear:function(){
			$scope.query = {};
			document.getElementById("beginTime").value='';
			document.getElementById("endTime").value='';
		},
		 
	};
	
	ordSpell.init();
}]);


