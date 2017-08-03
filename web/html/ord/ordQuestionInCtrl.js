var view = getQueryString("view");
var id = getQueryString("id");
var edit = getQueryString("edit");
var ordQuestionInApp = angular.module("ordQuestionInApp", ['commonApp']);
ordQuestionInApp.controller("ordQuestionInCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var ord ={
			init:function(){
				this.userData();
				this.bindEvent();
				this.queryOrg();
				this.queryType();
				
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
			bindEvent:function(){
				$scope.doSave = this.doSave;
				$scope.close = this.close;
				$scope.data = this.data;
			},
			data:{},
			initView:function(id){
				commonService.postUrl("ordQuestionBO.ajax?cmd=queryDetail","id="+id,function(data){
					if(data != null && data != undefined && data != ""){
						$scope.data = data.ordQuestionInfo;
						$scope.data.type=data.ordQuestionInfo.type+"";
						$scope.data.amount= data.ordQuestionInfo.amount/100;
//						console.info($scope.data);
						//判断当前用户是责任方还是登记方
						if($scope.data.orgId == $scope.currOrgId && $scope.data.dutyOrgId == $scope.currOrgId){
							if($scope.data.status == 1){
								//已处理
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
								
								$scope.audit = true;
								//不让修改审核内容
								$scope.auditEdit = true;
								
							}else{
								//未处理可以处理
								$scope.audit = true;
								//控制全局不能修改
								$scope.all = true;
								//显示保存
								$scope.showSave = true;
								$scope.auditEdit = false;
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
								//控制全局是否修改
								$scope.all = false;
								//审核
								$scope.audit = false;
								//显示保存
								$scope.showSave = true;
								
							}
						}else if($scope.currOrgId == $scope.data.dutyOrgId){
							//责任方
//							console.info("责任方");
							//已处理
							if($scope.data.status == 1){
								$scope.audit = true;
								//控制全局不能修改
								$scope.all = true;
								//不显示保存按钮
								$scope.showSave = false;
								//不让修改审核内容
								$scope.auditEdit = true;
							}else if($scope.data.status == 2){
							//未处理 可以修改
								$scope.audit = true;
								//控制全局不能修改
								$scope.all = true;
								//显示保存
								$scope.showSave = true;
								$scope.auditEdit = false;
								$scope.saveType = 1;
							}
						}else{
							//第三方
//							console.info("第三方");
						}
						
					}
				});
			},
			initData:function(){
				$scope.data.orgIdName = userInfo.orgName ;
				$scope.data.orgId = userInfo.currOrgId;
				//控制全局是否修改
				$scope.all = false;
				//审核
				$scope.audit = false;
				//不显示保存按钮
				$scope.showSave = true;
			},
			userData:function(){
				$scope.currOrgId=userInfo.orgId;
				$scope.currOrgName=userInfo.orgName;
			},
			/**网点列表查询*/
			queryOrg:function(){
				var url = "staticDataBO.ajax?cmd=selectOrg";
				commonService.postUrl(url,"",function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
						$scope.orgInfo=data;
		 	    	}
				});
			},
			queryType:function(){
				var url = "staticDataBO.ajax?cmd=selectQuestionType";
				commonService.postUrl(url,"",function(data){
					if(data!=null && data!=undefined && data!=""){
						$scope.questionTypeData=data;
		 	    	}
				});
			},
			close:function(){
				commonService.closeToParentTab(true);
			},
			doSave:function(){
				if($scope.saveType ==0){
					if($scope.data.trackingNum == null || $scope.data.trackingNum == undefined || $scope.data.trackingNum ==""){
						commonService.alert("请输入运单号");
						return;
					}
					if($scope.data.dutyOrgId == null || $scope.data.dutyOrgId == undefined || $scope.data.dutyOrgId ==""){
						commonService.alert("请输入接收网点");
						return;
					}
					if($scope.data.type == null || $scope.data.type == undefined || $scope.data.type ==""){
						commonService.alert("请输入问题类型");
						return;
					}
					if($scope.data.notes == null || $scope.data.notes == undefined || $scope.data.notes ==""){
						commonService.alert("请输入问题描述");
						return;
					}
					if($scope.data.amount == null || $scope.data.amount == undefined || $scope.data.amount ==""){
						commonService.alert("请输入协商金额");
						return;
					}
					$scope.data.amount=parseInt($scope.data.amount*100);
					var url = "ordQuestionBO.ajax?cmd=doSave";
					commonService.postUrl(url,$scope.data,function(data){
						//成功执行
						if(data!=null && data!=undefined && data!=""){
							$scope.data.id = data.id;
							$scope.data.amount="";
							commonService.alert("保存成功");
							
						}
					},function(response){
						if($scope.data.amount!=null&& $scope.data.amount!=undefined && $scope.data.amount!=""){
						     $scope.data.amount=parseInt($scope.data.amount/100);
						     commonService.alert(response.message);
						}
					});
				}else if($scope.saveType == 1){
					//处理业务
					if($scope.data.auditStatus == null || $scope.data.auditStatus== undefined || $scope.data.auditStatus==""){
						commonService.alert("请选择审核态度");
						return;
					}
					if($scope.data.auditIdea == null || $scope.data.auditIdea== undefined || $scope.data.auditIdea==""){
						commonService.alert("请输入审核意见");
						return;
					}
					var url = "ordQuestionBO.ajax?cmd=deal";
					$scope.param = {};
					$scope.param.id=$scope.data.id;
					$scope.param.auditStatus = $scope.data.auditStatus;
					$scope.param.auditIdea = $scope.data.auditIdea;
//					$scope.param.id=$scope.data.id;
					commonService.postUrl(url,$scope.param,function(data){
						//成功执行
						if(data!=null && data!=undefined && data!=""){
							commonService.alert("审核成功");
						}
					});
				}
			}
	};
	ord.init();
}]);