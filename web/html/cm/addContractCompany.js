var proveCashApp = angular.module("AddContractCompApp", ['commonApp']);
proveCashApp.controller("AddContractCompCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var addInstallUser={
		init:function(){
			this.bindEvent();
			this.initStaticData();
			$scope.isAreaShow=false;
			$scope.showServiceType=false;//显示服务类型
			$scope.showServiceTitle=true;
			$scope.showServiceInfo=false;
			$scope.showProductPricePage=false;
			$scope.showCompanyPage=false;
			
			$scope.serviceType={};
			$scope.districtIds={};
			$scope.districtMapList={};
			$scope.query={};
			//$scope.maintain.valueService={};
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
			$scope.selectOne =  this.selectOne;
			$scope.delSelService = this.delSelService;//删除服务
			$scope.checkSaveData = this.checkSaveData;
			$scope.doClose = this.doClose;
			$scope.setProdPrice = this.setProdPrice;
			$scope.saveProdPrice = this.saveProdPrice;
			$scope.queryCompanyInfoList = this.queryCompanyInfoList;
			$scope.selectCompany=this.selectCompany; //选择公司
			$scope.queryLevel4Products = this.queryLevel4Products;
			$scope.selectAllDistrict = this.selectAllDistrict;
			$scope.selectCity = this.selectCity;
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
							commonService.alert("你已成功添加公司资料");
							//$scope.clear();
							commonService.closeToParentTab(true);
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
		doClose:function(){
			commonService.closeToParentTab();
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
		//区域全选
		selectAllDistrict:function(){
			for(var districtIndex in $scope.districts){
				eval(" $scope.districtIds.district_"+ $scope.districts[districtIndex].id+"='drop_hover'");
				$scope.districtMapList[$scope.districts[districtIndex].id]={"id":$scope.districts[districtIndex].id,"name":$scope.districts[districtIndex].name};
			}
		},
		selectCompany:function(obj){
			if($("input[name='check-1']:checked").length!=1){
				alert("请选择一条数据!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=$(this).val();
					var obj = JSON.parse(data)
					$scope.contractComp.contractTenantCode =obj.tenantCode;
					$scope.contractComp.contractCompanyName = obj.deptName;
				}
			});
			$scope.showCompanyPage=false;
		},
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
		queryCompanyInfoList:function(){
			$scope.showCompanyPage = true;
			$timeout($scope.page.load({
							url:'cmCompanyBO.ajax?cmd=queryCompanyList',
							params:$scope.query
						}),500);
		},
		selectOne : function(batchNum,strId){
			if(document.getElementById(strId+batchNum).checked && document.getElementById(strId+batchNum) != undefined){
				document.getElementById(strId+batchNum).checked=false;
			}else{
				document.getElementById(strId+batchNum).checked=true;
			}
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
		//查询司机货品
		queryLevel4Products:function(productId){
			///产品信息
			var param ={catalogId:$scope.prodPriceTemp.curProductCalalogId};
			commonService.postUrl("productBO.ajax?cmd=queryProductCatalog",param,function(data){
				if(data != null && data != undefined && data != ""){
					$scope.level4Products = data.items;
				}
			});
		},
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
		
		serviceType:{
			sendName :'',
			sendValue :'',
			installName :'',
			installValue :'',
			sendAndInstallName :'',
			sendAndInstallValue :'',
		},
		checkSaveData:function(){
			if($scope.contractComp.contractCompanyName==''){
				commonService.alert('请输入合同公司名称');
				return false;
			}
			if($scope.contractComp.tenantCode==''){
				commonService.alert('请选择公司账户');
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