var view = getQueryString("view");
var id = getQueryString("id");
var edit = getQueryString("edit");

var ordCostChangeToApp = angular.module("ordCostChangeToApp", ['commonApp']);
ordCostChangeToApp.controller("ordCostChangeToCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var ord ={
			init:function(){
				this.addTransaction();
				$scope.query = {};
				this.bindEvent();
				this.queryOrg();
				
			},
			bindEvent:function(){
				$scope.addTransaction=this.addTransaction;
				$scope.doSave = this.doSave;
				$scope.openRemark = this.openRemark;
				$scope.close = this.close;
				$scope.data = this.data;
				$scope.ordMap=this.ordMap;
				$scope.queryOrg = this.queryOrg;
				$scope.doQueryOrder = this.doQueryOrder;
				$scope.closeView = this.closeView;
				$scope.clearModel = this.clearModel;
				$scope.toDetail = this.toDetail;
				$scope.data.trackingNum=id;
				$scope.historyClose=this.historyClose;
				$scope.selectAll=this.selectAll;
				$scope.items=this.items;
			},
			
			/**查询更多关闭后回显数据*/
			toDetail:function(o){
				$scope.closeView ();
				$scope.data.trackingNum = o.trackingNum;
				$scope.selectAll(o.trackingNum);
				
			},
			
			/**历史备注关闭点击事件*/
			historyClose : function(){
				$scope.showRemark=false;
				
			},
			
			/**查询回显*/
			selectAll : function(id){
				var param={"trackingNum":id};
				var url = "ordCastChangeBO.ajax?cmd=selectAll";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
						$scope.ordMap = data;
						return;
					}
				});
				return;
			},
			
			/**网点列表查询*/
			queryOrg:function(){
				var beginOrgId = userInfo.orgId;
				commonService.postUrl("routeBO.ajax?cmd=queryRoateTowards","beginOrgId="+beginOrgId+"&orgType=1",function(data){
					if(data != null && data != undefined && data != ""){
						$scope.orgInfo = data;
						if(data.items != null && data.items != undefined && data.items != ""){
							$scope.orgInfo.items.unshift({endOrgId:-1,endOwnerName:'全部'});
						}
						$scope.query.distributionOrgId = -1;
					}
				});
			},
			
			/**判断是新增还是修改*/
			addTransaction : function(){
				//新增
				if(id==""||id==null){
					$scope.isShow=true;
					$scope.dataAll=false;
					$scope.people=true;
				}else{
					//修改
					$scope.people=true;
					$scope.dataAll=true;
					$scope.isShow=false;
					this.selectAll(id);
				}
			},
			
			
			clearModel : function(){
			     $scope.query = {};
			     $scope.query.signState="-1";
				 $scope.query.transitOutgoingState="0";
				 if( $scope.des != undefined){
					 $scope.des.clear();
				 }
				 $scope.query.distributionOrgId = -1;
			},
			
			//查找更多页面关闭方法
			closeView : function(){
				$("#isShowSearch").hide();
		         $("#alertMsgIsShow").hide();
		         $("body").css('overflow','inherit');
		         $scope.clearModel();

			},
			
			//查找更多
			doQueryOrder : function(){
				$("#isShowSearch").show();
		        $("#alertMsgIsShow").show();
				$scope.paramsOrder = {};
				$scope.query.provinceId = $scope.des.chooseCityId;
				$scope.query.cityId = $scope.des.chooseRegionId;
				$scope.query.countyId = $scope.des.chooseCountyId;
				$scope.query.streetId = $scope.des.chooseStreetId;
				$scope.paramsOrder =  $scope.query;
				var url = "orderInfoBO.ajax?cmd=queryOrderInfoAndOut";
				$scope.paramsOrder.page=1;
				$scope.paramsOrder.count = 10;
				$timeout(function(){
					$scope.page.load({
								url:url,
								params:$scope.paramsOrder
							});
				},500);
			},
			
			data:{},
			ordMap:{},	
			items:{},
			
			/**历史详情*/
			openRemark : function(obj) {
				if(obj == 1 || obj == "1"){
					$scope.showRemark = true;
					var param={"orderId":$scope.ordMap.ordOrderInfo.orderId};
					var url = "orderInfoBO.ajax?cmd=queryRemarks";
					commonService.postUrl(url,param,function(data){
						if(data!=null && data!=undefined && data!=""){
							$scope.items = data;
						}
					});
				}else{
					$scope.showRemark = false;
				}
			},
			
			close:function(){
				commonService.closeToParentTab(true);
			},
			
			/**保存*/
			doSave:function(){
					if( $scope.ordMap.ordCastChangeInfo==null || $scope.ordMap.ordCastChangeInfo.notes == null ||  $scope.ordMap.ordCastChangeInfo.notes ==""){
						commonService.alert("请填写异动原因");
						return;
					}	                                                         
					if($scope.ordMap.ordCastChangeInfo==null ||  $scope.ordMap.ordCastChangeInfo.transactionMoneyDouble == null || $scope.ordMap.ordCastChangeInfo.transactionMoneyDouble == undefined  || $scope.ordMap.ordCastChangeInfo.transactionMoneyDouble =="" || $scope.ordMap.ordCastChangeInfo.transactionMoneyDouble == 0){
					   commonService.alert("请输入金额");
					   return;
				    }
					if(isNaN($scope.ordMap.ordCastChangeInfo.transactionMoneyDouble)){
						 commonService.alert("请输入正确的金额");
						  return;
					}
					
					if($scope.ordMap.ordCastChangeInfo==null  || $scope.ordMap.ordOrderInfo.trackingNum == null || $scope.ordMap.ordOrderInfo.trackingNum ==""){
						commonService.alert("请填写正确运单号");
						return;
					}
					
					$scope.ordMap.ordCastChangeInfo.trackingNum=$scope.ordMap.ordOrderInfo.trackingNum;
					$scope.ordMap.ordCastChangeInfo.orgIdName=$scope.ordMap.ordOrderInfo.orgIdName;
					$scope.ordMap.ordCastChangeInfo.orderId=$scope.ordMap.ordOrderInfo.orderId;
					
					var url = "ordCastChangeBO.ajax?cmd=doSaveOrUpdate";
					$scope.ordMap.ordCastChangeInfo;
					commonService.postUrl(url,$scope.ordMap.ordCastChangeInfo,function(data){
						var o = data.isOk;
						if("N"==o){
							commonService.alert("保存失败");
						}else{
							commonService.alert("保存成功");
							$timeout(function(){
								$scope.close();
							},2000);
						}
						
					});
			}
	};
	ord.init();
}]);