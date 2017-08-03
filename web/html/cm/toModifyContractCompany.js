
var proveCashApp = angular.module("AddContractCompApp", ['commonApp']);
var contractId = getQueryString("contractId");

proveCashApp.controller("AddContractCompCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
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
			$scope.showProductPricePage=false;
			$scope.prodPriceTemp={};
			
		},
		bindEvent:function(){
			$scope.doSave = this.doSave; //提交
			$scope.contractComp = this.contractComp;
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
			$scope.queryLevel4Products = this.queryLevel4Products;
			$scope.setProdPrice = this.setProdPrice;
			$scope.saveProdPrice = this.saveProdPrice;
			$scope.selectCity = this.selectCity;

		},
		initUserData:function(){
			var param = {"contractId":contractId};
			commonService.postUrl("cmCtrCompanyInfoBO.ajax?cmd=getContractCompany",param,function(data){
				if(data != null && data != undefined && data != ""){
						  $scope.contractComp.provinceId = data.contractComp.provinceId;
						  $scope.queryCity();
						  $scope.contractComp.contractId = data.contractComp.contractId;
						  $scope.contractComp.cityId = data.contractComp.cityId ;
						  $scope.contractComp.contractCompanyName = data.contractComp.contractCompanyName ;
						  $scope.contractComp.contractTenantCode = data.contractComp.contractTenantCode ;
						  $scope.contractComp.largestAcceptOrder = data.contractComp.largestAcceptOrder ;
						  $scope.contractComp.vehicleNums = data.contractComp.vehicleNums ;
						  $scope.contractComp.provinceName = data.contractComp.provinceName ;
						  $scope.contractComp.storeAddr = data.contractComp.storeAddr ;
						  $scope.contractComp.storeEand = data.contractComp.storeEand ;
						  $scope.contractComp.storeNand = data.contractComp.storeNand ;
						  $scope.contractComp.storeSquare = data.contractComp.storeSquare ;
						  $scope.contractComp.membersNums = data.contractComp.membersNums;
						  $scope.contractComp.endingDate = data.contractComp.endingDate;

						  $scope.contractComp.commonServiceId = '' ;
						  $scope.contractComp.commonServiceName = '' ;
						  $scope.contractComp.commonChildServiceId = '';
						  $scope.contractComp.commonChildServiceName = '' ;
						  
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
									$scope.contractComp.showSfServices[selServiceObj.serChildId]=selServiceObj;
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
							  $scope.contractComp.districtIds=ids;
							 $scope.contractComp.districtNames=areaNames;
						  }
						 //增值服务
						  if(data.serviceAttr!=null){
							  $scope.contractComp.valueService.maintain=1;
							  $scope.contractComp.valueService.maintainAttr={};
							  for(var attrSerIndex in data.serviceAttr){
								  if(data.serviceAttr[attrSerIndex].serviceId==1){
									var  attrArrayIds = data.serviceAttr[attrSerIndex].attrIds.split(",");
										for(var aIndex in attrArrayIds){
										  var attrId =   attrArrayIds[aIndex];
										if(attrId==1){
											$scope.contractComp.valueService.maintainAttr.skin=1;
										}else if(attrId==2){
											$scope.contractComp.valueService.maintainAttr.wood=2;
										}else if(attrId==3){
											$scope.contractComp.valueService.maintainAttr.other=3;
										}
									  }
								  }else if(data.serviceAttr[attrSerIndex].serviceId==2){
									  $scope.contractComp.valueService.returnGoods=2;

								  }else if(data.serviceAttr[attrSerIndex].serviceId==3){
									  $scope.contractComp.valueService.yanghu=3;
								  }
							  }
						  }
						  //服务价格
						  if(data.cmProdPrices!=null){
							  for(var priceIndex in data.cmProdPrices){
								  var prodPrice={
											productId:data.cmProdPrices[priceIndex].productId,
											productName:data.cmProdPrices[priceIndex].productName,
											branchLinePrice:data.cmProdPrices[priceIndex].branchLinePrice/100,
											installPrice:data.cmProdPrices[priceIndex].installPrice/100
									};
									$scope.contractComp.prodPrices[data.cmProdPrices[priceIndex].productId]=prodPrice;
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
			
			//重新设置经纬度地址
			$scope.contractComp.storeEand = $("#storeEand").val();
			$scope.contractComp.storeNand = $("#storeNand").val();
			$scope.contractComp.storeAddr = $("#desPlaceDtl").val();
			
			$timeout(function(){
				commonService.postUrl("cmCtrCompanyInfoBO.ajax?cmd=saveCmContractCompany",$scope.contractComp,function(data){
					if(data != null && data != undefined && data != ""){
						if(data == 'Y'){
							//var rtn = confirm("是否需继续新增师傅");
							commonService.alert('你已成功修改公司信息');
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
						$scope.contractComp.destCity = cId;
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
			var param={provinceId:$scope.contractComp.provinceId};
			commonService.postUrl("staticDataBO.ajax?cmd=selectCity",param,function(data){
				if(data != null && data != undefined && data != ""){
					/*var autoComplete = new AutoComplete('o','auto',data,["name","nameAlpha"],["id","name"],function(data,cId){
						bill.queryRoateRuting(cId);
						$scope.contractComp.destCity = cId;
					});*/
					$scope.citys = data.items;

				}
			});
			//获取名称
			for(var provinceIndex in $scope.provinces){
				var item = $scope.provinces[provinceIndex];
				if($scope.contractComp.provinceId==item.id){
					$scope.contractComp.provinceName=item.name;
					$("#provinceName").val(item.name);

				}
			}///end
			
		},
		//查找子类别
		queryChildServiceType:function(){
			for(var i=0 ; i<$scope.oneLevelProductItmes.length;i++){
				if($scope.oneLevelProductItmes[i].prodId==$scope.contractComp.commonServiceId){
					$scope.contractComp.commonServiceName = $scope.oneLevelProductItmes[i].name;
				}
			}
			var param ={catalogId:$scope.contractComp.commonServiceId};
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
		//查找货品
		query4LevelProducts:function(catalogId){
			var param ={catalogId:catalogId};
			commonService.postUrl("productBO.ajax?cmd=queryProductCatalog",param,function(data){
				if(data != null && data != undefined && data != ""){
					$scope.level4Products = data.items;
				}
			});
			
		},
		selectCity:function(){
			if($scope.contractComp.provinceId==''){
				commonService.alert('请先选择服务省份');
				return;
			}
			if($scope.contractComp.cityId!=$scope.contractComp.tempCityId){
				$scope.contractComp.tempCityId = $scope.contractComp.cityId;
				//选择城市时，清空区域
				$scope.districtMapList={};
				$scope.contractComp.districtIds = '';
				$scope.contractComp.districtNames ='';
			}
		},
		
		queryDistrict:function(){
			//展示
			if($scope.contractComp.cityId==''){
				commonService.alert('请先选择服务城市');
				return;
			}
			$scope.isAreaShow=!$scope.isAreaShow;
			var param={cityId:$scope.contractComp.cityId};
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
			$scope.contractComp.districtIds = idTempList.join(',');
			$scope.contractComp.districtNames =nameTempList.join(',');
			$scope.isAreaShow=false;
		},
		///子类选择
		selChildService:function(){
			///货物总类
			if($scope.contractComp.commonServiceId==''){
				commonService.alert('请先选择服务类别');
				return;
			}
			
			for(var i=0 ; i<$scope.childServiceItmes.length;i++){
				if($scope.childServiceItmes[i].prodId==$scope.contractComp.commonChildServiceId){
					$scope.contractComp.commonChildServiceName = $scope.childServiceItmes[i].name;
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
		setProdPrice:function(obj){
			var productId = obj.serChildId;
			var productName = obj.serChildName;
			
			$scope.showProductPricePage=true;
			//
			$scope.prodPriceTemp.level2ProductId=productId;

			///产品信息
			var param ={catalogId:productId};
			commonService.postUrl("productBO.ajax?cmd=queryProductCatalog",param,function(data){
				if(data != null && data != undefined && data != ""){
					$scope.level3Products = data.items;
				}
			});
		},
		queryLevel4Products:function(productId){
			///产品信息
			var param ={catalogId:$scope.prodPriceTemp.curProductCalalogId};
			commonService.postUrl("productBO.ajax?cmd=queryProductCatalog",param,function(data){
				if(data != null && data != undefined && data != ""){
					$scope.level4Products = data.items;
				}
			});
		},
		//设置产品价格
		saveProdPrice:function(){
			var productId = $scope.prodPriceTemp.productId;
			if(productId==null||productId==''){
				commonService.alert("请选择货品");
				return;
			}
			for(var itemIndex  in $scope.level4Products){
				var productItem = $scope.level4Products[itemIndex];
				if(productId==productItem.prodId){
					$scope.prodPriceTemp.productName =productItem.name;
				}
			}
			
			if($scope.contractComp.prodPrices[productId]==null){
				var prodPrice={
						level2ProductId:$scope.prodPriceTemp.level2ProductId,
						productId:productId,
						productName:$scope.prodPriceTemp.productName,
						branchLinePrice:$scope.prodPriceTemp.branchLinePrice,
						installPrice:$scope.prodPriceTemp.installPrice
				};
				$scope.contractComp.prodPrices[productId]=prodPrice;
			}else{
				$scope.contractComp.prodPrices[productId].branchLinePrice=$scope.prodPriceTemp.branchLinePrice;
				$scope.contractComp.prodPrices[productId].installPrice=$scope.prodPriceTemp.installPrice;
			}
			
			//clear
			$scope.prodPriceTemp.productId='';
			$scope.prodPriceTemp.productName='';
			$scope.prodPriceTemp.branchLinePrice='';
			$scope.prodPriceTemp.installPrice='';
		},
		selSfServices:function(){
			if($scope.contractComp.commonChildServiceId==''){
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
			
			var serName = $scope.contractComp.commonServiceName;
			var serId = $scope.contractComp.commonServiceId;
			var serChildId = $scope.contractComp.commonChildServiceId;
			var serChildName = $scope.contractComp.commonChildServiceName;
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
			$scope.contractComp.showSfServices[serChildId]=selServiceObj;
			//
			//if($scope.contractComp.showSfServices.length>0){
				$scope.showServiceInfo=true;
				$scope.showServiceTitle=false;
			//}
		},
		//删除服务属性
		delSelService:function(obj){
			delete $scope.contractComp.showSfServices[obj];
			//判断是否都删除完
			var isAllDel = true;
			for(var objServ in $scope.contractComp.showSfServices){
				if($scope.contractComp.showSfServices[objServ] != ''){
					isAllDel = false;
				}
			}
			if(isAllDel){
				$scope.showServiceTitle=true;
			}
			///删除属下的货品费用信息
			for(var prodPriceItemIndex  in $scope.contractComp.prodPrices){
				var level2ProductId = $scope.contractComp.prodPrices[prodPriceItemIndex].level2ProductId;
				if(level2ProductId ==obj){
					delete $scope.contractComp.prodPrices[prodPriceItemIndex];
				}
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
			if($scope.contractComp.name==''){
				commonService.alert('请输入师傅姓名');
				return false;
			}
			if($scope.contractComp.phone==''){
				commonService.alert('请输入师傅账户');
				return false;
			}
			if($scope.contractComp.largestAcceptOrder==''){
				commonService.alert('请输入最大接单数');
				return false;
			}
			if($scope.contractComp.cityId==''){
				commonService.alert('请选择服务城市');
				return false;
			}
			return true;
		},
		doClose:function(){
			commonService.closeToParentTab();
		},
		contractComp:{
			contractCompanyName:'',
			contractTenantCode:'',
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
			endingDate:'',
			prodPrices:{},
			showSfServices:{},
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