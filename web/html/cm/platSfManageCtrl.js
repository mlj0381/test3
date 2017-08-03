var myApp = angular.module("platSfManageApp", ['commonApp']);
myApp.controller("platSfManageCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var myManage={
		init:function(){
			this.bindEvent();			
			this.initData();
			$scope.doQueryPlat();
		},
		bindEvent:function(){
			$scope.queryCity = this.queryCity;
			$scope.clear = this.clear;
			$scope.initData = this.initData;
			$scope.selectAll = this.selectAll;
			$scope.selectOne = this.selectOne;
			$scope.clear = this.clear;
			$scope.params={};
			$scope.close=this.close;
			$scope.addSf=this.addSf;
			$scope.doQueryPlat=this.doQueryPlat;
		},
		addSf:function(){
			var sfUserIdArray =new Array();
			if($("input[name='check-1']:checked").length==0){
				commonService.alert("请选择一条数据!");
				return false;
			}
			$("input[name='check-1']:checked").each(function(){
				if($(this).val()!=null&&$(this).val()!=undefined&&$(this).val()!=''){
					var data=eval("("+$(this).val()+")");
					sfUserIdArray.push(data.sfUserId);
				}
			});
			var param = {"sfUserIds":sfUserIdArray.join(",")};
			commonService.postUrl("cmSfUserInfoBO.ajax?cmd=addPlatSf",param,function(data){
				if(data =="Y"){
					commonService.alert("新增成功!");
					$scope.close();
				}
			});
		},
		//获取静态数据
		initData:function(){
			commonService.postUrl("staticDataBO.ajax?cmd=selectProvince","",function(data){
				if(data != null && data != undefined && data != ""){
					$scope.provinces = data.items;
				}
			});
		},
		doQueryPlat:function(){
			$scope.params.superManage=1;
			$timeout(function(){
				$scope.sfPage.load({
							url:'cmSfUserInfoBO.ajax?cmd=queryUserInfo',
							params:$scope.params
						});
			},500);
		},
		queryCity:function(provinceId){
			var param={provinceId:provinceId};
			commonService.postUrl("staticDataBO.ajax?cmd=selectCity",param,function(data){
				if(data != null && data != undefined && data != ""){
					$scope.citys = data.items;
				}
			});
		},
		selectCity:function(){
			if($scope.params.provinceId==null){
				commonService.alert("请先选择查询省份");
				return ;
			}
		},
		selectOne : function(batchNum,strId){
			if(document.getElementById(strId+batchNum).checked && document.getElementById(strId+batchNum) != undefined){
				document.getElementById(strId+batchNum).checked=false;
			}else{
				document.getElementById(strId+batchNum).checked=true;
			}
		},
		/**全选*/
		selectAll : function(id,id1) {
			var checkbox = document.getElementsByName(id);
			if (document.getElementById(id1).checked) {
				for (var i = 0; i < checkbox.length; i++) {
					checkbox[i].checked = true;
				}
			} else {
				for (var i = 0; i < checkbox.length; i++) {
					checkbox[i].checked = false;
				}
			}
		},
		clear:function(){
			$scope.params.provinceId='-1';
			$scope.params.cityId='-1';
			$scope.params.sfUserName='';
			$scope.params.sfUserAcct='';
		},
		close:function(){
			commonService.closeToParentTab(true);
		},
	};
	myManage.init();
}]);
