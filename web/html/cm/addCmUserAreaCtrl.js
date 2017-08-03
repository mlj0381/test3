var addCmUserAreaApp = angular.module("addCmUserAreaApp", ['commonApp']);
addCmUserAreaApp.controller("addCmUserAreaCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var userId = getQueryString("userId");
	var tow={
			init:function(){
				this.bindEvent();
				this.selectProvince();
				this.queryAreaList();
				
				$scope.isAreaShow=false;
				$scope.objNameList=new Array();
				//$scope.nameTempList =new Array();
				$scope.districtIds={};
				$scope.districtMapList={};
			},
			bindEvent:function(){
				$scope.queryCity = this.queryCity;
				$scope.queryDistrict = this.queryDistrict;
				$scope.selDistrict = this.selDistrict;
				$scope.districtConfirm = this.districtConfirm;
				$scope.userArea = this.userArea;
				$scope.delUserArea = this.delUserArea;
				$scope.doSaveUserArea = this.doSaveUserArea;
				$scope.delArea = this.delArea;
				$scope.data= this.data;
				$scope.queryAreaList= this.queryAreaList;
				$scope.getAll= this.getAll;
			},
			data:{
				provinceId:"",
				},
			userArea:{},
			/**
			 * 查询省份信息
			 */
			queryAreaList:function(){
				commonService.postUrl("webCmUserInfoBO.ajax?cmd=queryAreaList","userId="+userId,function(data){
					if(data != null && data != undefined && data != ""){
						$scope.userAreaList=data;
					}
				});
			},
			selectProvince:function(){
				commonService.postUrl("staticDataBO.ajax?cmd=selectProvince","",function(data){
					if(data != null && data != undefined && data != ""){
						$scope.provinces = data.items;
					}
				});
				commonService.postUrl("webCmUserInfoBO.ajax?cmd=doQueryCmUserByUserId","userId="+userId,function(data){
					if(data != null && data != undefined && data != ""){
						$scope.userData=data.cmUser[0];
						
					}
				});
			},
			/**
			 * 查询城市信息
			 */
			queryCity:function(obj){
				var param={provinceId:$scope.data.province.id};
				commonService.postUrl("staticDataBO.ajax?cmd=selectCity",param,function(data){
					if(data != null && data != undefined && data != ""){
						$scope.citys = data.items;
						$scope.data.cityName="";
						$scope.districtMapList={};
						$scope.proName = obj.province.name;
						$scope.proId = obj.province.id;
					//	$scope.nameTempList.push(obj.province.name);
					}
				});
			},
			queryDistrict:function(){
				//展示
				if($scope.data.province==undefined  || $scope.data.province.id=='' ){
					commonService.alert('请先选择服务省');
					return;
				}
				$scope.isAreaShow=!$scope.isAreaShow;
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
			getAll:function(){
				$scope.userArea={};
				$scope.userArea.userAreaName=$scope.proName+" "+"全部";
				$scope.data.cityName="全部";
				$scope.userArea.userAreaReId="";
				$scope.userArea.dataType="Y";
				$scope.userArea.userAreaId=$scope.proId;
				$scope.objNameList.push($scope.userArea);
				$scope.isAreaShow=false;
			},
			districtConfirm:function(){
				var idTempList = new Array();
				var nameTempList = new Array();
				for(var objectIndex in $scope.districtMapList){
					var object = $scope.districtMapList[objectIndex];
					if(object!=null){
						idTempList.push(object.id);
						nameTempList.push(object.name);
					}
				}
				$scope.data.districtIds = idTempList.join(',');
				$scope.data.cityName =nameTempList.join(',');
				//var  userAreaName = $scope.proName+" "+$scope.data.cityName;
				$scope.userArea={};
				$scope.userArea.userAreaName=$scope.proName+" "+$scope.data.cityName;
				$scope.userArea.userAreaReId=$scope.data.districtIds;
				$scope.userArea.userAreaId=$scope.proId;
				$scope.objNameList.push($scope.userArea);
				$scope.isAreaShow=false;
			},
			/**
			 * 删除指定元素
			 * @param proviteId
			 */
			delUserArea:function(proviteId){
				if($scope.objNameList!=null && $scope.objNameList.length>0){
					for(var i=0;i<$scope.objNameList.length;i++){
						if($scope.objNameList[i].userAreaId==proviteId){
							$scope.objNameList.splice(i,1);
						}
					}
				}
			},
			/**
			 * 删除
			 * @param userAreaId
			 */
			delArea:function(userAreaId){
				$scope.data.provinceId=userAreaId;
				$scope.data.userId=userId;
				 commonService.confirm("确认取消数据?",function(){
						commonService.postUrl("webCmUserInfoBO.ajax?cmd=delArea",$scope.data,function(data){
							$scope.queryAreaList();
							commonService.alert("取消完成");
							/*if(data != null && data != undefined && data != ""){
							
							}*/
						});
				 });
			},
			doSaveUserArea:function(){
				var postList=new Array();
				if($scope.objNameList==null || $scope.objNameList.length==0){
					commonService.alert("请选择区域");
					return;
				}
				for(var i=0;i<$scope.objNameList.length;i++){
					var obj={};
					obj.userAreaName=$scope.objNameList[i].userAreaName;
					obj.userAreaReId=$scope.objNameList[i].userAreaReId;
					obj.userAreaId=$scope.objNameList[i].userAreaId;
					obj.dataType=$scope.objNameList[i].dataType;
					obj.userId=userId;
					postList.push(obj);
				}
				
				commonService.postUrl("webCmUserInfoBO.ajax?cmd=doSaveUserArea&size="+postList.length,{"list":postList},function(data){
					$scope.queryAreaList();
					postList=new Array();
					$scope.objNameList=new Array();
					commonService.alert("保存完成");
					/*if(data != null && data != undefined && data != ""){
					}*/
				});
			}
	};
	tow.init();
}]);
