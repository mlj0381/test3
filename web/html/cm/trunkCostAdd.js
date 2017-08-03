 var trunkAddApp = angular.module("trunkAddApp", ['commonApp']);
 trunkAddApp.controller("trunkAddCtrl", ['$http',"$scope","commonService","$timeout",function($http,$scope,commonService,$timeout){ 
	 var trunkAdd = {
			 init:function(){
				 $scope.Flag = true;
				 this.bindEvent();
				 this.selectLineOrg();
				 this.selectProvince();
				 this.cityAll();
				 $scope.saveItems = [];
			 },
			 bindEvent:function(){
				 $scope.saveInfo = this.saveInfo;
				 $scope.saveInfo.businessOrgId = -1; //默认查询标准
				 $scope.selectCity = this.selectCity;
				 $scope.selectCountry = this.selectCountry;
				 $scope.doSaveTable = this.doSaveTable;
				 $scope.saveItems = this.saveItems;
				 $scope.selectData = this.selectData;
				 $scope.delItems = this.delItems;
				 
				 $scope.selectChange = this.selectChange;
				 $scope.queryItems = this.queryItems;
				 $scope.doSave =  this.doSave;
	//dhl			 $scope.heightChange =  this.heightChange;
			 },
			 //自适应
//dhl			 heightChange : function(){
//				 var length = $scope.saveItems.length;
//				 if(length >= 7){
//					 $("#heightChange").css("height","36px");
//				 }else{
//					 $("#heightChange").css("height","37px");
//				 }
//			 },
			 //1：专线2商家
			 selectChange : function(orgId,type){
				 //console.log(orgId);
				 
                 if(type == 1){
					 
				 }else{
					 
				 }
                 if($scope.saveItems.length > 0){
    				 commonService.confirm("是否要保存修改的数据!",function(){
    					 $scope.doSave();
    					//保存数据
    					 $timeout(function(){
    						 $scope.queryItems($scope.saveInfo.lineOrgId,$scope.saveInfo.businessOrgId);
    						 $scope.$apply();
    					 },10);
						
    				 },function(){
    					 //不保存数据
    					 $timeout(function(){
    						 $scope.queryItems($scope.saveInfo.lineOrgId,$scope.saveInfo.businessOrgId);
    						 $scope.$apply();
    					 },10);
    				 });
                 }else{
                	 $scope.queryItems($scope.saveInfo.lineOrgId,$scope.saveInfo.businessOrgId);
                 }
                 //区数据置空
                 trunkAdd.clear();
                 if(type == 1){
                	 trunkAdd.selectBusinessOrg();
                 }
			 },
			 //回选支付方式、和费用图片
			 huxuanshuju : function(){
				 var arrs = $scope.saveItems;
				 if(arrs.length > 0){
					 var bc = arrs[0];
					 var costPic = bc.costPic;
					 var costPicUrl = bc.costPicUrl;
					 var costInstallPic = bc.costInstallPic;
					 var costInstallPicUrl = bc.costInstallPicUrl;
					 var paymentType = bc.paymentType;
					 
					 if(costPic != '' && costPic != undefined && costPic != null){
						 $scope.pic1.initDate(costPic);
					 }
					 
                     if(costInstallPic != '' && costInstallPic != undefined && costInstallPic != null){
                    	 $scope.pic2.initDate(costInstallPic);
					 }
                    $scope.saveInfo.paymentType = paymentType;
				 }
			 },
			 //清除数据
			 clear : function(){
		        $scope.districts = [];
                 $scope.saveInfo.province = -1;
                 $scope.citys = [];
                 trunkAdd.cityAll();
                 $scope.pic1.clean();
                 $scope.pic2.clean();
                 $scope.saveInfo.volumePriceDouble = null;
                 $scope.saveInfo.weightPriceDouble = null;
                 $scope.saveInfo.countPriceDouble = null;
                 $scope.saveInfo.paymentType = "-1";
			 },
			 
			 //查询干线费用配置信息
			 queryItems : function(lineOrgId,businessOrgId){
			     $scope.saveItems =[];
			     var param = {};
			     if($scope.saveItems.businessOrgId == -2){
					return false;
				  }
			     param.orgId = lineOrgId;
			     param.businessOrgId = businessOrgId;
			     param._ALLEXPORT=1;
			     var url = "cmTrunkCostBO.ajax?cmd=doQueryCmTrunkCost";
			     commonService.postUrl(url,param,function(data){
						if(data!=null && data!=undefined && data!=""){
							var arrays = new Array();
							
							var items = data.items;
							for(var i = 0;i<items.length;i++){
								var item = items[i];
								item.id = (i+1)+"";
								arrays.push(item);
							}
							$scope.saveItems = arrays;
							trunkAdd.huxuanshuju();
//dhl							$scope.heightChange();
			 	    	}
				 });
  
			 },
			 selectData : function(id){
				var checkStr = $("#checkbox"+id);
				
				var aa = document.getElementById("checkbox"+id).checked;
				if(aa){
					document.getElementById("checkbox"+id).checked = false;
				}else{
					document.getElementById("checkbox"+id).checked = true;
                 }
			},
			 
			 saveInfo : {},
			 saveItems :{},
			 //获取协议商家
			 selectBusinessOrg:function(){
				    var param = {};
				    param._ALLEXPORT = 1;
				    param.orgId = $scope.saveInfo.lineOrgId;
					var url = "cmTrunkCostBO.ajax?cmd=doQueryMerchant";
					commonService.postUrl(url,param,function(data){
						if(data!=null && data!=undefined && data!=""){
								$scope.businessOrgs = data.items;
								$scope.businessOrgs.unshift({tenantId:"-1",orgId:"-1",orgName:"标准费用（对所有的商家有效）"});
								$scope.businessOrgs.unshift({tenantId:"-2",orgId:"-2",orgName:""});
								$scope.saveInfo.businessOrgId = "-2";
			 	    	}
					});
			},
			 //获取归属网点
			 selectLineOrg:function(){
				    var param = {};
					var url = "cmTrunkCostBO.ajax?cmd=doQueryZxLine";
					commonService.postUrl(url,param,function(data){
						if(data!=null && data!=undefined && data!=""){
								$scope.lineOrgs = data.items;
								$scope.saveInfo.lineOrgId = data.items[0].orgId;
								var lineOrgId = data.items[0].orgId;
								trunkAdd.selectBusinessOrg();
								if(!$scope.Flag){
									//初始化页面不执行
									trunkAdd.queryItems(lineOrgId,$scope.saveInfo.businessOrgId);
								}
								$scope.Flag = false;
								
			 	    	}
					});
			},
			 
			 cityAll : function(){
				 $scope.citys = [];
				 $scope.m = {};
				 $scope.m.id= "-1";
				 $scope.m.name= "全部";
				 $scope.citys.push( $scope.m);
				 $scope.saveInfo.city = "-1";
			 },
			 selectProvince:function(){
					var param = {"isAll":"Y"};
					var url = "staticDataBO.ajax?cmd=selectProvince";
					commonService.postUrl(url,param,function(data){
						if(data!=null && data!=undefined && data!=""){
								$scope.provinces = data.items;
								$scope.saveInfo.province = data.items[0].id;
			 	    	}
					});
			},
			 selectCity:function(provinceId){
					if(parseInt(provinceId)>0){
						var param = {"isAll":"Y","provinceId":provinceId};
						var url = "staticDataBO.ajax?cmd=selectCity";
						commonService.postUrl(url,param,function(data){
							if(data!=null && data!=undefined && data!=""){
									$scope.citys = data.items;
									$scope.saveInfo.city = data.items[0].id;
									$scope.districts = [];
				 	    	}
						});
					}else{
						trunkAdd.cityAll();
						$scope.saveInfo.city = "-1";
						$scope.districts = [];
					}
				},
			
			selectCountry:function(cityId){
				if(parseInt(cityId)>0){
					var param = {"cityId":cityId};
					var url = "staticDataBO.ajax?cmd=selectDistrict";
					commonService.postUrl(url,param,function(data){
						// 成功执行
						if(data!=null && data!=undefined && data!=""){
								$scope.districts = data.items;
			 	    	}
					});
				}else{
					$scope.districts = [];
				}
			},
			//获取所有选中的区
			getCheckAdIds :	function () {  
		        var districts = [];  
		        $("input:checkbox[name=check_name]:checked").each(function(i){  
		        	districts.push($(this).val());
		        });  
		        
		        return districts;
		    },
			
			doSaveTable : function(){
				var  saveItems = $scope.saveItems;
				if($scope.saveInfo.businessOrgId == undefined  || $scope.saveInfo.businessOrgId == "-2"){
					commonService.alert("请选择发货商家！");
					return false;
				}
				if($scope.saveInfo.province == undefined || $scope.saveInfo.province == -1 || $scope.saveInfo.province == null){
					
					if($scope.saveInfo.businessOrgId == -1){
						
					}else{
						commonService.alert("非标准配置需要选择省份！");
						return false;
					}
					
				}
				var weightTrue = false;
				if($scope.saveInfo.weightPriceDouble == undefined || $scope.saveInfo.weightPriceDouble ==null || $scope.saveInfo.weightPriceDouble == ''){
					weightTrue = true;
				}
				var volumeTrue = false;
				if($scope.saveInfo.volumePriceDouble == undefined || $scope.saveInfo.volumePriceDouble ==null || $scope.saveInfo.volumePriceDouble == ''){
					volumeTrue = true;
				}
				var countTrue = false;
				if($scope.saveInfo.countPriceDouble == undefined || $scope.saveInfo.countPriceDouble ==null || $scope.saveInfo.countPriceDouble == ''){
					countTrue = true;
				}
				if(countTrue && volumeTrue && weightTrue ){
					commonService.alert("至少要输入1种费用计算价格！");
					return false;
				}
				
				
				var districts = trunkAdd.getCheckAdIds();
				//新值
				if(districts.length == 0){
					
					var saveMap = {};
					saveMap.id = "1";				
					saveMap.orgId = $scope.saveInfo.lineOrgId;
					saveMap.businessOrgId = $scope.saveInfo.businessOrgId;
					saveMap.paymentType = $scope.saveInfo.paymentType;
					var pic1 = $scope.pic1.get();
					var pic2 = $scope.pic2.get();
					saveMap.costPic = pic1.flowId;
					saveMap.costPicUrl = pic1.flowUrl;
					saveMap.costInstallPic = pic2.flowId;
					saveMap.costInstallPicUrl = pic2.flowUrl;
					saveMap.province = $scope.saveInfo.province+"";
					saveMap.city = $scope.saveInfo.city+"";
					saveMap.provinceName = trunkAdd.getProvinceName($scope.saveInfo.province);
					saveMap.cityName = trunkAdd.getCityName($scope.saveInfo.city);
					saveMap.weightPriceDouble = $scope.saveInfo.weightPriceDouble;
					saveMap.volumePriceDouble = $scope.saveInfo.volumePriceDouble;
					saveMap.countPriceDouble = $scope.saveInfo.countPriceDouble;
					saveMap.weightPrice = $scope.saveInfo.weightPriceDouble != undefined && $scope.saveInfo.weightPriceDouble != null &&$scope.saveInfo.weightPriceDouble != '' ? Math.round($scope.saveInfo.weightPriceDouble * 100) : null;
					saveMap.volumePrice = $scope.saveInfo.volumePriceDouble != undefined && $scope.saveInfo.volumePriceDouble != null &&$scope.saveInfo.volumePriceDouble != '' ? Math.round($scope.saveInfo.volumePriceDouble * 100) : null;
					saveMap.countPrice = $scope.saveInfo.countPriceDouble != undefined && $scope.saveInfo.countPriceDouble != null &&$scope.saveInfo.countPriceDouble != '' ? Math.round($scope.saveInfo.countPriceDouble * 100) : null;
					saveMap.county = "";
					saveMap.countyName = "";
					
					
 					var bol = trunkAdd.dealAgainShuju(saveMap); //过滤数据（和处理重复数据）

				}else{
					for(var i = 0;i<districts.length;i++){
						var saveMap = {};
//						saveMap.id = (i+1)+"";
						saveMap.orgId = $scope.saveInfo.lineOrgId;
						saveMap.businessOrgId = $scope.saveInfo.businessOrgId;
						saveMap.paymentType = $scope.saveInfo.paymentType;
						var pic1 = $scope.pic1.get();
						var pic2 = $scope.pic2.get();
						saveMap.costPic = pic1.flowId;
						saveMap.costPicUrl = pic1.flowUrl;
						saveMap.costInstallPic = pic2.flowId;
						saveMap.costInstallPicUrl = pic2.flowUrl;
						
						saveMap.province = $scope.saveInfo.province+"";
						saveMap.city = $scope.saveInfo.city+"";
						
						saveMap.provinceName = trunkAdd.getProvinceName($scope.saveInfo.province);
						saveMap.cityName = trunkAdd.getCityName($scope.saveInfo.city);
						
						saveMap.weightPriceDouble = $scope.saveInfo.weightPriceDouble;
						saveMap.volumePriceDouble = $scope.saveInfo.volumePriceDouble;
						saveMap.countPriceDouble = $scope.saveInfo.countPriceDouble;
						
						saveMap.weightPrice = $scope.saveInfo.weightPriceDouble != undefined && $scope.saveInfo.weightPriceDouble != null &&$scope.saveInfo.weightPriceDouble != '' ? Math.round($scope.saveInfo.weightPriceDouble * 100) : null;
						saveMap.volumePrice = $scope.saveInfo.volumePriceDouble != undefined && $scope.saveInfo.volumePriceDouble != null &&$scope.saveInfo.volumePriceDouble != '' ? Math.round($scope.saveInfo.volumePriceDouble * 100) : null;
						saveMap.countPrice = $scope.saveInfo.countPriceDouble != undefined && $scope.saveInfo.countPriceDouble != null &&$scope.saveInfo.countPriceDouble != '' ? Math.round($scope.saveInfo.countPriceDouble * 100) : null;
						
						var district = districts[i];
						saveMap.county = district;
						saveMap.countyName = trunkAdd.getCountyName(district);
						
						var bol = trunkAdd.dealAgainShuju(saveMap); //过滤数据（和处理重复数据）
						
					}
				}
				//去除选择
				$("input:checkbox[name=checkBox_]:checked").each(function(i){  
		        	$(this).attr("checked",false);
		        }); 
//		dhl		$scope.heightChange();
		    },
		    
		    dealAgainShuju : function(saveMap){
		    	//console.log(saveMap);
		    	//比较旧数据（是否存在 数据一致的情况（存在先移除））{只比较省市区是否相同}（因为选择了归属网点或商家都会刷新页面）
		    	var saveItems = $scope.saveItems;
//		    	//console.log(saveItems);
		        var businessOrgId = $scope.saveInfo.businessOrgId;
		        
		        var m = {};
		    	for(var i = 0;i<saveItems.length;i++){
		           if(businessOrgId == -1 || businessOrgId == "-1"){
		        	   var item = saveItems[i];
		        	      //(更新数据)
		        	   if(saveMap.province == item.province && saveMap.city == item.city && saveMap.county == item.county ){
		        		   m.isBoolean = false;
		        		   m.id = saveItems[i].id;
		        	   }
	        	
		           }else{
		        	   //省市区相同(更新数据)
		        	   var item = saveItems[i];
		        	   //console.log(item);
		        	   if(saveMap.province == item.province && saveMap.city == item.city && saveMap.county == item.county ){
		        		   m.isBoolean = false;
		        		   m.id = saveItems[i].id;
		        	   }
		        	   
		           }
		    	} 
		    	var addArray = new Array();
		    	saveMap.id="1";
		    	addArray.push(saveMap);
		    	var add = 2;
				for(var i = 0;i<saveItems.length;i++){
					if(m.id != saveItems[i].id){
						var m1 = saveItems[i];
							m1.id = add+"";
							addArray.push(m1);
							add = add + 1;
					}
				}
				$scope.saveItems = addArray;

		    },
		    //删除列表数据
		    delItems : function(){
		    	var saveItems = $scope.saveItems;
		    	var delIds = new Array();
		    	$("input:checkbox[name=checkBox_]:checked").each(function(i){  
		        	delIds.push($(this).val());
		        }); 
		    	
		    	
		    	if(delIds.length == 0){
		    		commonService.alert("请选择删除的数据！");
					return false;
		    	}else{
		    		commonService.confirm("确认要删除选择的"+delIds.length+"条数据!",function(){
		    			$timeout(function(){
		    				var bb = new Array();
					    	for(var i = 0;i<saveItems.length;i++){
					    		var isTrue = true;
					    		for(var j = 0;j < delIds.length;j++){
					    			if(delIds[j] === saveItems[i].id ){
					    				isTrue = false;
					    			}
					    		}
					    		
					    		if(isTrue){
					    			bb.push(saveItems[i]);
					    		}
					    		
					    	}
					    	var bout = new Array();
					    	for(var i = 0;i<bb.length;i++){
					    		bb[i].id=(i+1)+"";
					    		bout.push(bb[i]);
					    	}
					    	$scope.saveItems = bout;
//	dhl				    	$scope.heightChange();
		    			},10);
		    		});
		    		

		    	}
		    	
		    	
		    },
		   
		    doSave : function(){
		    	var saveInfo = $scope.saveItems;
		    	if(saveInfo.length == 0){
		    		commonService.alert("请新增列表数据！");
					return false;
		    	}
		    	
		    	var param = {};
		    	var toFrom = JSON.stringify(saveInfo);
		    	param.saveString = toFrom;
				var url = "cmTrunkCostBO.ajax?cmd=doSave";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
						trunkAdd.clear();
						$scope.saveInfo.businessOrgId = $scope.businessOrgs[0].orgId;
						commonService.confirm("保存成功,确定继续新增！",function(){
							$scope.saveItems = [];
							$scope.$apply();
							//trunkAdd.queryItems($scope.saveInfo.lineOrgId,$scope.businessOrgs[0].orgId);
						},function(){
							trunkAdd.close();
						});
		 	    	}
				});
		    	
		    },
			close:function(){
	    		commonService.closeToParentTab(true);
	    	},
		    getProvinceName : function(province){
		    	var provinces = $scope.provinces;
                if(provinces == "-1" || provinces == -1){
                	return "";
                }
				for(var i = 0;i<provinces.length;i++){
					 if(provinces[i].id == province){
						 return provinces[i].name;
					 }
				}
			},
		    getCityName : function(city){
		    	var citys = $scope.citys;
                if(city === "-1" || city == -1){
                	return "";
                }
				for(var i = 0;i<citys.length;i++){
					 if(citys[i].id == city){
						 return citys[i].name;
					 }
				}
			},
			getCountyName : function(district){
		    	var districts = $scope.districts;
                if(district === "-1" || district == -1){
                	return "";
                }
				for(var i = 0;i<districts.length;i++){
					 if((districts[i].id+"") == district){
						 return districts[i].name;
					 }
				}
			},

			
	 };
	 
	 trunkAdd.init();
    
 }]);
