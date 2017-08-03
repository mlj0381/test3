var userId=getQueryString("userId");
var myApp = angular.module("auditUserManageApp", ['commonApp']);
myApp.controller("auditUserManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var myManage={
		init:function(){
			this.bindEvent();			
			$scope.userId = getQueryString("userId");
			this.initStaticData();
			$scope.isAreaShow=false;
			$scope.showServiceType=false;//显示服务类型
			$scope.showServiceTitle=true;
			$scope.showServiceInfo=false;
			$scope.setReadOnly=false;
			$scope.checkPhone();
			$scope.serviceType={};
			$scope.districtIds={};
			$scope.districtMapList={};
		},
		bindEvent:function(){
			$scope.queryCity = this.queryCity;
			$scope.initUserData=this.initUserData;
			$scope.toAuditSfUser= this.toAuditSfUser;
			$scope.toModify = this.toModify;
			$scope.query={};
			$scope.params={};
			$scope.doClose=this.doClose;
			$scope.params = this.params;
			$scope.initStaticData=this.initStaticData;
			$scope.cmSfUser = this.cmSfUser;
			$scope.serviceType = this.serviceType;
			$scope.queryCity = this.queryCity;//查询地市
			$scope.queryDistrict = this.queryDistrict;//查询区县
			$scope.selDistrict = this.selDistrict;//选择区县
			$scope.selectCity=this.selectCity;
			$scope.checkPhone=this.checkPhone;
			$scope.doClose = this.doClose;
			$scope.selectAllDistrict = this.selectAllDistrict;
		},
		doClose:function(){
			commonService.closeToParentTab(true);
		},
		toAuditSfUser:function(opType){
			var param = {"userId":$scope.userId,"opType":opType,"auditReason":$scope.cmSfUser.auditReason};
			var pass = commonService.confirm("确定认证所选用户",function(){
				commonService.postUrl("cmSfUserInfoBO.ajax?cmd=auditSfUser",param,function(data){
					if(data =="Y"){
						commonService.alert("操作成功");
						$scope.doClose();
					}
				});
			}
			);
			
			
		},
		params:{
			
		},
		//获取静态数据
		initStaticData:function(){
			
			commonService.postUrl("staticDataBO.ajax?cmd=selectProvince","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.provinces = data.items;
				}
			});
			var cooperationTypeParam={codeType:'COOPERATION_TYPE'};
			commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",cooperationTypeParam,function(data){
				if(data != null && data != undefined && data != ""){
					$scope.cooperationTypes = data.items;
				}
			});
			//服务类型
			var param={codeType:'SCHE_SERVICE_TYPE'};
			commonService.postUrl("staticDataBO.ajax?cmd=selectSysStaticDataByCodeType",param,function(data){
				if(data != null && data != undefined && data != ""){
					$scope.commonServiceTypes =new Array();
					for(var objIndex in data.items){
						var object ={value:"",name:""};
							object.value=data.items[objIndex].codeValue;
							object.name=data.items[objIndex].codeName;
							$scope.commonServiceTypes[object.value]=object;
					} 
				}
			});
			///货物总类
			commonService.postUrl("productBO.ajax?cmd=queryProductCatalog","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.oneLevelProductItmes = data.items;
				}
			});
		},
		queryCity:function(){
			var param={provinceId:$scope.cmSfUser.provinceId};
			commonService.postUrl("staticDataBO.ajax?cmd=selectCity",param,function(data){
				if(data != null && data != undefined && data != ""){
					$scope.citys = data.items;
				}
			});
			//获取名称
			for(var provinceIndex in $scope.provinces){
				var item = $scope.provinces[provinceIndex];
				if($scope.cmSfUser.provinceId==item.id){
					$scope.cmSfUser.provinceName=item.name;
					$("#provinceName").val(item.name);
				}
			}///end

		},
		selectCity:function(){
			if($scope.cmSfUser.provinceId==''){
				commonService.alert('请先选择服务省份');
				return;
			}
			if($scope.cmSfUser.cityId!=$scope.cmSfUser.tempCityId){
				$scope.cmSfUser.tempCityId = $scope.cmSfUser.cityId;
				//选择城市时，清空区域
				$scope.districtMapList={};
				$scope.cmSfUser.districtIds = '';
				$scope.cmSfUser.districtNames ='';
			}
		},
		queryDistrict:function(){
			//展示
			if($scope.cmSfUser.cityId==''){
				commonService.alert('请先选择服务城市');
				return;
			}
			$scope.isAreaShow=!$scope.isAreaShow;
			var param={cityId:$scope.cmSfUser.cityId};
			commonService.postUrl("staticDataBO.ajax?cmd=selectDistrict",param,function(data){
				if(data != null && data != undefined && data != ""){
					$scope.districts = data.items;
				}
			});
			
		},
		serviceType:{
			sendName :'',
			sendValue :'',
			installName :'',
			installValue :'',
			sendAndInstallName :'',
			sendAndInstallValue :'',
		},
		checkPhone:function(){
			var url="";
			var param =null;
			if(userId!=null&&userId!=undefined&&userId!=""){
				 url="cmSfUserInfoBO.ajax?cmd=getUserInfoDtl";
				 param = {"userId":userId}
			}else{
				 url="cmSfUserInfoBO.ajax?cmd=checkUserPhone";
				 param = {"phone":$scope.cmSfUser.phone}
				if($scope.cmSfUser.phone==''){
					return;
				}
			}
			$timeout(function(){
			commonService.postUrl(url,param,function(data){
				if(data != null && data != undefined && data != ""){
					  $scope.cmSfUser=data.sfUserInfo;
					  $scope.showServiceInfo=true;
					  $scope.showServiceTitle=false;
					  $scope.cmSfUser.provinceId = data.sfUserInfo.provinceId;
					  $scope.queryCity();
					  $scope.cmSfUser.sfUserId = data.sfUserInfo.sfUserId;
					  //查到用户为只读
					  if( $scope.cmSfUser.sfUserId!=null){
						 $scope.setReadOnly=true;
					  }
					  if(data.sfOrgRel!=null&&data.sfOrgRel!=undefined&&data.sfOrgRel!=""){
						  $scope.cmSfUser.margin_Fee=data.sfOrgRel.marginFee==null?'':data.sfOrgRel.marginFee/100;
						  $scope.cmSfUser.margin_Fee_Limit=data.sfOrgRel.marginFeeLimit==null?'':data.sfOrgRel.marginFeeLimit/100;
						  $scope.cmSfUser.largestAcceptOrder = data.sfOrgRel.largestAcceptOrder ;
					  }
					  $scope.cmSfUser.native_=data.sfUserInfo.native_;
					  $scope.cmSfUser.receiType=data.sfUserInfo.receiType+"";
					  $scope.cmSfUser.receiTypeName=data.sfUserInfo.receiTypeName;
					  if($scope.cmSfUser.receiType==2){
						  $scope.cmSfUser.bankAccount=  data.sfUserInfo.paypalAccount;
					  }else if($scope.cmSfUser.receiType==3){
						  $scope.cmSfUser.bankAccount= data.sfUserInfo.wxAccount;
					  }
					  if(data.sfUserInfo.imagePositive!=null&&data.sfUserInfo.imagePositive!=undefined&&data.sfUserInfo.imagePositive!=""){
						  $scope.imagePositive.initDate(data.sfUserInfo.imagePositive);
					  }
					  if(data.sfUserInfo.imageCounter!=null&&data.sfUserInfo.imageCounter!=undefined&&data.sfUserInfo.imageCounter!=""){
						  $scope.imageCounter.initDate(data.sfUserInfo.imageCounter);
					  }
					  $scope.cmSfUser.commonServiceId = '' ;
					  $scope.cmSfUser.commonServiceName = '' ;
					  $scope.cmSfUser.commonChildServiceId = '';
					  $scope.cmSfUser.commonChildServiceName = '' ;
					  ///服务展示
					  if(data.services!=null){
						  if($scope.cmSfUser.showSfServices==null||$scope.cmSfUser.showSfServices==undefined){
							  $scope.cmSfUser.showSfServices={};
						  }
						  for(var serviceIndex in data.services){
							  var selServiceObj = {
										serName:'',
										serId:'',
										serChildId:'',
										serChildName:'',
										sendName:'',
										sendId:'',
										installId:'',
										installName:'',
										sendAndInstallName:'',
										sendAndInstallId:''
										
								};
								selServiceObj.serChildId=data.services[serviceIndex].productId
								selServiceObj.serChildName=data.services[serviceIndex].productName;
								var serviceArray = data.services[serviceIndex].serviceIds.split(",");
								if(serviceArray !=null && serviceArray instanceof Array){
									for(var serIndex in serviceArray ){
										if(serviceArray[serIndex]==13){
											selServiceObj.sendAndInstallName=$scope.commonServiceTypes[serviceArray[serIndex]].name;
											selServiceObj.sendAndInstallId=serviceArray[serIndex];
										}
										if(serviceArray[serIndex]==12){
											selServiceObj.sendName=$scope.commonServiceTypes[serviceArray[serIndex]].name;
											selServiceObj.sendId=serviceArray[serIndex];
										}
										if(serviceArray[serIndex]==14){
											selServiceObj.installName=$scope.commonServiceTypes[serviceArray[serIndex]].name;
											selServiceObj.installId=serviceArray[serIndex];
										}
									}
								}
								$scope.cmSfUser.showSfServices[selServiceObj.serChildId]=selServiceObj;
						  }
				}
					 //区域展示
					  if(data.serviceArea!=null){
						  var ids="";
						  var areaNames ="";
						  for(var areaIndex in data.serviceArea){
							  if(ids==''){
								  ids =data.serviceArea[areaIndex].districtId;
								  areaNames =data.serviceArea[areaIndex].districtName;

							  }else{
								  ids =ids+","+data.serviceArea[areaIndex].districtId;
								  areaNames =areaNames+","+data.serviceArea[areaIndex].districtName;

							  }
							  
						  }
						 $scope.cmSfUser.districtIds=ids;
						 $scope.cmSfUser.districtNames=areaNames;
					  }
					 //增值服务
					  //增值服务
					  if(data.serviceAttr!=null){
						  if($scope.cmSfUser.valueService==null||$scope.cmSfUser.valueService==undefined){
							  $scope.cmSfUser.valueService={};
						  }
						  $scope.cmSfUser.valueService.maintainAttr={};
						  for(var attrSerIndex in data.serviceAttr){
							  $scope.cmSfUser.valueService.maintain=1;
								
							  if(data.serviceAttr[attrSerIndex].serviceId==1){
									  var  attrArrayIds = data.serviceAttr[attrSerIndex].attrIds.split(",");
									for(var aIndex in attrArrayIds){
									  var attrId =   attrArrayIds[aIndex];
									if(attrId==1){
										$scope.cmSfUser.valueService.maintainAttr.skin=1;
									}else if(attrId==2){
										$scope.cmSfUser.valueService.maintainAttr.wood=2;
									}else if(attrId==3){
										$scope.cmSfUser.valueService.maintainAttr.other=3;
									}
								  }
							  }else if(data.serviceAttr[attrSerIndex].serviceId==2){
								  $scope.cmSfUser.valueService.returnGoods=2;

							  }else if(data.serviceAttr[attrSerIndex].serviceId==3){
								  $scope.cmSfUser.valueService.yanghu=3;
							  }
						  }
					  }
					//end
				}
				setContentHegthDelay();
			}); 
			},500);
		},
		selectAllDistrict:function(){
			for(var districtIndex in $scope.districts){
				eval(" $scope.districtIds.district_"+ $scope.districts[districtIndex].id+"='drop_hover'");
				$scope.districtMapList[$scope.districts[districtIndex].id]={"id":$scope.districts[districtIndex].id,"name":$scope.districts[districtIndex].name};
			}
		},
		cmSfUser:{
			name:'',
			phone:'',
			membersNums:'',
			largestAcceptOrder:'',
			vehicleNums:'',
			provinceName:'',
			provinceId:'',
			cityName:'',
			cityId:'',
			districtIds:'',
			districtNames:'',
			storeAddr:'',
			storeEand:'',
			storeNand:'',
			storeSquare:'',
			cooperationType:'',
			commonServiceId:'',
			commonServiceName:'',
			commonChildServiceId:'',
			commonChildServiceName:'',
			servicesLength:0,
			showSfServices:{},
			valueServicesLength:0,
			valueService:{}
		}
	};
	myManage.init();
}]);
