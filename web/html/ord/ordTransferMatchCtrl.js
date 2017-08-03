var ordTransferMatchApp = angular.module("ordTransferMatchApp", ['commonApp']);
ordTransferMatchApp.controller("ordTransferMatchCtrl", ["$scope","commonService","$timeout",'$filter',function($scope,commonService,$timeout,$filter) {
	var ordTransferMatch={
		init:function(){
			this.bindEvent();
			$scope.des={};
			$scope.doQuery();
		},
		bindEvent:function(){
			$scope.query=this.query;
			$scope.doQuery = this.doQuery;
			$scope.clear = this.clear;
			$scope.toDetailAllInfo = this.toDetailAllInfo;
			$scope.toTransfer=this.toTransfer;
		},
		query:{
			
		},
		toDetailAllInfo: function(obj){
			window.open("/ord/ordBillingDetail.html?view=1&orderId="+obj.orderId+"&trackingNum="+obj.trackingNum);
		},
		toTransfer:function(){
			var selectedValue =$scope.page.getSelectData();
			if(selectedValue.length<=0){
				commonService.alert("请选择一条运单信息");
				return false;
			}
			var orderList = new Array();
			var trackingNumArr=new Array();
			for(var index in selectedValue){
				trackingNumArr.push(selectedValue[index].trackingNum);
				var obj = {};
				obj.trackingNum = selectedValue[index].trackingNum;
				obj.transferValue =selectedValue[index].transferValue;
				obj.orderId = selectedValue[index].orderId;
				obj.orgId = selectedValue[index].orgId;
				obj.outgoingTime =document.getElementById(selectedValue[index].idNo+"time").value;
				obj.outgoingTrackingNum = selectedValue[index].outgoingTrackingNum;
				obj.transportContract=selectedValue[index].transportContract;
				orderList.push(obj);
			}
			var nary=trackingNumArr.sort(); 
			for(var i in trackingNumArr){ 
				if (nary[i]==nary[parseInt(i)+1]){ 
					commonService.alert("运单号["+nary[i]+"]相同的只能选择一条进行确认中转!");
					return false;
				} 
			} 
			var param ={};
			param.orderList= orderList;
			commonService.confirm("请确认["+orderList[0].trackingNum+"]的等运单的中转费,外发单号,中转时间,运输合同等信息是否录入正确,总共是【"+orderList.length+"】票,是否继续保存？", function(){
				window.top.showLoad();
				var url ="transferBO.ajax?cmd=matchSaveTransfer"
					commonService.postUrl(url,{"list":angular.toJson(param.orderList,true)},function(data){
						if(data != null && data != undefined && data != ""){
							window.top.hideLoad();
							commonService.alert("操作成功");
							$scope.doQuery();
						}
					});
				$timeout(function(){
					window.top.hideLoad();
				},700);
				
			});
		},
		/**订单列表查询*/
		doQuery:function(){
			var url = "transferBO.ajax?cmd=matchTransfer";
			$scope.query.beginTime = document.getElementById("beginTime").value;
			$scope.query.endTime = document.getElementById("endTime").value;
			$scope.query.provinceId = $scope.des.chooseCityId;
			$scope.query.cityId = $scope.des.chooseRegionId;
			$scope.query.countyId = $scope.des.chooseCountyId;
			$scope.query.streetId = $scope.des.chooseStreetId;
			$scope.tableCallBack=function(data){
//				commonService.refreshPageContentHeight($scope.page.data.items.length, 663, 270);
				setContentHegthDelay();
				$timeout(function(){
					for(var index in data.items){
						var preData=$filter('date')(new Date(new Date().getTime() - 24*60*60*1000),'yyyy-MM-dd');
						data.items[index].outgoingTime=preData;
						$("#"+data.items[index].idNo+"time").val(preData);
						//document.getElementById().value=$filter('date')(new Date(),'yyyy-MM-dd');
					}
				},700);
			}
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:ordTransferMatch.query,
							callBack:"$scope.tableCallBack"
						});
			},500);
		},
		/**清空查询条件*/
		clear:function(){
			document.getElementById("beginTime").value='';
			document.getElementById("endTime").value='';
			$scope.query.beginTime='';
			$scope.query.endTime='';
			$scope.query.trackingNum="";
			$scope.query.consignorBill = "";
			$scope.query.consigneeBill = "";
			$scope.query.consignorName = "";
			$scope.query.consigneeName = "";
			$scope.query.orgName = "";
		}

	};
	
	ordTransferMatch.init();
}]);