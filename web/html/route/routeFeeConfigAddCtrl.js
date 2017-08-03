var view = getQueryString("view");
var id = getQueryString("id");
var edit = getQueryString("edit");
var routeFeeConfigAddApp = angular.module("routeFeeConfigAddApp", ['commonApp']);
routeFeeConfigAddApp.controller("routeFeeConfigAddCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var ord ={
			init:function(){
				this.userData();
				this.bindEvent();
				this.initStaticData();
				//保存类型 0：默认提交保存 1:责任方处理
				$scope.saveType = 0; 
				this.initData();
				this.queryFacePoints();
			},
			initData:function(){
				//显示保存
				$scope.showSave = true;
				$scope.saveType = 0;
				$scope.orderShow = true;
			},
			bindEvent:function(){
				$scope.data = this.data;
				$scope.orderInfo = this.orderInfo;
				$scope.doSave = this.doSave;
				$scope.close = this.close;
				$scope.queryFacePoints = this.queryFacePoints;
				$scope.changeLimitTip = this.changeLimitTip;
			},
			orderInfo:{},
			//获取静态数据
			initStaticData:function(){
				//获取计费方式
				commonService.postUrl("staticDataBO.ajax?cmd=selectExceptionType","",function(data){
					if(data != null && data != undefined && data != ""){
						$scope.exceptionTypeData = data;
						$("#lowerLimit").val("重量:单位千克");
						$("#topLimit").val("重量:单位千克");
					}
				});
				/**责任网点查询*/
				var url = "staticDataBO.ajax?cmd=selectOrgByRole";
				commonService.postUrl(url,"",function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
						$scope.orgInfo=data;
						$scope.data.startOrgid=data[0].orgId;
		 	    	}
				});
				/**责任root网点查询*/
				var url = "staticDataBO.ajax?cmd=selectRootOrgByRole";
				commonService.postUrl(url,"",function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
						$scope.rootOrgInfo=data;
						$scope.data.tenantId=data[0].orgId;
					}
				});
				/**费用查询*/
				var url = "staticDataBO.ajax?cmd=queryAcFeeConfig";
				commonService.postUrl(url,{'feeId':'1'},function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
						$scope.acFeeConfig=data;
						$scope.data.feeId=data[0].feeId;
					}
				});
			},
			data:{},
			userData:function(){
				$scope.currOrgId=userInfo.orgId;
				$scope.currOrgName=userInfo.orgName;
			},
			doSave:function(){
					var formulastr = $scope.data.formula;
					if(formulastr!=undefined&&formulastr!=""){
						formulastr = formulastr.replace(/\w/g, "1");
						try
						{
							eval(formulastr);
						}
						catch (e)
						{
							commonService.alert("计算公式出错，请检查");
							return false;
						} 
					}
					commonService.postUrl("routeFeeConfigManageBO.ajax?cmd=doSave",$scope.data,function(data){
						if(data!=null && data!=undefined && data!=""){
							$scope.data.id = data.id;
							commonService.alert("保存成功");
						}
					});
			},
			close:function(){
				commonService.closeToParentTab(true);
			},			
			queryFacePoints:function(){
				var url = "routeFeeConfigManageBO.ajax?cmd=queryLinePoint";
				$timeout(function(){
				commonService.postUrl(url,$scope.data,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
						$scope.facePoints=data;
						if($scope.facePoints.items.length>0){
						    $scope.data.endOrgid=$scope.facePoints.items[0].endOrgId;
						}
					}
				});
				},500);
			},
			changeLimitTip:function(countType){
				if(countType=="1"){
					$("#lowerLimit").val("重量:单位千克");
					$("#topLimit").val("重量:单位千克");
				}
				else
				{
					$("#lowerLimit").val("体积:单位立方米");
					$("#topLimit").val("体积:单位立方米");
				}
			}
	};
	ord.init();
}]);