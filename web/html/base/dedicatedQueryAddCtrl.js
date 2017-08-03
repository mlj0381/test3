var dedicatedQueryAddApp = angular.module("dedicatedQueryAddApp", ['commonApp']);
dedicatedQueryAddApp.controller("dedicatedQueryAddCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) { 
	var dedicatedQueryAdd ={
		init:function(){
			this.selectProvince();
			this.bindEvent();
		},
		bindEvent:function(){
			$scope.doSave = this.doSave;
			$scope.addProvince = this.addProvince;
			$scope.user=this.user;
			//用来保存提交省份数据
			$scope.provinceArrays = [];
			//删除省份
			$scope.delId = this.delId;
			$scope.close = this.close;
			$scope.curProvinceId = this.curProvinceId;
		},
		curProvinceId:function(provinceId1){
			$scope.arr = {};
			$scope.arr.provinceId = provinceId1;
			var darr = $scope.provinceArrays;
			for(var i=0;i < darr.length;i++){
				if(darr[i].provinceId == $scope.arr.provinceId){
//					commonService.alert(darr[i].provinceName+"已存在，无需再选择！");
					return false;
				}
			}
			if($scope.arr.provinceId != undefined  && $scope.arr.provinceId != null && $scope.arr.provinceId != '' && $scope.arr.provinceId != -1){
				var data = $scope.provinceData;
				$scope.arr.provinceName = "";
				for(var i=0;i<data.items.length;i++){
					 if(data.items[i].id == $scope.arr.provinceId){
						 $scope.arr.provinceName = data.items[i].name;
						 $scope.provinceArrays.push($scope.arr);
						 return ;
					 }
				}
				
			}else{
				commonService.alert("请选择到达省份！");
			}
		},
		//新增到达省份
		addProvince : function(){
			$scope.arr = {};
			$scope.arr.provinceId = $scope.user.provinceId1;
			var darr = $scope.provinceArrays;
			for(var i=0;i < darr.length;i++){
				if(darr[i].provinceId == $scope.arr.provinceId){
//					commonService.alert(darr[i].provinceName+"已存在，无需再选择！");
					return false;
				}
			}
			if($scope.arr.provinceId != undefined  && $scope.arr.provinceId != null && $scope.arr.provinceId != '' && $scope.arr.provinceId != -1){
				var data = $scope.provinceData;
				$scope.arr.provinceName = "";
				for(var i=0;i<data.items.length;i++){
					 if(data.items[i].id == $scope.arr.provinceId){
						 $scope.arr.provinceName = data.items[i].name;
						 $scope.provinceArrays.push($scope.arr);
		//				 $scope.user.provinceId1 = -1;
						 return ;
					 }
				}
				
			}else{
				commonService.alert("请选择到达省份！");
			}
		},
		//删除省份
		delId : function(provinceId){
			var darr = $scope.provinceArrays;
			var arrays = new Array();
			for(var i=0;i < darr.length;i++){
				if(darr[i].provinceId != provinceId){
					arrays.push(darr[i]);
				}
			}
			 $scope.provinceArrays = arrays;
		},
		user :{
			"provinceId":-1,
			"provinceId1":-1,
		},
		selectProvince:function(){
			var param = {"isAll":"Y"};
			var url = "staticDataBO.ajax?cmd=selectProvince";
			commonService.postUrl(url,param,function(user){
				// 成功执行
				if(user!=null && user!=undefined && user!=""){
						$scope.provinceData=user;
	 	    	}
			});
		},
		doSave:function(){
			
			if($scope.user.tenantCode==null || $scope.user.tenantCode==undefined || $scope.user.tenantCode==""){
				commonService.alert("公司编码不能为空");
				return false;
			}
			if($scope.user.name==null || $scope.user.name==undefined || $scope.user.name==""){
				commonService.alert("公司名称不能为空");
				return false;
			}
			
			if($scope.user.linkMan==null || $scope.user.linkMan==undefined || $scope.user.linkMan==""){
				commonService.alert("联系人不能为空");
				return false;
			}
			if($scope.user.linkPhone==null || $scope.user.linkPhone==undefined || $scope.user.linkPhone==""){
				commonService.alert("联系电话不能为空");
				return false;
			}
			if(!validatemobile($scope.user.linkPhone)){
				commonService.alert("请输入正确的手机号码");
				return false;
			}
			if($scope.user.provinceId==null || $scope.user.provinceId==undefined || $scope.user.provinceId=="" ||$scope.user.provinceId== -1 ){
				commonService.alert("起始省份不能为空");
				return false;
			}
			if($scope.provinceArrays==null || $scope.provinceArrays==undefined || $scope.provinceArrays.length== 0 || $scope.provinceArrays == ""){
				commonService.alert("到达省份不能为空");
				return false;
			}
			$scope.user.provinceIds = "";
			for(var i =0;i<$scope.provinceArrays.length;i++){
				if($scope.provinceArrays.length != i+1){
					$scope.user.provinceIds = $scope.user.provinceIds + $scope.provinceArrays[i].provinceId+",";
				}else{
					$scope.user.provinceIds = $scope.user.provinceIds + $scope.provinceArrays[i].provinceId;
				}
				
			}
			var url = "organizationBO.ajax?cmd=doSaveSpecialLine";
			var dataLine = $scope.user;
			commonService.postUrl(url,dataLine,function(data){
					  commonService.alert("保存完成!");
					  commonService.closeToParentTab(true);
			});
		},
		close:function(){
			commonService.closeToParentTab(true);
		},
		};
	dedicatedQueryAdd.init();
}]);