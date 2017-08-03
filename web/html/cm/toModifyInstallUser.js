
var proveCashApp = angular.module("AddInstallUserApp", ['commonApp']);
var userId = getQueryString("userId");

proveCashApp.controller("AddInstallUserCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var addInstallUser={
		init:function(){
			this.bindEvent();
			this.initStaticData();
			this.initUserData();
			$scope.isAreaShow=false;
			$scope.showServiceType=false;//显示服务类型
			//$scope.showServiceTitle=true;
			$scope.showServiceInfo=true;

			$scope.serviceType={};
			$scope.districtIds={};
			$scope.districtMapList={};
			//$scope.maintain.valueService={};
			
		},
		bindEvent:function(){
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
			$scope.doClose = this.doClose;
			$scope.delSelService = this.delSelService;//删除服务
			$scope.checkSaveData = this.checkSaveData;
			$scope.selectCity = this.selectCity;
		},
		initUserData:function(){
			var param = {"userId":userId};
			commonService.postUrl("cmSfUserInfoBO.ajax?cmd=getUserInfoDtl",param,function(data){
				if(data != null && data != undefined && data != ""){
						  $scope.cmSfUser.provinceId = data.sfUserInfo.provinceId;
						  $scope.queryCity();
						  $scope.cmSfUser.sfUserId = data.sfUserInfo.sfUserId;
						  $scope.cmSfUser.cityId = data.sfUserInfo.cityId ;
						  $scope.cmSfUser.name = data.sfUserInfo.name ;
						  $scope.cmSfUser.phone = data.sfUserInfo.phone ;
						  $scope.cmSfUser.largestAcceptOrder = data.sfUserInfo.largestAcceptOrder ;
						  $scope.cmSfUser.vehicleNums = data.sfUserInfo.vehicleNums ;
						  $scope.cmSfUser.provinceName = data.sfUserInfo.provinceName ;
						  $scope.cmSfUser.storeAddr = data.sfUserInfo.storeAddr ;
						  $scope.cmSfUser.storeEand = data.sfUserInfo.storeEand ;
						  $scope.cmSfUser.storeNand = data.sfUserInfo.storeNand ;
						  $scope.cmSfUser.storeSquare = data.sfUserInfo.storeSquare ;
						  $scope.cmSfUser.cooperationType = data.sfUserInfo.cooperationType ;
						  $scope.cmSfUser.membersNums = data.sfUserInfo.membersNums;
						  $scope.cmSfUser.commonServiceId = '' ;
						  $scope.cmSfUser.commonServiceName = '' ;
						  $scope.cmSfUser.commonChildServiceId = '';
						  $scope.cmSfUser.commonChildServiceName = '' ;
						  ///服务展示
						  if(data.services!=null){
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
						  if(data.serviceAttr!=null){
							  $scope.cmSfUser.valueService.maintain=1;
							  $scope.cmSfUser.valueService.maintainAttr={};
							  for(var attrSerIndex in data.serviceAttr){
									
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
						  
				}
			});
		},
		//保存
		doSave:function(){
			if(!$scope.checkSaveData()){
				return;
			}
			$timeout(function(){
				commonService.postUrl("cmSfUserInfoBO.ajax?cmd=saveCmSfUser",$scope.cmSfUser,function(data){
					if(data != null && data != undefined && data != ""){
						if(data == 'Y'){
							//var rtn = confirm("是否需继续新增师傅");
							commonService.alert('你已成功修改师傅信息');
						}
					}
				},null,null,'Y');
			},500);
		},
		//获取静态数据
		initStaticData:function(){
			commonService.postUrl("staticDataBO.ajax?cmd=selectProvince","",function(data){
				if(data != null && data != undefined && data != ""){
					/*var autoComplete = new AutoComplete('o','auto',data,["name","nameAlpha"],["id","name"],function(data,cId){
						bill.queryRoateRuting(cId);
						$scope.cmSfUser.destCity = cId;
					});*/
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
					/*var autoComplete = new AutoComplete('o','auto',data,["name","nameAlpha"],["id","name"],function(data,cId){
						bill.queryRoateRuting(cId);
						$scope.cmSfUser.destCity = cId;
					});*/
					$scope.citys = data.items;

				}
			});
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
			if($scope.cmSfUser.largestAcceptOrder==''){
				commonService.alert('请输入最大接单数');
				return false;
			}
			if($scope.cmSfUser.cityId==''){
				commonService.alert('请选择服务城市');
				return false;
			}
			if($scope.cmSfUser.storeAddr==''){
				commonService.alert('请输入仓库地址');
				return false;
			}
			return true;
		},
		doClose:function(){
			commonService.closeToParentTab();
		},
		cmSfUser:{
			opType:'UPDATE',
			sfUserId:'',
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
