var businessAreaApp = angular.module("businessAreaApp", ['commonApp']);
businessAreaApp.controller("businessAreaCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	$scope.query={};
	$scope.orgInfo={};
	$scope.isFlag=true;
	$scope.dataFrom={};
	$scope.province=[];
	$scope.city=[];
	$scope.cityDistrict=[];
	$scope.count=0;
	$scope.choiceCitys=[];
	$scope.selectedCity = [];
	$scope.selectedDistrict = [];
	$scope.isAddOrUp=1;
	$scope.dataTemp={};
	$scope.saveTitle="保存";
	$scope.isTopOrg = false;
	var businessArea={
		init:function(){
			this.bindEvent();
			this.queryOrg();
			this.getProvince();
			this.doQuery();
			this.isTopOrg();
		},
		bindEvent:function(){
			$scope.doQuery=this.doQuery;
			$scope.doAdd=this.doAdd;
			$scope.doCancel=this.doCancel;
			$scope.add=this.add;
			$scope.changeProvince=this.changeProvince;
			$scope.getDistrict=this.getDistrict;
			$scope.forDistrict=this.forDistrict;
			$scope.isSelectedCity=this.isSelectedCity;
			$scope.updateSelectionCity=this.updateSelectionCity;
			$scope.updateSelectedCity=this.updateSelectedCity;
			$scope.isSelectedAll=this.isSelectedAll;
			$scope.selectAll=this.selectAll;
			$scope.isSelectedDistrict=this.isSelectedDistrict;
			$scope.updateSelectionDistrict=this.updateSelectionDistrict;
			$scope.updateSelectedDistrict=this.updateSelectedDistrict;
			$scope.selectAllCity=this.selectAllCity;
			$scope.doUpdate=this.doUpdate;
			$scope.doReset=this.doReset;
			$scope.isChoiceCitys=this.isChoiceCitys;
			$scope.updateChoiceCitys=this.updateChoiceCitys;
			$scope.eventChoiceCitys=this.eventChoiceCitys;
		},
		isTopOrg:function(){
			var url = "staticDataBO.ajax?cmd=isTopOrg";
			commonService.postUrl(url,"",function(data){
				if(data!=null && data!=undefined && data!=""){
					if (data == "Y") {
						$scope.isTopOrg = true;
					}else if(data == "N"){
						$scope.isTopOrg = false;
					}
	 	    	}
			});
		},
		/**网点列表查询*/
		queryOrg:function(){
//			commonService.postUrl("routeBO.ajax?cmd=queryRoateTowards","",function(data){
//				if(data != null && data != undefined && data != ""){
//					$scope.orgInfo = data;
//				}
//			});
			//var param = {"collectType":4,"addType":1};
			var url = "staticDataBO.ajax?cmd=getOrgan";
			commonService.postUrl(url,"",function(data){
				if(data!=null && data!=undefined && data!=""){
					if(data!=null && data!=undefined && data.items!=null && data.items!=undefined && data.items.length>0){
						$scope.orgInfo = data;
					}
	 	    	}
			});
		},
		doQuery:function(){
			$scope.query.provinceId = $scope.source.chooseCityId;
			$scope.query.cityId = $scope.source.chooseRegionId;
			$scope.query.countyId = $scope.source.chooseCountyId;
			var url="routeAreaRelCfgBO.ajax?cmd=doQueryPage";
			$scope.page.load({
				url:url,
				params:$scope.query,
				callBack:"setContentHegthDelay"
			});
		},
		doAdd:function(){
			$scope.isAddOrUp=1;
			$scope.isFlag=false;
			setContentHeight();
		},
		add:function(){
			if($scope.dataFrom.relName==null || $scope.dataFrom.relName==undefined || $scope.dataFrom.relName==""){
				 commonService.alert("请输入区域名称！");
				 return ;
			}
			if($scope.dataFrom.destOrgId==-1 || $scope.dataFrom.destOrgId==undefined || $scope.dataFrom.destOrgId==""){
				 commonService.alert("请选择归属网点！");
				 return ;
			}
			if($scope.dataFrom.provinceId==-1 || $scope.dataFrom.provinceId==undefined || $scope.dataFrom.provinceId==""){
				 commonService.alert("请选择省份！");
				 return ;
			}
//			if($scope.selectedDistrict==null || $scope.selectedDistrict.length<=0){
//				 commonService.alert("请选择区！");
//				 return ;
//			}
			var allCitys = $scope.choiceCitys;
			if($scope.isAddOrUp==2){
				if(($scope.selectedDistrict == null || $scope.selectedDistrict.length<=0) && $scope.choiceCitys <=0){
					commonService.alert("请选择一个城市或区域");
					return false;
				}
				if($scope.selectedDistrict.length>0){
					allCitys = [];
				}
			}else{
				if(($scope.selectedDistrict == null || $scope.selectedDistrict.length<=0) && $scope.choiceCitys <=0){
					commonService.alert("至少选择一个城市或区域");
					return false;
				}
			}
			$scope.dataFrom.isSeaTransport = $scope.isSeaTransport == true ? 1 : 0;//是否海运
			$scope.saveTitle="正在提交...";
			$scope.dataFrom.selectedDistrict=$scope.selectedDistrict.join();
			$scope.dataFrom.choiceCitys = allCitys.join();
			var url = "routeAreaRelCfgBO.ajax?cmd=getRouteAreaRelCfgOrg";
			var param = $scope.dataFrom;
			commonService.postUrl(url,param,function(data){
				if(data!=null && data!=undefined 
						&& data.routeAreaRelCfgList!=null 
						&& data.routeAreaRelCfgList!=undefined 
						&& data.routeAreaRelCfgList.length>0){
					var relId="";
					var str="对应的县区信息已经存在,";
					for(var i=0;i<data.routeAreaRelCfgList.length;i++){
						var obj = data.routeAreaRelCfgList[i];
						if(i==0){
							relId+=obj.relId;
							if(obj.countyName != null && obj.countyName != undefined && obj.countyName != ""){
								str+= "[区域名称："+obj.relName+"，区："+obj.countyName+"]";
							}else{
								str+= "[区域名称："+obj.relName+"，市："+obj.cityName+"]";
							}
						}else{
							relId+=","+obj.relId;
							if(obj.countyName != null && obj.countyName != undefined && obj.countyName != ""){
								str+= "[区域名称："+obj.relName+"，区："+obj.countyName+"]";
							}else{
								str+= "[区域名称："+obj.relName+"，市："+obj.cityName+"]";
							}
						}
					}
					str+="。是否删除这些信息，保存新的信息。";
					var bol = false;
					if($scope.isAddOrUp==2){
						//修改
						for(var i=0;i<$scope.selectedDistrict.length;i++){
							if(Number($scope.selectedDistrict[i])==Number($scope.dataTemp.countyId)){
								bol=true;
								break;
							}
						}
					}
					if(bol){
						var url = "routeAreaRelCfgBO.ajax?cmd=batchEdit";
						var param = $scope.dataFrom;
						commonService.postUrl(url,param,function(data){
							commonService.alert("操作成功！");
							$scope.doQuery();
							$scope.doCancel();
							$scope.saveTitle="保存";
			        	});
					}else{
						commonService.confirm(str,function(){
							var url = "routeAreaRelCfgBO.ajax?cmd=batchDelete";
							var paramdele = {"relId":relId};
							commonService.postUrl(url,paramdele,function(data){
								var url = "routeAreaRelCfgBO.ajax?cmd=batchEdit";
								var param = $scope.dataFrom;
								commonService.postUrl(url,param,function(data){
									commonService.alert("操作成功！");
									$scope.doQuery();
									$scope.doCancel();
									$scope.saveTitle="保存";
					        	});
				        	});
	                    });
						$scope.saveTitle="保存";
					}
				}else{
					var url = "routeAreaRelCfgBO.ajax?cmd=batchEdit";
					var param = $scope.dataFrom;
					commonService.postUrl(url,param,function(data){
						commonService.alert("操作成功！");
						$scope.doQuery();
						$scope.doCancel();
						$scope.saveTitle="保存";
		        	});
				}
        	});
		},
		doUpdate:function(){
			var selectedDate=$scope.page.getSelectData();
			if(selectedDate==undefined || selectedDate.length==0 || selectedDate.length>1){
				commonService.alert("请选择一条数据！");
				return ;
			}
			selectedDate=selectedDate[0];
			var url = "routeAreaRelCfgBO.ajax?cmd=doQuery";
			var param = {"reLId":selectedDate.reLId};
			commonService.postUrl(url,param,function(data){
				$scope.dataFrom.relId=data.relId;
				$scope.dataFrom.relName=data.relName;
				$scope.dataFrom.destOrgId=data.destOrgId;
				$scope.dataFrom.provinceId=data.provinceId;
				var selectedDistrict = null;
				if(data.countyId != null && data.countyId != undefined && data.countyId != ""){
					selectedDistrict = [data.countyId];
				}
				$scope.changeProvince(selectedDistrict);
				$scope.isAddOrUp=2;
				$scope.isFlag=false;
				$scope.dataTemp=data;
				//城市
				$scope.updateChoiceCitys('add',data.cityId);
				$scope.isSeaTransport = data.isSeaTransport == 1 ? true : false;
        	});
		},
		doCancel:function(){
			$scope.isFlag=true;
			$scope.dataFrom={"provinceId":"-1","orgId":"-1"};
			$scope.city=[];
			$scope.selectedCity=[];
			$scope.choiceCitys=[];
			$scope.selectedDistrict = [];
			$scope.cityDistrict=[];
			$scope.dataTemp={};
			$scope.count=0;
			$scope.saveTitle="保存";
			$scope.isSeaTransport =false;
		},
		getProvince:function(){
			var url = "staticDataBO.ajax?cmd=selectProvince";
			commonService.postUrl(url,"",function(data){
				$scope.province=data.items;
        	});
		},
		changeProvince:function(selectedDistrict){
			$scope.selectedCity=[];
			if(selectedDistrict!=null && selectedDistrict!=undefined){
				$scope.selectedDistrict = selectedDistrict;
			}else{
				$scope.selectedDistrict = [];
			}
			$scope.cityDistrict=[];
			$scope.count=0;
			if($scope.dataFrom.provinceId!=-1){
				var url = "staticDataBO.ajax?cmd=selectCity&provinceId="+$scope.dataFrom.provinceId;
				commonService.postUrl(url,"",function(data){
					$scope.city=data.items;
					$scope.getDistrict();
	        	});
			}else{
				$scope.selectedCity=[];
				$scope.selectedDistrict = [];
				$scope.city=[];
				$scope.cityDistrict=[];
				$scope.count=0;
			}
		},
		getDistrict:function(){
			if($scope.count<$scope.city.length){
				$scope.forDistrict($scope.city[$scope.count]);
			}
		},
		forDistrict:function(data){
			var dataCity={};
			dataCity.city=data;
			var url="staticDataBO.ajax?cmd=selectDistrict&cityId="+data.id;
			commonService.postUrl(url,"",function(data){
				dataCity.district=data.items;
				$scope.cityDistrict.push(dataCity);
				$scope.count++;
				$scope.getDistrict();
        	});
		},
		updateSelectionCity:function($event, id){
		    var checkbox = $event.target;
		    var action = (checkbox.checked ? 'add':'remove');
		    $scope.updateSelectedCity(action,id);
		},
		eventChoiceCitys:function($event,id){
			 var checkbox = $event.target;
			 var action = (checkbox.checked ? 'add':'remove');
			 $scope.updateChoiceCitys(action,id);
		},
		//单独选择市
		updateChoiceCitys:function(action,id){
			if(action == 'add' && $scope.choiceCitys.indexOf(id) == -1){
				 if($scope.isAddOrUp==2){
					   $scope.choiceCitys=[];
				 }
				 $scope.choiceCitys.push(id);
		    }
		    if(action == 'remove' && $scope.choiceCitys.indexOf(id)!=-1){
               var idx = $scope.choiceCitys.indexOf(id);
               $scope.choiceCitys.splice(idx,1);
		    }
		},
		//选择城市下 市和所有区域
		updateSelectedCity:function(action, id){
			if(action == 'add' && $scope.selectedCity.indexOf(id) == -1){
               $scope.selectedCity.push(id);
		    }
		    if(action == 'remove' && $scope.selectedCity.indexOf(id)!=-1){
               var idx = $scope.selectedCity.indexOf(id);
               $scope.selectedCity.splice(idx,1);
		    }
		    $scope.selectAllCity(action, id);
		    $scope.updateChoiceCitys(action,id);
		},
		isSelectedCity:function(id){
			var bool = true;
			for (var i = 0; i < $scope.cityDistrict.length; i++) {
	    		 var entity = $scope.cityDistrict[i].city;
		         if(entity.id==id){
		        	 for (var j = 0; j < $scope.cityDistrict[i].district.length; j++) {
		        		 var obj = $scope.cityDistrict[i].district[j];
		        		 if($scope.selectedDistrict.indexOf(obj.id)==-1){
		        			 bool = false;
		        		 }
		        	 }
		         }   
		    }
			return bool;
		},
		isSelectedAll:function () {
			if($scope.selectedCity.length === $scope.cityDistrict.length){
				return true;
			}
	        return false;
	    },
	    //所有都选
	    selectAll:function ($event) {
	        var checkbox = $event.target;
	        var action = (checkbox.checked ? 'add' : 'remove');
	        for (var i = 0; i < $scope.cityDistrict.length; i++) {
	            var entity = $scope.cityDistrict[i];
	            $scope.updateSelectedCity(action, entity.city.id);
	        }
	    },
	    //区域全选
	    selectAllCity:function(action,id){
	    	 for (var i = 0; i < $scope.cityDistrict.length; i++) {
	    		 var entity = $scope.cityDistrict[i].city;
		         if(entity.id==id){
		        	 for (var j = 0; j < $scope.cityDistrict[i].district.length; j++) {
		        		 var obj = $scope.cityDistrict[i].district[j];
		        		 $scope.updateSelectedDistrict(action,obj.id);
		        	 }
		         }   
		     }
	    },
	    updateSelectionDistrict:function($event, id){
		    var checkbox = $event.target;
		    var action = (checkbox.checked ? 'add':'remove');
		    $scope.updateSelectedDistrict(action,id);
		},
		updateSelectedDistrict:function(action, id){
			if(action == 'add' && $scope.selectedDistrict.indexOf(id) == -1){
			   if($scope.isAddOrUp==2){
				   $scope.selectedDistrict=[];
				   var cityId = Number((id+"").substring(0,5));
				   $scope.updateChoiceCitys(action,cityId);
			   }
               $scope.selectedDistrict.push(id);
		    }
		    if(action == 'remove' && $scope.selectedDistrict.indexOf(id)!=-1){
               var idx = $scope.selectedDistrict.indexOf(id);
               $scope.selectedDistrict.splice(idx,1);
		    }
		},
		isChoiceCitys:function(id){
			return $scope.choiceCitys.indexOf(id)>-1;
		},
	    isSelectedDistrict:function(id){
	    	return $scope.selectedDistrict.indexOf(id)>-1;
	    },
	    doReset:function(){
	    	$scope.query={"destOrgId":-1,"provinceId":"","cityId":"","countyId":""};
	    	$scope.source.clear();
	    }
	};
	$scope.$watch('$viewContentLoaded', function() {
		businessArea.init();
    });
}]);