var view = getQueryString("view");
var id = getQueryString("id");
var edit = getQueryString("edit");
var vehicleId = getQueryString("vehicleId");
var cmVehicleInfoDetailApp = angular.module("cmVehicleInfoDetailApp", ['commonApp','ngTouch','angucomplete']);
cmVehicleInfoDetailApp.controller("cmVehicleInfoDetailCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	$scope.countries = [];
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
				$scope.close = this.close;
				$scope.getDriverName = this.getDriverName;
				$scope.secondDriver = this.secondDriver;
				$scope.form = this.form;
				$scope.vehicleChange = this.vehicleChange;
				$scope.callback = this.callback;
				
			},
			orderInfo:{},
			form:{},
			//获取静态数据
			initStaticData:function(){
				if(vehicleId!=null && vehicleId!=undefined && vehicleId!=""){
					$scope.isShow=true;
				}else{
					$scope.isShow=false;
				}
				//获取计费方式
//				commonService.postUrl("staticDataBO.ajax?cmd=selectExceptionType","",function(data){
//					if(data != null && data != undefined && data != ""){
//						$scope.exceptionTypeData = data;
//					}
//				});
				/**创建网点查询*/
					commonService.postUrl("organizationBO.ajax?cmd=getAllOrg","",function(data){
						console.log(data);
						if(data != null && data != undefined && data != ""){
							$scope.orgInfo = data;
							$scope.orgInfo.items.unshift({orgId:-1,orgName:"请选择"});
						}
					});
				/**费用查询*/
			/*	var url = "staticDataBO.ajax?cmd=selectAcFeeConfig";
				commonService.postUrl(url,"",function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
						$scope.acFeeConfig=data;
					}
				});*/
				if(vehicleId!=null && vehicleId!=undefined && vehicleId!=""){
					var url="vehicleInfoBO.ajax?cmd=queryVehicleDtl";
					commonService.postUrl(url,"vehicleId="+vehicleId,function(data){
						//成功执行
						if(data!=null && data!=undefined && data!=""){
							$scope.data = data.cmVehicleInfo;
							$scope.data.businessType = data.cmVehicleInfo.businessType+"";
							$scope.data.vehicleType = data.cmVehicleInfo.vehicleType+"";
							$("#ex2_value").val(data.cmVehicleInfo.mainDriverName);
							$scope.data.mainDriverId=data.cmVehicleInfo.mainDriverId;
							$scope.data.bill=data.bill;
							$scope.drivingPic.initDate(data.cmVehicleInfo.drivingPic); 
							$scope.data.orgId=parseInt(data.cmVehicleInfo.orgId);
							if(data.cmVehicleInfo.businessType==1 ){
								$scope.isDisabled=true;
							}else{
								$scope.isDisabled=false;
							}
							$scope.drivingPic.isUpShow(false);
						}
					});
				}
			},
			data:{
				orgId:-1
			},
			userData:function(){
				$scope.currOrgId=userInfo.orgId;
				$scope.currOrgName=userInfo.orgName;
			},
			vehicleChange:function(){
				if(vehicleId!=null && vehicleId!=undefined && vehicleId!=""){
					if($scope.data.businessType==2){
						$scope.isDisabled=false;
					}else{
						$scope.isDisabled=true;
					}
				}
				//console.log($scope.data.businessType);
			},
			close:function(){
				commonService.closeToParentTab(false);
			},
			/**检索主驾驶员名称**/
			getDriverName:function(type,value){
				var param="";
				if(type==1){
					param=$("#ex2_value").val();
				}else if(type==2){
					param=$("#ex1_value").val();
				}
				if(param==null || param==undefined || param==""){
					$scope.isDisabled=false;
				}
				var url = "vehicleInfoBO.ajax?cmd=doQueryVehicleDriver";
				$scope.form.name=param;
				commonService.postUrl(url,$scope.form,function(data){
					//成功执行
					if(data!=null && data!=undefined && data!=""){
						var aa=new Array();
						for(var i=0;i<data.content.length;i++){
							aa.push(data.content[i]);
						}
						$scope.countries =aa;
					}
				});
			},
			callback:function(data){
				$scope.data.bill=data.bill;
					$scope.isDisabled=true;
			},
	};
	ord.init();
}]);