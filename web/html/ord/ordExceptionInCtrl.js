var view = getQueryString("view");
var id = getQueryString("id");
var edit = getQueryString("edit");
var showType = getQueryString("showType");
var ordExceptionInApp = angular.module("ordExceptionInApp", ['commonApp']);
ordExceptionInApp.controller("ordExceptionInCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var ord ={
			init:function(){
				this.userData();
				this.bindEvent();
				this.initStaticData();
				//保存类型 0：默认提交保存 1:责任方处理
				$scope.saveType = 0; 
				//查看逻辑
				if(view != null && view != undefined && view != "" && id != null && id != undefined && id !=""){
//					console.info("查看，修改");
					this.initView(id);
				}else if(edit != null && edit != undefined && edit != "" && id != null && id != undefined && id !=""){
//					console.info("修改。处理");
					this.initView(id);
				}else{
//					console.info("登记");
					this.initData();
				}
			},
			initView:function(id){
				//订单内容不能修改
				$scope.orderShow = true;
				commonService.postUrl("ordExceptionBO.ajax?cmd=queryDetail","id="+id,function(data){
					if(data != null && data != undefined && data != ""){
						$scope.data = data.ordExceptionInfo;
						$scope.data.type=data.ordExceptionInfo.type+"";
						$scope.img.initDate($scope.data.imageId); 
						if(showType==1 && $scope.data.status == 2){
							$scope.showDutyOrg=false;
						}else{
							$scope.showDutyOrg=true;
						}
						if($scope.data.trackingNum != null && $scope.data.trackingNum != undefined && $scope.data.trackingNum != ""){
							ord.getOrderInfo($scope.data.trackingNum);
						}
						if($scope.data.orgId == $scope.currOrgId && $scope.currOrgId == $scope.data.dutyOrgId){
							
							if($scope.data.status == 1){
								//已处理
								//显示责任意见
								$scope.ideaShow = true;
								//控制全局不能修改
								$scope.all = true;
								//显示异常单号
								$scope.idShow = true;
								//不显示保存按钮
								$scope.showSave = false;
								//责任意见不能修改
								$scope.isDeal = true;
							}else{
								//未处理
								//显示异常单号
								$scope.idShow = true;
								//控制全局不能修改
								$scope.all = true;
								//显示责任意见
								$scope.ideaShow = true;
								//显示保存
								$scope.showSave = true;
								//责任意见不能修改
								$scope.isDeal = false;
								$scope.saveType = 1;
								
								
							}
							
							
						}else if($scope.data.orgId == $scope.currOrgId){
							//发起方
//							console.info("发起方");
							//已处理
							if($scope.data.status == 1){
								//显示责任意见
								$scope.ideaShow = true;
								//控制全局是否修改
								$scope.all = true;
								//显示异常单号
								$scope.idShow = true;
								//不显示保存按钮
								$scope.showSave = false;
								//责任意见不能修改
								$scope.isDeal = true;
							}else if($scope.data.status == 2){
							//未处理 可以修改
								//显示异常单号
								$scope.idShow = false;
								//控制全局是否修改
								$scope.all = false;
								//不显示责任意见
								$scope.ideaShow = false;
								//显示保存
								$scope.showSave = true;
							}
						}else if($scope.currOrgId == $scope.data.dutyOrgId){
							//责任方
//							console.info("责任方");
							//已处理
							if($scope.data.status == 1){
								//显示责任意见
								$scope.ideaShow = true;
								//控制全局不能修改
								$scope.all = true;
								//显示异常单号
								$scope.idShow = true;
								//不显示保存按钮
								$scope.showSave = false;
								//责任意见不能修改
								$scope.isDeal = true;
							}else if($scope.data.status == 2){
							//未处理 可以修改
								
								//显示异常单号
								$scope.idShow = true;
								//控制全局不能修改
								$scope.all = true;
								//显示责任意见
								$scope.ideaShow = true;
								//显示保存
								$scope.showSave = true;
								//责任意见不能修改
								$scope.isDeal = false;
								$scope.saveType = 1;
							}
						}else{
							//第三方
//							console.info("第三方");
						}
						if($scope.all == true){
							$scope.img.isUpShow(false);
						}
					}
				});
			},
			initData:function(){
				//显示保存
				$scope.showSave = true;
				$scope.saveType = 0;
				$scope.orderShow = true;
				$scope.showDutyOrg=false;
			},
			bindEvent:function(){
				$scope.data = this.data;
				$scope.getOrderInfo = this.getOrderInfo;
				$scope.orderInfo = this.orderInfo;
				$scope.doSave = this.doSave;
				$scope.close = this.close;
				
			},
			orderInfo:{},
			getOrderInfo:function(id){
				if(id != null && id != undefined && id != ""){
					commonService.postUrl("orderInfoBO.ajax?cmd=queryOrderInfoDetail","trackingNum="+id+"&type=2",function(data){
						if(data != null && data != undefined && data != ""){
							if(data.items[0] != null&& data.items[0] != undefined && data.items[0] !=""){
								$scope.orderInfo = data.items[0].orderInfo;
								$scope.orderInfo.goodsName = data.items[0].goodsName;
								$scope.orderInfo.descRegionName = data.items[0].descRegionName;
							}
						}else{
							$scope.orderInfo = {};
							commonService.alert("请输入正确的运单号");
							return ;
						}
					});
				}else{
					commonService.alert("请输入运单号");
					return;
				}
				
				var url = "orderInfoBO.ajax?cmd=getRouteOrg";
				//$scope.query.trackingNum
				commonService.postUrl(url,"trackingNum="+id,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
						$scope.orgInfo=data.items;
						if(data.items!=null && data.items!=undefined && data.items!=""){
						   $scope.data.dutyOrgId=data.items[0].orgId;
						}
						/*$scope.orgInfo.unshift({orgId:-1,orgName:'全部'});
						$scope.query.dutyOrgId = -1;*/
		 	    	}
				});
			},
			//获取静态数据
			initStaticData:function(){
				//获取计费方式
				commonService.postUrl("staticDataBO.ajax?cmd=selectExceptionType","",function(data){
					if(data != null && data != undefined && data != ""){
						$scope.exceptionTypeData = data;
					}
				});
				/**责任网点查询*/
				/*var url = "staticDataBO.ajax?cmd=selectOrg";
				commonService.postUrl(url,"",function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
						$scope.orgInfo=data;
		 	    	}
				});*/
			},
			
			data:{},
			userData:function(){
				$scope.currOrgId=userInfo.orgId;
				$scope.currOrgName=userInfo.orgName;
			},
			doSave:function(){
				if($scope.saveType == 0){
					if($scope.data.dutyOrgId == null || $scope.data.dutyOrgId == undefined || $scope.data.dutyOrgId == ""){
						commonService.alert("请选择责任部门");
						return;
					}
					if($scope.data.type == null || $scope.data.type == undefined || $scope.data.type == ""){
						commonService.alert("请选择异常类型");
						return;
					}
					if($scope.data.notes == null || $scope.data.notes == undefined || $scope.data.notes == ""){
						commonService.alert("请填写异常描述");
						return;
					}
					$scope.data.imagePath = $scope.img.get().flowUrl;
					$scope.data.imageId = $scope.img.get().flowId;
					commonService.postUrl("ordExceptionBO.ajax?cmd=doSave",$scope.data,function(data){
						if(data!=null && data!=undefined && data!=""){
							$scope.data.id = data.id;
							commonService.alert("保存成功");
						}
					});
				}else if($scope.saveType == 1){
					if($scope.data.auditIdea == null || $scope.data.auditIdea == undefined || $scope.data.auditIdea == ""){
						commonService.alert("请填写责任意见");
						return;
					}
					$scope.paramsDeal = {};
					$scope.paramsDeal.id = $scope.data.id;
					$scope.paramsDeal.auditIdea = $scope.data.auditIdea;
					commonService.postUrl("ordExceptionBO.ajax?cmd=deal",$scope.paramsDeal,function(data){
						if(data!=null && data!=undefined && data!=""){
							commonService.alert("保存成功");
						}
					});
				}
			},
			close:function(){
				commonService.closeToParentTab(true);
			}
	};
	ord.init();
}]);