var view = getQueryString("view");
var id = getQueryString("id");
var edit = getQueryString("edit");
var vehicleId = getQueryString("vehicleId");
var routeFeeConfigAddApp = angular.module("routeFeeConfigAddApp", ['commonApp','ngTouch','angucomplete']);
routeFeeConfigAddApp.controller("routeFeeConfigAddCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	$scope.countries = [];
	var userType=userInfo.userType;
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
				$scope.getDriverName = this.getDriverName;
				$scope.secondDriver = this.secondDriver;
				$scope.form = this.form;
				$scope.vehicleChange = this.vehicleChange;
				$scope.callback = this.callback;
				$scope.isOrgNameShow=true;
			},
			orderInfo:{},
			form:{},
			//获取静态数据
			initStaticData:function(){
				if(userType==6){
					$scope.isOrgNameShow=false;
				}
				if(vehicleId!=null && vehicleId!=undefined && vehicleId!=""){
					$scope.isShow=true;
				}else{
					$scope.isShow=false;
				}
				
				/**创建网点查询*/
					commonService.postUrl("organizationBO.ajax?cmd=getAllOrg","",function(data){
						//console.log(data);
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
						}
					});
				}
			},
			data:{
				orgId:-1,
				registDate:""
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
			doSave:function(){
				if($scope.data.plateNumber==null || $scope.data.plateNumber==undefined ||$scope.data.plateNumber==""){
					 commonService.alert("请填写车牌号"); 
					 return;
				}
			    if($scope.data.actualWeight==null || $scope.data.actualWeight==undefined ||$scope.data.actualWeight==""){
					 commonService.alert("请填写可装载重量"); 
					 return;
				}
			    if($scope.data.actualVolume==null || $scope.data.actualVolume==undefined ||$scope.data.actualVolume==""){
					 commonService.alert("请填写可装载体积"); 
					 return;
				}
			    if($scope.data.length==null || $scope.data.length==undefined ||$scope.data.length==""){
					 commonService.alert("请填写车厢长"); 
					 return;
				}
				$scope.data.imagePath=$scope.drivingPic.get().flowUrl;
				$scope.data.drivingPic=$scope.drivingPic.get().flowId;
				if($scope.data.orgId==-1 && userType!=6){
					commonService.alert("归属网点不能为空");
					return false;
				}
					commonService.postUrl("vehicleInfoBO.ajax?cmd=doSaveVehicle",$scope.data,function(data){
						if(data!=null && data!=undefined && data!=""){
							 $scope.data.plateNumber="";
							 $scope.data.length="";
							 $scope.data.wide="";
							 $scope.data.high="";
							 $scope.data.volume="";
							 $scope.data.actualWeight="";
							 $scope.data.actualVolume="";
							 $scope.data.registDate="";
							 $scope.data.pullDate="";
							 $scope. data.systemType="";
							 commonService.alert("保存成功",function(){
								commonService.closeToParentTab(false);
							});
						}
					});
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