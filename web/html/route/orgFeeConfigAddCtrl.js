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
				$scope.queryAreas = this.queryAreas;
				$scope.changeCountType = this.changeCountType;
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
						$scope.data.orgId=data[0].orgId;
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
				commonService.postUrl(url,"",function(data){
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
					commonService.postUrl("orgFeeConfigManageBO.ajax?cmd=doSave",$scope.data,function(data){
						if(data!=null && data!=undefined && data!=""){
							$scope.data.id = data.id;
							commonService.alert("保存成功");
						}
					});
			},
			close:function(){
				commonService.closeToParentTab(true);
			},
			queryAreas:function(){
				var url = "webAcAreaFeeConfigBO.ajax?cmd=doQueryArea";
				commonService.postUrl(url,$scope.data,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
						$scope.areas=data;
					}
				});
			},
			changeCountType:function(feeId,orgId,colectType,countType){
				//区域相关费用，需要特殊处理,,data.colectType为到达网点
				if((feeId=="2")&&(orgId!=undefined&&orgId!="")){
					$("#area_id_display").show();
				}
				else
				{
					$("#area_id_display").hide();
				}
				if(feeId=="5"){
					$("#building_type_id").show();
				}
				else
				{
					$("#building_type_id").hide();
				}
				if(feeId!=null && feeId!=undefined && feeId!=""){
				    $scope.changeLimitTip(feeId,countType);
				}
			},
			changeLimitTip:function(feeId,countType){
				if(feeId!="5"){
					if(countType=="1"){
						$("#lowerLimit").val("重量:单位千克");
						$("#topLimit").val("重量:单位千克");
					}
					else
					{
						$("#lowerLimit").val("体积:单位立方米");
						$("#topLimit").val("体积:单位立方米");
					}
				}else{
					$("#lowerLimit").val("单位:楼层");
					$("#topLimit").val("单位:楼层");
				}
			}
	};
	ord.init();
}]);