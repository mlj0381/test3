var userId=getQueryString("userId");
var proveCashApp = angular.module("AddInstallUserApp", ['commonApp']);
proveCashApp.controller("AddInstallUserCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var addInstallUser={
		init:function(){
			this.bindEvent();
			$scope.isView = getQueryString("isView");
			$scope.userId = getQueryString("userId");
			if($scope.userId>0){
				//$scope.initUserData();
			}
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
			setContentHegthDelay();
		},
		bindEvent:function(){
			$scope.params = this.params;
			$scope.initUserData=this.initUserData;
			$scope.initStaticData=this.initStaticData;
			$scope.doSave = this.doSave; //提交
			$scope.cmSfUser = this.cmSfUser;
			$scope.serviceType = this.serviceType;
			$scope.queryCity = this.queryCity;//查询地市
			$scope.queryDistrict = this.queryDistrict;//查询区县
			$scope.selDistrict = this.selDistrict;//选择区县
			$scope.districtConfirm = this.districtConfirm;//提交选择区县
			$scope.selChildService=this.selChildService;//选择子服务
			$scope.queryChildServiceType = this.queryChildServiceType;
			$scope.selSfServices=this.selSfServices;//提交师傅服务
			$scope.comfirmSfServices=this.comfirmSfServices;//提交师傅服务
			$scope.selectCity=this.selectCity;
			$scope.delSelService = this.delSelService;//删除服务
			$scope.checkSaveData = this.checkSaveData;
			$scope.checkPhone=this.checkPhone;
			$scope.doClose = this.doClose;
			$scope.selectAllDistrict = this.selectAllDistrict;
		},
		initUserData:function(){
			var param = {"userId":$scope.userId};
			commonService.postUrl("cmSfUserInfoBO.ajax?cmd=getUserInfoDtl",param,function(data){
				if(data != null && data != undefined && data != ""){
					  $scope.cmSfUser=data.sfUserInfo;
					  $scope.cmSfUser.native_=data.sfUserInfo.native_;
					  $scope.cmSfUser.receiType=data.sfUserInfo.receiType+"";
					  if($scope.cmSfUser.receiType==2){
						  $scope.cmSfUser.bankAccount= $scope.cmSfUser.paypalAccount;
					  }else if($scope.cmSfUser.receiType==3){
						  $scope.cmSfUser.bankAccount= $scope.cmSfUser.wxAccount;
					  }
					  if(data.sfUserInfo.imagePositive!=null&&data.sfUserInfo.imagePositive!=undefined&&data.sfUserInfo.imagePositive!=""){
						  $scope.imagePositive.initDate(data.sfUserInfo.imagePositive);
						  if($scope.isView==1){
						  $scope.imagePositive.isUpShow(false); }
					  }
					  if(data.sfUserInfo.imageCounter!=null&&data.sfUserInfo.imageCounter!=undefined&&data.sfUserInfo.imageCounter!=""){
						  $scope.imageCounter.initDate(data.sfUserInfo.imageCounter);
						  if($scope.isView==1){
						  $scope.imageCounter.isUpShow(false); 
						  }
					  }
					  $scope.queryCity();	  
				}
			});
		},
		params:{
			
		},
		//保存
		doSave:function(){
			if(!$scope.checkSaveData()){
				return;
			}
			//重新设置经纬度地址
			$scope.cmSfUser.storeEand = $("#storeEand").val();
			$scope.cmSfUser.storeNand = $("#storeNand").val();
			//$scope.cmSfUser.storeAddr = $("#desPlaceDtl").val();
			if($scope.imageCounter.get().flowId!=null&&$scope.imageCounter.get().flowId!=undefined&&$scope.imageCounter.get().flowId!=''){
				$scope.cmSfUser.imageCounter=$scope.imageCounter.get().flowId;
			}
			if($scope.imagePositive.get().flowId!=null&&$scope.imagePositive.get().flowId!=undefined&&$scope.imagePositive.get().flowId!=''){
				$scope.cmSfUser.imagePositive=$scope.imagePositive.get().flowId;
			}
			$scope.cmSfUser.showSfServicesTemp=angular.toJson($scope.cmSfUser.showSfServices);
			$scope.cmSfUser.valueServiceTemp=angular.toJson($scope.cmSfUser.valueService);
//			$scope.cmSfUser.maintainAttr=angular.toJson($scope.cmSfUser.maintainAttr);
			$timeout(function(){
				commonService.postUrl("cmSfUserInfoBO.ajax?cmd=saveCmSfUser",$scope.cmSfUser,function(data){
					if(data != null && data != undefined && data != ""){
						if(data == 'Y'){
							commonService.alert("你已成功添加师傅信息");
							commonService.closeToParentTab(true);
						}
					}
				},null,null,'Y');
			},500);
		},
		//查找子类别
		queryChildServiceType:function(){
			for(var i=0 ; i<$scope.oneLevelProductItmes.length;i++){
				if($scope.oneLevelProductItmes[i].prodId==$scope.cmSfUser.commonServiceId){
					$scope.cmSfUser.commonServiceName = $scope.oneLevelProductItmes[i].name;
				}
			}
			var param ={catalogId:$scope.cmSfUser.commonServiceId};
			commonService.postUrl("productBO.ajax?cmd=queryProductCatalog",param,function(data){
				if(data != null && data != undefined && data != ""){
					$scope.childServiceItmes = data.items;
				}
			});
			
			//清空选择配送类型
			$scope.serviceType.sendName ='';
			$scope.serviceType.sendValue ='';
			$scope.serviceType.installName ='';
			$scope.serviceType.installValue ='';
			$scope.serviceType.sendAndInstallName ='';
			$scope.serviceType.sendAndInstallValue ='';
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
					$scope.cmSfUser.cityId=-1;
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
		doClose:function(){
			commonService.closeToParentTab(true);
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
		selDistrict:function(obj){
			eval(" var hasClass = $scope.districtIds.district_"+obj.district.id);
			if(hasClass=='drop_hover'){
				eval(" $scope.districtIds.district_"+obj.district.id+"=''");
				$scope.districtMapList[obj.district.id]=null;
			}else{
				eval(" $scope.districtIds.district_"+obj.district.id+"='drop_hover'");
				$scope.districtMapList[obj.district.id]={"id":obj.district.id,"name":obj.district.name};
			}
		},
		districtConfirm:function(){
			var idTempList = new Array();
			var nameTempList =  new Array();
			for(var objectIndex in $scope.districtMapList){
				var object = $scope.districtMapList[objectIndex];
				if(object!=null){
					idTempList.push(object.id);
					nameTempList.push(object.name);
				}
			}
			$scope.cmSfUser.districtIds = idTempList.join(',');
			$scope.cmSfUser.districtNames =nameTempList.join(',');
			$scope.isAreaShow=false;
		},
		///子类选择
		selChildService:function(){
			///货物总类
			if($scope.cmSfUser.commonServiceId==''){
				commonService.alert('请先选择服务类别');
				return;
			}
			
			for(var i=0 ; i<$scope.childServiceItmes.length;i++){
				if($scope.childServiceItmes[i].prodId==$scope.cmSfUser.commonChildServiceId){
					$scope.cmSfUser.commonChildServiceName = $scope.childServiceItmes[i].name;
				}
			}
				//清空选择配送类型
				$scope.serviceType.sendName ='';
				$scope.serviceType.sendValue ='';
				$scope.serviceType.installName ='';
				$scope.serviceType.installValue ='';
				$scope.serviceType.sendAndInstallName ='';
				$scope.serviceType.sendAndInstallValue ='';
		},
		selSfServices:function(){
			if($scope.cmSfUser.commonChildServiceId==''){
				commonService.alert('请先选择一子类别');
				return;
			}
			var value = $scope.serviceType.sendValue;
			if($scope.serviceType.sendValue!=''){
				$scope.serviceType.sendName = $scope.commonServiceTypes[value].name;
			}else{
				$scope.serviceType.sendName ='';
				$scope.serviceType.sendValue ='';
			}
			value = $scope.serviceType.installValue;
			if($scope.serviceType.installValue!=''){
				$scope.serviceType.installName = $scope.commonServiceTypes[value].name;
			}else{
				$scope.serviceType.installName ='';
				$scope.serviceType.installValue ='';
			}
			if($scope.serviceType.sendValue!=''&&$scope.serviceType.installValue!=''){
				$scope.serviceType.sendAndInstallValue=13;
			}
			value = $scope.serviceType.sendAndInstallValue;
			if($scope.serviceType.sendAndInstallValue!=''){
				$scope.serviceType.sendAndInstallName = $scope.commonServiceTypes[value].name;
				$scope.serviceType.sendAndInstallValue =value;
			}else{
				$scope.serviceType.sendAndInstallName ='';
				$scope.serviceType.sendAndInstallValue ='';
			}
			$scope.servicesLength++;
		},
//		checkPhone:function(){
//			if($scope.cmSfUser.phone==''){
//				return;
//			}
//			var param = {"phone":$scope.cmSfUser.phone};
//			$timeout(function(){
//			commonService.postUrl("cmSfUserInfoBO.ajax?cmd=checkUserPhone",param,function(data){
//				if(data.sfUserInfo != null && data.sfUserInfo != undefined && data.sfUserInfo != ""){
//					  $scope.cmSfUser=data.sfUserInfo;
//					  $scope.cmSfUser.native_=data.sfUserInfo.native_;
//					  $scope.cmSfUser.receiType=data.sfUserInfo.receiType+"";
//					  if($scope.cmSfUser.receiType==2){
//						  $scope.cmSfUser.bankAccount= $scope.cmSfUser.paypalAccount;
//					  }else if($scope.cmSfUser.receiType==3){
//						  $scope.cmSfUser.bankAccount= $scope.cmSfUser.wxAccount;
//					  }
//					  if(data.sfUserInfo.imagePositive!=null&&data.sfUserInfo.imagePositive!=undefined&&data.sfUserInfo.imagePositive!=""){
//						  $scope.imagePositive.initDate(data.sfUserInfo.imagePositive);
//					  }
//					  if(data.sfUserInfo.imageCounter!=null&&data.sfUserInfo.imageCounter!=undefined&&data.sfUserInfo.imageCounter!=""){
//						  $scope.imageCounter.initDate(data.sfUserInfo.imageCounter);
//					  }
//					  $scope.queryCity();
//				}
//			}); 
//			},500);
//		},
		comfirmSfServices:function(){
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
			
			var serName = $scope.cmSfUser.commonServiceName;
			var serId = $scope.cmSfUser.commonServiceId;
			var serChildId = $scope.cmSfUser.commonChildServiceId;
			var serChildName = $scope.cmSfUser.commonChildServiceName;
			var sendName = $scope.serviceType.sendName;
			var sendValue = $scope.serviceType.sendValue;
			var installValue = $scope.serviceType.installValue;
			var installName = $scope.serviceType.installName;
			var sendAndInstallName = $scope.serviceType.sendAndInstallName;
			var sendAndInstallValue = $scope.serviceType.sendAndInstallValue;
			
			if(serChildId==''){
				commonService.alert("请选择服务子类型");
				return;
			}
			
			if(sendValue==''&&installValue==''&&sendAndInstallValue==''){
				commonService.alert("请至少选择一服务能力");
				return;
			}
			
			selServiceObj.serName=serName;
			selServiceObj.serId=serId;
			selServiceObj.serChildId=serChildId;
			selServiceObj.serChildName=serChildName;
			selServiceObj.sendId=sendValue;
			selServiceObj.sendName=sendName;
			selServiceObj.installId=installValue;
			selServiceObj.installName=installName;
			selServiceObj.sendAndInstallName=sendAndInstallName;
			selServiceObj.sendAndInstallId=sendAndInstallValue;
			if($scope.cmSfUser.showSfServices==null||$scope.cmSfUser.showSfServices==undefined ||$scope.cmSfUser.showSfServices==''){
				$scope.cmSfUser.showSfServices={};
			}
			$scope.cmSfUser.showSfServices[serChildId]=selServiceObj;
			//
			//if($scope.cmSfUser.showSfServices.length>0){
				$scope.showServiceInfo=true;
				$scope.showServiceTitle=false;
			//}
		},
		delSelService:function(obj){
			delete $scope.cmSfUser.showSfServices[obj];
			//判断是否都删除完
			var isAllDel = true;
			for(var objServ in $scope.cmSfUser.showSfServices){
				if($scope.cmSfUser.showSfServices[objServ] != ''){
					isAllDel = false;
				}
			}
			if(isAllDel){
				$scope.showServiceTitle=true;
			}
		},
		serviceType:{
			sendName :'',
			sendValue :'',
			installName :'',
			installValue :'',
			sendAndInstallName :'',
			sendAndInstallValue :'',
		},
		checkSaveData:function(){
			if($scope.cmSfUser.name==''){
				commonService.alert('请输入师傅姓名');
				return false;
			}
			if($scope.cmSfUser.phone==''){
				commonService.alert('请输入师傅账户');
				return false;
			}
			if($scope.cmSfUser.provinceId==''||$scope.cmSfUser.provinceId=='-1'||$scope.cmSfUser.provinceId==null||$scope.cmSfUser.provinceId==undefined){
				commonService.alert('请选择服务省份');
				return false;
			}
			if($scope.cmSfUser.cityId==''||$scope.cmSfUser.cityId=='-1'||$scope.cmSfUser.cityId==null||$scope.cmSfUser.cityId==undefined){
				commonService.alert('请选择服务城市');
				return false;
			}
			
			return true;
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
				if(data != null && data != undefined && data != ""&&data.sfUserInfo!=null){
					  $scope.cmSfUser=data.sfUserInfo;
					  $scope.showServiceInfo=true;
					  $scope.showServiceTitle=false;
					  $scope.cmSfUser.provinceId = data.sfUserInfo.provinceId;
					  $scope.queryCity();
					  $scope.cmSfUser.sfUserId = data.sfUserInfo.sfUserId;
					  //查到用户为只读
					  if($scope.isView==1){
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
	addInstallUser.init();
}]);
var desPlaceDtlAutoComplete = null;
$(function(){// 自动补齐功能
	desPlaceDtlAutoComplete = new AutoComplete('desPlaceDtl','desPlaceDtlDiv',[],'cityId');
});
function setBlurDesPlaceDtl(){
		document.getElementById('desPlaceDtlDiv').style.display = "";
}